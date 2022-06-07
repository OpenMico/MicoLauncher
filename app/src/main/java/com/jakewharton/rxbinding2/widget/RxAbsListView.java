package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;

/* loaded from: classes2.dex */
public final class RxAbsListView {
    @NonNull
    @CheckResult
    public static Observable<AbsListViewScrollEvent> scrollEvents(@NonNull AbsListView absListView) {
        Preconditions.checkNotNull(absListView, "absListView == null");
        return new a(absListView);
    }

    private RxAbsListView() {
        throw new AssertionError("No instances.");
    }
}
