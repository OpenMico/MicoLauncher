package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.v;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class aa implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;
    final /* synthetic */ long f;
    final /* synthetic */ f g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(f fVar, String str, String str2, String str3, String str4, String str5, long j) {
        this.g = fVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        JSONObject d;
        d dVar;
        Configuration configuration;
        OneTrack.IEventHook iEventHook;
        v vVar;
        try {
            d = this.g.d("onetrack_bug_report");
            dVar = this.g.b;
            String str = this.a;
            String str2 = this.b;
            String str3 = this.c;
            String str4 = this.d;
            String str5 = this.e;
            long j = this.f;
            configuration = this.g.f;
            iEventHook = this.g.h;
            vVar = this.g.i;
            dVar.a("onetrack_bug_report", c.a(str, str2, str3, str4, str5, j, configuration, iEventHook, d, vVar));
        } catch (Exception e) {
            p.b("OneTrackImp", "trackException error: " + e.toString());
        }
    }
}
