package com.sb.taoquan.alimama.simulation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.entity.QuanProduct;
import com.sb.taoquan.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by tytx02 on 2017/1/25.
 */
@Component
public class QQHDApplySimulation {
    private static String qqhd2V1Url = "https://uland.taobao.com/coupon/edetail?activityId={activityId}&pid=" + Constants.webPid + "&itemId={itemId}&src=njrt_qytk&dx=0";
    private static IQuanProductDao quanProductDao;

    @Autowired(required = true)
    public QQHDApplySimulation(@Qualifier("quanProductDao")  IQuanProductDao quanproductDao) {
        QQHDApplySimulation.quanProductDao = quanproductDao;
    }
    public static boolean isHd(QuanProduct quanProduct) throws Exception {
        String qqhdQueryApi = "http://pub.alimama.com/items/channel/qqhd.json?channel=qqhd&perPageSize=50&shopTag=&t={t}&q={itemUrl}&_tb_token_={_tb_token_}";
        long t = (new Date()).getTime();
        qqhdQueryApi = qqhdQueryApi.replace("{t}", String.valueOf(t));
        qqhdQueryApi = qqhdQueryApi.replace("{_tb_token_}", AlimamaCookie._tb_token_);
        qqhdQueryApi = qqhdQueryApi.replace("{itemUrl}", quanProduct.getItemUrl());
        Map map = new HashMap<>();
        map.put(HttpUtil.COOKIE, AlimamaCookie.cookie);
        Thread.sleep(100 * (new Random().nextInt(5) + 10));
        String content = HttpUtil.executeGet(qqhdQueryApi, map)[0];
        JSONObject qqhdJSON = JSON.parseObject(content);
        if (qqhdJSON.getJSONObject("data").containsKey("pageList")) {
            JSONArray qqhdArray = qqhdJSON.getJSONObject("data").getJSONArray("pageList");
            if (qqhdArray != null) {
                double commissionRate = qqhdArray.getJSONObject(0).getDouble("eventRate");
                quanProduct.setCommissionRebate(commissionRate);
                quanProductDao.updateQqhdTransfer(quanProduct);
                return true;
            }
        }

        return false;
    }
}
