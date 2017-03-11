package com.sb.taoquan.init;

import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.dao.ITop100Dao;
import com.sb.taoquan.entity.QuanProduct;
import com.sb.taoquan.entity.Top100;
import com.sb.taoquan.util.HttpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 小平 on 2017/1/30.
 */
@Component
public class DropInvalidQuanProductListener implements InitializingBean {
    @Autowired
    private ITop100Dao top100Dao;
    @Override
    public void afterPropertiesSet() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<Top100> top100List = top100Dao.query();
                    if (CollectionUtils.isNotEmpty(top100List)) {
                        for (Top100 top100 : top100List) {
                            try {
                                Thread.sleep(1000 * 2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            boolean isValid = checkQuanIsValid(top100.getQuanUrl());
                            if (! isValid) { //券已过期  删除
                                System.out.println("-----------券已过期[" + top100.getTitle() +
                                        "]---------");
                                top100Dao.del(top100.getId());
                            } else {
                                System.out.println("-----------券还有效[" + top100.getTitle() +
                                        "]---------");
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000 * 60 * 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    private static boolean checkQuanIsValid(String quanUrl) {
        boolean isValid = false;
        try {
            String content = HttpUtil.executeGet(quanUrl, new HashMap<String, String>())[0];
            Document document = Jsoup.parse(content);
            Elements elements = document.select(".J_getCoupon");
            if (elements != null && elements.size() > 0) {
                //还有效
                isValid = true;
            }
        } catch (Exception e) {
        }
        return isValid;
    }
}
