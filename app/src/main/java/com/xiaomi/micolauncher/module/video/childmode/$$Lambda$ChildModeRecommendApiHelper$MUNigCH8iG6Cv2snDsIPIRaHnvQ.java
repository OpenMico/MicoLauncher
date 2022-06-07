package com.xiaomi.micolauncher.module.video.childmode;

import com.xiaomi.micolauncher.api.model.PatchWall;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.video.childmode.-$$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ implements Function {
    public static final /* synthetic */ $$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ INSTANCE = new $$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ();

    private /* synthetic */ $$Lambda$ChildModeRecommendApiHelper$MUNigCH8iG6Cv2snDsIPIRaHnvQ() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        List list;
        list = ((PatchWall.Category) obj).items;
        return list;
    }
}
