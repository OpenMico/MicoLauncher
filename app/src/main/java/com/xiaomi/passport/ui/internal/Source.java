package com.xiaomi.passport.ui.internal;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.exifinterface.media.ExifInterface;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.passport.ui.internal.Result;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Source.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u0017*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0003J.\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0007J\"\u0010\u0004\u001a\u00020\u00052\u0018\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0004\u0012\u00020\u00050\u0007H\u0002J\"\u0010\f\u001a\u00020\u00052\u0018\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0004\u0012\u00020\u00050\u0007H\u0002J\u001a\u0010\r\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0007J\u000b\u0010\u000e\u001a\u00028\u0000¢\u0006\u0002\u0010\u000fJ\"\u0010\u000e\u001a\u00020\u00052\u0018\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0004\u0012\u00020\u00050\u0007H\u0002J5\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0000\"\u0004\b\u0001\u0010\u00112!\u0010\u0012\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u0002H\u00110\u0007J\r\u0010\u0016\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000f¨\u0006\u0018"}, d2 = {"Lcom/xiaomi/passport/ui/internal/Source;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", BluetoothConstants.GET, "", StatConstants.BIND_SUCCESS, "Lkotlin/Function1;", "fail", "", "observer", "Lcom/xiaomi/passport/ui/internal/Result;", "getAsync", "getSuccess", "getSync", "()Ljava/lang/Object;", "next", ExifInterface.GPS_DIRECTION_TRUE, "func1", "Lkotlin/ParameterName;", "name", b.p, UMModuleRegister.PROCESS, "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class Source<V> {
    public static final Companion Companion = new Companion(null);

    public abstract V process();

    /* compiled from: Source.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0007¨\u0006\b"}, d2 = {"Lcom/xiaomi/passport/ui/internal/Source$Companion;", "", "()V", "from", "Lcom/xiaomi/passport/ui/internal/Source;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "func", "Lkotlin/Function0;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final <V> Source<V> from(@NotNull final Function0<? extends V> func) {
            Intrinsics.checkParameterIsNotNull(func, "func");
            return new Source<V>() { // from class: com.xiaomi.passport.ui.internal.Source$Companion$from$1
                /* JADX WARN: Type inference failed for: r0v1, types: [V, java.lang.Object] */
                @Override // com.xiaomi.passport.ui.internal.Source
                public V process() {
                    return Function0.this.invoke();
                }
            };
        }
    }

    private final void get(Function1<? super Result<V>, Unit> function1) {
        boolean enable_test = SourceTool.Companion.getENABLE_TEST();
        if (enable_test) {
            getSync(function1);
        } else if (!enable_test) {
            getAsync(function1);
        }
    }

    public final void get(@NotNull Function1<? super V, Unit> success, @NotNull Function1<? super Throwable, Unit> fail) {
        Intrinsics.checkParameterIsNotNull(success, "success");
        Intrinsics.checkParameterIsNotNull(fail, "fail");
        get(new Source$get$1(success, fail));
    }

    public final void getSuccess(@NotNull Function1<? super V, Unit> success) {
        Intrinsics.checkParameterIsNotNull(success, "success");
        get(success, Source$getSuccess$1.INSTANCE);
    }

    @NotNull
    public final <T> Source<T> next(@NotNull final Function1<? super V, ? extends T> func1) {
        Intrinsics.checkParameterIsNotNull(func1, "func1");
        return new Source<T>() { // from class: com.xiaomi.passport.ui.internal.Source$next$1
            /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.Object] */
            @Override // com.xiaomi.passport.ui.internal.Source
            public T process() {
                return func1.invoke(Source.this.process());
            }
        };
    }

    private final void getSync(Function1<? super Result<V>, Unit> function1) {
        try {
            function1.invoke(new Result.Success(process()));
        } catch (Throwable th) {
            function1.invoke(new Result.Failure(th));
        }
    }

    public final V getSync() {
        return process();
    }

    private final void getAsync(final Function1<? super Result<V>, Unit> function1) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.ui.internal.Source$getAsync$1
            @Override // java.lang.Runnable
            public final void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                try {
                    final Object process = Source.this.process();
                    handler.post(new Runnable() { // from class: com.xiaomi.passport.ui.internal.Source$getAsync$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            function1.invoke(new Result.Success(process));
                        }
                    });
                } catch (Throwable th) {
                    handler.post(new Runnable() { // from class: com.xiaomi.passport.ui.internal.Source$getAsync$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            function1.invoke(new Result.Failure(th));
                        }
                    });
                }
            }
        });
    }
}
