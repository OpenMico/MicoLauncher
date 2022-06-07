package com.xiaomi.smarthome.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class Error implements Parcelable {
    public static final int CODE_CORE_SERVICE_PERMISSION_DENY = -3;
    public static final int CODE_CORE_SERVICE_PROCESS_NOT_REGISTER = -2;
    public static final Parcelable.Creator<Error> CREATOR = new Parcelable.Creator<Error>() { // from class: com.xiaomi.smarthome.core.entity.Error.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Error createFromParcel(Parcel parcel) {
            return new Error(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Error[] newArray(int i) {
            return new Error[i];
        }
    };
    private int mCode;
    private String mDetail;
    private String mExtraMsg;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Error(int i, String str, String str2) {
        this.mCode = i;
        this.mDetail = str;
        this.mExtraMsg = str2;
    }

    public Error(int i, String str) {
        this.mCode = i;
        this.mDetail = str;
    }

    public final int getCode() {
        return this.mCode;
    }

    public final String getDetail() {
        return this.mDetail;
    }

    public String getmExtraMsg() {
        return this.mExtraMsg;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCode);
        parcel.writeString(this.mDetail);
        parcel.writeString(this.mExtraMsg);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Error(Parcel parcel) {
        this.mCode = parcel.readInt();
        this.mDetail = parcel.readString();
        this.mExtraMsg = parcel.readString();
    }
}
