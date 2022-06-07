package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HandlerDispatcher.kt */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0016J\u0013\u0010\u0016\u001a\u00020\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010 \u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00100\"H\u0016J\b\u0010#\u001a\u00020\u0006H\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lkotlinx/coroutines/android/HandlerContext;", "Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/Delay;", "handler", "Landroid/os/Handler;", "name", "", "(Landroid/os/Handler;Ljava/lang/String;)V", "invokeImmediately", "", "(Landroid/os/Handler;Ljava/lang/String;Z)V", "_immediate", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerContext;", "dispatch", "", c.R, "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "isDispatchNeeded", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "kotlinx-coroutines-android"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class HandlerContext extends HandlerDispatcher implements Delay {
    @Nullable
    private volatile HandlerContext _immediate;
    @NotNull
    private final Handler a;
    @Nullable
    private final String b;
    private final boolean c;
    @NotNull
    private final HandlerContext d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private HandlerContext(Handler handler, String str, boolean z) {
        super(null);
        HandlerContext handlerContext = null;
        this.a = handler;
        this.b = str;
        this.c = z;
        this._immediate = this.c ? this : handlerContext;
        HandlerContext handlerContext2 = this._immediate;
        if (handlerContext2 == null) {
            handlerContext2 = new HandlerContext(this.a, this.b, true);
            this._immediate = handlerContext2;
            Unit unit = Unit.INSTANCE;
        }
        this.d = handlerContext2;
    }

    public /* synthetic */ HandlerContext(Handler handler, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(handler, (i & 2) != 0 ? null : str);
    }

    public HandlerContext(@NotNull Handler handler, @Nullable String str) {
        this(handler, str, false);
    }

    @Override // kotlinx.coroutines.android.HandlerDispatcher, kotlinx.coroutines.MainCoroutineDispatcher
    @NotNull
    public HandlerContext getImmediate() {
        return this.d;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public boolean isDispatchNeeded(@NotNull CoroutineContext coroutineContext) {
        return !this.c || !Intrinsics.areEqual(Looper.myLooper(), this.a.getLooper());
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatch(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
        this.a.post(runnable);
    }

    /* compiled from: HandlerDispatcher.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n"}, d2 = {"<anonymous>", "", "it", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class a extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ Runnable $block;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Runnable runnable) {
            super(1);
            this.$block = runnable;
        }

        public final void a(@Nullable Throwable th) {
            HandlerContext.this.a.removeCallbacks(this.$block);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }
    }

    @Override // kotlinx.coroutines.android.HandlerDispatcher, kotlinx.coroutines.Delay
    @NotNull
    public DisposableHandle invokeOnTimeout(long j, @NotNull final Runnable runnable, @NotNull CoroutineContext coroutineContext) {
        this.a.postDelayed(runnable, RangesKt.coerceAtMost(j, (long) DurationKt.MAX_MILLIS));
        return new DisposableHandle() { // from class: kotlinx.coroutines.android.HandlerContext$invokeOnTimeout$1
            @Override // kotlinx.coroutines.DisposableHandle
            public void dispose() {
                HandlerContext.this.a.removeCallbacks(runnable);
            }
        };
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher
    @NotNull
    public String toString() {
        String stringInternalImpl = toStringInternalImpl();
        if (stringInternalImpl != null) {
            return stringInternalImpl;
        }
        HandlerContext handlerContext = this;
        String str = handlerContext.b;
        if (str == null) {
            str = handlerContext.a.toString();
        }
        return handlerContext.c ? Intrinsics.stringPlus(str, ".immediate") : str;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof HandlerContext) && ((HandlerContext) obj).a == this.a;
    }

    public int hashCode() {
        return System.identityHashCode(this.a);
    }

    @Override // kotlinx.coroutines.Delay
    public void scheduleResumeAfterDelay(long j, @NotNull final CancellableContinuation<? super Unit> cancellableContinuation) {
        Runnable handlerContext$scheduleResumeAfterDelay$$inlined$Runnable$1 = new Runnable() { // from class: kotlinx.coroutines.android.HandlerContext$scheduleResumeAfterDelay$$inlined$Runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                CancellableContinuation.this.resumeUndispatched(this, Unit.INSTANCE);
            }
        };
        this.a.postDelayed(handlerContext$scheduleResumeAfterDelay$$inlined$Runnable$1, RangesKt.coerceAtMost(j, (long) DurationKt.MAX_MILLIS));
        cancellableContinuation.invokeOnCancellation(new a(handlerContext$scheduleResumeAfterDelay$$inlined$Runnable$1));
    }
}
