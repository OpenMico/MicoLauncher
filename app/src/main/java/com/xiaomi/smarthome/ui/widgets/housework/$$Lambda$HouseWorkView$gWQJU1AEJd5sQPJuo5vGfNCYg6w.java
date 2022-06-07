package com.xiaomi.smarthome.ui.widgets.housework;

import com.xiaomi.miot.support.core.ISendMsgCallback;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w implements ISendMsgCallback {
    public static final /* synthetic */ $$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w INSTANCE = new $$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w();

    private /* synthetic */ $$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w() {
    }

    @Override // com.xiaomi.miot.support.core.ISendMsgCallback
    public final void onResult(String str) {
        HouseWorkView.b(str);
    }
}
