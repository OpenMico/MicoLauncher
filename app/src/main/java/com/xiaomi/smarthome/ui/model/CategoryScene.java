package com.xiaomi.smarthome.ui.model;

import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class CategoryScene extends ISmartHomeModel {
    public List<CommonUsedScene> sceneList = new ArrayList();

    public CategoryScene(List<CommonUsedScene> list) {
        if (list != null) {
            this.sceneList.addAll(list);
        }
    }

    @Override // com.xiaomi.smarthome.ui.model.ISmartHomeModel
    @NotNull
    public CategoryDic type() {
        return CategoryDic.CATEGORY_SCENE;
    }
}
