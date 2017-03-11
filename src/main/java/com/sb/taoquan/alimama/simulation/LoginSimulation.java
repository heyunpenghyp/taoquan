package com.sb.taoquan.alimama.simulation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sb.taoquan.util.HttpUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by tytx02 on 2017/1/20.
 */
public class LoginSimulation {
    public static String testUrl = "http://pub.alimama.com/common/getUnionPubContextInfo.json";
    public static WebDriver driver = null;
    public synchronized  static boolean login(boolean isManual) {
        if (AlimamaCookie.isLoginSucc) { //可能会有同步锁的问题
            return true;
        }
        //赋值给操作时间
        if (isManual) {
            AlimamaCookie.lastManualTryTime = new Date();
            AlimamaCookie.isManualLogin = true;
            AlimamaCookie.isMachineLogin = false;
        } else {
            AlimamaCookie.lastMachineTryTime = new Date();
            AlimamaCookie.isManualLogin = false;
            AlimamaCookie.isMachineLogin = true;
        }
        boolean isLoginSucc = false;
        try {
            //模拟浏览器登录
            String indexUrl = "http://www.alimama.com/index.htm";
            String loginUrl = "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http%3A%2F%2Fwww.alimama.com&full_redirect=true&disableQuickLogin=true";
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("ignore-certificate-errors");
            options.addArguments("incognito");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            String basePath = LoginSimulation.class.getClass().getResource("/").getPath();
            System.setProperty("webdriver.chrome.driver", basePath + "chromedriver.exe");
            driver = new ChromeDriver(capabilities);
            driver.get(indexUrl);
            driver.get(loginUrl);
            Thread.sleep(5 * 1000);
            try {
                driver.findElement(By.id("J_Quick2Static")).click();
            } catch (Exception e) {
                System.out.println("本来就是非扫二维码模式了");
            }
            WebElement userNameElement = driver.findElement(By.id("TPL_username_1"));
            Actions actions = new Actions(driver);
            actions.moveToElement(userNameElement);
            actions.click(userNameElement);
            actions.perform();
            userNameElement.clear();
            userNameElement.sendKeys("盏盏的夏天");
            WebElement psdElement = driver.findElement(By.id("TPL_password_1"));
            psdElement.clear();
            psdElement.sendKeys("wuwenqi,1234");
            WebElement subElement = driver.findElement(By.id("J_SubmitStatic"));
            if (driver.findElement(By.id("nc_1_n1z")) != null) {
                System.out.println("出现了滚动条，进入滚动条模式");
                Actions builder = new Actions(driver);
                WebElement source = driver.findElement(By.id("nc_1_n1z"));
                builder.dragAndDropBy(source, 300, 0).perform();
                Thread.sleep(2 * 1000);
            }
            subElement.click();

            //人工登录为何要停顿两分钟 是因为淘宝会出现繁琐的多余验证 机器无法简单识别
            if (isManual) {
                Thread.sleep(45 * 1000);
            } else {
                Thread.sleep(5 * 1000);
            }
            if (! isManual) {
                isNeedToSwitchToScrollMode();
                Thread.sleep(5 * 1000);
                //判断是否再出现一次滚动条
                isNeedToSwitchToScrollMode();
            }


            //获取cookie
            Set<Cookie> cookieSet = driver.manage().getCookies();
            String cookie = "";
            String _tb_token = "";
            for (Cookie cookieTmp : cookieSet) {
                if (cookieTmp.getName().equals("_tb_token_")) {
                    _tb_token = cookieTmp.getValue();
                }
                cookie += cookieTmp.getName() + "=" + cookieTmp.getValue() + "; ";
            }
            AlimamaCookie._tb_token_ = _tb_token;
            AlimamaCookie.cookie = cookie;

            //测试是否登录成功
            isLoginSucc = testLoginFlag(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            AlimamaCookie.loginErrorLog = e.getMessage();
        } finally {
            driver.quit();
        }
        AlimamaCookie.isLoginSucc = isLoginSucc;
        if (isLoginSucc) {
            AlimamaCookie.tryLoginNum = 0;
        } else {
            if (! isManual) {
                AlimamaCookie.tryLoginNum++;
            }
        }

        return isLoginSucc;
    }

    private static void isNeedToSwitchToScrollMode() throws IOException, InterruptedException {
        if (driver.findElement(By.id("nc_1_n1z")) != null) {
            System.out.println("出现了滚动条，进入滚动条模式");
            WebElement psdElement = driver.findElement(By.id("TPL_password_1"));
            psdElement.clear();
            ((JavascriptExecutor) driver).executeScript("document.getElementById('TPL_password_1').value='wuwenqi,1234'");
            Thread.sleep(1000);
            Actions builder = new Actions(driver);
            WebElement source = driver.findElement(By.id("nc_1_n1z"));
            builder.dragAndDropBy(source, 300, 0).perform();
            Thread.sleep(1000);
            WebElement subElement = driver.findElement(By.id("J_SubmitStatic"));
            subElement.click();
        }
    }

    public static boolean testLoginFlag(String cookie) {
        boolean isLoginSucc = false;
        Map map = new HashMap<String, String>();
        map.put(HttpUtil.COOKIE, cookie);
        try {
            for (int i = 0; i < 3; i++) {
                try {
                    String content = HttpUtil.executeGet(testUrl, map)[0];
                    if (content.contains("mmNick")) {
                        isLoginSucc = true;
                    }
                    break;
                } catch (Exception e) {
                    continue;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLoginSucc;
    }

//    public static void main(String[] args) {
//        testLoginFlag("cookie32=c63122cd4eec17d0d4e9bf454afb8198; cookie31=MTA5MzQ4NDc2LCVFNyU5QiU4RiVFNyU5QiU4RiVFNyU5QSU4NCVFNSVBNCU4RiVFNSVBNCVBOSxoZXl1bnBlbmdoeXBAMTI2LmNvbSxUQg%3D%3D; alimamapw=FiZUEnQBF3NWF3RQRntQFXUGFiEnEnUAF3QgF3MmaglQVAIEUlVWB1QBBQ5RUVBVAApSAwVQAlVQ%0AUQIFVlIB; l=AnZ2nKtl3LOUP6ndsnVKVUxdRqZ4l7rR; login=Vq8l%2BKCLz3%2F65A%3D%3D; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgNi4xOyBXT1c2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzU2LjAuMjkyNC44NyBTYWZhcmkvNTM3LjM2; cookie2=064172fe66eb1a8c47a1823abe3270dd; t=12d8a678a48953e71330b6986361730f; v=0; cna=glYjETd+bxICAXdi0MBFkLjt; apushd85657908926fb1d590ad7a35c5f4ae9=%7B%22ts%22%3A1486645581004%2C%22parentId%22%3A1486645454366%7D; isg=AuLiWbS6zqGgaNL9NFb8e5W7M2gcleZNr894yyx7DtUA_4J5FMM2XWj9WYz5; _tb_token_=U3sqlxkTLq;");
//    }

}
