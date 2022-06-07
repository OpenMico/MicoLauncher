package com.efs.sdk.base.a.e;

import android.os.Handler;
import android.os.Message;
import com.efs.sdk.base.a.b.a;
import com.efs.sdk.base.a.b.f;
import com.efs.sdk.base.a.b.h;
import com.efs.sdk.base.a.c.c;
import com.efs.sdk.base.a.e.f;
import com.efs.sdk.base.a.f.b;
import com.efs.sdk.base.a.i.e;
import com.efs.sdk.base.a.i.f;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class d extends Handler {
    public int a;
    public c b;
    private c c;
    private List<String> d;
    private AtomicInteger e;
    private f f;

    /* synthetic */ d(byte b) {
        this();
    }

    private d() {
        super(com.efs.sdk.base.a.h.a.a.a.getLooper());
        this.a = 5;
        this.d = new ArrayList();
        this.e = new AtomicInteger(0);
        this.b = new a();
        this.c = new e();
        this.f = new h();
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final d a = new d((byte) 0);
    }

    public static d a() {
        return a.a;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        f fVar;
        c cVar;
        com.efs.sdk.base.a.b.a aVar;
        List<File> list;
        com.efs.sdk.base.a.i.f fVar2;
        super.handleMessage(message);
        fVar = f.a.a;
        if (fVar.a()) {
            switch (message.what) {
                case 0:
                    cVar = c.a.a;
                    String a2 = cVar.a();
                    if ("denied".equalsIgnoreCase(a2) || "disconnected".equalsIgnoreCase(a2)) {
                        com.efs.sdk.base.a.h.d.a("efs.send_log", "log cann't be send because net status is ".concat(String.valueOf(a2)));
                        sendEmptyMessageDelayed(0, com.efs.sdk.base.a.d.a.a().l);
                        return;
                    }
                    List<b> emptyList = Collections.emptyList();
                    try {
                        aVar = a.b.a;
                        int i = this.a;
                        com.efs.sdk.base.a.b.f fVar3 = this.f;
                        aVar.a();
                        aVar.a();
                        File f = com.efs.sdk.base.a.h.a.f(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a);
                        if (!f.exists()) {
                            list = Collections.emptyList();
                        } else {
                            List<File> d = com.efs.sdk.base.a.h.b.d(f);
                            if (aVar.b) {
                                fVar2 = f.a.a;
                                int size = d.size();
                                if (fVar2.b != null && com.efs.sdk.base.a.d.a.a().d) {
                                    com.efs.sdk.base.a.i.b bVar = new com.efs.sdk.base.a.i.b("efs_core", "log_lag", fVar2.a.c);
                                    bVar.put(VoicePrintRegisterController.PARAMS_CNT, Integer.valueOf(size));
                                    fVar2.b.a(bVar);
                                }
                                aVar.b = false;
                            }
                            Collections.sort(d, aVar.d);
                            ArrayList arrayList = new ArrayList(i);
                            for (int size2 = d.size() - 1; size2 >= 0 && arrayList.size() < i; size2--) {
                                File file = d.get(size2);
                                if (file.exists() && (fVar3 == null || !fVar3.a(file))) {
                                    arrayList.add(file);
                                }
                            }
                            list = arrayList;
                        }
                        ArrayList arrayList2 = new ArrayList(i);
                        for (File file2 : list) {
                            b a3 = aVar.a(file2);
                            if (a3 == null) {
                                com.efs.sdk.base.a.h.d.a("efs.cache", "file upload error, name is " + file2.getName(), null);
                            } else {
                                arrayList2.add(a3);
                            }
                        }
                        emptyList = arrayList2;
                    } catch (Throwable unused) {
                    }
                    for (b bVar2 : emptyList) {
                        if ("wa".equals(bVar2.a.a) || b.a().a(bVar2.a.a, bVar2.a())) {
                            c cVar2 = this.b;
                            if ("wa".equals(bVar2.a.a)) {
                                cVar2 = this.c;
                            }
                            String uuid = UUID.randomUUID().toString();
                            this.d.add(uuid);
                            if (com.efs.sdk.base.a.h.a.d.a(new e(bVar2, cVar2, uuid)) == null) {
                                a(uuid, -1);
                            }
                        }
                    }
                    if (this.d.size() <= 0) {
                        sendEmptyMessageDelayed(0, com.efs.sdk.base.a.d.a.a().l);
                        return;
                    }
                    return;
                case 1:
                    Object obj = message.obj;
                    if (obj != null) {
                        this.d.remove(obj.toString());
                    }
                    int incrementAndGet = message.arg1 != 0 ? this.e.incrementAndGet() : 0;
                    if (!this.d.isEmpty()) {
                        return;
                    }
                    if (incrementAndGet < 5) {
                        sendEmptyMessage(0);
                        return;
                    }
                    this.e.set(0);
                    sendEmptyMessageDelayed(0, com.efs.sdk.base.a.d.a.a().k);
                    com.efs.sdk.base.a.h.d.a("efs.send_log", "request error cnt gt 5, next request delay 10s");
                    return;
                default:
                    return;
            }
        }
    }

    public final void a(Object obj, int i) {
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.obj = obj;
        obtain.arg1 = i;
        sendMessage(obtain);
    }
}
