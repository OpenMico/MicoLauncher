package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.lockscreen.manager.WeatherManager;

/* loaded from: classes3.dex */
public class AlarmWeatherRefreshJob implements ICacheJob {
    private JobService a;
    private JobParameters b;

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public String getJobType() {
        return JobTaskUtil.JOB_TYPE_ALARM_WEATHER;
    }

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public void setJobNeedsReschedule(boolean z) {
    }

    @Override // com.xiaomi.micolauncher.common.services.job.ICacheJob
    public void setJobService(JobService jobService, JobParameters jobParameters) {
        this.a = jobService;
        this.b = jobParameters;
        WeatherManager.getManager().setJobService(jobService, jobParameters);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a == null || this.b == null) {
            throw new IllegalArgumentException("JobService and JobParameters should not be null");
        }
        L.weather.d("AlarmWeatherRefreshJob check weather load condition");
        WeatherManager.getManager().startLoadWeatherData(true);
    }
}
