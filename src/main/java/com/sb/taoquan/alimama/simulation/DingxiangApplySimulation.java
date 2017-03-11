package com.sb.taoquan.alimama.simulation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sb.taoquan.dao.IQuanProductDao;
import com.sb.taoquan.entity.QuanProduct;
import com.sb.taoquan.util.HttpUtil;
import com.sb.taoquan.util.HttpXmlClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by tytx02 on 2017/1/25.
 */
@Component
public class DingxiangApplySimulation {
    private static String dx2V1Url = "https://uland.taobao.com/coupon/edetail?activityId={activityId}&pid=" + Constants.webPid + "&itemId={itemId}&src=njrt_qytk&dx=1";
    private static IQuanProductDao quanProductDao;

    @Autowired(required = true)
    public DingxiangApplySimulation(@Qualifier("quanProductDao")  IQuanProductDao quanproductDao) {
        DingxiangApplySimulation.quanProductDao = quanproductDao;
    }

    public static void apply(QuanProduct quanProduct) throws Exception {
        String queryDxApi = "http://pub.alimama.com/pubauc/getCommonCampaignByItemId.json?itemId={productId}&t={t}&_tb_token_={_tb_token_}";
        long t = (new Date()).getTime();
        queryDxApi = queryDxApi.replace("{t}", String.valueOf(t));
        queryDxApi = queryDxApi.replace("{_tb_token_}", AlimamaCookie._tb_token_);
        queryDxApi = queryDxApi.replace("{productId}", quanProduct.getProductId());
        Map map = new HashMap<>();
        map.put(HttpUtil.COOKIE, AlimamaCookie.cookie);
        Thread.sleep(100 * (new Random().nextInt(5) + 10));
        String content = HttpUtil.executeGet(queryDxApi, map)[0];
        JSONObject dxCampJSON = JSON.parseObject(content);
        JSONArray jsonArray = null;
        try {
            jsonArray = dxCampJSON.getJSONArray("data");
        } catch (ClassCastException e) {
            JSONObject jsonObject = dxCampJSON.getJSONObject("data");
            if (dxCampJSON.getJSONObject("data").size() != 0) {
                jsonArray = new JSONArray();
                jsonArray.add(jsonObject);
            }
        }
        if (jsonArray == null) {
            System.out.println(quanProduct.getItemUrl() + "无定向计划，自动忽略----------");
            quanProductDao.updateNoDxTransfer(quanProduct);
            return;
        }

        double commissionRate = 0;
        String campId = null;
        String keeperid = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            double commissionRateTmp = jsonObject.getDouble("commissionRate");
            if (commissionRate < commissionRateTmp) {
                commissionRate = commissionRateTmp;
                campId = jsonObject.getString("CampaignID");
                keeperid = jsonObject.getString("ShopKeeperID");
            }
        }
        String applyDxApi = "http://pub.alimama.com/pubauc/applyForCommonCampaign.json";
        map = new HashMap<>();
        map.put("campId", campId);
        map.put("keeperid", keeperid);
        map.put("applyreason", "淘客日推上千单，按成交收费，联系QQ：2467389489, 欢迎联系咨询，绝对给力！千语淘客联盟推广会员，请通过！");
        map.put("t", String.valueOf(t));
        map.put("_tb_token_", AlimamaCookie._tb_token_);
        Thread.sleep(100 * (new Random().nextInt(5) + 10));
        content = HttpXmlClient.post(applyDxApi, map, AlimamaCookie.cookie);
        JSONObject responseJSON = JSON.parseObject(content);
        //{"data":null,"info":{"message":"您已经在申请该计划或您已经申请过该掌柜计划!!","ok":false},"ok":false,"invalidKey":null}
        if (responseJSON.getString("ok").equals("true")
                || (responseJSON.getJSONObject("info").getString("message").contains("已经"))) {
            String composeUrl = dx2V1Url.replace("{activityId}", quanProduct.getQuanId()).replace("{itemId}", quanProduct.getProductId());
            quanProduct.setComposeUrl(composeUrl);
            quanProduct.setCommissionRebate(commissionRate);
            quanProductDao.updateDXTransfer(quanProduct);
            AlimamaCookie.applyDxSuccNum++;
        }
    }

}
