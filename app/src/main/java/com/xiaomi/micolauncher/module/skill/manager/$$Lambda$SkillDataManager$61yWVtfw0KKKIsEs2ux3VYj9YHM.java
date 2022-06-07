package com.xiaomi.micolauncher.module.skill.manager;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM implements Function {
    public static final /* synthetic */ $$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM INSTANCE = new $$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM();

    private /* synthetic */ $$Lambda$SkillDataManager$61yWVtfw0KKKIsEs2ux3VYj9YHM() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = SkillDataManager.a((List) obj);
        return a;
    }
}
