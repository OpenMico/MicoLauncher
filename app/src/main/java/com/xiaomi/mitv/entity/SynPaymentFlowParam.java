package com.xiaomi.mitv.entity;

import com.xiaomi.mitv.annotation.Required;

/* loaded from: classes4.dex */
public class SynPaymentFlowParam {
    private String activityDescription;
    @Required
    private Long appId;
    @Required
    private String cpName;
    @Required
    private String flowTypeName;
    private String macWire;
    private String macWireless;
    private String openVipMovieId;
    private String openVipMovieName;
    @Required
    private Long orderId;
    @Required
    private Integer orderPrice;
    @Required
    private String userId;
    private String userIp;
    @Required
    private String vipActiveTime;
    @Required
    private String vipExpireTime;
    @Required
    private String vipPayTime;
    @Required
    private String vipTypeName;

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long l) {
        this.orderId = l;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public Long getAppId() {
        return this.appId;
    }

    public void setAppId(Long l) {
        this.appId = l;
    }

    public String getCpName() {
        return this.cpName;
    }

    public void setCpName(String str) {
        this.cpName = str;
    }

    public String getFlowTypeName() {
        return this.flowTypeName;
    }

    public void setFlowTypeName(String str) {
        this.flowTypeName = str;
    }

    public String getVipTypeName() {
        return this.vipTypeName;
    }

    public void setVipTypeName(String str) {
        this.vipTypeName = str;
    }

    public String getVipPayTime() {
        return this.vipPayTime;
    }

    public void setVipPayTime(String str) {
        this.vipPayTime = str;
    }

    public String getVipActiveTime() {
        return this.vipActiveTime;
    }

    public void setVipActiveTime(String str) {
        this.vipActiveTime = str;
    }

    public String getVipExpireTime() {
        return this.vipExpireTime;
    }

    public void setVipExpireTime(String str) {
        this.vipExpireTime = str;
    }

    public String getOpenVipMovieId() {
        return this.openVipMovieId;
    }

    public void setOpenVipMovieId(String str) {
        this.openVipMovieId = str;
    }

    public String getOpenVipMovieName() {
        return this.openVipMovieName;
    }

    public void setOpenVipMovieName(String str) {
        this.openVipMovieName = str;
    }

    public Integer getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(Integer num) {
        this.orderPrice = num;
    }

    public String getActivityDescription() {
        return this.activityDescription;
    }

    public void setActivityDescription(String str) {
        this.activityDescription = str;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String str) {
        this.userIp = str;
    }

    public String getMacWireless() {
        return this.macWireless;
    }

    public void setMacWireless(String str) {
        this.macWireless = str;
    }

    public String getMacWire() {
        return this.macWire;
    }

    public void setMacWire(String str) {
        this.macWire = str;
    }
}
