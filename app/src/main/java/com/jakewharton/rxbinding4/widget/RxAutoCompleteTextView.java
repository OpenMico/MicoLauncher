package com.jakewharton.rxbinding4.widget;

import android.widget.AutoCompleteTextView;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoCompleteTextViewItemClickEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxAutoCompleteTextView__AutoCompleteTextViewItemClickEventObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxAutoCompleteTextView {
    @CheckResult
    @NotNull
    public static final Observable<AdapterViewItemClickEvent> itemClickEvents(@NotNull AutoCompleteTextView autoCompleteTextView) {
        return x.a(autoCompleteTextView);
    }
}
