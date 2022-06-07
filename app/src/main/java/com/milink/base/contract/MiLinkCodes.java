package com.milink.base.contract;

/* loaded from: classes2.dex */
public class MiLinkCodes {
    public static final int ERROR = -1;
    public static final int NOT_SUPPORT = -2;
    public static final int SUCC = 0;

    public static boolean isFail(int i) {
        return i < 0;
    }

    public static boolean isSucc(int i) {
        return i == 0;
    }

    public static boolean isSucc(String str) {
        try {
            return Integer.parseInt(str) == 0;
        } catch (NumberFormatException unused) {
            return false;
        }
    }
}
