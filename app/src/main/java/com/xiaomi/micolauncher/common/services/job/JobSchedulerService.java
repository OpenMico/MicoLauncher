package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.PersistableBundle;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;

/* loaded from: classes3.dex */
public class JobSchedulerService extends JobService {
    private Handler a;

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        L.base.d("JobSchedulerService onCreate");
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        if (this.a == null) {
            this.a = ThreadUtil.getWorkHandler();
        }
        if (-101 == jobParameters.getJobId()) {
            L.weather.d("JobSchedulerService onWeatherJobStart");
            AlarmRealmObjectBean alarm = AlarmModel.getInstance().getAlarm(jobParameters.getExtras().getString(JobTaskUtil.JOB_BUNDLE_KEY_ALARM_IDS));
            if (alarm != null && alarm.isInDay(System.currentTimeMillis())) {
                long randomMillisInNextMin = RandomTimeUtils.getRandomMillisInNextMin(20);
                AlarmWeatherRefreshJob alarmWeatherRefreshJob = new AlarmWeatherRefreshJob();
                alarmWeatherRefreshJob.setJobService(this, jobParameters);
                this.a.removeCallbacks(alarmWeatherRefreshJob);
                this.a.postDelayed(alarmWeatherRefreshJob, randomMillisInNextMin);
                return true;
            }
        } else {
            PersistableBundle extras = jobParameters.getExtras();
            String string = extras.getString(JobTaskUtil.JOB_BUNDLE_KEY_TYPE);
            String string2 = extras.getString(JobTaskUtil.JOB_BUNDLE_KEY_ALARM_IDS);
            String string3 = extras.getString(JobTaskUtil.JOB_BUNDLE_KEY_ALARM_RINGTONE);
            String string4 = extras.getString(JobTaskUtil.JOB_BUNDLE_KEY_ALARM_RINGTONE_TYPE);
            if (JobTaskUtil.JOB_TYPE_ALARM_MUSIC.equalsIgnoreCase(string)) {
                L.alarm.d("JobSchedulerService onAlarmMusicCacheJobStart");
                AlarmRealmObjectBean alarm2 = AlarmModel.getInstance().getAlarm(string2);
                if (alarm2 != null) {
                    String circle = alarm2.getCircle();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (!Circle.ONCE.equalsIgnoreCase(circle)) {
                        long j = AlarmModel.DAY_MILLISECONDS + currentTimeMillis;
                        Logger logger = L.alarm;
                        logger.d("JobSchedulerService scheduleNextTimeJob nextDelay: " + j);
                        JobTaskUtil.scheduleNextTimeJob(alarm2, j);
                    }
                    if (alarm2.isInDay(currentTimeMillis)) {
                        long randomMillisInNextMin2 = RandomTimeUtils.getRandomMillisInNextMin(20);
                        L.alarm.d("JobSchedulerService AlarmMusicCacheJob jobId=%s, will delay %s run...", Integer.valueOf(jobParameters.getJobId()), Long.valueOf(randomMillisInNextMin2));
                        AlarmMusicCacheJob alarmMusicCacheJob = new AlarmMusicCacheJob(string2, string3, string4);
                        alarmMusicCacheJob.setJobService(this, jobParameters);
                        this.a.removeCallbacks(alarmMusicCacheJob);
                        this.a.postDelayed(alarmMusicCacheJob, randomMillisInNextMin2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
