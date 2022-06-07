package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.support.MiotLog;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceActionBriefInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceActionBriefInfo> CREATOR = new Parcelable.Creator<DeviceActionBriefInfo>() { // from class: com.xiaomi.miot.support.category.DeviceActionBriefInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceActionBriefInfo createFromParcel(Parcel parcel) {
            return new DeviceActionBriefInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceActionBriefInfo[] newArray(int i) {
            return new DeviceActionBriefInfo[i];
        }
    };
    private String actionType;
    private int aiid;
    private String serviceType;
    private int siid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceActionBriefInfo() {
    }

    protected DeviceActionBriefInfo(Parcel parcel) {
        this.siid = parcel.readInt();
        this.aiid = parcel.readInt();
        this.actionType = parcel.readString();
        this.serviceType = parcel.readString();
    }

    public int getSiid() {
        return this.siid;
    }

    public void setSiid(int i) {
        this.siid = i;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String str) {
        this.actionType = str;
    }

    public int getAiid() {
        return this.aiid;
    }

    public void setAiid(int i) {
        this.aiid = i;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String str) {
        this.serviceType = str;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("siid", this.siid);
            jSONObject.put("aiid", this.aiid);
            jSONObject.put("actionType", this.actionType);
            jSONObject.put("serviceType", this.serviceType);
        } catch (JSONException e) {
            MiotLog.e("DeviceActionBriefInfo json error: " + e.getMessage());
        }
        return jSONObject.toString();
    }

    public static DeviceActionBriefInfo createFromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            MiotLog.d("DeviceActionBriefInfo json is null");
            return null;
        }
        DeviceActionBriefInfo deviceActionBriefInfo = new DeviceActionBriefInfo();
        deviceActionBriefInfo.setSiid(jSONObject.optInt("siid"));
        deviceActionBriefInfo.setAiid(jSONObject.optInt("aiid"));
        deviceActionBriefInfo.setActionType(jSONObject.optString("actionType"));
        deviceActionBriefInfo.setServiceType(jSONObject.optString("serviceType"));
        return deviceActionBriefInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.siid);
        parcel.writeInt(this.aiid);
        parcel.writeString(this.actionType);
        parcel.writeString(this.serviceType);
    }
}
