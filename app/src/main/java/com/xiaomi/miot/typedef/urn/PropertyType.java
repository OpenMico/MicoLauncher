package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class PropertyType extends Urn implements Parcelable {
    public static final Parcelable.Creator<PropertyType> CREATOR = new Parcelable.Creator<PropertyType>() { // from class: com.xiaomi.miot.typedef.urn.PropertyType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PropertyType createFromParcel(Parcel parcel) {
            return new PropertyType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PropertyType[] newArray(int i) {
            return new PropertyType[i];
        }
    };

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PropertyType(String str, String str2, String str3) {
        super.setDomain(str);
        super.setMainType(UrnType.PROPERTY);
        super.setSubType(str2);
        super.setShortUUID(str3);
    }

    public static PropertyType valueOf(String str) {
        PropertyType propertyType = new PropertyType();
        if (!propertyType.parse(str)) {
            return null;
        }
        return propertyType;
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public boolean parse(String str) {
        boolean parse = super.parse(str);
        if (parse && getMainType() == UrnType.PROPERTY) {
            return true;
        }
        return parse;
    }

    public PropertyType() {
    }

    public PropertyType(Parcel parcel) {
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
