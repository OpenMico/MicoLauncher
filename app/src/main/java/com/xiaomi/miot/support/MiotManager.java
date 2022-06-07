package com.xiaomi.miot.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerSimpleFragmentV2;
import com.xiaomi.miot.BinderParcel;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.IMsgCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.account.AccountInfo;
import com.xiaomi.miot.support.category.DeviceCategoryManager;
import com.xiaomi.miot.support.category.DeviceInfoLocalCacheManager;
import com.xiaomi.miot.support.category.DeviceRefreshFinishedCallback;
import com.xiaomi.miot.support.category.HomeCategory;
import com.xiaomi.miot.support.category.ReplyDeviceInfoCallback;
import com.xiaomi.miot.support.category.SmartHomeInfoReadyCallback;
import com.xiaomi.miot.support.core.ISendMsgCallback;
import com.xiaomi.miot.support.core.MiotDeviceCore;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.util.MiotUtil;
import com.xiaomi.passport.ui.settings.UserAvatarUpdateActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotManager {
    private static String PACKAGE_NAME = "";
    private static ExecutorService mExternalExecutorService = null;
    private static volatile boolean sAllInfoReady = false;
    private static Runnable sAllInfoReadyBackupRunnable = null;
    public static boolean sCacheReady = false;
    @SuppressLint({"StaticFieldLeak"})
    private static Context sContext = null;
    @SuppressLint({"StaticFieldLeak"})
    private static MiotDeviceCore sCore = null;
    public static List<IDeviceCacheReadyCallback> sDeviceCacheReadyCallback = null;
    private static boolean sDeviceInfoReady = false;
    private static volatile List<WeakReference<IDeviceInfoReadyCallback>> sDeviceInfoReadyCallback = null;
    private static boolean sFilterIrDevice = true;
    private static boolean sInitCache = false;
    private static boolean sInitialized = false;
    private static volatile long sLastRefreshDeviceTimestamp = 0;
    private static boolean sMappingInfoReady = false;
    private static List<IMiotMsgCallback> sMiotCallback;
    private static MiotProvider sProvider;
    private static volatile boolean sSmartHomeInfoReady;
    private static ICameraStatus statusCallBack;
    private static final List<WeakReference<IMiotServiceStatusListener>> sServiceStatusListeners = new ArrayList();
    private static final AtomicBoolean isLogin = new AtomicBoolean(false);
    private static final Object LOCK_ALL_INFO_READY_CALLBACK = new Object();
    private static final Object LOCK_SERVICE_STATUS_CALLBACK = new Object();

    /* loaded from: classes2.dex */
    public interface IDeviceCacheReadyCallback {
        void onDeviceCacheReady();
    }

    /* loaded from: classes2.dex */
    public interface IDeviceInfoReadyCallback {
        void onDeviceInfoReady();
    }

    /* loaded from: classes2.dex */
    public interface IMiotServiceStatusListener {
        void onServiceConnect();

        void onServiceDisconnect();
    }

    private static void assertInitialized(String str) {
        if (!sInitialized) {
            throw new RuntimeException("MiotManager must be initialized before calling " + str);
        }
    }

    public static void init(@NonNull Context context, @NonNull MiotProvider miotProvider) {
        init(context, miotProvider, false, null, false);
    }

    public static void init(@NonNull Context context, @NonNull MiotProvider miotProvider, boolean z) {
        init(context, miotProvider, z, null, false);
    }

    public static void init(@NonNull Context context, @NonNull MiotProvider miotProvider, boolean z, boolean z2) {
        init(context, miotProvider, z, null, z2);
    }

    public static void init(@NonNull Context context, @NonNull MiotProvider miotProvider, boolean z, MiotLogger miotLogger, boolean z2) {
        MiotLog.i("Info: Mico support lib init; version: 1.0, initialized: " + sInitialized);
        if (!sInitialized) {
            if (context instanceof Activity) {
                context = context.getApplicationContext();
            }
            sInitialized = true;
            MicoSupConstants.sIsDebug = z;
            sContext = context.getApplicationContext();
            PACKAGE_NAME = context.getPackageName();
            sProvider = miotProvider;
            if (miotLogger != null) {
                MiotLog.setLogger(miotLogger);
            }
            sCore = MiotDeviceCore.with(context);
            DeviceCategoryManager.init(sContext);
            registerMiotMsgCallback(new ReplyDeviceInfoCallback());
            registerMiotMsgCallback(new SmartHomeInfoReadyCallback());
            registerMiotMsgCallback(new DeviceRefreshFinishedCallback());
            sInitCache = z2;
            if (sInitCache) {
                MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.MiotManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DeviceInfoLocalCacheManager.initCache();
                    }
                });
            }
        }
    }

    public static void setDeviceInfoReady(boolean z) {
        MiotLog.i("Info: set device info ready! isDeviceUpdated: " + sSmartHomeInfoReady);
        sDeviceInfoReady = true;
        if (z) {
            sSmartHomeInfoReady = true;
        }
        if (!sMappingInfoReady) {
            return;
        }
        if (sSmartHomeInfoReady) {
            sAllInfoReady = true;
            onAllInfoReadyCallback();
            return;
        }
        postAllInfoReadyBackupRunnable();
    }

    public static void checkAllInfoReadyBackup() {
        if (sDeviceInfoReady && sMappingInfoReady) {
            sSmartHomeInfoReady = true;
            sAllInfoReady = true;
            onAllInfoReadyCallback();
        }
    }

    public static void resetSmartHomeInfoReady() {
        sSmartHomeInfoReady = false;
    }

    public static void forceRefreshDeviceCategoryInfo() {
        HashMap hashMap = new HashMap();
        hashMap.put(MicoSupConstants.Other.KEY_REFRESH_DEVICE_TASK_ID, System.currentTimeMillis() + "");
        sCore.sendMsg(MiotHelper.createSendMsg(MicoSupConstants.MsgAction.ACTION_FORCE_REFRESH_CATEGORY, hashMap), null);
    }

    public static void onSmartHomeInfoReady() {
        if (sSmartHomeInfoReady) {
            Log.i(com.xiaomi.miot.MicoSupConstants.TAG_SUP_MIJIA, "Info: receive smartHomeInfoReady, ignore");
        } else {
            MiotUtil.postDelayed(new Runnable() { // from class: com.xiaomi.miot.support.MiotManager.2
                @Override // java.lang.Runnable
                public void run() {
                    boolean unused = MiotManager.sSmartHomeInfoReady = true;
                    if (MiotManager.sAllInfoReadyBackupRunnable != null) {
                        MiotUtil.removeCallback(MiotManager.sAllInfoReadyBackupRunnable);
                    }
                    Log.i("MICO_SUP_MIJIA_KEY", "Info: refresh device on smartHomeInfoReady!");
                    MiotManager.forceRefreshDeviceCategoryInfo();
                }
            }, 1000L);
        }
    }

    public static void setMappingInfoReady() {
        MiotLog.i("Info: set Mapping info ready! isDeviceUpdated: " + sSmartHomeInfoReady);
        sMappingInfoReady = true;
        if (!sDeviceInfoReady) {
            return;
        }
        if (sSmartHomeInfoReady) {
            sAllInfoReady = true;
            onAllInfoReadyCallback();
            return;
        }
        postAllInfoReadyBackupRunnable();
    }

    private static void postAllInfoReadyBackupRunnable() {
        if (sAllInfoReadyBackupRunnable == null) {
            sAllInfoReadyBackupRunnable = new Runnable() { // from class: com.xiaomi.miot.support.MiotManager.3
                @Override // java.lang.Runnable
                public void run() {
                    Runnable unused = MiotManager.sAllInfoReadyBackupRunnable = null;
                    MiotManager.checkAllInfoReadyBackup();
                }
            };
            MiotUtil.postDelayed(sAllInfoReadyBackupRunnable, 20000L);
        }
    }

    public static synchronized boolean isCacheReady() {
        boolean z;
        synchronized (MiotManager.class) {
            z = sCacheReady;
        }
        return z;
    }

    public static synchronized void setCacheInfoReady() {
        synchronized (MiotManager.class) {
            MiotLog.i("Info: set cache info ready!");
            sCacheReady = true;
            onCacheInfoReady();
        }
    }

    private static synchronized void onCacheInfoReady() {
        synchronized (MiotManager.class) {
            StringBuilder sb = new StringBuilder();
            sb.append("On cache info ready! callback size: ");
            sb.append(sDeviceCacheReadyCallback == null ? 0 : sDeviceCacheReadyCallback.size());
            MiotLog.i(sb.toString());
            if (sDeviceCacheReadyCallback != null && !sDeviceCacheReadyCallback.isEmpty()) {
                for (IDeviceCacheReadyCallback iDeviceCacheReadyCallback : sDeviceCacheReadyCallback) {
                    iDeviceCacheReadyCallback.onDeviceCacheReady();
                }
                sDeviceCacheReadyCallback.clear();
                sDeviceCacheReadyCallback = null;
            }
        }
    }

    public static synchronized void registerDeviceCacheReadyCallback(IDeviceCacheReadyCallback iDeviceCacheReadyCallback) {
        synchronized (MiotManager.class) {
            MiotLog.i("Register cache callback!");
            if (!sInitCache) {
                MiotLog.i("Register cache callback failed, Not init cache!");
            } else if (iDeviceCacheReadyCallback != null) {
                if (sCacheReady) {
                    MiotLog.i("invoke callback direct when register cache callback!");
                    iDeviceCacheReadyCallback.onDeviceCacheReady();
                } else if (isDeviceInfoReady()) {
                    MiotLog.i("Data has been loaded, can't use cache");
                } else {
                    if (sDeviceCacheReadyCallback == null) {
                        sDeviceCacheReadyCallback = new ArrayList();
                    }
                    sDeviceCacheReadyCallback.add(iDeviceCacheReadyCallback);
                }
            }
        }
    }

    public static void setExternalExecutorService(ExecutorService executorService) {
        assertInitialized("setExternalExecutorService");
        MiotLog.i("setExternalExecutorService");
        mExternalExecutorService = executorService;
    }

    public static ExecutorService getExternalExecutorService() {
        return mExternalExecutorService;
    }

    public static void onDeviceIdUpdated(String str) {
        assertInitialized("onDeviceIdUpdated");
        MiotLog.i("onHostDeviceId: " + str);
        sCore.updateHostDeviceId(str);
    }

    public static void setFilterIrDevice(boolean z) {
        sFilterIrDevice = z;
    }

    public static boolean isFilterIrDevice() {
        return sFilterIrDevice;
    }

    public static void destroy() {
        assertInitialized("destroy");
        MiotLog.i("destroy");
        sCore.destroy();
    }

    public static void onLogon() {
        assertInitialized("onLogon");
        MiotLog.i("Info: MiotManager on login!!!");
        if (isLogin.compareAndSet(false, true)) {
            sCore.refreshDevices();
        } else {
            MiotLog.i("Info: on login conflict!!!");
        }
    }

    public static boolean isLogin() {
        return isLogin.get();
    }

    public static void onLogout() {
        assertInitialized("onLogout");
        MiotLog.i("Info: onlogout!!!");
        sCore.clearDevices();
        sContext.sendBroadcast(new Intent("com.xiaomi.miot.support.LOGOUT"));
    }

    public static MiotDeviceCore getDeviceCore() {
        return sCore;
    }

    public static void onHostDeviceHomeIdUpdated(@NonNull String str) {
        assertInitialized("onHostDeviceHomeIdUpdated");
        MiotLog.i("onHostDeviceHomeIdUpdated: " + str);
        sCore.updateHostDeviceHomeId(str);
    }

    public static void onHostDeviceRoomIdUpdated(@NonNull String str) {
        assertInitialized("onHostDeviceRoomIdUpdated");
        MiotLog.i("onHostDeviceRoomIdUpdated: " + str);
        sCore.updateHostDeviceRoomId(str);
    }

    public static void refreshDevices() {
        MiotLog.i("Info: refresh device in MiotManager.refreshDevices()");
        assertInitialized("refreshDevices");
        synchronized (MiotManager.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - sLastRefreshDeviceTimestamp > 5000) {
                sLastRefreshDeviceTimestamp = currentTimeMillis;
                sCore.refreshDevices();
            } else {
                MiotLog.e("Error: refresh device cancel, too frequently!");
            }
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static int getDevicesCount() {
        assertInitialized("getDevicesCount");
        MiotLog.i("getDevicesCount");
        List<DeviceInfo> devices = sCore.getDevices();
        if (devices != null) {
            return devices.size();
        }
        return 0;
    }

    public static List<DeviceInfo> getDevices() {
        assertInitialized("getDevices");
        MiotLog.i("getDevices");
        return sCore.getDevices();
    }

    public static List<DeviceInfo> getRoomDevices(String str, ArrayList<String> arrayList) {
        assertInitialized("getRoomDevices");
        MiotLog.i("getRoomDevices");
        return sCore.getRoomDevices(str, arrayList);
    }

    public static List<DeviceInfo> getDevicesFromCache() {
        assertInitialized("getDevicesFromCache");
        MiotLog.i("getDevicesFromCache");
        return DeviceInfoLocalCacheManager.getDevices();
    }

    public static List<MiotHome> getHomeList() {
        MiotLog.i("getHomeList");
        return sCore.getHomeList();
    }

    public static List<String> getShareHomeIdList() {
        MiotLog.i("getShareHomeIdList");
        return sCore.getShareHomeIdList();
    }

    public static List<String> getFreqDeviceIdList() {
        MiotLog.i("getFreqDeviceIdList");
        return sCore.getFreqDeviceIdList();
    }

    public static String getHostDeviceHomeId() {
        MiotLog.i("getHostDeviceHomeId");
        return sCore.getHostDeviceHomeId();
    }

    @Nullable
    public static Map<String, HomeCategory> getHomeCategories(List<DeviceInfo> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("GetHomeCategories: deviceSize: ");
        sb.append(list == null ? 0 : list.size());
        MiotLog.i(sb.toString());
        return DeviceCategoryManager.getHomeCategories(list, sCore.getModelInfos(), sCore.getDevicePropInfos());
    }

    @Nullable
    public static Map<String, HomeCategory> getHomeCategoriesFromCache(List<DeviceInfo> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("GetHomeCategoriesFromCache: deviceSize: ");
        sb.append(list == null ? 0 : list.size());
        MiotLog.i(sb.toString());
        MiotLog.i("Get home category from local cache!");
        return DeviceCategoryManager.getHomeCategories(list, DeviceInfoLocalCacheManager.getModelInfo(), DeviceInfoLocalCacheManager.getPropInfo());
    }

    public static String getUserId() {
        if (!sInitialized) {
            return null;
        }
        return sProvider.getUserId(sContext);
    }

    public static String getNickName() {
        if (!sInitialized) {
            return null;
        }
        return sProvider.getNickName(sContext);
    }

    public static void getSupportDoorbellPdids(DoorbellPdidsCallback doorbellPdidsCallback) {
        assertInitialized("getSupportDoorbellPdids");
        MiotLog.i("getSupportDoorbellPdids");
        sCore.getSupportDoorbellPdids(doorbellPdidsCallback);
    }

    public static void getMiotSensorData(MiotSensorCallback miotSensorCallback) {
        assertInitialized("getMiotSensorData");
        MiotLog.i("getMiotSensorData");
        sCore.getMiotSensorData(miotSensorCallback);
    }

    public static void setCameraStatus(ICameraStatus iCameraStatus) {
        statusCallBack = iCameraStatus;
    }

    public static Bundle getAuthToken(String str) {
        MiotLog.i("getAuthToken: " + str);
        Bundle bundle = new Bundle();
        if (!sInitialized) {
            bundle.putInt("error_code", -1);
            bundle.putString("error_msg", "account service not ready");
            return bundle;
        }
        String userId = sProvider.getUserId(sContext);
        if (TextUtils.isEmpty(userId)) {
            bundle.putInt("error_code", -2);
            bundle.putString("error_msg", "xiaomi account not found");
            return bundle;
        }
        String cUserId = sProvider.getCUserId(sContext);
        if (TextUtils.isEmpty(userId)) {
            bundle.putInt("error_code", -3);
            bundle.putString("error_msg", "can not get cUserId");
            return bundle;
        }
        ServiceTokenInfo serviceToken = sProvider.getServiceToken(sContext, str);
        if (serviceToken == null) {
            bundle.putInt("error_code", -4);
            bundle.putString("error_msg", "can not get serviceToken");
            return bundle;
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.userId = userId;
        accountInfo.cUserId = cUserId;
        accountInfo.serviceToken = serviceToken.serviceToken;
        accountInfo.security = serviceToken.security;
        bundle.putInt("error_code", 0);
        bundle.putParcelable("account_info", accountInfo);
        return bundle;
    }

    public static HostDeviceInfo getHostDeviceInfo() {
        MiotLog.i("getHostDeviceInfo");
        if (!sInitialized) {
            return null;
        }
        return sProvider.getHostDeviceInfo(sContext);
    }

    public static void onDevicesCountChanged(int i, int i2) {
        MiotLog.i("onDevicesCountChanged, lastCount=" + i + ", currentCount=" + i2);
        if (sInitialized) {
            sProvider.onDevicesCountChanged(i, i2);
        }
    }

    public static void displayImage(ImageView imageView, String str) {
        assertInitialized("displayImage");
        sProvider.displayImage(imageView, str);
    }

    public static boolean showDevicePage(Context context, String str, String str2) {
        assertInitialized("showDevicePage");
        MiotLog.d("show device 1: encryptDid=\"" + str + "\", type=\"" + str2 + "\"");
        if (TextUtils.isEmpty(str)) {
            MiotLog.e("encryptDid must not be null");
            return false;
        }
        DeviceInfo deviceByEncryptDid = sCore.getDeviceByEncryptDid(str);
        if (deviceByEncryptDid == null) {
            MiotLog.e("encryptDid (" + str + ") not found");
            return false;
        }
        showDevicePage(context, deviceByEncryptDid, null, true, null, str2);
        return true;
    }

    public static boolean showDevicePage(Context context, String str, String str2, ICameraStatus iCameraStatus) {
        assertInitialized("showDevicePage");
        MiotLog.d("show device 2: encryptDid=\"" + str + "\", type=\"" + str2 + "\"");
        if (TextUtils.isEmpty(str)) {
            MiotLog.e("encryptDid must not be null");
            return false;
        }
        DeviceInfo deviceByEncryptDid = sCore.getDeviceByEncryptDid(str);
        if (deviceByEncryptDid == null) {
            MiotLog.e("encryptDid (" + str + ") not found");
            return false;
        }
        showDevicePage(context, deviceByEncryptDid, null, true, iCameraStatus == null ? null : iCameraStatus.asBinder(), str2);
        return true;
    }

    public static boolean showDevicePageUnencrypt(Context context, String str, String str2, ICameraStatus iCameraStatus) {
        assertInitialized("showDevicePage");
        MiotLog.d("show device 3: did=\"" + str + "\", type=\"" + str2 + "\"");
        if (TextUtils.isEmpty(str)) {
            MiotLog.e("encryptDid must not be null");
            return false;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaomi.smarthome", "com.xiaomi.smarthome.camera.page.CameraP2pActivity"));
        intent.setPackage("com.xiaomi.smarthome");
        if (iCameraStatus != null) {
            intent.putExtra("status_call_back", new BinderParcel(iCameraStatus.asBinder()));
        }
        intent.putExtra("did", str);
        intent.setFlags(268468224);
        context.startActivity(intent);
        return true;
    }

    public static void showDevicePage(Context context, DeviceInfo deviceInfo, RectF rectF) {
        showDevicePage(context, deviceInfo, rectF, false, null, "card");
    }

    private static void showDevicePage(Context context, DeviceInfo deviceInfo, RectF rectF, boolean z, IBinder iBinder, String str) {
        assertInitialized("showDevicePage");
        String cardInfo = sCore.getCardInfo(deviceInfo.model);
        MiotLog.i("showDevicePage: type = " + str + "  && card = " + cardInfo);
        if ((!z || !TextUtils.isEmpty(str)) && (!deviceInfo.model.contains(UserAvatarUpdateActivity.CAMERA) || TextUtils.isEmpty(cardInfo))) {
            MiotLog.i("showDevicePage: goto  MIUI10CardActivity ");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.smarthome", "com.xiaomi.smarthome.miui10.MIUI10CardActivity"));
            intent.setPackage("com.xiaomi.smarthome");
            intent.putExtra("model", deviceInfo.model);
            intent.putExtra("did", deviceInfo.did);
            if (!TextUtils.isEmpty(deviceInfo.deviceName)) {
                intent.putExtra(ai.J, deviceInfo.deviceName);
            }
            if (!TextUtils.isEmpty(cardInfo)) {
                intent.putExtra("device_card", cardInfo);
            }
            if (rectF != null) {
                intent.putExtra(PlayerSimpleFragmentV2.VIEW_POSITION, rectF);
            }
            if (iBinder != null) {
                intent.putExtra("status_call_back", new BinderParcel(iBinder));
            } else {
                ICameraStatus iCameraStatus = statusCallBack;
                if (iCameraStatus != null) {
                    intent.putExtra("status_call_back", new BinderParcel(iCameraStatus.asBinder()));
                }
            }
            intent.putExtra("from_voice2", z);
            intent.setFlags(335544320);
            context.startActivity(intent);
            return;
        }
        MiotLog.i("showDevicePage: goto  CameraP2pActivity ");
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("com.xiaomi.smarthome", "com.xiaomi.smarthome.camera.page.CameraP2pActivity"));
        intent2.setPackage("com.xiaomi.smarthome");
        if (iBinder != null) {
            intent2.putExtra("status_call_back", new BinderParcel(iBinder));
        } else {
            ICameraStatus iCameraStatus2 = statusCallBack;
            if (iCameraStatus2 != null) {
                intent2.putExtra("status_call_back", new BinderParcel(iCameraStatus2.asBinder()));
            }
        }
        intent2.putExtra("did", deviceInfo.did);
        intent2.setFlags(268435456);
        context.startActivity(intent2);
    }

    public static void sendSmartHomeRequest(String str, String str2, String str3, SmartHomeRequestCallback smartHomeRequestCallback) {
        assertInitialized("sendSmartHomeRequest");
        MiotLog.i("sendSmartHomeRequest");
        sCore.sendSmartHomeRequest(str, str2, str3, smartHomeRequestCallback);
    }

    public static void deviceIsSetPinCode(String str, DevicesSetPincodeCallback devicesSetPincodeCallback) {
        assertInitialized("deviceIsSetPinCode");
        MiotLog.i("deviceIsSetPinCode");
        sCore.deviceIsSetPinCode(str, devicesSetPincodeCallback);
    }

    public static void enterDeviceListPage() {
        assertInitialized("enterDeviceListPage");
        MiotLog.i("enterDeviceListPage");
        sCore.enterDeviceListPage();
    }

    public static void exitDeviceListPage() {
        assertInitialized("exitDeviceListPage");
        MiotLog.i("exitDeviceListPage");
        sCore.exitDeviceListPage();
    }

    public static void logoutMiHome(LoginCallback loginCallback) {
        assertInitialized("logoutMiHome");
        MiotLog.i("logoutMiHome");
        sCore.logoutMiHome(loginCallback);
    }

    public static void setDeviceProperty(String str, String str2, String str3, String str4, ISendMsgCallback iSendMsgCallback) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            HashMap hashMap = new HashMap();
            hashMap.put("did", str);
            hashMap.put("propKey", str2);
            hashMap.put("propValue", str3);
            if (!TextUtils.isEmpty(str4)) {
                hashMap.put("valueType", str4);
            }
            sCore.sendMsg(MiotHelper.createSendMsg(MicoSupConstants.MsgAction.ACTION_SET_DEVICE_PROP, hashMap), iSendMsgCallback);
        } else if (iSendMsgCallback != null) {
            iSendMsgCallback.onResult("{\"code\":502,\"msg\":\"invalid params\"}");
        }
    }

    public static void setDeviceAction(String str, int i, int i2, ISendMsgCallback iSendMsgCallback) {
        if (!TextUtils.isEmpty(str) && i > 0 && i2 > 0) {
            HashMap hashMap = new HashMap();
            hashMap.put("did", str);
            hashMap.put("siid", i + "");
            hashMap.put("aiid", i2 + "");
            sCore.sendMsg(MiotHelper.createSendMsg(MicoSupConstants.MsgAction.ACTION_SET_DEVICE_ACTION, hashMap), iSendMsgCallback);
        } else if (iSendMsgCallback != null) {
            iSendMsgCallback.onResult("{\"code\":503,\"msg\":\"invalid params\"}");
        }
    }

    public static synchronized void registerMiotMsgCallback(IMiotMsgCallback iMiotMsgCallback) {
        synchronized (MiotManager.class) {
            MiotLog.i("Info: registerMiotMsgCb: " + iMiotMsgCallback);
            if (!(iMiotMsgCallback == null || iMiotMsgCallback.getAction() == 0)) {
                if (sMiotCallback == null) {
                    sMiotCallback = new ArrayList(4);
                }
                sMiotCallback.add(iMiotMsgCallback);
            }
        }
    }

    public static synchronized boolean containMsgCallback(IMiotMsgCallback iMiotMsgCallback) {
        synchronized (MiotManager.class) {
            if (!(sMiotCallback == null || sMiotCallback.isEmpty() || iMiotMsgCallback == null)) {
                return sMiotCallback.contains(iMiotMsgCallback);
            }
            return false;
        }
    }

    public static void registerDeviceInfoReadyCallback(IDeviceInfoReadyCallback iDeviceInfoReadyCallback) {
        if (iDeviceInfoReadyCallback != null) {
            if (sDeviceInfoReadyCallback == null) {
                synchronized (LOCK_ALL_INFO_READY_CALLBACK) {
                    if (sDeviceInfoReadyCallback == null) {
                        sDeviceInfoReadyCallback = new ArrayList();
                    }
                }
            }
            if (isDeviceInfoReady()) {
                iDeviceInfoReadyCallback.onDeviceInfoReady();
                return;
            }
            synchronized (LOCK_ALL_INFO_READY_CALLBACK) {
                sDeviceInfoReadyCallback.add(new WeakReference<>(iDeviceInfoReadyCallback));
            }
        }
    }

    private static void onAllInfoReadyCallback() {
        StringBuilder sb = new StringBuilder();
        sb.append("Info: on all info ready; readyCallback size: ");
        sb.append(sDeviceInfoReadyCallback == null ? 0 : sDeviceInfoReadyCallback.size());
        MiotLog.i(sb.toString());
        if (sSmartHomeInfoReady) {
            DeviceInfoLocalCacheManager.releaseCache();
            synchronized (LOCK_ALL_INFO_READY_CALLBACK) {
                if (sDeviceInfoReadyCallback != null && !sDeviceInfoReadyCallback.isEmpty()) {
                    for (WeakReference<IDeviceInfoReadyCallback> weakReference : sDeviceInfoReadyCallback) {
                        IDeviceInfoReadyCallback iDeviceInfoReadyCallback = weakReference.get();
                        if (iDeviceInfoReadyCallback != null) {
                            iDeviceInfoReadyCallback.onDeviceInfoReady();
                        }
                    }
                }
            }
        }
    }

    public static synchronized boolean unregisterMiotMsgCallback(IMiotMsgCallback iMiotMsgCallback) {
        synchronized (MiotManager.class) {
            if (iMiotMsgCallback != null) {
                if (!(iMiotMsgCallback.getAction() == 0 || sMiotCallback == null)) {
                    return sMiotCallback.remove(iMiotMsgCallback);
                }
            }
            return true;
        }
    }

    /* loaded from: classes2.dex */
    public static class MiotMsgCallback extends IMsgCallback.Stub {
        @Override // com.xiaomi.miot.service.IMsgCallback
        public int getAction() throws RemoteException {
            return 15;
        }

        private MiotMsgCallback() {
        }

        @Override // com.xiaomi.miot.service.IMsgCallback
        public void onMsgCallback(String str) throws RemoteException {
            StringBuilder sb = new StringBuilder();
            sb.append("Receive msg callback size: ");
            int i = 0;
            sb.append(TextUtils.isEmpty(str) ? 0 : str.length());
            sb.append(", callback Size: ");
            if (!MiotManager.sMiotCallback.isEmpty()) {
                i = MiotManager.sMiotCallback.size();
            }
            sb.append(i);
            MiotLog.i(sb.toString());
            if (MiotManager.sMiotCallback != null && !MiotManager.sMiotCallback.isEmpty()) {
                try {
                    final JSONObject jSONObject = new JSONObject(str);
                    final int optInt = jSONObject.optInt("action");
                    MiotLog.i("receive action: " + optInt);
                    if (optInt != 0) {
                        MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.MiotManager.MiotMsgCallback.1
                            @Override // java.lang.Runnable
                            public void run() {
                                synchronized (MiotManager.class) {
                                    for (IMiotMsgCallback iMiotMsgCallback : MiotManager.sMiotCallback) {
                                        if (iMiotMsgCallback.getAction() == optInt) {
                                            iMiotMsgCallback.onMsgCallback(jSONObject.optJSONObject("params"));
                                        }
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    MiotLog.e("Error: receive miot msg error: " + e.getMessage());
                }
            }
        }

        @Override // com.xiaomi.miot.service.IMsgCallback
        public String getTag() throws RemoteException {
            return MiotManager.PACKAGE_NAME;
        }
    }

    public static IMsgCallback newMsgCallback() {
        return new MiotMsgCallback();
    }

    public static boolean isDeviceInfoReady() {
        boolean z;
        if (sAllInfoReady) {
            return true;
        }
        synchronized (MiotManager.class) {
            z = sAllInfoReady;
        }
        return z;
    }

    public static void saveDeviceListToCache() {
        MiotUtil.execute(new Runnable() { // from class: com.xiaomi.miot.support.MiotManager.4
            @Override // java.lang.Runnable
            public void run() {
                MiotLog.i("Save device info to local cache!");
                DeviceInfoLocalCacheManager.saveDeviceInfoList();
            }
        });
    }

    public static void registerServiceStatusListener(IMiotServiceStatusListener iMiotServiceStatusListener) {
        if (iMiotServiceStatusListener != null) {
            synchronized (LOCK_SERVICE_STATUS_CALLBACK) {
                sServiceStatusListeners.add(new WeakReference<>(iMiotServiceStatusListener));
            }
        }
    }

    public static void unregisterServiceStatusListener(IMiotServiceStatusListener iMiotServiceStatusListener) {
        if (iMiotServiceStatusListener != null) {
            synchronized (LOCK_SERVICE_STATUS_CALLBACK) {
                if (!sServiceStatusListeners.isEmpty()) {
                    WeakReference<IMiotServiceStatusListener> weakReference = null;
                    Iterator<WeakReference<IMiotServiceStatusListener>> it = sServiceStatusListeners.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            WeakReference<IMiotServiceStatusListener> next = it.next();
                            IMiotServiceStatusListener iMiotServiceStatusListener2 = next.get();
                            if (iMiotServiceStatusListener2 != null && iMiotServiceStatusListener2 == iMiotServiceStatusListener) {
                                weakReference = next;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (weakReference != null) {
                        sServiceStatusListeners.remove(weakReference);
                        weakReference.clear();
                    }
                }
            }
        }
    }

    public static void onMiotServiceStatusChanged(boolean z) {
        if (!z) {
            resetSmartHomeInfoReady();
        }
        synchronized (LOCK_SERVICE_STATUS_CALLBACK) {
            if (!sServiceStatusListeners.isEmpty()) {
                for (WeakReference<IMiotServiceStatusListener> weakReference : sServiceStatusListeners) {
                    IMiotServiceStatusListener iMiotServiceStatusListener = weakReference.get();
                    if (iMiotServiceStatusListener != null) {
                        if (z) {
                            iMiotServiceStatusListener.onServiceConnect();
                        } else {
                            iMiotServiceStatusListener.onServiceDisconnect();
                        }
                    }
                }
            }
        }
    }

    public static void bindMiotService() {
        MiotDeviceCore miotDeviceCore = sCore;
        if (miotDeviceCore != null) {
            miotDeviceCore.bindService();
        }
    }

    public static void onNetworkAvailable() {
        DeviceCategoryManager.refreshMappingFromServer(true);
        MiotDeviceCore miotDeviceCore = sCore;
        if (miotDeviceCore != null) {
            miotDeviceCore.refreshDevices();
        }
    }
}
