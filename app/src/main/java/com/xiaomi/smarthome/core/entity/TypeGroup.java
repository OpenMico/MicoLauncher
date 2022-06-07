package com.xiaomi.smarthome.core.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeBeans.kt */
@Parcelize
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\tHÆ\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\t\u0010\u0015\u001a\u00020\tHÖ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\tHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\b\u0010\u001d\u001a\u00020\u0004H\u0016J\u0019\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\tHÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006#"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/TypeGroup;", "Landroid/os/Parcelable;", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "category", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "deviceList", "", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "showCountInfFirstPage", "", "(Lcom/xiaomi/smarthome/core/entity/CategoryDic;Ljava/util/List;I)V", "getCategory", "()Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "getDeviceList", "()Ljava/util/List;", "getShowCountInfFirstPage", "()I", "component1", "component2", "component3", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "", "type", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public final class TypeGroup extends ISmartHomeModel implements Parcelable {
    public static final Parcelable.Creator<TypeGroup> CREATOR = new Creator();
    @NotNull
    private final CategoryDic category;
    @NotNull
    private final List<DeviceInfoBean> deviceList;
    private final int showCountInfFirstPage;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<TypeGroup> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final TypeGroup createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            CategoryDic categoryDic = (CategoryDic) Enum.valueOf(CategoryDic.class, in.readString());
            int readInt = in.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            while (readInt != 0) {
                arrayList.add(DeviceInfoBean.CREATOR.createFromParcel(in));
                readInt--;
            }
            return new TypeGroup(categoryDic, arrayList, in.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final TypeGroup[] newArray(int i) {
            return new TypeGroup[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TypeGroup copy$default(TypeGroup typeGroup, CategoryDic categoryDic, List list, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            categoryDic = typeGroup.category;
        }
        if ((i2 & 2) != 0) {
            list = typeGroup.deviceList;
        }
        if ((i2 & 4) != 0) {
            i = typeGroup.showCountInfFirstPage;
        }
        return typeGroup.copy(categoryDic, list, i);
    }

    @NotNull
    public final CategoryDic component1() {
        return this.category;
    }

    @NotNull
    public final List<DeviceInfoBean> component2() {
        return this.deviceList;
    }

    public final int component3() {
        return this.showCountInfFirstPage;
    }

    @NotNull
    public final TypeGroup copy(@NotNull CategoryDic category, @NotNull List<DeviceInfoBean> deviceList, int i) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(deviceList, "deviceList");
        return new TypeGroup(category, deviceList, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeGroup)) {
            return false;
        }
        TypeGroup typeGroup = (TypeGroup) obj;
        return Intrinsics.areEqual(this.category, typeGroup.category) && Intrinsics.areEqual(this.deviceList, typeGroup.deviceList) && this.showCountInfFirstPage == typeGroup.showCountInfFirstPage;
    }

    public int hashCode() {
        CategoryDic categoryDic = this.category;
        int i = 0;
        int hashCode = (categoryDic != null ? categoryDic.hashCode() : 0) * 31;
        List<DeviceInfoBean> list = this.deviceList;
        if (list != null) {
            i = list.hashCode();
        }
        return ((hashCode + i) * 31) + Integer.hashCode(this.showCountInfFirstPage);
    }

    @NotNull
    public String toString() {
        return "TypeGroup(category=" + this.category + ", deviceList=" + this.deviceList + ", showCountInfFirstPage=" + this.showCountInfFirstPage + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.category.name());
        List<DeviceInfoBean> list = this.deviceList;
        parcel.writeInt(list.size());
        for (DeviceInfoBean deviceInfoBean : list) {
            deviceInfoBean.writeToParcel(parcel, 0);
        }
        parcel.writeInt(this.showCountInfFirstPage);
    }

    @NotNull
    public final CategoryDic getCategory() {
        return this.category;
    }

    @NotNull
    public final List<DeviceInfoBean> getDeviceList() {
        return this.deviceList;
    }

    public /* synthetic */ TypeGroup(CategoryDic categoryDic, List list, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(categoryDic, list, (i2 & 4) != 0 ? 1 : i);
    }

    public final int getShowCountInfFirstPage() {
        return this.showCountInfFirstPage;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeGroup(@NotNull CategoryDic category, @NotNull List<DeviceInfoBean> deviceList, int i) {
        super(category.getType());
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(deviceList, "deviceList");
        this.category = category;
        this.deviceList = deviceList;
        this.showCountInfFirstPage = i;
    }

    @Override // com.xiaomi.smarthome.ui.model.ISmartHomeModel
    @NotNull
    public CategoryDic type() {
        return this.category;
    }
}
