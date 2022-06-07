package com.xiaomi.micolauncher.common.stat;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o implements Function {
    public static final /* synthetic */ $$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o INSTANCE = new $$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o();

    private /* synthetic */ $$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource removeEvents;
        String str = (String) obj;
        removeEvents = StatModel.removeEvents();
        return removeEvents;
    }
}
