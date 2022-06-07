package com.xiaomi.miot.support.core;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.service.IMiuiService;
import com.xiaomi.miot.service.IMsgCallback;
import com.xiaomi.miot.support.DevicesSetPincodeCallback;
import com.xiaomi.miot.support.DoorbellPdidsCallback;
import com.xiaomi.miot.support.HostDeviceInfo;
import com.xiaomi.miot.support.LoginCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.MiotSensorCallback;
import com.xiaomi.miot.support.SmartHomeRequestCallback;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.scene.GetSceneCallback;
import com.xiaomi.miot.support.scene.RunSceneCallback;
import com.xiaomi.miot.support.scene.SceneUtils;
import com.xiaomi.miot.support.util.MiotUtil;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MiotDeviceCore {
    public static final String ACTION_DEVICES_FAILED = "action_devices_failed";
    public static final String ACTION_DEVICES_REFRESHED = "action_devices_refreshed";
    public static final String ACTION_EVENTS_UPDATED = "action_events_updated";
    private static final String DEVICE_INFOS = "device_infos";
    private static final String DEVICE_INFO_JSON = "device_info_json";
    private static final String ERROR_CODE = "error_code";
    private static final String FREQ_DEVICE_ID = "freq_device_id";
    private static final String HOME_INFOS = "home_infos";
    public static final String KEY_DEVICE_SET_PINCODE = "device_set_pincode";
    private static final int MAX_REFRESH_RETRY_TIME = 3;
    private static final String MIHOME_PACKAGE_NAME = "com.xiaomi.smarthome";
    private static final String MIHOME_SERVICE = "com.xiaomi.smarthome.service.MiuiService";
    private static final String MIHOME_SERVICE_CONNECTED = "service_connected";
    private static final String MI_WIFI_SPEAKER_MODEL_PREFIX = "xiaomi.wifispeaker.";
    private static final String ONEMORE_WIFI_SPEAKER_MODEL_PREFIX = "onemore.wifispeaker.";
    private static final int REFRESH_DEVICE_TIMEOUT = 15000;
    private static final String SUPPORT_CAMERAS = "support_cameras";
    private static ExecutorService THREAD_POOL_EXECUTOR = null;
    private static final String USER_ID = "current_uid";
    @SuppressLint({"StaticFieldLeak"})
    private static MiotDeviceCore sInstance;
    private final Context mContext;
    private volatile IMiuiService mMiHomeService;
    private IMsgCallback mMsgCallback;
    private volatile boolean mBound = false;
    private final HandlerThread handlerThread = new HandlerThread("MiotDeviceCore");
    private List<DeviceInfo> mDevices = new ArrayList();
    private Map<String, ModelInfo> mModelInfos = new HashMap();
    private Map<String, DeviceItemInfo> mDevicePropInfos = new HashMap();
    private int mOriginDevicesCount = 0;
    private HashMap<String, String> mCardInfo = new HashMap<>();
    private List<MiotDeviceEvent> mEvents = new ArrayList();
    private List<MiotHome> mHomeList = new ArrayList();
    private List<String> mSupportCameraDidList = new ArrayList();
    private List<String> mShareHomeIdList = new ArrayList();
    private List<String> mFreqDeviceIdList = new ArrayList();
    private HostDeviceInfo mHostDeviceInfo = null;
    private volatile boolean mIsRefreshing = false;
    private int mRetainRetryTime = 3;
    private int mRunnableId = 0;
    private Map<Integer, RefreshTimeoutRunnable> mRefreshTimeoutMap = new ConcurrentHashMap();
    private final ServiceConnection mConnection = new ServiceConnection() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            MiotLog.i("Info: smart home service connected");
            MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.1.1
                @Override // java.lang.Runnable
                public void run() {
                    MiotDeviceCore.this.handleConnected(iBinder);
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            MiotLog.w("mihome service disconnected");
            MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.1.2
                @Override // java.lang.Runnable
                public void run() {
                    MiotDeviceCore.this.handleDisconnected();
                }
            });
        }
    };
    private Handler mHandler = new Handler(this.handlerThread.getLooper());

    /* loaded from: classes3.dex */
    public interface DeviceEventCallback {
        void onFailed(String str);

        void onSuccess(List<MiotDeviceEvent> list);
    }

    public void handleDisconnected() {
        this.mMiHomeService = null;
        this.mBound = false;
        this.mIsRefreshing = false;
        MiotManager.onMiotServiceStatusChanged(false);
    }

    public void handleConnected(IBinder iBinder) {
        this.mMiHomeService = IMiuiService.Stub.asInterface(iBinder);
        try {
            executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.2
                @Override // java.lang.Runnable
                public void run() {
                    MiotLog.i("refresh   the device info! onServiceConnected");
                    MiotDeviceCore.this.refreshDevices();
                }
            });
            MiotLog.i("Info: support lib register msg callback");
            this.mMsgCallback = MiotManager.newMsgCallback();
            this.mMiHomeService.registerMsgCallback(this.mMsgCallback);
        } catch (RemoteException e) {
            MiotLog.i("Error: register msg callback error, " + e.getMessage());
        }
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(MIHOME_SERVICE_CONNECTED));
        MiotManager.onMiotServiceStatusChanged(true);
    }

    public static MiotDeviceCore with(Context context) {
        if (sInstance == null) {
            sInstance = new MiotDeviceCore(context);
        }
        return sInstance;
    }

    public void destroy() {
        clearDevices();
        unbindService();
    }

    public void clearDevices() {
        MiotManager.onDevicesCountChanged(this.mDevices.size(), 0);
        this.mDevices.clear();
        this.mCardInfo.clear();
        notifyDevicesRefreshed(false);
        this.mEvents.clear();
        notifyEventsUpdated();
    }

    private MiotDeviceCore(Context context) {
        this.mContext = context.getApplicationContext();
        this.handlerThread.start();
        bindService();
    }

    public void bindService() {
        MiotLog.i("Info: try to bind smart home service, mBound: " + this.mBound);
        if (!this.mBound) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.smarthome", MIHOME_SERVICE));
            this.mBound = this.mContext.bindService(intent, this.mConnection, 1);
            return;
        }
        MiotLog.i("Info: try to bind smart home service failed, has bound!");
    }

    private void unbindService() {
        MiotLog.i("Info: unbind smart home service");
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.3
            @Override // java.lang.Runnable
            public void run() {
                if (MiotDeviceCore.this.mBound) {
                    if (!(MiotDeviceCore.this.mMiHomeService == null || MiotDeviceCore.this.mMsgCallback == null)) {
                        try {
                            MiotDeviceCore.this.mMiHomeService.unRegisterMsgCallback(MiotDeviceCore.this.mMsgCallback.getTag());
                            MiotDeviceCore.this.mMsgCallback = null;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    MiotDeviceCore.this.mContext.unbindService(MiotDeviceCore.this.mConnection);
                    MiotDeviceCore.this.mBound = false;
                }
            }
        });
    }

    public List<DeviceInfo> getDevices() {
        return this.mDevices;
    }

    public Map<String, ModelInfo> getModelInfos() {
        return this.mModelInfos;
    }

    public void setModelInfos(Map<String, ModelInfo> map) {
        this.mModelInfos = map;
    }

    public Map<String, DeviceItemInfo> getDevicePropInfos() {
        return this.mDevicePropInfos;
    }

    public void setDevicePropInfos(Map<String, DeviceItemInfo> map) {
        this.mDevicePropInfos = map;
    }

    public List<MiotHome> getHomeList() {
        return this.mHomeList;
    }

    public List<String> getShareHomeIdList() {
        return this.mShareHomeIdList;
    }

    public List<String> getFreqDeviceIdList() {
        return this.mFreqDeviceIdList;
    }

    public List<String> getSupportCameraDidList() {
        return this.mSupportCameraDidList;
    }

    public int getOriginDevicesCount() {
        return this.mOriginDevicesCount;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.miot.DeviceInfo getDeviceByEncryptDid(@androidx.annotation.NonNull java.lang.String r5) {
        /*
            r4 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.util.List<com.xiaomi.miot.DeviceInfo> r0 = r4.mDevices
            java.util.Iterator r0 = r0.iterator()
        L_0x000e:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x002b
            java.lang.Object r2 = r0.next()
            com.xiaomi.miot.DeviceInfo r2 = (com.xiaomi.miot.DeviceInfo) r2
            java.lang.String r3 = r2.encryptDid
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x002a
            java.lang.String r3 = r2.did
            boolean r3 = android.text.TextUtils.equals(r5, r3)
            if (r3 == 0) goto L_0x000e
        L_0x002a:
            return r2
        L_0x002b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miot.support.core.MiotDeviceCore.getDeviceByEncryptDid(java.lang.String):com.xiaomi.miot.DeviceInfo");
    }

    public DeviceInfo getDeviceDid(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (DeviceInfo deviceInfo : this.mDevices) {
            if (str.equals(deviceInfo.did)) {
                return deviceInfo;
            }
        }
        return null;
    }

    public String getCardInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mCardInfo.get(str);
    }

    public String getHostDeviceHomeId() {
        HostDeviceInfo hostDeviceInfo = this.mHostDeviceInfo;
        if (hostDeviceInfo != null) {
            return hostDeviceInfo.homeId;
        }
        return null;
    }

    public String getHostDeviceRoomId() {
        HostDeviceInfo hostDeviceInfo = this.mHostDeviceInfo;
        if (hostDeviceInfo != null) {
            return hostDeviceInfo.roomId;
        }
        return null;
    }

    public String getRoomInfoByDid(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        for (DeviceInfo deviceInfo : this.mDevices) {
            if (str.equals(deviceInfo.did)) {
                return deviceInfo.roomInfo;
            }
        }
        return "";
    }

    public String getRoomInfoByRoomId(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        for (DeviceInfo deviceInfo : this.mDevices) {
            if (str.equals(deviceInfo.roomId)) {
                return deviceInfo.roomInfo;
            }
        }
        return "";
    }

    public List<MiotDeviceEvent> getEvents() {
        return this.mEvents;
    }

    public void updateHostDeviceHomeId(@NonNull String str) {
        if (this.mHostDeviceInfo == null) {
            this.mHostDeviceInfo = new HostDeviceInfo();
        }
        this.mHostDeviceInfo.homeId = str;
        updateDeviceEvents(str, null);
    }

    public void updateHostDeviceId(String str) {
        if (this.mHostDeviceInfo == null) {
            this.mHostDeviceInfo = new HostDeviceInfo();
        }
        this.mHostDeviceInfo.did = str;
    }

    public void updateHostDeviceRoomId(@NonNull String str) {
        if (this.mHostDeviceInfo == null) {
            this.mHostDeviceInfo = new HostDeviceInfo();
        }
        this.mHostDeviceInfo.roomId = str;
        notifyEventsUpdated();
    }

    private void executeTaskInThreadPool(Runnable runnable) {
        ExecutorService externalExecutorService = MiotManager.getExternalExecutorService();
        if (externalExecutorService == null || externalExecutorService.isShutdown()) {
            if (THREAD_POOL_EXECUTOR == null) {
                THREAD_POOL_EXECUTOR = Executors.newCachedThreadPool();
            }
            THREAD_POOL_EXECUTOR.execute(runnable);
            return;
        }
        try {
            externalExecutorService.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshDevices() {
        refreshDevicesInternal(false);
    }

    public void refreshDevicesInternal(boolean z) {
        if (this.mMiHomeService == null) {
            MiotLog.e("Info: mMiHomeService is null ,smart home service not connected");
        } else if (!MiotManager.isLogin()) {
            MiotLog.e("Info: refresh device cancel, not login");
        } else if (this.mIsRefreshing) {
            MiotLog.e("Info: refresh device cancel, in refreshing");
        } else {
            this.mIsRefreshing = true;
            if (!z) {
                this.mRetainRetryTime = 3;
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.4
                @Override // java.lang.Runnable
                public void run() {
                    if (MiotDeviceCore.this.mIsRefreshing) {
                        MiotLog.i("refresh timeout, restart refresh task");
                        MiotDeviceCore.this.notifyDevicesFailed(true);
                    }
                }
            }, 30000L);
            HostDeviceInfo hostDeviceInfo = this.mHostDeviceInfo;
            if (hostDeviceInfo != null) {
                checkLogin(hostDeviceInfo.did);
            } else {
                executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.5
                    @Override // java.lang.Runnable
                    public void run() {
                        HostDeviceInfo hostDeviceInfo2 = MiotManager.getHostDeviceInfo();
                        if (MiotDeviceCore.this.mHostDeviceInfo != null) {
                            MiotDeviceCore miotDeviceCore = MiotDeviceCore.this;
                            miotDeviceCore.checkLogin(miotDeviceCore.mHostDeviceInfo.did);
                        } else if (hostDeviceInfo2 != null) {
                            MiotDeviceCore.this.mHostDeviceInfo = hostDeviceInfo2;
                            MiotDeviceCore miotDeviceCore2 = MiotDeviceCore.this;
                            miotDeviceCore2.checkLogin(miotDeviceCore2.mHostDeviceInfo.did);
                        } else {
                            MiotLog.e("host device info return null???");
                        }
                    }
                });
            }
        }
    }

    public void getSupportDoorbellPdids(final DoorbellPdidsCallback doorbellPdidsCallback) {
        getSupportDoorbellPdids(new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.6
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                if (doorbellPdidsCallback != null) {
                    ArrayList<Integer> arrayList = null;
                    if (bundle != null) {
                        arrayList = bundle.getIntegerArrayList("result");
                    }
                    if (arrayList != null) {
                        doorbellPdidsCallback.onSuccess(arrayList);
                    } else {
                        doorbellPdidsCallback.onFailed("get support pdids from bundle failed");
                    }
                }
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) throws RemoteException {
                if (doorbellPdidsCallback != null) {
                    String str = "";
                    if (bundle != null) {
                        str = bundle.getString("error_info", "");
                    }
                    doorbellPdidsCallback.onFailed(str);
                }
            }
        });
    }

    public void sendSmartHomeRequest(String str, String str2, String str3, final SmartHomeRequestCallback smartHomeRequestCallback) {
        callSmartHomeApiWithMethod(str, str2, str3, new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.7
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                if (smartHomeRequestCallback != null) {
                    smartHomeRequestCallback.onSuccess(bundle.getString("result"));
                }
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) throws RemoteException {
                if (smartHomeRequestCallback != null) {
                    int i = bundle.getInt("errorCode");
                    String str4 = "";
                    if (bundle.containsKey(SceneUtils.ERROR_DETAIL)) {
                        str4 = bundle.getString(SceneUtils.ERROR_DETAIL);
                    }
                    smartHomeRequestCallback.onFailed(i, str4);
                }
            }
        });
    }

    public void getSceneByHomeId(String str, GetSceneCallback getSceneCallback) {
        getSceneByHomeId(str, new SceneUtils.GetSceneICallback(getSceneCallback));
    }

    private void getSceneByHomeId(final String str, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.getCommonUsedScene(str, iCallback);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                ICallback iCallback2 = iCallback;
                if (iCallback2 != null) {
                    try {
                        iCallback2.onFailure(new Bundle());
                    } catch (RemoteException unused) {
                    }
                }
            }
        }.start();
    }

    public void runScene(long j, int i, String str, RunSceneCallback runSceneCallback) {
        runScene(j, i, new SceneUtils.RunSceneICallback(runSceneCallback, str));
    }

    private void runScene(final long j, final int i, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.runSpecScene(j, i, iCallback);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                ICallback iCallback2 = iCallback;
                if (iCallback2 != null) {
                    try {
                        iCallback2.onFailure(new Bundle());
                    } catch (RemoteException unused) {
                    }
                }
            }
        }.start();
    }

    public void checkLogin(@Nullable final String str) {
        isLogin(MiotManager.getUserId(), new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.10
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(final Bundle bundle) throws RemoteException {
                MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        String string = bundle.getString(MiotDeviceCore.USER_ID);
                        MiotLog.i("mihome logon " + string);
                        MiotDeviceCore.this.refreshDevices((String) Objects.requireNonNull(string), str);
                    }
                });
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) throws RemoteException {
                MiotLog.e("mihome login failed???");
                MiotDeviceCore.this.notifyDevicesFailed(false);
            }
        });
    }

    public void refreshDevices(@NonNull final String str, String str2) {
        MiotLog.i("Info: refresh devices; userId: " + str + ", homeId: " + str2);
        String userId = MiotManager.getUserId();
        if (!str.equals(userId)) {
            MiotLog.e("mihome userId (" + str + ") not matched with current userId (" + userId + ")");
            notifyDevicesFailed(false);
            return;
        }
        getDeviceListByHomeId(str2, new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.11
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                MiotDeviceCore.this.processDevicesRefreshed(str, bundle);
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) throws RemoteException {
                int i = bundle.getInt(MiotDeviceCore.ERROR_CODE);
                MiotLog.e("get devices failed with code (" + i + ") from mihome");
                MiotDeviceCore.this.notifyDevicesFailed(true);
            }
        });
    }

    public void processDevicesRefreshed(@NonNull String str, final Bundle bundle) {
        String userId = MiotManager.getUserId();
        if (!str.equals(userId)) {
            MiotLog.e("mihome userId (" + str + ") not matched with current userId (" + userId + ")");
            notifyDevicesFailed(false);
            return;
        }
        executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.12
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                int i = 1;
                try {
                    bundle.setClassLoader(DeviceInfo.class.getClassLoader());
                    final ArrayList parcelableArrayList = bundle.getParcelableArrayList(MiotDeviceCore.DEVICE_INFOS);
                    int i2 = 0;
                    MiotDeviceCore.this.mOriginDevicesCount = parcelableArrayList == null ? 0 : parcelableArrayList.size();
                    final String string = bundle.getString("home_id");
                    String string2 = bundle.getString(OneTrack.Param.ROOM_ID);
                    String string3 = bundle.getString(MiotDeviceCore.HOME_INFOS);
                    String string4 = bundle.getString(MiotDeviceCore.SUPPORT_CAMERAS);
                    final ArrayList<String> stringArrayList = bundle.getStringArrayList(MiotDeviceCore.FREQ_DEVICE_ID);
                    MiotLog.d("freqDeviceIdList - " + stringArrayList);
                    MiotLog.d("Receive homeId - " + string);
                    MiotLog.d("Receive roomId - " + string2);
                    if (string != null) {
                        MiotDeviceCore.this.mHostDeviceInfo.homeId = string;
                    }
                    if (string2 != null) {
                        MiotDeviceCore.this.mHostDeviceInfo.roomId = string2;
                    }
                    String string5 = bundle.getString("device_card_data");
                    final HashMap hashMap = new HashMap();
                    if (string5 != null) {
                        try {
                            JSONObject jSONObject = new JSONObject(string5);
                            Iterator keys = jSONObject.keys();
                            while (keys.hasNext()) {
                                String str2 = (String) keys.next();
                                hashMap.put(str2, jSONObject.get(str2).toString());
                            }
                        } catch (JSONException e) {
                            MiotLog.e("parse card info failed: " + e.getMessage());
                        }
                    }
                    if (parcelableArrayList != null) {
                        int size = parcelableArrayList.size();
                        Iterator it = parcelableArrayList.iterator();
                        while (it.hasNext()) {
                            DeviceInfo deviceInfo = (DeviceInfo) it.next();
                            if (deviceInfo.model.startsWith(MiotDeviceCore.MI_WIFI_SPEAKER_MODEL_PREFIX) || deviceInfo.model.startsWith(MiotDeviceCore.ONEMORE_WIFI_SPEAKER_MODEL_PREFIX)) {
                                it.remove();
                            }
                        }
                        MiotLog.d("get (" + size + ") devices from mihome, including (" + (size - parcelableArrayList.size()) + ") xiaomi wifi speakers");
                    } else {
                        MiotLog.w("get null devices from mihome");
                    }
                    if (parcelableArrayList != null && !TextUtils.isEmpty(MiotDeviceCore.this.mHostDeviceInfo.roomId)) {
                        ArrayList arrayList = new ArrayList();
                        Iterator it2 = parcelableArrayList.iterator();
                        while (it2.hasNext()) {
                            DeviceInfo deviceInfo2 = (DeviceInfo) it2.next();
                            if (TextUtils.equals(deviceInfo2.roomId, MiotDeviceCore.this.mHostDeviceInfo.roomId)) {
                                arrayList.add(deviceInfo2);
                                it2.remove();
                            }
                        }
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            parcelableArrayList.add(i3, arrayList.get(i3));
                        }
                    }
                    final ArrayList arrayList2 = new ArrayList();
                    final ArrayList arrayList3 = new ArrayList();
                    try {
                        if (!TextUtils.isEmpty(string3)) {
                            JSONArray jSONArray = new JSONArray(string3);
                            int i4 = 0;
                            while (i4 < jSONArray.length()) {
                                JSONObject optJSONObject = jSONArray.optJSONObject(i4);
                                if (optJSONObject != null) {
                                    MiotHome miotHome = new MiotHome();
                                    miotHome.homeId = optJSONObject.optString("id");
                                    miotHome.homeName = optJSONObject.optString("name");
                                    if (TextUtils.isEmpty(miotHome.homeName)) {
                                        if (!TextUtils.isEmpty(MiotManager.getNickName())) {
                                            miotHome.homeName = MiotManager.getNickName() + "的家";
                                        } else {
                                            miotHome.homeName = "我的家庭";
                                        }
                                    }
                                    int optInt = optJSONObject.optInt("share_flag", i2);
                                    miotHome.shareHomeFlag = optInt;
                                    if (i == optInt) {
                                        arrayList3.add(miotHome.homeId);
                                    }
                                    JSONArray optJSONArray = optJSONObject.optJSONArray("dids");
                                    if (optJSONArray != null && optJSONArray.length() > 0) {
                                        for (int i5 = i2; i5 < optJSONArray.length(); i5++) {
                                            miotHome.homeDeviceList.add(optJSONArray.getString(i5));
                                        }
                                    }
                                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("rooms");
                                    if (optJSONArray2 == null || optJSONArray2.length() <= 0) {
                                        jSONArray = jSONArray;
                                    } else {
                                        for (int i6 = 0; i6 < optJSONArray2.length(); i6++) {
                                            JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i6);
                                            if (optJSONObject2 != null) {
                                                MiotRoom miotRoom = new MiotRoom();
                                                miotRoom.roomId = optJSONObject2.optString("id");
                                                miotRoom.roomName = optJSONObject2.optString("name");
                                                JSONArray optJSONArray3 = optJSONObject2.optJSONArray("dids");
                                                if (optJSONArray3 == null || optJSONArray3.length() <= 0) {
                                                    jSONArray = jSONArray;
                                                    optJSONArray2 = optJSONArray2;
                                                    z = false;
                                                } else {
                                                    jSONArray = jSONArray;
                                                    int i7 = 0;
                                                    boolean z2 = false;
                                                    while (i7 < optJSONArray3.length()) {
                                                        String string6 = optJSONArray3.getString(i7);
                                                        miotRoom.roomDeviceList.add(string6);
                                                        if (string6.equals(MiotDeviceCore.this.mHostDeviceInfo.did)) {
                                                            z2 = true;
                                                        }
                                                        i7++;
                                                        optJSONArray2 = optJSONArray2;
                                                    }
                                                    optJSONArray2 = optJSONArray2;
                                                    z = z2;
                                                }
                                                miotRoom.isCurrentSpeakerRoom = z;
                                                miotHome.roomList.add(miotRoom);
                                            } else {
                                                jSONArray = jSONArray;
                                                optJSONArray2 = optJSONArray2;
                                            }
                                        }
                                        jSONArray = jSONArray;
                                    }
                                    arrayList2.add(miotHome);
                                } else {
                                    jSONArray = jSONArray;
                                }
                                i4++;
                                i2 = 0;
                                i = 1;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    final ArrayList arrayList4 = new ArrayList();
                    try {
                        if (!TextUtils.isEmpty(string4)) {
                            JSONArray jSONArray2 = new JSONArray(string4);
                            for (int i8 = 0; i8 < jSONArray2.length(); i8++) {
                                arrayList4.add(jSONArray2.optString(i8));
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            int size2 = MiotDeviceCore.this.mDevices.size();
                            MiotDeviceCore.this.mDevices = parcelableArrayList;
                            if (MiotDeviceCore.this.mDevices == null) {
                                MiotDeviceCore.this.mDevices = new ArrayList();
                            }
                            MiotDeviceCore.this.mHomeList = arrayList2;
                            MiotDeviceCore.this.mShareHomeIdList = arrayList3;
                            MiotDeviceCore.this.mSupportCameraDidList = arrayList4;
                            MiotDeviceCore.this.mFreqDeviceIdList = stringArrayList;
                            if (MiotDeviceCore.this.mHostDeviceInfo.homeId != null) {
                                MiotDeviceCore.this.updateDeviceEvents(MiotDeviceCore.this.mHostDeviceInfo.homeId, null);
                            } else if (string != null) {
                                MiotLog.d("start refresh event info from homeid " + string);
                                MiotDeviceCore.this.updateDeviceEvents(string, null);
                            }
                            int size3 = MiotDeviceCore.this.mDevices.size();
                            if (size2 != size3) {
                                MiotManager.onDevicesCountChanged(size2, size3);
                            }
                            MiotDeviceCore.this.mCardInfo = hashMap;
                            MiotDeviceCore.this.notifyDevicesRefreshed(true);
                            MiotDeviceCore.this.mIsRefreshing = false;
                        }
                    });
                } catch (Exception e4) {
                    MiotLog.e("parse refreshed device failed: " + e4.getMessage());
                    MiotDeviceCore.this.notifyDevicesFailed(true);
                }
            }
        });
    }

    public void updateDeviceEvents(@NonNull String str, final DeviceEventCallback deviceEventCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("home_id", Long.valueOf(str));
            jSONObject.put("limit", 50);
            if (this.mHostDeviceInfo != null && !TextUtils.isEmpty(this.mHostDeviceInfo.model)) {
                jSONObject.put("model", this.mHostDeviceInfo.model);
            }
            callSmartHomeApi("/v2/home/get_device_description", jSONObject.toString(), new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.13
                @Override // com.xiaomi.miot.service.ICallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    MiotDeviceCore.this.processDeviceEvents(bundle, deviceEventCallback);
                }

                @Override // com.xiaomi.miot.service.ICallback
                public void onFailure(Bundle bundle) throws RemoteException {
                    int i = bundle.getInt("errorCode");
                    String str2 = "";
                    if (bundle.containsKey(SceneUtils.ERROR_DETAIL)) {
                        str2 = bundle.getString(SceneUtils.ERROR_DETAIL);
                    }
                    MiotLog.e("get device events failed with code (" + i + ") and msg (" + str2 + ") from mihome");
                    DeviceEventCallback deviceEventCallback2 = deviceEventCallback;
                    if (deviceEventCallback2 != null) {
                        deviceEventCallback2.onFailed("get device events failed with code (" + i + ") and msg (" + str2 + ") from mihome");
                    }
                }
            });
        } catch (JSONException e) {
            MiotLog.e("build device events's query failed: " + e.getMessage());
        }
    }

    public void processDeviceEvents(final Bundle bundle, final DeviceEventCallback deviceEventCallback) {
        executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final ArrayList arrayList = new ArrayList();
                    JSONObject jSONObject = new JSONObject(bundle.getString("result"));
                    MiotLog.e("receive miot device event: " + jSONObject.toString());
                    JSONArray optJSONArray = jSONObject.optJSONArray("description_list");
                    if (optJSONArray != null) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                            String optString = jSONObject2.optString("did");
                            String optString2 = jSONObject2.optString(OneTrack.Param.ROOM_ID);
                            JSONArray optJSONArray2 = jSONObject2.optJSONArray("details");
                            if (!(optString == null || optString2 == null || optJSONArray2 == null)) {
                                ArrayList arrayList2 = new ArrayList();
                                for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                    JSONObject jSONObject3 = optJSONArray2.getJSONObject(i2);
                                    String optString3 = jSONObject3.optString(b.p);
                                    String optString4 = jSONObject3.optString("extra");
                                    if (optString4 != null) {
                                        String optString5 = new JSONObject(optString4).optString("attr_type");
                                        if ("temp".equals(optString5)) {
                                            arrayList2.add(new MiotDeviceEvent.Attr(MiotDeviceEvent.Type.TEMPERATURE, optString3));
                                        } else if ("hum".equals(optString5)) {
                                            arrayList2.add(new MiotDeviceEvent.Attr(MiotDeviceEvent.Type.HUMIDITY, optString3));
                                        } else if ("pm25".equals(optString5)) {
                                            arrayList2.add(new MiotDeviceEvent.Attr(MiotDeviceEvent.Type.PM25, optString3));
                                        }
                                    }
                                }
                                if (!arrayList2.isEmpty()) {
                                    for (DeviceInfo deviceInfo : MiotDeviceCore.this.mDevices) {
                                        if (deviceInfo.did.equals(optString) && deviceInfo.isOnline) {
                                            arrayList.add(new MiotDeviceEvent(optString, optString2, arrayList2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.14.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MiotDeviceCore.this.mEvents = arrayList;
                            MiotDeviceCore.this.notifyEventsUpdated();
                            if (deviceEventCallback != null) {
                                deviceEventCallback.onSuccess(arrayList);
                            }
                            MiotDeviceCore.this.clearRefreshTimeoutMap(true, arrayList, null);
                        }
                    });
                } catch (JSONException e) {
                    MiotLog.e("parse card info failed: " + e.getMessage());
                    DeviceEventCallback deviceEventCallback2 = deviceEventCallback;
                    if (deviceEventCallback2 != null) {
                        deviceEventCallback2.onFailed("parse card info failed");
                    }
                    MiotDeviceCore.this.clearRefreshTimeoutMap(false, null, "parse card info failed");
                }
            }
        });
    }

    public void processDeviceUpdatedJson(final Intent intent) {
        executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.15
            @Override // java.lang.Runnable
            public void run() {
                try {
                    String stringExtra = intent.getStringExtra(MiotDeviceCore.DEVICE_INFO_JSON);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        JSONObject jSONObject = new JSONObject(stringExtra);
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
                        deviceInfo.model = jSONObject.optString("model");
                        Bundle bundle = new Bundle();
                        JSONObject optJSONObject = jSONObject.optJSONObject("subtitleMap");
                        if (optJSONObject != null) {
                            Iterator keys = optJSONObject.keys();
                            while (keys.hasNext()) {
                                String str = (String) keys.next();
                                bundle.putString(str, optJSONObject.optString(str));
                            }
                        }
                        deviceInfo.subtitleMap = bundle;
                        MiotDeviceCore.this.processDeviceUpdatedInternal(deviceInfo);
                    }
                } catch (Exception e) {
                    MiotLog.e("parse updated device failed: " + e.getMessage());
                }
            }
        });
    }

    public void processDeviceUpdated(final Intent intent) {
        executeTaskInThreadPool(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.16
            @Override // java.lang.Runnable
            public void run() {
                try {
                    intent.getExtras().setClassLoader(DeviceInfo.class.getClassLoader());
                    MiotDeviceCore.this.processDeviceUpdatedInternal((DeviceInfo) intent.getParcelableExtra(MiotDeviceCore.DEVICE_INFOS));
                } catch (Exception e) {
                    MiotLog.e("parse updated device failed: " + e.getMessage());
                }
            }
        });
    }

    public void processDeviceUpdatedInternal(final DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            try {
                if (!TextUtils.isEmpty(deviceInfo.did)) {
                    if (this.mOriginDevicesCount == 0) {
                        this.mOriginDevicesCount = 1;
                    }
                    if (!deviceInfo.model.startsWith(MI_WIFI_SPEAKER_MODEL_PREFIX) && !deviceInfo.model.startsWith(ONEMORE_WIFI_SPEAKER_MODEL_PREFIX)) {
                        this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.17
                            @Override // java.lang.Runnable
                            public void run() {
                                boolean z;
                                int i = 0;
                                while (true) {
                                    if (i >= MiotDeviceCore.this.mDevices.size()) {
                                        z = false;
                                        break;
                                    } else if (((DeviceInfo) MiotDeviceCore.this.mDevices.get(i)).did.equals(deviceInfo.did)) {
                                        MiotDeviceCore.this.mDevices.remove(i);
                                        MiotDeviceCore.this.mDevices.add(i, deviceInfo);
                                        z = true;
                                        break;
                                    } else {
                                        i++;
                                    }
                                }
                                if (!z) {
                                    int size = MiotDeviceCore.this.mDevices.size();
                                    MiotManager.onDevicesCountChanged(size, size + 1);
                                    MiotDeviceCore.this.mDevices.add(0, deviceInfo);
                                }
                                MiotLog.d("device (\"" + deviceInfo.deviceName + "\") updated");
                                MiotDeviceCore.this.notifyDevicesRefreshed(false);
                            }
                        });
                        return;
                    }
                    MiotLog.w("xiaomi wifi speaker device updated???");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        MiotLog.e("updated device invalid: did is null");
    }

    public void notifyDevicesRefreshed(boolean z) {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(ACTION_DEVICES_REFRESHED));
    }

    public void notifyDevicesFailed(boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.18
            @Override // java.lang.Runnable
            public void run() {
                MiotDeviceCore.this.mIsRefreshing = false;
                LocalBroadcastManager.getInstance(MiotDeviceCore.this.mContext).sendBroadcast(new Intent(MiotDeviceCore.ACTION_DEVICES_FAILED));
                MiotDeviceCore.this.clearRefreshTimeoutMap(false, null, "refresh device failed");
            }
        });
        if (z && this.mRetainRetryTime > 0 && this.mDevices.size() == 0) {
            this.mRetainRetryTime--;
            MiotLog.i("Info: notify device fail，refresh device");
            nextRefreshDevices(5000L);
        }
    }

    private void nextRefreshDevices(long j) {
        MiotLog.i("Info: refreshInternal 222, " + j);
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.19
            @Override // java.lang.Runnable
            public void run() {
                MiotDeviceCore.this.refreshDevicesInternal(true);
            }
        }, j);
    }

    public void notifyEventsUpdated() {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(ACTION_EVENTS_UPDATED));
    }

    public void isLogin(final String str, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.20
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    int apiLevel = MiotDeviceCore.this.mMiHomeService.getApiLevel();
                    MiotLog.i("AIDL_API_LEVEL: " + apiLevel);
                    if (apiLevel <= 0) {
                        MiotDeviceCore.this.mMiHomeService.isLogin(iCallback);
                        return;
                    }
                    MiotLog.i("Info: send login to smarthome, uid: " + str);
                    MiotDeviceCore.this.mMiHomeService.isLoginFromUid(str, iCallback);
                    return;
                }
                MiotLog.e("Error: send login fail, MiHomeService is null");
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                MiotDeviceCore.this.mIsRefreshing = false;
            }
        }.start();
    }

    private void getDeviceList(final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.21
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.getDeviceList(iCallback);
                } else {
                    MiotLog.e("getDeviceList failed, mMiHomeService is null");
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                MiotDeviceCore.this.mIsRefreshing = false;
            }
        }.start();
    }

    private void getDeviceListByHomeId(final String str, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.22
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.getDeviceListByHomeId(str, iCallback);
                } else {
                    MiotLog.e("getDeviceListByHomeId failed, mMiHomeService is null");
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                MiotDeviceCore.this.mIsRefreshing = false;
            }
        }.start();
    }

    public void setDeviceOn(final String str, final boolean z, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.23
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.setDeviceOn(str, z, iCallback);
                    return;
                }
                MiotLog.e("setDeviceOn failed, mMiHomeService is null");
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MiotDeviceCore.ERROR_CODE, -1);
                    iCallback.onFailure(bundle);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MiotDeviceCore.ERROR_CODE, -1);
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void sendMsg(final String str, final ISendMsgCallback iSendMsgCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.24
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotLog.i("Info: sendMsg: " + str);
                    String sendMsg = MiotDeviceCore.this.mMiHomeService.sendMsg(str);
                    ISendMsgCallback iSendMsgCallback2 = iSendMsgCallback;
                    if (iSendMsgCallback2 != null) {
                        iSendMsgCallback2.onResult(sendMsg);
                        return;
                    }
                    return;
                }
                MiotLog.e("Info: set device prop failed, mMiHomeService is null, isBound: " + MiotDeviceCore.this.mBound);
                ISendMsgCallback iSendMsgCallback3 = iSendMsgCallback;
                if (iSendMsgCallback3 != null) {
                    iSendMsgCallback3.onResult("{\"code\":500,\"msg\":\"MiHomeService is null\"}");
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                ISendMsgCallback iSendMsgCallback2 = iSendMsgCallback;
                if (iSendMsgCallback2 != null) {
                    iSendMsgCallback2.onResult("{\"code\":501,\"msg\":\"MiHomeService bind failed\"}");
                }
            }
        }.start();
    }

    public void deviceIsSetPinCode(String str, final DevicesSetPincodeCallback devicesSetPincodeCallback) {
        deviceIsSetPinCode(str, new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.25
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                if (bundle == null) {
                    DevicesSetPincodeCallback devicesSetPincodeCallback2 = devicesSetPincodeCallback;
                    if (devicesSetPincodeCallback2 != null) {
                        devicesSetPincodeCallback2.onResult(false);
                        return;
                    }
                    return;
                }
                boolean z = bundle.getBoolean(MiotDeviceCore.KEY_DEVICE_SET_PINCODE, false);
                MiotLog.i("deviceIsSetPinCode: " + z);
                DevicesSetPincodeCallback devicesSetPincodeCallback3 = devicesSetPincodeCallback;
                if (devicesSetPincodeCallback3 != null) {
                    devicesSetPincodeCallback3.onResult(z);
                }
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) throws RemoteException {
                DevicesSetPincodeCallback devicesSetPincodeCallback2 = devicesSetPincodeCallback;
                if (devicesSetPincodeCallback2 != null) {
                    devicesSetPincodeCallback2.onResult(false);
                }
            }
        });
    }

    private void deviceIsSetPinCode(final String str, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.26
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.isDeviceSetPincode(str, iCallback);
                    return;
                }
                MiotLog.e("setDeviceOn failed, mMiHomeService is null");
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MiotDeviceCore.ERROR_CODE, -1);
                    iCallback.onFailure(bundle);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MiotDeviceCore.ERROR_CODE, -1);
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void enterDeviceListPage() {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.27
            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.enterDeviceListPage();
                } else {
                    MiotLog.e("enterDeviceListPage failed, mMiHomeService is null");
                }
            }
        }.start();
    }

    public void exitDeviceListPage() {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.28
            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.exitDeviceListPage();
                } else {
                    MiotLog.e("exitDeviceListPage failed, mMiHomeService is null");
                }
            }
        }.start();
    }

    public void logoutMiHome(final LoginCallback loginCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.29
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService == null) {
                    LoginCallback loginCallback2 = loginCallback;
                    if (loginCallback2 != null) {
                        loginCallback2.onFailed();
                    }
                    MiotLog.e("logoutMiHome failed, mMiHomeService is null");
                } else if (MiotDeviceCore.this.mMiHomeService.getApiLevel() <= 0) {
                    LoginCallback loginCallback3 = loginCallback;
                    if (loginCallback3 != null) {
                        loginCallback3.onFailed();
                    }
                } else {
                    MiotDeviceCore.this.mMiHomeService.logoutMiHome(new ICallback.Stub() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.29.1
                        @Override // com.xiaomi.miot.service.ICallback
                        public void onSuccess(Bundle bundle) throws RemoteException {
                            if (loginCallback != null) {
                                loginCallback.onSuccess();
                            }
                        }

                        @Override // com.xiaomi.miot.service.ICallback
                        public void onFailure(Bundle bundle) throws RemoteException {
                            if (loginCallback != null) {
                                loginCallback.onFailed();
                            }
                        }
                    });
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                LoginCallback loginCallback2 = loginCallback;
                if (loginCallback2 != null) {
                    loginCallback2.onFailed();
                }
            }
        }.start();
    }

    private void callSmartHomeApi(final String str, final String str2, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.30
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.callSmartHomeApi(str, str2, iCallback);
                    return;
                }
                MiotLog.e("callSmartHomeApi failed, mMiHomeService is null");
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("errorCode", -1);
                    bundle.putString(SceneUtils.ERROR_DETAIL, "mMiHomeService is null");
                    iCallback.onFailure(bundle);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("errorCode", -1);
                    bundle.putString(SceneUtils.ERROR_DETAIL, "mMiHomeService bind failed");
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void callSmartHomeApiWithMethod(final String str, final String str2, final String str3, final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.31
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.callSmartHomeApiWithMethod(str, str2, str3, iCallback);
                    return;
                }
                MiotLog.e("callSmartHomeApiWithMethod failed, mMiHomeService is null");
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("errorCode", -1);
                    bundle.putString(SceneUtils.ERROR_DETAIL, "mMiHomeService is null");
                    iCallback.onFailure(bundle);
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("errorCode", -1);
                    bundle.putString(SceneUtils.ERROR_DETAIL, "mMiHomeService bind failed");
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void getSupportDoorbellPdids(final ICallback iCallback) {
        new RemoteTask() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.32
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                MiotDeviceCore.this = this;
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void executeTask() throws RemoteException {
                if (MiotDeviceCore.this.mMiHomeService != null) {
                    MiotDeviceCore.this.mMiHomeService.getSupportDoorbellPdids(iCallback);
                    return;
                }
                MiotLog.e("getSupportDoorbellPdids failed, mMiHomeService is null");
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("error_info", "mMiHomeService is null");
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask
            public void onBindFailed() {
                if (iCallback != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("error_info", "bind failed");
                    try {
                        iCallback.onFailure(bundle);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public synchronized void getMiotSensorData(final MiotSensorCallback miotSensorCallback) {
        if (miotSensorCallback != null) {
            if (!TextUtils.isEmpty(this.mHostDeviceInfo.homeId) && this.mDevices.size() != 0) {
                updateDeviceEvents(this.mHostDeviceInfo.homeId, new DeviceEventCallback() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.33
                    @Override // com.xiaomi.miot.support.core.MiotDeviceCore.DeviceEventCallback
                    public void onSuccess(List<MiotDeviceEvent> list) {
                        miotSensorCallback.onSuccess(MiotUtil.parseEvents(MiotDeviceCore.this.getHostDeviceRoomId(), list, MiotDeviceCore.this));
                    }

                    @Override // com.xiaomi.miot.support.core.MiotDeviceCore.DeviceEventCallback
                    public void onFailed(String str) {
                        miotSensorCallback.onFailed(str);
                    }
                });
            }
            int i = this.mRunnableId + 1;
            this.mRunnableId = i;
            RefreshTimeoutRunnable refreshTimeoutRunnable = new RefreshTimeoutRunnable(i, miotSensorCallback);
            this.mRefreshTimeoutMap.put(Integer.valueOf(refreshTimeoutRunnable.runnableId), refreshTimeoutRunnable);
            this.mHandler.postDelayed(refreshTimeoutRunnable, 15000L);
            MiotLog.i("Info: refresh device in sensor data");
            refreshDevices();
        }
    }

    public synchronized void clearRefreshTimeoutMap(boolean z, List<MiotDeviceEvent> list, String str) {
        if (this.mRefreshTimeoutMap.size() > 0) {
            Iterator<Map.Entry<Integer, RefreshTimeoutRunnable>> it = this.mRefreshTimeoutMap.entrySet().iterator();
            while (it.hasNext()) {
                RefreshTimeoutRunnable value = it.next().getValue();
                if (value != null) {
                    this.mHandler.removeCallbacks(value);
                    if (value.callback != null) {
                        if (z) {
                            value.callback.onSuccess(MiotUtil.parseEvents(getHostDeviceRoomId(), list, this));
                        } else {
                            value.callback.onFailed(str);
                        }
                    }
                }
                it.remove();
            }
        }
    }

    public List<DeviceInfo> getRoomDevices(String str, ArrayList<String> arrayList) {
        List<MiotHome> list;
        if (str == null || arrayList == null || (list = this.mHomeList) == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList<DeviceInfo> arrayList3 = new ArrayList(this.mDevices);
        for (MiotHome miotHome : this.mHomeList) {
            if (miotHome.homeId.equals(str)) {
                for (String str2 : miotHome.homeDeviceList) {
                    for (DeviceInfo deviceInfo : arrayList3) {
                        if (deviceInfo.did.equals(str2)) {
                            String str3 = deviceInfo.roomId;
                            if (!TextUtils.isEmpty(str3)) {
                                if (arrayList.contains(str3)) {
                                    arrayList2.add(deviceInfo);
                                }
                            } else if (MicoSupConstants.ROOM_DEFAULT.equals(deviceInfo.roomInfo) && arrayList.contains("0")) {
                                arrayList2.add(deviceInfo);
                            } else if (MicoSupConstants.ROOM_SHARE.equals(deviceInfo.roomInfo) && arrayList.contains(MicoSupConstants.SHARE_ROOM_ID)) {
                                arrayList2.add(deviceInfo);
                            }
                        }
                    }
                }
            }
            if (arrayList2.size() != 0) {
                return arrayList2;
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public abstract class RemoteTask {
        public abstract void executeTask() throws RemoteException;

        public abstract void onBindFailed();

        private RemoteTask() {
            MiotDeviceCore.this = r1;
        }

        public void start() {
            MiotDeviceCore.this.mHandler.post(new Runnable() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MiotDeviceCore.this.mMiHomeService != null) {
                        try {
                            RemoteTask.this.executeTask();
                        } catch (RemoteException e) {
                            MiotDeviceCore.this.mMiHomeService = null;
                            MiotLog.e("mihome remote exception: " + e.getMessage());
                        }
                    } else {
                        if (!MiotDeviceCore.this.mBound) {
                            MiotDeviceCore.this.bindService();
                        }
                        if (!MiotDeviceCore.this.mBound) {
                            MiotLog.e("mihome service not found???");
                            RemoteTask.this.onBindFailed();
                            return;
                        }
                        IntentFilter intentFilter = new IntentFilter(MiotDeviceCore.MIHOME_SERVICE_CONNECTED);
                        LocalBroadcastManager.getInstance(MiotDeviceCore.this.mContext).registerReceiver(new BroadcastReceiver() { // from class: com.xiaomi.miot.support.core.MiotDeviceCore.RemoteTask.1.1
                            @Override // android.content.BroadcastReceiver
                            public void onReceive(Context context, Intent intent) {
                                LocalBroadcastManager.getInstance(MiotDeviceCore.this.mContext).unregisterReceiver(this);
                                try {
                                    RemoteTask.this.executeTask();
                                } catch (RemoteException e2) {
                                    MiotDeviceCore.this.mMiHomeService = null;
                                    MiotLog.e("mihome remote exception: " + e2.getMessage());
                                }
                            }
                        }, intentFilter);
                    }
                }
            });
        }
    }

    /* loaded from: classes3.dex */
    public class RefreshTimeoutRunnable implements Runnable {
        private final MiotSensorCallback callback;
        private final int runnableId;

        public RefreshTimeoutRunnable(int i, MiotSensorCallback miotSensorCallback) {
            MiotDeviceCore.this = r1;
            this.runnableId = i;
            this.callback = miotSensorCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            MiotDeviceCore.this.mRefreshTimeoutMap.remove(Integer.valueOf(this.runnableId));
            MiotSensorCallback miotSensorCallback = this.callback;
            if (miotSensorCallback != null) {
                miotSensorCallback.onFailed("request timeout");
            }
        }
    }
}
