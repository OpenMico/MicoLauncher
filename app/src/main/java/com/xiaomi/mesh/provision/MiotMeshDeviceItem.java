package com.xiaomi.mesh.provision;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;

/* loaded from: classes3.dex */
public class MiotMeshDeviceItem implements Parcelable {
    public static final String CONNECT_STATE_FAILED = "failed";
    public static final String CONNECT_STATE_SUCCEEDED = "succeeded";
    public static final Parcelable.Creator<MiotMeshDeviceItem> CREATOR = new Parcelable.Creator<MiotMeshDeviceItem>() { // from class: com.xiaomi.mesh.provision.MiotMeshDeviceItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotMeshDeviceItem createFromParcel(Parcel parcel) {
            return new MiotMeshDeviceItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotMeshDeviceItem[] newArray(int i) {
            return new MiotMeshDeviceItem[i];
        }
    };
    private String connectState;
    private String deviceName;
    private String devssid;
    private String did;
    private int miotType;
    private String model;
    private String pid;
    private boolean pullCloudFinished;
    private String roomName;
    private String type;
    private String uuid;
    private String viewUrl;
    private String wifiMac;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotMeshDeviceItem() {
    }

    public void MiotMeshDeviceItem(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, String str11, boolean z) {
        this.deviceName = str;
        this.roomName = str2;
        this.viewUrl = str3;
        this.did = str4;
        this.type = str5;
        this.pid = str6;
        this.connectState = str7;
        this.uuid = str8;
        this.devssid = str9;
        this.model = str10;
        this.miotType = i;
        this.wifiMac = str11;
        this.pullCloudFinished = z;
    }

    protected MiotMeshDeviceItem(Parcel parcel) {
        this.deviceName = parcel.readString();
        this.roomName = parcel.readString();
        this.viewUrl = parcel.readString();
        this.did = parcel.readString();
        this.type = parcel.readString();
        this.pid = parcel.readString();
        this.connectState = parcel.readString();
        this.uuid = parcel.readString();
        this.devssid = parcel.readString();
        this.model = parcel.readString();
        this.miotType = parcel.readInt();
        this.wifiMac = parcel.readString();
        this.pullCloudFinished = parcel.readInt() != 1 ? false : true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceName);
        parcel.writeString(this.roomName);
        parcel.writeString(this.viewUrl);
        parcel.writeString(this.did);
        parcel.writeString(this.type);
        parcel.writeString(this.pid);
        parcel.writeString(this.connectState);
        parcel.writeString(this.uuid);
        parcel.writeString(this.devssid);
        parcel.writeString(this.model);
        parcel.writeInt(this.miotType);
        parcel.writeString(this.wifiMac);
        parcel.writeInt(this.pullCloudFinished ? 1 : 0);
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setViewUrl(String str) {
        this.viewUrl = str;
    }

    public String getViewUrl() {
        return this.viewUrl;
    }

    public void setDid(String str) {
        this.did = str;
    }

    public String getDid() {
        return this.did;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getPid() {
        return this.pid;
    }

    public void setConnectState(String str) {
        this.connectState = str;
    }

    public String getConnectState() {
        return this.connectState;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public String getDevssid() {
        return this.devssid;
    }

    public void setDevssid(String str) {
        this.devssid = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public int getMiotType() {
        return this.miotType;
    }

    public void setMiotType(int i) {
        this.miotType = i;
    }

    public String toString() {
        return "MiotMeshDeviceItem{deviceName='" + this.deviceName + "', roomName='" + this.roomName + "', viewUrl='" + this.viewUrl + "', did='" + this.did + "', type='" + this.type + "', pid='" + this.pid + "', connectState='" + this.connectState + "', uuid='" + this.uuid + "', devssid='" + this.devssid + "', wifiMac='" + this.wifiMac + "', model='" + this.model + "', pullCloudFinished='" + this.pullCloudFinished + "', miotType=" + MiotDeviceScanResult.ParamsBean.IOT_DEVICE_TYPE.get(Integer.valueOf(this.miotType)) + '}';
    }

    public void setWifiMac(String str) {
        this.wifiMac = str;
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public boolean isPullCloudFinished() {
        return this.pullCloudFinished;
    }

    public void setPullCloudFinished(boolean z) {
        this.pullCloudFinished = z;
    }
}
