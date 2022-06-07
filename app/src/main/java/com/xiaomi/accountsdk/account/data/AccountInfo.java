package com.xiaomi.accountsdk.account.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AccountInfo implements Parcelable {
    public static final Parcelable.Creator<AccountInfo> CREATOR = new Parcelable.Creator<AccountInfo>() { // from class: com.xiaomi.accountsdk.account.data.AccountInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountInfo createFromParcel(Parcel parcel) {
            return new AccountInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountInfo[] newArray(int i) {
            return new AccountInfo[i];
        }
    };
    private static final String KEY_HAS_PWD = "has_pwd";
    private static final String KEY_USER_SYNCED_URL = "user_synced_url";
    public final String autoLoginUrl;
    public final String encryptedUserId;
    public final boolean hasPwd;
    public final String passToken;
    public final String ph;
    public final String psecurity;
    public final String rePassToken;
    public final String security;
    public final String serviceId;
    public final String serviceToken;
    public final String slh;
    public final String userId;
    public final String userSyncedUrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static AccountInfo copyFrom(AccountInfo accountInfo) {
        if (accountInfo == null) {
            return null;
        }
        return new Builder().userId(accountInfo.userId).encryptedUserId(accountInfo.encryptedUserId).hasPwd(accountInfo.hasPwd).passToken(accountInfo.passToken).rePassToken(accountInfo.rePassToken).psecurity(accountInfo.psecurity).serviceId(accountInfo.serviceId).autoLoginUrl(accountInfo.autoLoginUrl).serviceToken(accountInfo.serviceToken).security(accountInfo.security).slh(accountInfo.slh).ph(accountInfo.ph).userSyncedUrl(accountInfo.userSyncedUrl).build();
    }

    @Deprecated
    public AccountInfo(String str, String str2, String str3, String str4, String str5) {
        this(new Builder().userId(str).serviceId(str2).passToken(str3).psecurity(str4).autoLoginUrl(str5));
    }

    @Deprecated
    public AccountInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this(new Builder().userId(str).serviceId(str2).passToken(str3).encryptedUserId(str4).serviceToken(str5).security(str6).psecurity(str7));
    }

    @Deprecated
    public AccountInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this(new Builder().userId(str).serviceId(str2).passToken(str3).encryptedUserId(str4).serviceToken(str5).security(str6).psecurity(str7).autoLoginUrl(str8));
    }

    private AccountInfo(Builder builder) {
        this.userId = builder.userId;
        this.serviceId = builder.serviceId;
        this.passToken = builder.passToken;
        this.encryptedUserId = builder.encryptedUserId;
        this.serviceToken = builder.serviceToken;
        this.security = builder.security;
        this.psecurity = builder.psecurity;
        this.autoLoginUrl = builder.autoLoginUrl;
        this.rePassToken = builder.rePassToken;
        this.slh = builder.slh;
        this.ph = builder.ph;
        this.hasPwd = builder.hasPwd;
        this.userSyncedUrl = builder.userSyncedUrl;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getEncryptedUserId() {
        return this.encryptedUserId;
    }

    public String getServiceToken() {
        return this.serviceToken;
    }

    public String getPassToken() {
        return this.passToken;
    }

    public String getSecurity() {
        return this.security;
    }

    public String getPsecurity() {
        return this.psecurity;
    }

    public String getAutoLoginUrl() {
        return this.autoLoginUrl;
    }

    public String getPh() {
        return this.ph;
    }

    public String getSlh() {
        return this.slh;
    }

    public String getRePassToken() {
        return this.rePassToken;
    }

    public boolean getHasPwd() {
        return this.hasPwd;
    }

    public String toString() {
        return "AccountInfo{userId='" + this.userId + "', security='" + this.security + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.serviceId);
        parcel.writeString(this.passToken);
        parcel.writeString(this.encryptedUserId);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.security);
        parcel.writeString(this.psecurity);
        parcel.writeString(this.autoLoginUrl);
        parcel.writeString(this.rePassToken);
        parcel.writeString(this.slh);
        parcel.writeString(this.ph);
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_HAS_PWD, this.hasPwd);
        bundle.putString(KEY_USER_SYNCED_URL, this.userSyncedUrl);
        parcel.writeBundle(bundle);
    }

    private AccountInfo(Parcel parcel) {
        this.userId = parcel.readString();
        this.serviceId = parcel.readString();
        this.passToken = parcel.readString();
        this.encryptedUserId = parcel.readString();
        this.serviceToken = parcel.readString();
        this.security = parcel.readString();
        this.psecurity = parcel.readString();
        this.autoLoginUrl = parcel.readString();
        this.rePassToken = parcel.readString();
        this.slh = parcel.readString();
        this.ph = parcel.readString();
        Bundle readBundle = parcel.readBundle();
        this.hasPwd = readBundle != null ? readBundle.getBoolean(KEY_HAS_PWD) : true;
        this.userSyncedUrl = readBundle != null ? readBundle.getString(KEY_USER_SYNCED_URL) : null;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private String autoLoginUrl;
        private String encryptedUserId;
        private boolean hasPwd;
        private String passToken;
        private String ph;
        private String psecurity;
        private String rePassToken;
        private String security;
        private String serviceId;
        private String serviceToken;
        private String slh;
        private String userId;
        private String userSyncedUrl;

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public Builder serviceId(String str) {
            this.serviceId = str;
            return this;
        }

        public Builder passToken(String str) {
            this.passToken = str;
            return this;
        }

        public Builder encryptedUserId(String str) {
            this.encryptedUserId = str;
            return this;
        }

        public Builder serviceToken(String str) {
            this.serviceToken = str;
            return this;
        }

        public Builder security(String str) {
            this.security = str;
            return this;
        }

        public Builder psecurity(String str) {
            this.psecurity = str;
            return this;
        }

        public Builder autoLoginUrl(String str) {
            this.autoLoginUrl = str;
            return this;
        }

        public Builder rePassToken(String str) {
            this.rePassToken = str;
            return this;
        }

        public Builder slh(String str) {
            this.slh = str;
            return this;
        }

        public Builder ph(String str) {
            this.ph = str;
            return this;
        }

        public Builder hasPwd(boolean z) {
            this.hasPwd = z;
            return this;
        }

        public Builder userSyncedUrl(String str) {
            this.userSyncedUrl = str;
            return this;
        }

        public AccountInfo build() {
            return new AccountInfo(this);
        }
    }
}
