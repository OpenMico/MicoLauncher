package com.xiaomi.miot.support.account;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AccountInfo implements Parcelable {
    public static final Parcelable.Creator<AccountInfo> CREATOR = new Parcelable.Creator<AccountInfo>() { // from class: com.xiaomi.miot.support.account.AccountInfo.1
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
    public String cUserId;
    public String security;
    public String serviceToken;
    public String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccountInfo() {
    }

    protected AccountInfo(Parcel parcel) {
        this.userId = parcel.readString();
        this.cUserId = parcel.readString();
        this.serviceToken = parcel.readString();
        this.security = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.userId);
        parcel.writeString(this.cUserId);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.security);
    }
}
