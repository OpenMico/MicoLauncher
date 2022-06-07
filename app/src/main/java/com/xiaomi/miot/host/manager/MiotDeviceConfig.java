package com.xiaomi.miot.host.manager;

import com.xiaomi.miot.typedef.device.DiscoveryType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MiotDeviceConfig {
    private String deviceId;
    private List<DiscoveryType> discoveryTypes = new ArrayList();
    private String friendlyName;
    private boolean isSmartConfig;
    private String macAddress;
    private String manufacturer;
    private String miotInfo;
    private String miotToken;
    private String modelName;
    private String sn;

    public void addDiscoveryType(DiscoveryType discoveryType) {
        this.discoveryTypes.add(discoveryType);
    }

    public List<DiscoveryType> discoveryTypes() {
        return this.discoveryTypes;
    }

    public String friendlyName() {
        return this.friendlyName;
    }

    public void friendlyName(String str) {
        this.friendlyName = str;
    }

    public void deviceId(String str) {
        this.deviceId = str;
    }

    public void sn(String str) {
        this.sn = str;
    }

    public String sn() {
        return this.sn;
    }

    public String deviceId() {
        return this.deviceId;
    }

    public void macAddress(String str) {
        this.macAddress = str;
    }

    public String macAddress() {
        return this.macAddress;
    }

    public void miotToken(String str) {
        this.miotToken = str;
    }

    public String miotToken() {
        return this.miotToken;
    }

    public void miotInfo(String str) {
        this.miotInfo = str;
    }

    public String miotInfo() {
        return this.miotInfo;
    }

    public void manufacturer(String str) {
        this.manufacturer = str;
    }

    public String manufacturer() {
        return this.manufacturer;
    }

    public void modelName(String str) {
        this.modelName = str;
    }

    public String modelName() {
        return this.modelName;
    }

    public boolean isSmartConfig() {
        return this.isSmartConfig;
    }

    public void setSmartConfig(boolean z) {
        this.isSmartConfig = z;
    }
}
