package com.xiaomi.mico.base.utils;

/* loaded from: classes3.dex */
public final class ImageExifUtils {
    public static final int INFINITY = Integer.MAX_VALUE;
    public static final int INVALID = -1;

    public static float exifOrientationToDegrees(int i) {
        if (i == 6) {
            return 90.0f;
        }
        if (i == 3) {
            return 180.0f;
        }
        return i == 8 ? 270.0f : 0.0f;
    }

    public static int degreesToExifOrientation(float f) {
        if (ImageUtils.floatEquals(f, 0.0f)) {
            return 1;
        }
        if (ImageUtils.floatEquals(f, 90.0f)) {
            return 6;
        }
        if (ImageUtils.floatEquals(f, 180.0f)) {
            return 3;
        }
        return ImageUtils.floatEquals(f, 270.0f) ? 8 : 1;
    }

    public static int degreesToExifOrientation(int i) {
        int i2 = (i + 360) % 360;
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 90) {
            return 6;
        }
        if (i2 == 180) {
            return 3;
        }
        return i2 == 270 ? 8 : 1;
    }
}
