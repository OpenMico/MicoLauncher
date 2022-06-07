package com.xiaomi.passport.ui.internal.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import com.xiaomi.passport.ui.R;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class BitmapFactory extends android.graphics.BitmapFactory {
    public static final int BITMAP_COLOR_MODE_DARK = 0;
    public static final int BITMAP_COLOR_MODE_LIGHT = 2;
    public static final int BITMAP_COLOR_MODE_MEDIUM = 1;
    static RenderScript sRsContext;
    static Object sLockForRsContext = new Object();
    private static byte[] PNG_HEAD_FORMAT = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final ThreadLocal<Canvas> sCanvasCache = new ThreadLocal<>();
    private static final Paint sSrcInPaint = new Paint(1);

    static {
        sSrcInPaint.setFilterBitmap(true);
        sSrcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    protected BitmapFactory() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
        scaleBitmap(bitmap, createBitmap);
        return createBitmap;
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null || bitmap2 == null) {
            return null;
        }
        if (bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight()) {
            return bitmap;
        }
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), paint);
        return bitmap2;
    }

    static Bitmap cropBitmap(Bitmap bitmap, CropOption cropOption) {
        if (bitmap != null) {
            return cropBitmap(bitmap, copyToEmpty(bitmap), cropOption);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Bitmap createPhoto(Context context, Bitmap bitmap) {
        return createPhoto(context, bitmap, context.getResources().getDimensionPixelSize(R.dimen.contact_photo_width));
    }

    public static Bitmap createPhoto(Context context, Bitmap bitmap, int i) {
        Resources resources = context.getResources();
        return composeBitmap(bitmap, null, resources.getDrawable(R.drawable.ic_contact_photo_mask), resources.getDrawable(R.drawable.ic_contact_photo_fg), resources.getDrawable(R.drawable.ic_contact_photo_bg), i);
    }

    private static Bitmap cropBitmap(Bitmap bitmap, Bitmap bitmap2, CropOption cropOption) {
        if (bitmap == null || bitmap2 == null) {
            return null;
        }
        CropOption cropOption2 = cropOption == null ? new CropOption() : cropOption;
        Rect rect = cropOption2.srcBmpDrawingArea;
        if (rect == null) {
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        int between = between(0, bitmap.getWidth() - 1, rect.left);
        int between2 = between(between, bitmap.getWidth(), rect.right);
        int between3 = between(0, bitmap.getHeight() - 1, rect.top);
        int between4 = between(between3, bitmap.getHeight(), rect.bottom);
        int i = between2 - between;
        int i2 = between4 - between3;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        cropOption2.borderWidth = between(0, Math.min(width, height) / 2, cropOption2.borderWidth);
        cropOption2.rx = between(0, width / 2, cropOption2.rx);
        cropOption2.ry = between(0, height / 2, cropOption2.ry);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawARGB(0, 0, 0, 0);
        if (cropOption2.rx - cropOption2.borderWidth > 0 && cropOption2.ry - cropOption2.borderWidth > 0) {
            canvas.drawRoundRect(new RectF(cropOption2.borderWidth, cropOption2.borderWidth, width - cropOption2.borderWidth, height - cropOption2.borderWidth), cropOption2.rx - cropOption2.borderWidth, cropOption2.ry - cropOption2.borderWidth, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        float f = i;
        float f2 = width - (cropOption2.borderWidth * 2);
        float f3 = i2;
        float f4 = height - (cropOption2.borderWidth * 2);
        float min = Math.min((f * 1.0f) / f2, (1.0f * f3) / f4);
        int i3 = (int) ((f - (f2 * min)) / 2.0f);
        int i4 = (int) ((f3 - (f4 * min)) / 2.0f);
        canvas.drawBitmap(bitmap, new Rect(between + i3, between3 + i4, between2 - i3, between4 - i4), new Rect(cropOption2.borderWidth, cropOption2.borderWidth, width - cropOption2.borderWidth, height - cropOption2.borderWidth), paint);
        if (cropOption2.borderWidth > 0 && (cropOption2.borderColor >>> 24) != 0) {
            paint.setColor(cropOption2.borderColor);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height), cropOption2.rx, cropOption2.ry, paint);
        }
        return bitmap2;
    }

    private static int between(int i, int i2, int i3) {
        return Math.min(i2, Math.max(i, i3));
    }

    public static boolean saveToFile(Bitmap bitmap, String str) throws IOException {
        return saveToFile(bitmap, str, false);
    }

    private static boolean saveToFile(Bitmap bitmap, String str, boolean z) throws IOException {
        Throwable th;
        if (bitmap == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                bitmap.compress(z ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                return true;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static Bitmap copyToEmpty(Bitmap bitmap) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        return Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
    }

    private static Bitmap transferF16ToARGB(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setFlags(3);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    private static Canvas getCachedCanvas() {
        Canvas canvas = sCanvasCache.get();
        if (canvas != null) {
            return canvas;
        }
        Canvas canvas2 = new Canvas();
        sCanvasCache.set(canvas2);
        return canvas2;
    }

    static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        Rect rect;
        Rect rect2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (bitmap2 != null) {
            rect = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        } else {
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        return composeBitmap(bitmap, bitmap2, drawable, drawable2, drawable3, rect2, rect);
    }

    static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3, int i) {
        return composeBitmap(bitmap, bitmap2, drawable, drawable2, drawable3, null, new Rect(0, 0, i, i));
    }

    static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3, Rect rect, Rect rect2) {
        if (bitmap2 == null && rect2 == null) {
            return null;
        }
        if (bitmap2 == null) {
            if (rect2.height() <= 0 || rect2.width() <= 0) {
                return null;
            }
            bitmap2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
        } else if (rect2 == null) {
            rect2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        }
        Bitmap maskOutBitmap = maskOutBitmap(bitmap, drawable, null, rect, rect2);
        Canvas cachedCanvas = getCachedCanvas();
        cachedCanvas.setBitmap(bitmap2);
        if (drawable3 != null) {
            drawable3.setBounds(rect2);
            drawable3.draw(cachedCanvas);
        }
        cachedCanvas.drawBitmap(maskOutBitmap, rect2, rect2, (Paint) null);
        maskOutBitmap.recycle();
        if (drawable2 != null) {
            drawable2.setBounds(rect2);
            drawable2.draw(cachedCanvas);
        }
        return bitmap2;
    }

    private static Bitmap maskOutBitmap(Bitmap bitmap, Drawable drawable, Bitmap bitmap2, Rect rect, Rect rect2) {
        int i;
        int i2;
        if (bitmap2 == null && rect2 == null) {
            return null;
        }
        int i3 = 0;
        if (bitmap2 == null) {
            if (rect2.height() <= 0 || rect2.width() <= 0) {
                return null;
            }
            bitmap2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
        } else if (rect2 == null) {
            rect2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        }
        Canvas cachedCanvas = getCachedCanvas();
        cachedCanvas.setBitmap(bitmap2);
        cachedCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (drawable != null) {
            drawable.setBounds(rect2);
            drawable.draw(cachedCanvas);
        }
        if (rect == null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int width2 = rect2.width();
            float height2 = rect2.height();
            float f = width2;
            float f2 = height2 / f;
            float f3 = width;
            float f4 = f3 / f;
            float f5 = height;
            float f6 = f5 / height2;
            if (f4 > f6) {
                int i4 = (int) (f5 / f2);
                i3 = (width - i4) / 2;
                width = i4;
                i2 = height;
                i = 0;
            } else if (f4 < f6) {
                i2 = (int) (f2 * f3);
                i = (height - i2) / 2;
            } else {
                i2 = height;
                i = 0;
            }
            rect = new Rect(i3, i, width + i3, i2 + i);
        }
        cachedCanvas.drawBitmap(bitmap, rect, rect2, sSrcInPaint);
        return bitmap2;
    }

    static int getBitmapColorMode(Bitmap bitmap, int i) {
        int height = bitmap.getHeight() / i;
        int width = bitmap.getWidth() / i;
        int i2 = (width * height) / 5;
        Bitmap scaleBitmap = scaleBitmap(bitmap, width, height);
        int i3 = 2;
        int i4 = 0;
        for (int i5 = 0; i5 < width; i5++) {
            int i6 = i3;
            int i7 = 0;
            while (true) {
                if (i7 >= height) {
                    i3 = i6;
                    break;
                }
                int pixel = scaleBitmap.getPixel(i5, i7);
                if (((int) ((((16711680 & pixel) >> 16) * 0.3d) + (((65280 & pixel) >> 8) * 0.59d) + ((pixel & 255) * 0.11d))) < 180) {
                    i4++;
                    if (i4 > i2) {
                        i6 = 1;
                    }
                    if (i4 > i2 * 2) {
                        i3 = 0;
                        break;
                    }
                }
                i7++;
            }
        }
        if (scaleBitmap != bitmap) {
            scaleBitmap.recycle();
        }
        return i3;
    }

    public static File saveAsFile(Context context, InputStream inputStream, String str) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(context.openFileOutput(str, 0));
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = bufferedInputStream.read(bArr);
                if (read != -1) {
                    bufferedOutputStream.write(bArr, 0, read);
                } else {
                    try {
                        break;
                    } catch (IOException unused) {
                    }
                }
            } finally {
                try {
                    bufferedOutputStream.flush();
                } catch (IOException unused2) {
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused3) {
                }
            }
        }
        return context.getFileStreamPath(str);
    }

    /* loaded from: classes4.dex */
    public static class CropOption {
        public int borderColor;
        public int borderWidth;
        public int rx;
        public int ry;
        public Rect srcBmpDrawingArea;

        public CropOption() {
        }

        public CropOption(int i, int i2, int i3, int i4) {
            this.rx = i;
            this.ry = i2;
            this.borderWidth = i3;
            this.borderColor = i4;
        }

        public CropOption(CropOption cropOption) {
            this.rx = cropOption.rx;
            this.ry = cropOption.ry;
            this.borderWidth = cropOption.borderWidth;
            this.borderColor = cropOption.borderColor;
            this.srcBmpDrawingArea = cropOption.srcBmpDrawingArea;
        }
    }
}
