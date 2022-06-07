package com.xiaomi.micolauncher.common.player.opengl;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class InfoLog {
    private static String a;
    private static int b;

    public static String getMessage() {
        return a;
    }

    public static int getErrorLine() {
        return b;
    }

    public static void parse(String str) {
        int i;
        a = null;
        b = 0;
        if (str != null) {
            int indexOf = str.indexOf("ERROR: 0:");
            if (indexOf > -1) {
                i = indexOf + 9;
            } else {
                i = str.indexOf("0:");
                if (i > -1) {
                    i += 2;
                }
            }
            if (i > -1) {
                int indexOf2 = str.indexOf(Constants.COLON_SEPARATOR, i);
                if (indexOf2 > -1) {
                    try {
                        b = Integer.parseInt(str.substring(i, indexOf2).trim());
                    } catch (NullPointerException | NumberFormatException unused) {
                    }
                    i = indexOf2 + 1;
                }
                int indexOf3 = str.indexOf("\n", i);
                if (indexOf3 < 0) {
                    indexOf3 = str.length();
                }
                str = str.substring(i, indexOf3).trim();
            }
            a = str;
        }
    }
}
