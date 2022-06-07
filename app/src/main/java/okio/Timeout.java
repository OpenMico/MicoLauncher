package okio;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Timeout.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0000H\u0016J\b\u0010\t\u001a\u00020\u0000H\u0016J\u0016\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001f\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00002\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012H\u0086\bJ\b\u0010\u0013\u001a\u00020\u000fH\u0016J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lokio/Timeout;", "", "()V", "deadlineNanoTime", "", "hasDeadline", "", "timeoutNanos", "clearDeadline", "clearTimeout", "deadline", "duration", "unit", "Ljava/util/concurrent/TimeUnit;", "intersectWith", "", "other", "block", "Lkotlin/Function0;", "throwIfReached", RtspHeaders.Values.TIMEOUT, "waitUntilNotified", "monitor", "Companion", "okio"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public class Timeout {
    public static final Companion Companion = new Companion(null);
    @JvmField
    @NotNull
    public static final Timeout NONE = new Timeout() { // from class: okio.Timeout$Companion$NONE$1
        @Override // okio.Timeout
        public void throwIfReached() {
        }

        @Override // okio.Timeout
        @NotNull
        public Timeout timeout(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            return this;
        }

        @Override // okio.Timeout
        @NotNull
        public Timeout deadlineNanoTime(long j) {
            return this;
        }
    };
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    @NotNull
    public Timeout timeout(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        if (j >= 0) {
            this.timeoutNanos = unit.toNanos(j);
            return this;
        }
        throw new IllegalArgumentException(("timeout < 0: " + j).toString());
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public long deadlineNanoTime() {
        if (this.hasDeadline) {
            return this.deadlineNanoTime;
        }
        throw new IllegalStateException("No deadline".toString());
    }

    @NotNull
    public Timeout deadlineNanoTime(long j) {
        this.hasDeadline = true;
        this.deadlineNanoTime = j;
        return this;
    }

    @NotNull
    public final Timeout deadline(long j, @NotNull TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        if (j > 0) {
            return deadlineNanoTime(System.nanoTime() + unit.toNanos(j));
        }
        throw new IllegalArgumentException(("duration <= 0: " + j).toString());
    }

    @NotNull
    public Timeout clearTimeout() {
        this.timeoutNanos = 0L;
        return this;
    }

    @NotNull
    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        } else if (this.hasDeadline && this.deadlineNanoTime - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    public final void waitUntilNotified(@NotNull Object monitor) throws InterruptedIOException {
        Intrinsics.checkParameterIsNotNull(monitor, "monitor");
        try {
            boolean hasDeadline = hasDeadline();
            long timeoutNanos = timeoutNanos();
            long j = 0;
            if (hasDeadline || timeoutNanos != 0) {
                long nanoTime = System.nanoTime();
                if (hasDeadline && timeoutNanos != 0) {
                    timeoutNanos = Math.min(timeoutNanos, deadlineNanoTime() - nanoTime);
                } else if (hasDeadline) {
                    timeoutNanos = deadlineNanoTime() - nanoTime;
                }
                if (timeoutNanos > 0) {
                    long j2 = timeoutNanos / 1000000;
                    monitor.wait(j2, (int) (timeoutNanos - (1000000 * j2)));
                    j = System.nanoTime() - nanoTime;
                }
                if (j >= timeoutNanos) {
                    throw new InterruptedIOException(RtspHeaders.Values.TIMEOUT);
                }
                return;
            }
            monitor.wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        }
    }

    public final void intersectWith(@NotNull Timeout other, @NotNull Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(block, "block");
        long timeoutNanos = timeoutNanos();
        timeout(Companion.minTimeout(other.timeoutNanos(), timeoutNanos()), TimeUnit.NANOSECONDS);
        if (hasDeadline()) {
            long deadlineNanoTime = deadlineNanoTime();
            if (other.hasDeadline()) {
                deadlineNanoTime(Math.min(deadlineNanoTime(), other.deadlineNanoTime()));
            }
            try {
                block.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                if (other.hasDeadline()) {
                    deadlineNanoTime(deadlineNanoTime);
                }
                InlineMarker.finallyEnd(1);
            }
        } else {
            if (other.hasDeadline()) {
                deadlineNanoTime(other.deadlineNanoTime());
            }
            try {
                block.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                if (other.hasDeadline()) {
                    clearDeadline();
                }
                InlineMarker.finallyEnd(1);
            }
        }
    }

    /* compiled from: Timeout.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lokio/Timeout$Companion;", "", "()V", "NONE", "Lokio/Timeout;", "minTimeout", "", "aNanos", "bNanos", "okio"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        public final long minTimeout(long j, long j2) {
            return (j != 0 && (j2 == 0 || j < j2)) ? j : j2;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
