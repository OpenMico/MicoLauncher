package com.xiaomi.smarthome.core.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeBeans.kt */
@Parcelize
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u00012\u00020\u0002B+\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0004¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J\t\u0010\u0015\u001a\u00020\u0004HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0004HÆ\u0001J\t\u0010\u0017\u001a\u00020\u0004HÖ\u0001J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u0004HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\u0019\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0004HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\rR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e¨\u0006#"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "Landroid/os/Parcelable;", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "orderNo", "", "deviceInfoWrapper", "Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;", "isLocationDeviceNameExchange", "", "isShowInFirstPage", "(ILcom/xiaomi/miot/support/category/DeviceInfoWrapper;ZI)V", "getDeviceInfoWrapper", "()Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;", "()Z", "()I", "setShowInFirstPage", "(I)V", "getOrderNo", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", "other", "", "hashCode", "toString", "", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public final class DeviceInfoBean implements Parcelable, IDevice {
    public static final Parcelable.Creator<DeviceInfoBean> CREATOR = new Creator();
    @NotNull
    private final DeviceInfoWrapper deviceInfoWrapper;
    private final boolean isLocationDeviceNameExchange;
    private int isShowInFirstPage;
    private final int orderNo;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<DeviceInfoBean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final DeviceInfoBean createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new DeviceInfoBean(in.readInt(), (DeviceInfoWrapper) in.readParcelable(DeviceInfoBean.class.getClassLoader()), in.readInt() != 0, in.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final DeviceInfoBean[] newArray(int i) {
            return new DeviceInfoBean[i];
        }
    }

    public static /* synthetic */ DeviceInfoBean copy$default(DeviceInfoBean deviceInfoBean, int i, DeviceInfoWrapper deviceInfoWrapper, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = deviceInfoBean.orderNo;
        }
        if ((i3 & 2) != 0) {
            deviceInfoWrapper = deviceInfoBean.deviceInfoWrapper;
        }
        if ((i3 & 4) != 0) {
            z = deviceInfoBean.isLocationDeviceNameExchange;
        }
        if ((i3 & 8) != 0) {
            i2 = deviceInfoBean.isShowInFirstPage;
        }
        return deviceInfoBean.copy(i, deviceInfoWrapper, z, i2);
    }

    public final int component1() {
        return this.orderNo;
    }

    @NotNull
    public final DeviceInfoWrapper component2() {
        return this.deviceInfoWrapper;
    }

    public final boolean component3() {
        return this.isLocationDeviceNameExchange;
    }

    public final int component4() {
        return this.isShowInFirstPage;
    }

    @NotNull
    public final DeviceInfoBean copy(int i, @NotNull DeviceInfoWrapper deviceInfoWrapper, boolean z, int i2) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        return new DeviceInfoBean(i, deviceInfoWrapper, z, i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceInfoBean)) {
            return false;
        }
        DeviceInfoBean deviceInfoBean = (DeviceInfoBean) obj;
        return this.orderNo == deviceInfoBean.orderNo && Intrinsics.areEqual(this.deviceInfoWrapper, deviceInfoBean.deviceInfoWrapper) && this.isLocationDeviceNameExchange == deviceInfoBean.isLocationDeviceNameExchange && this.isShowInFirstPage == deviceInfoBean.isShowInFirstPage;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.orderNo) * 31;
        DeviceInfoWrapper deviceInfoWrapper = this.deviceInfoWrapper;
        int hashCode2 = (hashCode + (deviceInfoWrapper != null ? deviceInfoWrapper.hashCode() : 0)) * 31;
        boolean z = this.isLocationDeviceNameExchange;
        if (z) {
            z = true;
        }
        int i = z ? 1 : 0;
        int i2 = z ? 1 : 0;
        int i3 = z ? 1 : 0;
        return ((hashCode2 + i) * 31) + Integer.hashCode(this.isShowInFirstPage);
    }

    @NotNull
    public String toString() {
        return "DeviceInfoBean(orderNo=" + this.orderNo + ", deviceInfoWrapper=" + this.deviceInfoWrapper + ", isLocationDeviceNameExchange=" + this.isLocationDeviceNameExchange + ", isShowInFirstPage=" + this.isShowInFirstPage + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeInt(this.orderNo);
        parcel.writeParcelable(this.deviceInfoWrapper, i);
        parcel.writeInt(this.isLocationDeviceNameExchange ? 1 : 0);
        parcel.writeInt(this.isShowInFirstPage);
    }

    public DeviceInfoBean(int i, @NotNull DeviceInfoWrapper deviceInfoWrapper, boolean z, int i2) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        this.orderNo = i;
        this.deviceInfoWrapper = deviceInfoWrapper;
        this.isLocationDeviceNameExchange = z;
        this.isShowInFirstPage = i2;
    }

    public final int getOrderNo() {
        return this.orderNo;
    }

    @NotNull
    public final DeviceInfoWrapper getDeviceInfoWrapper() {
        return this.deviceInfoWrapper;
    }

    public final boolean isLocationDeviceNameExchange() {
        return this.isLocationDeviceNameExchange;
    }

    public /* synthetic */ DeviceInfoBean(int i, DeviceInfoWrapper deviceInfoWrapper, boolean z, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? Integer.MAX_VALUE : i, deviceInfoWrapper, (i3 & 4) != 0 ? false : z, (i3 & 8) != 0 ? 0 : i2);
    }

    public final int isShowInFirstPage() {
        return this.isShowInFirstPage;
    }

    public final void setShowInFirstPage(int i) {
        this.isShowInFirstPage = i;
    }
}
