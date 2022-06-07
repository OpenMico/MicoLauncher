package com.xiaomi.smarthome.core.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.parcelize.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeBeans.kt */
@Parcelize
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\u0006\u001a\u00020\u0004HÂ\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\t\u0010\b\u001a\u00020\tHÖ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\tHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\b\u0010\u0011\u001a\u00020\u0004H\u0016J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\tHÖ\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/TypeCategoryReorderEntrance;", "Landroid/os/Parcelable;", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "category", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "(Lcom/xiaomi/smarthome/core/entity/CategoryDic;)V", "component1", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "", "type", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", MiLinkKeys.PARAM_FLAGS, "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class TypeCategoryReorderEntrance extends ISmartHomeModel implements Parcelable {
    public static final Parcelable.Creator<TypeCategoryReorderEntrance> CREATOR = new Creator();
    private final CategoryDic category;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static class Creator implements Parcelable.Creator<TypeCategoryReorderEntrance> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final TypeCategoryReorderEntrance createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new TypeCategoryReorderEntrance((CategoryDic) Enum.valueOf(CategoryDic.class, in.readString()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public final TypeCategoryReorderEntrance[] newArray(int i) {
            return new TypeCategoryReorderEntrance[i];
        }
    }

    public TypeCategoryReorderEntrance() {
        this(null, 1, null);
    }

    private final CategoryDic component1() {
        return this.category;
    }

    public static /* synthetic */ TypeCategoryReorderEntrance copy$default(TypeCategoryReorderEntrance typeCategoryReorderEntrance, CategoryDic categoryDic, int i, Object obj) {
        if ((i & 1) != 0) {
            categoryDic = typeCategoryReorderEntrance.category;
        }
        return typeCategoryReorderEntrance.copy(categoryDic);
    }

    @NotNull
    public final TypeCategoryReorderEntrance copy(@NotNull CategoryDic category) {
        Intrinsics.checkNotNullParameter(category, "category");
        return new TypeCategoryReorderEntrance(category);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof TypeCategoryReorderEntrance) && Intrinsics.areEqual(this.category, ((TypeCategoryReorderEntrance) obj).category);
        }
        return true;
    }

    public int hashCode() {
        CategoryDic categoryDic = this.category;
        if (categoryDic != null) {
            return categoryDic.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "TypeCategoryReorderEntrance(category=" + this.category + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int i) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.category.name());
    }

    public /* synthetic */ TypeCategoryReorderEntrance(CategoryDic categoryDic, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CategoryDic.CATEGORY_ENTRANCE : categoryDic);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeCategoryReorderEntrance(@NotNull CategoryDic category) {
        super(category.getType());
        Intrinsics.checkNotNullParameter(category, "category");
        this.category = category;
    }

    @Override // com.xiaomi.smarthome.ui.model.ISmartHomeModel
    @NotNull
    public CategoryDic type() {
        return this.category;
    }
}
