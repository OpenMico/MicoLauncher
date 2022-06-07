package com.xiaomi.mitv.entity;

import com.xiaomi.mitv.annotation.Required;

/* loaded from: classes4.dex */
public class UnLoginCreateOrderParam extends BaseOrderParam {
    @Required
    private Long appId;
    @Required
    private Integer biz;
    private String codever;
    @Required
    private String customerOrderId;
    private String extraData;
    private Integer isLogin;
    @Required
    private String mac;
    private String orderDesc;
    private String psubcode;
    private String returnUrl;
    private String rid;
    private String sn;
    private String thirdUserId;
    @Required
    private Long trxAmount;
    private String userLocale;

    public String getSn() {
        return this.sn;
    }

    public void setSn(String str) {
        this.sn = str;
    }

    public String getCustomerOrderId() {
        return this.customerOrderId;
    }

    public void setCustomerOrderId(String str) {
        this.customerOrderId = str;
    }

    public Long getTrxAmount() {
        return this.trxAmount;
    }

    public void setTrxAmount(Long l) {
        this.trxAmount = l;
    }

    public Long getAppId() {
        return this.appId;
    }

    public void setAppId(Long l) {
        this.appId = l;
    }

    public String getOrderDesc() {
        return this.orderDesc;
    }

    public void setOrderDesc(String str) {
        this.orderDesc = str;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String str) {
        this.rid = str;
    }

    public Integer getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(Integer num) {
        this.isLogin = num;
    }

    public Integer getBiz() {
        return this.biz;
    }

    public void setBiz(Integer num) {
        this.biz = num;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public void setExtraData(String str) {
        this.extraData = str;
    }

    public String getUserLocale() {
        return this.userLocale;
    }

    public void setUserLocale(String str) {
        this.userLocale = str;
    }

    public String getPsubcode() {
        return this.psubcode;
    }

    public void setPsubcode(String str) {
        this.psubcode = str;
    }

    public String getCodever() {
        return this.codever;
    }

    public void setCodever(String str) {
        this.codever = str;
    }

    public String getReturnUrl() {
        return this.returnUrl;
    }

    public void setReturnUrl(String str) {
        this.returnUrl = str;
    }

    public String getThirdUserId() {
        return this.thirdUserId;
    }

    public void setThirdUserId(String str) {
        this.thirdUserId = str;
    }
}
