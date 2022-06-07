package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpecDao;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class StopWorkRunnable implements Runnable {
    private static final String a = Logger.tagWithPrefix("StopWorkRunnable");
    private final WorkManagerImpl b;
    private final String c;
    private final boolean d;

    public StopWorkRunnable(@NonNull WorkManagerImpl workManagerImpl, @NonNull String str, boolean z) {
        this.b = workManagerImpl;
        this.c = str;
        this.d = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        WorkDatabase workDatabase = this.b.getWorkDatabase();
        Processor processor = this.b.getProcessor();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        workDatabase.beginTransaction();
        try {
            boolean isEnqueuedInForeground = processor.isEnqueuedInForeground(this.c);
            if (this.d) {
                z = this.b.getProcessor().stopForegroundWork(this.c);
            } else {
                if (!isEnqueuedInForeground && workSpecDao.getState(this.c) == WorkInfo.State.RUNNING) {
                    workSpecDao.setState(WorkInfo.State.ENQUEUED, this.c);
                }
                z = this.b.getProcessor().stopWork(this.c);
            }
            Logger.get().debug(a, String.format("StopWorkRunnable for %s; Processor.stopWork = %s", this.c, Boolean.valueOf(z)), new Throwable[0]);
            workDatabase.setTransactionSuccessful();
        } finally {
            workDatabase.endTransaction();
        }
    }
}
