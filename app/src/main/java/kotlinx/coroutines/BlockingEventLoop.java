package kotlinx.coroutines;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: EventLoop.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/BlockingEventLoop;", "Lkotlinx/coroutines/EventLoopImplBase;", "thread", "Ljava/lang/Thread;", "(Ljava/lang/Thread;)V", "getThread", "()Ljava/lang/Thread;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class BlockingEventLoop extends EventLoopImplBase {
    @NotNull
    private final Thread a;

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    @NotNull
    protected Thread getThread() {
        return this.a;
    }

    public BlockingEventLoop(@NotNull Thread thread) {
        this.a = thread;
    }
}
