package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.DeviceInfo;

/* loaded from: classes3.dex */
public class DeviceInfoWrapper implements Parcelable {
    public static final Parcelable.Creator<DeviceInfoWrapper> CREATOR = new Parcelable.Creator<DeviceInfoWrapper>() { // from class: com.xiaomi.miot.support.category.DeviceInfoWrapper.1
        @Override // android.os.Parcelable.Creator
        public DeviceInfoWrapper createFromParcel(Parcel parcel) {
            return new DeviceInfoWrapper(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DeviceInfoWrapper[] newArray(int i) {
            return new DeviceInfoWrapper[i];
        }
    };
    private DeviceInfo deviceInfo;
    private DeviceItemInfo deviceItemInfo;
    private ModelInfo modelInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DeviceInfoWrapper() {
    }

    public DeviceInfoWrapper(DeviceInfo deviceInfo, ModelInfo modelInfo, DeviceItemInfo deviceItemInfo) {
        this.deviceInfo = deviceInfo;
        this.modelInfo = modelInfo;
        this.deviceItemInfo = deviceItemInfo;
    }

    protected DeviceInfoWrapper(Parcel parcel) {
        this.deviceInfo = (DeviceInfo) parcel.readParcelable(DeviceInfo.class.getClassLoader());
        this.modelInfo = (ModelInfo) parcel.readParcelable(ModelInfo.class.getClassLoader());
        this.deviceItemInfo = (DeviceItemInfo) parcel.readParcelable(DeviceItemInfo.class.getClassLoader());
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public ModelInfo getModelInfo() {
        return this.modelInfo;
    }

    public DeviceItemInfo getDevicePropInfo() {
        return this.deviceItemInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.deviceInfo, i);
        parcel.writeParcelable(this.modelInfo, i);
        parcel.writeParcelable(this.deviceItemInfo, i);
    }
}
