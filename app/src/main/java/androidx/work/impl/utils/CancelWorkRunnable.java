package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.Scheduler;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkSpecDao;
import java.util.LinkedList;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class CancelWorkRunnable implements Runnable {
    private final OperationImpl a = new OperationImpl();

    abstract void a();

    public Operation getOperation() {
        return this.a;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a();
            this.a.setState(Operation.SUCCESS);
        } catch (Throwable th) {
            this.a.setState(new Operation.State.FAILURE(th));
        }
    }

    void a(WorkManagerImpl workManagerImpl, String str) {
        a(workManagerImpl.getWorkDatabase(), str);
        workManagerImpl.getProcessor().stopAndCancelWork(str);
        for (Scheduler scheduler : workManagerImpl.getSchedulers()) {
            scheduler.cancel(str);
        }
    }

    void a(WorkManagerImpl workManagerImpl) {
        Schedulers.schedule(workManagerImpl.getConfiguration(), workManagerImpl.getWorkDatabase(), workManagerImpl.getSchedulers());
    }

    private void a(WorkDatabase workDatabase, String str) {
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        DependencyDao dependencyDao = workDatabase.dependencyDao();
        LinkedList linkedList = new LinkedList();
        linkedList.add(str);
        while (!linkedList.isEmpty()) {
            String str2 = (String) linkedList.remove();
            WorkInfo.State state = workSpecDao.getState(str2);
            if (!(state == WorkInfo.State.SUCCEEDED || state == WorkInfo.State.FAILED)) {
                workSpecDao.setState(WorkInfo.State.CANCELLED, str2);
            }
            linkedList.addAll(dependencyDao.getDependentWorkIds(str2));
        }
    }

    public static CancelWorkRunnable forId(@NonNull final UUID uuid, @NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() { // from class: androidx.work.impl.utils.CancelWorkRunnable.1
            /* JADX WARN: Finally extract failed */
            @Override // androidx.work.impl.utils.CancelWorkRunnable
            @WorkerThread
            void a() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    a(WorkManagerImpl.this, uuid.toString());
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    a(WorkManagerImpl.this);
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forTag(@NonNull final String str, @NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() { // from class: androidx.work.impl.utils.CancelWorkRunnable.2
            /* JADX WARN: Finally extract failed */
            @Override // androidx.work.impl.utils.CancelWorkRunnable
            @WorkerThread
            void a() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String str2 : workDatabase.workSpecDao().getUnfinishedWorkWithTag(str)) {
                        a(WorkManagerImpl.this, str2);
                    }
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    a(WorkManagerImpl.this);
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forName(@NonNull final String str, @NonNull final WorkManagerImpl workManagerImpl, final boolean z) {
        return new CancelWorkRunnable() { // from class: androidx.work.impl.utils.CancelWorkRunnable.3
            /* JADX WARN: Finally extract failed */
            @Override // androidx.work.impl.utils.CancelWorkRunnable
            @WorkerThread
            void a() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String str2 : workDatabase.workSpecDao().getUnfinishedWorkWithName(str)) {
                        a(WorkManagerImpl.this, str2);
                    }
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    if (z) {
                        a(WorkManagerImpl.this);
                    }
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forAll(@NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() { // from class: androidx.work.impl.utils.CancelWorkRunnable.4
            @Override // androidx.work.impl.utils.CancelWorkRunnable
            @WorkerThread
            void a() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String str : workDatabase.workSpecDao().getAllUnfinishedWork()) {
                        a(WorkManagerImpl.this, str);
                    }
                    new PreferenceUtils(WorkManagerImpl.this.getWorkDatabase()).setLastCancelAllTimeMillis(System.currentTimeMillis());
                    workDatabase.setTransactionSuccessful();
                } finally {
                    workDatabase.endTransaction();
                }
            }
        };
    }
}
