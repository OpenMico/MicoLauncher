package com.xiaomi.miplay.audioclient;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class MediaMetaData implements Parcelable {
    public static final Parcelable.Creator<MediaMetaData> CREATOR = new Parcelable.Creator<MediaMetaData>() { // from class: com.xiaomi.miplay.audioclient.MediaMetaData.1
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
    private String b;
    private String c;
    private long d;
    private String e;
    private String f;
    private int g;
    private long h;
    private Bitmap i;
    private long j;
    private String k;
    private String l;
    private String m;
    private int n;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaMetaData(String str, String str2, String str3, long j, String str4, String str5, int i, long j2, String str6) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = j;
        this.e = str4;
        this.f = str5;
        this.g = i;
        this.h = j2;
        this.k = str6;
    }

    public MediaMetaData() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = 0L;
        this.e = "";
        this.f = "";
        this.g = 0;
        this.h = 0L;
        this.i = null;
        this.j = 0L;
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = 0;
    }

    public Bitmap getArt() {
        return this.i;
    }

    public void setArt(Bitmap bitmap) {
        this.i = bitmap;
    }

    public String getArtist() {
        return this.a;
    }

    public void setArtist(String str) {
        this.a = str;
    }

    public String getAlbum() {
        return this.b;
    }

    public void setAlbum(String str) {
        this.b = str;
    }

    public String getTitle() {
        return this.c;
    }

    public void setTitle(String str) {
        this.c = str;
    }

    public long getDuration() {
        return this.d;
    }

    public void setDuration(long j) {
        this.d = j;
    }

    public String getId() {
        return this.e;
    }

    public void setId(String str) {
        this.e = str;
    }

    public String getCoverUrl() {
        return this.f;
    }

    public void setCoverUrl(String str) {
        this.f = str;
    }

    public int getStatus() {
        return this.g;
    }

    public void setStatus(int i) {
        this.g = i;
    }

    public long getVolume() {
        return this.h;
    }

    public void setVolume(long j) {
        this.h = j;
    }

    public long getPosition() {
        return this.j;
    }

    public void setPosition(long j) {
        this.j = j;
    }

    public String getmLrc() {
        return this.k;
    }

    public void setmLrc(String str) {
        this.k = str;
    }

    public String getPackgeName() {
        return this.l;
    }

    public void setPackgeName(String str) {
        this.l = str;
    }

    public String getSourceName() {
        return this.m;
    }

    public void setSourceName(String str) {
        this.m = str;
    }

    public int getDeviceState() {
        return this.n;
    }

    public void setDeviceState(int i) {
        this.n = i;
    }

    public String toString() {
        return "MediaMetaData{mArtist='" + this.a + "', mAlbum='" + this.b + "', mTitle='" + this.c + "', mDuration=" + this.d + ", mId='" + this.e + "', mCoverUrl='" + this.f + "', mStatus=" + this.g + ", mVolume=" + this.h + ", mArt=" + this.i + ", mPosition=" + this.j + ", mLrc='" + this.k + "', mPackgeName='" + this.l + "', mSourceName='" + this.m + "', mDeviceState=" + this.n + '}';
    }

    protected MediaMetaData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readLong();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readInt();
        this.h = parcel.readLong();
        this.i = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        this.j = parcel.readLong();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readString();
        this.n = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeLong(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeLong(this.h);
        parcel.writeParcelable(this.i, i);
        parcel.writeLong(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n);
    }
}
