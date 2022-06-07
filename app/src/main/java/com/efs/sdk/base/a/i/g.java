package com.efs.sdk.base.a.i;

import com.efs.sdk.base.a.i.f;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class g extends a {
    private ConcurrentHashMap<String, a> b = new ConcurrentHashMap<>(10);

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(String str, String str2, String str3) {
        String str4 = str + "_" + str2 + "_" + str3.trim();
        if (!this.b.containsKey(str4) || this.b.get(str4) == null) {
            this.b.putIfAbsent(str4, new a(str, str2, str3));
        }
        this.b.get(str4).d.incrementAndGet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        String a;
        String b;
        String c;
        AtomicInteger d = new AtomicInteger(0);

        a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    @Override // com.efs.sdk.base.a.i.a
    public final void a() {
        f fVar;
        try {
            if (this.a != null) {
                for (Map.Entry<String, a> entry : this.b.entrySet()) {
                    a value = entry.getValue();
                    int i = value.d.get();
                    if (i > 0) {
                        com.efs.sdk.base.a.d.a aVar = this.a;
                        String str = value.a;
                        String str2 = value.b;
                        String str3 = value.c;
                        fVar = f.a.a;
                        b bVar = new b("efs_core", "req_succ_rate", fVar.a.c);
                        bVar.put("rep_code", str);
                        bVar.put("px_code", str2);
                        bVar.put("path", str3);
                        bVar.put(VoicePrintRegisterController.PARAMS_CNT, Integer.valueOf(i));
                        aVar.a(bVar);
                        value.d.addAndGet(i * (-1));
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }
}
