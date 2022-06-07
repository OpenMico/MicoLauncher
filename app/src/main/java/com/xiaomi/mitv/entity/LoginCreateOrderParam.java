package com.xiaomi.mitv.entity;

import com.xiaomi.mitv.annotation.Required;

/* loaded from: classes4.dex */
public class LoginCreateOrderParam extends BaseOrderParam {
    @Required
    private String appId;
    @Required
    private Integer biz;
    @Required
    private String customerOrderId;
    private String extraData;
    @Required
    private String mac;
    private String orderDesc;
    private String psubcode;
    private String sn;
    @Required
    private Long trxAmount;
    private String userLocale;
    @Required
    private Long userid;

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long l) {
        this.userid = l;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public Integer getBiz() {
        return this.biz;
    }

    public void setBiz(Integer num) {
        this.biz = num;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String str) {
        this.sn = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
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

    public String getOrderDesc() {
        return this.orderDesc;
    }

    public void setOrderDesc(String str) {
        this.orderDesc = str;
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
}
