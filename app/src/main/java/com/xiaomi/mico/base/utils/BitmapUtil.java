package com.xiaomi.mico.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class BitmapUtil {
    private static boolean a(float f, float f2, float f3) {
        return ((int) ((((f * 0.257f) + (f2 * 0.504f)) + (f3 * 0.098f)) + 16.0f)) >= 100;
    }

    static native int nativeGetMajorColor(Bitmap bitmap, float f, float f2);

    public static Bitmap circle(Bitmap bitmap) {
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        int width = (bitmap.getWidth() - min) / 2;
        int height = (bitmap.getHeight() - min) / 2;
        Bitmap createBitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        if (!(width == 0 && height == 0)) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            bitmapShader.setLocalMatrix(matrix);
        }
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        float f = min / 2.0f;
        canvas.drawCircle(f, f, f, paint);
        return createBitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap blur(android.content.Context r4, android.graphics.Bitmap r5, int r6, int r7) throws android.renderscript.RSRuntimeException {
        /*
            int r0 = r5.getWidth()
            int r0 = r0 / r6
            int r1 = r5.getHeight()
            int r1 = r1 / r6
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r0, r1, r2)
            android.graphics.Canvas r1 = new android.graphics.Canvas
            r1.<init>(r0)
            float r6 = (float) r6
            r2 = 1065353216(0x3f800000, float:1.0)
            float r2 = r2 / r6
            r1.scale(r2, r2)
            android.graphics.Paint r6 = new android.graphics.Paint
            r6.<init>()
            r2 = 2
            r6.setFlags(r2)
            r2 = 0
            r1.drawBitmap(r5, r2, r2, r6)
            r5 = 0
            android.renderscript.RenderScript r4 = android.renderscript.RenderScript.create(r4)     // Catch: all -> 0x0075
            android.renderscript.Allocation$MipmapControl r6 = android.renderscript.Allocation.MipmapControl.MIPMAP_NONE     // Catch: all -> 0x0072
            r1 = 1
            android.renderscript.Allocation r6 = android.renderscript.Allocation.createFromBitmap(r4, r0, r6, r1)     // Catch: all -> 0x0072
            android.renderscript.Type r1 = r6.getType()     // Catch: all -> 0x006c
            android.renderscript.Allocation r1 = android.renderscript.Allocation.createTyped(r4, r1)     // Catch: all -> 0x006c
            android.renderscript.Element r2 = android.renderscript.Element.U8_4(r4)     // Catch: all -> 0x0069
            android.renderscript.ScriptIntrinsicBlur r2 = android.renderscript.ScriptIntrinsicBlur.create(r4, r2)     // Catch: all -> 0x0069
            r2.setInput(r6)     // Catch: all -> 0x0064
            float r7 = (float) r7     // Catch: all -> 0x0064
            r2.setRadius(r7)     // Catch: all -> 0x0064
            r2.forEach(r1)     // Catch: all -> 0x0064
            r1.copyTo(r0)     // Catch: all -> 0x0064
            r6.destroy()     // Catch: all -> 0x0064
            r2.destroy()     // Catch: all -> 0x0062
            r1.destroy()     // Catch: all -> 0x005f
            r4.destroy()     // Catch: all -> 0x0072
            return r0
        L_0x005f:
            r6 = move-exception
            r2 = r5
            goto L_0x0079
        L_0x0062:
            r6 = move-exception
            goto L_0x0079
        L_0x0064:
            r5 = move-exception
            r3 = r6
            r6 = r5
            r5 = r3
            goto L_0x0079
        L_0x0069:
            r7 = move-exception
            r2 = r5
            goto L_0x006f
        L_0x006c:
            r7 = move-exception
            r1 = r5
            r2 = r1
        L_0x006f:
            r5 = r6
            r6 = r7
            goto L_0x0079
        L_0x0072:
            r6 = move-exception
            r1 = r5
            goto L_0x0078
        L_0x0075:
            r6 = move-exception
            r4 = r5
            r1 = r4
        L_0x0078:
            r2 = r1
        L_0x0079:
            if (r5 == 0) goto L_0x007e
            r5.destroy()
        L_0x007e:
            if (r2 == 0) goto L_0x0083
            r2.destroy()
        L_0x0083:
            if (r1 == 0) goto L_0x0088
            r1.destroy()
        L_0x0088:
            if (r4 == 0) goto L_0x008d
            r4.destroy()
        L_0x008d:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.base.utils.BitmapUtil.blur(android.content.Context, android.graphics.Bitmap, int, int):android.graphics.Bitmap");
    }

    public static Bitmap getLinearGradientBitmap(int i, int i2, int i3, int i4, int i5) {
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvas.setBitmap(createBitmap);
        canvas.drawColor(i2, PorterDuff.Mode.CLEAR);
        paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, createBitmap.getHeight() - i5, new int[]{i, i2}, (float[]) null, Shader.TileMode.CLAMP));
        canvas.drawRect(new RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight()), paint);
        return createBitmap;
    }

    public static Bitmap encodeAsBitmap(String str, int i, int i2) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            HashMap hashMap = new HashMap(4);
            hashMap.put(EncodeHintType.MARGIN, 0);
            return new BarcodeEncoder().createBitmap(multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, i, i2, hashMap));
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static int getMajorColor(Bitmap bitmap) {
        return a(Color.valueOf(nativeGetMajorColor(bitmap, 0.1f, 0.99f)));
    }

    private static int a(Color color) {
        float red = color.red();
        float green = color.green();
        float blue = color.blue();
        if (a(red, green, blue)) {
            return color.toArgb();
        }
        return Color.valueOf(Math.max(red - 0.1f, 0.0f), Math.max(green - 0.1f, 0.0f), Math.max(blue - 0.1f, 0.0f)).toArgb();
    }

    static {
        System.loadLibrary("mico_bitmap_utils");
    }
}
