package androidx.work.impl.background.greedy;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.RunnableScheduler;
import androidx.work.impl.model.WorkSpec;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class DelayedWorkTracker {
    static final String a = Logger.tagWithPrefix("DelayedWorkTracker");
    final GreedyScheduler b;
    private final RunnableScheduler c;
    private final Map<String, Runnable> d = new HashMap();

    public DelayedWorkTracker(@NonNull GreedyScheduler greedyScheduler, @NonNull RunnableScheduler runnableScheduler) {
        this.b = greedyScheduler;
        this.c = runnableScheduler;
    }

    public void schedule(@NonNull final WorkSpec workSpec) {
        Runnable remove = this.d.remove(workSpec.id);
        if (remove != null) {
            this.c.cancel(remove);
        }
        Runnable runnable = new Runnable() { // from class: androidx.work.impl.background.greedy.DelayedWorkTracker.1
            @Override // java.lang.Runnable
            public void run() {
                Logger.get().debug(DelayedWorkTracker.a, String.format("Scheduling work %s", workSpec.id), new Throwable[0]);
                DelayedWorkTracker.this.b.schedule(workSpec);
            }
        };
        this.d.put(workSpec.id, runnable);
        long currentTimeMillis = System.currentTimeMillis();
        this.c.scheduleWithDelay(workSpec.calculateNextRunTime() - currentTimeMillis, runnable);
    }

    public void unschedule(@NonNull String str) {
        Runnable remove = this.d.remove(str);
        if (remove != null) {
            this.c.cancel(remove);
        }
    }
}
