package com.xiaomi.mesh.provision;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class MiotDeviceScanResult implements Parcelable {
    public static final Parcelable.Creator<MiotDeviceScanResult> CREATOR = new Parcelable.Creator<MiotDeviceScanResult>() { // from class: com.xiaomi.mesh.provision.MiotDeviceScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotDeviceScanResult createFromParcel(Parcel parcel) {
            return new MiotDeviceScanResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotDeviceScanResult[] newArray(int i) {
            return new MiotDeviceScanResult[i];
        }
    };
    public static final int DUAL_DEVICE = 2;
    public static final int MESH_DEVICE = 1;
    public static final int SINGLE_MODE = 0;
    private int id;
    private String method;
    private List<ParamsBean> params;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotDeviceScanResult() {
    }

    protected MiotDeviceScanResult(Parcel parcel) {
        this.id = parcel.readInt();
        this.method = parcel.readString();
        this.params = new ArrayList();
        parcel.readList(this.params, ParamsBean.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.method);
        parcel.writeList(this.params);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public List<ParamsBean> getParams() {
        return this.params;
    }

    public void setParams(List<ParamsBean> list) {
        this.params = list;
    }

    /* loaded from: classes3.dex */
    public static class ParamsBean implements Parcelable {
        public static final Parcelable.Creator<ParamsBean> CREATOR = new Parcelable.Creator<ParamsBean>() { // from class: com.xiaomi.mesh.provision.MiotDeviceScanResult.ParamsBean.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParamsBean createFromParcel(Parcel parcel) {
                return new ParamsBean(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParamsBean[] newArray(int i) {
                return new ParamsBean[i];
            }
        };
        public static final Map IOT_DEVICE_TYPE = new HashMap() { // from class: com.xiaomi.mesh.provision.MiotDeviceScanResult.ParamsBean.2
            {
                put(0, "SINGLE_MODE");
                put(1, "MESH_DEVICE");
                put(2, "DUAL_DEVICE");
            }
        };
        private String devssid;
        private String friendly_name;
        private String mac;
        private String model;
        private int pid;
        private int type;
        private String uuid;
        private String wifi_mac;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected ParamsBean(Parcel parcel) {
            this.friendly_name = parcel.readString();
            this.uuid = parcel.readString();
            this.pid = parcel.readInt();
            this.mac = parcel.readString();
            this.model = parcel.readString();
            this.devssid = parcel.readString();
            this.type = parcel.readInt();
            this.wifi_mac = parcel.readString();
        }

        public ParamsBean() {
        }

        public String getFriendly_name() {
            return this.friendly_name;
        }

        public void setFriendly_name(String str) {
            this.friendly_name = str;
        }

        public String getUuid() {
            return this.uuid;
        }

        public void setUuid(String str) {
            this.uuid = str;
        }

        public int getPid() {
            return this.pid;
        }

        public void setPid(int i) {
            this.pid = i;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String str) {
            this.mac = str;
        }

        public String getModel() {
            return this.model;
        }

        public void setModel(String str) {
            this.model = str;
        }

        public String getDevssid() {
            return this.devssid;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public String getWifi_mac() {
            return this.wifi_mac;
        }

        public void setWifi_mac(String str) {
            this.wifi_mac = str;
        }

        public void setDevssid(String str) {
            this.devssid = str;
        }

        public String toString() {
            return "ParamsBean{friendly_name='" + this.friendly_name + "', uuid='" + this.uuid + "', pid=" + this.pid + ", mac='" + this.mac + "', model='" + this.model + "', devssid='" + this.devssid + "', type=" + IOT_DEVICE_TYPE.get(Integer.valueOf(this.type)) + ", wifi_mac='" + this.wifi_mac + "'}";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.friendly_name);
            parcel.writeString(this.uuid);
            parcel.writeInt(this.pid);
            parcel.writeString(this.mac);
            parcel.writeString(this.model);
            parcel.writeString(this.devssid);
            parcel.writeInt(this.type);
            parcel.writeString(this.wifi_mac);
        }
    }
}
