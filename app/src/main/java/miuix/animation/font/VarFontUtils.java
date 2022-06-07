package miuix.animation.font;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import java.io.File;
import miuix.animation.internal.AnimTask;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.DeviceUtils;

@TargetApi(26)
/* loaded from: classes5.dex */
public class VarFontUtils {
    public static final int MIN_WGHT;
    private static final int[] a;
    private static final int[] b;
    private static final int[][][] c;
    private static final boolean d;

    private static boolean a() {
        String[] list;
        File file = new File("/data/system/theme/fonts/");
        try {
            if (!file.exists() || (list = file.list()) == null) {
                return false;
            }
            return list.length > 0;
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "isUsingThemeFont, failed to check theme font directory", e);
        }
        return false;
    }

    private static boolean b() {
        try {
            return CommonUtils.readProp("ro.miui.ui.font.animation").equals("disable");
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "isFontAnimationEnabled failed", e);
            return false;
        }
    }

    static {
        d = !a() && Build.VERSION.SDK_INT >= 26 && DeviceUtils.getTotalRam() > 6 && !b() && DeviceUtils.getDeviceLevel() > 0;
        if (d) {
            a = new int[]{AnimTask.MAX_PAGE_SIZE, 200, 250, 305, 340, 400, com.xiaomi.mico.base.utils.CommonUtils.IMAGE_WIDTH_THRESHOLD, 540, 630, 700};
            b = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
            MIN_WGHT = 10;
            c = new int[3][];
            int[][][] iArr = c;
            int[][] iArr2 = new int[10];
            iArr2[0] = new int[]{0, 5};
            iArr2[1] = new int[]{0, 5};
            iArr2[2] = new int[]{1, 6};
            iArr2[3] = new int[]{2, 6};
            iArr2[4] = new int[]{2, 7};
            iArr2[5] = new int[]{3, 8};
            iArr2[6] = new int[]{4, 8};
            iArr2[7] = new int[]{5, 9};
            iArr2[8] = new int[]{6, 9};
            iArr2[9] = new int[]{7, 9};
            iArr[0] = iArr2;
            int[][] iArr3 = new int[10];
            iArr3[0] = new int[]{0, 2};
            iArr3[1] = new int[]{0, 3};
            iArr3[2] = new int[]{1, 4};
            iArr3[3] = new int[]{1, 5};
            iArr3[4] = new int[]{2, 6};
            iArr3[5] = new int[]{2, 7};
            iArr3[6] = new int[]{3, 8};
            iArr3[7] = new int[]{4, 9};
            iArr3[8] = new int[]{5, 9};
            iArr3[9] = new int[]{6, 9};
            iArr[1] = iArr3;
            int[][] iArr4 = new int[10];
            iArr4[0] = new int[]{0, 5};
            iArr4[1] = new int[]{1, 5};
            iArr4[2] = new int[]{2, 5};
            iArr4[3] = new int[]{3, 6};
            iArr4[4] = new int[]{3, 6};
            iArr4[5] = new int[]{4, 7};
            iArr4[6] = new int[]{5, 8};
            iArr4[7] = new int[]{6, 8};
            iArr4[8] = new int[]{7, 8};
            iArr4[9] = new int[]{8, 9};
            iArr[2] = iArr4;
            return;
        }
        MIN_WGHT = 0;
        int[] iArr5 = new int[0];
        b = iArr5;
        a = iArr5;
        c = new int[0][];
    }

    private VarFontUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i, float f, int i2, int i3) {
        if (!d) {
            return i;
        }
        int[] a2 = a(i, f);
        int a3 = a(a2[0], i2);
        int a4 = a(i, i2);
        int a5 = a(a2[1], i2);
        if (i3 < 50) {
            float f2 = i3 / 50.0f;
            return (int) (((1.0f - f2) * a3) + (f2 * a4));
        } else if (i3 <= 50) {
            return a4;
        } else {
            float f3 = (i3 - 50) / 50.0f;
            return (int) (((1.0f - f3) * a4) + (f3 * a5));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(TextView textView, int i) {
        if (d) {
            textView.setFontVariationSettings("'wght' " + i);
        }
    }

    private static int[] a(int i) {
        return (i == 1 || i == 2) ? b : a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "key_miui_font_weight_scale", 50);
    }

    private static int[] a(int i, float f) {
        return c[f > 20.0f ? (char) 1 : (f <= 0.0f || f >= 12.0f) ? (char) 0 : (char) 2][i];
    }

    private static int a(int i, int i2) {
        return a(i2)[i];
    }
}
