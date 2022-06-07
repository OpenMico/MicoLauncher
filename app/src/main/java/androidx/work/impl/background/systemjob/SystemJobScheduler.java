package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.IdGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@RequiresApi(23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemJobScheduler implements Scheduler {
    private static final String a = Logger.tagWithPrefix("SystemJobScheduler");
    private final Context b;
    private final JobScheduler c;
    private final WorkManagerImpl d;
    private final a e;

    @Override // androidx.work.impl.Scheduler
    public boolean hasLimitedSchedulingSlots() {
        return true;
    }

    public SystemJobScheduler(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl) {
        this(context, workManagerImpl, (JobScheduler) context.getSystemService("jobscheduler"), new a(context));
    }

    @VisibleForTesting
    public SystemJobScheduler(Context context, WorkManagerImpl workManagerImpl, JobScheduler jobScheduler, a aVar) {
        this.b = context;
        this.d = workManagerImpl;
        this.c = jobScheduler;
        this.e = aVar;
    }

    @Override // androidx.work.impl.Scheduler
    public void schedule(@NonNull WorkSpec... workSpecArr) {
        List<Integer> a2;
        int i;
        WorkDatabase workDatabase = this.d.getWorkDatabase();
        IdGenerator idGenerator = new IdGenerator(workDatabase);
        for (WorkSpec workSpec : workSpecArr) {
            workDatabase.beginTransaction();
            try {
                WorkSpec workSpec2 = workDatabase.workSpecDao().getWorkSpec(workSpec.id);
                if (workSpec2 == null) {
                    Logger.get().warning(a, "Skipping scheduling " + workSpec.id + " because it's no longer in the DB", new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                } else if (workSpec2.state != WorkInfo.State.ENQUEUED) {
                    Logger.get().warning(a, "Skipping scheduling " + workSpec.id + " because it is no longer enqueued", new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                } else {
                    SystemIdInfo systemIdInfo = workDatabase.systemIdInfoDao().getSystemIdInfo(workSpec.id);
                    int nextJobSchedulerIdWithRange = systemIdInfo != null ? systemIdInfo.systemId : idGenerator.nextJobSchedulerIdWithRange(this.d.getConfiguration().getMinJobSchedulerId(), this.d.getConfiguration().getMaxJobSchedulerId());
                    if (systemIdInfo == null) {
                        this.d.getWorkDatabase().systemIdInfoDao().insertSystemIdInfo(new SystemIdInfo(workSpec.id, nextJobSchedulerIdWithRange));
                    }
                    scheduleInternal(workSpec, nextJobSchedulerIdWithRange);
                    if (Build.VERSION.SDK_INT == 23 && (a2 = a(this.b, this.c, workSpec.id)) != null) {
                        int indexOf = a2.indexOf(Integer.valueOf(nextJobSchedulerIdWithRange));
                        if (indexOf >= 0) {
                            a2.remove(indexOf);
                        }
                        if (!a2.isEmpty()) {
                            i = a2.get(0).intValue();
                        } else {
                            i = idGenerator.nextJobSchedulerIdWithRange(this.d.getConfiguration().getMinJobSchedulerId(), this.d.getConfiguration().getMaxJobSchedulerId());
                        }
                        scheduleInternal(workSpec, i);
                    }
                    workDatabase.setTransactionSuccessful();
                }
            } finally {
                workDatabase.endTransaction();
            }
        }
    }

    @VisibleForTesting
    public void scheduleInternal(WorkSpec workSpec, int i) {
        JobInfo a2 = this.e.a(workSpec, i);
        Logger.get().debug(a, String.format("Scheduling work ID %s Job ID %s", workSpec.id, Integer.valueOf(i)), new Throwable[0]);
        try {
            this.c.schedule(a2);
        } catch (IllegalStateException e) {
            List<JobInfo> a3 = a(this.b, this.c);
            String format = String.format(Locale.getDefault(), "JobScheduler 100 job limit exceeded.  We count %d WorkManager jobs in JobScheduler; we have %d tracked jobs in our DB; our Configuration limit is %d.", Integer.valueOf(a3 != null ? a3.size() : 0), Integer.valueOf(this.d.getWorkDatabase().workSpecDao().getScheduledWork().size()), Integer.valueOf(this.d.getConfiguration().getMaxSchedulerLimit()));
            Logger.get().error(a, format, new Throwable[0]);
            throw new IllegalStateException(format, e);
        } catch (Throwable th) {
            Logger.get().error(a, String.format("Unable to schedule %s", workSpec), th);
        }
    }

    @Override // androidx.work.impl.Scheduler
    public void cancel(@NonNull String str) {
        List<Integer> a2 = a(this.b, this.c, str);
        if (!(a2 == null || a2.isEmpty())) {
            for (Integer num : a2) {
                a(this.c, num.intValue());
            }
            this.d.getWorkDatabase().systemIdInfoDao().removeSystemIdInfo(str);
        }
    }

    private static void a(@NonNull JobScheduler jobScheduler, int i) {
        try {
            jobScheduler.cancel(i);
        } catch (Throwable th) {
            Logger.get().error(a, String.format(Locale.getDefault(), "Exception while trying to cancel job (%d)", Integer.valueOf(i)), th);
        }
    }

    public static void cancelAll(@NonNull Context context) {
        List<JobInfo> a2;
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (!(jobScheduler == null || (a2 = a(context, jobScheduler)) == null || a2.isEmpty())) {
            for (JobInfo jobInfo : a2) {
                a(jobScheduler, jobInfo.getId());
            }
        }
    }

    public static boolean reconcileJobs(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        List<JobInfo> a2 = a(context, jobScheduler);
        List<String> workSpecIds = workManagerImpl.getWorkDatabase().systemIdInfoDao().getWorkSpecIds();
        boolean z = false;
        HashSet hashSet = new HashSet(a2 != null ? a2.size() : 0);
        if (a2 != null && !a2.isEmpty()) {
            for (JobInfo jobInfo : a2) {
                String a3 = a(jobInfo);
                if (!TextUtils.isEmpty(a3)) {
                    hashSet.add(a3);
                } else {
                    a(jobScheduler, jobInfo.getId());
                }
            }
        }
        Iterator<String> it = workSpecIds.iterator();
        while (true) {
            if (it.hasNext()) {
                if (!hashSet.contains(it.next())) {
                    Logger.get().debug(a, "Reconciling jobs", new Throwable[0]);
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            WorkDatabase workDatabase = workManagerImpl.getWorkDatabase();
            workDatabase.beginTransaction();
            try {
                WorkSpecDao workSpecDao = workDatabase.workSpecDao();
                for (String str : workSpecIds) {
                    workSpecDao.markWorkSpecScheduled(str, -1L);
                }
                workDatabase.setTransactionSuccessful();
            } finally {
                workDatabase.endTransaction();
            }
        }
        return z;
    }

    @Nullable
    private static List<JobInfo> a(@NonNull Context context, @NonNull JobScheduler jobScheduler) {
        List<JobInfo> list;
        try {
            list = jobScheduler.getAllPendingJobs();
        } catch (Throwable th) {
            Logger.get().error(a, "getAllPendingJobs() is not reliable on this device.", th);
            list = null;
        }
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        ComponentName componentName = new ComponentName(context, SystemJobService.class);
        for (JobInfo jobInfo : list) {
            if (componentName.equals(jobInfo.getService())) {
                arrayList.add(jobInfo);
            }
        }
        return arrayList;
    }

    @Nullable
    private static List<Integer> a(@NonNull Context context, @NonNull JobScheduler jobScheduler, @NonNull String str) {
        List<JobInfo> a2 = a(context, jobScheduler);
        if (a2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(2);
        for (JobInfo jobInfo : a2) {
            if (str.equals(a(jobInfo))) {
                arrayList.add(Integer.valueOf(jobInfo.getId()));
            }
        }
        return arrayList;
    }

    @Nullable
    private static String a(@NonNull JobInfo jobInfo) {
        PersistableBundle extras = jobInfo.getExtras();
        if (extras == null) {
            return null;
        }
        try {
            if (extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return extras.getString("EXTRA_WORK_SPEC_ID");
            }
            return null;
        } catch (NullPointerException unused) {
            return null;
        }
    }
}
