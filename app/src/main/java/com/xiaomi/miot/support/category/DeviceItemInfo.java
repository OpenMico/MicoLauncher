package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.miot.support.MiotLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceItemInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceItemInfo> CREATOR = new Parcelable.Creator<DeviceItemInfo>() { // from class: com.xiaomi.miot.support.category.DeviceItemInfo.1
        @Override // android.os.Parcelable.Creator
        public DeviceItemInfo createFromParcel(Parcel parcel) {
            return new DeviceItemInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DeviceItemInfo[] newArray(int i) {
            return new DeviceItemInfo[i];
        }
    };
    private Map<String, List<DeviceActionBriefInfo>> actionMap;
    private String deviceId;
    private String homeId;
    private String homeName;
    private int homeSharedFlag;
    private String model;
    private Map<String, List<DevicePropertyBriefInfo>> propMap;
    private String roomId;
    private String roomName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DeviceItemInfo() {
    }

    protected DeviceItemInfo(Parcel parcel) {
        this.deviceId = parcel.readString();
        this.model = parcel.readString();
        this.homeId = parcel.readString();
        this.homeName = parcel.readString();
        this.roomId = parcel.readString();
        this.roomName = parcel.readString();
        this.homeSharedFlag = parcel.readInt();
        try {
            this.propMap = parcel.readHashMap(DevicePropertyBriefInfo.class.getClassLoader());
        } catch (Exception e) {
            MiotLog.e("Create devicePropInfo from Parcel error: " + e.getMessage());
        }
        try {
            this.actionMap = parcel.readHashMap(DeviceActionBriefInfo.class.getClassLoader());
        } catch (Exception e2) {
            MiotLog.e("Create device action info from Parcel error: " + e2.getMessage());
        }
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    private DeviceItemInfo setDeviceId(String str) {
        this.deviceId = str;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    private DeviceItemInfo setModel(String str) {
        this.model = str;
        return this;
    }

    public String getHomeId() {
        return this.homeId;
    }

    private DeviceItemInfo setHomeId(String str) {
        this.homeId = str;
        return this;
    }

    public String getHomeName() {
        return this.homeName;
    }

    private DeviceItemInfo setHomeName(String str) {
        this.homeName = str;
        return this;
    }

    public String getRoomId() {
        return this.roomId;
    }

    private DeviceItemInfo setRoomId(String str) {
        this.roomId = str;
        return this;
    }

    public String getRoomName() {
        return this.roomName;
    }

    private DeviceItemInfo setRoomName(String str) {
        this.roomName = str;
        return this;
    }

    public int getHomeSharedFlag() {
        return this.homeSharedFlag;
    }

    public DeviceItemInfo setHomeSharedFlag(int i) {
        this.homeSharedFlag = i;
        return this;
    }

    public Map<String, List<DevicePropertyBriefInfo>> getProperties() {
        return this.propMap;
    }

    private DeviceItemInfo setProperties(Map<String, List<DevicePropertyBriefInfo>> map) {
        this.propMap = map;
        return this;
    }

    public Map<String, List<DeviceActionBriefInfo>> getActions() {
        return this.actionMap;
    }

    public DeviceItemInfo setActions(Map<String, List<DeviceActionBriefInfo>> map) {
        this.actionMap = map;
        return this;
    }

    public static DeviceItemInfo createFromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("properties");
        HashMap hashMap = new HashMap();
        for (int i = 0; i < optJSONArray.length(); i++) {
            DevicePropertyBriefInfo createFromJson = DevicePropertyBriefInfo.createFromJson(optJSONArray.getJSONObject(i));
            if (createFromJson != null) {
                String typeName = createFromJson.getTypeName();
                List list = (List) hashMap.get(typeName);
                if (list == null) {
                    list = new ArrayList(4);
                    hashMap.put(typeName, list);
                }
                list.add(createFromJson);
            }
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("actions");
        HashMap hashMap2 = new HashMap();
        if (optJSONArray2 != null) {
            int length = optJSONArray2.length();
            if (optJSONArray2.length() > 0) {
                for (int i2 = 0; i2 < length; i2++) {
                    DeviceActionBriefInfo createFromJson2 = DeviceActionBriefInfo.createFromJson(optJSONArray2.optJSONObject(i2));
                    if (createFromJson2 != null) {
                        String actionType = createFromJson2.getActionType();
                        List list2 = (List) hashMap2.get(actionType);
                        if (list2 == null) {
                            list2 = new ArrayList(4);
                            hashMap2.put(actionType, list2);
                        }
                        list2.add(createFromJson2);
                    }
                }
            }
        }
        return new DeviceItemInfo().setDeviceId(jSONObject.optString(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID)).setModel(jSONObject.optString("model")).setHomeId(jSONObject.optString("homeId")).setHomeName(jSONObject.optString("homeName")).setRoomId(jSONObject.optString("roomId")).setRoomName(jSONObject.optString("roomName")).setHomeSharedFlag(jSONObject.optInt("homeSharedFlag")).setProperties(hashMap).setActions(hashMap2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.model);
        parcel.writeString(this.homeId);
        parcel.writeString(this.homeName);
        parcel.writeString(this.roomId);
        parcel.writeString(this.roomName);
        parcel.writeInt(this.homeSharedFlag);
        parcel.writeMap(this.propMap);
        parcel.writeMap(this.actionMap);
    }
}
