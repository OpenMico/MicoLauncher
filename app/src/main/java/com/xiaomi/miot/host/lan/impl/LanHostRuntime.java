package com.xiaomi.miot.host.lan.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.ouyang.network.monitor.NetworkChangeListener;
import com.ouyang.network.monitor.NetworkChangeReceiver;
import com.ouyang.network.monitor.NetworkWakeLock;
import com.umeng.analytics.pro.ai;
import com.xiaomi.miot.host.runtime.HostRuntime;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.listener.BindKeyListener;
import com.xiaomi.miot.typedef.listener.CheckBindListener;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;
import com.xiaomi.miot.typedef.listener.GetDeviceInfoHandler;
import com.xiaomi.miot.typedef.listener.MiotConnectedListener;
import com.xiaomi.miot.typedef.listener.MiotInternalCommandListener;
import com.xiaomi.miot.typedef.listener.OnBindListener;
import com.xiaomi.miot.typedef.listener.OnTransmitListener;
import com.xiaomi.miot.typedef.listener.SessionListener;
import com.xiaomi.miot.typedef.listener.SyncTimeListener;
import com.xiaomi.miot.typedef.listener.TransparentListener;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class LanHostRuntime implements NetworkChangeListener, HostRuntime, MiotConnectedListener {
    private static final String TAG = "LanHostRuntime";
    private static final long WATCH_DOG_MINUTE_TIME = 1;
    private ScheduledFuture mConnectScheduledFuture;
    private Context mContext;
    private ScheduledExecutorService mExecutorService;
    private boolean mInitialized;
    private MiotConnectedListener mMiotConnectedListener;
    private MiotHost mMiotHost;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private ScreenOnReceiver mScreenOnReceiver;
    private boolean mStart;
    private Runnable mWatchDogRunnable = new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.LanHostRuntime.1
        @Override // java.lang.Runnable
        public void run() {
            LanHostRuntime.this.doubleCheckNetwork();
        }
    };
    private ScheduledFuture mWatchDogScheduledFuture;
    private NetworkWakeLock wakeLock;

    public static HostRuntime newInstance(Context context) {
        return new LanHostRuntime(context);
    }

    private LanHostRuntime(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void initialize() {
        MiotLogUtil.d(TAG, "initialize");
        if (this.mInitialized) {
            MiotLogUtil.d(TAG, "has initialized");
            return;
        }
        this.mInitialized = true;
        this.wakeLock = new NetworkWakeLock(this.mContext, TAG);
        this.wakeLock.acquireWakeLock();
        this.mMiotHost = new MiotHost();
        this.mMiotHost.initialize(this.mContext);
        this.mMiotHost.registerMiotConnectedListener(this);
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void destroy() {
        MiotLogUtil.d(TAG, "destroy");
        if (!this.mInitialized) {
            MiotLogUtil.d(TAG, "has destroyed");
            return;
        }
        this.mInitialized = false;
        ScheduledFuture scheduledFuture = this.mConnectScheduledFuture;
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            this.mConnectScheduledFuture.cancel(true);
            this.mConnectScheduledFuture = null;
        }
        ScheduledExecutorService scheduledExecutorService = this.mExecutorService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            this.mExecutorService = null;
        }
        stop();
        this.wakeLock.releaseWakeLock();
        this.mMiotHost.destroy();
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerGetDeviceInfoHandler(GetDeviceInfoHandler getDeviceInfoHandler) {
        this.mMiotHost.registerGetDeviceInfoHandler(getDeviceInfoHandler);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void setWifiMode(int i) {
        this.mMiotHost.setWifiMode(i);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void start(Device device, boolean z, int i, BindParams bindParams) {
        MiotLogUtil.d(TAG, "start");
        this.mStart = true;
        this.mMiotHost.setDynamicDid(z);
        this.mMiotHost.setSupportRpcType(i);
        this.mMiotHost.setBindParams(bindParams);
        registerWifiReceiver();
        registerScreenOnReceiver();
        ScheduledExecutorService scheduledExecutorService = this.mExecutorService;
        if (scheduledExecutorService != null) {
            this.mWatchDogScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(this.mWatchDogRunnable, 1L, 1L, TimeUnit.MINUTES);
        }
        if (!this.mMiotHost.isStarted()) {
            startIfNecessary(0L);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void stop() {
        MiotLogUtil.d(TAG, "stop");
        stopIfNecessary();
        unregisterWifiReceiver();
        unregisterScreenOnReceiver();
        this.mStart = false;
        ScheduledFuture scheduledFuture = this.mConnectScheduledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mConnectScheduledFuture = null;
        }
        ScheduledFuture scheduledFuture2 = this.mWatchDogScheduledFuture;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
            this.mWatchDogScheduledFuture = null;
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void register(Device device, CompletedListener completedListener, OperationHandler operationHandler) {
        MiotLogUtil.d(TAG, MiPushClient.COMMAND_REGISTER);
        this.mMiotHost.register(device, completedListener, operationHandler);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void unregister(Device device) {
        MiotLogUtil.d(TAG, MiPushClient.COMMAND_UNREGISTER);
        this.mMiotHost.unregister(device);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendEvent(List<Property> list, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.sendEvents(list, completedListener);
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendMiotSpecEvent(List<Property> list, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.sendMiotSpecEvent(list, completedListener);
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendMiotSpecEventNotify(Event event, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.sendMiotSpecEventNotify(event, completedListener);
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendMessage(String str, String str2, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.sendMessage(str, str2, completedListener);
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void getDeviceProps(List<String> list, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.getDeviceProps(list, completedListener);
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void createSession(String str, SessionListener sessionListener) {
        if (this.mStart) {
            this.mMiotHost.createSession(str, sessionListener);
            doubleCheckNetwork();
        } else if (sessionListener != null) {
            sessionListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerOnBindListener(OnBindListener onBindListener) {
        this.mMiotHost.registerOnBindListener(onBindListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerOnTransmitListener(OnTransmitListener onTransmitListener) {
        this.mMiotHost.registerOnTransmitListener(onTransmitListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void getBindKey(BindKeyListener bindKeyListener) {
        if (this.mStart) {
            this.mMiotHost.getBindKey(bindKeyListener);
            doubleCheckNetwork();
        } else if (bindKeyListener != null) {
            bindKeyListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void checkBind(CheckBindListener checkBindListener) {
        if (this.mStart) {
            this.mMiotHost.checkBind(checkBindListener);
            doubleCheckNetwork();
        } else if (checkBindListener != null) {
            checkBindListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void getToken(CompletedListener completedListener) {
        MiotHost miotHost = this.mMiotHost;
        String token = miotHost != null ? miotHost.getToken() : "";
        if (completedListener == null) {
            return;
        }
        if (!TextUtils.isEmpty(token)) {
            completedListener.onSucceed(token);
        } else {
            completedListener.onFailed(MiotError.INTERNAL_NOT_INITIALIZED);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void setKeepLiveInterval(int i) {
        this.mMiotHost.setKeepLiveInterval(i);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void getSyncTime(SyncTimeListener syncTimeListener) {
        if (this.mStart) {
            this.mMiotHost.getSyncTime(syncTimeListener);
            doubleCheckNetwork();
        } else if (syncTimeListener != null) {
            syncTimeListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void getLocalStatus(CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.getLocalStatus(completedListener);
            doubleCheckNetwork();
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void refreshMiotInfo(String str) {
        if (this.mStart) {
            this.mMiotHost.refreshMiotInfo(str);
            doubleCheckNetwork();
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void setMiioClientLogLevel(int i, CompletedListener completedListener) {
        this.mMiotHost.setMiioClientLogLevel(i, completedListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void syncDeviceName(String str, CompletedListener completedListener) {
        if (this.mStart) {
            this.mMiotHost.syncDeviceName(str, completedListener);
            doubleCheckNetwork();
        } else if (completedListener != null) {
            completedListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendGatewayMessage(String str, GatewayMessageListener gatewayMessageListener) {
        if (this.mStart) {
            this.mMiotHost.sendGatewayMessage(str, gatewayMessageListener);
        } else if (gatewayMessageListener != null) {
            gatewayMessageListener.onFailed(MiotError.INTERNAL_OT_SERVICE_NOT_START);
        }
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerGatewayReceiveListener(GatewayMessageListener gatewayMessageListener) {
        this.mMiotHost.registerGatewayReceiveListener(gatewayMessageListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerMiotInternalCommandListener(MiotInternalCommandListener miotInternalCommandListener) {
        this.mMiotHost.registerMiotInternalCommandListener(miotInternalCommandListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerMiotTransparentListener(List<String> list, TransparentListener transparentListener) {
        this.mMiotHost.registerMiotTransparentListener(list, transparentListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void sendTransparentMessage(String str, CompletedListener completedListener) {
        this.mMiotHost.sendTransparentMessage(str, completedListener);
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void reset() {
        this.mMiotHost.reset();
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public void registerMiotConnectedListener(MiotConnectedListener miotConnectedListener) {
        this.mMiotConnectedListener = miotConnectedListener;
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public boolean isMiotConnected() {
        return this.mMiotHost.isMiotConnected();
    }

    @Override // com.xiaomi.miot.host.runtime.HostRuntime
    public boolean hasSetWifiStatus() {
        return this.mMiotHost.hasSetWifiStatus();
    }

    private void registerWifiReceiver() {
        if (this.mNetworkChangeReceiver == null) {
            this.mNetworkChangeReceiver = new NetworkChangeReceiver(this);
            this.mContext.registerReceiver(this.mNetworkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    private void unregisterWifiReceiver() {
        NetworkChangeReceiver networkChangeReceiver = this.mNetworkChangeReceiver;
        if (networkChangeReceiver != null) {
            this.mContext.unregisterReceiver(networkChangeReceiver);
            this.mNetworkChangeReceiver = null;
        }
    }

    private void registerScreenOnReceiver() {
        if (this.mScreenOnReceiver == null) {
            this.mScreenOnReceiver = new ScreenOnReceiver();
            this.mContext.registerReceiver(this.mScreenOnReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
        }
    }

    private void unregisterScreenOnReceiver() {
        ScreenOnReceiver screenOnReceiver = this.mScreenOnReceiver;
        if (screenOnReceiver != null) {
            this.mContext.unregisterReceiver(screenOnReceiver);
            this.mScreenOnReceiver = null;
        }
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onWifiConnected() {
        MiotLogUtil.d(TAG, "onWifiConnected");
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onWifiDisconnected() {
        MiotLogUtil.d(TAG, "onWifiDisconnected");
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onEthernetConnected() {
        MiotLogUtil.d(TAG, "onEthernetConnected");
        this.mMiotHost.sendInternetConnectEventToOt("miot_tv_ethernet_virtual_ssid");
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onEthernetDisconnected() {
        MiotLogUtil.d(TAG, "onEthernetDisconnected");
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onMobileConnected() {
        MiotLogUtil.d(TAG, "onMobileConnected");
    }

    @Override // com.ouyang.network.monitor.NetworkChangeListener
    public void onMobileDisconnected() {
        MiotLogUtil.d(TAG, "onMobileDisconnected");
    }

    private void startIfNecessary(long j) {
        MiotLogUtil.d(TAG, "startIfNecessary");
        if (!this.mStart) {
            MiotLogUtil.d(TAG, "not start");
        } else if (this.mMiotHost.isMiotConnected()) {
            MiotLogUtil.d(TAG, "already running");
        } else if (j == 0) {
            this.mMiotHost.start();
        } else {
            relayConnect("onNetworkConnected", j);
        }
    }

    private void stopIfNecessary() {
        MiotLogUtil.d(TAG, "stopIfNecessary");
        this.mMiotHost.stop();
    }

    @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
    public void onConnected() {
        MiotLogUtil.d(TAG, "Miot onConnected");
        MiotConnectedListener miotConnectedListener = this.mMiotConnectedListener;
        if (miotConnectedListener != null) {
            miotConnectedListener.onConnected();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
    public void onDisconnected() {
        MiotLogUtil.d(TAG, "Miot onDisconnected start " + this.mStart);
        MiotConnectedListener miotConnectedListener = this.mMiotConnectedListener;
        if (miotConnectedListener != null) {
            miotConnectedListener.onDisconnected();
        }
    }

    @Override // com.xiaomi.miot.typedef.listener.MiotConnectedListener
    public void onTokenException() {
        MiotLogUtil.d(TAG, "onTokenException");
        MiotConnectedListener miotConnectedListener = this.mMiotConnectedListener;
        if (miotConnectedListener != null) {
            miotConnectedListener.onTokenException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doubleCheckNetwork() {
        if (this.mStart && !this.mMiotHost.isMiotConnected()) {
            MiotLogUtil.d(TAG, "doubleCheckNetwork");
            if (isNetworkConnected()) {
                MiotLogUtil.d(TAG, "doubleCheckNetwork is connected");
                new Random().nextInt(10);
            }
        }
    }

    private boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) ? false : true;
    }

    private void relayConnect(final String str, long j) {
        if (this.mExecutorService != null) {
            ScheduledFuture scheduledFuture = this.mConnectScheduledFuture;
            if (scheduledFuture == null || scheduledFuture.isDone()) {
                MiotLogUtil.d(TAG, "relayConnect, delay " + j + ai.az);
                this.mConnectScheduledFuture = this.mExecutorService.schedule(new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.LanHostRuntime.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (LanHostRuntime.this.mStart && !LanHostRuntime.this.mMiotHost.isMiotConnected()) {
                            if (!TextUtils.isEmpty(str)) {
                                MiotLogUtil.d(LanHostRuntime.TAG, str);
                            }
                            LanHostRuntime.this.mMiotHost.stop();
                            new Thread(new Runnable() { // from class: com.xiaomi.miot.host.lan.impl.LanHostRuntime.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        Thread.sleep(1000L);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (LanHostRuntime.this.mStart && !LanHostRuntime.this.mMiotHost.isMiotConnected()) {
                                        LanHostRuntime.this.mMiotHost.start();
                                    }
                                }
                            }).start();
                        }
                    }
                }, j, TimeUnit.SECONDS);
                return;
            }
            MiotLogUtil.d(TAG, "relayConnect, has other schedule");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ScreenOnReceiver extends BroadcastReceiver {
        private ScreenOnReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "android.intent.action.SCREEN_ON")) {
                LanHostRuntime.this.doubleCheckNetwork();
            }
        }
    }
}
