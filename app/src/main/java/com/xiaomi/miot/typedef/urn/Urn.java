package com.xiaomi.miot.typedef.urn;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class Urn implements Parcelable {
    public static final Parcelable.Creator<Urn> CREATOR = new Parcelable.Creator<Urn>() { // from class: com.xiaomi.miot.typedef.urn.Urn.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Urn createFromParcel(Parcel parcel) {
            return new Urn(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Urn[] newArray(int i) {
            return new Urn[i];
        }
    };
    private static final String URN = "urn";
    private String domain;
    private UrnType mainType = UrnType.UNDEFINED;
    private String shortUUID;
    private String subType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static Urn create(String str, UrnType urnType, String str2, String str3) {
        Urn urn = new Urn();
        urn.domain = str;
        urn.mainType = urnType;
        urn.subType = str2;
        urn.shortUUID = str3;
        return urn;
    }

    public boolean parse(String str) {
        String[] split = str.split(Constants.COLON_SEPARATOR);
        if (split.length != 5 || !split[0].equals(URN)) {
            return false;
        }
        this.domain = split[1];
        this.mainType = UrnType.retrieveType(split[2]);
        this.subType = split[3];
        this.shortUUID = split[4];
        return true;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public UrnType getMainType() {
        return this.mainType;
    }

    public void setMainType(UrnType urnType) {
        this.mainType = urnType;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String str) {
        this.subType = str;
    }

    public String getShortUUID() {
        return this.shortUUID;
    }

    public void setShortUUID(String str) {
        this.shortUUID = str;
    }

    public String toString() {
        return String.format("%s:%s:%s:%s:%s", URN, this.domain, this.mainType.toString(), this.subType, this.shortUUID);
    }

    public int hashCode() {
        String str = this.domain;
        int i = 0;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        UrnType urnType = this.mainType;
        int hashCode2 = (hashCode + (urnType == null ? 0 : urnType.hashCode())) * 31;
        String str2 = this.subType;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.shortUUID;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode3 + i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Urn)) {
            return false;
        }
        Urn urn = (Urn) obj;
        String str = this.domain;
        if (str == null) {
            if (urn.domain != null) {
                return false;
            }
        } else if (!str.equals(urn.domain)) {
            return false;
        }
        if (this.mainType != urn.mainType) {
            return false;
        }
        String str2 = this.subType;
        if (str2 == null) {
            if (urn.subType != null) {
                return false;
            }
        } else if (!str2.equals(urn.subType)) {
            return false;
        }
        return this.shortUUID.equals(urn.shortUUID);
    }

    public Urn() {
    }

    public Urn(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        parse(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toString());
    }
}
