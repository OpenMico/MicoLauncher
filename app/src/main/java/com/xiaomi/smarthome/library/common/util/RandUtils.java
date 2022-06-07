package com.xiaomi.smarthome.library.common.util;

import java.util.Random;

/* loaded from: classes4.dex */
public class RandUtils {
    private static Random a = new Random(System.currentTimeMillis());

    public static double randFloat() {
        return a.nextDouble();
    }
}
