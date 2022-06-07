package com.xiaomi.micolauncher.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.xiaomi.micolauncher.R;
import io.reactivex.Observable;
import io.reactivex.Observer;

/* loaded from: classes3.dex */
public class BlurBuilder {
    public static final int PAINT_FLAGS = 6;
    public static final int SAMPLING = 10;
    public static final Paint DEFAULT_PAINT = new Paint(6);
    private static Bitmap a = null;
    private static Bitmap b = null;

    public static void recycleBg() {
    }

    public static void recycleOverlay() {
    }

    public static Observable<Bitmap> createFullScreenBlurBackground(final Context context) {
        return new Observable<Bitmap>() { // from class: com.xiaomi.micolauncher.common.BlurBuilder.1
            @Override // io.reactivex.Observable
            protected void subscribeActual(Observer<? super Bitmap> observer) {
                Bitmap snapShotFullScreenBlur = BlurBuilder.snapShotFullScreenBlur(context);
                if (snapShotFullScreenBlur != null) {
                    observer.onNext(snapShotFullScreenBlur);
                    observer.onComplete();
                    return;
                }
                observer.onError(new IllegalStateException("crate bitmap error"));
            }
        };
    }

    public static Bitmap snapShotFullScreenBlur(Context context) {
        snapShotFullScreen(context);
        Bitmap blur = blur(context);
        if (blur == null) {
            return null;
        }
        Bitmap copy = blur.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap createBitmap = Bitmap.createBitmap(copy.getWidth(), copy.getHeight(), copy.getConfig());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(copy, 0.0f, 0.0f, paint);
        canvas.drawColor(context.getResources().getColor(R.color.color_E1F8FF_65));
        return createBitmap;
    }

    public static Bitmap blur(Context context) {
        Bitmap bitmap = a;
        if (bitmap != null) {
            return blur(context, bitmap);
        }
        Log.d("BlurBuilder", "BlurBuilder.tab_bg=null");
        Bitmap bitmap2 = b;
        if (bitmap2 != null) {
            return bitmap2;
        }
        return null;
    }

    public static Bitmap blur(Context context, Bitmap bitmap) {
        if (b != null) {
            recycleOverlay();
        }
        try {
            b = a(context, null, bitmap, 10, 25);
        } catch (Exception e) {
            Log.e("BlurBuilder", "BlurBuilder.exception= " + e);
        }
        return b;
    }

    public static Bitmap blur(Context context, Bitmap bitmap, int i, int i2) {
        return a(context, null, bitmap, i, i2);
    }

    public static Bitmap blur(Context context, @Nullable BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return a(context, bitmapPool, bitmap, i, i2);
    }

    public static void getScreenshot(View view) {
        if (a != null) {
            recycle();
        }
        try {
            a = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(a));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static void snapShotFullScreen(Context context) {
        if (a != null) {
            recycleBg();
        }
        a = SurfaceControl.screenshot(new Rect(), getScreenWidth(context), getScreenHeight(context), 0, 0, true, 0);
    }

    public static void snapShot(Activity activity) {
        if (a != null) {
            recycleBg();
        }
        View decorView = activity.getWindow().getDecorView();
        try {
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            a = decorView.getDrawingCache();
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int i = rect.top;
            a = Bitmap.createBitmap(a, 0, i, getScreenWidth(activity), getScreenHeight(activity) - i);
            decorView.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            getScreenshot(decorView);
        }
    }

    public static void recycle() {
        recycleBg();
        recycleOverlay();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Bitmap a(android.content.Context r3, @androidx.annotation.Nullable com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool r4, android.graphics.Bitmap r5, int r6, int r7) throws android.renderscript.RSRuntimeException {
        /*
            int r0 = r5.getWidth()
            int r0 = r0 / r6
            int r1 = r5.getHeight()
            int r1 = r1 / r6
            if (r4 == 0) goto L_0x0013
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r4 = r4.get(r0, r1, r2)
            goto L_0x0019
        L_0x0013:
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r4 = android.graphics.Bitmap.createBitmap(r0, r1, r4)
        L_0x0019:
            android.graphics.Canvas r0 = new android.graphics.Canvas
            r0.<init>(r4)
            r1 = 1065353216(0x3f800000, float:1.0)
            float r6 = (float) r6
            float r1 = r1 / r6
            r0.scale(r1, r1)
            android.graphics.Paint r6 = com.xiaomi.micolauncher.common.BlurBuilder.DEFAULT_PAINT
            r1 = 0
            r0.drawBitmap(r5, r1, r1, r6)
            r5 = 0
            android.renderscript.RenderScript r3 = android.renderscript.RenderScript.create(r3)     // Catch: all -> 0x0071
            android.renderscript.Allocation$MipmapControl r6 = android.renderscript.Allocation.MipmapControl.MIPMAP_NONE     // Catch: all -> 0x006e
            r0 = 1
            android.renderscript.Allocation r6 = android.renderscript.Allocation.createFromBitmap(r3, r4, r6, r0)     // Catch: all -> 0x006e
            android.renderscript.Type r0 = r6.getType()     // Catch: all -> 0x006b
            android.renderscript.Allocation r0 = android.renderscript.Allocation.createTyped(r3, r0)     // Catch: all -> 0x006b
            android.renderscript.Element r1 = android.renderscript.Element.U8_4(r3)     // Catch: all -> 0x0069
            android.renderscript.ScriptIntrinsicBlur r5 = android.renderscript.ScriptIntrinsicBlur.create(r3, r1)     // Catch: all -> 0x0069
            r5.setInput(r6)     // Catch: all -> 0x0069
            float r7 = (float) r7     // Catch: all -> 0x0069
            r5.setRadius(r7)     // Catch: all -> 0x0069
            r5.forEach(r0)     // Catch: all -> 0x0069
            r0.copyTo(r4)     // Catch: all -> 0x0069
            if (r6 == 0) goto L_0x0059
            r6.destroy()
        L_0x0059:
            if (r5 == 0) goto L_0x005e
            r5.destroy()
        L_0x005e:
            if (r0 == 0) goto L_0x0063
            r0.destroy()
        L_0x0063:
            if (r3 == 0) goto L_0x0068
            r3.destroy()
        L_0x0068:
            return r4
        L_0x0069:
            r4 = move-exception
            goto L_0x0075
        L_0x006b:
            r4 = move-exception
            r0 = r5
            goto L_0x0075
        L_0x006e:
            r4 = move-exception
            r6 = r5
            goto L_0x0074
        L_0x0071:
            r4 = move-exception
            r3 = r5
            r6 = r3
        L_0x0074:
            r0 = r6
        L_0x0075:
            if (r6 == 0) goto L_0x007a
            r6.destroy()
        L_0x007a:
            if (r5 == 0) goto L_0x007f
            r5.destroy()
        L_0x007f:
            if (r0 == 0) goto L_0x0084
            r0.destroy()
        L_0x0084:
            if (r3 == 0) goto L_0x0089
            r3.destroy()
        L_0x0089:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.BlurBuilder.a(android.content.Context, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool, android.graphics.Bitmap, int, int):android.graphics.Bitmap");
    }
}
