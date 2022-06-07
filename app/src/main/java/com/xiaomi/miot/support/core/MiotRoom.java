package com.xiaomi.miot.support.core;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotRoom implements Parcelable {
    public static final Parcelable.Creator<MiotRoom> CREATOR = new Parcelable.Creator<MiotRoom>() { // from class: com.xiaomi.miot.support.core.MiotRoom.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotRoom createFromParcel(Parcel parcel) {
            return new MiotRoom(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotRoom[] newArray(int i) {
            return new MiotRoom[i];
        }
    };
    public boolean isCurrentSpeakerRoom;
    public List<String> roomDeviceList;
    public String roomId;
    public String roomName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotRoom() {
        this.roomId = "";
        this.roomName = "";
        this.roomDeviceList = new ArrayList();
    }

    public MiotRoom(Parcel parcel) {
        this.roomId = "";
        this.roomName = "";
        this.roomDeviceList = new ArrayList();
        this.roomId = parcel.readString();
        this.roomName = parcel.readString();
        parcel.readStringList(this.roomDeviceList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.roomId);
        parcel.writeString(this.roomName);
        parcel.writeStringList(this.roomDeviceList);
    }
}
