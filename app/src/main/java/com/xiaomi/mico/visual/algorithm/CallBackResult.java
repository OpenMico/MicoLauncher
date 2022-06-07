package com.xiaomi.mico.visual.algorithm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CallBackResult implements Parcelable {
    public static final Parcelable.Creator<CallBackResult> CREATOR = new Parcelable.Creator<CallBackResult>() { // from class: com.xiaomi.mico.visual.algorithm.CallBackResult.1
        /* renamed from: a */
        public CallBackResult createFromParcel(Parcel parcel) {
            return new CallBackResult(parcel);
        }

        /* renamed from: a */
        public CallBackResult[] newArray(int i) {
            return new CallBackResult[i];
        }
    };
    private int a;
    private int b;
    private String c;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected CallBackResult(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readString();
    }

    public CallBackResult() {
    }

    public CallBackResult(int i, int i2, String str) {
        this.a = i;
        this.b = i2;
        this.c = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeString(this.c);
    }

    public void readFromParcel(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readString();
    }

    public int getEventType() {
        return this.a;
    }

    public int getStatus() {
        return this.b;
    }

    public String getVisionRecognizeResult() {
        return this.c;
    }

    public void setEventType(int i) {
        this.a = i;
    }

    public void setStatus(int i) {
        this.b = i;
    }

    public void setVisionRecognizeResult(String str) {
        this.c = str;
    }
}
