package kotlinx.coroutines.internal;

import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.rtsp.RtspHeaders;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadContext.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\t0\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\r¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/internal/ThreadState;", "", c.R, "Lkotlin/coroutines/CoroutineContext;", "n", "", "(Lkotlin/coroutines/CoroutineContext;I)V", "elements", "", "Lkotlinx/coroutines/ThreadContextElement;", "[Lkotlinx/coroutines/ThreadContextElement;", ai.aA, "values", "[Ljava/lang/Object;", RtspHeaders.Values.APPEND, "", "element", b.p, "restore", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class e {
    @JvmField
    @NotNull
    public final CoroutineContext a;
    @NotNull
    private final Object[] b;
    @NotNull
    private final ThreadContextElement<Object>[] c;
    private int d;

    public e(@NotNull CoroutineContext coroutineContext, int i) {
        this.a = coroutineContext;
        this.b = new Object[i];
        this.c = new ThreadContextElement[i];
    }

    public final void a(@NotNull ThreadContextElement<?> threadContextElement, @Nullable Object obj) {
        Object[] objArr = this.b;
        int i = this.d;
        objArr[i] = obj;
        ThreadContextElement<Object>[] threadContextElementArr = this.c;
        this.d = i + 1;
        threadContextElementArr[i] = threadContextElement;
    }

    public final void a(@NotNull CoroutineContext coroutineContext) {
        int length = this.c.length - 1;
        if (length >= 0) {
            while (true) {
                int i = length - 1;
                ThreadContextElement<Object> threadContextElement = this.c[length];
                Intrinsics.checkNotNull(threadContextElement);
                threadContextElement.restoreThreadContext(coroutineContext, this.b[length]);
                if (i >= 0) {
                    length = i;
                } else {
                    return;
                }
            }
        }
    }
}
