package com.xiaomi.push;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ex;
import com.xiaomi.push.service.XMJobService;

@TargetApi(21)
/* loaded from: classes4.dex */
public class ez implements ex.a {
    Context a;
    JobScheduler b;
    private boolean c = false;

    ez(Context context) {
        this.a = context;
        this.b = (JobScheduler) context.getSystemService("jobscheduler");
    }

    @Override // com.xiaomi.push.ex.a
    /* renamed from: a */
    public void mo900a() {
        this.c = false;
        this.b.cancel(1);
    }

    void a(long j) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this.a.getPackageName(), XMJobService.class.getName()));
        builder.setMinimumLatency(j);
        builder.setOverrideDeadline(j);
        builder.setRequiredNetworkType(1);
        builder.setPersisted(false);
        JobInfo build = builder.build();
        b.c("schedule Job = " + build.getId() + " in " + j);
        this.b.schedule(builder.build());
    }

    @Override // com.xiaomi.push.ex.a
    public void a(boolean z) {
        if (z || this.c) {
            long b = ft.b();
            if (z) {
                mo900a();
                b -= SystemClock.elapsedRealtime() % b;
            }
            this.c = true;
            a(b);
        }
    }

    @Override // com.xiaomi.push.ex.a
    /* renamed from: a  reason: collision with other method in class */
    public boolean mo900a() {
        return this.c;
    }
}
