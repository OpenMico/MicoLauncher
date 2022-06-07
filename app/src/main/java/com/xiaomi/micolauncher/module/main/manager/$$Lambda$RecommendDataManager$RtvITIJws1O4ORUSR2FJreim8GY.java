package com.xiaomi.micolauncher.module.main.manager;

import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import io.reactivex.functions.BiFunction;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.main.manager.-$$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY implements BiFunction {
    public static final /* synthetic */ $$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY INSTANCE = new $$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY();

    private /* synthetic */ $$Lambda$RecommendDataManager$RtvITIJws1O4ORUSR2FJreim8GY() {
    }

    @Override // io.reactivex.functions.BiFunction
    public final Object apply(Object obj, Object obj2) {
        RecommendDataManager.HomeDataContainer c;
        c = RecommendDataManager.c((List) obj, (List) obj2);
        return c;
    }
}
