package com.xiaomi.miot;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DeviceInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() { // from class: com.xiaomi.miot.DeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceInfo createFromParcel(Parcel parcel) {
            return new DeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceInfo[] newArray(int i) {
            return new DeviceInfo[i];
        }
    };
    public boolean currentStatus;
    public String deviceName;
    public String did;
    public String encryptDid;
    public String icon;
    public boolean isOnline;
    public String model;
    public String roomId;
    public String roomInfo;
    public boolean showSlideButton;
    public String subtitle;
    public Bundle subtitleMap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceInfo(Parcel parcel) {
        this.icon = "";
        this.subtitle = "";
        this.roomId = "";
        this.roomInfo = "";
        this.subtitleMap = new Bundle();
        this.model = "";
        this.did = parcel.readString();
        this.encryptDid = parcel.readString();
        this.deviceName = parcel.readString();
        this.icon = parcel.readString();
        this.subtitle = parcel.readString();
        this.roomId = parcel.readString();
        this.roomInfo = parcel.readString();
        boolean z = true;
        this.isOnline = parcel.readInt() != 0;
        this.subtitleMap = parcel.readBundle(ClassLoader.getSystemClassLoader());
        this.showSlideButton = parcel.readInt() != 0;
        this.currentStatus = parcel.readInt() == 0 ? false : z;
        this.model = parcel.readString();
    }

    public DeviceInfo() {
        this.icon = "";
        this.subtitle = "";
        this.roomId = "";
        this.roomInfo = "";
        this.subtitleMap = new Bundle();
        this.model = "";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.did);
        parcel.writeString(this.encryptDid);
        parcel.writeString(this.deviceName);
        parcel.writeString(this.icon);
        parcel.writeString(this.subtitle);
        parcel.writeString(this.roomId);
        parcel.writeString(this.roomInfo);
        parcel.writeInt(this.isOnline ? 1 : 0);
        parcel.writeBundle(this.subtitleMap);
        parcel.writeInt(this.showSlideButton ? 1 : 0);
        parcel.writeInt(this.currentStatus ? 1 : 0);
        parcel.writeString(this.model);
    }
}
