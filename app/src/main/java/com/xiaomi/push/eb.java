package com.xiaomi.push;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes4.dex */
public class eb extends dy {
    public eb(Context context, int i) {
        super(context, i);
    }

    private double a(double d) {
        int i = 1;
        while (true) {
            double d2 = i;
            if (d2 >= d) {
                return d2;
            }
            i <<= 1;
        }
    }

    private long a(File file) {
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getBlockSize() * statFs.getBlockCount();
    }

    private String c() {
        BufferedReader bufferedReader;
        Throwable th;
        String[] split;
        String str = "0";
        if (new File("/proc/meminfo").exists()) {
            try {
                BufferedReader bufferedReader2 = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
                    try {
                        String readLine = bufferedReader.readLine();
                        if (!TextUtils.isEmpty(readLine) && (split = readLine.split("\\s+")) != null && split.length >= 2) {
                            double doubleValue = (Double.valueOf(split[1]).doubleValue() / 1024.0d) / 1024.0d;
                            if (doubleValue > 0.5d) {
                                doubleValue = Math.ceil(doubleValue);
                            }
                            String str2 = doubleValue + "";
                        }
                        bufferedReader.close();
                    } catch (Exception unused) {
                        bufferedReader2 = bufferedReader;
                        str = "0";
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                        return str + "GB";
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception unused3) {
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            } catch (IOException unused4) {
            }
        }
        return str + "GB";
    }

    private String d() {
        double a = a(((a(Environment.getDataDirectory()) / 1024.0d) / 1024.0d) / 1024.0d);
        return a + "GB";
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 23;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public hj mo834a() {
        return hj.Storage;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        return "ram:" + c() + Constants.ACCEPT_TIME_SEPARATOR_SP + "rom:" + d();
    }
}
