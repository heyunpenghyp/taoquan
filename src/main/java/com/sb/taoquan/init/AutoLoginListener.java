package com.sb.taoquan.init;

import com.sb.taoquan.alimama.simulation.AlimamaCookie;
import com.sb.taoquan.alimama.simulation.DownloadQuanXls;
import com.sb.taoquan.alimama.simulation.LoginSimulation;
import com.sb.taoquan.alimama.simulation.XlsQuanToDb;
import com.sb.taoquan.dao.IQuanProductDao;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 小平 on 2017/1/28.
 */
//@Component("autoLoginListener")
public class AutoLoginListener implements InitializingBean {
    @Autowired
    private IQuanProductDao quanProductDao;
    @Autowired
    private XlsQuanToDb xlsQuanToDb;
    @Autowired
    private DownloadQuanXls downloadQuanXls;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("---------start to login-------------");
        LoginSimulation.login(false);//机器登录
        System.out.println("---------end login-------------");
        //监听登录失效状况
        ScheduledExecutorService inspectLoginExecutor = Executors.newScheduledThreadPool(1);
        Runnable inspectLoginRunnable = new Runnable() {
            @Override
            public void run() {
                AlimamaCookie.isLoginSucc = LoginSimulation.testLoginFlag(AlimamaCookie.cookie);
                if (! AlimamaCookie.isLoginSucc && AlimamaCookie.tryLoginNum < 3) {
                    LoginSimulation.login(false);
                    AlimamaCookie.tryLoginNum++;
                }
                if (AlimamaCookie.isLoginSucc) {
                    AlimamaCookie.tryLoginNum = 0;
                }
            }
        };
        inspectLoginExecutor.scheduleAtFixedRate(
                inspectLoginRunnable,
                0,
                1000 * 120,
                TimeUnit.MILLISECONDS);


        //每日早上10:05分开始下载内部券商品
        Date xlsToDbDay = quanProductDao.getXlsToDbDay();
        AlimamaCookie.lastDownloadSussTime = xlsToDbDay;
        ScheduledExecutorService downloadExecutor = Executors.newScheduledThreadPool(1);
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay  = getTimeMillis("19:24:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        Runnable downloadRunnable = new Runnable() {
            @Override
            public void run() {
                Date xlsToDbDay = quanProductDao.getXlsToDbDay();
                AlimamaCookie.lastDownloadSussTime = xlsToDbDay;
                AlimamaCookie.xlsToDbProcessSuccNum = 0;
                AlimamaCookie.xlsToDbProcessErrorNum = 0;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (AlimamaCookie.isLoginSucc) {
                    AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + "正准备下载,系统会吃掉大量内存，会比较卡，估计时间花费在20分钟";
                    if (! DateUtils.isSameDay(xlsToDbDay, new Date())) {
                        boolean downloadFlag = downloadQuanXls.download();
                        try {
                            if (downloadFlag) {
                                xlsQuanToDb.xlsQuanToDb();
                            } else {
                                throw new RuntimeException("下载过程中失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " 下载过程中失败了:" + e.getMessage();
                        }
                    }
                    AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " 下载成功";
                } else {
                    AlimamaCookie.downloadInfo = "["+df.format(new Date())+"]" + " cookie失效，下载失败，请登录护航后，触发下载护航";
                }
            }
        };
        downloadExecutor.scheduleAtFixedRate(
                downloadRunnable,
                initDelay,
                oneDay,
                TimeUnit.MILLISECONDS);
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
