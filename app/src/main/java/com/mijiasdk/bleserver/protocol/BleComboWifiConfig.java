package com.mijiasdk.bleserver.protocol;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class BleComboWifiConfig implements Parcelable {
    public static final Parcelable.Creator<BleComboWifiConfig> CREATOR = new Parcelable.Creator<BleComboWifiConfig>() { // from class: com.mijiasdk.bleserver.protocol.BleComboWifiConfig.1
        /* renamed from: a */
        public BleComboWifiConfig createFromParcel(Parcel parcel) {
            return new BleComboWifiConfig(parcel);
        }

        /* renamed from: a */
        public BleComboWifiConfig[] newArray(int i) {
            return new BleComboWifiConfig[i];
        }
    };
    public String bindKey;
    public String configType;
    public String countryCode;
    public String countryDomain;
    public int gmtOffset;
    public String passportUrl;
    public String password;
    public String ssid;
    public String timezone;
    public String uid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BleComboWifiConfig() {
        this.uid = "";
        this.ssid = "";
        this.password = "";
        this.countryDomain = "";
        this.timezone = "";
        this.configType = "";
        this.bindKey = "";
        this.countryCode = "";
        this.passportUrl = "";
    }

    public BleComboWifiConfig(Parcel parcel) {
        this.uid = "";
        this.ssid = "";
        this.password = "";
        this.countryDomain = "";
        this.timezone = "";
        this.configType = "";
        this.bindKey = "";
        this.countryCode = "";
        this.passportUrl = "";
        this.uid = parcel.readString();
        this.ssid = parcel.readString();
        this.password = parcel.readString();
        this.gmtOffset = parcel.readInt();
        this.countryDomain = parcel.readString();
        this.timezone = parcel.readString();
        this.configType = parcel.readString();
        this.bindKey = parcel.readString();
        this.countryCode = parcel.readString();
        this.passportUrl = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.uid);
        parcel.writeString(this.ssid);
        parcel.writeString(this.password);
        parcel.writeInt(this.gmtOffset);
        parcel.writeString(this.countryDomain);
        parcel.writeString(this.timezone);
        parcel.writeString(this.configType);
        parcel.writeString(this.bindKey);
        parcel.writeString(this.countryCode);
        parcel.writeString(this.passportUrl);
    }
}
