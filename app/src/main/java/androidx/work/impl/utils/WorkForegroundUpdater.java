package androidx.work.impl.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.WorkInfo;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkForegroundUpdater implements ForegroundUpdater {
    final ForegroundProcessor a;
    final WorkSpecDao b;
    private final TaskExecutor c;

    public WorkForegroundUpdater(@NonNull WorkDatabase workDatabase, @NonNull ForegroundProcessor foregroundProcessor, @NonNull TaskExecutor taskExecutor) {
        this.a = foregroundProcessor;
        this.c = taskExecutor;
        this.b = workDatabase.workSpecDao();
    }

    @Override // androidx.work.ForegroundUpdater
    @NonNull
    public ListenableFuture<Void> setForegroundAsync(@NonNull final Context context, @NonNull final UUID uuid, @NonNull final ForegroundInfo foregroundInfo) {
        final SettableFuture create = SettableFuture.create();
        this.c.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.utils.WorkForegroundUpdater.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (!create.isCancelled()) {
                        String uuid2 = uuid.toString();
                        WorkInfo.State state = WorkForegroundUpdater.this.b.getState(uuid2);
                        if (state == null || state.isFinished()) {
                            throw new IllegalStateException("Calls to setForegroundAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
                        }
                        WorkForegroundUpdater.this.a.startForeground(uuid2, foregroundInfo);
                        context.startService(SystemForegroundDispatcher.createNotifyIntent(context, uuid2, foregroundInfo));
                    }
                    create.set(null);
                } catch (Throwable th) {
                    create.setException(th);
                }
            }
        });
        return create;
    }
}
