package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class SetPasswordParams {
    public final String deviceId;
    public final MiuiActivatorInfo miuiActivatorInfo;
    public final String passToken;
    public final PassportInfo passportApiInfo;
    public final String pwd;
    public final String serviceId;
    public final String ticket;
    public final String userId;

    public SetPasswordParams(Builder builder) {
        this.userId = builder.userId;
        this.passportApiInfo = builder.passportApiInfo;
        this.pwd = builder.pwd;
        this.passToken = builder.passToken;
        this.ticket = builder.ticket;
        this.serviceId = builder.serviceId;
        this.deviceId = builder.deviceId;
        this.miuiActivatorInfo = builder.miuiActivatorInfo;
    }

    public static Builder copyFrom(SetPasswordParams setPasswordParams) {
        if (setPasswordParams == null) {
            return null;
        }
        return new Builder(setPasswordParams.userId).passportApiInfo(setPasswordParams.passportApiInfo).password(setPasswordParams.pwd).passToken(setPasswordParams.passToken).ticket(setPasswordParams.ticket).serviceId(setPasswordParams.serviceId).deviceId(setPasswordParams.deviceId).miuiActivatorInfo(setPasswordParams.miuiActivatorInfo);
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private String deviceId;
        private MiuiActivatorInfo miuiActivatorInfo;
        private String passToken;
        private PassportInfo passportApiInfo;
        private String pwd;
        private String serviceId;
        private String ticket;
        private final String userId;

        public Builder(String str) {
            this.userId = str;
        }

        public Builder password(String str) {
            this.pwd = str;
            return this;
        }

        public Builder passToken(String str) {
            this.passToken = str;
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder deviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder ticket(String str) {
            this.ticket = str;
            return this;
        }

        public Builder passportApiInfo(PassportInfo passportInfo) {
            this.passportApiInfo = passportInfo;
            return this;
        }

        public Builder miuiActivatorInfo(MiuiActivatorInfo miuiActivatorInfo) {
            this.miuiActivatorInfo = miuiActivatorInfo;
            return this;
        }

        public SetPasswordParams build() {
            return new SetPasswordParams(this);
        }
    }
}
