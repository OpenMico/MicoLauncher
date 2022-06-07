package androidx.work.impl.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.InitializationExceptionHandler;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabasePathHelper;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ForceStopRunnable implements Runnable {
    private static final String a = Logger.tagWithPrefix("ForceStopRunnable");
    private static final long b = TimeUnit.DAYS.toMillis(3650);
    private final Context c;
    private final WorkManagerImpl d;
    private int e = 0;

    public ForceStopRunnable(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl) {
        this.c = context.getApplicationContext();
        this.d = workManagerImpl;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (multiProcessChecks()) {
                while (true) {
                    WorkDatabasePathHelper.migrateDatabase(this.c);
                    Logger.get().debug(a, "Performing cleanup operations.", new Throwable[0]);
                    try {
                        forceStopRunnable();
                        break;
                    } catch (SQLiteAccessPermException | SQLiteCantOpenDatabaseException | SQLiteConstraintException | SQLiteDatabaseCorruptException | SQLiteDatabaseLockedException | SQLiteTableLockedException e) {
                        this.e++;
                        if (this.e >= 3) {
                            Logger.get().error(a, "The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", e);
                            IllegalStateException illegalStateException = new IllegalStateException("The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", e);
                            InitializationExceptionHandler exceptionHandler = this.d.getConfiguration().getExceptionHandler();
                            if (exceptionHandler != null) {
                                Logger.get().debug(a, "Routing exception to the specified exception handler", illegalStateException);
                                exceptionHandler.handleException(illegalStateException);
                            } else {
                                throw illegalStateException;
                            }
                        } else {
                            Logger.get().debug(a, String.format("Retrying after %s", Long.valueOf(this.e * 300)), e);
                            sleep(this.e * 300);
                        }
                    }
                    Logger.get().debug(a, String.format("Retrying after %s", Long.valueOf(this.e * 300)), e);
                    sleep(this.e * 300);
                }
            }
        } finally {
            this.d.onForceStopRunnableCompleted();
        }
    }

    @VisibleForTesting
    public boolean isForceStopped() {
        try {
            if (a(this.c, 536870912) != null) {
                return false;
            }
            b(this.c);
            return true;
        } catch (SecurityException e) {
            Logger.get().warning(a, "Ignoring security exception", e);
            return true;
        }
    }

    @VisibleForTesting
    public void forceStopRunnable() {
        boolean cleanUp = cleanUp();
        if (a()) {
            Logger.get().debug(a, "Rescheduling Workers.", new Throwable[0]);
            this.d.rescheduleEligibleWork();
            this.d.getPreferenceUtils().setNeedsReschedule(false);
        } else if (isForceStopped()) {
            Logger.get().debug(a, "Application was force-stopped, rescheduling.", new Throwable[0]);
            this.d.rescheduleEligibleWork();
        } else if (cleanUp) {
            Logger.get().debug(a, "Found unfinished work, scheduling it.", new Throwable[0]);
            Schedulers.schedule(this.d.getConfiguration(), this.d.getWorkDatabase(), this.d.getSchedulers());
        }
    }

    @VisibleForTesting
    public boolean cleanUp() {
        boolean reconcileJobs = Build.VERSION.SDK_INT >= 23 ? SystemJobScheduler.reconcileJobs(this.c, this.d) : false;
        WorkDatabase workDatabase = this.d.getWorkDatabase();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        WorkProgressDao workProgressDao = workDatabase.workProgressDao();
        workDatabase.beginTransaction();
        try {
            List<WorkSpec> runningWork = workSpecDao.getRunningWork();
            boolean z = runningWork != null && !runningWork.isEmpty();
            if (z) {
                for (WorkSpec workSpec : runningWork) {
                    workSpecDao.setState(WorkInfo.State.ENQUEUED, workSpec.id);
                    workSpecDao.markWorkSpecScheduled(workSpec.id, -1L);
                }
            }
            workProgressDao.deleteAll();
            workDatabase.setTransactionSuccessful();
            return z || reconcileJobs;
        } finally {
            workDatabase.endTransaction();
        }
    }

    @VisibleForTesting
    boolean a() {
        return this.d.getPreferenceUtils().getNeedsReschedule();
    }

    @VisibleForTesting
    public boolean multiProcessChecks() {
        boolean isDefaultProcess = ProcessUtils.isDefaultProcess(this.c, this.d.getConfiguration());
        Logger.get().debug(a, String.format("Is default app process = %s", Boolean.valueOf(isDefaultProcess)), new Throwable[0]);
        return isDefaultProcess;
    }

    @VisibleForTesting
    public void sleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
        }
    }

    private static PendingIntent a(Context context, int i) {
        return PendingIntent.getBroadcast(context, -1, a(context), i);
    }

    @VisibleForTesting
    static Intent a(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, BroadcastReceiver.class));
        intent.setAction("ACTION_FORCE_STOP_RESCHEDULE");
        return intent;
    }

    static void b(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent a2 = a(context, 134217728);
        long currentTimeMillis = System.currentTimeMillis() + b;
        if (alarmManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(0, currentTimeMillis, a2);
        } else {
            alarmManager.set(0, currentTimeMillis, a2);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static class BroadcastReceiver extends android.content.BroadcastReceiver {
        private static final String a = Logger.tagWithPrefix("ForceStopRunnable$Rcvr");

        @Override // android.content.BroadcastReceiver
        public void onReceive(@NonNull Context context, @Nullable Intent intent) {
            if (intent != null && "ACTION_FORCE_STOP_RESCHEDULE".equals(intent.getAction())) {
                Logger.get().verbose(a, "Rescheduling alarm that keeps track of force-stops.", new Throwable[0]);
                ForceStopRunnable.b(context);
            }
        }
    }
}
