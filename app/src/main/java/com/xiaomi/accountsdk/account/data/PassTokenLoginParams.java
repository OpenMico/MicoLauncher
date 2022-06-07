package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class PassTokenLoginParams {
    public final String deviceId;
    public final boolean isGetPhoneTicketLoginMetaData;
    public final String loginRequestUrl;
    public final String passToken;
    public final boolean returnStsUrl;
    public final String serviceId;
    public final String userId;

    private PassTokenLoginParams(Builder builder) {
        this.userId = builder.userId;
        this.passToken = builder.passToken;
        this.serviceId = builder.serviceId;
        this.loginRequestUrl = builder.loginRequestUrl;
        this.deviceId = builder.deviceId;
        this.returnStsUrl = builder.returnStsUrl;
        this.isGetPhoneTicketLoginMetaData = builder.isGetPhoneTicketLoginMetaData;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String deviceId;
        private String loginRequestUrl;
        private final String passToken;
        private final String serviceId;
        private final String userId;
        private boolean returnStsUrl = false;
        private boolean isGetPhoneTicketLoginMetaData = false;

        public Builder(String str, String str2, String str3) {
            this.userId = str;
            this.passToken = str2;
            this.serviceId = str3;
        }

        public Builder loginRequestUrl(String str) {
            this.loginRequestUrl = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder isReturnStsUrl(boolean z) {
            this.returnStsUrl = z;
            return this;
        }

        public Builder isGetPhoneTicketLoginMetaData(boolean z) {
            this.isGetPhoneTicketLoginMetaData = z;
            return this;
        }

        public PassTokenLoginParams build() {
            return new PassTokenLoginParams(this);
        }
    }
}
