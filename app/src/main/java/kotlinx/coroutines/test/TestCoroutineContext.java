package kotlinx.coroutines.test;

import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TestCoroutineContext.kt */
@Deprecated(level = DeprecationLevel.WARNING, message = "This API has been deprecated to integrate with Structured Concurrency.", replaceWith = @ReplaceWith(expression = "TestCoroutineScope", imports = {"kotlin.coroutines.test"}))
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001:\u0001<B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0019J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0019J$\u0010\u001d\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u00032\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020!0 J$\u0010\"\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u00032\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020!0 J*\u0010#\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u00032\u0018\u0010\u001f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0004\u0012\u00020!0 J$\u0010$\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u00032\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020!0 J\u0006\u0010%\u001a\u00020\u001bJ\u0014\u0010&\u001a\u00020\u001b2\n\u0010'\u001a\u00060(j\u0002`)H\u0002J5\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010+2\u0006\u0010,\u001a\u0002H+2\u0018\u0010-\u001a\u0014\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002H+0.H\u0016¢\u0006\u0002\u00100J(\u00101\u001a\u0004\u0018\u0001H2\"\b\b\u0000\u00102*\u00020/2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H204H\u0096\u0002¢\u0006\u0002\u00105J\u0014\u00106\u001a\u00020\u00012\n\u00103\u001a\u0006\u0012\u0002\b\u000304H\u0016J\u0010\u00107\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0019J\u001c\u00108\u001a\u00020\u00122\n\u0010'\u001a\u00060(j\u0002`)2\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\b\u00109\u001a\u00020\u0006H\u0002J\b\u0010:\u001a\u00020\u0003H\u0016J\u0006\u0010;\u001a\u00020\u001bJ\u0010\u0010;\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lkotlinx/coroutines/test/TestCoroutineContext;", "Lkotlin/coroutines/CoroutineContext;", "name", "", "(Ljava/lang/String;)V", "counter", "", "ctxDispatcher", "Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "ctxHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "exceptions", "", "", "getExceptions", "()Ljava/util/List;", "queue", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/test/TimedRunnableObsolete;", "time", "uncaughtExceptions", "", "advanceTimeBy", "delayTime", "unit", "Ljava/util/concurrent/TimeUnit;", "advanceTimeTo", "", "targetTime", "assertAllUnhandledExceptions", "message", "predicate", "Lkotlin/Function1;", "", "assertAnyUnhandledException", "assertExceptions", "assertUnhandledException", "cancelAllActions", "enqueue", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", BluetoothConstants.GET, "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "now", "postDelayed", "processNextEvent", "toString", "triggerActions", "Dispatcher", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class TestCoroutineContext implements CoroutineContext {
    @Nullable
    private final String a;
    @NotNull
    private final List<Throwable> b;
    @NotNull
    private final a c;
    @NotNull
    private final CoroutineExceptionHandler d;
    @NotNull
    private final ThreadSafeHeap<a> e;
    private long f;
    private long g;

    public TestCoroutineContext() {
        this(null, 1, null);
    }

    public TestCoroutineContext(@Nullable String str) {
        this.a = str;
        this.b = new ArrayList();
        this.c = new a();
        this.d = new TestCoroutineContext$special$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, this);
        this.e = new ThreadSafeHeap<>();
    }

    @Override // kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public /* synthetic */ TestCoroutineContext(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    @NotNull
    public final List<Throwable> getExceptions() {
        return this.b;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public <R> R fold(R r, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return (R) function2.invoke((Object) function2.invoke(r, this.c), this.d);
    }

    @Override // kotlin.coroutines.CoroutineContext
    @Nullable
    public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
        if (key == ContinuationInterceptor.Key) {
            return this.c;
        }
        if (key == CoroutineExceptionHandler.Key) {
            return this.d;
        }
        return null;
    }

    @Override // kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
        return key == ContinuationInterceptor.Key ? this.d : key == CoroutineExceptionHandler.Key ? this.c : this;
    }

    public static /* synthetic */ long now$default(TestCoroutineContext testCoroutineContext, TimeUnit timeUnit, int i, Object obj) {
        if ((i & 1) != 0) {
            timeUnit = TimeUnit.MILLISECONDS;
        }
        return testCoroutineContext.now(timeUnit);
    }

    public final long now(@NotNull TimeUnit timeUnit) {
        return timeUnit.convert(this.g, TimeUnit.NANOSECONDS);
    }

    public static /* synthetic */ long advanceTimeBy$default(TestCoroutineContext testCoroutineContext, long j, TimeUnit timeUnit, int i, Object obj) {
        if ((i & 2) != 0) {
            timeUnit = TimeUnit.MILLISECONDS;
        }
        return testCoroutineContext.advanceTimeBy(j, timeUnit);
    }

    public final long advanceTimeBy(long j, @NotNull TimeUnit timeUnit) {
        long j2 = this.g;
        advanceTimeTo(timeUnit.toNanos(j) + j2, TimeUnit.NANOSECONDS);
        return timeUnit.convert(this.g - j2, TimeUnit.NANOSECONDS);
    }

    public static /* synthetic */ void advanceTimeTo$default(TestCoroutineContext testCoroutineContext, long j, TimeUnit timeUnit, int i, Object obj) {
        if ((i & 2) != 0) {
            timeUnit = TimeUnit.MILLISECONDS;
        }
        testCoroutineContext.advanceTimeTo(j, timeUnit);
    }

    public final void advanceTimeTo(long j, @NotNull TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        a(nanos);
        if (nanos > this.g) {
            this.g = nanos;
        }
    }

    public final void triggerActions() {
        a(this.g);
    }

    public final void cancelAllActions() {
        if (!this.e.isEmpty()) {
            this.e.clear();
        }
    }

    public static /* synthetic */ void assertUnhandledException$default(TestCoroutineContext testCoroutineContext, String str, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        testCoroutineContext.assertUnhandledException(str, function1);
    }

    public final void assertUnhandledException(@NotNull String str, @NotNull Function1<? super Throwable, Boolean> function1) {
        if (this.b.size() != 1 || !function1.invoke(this.b.get(0)).booleanValue()) {
            throw new AssertionError(str);
        }
        this.b.clear();
    }

    public static /* synthetic */ void assertAllUnhandledExceptions$default(TestCoroutineContext testCoroutineContext, String str, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        testCoroutineContext.assertAllUnhandledExceptions(str, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void assertAllUnhandledExceptions(@NotNull String str, @NotNull Function1<? super Throwable, Boolean> function1) {
        List<Throwable> list = this.b;
        boolean z = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!function1.invoke(it.next()).booleanValue()) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (z) {
            this.b.clear();
            return;
        }
        throw new AssertionError(str);
    }

    public static /* synthetic */ void assertAnyUnhandledException$default(TestCoroutineContext testCoroutineContext, String str, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        testCoroutineContext.assertAnyUnhandledException(str, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void assertAnyUnhandledException(@NotNull String str, @NotNull Function1<? super Throwable, Boolean> function1) {
        List<Throwable> list = this.b;
        boolean z = false;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (function1.invoke(it.next()).booleanValue()) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (z) {
            this.b.clear();
            return;
        }
        throw new AssertionError(str);
    }

    public static /* synthetic */ void assertExceptions$default(TestCoroutineContext testCoroutineContext, String str, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        testCoroutineContext.assertExceptions(str, function1);
    }

    public final void assertExceptions(@NotNull String str, @NotNull Function1<? super List<? extends Throwable>, Boolean> function1) {
        if (function1.invoke(this.b).booleanValue()) {
            this.b.clear();
            return;
        }
        throw new AssertionError(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(Runnable runnable) {
        ThreadSafeHeap<a> threadSafeHeap = this.e;
        long j = this.f;
        this.f = 1 + j;
        threadSafeHeap.addLast(new a(runnable, j, 0L, 4, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final a a(Runnable runnable, long j) {
        long j2 = this.f;
        this.f = 1 + j2;
        a aVar = new a(runnable, j2, this.g + TimeUnit.MILLISECONDS.toNanos(j));
        this.e.addLast(aVar);
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long a() {
        a peek = this.e.peek();
        if (peek != null) {
            a(peek.a);
        }
        return this.e.isEmpty() ? Long.MAX_VALUE : 0L;
    }

    private final void a(long j) {
        ThreadSafeHeapNode threadSafeHeapNode;
        while (true) {
            ThreadSafeHeap<a> threadSafeHeap = this.e;
            synchronized (threadSafeHeap) {
                a firstImpl = threadSafeHeap.firstImpl();
                threadSafeHeapNode = null;
                if (firstImpl != null) {
                    if (firstImpl.a <= j) {
                        threadSafeHeapNode = threadSafeHeap.removeAtImpl(0);
                    } else {
                        threadSafeHeapNode = null;
                    }
                }
            }
            a aVar = (a) threadSafeHeapNode;
            if (aVar != null) {
                if (aVar.a != 0) {
                    this.g = aVar.a;
                }
                aVar.run();
            } else {
                return;
            }
        }
    }

    @NotNull
    public String toString() {
        String str = this.a;
        return str == null ? Intrinsics.stringPlus("TestCoroutineContext@", DebugStringsKt.getHexAddress(this)) : str;
    }

    /* compiled from: TestCoroutineContext.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\tj\u0002`\nH\u0016J$\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\n\u0010\b\u001a\u00060\tj\u0002`\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u001e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016¨\u0006\u0017"}, d2 = {"Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/Delay;", "(Lkotlinx/coroutines/test/TestCoroutineContext;)V", "dispatch", "", c.R, "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "processNextEvent", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "shouldBeProcessedFromContext", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private final class a extends EventLoop implements Delay {
        @Override // kotlinx.coroutines.EventLoop
        public boolean shouldBeProcessedFromContext() {
            return true;
        }

        public a() {
            EventLoop.incrementUseCount$default(this, false, 1, null);
        }

        @Override // kotlinx.coroutines.Delay
        @Nullable
        public Object delay(long j, @NotNull Continuation<? super Unit> continuation) {
            return Delay.DefaultImpls.delay(this, j, continuation);
        }

        @Override // kotlinx.coroutines.CoroutineDispatcher
        public void dispatch(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
            TestCoroutineContext.this.a(runnable);
        }

        @Override // kotlinx.coroutines.Delay
        public void scheduleResumeAfterDelay(long j, @NotNull final CancellableContinuation<? super Unit> cancellableContinuation) {
            TestCoroutineContext.this.a(new Runnable() { // from class: kotlinx.coroutines.test.TestCoroutineContext$Dispatcher$scheduleResumeAfterDelay$$inlined$Runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    CancellableContinuation.this.resumeUndispatched(this, Unit.INSTANCE);
                }
            }, j);
        }

        @Override // kotlinx.coroutines.Delay
        @NotNull
        public DisposableHandle invokeOnTimeout(long j, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
            final a a = TestCoroutineContext.this.a(runnable, j);
            final TestCoroutineContext testCoroutineContext = TestCoroutineContext.this;
            return new DisposableHandle() { // from class: kotlinx.coroutines.test.TestCoroutineContext$Dispatcher$invokeOnTimeout$1
                @Override // kotlinx.coroutines.DisposableHandle
                public void dispose() {
                    ThreadSafeHeap threadSafeHeap;
                    threadSafeHeap = TestCoroutineContext.this.e;
                    threadSafeHeap.remove(a);
                }
            };
        }

        @Override // kotlinx.coroutines.EventLoop
        public long processNextEvent() {
            return TestCoroutineContext.this.a();
        }

        @Override // kotlinx.coroutines.CoroutineDispatcher
        @NotNull
        public String toString() {
            return "Dispatcher(" + TestCoroutineContext.this + ')';
        }
    }
}
