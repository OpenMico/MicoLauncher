package com.jakewharton.rxbinding4.view;

import android.view.View;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewTreeObserverPreDrawObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0007¨\u0006\u0007"}, d2 = {"preDraws", "Lio/reactivex/rxjava3/core/Observable;", "", "Landroid/view/View;", "proceedDrawingPass", "Lkotlin/Function0;", "", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/view/RxView")
/* loaded from: classes2.dex */
final /* synthetic */ class u {
    @CheckResult
    @NotNull
    public static final Observable<Unit> a(@NotNull View preDraws, @NotNull Function0<Boolean> proceedDrawingPass) {
        Intrinsics.checkParameterIsNotNull(preDraws, "$this$preDraws");
        Intrinsics.checkParameterIsNotNull(proceedDrawingPass, "proceedDrawingPass");
        return new am(preDraws, proceedDrawingPass);
    }
}
