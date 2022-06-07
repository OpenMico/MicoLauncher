package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ActionType extends Urn implements Parcelable {
    public static final Parcelable.Creator<ActionType> CREATOR = new Parcelable.Creator<ActionType>() { // from class: com.xiaomi.miot.typedef.urn.ActionType.1
        @Override // android.os.Parcelable.Creator
        public ActionType createFromParcel(Parcel parcel) {
            return new ActionType(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ActionType[] newArray(int i) {
            return new ActionType[i];
        }
    };

    @Override // com.xiaomi.miot.typedef.urn.Urn, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActionType(String str, String str2, String str3) {
        super.setDomain(str);
        super.setMainType(UrnType.ACTION);
        super.setSubType(str2);
        super.setShortUUID(str3);
    }

    public static ActionType valueOf(String str) {
        ActionType actionType = new ActionType();
        if (!actionType.parse(str)) {
            return null;
        }
        return actionType;
    }

    @Override // com.xiaomi.miot.typedef.urn.Urn
    public boolean parse(String str) {
        boolean parse = super.parse(str);
        if (parse && getMainType() == UrnType.PROPERTY) {
            return true;
        }
        return parse;
    }

    public ActionType() {
    }

    public ActionType(Parcel parcel) {
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
