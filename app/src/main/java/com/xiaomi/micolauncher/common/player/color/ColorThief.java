package com.xiaomi.micolauncher.common.player.color;

import android.graphics.Bitmap;
import androidx.core.view.MotionEventCompat;
import com.xiaomi.micolauncher.common.player.color.MMCQ;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class ColorThief {
    public static int[] getColor(Bitmap bitmap) {
        int[][] palette = getPalette(bitmap, 5);
        if (palette == null) {
            return null;
        }
        return palette[0];
    }

    public static int[] getColor(Bitmap bitmap, int i, boolean z) {
        int[][] palette = getPalette(bitmap, 5, i, z);
        if (palette == null) {
            return null;
        }
        return palette[0];
    }

    public static int[][] getPalette(Bitmap bitmap, int i) {
        MMCQ.CMap colorMap = getColorMap(bitmap, i);
        if (colorMap == null) {
            return null;
        }
        return colorMap.palette();
    }

    public static int[][] getPalette(Bitmap bitmap, int i, int i2, boolean z) {
        MMCQ.CMap colorMap = getColorMap(bitmap, i, i2, z);
        if (colorMap == null) {
            return null;
        }
        return colorMap.palette();
    }

    public static MMCQ.CMap getColorMap(Bitmap bitmap, int i) {
        return getColorMap(bitmap, i, 10, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.common.player.color.ColorThief$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Bitmap.Config.values().length];

        static {
            try {
                a[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static MMCQ.CMap getColorMap(Bitmap bitmap, int i, int i2, boolean z) {
        int[][] iArr;
        if (i < 2 || i > 256) {
            throw new IllegalArgumentException("Specified colorCount must be between 2 and 256.");
        } else if (i2 >= 1) {
            if (AnonymousClass1.a[bitmap.getConfig().ordinal()] != 1) {
                iArr = b(bitmap, i2, z);
            } else {
                iArr = a(bitmap, i2, z);
            }
            return MMCQ.quantize(iArr, i);
        } else {
            throw new IllegalArgumentException("Specified quality should be greater then 0.");
        }
    }

    private static int[][] a(Bitmap bitmap, int i, boolean z) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth() * bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        if (AnonymousClass1.a[config.ordinal()] != 1) {
            throw new IllegalArgumentException("Unhandled type: " + config);
        } else if (width == iArr.length) {
            int[][] iArr2 = new int[((width + i) - 1) / i];
            if (AnonymousClass1.a[config.ordinal()] == 1) {
                int i2 = 0;
                int i3 = 0;
                while (i2 < width) {
                    int i4 = iArr[i2] & 255;
                    int i5 = (iArr[i2] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                    int i6 = (iArr[i2] & 16711680) >> 16;
                    if (!z || i6 <= 250 || i5 <= 250 || i4 <= 250) {
                        int[] iArr3 = new int[3];
                        iArr3[0] = i6;
                        iArr3[1] = i5;
                        iArr3[2] = i4;
                        iArr2[i3] = iArr3;
                        i3++;
                    }
                    i2 += i;
                }
                return (int[][]) Arrays.copyOfRange(iArr2, 0, i3);
            }
            throw new IllegalArgumentException("Unhandled type: " + config);
        } else {
            throw new IllegalArgumentException("(expectedDataLength = " + width + ") != (pixels.length = " + iArr.length + ")");
        }
    }

    private static int[][] b(Bitmap bitmap, int i, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight() * width;
        int[][] iArr = new int[((height + i) - 1) / i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < height) {
            int pixel = bitmap.getPixel(i2 % width, i2 / width);
            int i4 = (pixel >> 16) & 255;
            int i5 = (pixel >> 8) & 255;
            int i6 = pixel & 255;
            if (!z || i4 <= 250 || i5 <= 250 || i6 <= 250) {
                int[] iArr2 = new int[3];
                iArr2[0] = i4;
                iArr2[1] = i5;
                iArr2[2] = i6;
                iArr[i3] = iArr2;
                i3++;
            }
            i2 += i;
        }
        return (int[][]) Arrays.copyOfRange(iArr, 0, i3);
    }
}
