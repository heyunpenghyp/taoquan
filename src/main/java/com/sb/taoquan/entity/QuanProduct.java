package com.sb.taoquan.entity;

import java.util.Date;

/**
 * Created by 小平 on 2017/1/28.
 */
public class QuanProduct {
    private String productId;
    private String productName;
    private String imageUrl;
    private String itemUrl;
    private String category;
    private double price;
    private int soldNum;
    private double commissionRebate;
    private String commission;
    private String shopName;
    private String sellId;
    private String platName;
    private String quanId;
    private int quanTotalNum;
    private String quanInfo;
    private Date quanBeginDate;
    private Date quanEndDate;
    private String quanUrl;
    private String composeUrl;
    private int transfer = 2;//是否已经转链
    private long quanWeight;
    private int isHd;
    private Date createTime;
    private Date updateTime;
    private Date lastCheckTime;

    public int getIsHd() {
        return isHd;
    }

    public void setIsHd(int isHd) {
        this.isHd = isHd;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCommissionRebate() {
        return commissionRebate;
    }

    public void setCommissionRebate(double commissionRebate) {
        this.commissionRebate = commissionRebate;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getQuanId() {
        return quanId;
    }

    public void setQuanId(String quanId) {
        this.quanId = quanId;
    }

    public String getQuanInfo() {
        return quanInfo;
    }

    public void setQuanInfo(String quanInfo) {
        this.quanInfo = quanInfo;
    }

    public Date getQuanBeginDate() {
        return quanBeginDate;
    }

    public void setQuanBeginDate(Date quanBeginDate) {
        this.quanBeginDate = quanBeginDate;
    }

    public Date getQuanEndDate() {
        return quanEndDate;
    }

    public void setQuanEndDate(Date quanEndDate) {
        this.quanEndDate = quanEndDate;
    }

    public String getQuanUrl() {
        return quanUrl;
    }

    public void setQuanUrl(String quanUrl) {
        this.quanUrl = quanUrl;
    }

    public String getComposeUrl() {
        return composeUrl;
    }

    public void setComposeUrl(String composeUrl) {
        this.composeUrl = composeUrl;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public long getQuanWeight() {
        return quanWeight;
    }

    public void setQuanWeight(long quanWeight) {
        this.quanWeight = quanWeight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public int getQuanTotalNum() {
        return quanTotalNum;
    }

    public void setQuanTotalNum(int quanTotalNum) {
        this.quanTotalNum = quanTotalNum;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }
}
