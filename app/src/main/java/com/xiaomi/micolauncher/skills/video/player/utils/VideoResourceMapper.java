package com.xiaomi.micolauncher.skills.video.player.utils;

import com.xiaomi.micolauncher.api.model.MIBrain;
import io.reactivex.functions.Function;

/* loaded from: classes3.dex */
public class VideoResourceMapper implements Function<MIBrain.CpResource, String> {
    public String apply(MIBrain.CpResource cpResource) {
        return (cpResource.data == null || cpResource.data.size() <= 0) ? "" : cpResource.data.get(0).url;
    }
}
