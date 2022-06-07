package com.xiaomi.mitv.entity;

import com.xiaomi.mitv.annotation.Required;

/* loaded from: classes4.dex */
public class SignInfoParam {
    @Required
    private Long appId;
    @Required
    private Integer biz;
    @Required
    private String bizChannel;
    private String customerSignId;
    private String mac;
    @Required
    private Long orderAmount;
    private Integer payType;
    private String phone;
    private String planId;
    @Required
    private Integer platform;
    @Required
    private String productId;
    @Required
    private String productName;
    @Required
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public Integer getPlatform() {
        return this.platform;
    }

    public void setPlatform(Integer num) {
        this.platform = num;
    }

    public Integer getBiz() {
        return this.biz;
    }

    public void setBiz(Integer num) {
        this.biz = num;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public String getCustomerSignId() {
        return this.customerSignId;
    }

    public void setCustomerSignId(String str) {
        this.customerSignId = str;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public Long getAppId() {
        return this.appId;
    }

    public void setAppId(Long l) {
        this.appId = l;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer num) {
        this.payType = num;
    }

    public Long getOrderAmount() {
        return this.orderAmount;
    }

    public void setOrderAmount(Long l) {
        this.orderAmount = l;
    }

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String str) {
        this.planId = str;
    }

    public String getBizChannel() {
        return this.bizChannel;
    }

    public void setBizChannel(String str) {
        this.bizChannel = str;
    }

    public String toString() {
        return "SignInfoParam{userId='" + this.userId + "', mac='" + this.mac + "', platform=" + this.platform + ", biz=" + this.biz + ", productId='" + this.productId + "', productName='" + this.productName + "', customerSignId='" + this.customerSignId + "', phone='" + this.phone + "', appId=" + this.appId + ", payType=" + this.payType + ", orderAmount=" + this.orderAmount + ", planId=" + this.planId + ", bizChannel=" + this.bizChannel + '}';
    }
}
