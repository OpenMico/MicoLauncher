package com.xiaomi.miot.support.core;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotHome implements Parcelable {
    public static final Parcelable.Creator<MiotHome> CREATOR = new Parcelable.Creator<MiotHome>() { // from class: com.xiaomi.miot.support.core.MiotHome.1
        @Override // android.os.Parcelable.Creator
        public MiotHome createFromParcel(Parcel parcel) {
            return new MiotHome(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public MiotHome[] newArray(int i) {
            return new MiotHome[i];
        }
    };
    public List<String> homeDeviceList;
    public String homeId;
    public String homeName;
    public List<MiotRoom> roomList;
    public boolean selected;
    public int shareHomeFlag;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotHome() {
        this.selected = false;
        this.homeId = "";
        this.homeName = "";
        this.shareHomeFlag = 0;
        this.homeDeviceList = new ArrayList();
        this.roomList = new ArrayList();
    }

    public MiotHome(Parcel parcel) {
        boolean z = false;
        this.selected = false;
        this.homeId = "";
        this.homeName = "";
        this.shareHomeFlag = 0;
        this.homeDeviceList = new ArrayList();
        this.roomList = new ArrayList();
        this.selected = parcel.readInt() != 0 ? true : z;
        this.homeId = parcel.readString();
        this.homeName = parcel.readString();
        this.shareHomeFlag = parcel.readInt();
        parcel.readStringList(this.homeDeviceList);
        parcel.readList(this.roomList, MiotRoom.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.selected ? 1 : 0);
        parcel.writeString(this.homeId);
        parcel.writeString(this.homeName);
        parcel.writeInt(this.shareHomeFlag);
        parcel.writeStringList(this.homeDeviceList);
        parcel.writeList(this.roomList);
    }
}
