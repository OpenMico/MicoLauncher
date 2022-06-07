package com.jakewharton.rxbinding4.view;

import android.view.ViewGroup;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewGroupHierarchyChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/view/RxViewGroup__ViewGroupHierarchyChangeEventObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxViewGroup {
    @CheckResult
    @NotNull
    public static final Observable<ViewGroupHierarchyChangeEvent> changeEvents(@NotNull ViewGroup viewGroup) {
        return e.a(viewGroup);
    }
}
