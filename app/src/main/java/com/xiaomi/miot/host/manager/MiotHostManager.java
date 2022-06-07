package com.xiaomi.miot.host.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.miot.host.lan.impl.util.SharePrefsManager;
import com.xiaomi.miot.host.service.IMiotHostService;
import com.xiaomi.miot.host.service.handler.IOperationHandler;
import com.xiaomi.miot.host.service.listener.IBindKeyListener;
import com.xiaomi.miot.host.service.listener.ICheckBindListener;
import com.xiaomi.miot.host.service.listener.ICompletedListener;
import com.xiaomi.miot.host.service.listener.IGatewayMessageListener;
import com.xiaomi.miot.host.service.listener.IGetDeviceInfoHandler;
import com.xiaomi.miot.host.service.listener.IMiotConnectedListener;
import com.xiaomi.miot.host.service.listener.IMiotInternalCommandListener;
import com.xiaomi.miot.host.service.listener.IMiotLogListener;
import com.xiaomi.miot.host.service.listener.IOnBindListener;
import com.xiaomi.miot.host.service.listener.IOnTransmitListener;
import com.xiaomi.miot.host.service.listener.ISessionListener;
import com.xiaomi.miot.host.service.listener.ISyncTimeListener;
import com.xiaomi.miot.host.service.listener.ITransparentListener;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.listener.AccessTokenListener;
import com.xiaomi.miot.typedef.listener.BindKeyListener;
import com.xiaomi.miot.typedef.listener.CheckBindListener;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;
import com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler;
import com.xiaomi.miot.typedef.listener.MiotConnectedListener;
import com.xiaomi.miot.typedef.listener.MiotInternalCommandListener;
import com.xiaomi.miot.typedef.listener.MiotLogListener;
import com.xiaomi.miot.typedef.listener.OnBindListener;
import com.xiaomi.miot.typedef.listener.OnTransmitListener;
import com.xiaomi.miot.typedef.listener.SessionListener;
import com.xiaomi.miot.typedef.listener.SyncTimeListener;
import com.xiaomi.miot.typedef.listener.TokenListener;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotHostManager {
    private static String SVC_NAME = IMiotHostService.class.getName();
    private static final String TAG = "MiotHostManager";
    private static volatile MiotHostManager sInstance;
    private MyServiceConnection mConnection;
    private MiotLogListener mMiotLogListener;
    private IMiotHostService mServiceInstance;

    public void getConfigUIDbyHideSsidWay() {
    }

    public String getSdkVersion() {
        return "4.3.4";
    }

    public static MiotHostManager getInstance() {
        if (sInstance == null) {
            synchronized (MiotHostManager.class) {
                if (sInstance == null) {
                    sInstance = new MiotHostManager();
                }
            }
        }
        return sInstance;
    }

    private MiotHostManager() {
    }

    public boolean isMiotServiceConnected() {
        return (this.mConnection == null || this.mServiceInstance == null) ? false : true;
    }

    public void setMijiaSe(Context context, boolean z) {
        if (context == null) {
            log(TAG, " setMijiaSe failed because context is null!");
        }
        SharePrefsManager.setSettingBoolean(context, SharePrefsManager.SP_MIOTHOST, SharePrefsManager.SP_MIOT_SE_KEY, z);
    }

    public synchronized void bind(Context context, CompletedListener completedListener, GetDeviceInfoHandler getDeviceInfoHandler) throws MiotException {
        log(TAG, " bind start. OT SDK Version: " + getSdkVersion());
        if (this.mConnection == null) {
            this.mConnection = new MyServiceConnection(context, completedListener, getDeviceInfoHandler);
            Intent intent = new Intent(SVC_NAME);
            intent.setPackage(context.getPackageName());
            if (!context.bindService(intent, this.mConnection, 1)) {
                this.mConnection = null;
                throw new MiotException(MiotError.INTERNAL, "bindService failed");
            }
        } else {
            throw new MiotException(MiotError.INTERNAL, "already bound");
        }
    }

    public synchronized void unbind(Context context) throws MiotException {
        if (this.mConnection != null) {
            context.unbindService(this.mConnection);
            this.mConnection = null;
        } else {
            throw new MiotException(MiotError.INTERNAL, "not bind");
        }
    }

    /* loaded from: classes2.dex */
    public class MyServiceConnection implements ServiceConnection {
        private Context context;
        private GetDeviceInfoHandler getDeviceInfoHandler;
        private CompletedListener handler;
        private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.MyServiceConnection.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                Log.e(MiotHostManager.TAG, "enter Service binderDied ");
                if (MiotHostManager.this.mServiceInstance != null) {
                    MiotHostManager.this.mServiceInstance.asBinder().unlinkToDeath(MyServiceConnection.this.mDeathRecipient, 0);
                    Intent intent = new Intent(MiotHostManager.SVC_NAME);
                    intent.setPackage(MyServiceConnection.this.context.getPackageName());
                    MyServiceConnection.this.context.bindService(intent, MiotHostManager.this.mConnection, 1);
                }
            }
        };

        public MyServiceConnection(Context context, CompletedListener completedListener, GetDeviceInfoHandler getDeviceInfoHandler) {
            MiotHostManager.this = r1;
            this.context = context;
            this.handler = completedListener;
            this.getDeviceInfoHandler = getDeviceInfoHandler;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MiotHostManager.this.log(MiotHostManager.TAG, String.format("onServiceConnected: %s", componentName.getShortClassName()));
            MiotHostManager.this.mServiceInstance = IMiotHostService.Stub.asInterface(iBinder);
            CompletedListener completedListener = this.handler;
            if (completedListener != null) {
                completedListener.onSucceed(null);
            }
            try {
                iBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MiotHostManager.this.registerGetDeviceInfoHandler(this.getDeviceInfoHandler);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            MiotHostManager.this.log(MiotHostManager.TAG, String.format("onServiceDisconnected: %s", componentName.getShortClassName()));
            MiotHostManager.this.mServiceInstance = null;
            if (MiotHostManager.this.mConnection != null) {
                try {
                    this.context.unbindService(MiotHostManager.this.mConnection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MiotHostManager.this.mConnection = null;
            }
            CompletedListener completedListener = this.handler;
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED);
            }
        }
    }

    private synchronized void check() throws MiotException {
        if (this.mConnection == null || this.mServiceInstance == null) {
            throw new MiotException(MiotError.INTERNAL, "not bind");
        }
    }

    public void registerGetDeviceInfoHandler(final GetDeviceInfoHandler getDeviceInfoHandler) {
        if (getDeviceInfoHandler != null) {
            log(TAG, "registerGetDeviceInfoHandler");
            try {
                this.mServiceInstance.registerGetDeviceInfoHandler(new IGetDeviceInfoHandler.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.1
                    @Override // com.xiaomi.miot.host.service.listener.IGetDeviceInfoHandler
                    public String onRequestRouterInfo() throws RemoteException {
                        return getDeviceInfoHandler.onRequestRouterInfo();
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWifiMode(int i) throws MiotException {
        log(TAG, "setWifiMode: " + i);
        check();
        try {
            this.mServiceInstance.setWifiMode(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "setWifiMode failed: RemoteException");
        }
    }

    public void start(Device device) throws MiotException {
        log(TAG, "start");
        check();
        try {
            this.mServiceInstance.start(device);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "start failed: RemoteException");
        }
    }

    public void start(Device device, int i) throws MiotException {
        log(TAG, "start, supportRpcType = " + i);
        check();
        try {
            this.mServiceInstance.startWithRpcType(device, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "start failed: RemoteException");
        }
    }

    public void start(Device device, boolean z, int i) throws MiotException {
        log(TAG, "start, isDynamicDid = " + z + ", supportRpcType = " + i);
        check();
        try {
            this.mServiceInstance.startWithDynamicDid(device, z, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "start failed: RemoteException");
        }
    }

    public void startWithBindParams(Device device, BindParams bindParams) throws MiotException {
        log(TAG, "startWithBindParams");
        check();
        try {
            this.mServiceInstance.startWithBindParams(device, bindParams);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "startWithBindParams failed: RemoteException");
        }
    }

    public void stop() throws MiotException {
        log(TAG, "stop");
        check();
        try {
            this.mServiceInstance.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "stop failed: RemoteException");
        }
    }

    public void register(Device device, final CompletedListener completedListener, final OperationHandler operationHandler) throws MiotException {
        log(TAG, MiPushClient.COMMAND_REGISTER);
        check();
        try {
            this.mServiceInstance.register(device, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.2
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "register onSucceed");
                    try {
                        completedListener.onSucceed(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "register onFailed");
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new IOperationHandler.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.3
                @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
                public MiotError onAction(ActionInfo actionInfo) throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "onAction");
                    try {
                        return operationHandler.onAction(actionInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MiotError.INTERNAL;
                    }
                }

                @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
                public MiotError onGet(Property property) throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "onGet");
                    try {
                        return operationHandler.onGet(property);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MiotError.INTERNAL;
                    }
                }

                @Override // com.xiaomi.miot.host.service.handler.IOperationHandler
                public MiotError onSet(Property property) throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "onSet");
                    try {
                        return operationHandler.onSet(property);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return MiotError.INTERNAL;
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "register failed: RemoteException");
        }
    }

    public void unregister(Device device, final CompletedListener completedListener) throws MiotException {
        log(TAG, MiPushClient.COMMAND_UNREGISTER);
        check();
        try {
            this.mServiceInstance.unregister(device, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.4
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        completedListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "unregister failed: RemoteException");
        }
    }

    public void sendEvent(List<Property> list, final CompletedListener completedListener) throws MiotException {
        log(TAG, "sendEvent");
        check();
        if (list.size() == 0) {
            log(TAG, "no properties changed");
            return;
        }
        try {
            this.mServiceInstance.sendEvent(list, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.5
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        if (completedListener != null) {
                            completedListener.onSucceed(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        if (completedListener != null) {
                            completedListener.onFailed(miotError);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "sendEvent failed: RemoteException");
        }
    }

    public void sendMiotSpecEvent(List<Property> list, final CompletedListener completedListener) throws MiotException {
        log(TAG, "sendMiotSpecEvent");
        check();
        if (list.size() == 0) {
            log(TAG, "no properties changed");
            return;
        }
        try {
            this.mServiceInstance.sendMiotSpecEvent(list, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.6
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        try {
                            completedListener2.onSucceed(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        try {
                            completedListener2.onFailed(miotError);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "sendMiotSpecEvent failed: RemoteException");
        }
    }

    public void sendMiotSpecEventNotify(Event event, final CompletedListener completedListener) throws MiotException {
        log(TAG, "sendMiotSpecEvent");
        check();
        if (event == null) {
            log(TAG, "no event!");
            return;
        }
        try {
            this.mServiceInstance.sendMiotSpecEventNotify(event, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.7
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        if (completedListener != null) {
                            completedListener.onSucceed(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        if (completedListener != null) {
                            completedListener.onFailed(miotError);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "sendMiotSpecEventNotify failed: RemoteException");
        }
    }

    public void send(String str, String str2, final CompletedListener completedListener) throws MiotException {
        log(TAG, "sendEvent");
        check();
        try {
            this.mServiceInstance.send(str, str2, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.8
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str3) throws RemoteException {
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        try {
                            completedListener2.onSucceed(str3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    CompletedListener completedListener2 = completedListener;
                    if (completedListener2 != null) {
                        try {
                            completedListener2.onFailed(miotError);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "send failed: RemoteException");
        }
    }

    public void createSession(JSONObject jSONObject, SessionListener sessionListener) throws MiotException {
        log(TAG, "createSession not support now");
    }

    public void getVoiceCtrlAccessToken(AccessTokenListener accessTokenListener) throws MiotException {
        getAccessToken(accessTokenListener);
    }

    @Deprecated
    public void getAccessToken(final AccessTokenListener accessTokenListener) throws MiotException {
        log(TAG, "getAccessToken");
        check();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("user_ctx", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.mServiceInstance.createSession(jSONObject.toString(), new ISessionListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.9
                @Override // com.xiaomi.miot.host.service.listener.ISessionListener
                public void onSucceed(String str, String str2, int i) throws RemoteException {
                    try {
                        accessTokenListener.onSucceed(String.format("d~%s~%s", str, str2), i);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ISessionListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        accessTokenListener.onFailed(miotError);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e2) {
            e2.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "createSession failed: RemoteException");
        }
    }

    public void getBindKey(final BindKeyListener bindKeyListener) throws MiotException {
        log(TAG, "getBindKey");
        check();
        try {
            this.mServiceInstance.getBindKey(new IBindKeyListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.10
                @Override // com.xiaomi.miot.host.service.listener.IBindKeyListener
                public void onSucceed(String str, int i) throws RemoteException {
                    try {
                        bindKeyListener.onSucceed(str, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IBindKeyListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        bindKeyListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "getBindKey failed: RemoteException");
        }
    }

    public void getToken(final TokenListener tokenListener) throws MiotException {
        log(TAG, "getToken");
        check();
        try {
            this.mServiceInstance.getToken(new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.11
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        tokenListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        tokenListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "getToken failed: RemoteException");
        }
    }

    public void reset(final CompletedListener completedListener) throws MiotException {
        log(TAG, "reset");
        check();
        try {
            this.mServiceInstance.reset(new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.12
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        completedListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "reset failed: RemoteException");
        }
    }

    public void registerBindListener(final OnBindListener onBindListener, final CompletedListener completedListener) throws MiotException {
        log(TAG, "registerBindListener");
        check();
        try {
            this.mServiceInstance.registerBindListener(new IOnBindListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.13
                @Override // com.xiaomi.miot.host.service.listener.IOnBindListener
                public void onBind() throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "onBind");
                    try {
                        onBindListener.onBind();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IOnBindListener
                public void onUnBind() throws RemoteException {
                    MiotHostManager.this.log(MiotHostManager.TAG, "onUnBind");
                    try {
                        onBindListener.onUnBind();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.14
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        completedListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerBindListener failed: RemoteException");
        }
    }

    public void registerTransmitListener(final OnTransmitListener onTransmitListener) throws MiotException {
        log(TAG, "registerTransmitListener");
        check();
        try {
            this.mServiceInstance.registerTransmitListener(new IOnTransmitListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.15
                @Override // com.xiaomi.miot.host.service.listener.IOnTransmitListener
                public void onTransmit(String str) throws RemoteException {
                    OnTransmitListener onTransmitListener2 = onTransmitListener;
                    if (onTransmitListener2 != null) {
                        onTransmitListener2.onTransmit(str);
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerTransmitListener failed: RemoteException");
        }
    }

    public void registerMiotConnectedListener(final MiotConnectedListener miotConnectedListener) throws MiotException {
        log(TAG, "registerMiotConnectedListener");
        check();
        try {
            this.mServiceInstance.registerMiotConnectedListener(new IMiotConnectedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.16
                @Override // com.xiaomi.miot.host.service.listener.IMiotConnectedListener
                public void onConnected() throws RemoteException {
                    try {
                        miotConnectedListener.onConnected();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IMiotConnectedListener
                public void onDisconnected() throws RemoteException {
                    try {
                        miotConnectedListener.onDisconnected();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IMiotConnectedListener
                public void onTokenException() throws RemoteException {
                    try {
                        miotConnectedListener.onTokenException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerMiotConnectedListener failed: RemoteException");
        }
    }

    public boolean isMiotConnected() throws MiotException {
        log(TAG, "isMiotConnected");
        check();
        try {
            return this.mServiceInstance.isMiotConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "isMiotConnected failed: RemoteException");
        }
    }

    public boolean hasSetWifiStatus() throws MiotException {
        log(TAG, "hasSetWifiStatus get");
        check();
        try {
            return this.mServiceInstance.hasSetWifiStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "hasSetWifiStatus failed: RemoteException");
        }
    }

    public void checkBind(final CheckBindListener checkBindListener) throws MiotException {
        log(TAG, "checkBind");
        check();
        try {
            this.mServiceInstance.checkBind(new ICheckBindListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.17
                @Override // com.xiaomi.miot.host.service.listener.ICheckBindListener
                public void onSucceed(boolean z) throws RemoteException {
                    try {
                        checkBindListener.onSucceed(z);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICheckBindListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        checkBindListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "checkBind failed: RemoteException");
        }
    }

    public void refreshMiotInfo(String str) throws MiotException {
        log(TAG, "refreshMiotInfo");
        check();
        try {
            this.mServiceInstance.refreshMiotInfo(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "refreshMiotInfo failed: RemoteException");
        }
    }

    public void getDeviceProps(List<String> list, final CompletedListener completedListener) throws MiotException {
        log(TAG, "getDeviceProps");
        check();
        try {
            this.mServiceInstance.getDeviceProps(list, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.18
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    MiotHostManager miotHostManager = MiotHostManager.this;
                    miotHostManager.log(MiotHostManager.TAG, "getDeviceProps:" + str);
                    try {
                        if (completedListener != null) {
                            completedListener.onSucceed(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        if (completedListener != null) {
                            completedListener.onFailed(miotError);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "send failed: RemoteException");
        }
    }

    public void syncDeviceName(String str, final CompletedListener completedListener) throws MiotException {
        log(TAG, "syncDeviceName");
        if (TextUtils.isEmpty(str)) {
            log(TAG, "syncDeviceName failed, deviceName is null");
            if (completedListener != null) {
                completedListener.onFailed(MiotError.INTERNAL);
                return;
            }
            return;
        }
        check();
        try {
            this.mServiceInstance.syncDeviceName(str, new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.19
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str2) throws RemoteException {
                    try {
                        completedListener.onSucceed(str2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "syncDeviceName failed: RemoteException");
        }
    }

    public void getSyncTime(final SyncTimeListener syncTimeListener) throws MiotException {
        log(TAG, "getSyncTime");
        check();
        try {
            this.mServiceInstance.getSyncTime(new ISyncTimeListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.20
                @Override // com.xiaomi.miot.host.service.listener.ISyncTimeListener
                public void onSucceed(long j) throws RemoteException {
                    try {
                        syncTimeListener.onSucceed(j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ISyncTimeListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        syncTimeListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "getSyncTime failed: RemoteException");
        }
    }

    public void getOtLocalStatus(final CompletedListener completedListener) throws MiotException {
        log(TAG, "getOtLocalStatus");
        check();
        try {
            this.mServiceInstance.getLocalStatus(new ICompletedListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.21
                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        completedListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ICompletedListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        completedListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "getOtLocalStatus failed: RemoteException");
        }
    }

    public void sendGatewayMessage(String str, final GatewayMessageListener gatewayMessageListener) throws MiotException {
        log(TAG, "sendGatewayMessage");
        check();
        try {
            this.mServiceInstance.sendGatewayMessage(str, new IGatewayMessageListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.22
                @Override // com.xiaomi.miot.host.service.listener.IGatewayMessageListener
                public void onSucceed(String str2) throws RemoteException {
                    try {
                        gatewayMessageListener.onSucceed(str2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IGatewayMessageListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        gatewayMessageListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "sendGatewayMessage failed: RemoteException");
        }
    }

    public void registerGatewayReceiveListener(final GatewayMessageListener gatewayMessageListener) throws MiotException {
        log(TAG, "registerGatewayReceiveListener");
        check();
        try {
            this.mServiceInstance.registerGatewayReceiveListener(new IGatewayMessageListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.23
                @Override // com.xiaomi.miot.host.service.listener.IGatewayMessageListener
                public void onSucceed(String str) throws RemoteException {
                    try {
                        gatewayMessageListener.onSucceed(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.IGatewayMessageListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    try {
                        gatewayMessageListener.onFailed(miotError);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerGatewayReceiveListener failed: RemoteException");
        }
    }

    public void registerMiotLogListener(MiotLogListener miotLogListener) throws MiotException {
        log(TAG, "registerMiotLogListener");
        check();
        this.mMiotLogListener = miotLogListener;
        try {
            this.mServiceInstance.registerMiotLogListener(new IMiotLogListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.24
                @Override // com.xiaomi.miot.host.service.listener.IMiotLogListener
                public void log(int i, String str, String str2) throws RemoteException {
                    try {
                        if (MiotHostManager.this.mMiotLogListener != null) {
                            MiotHostManager.this.mMiotLogListener.log(i, str, str2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerMiotLogListener failed: RemoteException");
        }
    }

    public void registerMiotInternalCommandListener(final MiotInternalCommandListener miotInternalCommandListener) throws MiotException {
        log(TAG, "registerMiotInternalCommandListener");
        check();
        try {
            this.mServiceInstance.registerMiotInternalCommandListener(new IMiotInternalCommandListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.25
                @Override // com.xiaomi.miot.host.service.listener.IMiotInternalCommandListener
                public void clearUserData() throws RemoteException {
                    try {
                        if (miotInternalCommandListener != null) {
                            miotInternalCommandListener.clearUserData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerMiotInternalCommandListener failed: RemoteException");
        }
    }

    public void registerMiotTransparentRequestListener(List<String> list, final ITransparentListener.Stub stub) throws MiotException {
        log(TAG, "registerMiotTransparentRequestListener");
        check();
        try {
            this.mServiceInstance.registerMiotTransparentListener(list, new ITransparentListener.Stub() { // from class: com.xiaomi.miot.host.manager.MiotHostManager.26
                @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                public void onMessage(String str) throws RemoteException {
                    MiotHostManager miotHostManager = MiotHostManager.this;
                    miotHostManager.log(MiotHostManager.TAG, "get TransparentRequest: " + str);
                    ITransparentListener.Stub stub2 = stub;
                    if (stub2 != null) {
                        stub2.onMessage(str);
                    }
                }

                @Override // com.xiaomi.miot.host.service.listener.ITransparentListener
                public void onFailed(MiotError miotError) throws RemoteException {
                    MiotHostManager miotHostManager = MiotHostManager.this;
                    miotHostManager.log(MiotHostManager.TAG, "get TransparentRequest error: " + miotError);
                    ITransparentListener.Stub stub2 = stub;
                    if (stub2 != null) {
                        stub2.onFailed(miotError);
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "registerMiotTransparentRequestListener failed: RemoteException");
        }
    }

    public void sendTransparentMessage(String str, ICompletedListener.Stub stub) throws MiotException {
        log(TAG, "sendTransparentMessage");
        check();
        try {
            this.mServiceInstance.sendTransParentMessage(str, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new MiotException(MiotError.INTERNAL, "sendTransparentMessage failed: RemoteException");
        }
    }

    public void log(String str, String str2) {
        Log.d(str, str2);
        MiotLogListener miotLogListener = this.mMiotLogListener;
        if (miotLogListener != null) {
            miotLogListener.log(3, str, str2);
        }
    }
}
