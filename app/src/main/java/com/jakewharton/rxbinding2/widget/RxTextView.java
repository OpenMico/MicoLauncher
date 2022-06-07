package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/* loaded from: classes2.dex */
public final class RxTextView {
    @NonNull
    @CheckResult
    public static Observable<Integer> editorActions(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return editorActions(textView, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<Integer> editorActions(@NonNull TextView textView, @NonNull Predicate<? super Integer> predicate) {
        Preconditions.checkNotNull(textView, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new ak(textView, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<TextViewEditorActionEvent> editorActionEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return editorActionEvents(textView, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<TextViewEditorActionEvent> editorActionEvents(@NonNull TextView textView, @NonNull Predicate<? super TextViewEditorActionEvent> predicate) {
        Preconditions.checkNotNull(textView, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new aj(textView, predicate);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<CharSequence> textChanges(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new am(textView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewTextChangeEvent> textChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new al(textView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewBeforeTextChangeEvent> beforeTextChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new ai(textView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new ah(textView);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> text(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.1
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                textView.setText(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> textRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.2
            /* renamed from: a */
            public void accept(Integer num) {
                textView.setText(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> error(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.3
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                textView.setError(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> errorRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.4
            /* renamed from: a */
            public void accept(Integer num) {
                TextView textView2 = textView;
                textView2.setError(textView2.getContext().getResources().getText(num.intValue()));
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> hint(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.5
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                textView.setHint(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> hintRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.6
            /* renamed from: a */
            public void accept(Integer num) {
                textView.setHint(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> color(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.7
            /* renamed from: a */
            public void accept(Integer num) throws Exception {
                textView.setTextColor(num.intValue());
            }
        };
    }

    private RxTextView() {
        throw new AssertionError("No instances.");
    }
}
