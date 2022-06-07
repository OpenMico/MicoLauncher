package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class DeviceType extends Urn implements Parcelable {
    public static final Parcelable.Creator<DeviceType> CREATOR = new Parcelable.Creator<DeviceType>() { // from class: com.xiaomi.miot.typedef.urn.DeviceType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceType createFromParcel(Parcel parcel) {
            return new DeviceType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceType[] newArray(int i) {
            return new DeviceType[i];
        }
    };

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceType(String str, String str2, String str3) {
        super.setDomain(str);
        super.setMainType(UrnType.DEVICE);
        super.setSubType(str2);
        super.setShortUUID(str3);
    }

    public static DeviceType valueOf(String str) {
        DeviceType deviceType = new DeviceType();
        if (!deviceType.parse(str)) {
            return null;
        }
        return deviceType;
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public boolean parse(String str) {
        boolean parse = super.parse(str);
        if (parse && getMainType() == UrnType.DEVICE) {
            return true;
        }
        return parse;
    }

    public DeviceType() {
    }

    public DeviceType(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public void readFromParcel(Parcel parcel) {
        parse(parcel.readString());
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toString());
    }
}
