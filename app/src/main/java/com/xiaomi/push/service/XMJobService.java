package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.au;
import com.xiaomi.push.ex;

/* loaded from: classes4.dex */
public class XMJobService extends Service {
    static Service a;
    private IBinder b = null;

    @TargetApi(21)
    /* loaded from: classes4.dex */
    static class a extends JobService {
        Binder a;
        private Handler b;

        /* renamed from: com.xiaomi.push.service.XMJobService$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        private static class HandlerC0187a extends Handler {
            JobService a;

            HandlerC0187a(JobService jobService) {
                super(jobService.getMainLooper());
                this.a = jobService;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    JobParameters jobParameters = (JobParameters) message.obj;
                    b.m149a("Job finished " + jobParameters.getJobId());
                    this.a.jobFinished(jobParameters, false);
                    if (jobParameters.getJobId() == 1) {
                        ex.a(false);
                    }
                }
            }
        }

        a(Service service) {
            this.a = null;
            this.a = (Binder) au.a(this, "onBind", new Intent());
            au.a(this, "attachBaseContext", service);
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            b.m149a("Job started " + jobParameters.getJobId());
            Intent intent = new Intent(this, XMPushService.class);
            intent.setAction("com.xiaomi.push.timer");
            intent.setPackage(getPackageName());
            startService(intent);
            if (this.b == null) {
                this.b = new HandlerC0187a(this);
            }
            Handler handler = this.b;
            handler.sendMessage(Message.obtain(handler, 1, jobParameters));
            return true;
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            b.m149a("Job stop " + jobParameters.getJobId());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Service a() {
        return a;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        IBinder iBinder = this.b;
        return iBinder != null ? iBinder : new Binder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 21) {
            this.b = new a(this).a;
        }
        a = this;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        a = null;
    }
}
