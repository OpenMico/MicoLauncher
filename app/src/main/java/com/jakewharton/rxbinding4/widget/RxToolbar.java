package com.jakewharton.rxbinding4.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.annotation.CheckResult;
import androidx.annotation.RequiresApi;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxToolbar__ToolbarItemClickObservableKt", "com/jakewharton/rxbinding4/widget/RxToolbar__ToolbarNavigationClickObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxToolbar {
    @CheckResult
    @RequiresApi(21)
    @NotNull
    public static final Observable<MenuItem> itemClicks(@NotNull Toolbar toolbar) {
        return aq.a(toolbar);
    }

    @CheckResult
    @RequiresApi(21)
    @NotNull
    public static final Observable<Unit> navigationClicks(@NotNull Toolbar toolbar) {
        return ar.a(toolbar);
    }
}
