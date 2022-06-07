package com.xiaomi.accountsdk.account;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class XiaomiAccount implements Parcelable {
    public static final Parcelable.Creator<XiaomiAccount> CREATOR = new Parcelable.Creator<XiaomiAccount>() { // from class: com.xiaomi.accountsdk.account.XiaomiAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XiaomiAccount createFromParcel(Parcel parcel) {
            return new XiaomiAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public XiaomiAccount[] newArray(int i) {
            return new XiaomiAccount[i];
        }
    };
    private String mAvatarFileName;
    private String mEmail;
    private String mName;
    private String mNickName;
    private String mPhone;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public XiaomiAccount(String str, String str2, String str3, String str4, String str5) {
        this.mName = str;
        this.mNickName = str2;
        this.mEmail = str3;
        this.mPhone = str4;
        this.mAvatarFileName = str5;
    }

    public String getName() {
        return this.mName;
    }

    public String getNickName() {
        return this.mNickName;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getAvatarFileName() {
        return this.mAvatarFileName;
    }

    public XiaomiAccount(Parcel parcel) {
        this.mName = parcel.readString();
        this.mNickName = parcel.readString();
        this.mEmail = parcel.readString();
        this.mPhone = parcel.readString();
        this.mAvatarFileName = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mNickName);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mPhone);
        parcel.writeString(this.mAvatarFileName);
    }
}
