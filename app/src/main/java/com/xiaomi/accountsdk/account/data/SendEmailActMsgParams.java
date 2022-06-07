package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class SendEmailActMsgParams {
    public final String captIck;
    public final String captcha;
    public final String deviceId;
    public final String email;
    public final String identityAuthToken;
    public final PassportInfo passportInfo;

    private SendEmailActMsgParams(Builder builder) {
        this.passportInfo = builder.passportInfo;
        this.identityAuthToken = builder.identityAuthToken;
        this.email = builder.email;
        this.deviceId = builder.deviceId;
        this.captcha = builder.captcha;
        this.captIck = builder.captIck;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String captIck;
        private String captcha;
        private String deviceId;
        private String email;
        private String identityAuthToken;
        private PassportInfo passportInfo;

        public Builder passportInfo(PassportInfo passportInfo) {
            this.passportInfo = passportInfo;
            return this;
        }

        public Builder identityAuthToken(String str) {
            this.identityAuthToken = str;
            return this;
        }

        public Builder emailAddress(String str) {
            this.email = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder captcha(String str, String str2) {
            this.captcha = str;
            this.captIck = str2;
            return this;
        }

        public SendEmailActMsgParams build() {
            return new SendEmailActMsgParams(this);
        }
    }
}
