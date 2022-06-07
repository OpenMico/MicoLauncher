package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ServiceType extends Urn implements Parcelable {
    public static final Parcelable.Creator<ServiceType> CREATOR = new Parcelable.Creator<ServiceType>() { // from class: com.xiaomi.miot.typedef.urn.ServiceType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceType createFromParcel(Parcel parcel) {
            return new ServiceType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceType[] newArray(int i) {
            return new ServiceType[i];
        }
    };

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ServiceType(String str, String str2, String str3) {
        super.setDomain(str);
        super.setMainType(UrnType.SERVICE);
        super.setSubType(str2);
        super.setShortUUID(str3);
    }

    public static ServiceType valueOf(String str) {
        ServiceType serviceType = new ServiceType();
        if (!serviceType.parse(str)) {
            return null;
        }
        return serviceType;
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public boolean parse(String str) {
        boolean parse = super.parse(str);
        if (parse && getMainType() == UrnType.SERVICE) {
            return true;
        }
        return parse;
    }

    public ServiceType() {
    }

    public ServiceType(Parcel parcel) {
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
