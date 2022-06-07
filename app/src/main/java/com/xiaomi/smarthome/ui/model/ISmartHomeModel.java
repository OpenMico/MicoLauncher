package com.xiaomi.smarthome.ui.model;

import com.xiaomi.smarthome.core.entity.CategoryDic;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ISmartHomeModel.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH&R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\n"}, d2 = {"Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "", "orderNo", "", "(I)V", "getOrderNo", "()I", "setOrderNo", "type", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public abstract class ISmartHomeModel {
    private int orderNo;

    public ISmartHomeModel() {
        this(0, 1, null);
    }

    @NotNull
    public abstract CategoryDic type();

    public ISmartHomeModel(int i) {
        this.orderNo = i;
    }

    public /* synthetic */ ISmartHomeModel(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? -1 : i);
    }

    public final int getOrderNo() {
        return this.orderNo;
    }

    public final void setOrderNo(int i) {
        this.orderNo = i;
    }
}
