package com.google.android.exoplayer2.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

@RequiresApi(21)
/* loaded from: classes2.dex */
public final class PlatformScheduler implements Scheduler {
    private static final int a;
    private final int b;
    private final ComponentName c;
    private final JobScheduler d;

    static {
        a = (Util.SDK_INT >= 26 ? 16 : 0) | 15;
    }

    @RequiresPermission("android.permission.RECEIVE_BOOT_COMPLETED")
    public PlatformScheduler(Context context, int i) {
        Context applicationContext = context.getApplicationContext();
        this.b = i;
        this.c = new ComponentName(applicationContext, PlatformSchedulerService.class);
        this.d = (JobScheduler) Assertions.checkNotNull((JobScheduler) applicationContext.getSystemService("jobscheduler"));
    }

    @Override // com.google.android.exoplayer2.scheduler.Scheduler
    public boolean schedule(Requirements requirements, String str, String str2) {
        return this.d.schedule(a(this.b, this.c, requirements, str2, str)) == 1;
    }

    @Override // com.google.android.exoplayer2.scheduler.Scheduler
    public boolean cancel() {
        this.d.cancel(this.b);
        return true;
    }

    @Override // com.google.android.exoplayer2.scheduler.Scheduler
    public Requirements getSupportedRequirements(Requirements requirements) {
        return requirements.filterRequirements(a);
    }

    private static JobInfo a(int i, ComponentName componentName, Requirements requirements, String str, String str2) {
        Requirements filterRequirements = requirements.filterRequirements(a);
        if (!filterRequirements.equals(requirements)) {
            StringBuilder sb = new StringBuilder(46);
            sb.append("Ignoring unsupported requirements: ");
            sb.append(filterRequirements.getRequirements() ^ requirements.getRequirements());
            Log.w("PlatformScheduler", sb.toString());
        }
        JobInfo.Builder builder = new JobInfo.Builder(i, componentName);
        if (requirements.isUnmeteredNetworkRequired()) {
            builder.setRequiredNetworkType(2);
        } else if (requirements.isNetworkRequired()) {
            builder.setRequiredNetworkType(1);
        }
        builder.setRequiresDeviceIdle(requirements.isIdleRequired());
        builder.setRequiresCharging(requirements.isChargingRequired());
        if (Util.SDK_INT >= 26 && requirements.isStorageNotLowRequired()) {
            builder.setRequiresStorageNotLow(true);
        }
        builder.setPersisted(true);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("service_action", str);
        persistableBundle.putString("service_package", str2);
        persistableBundle.putInt(DownloadService.KEY_REQUIREMENTS, requirements.getRequirements());
        builder.setExtras(persistableBundle);
        return builder.build();
    }

    /* loaded from: classes2.dex */
    public static final class PlatformSchedulerService extends JobService {
        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            PersistableBundle extras = jobParameters.getExtras();
            int notMetRequirements = new Requirements(extras.getInt(DownloadService.KEY_REQUIREMENTS)).getNotMetRequirements(this);
            if (notMetRequirements == 0) {
                Util.startForegroundService(this, new Intent((String) Assertions.checkNotNull(extras.getString("service_action"))).setPackage((String) Assertions.checkNotNull(extras.getString("service_package"))));
                return false;
            }
            StringBuilder sb = new StringBuilder(33);
            sb.append("Requirements not met: ");
            sb.append(notMetRequirements);
            Log.w("PlatformScheduler", sb.toString());
            jobFinished(jobParameters, true);
            return false;
        }
    }
}
