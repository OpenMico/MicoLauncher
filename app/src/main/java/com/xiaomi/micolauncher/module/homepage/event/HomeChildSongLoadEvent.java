package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.PatchWall;

/* loaded from: classes3.dex */
public class HomeChildSongLoadEvent {
    public PatchWall.Block block;
    public int viewType;

    public HomeChildSongLoadEvent(int i, PatchWall.Block block) {
        this.viewType = i;
        this.block = block;
    }
}
