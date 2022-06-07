package com.xiaomi.miot.support.category;

import android.text.TextUtils;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.util.JsonResolver;
import com.xiaomi.miot.support.util.MiotUtil;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceInfoLocalCacheManager {
    public static final String FILE_NAME_DEVICE_CACHE = "device_info.cache";
    public static final String FILE_NAME_PROP_CACHE = "device_prop_info.cache";
    private static String sDeviceListCacheMd5;
    private static Map<String, DeviceItemInfo> sDevicePropInfoCache;
    private static List<DeviceInfo> sDevicesCache;
    private static Map<String, ModelInfo> sModelInfoCache;

    private DeviceInfoLocalCacheManager() {
    }

    public static void initCache() {
        MiotLog.i("Init local cache!");
        MiotLog.i("Start load model and prop info!");
        String loadInfoFromCache = MiotUtil.loadInfoFromCache(FILE_NAME_PROP_CACHE);
        StringBuilder sb = new StringBuilder();
        sb.append("Load model and prop cache size: ");
        int i = 0;
        sb.append(loadInfoFromCache == null ? 0 : loadInfoFromCache.length());
        MiotLog.i(sb.toString());
        if (!TextUtils.isEmpty(loadInfoFromCache)) {
            try {
                JSONObject jSONObject = new JSONObject(loadInfoFromCache);
                sModelInfoCache = JsonResolver.resolveModelInfo(jSONObject.optJSONObject(JsonResolver.KEY_MODEL_INFO));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("cache model size: ");
                sb2.append(sModelInfoCache == null ? 0 : sModelInfoCache.size());
                MiotLog.i(sb2.toString());
                sDevicePropInfoCache = JsonResolver.resolveDevicePropInfo(jSONObject.optJSONObject(JsonResolver.KEY_PROP_ACTION_INFO));
                StringBuilder sb3 = new StringBuilder();
                sb3.append("cache prop size: ");
                sb3.append(sDevicePropInfoCache == null ? 0 : sDevicePropInfoCache.size());
                MiotLog.i(sb3.toString());
            } catch (JSONException e) {
                MiotLog.i("Error: init device prop info from cache error; " + e.getMessage());
            }
        }
        MiotLog.i("Start load device info!");
        String loadInfoFromCache2 = MiotUtil.loadInfoFromCache(FILE_NAME_DEVICE_CACHE);
        if (loadInfoFromCache2 != null && loadInfoFromCache2.length() != 0) {
            sDeviceListCacheMd5 = MiotUtil.getBytesMD5(loadInfoFromCache2.getBytes(StandardCharsets.UTF_8));
            MiotLog.i("Load device info size: " + loadInfoFromCache2.length());
            sDevicesCache = JsonResolver.resolveDeviceListInfo(loadInfoFromCache2);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("cache device info size: ");
            List<DeviceInfo> list = sDevicesCache;
            if (list != null) {
                i = list.size();
            }
            sb4.append(i);
            MiotLog.i(sb4.toString());
            MiotManager.setCacheInfoReady();
        }
    }

    public static void releaseCache() {
        MiotLog.i("Release cache data!");
        MiotManager.sCacheReady = false;
        sDevicesCache = null;
        sModelInfoCache = null;
        sDevicePropInfoCache = null;
    }

    public static void saveDeviceInfoList() {
        String parseDeviceListToJson = JsonResolver.parseDeviceListToJson(MiotManager.getDevices());
        if (!TextUtils.isEmpty(parseDeviceListToJson) && !isDeviceListInfoCached(MiotUtil.getBytesMD5(parseDeviceListToJson.getBytes(StandardCharsets.UTF_8)))) {
            MiotUtil.saveDeviceListInfoCache(parseDeviceListToJson);
        }
    }

    public static List<DeviceInfo> getDevices() {
        return sDevicesCache;
    }

    public static Map<String, ModelInfo> getModelInfo() {
        return sModelInfoCache;
    }

    public static Map<String, DeviceItemInfo> getPropInfo() {
        return sDevicePropInfoCache;
    }

    public static boolean isDeviceListInfoCached(String str) {
        return str.equals(sDeviceListCacheMd5);
    }
}
