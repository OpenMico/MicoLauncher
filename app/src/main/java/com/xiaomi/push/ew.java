package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.exoplayer2.PlaybackException;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.data.a;
import com.xiaomi.clientreport.manager.ClientReportClient;

/* loaded from: classes4.dex */
public class ew {
    private static volatile ew a;
    private Context b;

    private ew(Context context) {
        this.b = context;
    }

    public static ew a(Context context) {
        if (a == null) {
            synchronized (ew.class) {
                if (a == null) {
                    a = new ew(context);
                }
            }
        }
        return a;
    }

    private void a(a aVar) {
        if (aVar instanceof PerfClientReport) {
            ClientReportClient.reportPerf(this.b, (PerfClientReport) aVar);
        } else if (aVar instanceof EventClientReport) {
            ClientReportClient.reportEvent(this.b, (EventClientReport) aVar);
        }
    }

    public void a(String str, int i, long j, long j2) {
        if (i >= 0 && j2 >= 0 && j > 0) {
            PerfClientReport a2 = ev.a(this.b, i, j, j2);
            a2.setAppPackageName(str);
            a2.setSdkVersion("3_6_19");
            a(a2);
        }
    }

    public void a(String str, Intent intent, int i, String str2) {
        if (intent != null) {
            a(str, ev.m896a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), i, System.currentTimeMillis(), str2);
        }
    }

    public void a(String str, Intent intent, String str2) {
        if (intent != null) {
            a(str, ev.m896a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED, System.currentTimeMillis(), str2);
        }
    }

    public void a(String str, Intent intent, Throwable th) {
        if (intent != null) {
            a(str, ev.m896a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra("messageId"), PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED, System.currentTimeMillis(), th.getMessage());
        }
    }

    public void a(String str, String str2, String str3, int i, long j, String str4) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            EventClientReport a2 = ev.a(this.b, str2, str3, i, j, str4);
            a2.setAppPackageName(str);
            a2.setSdkVersion("3_6_19");
            a(a2);
        }
    }

    public void a(String str, String str2, String str3, int i, String str4) {
        a(str, str2, str3, i, System.currentTimeMillis(), str4);
    }

    public void a(String str, String str2, String str3, String str4) {
        a(str, str2, str3, PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED, System.currentTimeMillis(), str4);
    }

    public void a(String str, String str2, String str3, Throwable th) {
        a(str, str2, str3, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED, System.currentTimeMillis(), th.getMessage());
    }

    public void b(String str, String str2, String str3, String str4) {
        a(str, str2, str3, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED, System.currentTimeMillis(), str4);
    }

    public void c(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 4002, System.currentTimeMillis(), str4);
    }
}
