package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeMode;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AlarmRunStateCheckJobService extends JobService {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        L.base.d("[AlarmRunStateCheckJobService]: onCreate");
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        L.base.d("[AlarmRunStateCheckJobService]: onStartJob");
        if (!AlarmModel.getInstance().isLooping()) {
            AlarmModel.getInstance().syncTime(new AlarmModel.SyncTimeListener() { // from class: com.xiaomi.micolauncher.common.services.job.AlarmRunStateCheckJobService.1
                @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmModel.SyncTimeListener
                public void onSyncTimeSuccess(long j) {
                    if (!AlarmModel.getInstance().isLooping()) {
                        AlarmModel.getInstance().resetAlarmAfterTimeSync();
                        HourlyChimeMode.getInstance().resetHourlyChimeAfterTimeSync();
                    }
                }

                @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmModel.SyncTimeListener
                public void onSyncTimeFail() {
                    L.base.d("[AlarmRunStateCheckJobService]: onSyncTimeFail");
                }
            });
        }
        jobFinished(jobParameters, false);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        L.base.d("[AlarmRunStateCheckJobService]: onStopJob");
        return false;
    }

    /* loaded from: classes3.dex */
    public static class JobStarter {
        public static void start(Context context) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
            JobInfo.Builder builder = new JobInfo.Builder(Constants.JOB_CHECK_ALARM_STATE_ID, new ComponentName(context, AlarmRunStateCheckJobService.class));
            builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(3L), 0);
            builder.setPersisted(false);
            builder.setRequiredNetworkType(1);
            builder.setRequiresCharging(false);
            builder.setRequiresDeviceIdle(false);
            builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(5L));
            L.base.d("%s startJobScheduler", "[AlarmRunStateCheckJobService]: ");
            if (jobScheduler != null) {
                try {
                    jobScheduler.schedule(builder.build());
                } catch (IllegalStateException e) {
                    L.alarm.w(e);
                }
            }
        }
    }
}
