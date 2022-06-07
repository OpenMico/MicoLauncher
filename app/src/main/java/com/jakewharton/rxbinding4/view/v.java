package com.jakewharton.rxbinding4.view;

import android.view.View;
import androidx.annotation.CheckResult;
import com.xiaomi.onetrack.api.b;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewVisibilityConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u001e\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"visibility", "Lio/reactivex/rxjava3/functions/Consumer;", "", "Landroid/view/View;", "visibilityWhenFalse", "", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/view/RxView")
/* loaded from: classes2.dex */
public final /* synthetic */ class v {
    public static /* synthetic */ Consumer a(View view, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8;
        }
        return RxView.visibility(view, i);
    }

    /* compiled from: ViewVisibilityConsumer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", b.p, "", "kotlin.jvm.PlatformType", "accept", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    static final class a<T> implements Consumer<Boolean> {
        final /* synthetic */ View a;
        final /* synthetic */ int b;

        a(View view, int i) {
            this.a = view;
            this.b = i;
        }

        /* renamed from: a */
        public final void accept(Boolean value) {
            View view = this.a;
            Intrinsics.checkExpressionValueIsNotNull(value, "value");
            view.setVisibility(value.booleanValue() ? 0 : this.b);
        }
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Consumer<? super Boolean> a(@NotNull View visibility, int i) {
        Intrinsics.checkParameterIsNotNull(visibility, "$this$visibility");
        boolean z = true;
        if (i != 0) {
            if (!(i == 4 || i == 8)) {
                z = false;
            }
            if (z) {
                return new a(visibility, i);
            }
            throw new IllegalArgumentException("Must set visibility to INVISIBLE or GONE when false.".toString());
        }
        throw new IllegalArgumentException("Setting visibility to VISIBLE when false would have no effect.".toString());
    }
}
