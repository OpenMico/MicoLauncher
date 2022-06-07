package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class BindParams implements Parcelable {
    public static final Parcelable.Creator<BindParams> CREATOR = new Parcelable.Creator<BindParams>() { // from class: com.xiaomi.miot.typedef.device.BindParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BindParams createFromParcel(Parcel parcel) {
            return new BindParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BindParams[] newArray(int i) {
            return new BindParams[i];
        }
    };
    private String mBindKey;
    private String mPartnerId;
    private String mPartnerToken;
    private int mSupportRpcType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BindParams() {
        this.mBindKey = "";
        this.mPartnerId = "";
        this.mPartnerToken = "";
        this.mSupportRpcType = 0;
    }

    public BindParams(Parcel parcel) {
        this.mBindKey = "";
        this.mPartnerId = "";
        this.mPartnerToken = "";
        this.mSupportRpcType = 0;
        this.mBindKey = parcel.readString();
        this.mPartnerId = parcel.readString();
        this.mPartnerToken = parcel.readString();
        this.mSupportRpcType = parcel.readInt();
    }

    public String getBindKey() {
        return this.mBindKey;
    }

    public void setBindKey(String str) {
        this.mBindKey = str;
    }

    public String getPartnerId() {
        return this.mPartnerId;
    }

    public void setPartnerId(String str) {
        this.mPartnerId = str;
    }

    public String getPartnerToken() {
        return this.mPartnerToken;
    }

    public void setPartnerToken(String str) {
        this.mPartnerToken = str;
    }

    public int getSupportRpcType() {
        return this.mSupportRpcType;
    }

    public void setSupportRpcType(int i) {
        this.mSupportRpcType = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mBindKey);
        parcel.writeString(this.mPartnerId);
        parcel.writeString(this.mPartnerToken);
        parcel.writeInt(this.mSupportRpcType);
    }
}
