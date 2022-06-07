package com.jakewharton.rxbinding4.widget;

import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxPopupMenu__PopupMenuDismissObservableKt", "com/jakewharton/rxbinding4/widget/RxPopupMenu__PopupMenuItemClickObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxPopupMenu {
    @CheckResult
    @NotNull
    public static final Observable<Unit> dismisses(@NotNull PopupMenu popupMenu) {
        return z.a(popupMenu);
    }

    @CheckResult
    @NotNull
    public static final Observable<MenuItem> itemClicks(@NotNull PopupMenu popupMenu) {
        return aa.a(popupMenu);
    }
}
