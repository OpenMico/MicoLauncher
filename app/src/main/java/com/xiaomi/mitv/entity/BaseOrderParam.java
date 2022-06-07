package com.xiaomi.mitv.entity;

import com.xiaomi.mitv.annotation.Required;

/* loaded from: classes4.dex */
public class BaseOrderParam {
    @Required
    private String country;
    @Required
    private String deviceID;
    @Required
    private String language;
    @Required
    private Integer platform;
    @Required
    private String sdk_version;

    public String getDeviceID() {
        return this.deviceID;
    }

    public void setDeviceID(String str) {
        this.deviceID = str;
    }

    public Integer getPlatform() {
        return this.platform;
    }

    public void setPlatform(Integer num) {
        this.platform = num;
    }

    public String getSdk_version() {
        return this.sdk_version;
    }

    public void setSdk_version(String str) {
        this.sdk_version = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }
}
