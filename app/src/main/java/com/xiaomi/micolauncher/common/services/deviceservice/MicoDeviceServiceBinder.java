package com.xiaomi.micolauncher.common.services.deviceservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.RemoteException;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface;
import com.xiaomi.mico.device.service.iface.IUbusRelayCallback;
import com.xiaomi.micolauncher.api.ErrorCode;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.statemodel.HomeModel;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.common.ubus.UbusManager;
import com.xiaomi.micolauncher.common.ubus.handlers.InternalHandler;
import java.util.Locale;

/* loaded from: classes3.dex */
public class MicoDeviceServiceBinder extends IMicoDeviceServiceInterface.Stub {
    private Context a;

    public MicoDeviceServiceBinder(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    @SuppressLint({"MissingPermission"})
    public String getSN() throws RemoteException {
        String sn = Constants.getSn();
        L.base.i("getSN Serial %s", sn);
        return sn;
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getMAC() throws RemoteException {
        String macAddress = WiFiUtil.getMacAddress();
        L.base.i("getMAC mac address %s", macAddress);
        return macAddress;
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getHardware() throws RemoteException {
        String name = Hardware.current(this.a).getName();
        L.base.i("getHardware %s", name);
        return name;
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getROMVersion() throws RemoteException {
        return SystemSetting.getRomVersion();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getROMChannel() throws RemoteException {
        return SystemSetting.getRomChannel();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getMIBrainLevel() throws RemoteException {
        return SystemSetting.getMiBrainLevel();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public void setMIBrainLevel(String str) throws RemoteException {
        L.base.e("Not implemented yet.");
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getMiotDID() throws RemoteException {
        return SystemSetting.getMiotDeviceId();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getLocale() throws RemoteException {
        return Locale.getDefault().toString();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getBssid() throws RemoteException {
        return WiFiUtil.getBSSID(this.a);
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public void ubusRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
        try {
            if (!a(str, str2, iUbusRelayCallback)) {
                String handle = UbusManager.getInstance().handle(this.a, str, str2, str3);
                L.base.d("ubus ubusRelay response: %s", handle);
                iUbusRelayCallback.call(handle);
            }
        } catch (Exception e) {
            L.base.e("remote exception", e);
        }
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public void deviceRelay(String str, String str2, String str3, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
        try {
            L.base.d("ubus deviceRelay %s %s %s", str, str2, str3);
            if (!a(str, str2, iUbusRelayCallback)) {
                String handle = UbusManager.getInstance().handle(this.a, str, str2, str3);
                L.base.d("ubus deviceRelay response: %s", handle);
                iUbusRelayCallback.call(handle);
            }
        } catch (Exception e) {
            L.base.e("remote exception", e);
        }
    }

    private boolean a(String str, String str2, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
        if (HomeModel.getInstance().isInitialized() || UbusManager.getInstance().canHandleBeforeInit(str, str2)) {
            return false;
        }
        UBus.Response response = new UBus.Response();
        response.code = ErrorCode.UNKNOWN_ERROR.getCode();
        response.info = "device has not initialized";
        iUbusRelayCallback.call(response.toString());
        return true;
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public void unbindDevice(String str, IUbusRelayCallback iUbusRelayCallback) throws RemoteException {
        try {
            L.base.d("unbindDevice id %s", str);
            String handle = UbusManager.getInstance().handle(this.a, InternalHandler.SYNC_UNBIND_DEVICE, "POST", "device_id=" + str);
            L.base.d("unbindDevice response: %s", handle);
            if (iUbusRelayCallback != null) {
                iUbusRelayCallback.call(handle);
            }
        } catch (Exception e) {
            L.base.e("remote exception", e);
        }
    }

    private a a() {
        return new a();
    }

    @Override // com.xiaomi.mico.device.service.iface.IMicoDeviceServiceInterface
    public String getRegisterJsonExtras() {
        String json = Gsons.getGson().toJson(a());
        L.base.i("RegisterJsonExtras %s", json);
        return json;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private a() {
        }
    }
}
