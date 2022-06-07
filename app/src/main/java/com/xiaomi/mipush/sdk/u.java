package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.z;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class u {
    private static volatile u a;
    private static final Object b = new Object();
    private Context c;

    private u(Context context) {
        this.c = context;
    }

    public static u a(Context context) {
        if (a == null) {
            synchronized (u.class) {
                if (a == null) {
                    a = new u(context);
                }
            }
        }
        return a;
    }

    private File a(String str) {
        File file = new File(this.c.getFilesDir() + "/crash");
        if (!file.exists()) {
            file.mkdirs();
            return null;
        }
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile() && listFiles[i].getName().startsWith(str)) {
                return listFiles[i];
            }
        }
        return null;
    }

    public String a(File file) {
        if (file == null) {
            return "";
        }
        String[] split = file.getName().split(Constants.COLON_SEPARATOR);
        return split.length != 2 ? "" : split[0];
    }

    public ArrayList<File> a() {
        ArrayList<File> arrayList = new ArrayList<>();
        File file = new File(this.c.getFilesDir() + "/crash");
        if (!file.exists()) {
            file.mkdirs();
            return arrayList;
        }
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            String[] split = listFiles[i].getName().split(Constants.COLON_SEPARATOR);
            if (split.length >= 2 && Integer.parseInt(split[1]) >= 1 && listFiles[i].isFile()) {
                arrayList.add(listFiles[i]);
            }
        }
        return arrayList;
    }

    public void a(String str, String str2) {
        Throwable th;
        Exception e;
        FileOutputStream fileOutputStream;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            synchronized (b) {
                File a2 = a(str2);
                if (a2 != null) {
                    String[] split = a2.getName().split(Constants.COLON_SEPARATOR);
                    if (split.length >= 2) {
                        a2.renameTo(new File(this.c.getFilesDir() + "/crash/" + str2 + Constants.COLON_SEPARATOR + String.valueOf(Integer.parseInt(split[1]) + 1)));
                    }
                } else {
                    FileOutputStream fileOutputStream2 = null;
                    try {
                        try {
                            fileOutputStream = new FileOutputStream(new File(this.c.getFilesDir() + "/crash/" + str2 + Constants.COLON_SEPARATOR + "1"));
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    try {
                        fileOutputStream.write(str.getBytes());
                        fileOutputStream.flush();
                        z.a(fileOutputStream);
                    } catch (Exception e3) {
                        e = e3;
                        fileOutputStream2 = fileOutputStream;
                        b.a(e);
                        z.a(fileOutputStream2);
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream2 = fileOutputStream;
                        z.a(fileOutputStream2);
                        throw th;
                    }
                }
            }
        }
    }
}
