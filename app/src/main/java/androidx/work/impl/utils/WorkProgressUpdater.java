package androidx.work.impl.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.ProgressUpdater;
import androidx.work.WorkInfo;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.model.WorkProgress;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkProgressUpdater implements ProgressUpdater {
    static final String a = Logger.tagWithPrefix("WorkProgressUpdater");
    final WorkDatabase b;
    final TaskExecutor c;

    public WorkProgressUpdater(@NonNull WorkDatabase workDatabase, @NonNull TaskExecutor taskExecutor) {
        this.b = workDatabase;
        this.c = taskExecutor;
    }

    @Override // androidx.work.ProgressUpdater
    @NonNull
    public ListenableFuture<Void> updateProgress(@NonNull Context context, @NonNull final UUID uuid, @NonNull final Data data) {
        final SettableFuture create = SettableFuture.create();
        this.c.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.utils.WorkProgressUpdater.1
            @Override // java.lang.Runnable
            public void run() {
                String uuid2 = uuid.toString();
                Logger.get().debug(WorkProgressUpdater.a, String.format("Updating progress for %s (%s)", uuid, data), new Throwable[0]);
                WorkProgressUpdater.this.b.beginTransaction();
                try {
                    WorkSpec workSpec = WorkProgressUpdater.this.b.workSpecDao().getWorkSpec(uuid2);
                    if (workSpec != null) {
                        if (workSpec.state == WorkInfo.State.RUNNING) {
                            WorkProgressUpdater.this.b.workProgressDao().insert(new WorkProgress(uuid2, data));
                        } else {
                            Logger.get().warning(WorkProgressUpdater.a, String.format("Ignoring setProgressAsync(...). WorkSpec (%s) is not in a RUNNING state.", uuid2), new Throwable[0]);
                        }
                        create.set(null);
                        WorkProgressUpdater.this.b.setTransactionSuccessful();
                        return;
                    }
                    throw new IllegalStateException("Calls to setProgressAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
                } finally {
                    WorkProgressUpdater.this.b.endTransaction();
                }
            }
        });
        return create;
    }
}
