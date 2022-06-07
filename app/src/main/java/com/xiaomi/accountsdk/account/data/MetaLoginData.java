package com.xiaomi.accountsdk.account.data;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MetaLoginData implements Parcelable {
    public static final Parcelable.Creator<MetaLoginData> CREATOR = new Parcelable.Creator<MetaLoginData>() { // from class: com.xiaomi.accountsdk.account.data.MetaLoginData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetaLoginData createFromParcel(Parcel parcel) {
            return new MetaLoginData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MetaLoginData[] newArray(int i) {
            return new MetaLoginData[i];
        }
    };
    public final String callback;
    public final String qs;
    public final String sign;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MetaLoginData(String str, String str2, String str3) {
        this.sign = str;
        this.qs = str2;
        this.callback = str3;
    }

    public MetaLoginData(Parcel parcel) {
        this.sign = parcel.readString();
        this.qs = parcel.readString();
        this.callback = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sign);
        parcel.writeString(this.qs);
        parcel.writeString(this.callback);
    }
}
