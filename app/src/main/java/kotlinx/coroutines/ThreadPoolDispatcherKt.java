package kotlinx.coroutines;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ThreadPoolDispatcher.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0007"}, d2 = {"newFixedThreadPoolContext", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "nThreads", "", "name", "", "newSingleThreadContext", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ThreadPoolDispatcherKt {
    @ObsoleteCoroutinesApi
    @NotNull
    public static final ExecutorCoroutineDispatcher newSingleThreadContext(@NotNull String str) {
        return newFixedThreadPoolContext(1, str);
    }

    @ObsoleteCoroutinesApi
    @NotNull
    public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(int i, @NotNull String str) {
        boolean z = true;
        if (i < 1) {
            z = false;
        }
        if (z) {
            return new ThreadPoolDispatcher(i, str);
        }
        throw new IllegalArgumentException(("Expected at least one thread, but " + i + " specified").toString());
    }
}
