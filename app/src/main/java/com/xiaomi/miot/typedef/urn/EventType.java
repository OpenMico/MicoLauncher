package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class EventType extends Urn implements Parcelable {
    public static final Parcelable.Creator<EventType> CREATOR = new Parcelable.Creator<EventType>() { // from class: com.xiaomi.miot.typedef.urn.EventType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventType createFromParcel(Parcel parcel) {
            return new EventType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EventType[] newArray(int i) {
            return new EventType[i];
        }
    };

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EventType(String str, String str2, String str3) {
        super.setDomain(str);
        super.setMainType(UrnType.EVENT);
        super.setSubType(str2);
        super.setShortUUID(str3);
    }

    public static EventType valueOf(String str) {
        EventType eventType = new EventType();
        if (!eventType.parse(str)) {
            return null;
        }
        return eventType;
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public boolean parse(String str) {
        boolean parse = super.parse(str);
        if (parse && getMainType() == UrnType.EVENT) {
            return true;
        }
        return parse;
    }

    public EventType() {
    }

    public EventType(Parcel parcel) {
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
