package com.xiaomi.mesh;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mesh.internal.IMeshLogListener;
import com.xiaomi.mesh.internal.IMeshStatusListener;
import com.xiaomi.mesh.internal.IMiotMeshService;
import com.xiaomi.mesh.internal.IMiotMeshServiceCallBack;
import com.xiaomi.mesh.internal.IOtSendListener;
import com.xiaomi.mesh.internal.WiFiInfo;
import com.xiaomi.mesh.provision.MiotBindDevicesResult;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mesh.utils.LogUtil;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class MiotMeshManager {
    public static final int BIND_DEVICE_ACK = 1;
    private static final long FREQUENT_DISCONNECT_INTERVAL = 300000;
    private static final String INTERNAL_IPC_GET_ROM_VERSION = "get_rom_version";
    private static final String INTERNAL_IPC_SET_MESH_OTA_BEGIN = "set_mesh_ota_begin";
    private static final int MAX_DISCONNECT_TIME = 5;
    private static final int RESTART_DELAY_TIME = 5000;
    public static final int SCAN_DEVICE_ACK = 0;
    private static final String TAG = "MiotMeshManager";
    private static volatile MiotMeshManager sInstance;
    private Context mContext;
    private List<MiotMeshDeviceItem> mNoSupportDeviceFilter;
    private IMiotMeshService mServiceInstance;
    private int mDisconnectTime = 0;
    private long mFirstDisconnect = 0;
    private boolean mHasInit = false;
    private boolean mIsBigScreen = false;
    private boolean mHasBindService = false;
    private boolean mIsBinding = false;
    private final List<MiotMeshCompleteListener> mBindCompleteListenerList = new ArrayList();
    private MiotMeshServiceCallBack mMiotMeshServiceCallBack = null;
    private GatewayMessageListener mReceiveGatewayMessageListener = new GatewayMessageListener() { // from class: com.xiaomi.mesh.MiotMeshManager.1
        @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
        public void onFailed(MiotError miotError) {
        }

        @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
        public void onSucceed(String str) {
            LogUtil.v(MiotMeshManager.TAG, "receive ot request message: " + str);
            if (!TextUtils.isEmpty(str)) {
                MiotMeshManager.this.receiveOtMessage(str);
                MiotInternalRpcMethod miotInternalRpcMethod = (MiotInternalRpcMethod) Gsons.getGson().fromJson(str, (Class<Object>) MiotInternalRpcMethod.class);
                if (miotInternalRpcMethod != null && miotInternalRpcMethod.getMethod() != null) {
                    String method = miotInternalRpcMethod.getMethod();
                    char c = 65535;
                    int hashCode = method.hashCode();
                    if (hashCode != -1681181839) {
                        if (hashCode == 778783232 && method.equals(MiotMeshManager.INTERNAL_IPC_GET_ROM_VERSION)) {
                            c = 0;
                        }
                    } else if (method.equals(MiotMeshManager.INTERNAL_IPC_SET_MESH_OTA_BEGIN)) {
                        c = 1;
                    }
                    switch (c) {
                        case 0:
                        case 1:
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put("id", miotInternalRpcMethod.getId());
                                jSONObject.put("result", "ok");
                                jSONObject.put("rom_ver", SystemProperties.get("ro.mi.sw_ver"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                MiotHostManager.getInstance().sendGatewayMessage(jSONObject.toString(), new GatewayMessageListener() { // from class: com.xiaomi.mesh.MiotMeshManager.1.1
                                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                                    public void onFailed(MiotError miotError) {
                                    }

                                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                                    public void onSucceed(String str2) {
                                    }
                                });
                            } catch (MiotException e2) {
                                e2.printStackTrace();
                            }
                            if (MiotMeshManager.INTERNAL_IPC_SET_MESH_OTA_BEGIN.equals(miotInternalRpcMethod.getMethod())) {
                                AppStoreUtil.silentUpgradeApps(MicoApplication.getGlobalContext().getApplicationContext(), "com.xiaomi.mesh.gateway");
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    };
    private IMiotMeshServiceCallBack mIMiotMeshServiceCallBack = new IMiotMeshServiceCallBack.Stub() { // from class: com.xiaomi.mesh.MiotMeshManager.2
        @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
        public void reportScanDevicesResult(MiotDeviceScanResult miotDeviceScanResult) throws RemoteException {
            if (MiotMeshManager.this.mMiotMeshServiceCallBack != null) {
                MiotMeshManager.this.mMiotMeshServiceCallBack.reportScanDevicesResult(miotDeviceScanResult);
            }
        }

        @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
        public void reportBindDevicesResult(MiotBindDevicesResult miotBindDevicesResult) throws RemoteException {
            if (MiotMeshManager.this.mMiotMeshServiceCallBack != null) {
                MiotMeshManager.this.mMiotMeshServiceCallBack.reportBindDevicesResult(miotBindDevicesResult);
            }
        }

        @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
        public void reportAck(int i, String str) throws RemoteException {
            if (MiotMeshManager.this.mMiotMeshServiceCallBack != null) {
                MiotMeshManager.this.mMiotMeshServiceCallBack.reportAck(i, str);
            }
        }

        @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
        public WiFiInfo getWiFiInfo() throws RemoteException {
            if (MiotMeshManager.this.mMiotMeshServiceCallBack != null) {
                return MiotMeshManager.this.mMiotMeshServiceCallBack.getWiFiInfo();
            }
            return new WiFiInfo("", "", "");
        }

        @Override // com.xiaomi.mesh.internal.IMiotMeshServiceCallBack
        public void saveCurrentWiFi() throws RemoteException {
            if (MiotMeshManager.this.mMiotMeshServiceCallBack != null) {
                MiotMeshManager.this.mMiotMeshServiceCallBack.saveCurrentWiFi();
            }
        }
    };
    private IOtSendListener mIOtSendListener = new IOtSendListener.Stub() { // from class: com.xiaomi.mesh.MiotMeshManager.3
        @Override // com.xiaomi.mesh.internal.IOtSendListener
        public void sendOtMessage(final String str) throws RemoteException {
            try {
                MiotHostManager.getInstance().sendGatewayMessage(str, new GatewayMessageListener() { // from class: com.xiaomi.mesh.MiotMeshManager.3.1
                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onSucceed(String str2) {
                        LogUtil.v(MiotMeshManager.TAG, "sendOtMessage response success: " + str2);
                        MiotMeshManager.this.receiveOtMessage(str2);
                    }

                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onFailed(MiotError miotError) {
                        JSONObject jSONObject;
                        LogUtil.d(MiotMeshManager.TAG, "sendOtMessage response failed: " + miotError.toString());
                        if (miotError.getCode() != MiotError.INTERNAL.getCode() && miotError.getCode() != MiotError.INTERNAL_NOT_INITIALIZED.getCode() && miotError.getCode() != MiotError.INTERNAL_OT_SERVICE_NOT_START.getCode() && miotError.getCode() != MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode() && miotError.getCode() != MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode() && miotError.getCode() != MiotError.INTERNAL_INVALID_ARGS.getCode() && miotError.getCode() != MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                            int i = 0;
                            try {
                                i = new JSONObject(str).optInt("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("id", i);
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("code", miotError.getCode());
                                JSONArray jSONArray = null;
                                try {
                                    jSONObject = new JSONObject(miotError.getMessage());
                                } catch (Exception unused) {
                                    jSONObject = null;
                                }
                                if (jSONObject != null) {
                                    jSONObject3.put("message", jSONObject);
                                } else {
                                    try {
                                        jSONArray = new JSONArray(miotError.getMessage());
                                    } catch (Exception unused2) {
                                    }
                                    if (jSONArray != null) {
                                        jSONObject3.put("message", jSONArray);
                                    } else {
                                        jSONObject3.put("message", miotError.getMessage());
                                    }
                                }
                                jSONObject2.put("error", jSONObject3);
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                            MiotMeshManager.this.receiveOtMessage(jSONObject2.toString());
                        }
                    }
                });
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.xiaomi.mesh.MiotMeshManager.4
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(MiotMeshManager.TAG, String.format("onServiceConnected: %s", componentName.getShortClassName()));
            MiotMeshManager.this.mIsBinding = false;
            MiotMeshManager.this.mServiceInstance = IMiotMeshService.Stub.asInterface(iBinder);
            try {
                if (MiotMeshManager.this.mServiceInstance != null) {
                    MiotMeshManager.this.mServiceInstance.registerOtSendListener(MiotMeshManager.this.mIOtSendListener);
                    MiotMeshManager.this.mServiceInstance.registerMiotMeshServiceCallBack(MiotMeshManager.this.mIMiotMeshServiceCallBack);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MiotMeshManager.this.mHandler.removeCallbacks(MiotMeshManager.this.mBindTimeoutRunnable);
            synchronized (MiotMeshManager.this.mBindCompleteListenerList) {
                if (MiotMeshManager.this.mBindCompleteListenerList.size() > 0) {
                    for (MiotMeshCompleteListener miotMeshCompleteListener : MiotMeshManager.this.mBindCompleteListenerList) {
                        miotMeshCompleteListener.onComplete();
                    }
                    MiotMeshManager.this.mBindCompleteListenerList.clear();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(MiotMeshManager.TAG, String.format("onServiceDisconnected: %s", componentName.getShortClassName()));
            MiotMeshManager.this.mIsBinding = false;
            MiotMeshManager.this.mServiceInstance = null;
            if (MiotMeshManager.this.mContext != null) {
                try {
                    MiotMeshManager.this.mContext.unbindService(MiotMeshManager.this.mConnection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            MiotProvisionManagerWrapper.getInstance().reportMeshServiceHasDisconnect();
        }
    };
    private Runnable mBindTimeoutRunnable = new Runnable() { // from class: com.xiaomi.mesh.MiotMeshManager.5
        @Override // java.lang.Runnable
        public void run() {
            MiotMeshManager.this.mIsBinding = false;
            Log.e(MiotMeshManager.TAG, "bindMeshService timeout");
            MiotMeshManager.this.bindServiceFailed("bindMeshService timeout");
        }
    };
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /* loaded from: classes3.dex */
    public interface MiotMeshCompleteListener {
        void onComplete();

        void onFailed(String str);
    }

    /* loaded from: classes3.dex */
    public interface MiotMeshServiceCallBack {
        WiFiInfo getWiFiInfo();

        void reportAck(int i, String str);

        void reportBindDevicesResult(MiotBindDevicesResult miotBindDevicesResult);

        void reportScanDevicesResult(MiotDeviceScanResult miotDeviceScanResult);

        void saveCurrentWiFi();
    }

    private MiotMeshManager() {
    }

    public static MiotMeshManager getInstance() {
        if (sInstance == null) {
            synchronized (MiotMeshManager.class) {
                if (sInstance == null) {
                    sInstance = new MiotMeshManager();
                }
            }
        }
        return sInstance;
    }

    public boolean isMeshServiceConnected() {
        return this.mServiceInstance != null;
    }

    public void bindMeshService(Context context, MiotMeshCompleteListener miotMeshCompleteListener) {
        boolean z;
        Log.d(TAG, "bindMeshService");
        this.mHasBindService = true;
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
        }
        if (this.mServiceInstance == null) {
            synchronized (this.mBindCompleteListenerList) {
                if (miotMeshCompleteListener != null) {
                    this.mBindCompleteListenerList.add(miotMeshCompleteListener);
                }
                if (!this.mIsBinding) {
                    this.mIsBinding = true;
                    Intent intent = new Intent();
                    intent.setAction("com.xiaomi.mesh.internal.IMiotMeshService");
                    intent.setPackage("com.xiaomi.mesh.gateway");
                    try {
                        z = this.mContext.bindService(intent, this.mConnection, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        z = false;
                    }
                    if (z) {
                        this.mHandler.removeCallbacks(this.mBindTimeoutRunnable);
                        this.mHandler.postDelayed(this.mBindTimeoutRunnable, 15000L);
                    } else {
                        this.mIsBinding = false;
                        Log.e(TAG, "bindMeshService failed");
                        bindServiceFailed("bindMeshService failed");
                    }
                }
            }
        } else if (miotMeshCompleteListener != null) {
            miotMeshCompleteListener.onComplete();
        }
        if (MiotHostManager.getInstance().isMiotServiceConnected()) {
            try {
                MiotHostManager.getInstance().registerGatewayReceiveListener(this.mReceiveGatewayMessageListener);
            } catch (MiotException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void registerGatewayReceiveListener() {
        if (MiotHostManager.getInstance().isMiotServiceConnected()) {
            try {
                L.mesh.d("registerGatewayReceiveListener");
                MiotHostManager.getInstance().registerGatewayReceiveListener(this.mReceiveGatewayMessageListener);
            } catch (MiotException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindServiceFailed(String str) {
        synchronized (this.mBindCompleteListenerList) {
            if (this.mBindCompleteListenerList.size() > 0) {
                for (MiotMeshCompleteListener miotMeshCompleteListener : this.mBindCompleteListenerList) {
                    miotMeshCompleteListener.onFailed(str);
                }
                this.mBindCompleteListenerList.clear();
            }
        }
    }

    public void tryToRebindMeshService() {
        if (this.mHasBindService) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - this.mFirstDisconnect) > 300000) {
                this.mDisconnectTime = 1;
                this.mFirstDisconnect = currentTimeMillis;
                this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.mesh.MiotMeshManager.6
                    @Override // java.lang.Runnable
                    public void run() {
                        MiotMeshManager.this.rebindMeshService();
                    }
                }, 5000L);
                return;
            }
            this.mDisconnectTime++;
            if (this.mDisconnectTime < 5) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.xiaomi.mesh.MiotMeshManager.7
                    @Override // java.lang.Runnable
                    public void run() {
                        MiotMeshManager.this.rebindMeshService();
                    }
                }, 5000L);
                return;
            }
            L.mesh.e("mesh service disconnect frequently");
            MiotProvisionManagerWrapper.getInstance().setMeshEnable(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebindMeshService() {
        Log.d(TAG, "rebindMeshService");
        Context context = this.mContext;
        if (context != null && this.mHasBindService) {
            bindMeshService(context, new MiotMeshCompleteListener() { // from class: com.xiaomi.mesh.MiotMeshManager.8
                @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshCompleteListener
                public void onFailed(String str) {
                }

                @Override // com.xiaomi.mesh.MiotMeshManager.MiotMeshCompleteListener
                public void onComplete() {
                    if (MiotMeshManager.this.mHasInit && MiotMeshManager.this.mServiceInstance != null) {
                        try {
                            Log.d(MiotMeshManager.TAG, "init after rebind service");
                            MiotMeshManager.this.mServiceInstance.init(MiotMeshManager.this.mIsBigScreen, MiotMeshManager.this.mNoSupportDeviceFilter);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void setBleDebugLog(boolean z) {
        Log.d(TAG, "setBleDebugLog enabled = " + z);
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.setBleDebugLog(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "setBleDebugLog failed, miot mesh service is disconnected");
        }
    }

    public void init(boolean z, List<MiotMeshDeviceItem> list) {
        Log.d(TAG, "init");
        this.mHasInit = true;
        this.mIsBigScreen = z;
        this.mNoSupportDeviceFilter = list;
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.init(this.mIsBigScreen, this.mNoSupportDeviceFilter);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "init failed, miot mesh service is disconnected");
        }
    }

    public void registerMiotMeshServiceCallBack(MiotMeshServiceCallBack miotMeshServiceCallBack) {
        Log.d(TAG, "registerMiotMeshServiceCallBack");
        this.mMiotMeshServiceCallBack = miotMeshServiceCallBack;
    }

    public void start() {
        Log.d(TAG, "start");
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "start failed, miot mesh service is disconnected");
        }
    }

    public void stop() {
        Log.d(TAG, "stop");
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "stop failed, miot mesh service is disconnected");
        }
    }

    public void quit() {
        Log.d(TAG, Commands.QUIT);
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.quit();
                this.mHasBindService = false;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "quit failed, miot mesh service is disconnected");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveOtMessage(String str) {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.receiveOtMessage(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "receiveOtMessage failed, service is disconnected");
        }
    }

    public void receiveBrainMessage(String str, String str2) {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.receiveBrainMessage(str, str2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "receiveBrainMessage failed, service is disconnected");
        }
    }

    public void registerMiotMeshStatusListener(final MiotMeshStatusListener miotMeshStatusListener) {
        Log.d(TAG, "registerMiotMeshStatusListener");
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.registerMiotMeshStatusListener(new IMeshStatusListener.Stub() { // from class: com.xiaomi.mesh.MiotMeshManager.9
                    @Override // com.xiaomi.mesh.internal.IMeshStatusListener
                    public void onMeshInitDone(int i) {
                        MiotMeshStatusListener miotMeshStatusListener2 = miotMeshStatusListener;
                        if (miotMeshStatusListener2 != null) {
                            miotMeshStatusListener2.onMeshInitDone(i);
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "registerMiotMeshStatusListener failed, miot mesh service is disconnected");
        }
    }

    public int getMeshInitDoneResult() {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                return iMiotMeshService.getMeshInitDoneResult();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "getMeshInitDoneResult failed, miot mesh service is disconnected");
        }
        return -1;
    }

    public int getScanDeviceTimeout() {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                return iMiotMeshService.getScanDeviceTimeout();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "getScanDeviceTimeout failed, miot mesh service is disconnected");
        }
        return -1;
    }

    public int getBindSingleDeviceTimeout() {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                return iMiotMeshService.getBindSingleDeviceTimeout();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "getBindSingleDeviceTimeout failed, miot mesh service is disconnected");
        }
        return -1;
    }

    public int getProvisionState() {
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                return iMiotMeshService.getProvisionState();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "getProvisionState failed, miot mesh service is disconnected");
        }
        return -1;
    }

    public void setProvisionState(int i) {
        Log.d(TAG, "setProvisionState state = " + i);
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.setProvisionState(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "setProvisionState failed, miot mesh service is disconnected");
        }
    }

    public void registerMeshLogListener(final MeshLogListener meshLogListener) {
        Log.d(TAG, "registerMeshLogListener");
        IMiotMeshService iMiotMeshService = this.mServiceInstance;
        if (iMiotMeshService != null) {
            try {
                iMiotMeshService.registerMeshLogListener(new IMeshLogListener.Stub() { // from class: com.xiaomi.mesh.MiotMeshManager.10
                    @Override // com.xiaomi.mesh.internal.IMeshLogListener
                    public void logV(String str, String str2) throws RemoteException {
                        try {
                            if (meshLogListener != null) {
                                meshLogListener.logV(str, str2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override // com.xiaomi.mesh.internal.IMeshLogListener
                    public void logD(String str, String str2) throws RemoteException {
                        try {
                            if (meshLogListener != null) {
                                meshLogListener.logD(str, str2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override // com.xiaomi.mesh.internal.IMeshLogListener
                    public void logI(String str, String str2) throws RemoteException {
                        try {
                            if (meshLogListener != null) {
                                meshLogListener.logI(str, str2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override // com.xiaomi.mesh.internal.IMeshLogListener
                    public void logW(String str, String str2) throws RemoteException {
                        try {
                            if (meshLogListener != null) {
                                meshLogListener.logW(str, str2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override // com.xiaomi.mesh.internal.IMeshLogListener
                    public void logE(String str, String str2) throws RemoteException {
                        try {
                            if (meshLogListener != null) {
                                meshLogListener.logE(str, str2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "registerMeshLogListener failed, miot mesh service is disconnected");
        }
    }
}
