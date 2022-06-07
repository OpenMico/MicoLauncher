package kotlinx.coroutines;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: EventLoop.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0000\u001a\b\u0010\u0002\u001a\u00020\u0003H\u0007¨\u0006\u0004"}, d2 = {"createEventLoop", "Lkotlinx/coroutines/EventLoop;", "processNextEventInCurrentThread", "", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class EventLoopKt {
    @NotNull
    public static final EventLoop createEventLoop() {
        return new BlockingEventLoop(Thread.currentThread());
    }

    @InternalCoroutinesApi
    public static final long processNextEventInCurrentThread() {
        EventLoop currentOrNull$kotlinx_coroutines_core = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
        if (currentOrNull$kotlinx_coroutines_core == null) {
            return Long.MAX_VALUE;
        }
        return currentOrNull$kotlinx_coroutines_core.processNextEvent();
    }
}
