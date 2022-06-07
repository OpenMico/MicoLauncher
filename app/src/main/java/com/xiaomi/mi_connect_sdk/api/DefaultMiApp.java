package com.xiaomi.mi_connect_sdk.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.idm.api.IDMBinderBase;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.IApStateCallback;
import com.xiaomi.mi_connect_service.IDeviceScannerCallback;
import com.xiaomi.mi_connect_service.IIPCDataCallback;
import com.xiaomi.mi_connect_service.IMiConnect;
import com.xiaomi.mi_connect_service.IMiConnectCallback;
import com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DefaultMiApp extends IDMBinderBase implements InnerMiApp {
    private static final int MIN_VERSION = 2;
    private static final String TAG = "DefaultMiApp";
    private int mAppId;
    private List<Command> commandList = new ArrayList();
    private volatile boolean isAdvertising = false;
    private volatile boolean isDiscovering = false;
    private volatile boolean isDestroying = false;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private IMiConnectCallback mCallback = new IMiConnectCallback.Stub() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1
        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onAdvertisingResult(final int i, final int i2) {
            if (i2 == ResultCode.START_ADVERTISING_SUCCESS.getCode()) {
                DefaultMiApp.this.isAdvertising = true;
            }
            if (i2 == ResultCode.STOP_ADVERTISING_SUCCESS.getCode()) {
                DefaultMiApp.this.isAdvertising = false;
            }
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.1
                @Override // java.lang.Runnable
                public void run() {
                    DefaultMiApp.this.mMiAppCallback.onAdvertingResult(i, i2);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onDiscoveryResult(final int i, final int i2) {
            if (i2 == ResultCode.START_DISCOVERY_SUCCESS.getCode()) {
                DefaultMiApp.this.isDiscovering = true;
            }
            if (i2 == ResultCode.STOP_DISCOVERY_SUCCESS.getCode()) {
                DefaultMiApp.this.isDiscovering = false;
            }
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.2
                @Override // java.lang.Runnable
                public void run() {
                    DefaultMiApp.this.mMiAppCallback.onDiscoveryResult(i, i2);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onEndpointFound(final int i, final int i2, final String str, final byte[] bArr) {
            LogUtil.d(DefaultMiApp.TAG, "onEndpointFound: manager", new Object[0]);
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.3
                @Override // java.lang.Runnable
                public void run() {
                    MiAppCallback miAppCallback = DefaultMiApp.this.mMiAppCallback;
                    int i3 = i;
                    int i4 = i2;
                    String str2 = str;
                    if (str2 == null) {
                        str2 = "";
                    }
                    byte[] bArr2 = bArr;
                    if (bArr2 == null) {
                        bArr2 = new byte[0];
                    }
                    miAppCallback.onEndpointFound(i3, i4, str2, bArr2);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onEndpointLost(final int i, final int i2, final String str) throws RemoteException {
            LogUtil.d(DefaultMiApp.TAG, "onEndpointLost: manager", new Object[0]);
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.4
                @Override // java.lang.Runnable
                public void run() {
                    MiAppCallback miAppCallback = DefaultMiApp.this.mMiAppCallback;
                    int i3 = i;
                    int i4 = i2;
                    String str2 = str;
                    if (str2 == null) {
                        str2 = "";
                    }
                    miAppCallback.onEndpointLost(i3, i4, str2);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onConnectionInitiated(final int i, final int i2, final String str, final byte[] bArr, final byte[] bArr2) {
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.5
                @Override // java.lang.Runnable
                public void run() {
                    MiAppCallback miAppCallback = DefaultMiApp.this.mMiAppCallback;
                    int i3 = i;
                    int i4 = i2;
                    String str2 = str;
                    if (str2 == null) {
                        str2 = "";
                    }
                    byte[] bArr3 = bArr;
                    if (bArr3 == null) {
                        bArr3 = new byte[0];
                    }
                    byte[] bArr4 = bArr2;
                    if (bArr4 == null) {
                        bArr4 = new byte[0];
                    }
                    miAppCallback.onConnectionInitiated(i3, i4, str2, bArr3, bArr4);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onConnectionResult(final int i, final int i2, final String str, final int i3) {
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.6
                @Override // java.lang.Runnable
                public void run() {
                    DefaultMiApp.this.mMiAppCallback.onConnectionResult(i, i2, str, i3);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onDisconnection(final int i, final int i2) {
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.7
                @Override // java.lang.Runnable
                public void run() {
                    DefaultMiApp.this.mMiAppCallback.onDisconnection(i, i2);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onPayloadSentResult(final int i, final int i2, final int i3) {
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.8
                @Override // java.lang.Runnable
                public void run() {
                    DefaultMiApp.this.mMiAppCallback.onPayloadSentResult(i, i2, i3);
                }
            });
        }

        @Override // com.xiaomi.mi_connect_service.IMiConnectCallback
        public void onPayloadReceived(final int i, final int i2, final byte[] bArr) {
            DefaultMiApp.this.uiHandler.post(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.1.9
                @Override // java.lang.Runnable
                public void run() {
                    MiAppCallback miAppCallback = DefaultMiApp.this.mMiAppCallback;
                    int i3 = i;
                    int i4 = i2;
                    byte[] bArr2 = bArr;
                    if (bArr2 == null) {
                        bArr2 = new byte[0];
                    }
                    miAppCallback.onPayloadReceived(i3, i4, bArr2);
                }
            });
        }
    };

    @Override // com.xiaomi.idm.api.IDMBinderBase
    protected int getMinVersion() {
        return 2;
    }

    public DefaultMiApp(Context context, MiAppCallback miAppCallback, int i) {
        super(context, miAppCallback);
        if (context == null || miAppCallback == null) {
            throw new IllegalArgumentException("context or callback can not be null");
        }
        this.mAppId = i;
    }

    @Override // com.xiaomi.idm.api.IDMBinderBase
    public void onServiceConnected() {
        super.onServiceConnected();
        List<Command> list = this.commandList;
        int size = list.size();
        for (int i = 0; size > 0 && i < size; i++) {
            list.get(i).execute();
        }
        list.clear();
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void startAdvertising(final AppConfig appConfig) {
        if (!this.isDestroying) {
            if (!serviceAvailable()) {
                bindService();
                this.commandList.add(new Command(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.2
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultMiApp.this.startAdvertising(appConfig);
                    }
                }));
            } else if (!isVersionSatisfied()) {
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
            } else {
                try {
                    this.mService.setCallback(this.mAppId, 1, this.mCallback);
                    this.mService.startAdvertising(this.mAppId, appConfig.getAdvData(), appConfig.getDiscType(), appConfig.getAppCommType(), appConfig.getCommData());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
                }
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void stopAdvertising() {
        if (!serviceAvailable()) {
            if (this.isAdvertising) {
                LogUtil.e(TAG, "service unbind but advertising", new Object[0]);
                this.mMiAppCallback.onServiceError(ResultCode.STOP_ADVERTISING_ERROR.getCode());
                return;
            }
            this.mMiAppCallback.onAdvertingResult(this.mAppId, ResultCode.STOP_ADVERTISING_SUCCESS.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.stopAdvertising(this.mAppId);
            } catch (RemoteException unused) {
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void startDiscovery(final AppConfig appConfig) {
        if (!this.isDestroying) {
            if (!serviceAvailable()) {
                bindService();
                this.commandList.add(new Command(new Runnable() { // from class: com.xiaomi.mi_connect_sdk.api.DefaultMiApp.3
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultMiApp.this.startDiscovery(appConfig);
                    }
                }));
            } else if (!isVersionSatisfied()) {
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
            } else {
                try {
                    this.mService.setCallback(this.mAppId, 2, this.mCallback);
                    if (!requestServiceFromOtherApp(this.mAppId, appConfig) || sServiceApiVersion <= 3) {
                        this.mService.startDiscovery(this.mAppId, appConfig.getCommData(), appConfig.getDiscType(), appConfig.getAppCommType(), appConfig.getAppCommDataType());
                    } else {
                        this.mService.startDiscoveryV2(this.mAppId, appConfig.getCommData(), appConfig.getDiscType(), appConfig.getAppCommType(), appConfig.getAppCommDataType(), appConfig.getDiscAppIds());
                    }
                } catch (RemoteException unused) {
                    this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
                }
            }
        }
    }

    private boolean requestServiceFromOtherApp(int i, AppConfig appConfig) {
        if (appConfig.getDiscAppIds().length < 1) {
            return false;
        }
        return (appConfig.getDiscAppIds().length == 1 && appConfig.getDiscAppIds()[0] == i) ? false : true;
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void stopDiscovery() {
        if (!serviceAvailable()) {
            if (this.isDiscovering) {
                LogUtil.e(TAG, "service unbind but discovering", new Object[0]);
                this.mMiAppCallback.onServiceError(ResultCode.STOP_DISCOVERY_ERROR.getCode());
                return;
            }
            this.mMiAppCallback.onDiscoveryResult(this.mAppId, ResultCode.STOP_DISCOVERY_SUCCESS.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.stopDiscovery(this.mAppId);
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void requestConnection(ConnectionConfig connectionConfig) {
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.requestConnection(this.mAppId, connectionConfig.getEndPointId(), connectionConfig.getCommData());
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void acceptConnection(ConnectionConfig connectionConfig) {
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.acceptConnection(this.mAppId, connectionConfig.getRoleType(), connectionConfig.getEndPointId(), connectionConfig.isEndPointTrusted());
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void rejectConnection(ConnectionConfig connectionConfig) {
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.rejectConnection(this.mAppId, connectionConfig.getRoleType(), connectionConfig.getEndPointId());
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void disconnectFromEndPoint(ConnectionConfig connectionConfig) {
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.disconnectFromEndPoint(this.mAppId, connectionConfig.getRoleType(), connectionConfig.getEndPointId());
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void sendPayload(PayloadConfig payloadConfig) {
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.sendPayload(this.mAppId, payloadConfig.getRoleType(), payloadConfig.getEndPointId(), payloadConfig.getPayload());
            } catch (RemoteException e) {
                e.printStackTrace();
                this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public byte[] getIdHash() {
        LogUtil.d(TAG, "getIdHash", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return null;
        } else if (!isVersionSatisfied()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
            return null;
        } else {
            try {
                return this.mService.getIdHash();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public byte[] deviceInfoIDM() {
        LogUtil.d(TAG, "deviceInfoIDM", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return null;
        } else if (sServiceApiVersion < 5) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
            return null;
        } else {
            try {
                return this.mService.deviceInfoIDM();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public int startScannerList(IDeviceScannerCallback iDeviceScannerCallback, String str) {
        LogUtil.i(TAG, "startScannerList", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return -1;
        }
        try {
            return this.mService.startScannerList(this.mAppId, iDeviceScannerCallback, str);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "", e);
            return -1;
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public int registerSoundBoxWhiteName(ISoundBoxWhiteNameCallBack iSoundBoxWhiteNameCallBack) {
        LogUtil.i(TAG, "registerSoundBoxWhiteName", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return -1;
        }
        try {
            return this.mService.registerSoundBoxWhiteName(iSoundBoxWhiteNameCallBack);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "", e);
            return -1;
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public long startAp(String str, String str2, int i, boolean z, IApStateCallback iApStateCallback) {
        LogUtil.i(TAG, "startAp", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return ResultCode.SERVICE_ERROR.getCode();
        } else if (sServiceApiVersion < 7) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
            return ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode();
        } else {
            try {
                return this.mService.startSap(str, str2, i, z, iApStateCallback);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "", e);
                return -1L;
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.MiApp
    public void stopAp(long j) {
        LogUtil.i(TAG, "stopAp", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
        } else if (sServiceApiVersion < 7) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_API_VERSION_TOO_LOW.getCode());
        } else {
            try {
                this.mService.stopSap(j);
            } catch (RemoteException e) {
                LogUtil.e(TAG, "", e);
            }
        }
    }

    @Override // com.xiaomi.mi_connect_sdk.api.InnerMiApp
    public void destroy(int i) {
        doDestroy();
        this.isDestroying = true;
        try {
            IMiConnect iMiConnect = this.mService;
            if (iMiConnect != null) {
                iMiConnect.destroy(this.mAppId, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService();
    }

    public int setIPCDataCallback(String str, IIPCDataCallback iIPCDataCallback) {
        LogUtil.i(TAG, "setIPCDataCallback", new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return -1;
        }
        try {
            return this.mService.setIPCDataCallback(this.mAppId, str, iIPCDataCallback);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "", e);
            return -1;
        }
    }

    public int publish(String str, String str2, byte[] bArr) {
        LogUtil.i(TAG, "publish resource: " + str + " did: " + str2, new Object[0]);
        if (!serviceAvailable()) {
            this.mMiAppCallback.onServiceError(ResultCode.SERVICE_ERROR.getCode());
            return -1;
        }
        try {
            return this.mService.publish(this.mAppId, str, str2, bArr);
        } catch (RemoteException e) {
            LogUtil.e(TAG, "", e);
            return 0;
        }
    }

    /* loaded from: classes3.dex */
    public class Command {
        private Runnable worker;

        Command(Runnable runnable) {
            DefaultMiApp.this = r1;
            this.worker = runnable;
        }

        void execute() {
            this.worker.run();
        }
    }
}
