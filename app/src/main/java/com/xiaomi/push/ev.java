package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.manager.ClientReportClient;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.be;
import com.xiaomi.push.service.bf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ev {
    private static a a;
    private static Map<String, hr> b;

    /* loaded from: classes4.dex */
    public interface a {
        void a(Context context, hl hlVar);
    }

    public static int a(int i) {
        if (i > 0) {
            return i + 1000;
        }
        return -1;
    }

    public static int a(Enum r1) {
        if (r1 != null) {
            if (r1 instanceof hh) {
                return r1.ordinal() + 1001;
            }
            if (r1 instanceof hr) {
                return r1.ordinal() + 2001;
            }
            if (r1 instanceof fb) {
                return r1.ordinal() + 3001;
            }
        }
        return -1;
    }

    public static Config a(Context context) {
        boolean a2 = ag.a(context).a(hm.PerfUploadSwitch.a(), false);
        boolean a3 = ag.a(context).a(hm.EventUploadSwitch.a(), false);
        int a4 = ag.a(context).a(hm.PerfUploadFrequency.a(), CacheConstants.DAY);
        return Config.getBuilder().setEventUploadSwitchOpen(a3).setEventUploadFrequency(ag.a(context).a(hm.EventUploadFrequency.a(), CacheConstants.DAY)).setPerfUploadSwitchOpen(a2).setPerfUploadFrequency(a4).build(context);
    }

    public static EventClientReport a(Context context, String str, String str2, int i, long j, String str3) {
        EventClientReport a2 = a(str);
        a2.eventId = str2;
        a2.eventType = i;
        a2.eventTime = j;
        a2.eventContent = str3;
        return a2;
    }

    public static EventClientReport a(String str) {
        EventClientReport eventClientReport = new EventClientReport();
        eventClientReport.production = 1000;
        eventClientReport.reportType = 1001;
        eventClientReport.clientInterfaceId = str;
        return eventClientReport;
    }

    public static PerfClientReport a() {
        PerfClientReport perfClientReport = new PerfClientReport();
        perfClientReport.production = 1000;
        perfClientReport.reportType = 1000;
        perfClientReport.clientInterfaceId = "P100000";
        return perfClientReport;
    }

    public static PerfClientReport a(Context context, int i, long j, long j2) {
        PerfClientReport a2 = a();
        a2.code = i;
        a2.perfCounts = j;
        a2.perfLatencies = j2;
        return a2;
    }

    public static hl a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        hl hlVar = new hl();
        hlVar.d("category_client_report_data");
        hlVar.a("push_sdk_channel");
        hlVar.a(1L);
        hlVar.b(str);
        hlVar.a(true);
        hlVar.b(System.currentTimeMillis());
        hlVar.g(context.getPackageName());
        hlVar.e("com.xiaomi.xmsf");
        hlVar.f(be.a());
        hlVar.c("quality_support");
        return hlVar;
    }

    /* renamed from: a */
    public static hr m895a(String str) {
        if (b == null) {
            synchronized (hr.class) {
                if (b == null) {
                    b = new HashMap();
                    hr[] values = hr.values();
                    for (hr hrVar : values) {
                        b.put(hrVar.f67a.toLowerCase(), hrVar);
                    }
                }
            }
        }
        hr hrVar2 = b.get(str.toLowerCase());
        return hrVar2 != null ? hrVar2 : hr.Invalid;
    }

    /* renamed from: a */
    public static String m896a(int i) {
        return i == 1000 ? "E100000" : i == 3000 ? "E100002" : i == 2000 ? "E100001" : i == 6000 ? "E100003" : "";
    }

    /* renamed from: a */
    public static void m897a(Context context) {
        ClientReportClient.updateConfig(context, a(context));
    }

    public static void a(Context context, Config config) {
        ClientReportClient.init(context, config, new et(context), new eu(context));
    }

    private static void a(Context context, hl hlVar) {
        if (m898a(context.getApplicationContext())) {
            bf.a(context.getApplicationContext(), hlVar);
            return;
        }
        a aVar = a;
        if (aVar != null) {
            aVar.a(context, hlVar);
        }
    }

    public static void a(Context context, List<String> list) {
        if (list != null) {
            try {
                for (String str : list) {
                    hl a2 = a(context, str);
                    if (be.a(a2, false)) {
                        b.c(a2.d() + "is not valid...");
                    } else {
                        b.c("send event/perf data item id:" + a2.d());
                        a(context, a2);
                    }
                }
            } catch (Throwable th) {
                b.d(th.getMessage());
            }
        }
    }

    public static void a(a aVar) {
        a = aVar;
    }

    /* renamed from: a */
    public static boolean m898a(Context context) {
        return context != null && !TextUtils.isEmpty(context.getPackageName()) && "com.xiaomi.xmsf".equals(context.getPackageName());
    }
}
