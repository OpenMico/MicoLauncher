package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.PatchWall;

/* loaded from: classes3.dex */
public class MusicItemDataChangeEvent {
    public PatchWall.Block block;
    public int type;

    public MusicItemDataChangeEvent(int i, PatchWall.Block block) {
        this.type = i;
        this.block = block;
    }
}
