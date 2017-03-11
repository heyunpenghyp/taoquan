package com.sb.taoquan.alimama.simulation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 小平 on 2017/1/28.
 */
@Component
public class DownloadQuanXls {
    public static final int cache = 10 * 1024;

    public boolean download() {
        boolean downloadFlag = true;
        int forNum = 0;
        String downloadUrl = "http://pub.alimama.com/coupon/qq/export.json?adzoneId=" + Constants.adzoneId + "&siteId=" + Constants.siteId;
        InputStream is = null;
        FileOutputStream fileout = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            File file = new File("C:\\quan\\" +  df.format(new Date()) + ".xls");
            if (file.exists()) {
                file.delete();
            }
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(downloadUrl);
            httpget.setHeader("Cookie", AlimamaCookie.cookie);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            file.getParentFile().mkdirs();
            fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer=new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer,0,ch);
                forNum++;
            }
        } catch (Exception e) {
            downloadFlag = false;
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fileout.flush();
                fileout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (forNum < 10) {
            downloadFlag = false;
        }
        return downloadFlag;
    }
}
