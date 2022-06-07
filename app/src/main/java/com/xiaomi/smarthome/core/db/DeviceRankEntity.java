package com.xiaomi.smarthome.core.db;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.milink.base.contract.MiLinkKeys;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Db.kt */
@Parcelize
@Keep
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0005HÆ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\u0019\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010¨\u0006%"}, d2 = {"Lcom/xiaomi/smarthome/core/db/DeviceRankEntity;", "Landroid/os/Parcelable;", "did", "", "orderNo", "", "categoryName", "homeId", "isShowInFirstPage", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;I)V", "getCategoryName", "()Ljava/lang/String;", "getDid", "getHomeId", "()I", "setShowInFirstPage", "(I)V", "getOrderNo", "setOrderNo", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
@Entity
/* loaded from: classes4.dex */
public final class DeviceRankEntity implements Parcelable {
    public static final Parcelable.Creator<DeviceRankEntity> CREATOR = new Creator();
    @NotNull
    private final String categoryName;
    @PrimaryKey
    @NotNull
    private final String did;
    @NotNull
    private final String homeId;
    private int isShowInFirstPage;
    private int orderNo;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<DeviceRankEntity> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final DeviceRankEntity createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new DeviceRankEntity(in.readString(), in.readInt(), in.readString(), in.readString(), in.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final DeviceRankEntity[] newArray(int i) {
            return new DeviceRankEntity[i];
        }
    }

    public static /* synthetic */ DeviceRankEntity copy$default(DeviceRankEntity deviceRankEntity, String str, int i, String str2, String str3, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = deviceRankEntity.did;
        }
        if ((i3 & 2) != 0) {
            i = deviceRankEntity.orderNo;
        }
        if ((i3 & 4) != 0) {
            str2 = deviceRankEntity.categoryName;
        }
        if ((i3 & 8) != 0) {
            str3 = deviceRankEntity.homeId;
        }
        if ((i3 & 16) != 0) {
            i2 = deviceRankEntity.isShowInFirstPage;
        }
        return deviceRankEntity.copy(str, i, str2, str3, i2);
    }

    @NotNull
    public final String component1() {
        return this.did;
    }

    public final int component2() {
        return this.orderNo;
    }

    @NotNull
    public final String component3() {
        return this.categoryName;
    }

    @NotNull
    public final String component4() {
        return this.homeId;
    }

    public final int component5() {
        return this.isShowInFirstPage;
    }

    @NotNull
    public final DeviceRankEntity copy(@NotNull String did, int i, @NotNull String categoryName, @NotNull String homeId, int i2) {
        Intrinsics.checkNotNullParameter(did, "did");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        return new DeviceRankEntity(did, i, categoryName, homeId, i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceRankEntity)) {
            return false;
        }
        DeviceRankEntity deviceRankEntity = (DeviceRankEntity) obj;
        return Intrinsics.areEqual(this.did, deviceRankEntity.did) && this.orderNo == deviceRankEntity.orderNo && Intrinsics.areEqual(this.categoryName, deviceRankEntity.categoryName) && Intrinsics.areEqual(this.homeId, deviceRankEntity.homeId) && this.isShowInFirstPage == deviceRankEntity.isShowInFirstPage;
    }

    public int hashCode() {
        String str = this.did;
        int i = 0;
        int hashCode = (((str != null ? str.hashCode() : 0) * 31) + Integer.hashCode(this.orderNo)) * 31;
        String str2 = this.categoryName;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.homeId;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return ((hashCode2 + i) * 31) + Integer.hashCode(this.isShowInFirstPage);
    }

    @NotNull
    public String toString() {
        return "DeviceRankEntity(did=" + this.did + ", orderNo=" + this.orderNo + ", categoryName=" + this.categoryName + ", homeId=" + this.homeId + ", isShowInFirstPage=" + this.isShowInFirstPage + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.did);
        parcel.writeInt(this.orderNo);
        parcel.writeString(this.categoryName);
        parcel.writeString(this.homeId);
        parcel.writeInt(this.isShowInFirstPage);
    }

    public DeviceRankEntity(@NotNull String did, int i, @NotNull String categoryName, @NotNull String homeId, int i2) {
        Intrinsics.checkNotNullParameter(did, "did");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        this.did = did;
        this.orderNo = i;
        this.categoryName = categoryName;
        this.homeId = homeId;
        this.isShowInFirstPage = i2;
    }

    @NotNull
    public final String getDid() {
        return this.did;
    }

    public final int getOrderNo() {
        return this.orderNo;
    }

    public final void setOrderNo(int i) {
        this.orderNo = i;
    }

    @NotNull
    public final String getCategoryName() {
        return this.categoryName;
    }

    @NotNull
    public final String getHomeId() {
        return this.homeId;
    }

    public /* synthetic */ DeviceRankEntity(String str, int i, String str2, String str3, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? Integer.MAX_VALUE : i, str2, (i3 & 8) != 0 ? "" : str3, (i3 & 16) != 0 ? 0 : i2);
    }

    public final int isShowInFirstPage() {
        return this.isShowInFirstPage;
    }

    public final void setShowInFirstPage(int i) {
        this.isShowInFirstPage = i;
    }
}
