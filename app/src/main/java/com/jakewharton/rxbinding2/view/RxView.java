package com.jakewharton.rxbinding2.view;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
public final class RxView {
    @NonNull
    @CheckResult
    public static Observable<Object> attaches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new l(view, true);
    }

    @NonNull
    @CheckResult
    public static Observable<ViewAttachEvent> attachEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new k(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> detaches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new l(view, false);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> clicks(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new m(view);
    }

    @NonNull
    @CheckResult
    public static Observable<DragEvent> drags(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new n(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<DragEvent> drags(@NonNull View view, @NonNull Predicate<? super DragEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new n(view, predicate);
    }

    @NonNull
    @CheckResult
    @RequiresApi(16)
    public static Observable<Object> draws(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new y(view);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Boolean> focusChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new o(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> globalLayouts(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new z(view);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> hovers(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new q(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> hovers(@NonNull View view, @NonNull Predicate<? super MotionEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new q(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> layoutChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new t(view);
    }

    @NonNull
    @CheckResult
    public static Observable<ViewLayoutChangeEvent> layoutChangeEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new s(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> longClicks(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new u(view, Functions.CALLABLE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> longClicks(@NonNull View view, @NonNull Callable<Boolean> callable) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(callable, "handled == null");
        return new u(view, callable);
    }

    @NonNull
    @CheckResult
    public static Observable<Object> preDraws(@NonNull View view, @NonNull Callable<Boolean> callable) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(callable, "proceedDrawingPass == null");
        return new aa(view, callable);
    }

    @NonNull
    @CheckResult
    @RequiresApi(23)
    public static Observable<ViewScrollChangeEvent> scrollChangeEvents(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new v(view);
    }

    @NonNull
    @CheckResult
    public static Observable<Integer> systemUiVisibilityChanges(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new w(view);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> touches(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new x(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<MotionEvent> touches(@NonNull View view, @NonNull Predicate<? super MotionEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new x(view, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<KeyEvent> keys(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new r(view, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<KeyEvent> keys(@NonNull View view, @NonNull Predicate<? super KeyEvent> predicate) {
        Preconditions.checkNotNull(view, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new r(view, predicate);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> activated(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.1
            /* renamed from: a */
            public void accept(Boolean bool) {
                view.setActivated(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> clickable(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.2
            /* renamed from: a */
            public void accept(Boolean bool) {
                view.setClickable(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> enabled(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.3
            /* renamed from: a */
            public void accept(Boolean bool) {
                view.setEnabled(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> pressed(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.4
            /* renamed from: a */
            public void accept(Boolean bool) {
                view.setPressed(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    @Deprecated
    public static Consumer<? super Boolean> selected(@NonNull final View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.5
            /* renamed from: a */
            public void accept(Boolean bool) {
                view.setSelected(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> visibility(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return visibility(view, 8);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> visibility(@NonNull final View view, final int i) {
        Preconditions.checkNotNull(view, "view == null");
        if (i == 0) {
            throw new IllegalArgumentException("Setting visibility to VISIBLE when false would have no effect.");
        } else if (i == 4 || i == 8) {
            return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.view.RxView.6
                /* renamed from: a */
                public void accept(Boolean bool) {
                    view.setVisibility(bool.booleanValue() ? 0 : i);
                }
            };
        } else {
            throw new IllegalArgumentException("Must set visibility to INVISIBLE or GONE when false.");
        }
    }

    private RxView() {
        throw new AssertionError("No instances.");
    }
}
