package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ConstraintsCommandHandler.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
class b {
    private static final String a = Logger.tagWithPrefix("ConstraintsCmdHandler");
    private final Context b;
    private final int c;
    private final SystemAlarmDispatcher d;
    private final WorkConstraintsTracker e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@NonNull Context context, int i, @NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
        this.b = context;
        this.c = i;
        this.d = systemAlarmDispatcher;
        this.e = new WorkConstraintsTracker(this.b, this.d.e(), null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @WorkerThread
    public void a() {
        List<WorkSpec> scheduledWork = this.d.d().getWorkDatabase().workSpecDao().getScheduledWork();
        ConstraintProxy.a(this.b, scheduledWork);
        this.e.replace(scheduledWork);
        ArrayList<WorkSpec> arrayList = new ArrayList(scheduledWork.size());
        long currentTimeMillis = System.currentTimeMillis();
        for (WorkSpec workSpec : scheduledWork) {
            String str = workSpec.id;
            if (currentTimeMillis >= workSpec.calculateNextRunTime() && (!workSpec.hasConstraints() || this.e.areAllConstraintsMet(str))) {
                arrayList.add(workSpec);
            }
        }
        for (WorkSpec workSpec2 : arrayList) {
            String str2 = workSpec2.id;
            Intent b = CommandHandler.b(this.b, str2);
            Logger.get().debug(a, String.format("Creating a delay_met command for workSpec with id (%s)", str2), new Throwable[0]);
            SystemAlarmDispatcher systemAlarmDispatcher = this.d;
            systemAlarmDispatcher.a(new SystemAlarmDispatcher.a(systemAlarmDispatcher, b, this.c));
        }
        this.e.reset();
    }
}
