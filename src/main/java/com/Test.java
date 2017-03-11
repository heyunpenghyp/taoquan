package com;

import com.sb.taoquan.util.HttpUtil;
import com.show.api.ShowApiRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tytx02 on 2017/1/14.
 */
public class Test {
    public static void main(String[] args) throws Exception {

//        String res=new ShowApiRequest("http://route.showapi.com/269-1",
//                "30807","6a696aa349ca4df2a7b3b3d0e2c5eff7")
//                .addTextPara("precise","0")
//                .addTextPara("debug","0")
//                .addTextPara("text","口红 豆沙色")
//                .post();
//        System.out.println(res);
        boolean isValid = false;
        try {
            String url = "http://shop.m.taobao.com/shop/coupon.htm?seller_id=1667471044&activity_id=732b737bf6c6402d889e90bd2d6f586c";
            String content = HttpUtil.executeGet(url, new HashMap<String, String>())[0];
            String restQuanNum = Jsoup.parse(content).select(".rest").get(0).text();
            if (Integer.parseInt(restQuanNum)  > 0) {
                isValid = true;
            }
        } catch (Exception e) {

        }

         System.out.println(isValid);
    }
}
