package com.xiaomi.clientreport.manager;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;
import com.xiaomi.push.aj;
import com.xiaomi.push.ba;
import com.xiaomi.push.bb;
import com.xiaomi.push.bc;
import com.xiaomi.push.bd;
import com.xiaomi.push.bf;
import com.xiaomi.push.bi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class a {
    private static volatile a a;
    private ExecutorService b = Executors.newSingleThreadExecutor();
    private HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> c = new HashMap<>();
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> d = new HashMap<>();
    private Context e;
    private Config f;
    private IEventProcessor g;
    private IPerfProcessor h;

    private a(Context context) {
        this.e = context;
    }

    public static a a(Context context) {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a(context);
                }
            }
        }
        return a;
    }

    private void a(Runnable runnable, int i) {
        aj.a(this.e).a(runnable, i);
    }

    private void d() {
        if (a(this.e).a().isEventUploadSwitchOpen()) {
            bb bbVar = new bb(this.e);
            int eventUploadFrequency = (int) a(this.e).a().getEventUploadFrequency();
            if (eventUploadFrequency < 1800) {
                eventUploadFrequency = 1800;
            }
            if (System.currentTimeMillis() - bi.a(this.e).a("sp_client_report_status", "event_last_upload_time", 0L) > eventUploadFrequency * 1000) {
                aj.a(this.e).a(new d(this, bbVar), 10);
            }
            synchronized (a.class) {
                if (!aj.a(this.e).a((aj.a) bbVar, eventUploadFrequency)) {
                    aj.a(this.e).a(100886);
                    aj.a(this.e).a((aj.a) bbVar, eventUploadFrequency);
                }
            }
        }
    }

    private void e() {
        if (a(this.e).a().isPerfUploadSwitchOpen()) {
            bc bcVar = new bc(this.e);
            int perfUploadFrequency = (int) a(this.e).a().getPerfUploadFrequency();
            if (perfUploadFrequency < 1800) {
                perfUploadFrequency = 1800;
            }
            if (System.currentTimeMillis() - bi.a(this.e).a("sp_client_report_status", "perf_last_upload_time", 0L) > perfUploadFrequency * 1000) {
                aj.a(this.e).a(new e(this, bcVar), 15);
            }
            synchronized (a.class) {
                if (!aj.a(this.e).a((aj.a) bcVar, perfUploadFrequency)) {
                    aj.a(this.e).a(100887);
                    aj.a(this.e).a((aj.a) bcVar, perfUploadFrequency);
                }
            }
        }
    }

    public synchronized Config a() {
        if (this.f == null) {
            this.f = Config.defaultConfig(this.e);
        }
        return this.f;
    }

    /* renamed from: a */
    public void m150a() {
        a(this.e).d();
        a(this.e).e();
    }

    public void a(Config config, IEventProcessor iEventProcessor, IPerfProcessor iPerfProcessor) {
        this.f = config;
        this.g = iEventProcessor;
        this.h = iPerfProcessor;
        this.g.setEventMap(this.d);
        this.h.setPerfMap(this.c);
    }

    public void a(EventClientReport eventClientReport) {
        if (a().isEventUploadSwitchOpen()) {
            this.b.execute(new ba(this.e, eventClientReport, this.g));
            a(new b(this), 30);
        }
    }

    public void a(PerfClientReport perfClientReport) {
        if (a().isPerfUploadSwitchOpen()) {
            this.b.execute(new ba(this.e, perfClientReport, this.h));
            a(new c(this), 30);
        }
    }

    public void a(boolean z, boolean z2, long j, long j2) {
        Config config = this.f;
        if (config == null) {
            return;
        }
        if (z != config.isEventUploadSwitchOpen() || z2 != this.f.isPerfUploadSwitchOpen() || j != this.f.getEventUploadFrequency() || j2 != this.f.getPerfUploadFrequency()) {
            long eventUploadFrequency = this.f.getEventUploadFrequency();
            long perfUploadFrequency = this.f.getPerfUploadFrequency();
            Config build = Config.getBuilder().setAESKey(bf.a(this.e)).setEventEncrypted(this.f.isEventEncrypted()).setEventUploadSwitchOpen(z).setEventUploadFrequency(j).setPerfUploadSwitchOpen(z2).setPerfUploadFrequency(j2).build(this.e);
            this.f = build;
            if (!this.f.isEventUploadSwitchOpen()) {
                aj.a(this.e).a(100886);
            } else if (eventUploadFrequency != build.getEventUploadFrequency()) {
                b.c(this.e.getPackageName() + "reset event job " + build.getEventUploadFrequency());
                d();
            }
            if (!this.f.isPerfUploadSwitchOpen()) {
                aj.a(this.e).a(100887);
            } else if (perfUploadFrequency != build.getPerfUploadFrequency()) {
                b.c(this.e.getPackageName() + "reset perf job " + build.getPerfUploadFrequency());
                e();
            }
        }
    }

    public void b() {
        if (a().isEventUploadSwitchOpen()) {
            bd bdVar = new bd();
            bdVar.a(this.e);
            bdVar.a(this.g);
            this.b.execute(bdVar);
        }
    }

    public void c() {
        if (a().isPerfUploadSwitchOpen()) {
            bd bdVar = new bd();
            bdVar.a(this.h);
            bdVar.a(this.e);
            this.b.execute(bdVar);
        }
    }
}
