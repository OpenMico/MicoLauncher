package com.xiaomi.onetrack.util;

/* loaded from: classes4.dex */
public class z {
    public static boolean a(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean b(CharSequence charSequence) {
        return !a(charSequence);
    }
}
