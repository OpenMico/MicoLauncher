package com.xiaomi.micolauncher.module.station;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$wgVlOje-cURWDlyU9ySc5Ub1Zk0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$StationCategoryListActivity$wgVlOjecURWDlyU9ySc5Ub1Zk0 implements Function {
    public static final /* synthetic */ $$Lambda$StationCategoryListActivity$wgVlOjecURWDlyU9ySc5Ub1Zk0 INSTANCE = new $$Lambda$StationCategoryListActivity$wgVlOjecURWDlyU9ySc5Ub1Zk0();

    private /* synthetic */ $$Lambda$StationCategoryListActivity$wgVlOjecURWDlyU9ySc5Ub1Zk0() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ChildVideo.ItemsBean a;
        a = StationCategoryListActivity.a((VideoItem) obj);
        return a;
    }
}
