package com.xiaomi.smarthome.core.entity.net;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.smarthome.core.entity.Error;

/* loaded from: classes4.dex */
public class NetError extends Error {
    public static final Parcelable.Creator<NetError> CREATOR = new Parcelable.Creator<NetError>() { // from class: com.xiaomi.smarthome.core.entity.net.NetError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetError createFromParcel(Parcel parcel) {
            return new NetError(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetError[] newArray(int i) {
            return new NetError[i];
        }
    };

    @Override // com.xiaomi.smarthome.core.entity.Error, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NetError(int i, String str) {
        super(i, str);
    }

    public NetError(int i, String str, String str2) {
        super(i, str, str2);
    }

    protected NetError(Parcel parcel) {
        super(parcel);
    }

    @Override // com.xiaomi.smarthome.core.entity.Error, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
