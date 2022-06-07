package com.xiaomi.micolauncher.skills.miot.multipledevices;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class MultipleDevicesItem implements Parcelable {
    public static final Parcelable.Creator<MultipleDevicesItem> CREATOR = new Parcelable.Creator<MultipleDevicesItem>() { // from class: com.xiaomi.micolauncher.skills.miot.multipledevices.MultipleDevicesItem.1
        /* renamed from: a */
        public MultipleDevicesItem createFromParcel(Parcel parcel) {
            return new MultipleDevicesItem(parcel);
        }

        /* renamed from: a */
        public MultipleDevicesItem[] newArray(int i) {
            return new MultipleDevicesItem[i];
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MultipleDevicesItem() {
    }

    public void MultipleDevicesItem(String str, String str2, String str3, String str4, String str5) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
    }

    protected MultipleDevicesItem(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
    }

    public void setDeviceName(String str) {
        this.a = str;
    }

    public String getDeviceName() {
        return this.a;
    }

    public void setRoomName(String str) {
        this.b = str;
    }

    public String getRoomName() {
        return this.b;
    }

    public void setViewUrl(String str) {
        this.c = str;
    }

    public String getViewUrl() {
        return this.c;
    }

    public void setDid(String str) {
        this.d = str;
    }

    public String getDid() {
        return this.d;
    }

    public void setType(String str) {
        this.e = str;
    }

    public String getType() {
        return this.e;
    }
}
