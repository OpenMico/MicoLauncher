package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class QueryPhoneInfoParams {
    public final ActivatorPhoneInfo activatorPhoneInfo;
    public final String activatorToken;
    public final String deviceId;
    public final String phone;
    public final String phoneHash;
    public final String serviceId;
    public final String ticket;

    private QueryPhoneInfoParams(Builder builder) {
        this.phone = builder.phone;
        this.deviceId = builder.deviceId;
        this.ticket = builder.ticket;
        this.activatorToken = builder.activatorToken;
        this.phoneHash = builder.phoneHash;
        this.activatorPhoneInfo = builder.activatorPhoneInfo;
        this.serviceId = builder.serviceId;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String activatorToken;
        private String deviceId;
        private String phone;
        private String phoneHash;
        private String serviceId;
        private String ticket;

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder phoneTicket(String str, String str2) {
            this.phone = str;
            this.ticket = str2;
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder phoneHashActivatorToken(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            if (activatorPhoneInfo != null) {
                this.phoneHash = activatorPhoneInfo.phoneHash;
                this.activatorToken = activatorPhoneInfo.activatorToken;
            }
            return this;
        }

        public QueryPhoneInfoParams build() {
            return new QueryPhoneInfoParams(this);
        }
    }
}
