package com.xiaomi.mesh.provision;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.mesh.provision.MiotDeviceScanResult;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotBindDevicesResult implements Parcelable {
    public static final Parcelable.Creator<MiotBindDevicesResult> CREATOR = new Parcelable.Creator<MiotBindDevicesResult>() { // from class: com.xiaomi.mesh.provision.MiotBindDevicesResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotBindDevicesResult createFromParcel(Parcel parcel) {
            return new MiotBindDevicesResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiotBindDevicesResult[] newArray(int i) {
            return new MiotBindDevicesResult[i];
        }
    };
    private int id;
    private String method;
    private ParamsBean params;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiotBindDevicesResult() {
    }

    protected MiotBindDevicesResult(Parcel parcel) {
        this.id = parcel.readInt();
        this.method = parcel.readString();
        this.params = (ParamsBean) parcel.readParcelable(ParamsBean.class.getClassLoader());
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

    public ParamsBean getParams() {
        return this.params;
    }

    public void setParams(ParamsBean paramsBean) {
        this.params = paramsBean;
    }

    public String toString() {
        return "MiotBindDevicesResult{id=" + this.id + ", method='" + this.method + "', params=" + this.params + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.method);
        parcel.writeParcelable(this.params, i);
    }

    /* loaded from: classes3.dex */
    public static class ParamsBean implements Parcelable {
        public static final Parcelable.Creator<ParamsBean> CREATOR = new Parcelable.Creator<ParamsBean>() { // from class: com.xiaomi.mesh.provision.MiotBindDevicesResult.ParamsBean.1
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
        private List<BaseBean> failed;
        private List<BaseBean> succeeded;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ParamsBean() {
        }

        protected ParamsBean(Parcel parcel) {
            this.succeeded = new ArrayList();
            this.failed = new ArrayList();
            parcel.readList(this.succeeded, BaseBean.class.getClassLoader());
            parcel.readList(this.failed, BaseBean.class.getClassLoader());
        }

        public List<BaseBean> getSucceeded() {
            return this.succeeded;
        }

        public void setSucceeded(List<BaseBean> list) {
            this.succeeded = list;
        }

        public List<BaseBean> getFailed() {
            return this.failed;
        }

        public void setFailed(List<BaseBean> list) {
            this.failed = list;
        }

        public String toString() {
            return "ParamsBean{succeeded=" + this.succeeded + ", failed=" + this.failed + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeList(this.succeeded);
            parcel.writeList(this.failed);
        }

        /* loaded from: classes3.dex */
        public static class BaseBean implements Parcelable {
            public static final Parcelable.Creator<BaseBean> CREATOR = new Parcelable.Creator<BaseBean>() { // from class: com.xiaomi.mesh.provision.MiotBindDevicesResult.ParamsBean.BaseBean.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BaseBean createFromParcel(Parcel parcel) {
                    return new BaseBean(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BaseBean[] newArray(int i) {
                    return new BaseBean[i];
                }
            };
            private String devssid;
            private String did;
            private String friendly_name;
            private String mac;
            private int miotType;
            private String model;
            private String status;
            private String uuid;
            private String wifiMac;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public BaseBean() {
            }

            protected BaseBean(Parcel parcel) {
                this.friendly_name = parcel.readString();
                this.uuid = parcel.readString();
                this.mac = parcel.readString();
                this.devssid = parcel.readString();
                this.model = parcel.readString();
                this.did = parcel.readString();
                this.status = parcel.readString();
                this.miotType = parcel.readInt();
                this.wifiMac = parcel.readString();
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

            public String getMac() {
                return this.mac;
            }

            public void setMac(String str) {
                this.mac = str;
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

            public String getDid() {
                return this.did;
            }

            public void setDid(String str) {
                this.did = str;
            }

            public String getStatus() {
                return this.status;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public int getMiotType() {
                return this.miotType;
            }

            public void setMiotType(int i) {
                this.miotType = i;
            }

            public String toString() {
                return "BaseBean{friendly_name='" + this.friendly_name + "', uuid='" + this.uuid + "', mac='" + this.mac + "', devssid='" + this.devssid + "', model='" + this.model + "', did='" + this.did + "', status='" + this.status + "', miotType=" + MiotDeviceScanResult.ParamsBean.IOT_DEVICE_TYPE.get(Integer.valueOf(this.miotType)) + '}';
            }

            public void setWifiMac(String str) {
                this.wifiMac = str;
            }

            public String getWifiMac() {
                return this.wifiMac;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(this.friendly_name);
                parcel.writeString(this.uuid);
                parcel.writeString(this.mac);
                parcel.writeString(this.devssid);
                parcel.writeString(this.model);
                parcel.writeString(this.did);
                parcel.writeString(this.status);
                parcel.writeInt(this.miotType);
                parcel.writeString(this.wifiMac);
            }
        }
    }
}
