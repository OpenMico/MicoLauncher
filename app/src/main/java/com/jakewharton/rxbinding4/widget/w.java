package com.jakewharton.rxbinding4.widget;

import android.widget.Adapter;
import androidx.annotation.CheckResult;
import androidx.exifinterface.media.ExifInterface;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterDataChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u0002H\u0007¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"dataChanges", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/widget/Adapter;", "(Landroid/widget/Adapter;)Lcom/jakewharton/rxbinding4/InitialValueObservable;", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/widget/RxAdapter")
/* loaded from: classes2.dex */
final /* synthetic */ class w {
    @CheckResult
    @NotNull
    public static final <T extends Adapter> InitialValueObservable<T> a(@NotNull T dataChanges) {
        Intrinsics.checkParameterIsNotNull(dataChanges, "$this$dataChanges");
        return new b(dataChanges);
    }
}
