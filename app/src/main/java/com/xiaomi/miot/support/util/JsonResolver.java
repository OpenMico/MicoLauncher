package com.xiaomi.miot.support.util;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class JsonResolver {
    public static final String KEY_DEVICE_INFO_LIST = "deviceInfoList";
    public static final String KEY_IS_SPECDEVICE_ALLREADY = "isSpecDeviceAllReady";
    public static final String KEY_MODEL_INFO = "deviceModelInfo";
    public static final String KEY_PROP_ACTION_INFO = "devicePropAndActionInfo";

    private JsonResolver() {
    }

    public static Map<String, ModelInfo> resolveModelInfo(JSONObject jSONObject) {
        try {
            HashMap hashMap = new HashMap();
            JSONObject optJSONObject = jSONObject.optJSONObject("expandDeviceInfo");
            if (optJSONObject != null) {
                JSONArray optJSONArray = optJSONObject.optJSONArray("modelInfo");
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                    hashMap.put(optJSONObject2.optString("model"), ModelInfo.createFromJson(optJSONObject2.optJSONObject("modelInfo")));
                }
            }
            return hashMap;
        } catch (JSONException e) {
            Log.e("MICO_ME", "Error: resolve model info from bundle json error; " + e.getMessage());
            return null;
        }
    }

    public static Map<String, ModelInfo> resolveModelInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return resolveModelInfo(new JSONObject(str));
        } catch (JSONException e) {
            MiotLog.e("resolveModelInfo error: " + e.getMessage());
            return null;
        }
    }

    public static Map<String, DeviceItemInfo> resolveDevicePropInfo(JSONObject jSONObject) {
        try {
            HashMap hashMap = new HashMap();
            if (jSONObject.optJSONArray("device_prop_info") != null) {
                JSONArray optJSONArray = jSONObject.optJSONArray("device_prop_info");
                for (int i = 0; i < optJSONArray.length(); i++) {
                    DeviceItemInfo createFromJson = DeviceItemInfo.createFromJson(optJSONArray.optJSONObject(i));
                    hashMap.put(createFromJson.getDeviceId(), createFromJson);
                }
            }
            return hashMap;
        } catch (JSONException e) {
            Log.e("MICO_ME", "Error: resolve device prop info from bundle json error; " + e.getMessage());
            return null;
        }
    }

    public static Map<String, DeviceItemInfo> resolveDevicePropInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return resolveDevicePropInfo(new JSONObject(str));
        } catch (JSONException e) {
            MiotLog.e("resolvePropInfo error: " + e.getMessage());
            return null;
        }
    }

    public static List<DeviceInfo> resolveDeviceListInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray optJSONArray = new JSONObject(str).optJSONArray(KEY_DEVICE_INFO_LIST);
            if (optJSONArray != null && optJSONArray.length() > 0) {
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    DeviceInfo createDeviceInfoFromJson = createDeviceInfoFromJson(optJSONArray.optJSONObject(i));
                    if (createDeviceInfoFromJson != null) {
                        arrayList.add(createDeviceInfoFromJson);
                    }
                }
            }
        } catch (JSONException e) {
            MiotLog.e("Error: resolve device list error; " + e.getMessage());
        }
        return arrayList;
    }

    public static String parseDeviceListToJson(List<DeviceInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append(KEY_DEVICE_INFO_LIST);
        sb.append("\":[");
        for (DeviceInfo deviceInfo : list) {
            sb.append(parseDeviceInfoToJsonStr(deviceInfo));
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");
        return sb.toString();
    }

    public static String parseDeviceInfoToJsonStr(DeviceInfo deviceInfo) {
        Set<String> keySet;
        if (deviceInfo == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("did", deviceInfo.did);
            jSONObject.put("encryptDid", deviceInfo.encryptDid);
            jSONObject.put("deviceName", deviceInfo.deviceName);
            jSONObject.put("icon", deviceInfo.icon);
            jSONObject.put("subtitle", deviceInfo.subtitle);
            jSONObject.put("roomId", deviceInfo.roomId);
            jSONObject.put("roomInfo", deviceInfo.roomInfo);
            jSONObject.put("isOnline", deviceInfo.isOnline);
            jSONObject.put("showSlideButton", deviceInfo.showSlideButton);
            jSONObject.put("currentStatus", deviceInfo.currentStatus);
            jSONObject.put("model", deviceInfo.model);
            if (!(deviceInfo.subtitleMap == null || (keySet = deviceInfo.subtitleMap.keySet()) == null || keySet.isEmpty())) {
                JSONObject jSONObject2 = new JSONObject();
                for (String str : keySet) {
                    jSONObject2.put(str, deviceInfo.subtitleMap.get(str));
                }
                jSONObject.put("subtitleMap", jSONObject2);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            MiotLog.e("Error: parse device info to json str error: " + e.getMessage());
            return null;
        }
    }

    private static DeviceInfo createDeviceInfoFromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.did = jSONObject.optString("did");
        deviceInfo.encryptDid = jSONObject.optString("encryptDid");
        deviceInfo.deviceName = jSONObject.optString("deviceName");
        deviceInfo.icon = jSONObject.optString("icon");
        deviceInfo.subtitle = jSONObject.optString("subtitle");
        deviceInfo.roomId = jSONObject.optString("roomId");
        deviceInfo.roomInfo = jSONObject.optString("roomInfo");
        deviceInfo.isOnline = jSONObject.optBoolean("isOnline");
        deviceInfo.showSlideButton = jSONObject.optBoolean("showSlideButton");
        deviceInfo.currentStatus = jSONObject.optBoolean("currentStatus");
        return deviceInfo;
    }
}
