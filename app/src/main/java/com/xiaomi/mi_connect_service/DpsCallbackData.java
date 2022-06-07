package com.xiaomi.mi_connect_service;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes3.dex */
public class DpsCallbackData implements Parcelable {
    public static final Parcelable.Creator<DpsCallbackData> CREATOR = new Parcelable.Creator<DpsCallbackData>() { // from class: com.xiaomi.mi_connect_service.DpsCallbackData.1
        /* renamed from: a */
        public DpsCallbackData[] newArray(int i) {
            return new DpsCallbackData[i];
        }

        /* renamed from: a */
        public DpsCallbackData createFromParcel(Parcel parcel) {
            return new DpsCallbackData(parcel);
        }
    };
    public static final int INVALID_MESSAGE = 1;
    public static final String TAG = "DpsCallbackData";
    public static final int VALID_MESSAGE = 0;
    private int a;
    private String b;
    private String c;
    private byte[] d;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DpsCallbackData(int i, String str, String str2, byte[] bArr) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = bArr;
    }

    public DpsCallbackData(Parcel parcel) {
        a(parcel);
    }

    public int getCode() {
        return this.a;
    }

    public String getTopicName() {
        return this.b;
    }

    public String getPartition() {
        return this.c;
    }

    public byte[] getMessage() {
        return this.d;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeInt(this.a);
            parcel.writeString(this.b);
            parcel.writeString(this.c);
            if (this.d != null) {
                parcel.writeInt(0);
                parcel.writeInt(this.d.length);
                parcel.writeByteArray(this.d);
                return;
            }
            parcel.writeInt(1);
        }
    }

    private void a(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.d = new byte[parcel.readInt()];
            parcel.readByteArray(this.d);
        } else if (1 == readInt) {
            Log.w(TAG, "readFromParcel: invalid message ...");
        }
    }
}
