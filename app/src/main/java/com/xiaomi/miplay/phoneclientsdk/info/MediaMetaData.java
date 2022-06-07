package com.xiaomi.miplay.phoneclientsdk.info;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class MediaMetaData implements Parcelable {
    public static final Parcelable.Creator<MediaMetaData> CREATOR = new Parcelable.Creator<MediaMetaData>() { // from class: com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData.1
        /* renamed from: a */
        public MediaMetaData createFromParcel(Parcel parcel) {
            return new MediaMetaData(parcel);
        }

        /* renamed from: a */
        public MediaMetaData[] newArray(int i) {
            return new MediaMetaData[i];
        }
    };
    private String a;
    private long b;
    private Bitmap c;
    private long d;
    private String e;
    private String f;
    private String g;
    private String h;
    private int i;
    private String j;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String str) {
        this.a = str;
    }

    public long getDuration() {
        return this.b;
    }

    public void setDuration(long j) {
        this.b = j;
    }

    public Bitmap getArt() {
        return this.c;
    }

    public void setArt(Bitmap bitmap) {
        this.c = bitmap;
    }

    public long getPosition() {
        return this.d;
    }

    public void setPosition(long j) {
        this.d = j;
    }

    public String getUrl() {
        return this.e;
    }

    public void setUrl(String str) {
        this.e = str;
    }

    public String getMux() {
        return this.f;
    }

    public void setMux(String str) {
        this.f = str;
    }

    public String getCodec() {
        return this.g;
    }

    public void setCodec(String str) {
        this.g = str;
    }

    public String getReverso() {
        return this.h;
    }

    public void setReverso(String str) {
        this.h = str;
    }

    public int getIsDlnaCast() {
        return this.i;
    }

    public void setIsDlnaCast(int i) {
        this.i = i;
    }

    public String getPropertiesInfo() {
        return this.j;
    }

    public void setPropertiesInfo(String str) {
        this.j = str;
    }

    public String toString() {
        return "MediaMetaData{title='" + this.a + "', duration=" + this.b + ", art=" + this.c + ", position=" + this.d + ", url='" + this.e + "', mux='" + this.f + "', codec='" + this.g + "', reverso='" + this.h + "', isDlnaCast=" + this.i + ", propertiesInfo='" + this.j + "'}";
    }

    public MediaMetaData() {
        this.a = "";
        this.b = 0L;
        this.c = null;
        this.d = 0L;
        this.e = "";
        this.f = "";
        this.g = "";
        this.h = "";
        this.i = -1;
        this.j = "";
    }

    protected MediaMetaData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readLong();
        this.c = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        this.d = parcel.readLong();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readInt();
        this.j = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeLong(this.b);
        parcel.writeParcelable(this.c, i);
        parcel.writeLong(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeInt(this.i);
        parcel.writeString(this.j);
    }
}
