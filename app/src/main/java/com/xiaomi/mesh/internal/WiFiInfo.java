package com.xiaomi.mesh.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class WiFiInfo implements Parcelable {
    public static final Parcelable.Creator<WiFiInfo> CREATOR = new Parcelable.Creator<WiFiInfo>() { // from class: com.xiaomi.mesh.internal.WiFiInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WiFiInfo createFromParcel(Parcel parcel) {
            return new WiFiInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WiFiInfo[] newArray(int i) {
            return new WiFiInfo[i];
        }
    };
    private String password;
    private String ssid;
    private String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WiFiInfo() {
    }

    public WiFiInfo(String str, String str2, String str3) {
        this.ssid = str;
        this.password = str2;
        this.userId = str3;
    }

    protected WiFiInfo(Parcel parcel) {
        this.ssid = parcel.readString();
        this.password = parcel.readString();
        this.userId = parcel.readString();
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "WifiInfo [ssid=" + this.ssid + ", password=" + this.password + ", userId=" + this.userId + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.password);
        parcel.writeString(this.userId);
    }
}
