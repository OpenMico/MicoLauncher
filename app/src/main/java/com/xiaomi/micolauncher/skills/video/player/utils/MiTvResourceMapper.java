package com.xiaomi.micolauncher.skills.video.player.utils;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.MIBrain;
import io.reactivex.functions.Function;
import java.util.List;

/* loaded from: classes3.dex */
public class MiTvResourceMapper implements Function<MIBrain.MiTvResource, String> {
    public String apply(MIBrain.MiTvResource miTvResource) {
        List<MIBrain.MiTvResource.MiTvResourceItem> data = miTvResource.getData();
        return !ContainerUtil.isEmpty(data) ? data.get(0).getUrl() : "";
    }
}
