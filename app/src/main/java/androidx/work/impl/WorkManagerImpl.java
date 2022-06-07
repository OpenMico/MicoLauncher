package androidx.work.impl;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.work.Configuration;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.R;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;
import androidx.work.WorkRequest;
import androidx.work.WorkerParameters;
import androidx.work.impl.background.greedy.GreedyScheduler;
import androidx.work.impl.background.systemjob.SystemJobScheduler;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.CancelWorkRunnable;
import androidx.work.impl.utils.ForceStopRunnable;
import androidx.work.impl.utils.LiveDataUtils;
import androidx.work.impl.utils.PreferenceUtils;
import androidx.work.impl.utils.PruneWorkRunnable;
import androidx.work.impl.utils.RawQueries;
import androidx.work.impl.utils.StartWorkRunnable;
import androidx.work.impl.utils.StatusRunnable;
import androidx.work.impl.utils.StopWorkRunnable;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor;
import androidx.work.multiprocess.RemoteWorkManager;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkManagerImpl extends WorkManager {
    public static final int MAX_PRE_JOB_SCHEDULER_API_LEVEL = 22;
    public static final int MIN_JOB_SCHEDULER_API_LEVEL = 23;
    public static final String REMOTE_WORK_MANAGER_CLIENT = "androidx.work.multiprocess.RemoteWorkManagerClient";
    private static final String a = Logger.tagWithPrefix("WorkManagerImpl");
    private static WorkManagerImpl l = null;
    private static WorkManagerImpl m = null;
    private static final Object n = new Object();
    private Context b;
    private Configuration c;
    private WorkDatabase d;
    private TaskExecutor e;
    private List<Scheduler> f;
    private Processor g;
    private PreferenceUtils h;
    private boolean i;
    private BroadcastReceiver.PendingResult j;
    private volatile RemoteWorkManager k;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void setDelegate(@Nullable WorkManagerImpl workManagerImpl) {
        synchronized (n) {
            l = workManagerImpl;
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Deprecated
    public static WorkManagerImpl getInstance() {
        synchronized (n) {
            if (l != null) {
                return l;
            }
            return m;
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static WorkManagerImpl getInstance(@NonNull Context context) {
        WorkManagerImpl instance;
        synchronized (n) {
            instance = getInstance();
            if (instance == null) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext instanceof Configuration.Provider) {
                    initialize(applicationContext, ((Configuration.Provider) applicationContext).getWorkManagerConfiguration());
                    instance = getInstance(applicationContext);
                } else {
                    throw new IllegalStateException("WorkManager is not initialized properly.  You have explicitly disabled WorkManagerInitializer in your manifest, have not manually called WorkManager#initialize at this point, and your Application does not implement Configuration.Provider.");
                }
            }
        }
        return instance;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void initialize(@NonNull Context context, @NonNull Configuration configuration) {
        synchronized (n) {
            if (!(l == null || m == null)) {
                throw new IllegalStateException("WorkManager is already initialized.  Did you try to initialize it manually without disabling WorkManagerInitializer? See WorkManager#initialize(Context, Configuration) or the class level Javadoc for more information.");
            }
            if (l == null) {
                Context applicationContext = context.getApplicationContext();
                if (m == null) {
                    m = new WorkManagerImpl(applicationContext, configuration, new WorkManagerTaskExecutor(configuration.getTaskExecutor()));
                }
                l = m;
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkManagerImpl(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor) {
        this(context, configuration, taskExecutor, context.getResources().getBoolean(R.bool.workmanager_test_configuration));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkManagerImpl(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, boolean z) {
        this(context, configuration, taskExecutor, WorkDatabase.create(context.getApplicationContext(), taskExecutor.getBackgroundExecutor(), z));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkManagerImpl(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkDatabase workDatabase) {
        Context applicationContext = context.getApplicationContext();
        Logger.setLogger(new Logger.LogcatLogger(configuration.getMinimumLoggingLevel()));
        List<Scheduler> createSchedulers = createSchedulers(applicationContext, configuration, taskExecutor);
        a(context, configuration, taskExecutor, workDatabase, createSchedulers, new Processor(context, configuration, taskExecutor, workDatabase, createSchedulers));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkManagerImpl(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkDatabase workDatabase, @NonNull List<Scheduler> list, @NonNull Processor processor) {
        a(context, configuration, taskExecutor, workDatabase, list, processor);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Context getApplicationContext() {
        return this.b;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkDatabase getWorkDatabase() {
        return this.d;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Configuration getConfiguration() {
        return this.c;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public List<Scheduler> getSchedulers() {
        return this.f;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Processor getProcessor() {
        return this.g;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public TaskExecutor getWorkTaskExecutor() {
        return this.e;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PreferenceUtils getPreferenceUtils() {
        return this.h;
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation enqueue(@NonNull List<? extends WorkRequest> list) {
        if (!list.isEmpty()) {
            return new WorkContinuationImpl(this, list).enqueue();
        }
        throw new IllegalArgumentException("enqueue needs at least one WorkRequest.");
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public WorkContinuation beginWith(@NonNull List<OneTimeWorkRequest> list) {
        if (!list.isEmpty()) {
            return new WorkContinuationImpl(this, list);
        }
        throw new IllegalArgumentException("beginWith needs at least one OneTimeWorkRequest.");
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public WorkContinuation beginUniqueWork(@NonNull String str, @NonNull ExistingWorkPolicy existingWorkPolicy, @NonNull List<OneTimeWorkRequest> list) {
        if (!list.isEmpty()) {
            return new WorkContinuationImpl(this, str, existingWorkPolicy, list);
        }
        throw new IllegalArgumentException("beginUniqueWork needs at least one OneTimeWorkRequest.");
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation enqueueUniqueWork(@NonNull String str, @NonNull ExistingWorkPolicy existingWorkPolicy, @NonNull List<OneTimeWorkRequest> list) {
        return new WorkContinuationImpl(this, str, existingWorkPolicy, list).enqueue();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation enqueueUniquePeriodicWork(@NonNull String str, @NonNull ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy, @NonNull PeriodicWorkRequest periodicWorkRequest) {
        return createWorkContinuationForUniquePeriodicWork(str, existingPeriodicWorkPolicy, periodicWorkRequest).enqueue();
    }

    @NonNull
    public WorkContinuationImpl createWorkContinuationForUniquePeriodicWork(@NonNull String str, @NonNull ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy, @NonNull PeriodicWorkRequest periodicWorkRequest) {
        ExistingWorkPolicy existingWorkPolicy;
        if (existingPeriodicWorkPolicy == ExistingPeriodicWorkPolicy.KEEP) {
            existingWorkPolicy = ExistingWorkPolicy.KEEP;
        } else {
            existingWorkPolicy = ExistingWorkPolicy.REPLACE;
        }
        return new WorkContinuationImpl(this, str, existingWorkPolicy, Collections.singletonList(periodicWorkRequest));
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation cancelWorkById(@NonNull UUID uuid) {
        CancelWorkRunnable forId = CancelWorkRunnable.forId(uuid, this);
        this.e.executeOnBackgroundThread(forId);
        return forId.getOperation();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation cancelAllWorkByTag(@NonNull String str) {
        CancelWorkRunnable forTag = CancelWorkRunnable.forTag(str, this);
        this.e.executeOnBackgroundThread(forTag);
        return forTag.getOperation();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation cancelUniqueWork(@NonNull String str) {
        CancelWorkRunnable forName = CancelWorkRunnable.forName(str, this, true);
        this.e.executeOnBackgroundThread(forName);
        return forName.getOperation();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation cancelAllWork() {
        CancelWorkRunnable forAll = CancelWorkRunnable.forAll(this);
        this.e.executeOnBackgroundThread(forAll);
        return forAll.getOperation();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public PendingIntent createCancelPendingIntent(@NonNull UUID uuid) {
        return PendingIntent.getService(this.b, 0, SystemForegroundDispatcher.createCancelWorkIntent(this.b, uuid.toString()), 134217728);
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public LiveData<Long> getLastCancelAllTimeMillisLiveData() {
        return this.h.getLastCancelAllTimeMillisLiveData();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public ListenableFuture<Long> getLastCancelAllTimeMillis() {
        final SettableFuture create = SettableFuture.create();
        final PreferenceUtils preferenceUtils = this.h;
        this.e.executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.WorkManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    create.set(Long.valueOf(preferenceUtils.getLastCancelAllTimeMillis()));
                } catch (Throwable th) {
                    create.setException(th);
                }
            }
        });
        return create;
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public Operation pruneWork() {
        PruneWorkRunnable pruneWorkRunnable = new PruneWorkRunnable(this);
        this.e.executeOnBackgroundThread(pruneWorkRunnable);
        return pruneWorkRunnable.getOperation();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public LiveData<WorkInfo> getWorkInfoByIdLiveData(@NonNull UUID uuid) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.d.workSpecDao().getWorkStatusPojoLiveDataForIds(Collections.singletonList(uuid.toString())), new Function<List<WorkSpec.WorkInfoPojo>, WorkInfo>() { // from class: androidx.work.impl.WorkManagerImpl.2
            /* renamed from: a */
            public WorkInfo apply(List<WorkSpec.WorkInfoPojo> list) {
                if (list == null || list.size() <= 0) {
                    return null;
                }
                return list.get(0).toWorkInfo();
            }
        }, this.e);
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public ListenableFuture<WorkInfo> getWorkInfoById(@NonNull UUID uuid) {
        StatusRunnable<WorkInfo> forUUID = StatusRunnable.forUUID(this, uuid);
        this.e.getBackgroundExecutor().execute(forUUID);
        return forUUID.getFuture();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public LiveData<List<WorkInfo>> getWorkInfosByTagLiveData(@NonNull String str) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.d.workSpecDao().getWorkStatusPojoLiveDataForTag(str), WorkSpec.WORK_INFO_MAPPER, this.e);
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public ListenableFuture<List<WorkInfo>> getWorkInfosByTag(@NonNull String str) {
        StatusRunnable<List<WorkInfo>> forTag = StatusRunnable.forTag(this, str);
        this.e.getBackgroundExecutor().execute(forTag);
        return forTag.getFuture();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public LiveData<List<WorkInfo>> getWorkInfosForUniqueWorkLiveData(@NonNull String str) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.d.workSpecDao().getWorkStatusPojoLiveDataForName(str), WorkSpec.WORK_INFO_MAPPER, this.e);
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public ListenableFuture<List<WorkInfo>> getWorkInfosForUniqueWork(@NonNull String str) {
        StatusRunnable<List<WorkInfo>> forUniqueWork = StatusRunnable.forUniqueWork(this, str);
        this.e.getBackgroundExecutor().execute(forUniqueWork);
        return forUniqueWork.getFuture();
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public LiveData<List<WorkInfo>> getWorkInfosLiveData(@NonNull WorkQuery workQuery) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.d.rawWorkInfoDao().getWorkInfoPojosLiveData(RawQueries.workQueryToRawQuery(workQuery)), WorkSpec.WORK_INFO_MAPPER, this.e);
    }

    @Override // androidx.work.WorkManager
    @NonNull
    public ListenableFuture<List<WorkInfo>> getWorkInfos(@NonNull WorkQuery workQuery) {
        StatusRunnable<List<WorkInfo>> forWorkQuerySpec = StatusRunnable.forWorkQuerySpec(this, workQuery);
        this.e.getBackgroundExecutor().execute(forWorkQuerySpec);
        return forWorkQuerySpec.getFuture();
    }

    public LiveData<List<WorkInfo>> a(@NonNull List<String> list) {
        return LiveDataUtils.dedupedMappedLiveDataFor(this.d.workSpecDao().getWorkStatusPojoLiveDataForIds(list), WorkSpec.WORK_INFO_MAPPER, this.e);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteWorkManager getRemoteWorkManager() {
        if (this.k == null) {
            synchronized (n) {
                if (this.k == null) {
                    a();
                    if (this.k == null && !TextUtils.isEmpty(this.c.getDefaultProcessName())) {
                        throw new IllegalStateException("Invalid multiprocess configuration. Define an `implementation` dependency on :work:work-multiprocess library");
                    }
                }
            }
        }
        return this.k;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void startWork(@NonNull String str) {
        startWork(str, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void startWork(@NonNull String str, @Nullable WorkerParameters.RuntimeExtras runtimeExtras) {
        this.e.executeOnBackgroundThread(new StartWorkRunnable(this, str, runtimeExtras));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void stopWork(@NonNull String str) {
        this.e.executeOnBackgroundThread(new StopWorkRunnable(this, str, false));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void stopForegroundWork(@NonNull String str) {
        this.e.executeOnBackgroundThread(new StopWorkRunnable(this, str, true));
    }

    public void rescheduleEligibleWork() {
        if (Build.VERSION.SDK_INT >= 23) {
            SystemJobScheduler.cancelAll(getApplicationContext());
        }
        getWorkDatabase().workSpecDao().resetScheduledState();
        Schedulers.schedule(getConfiguration(), getWorkDatabase(), getSchedulers());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onForceStopRunnableCompleted() {
        synchronized (n) {
            this.i = true;
            if (this.j != null) {
                this.j.finish();
                this.j = null;
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setReschedulePendingResult(@NonNull BroadcastReceiver.PendingResult pendingResult) {
        synchronized (n) {
            this.j = pendingResult;
            if (this.i) {
                this.j.finish();
                this.j = null;
            }
        }
    }

    private void a(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkDatabase workDatabase, @NonNull List<Scheduler> list, @NonNull Processor processor) {
        Context applicationContext = context.getApplicationContext();
        this.b = applicationContext;
        this.c = configuration;
        this.e = taskExecutor;
        this.d = workDatabase;
        this.f = list;
        this.g = processor;
        this.h = new PreferenceUtils(workDatabase);
        this.i = false;
        if (Build.VERSION.SDK_INT < 24 || !applicationContext.isDeviceProtectedStorage()) {
            this.e.executeOnBackgroundThread(new ForceStopRunnable(applicationContext, this));
            return;
        }
        throw new IllegalStateException("Cannot initialize WorkManager in direct boot mode");
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public List<Scheduler> createSchedulers(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor) {
        return Arrays.asList(Schedulers.a(context, this), new GreedyScheduler(context, configuration, taskExecutor, this));
    }

    private void a() {
        try {
            this.k = (RemoteWorkManager) Class.forName(REMOTE_WORK_MANAGER_CLIENT).getConstructor(Context.class, WorkManagerImpl.class).newInstance(this.b, this);
        } catch (Throwable th) {
            Logger.get().debug(a, "Unable to initialize multi-process support", th);
        }
    }
}
