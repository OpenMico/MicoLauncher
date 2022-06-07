package com.xiaomi.miot.support.category;

import androidx.annotation.NonNull;
import com.xiaomi.miot.support.MiotManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class HomeCategory {
    private Map<String, DeviceCategory> deviceCategories;
    private String homeId;
    private String homeName;
    private int homeSharedFlag;
    private boolean localDeviceHome;

    public HomeCategory() {
    }

    public HomeCategory(String str, String str2, int i) {
        this.homeId = str;
        this.homeName = str2;
        this.homeSharedFlag = i;
        this.localDeviceHome = Objects.equals(str, MiotManager.getHostDeviceHomeId());
    }

    @NonNull
    public String getHomeId() {
        return this.homeId;
    }

    public void setHomeId(String str) {
        this.homeId = str;
    }

    public String getHomeName() {
        return this.homeName;
    }

    public void setHomeName(String str) {
        this.homeName = str;
    }

    public boolean isLocalDeviceHome() {
        return this.localDeviceHome;
    }

    public void setLocalDeviceHome(boolean z) {
        this.localDeviceHome = z;
    }

    public int getHomeSharedFlag() {
        return this.homeSharedFlag;
    }

    public void setHomeSharedFlag(int i) {
        this.homeSharedFlag = i;
    }

    public synchronized Map<String, DeviceCategory> getDeviceCategories() {
        if (this.deviceCategories == null) {
            this.deviceCategories = new HashMap();
        }
        return this.deviceCategories;
    }

    public void addCategoryDevice(String str, DeviceInfoWrapper deviceInfoWrapper) {
        Map<String, DeviceCategory> deviceCategories = getDeviceCategories();
        DeviceCategory deviceCategory = deviceCategories.get(str);
        if (deviceCategory == null) {
            deviceCategory = new DeviceCategory();
            deviceCategory.setCateGoryName(str);
            deviceCategories.put(str, deviceCategory);
        }
        deviceCategory.addDeviceWrapper(deviceInfoWrapper);
    }
}
