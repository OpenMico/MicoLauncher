package com.xiaomi.accountsdk.account.data;

import android.app.Application;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* loaded from: classes2.dex */
public class SendPhoneTicketParams {
    public final ActivatorPhoneInfo activatorPhoneInfo;
    public final String activatorToken;
    public final String captCode;
    public final String captIck;
    public final String deviceId;
    public final String phone;
    public final String phoneHash;
    public final String region;
    public final String serviceId;

    private SendPhoneTicketParams(Builder builder) {
        this.phone = builder.phone;
        this.activatorPhoneInfo = builder.activatorPhoneInfo;
        ActivatorPhoneInfo activatorPhoneInfo = this.activatorPhoneInfo;
        String str = null;
        this.phoneHash = activatorPhoneInfo != null ? activatorPhoneInfo.phoneHash : null;
        ActivatorPhoneInfo activatorPhoneInfo2 = this.activatorPhoneInfo;
        this.activatorToken = activatorPhoneInfo2 != null ? activatorPhoneInfo2.activatorToken : str;
        this.deviceId = builder.deviceId;
        this.serviceId = builder.serviceId;
        this.captCode = builder.captCode;
        this.captIck = builder.captIck;
        this.region = builder.region;
    }

    public static Builder copyFrom(SendPhoneTicketParams sendPhoneTicketParams) {
        if (sendPhoneTicketParams == null) {
            return null;
        }
        return new Builder().phone(sendPhoneTicketParams.phone).phoneHashActivatorToken(sendPhoneTicketParams.activatorPhoneInfo).serviceId(sendPhoneTicketParams.serviceId).deviceId(sendPhoneTicketParams.deviceId).captchaCode(sendPhoneTicketParams.captCode, sendPhoneTicketParams.captIck).region(sendPhoneTicketParams.region);
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String captCode;
        private String captIck;
        private String deviceId;
        private String phone;
        private String region;
        private String serviceId;

        public Builder phone(String str) {
            this.phone = str;
            return this;
        }

        public Builder phoneHashActivatorToken(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder captchaCode(String str, String str2) {
            this.captCode = str;
            this.captIck = str2;
            return this;
        }

        public Builder region(String str) {
            this.region = str;
            return this;
        }

        public Builder application(Application application) {
            XMPassportSettings.setApplicationContext(application);
            return this;
        }

        public SendPhoneTicketParams build() {
            return new SendPhoneTicketParams(this);
        }
    }
}
