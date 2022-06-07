package com.xiaomi.smarthome.core.exts;

import android.view.View;
import com.xiaomi.smarthome.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"viewScope", "Lkotlinx/coroutines/CoroutineScope;", "Landroid/view/View;", "getViewScope", "(Landroid/view/View;)Lkotlinx/coroutines/CoroutineScope;", "smarthome_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class ViewExtsKt {
    @NotNull
    public static final CoroutineScope getViewScope(@NotNull View viewScope) {
        Intrinsics.checkNotNullParameter(viewScope, "$this$viewScope");
        Object tag = viewScope.getTag(R.string.tag_view_coroutine_scope);
        if (!(tag instanceof CoroutineScope)) {
            tag = null;
        }
        CoroutineScope coroutineScope = (CoroutineScope) tag;
        if (coroutineScope != null) {
            return coroutineScope;
        }
        a aVar = new a();
        if (viewScope.isAttachedToWindow()) {
            viewScope.addOnAttachStateChangeListener(aVar);
            viewScope.setTag(Integer.valueOf(R.string.tag_view_coroutine_scope));
        } else {
            CoroutineScopeKt.cancel$default(aVar, null, 1, null);
        }
        return aVar;
    }
}
