package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.api.model.PatchWall;

/* loaded from: classes3.dex */
public class AutoPagingSuccessEvent {
    public PatchWall patchWall;
    public int type;

    public AutoPagingSuccessEvent(PatchWall patchWall) {
        this.patchWall = patchWall;
    }

    public AutoPagingSuccessEvent(int i, PatchWall patchWall) {
        this.type = i;
        this.patchWall = patchWall;
    }
}
