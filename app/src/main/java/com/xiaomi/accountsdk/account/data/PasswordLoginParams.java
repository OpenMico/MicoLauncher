package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PasswordLoginParams implements Parcelable {
    private static final String ACTIVATOR_PHONE_INFO = "activatorPhoneInfo";
    public static final Parcelable.Creator<PasswordLoginParams> CREATOR = new Parcelable.Creator<PasswordLoginParams>() { // from class: com.xiaomi.accountsdk.account.data.PasswordLoginParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordLoginParams createFromParcel(Parcel parcel) {
            return new PasswordLoginParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordLoginParams[] newArray(int i) {
            return new PasswordLoginParams[i];
        }
    };
    protected static final String DEVICE_ID = "deviceId";
    protected static final String HASHED_ENV_FACTORS = "hashedEnvFactors";
    protected static final String META_LOGIN_DATA = "metaLoginData";
    protected static final String NEED_PROCESS_NOTIFICATION = "needProcessNotification";
    protected static final String RETURN_STS_URL = "returnStsUrl";
    protected static final String TICKET_TOKEN = "ticketToken";
    public ActivatorPhoneInfo activatorPhoneInfo;
    public final String captCode;
    public final String captIck;
    public String deviceId;
    public String[] hashedEnvFactors;
    public MetaLoginData metaLoginData;
    public boolean needProcessNotification;
    public final String password;
    public boolean returnStsUrl;
    public final String serviceId;
    public String ticketToken;
    public final String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private ActivatorPhoneInfo activatorPhoneInfo;
        private String captCode;
        private String captIck;
        private String deviceId;
        private String[] hashedEnvFactors;
        private MetaLoginData metaLoginData;
        private String password;
        private String serviceId;
        private String ticketToken;
        private String userId;
        private boolean returnStsUrl = false;
        private boolean needProcessNotification = true;

        public Builder setUserId(String str) {
            this.userId = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.password = str;
            return this;
        }

        public Builder setServiceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder setCaptCode(String str) {
            this.captCode = str;
            return this;
        }

        public Builder setCaptIck(String str) {
            this.captIck = str;
            return this;
        }

        public Builder setDeviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public Builder setTicketToken(String str) {
            this.ticketToken = str;
            return this;
        }

        public Builder setMetaLoginData(MetaLoginData metaLoginData) {
            this.metaLoginData = metaLoginData;
            return this;
        }

        public Builder setIsReturnStsUrl(boolean z) {
            this.returnStsUrl = z;
            return this;
        }

        public Builder setNeedProcessNotification(boolean z) {
            this.needProcessNotification = z;
            return this;
        }

        public Builder setHashedEnvFactors(String[] strArr) {
            this.hashedEnvFactors = strArr;
            return this;
        }

        public Builder setActivatorPhone(ActivatorPhoneInfo activatorPhoneInfo) {
            this.activatorPhoneInfo = activatorPhoneInfo;
            return this;
        }

        public PasswordLoginParams build() {
            return new PasswordLoginParams(this);
        }
    }

    public static Builder copyFrom(PasswordLoginParams passwordLoginParams) {
        if (passwordLoginParams == null) {
            return null;
        }
        return new Builder().setUserId(passwordLoginParams.userId).setPassword(passwordLoginParams.password).setServiceId(passwordLoginParams.serviceId).setCaptCode(passwordLoginParams.captCode).setCaptIck(passwordLoginParams.captIck).setDeviceId(passwordLoginParams.deviceId).setTicketToken(passwordLoginParams.ticketToken).setMetaLoginData(passwordLoginParams.metaLoginData).setIsReturnStsUrl(passwordLoginParams.returnStsUrl).setNeedProcessNotification(passwordLoginParams.needProcessNotification).setHashedEnvFactors(passwordLoginParams.hashedEnvFactors).setActivatorPhone(passwordLoginParams.activatorPhoneInfo);
    }

    private PasswordLoginParams(Builder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.serviceId = builder.serviceId;
        this.captCode = builder.captCode;
        this.captIck = builder.captIck;
        this.deviceId = builder.deviceId;
        this.ticketToken = builder.ticketToken;
        this.metaLoginData = builder.metaLoginData;
        this.returnStsUrl = builder.returnStsUrl;
        this.needProcessNotification = builder.needProcessNotification;
        this.hashedEnvFactors = builder.hashedEnvFactors;
        this.activatorPhoneInfo = builder.activatorPhoneInfo;
    }

    public PasswordLoginParams(Parcel parcel) {
        this.userId = parcel.readString();
        this.password = parcel.readString();
        this.serviceId = parcel.readString();
        this.captCode = parcel.readString();
        this.captIck = parcel.readString();
        Bundle readBundle = parcel.readBundle();
        if (readBundle != null) {
            this.deviceId = readBundle.getString("deviceId");
            this.ticketToken = readBundle.getString(TICKET_TOKEN);
            this.metaLoginData = (MetaLoginData) readBundle.getParcelable(META_LOGIN_DATA);
            this.returnStsUrl = readBundle.getBoolean(RETURN_STS_URL, false);
            this.needProcessNotification = readBundle.getBoolean(NEED_PROCESS_NOTIFICATION, true);
            this.hashedEnvFactors = readBundle.getStringArray(HASHED_ENV_FACTORS);
            this.activatorPhoneInfo = (ActivatorPhoneInfo) readBundle.getParcelable(ACTIVATOR_PHONE_INFO);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.password);
        parcel.writeString(this.serviceId);
        parcel.writeString(this.captCode);
        parcel.writeString(this.captIck);
        Bundle bundle = new Bundle();
        bundle.putString("deviceId", this.deviceId);
        bundle.putString(TICKET_TOKEN, this.ticketToken);
        bundle.putParcelable(META_LOGIN_DATA, this.metaLoginData);
        bundle.putBoolean(RETURN_STS_URL, this.returnStsUrl);
        bundle.putBoolean(NEED_PROCESS_NOTIFICATION, this.needProcessNotification);
        bundle.putStringArray(HASHED_ENV_FACTORS, this.hashedEnvFactors);
        bundle.putParcelable(ACTIVATOR_PHONE_INFO, this.activatorPhoneInfo);
        parcel.writeBundle(bundle);
    }
}
