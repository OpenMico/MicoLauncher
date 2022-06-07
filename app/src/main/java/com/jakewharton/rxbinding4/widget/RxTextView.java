package com.jakewharton.rxbinding4.widget;

import android.widget.TextView;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxTextView__TextViewAfterTextChangeEventObservableKt", "com/jakewharton/rxbinding4/widget/RxTextView__TextViewBeforeTextChangeEventObservableKt", "com/jakewharton/rxbinding4/widget/RxTextView__TextViewEditorActionEventObservableKt", "com/jakewharton/rxbinding4/widget/RxTextView__TextViewEditorActionObservableKt", "com/jakewharton/rxbinding4/widget/RxTextView__TextViewTextChangeEventObservableKt", "com/jakewharton/rxbinding4/widget/RxTextView__TextViewTextChangesObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxTextView {
    @CheckResult
    @NotNull
    public static final InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(@NotNull TextView textView) {
        return ak.a(textView);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<TextViewBeforeTextChangeEvent> beforeTextChangeEvents(@NotNull TextView textView) {
        return al.a(textView);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<TextViewEditorActionEvent> editorActionEvents(@NotNull TextView textView) {
        return am.a(textView, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<TextViewEditorActionEvent> editorActionEvents(@NotNull TextView textView, @NotNull Function1<? super TextViewEditorActionEvent, Boolean> function1) {
        return am.a(textView, function1);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Integer> editorActions(@NotNull TextView textView) {
        return an.a(textView, null, 1, null);
    }

    @CheckResult
    @JvmOverloads
    @NotNull
    public static final Observable<Integer> editorActions(@NotNull TextView textView, @NotNull Function1<? super Integer, Boolean> function1) {
        return an.a(textView, function1);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<TextViewTextChangeEvent> textChangeEvents(@NotNull TextView textView) {
        return ao.a(textView);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<CharSequence> textChanges(@NotNull TextView textView) {
        return ap.a(textView);
    }
}
