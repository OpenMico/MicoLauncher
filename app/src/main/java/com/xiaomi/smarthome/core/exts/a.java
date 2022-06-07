package com.xiaomi.smarthome.core.exts;

import android.view.View;
import com.umeng.analytics.pro.ai;
import com.xiaomi.smarthome.R;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/xiaomi/smarthome/core/exts/ViewCoroutineScope;", "Lkotlinx/coroutines/CoroutineScope;", "Landroid/view/View$OnAttachStateChangeListener;", "()V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "onViewAttachedToWindow", "", ai.aC, "Landroid/view/View;", "onViewDetachedFromWindow", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class a implements View.OnAttachStateChangeListener, CoroutineScope {
    @NotNull
    private final CoroutineContext a = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain());

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(@Nullable View view) {
    }

    @Override // kotlinx.coroutines.CoroutineScope
    @NotNull
    public CoroutineContext getCoroutineContext() {
        return this.a;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(@NotNull View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        u.a(getCoroutineContext(), (CancellationException) null, 1, (Object) null);
        v.setTag(R.string.tag_view_coroutine_scope, null);
    }
}
