package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.z;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
public class a {
    private static volatile a j;
    private final Object a = new Object();
    private final Object b = new Object();
    private final String c = "mipush_region";
    private final String d = "mipush_country_code";
    private final String e = "mipush_region.lock";
    private final String f = "mipush_country_code.lock";
    private volatile String g;
    private volatile String h;
    private Context i;

    public a(Context context) {
        this.i = context;
    }

    public static a a(Context context) {
        if (j == null) {
            synchronized (a.class) {
                if (j == null) {
                    j = new a(context);
                }
            }
        }
        return j;
    }

    private String a(Context context, String str, String str2, Object obj) {
        Throwable th;
        RandomAccessFile randomAccessFile;
        Exception e;
        FileLock fileLock;
        File file = new File(context.getFilesDir(), str);
        FileLock fileLock2 = null;
        if (!file.exists()) {
            b.m149a("No ready file to get data from " + str);
            return null;
        }
        synchronized (obj) {
            try {
                File file2 = new File(context.getFilesDir(), str2);
                z.m1176a(file2);
                randomAccessFile = new RandomAccessFile(file2, "rw");
            } catch (Exception e2) {
                e = e2;
                randomAccessFile = null;
                fileLock = null;
            } catch (Throwable th2) {
                th = th2;
                randomAccessFile = null;
            }
            try {
                fileLock = randomAccessFile.getChannel().lock();
                try {
                    try {
                        String a = z.a(file);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e3) {
                                b.a(e3);
                            }
                        }
                        z.a(randomAccessFile);
                        return a;
                    } catch (Exception e4) {
                        e = e4;
                        b.a(e);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e5) {
                                b.a(e5);
                            }
                        }
                        z.a(randomAccessFile);
                        return null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileLock2 = fileLock;
                    if (fileLock2 != null && fileLock2.isValid()) {
                        try {
                            fileLock2.release();
                        } catch (IOException e6) {
                            b.a(e6);
                        }
                    }
                    z.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
                fileLock = null;
            } catch (Throwable th4) {
                th = th4;
                if (fileLock2 != null) {
                    fileLock2.release();
                }
                z.a(randomAccessFile);
                throw th;
            }
        }
    }

    private void a(Context context, String str, String str2, String str3, Object obj) {
        RandomAccessFile randomAccessFile;
        Throwable th;
        Exception e;
        synchronized (obj) {
            FileLock fileLock = null;
            try {
                File file = new File(context.getFilesDir(), str3);
                z.m1176a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    try {
                        fileLock = randomAccessFile.getChannel().lock();
                        z.a(new File(context.getFilesDir(), str2), str);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e2) {
                                b.a(e2);
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        b.a(e);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e4) {
                                b.a(e4);
                            }
                        }
                        z.a(randomAccessFile);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e5) {
                            b.a(e5);
                        }
                    }
                    z.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                randomAccessFile = null;
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                if (fileLock != null) {
                    fileLock.release();
                }
                z.a(randomAccessFile);
                throw th;
            }
            z.a(randomAccessFile);
        }
    }

    public String a() {
        if (TextUtils.isEmpty(this.g)) {
            this.g = a(this.i, "mipush_region", "mipush_region.lock", this.a);
        }
        return this.g;
    }

    public void a(String str) {
        if (!TextUtils.equals(str, this.g)) {
            this.g = str;
            a(this.i, this.g, "mipush_region", "mipush_region.lock", this.a);
        }
    }

    public String b() {
        if (TextUtils.isEmpty(this.h)) {
            this.h = a(this.i, "mipush_country_code", "mipush_country_code.lock", this.b);
        }
        return this.h;
    }

    public void b(String str) {
        if (!TextUtils.equals(str, this.h)) {
            this.h = str;
            a(this.i, this.h, "mipush_country_code", "mipush_country_code.lock", this.b);
        }
    }
}
