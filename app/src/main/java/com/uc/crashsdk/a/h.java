package com.uc.crashsdk.a;

import android.os.Build;
import android.os.Process;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseArray;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.common.base.Ascii;
import com.uc.crashsdk.JNIBridge;
import com.uc.crashsdk.b;
import com.uc.crashsdk.e;
import com.uc.crashsdk.g;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import xcrash.TombstoneParser;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class h {
    static final /* synthetic */ boolean a = !h.class.desiredAssertionStatus();
    private static final Object b = new Object();
    private static final Map<String, String> c = new HashMap();
    private static int d = 0;
    private static final Map<String, a> e = new HashMap();
    private static final Object f = new Object();
    private static final Object g = new Object();
    private static final SparseArray<String> h = new SparseArray<>();
    private static boolean i = false;
    private static boolean j = false;
    private static final Object k = new Object();
    private static String l = null;

    static /* synthetic */ String a(long j2) {
        if (j2 < PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
            return "512M";
        }
        return String.format(Locale.US, "%dG", Long.valueOf(((j2 / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + 512) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
    }

    static /* synthetic */ void a(StringBuilder sb, String str, long j2) {
        b(sb, str, String.valueOf(j2));
    }

    static /* synthetic */ void a(StringBuilder sb, Map map) {
        for (String str : map.keySet()) {
            b(sb, str, (String) map.get(str));
        }
    }

    static /* synthetic */ Map c(String str) {
        HashMap hashMap = new HashMap();
        String[] split = str.split("`");
        for (String str2 : split) {
            if (str2.length() > 1) {
                String[] split2 = str2.split("=", 3);
                if (split2.length == 2) {
                    hashMap.put(split2[0], split2[1]);
                }
            }
        }
        return hashMap;
    }

    /* compiled from: ProGuard */
    /* loaded from: classes2.dex */
    public static class a {
        long a = 0;
        int b = 0;
        Map<String, String> c = new HashMap();
        private String d;
        private boolean e;
        private boolean f;

        a(String str, boolean z, boolean z2) {
            this.e = false;
            this.f = false;
            this.d = str;
            this.e = z;
            this.f = z2;
        }

        final void a(String str, String str2) {
            this.c.put(str, str2);
        }

        final void a(String str, long j) {
            long d = d(str) + j;
            if (d <= 100) {
                j = d < 0 ? 0L : d;
            }
            a(str, String.valueOf(j));
        }

        final boolean a(a aVar) {
            if (!this.f) {
                a.a("crashsdk", String.format(Locale.US, "WaItem '%s' is not mergable!", this.d), null);
                return false;
            }
            for (String str : aVar.c.keySet()) {
                if (str.startsWith("c_")) {
                    a(str, aVar.a(str));
                } else {
                    long d = aVar.d(str);
                    if (d == 0) {
                        a(str, aVar.a(str));
                    } else if (d < 100) {
                        a(str, d);
                    }
                }
            }
            return true;
        }

        final String a(String str) {
            return this.c.get(str);
        }

        final String b(String str) {
            String a = a(str);
            return a == null ? "" : a;
        }

        private long d(String str) {
            return g.c(a(str));
        }

        final String a(boolean z, boolean z2, boolean z3) {
            if (this.d == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            if (z) {
                h.b(sb, "lt", "uc");
                h.b(sb, "pre", g.e());
                h.b(sb, OneTrack.Param.PKG, com.uc.crashsdk.a.a);
                h.b(sb, PlaybackTrackHelper.ROM_TAG, Build.VERSION.RELEASE);
                h.b(sb, "brd", Build.BRAND);
                h.b(sb, "model", Build.MODEL);
                h.a(sb, "sdk", Build.VERSION.SDK_INT);
                h.b(sb, ai.w, e.e());
                h.b(sb, "hdw", e.f());
                long n = h.n();
                h.a(sb, "ram", n);
                h.b(sb, "aram", h.a(n));
                h.b(sb, "cver", "3.2.0.4");
                h.b(sb, "cseq", "210105150455");
                h.b(sb, "ctag", "release");
                h.b(sb, "aver", com.uc.crashsdk.a.a());
                h.b(sb, "ver", g.R());
                h.b(sb, "sver", g.S());
                h.b(sb, RtspHeaders.Values.SEQ, g.T());
                h.b(sb, "grd", b.x() ? "fg" : "bg");
                h.b(sb, ai.x, "android");
                sb.append("\n");
            }
            h.b(sb, "lt", this.d);
            h.a(sb, this.c);
            if (this.e && !z2) {
                long j = this.a;
                if (j != 0) {
                    h.b(sb, "up", String.valueOf(j));
                }
                if (z3) {
                    h.b(sb, TombstoneParser.keyProcessId, String.format(Locale.US, "%d", Integer.valueOf(Process.myPid())));
                } else if (this.b != 0) {
                    h.b(sb, TombstoneParser.keyProcessId, String.format(Locale.US, "%d", Integer.valueOf(this.b)));
                }
            }
            sb.append("\n");
            return sb.toString();
        }

        final boolean c(String str) {
            if (g.a(str)) {
                return false;
            }
            String str2 = null;
            long j = 0;
            HashMap hashMap = new HashMap();
            Map c = h.c(str);
            int i = 0;
            for (String str3 : c.keySet()) {
                String str4 = (String) c.get(str3);
                if (str3.equals("lt")) {
                    str2 = str4;
                } else if (this.e && str3.equals("up")) {
                    j = g.c(str4);
                } else if (!this.e || !str3.equals(TombstoneParser.keyProcessId)) {
                    hashMap.put(str3, str4);
                } else {
                    i = (int) g.c(str4);
                }
            }
            String str5 = this.d;
            if (!(str5 == null || str5.equals(str2))) {
                return false;
            }
            this.a = j;
            this.b = i;
            this.d = str2;
            this.c = hashMap;
            return true;
        }
    }

    public static void b(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append("=");
        sb.append(str2);
        sb.append("`");
    }

    public static void a(String str) {
        synchronized (b) {
            File file = new File(l());
            a aVar = new a("pv", true, true);
            String c2 = g.c(file);
            if (!g.a(c2)) {
                aVar.c(c2);
            }
            aVar.a(str, 1L);
            aVar.a("aujv", 1L);
            g.a(file, aVar.a(false, false, false));
        }
    }

    public static void a() {
        a(0, b.E() ? 700000L : 70000L);
    }

    public static void b() {
        a(2, 0L);
    }

    public static void c() {
        a(3, 0L);
    }

    private static void a(int i2, long j2) {
        if (b.C()) {
            f.a(0, new e(302, new Object[]{Integer.valueOf(i2)}), j2);
        }
    }

    public static void d() {
        b(2, (long) SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        a(1, 70000L);
    }

    public static boolean a(String str, String str2) {
        try {
            String str3 = "c_" + str.replaceAll("[^0-9a-zA-Z-_]", Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            String replaceAll = g.a(str2) ? "" : str2.replaceAll("[`=]", Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            synchronized (c) {
                if (c.get(str3) == null) {
                    if (d >= 20) {
                        return false;
                    }
                    d++;
                }
                c.put(str3, replaceAll);
                return true;
            }
        } catch (Throwable th) {
            g.a(th);
            return false;
        }
    }

    private static void a(a aVar) {
        synchronized (c) {
            for (String str : c.keySet()) {
                aVar.a(str, c.get(str));
            }
        }
    }

    public static boolean e() {
        return j;
    }

    private static void b(int i2, long j2) {
        if (g.O()) {
            f.a(1, new e(301, new Object[]{Integer.valueOf(i2)}), j2);
        }
    }

    public static void f() {
        b(1, (long) SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public static void g() {
        b(3, 0L);
    }

    public static void h() {
        b(4, 0L);
    }

    public static void a(boolean z) {
        a(1, z);
    }

    public static boolean a(boolean z, String str) {
        if (!b.d || z || !JNIBridge.nativeIsCrashing()) {
            return false;
        }
        a.b("crashsdk", "Native is crashing, skip stat for " + str);
        return true;
    }

    private static void a(int i2, boolean z) {
        if (!a(z, "crash rate")) {
            String str = g.U() + "cr.wa";
            b.a(b, str, new e(351, new Object[]{str, Integer.valueOf(i2)}));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x006a, code lost:
        if (r3 == false) goto L_0x006c;
     */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00af A[Catch: all -> 0x00cb, TryCatch #0 {, blocks: (B:16:0x001d, B:18:0x0028, B:20:0x002c, B:21:0x002e, B:23:0x0038, B:25:0x0040, B:27:0x005a, B:29:0x005f, B:36:0x0070, B:37:0x0077, B:42:0x0083, B:43:0x008b, B:45:0x0097, B:48:0x00a5, B:50:0x00af, B:51:0x00c2, B:52:0x00c9), top: B:66:0x001d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(int r9, java.lang.Object[] r10) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.h.a(int, java.lang.Object[]):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean b(int r7, java.lang.Object[] r8) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.h.b(int, java.lang.Object[]):boolean");
    }

    private static StringBuilder a(Iterable<a> iterable, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        boolean z3 = true;
        for (a aVar : iterable) {
            if (z3) {
                sb.append(aVar.a(z, z, z2));
                z3 = false;
            } else {
                sb.append(aVar.a(false, z, z2));
            }
        }
        return sb;
    }

    public static void a(String str, int i2, int i3) {
        if (g.O()) {
            synchronized (f) {
                a aVar = e.get(str);
                if (aVar == null) {
                    aVar = new a("cst", false, true);
                    e.put(str, aVar);
                    a(aVar);
                }
                synchronized (h) {
                    if (h.size() == 0) {
                        a(100, "pv");
                        a(102, "hpv");
                        a(1, "all");
                        a(2, "afg");
                        a(101, "abg");
                        a(3, "jfg");
                        a(4, "jbg");
                        a(7, "nfg");
                        a(8, "nbg");
                        a(27, "nafg");
                        a(28, "nabg");
                        a(9, "nho");
                        a(10, "uar");
                        a(29, "ulm");
                        a(30, "ukt");
                        a(31, "uet");
                        a(32, "urs");
                        a(11, "ufg");
                        a(12, "ubg");
                        a(40, "anf");
                        a(41, "anb");
                        a(42, "ancf");
                        a(43, "ancb");
                        a(13, "lup");
                        a(14, "luf");
                        a(15, "lef");
                        a(200, "ltf");
                        a(16, "laf");
                        a(22, "lac");
                        a(23, "lau");
                        a(17, "llf");
                        a(18, "lul");
                        a(19, "lub");
                        a(20, "luc");
                        a(21, "luu");
                        a(24, "lzc");
                        a(201, "lec");
                        a(25, "lrc");
                        a(26, "lss");
                    }
                }
                String str2 = h.get(i2);
                if (str2 == null) {
                    a.a("crashsdk", "map key is not set with: " + i2, null);
                }
                aVar.a("prc", str);
                if (str2 != null) {
                    aVar.a(str2, String.valueOf(i3));
                }
            }
        }
    }

    public static void i() {
        if (g.O()) {
            f.a(1, new e(303));
        }
    }

    public static void b(boolean z) {
        if (!a(z, "crash detail upload")) {
            String str = g.U() + "dt.wa";
            b.a(f, str, new e(352, new Object[]{str}));
            String m = m();
            b.a(g, m, new e(354, new Object[]{m}));
        }
    }

    private static boolean d(String str) {
        File file = new File(str);
        Iterator<a> it = a(file, "cst", 30).iterator();
        while (it.hasNext()) {
            a next = it.next();
            String a2 = next.a("prc");
            if (!g.a(a2)) {
                a aVar = e.get(a2);
                if (aVar != null) {
                    aVar.a(next);
                } else {
                    e.put(a2, next);
                }
            }
        }
        boolean b2 = b(e.p(), a((Iterable<a>) e.values(), true, false).toString());
        g.b(file);
        if (b2 || g.a(file, a((Iterable<a>) e.values(), false, true).toString())) {
            e.clear();
        }
        return true;
    }

    public static boolean a(String str, String str2, boolean z, boolean z2) {
        if (!g.O()) {
            return false;
        }
        return b.a(g, m(), new e(353, new Object[]{str, str2, Boolean.valueOf(z), Boolean.valueOf(z2)}));
    }

    private static boolean b(String str, String str2, boolean z, boolean z2) {
        a aVar;
        File file = new File(m());
        ArrayList<a> a2 = a(file, "cst", 30);
        String str3 = str + str2;
        Iterator<a> it = a2.iterator();
        while (true) {
            if (!it.hasNext()) {
                aVar = null;
                break;
            }
            aVar = it.next();
            if (str3.equals(aVar.b("prc") + aVar.b("typ"))) {
                break;
            }
        }
        if (aVar == null) {
            aVar = new a("cst", false, true);
            aVar.a("prc", str);
            aVar.a("typ", str2);
            a(aVar);
            a2.add(aVar);
        }
        aVar.a(VoicePrintRegisterController.PARAMS_CNT, 1L);
        if (z) {
            aVar.a("lim", 1L);
        }
        if (z2) {
            aVar.a("syu", 1L);
        }
        return g.a(file, a((Iterable<a>) a2, false, false).toString());
    }

    private static void a(int i2, String str) {
        h.put(i2, str);
    }

    private static ArrayList<a> a(File file, String str, int i2) {
        ArrayList<String> a2 = g.a(file, i2);
        ArrayList<a> arrayList = new ArrayList<>();
        Iterator<String> it = a2.iterator();
        while (it.hasNext()) {
            a aVar = new a(str, false, false);
            if (aVar.c(it.next())) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    private static String l() {
        return g.U() + "pv.wa";
    }

    private static String m() {
        return g.U() + "cdt.wa";
    }

    private static boolean b(String str, String str2) {
        boolean z;
        byte[] bArr;
        String str3;
        byte[] a2;
        if (g.a(str2)) {
            return true;
        }
        byte[] bytes = str2.getBytes();
        try {
            byte[] bArr2 = new byte[16];
            c.a(bArr2, 0, c.a());
            c.a(bArr2, 4, j());
            c.a(bArr2, 8, com.uc.crashsdk.a.f());
            c.a(bArr2, 12, d.c());
            bArr = c.a(bytes, bArr2);
            if (bArr != null) {
                z = true;
            } else {
                bArr = bytes;
                z = false;
            }
        } catch (Throwable th) {
            g.a(th);
            bArr = bytes;
            z = false;
        }
        if (str == null) {
            str = "unknown";
        }
        String str4 = "28ef1713347d";
        if (g.P()) {
            str4 = "4ea4e41a3993";
        }
        String valueOf = String.valueOf(System.currentTimeMillis());
        String d2 = g.d(str4 + str + valueOf + "AppChk#2014");
        if (d2 == null) {
            str3 = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(o());
            sb.append("?chk=");
            sb.append(d2.substring(d2.length() - 8, d2.length()));
            sb.append("&vno=");
            sb.append(valueOf);
            sb.append("&uuid=");
            sb.append(str);
            sb.append("&app=");
            sb.append(str4);
            if (z) {
                sb.append("&enc=aes");
            }
            str3 = sb.toString();
        }
        return (str3 == null || (a2 = c.a(str3, bArr)) == null || !new String(a2).contains("retcode=0")) ? false : true;
    }

    public static long n() {
        Iterator<String> it = g.a(new File("/proc/meminfo"), 2).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (next.contains("MemTotal:")) {
                try {
                    return Long.parseLong(next.replaceAll("\\D+", ""));
                } catch (NumberFormatException e2) {
                    g.a(e2);
                }
            }
        }
        return 0L;
    }

    public static byte[] j() {
        return new byte[]{Byte.MAX_VALUE, 100, 110, Ascii.US};
    }

    public static void b(String str) {
        synchronized (k) {
            l = str;
            String h2 = b.h();
            b.a(h2, l + "\n");
        }
    }

    private static String o() {
        if (g.a(l)) {
            synchronized (k) {
                String str = "https://errlog.umeng.com/api/crashsdk/logcollect";
                if (g.P()) {
                    str = "https://errlogos.umeng.com/api/crashsdk/logcollect";
                }
                l = g.a(b.h(), str, true);
            }
        }
        return l;
    }
}
