package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

@RequiresApi(21)
/* loaded from: classes2.dex */
public final class RxToolbar {
    @NonNull
    @CheckResult
    public static Observable<MenuItem> itemClicks(@NonNull Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new an(toolbar);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> navigationClicks(@NonNull Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new ao(toolbar);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> title(@NonNull final Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxToolbar.1
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                toolbar.setTitle(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> titleRes(@NonNull final Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxToolbar.2
            /* renamed from: a */
            public void accept(Integer num) {
                toolbar.setTitle(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> subtitle(@NonNull final Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxToolbar.3
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                toolbar.setSubtitle(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> subtitleRes(@NonNull final Toolbar toolbar) {
        Preconditions.checkNotNull(toolbar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxToolbar.4
            /* renamed from: a */
            public void accept(Integer num) {
                toolbar.setSubtitle(num.intValue());
            }
        };
    }

    private RxToolbar() {
        throw new AssertionError("No instances.");
    }
}
