package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.b;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class y {
    private static final HashMap<String, String> a = new HashMap<>();

    static {
        a.put("FFD8FF", "jpg");
        a.put("89504E47", "png");
        a.put("47494638", "gif");
        a.put("474946", "gif");
        a.put("424D", "bmp");
    }

    public static long a(File file) {
        long j = 0;
        try {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                j += listFiles[i].isDirectory() ? a(listFiles[i]) : listFiles[i].length();
            }
        } catch (Exception e) {
            b.a(e);
        }
        return j;
    }
}
