package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.text.TextUtils;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class JobTaskUtil {
    public static final String JOB_BUNDLE_KEY_ALARM_IDS = "job_bundle_key_alarm_ids";
    public static final String JOB_BUNDLE_KEY_ALARM_RINGTONE = "job_bundle_key_alarm_ringtone";
    public static final String JOB_BUNDLE_KEY_ALARM_RINGTONE_TYPE = "job_bundle_key_alarm_ringtone_type";
    public static final String JOB_BUNDLE_KEY_TIME = "JOB_BUNDLE_KEY_TIME";
    public static final String JOB_BUNDLE_KEY_TYPE = "JOB_BUNDLE_KEY_TYPE";
    public static final int JOB_START_AHEAD_OF_MINUTE = 20;
    public static final String JOB_TYPE_ALARM_MUSIC = "alarm_music";
    public static final String JOB_TYPE_ALARM_WEATHER = "alarm_weather";
    private static final long a = TimeUnit.MINUTES.toMillis(21);
    private static Context b;

    public static void init(Context context) {
        b = context;
    }

    public static JobScheduler getJobScheduler() {
        return (JobScheduler) b.getSystemService("jobscheduler");
    }

    private static void a(AlarmRealmObjectBean alarmRealmObjectBean, long j) {
        long alarmTimestampWithTimeZone = alarmRealmObjectBean.getAlarmTimestampWithTimeZone();
        JobScheduler jobScheduler = getJobScheduler();
        JobInfo pendingJob = jobScheduler.getPendingJob(Constants.JOB_ID_REFRESH_WEATHER);
        if (pendingJob != null) {
            long j2 = pendingJob.getExtras().getLong(JOB_BUNDLE_KEY_TIME);
            L.alarm.i("JobTaskUtil#scheduleWeatherJob getPendingWeatherJob time=%s", Long.valueOf(j2));
            int i = (alarmTimestampWithTimeZone > j2 ? 1 : (alarmTimestampWithTimeZone == j2 ? 0 : -1));
            if (i < 0) {
                jobScheduler.cancel(Constants.JOB_ID_REFRESH_WEATHER);
            } else if (i >= 0) {
                L.alarm.w("there is a earlier weather job scheduled, so ignore this alarm=%s", alarmRealmObjectBean);
                return;
            }
        }
        JobInfo.Builder builder = new JobInfo.Builder(Constants.JOB_ID_REFRESH_WEATHER, new ComponentName(b.getPackageName(), JobSchedulerService.class.getName()));
        builder.setRequiredNetworkType(1);
        if (j != 0) {
            builder.setMinimumLatency(j);
            builder.setOverrideDeadline(j);
        } else {
            long aheadOfCacheJobTime = TimeUtils.getAheadOfCacheJobTime(alarmTimestampWithTimeZone, a);
            if (aheadOfCacheJobTime >= 0) {
                builder.setMinimumLatency(aheadOfCacheJobTime);
                builder.setOverrideDeadline(aheadOfCacheJobTime);
            } else {
                return;
            }
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong(JOB_BUNDLE_KEY_TIME, alarmTimestampWithTimeZone);
        persistableBundle.putString(JOB_BUNDLE_KEY_ALARM_IDS, alarmRealmObjectBean.getIdStr());
        builder.setExtras(persistableBundle);
        try {
            if (jobScheduler.schedule(builder.build()) == 0) {
                L.alarm.e("JobTaskUtil#scheduleWeatherJob time=%s schedule job failed", Long.valueOf(alarmTimestampWithTimeZone));
                jobScheduler.cancel(Constants.JOB_ID_REFRESH_WEATHER);
            }
        } catch (IllegalStateException e) {
            L.base.w(e);
        }
    }

    public static void scheduleJobWhenCreateAlarm(AlarmRealmObjectBean alarmRealmObjectBean) {
        long alarmTimestampWithTimeZone = alarmRealmObjectBean.getAlarmTimestampWithTimeZone();
        if (TimeUtils.isMorningPeak(alarmTimestampWithTimeZone)) {
            JobScheduler jobScheduler = getJobScheduler();
            a(alarmRealmObjectBean, 0L);
            long aheadOfCacheJobTime = TimeUtils.getAheadOfCacheJobTime(alarmTimestampWithTimeZone, a);
            if (aheadOfCacheJobTime < 0) {
                L.alarm.e("JobTaskUtil#scheduleJobWhenCreateAlarm ahead of alarm time is short");
                return;
            }
            JobInfo b2 = b(alarmRealmObjectBean, aheadOfCacheJobTime);
            try {
                L.alarm.i("JobTaskUtil#scheduleJobWhenCreateAlarm schedule jobId=%s result=%s, delay=%s ", Integer.valueOf(b2.getId()), Integer.valueOf(jobScheduler.schedule(b2)), Long.valueOf(aheadOfCacheJobTime));
            } catch (IllegalStateException e) {
                L.alarm.w(e);
            }
        }
    }

    public static void scheduleJobWhenUpdateAlarm(AlarmRealmObjectBean alarmRealmObjectBean) {
        a(alarmRealmObjectBean, 0L);
        JobScheduler jobScheduler = getJobScheduler();
        JobInfo pendingJob = jobScheduler.getPendingJob(alarmRealmObjectBean.getId());
        if (pendingJob != null) {
            L.alarm.w("已经有一个安排的闹钟job id=%s ", Integer.valueOf(pendingJob.getId()));
        }
        jobScheduler.cancel(alarmRealmObjectBean.getId());
        if (TextUtils.isEmpty(alarmRealmObjectBean.getRingtone())) {
            L.alarm.e("JobTaskUtil#scheduleJobWhenUpdateAlarm: this alarm is not a music alarm, so ignore it!!!");
            return;
        }
        long alarmTimestampWithTimeZone = alarmRealmObjectBean.getAlarmTimestampWithTimeZone();
        if (TimeUtils.isMorningPeak(alarmTimestampWithTimeZone)) {
            long aheadOfCacheJobTime = TimeUtils.getAheadOfCacheJobTime(alarmTimestampWithTimeZone, a);
            if (aheadOfCacheJobTime < 0) {
                L.alarm.e("JobTaskUtil#scheduleJobWhenUpdateAlarm ahead of alarm time is short");
                return;
            }
            JobInfo b2 = b(alarmRealmObjectBean, aheadOfCacheJobTime);
            try {
                L.alarm.i("JobTaskUtil#scheduleJobWhenUpdateAlarm schedule jobId=%s result=%s, delay=%s ", Integer.valueOf(b2.getId()), Integer.valueOf(jobScheduler.schedule(b2)), Long.valueOf(aheadOfCacheJobTime));
            } catch (IllegalStateException e) {
                L.alarm.w(e);
            }
        }
    }

    public static void scheduleNextTimeJob(AlarmRealmObjectBean alarmRealmObjectBean, long j) {
        JobScheduler jobScheduler = getJobScheduler();
        a(alarmRealmObjectBean, j);
        try {
            jobScheduler.schedule(b(alarmRealmObjectBean, j));
        } catch (IllegalStateException e) {
            L.base.w(e);
        }
    }

    private static JobInfo b(AlarmRealmObjectBean alarmRealmObjectBean, long j) {
        L.alarm.d("JobTaskUtil#buildJobInfo alarmItem dbId=%s", Integer.valueOf(alarmRealmObjectBean.getId()));
        JobInfo.Builder builder = new JobInfo.Builder(alarmRealmObjectBean.getId(), new ComponentName(b.getPackageName(), JobSchedulerService.class.getName()));
        builder.setRequiredNetworkType(1);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(JOB_BUNDLE_KEY_TYPE, JOB_TYPE_ALARM_MUSIC);
        persistableBundle.putString(JOB_BUNDLE_KEY_ALARM_IDS, alarmRealmObjectBean.getIdStr());
        persistableBundle.putString(JOB_BUNDLE_KEY_ALARM_RINGTONE, alarmRealmObjectBean.getRingtone());
        persistableBundle.putString(JOB_BUNDLE_KEY_ALARM_RINGTONE_TYPE, alarmRealmObjectBean.getRingtoneType());
        builder.setMinimumLatency(j);
        builder.setOverrideDeadline(j);
        builder.setExtras(persistableBundle);
        return builder.build();
    }

    public static void cancelJob(int i, long j) {
        L.alarm.i("JobTaskUtil cancelJob jobId %s", Integer.valueOf(i));
        JobScheduler jobScheduler = getJobScheduler();
        jobScheduler.cancel(i);
        JobInfo pendingJob = jobScheduler.getPendingJob(Constants.JOB_ID_REFRESH_WEATHER);
        if (pendingJob != null && j + TimeUtils.getTimeZoneOffsetMillis() == pendingJob.getExtras().getLong(JOB_BUNDLE_KEY_TIME)) {
            jobScheduler.cancel(Constants.JOB_ID_REFRESH_WEATHER);
        }
    }
}
