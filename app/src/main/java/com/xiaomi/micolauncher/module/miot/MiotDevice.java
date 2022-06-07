package com.xiaomi.micolauncher.module.miot;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.miot.defined.MiotPluginsAutomation;
import com.xiaomi.miot.host.lan.impl.codec.internal.MiotInfo;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.device.DiscoveryType;
import com.xiaomi.miot.typedef.device.operable.DeviceOperable;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.CompletedListener;
import com.xiaomi.miot.typedef.urn.DeviceType;
import com.xiaomi.miotse.MiotSecureElementManager;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class MiotDevice extends DeviceOperable {
    private static final DeviceType a = new DeviceType("miot-spec", "speaker", "00000A01");

    public MiotDevice(Context context) {
        super(a);
        setManufacturer("xiaomi");
        setMacAddress(WiFiUtil.getMacAddress());
        setDeviceId(SystemSetting.getMiotDeviceId());
        if (context.getPackageManager().hasSystemFeature(MiotDeviceManager.SE_HARDWARE_FEATURE)) {
            MiotSecureElementManager miotSecureElementManager = (MiotSecureElementManager) context.getSystemService(MiotDeviceManager.SE_ELEMENT_SERVICE);
            if (miotSecureElementManager != null) {
                setDevCert(miotSecureElementManager.getDevCert());
                setManuCert(miotSecureElementManager.getManuCert());
            }
        } else {
            setMiotToken(SystemSetting.getMiotToken());
        }
        setSn(Constants.getSn());
        L.miot.d("miot token info mac:%s did: %s token:%s", getMacAddress(), getDeviceId(), getMiotToken());
        setMiotInfo(a(context));
        ArrayList arrayList = new ArrayList();
        arrayList.add(DiscoveryType.MIOT);
        setDiscoveryTypes(arrayList);
        MibrainConfig mibrainConfig = MibrainConfig.getMibrainConfig(context);
        setFriendlyName(mibrainConfig.iotFriendlyName);
        setModelName(mibrainConfig.deviceModel);
        L.miot.i("iot friendly name %s, device model %s", mibrainConfig.iotFriendlyName, mibrainConfig.deviceModel);
        addService(MiotPluginsAutomation.getInstance().getMicrophone());
        addService(MiotPluginsAutomation.getInstance().getSpeaker());
        addService(MiotPluginsAutomation.getInstance().getPlayer());
        addService(MiotPluginsAutomation.getInstance().getCamera());
        addService(MiotPluginsAutomation.getInstance().getGateway());
        if (Hardware.isSupportSpec()) {
            addService(MiotPluginsAutomation.getInstance().getClock());
            addService(MiotPluginsAutomation.getInstance().getIntelligentSpeaker());
            addService(MiotPluginsAutomation.getInstance().getTvSwitch());
            return;
        }
        initializeInstanceID();
    }

    public void register(CompletedListener completedListener) throws MiotException {
        MiotHostManager.getInstance().register(this, completedListener, this);
    }

    public void unregister(CompletedListener completedListener) throws MiotException {
        MiotHostManager.getInstance().unregister(this, completedListener);
    }

    public void sendEvents() throws MiotException {
        MiotHostManager.getInstance().sendMiotSpecEvent(super.getChangedProperties(), new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDevice.1
            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onSucceed(String str) {
                L.miot.d("onSucceed, result: %s", str);
            }

            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onFailed(MiotError miotError) {
                L.miot.e("onFailed: %s", miotError.toString());
            }
        });
    }

    public void send(String str, String str2) throws MiotException {
        MiotHostManager.getInstance().send(str, str2, new CompletedListener() { // from class: com.xiaomi.micolauncher.module.miot.MiotDevice.2
            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onSucceed(String str3) {
                L.miot.i("onSucceed: %s", str3);
            }

            @Override // com.xiaomi.miot.typedef.listener.CompletedListener
            public void onFailed(MiotError miotError) {
                L.miot.e("onFailed: %s", miotError);
            }
        });
    }

    private String a(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SchemaActivity.KEY_METHOD, MiotInfo.REQUEST_METHOD);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("hw_ver", "Android");
            jSONObject2.put("fw_ver", SystemSetting.getRomVersion());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("rssi", connectionInfo.getRssi());
            jSONObject3.put("ssid", WiFiUtil.stripSSID(connectionInfo.getSSID()));
            jSONObject3.put("bssid", connectionInfo.getBSSID());
            jSONObject2.put("ap", jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("localIp", a(dhcpInfo.ipAddress));
            jSONObject4.put("mask", a(dhcpInfo.netmask));
            jSONObject4.put("gw", a(dhcpInfo.gateway));
            jSONObject2.put("netif", jSONObject4);
            jSONObject.put("params", jSONObject2);
            return jSONObject.toString();
        } catch (JSONException e) {
            L.miot.e("getMiotInfo", e);
            return null;
        }
    }

    private static String a(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }
}
