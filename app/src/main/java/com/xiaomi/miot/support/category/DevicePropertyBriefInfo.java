package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.support.MiotLog;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DevicePropertyBriefInfo implements Parcelable {
    public static final Parcelable.Creator<DevicePropertyBriefInfo> CREATOR = new Parcelable.Creator<DevicePropertyBriefInfo>() { // from class: com.xiaomi.miot.support.category.DevicePropertyBriefInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePropertyBriefInfo createFromParcel(Parcel parcel) {
            return new DevicePropertyBriefInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePropertyBriefInfo[] newArray(int i) {
            return new DevicePropertyBriefInfo[i];
        }
    };
    private String key;
    private DevicePropValue propValue;
    private String serviceTypeName;
    private int siid;
    private String typeName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePropertyBriefInfo() {
    }

    protected DevicePropertyBriefInfo(Parcel parcel) {
        this.key = parcel.readString();
        this.typeName = parcel.readString();
        this.siid = parcel.readInt();
        this.serviceTypeName = parcel.readString();
        this.propValue = (DevicePropValue) parcel.readParcelable(DevicePropValue.class.getClassLoader());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public DevicePropValue getPropValue() {
        return this.propValue;
    }

    public void setPropValue(DevicePropValue devicePropValue) {
        this.propValue = devicePropValue;
    }

    public int getSiid() {
        return this.siid;
    }

    public void setSiid(int i) {
        this.siid = i;
    }

    public String getServiceTypeName() {
        return this.serviceTypeName;
    }

    public void setServiceTypeName(String str) {
        this.serviceTypeName = str;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("key", this.key);
            jSONObject.put("typeName", this.typeName);
            if (this.propValue != null) {
                jSONObject.put("propValue", new JSONObject(this.propValue.toString()));
            }
        } catch (JSONException e) {
            Log.e("MICO_ME", "DevicePropertyBriefInfo json error: " + e.getMessage());
        }
        return jSONObject.toString();
    }

    public static DevicePropertyBriefInfo createFromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            MiotLog.d("devicePropertyBriefInfo json is null");
            return null;
        }
        DevicePropertyBriefInfo devicePropertyBriefInfo = new DevicePropertyBriefInfo();
        devicePropertyBriefInfo.setKey(jSONObject.optString("key"));
        devicePropertyBriefInfo.setTypeName(jSONObject.optString("typeName"));
        devicePropertyBriefInfo.setSiid(jSONObject.optInt("siid"));
        devicePropertyBriefInfo.setServiceTypeName(jSONObject.optString("srvTypeName"));
        JSONObject optJSONObject = jSONObject.optJSONObject("propValue");
        if (optJSONObject != null) {
            devicePropertyBriefInfo.propValue = DevicePropValue.createFromJson(optJSONObject);
        }
        return devicePropertyBriefInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.typeName);
        parcel.writeInt(this.siid);
        parcel.writeString(this.serviceTypeName);
        parcel.writeParcelable(this.propValue, 0);
    }
}
