package xcrash;

import android.content.Context;
import android.os.Build;
import android.os.FileObserver;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: AnrHandler.java */
/* loaded from: classes6.dex */
public class b {
    private static final b a = new b();
    private Context f;
    private int g;
    private String h;
    private String i;
    private String j;
    private String k;
    private boolean l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private ICrashCallback r;
    private final Date b = new Date();
    private final Pattern c = Pattern.compile("^-----\\spid\\s(\\d+)\\sat\\s(.*)\\s-----$");
    private final Pattern d = Pattern.compile("^Cmd\\sline:\\s+(.*)$");
    private final long e = 15000;
    private long s = 0;
    private FileObserver t = null;

    private b() {
    }

    public static b a() {
        return a;
    }

    public void a(Context context, int i, String str, String str2, String str3, String str4, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, ICrashCallback iCrashCallback) {
        if (Build.VERSION.SDK_INT < 21) {
            this.f = context;
            this.g = i;
            if (TextUtils.isEmpty(str)) {
                str = "unknown";
            }
            this.h = str;
            this.i = str2;
            this.j = str3;
            this.k = str4;
            this.l = z;
            this.m = i2;
            this.n = i3;
            this.o = i4;
            this.p = z2;
            this.q = z3;
            this.r = iCrashCallback;
            this.t = new FileObserver("/data/anr/", 8) { // from class: xcrash.b.1
                @Override // android.os.FileObserver
                public void onEvent(int i5, String str5) {
                    if (str5 != null) {
                        try {
                            String str6 = "/data/anr/" + str5;
                            if (str6.contains("trace")) {
                                b.this.a(str6);
                            }
                        } catch (Exception e) {
                            XCrash.d().e("xcrash", "AnrHandler fileObserver onEvent failed", e);
                        }
                    }
                }
            };
            try {
                this.t.startWatching();
            } catch (Exception e) {
                this.t = null;
                XCrash.d().e("xcrash", "AnrHandler fileObserver startWatching failed", e);
            }
        }
    }

    public void b() {
        FileObserver fileObserver = this.t;
        if (fileObserver != null) {
            try {
                try {
                    fileObserver.stopWatching();
                } catch (Exception e) {
                    XCrash.d().e("xcrash", "AnrHandler fileObserver stopWatching failed", e);
                }
            } finally {
                this.t = null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0121 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.b.a(java.lang.String):void");
    }

    private String a(Date date, String str) {
        return f.a(this.b, date, "anr", this.i, this.j) + "pid: " + this.g + "  >>> " + this.h + " <<<\n\n" + com.xiaomi.onetrack.util.b.e + "\n" + str + "\n" + com.xiaomi.onetrack.util.b.f + "\n\n";
    }

    private String a(String str, long j) {
        BufferedReader bufferedReader;
        Throwable th;
        Date parse;
        String group;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        StringBuilder sb = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(str));
            boolean z = false;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else if (!z && readLine.startsWith("----- pid ")) {
                        Matcher matcher = this.c.matcher(readLine);
                        if (matcher.find() && matcher.groupCount() == 2) {
                            String group2 = matcher.group(1);
                            String group3 = matcher.group(2);
                            if (!(group2 == null || group3 == null || this.g != Integer.parseInt(group2) || (parse = simpleDateFormat.parse(group3)) == null || Math.abs(parse.getTime() - j) > 15000)) {
                                String readLine2 = bufferedReader.readLine();
                                if (readLine2 == null) {
                                    break;
                                }
                                Matcher matcher2 = this.d.matcher(readLine2);
                                if (matcher2.find() && matcher2.groupCount() == 1 && (group = matcher2.group(1)) != null && group.equals(this.h)) {
                                    sb.append(readLine2);
                                    sb.append('\n');
                                    sb.append("Mode: Watching /data/anr/*\n");
                                    z = true;
                                }
                            }
                        }
                    } else if (!z) {
                        continue;
                    } else if (readLine.startsWith("----- end ")) {
                        break;
                    } else {
                        sb.append(readLine);
                        sb.append('\n');
                    }
                } catch (Exception unused) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused3) {
                        }
                    }
                    throw th;
                }
            }
            String sb2 = sb.toString();
            try {
                bufferedReader.close();
            } catch (Exception unused4) {
            }
            return sb2;
        } catch (Exception unused5) {
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }
}
