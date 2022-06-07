package com.xiaomi.smarthome.core.db;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.room.Entity;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Db.kt */
@Parcelize
@Keep
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\t\u0010\u0016\u001a\u00020\u0005HÖ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\b\u0010\u001b\u001a\u00020\u0005H\u0007J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\u0019\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\f\"\u0004\b\u000f\u0010\u0010¨\u0006#"}, d2 = {"Lcom/xiaomi/smarthome/core/db/CategoryRankEntity;", "Landroid/os/Parcelable;", "categoryName", "", "orderNo", "", "homeId", "deviceCount", "(Ljava/lang/String;ILjava/lang/String;I)V", "getCategoryName", "()Ljava/lang/String;", "getDeviceCount", "()I", "getHomeId", "getOrderNo", "setOrderNo", "(I)V", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", "", "other", "", "getIconRes", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
@Entity(primaryKeys = {"categoryName", "homeId"})
/* loaded from: classes4.dex */
public final class CategoryRankEntity implements Parcelable {
    public static final Parcelable.Creator<CategoryRankEntity> CREATOR = new Creator();
    @NotNull
    private final String categoryName;
    private final int deviceCount;
    @NotNull
    private final String homeId;
    private int orderNo;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<CategoryRankEntity> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final CategoryRankEntity createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new CategoryRankEntity(in.readString(), in.readInt(), in.readString(), in.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final CategoryRankEntity[] newArray(int i) {
            return new CategoryRankEntity[i];
        }
    }

    public static /* synthetic */ CategoryRankEntity copy$default(CategoryRankEntity categoryRankEntity, String str, int i, String str2, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = categoryRankEntity.categoryName;
        }
        if ((i3 & 2) != 0) {
            i = categoryRankEntity.orderNo;
        }
        if ((i3 & 4) != 0) {
            str2 = categoryRankEntity.homeId;
        }
        if ((i3 & 8) != 0) {
            i2 = categoryRankEntity.deviceCount;
        }
        return categoryRankEntity.copy(str, i, str2, i2);
    }

    @NotNull
    public final String component1() {
        return this.categoryName;
    }

    public final int component2() {
        return this.orderNo;
    }

    @NotNull
    public final String component3() {
        return this.homeId;
    }

    public final int component4() {
        return this.deviceCount;
    }

    @NotNull
    public final CategoryRankEntity copy(@NotNull String categoryName, int i, @NotNull String homeId, int i2) {
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        return new CategoryRankEntity(categoryName, i, homeId, i2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CategoryRankEntity)) {
            return false;
        }
        CategoryRankEntity categoryRankEntity = (CategoryRankEntity) obj;
        return Intrinsics.areEqual(this.categoryName, categoryRankEntity.categoryName) && this.orderNo == categoryRankEntity.orderNo && Intrinsics.areEqual(this.homeId, categoryRankEntity.homeId) && this.deviceCount == categoryRankEntity.deviceCount;
    }

    public int hashCode() {
        String str = this.categoryName;
        int i = 0;
        int hashCode = (((str != null ? str.hashCode() : 0) * 31) + Integer.hashCode(this.orderNo)) * 31;
        String str2 = this.homeId;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return ((hashCode + i) * 31) + Integer.hashCode(this.deviceCount);
    }

    @NotNull
    public String toString() {
        return "CategoryRankEntity(categoryName=" + this.categoryName + ", orderNo=" + this.orderNo + ", homeId=" + this.homeId + ", deviceCount=" + this.deviceCount + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.categoryName);
        parcel.writeInt(this.orderNo);
        parcel.writeString(this.homeId);
        parcel.writeInt(this.deviceCount);
    }

    public CategoryRankEntity(@NotNull String categoryName, int i, @NotNull String homeId, int i2) {
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        this.categoryName = categoryName;
        this.orderNo = i;
        this.homeId = homeId;
        this.deviceCount = i2;
    }

    @NotNull
    public final String getCategoryName() {
        return this.categoryName;
    }

    public final int getOrderNo() {
        return this.orderNo;
    }

    public final void setOrderNo(int i) {
        this.orderNo = i;
    }

    @NotNull
    public final String getHomeId() {
        return this.homeId;
    }

    public /* synthetic */ CategoryRankEntity(String str, int i, String str2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? Integer.MAX_VALUE : i, (i3 & 4) != 0 ? "" : str2, (i3 & 8) != 0 ? 0 : i2);
    }

    public final int getDeviceCount() {
        return this.deviceCount;
    }

    @DrawableRes
    public final int getIconRes() {
        String str = this.categoryName;
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_SOCKET.getTypeName())) {
            return R.drawable.sh_ic_category_socket;
        }
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_LIGHT.getTypeName())) {
            return R.drawable.sh_ic_category_light;
        }
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_ENV.getTypeName())) {
            return R.drawable.sh_ic_category_environment;
        }
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_MEDIA.getTypeName())) {
            return R.drawable.sh_ic_category_media;
        }
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_CURTAIN.getTypeName())) {
            return R.drawable.sh_ic_category_curtain;
        }
        if (Intrinsics.areEqual(str, CategoryDic.CATEGORY_HOUSE_WORK.getTypeName())) {
            return R.drawable.sh_ic_category_housework;
        }
        return R.drawable.sh_ic_category_security;
    }
}
