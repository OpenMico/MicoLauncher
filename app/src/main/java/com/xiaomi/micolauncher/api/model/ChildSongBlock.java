package com.xiaomi.micolauncher.api.model;

import java.util.List;

/* loaded from: classes3.dex */
public class ChildSongBlock implements IBlockBean {
    private List<? extends IListItem> listItem;
    String title;

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public String getBlockType() {
        return IBlockBean.BLOCK_TYPE_STORY;
    }

    @Override // com.xiaomi.micolauncher.api.model.IBlockBean
    public String getUiType() {
        return "";
    }

    public String getTitle() {
        return this.title;
    }

    public void setListItem(List<? extends IListItem> list) {
        this.listItem = list;
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
        return this.listItem;
    }
}
