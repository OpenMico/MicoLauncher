package com.xiaomi.micolauncher.module.main.manager;

import com.xiaomi.micolauncher.api.model.Station;
import io.reactivex.functions.BiFunction;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2Nu-lIvZwSY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2NulIvZwSY implements BiFunction {
    public static final /* synthetic */ $$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2NulIvZwSY INSTANCE = new $$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2NulIvZwSY();

    private /* synthetic */ $$Lambda$RecommendDataManager$jhFe0M2klv8jhTRy2NulIvZwSY() {
    }

    @Override // io.reactivex.functions.BiFunction
    public final Object apply(Object obj, Object obj2) {
        List a;
        a = RecommendDataManager.a((Station.Sound) obj, (Station.Sound) obj2);
        return a;
    }
}
