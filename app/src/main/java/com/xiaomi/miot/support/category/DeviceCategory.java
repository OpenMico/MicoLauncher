package com.xiaomi.miot.support.category;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DeviceCategory {
    private String categoryName;
    private List<DeviceInfoWrapper> devices;

    public DeviceCategory(String str) {
        this.categoryName = str;
    }

    public void addDeviceWrapper(DeviceInfoWrapper deviceInfoWrapper) {
        if (deviceInfoWrapper != null) {
            if (this.devices == null) {
                this.devices = new ArrayList(4);
            }
            this.devices.add(deviceInfoWrapper);
        }
    }

    public DeviceCategory() {
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCateGoryName(String str) {
        this.categoryName = str;
    }

    public List<DeviceInfoWrapper> getDevices() {
        return this.devices;
    }
}
