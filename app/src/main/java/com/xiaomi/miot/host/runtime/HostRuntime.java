package com.xiaomi.miot.host.runtime;

import com.xiaomi.miot.typedef.device.BindParams;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Event;
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
public interface HostRuntime {
    void checkBind(CheckBindListener checkBindListener);

    void createSession(String str, SessionListener sessionListener);

    void destroy();

    void getBindKey(BindKeyListener bindKeyListener);

    void getDeviceProps(List<String> list, CompletedListener completedListener);

    void getLocalStatus(CompletedListener completedListener);

    void getSyncTime(SyncTimeListener syncTimeListener);

    void getToken(CompletedListener completedListener);

    boolean hasSetWifiStatus();

    void initialize();

    boolean isMiotConnected();

    void refreshMiotInfo(String str);

    void register(Device device, CompletedListener completedListener, OperationHandler operationHandler);

    void registerGatewayReceiveListener(GatewayMessageListener gatewayMessageListener);

    void registerGetDeviceInfoHandler(GetDeviceInfoHandler getDeviceInfoHandler);

    void registerMiotConnectedListener(MiotConnectedListener miotConnectedListener);

    void registerMiotInternalCommandListener(MiotInternalCommandListener miotInternalCommandListener);

    void registerMiotTransparentListener(List<String> list, TransparentListener transparentListener);

    void registerOnBindListener(OnBindListener onBindListener);

    void registerOnTransmitListener(OnTransmitListener onTransmitListener);

    void reset();

    void sendEvent(List<Property> list, CompletedListener completedListener);

    void sendGatewayMessage(String str, GatewayMessageListener gatewayMessageListener);

    void sendMessage(String str, String str2, CompletedListener completedListener);

    void sendMiotSpecEvent(List<Property> list, CompletedListener completedListener);

    void sendMiotSpecEventNotify(Event event, CompletedListener completedListener);

    void sendTransparentMessage(String str, CompletedListener completedListener);

    void setKeepLiveInterval(int i);

    void setMiioClientLogLevel(int i, CompletedListener completedListener);

    void setWifiMode(int i);

    void start(Device device, boolean z, int i, BindParams bindParams);

    void stop();

    void syncDeviceName(String str, CompletedListener completedListener);

    void unregister(Device device);
}
