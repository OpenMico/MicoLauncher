package androidx.work.impl.background.systemjob;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkManagerImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemJobService extends JobService implements ExecutionListener {
    private static final String a = Logger.tagWithPrefix("SystemJobService");
    private WorkManagerImpl b;
    private final Map<String, JobParameters> c = new HashMap();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            this.b = WorkManagerImpl.getInstance(getApplicationContext());
            this.b.getProcessor().addExecutionListener(this);
        } catch (IllegalStateException unused) {
            if (Application.class.equals(getApplication().getClass())) {
                Logger.get().warning(a, "Could not find WorkManager instance; this may be because an auto-backup is in progress. Ignoring JobScheduler commands for now. Please make sure that you are initializing WorkManager if you have manually disabled WorkManagerInitializer.", new Throwable[0]);
                return;
            }
            throw new IllegalStateException("WorkManager needs to be initialized via a ContentProvider#onCreate() or an Application#onCreate().");
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        WorkManagerImpl workManagerImpl = this.b;
        if (workManagerImpl != null) {
            workManagerImpl.getProcessor().removeExecutionListener(this);
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(@NonNull JobParameters jobParameters) {
        if (this.b == null) {
            Logger.get().debug(a, "WorkManager is not initialized; requesting retry.", new Throwable[0]);
            jobFinished(jobParameters, true);
            return false;
        }
        String a2 = a(jobParameters);
        if (TextUtils.isEmpty(a2)) {
            Logger.get().error(a, "WorkSpec id not found!", new Throwable[0]);
            return false;
        }
        synchronized (this.c) {
            if (this.c.containsKey(a2)) {
                Logger.get().debug(a, String.format("Job is already being executed by SystemJobService: %s", a2), new Throwable[0]);
                return false;
            }
            Logger.get().debug(a, String.format("onStartJob for %s", a2), new Throwable[0]);
            this.c.put(a2, jobParameters);
            WorkerParameters.RuntimeExtras runtimeExtras = null;
            if (Build.VERSION.SDK_INT >= 24) {
                runtimeExtras = new WorkerParameters.RuntimeExtras();
                if (jobParameters.getTriggeredContentUris() != null) {
                    runtimeExtras.triggeredContentUris = Arrays.asList(jobParameters.getTriggeredContentUris());
                }
                if (jobParameters.getTriggeredContentAuthorities() != null) {
                    runtimeExtras.triggeredContentAuthorities = Arrays.asList(jobParameters.getTriggeredContentAuthorities());
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    runtimeExtras.network = jobParameters.getNetwork();
                }
            }
            this.b.startWork(a2, runtimeExtras);
            return true;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(@NonNull JobParameters jobParameters) {
        if (this.b == null) {
            Logger.get().debug(a, "WorkManager is not initialized; requesting retry.", new Throwable[0]);
            return true;
        }
        String a2 = a(jobParameters);
        if (TextUtils.isEmpty(a2)) {
            Logger.get().error(a, "WorkSpec id not found!", new Throwable[0]);
            return false;
        }
        Logger.get().debug(a, String.format("onStopJob for %s", a2), new Throwable[0]);
        synchronized (this.c) {
            this.c.remove(a2);
        }
        this.b.stopWork(a2);
        return !this.b.getProcessor().isCancelled(a2);
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        JobParameters remove;
        Logger.get().debug(a, String.format("%s executed on JobScheduler", str), new Throwable[0]);
        synchronized (this.c) {
            remove = this.c.remove(str);
        }
        if (remove != null) {
            jobFinished(remove, z);
        }
    }

    @Nullable
    private static String a(@NonNull JobParameters jobParameters) {
        try {
            PersistableBundle extras = jobParameters.getExtras();
            if (extras == null || !extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return null;
            }
            return extras.getString("EXTRA_WORK_SPEC_ID");
        } catch (NullPointerException unused) {
            return null;
        }
    }
}
