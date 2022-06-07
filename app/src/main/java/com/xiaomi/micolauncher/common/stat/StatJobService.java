package com.xiaomi.micolauncher.common.stat;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class StatJobService extends JobService {
    private static final String LOG_TAG = "[StatJobService]: ";
    private static final int POST_FAIL_CNT_MAX = 2;
    private StatUtils.StatPointsPostListener mListener = new StatUtils.StatPointsPostListener() { // from class: com.xiaomi.micolauncher.common.stat.StatJobService.1
        @Override // com.xiaomi.micolauncher.common.stat.StatUtils.StatPointsPostListener
        public void onSuccess() {
            Logger logger = L.base;
            logger.d("[StatJobService]: mListener.onSuccess.mPostFailCnt=" + StatJobService.mPostFailCnt);
            int unused = StatJobService.mPostFailCnt = 0;
        }

        @Override // com.xiaomi.micolauncher.common.stat.StatUtils.StatPointsPostListener
        public void onFail(String str) {
            StatJobService.access$008();
            L.base.d("%s mListener.onFail.error=%s", StatJobService.LOG_TAG, str);
            Logger logger = L.base;
            logger.d("[StatJobService]: mListener.onFail.mPostFailCnt=" + StatJobService.mPostFailCnt);
        }
    };
    private static final long JOB_INTERVAL = TimeUnit.HOURS.toMillis(1);
    private static int mPostFailCnt = 0;

    static /* synthetic */ int access$008() {
        int i = mPostFailCnt;
        mPostFailCnt = i + 1;
        return i;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        L.base.d("[StatJobService]: onCreate");
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        L.base.d("[StatJobService]: onStartJob");
        if (mPostFailCnt >= 2) {
            MicoStatUtils.postEventsRightNow(getApplication(), this.mListener);
        } else {
            MicoStatUtils.postEvents(getApplication(), this.mListener);
        }
        checkDoTaskPerDay();
        jobFinished(jobParameters, false);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        L.base.d("[StatJobService]: onStopJob");
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        L.base.i("[StatJobService]: onDestroy");
        super.onDestroy();
    }

    public void checkDoTaskPerDay() {
        int i = Calendar.getInstance().get(11);
        if (i < 2) {
            checkUploadMultiChsAsr();
            StatPoints.recordPointsOnNight();
        } else if (i > 12 && i < 15) {
            StatPoints.recordPointsOnNoon();
        }
    }

    private void checkUploadMultiChsAsr() {
        SpeechManager peekInstance = SpeechManager.peekInstance();
        if (peekInstance != null) {
            peekInstance.startUploadMultiChsAsr();
        }
    }

    /* loaded from: classes3.dex */
    public static class StatJobStarter {
        public static void start(Context context) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
            JobInfo.Builder builder = new JobInfo.Builder(-100, new ComponentName(context, StatJobService.class));
            builder.setBackoffCriteria(StatJobService.JOB_INTERVAL, 0);
            builder.setPersisted(false);
            builder.setRequiredNetworkType(1);
            builder.setPeriodic(StatJobService.JOB_INTERVAL);
            builder.setRequiresCharging(false);
            builder.setRequiresDeviceIdle(false);
            L.base.d("%s startJobScheduler", StatJobService.LOG_TAG);
            if (jobScheduler != null) {
                try {
                    jobScheduler.schedule(builder.build());
                } catch (IllegalStateException e) {
                    L.base.w(e);
                }
            }
        }
    }
}
