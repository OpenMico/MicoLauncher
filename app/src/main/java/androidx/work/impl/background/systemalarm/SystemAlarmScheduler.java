package androidx.work.impl.background.systemalarm;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.Scheduler;
import androidx.work.impl.model.WorkSpec;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemAlarmScheduler implements Scheduler {
    private static final String a = Logger.tagWithPrefix("SystemAlarmScheduler");
    private final Context b;

    @Override // androidx.work.impl.Scheduler
    public boolean hasLimitedSchedulingSlots() {
        return true;
    }

    public SystemAlarmScheduler(@NonNull Context context) {
        this.b = context.getApplicationContext();
    }

    @Override // androidx.work.impl.Scheduler
    public void schedule(@NonNull WorkSpec... workSpecArr) {
        for (WorkSpec workSpec : workSpecArr) {
            a(workSpec);
        }
    }

    @Override // androidx.work.impl.Scheduler
    public void cancel(@NonNull String str) {
        this.b.startService(CommandHandler.c(this.b, str));
    }

    private void a(@NonNull WorkSpec workSpec) {
        Logger.get().debug(a, String.format("Scheduling work with workSpecId %s", workSpec.id), new Throwable[0]);
        this.b.startService(CommandHandler.a(this.b, workSpec.id));
    }
}
