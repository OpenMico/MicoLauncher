package com.jakewharton.rxbinding4.view;

import android.view.DragEvent;
import android.view.View;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.internal.AlwaysTrue;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewDragObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u0007¨\u0006\u0007"}, d2 = {"drags", "Lio/reactivex/rxjava3/core/Observable;", "Landroid/view/DragEvent;", "Landroid/view/View;", "handled", "Lkotlin/Function1;", "", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/view/RxView")
/* loaded from: classes2.dex */
public final /* synthetic */ class i {
    public static /* synthetic */ Observable a(View view, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = AlwaysTrue.INSTANCE;
        }
        return RxView.drags(view, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<DragEvent> a(@NotNull View drags, @NotNull Function1<? super DragEvent, Boolean> handled) {
        Intrinsics.checkParameterIsNotNull(drags, "$this$drags");
        Intrinsics.checkParameterIsNotNull(handled, "handled");
        return new z(drags, handled);
    }
}
