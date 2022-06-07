package com.jakewharton.rxbinding4.widget;

import android.widget.AbsListView;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbsListViewScrollEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxAbsListView__AbsListViewScrollEventObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxAbsListView {
    @CheckResult
    @NotNull
    public static final Observable<AbsListViewScrollEvent> scrollEvents(@NotNull AbsListView absListView) {
        return p.a(absListView);
    }
}
