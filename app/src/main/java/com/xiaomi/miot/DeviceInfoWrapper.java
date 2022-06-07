package com.xiaomi.miot;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DeviceInfoWrapper {
    private ArrayList<DeviceInfo> deviceInfoList;
    private List<ExhaustiveDeviceInfo> edDeviceInfoList;
    private ExpandDeviceInfo expandDeviceInfo;
    private ArrayList<String> freqDeviceIdList;

    public ArrayList<DeviceInfo> getDeviceInfoList() {
        return this.deviceInfoList;
    }

    public void setDeviceInfoList(ArrayList<DeviceInfo> arrayList) {
        this.deviceInfoList = arrayList;
    }

    public ExpandDeviceInfo getExpandDeviceInfo() {
        return this.expandDeviceInfo;
    }

    public void setExpandDeviceInfo(ExpandDeviceInfo expandDeviceInfo) {
        this.expandDeviceInfo = expandDeviceInfo;
    }

    public List<ExhaustiveDeviceInfo> getExhaustiveDeviceInfoList() {
        return this.edDeviceInfoList;
    }

    public void setExhaustiveDeviceInfoList(List<ExhaustiveDeviceInfo> list) {
        this.edDeviceInfoList = list;
    }

    public ArrayList<String> getFreqDeviceIdList() {
        return this.freqDeviceIdList;
    }

    public void setFreqDeviceIdList(ArrayList<String> arrayList) {
        this.freqDeviceIdList = arrayList;
    }
}
