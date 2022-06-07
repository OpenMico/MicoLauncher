package com.efs.sdk.pa.a;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import com.efs.sdk.pa.PA;
import com.efs.sdk.pa.PAANRListener;
import com.efs.sdk.pa.PAMsgListener;
import com.efs.sdk.pa.a.b;
import com.efs.sdk.pa.a.g;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public final class c implements PA {
    private boolean a;
    private e c;
    private f d;
    private a e;
    private boolean h;
    private boolean i;
    private Looper b = Looper.myLooper();
    private b f = new b();
    private g g = new g();

    @Override // com.efs.sdk.pa.PA
    public final void unregisterPAANRListener() {
    }

    public c(boolean z) {
        this.i = z;
    }

    @Override // com.efs.sdk.pa.PA
    public final void start() {
        if (this.i) {
            this.h = true;
            e eVar = this.c;
            if (eVar != null) {
                this.b.setMessageLogging(eVar);
            }
            a aVar = this.e;
            if (aVar != null && aVar.f) {
                aVar.f = false;
                aVar.g.post(aVar.m);
                aVar.j = SystemClock.uptimeMillis();
            }
        }
    }

    @Override // com.efs.sdk.pa.PA
    public final void stop() {
        this.h = false;
        this.b.setMessageLogging(null);
        a aVar = this.e;
        if (aVar != null) {
            aVar.f = true;
            aVar.g.removeCallbacksAndMessages(null);
            aVar.a = true;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.efs.sdk.pa.a.b.a.1.<init>(com.efs.sdk.pa.a.b$a):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:250)
        	at jadx.core.dex.visitors.ModVisitor.processAnonymousConstructor(ModVisitor.java:449)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:107)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:92)
        */
    @Override // com.efs.sdk.pa.PA
    public final void startCalFPS(java.lang.String r5, android.view.View r6) {
        /*
            r4 = this;
            boolean r0 = r4.h
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.efs.sdk.pa.a.b r0 = r4.f
            if (r5 == 0) goto L_0x0044
            java.lang.String r1 = r5.trim()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x0044
            if (r6 != 0) goto L_0x0016
            goto L_0x0044
        L_0x0016:
            java.util.HashMap<java.lang.String, com.efs.sdk.pa.a.b$a> r1 = r0.a
            java.lang.Object r1 = r1.get(r5)
            if (r1 != 0) goto L_0x0043
            com.efs.sdk.pa.a.b$a r1 = new com.efs.sdk.pa.a.b$a
            r2 = 0
            r1.<init>(r2)
            if (r6 == 0) goto L_0x003e
            r1.d = r6
            com.efs.sdk.pa.a.b$a$1 r6 = new com.efs.sdk.pa.a.b$a$1
            r6.<init>()
            r1.c = r6
            android.view.View r2 = r1.d
            android.view.ViewTreeObserver r2 = r2.getViewTreeObserver()
            r2.addOnPreDrawListener(r6)
            long r2 = java.lang.System.currentTimeMillis()
            r1.a = r2
        L_0x003e:
            java.util.HashMap<java.lang.String, com.efs.sdk.pa.a.b$a> r6 = r0.a
            r6.put(r5, r1)
        L_0x0043:
            return
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.pa.a.c.startCalFPS(java.lang.String, android.view.View):void");
    }

    @Override // com.efs.sdk.pa.PA
    public final int endCalFPS(String str) {
        b.a aVar;
        if (!this.h) {
            return -1;
        }
        b bVar = this.f;
        int i = 0;
        if (str == null || str.trim().length() == 0 || (aVar = bVar.a.get(str)) == null) {
            return 0;
        }
        if (!(aVar.d == null || aVar.c == null)) {
            aVar.d.getViewTreeObserver().removeOnPreDrawListener(aVar.c);
        }
        bVar.a.remove(str);
        int currentTimeMillis = (int) (((float) aVar.b) / (((float) (System.currentTimeMillis() - aVar.a)) / 1000.0f));
        if (currentTimeMillis > 0) {
            i = currentTimeMillis;
        }
        if (bVar.b) {
            Log.e("PerformanceAnalyze", "key=" + str + ",fps=" + i);
        }
        return i;
    }

    @Override // com.efs.sdk.pa.PA
    public final void startCalTime(String str) {
        if (this.h) {
            g gVar = this.g;
            if (str != null && str.trim().length() != 0 && gVar.a.get(str) == null) {
                g.a aVar = new g.a((byte) 0);
                aVar.a = System.currentTimeMillis();
                gVar.a.put(str, aVar);
            }
        }
    }

    @Override // com.efs.sdk.pa.PA
    public final long endCalTime(String str) {
        g.a aVar;
        if (!this.h) {
            return -1L;
        }
        g gVar = this.g;
        if (str == null || str.trim().length() == 0 || (aVar = gVar.a.get(str)) == null) {
            return 0L;
        }
        gVar.a.remove(str);
        long currentTimeMillis = System.currentTimeMillis() - aVar.a;
        if (gVar.b) {
            Log.e("PerformanceAnalyze", "key=" + str + ",consumeTime=" + currentTimeMillis);
        }
        return currentTimeMillis;
    }

    @Override // com.efs.sdk.pa.PA
    public final void enableLog(boolean z) {
        this.a = z;
        this.f.b = z;
        this.g.b = z;
        f fVar = this.d;
        if (fVar != null) {
            fVar.b = z;
        }
    }

    @Override // com.efs.sdk.pa.PA
    public final void enableDumpToFile(String str) {
        FileOutputStream fileOutputStream;
        f fVar = this.d;
        if (fVar != null && str != null && str.trim().length() != 0) {
            fVar.c = str;
            if (fVar.d == null) {
                FileOutputStream fileOutputStream2 = null;
                try {
                    fileOutputStream = new FileOutputStream(str);
                } catch (Exception unused) {
                }
                try {
                    fVar.d = new BufferedOutputStream(fileOutputStream);
                } catch (Exception unused2) {
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (Exception unused3) {
                        }
                    }
                }
            }
        }
    }

    @Override // com.efs.sdk.pa.PA
    public final void registerPAMsgListener(PAMsgListener pAMsgListener) {
        if (this.c == null) {
            this.c = new e();
        }
        this.b.setMessageLogging(this.c);
        if (this.d == null) {
            this.d = new f();
        }
        f fVar = this.d;
        fVar.b = this.a;
        fVar.a = pAMsgListener;
        this.c.a.add(fVar);
    }

    @Override // com.efs.sdk.pa.PA
    public final void unRegisterPAMsgListener() {
        f fVar = this.d;
        if (fVar != null) {
            fVar.a = null;
        }
        e eVar = this.c;
        if (eVar != null) {
            eVar.a.remove(this.d);
        }
    }

    @Override // com.efs.sdk.pa.PA
    public final void registerPAANRListener(Context context, PAANRListener pAANRListener) {
        registerPAANRListener(context, pAANRListener, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // com.efs.sdk.pa.PA
    public final void registerPAANRListener(Context context, PAANRListener pAANRListener, long j) {
        registerPAANRListener(context, pAANRListener, j, Looper.getMainLooper().getThread());
    }

    @Override // com.efs.sdk.pa.PA
    public final void registerPAANRListener(Context context, PAANRListener pAANRListener, long j, Thread thread) {
        if (this.e == null) {
            if (thread != null) {
                this.e = new a((Application) context.getApplicationContext(), j);
            } else {
                this.e = new a((Application) context.getApplicationContext(), j, false);
            }
        }
        this.e.h = pAANRListener;
    }
}
