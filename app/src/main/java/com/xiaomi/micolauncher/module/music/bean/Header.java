package com.xiaomi.micolauncher.module.music.bean;

import com.xiaomi.micolauncher.api.model.IListItem;

/* loaded from: classes3.dex */
public class Header implements IListItem {
    public String title;

    @Override // com.xiaomi.micolauncher.api.model.IListItem
    public String getItemId() {
        return "";
    }

    @Override // com.xiaomi.micolauncher.api.model.IListItem
    public String getItemImageUrl() {
        return "";
    }

    @Override // com.xiaomi.micolauncher.api.model.IListItem
    public String getItemTarget() {
        return "";
    }

    public Header() {
    }

    public Header(String str) {
        this.title = str;
    }

    @Override // com.xiaomi.micolauncher.api.model.IListItem
    public String getItemTitle() {
        return this.title;
    }
}
