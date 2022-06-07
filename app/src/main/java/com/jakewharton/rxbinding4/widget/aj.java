package com.jakewharton.rxbinding4.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SeekBarChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¨\u0006\u0006"}, d2 = {"changes", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "", "Landroid/widget/SeekBar;", "systemChanges", "userChanges", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/widget/RxSeekBar")
/* loaded from: classes2.dex */
final /* synthetic */ class aj {
    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> a(@NotNull SeekBar changes) {
        Intrinsics.checkParameterIsNotNull(changes, "$this$changes");
        return new av(changes, null);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> b(@NotNull SeekBar userChanges) {
        Intrinsics.checkParameterIsNotNull(userChanges, "$this$userChanges");
        return new av(userChanges, true);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> c(@NotNull SeekBar systemChanges) {
        Intrinsics.checkParameterIsNotNull(systemChanges, "$this$systemChanges");
        return new av(systemChanges, false);
    }
}
