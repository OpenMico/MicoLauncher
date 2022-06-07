package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class OAuthParameter {
    public String aUthorizedDeviceId;
    public String cUserId;
    public String clientId;
    public String deviceId;
    public String locale;
    public String platform;
    public String redirectUri;
    public String responseType;
    public String scope;
    public String serviceToken;
    public String state;
    public boolean useCUserId;
    public String userId;

    private OAuthParameter(Builder builder) {
        this.userId = builder.userId;
        this.clientId = builder.clientId;
        this.redirectUri = builder.redirectUri;
        this.scope = builder.scope;
        this.responseType = builder.responseType;
        this.serviceToken = builder.serviceToken;
        this.platform = builder.platform;
        this.aUthorizedDeviceId = builder.aUthorizedDeviceId;
        this.state = builder.state;
        this.deviceId = builder.deviceId;
        this.cUserId = builder.cUserId;
        this.useCUserId = builder.useCUserId;
        this.locale = builder.locale;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private String aUthorizedDeviceId;
        private String cUserId;
        private String clientId;
        private String deviceId;
        private String locale;
        private String platform;
        private String redirectUri;
        private String responseType;
        private String scope;
        private String serviceToken;
        private String state;
        private boolean useCUserId;
        private String userId;

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public Builder clientId(String str) {
            this.clientId = str;
            return this;
        }

        public Builder redirectUri(String str) {
            this.redirectUri = str;
            return this;
        }

        public Builder scope(String str) {
            this.scope = str;
            return this;
        }

        public Builder responseType(String str) {
            this.responseType = str;
            return this;
        }

        public Builder serviceToken(String str) {
            this.serviceToken = str;
            return this;
        }

        public Builder platform(String str) {
            this.platform = str;
            return this;
        }

        public Builder aUthorizedDeviceId(String str) {
            this.aUthorizedDeviceId = str;
            return this;
        }

        public Builder state(String str) {
            this.state = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder cUserId(String str) {
            this.cUserId = str;
            return this;
        }

        public Builder useCUserId(boolean z) {
            this.useCUserId = z;
            return this;
        }

        public Builder locale(String str) {
            this.locale = str;
            return this;
        }

        public OAuthParameter build() {
            return new OAuthParameter(this);
        }
    }
}
