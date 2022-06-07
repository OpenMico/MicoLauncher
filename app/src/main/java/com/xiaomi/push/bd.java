package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;
import com.xiaomi.clientreport.processor.c;

/* loaded from: classes4.dex */
public class bd implements Runnable {
    private c a;
    private Context b;

    public void a(Context context) {
        this.b = context;
    }

    public void a(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        bi a;
        String str;
        String str2;
        long currentTimeMillis;
        try {
            if (this.a != null) {
                this.a.a();
            }
            b.c("begin read and send perf / event");
            if (this.a instanceof IEventProcessor) {
                a = bi.a(this.b);
                str = "sp_client_report_status";
                str2 = "event_last_upload_time";
                currentTimeMillis = System.currentTimeMillis();
            } else if (this.a instanceof IPerfProcessor) {
                a = bi.a(this.b);
                str = "sp_client_report_status";
                str2 = "perf_last_upload_time";
                currentTimeMillis = System.currentTimeMillis();
            } else {
                return;
            }
            a.m780a(str, str2, currentTimeMillis);
        } catch (Exception e) {
            b.a(e);
        }
    }
}
