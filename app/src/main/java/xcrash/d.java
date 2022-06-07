package xcrash;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: FileManager.java */
/* loaded from: classes6.dex */
public class d {
    private static final d m = new d();
    private String a = "placeholder";
    private String b = ".clean.xcrash";
    private String c = ".dirty.xcrash";
    private String d = null;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 1;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private AtomicInteger l = new AtomicInteger();

    private d() {
    }

    public static d a() {
        return m;
    }

    public void a(String str, int i, int i2, int i3, int i4, int i5, int i6) {
        File[] listFiles;
        this.d = str;
        this.e = i;
        this.f = i2;
        this.g = i3;
        this.i = i4;
        this.j = i5;
        this.k = i6;
        try {
            File file = new File(str);
            if (file.exists() && file.isDirectory() && (listFiles = file.listFiles()) != null) {
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                int i10 = 0;
                int i11 = 0;
                int i12 = 0;
                for (File file2 : listFiles) {
                    if (file2.isFile()) {
                        String name = file2.getName();
                        if (!name.startsWith("tombstone_")) {
                            if (name.startsWith(this.a + "_")) {
                                if (name.endsWith(this.b)) {
                                    i11++;
                                } else if (name.endsWith(this.c)) {
                                    i12++;
                                }
                            }
                        } else if (name.endsWith(".java.xcrash")) {
                            i7++;
                        } else if (name.endsWith(".native.xcrash")) {
                            i8++;
                        } else if (name.endsWith(".anr.xcrash")) {
                            i9++;
                        } else if (name.endsWith(".trace.xcrash")) {
                            i10++;
                        }
                    }
                }
                if (i7 > this.e || i8 > this.f || i9 > this.g || i10 > this.h || i11 != this.i || i12 != 0) {
                    if (i7 <= this.e + 10 && i8 <= this.f + 10 && i9 <= this.g + 10 && i10 <= this.h + 10 && i11 <= this.i + 10 && i12 <= 10) {
                        if (i7 > this.e || i8 > this.f || i9 > this.g || i10 > this.h || i11 > this.i || i12 > 0) {
                            this.k = 0;
                            return;
                        }
                        return;
                    }
                    d();
                    this.k = -1;
                    return;
                }
                this.k = -1;
            }
        } catch (Exception e) {
            XCrash.d().e("xcrash", "FileManager init failed", e);
        }
    }

    public void b() {
        int i;
        if (this.d != null && (i = this.k) >= 0) {
            try {
                if (i == 0) {
                    new Thread(new Runnable() { // from class: xcrash.d.1
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.d();
                        }
                    }, "xcrash_file_mgr").start();
                } else {
                    new Timer("xcrash_file_mgr").schedule(new TimerTask() { // from class: xcrash.d.3
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            d.this.d();
                        }
                    }, this.k);
                }
            } catch (Exception e) {
                XCrash.d().e("xcrash", "FileManager maintain start failed", e);
            }
        }
    }

    public boolean c() {
        if (!f.a(this.d)) {
            return false;
        }
        try {
            return a(new File(this.d), ".anr.xcrash", this.g);
        } catch (Exception e) {
            XCrash.d().e("xcrash", "FileManager maintainAnr failed", e);
            return false;
        }
    }

    public File a(String str) {
        String str2 = this.d;
        if (str2 == null || !f.a(str2)) {
            return null;
        }
        File file = new File(str);
        File[] listFiles = new File(this.d).listFiles(new FilenameFilter() { // from class: xcrash.d.4
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str3) {
                StringBuilder sb = new StringBuilder();
                sb.append(d.this.a);
                sb.append("_");
                return str3.startsWith(sb.toString()) && str3.endsWith(d.this.b);
            }
        });
        if (listFiles != null) {
            for (int length = listFiles.length; length > 0; length--) {
                File file2 = listFiles[length - 1];
                try {
                } catch (Exception e) {
                    XCrash.d().e("xcrash", "FileManager createLogFile by renameTo failed", e);
                }
                if (file2.renameTo(file)) {
                    return file;
                }
                file2.delete();
            }
        }
        try {
            if (file.createNewFile()) {
                return file;
            }
            XCrash.d().e("xcrash", "FileManager createLogFile by createNewFile failed, file already exists");
            return null;
        } catch (Exception e2) {
            XCrash.d().e("xcrash", "FileManager createLogFile by createNewFile failed", e2);
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x004f: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:21:0x004f
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    boolean a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x004f: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:21:0x004f
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r13v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public boolean a(File file) {
        if (file == null) {
            return false;
        }
        String str = this.d;
        if (str == null || this.i <= 0) {
            try {
                return file.delete();
            } catch (Exception unused) {
                return false;
            }
        } else {
            try {
                File[] listFiles = new File(str).listFiles(new FilenameFilter() { // from class: xcrash.d.5
                    @Override // java.io.FilenameFilter
                    public boolean accept(File file2, String str2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(d.this.a);
                        sb.append("_");
                        return str2.startsWith(sb.toString()) && str2.endsWith(d.this.b);
                    }
                });
                if (listFiles == null || listFiles.length < this.i) {
                    File file2 = new File(String.format(Locale.US, "%s/%s_%020d%s", this.d, this.a, Long.valueOf((new Date().getTime() * 1000) + e()), this.c));
                    if (file.renameTo(file2)) {
                        return d(file2);
                    }
                    try {
                        return file.delete();
                    } catch (Exception unused2) {
                        return false;
                    }
                } else {
                    try {
                        return file.delete();
                    } catch (Exception unused3) {
                        return false;
                    }
                }
            } catch (Exception e) {
                XCrash.d().e("xcrash", "FileManager recycleLogFile failed", e);
                try {
                    return file.delete();
                } catch (Exception unused4) {
                    return false;
                }
            }
        }
    }

    public void d() {
        if (f.a(this.d)) {
            File file = new File(this.d);
            try {
                b(file);
            } catch (Exception e) {
                XCrash.d().e("xcrash", "FileManager doMaintainTombstone failed", e);
            }
            try {
                c(file);
            } catch (Exception e2) {
                XCrash.d().e("xcrash", "FileManager doMaintainPlaceholder failed", e2);
            }
        }
    }

    private void b(File file) {
        a(file, ".native.xcrash", this.f);
        a(file, ".java.xcrash", this.e);
        a(file, ".anr.xcrash", this.g);
        a(file, ".trace.xcrash", this.h);
    }

    private boolean a(File file, final String str, int i) {
        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: xcrash.d.6
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str2) {
                return str2.startsWith("tombstone_") && str2.endsWith(str);
            }
        });
        if (listFiles == null || listFiles.length <= i) {
            return true;
        }
        if (i > 0) {
            Arrays.sort(listFiles, new Comparator<File>() { // from class: xcrash.d.7
                /* renamed from: a */
                public int compare(File file2, File file3) {
                    return file2.getName().compareTo(file3.getName());
                }
            });
        }
        boolean z = true;
        for (int i2 = 0; i2 < listFiles.length - i; i2++) {
            if (!a(listFiles[i2])) {
                z = false;
            }
        }
        return z;
    }

    private void c(File file) {
        File[] listFiles;
        int i;
        int i2;
        File[] listFiles2 = file.listFiles(new FilenameFilter() { // from class: xcrash.d.8
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                StringBuilder sb = new StringBuilder();
                sb.append(d.this.a);
                sb.append("_");
                return str.startsWith(sb.toString()) && str.endsWith(d.this.b);
            }
        });
        if (!(listFiles2 == null || (listFiles = file.listFiles(new FilenameFilter() { // from class: xcrash.d.9
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                StringBuilder sb = new StringBuilder();
                sb.append(d.this.a);
                sb.append("_");
                return str.startsWith(sb.toString()) && str.endsWith(d.this.c);
            }
        })) == null)) {
            int length = listFiles2.length;
            int length2 = listFiles.length;
            char c = 0;
            int i3 = 0;
            while (true) {
                if (length >= this.i) {
                    i = i3;
                    break;
                }
                if (length2 > 0) {
                    if (d(listFiles[length2 - 1])) {
                        length++;
                    }
                    length2--;
                    i2 = i3;
                } else {
                    try {
                        Locale locale = Locale.US;
                        Object[] objArr = new Object[4];
                        objArr[c] = this.d;
                        objArr[1] = this.a;
                        i2 = i3;
                        try {
                            objArr[2] = Long.valueOf((new Date().getTime() * 1000) + e());
                            objArr[3] = this.c;
                            File file2 = new File(String.format(locale, "%s/%s_%020d%s", objArr));
                            if (file2.createNewFile() && d(file2)) {
                                length++;
                            }
                        } catch (Exception unused) {
                        }
                    } catch (Exception unused2) {
                        i2 = i3;
                    }
                }
                i3 = i2 + 1;
                if (i3 > this.i * 2) {
                    i = i3;
                    break;
                }
                c = 0;
            }
            if (i > 0) {
                listFiles2 = file.listFiles(new FilenameFilter() { // from class: xcrash.d.10
                    @Override // java.io.FilenameFilter
                    public boolean accept(File file3, String str) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(d.this.a);
                        sb.append("_");
                        return str.startsWith(sb.toString()) && str.endsWith(d.this.b);
                    }
                });
                listFiles = file.listFiles(new FilenameFilter() { // from class: xcrash.d.2
                    @Override // java.io.FilenameFilter
                    public boolean accept(File file3, String str) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(d.this.a);
                        sb.append("_");
                        return str.startsWith(sb.toString()) && str.endsWith(d.this.c);
                    }
                });
            }
            if (listFiles2 != null && listFiles2.length > this.i) {
                for (int i4 = 0; i4 < listFiles2.length - this.i; i4++) {
                    listFiles2[i4].delete();
                }
            }
            if (listFiles != null) {
                for (File file3 : listFiles) {
                    file3.delete();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00b0 A[Catch: Exception -> 0x00b3, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x00b3, blocks: (B:21:0x0090, B:33:0x00b0), top: B:54:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean d(java.io.File r17) {
        /*
            r16 = this;
            r1 = r16
            r0 = 1024(0x400, float:1.435E-42)
            r2 = 0
            r3 = 0
            byte[] r4 = new byte[r0]     // Catch: Exception -> 0x00a0, all -> 0x009d
            java.util.Arrays.fill(r4, r2)     // Catch: Exception -> 0x00a0, all -> 0x009d
            int r5 = r1.j     // Catch: Exception -> 0x00a0, all -> 0x009d
            long r5 = (long) r5     // Catch: Exception -> 0x00a0, all -> 0x009d
            long r7 = r17.length()     // Catch: Exception -> 0x00a0, all -> 0x009d
            int r9 = r1.j     // Catch: Exception -> 0x00a0, all -> 0x009d
            int r9 = r9 * r0
            long r9 = (long) r9     // Catch: Exception -> 0x00a0, all -> 0x009d
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            r9 = 0
            r11 = 1024(0x400, double:5.06E-321)
            if (r0 <= 0) goto L_0x0029
            long r5 = r7 / r11
            long r13 = r7 % r11
            int r0 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
            if (r0 == 0) goto L_0x0029
            r13 = 1
            long r5 = r5 + r13
        L_0x0029:
            java.io.FileOutputStream r13 = new java.io.FileOutputStream     // Catch: Exception -> 0x00a0, all -> 0x009d
            java.io.File r0 = r17.getAbsoluteFile()     // Catch: Exception -> 0x00a0, all -> 0x009d
            r13.<init>(r0, r2)     // Catch: Exception -> 0x00a0, all -> 0x009d
            r0 = r2
        L_0x0033:
            long r14 = (long) r0
            int r3 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r3 >= 0) goto L_0x0050
            int r0 = r0 + 1
            long r14 = (long) r0
            int r3 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x004c
            long r14 = r7 % r11
            int r3 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1))
            if (r3 == 0) goto L_0x004c
            long r14 = r7 % r11
            int r3 = (int) r14     // Catch: Exception -> 0x0098, all -> 0x0096
            r13.write(r4, r2, r3)     // Catch: Exception -> 0x0098, all -> 0x0096
            goto L_0x0033
        L_0x004c:
            r13.write(r4)     // Catch: Exception -> 0x0098, all -> 0x0096
            goto L_0x0033
        L_0x0050:
            r13.flush()     // Catch: Exception -> 0x0098, all -> 0x0096
            java.util.Locale r0 = java.util.Locale.US     // Catch: Exception -> 0x0098, all -> 0x0096
            java.lang.String r3 = "%s/%s_%020d%s"
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: Exception -> 0x0098, all -> 0x0096
            java.lang.String r5 = r1.d     // Catch: Exception -> 0x0098, all -> 0x0096
            r4[r2] = r5     // Catch: Exception -> 0x0098, all -> 0x0096
            java.lang.String r5 = r1.a     // Catch: Exception -> 0x0098, all -> 0x0096
            r6 = 1
            r4[r6] = r5     // Catch: Exception -> 0x0098, all -> 0x0096
            r5 = 2
            java.util.Date r6 = new java.util.Date     // Catch: Exception -> 0x0098, all -> 0x0096
            r6.<init>()     // Catch: Exception -> 0x0098, all -> 0x0096
            long r6 = r6.getTime()     // Catch: Exception -> 0x0098, all -> 0x0096
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 * r8
            int r8 = r16.e()     // Catch: Exception -> 0x0098, all -> 0x0096
            long r8 = (long) r8     // Catch: Exception -> 0x0098, all -> 0x0096
            long r6 = r6 + r8
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch: Exception -> 0x0098, all -> 0x0096
            r4[r5] = r6     // Catch: Exception -> 0x0098, all -> 0x0096
            r5 = 3
            java.lang.String r6 = r1.b     // Catch: Exception -> 0x0098, all -> 0x0096
            r4[r5] = r6     // Catch: Exception -> 0x0098, all -> 0x0096
            java.lang.String r0 = java.lang.String.format(r0, r3, r4)     // Catch: Exception -> 0x0098, all -> 0x0096
            java.io.File r3 = new java.io.File     // Catch: Exception -> 0x0098, all -> 0x0096
            r3.<init>(r0)     // Catch: Exception -> 0x0098, all -> 0x0096
            r4 = r17
            boolean r2 = r4.renameTo(r3)     // Catch: Exception -> 0x0094, all -> 0x0096
            r13.close()     // Catch: Exception -> 0x00b3
            goto L_0x00b3
        L_0x0094:
            r0 = move-exception
            goto L_0x009b
        L_0x0096:
            r0 = move-exception
            goto L_0x00b9
        L_0x0098:
            r0 = move-exception
            r4 = r17
        L_0x009b:
            r3 = r13
            goto L_0x00a3
        L_0x009d:
            r0 = move-exception
            r13 = r3
            goto L_0x00b9
        L_0x00a0:
            r0 = move-exception
            r4 = r17
        L_0x00a3:
            xcrash.ILogger r5 = xcrash.XCrash.d()     // Catch: all -> 0x009d
            java.lang.String r6 = "xcrash"
            java.lang.String r7 = "FileManager cleanTheDirtyFile failed"
            r5.e(r6, r7, r0)     // Catch: all -> 0x009d
            if (r3 == 0) goto L_0x00b3
            r3.close()     // Catch: Exception -> 0x00b3
        L_0x00b3:
            if (r2 != 0) goto L_0x00b8
            r17.delete()     // Catch: Exception -> 0x00b8
        L_0x00b8:
            return r2
        L_0x00b9:
            if (r13 == 0) goto L_0x00be
            r13.close()     // Catch: Exception -> 0x00be
        L_0x00be:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.d.d(java.io.File):boolean");
    }

    private int e() {
        int incrementAndGet = this.l.incrementAndGet();
        if (incrementAndGet >= 999) {
            this.l.set(0);
        }
        return incrementAndGet;
    }
}
