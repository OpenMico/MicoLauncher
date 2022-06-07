package com.xiaomi.micolauncher.api.model;

import com.xiaomi.mico.common.ContainerUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class AppBlock implements IBlockBean {
    String title;

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public String getBlockType() {
        return "app";
    }

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public String getUiType() {
        return "";
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public String getBlockTitle() {
        return getTitle();
    }

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public List<? extends IListItem> getListItems() {
        return ContainerUtil.getEmptyArrayList();
    }
}
