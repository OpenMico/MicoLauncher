package kotlinx.coroutines;

import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

/* compiled from: ThreadPoolDispatcher.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/PoolThread;", "Ljava/lang/Thread;", "dispatcher", "Lkotlinx/coroutines/ThreadPoolDispatcher;", OneTrackWorldUrl.ACTION_TARGET, "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "name", "", "(Lkotlinx/coroutines/ThreadPoolDispatcher;Ljava/lang/Runnable;Ljava/lang/String;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class PoolThread extends Thread {
    @JvmField
    @NotNull
    public final ThreadPoolDispatcher dispatcher;

    public PoolThread(@NotNull ThreadPoolDispatcher threadPoolDispatcher, @NotNull Runnable runnable, @NotNull String str) {
        super(runnable, str);
        this.dispatcher = threadPoolDispatcher;
        setDaemon(true);
    }
}
