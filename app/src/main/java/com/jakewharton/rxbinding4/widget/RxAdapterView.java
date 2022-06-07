package com.jakewharton.rxbinding4.widget;

import android.widget.Adapter;
import android.widget.AdapterView;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewItemClickEventObservableKt", "com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewItemClickObservableKt", "com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewItemLongClickEventObservableKt", "com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewItemLongClickObservableKt", "com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewItemSelectionObservableKt", "com/jakewharton/rxbinding4/widget/RxAdapterView__AdapterViewSelectionObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxAdapterView {
    @CheckResult
    @NotNull
    public static final <T extends Adapter> Observable<AdapterViewItemClickEvent> itemClickEvents(@NotNull AdapterView<T> adapterView) {
        return q.a(adapterView);
    }

    @CheckResult
    @NotNull
    public static final <T extends Adapter> Observable<Integer> itemClicks(@NotNull AdapterView<T> adapterView) {
        return r.a(adapterView);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final <T extends Adapter> Observable<AdapterViewItemLongClickEvent> itemLongClickEvents(@NotNull AdapterView<T> adapterView) {
        return s.a(adapterView, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final <T extends Adapter> Observable<AdapterViewItemLongClickEvent> itemLongClickEvents(@NotNull AdapterView<T> adapterView, @NotNull Function1<? super AdapterViewItemLongClickEvent, Boolean> function1) {
        return s.a(adapterView, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final <T extends Adapter> Observable<Integer> itemLongClicks(@NotNull AdapterView<T> adapterView) {
        return t.a(adapterView, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final <T extends Adapter> Observable<Integer> itemLongClicks(@NotNull AdapterView<T> adapterView, @NotNull Function0<Boolean> function0) {
        return t.a(adapterView, function0);
    }

    @CheckResult
    @NotNull
    public static final <T extends Adapter> InitialValueObservable<Integer> itemSelections(@NotNull AdapterView<T> adapterView) {
        return u.a(adapterView);
    }

    @CheckResult
    @NotNull
    public static final <T extends Adapter> InitialValueObservable<AdapterViewSelectionEvent> selectionEvents(@NotNull AdapterView<T> adapterView) {
        return v.a(adapterView);
    }
}
