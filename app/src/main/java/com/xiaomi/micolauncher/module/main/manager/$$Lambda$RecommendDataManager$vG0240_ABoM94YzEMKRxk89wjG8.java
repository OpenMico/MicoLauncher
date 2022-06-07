package com.xiaomi.micolauncher.module.main.manager;

import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import io.reactivex.functions.BiFunction;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8 implements BiFunction {
    public static final /* synthetic */ $$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8 INSTANCE = new $$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8();

    private /* synthetic */ $$Lambda$RecommendDataManager$vG0240_ABoM94YzEMKRxk89wjG8() {
    }

    @Override // io.reactivex.functions.BiFunction
    public final Object apply(Object obj, Object obj2) {
        RecommendDataManager.HomeDataContainer b;
        b = RecommendDataManager.b((List) obj, (List) obj2);
        return b;
    }
}
