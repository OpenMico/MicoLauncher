package xcrash;

import android.content.Context;

/* loaded from: classes6.dex */
public final class XCrash {
    private static boolean a = false;
    private static String b;
    private static String c;
    private static String d;
    private static ILogger e = new c();

    private XCrash() {
    }

    public static int init(Context context) {
        return init(context, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00de A[Catch: all -> 0x01f1, TryCatch #0 {, blocks: (B:4:0x0003, B:10:0x000b, B:15:0x0012, B:20:0x001e, B:22:0x0027, B:24:0x002b, B:25:0x002f, B:27:0x003d, B:28:0x0041, B:30:0x0049, B:31:0x004f, B:33:0x005b, B:34:0x0072, B:36:0x007f, B:40:0x0086, B:42:0x008e, B:44:0x0094, B:46:0x009a, B:48:0x009d, B:50:0x00c4, B:52:0x00c8, B:54:0x00cc, B:56:0x00d0, B:57:0x00da, B:59:0x00de, B:61:0x0120, B:63:0x0126, B:65:0x012a, B:67:0x0151, B:69:0x0155, B:71:0x0159, B:75:0x0164, B:77:0x019a, B:82:0x01a9, B:83:0x01e8), top: B:88:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019a A[Catch: all -> 0x01f1, TryCatch #0 {, blocks: (B:4:0x0003, B:10:0x000b, B:15:0x0012, B:20:0x001e, B:22:0x0027, B:24:0x002b, B:25:0x002f, B:27:0x003d, B:28:0x0041, B:30:0x0049, B:31:0x004f, B:33:0x005b, B:34:0x0072, B:36:0x007f, B:40:0x0086, B:42:0x008e, B:44:0x0094, B:46:0x009a, B:48:0x009d, B:50:0x00c4, B:52:0x00c8, B:54:0x00cc, B:56:0x00d0, B:57:0x00da, B:59:0x00de, B:61:0x0120, B:63:0x0126, B:65:0x012a, B:67:0x0151, B:69:0x0155, B:71:0x0159, B:75:0x0164, B:77:0x019a, B:82:0x01a9, B:83:0x01e8), top: B:88:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized int init(android.content.Context r34, xcrash.XCrash.InitParameters r35) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.XCrash.init(android.content.Context, xcrash.XCrash$InitParameters):int");
    }

    /* loaded from: classes6.dex */
    public static class InitParameters {
        String a = null;
        String b = null;
        int c = 5000;
        ILogger d = null;
        ILibLoader e = null;
        int f = 0;
        int g = 128;
        boolean h = true;
        boolean i = true;
        int j = 10;
        int k = 50;
        int l = 50;
        int m = 200;
        boolean n = true;
        boolean o = true;
        boolean p = true;
        int q = 0;
        String[] r = null;
        ICrashCallback s = null;
        boolean t = true;
        boolean u = true;
        int v = 10;
        int w = 50;
        int x = 50;
        int y = 200;
        boolean z = true;
        boolean A = true;
        boolean B = true;
        boolean C = true;
        boolean D = true;
        int E = 0;
        String[] F = null;
        ICrashCallback G = null;
        boolean H = true;
        boolean I = true;
        boolean J = true;
        int K = 10;
        int L = 50;
        int M = 50;
        int N = 200;
        boolean O = true;
        boolean P = true;
        ICrashCallback Q = null;

        public InitParameters setAppVersion(String str) {
            this.a = str;
            return this;
        }

        public InitParameters setLogDir(String str) {
            this.b = str;
            return this;
        }

        public InitParameters setLogFileMaintainDelayMs(int i) {
            if (i < 0) {
                i = 0;
            }
            this.c = i;
            return this;
        }

        public InitParameters setLogger(ILogger iLogger) {
            this.d = iLogger;
            return this;
        }

        public InitParameters setLibLoader(ILibLoader iLibLoader) {
            this.e = iLibLoader;
            return this;
        }

        public InitParameters setPlaceholderCountMax(int i) {
            if (i < 0) {
                i = 0;
            }
            this.f = i;
            return this;
        }

        public InitParameters setPlaceholderSizeKb(int i) {
            if (i < 0) {
                i = 0;
            }
            this.g = i;
            return this;
        }

        public InitParameters enableJavaCrashHandler() {
            this.h = true;
            return this;
        }

        public InitParameters disableJavaCrashHandler() {
            this.h = false;
            return this;
        }

        public InitParameters setJavaRethrow(boolean z) {
            this.i = z;
            return this;
        }

        public InitParameters setJavaLogCountMax(int i) {
            if (i < 1) {
                i = 1;
            }
            this.j = i;
            return this;
        }

        public InitParameters setJavaLogcatSystemLines(int i) {
            this.k = i;
            return this;
        }

        public InitParameters setJavaLogcatEventsLines(int i) {
            this.l = i;
            return this;
        }

        public InitParameters setJavaLogcatMainLines(int i) {
            this.m = i;
            return this;
        }

        public InitParameters setJavaDumpFds(boolean z) {
            this.n = z;
            return this;
        }

        public InitParameters setJavaDumpNetworkInfo(boolean z) {
            this.o = z;
            return this;
        }

        public InitParameters setJavaDumpAllThreads(boolean z) {
            this.p = z;
            return this;
        }

        public InitParameters setJavaDumpAllThreadsCountMax(int i) {
            if (i < 0) {
                i = 0;
            }
            this.q = i;
            return this;
        }

        public InitParameters setJavaDumpAllThreadsWhiteList(String[] strArr) {
            this.r = strArr;
            return this;
        }

        public InitParameters setJavaCallback(ICrashCallback iCrashCallback) {
            this.s = iCrashCallback;
            return this;
        }

        public InitParameters enableNativeCrashHandler() {
            this.t = true;
            return this;
        }

        public InitParameters disableNativeCrashHandler() {
            this.t = false;
            return this;
        }

        public InitParameters setNativeRethrow(boolean z) {
            this.u = z;
            return this;
        }

        public InitParameters setNativeLogCountMax(int i) {
            if (i < 1) {
                i = 1;
            }
            this.v = i;
            return this;
        }

        public InitParameters setNativeLogcatSystemLines(int i) {
            this.w = i;
            return this;
        }

        public InitParameters setNativeLogcatEventsLines(int i) {
            this.x = i;
            return this;
        }

        public InitParameters setNativeLogcatMainLines(int i) {
            this.y = i;
            return this;
        }

        public InitParameters setNativeDumpElfHash(boolean z) {
            this.z = z;
            return this;
        }

        public InitParameters setNativeDumpMap(boolean z) {
            this.A = z;
            return this;
        }

        public InitParameters setNativeDumpFds(boolean z) {
            this.B = z;
            return this;
        }

        public InitParameters setNativeDumpNetwork(boolean z) {
            this.C = z;
            return this;
        }

        public InitParameters setNativeDumpAllThreads(boolean z) {
            this.D = z;
            return this;
        }

        public InitParameters setNativeDumpAllThreadsCountMax(int i) {
            if (i < 0) {
                i = 0;
            }
            this.E = i;
            return this;
        }

        public InitParameters setNativeDumpAllThreadsWhiteList(String[] strArr) {
            this.F = strArr;
            return this;
        }

        public InitParameters setNativeCallback(ICrashCallback iCrashCallback) {
            this.G = iCrashCallback;
            return this;
        }

        public InitParameters enableAnrCrashHandler() {
            this.H = true;
            return this;
        }

        public InitParameters disableAnrCrashHandler() {
            this.H = false;
            return this;
        }

        public InitParameters setAnrRethrow(boolean z) {
            this.I = z;
            return this;
        }

        public InitParameters setAnrCheckProcessState(boolean z) {
            this.J = z;
            return this;
        }

        public InitParameters setAnrLogCountMax(int i) {
            if (i < 1) {
                i = 1;
            }
            this.K = i;
            return this;
        }

        public InitParameters setAnrLogcatSystemLines(int i) {
            this.L = i;
            return this;
        }

        public InitParameters setAnrLogcatEventsLines(int i) {
            this.M = i;
            return this;
        }

        public InitParameters setAnrLogcatMainLines(int i) {
            this.N = i;
            return this;
        }

        public InitParameters setAnrDumpFds(boolean z) {
            this.O = z;
            return this;
        }

        public InitParameters setAnrDumpNetwork(boolean z) {
            this.P = z;
            return this;
        }

        public InitParameters setAnrCallback(ICrashCallback iCrashCallback) {
            this.Q = iCrashCallback;
            return this;
        }
    }

    public static String a() {
        return b;
    }

    public static String b() {
        return c;
    }

    public static String c() {
        return d;
    }

    public static ILogger d() {
        return e;
    }

    public static void testJavaCrash(boolean z) throws RuntimeException {
        if (z) {
            Thread thread = new Thread() { // from class: xcrash.XCrash.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    throw new RuntimeException("test java exception");
                }
            };
            thread.setName("xcrash_test_java_thread");
            thread.start();
            return;
        }
        throw new RuntimeException("test java exception");
    }

    public static void testNativeCrash(boolean z) {
        NativeHandler.a().a(z);
    }
}
