package com.xiaomi.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.service.ba;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
class cw {
    private static String b = "/MiPushLog";
    private String c;
    private String d;
    private boolean e;
    private int f;
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int g = 2097152;
    private ArrayList<File> h = new ArrayList<>();

    private void a(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Pattern pattern) {
        char[] cArr = new char[4096];
        int read = bufferedReader.read(cArr);
        boolean z = false;
        while (read != -1 && !z) {
            String str = new String(cArr, 0, read);
            Matcher matcher = pattern.matcher(str);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= read || !matcher.find(i)) {
                    break;
                }
                int start = matcher.start();
                String substring = str.substring(start, this.c.length() + start);
                if (this.e) {
                    if (substring.compareTo(this.d) > 0) {
                        z = true;
                        read = start;
                        break;
                    }
                } else if (substring.compareTo(this.c) >= 0) {
                    this.e = true;
                    i2 = start;
                }
                int indexOf = str.indexOf(10, start);
                if (indexOf == -1) {
                    indexOf = this.c.length();
                }
                i = start + indexOf;
            }
            if (this.e) {
                int i3 = read - i2;
                this.f += i3;
                if (z) {
                    bufferedWriter.write(cArr, i2, i3);
                    return;
                }
                bufferedWriter.write(cArr, i2, i3);
                if (this.f > this.g) {
                    return;
                }
            }
            read = bufferedReader.read(cArr);
        }
    }

    private void b(File file) {
        Throwable th;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        String str;
        FileNotFoundException e;
        IOException e2;
        try {
            Pattern compile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            bufferedReader = null;
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                try {
                    bufferedWriter.write("model :" + Build.MODEL + "; os :" + Build.VERSION.INCREMENTAL + "; uid :" + ba.m1145a() + "; lng :" + Locale.getDefault().toString() + "; sdk :38; andver :" + Build.VERSION.SDK_INT + "\n");
                    this.f = 0;
                    Iterator<File> it = this.h.iterator();
                    while (it.hasNext()) {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(it.next())));
                        try {
                            a(bufferedReader, bufferedWriter, compile);
                            bufferedReader.close();
                            bufferedReader = bufferedReader;
                        } catch (FileNotFoundException e3) {
                            e = e3;
                            bufferedReader = bufferedWriter;
                            str = "LOG: filter error = " + e.getMessage();
                            b.c(str);
                            z.a(bufferedReader);
                            z.a(bufferedReader);
                            return;
                        } catch (IOException e4) {
                            e2 = e4;
                            bufferedReader = bufferedWriter;
                            str = "LOG: filter error = " + e2.getMessage();
                            b.c(str);
                            z.a(bufferedReader);
                            z.a(bufferedReader);
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            z.a(bufferedWriter);
                            z.a(bufferedReader);
                            throw th;
                        }
                    }
                    bufferedWriter.write(cv.a().c());
                    z.a(bufferedWriter);
                    z.a(bufferedReader);
                } catch (FileNotFoundException e5) {
                    e = e5;
                } catch (IOException e6) {
                    e2 = e6;
                } catch (Throwable th3) {
                    th = th3;
                    z.a(bufferedWriter);
                    z.a(bufferedReader);
                    throw th;
                }
            } catch (FileNotFoundException e7) {
                e = e7;
                bufferedReader = null;
            } catch (IOException e8) {
                e2 = e8;
                bufferedReader = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedWriter = null;
            }
        } catch (Throwable th5) {
            th = th5;
            bufferedWriter = bufferedReader;
        }
    }

    cw a(File file) {
        if (file.exists()) {
            this.h.add(file);
        }
        return this;
    }

    cw a(Date date, Date date2) {
        String str;
        if (date.after(date2)) {
            this.c = this.a.format(date2);
            str = this.a.format(date);
        } else {
            this.c = this.a.format(date);
            str = this.a.format(date2);
        }
        this.d = str;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public File a(Context context, Date date, Date date2, File file) {
        File file2;
        if ("com.xiaomi.xmsf".equalsIgnoreCase(context.getPackageName())) {
            file2 = context.getFilesDir();
            a(new File(file2, "xmsf.log.1"));
            a(new File(file2, "xmsf.log"));
        } else {
            File file3 = new File(context.getExternalFilesDir(null) + b);
            a(new File(file3, "log0.txt"));
            a(new File(file3, "log1.txt"));
            file2 = file3;
        }
        if (!file2.isDirectory()) {
            return null;
        }
        File file4 = new File(file, date.getTime() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + date2.getTime() + ".zip");
        if (file4.exists()) {
            return null;
        }
        a(date, date2);
        long currentTimeMillis = System.currentTimeMillis();
        File file5 = new File(file, "log.txt");
        b(file5);
        b.c("LOG: filter cost = " + (System.currentTimeMillis() - currentTimeMillis));
        if (file5.exists()) {
            long currentTimeMillis2 = System.currentTimeMillis();
            z.a(file4, file5);
            b.c("LOG: zip cost = " + (System.currentTimeMillis() - currentTimeMillis2));
            file5.delete();
            if (file4.exists()) {
                return file4;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        if (i != 0) {
            this.g = i;
        }
    }
}
