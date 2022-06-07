package com.xiaomi.miplay.audioclient;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class MiPlayDeviceControlCenter implements Parcelable {
    public static final Parcelable.Creator<MiPlayDeviceControlCenter> CREATOR = new Parcelable.Creator<MiPlayDeviceControlCenter>() { // from class: com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter.1
        /* renamed from: a */
        public MiPlayDeviceControlCenter createFromParcel(Parcel parcel) {
            return new MiPlayDeviceControlCenter(parcel);
        }

        /* renamed from: a */
        public MiPlayDeviceControlCenter[] newArray(int i) {
            return new MiPlayDeviceControlCenter[i];
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
    private String m;
    private int n;
    private float o;
    private float p;
    private int q;
    private String r;
    private int s;
    private String t;
    private int u;
    private int v;
    private String w;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getIdhash() {
        return this.t;
    }

    public void setIdhash(String str) {
        this.t = str;
    }

    public String getGroupId() {
        return this.r;
    }

    public void setGroupId(String str) {
        this.r = str;
    }

    public int getDeviceConnectState() {
        return this.u;
    }

    public void setDeviceConnectState(int i) {
        this.u = i;
    }

    public int getMirrorMode() {
        return this.v;
    }

    public void setMirrorMode(int i) {
        this.v = i;
    }

    public String getRoomName() {
        return this.g;
    }

    public void setRoomName(String str) {
        this.g = str;
    }

    public String getMiotDid() {
        return this.h;
    }

    public void setMiotDid(String str) {
        this.h = str;
    }

    public String getMiName() {
        return this.i;
    }

    public void setMiName(String str) {
        this.i = str;
    }

    public String getMiAlias() {
        return this.j;
    }

    public void setMiAlias(String str) {
        this.j = str;
    }

    public String getHouse_Id() {
        return this.k;
    }

    public void setHouse_Id(String str) {
        this.k = str;
    }

    public String getRoom_Id() {
        return this.l;
    }

    public void setRoom_Id(String str) {
        this.l = str;
    }

    public String getRoom_Alias() {
        return this.m;
    }

    public void setRoom_Alias(String str) {
        this.m = str;
    }

    public int getStatus() {
        return this.n;
    }

    public void setStatus(int i) {
        this.n = i;
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
        return this.f;
    }

    public void setAccountId(String str) {
        this.f = str;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public float getAltitude() {
        return this.o;
    }

    public void setAltitude(float f) {
        this.o = f;
    }

    public float getAzimuth() {
        return this.p;
    }

    public void setAzimuth(float f) {
        this.p = f;
    }

    public int getDistance() {
        return this.q;
    }

    public void setDistance(int i) {
        this.q = i;
    }

    public int getDeviceType() {
        return this.s;
    }

    public void setDeviceType(int i) {
        this.s = i;
    }

    public String getMac() {
        return this.e;
    }

    public void setMac(String str) {
        this.e = str;
    }

    public String getIp_address() {
        return this.w;
    }

    public void setIp_address(String str) {
        this.w = str;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MiPlayDeviceControlCenter) && ((MiPlayDeviceControlCenter) obj).getId() == this.a;
    }

    public MiPlayDeviceControlCenter() {
        this.o = 0.0f;
        this.p = 0.0f;
        this.q = 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.f);
        parcel.writeString(this.e);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n);
        parcel.writeFloat(this.o);
        parcel.writeFloat(this.p);
        parcel.writeInt(this.q);
        parcel.writeString(this.r);
        parcel.writeInt(this.s);
        parcel.writeString(this.e);
        parcel.writeInt(this.u);
        parcel.writeInt(this.v);
        parcel.writeString(this.t);
        parcel.writeString(this.w);
    }

    protected MiPlayDeviceControlCenter(Parcel parcel) {
        this.o = 0.0f;
        this.p = 0.0f;
        this.q = 0;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        this.f = parcel.readString();
        this.e = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readString();
        this.n = parcel.readInt();
        this.o = parcel.readFloat();
        this.p = parcel.readFloat();
        this.q = parcel.readInt();
        this.r = parcel.readString();
        this.s = parcel.readInt();
        this.e = parcel.readString();
        this.u = parcel.readInt();
        this.v = parcel.readInt();
        this.t = parcel.readString();
        this.w = parcel.readString();
    }
}
