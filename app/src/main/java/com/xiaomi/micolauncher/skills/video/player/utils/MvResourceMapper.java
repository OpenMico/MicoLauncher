package com.xiaomi.micolauncher.skills.video.player.utils;

import com.xiaomi.micolauncher.api.model.MIBrain;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class MvResourceMapper implements Function<MIBrain.MVResource, String> {
    public String apply(MIBrain.MVResource mVResource) {
        return (mVResource.data == null || mVResource.data.size() <= 0) ? "" : mVResource.data.get(0).getPlayUrl();
    }
}
