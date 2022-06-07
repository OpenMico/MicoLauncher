package com.efs.sdk.base.a.h;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import java.io.File;
import java.util.UUID;

/* loaded from: classes.dex */
public class h {
    private static volatile String a = "";

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            synchronized (h.class) {
                if (TextUtils.isEmpty(a)) {
                    String b = b(context);
                    a = b;
                    if (TextUtils.isEmpty(b)) {
                        a = c(context);
                    }
                }
            }
        }
        return a;
    }

    private static String b(Context context) {
        try {
            File file = new File(a.a(context), "efsid");
            if (file.exists()) {
                return b.a(file);
            }
            return null;
        } catch (Exception e) {
            d.b("efs.base", "get uuid error", e);
            return null;
        }
    }

    private static String c(Context context) {
        String str = "";
        for (int i = 0; i < 3; i++) {
            try {
                str = UUID.randomUUID().toString();
            } catch (Throwable unused) {
            }
            if (TextUtils.isEmpty(str)) {
            }
        }
        try {
            File a2 = a.a(context);
            File file = new File(a2, "efsid" + Process.myPid());
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            b.a(file, str);
            if (file.renameTo(new File(a2, "efsid"))) {
                file.delete();
            }
        } catch (Exception e) {
            d.b("efs.base", "save uuid '" + str + "' error", e);
        }
        return str;
    }
}
