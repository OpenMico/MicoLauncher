package com.xiaomi.micolauncher.common.view.base;

/* loaded from: classes3.dex */
public class FastClickJudgementManager {
    private static int a;
    private static long b;

    public static boolean isFastDoubleClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - b) < 700) {
            return true;
        }
        b = currentTimeMillis;
        return false;
    }

    public static boolean isFastContinuousClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - b < 700) {
            a++;
            if (a >= 4) {
                return false;
            }
        } else {
            a = 0;
        }
        b = currentTimeMillis;
        return true;
    }
}
