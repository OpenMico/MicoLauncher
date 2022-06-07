package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.alibaba.fastjson.asm.Opcodes;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public final class TransformationUtils {
    public static final int PAINT_FLAGS = 6;
    private static final Paint c;
    private static final Lock e;
    private static final Paint a = new Paint(6);
    private static final Paint b = new Paint(7);
    private static final Set<String> d = new HashSet(Arrays.asList("XT1085", "XT1092", "XT1093", "XT1094", "XT1095", "XT1096", "XT1097", "XT1098", "XT1031", "XT1028", "XT937C", "XT1032", "XT1008", "XT1033", "XT1035", "XT1034", "XT939G", "XT1039", "XT1040", "XT1042", "XT1045", "XT1063", "XT1064", "XT1068", "XT1069", "XT1072", "XT1077", "XT1078", "XT1079"));

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface a {
        void a(Canvas canvas, Paint paint, RectF rectF);
    }

    public static int getExifOrientationDegrees(int i) {
        switch (i) {
            case 3:
            case 4:
                return Opcodes.GETFIELD;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    public static boolean isExifOrientationRequired(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    static {
        e = d.contains(Build.MODEL) ? new ReentrantLock() : new b();
        c = new Paint(7);
        c.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private TransformationUtils() {
    }

    public static Lock getBitmapDrawableLock() {
        return e;
    }

    public static Bitmap centerCrop(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        float f3 = 0.0f;
        if (bitmap.getWidth() * i2 > bitmap.getHeight() * i) {
            f2 = i2 / bitmap.getHeight();
            f = (i - (bitmap.getWidth() * f2)) * 0.5f;
        } else {
            f2 = i / bitmap.getWidth();
            f3 = (i2 - (bitmap.getHeight() * f2)) * 0.5f;
            f = 0.0f;
        }
        matrix.setScale(f2, f2);
        matrix.postTranslate((int) (f + 0.5f), (int) (f3 + 0.5f));
        Bitmap bitmap2 = bitmapPool.get(i, i2, b(bitmap));
        setAlpha(bitmap, bitmap2);
        a(bitmap, bitmap2, matrix);
        return bitmap2;
    }

    public static Bitmap fitCenter(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "requested target size matches input, returning input");
            }
            return bitmap;
        }
        float min = Math.min(i / bitmap.getWidth(), i2 / bitmap.getHeight());
        int round = Math.round(bitmap.getWidth() * min);
        int round2 = Math.round(bitmap.getHeight() * min);
        if (bitmap.getWidth() == round && bitmap.getHeight() == round2) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "adjusted target size matches input, returning input");
            }
            return bitmap;
        }
        Bitmap bitmap2 = bitmapPool.get((int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), b(bitmap));
        setAlpha(bitmap, bitmap2);
        if (Log.isLoggable("TransformationUtils", 2)) {
            Log.v("TransformationUtils", "request: " + i + "x" + i2);
            Log.v("TransformationUtils", "toFit:   " + bitmap.getWidth() + "x" + bitmap.getHeight());
            Log.v("TransformationUtils", "toReuse: " + bitmap2.getWidth() + "x" + bitmap2.getHeight());
            StringBuilder sb = new StringBuilder();
            sb.append("minPct:   ");
            sb.append(min);
            Log.v("TransformationUtils", sb.toString());
        }
        Matrix matrix = new Matrix();
        matrix.setScale(min, min);
        a(bitmap, bitmap2, matrix);
        return bitmap2;
    }

    public static Bitmap centerInside(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() > i || bitmap.getHeight() > i2) {
            if (Log.isLoggable("TransformationUtils", 2)) {
                Log.v("TransformationUtils", "requested target size too big for input, fit centering instead");
            }
            return fitCenter(bitmapPool, bitmap, i, i2);
        }
        if (Log.isLoggable("TransformationUtils", 2)) {
            Log.v("TransformationUtils", "requested target size larger or equal to input, returning input");
        }
        return bitmap;
    }

    public static void setAlpha(Bitmap bitmap, Bitmap bitmap2) {
        bitmap2.setHasAlpha(bitmap.hasAlpha());
    }

    public static Bitmap rotateImage(@NonNull Bitmap bitmap, int i) {
        if (i == 0) {
            return bitmap;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.setRotate(i);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e2) {
            if (!Log.isLoggable("TransformationUtils", 6)) {
                return bitmap;
            }
            Log.e("TransformationUtils", "Exception when trying to orient image", e2);
            return bitmap;
        }
    }

    public static Bitmap rotateImageExif(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i) {
        if (!isExifOrientationRequired(i)) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        a(i, matrix);
        RectF rectF = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        matrix.mapRect(rectF);
        Bitmap bitmap2 = bitmapPool.get(Math.round(rectF.width()), Math.round(rectF.height()), b(bitmap));
        matrix.postTranslate(-rectF.left, -rectF.top);
        bitmap2.setHasAlpha(bitmap.hasAlpha());
        a(bitmap, bitmap2, matrix);
        return bitmap2;
    }

    /* JADX WARN: Finally extract failed */
    public static Bitmap circleCrop(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        int min = Math.min(i, i2);
        float f = min;
        float f2 = f / 2.0f;
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float max = Math.max(f / width, f / height);
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f - f4) / 2.0f;
        RectF rectF = new RectF(f5, f6, f3 + f5, f4 + f6);
        Bitmap a2 = a(bitmapPool, bitmap);
        Bitmap bitmap2 = bitmapPool.get(min, min, a(bitmap));
        bitmap2.setHasAlpha(true);
        e.lock();
        try {
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawCircle(f2, f2, f2, b);
            canvas.drawBitmap(a2, (Rect) null, rectF, c);
            a(canvas);
            e.unlock();
            if (!a2.equals(bitmap)) {
                bitmapPool.put(a2);
            }
            return bitmap2;
        } catch (Throwable th) {
            e.unlock();
            throw th;
        }
    }

    private static Bitmap a(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap) {
        Bitmap.Config a2 = a(bitmap);
        if (a2.equals(bitmap.getConfig())) {
            return bitmap;
        }
        Bitmap bitmap2 = bitmapPool.get(bitmap.getWidth(), bitmap.getHeight(), a2);
        new Canvas(bitmap2).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return bitmap2;
    }

    @NonNull
    private static Bitmap.Config a(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < 26 || !Bitmap.Config.RGBA_F16.equals(bitmap.getConfig())) {
            return Bitmap.Config.ARGB_8888;
        }
        return Bitmap.Config.RGBA_F16;
    }

    @Deprecated
    public static Bitmap roundedCorners(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2, int i3) {
        return roundedCorners(bitmapPool, bitmap, i3);
    }

    public static Bitmap roundedCorners(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, final int i) {
        Preconditions.checkArgument(i > 0, "roundingRadius must be greater than 0.");
        return a(bitmapPool, bitmap, new a() { // from class: com.bumptech.glide.load.resource.bitmap.TransformationUtils.1
            @Override // com.bumptech.glide.load.resource.bitmap.TransformationUtils.a
            public void a(Canvas canvas, Paint paint, RectF rectF) {
                int i2 = i;
                canvas.drawRoundRect(rectF, i2, i2, paint);
            }
        });
    }

    public static Bitmap roundedCorners(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, final float f, final float f2, final float f3, final float f4) {
        return a(bitmapPool, bitmap, new a() { // from class: com.bumptech.glide.load.resource.bitmap.TransformationUtils.2
            @Override // com.bumptech.glide.load.resource.bitmap.TransformationUtils.a
            public void a(Canvas canvas, Paint paint, RectF rectF) {
                Path path = new Path();
                float f5 = f;
                float f6 = f2;
                float f7 = f3;
                float f8 = f4;
                path.addRoundRect(rectF, new float[]{f5, f5, f6, f6, f7, f7, f8, f8}, Path.Direction.CW);
                canvas.drawPath(path, paint);
            }
        });
    }

    /* JADX WARN: Finally extract failed */
    private static Bitmap a(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, a aVar) {
        Bitmap.Config a2 = a(bitmap);
        Bitmap a3 = a(bitmapPool, bitmap);
        Bitmap bitmap2 = bitmapPool.get(a3.getWidth(), a3.getHeight(), a2);
        bitmap2.setHasAlpha(true);
        BitmapShader bitmapShader = new BitmapShader(a3, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        RectF rectF = new RectF(0.0f, 0.0f, bitmap2.getWidth(), bitmap2.getHeight());
        e.lock();
        try {
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            aVar.a(canvas, paint, rectF);
            a(canvas);
            e.unlock();
            if (!a3.equals(bitmap)) {
                bitmapPool.put(a3);
            }
            return bitmap2;
        } catch (Throwable th) {
            e.unlock();
            throw th;
        }
    }

    private static void a(Canvas canvas) {
        canvas.setBitmap(null);
    }

    @NonNull
    private static Bitmap.Config b(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    private static void a(@NonNull Bitmap bitmap, @NonNull Bitmap bitmap2, Matrix matrix) {
        e.lock();
        try {
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawBitmap(bitmap, matrix, a);
            a(canvas);
        } finally {
            e.unlock();
        }
    }

    @VisibleForTesting
    static void a(int i, Matrix matrix) {
        switch (i) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                return;
            case 3:
                matrix.setRotate(180.0f);
                return;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 6:
                matrix.setRotate(90.0f);
                return;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 8:
                matrix.setRotate(-90.0f);
                return;
            default:
                return;
        }
    }

    /* loaded from: classes.dex */
    private static final class b implements Lock {
        @Override // java.util.concurrent.locks.Lock
        public void lock() {
        }

        @Override // java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
        }

        @Override // java.util.concurrent.locks.Lock
        public boolean tryLock() {
            return true;
        }

        @Override // java.util.concurrent.locks.Lock
        public boolean tryLock(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return true;
        }

        @Override // java.util.concurrent.locks.Lock
        public void unlock() {
        }

        b() {
        }

        @Override // java.util.concurrent.locks.Lock
        @NonNull
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
