package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.PatchWall;
import java.util.List;

/* loaded from: classes3.dex */
public class LoadMusicCacheSuccessEvent {
    public List<PatchWall.Block> blocks;

    public LoadMusicCacheSuccessEvent(List<PatchWall.Block> list) {
        this.blocks = list;
    }
}
