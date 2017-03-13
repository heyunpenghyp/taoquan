package com.sb.taoquan.entity;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public class Top100 {
    private int id;
    private String picUrl;
    private String title;
    private String quanUrl;
    private int quanAmount;
    private double price;
    private int soldNum;
    private String tkl;
    private Date createTime;

    private String sxInfo;
    private String creatTimeDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuanUrl() {
        return quanUrl;
    }

    public void setQuanUrl(String quanUrl) {
        this.quanUrl = quanUrl;
    }

    public int getQuanAmount() {
        return quanAmount;
    }

    public void setQuanAmount(int quanAmount) {
        this.quanAmount = quanAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public String getTkl() {
        return tkl;
    }

    public void setTkl(String tkl) {
        this.tkl = tkl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSxInfo() {
        if (DateUtils.addMinutes(createTime, 60).getTime() > (new Date()).getTime()) {
            return "一小时内上新";
        }
        return "";
    }

    public String getCreatTimeDesc() {
        if (createTime != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(createTime);
        }
        return "";
    }
}
