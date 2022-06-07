package com.xiaomi.micolauncher.skills.music.controller;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE implements Function {
    public static final /* synthetic */ $$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE INSTANCE = new $$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE();

    private /* synthetic */ $$Lambda$CollectionManager$LM14y8aI4rxnltBtRqw5t0mhkYE() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource d;
        d = CollectionManager.d((List) obj);
        return d;
    }
}
