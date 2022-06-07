package com.jakewharton.rxbinding4.view;

import android.view.MenuItem;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/view/RxMenuItem__MenuItemActionViewEventObservableKt", "com/jakewharton/rxbinding4/view/RxMenuItem__MenuItemClickObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxMenuItem {
    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MenuItemActionViewEvent> actionViewEvents(@NotNull MenuItem menuItem) {
        return c.a(menuItem, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MenuItemActionViewEvent> actionViewEvents(@NotNull MenuItem menuItem, @NotNull Function1<? super MenuItemActionViewEvent, Boolean> function1) {
        return c.a(menuItem, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Unit> clicks(@NotNull MenuItem menuItem) {
        return d.a(menuItem, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Unit> clicks(@NotNull MenuItem menuItem, @NotNull Function1<? super MenuItem, Boolean> function1) {
        return d.a(menuItem, function1);
    }
}
