package com.xiaomi.micolauncher.common.services.job;

import android.app.job.JobParameters;
import android.app.job.JobService;

/* loaded from: classes3.dex */
public interface ICacheJob extends Runnable {
    String getJobType();

    void setJobNeedsReschedule(boolean z);

    void setJobService(JobService jobService, JobParameters jobParameters);
}
