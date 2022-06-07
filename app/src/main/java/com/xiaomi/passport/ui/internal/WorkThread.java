package com.xiaomi.passport.ui.internal;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Source.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J(\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\t¨\u0006\n"}, d2 = {"Lcom/xiaomi/passport/ui/internal/WorkThread;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "doInWorkThread", "", "work", "Lkotlin/Function0;", "callback", "Lkotlin/Function1;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class WorkThread<T> {
    public final void doInWorkThread(@NotNull final Function0<? extends T> work, @NotNull final Function1<? super T, Unit> callback) {
        Intrinsics.checkParameterIsNotNull(work, "work");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.ui.internal.WorkThread$doInWorkThread$1
            @Override // java.lang.Runnable
            public final void run() {
                final Object invoke = Function0.this.invoke();
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.passport.ui.internal.WorkThread$doInWorkThread$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.invoke(invoke);
                    }
                });
            }
        });
    }
}
