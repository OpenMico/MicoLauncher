package com.xiaomi.smarthome.ui.model;

import com.xiaomi.smarthome.core.entity.CategoryDic;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class CategoryEmpty extends ISmartHomeModel {
    @Override // com.xiaomi.smarthome.ui.model.ISmartHomeModel
    @NotNull
    public CategoryDic type() {
        return CategoryDic.CATEGORY_EMPTY;
    }
}
