package com.xiaomi.miplay.audioclient;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class MiPlayDevice implements Parcelable {
    public static final Parcelable.Creator<MiPlayDevice> CREATOR = new Parcelable.Creator<MiPlayDevice>() { // from class: com.xiaomi.miplay.audioclient.MiPlayDevice.1
        /* renamed from: a */
        public MiPlayDevice createFromParcel(Parcel parcel) {
            return new MiPlayDevice(parcel);
        }

        /* renamed from: a */
        public MiPlayDevice[] newArray(int i) {
            return new MiPlayDevice[i];
        }
    };
    private String a;
    private String b;
    private int c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private int m;
    private float n;
    private float o;
    private int p;
    private int q;
    private int r;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getDeviceConnectState() {
        return this.q;
    }

    public void setDeviceConnectState(int i) {
        this.q = i;
    }

    public int getMirrorMode() {
        return this.r;
    }

    public void setMirrorMode(int i) {
        this.r = i;
    }

    public String getRoomName() {
        return this.f;
    }

    public void setRoomName(String str) {
        this.f = str;
    }

    public String getMiotDid() {
        return this.g;
    }

    public void setMiotDid(String str) {
        this.g = str;
    }

    public String getMiName() {
        return this.h;
    }

    public void setMiName(String str) {
        this.h = str;
    }

    public String getMiAlias() {
        return this.i;
    }

    public void setMiAlias(String str) {
        this.i = str;
    }

    public String getHouse_Id() {
        return this.j;
    }

    public void setHouse_Id(String str) {
        this.j = str;
    }

    public String getRoom_Id() {
        return this.k;
    }

    public void setRoom_Id(String str) {
        this.k = str;
    }

    public String getRoom_Alias() {
        return this.l;
    }

    public void setRoom_Alias(String str) {
        this.l = str;
    }

    public int getStatus() {
        return this.m;
    }

    public void setStatus(int i) {
        this.m = i;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public int getIsGroup() {
        return this.c;
    }

    public void setIsGroup(int i) {
        this.c = i;
    }

    public String getBluetoothMac() {
        return this.d;
    }

    public void setBluetoothMac(String str) {
        this.d = str;
    }

    public String getAccountId() {
        return this.e;
    }

    public void setAccountId(String str) {
        this.e = str;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public float getAltitude() {
        return this.n;
    }

    public void setAltitude(float f) {
        this.n = f;
    }

    public float getAzimuth() {
        return this.o;
    }

    public void setAzimuth(float f) {
        this.o = f;
    }

    public int getDistance() {
        return this.p;
    }

    public void setDistance(int i) {
        this.p = i;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MiPlayDevice) && ((MiPlayDevice) obj).getId() == this.a;
    }

    public MiPlayDevice() {
        this.n = 0.0f;
        this.o = 0.0f;
        this.p = 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.e);
        parcel.writeString(this.d);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeInt(this.m);
        parcel.writeInt(this.q);
        parcel.writeInt(this.r);
        parcel.writeFloat(this.n);
        parcel.writeFloat(this.o);
        parcel.writeInt(this.p);
    }

    protected MiPlayDevice(Parcel parcel) {
        this.n = 0.0f;
        this.o = 0.0f;
        this.p = 0;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt();
        this.e = parcel.readString();
        this.d = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readInt();
        this.q = parcel.readInt();
        this.r = parcel.readInt();
        this.n = parcel.readFloat();
        this.o = parcel.readFloat();
        this.p = parcel.readInt();
    }
}
