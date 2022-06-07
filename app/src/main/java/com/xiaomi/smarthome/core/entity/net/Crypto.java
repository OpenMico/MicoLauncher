package com.xiaomi.smarthome.core.entity.net;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public enum Crypto implements Parcelable {
    NONE,
    AES,
    RC4,
    HTTPS;
    
    public static final Parcelable.Creator<Crypto> CREATOR = new Parcelable.Creator<Crypto>() { // from class: com.xiaomi.smarthome.core.entity.net.Crypto.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Crypto createFromParcel(Parcel parcel) {
            return Crypto.values()[parcel.readInt()];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Crypto[] newArray(int i) {
            return new Crypto[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ordinal());
    }
}
