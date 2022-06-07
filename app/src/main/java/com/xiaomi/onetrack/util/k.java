package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import android.util.LruCache;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/* loaded from: classes4.dex */
public class k {
    private static final String a = "FileUtil";
    private static final String b = "onetrack";
    private static final String c = "tombstone";
    private static LruCache<String, a> d = new l(1048576);

    /* loaded from: classes4.dex */
    public static class a {
        String a;

        private a() {
        }

        /* synthetic */ a(l lVar) {
            this();
        }
    }

    public static void a(String str, String str2) {
        Throwable th;
        Exception e;
        BufferedWriter bufferedWriter;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    a aVar = new a(null);
                    aVar.a = str2;
                    d.put(str, aVar);
                    String b2 = b();
                    File file = new File(b2);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(b2, str);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    bufferedWriter = new BufferedWriter(new FileWriter(file2), 1024);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedWriter.write(str2);
                bufferedWriter.flush();
                m.a(bufferedWriter);
            } catch (Exception e3) {
                e = e3;
                bufferedWriter2 = bufferedWriter;
                p.c(a, "put error:" + e.toString());
                m.a(bufferedWriter2);
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter2 = bufferedWriter;
                m.a(bufferedWriter2);
                throw th;
            }
        }
    }

    public static String a(String str) {
        Throwable th;
        Exception e;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        a aVar = d.get(str);
        if (aVar != null) {
            return aVar.a;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                File file = new File(b(), str);
                StringBuilder sb = new StringBuilder();
                if (file.exists()) {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        } catch (Exception e2) {
                            e = e2;
                            p.c(a, "get error:" + e.toString());
                            m.a(bufferedReader);
                            return "";
                        } catch (Throwable th2) {
                            th = th2;
                            m.a(bufferedReader);
                            throw th;
                        }
                    }
                } else {
                    bufferedReader = null;
                }
                String sb2 = sb.toString();
                a aVar2 = new a(null);
                aVar2.a = sb2;
                d.put(str, aVar2);
                m.a(bufferedReader);
                return sb2;
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static String b() {
        return c("onetrack");
    }

    public static String a() {
        return c(c);
    }

    private static String c(String str) {
        String str2 = com.xiaomi.onetrack.e.a.a().getFilesDir().getAbsolutePath() + File.separator + str;
        File file = new File(str2);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str2;
    }

    public static void b(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                d.remove(str);
                File file = new File(b(), str);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            p.c(a, "clear error:" + e.toString());
        }
    }

    public static String a(String str, int i) {
        Throwable th;
        BufferedReader bufferedReader;
        Exception e;
        try {
            File file = new File(str);
            StringBuilder sb = new StringBuilder();
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                do {
                    try {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                            sb.append("\n");
                        } catch (Exception e2) {
                            e = e2;
                            p.c(a, "get error:" + e.toString());
                            m.a(bufferedReader);
                            return null;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        m.a(bufferedReader);
                        throw th;
                    }
                } while (sb.length() <= i);
            } else {
                bufferedReader = null;
            }
            if (sb.length() > i) {
                String substring = sb.substring(0, i - 1);
                m.a(bufferedReader);
                return substring;
            }
            String sb2 = sb.toString();
            m.a(bufferedReader);
            return sb2;
        } catch (Exception e3) {
            e = e3;
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    public static void a(File file) {
        try {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            p.c(a, "failed to remove file: " + file.getName() + Constants.ACCEPT_TIME_SEPARATOR_SP + e.toString());
        }
    }
}
