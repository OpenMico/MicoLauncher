package kotlinx.coroutines.scheduling;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.internal.d;
import org.jetbrains.annotations.NotNull;

/* compiled from: Tasks.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\b\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0010\u0010\u000b\u001a\u00020\u00078\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0012\u0010\f\u001a\u00020\r8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000\"\u0019\u0010\u000e\u001a\u00020\u000f*\u00020\u00108À\u0002X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0011¨\u0006\u0012"}, d2 = {"BLOCKING_DEFAULT_PARALLELISM", "", "CORE_POOL_SIZE", "DEFAULT_DISPATCHER_NAME", "", "DEFAULT_SCHEDULER_NAME", "IDLE_WORKER_KEEP_ALIVE_NS", "", "MAX_POOL_SIZE", "TASK_NON_BLOCKING", "TASK_PROBABLY_BLOCKING", "WORK_STEALING_TIME_RESOLUTION_NS", "schedulerTimeSource", "Lkotlinx/coroutines/scheduling/SchedulerTimeSource;", "isBlocking", "", "Lkotlinx/coroutines/scheduling/Task;", "(Lkotlinx/coroutines/scheduling/Task;)Z", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class TasksKt {
    @JvmField
    public static final int BLOCKING_DEFAULT_PARALLELISM;
    @JvmField
    public static final int CORE_POOL_SIZE;
    @NotNull
    public static final String DEFAULT_DISPATCHER_NAME = "Dispatchers.Default";
    @NotNull
    public static final String DEFAULT_SCHEDULER_NAME = "DefaultDispatcher";
    @JvmField
    public static final long IDLE_WORKER_KEEP_ALIVE_NS;
    @JvmField
    public static final int MAX_POOL_SIZE;
    public static final int TASK_NON_BLOCKING = 0;
    public static final int TASK_PROBABLY_BLOCKING = 1;
    @JvmField
    public static final long WORK_STEALING_TIME_RESOLUTION_NS;
    @JvmField
    @NotNull
    public static SchedulerTimeSource schedulerTimeSource = NanoTimeSource.INSTANCE;

    static {
        long systemProp;
        int a;
        int a2;
        int a3;
        long systemProp2;
        systemProp = SystemPropsKt.systemProp("kotlinx.coroutines.scheduler.resolution.ns", 100000L, (r14 & 4) != 0 ? 1L : 0L, (r14 & 8) != 0 ? Long.MAX_VALUE : 0L);
        WORK_STEALING_TIME_RESOLUTION_NS = systemProp;
        a = d.a("kotlinx.coroutines.scheduler.blocking.parallelism", 16, 0, 0, 12, (Object) null);
        BLOCKING_DEFAULT_PARALLELISM = a;
        a2 = d.a("kotlinx.coroutines.scheduler.core.pool.size", RangesKt.coerceAtLeast(SystemPropsKt.getAVAILABLE_PROCESSORS(), 2), 1, 0, 8, (Object) null);
        CORE_POOL_SIZE = a2;
        a3 = d.a("kotlinx.coroutines.scheduler.max.pool.size", RangesKt.coerceIn(SystemPropsKt.getAVAILABLE_PROCESSORS() * 128, CORE_POOL_SIZE, (int) CoroutineScheduler.MAX_SUPPORTED_POOL_SIZE), 0, (int) CoroutineScheduler.MAX_SUPPORTED_POOL_SIZE, 4, (Object) null);
        MAX_POOL_SIZE = a3;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        systemProp2 = SystemPropsKt.systemProp("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, (r14 & 4) != 0 ? 1L : 0L, (r14 & 8) != 0 ? Long.MAX_VALUE : 0L);
        IDLE_WORKER_KEEP_ALIVE_NS = timeUnit.toNanos(systemProp2);
    }

    public static final boolean isBlocking(@NotNull Task task) {
        return task.taskContext.getTaskMode() == 1;
    }
}
