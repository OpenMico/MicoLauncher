package kotlinx.coroutines.debug.internal;

import com.umeng.analytics.pro.c;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugCoroutineInfo.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\fR\u0019\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8G¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "", "source", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", c.R, "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/CoroutineContext;)V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "creationStackBottom", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCreationStackBottom", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "creationStackTrace", "", "Ljava/lang/StackTraceElement;", "getCreationStackTrace", "()Ljava/util/List;", "lastObservedFrame", "getLastObservedFrame", "lastObservedStackTrace", "lastObservedThread", "Ljava/lang/Thread;", "getLastObservedThread", "()Ljava/lang/Thread;", "sequenceNumber", "", "getSequenceNumber", "()J", XiaomiOAuthConstants.EXTRA_STATE_2, "", "getState", "()Ljava/lang/String;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
@PublishedApi
/* loaded from: classes5.dex */
public final class DebugCoroutineInfo {
    @NotNull
    private final CoroutineContext a;
    @Nullable
    private final CoroutineStackFrame b;
    private final long c;
    @NotNull
    private final List<StackTraceElement> d;
    @NotNull
    private final String e;
    @Nullable
    private final Thread f;
    @Nullable
    private final CoroutineStackFrame g;
    @NotNull
    private final List<StackTraceElement> h;

    public DebugCoroutineInfo(@NotNull DebugCoroutineInfoImpl debugCoroutineInfoImpl, @NotNull CoroutineContext coroutineContext) {
        this.a = coroutineContext;
        this.b = debugCoroutineInfoImpl.getCreationStackBottom();
        this.c = debugCoroutineInfoImpl.sequenceNumber;
        this.d = debugCoroutineInfoImpl.getCreationStackTrace();
        this.e = debugCoroutineInfoImpl.getState();
        this.f = debugCoroutineInfoImpl.lastObservedThread;
        this.g = debugCoroutineInfoImpl.getLastObservedFrame$kotlinx_coroutines_core();
        this.h = debugCoroutineInfoImpl.lastObservedStackTrace();
    }

    @NotNull
    public final CoroutineContext getContext() {
        return this.a;
    }

    @Nullable
    public final CoroutineStackFrame getCreationStackBottom() {
        return this.b;
    }

    public final long getSequenceNumber() {
        return this.c;
    }

    @NotNull
    public final List<StackTraceElement> getCreationStackTrace() {
        return this.d;
    }

    @NotNull
    public final String getState() {
        return this.e;
    }

    @Nullable
    public final Thread getLastObservedThread() {
        return this.f;
    }

    @Nullable
    public final CoroutineStackFrame getLastObservedFrame() {
        return this.g;
    }

    @JvmName(name = "lastObservedStackTrace")
    @NotNull
    public final List<StackTraceElement> lastObservedStackTrace() {
        return this.h;
    }
}
