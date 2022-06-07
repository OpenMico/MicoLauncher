package com.qiyi.video.pad.service;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class QYResult implements Parcelable {
    public static final Parcelable.Creator<QYResult> CREATOR = new Parcelable.Creator<QYResult>() { // from class: com.qiyi.video.pad.service.QYResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QYResult createFromParcel(Parcel parcel) {
            return new QYResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QYResult[] newArray(int i) {
            return new QYResult[i];
        }
    };
    private int code;
    private String msg;
    private boolean success;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public QYResult() {
    }

    protected QYResult(Parcel parcel) {
        this.success = parcel.readByte() != 0;
        this.code = parcel.readInt();
        this.msg = parcel.readString();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public QYResult setSuccess(boolean z) {
        this.success = z;
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public QYResult setCode(int i) {
        this.code = i;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public QYResult setMsg(String str) {
        this.msg = str;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.success ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.code);
        parcel.writeString(this.msg);
    }
}
