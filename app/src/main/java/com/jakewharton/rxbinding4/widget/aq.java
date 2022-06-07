package com.jakewharton.rxbinding4.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.annotation.CheckResult;
import androidx.annotation.RequiresApi;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ToolbarItemClickObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¨\u0006\u0004"}, d2 = {"itemClicks", "Lio/reactivex/rxjava3/core/Observable;", "Landroid/view/MenuItem;", "Landroid/widget/Toolbar;", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/widget/RxToolbar")
/* loaded from: classes2.dex */
final /* synthetic */ class aq {
    @CheckResult
    @RequiresApi(21)
    @NotNull
    public static final Observable<MenuItem> a(@NotNull Toolbar itemClicks) {
        Intrinsics.checkParameterIsNotNull(itemClicks, "$this$itemClicks");
        return new bc(itemClicks);
    }
}
