package com.xiaomi.miot;

import com.xiaomi.mipush.sdk.Constants;
import java.util.List;

/* loaded from: classes2.dex */
public class ExhaustiveDeviceInfo {
    private List<DeviceActionInfo> actions;
    private String deviceId;
    private String homeId;
    private String homeName;
    private int homeSharedFlag;
    private String model;
    private List<DevicePropertyBriefInfo> properties;
    private String roomId;
    private String roomName;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

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

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String str) {
        this.roomName = str;
    }

    public List<DevicePropertyBriefInfo> getProperties() {
        return this.properties;
    }

    public void setProperties(List<DevicePropertyBriefInfo> list) {
        this.properties = list;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public int getHomeSharedFlag() {
        return this.homeSharedFlag;
    }

    public void setHomeSharedFlag(int i) {
        this.homeSharedFlag = i;
    }

    public List<DeviceActionInfo> getActions() {
        return this.actions;
    }

    public void setActions(List<DeviceActionInfo> list) {
        this.actions = list;
    }

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"deviceId\":\"");
        sb.append(this.deviceId);
        sb.append("\",");
        sb.append("\"homeId\":\"");
        sb.append(this.homeId);
        sb.append("\",");
        sb.append("\"homeName\":\"");
        sb.append(this.homeName);
        sb.append("\",");
        sb.append("\"roomId\":\"");
        sb.append(this.roomId);
        sb.append("\",");
        sb.append("\"roomName\":\"");
        sb.append(this.roomName);
        sb.append("\",");
        sb.append("\"model\":\"");
        sb.append(this.model);
        sb.append("\",");
        sb.append("\"homeSharedFlag\":\"");
        sb.append(this.homeSharedFlag);
        sb.append("\",");
        sb.append("\"properties\":[");
        List<DevicePropertyBriefInfo> list = this.properties;
        if (list != null && !list.isEmpty()) {
            for (DevicePropertyBriefInfo devicePropertyBriefInfo : this.properties) {
                sb.append(devicePropertyBriefInfo.toString());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("],");
        sb.append("\"actions\":[");
        List<DeviceActionInfo> list2 = this.actions;
        if (list2 != null && !list2.isEmpty()) {
            for (DeviceActionInfo deviceActionInfo : this.actions) {
                sb.append(deviceActionInfo.toString());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]}");
        return sb.toString();
    }
}
