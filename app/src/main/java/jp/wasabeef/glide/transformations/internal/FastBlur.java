package jp.wasabeef.glide.transformations.internal;

import android.graphics.Bitmap;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Array;

/* loaded from: classes5.dex */
public class FastBlur {
    public static Bitmap blur(Bitmap bitmap, int i, boolean z) {
        int i2;
        int i3 = i;
        Bitmap copy = z ? bitmap : bitmap.copy(bitmap.getConfig(), true);
        if (i3 < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i4 = width * height;
        int[] iArr = new int[i4];
        copy.getPixels(iArr, 0, width, 0, 0, width, height);
        int i5 = width - 1;
        int i6 = height - 1;
        int i7 = i3 + i3 + 1;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int[] iArr5 = new int[Math.max(width, height)];
        int i8 = (i7 + 1) >> 1;
        int i9 = i8 * i8;
        int i10 = i9 * 256;
        int[] iArr6 = new int[i10];
        for (int i11 = 0; i11 < i10; i11++) {
            iArr6[i11] = i11 / i9;
        }
        int[][] iArr7 = (int[][]) Array.newInstance(int.class, i7, 3);
        int i12 = i3 + 1;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        while (i13 < height) {
            int i16 = -i3;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            int i25 = 0;
            while (i16 <= i3) {
                int i26 = iArr[i14 + Math.min(i5, Math.max(i16, 0))];
                int[] iArr8 = iArr7[i16 + i3];
                iArr8[0] = (i26 & 16711680) >> 16;
                iArr8[1] = (i26 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr8[2] = i26 & 255;
                int abs = i12 - Math.abs(i16);
                i17 += iArr8[0] * abs;
                i18 += iArr8[1] * abs;
                i19 += iArr8[2] * abs;
                if (i16 > 0) {
                    i23 += iArr8[0];
                    i24 += iArr8[1];
                    i25 += iArr8[2];
                } else {
                    i20 += iArr8[0];
                    i21 += iArr8[1];
                    i22 += iArr8[2];
                }
                i16++;
                height = height;
                i6 = i6;
            }
            int i27 = i3;
            for (int i28 = 0; i28 < width; i28++) {
                iArr2[i14] = iArr6[i17];
                iArr3[i14] = iArr6[i18];
                iArr4[i14] = iArr6[i19];
                int i29 = i17 - i20;
                int i30 = i18 - i21;
                int i31 = i19 - i22;
                int[] iArr9 = iArr7[((i27 - i3) + i7) % i7];
                int i32 = i20 - iArr9[0];
                int i33 = i21 - iArr9[1];
                int i34 = i22 - iArr9[2];
                if (i13 == 0) {
                    iArr6 = iArr6;
                    iArr5[i28] = Math.min(i28 + i3 + 1, i5);
                } else {
                    iArr6 = iArr6;
                }
                int i35 = iArr[i15 + iArr5[i28]];
                iArr9[0] = (i35 & 16711680) >> 16;
                iArr9[1] = (i35 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr9[2] = i35 & 255;
                int i36 = i23 + iArr9[0];
                int i37 = i24 + iArr9[1];
                int i38 = i25 + iArr9[2];
                i17 = i29 + i36;
                i18 = i30 + i37;
                i19 = i31 + i38;
                i27 = (i27 + 1) % i7;
                int[] iArr10 = iArr7[i27 % i7];
                i20 = i32 + iArr10[0];
                i21 = i33 + iArr10[1];
                i22 = i34 + iArr10[2];
                i23 = i36 - iArr10[0];
                i24 = i37 - iArr10[1];
                i25 = i38 - iArr10[2];
                i14++;
            }
            i15 += width;
            i13++;
            copy = copy;
            height = height;
            i6 = i6;
        }
        int i39 = i6;
        int i40 = height;
        int i41 = 0;
        while (i41 < width) {
            int i42 = -i3;
            int i43 = i42 * width;
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            while (i42 <= i3) {
                int max = Math.max(0, i43) + i41;
                int[] iArr11 = iArr7[i42 + i3];
                iArr11[0] = iArr2[max];
                iArr11[1] = iArr3[max];
                iArr11[2] = iArr4[max];
                int abs2 = i12 - Math.abs(i42);
                i44 += iArr2[max] * abs2;
                i45 += iArr3[max] * abs2;
                i46 += iArr4[max] * abs2;
                if (i42 > 0) {
                    i50 += iArr11[0];
                    i51 += iArr11[1];
                    i52 += iArr11[2];
                    i2 = i39;
                } else {
                    i47 += iArr11[0];
                    i48 += iArr11[1];
                    i49 += iArr11[2];
                    i2 = i39;
                }
                if (i42 < i2) {
                    i43 += width;
                }
                i42++;
                i39 = i2;
                iArr5 = iArr5;
            }
            int i53 = i41;
            int i54 = i51;
            int i55 = i52;
            int i56 = 0;
            int i57 = i3;
            int i58 = i50;
            int i59 = i49;
            int i60 = i48;
            int i61 = i47;
            int i62 = i46;
            int i63 = i45;
            int i64 = i44;
            while (i56 < i40) {
                iArr[i53] = (iArr[i53] & ViewCompat.MEASURED_STATE_MASK) | (iArr6[i64] << 16) | (iArr6[i63] << 8) | iArr6[i62];
                int i65 = i64 - i61;
                int i66 = i63 - i60;
                int i67 = i62 - i59;
                int[] iArr12 = iArr7[((i57 - i3) + i7) % i7];
                int i68 = i61 - iArr12[0];
                int i69 = i60 - iArr12[1];
                int i70 = i59 - iArr12[2];
                if (i41 == 0) {
                    iArr5[i56] = Math.min(i56 + i12, i39) * width;
                }
                int i71 = iArr5[i56] + i41;
                iArr12[0] = iArr2[i71];
                iArr12[1] = iArr3[i71];
                iArr12[2] = iArr4[i71];
                int i72 = i58 + iArr12[0];
                int i73 = i54 + iArr12[1];
                int i74 = i55 + iArr12[2];
                i64 = i65 + i72;
                i63 = i66 + i73;
                i62 = i67 + i74;
                i57 = (i57 + 1) % i7;
                int[] iArr13 = iArr7[i57];
                i61 = i68 + iArr13[0];
                i60 = i69 + iArr13[1];
                i59 = i70 + iArr13[2];
                i58 = i72 - iArr13[0];
                i54 = i73 - iArr13[1];
                i55 = i74 - iArr13[2];
                i53 += width;
                i56++;
                i3 = i;
            }
            i41++;
            i39 = i39;
            i40 = i40;
            iArr5 = iArr5;
            i3 = i;
        }
        copy.setPixels(iArr, 0, width, 0, 0, width, i40);
        return copy;
    }
}
