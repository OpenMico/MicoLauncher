package miuix.animation.internal;

import androidx.annotation.NonNull;
import com.xiaomi.mipush.sdk.Constants;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
public class ThreadPoolUtil {
    public static final int MAX_SPLIT_COUNT;
    private static final int a = Runtime.getRuntime().availableProcessors();
    private static final int b;
    private static final ThreadPoolExecutor c;
    private static final Executor d;

    static {
        int i = a;
        MAX_SPLIT_COUNT = (i * 2) + 1;
        b = i < 4 ? 0 : (i / 2) + 1;
        c = new ThreadPoolExecutor(b, MAX_SPLIT_COUNT + 3, 30L, TimeUnit.SECONDS, new SynchronousQueue(), a("AnimThread"), new RejectedExecutionHandler() { // from class: miuix.animation.internal.ThreadPoolUtil.1
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                ThreadPoolUtil.d.execute(runnable);
            }
        });
        d = Executors.newSingleThreadExecutor(a("WorkThread"));
    }

    private static ThreadFactory a(final String str) {
        return new ThreadFactory() { // from class: miuix.animation.internal.ThreadPoolUtil.2
            final AtomicInteger a = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable, str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.a.getAndIncrement());
                thread.setPriority(5);
                return thread;
            }
        };
    }

    public static void post(Runnable runnable) {
        c.execute(runnable);
    }

    public static void getSplitCount(int i, int[] iArr) {
        int max = Math.max(i / 4000, 1);
        int i2 = MAX_SPLIT_COUNT;
        if (max > i2) {
            max = i2;
        }
        iArr[0] = max;
        iArr[1] = (int) Math.ceil(i / max);
    }
}
