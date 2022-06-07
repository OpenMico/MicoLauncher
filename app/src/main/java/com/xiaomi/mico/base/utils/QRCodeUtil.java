package com.xiaomi.mico.base.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xiaomi.onetrack.api.c;
import java.util.Hashtable;

/* loaded from: classes3.dex */
public class QRCodeUtil {
    @Nullable
    public static Bitmap createQRCodeBitmap(String str, int i, int i2) {
        return createQRCodeBitmap(str, i, i2, "UTF-8", c.b, "2", ViewCompat.MEASURED_STATE_MASK, -1);
    }

    @Nullable
    public static Bitmap createQRCodeBitmap(String str, int i, int i2, @Nullable String str2, @Nullable String str3, @Nullable String str4, @ColorInt int i3, @ColorInt int i4) {
        if (TextUtils.isEmpty(str) || i < 0 || i2 < 0) {
            return null;
        }
        try {
            Hashtable hashtable = new Hashtable();
            if (!TextUtils.isEmpty(str2)) {
                hashtable.put(EncodeHintType.CHARACTER_SET, str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                hashtable.put(EncodeHintType.ERROR_CORRECTION, str3);
            }
            if (!TextUtils.isEmpty(str4)) {
                hashtable.put(EncodeHintType.MARGIN, str4);
            }
            BitMatrix encode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i, i2, hashtable);
            int[] iArr = new int[i * i2];
            for (int i5 = 0; i5 < i2; i5++) {
                for (int i6 = 0; i6 < i; i6++) {
                    if (encode.get(i6, i5)) {
                        iArr[(i5 * i) + i6] = i3;
                    } else {
                        iArr[(i5 * i) + i6] = i4;
                    }
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
            return createBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
