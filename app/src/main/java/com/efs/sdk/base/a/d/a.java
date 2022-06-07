package com.efs.sdk.base.a.d;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.efs.sdk.base.BuildConfig;
import com.efs.sdk.base.EfsReporter;
import com.efs.sdk.base.a.c.b;
import com.efs.sdk.base.a.c.c;
import com.efs.sdk.base.a.e.d;
import com.efs.sdk.base.a.e.f;
import com.efs.sdk.base.a.g.d;
import com.efs.sdk.base.a.h.e;
import com.efs.sdk.base.a.h.g;
import com.efs.sdk.base.a.h.h;
import com.efs.sdk.base.a.i.f;
import com.efs.sdk.base.http.HttpResponse;
import com.efs.sdk.base.protocol.ILogProtocol;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import xcrash.TombstoneParser;

/* loaded from: classes.dex */
public final class a implements Handler.Callback {
    public static com.efs.sdk.base.a.c.a a;
    private com.efs.sdk.base.a.d.a.a h;
    private int b = 0;
    private final int c = 0;
    private final int d = 1;
    private final int e = 2;
    private final int f = 3;
    private volatile boolean g = false;
    private Handler i = new Handler(com.efs.sdk.base.a.h.a.a.a.getLooper(), this);

    public a(EfsReporter.Builder builder) {
        a = builder.getGlobalEnvStruct();
        this.i.sendEmptyMessage(0);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(@NonNull Message message) {
        c cVar;
        f fVar;
        f fVar2;
        com.efs.sdk.base.a.e.f unused;
        switch (message.what) {
            case 0:
                cVar = c.a.a;
                cVar.a = new b();
                cVar.a.a("appid", a.a);
                int a2 = g.a();
                cVar.a.a(TombstoneParser.keyProcessId, Integer.valueOf(a2));
                cVar.a.a("ps", g.a(a2));
                String a3 = h.a(cVar.b);
                cVar.a.a("wid", a3);
                if (TextUtils.isEmpty(a.h)) {
                    cVar.a.a(OneTrack.Param.UID, a3);
                } else {
                    cVar.a.a(OneTrack.Param.UID, a.h);
                }
                b bVar = cVar.a;
                com.efs.sdk.base.a.a.a.a();
                bVar.a("stime", Long.valueOf(com.efs.sdk.base.a.a.a.b() - Process.getElapsedCpuTime()));
                cVar.a.a(OneTrack.Param.PKG, cVar.b.getPackageName());
                cVar.a.a("ver", com.efs.sdk.base.a.h.f.a(cVar.b));
                cVar.a.a("vcode", com.efs.sdk.base.a.h.f.b(cVar.b));
                cVar.a.a("sdk_ver", BuildConfig.VERSION_NAME);
                cVar.a.a(Constants.PHONE_BRAND, Build.BRAND.toLowerCase());
                cVar.a.a("model", Build.MODEL == null ? "unknown" : Build.MODEL.replace(StringUtils.SPACE, Constants.ACCEPT_TIME_SEPARATOR_SERVER).replace("_", Constants.ACCEPT_TIME_SEPARATOR_SERVER).toLowerCase());
                DisplayMetrics displayMetrics = cVar.b.getResources().getDisplayMetrics();
                cVar.a.a("dsp_w", Integer.valueOf(displayMetrics.widthPixels));
                cVar.a.a("dsp_h", Integer.valueOf(displayMetrics.heightPixels));
                cVar.a.a("fr", "android");
                cVar.a.a(PlaybackTrackHelper.ROM_TAG, Build.VERSION.RELEASE);
                cVar.a.a("sdk", Integer.valueOf(Build.VERSION.SDK_INT));
                cVar.a.a("lang", Locale.getDefault().getLanguage());
                cVar.a.a("tzone", TimeZone.getDefault().getID());
                cVar.a.a(OneTrack.Param.NET, e.b(cVar.b));
                unused = f.a.a;
                com.efs.sdk.base.a.c.a.c.a().b();
                b();
                fVar = f.a.a;
                boolean z = a.i;
                com.efs.sdk.base.a.i.c cVar2 = fVar.a;
                if (z) {
                    cVar2.a = "https://errlogos.umeng.com/api/crashsdk/logcollect";
                    cVar2.b = "4ea4e41a3993";
                } else {
                    cVar2.a = "https://errlog.umeng.com/api/crashsdk/logcollect";
                    cVar2.b = "28ef1713347d";
                }
                fVar.b = this;
                fVar.c.a = fVar.b;
                fVar.d.a = fVar.b;
                this.g = true;
                d.a().sendEmptyMessageDelayed(0, a.k);
                fVar2 = f.a.a;
                if (fVar2.b != null && a.d) {
                    fVar2.b.a(new com.efs.sdk.base.a.i.b("efs_core", "pvuv", fVar2.a.c));
                    break;
                }
                break;
            case 1:
                Object obj = message.obj;
                if (obj != null && (obj instanceof ILogProtocol)) {
                    c((ILogProtocol) obj);
                    break;
                }
                break;
            case 3:
                b();
                break;
        }
        return true;
    }

    @NonNull
    public static com.efs.sdk.base.a.c.a a() {
        return a;
    }

    private void b() {
        if (this.h == null) {
            this.h = new com.efs.sdk.base.a.d.a.a();
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            a.c.registerReceiver(this.h, intentFilter);
        } catch (Throwable th) {
            com.efs.sdk.base.a.h.d.a("efs.base", "register network change receiver error", th);
            this.b++;
            if (this.b < 3) {
                this.i.sendEmptyMessageDelayed(3, 6000L);
            }
        }
    }

    public final void a(ILogProtocol iLogProtocol) {
        if (!this.g) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = iLogProtocol;
            this.i.sendMessage(obtain);
            return;
        }
        c(iLogProtocol);
    }

    private void c(final ILogProtocol iLogProtocol) {
        if (iLogProtocol != null) {
            com.efs.sdk.base.a.h.a.d.a(new Runnable() { // from class: com.efs.sdk.base.a.d.a.1
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.efs.sdk.base.a.g.d.1.<init>(com.efs.sdk.base.a.g.d, com.efs.sdk.base.a.f.b):void, class status: GENERATED_AND_UNLOADED
                    	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:250)
                    	at jadx.core.dex.visitors.ModVisitor.processAnonymousConstructor(ModVisitor.java:449)
                    	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:107)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:92)
                    */
                @Override // java.lang.Runnable
                public final void run() {
                    /*
                        r3 = this;
                        com.efs.sdk.base.protocol.ILogProtocol r0 = r2     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.c.c r1 = com.efs.sdk.base.a.c.c.a.a()     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.c.b r1 = r1.a     // Catch: Throwable -> 0x003a
                        r0.insertGlobal(r1)     // Catch: Throwable -> 0x003a
                        java.lang.String r0 = "wa"
                        com.efs.sdk.base.protocol.ILogProtocol r1 = r2     // Catch: Throwable -> 0x003a
                        java.lang.String r1 = r1.getLogType()     // Catch: Throwable -> 0x003a
                        boolean r0 = r0.equalsIgnoreCase(r1)     // Catch: Throwable -> 0x003a
                        if (r0 != 0) goto L_0x001e
                        com.efs.sdk.base.protocol.ILogProtocol r0 = r2     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.d.a.b(r0)     // Catch: Throwable -> 0x003a
                    L_0x001e:
                        com.efs.sdk.base.a.c.a r0 = com.efs.sdk.base.a.d.a.a()     // Catch: Throwable -> 0x003a
                        boolean r0 = r0.e     // Catch: Throwable -> 0x003a
                        if (r0 != 0) goto L_0x0027
                        return
                    L_0x0027:
                        com.efs.sdk.base.protocol.ILogProtocol r0 = r2     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.f.b r0 = com.efs.sdk.base.a.f.b.a(r0)     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.g.d r1 = com.efs.sdk.base.a.g.d.a.a()     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.g.d$1 r2 = new com.efs.sdk.base.a.g.d$1     // Catch: Throwable -> 0x003a
                        r2.<init>()     // Catch: Throwable -> 0x003a
                        com.efs.sdk.base.a.h.a.d.a(r2)     // Catch: Throwable -> 0x003a
                        return
                    L_0x003a:
                        r0 = move-exception
                        java.lang.String r1 = "efs.base"
                        java.lang.String r2 = "log send error"
                        com.efs.sdk.base.a.h.d.b(r1, r2, r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.efs.sdk.base.a.d.a.AnonymousClass1.run():void");
                }
            });
        }
    }

    @Nullable
    public static HttpResponse a(String str, int i, String str2, boolean z, File file) {
        com.efs.sdk.base.a.g.d dVar;
        com.efs.sdk.base.a.f.b bVar = new com.efs.sdk.base.a.f.b(str, (byte) 2);
        bVar.b(1);
        bVar.d = file;
        bVar.a(str2);
        bVar.a(i);
        bVar.b.b = z;
        bVar.d();
        dVar = d.a.a;
        dVar.a.a(bVar);
        return bVar.b.c;
    }

    static /* synthetic */ void b(ILogProtocol iLogProtocol) {
        for (ValueCallback<Pair<Message, Message>> valueCallback : a.a(9)) {
            HashMap hashMap = new HashMap(4);
            hashMap.put("log_type", iLogProtocol.getLogType());
            hashMap.put("log_data", iLogProtocol.generateString());
            hashMap.put("link_key", iLogProtocol.getLinkKey());
            hashMap.put("link_id", iLogProtocol.getLinkId());
            Message obtain = Message.obtain(null, 9, hashMap);
            Message obtain2 = Message.obtain();
            valueCallback.onReceiveValue(new Pair<>(obtain, obtain2));
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
