package com.jakewharton.rxbinding4.view;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding4.InitialValueObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/view/RxView__ViewAttachEventObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewAttachesObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewClickObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewDragObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewFocusChangeObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewHoverObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewKeyObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewLayoutChangeEventObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewLayoutChangeObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewLongClickObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewScrollChangeEventObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewSystemUiVisibilityChangeObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewTouchObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewTreeObserverDrawObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewTreeObserverGlobalLayoutObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewTreeObserverPreDrawObservableKt", "com/jakewharton/rxbinding4/view/RxView__ViewVisibilityConsumerKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxView {
    @CheckResult
    @NotNull
    public static final Observable<ViewAttachEvent> attachEvents(@NotNull View view) {
        return f.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> attaches(@NotNull View view) {
        return g.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> clicks(@NotNull View view) {
        return h.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> detaches(@NotNull View view) {
        return g.b(view);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<DragEvent> drags(@NotNull View view) {
        return i.a(view, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<DragEvent> drags(@NotNull View view, @NotNull Function1<? super DragEvent, Boolean> function1) {
        return i.a(view, function1);
    }

    @CheckResult
    @RequiresApi(16)
    @NotNull
    public static final Observable<Unit> draws(@NotNull View view) {
        return s.a(view);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Boolean> focusChanges(@NotNull View view) {
        return j.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> globalLayouts(@NotNull View view) {
        return t.a(view);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MotionEvent> hovers(@NotNull View view) {
        return k.a(view, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MotionEvent> hovers(@NotNull View view, @NotNull Function1<? super MotionEvent, Boolean> function1) {
        return k.a(view, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<KeyEvent> keys(@NotNull View view) {
        return l.a(view, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<KeyEvent> keys(@NotNull View view, @NotNull Function1<? super KeyEvent, Boolean> function1) {
        return l.a(view, function1);
    }

    @CheckResult
    @NotNull
    public static final Observable<ViewLayoutChangeEvent> layoutChangeEvents(@NotNull View view) {
        return m.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> layoutChanges(@NotNull View view) {
        return n.a(view);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Unit> longClicks(@NotNull View view) {
        return o.a(view, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Unit> longClicks(@NotNull View view, @NotNull Function0<Boolean> function0) {
        return o.a(view, function0);
    }

    @CheckResult
    @NotNull
    public static final Observable<Unit> preDraws(@NotNull View view, @NotNull Function0<Boolean> function0) {
        return u.a(view, function0);
    }

    @CheckResult
    @RequiresApi(23)
    @NotNull
    public static final Observable<ViewScrollChangeEvent> scrollChangeEvents(@NotNull View view) {
        return p.a(view);
    }

    @CheckResult
    @NotNull
    public static final Observable<Integer> systemUiVisibilityChanges(@NotNull View view) {
        return q.a(view);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MotionEvent> touches(@NotNull View view) {
        return r.a(view, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<MotionEvent> touches(@NotNull View view, @NotNull Function1<? super MotionEvent, Boolean> function1) {
        return r.a(view, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Consumer<? super Boolean> visibility(@NotNull View view) {
        return v.a(view, 0, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Consumer<? super Boolean> visibility(@NotNull View view, int i) {
        return v.a(view, i);
    }
}
