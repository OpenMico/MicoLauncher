package com.xiaomi.miot.host.runtime;

import android.util.Log;
import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
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
import java.util.List;

/* loaded from: classes2.dex */
public class HostRuntimeManager {
    private static final String TAG = "HostRuntimeManager";
    private boolean initialized;
    private HostRuntime runtime;

    public synchronized void initialize(HostRuntime hostRuntime) {
        if (this.initialized) {
            Log.d(TAG, "initialize: already initialized");
            return;
        }
        this.runtime = hostRuntime;
        this.runtime.initialize();
        this.initialized = true;
    }

    public synchronized void destroy() throws MiotException {
        checkInitialized();
        this.runtime.destroy();
        this.runtime = null;
        this.initialized = false;
    }

    public synchronized void registerGetDeviceInfoHandler(GetDeviceInfoHandler getDeviceInfoHandler) throws MiotException {
        checkInitialized();
        this.runtime.registerGetDeviceInfoHandler(getDeviceInfoHandler);
    }

    public synchronized void setWifiMode(int i) throws MiotException {
        checkInitialized();
        this.runtime.setWifiMode(i);
    }

    public synchronized void setMiioClientLogLevel(int i, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        this.runtime.setMiioClientLogLevel(i, completedListener);
    }

    public synchronized void start(Device device, boolean z, int i, BindParams bindParams) throws MiotException {
        checkInitialized();
        this.runtime.start(device, z, i, bindParams);
    }

    public synchronized void stop() throws MiotException {
        checkInitialized();
        this.runtime.stop();
    }

    public synchronized void register(Device device, CompletedListener completedListener, OperationHandler operationHandler) throws MiotException {
        checkInitialized();
        getDefaultRuntime().register(device, completedListener, operationHandler);
    }

    public synchronized void unregister(Device device) throws MiotException {
        checkInitialized();
        getDefaultRuntime().unregister(device);
    }

    public synchronized void sendEvents(List<Property> list, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().sendEvent(list, completedListener);
    }

    public synchronized void sendMiotSpecEvent(List<Property> list, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().sendMiotSpecEvent(list, completedListener);
    }

    public synchronized void sendMiotSpecEventNotify(Event event, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().sendMiotSpecEventNotify(event, completedListener);
    }

    public void sendMessage(String str, String str2, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().sendMessage(str, str2, completedListener);
    }

    public void getBindKey(BindKeyListener bindKeyListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().getBindKey(bindKeyListener);
    }

    public void checkBind(CheckBindListener checkBindListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().checkBind(checkBindListener);
    }

    public void getToken(CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().getToken(completedListener);
    }

    private HostRuntime getDefaultRuntime() throws MiotException {
        HostRuntime hostRuntime = this.runtime;
        if (hostRuntime != null) {
            return hostRuntime;
        }
        throw new MiotException(MiotError.INTERNAL, "HostRuntime not found");
    }

    private void checkInitialized() throws MiotException {
        if (!this.initialized || this.runtime == null) {
            throw new MiotException(MiotError.INTERNAL, "Not initialized");
        }
    }

    public void getDeviceProps(List<String> list, CompletedListener completedListener) throws MiotException {
        checkInitialized();
        getDefaultRuntime().getDeviceProps(list, completedListener);
    }

    public void createSession(String str, SessionListener sessionListener) throws MiotException {
        getDefaultRuntime().createSession(str, sessionListener);
    }

    public void registerOnBindListener(OnBindListener onBindListener) throws MiotException {
        getDefaultRuntime().registerOnBindListener(onBindListener);
    }

    public void registerOnTransmitListener(OnTransmitListener onTransmitListener) throws MiotException {
        getDefaultRuntime().registerOnTransmitListener(onTransmitListener);
    }

    public void reset() throws MiotException {
        getDefaultRuntime().reset();
    }

    public void registerMiotConnectedListener(MiotConnectedListener miotConnectedListener) throws MiotException {
        getDefaultRuntime().registerMiotConnectedListener(miotConnectedListener);
    }

    public boolean isMiotConnected() throws MiotException {
        return getDefaultRuntime().isMiotConnected();
    }

    public boolean hasSetWifiStatus() throws MiotException {
        return getDefaultRuntime().hasSetWifiStatus();
    }

    public void setKeepLiveInterval(int i) throws MiotException {
        getDefaultRuntime().setKeepLiveInterval(i);
    }

    public void getSyncTime(SyncTimeListener syncTimeListener) throws MiotException {
        getDefaultRuntime().getSyncTime(syncTimeListener);
    }

    public void getLocalStatus(CompletedListener completedListener) throws MiotException {
        getDefaultRuntime().getLocalStatus(completedListener);
    }

    public void refreshMiotInfo(String str) throws MiotException {
        getDefaultRuntime().refreshMiotInfo(str);
    }

    public void syncDeviceName(String str, CompletedListener completedListener) throws MiotException {
        getDefaultRuntime().syncDeviceName(str, completedListener);
    }

    public void sendGatewayMessage(String str, GatewayMessageListener gatewayMessageListener) throws MiotException {
        getDefaultRuntime().sendGatewayMessage(str, gatewayMessageListener);
    }

    public void registerGatewayReceiveListener(GatewayMessageListener gatewayMessageListener) throws MiotException {
        getDefaultRuntime().registerGatewayReceiveListener(gatewayMessageListener);
    }

    public void registerMiotInternalCommandListener(MiotInternalCommandListener miotInternalCommandListener) throws MiotException {
        getDefaultRuntime().registerMiotInternalCommandListener(miotInternalCommandListener);
    }

    public void registerMiotTransparentListener(List<String> list, TransparentListener transparentListener) throws MiotException {
        getDefaultRuntime().registerMiotTransparentListener(list, transparentListener);
    }

    public void sendTransparentMessage(String str, CompletedListener completedListener) throws MiotException {
        getDefaultRuntime().sendTransparentMessage(str, completedListener);
    }
}
