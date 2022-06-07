package com.xiaomi.miot.support.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import com.metv.component.simple_http.SimpleHttpCallback;
import com.metv.component.simple_http.SimpleHttpClient;
import com.metv.component.simple_http.SimpleHttpRequest;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.util.MiotUtil;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceCategoryManager {
    private static final String HOST_PREVIEW = "https://preview.speaker.xiaomi.com";
    private static final String HOST_QA = "https://test.speaker.xiaomi.com";
    private static final String HOST_RELEASE = "https://api.speaker.xiaomi.com";
    private static final String PATH = "/speaker/api/common/category/mappings";
    private static final int STATUS_REQUESTING = 1;
    private static final int STATUS_SUCCESS = 2;
    @SuppressLint({"StaticFieldLeak"})
    private static Context sContext = null;
    private static SimpleHttpClient sHttpClient = null;
    private static volatile int sMappingRequestStatus = 0;
    private static Map<String, String> sModelCategoryMapping = null;
    private static Runnable sPendingRequestRunnable = null;
    private static int sRetryCount = 0;
    private static int sServerEnv = -1;
    private static Map<String, String> sTypeCategoryMapping;

    static /* synthetic */ int access$304() {
        int i = sRetryCount + 1;
        sRetryCount = i;
        return i;
    }

    public static void init(Context context) {
        sContext = context;
        sHttpClient = new SimpleHttpClient(getHost(context));
        registerServerEnvChangedListener();
        MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.category.DeviceCategoryManager.1
            @Override // java.lang.Runnable
            public void run() {
                DeviceCategoryManager.initMappingFromLocal();
            }
        });
    }

    private static void registerServerEnvChangedListener() {
        sContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mico_server_env"), true, new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaomi.miot.support.category.DeviceCategoryManager.2
            @Override // android.database.ContentObserver
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                onChange(z, null);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                int unused = DeviceCategoryManager.sServerEnv = Settings.Global.getInt(DeviceCategoryManager.sContext.getContentResolver(), "mico_server_env", 0);
            }
        });
    }

    private static String getHost(Context context) {
        if (sServerEnv == -1) {
            sServerEnv = Settings.Global.getInt(context.getContentResolver(), "mico_server_env", 3);
        }
        switch (sServerEnv) {
            case 1:
            case 2:
                return HOST_QA;
            case 3:
                return "https://api.speaker.xiaomi.com";
            case 4:
                return "https://preview.speaker.xiaomi.com";
            default:
                return "https://api.speaker.xiaomi.com";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initMappingFromLocal() {
        MiotLog.i("Info: init mapping from local");
        String mappingJsonStrFromSP = DeviceCategoryHelper.getMappingJsonStrFromSP(sContext);
        if (mappingJsonStrFromSP != null && !TextUtils.isEmpty(mappingJsonStrFromSP)) {
            try {
                MiotLog.i("Info: resolve mapping from local");
                resolveMapping(new JSONObject(mappingJsonStrFromSP));
            } catch (JSONException unused) {
            }
        }
    }

    public static void refreshMappingFromServer(boolean z) {
        MiotLog.i("Info: refresh mapping from server! , isNetworkChanged: " + z);
        if (sMappingRequestStatus == 2) {
            MiotLog.i("Info: refresh mapping from server cancel, request status: " + sMappingRequestStatus);
        } else if (sMappingRequestStatus != 1 || z) {
            if (sPendingRequestRunnable != null) {
                if (z) {
                    MiotLog.i("Info: force refresh mapping from server!");
                    MiotUtil.removeCallback(sPendingRequestRunnable);
                    sPendingRequestRunnable = null;
                } else {
                    MiotLog.i("Info: refresh mapping from server cancel, pending request is not NULL, Retrying!");
                    return;
                }
            }
            sMappingRequestStatus = 1;
            final SimpleHttpRequest simpleHttpRequest = new SimpleHttpRequest();
            simpleHttpRequest.setPath(PATH);
            sHttpClient.request(simpleHttpRequest, new SimpleHttpCallback() { // from class: com.xiaomi.miot.support.category.DeviceCategoryManager.3
                @Override // com.metv.component.simple_http.SimpleHttpCallback
                public void onSuccess(SimpleHttpRequest simpleHttpRequest2, int i, byte[] bArr) {
                    final String str = new String(bArr);
                    MiotLog.i("Info: refresh mapping from server, Response: " + str);
                    if (i == 200) {
                        try {
                            final JSONObject jSONObject = new JSONObject(str);
                            if (jSONObject.optInt("code") == 0) {
                                int unused = DeviceCategoryManager.sRetryCount = 0;
                                int unused2 = DeviceCategoryManager.sMappingRequestStatus = 2;
                                MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.category.DeviceCategoryManager.3.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DeviceCategoryManager.resolveMapping(jSONObject);
                                        DeviceCategoryHelper.persistMappingJsonStr(DeviceCategoryManager.sContext, str);
                                    }
                                });
                            } else {
                                onError(SimpleHttpRequest.this, 491, "server response code error!");
                            }
                        } catch (JSONException unused3) {
                            onError(SimpleHttpRequest.this, 492, "server response json syntax error!");
                        }
                    } else {
                        onError(SimpleHttpRequest.this, 493, "http response code error!");
                    }
                }

                @Override // com.metv.component.simple_http.SimpleHttpCallback
                public void onError(SimpleHttpRequest simpleHttpRequest2, int i, String str) {
                    MiotLog.e("HttpRequest Error: " + i + Constants.ACCEPT_TIME_SEPARATOR_SP + str);
                    int unused = DeviceCategoryManager.sMappingRequestStatus = 0;
                    if (DeviceCategoryManager.sTypeCategoryMapping == null && DeviceCategoryManager.sPendingRequestRunnable == null) {
                        Runnable unused2 = DeviceCategoryManager.sPendingRequestRunnable = new Runnable() { // from class: com.xiaomi.miot.support.category.DeviceCategoryManager.3.2
                            @Override // java.lang.Runnable
                            public void run() {
                                Runnable unused3 = DeviceCategoryManager.sPendingRequestRunnable = null;
                                DeviceCategoryManager.refreshMappingFromServer(false);
                            }
                        };
                        MiotUtil.postDelayed(DeviceCategoryManager.sPendingRequestRunnable, DeviceCategoryManager.access$304() * 1000);
                    }
                }
            });
        } else {
            MiotLog.i("Info: refresh mapping from server cancel, request status: " + sMappingRequestStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resolveMapping(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("data");
        Map<String, String> parseCategoryMapping = DeviceCategoryHelper.parseCategoryMapping(optJSONObject.optJSONObject("type_category_mapping"));
        if (parseCategoryMapping != null && !parseCategoryMapping.isEmpty()) {
            sTypeCategoryMapping = parseCategoryMapping;
        }
        Map<String, String> parseCategoryMapping2 = DeviceCategoryHelper.parseCategoryMapping(optJSONObject.optJSONObject("model_category_mapping"));
        if (parseCategoryMapping2 != null && !parseCategoryMapping2.isEmpty()) {
            sModelCategoryMapping = parseCategoryMapping2;
        }
        MiotManager.setMappingInfoReady();
    }

    public static Map<String, HomeCategory> getHomeCategories(List<DeviceInfo> list, Map<String, ModelInfo> map, Map<String, DeviceItemInfo> map2) {
        if (list == null || list.isEmpty()) {
            MiotLog.e("Error: getHomeCategories error: invalid params; devices size is 0;");
            return null;
        } else if (map == null || map.isEmpty()) {
            MiotLog.e("Error: getHomeCategories error: invalid params; modelinfos size is 0;");
            return null;
        } else if (map2 == null || map2.isEmpty()) {
            MiotLog.e("Error: getHomeCategories error: invalid params; devicePropInfos size is 0;");
            return null;
        } else {
            Map<String, String> map3 = sTypeCategoryMapping;
            if (map3 == null || map3.isEmpty()) {
                MiotLog.e("Error: getHomeCategories error: invalid params; sTypeCategoryMapping is null");
                return null;
            }
            HashMap hashMap = new HashMap();
            for (MiotHome miotHome : MiotManager.getHomeList()) {
                HomeCategory homeCategory = new HomeCategory(miotHome.homeId, miotHome.homeName, miotHome.shareHomeFlag);
                hashMap.put(homeCategory.getHomeId(), homeCategory);
            }
            ArrayList<CategoryWrapperInner> arrayList = new ArrayList();
            for (DeviceInfo deviceInfo : list) {
                if (!deviceInfo.did.startsWith("ir") || !MiotManager.isFilterIrDevice()) {
                    String str = deviceInfo.model;
                    ModelInfo modelInfo = map.get(str);
                    DeviceItemInfo deviceItemInfo = map2.get(deviceInfo.did);
                    if (modelInfo == null) {
                        MiotLog.i("Info: device don't has model info， " + deviceInfo.did);
                    } else if (deviceItemInfo == null) {
                        MiotLog.i("device don't has prop: " + deviceInfo.did);
                    } else {
                        String homeId = deviceItemInfo.getHomeId();
                        boolean equals = DataModel.CIRCULATEFAIL_NO_SUPPORT.equals(homeId);
                        HomeCategory homeCategory2 = (HomeCategory) hashMap.get(homeId);
                        String categoryNameByDeviceType = getCategoryNameByDeviceType(str, modelInfo.getDeviceType());
                        if (!TextUtils.isEmpty(categoryNameByDeviceType)) {
                            DeviceInfoWrapper deviceInfoWrapper = new DeviceInfoWrapper(deviceInfo, modelInfo, deviceItemInfo);
                            if (equals) {
                                arrayList.add(new CategoryWrapperInner(categoryNameByDeviceType, deviceInfoWrapper));
                            } else if (homeCategory2 != null) {
                                homeCategory2.addCategoryDevice(categoryNameByDeviceType, deviceInfoWrapper);
                            }
                        }
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                for (String str2 : hashMap.keySet()) {
                    HomeCategory homeCategory3 = (HomeCategory) hashMap.get(str2);
                    if (1 != homeCategory3.getHomeSharedFlag()) {
                        for (CategoryWrapperInner categoryWrapperInner : arrayList) {
                            homeCategory3.addCategoryDevice(categoryWrapperInner.categoryName, categoryWrapperInner.deviceInfoWrapper);
                        }
                    }
                }
            }
            return hashMap;
        }
    }

    private static String getCategoryNameByDeviceType(String str, String str2) {
        Map<String, String> map = sModelCategoryMapping;
        String str3 = (map == null || map.isEmpty()) ? null : sModelCategoryMapping.get(str);
        return TextUtils.isEmpty(str3) ? sTypeCategoryMapping.get(str2) : str3;
    }

    /* loaded from: classes3.dex */
    static class CategoryWrapperInner {
        String categoryName;
        DeviceInfoWrapper deviceInfoWrapper;

        CategoryWrapperInner(String str, DeviceInfoWrapper deviceInfoWrapper) {
            this.categoryName = str;
            this.deviceInfoWrapper = deviceInfoWrapper;
        }
    }
}
