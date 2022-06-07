package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.host.lan.impl.MiotStore;

/* loaded from: classes3.dex */
public enum DiscoveryType implements Parcelable {
    UNDEFINED("undefined"),
    MIOT(MiotStore.PREFS_MIOT);
    
    public static final Parcelable.Creator<DiscoveryType> CREATOR = new Parcelable.Creator<DiscoveryType>() { // from class: com.xiaomi.miot.typedef.device.DiscoveryType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DiscoveryType createFromParcel(Parcel parcel) {
            return DiscoveryType.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DiscoveryType[] newArray(int i) {
            return new DiscoveryType[i];
        }
    };
    private String string;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DiscoveryType(String str) {
        this.string = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.string;
    }

    public static DiscoveryType retrieveType(String str) {
        DiscoveryType[] values = values();
        for (DiscoveryType discoveryType : values) {
            if (discoveryType.toString().equals(str)) {
                return discoveryType;
            }
        }
        return UNDEFINED;
    }

    public static DiscoveryType readFromParcel(Parcel parcel) {
        return retrieveType(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.string);
    }
}
