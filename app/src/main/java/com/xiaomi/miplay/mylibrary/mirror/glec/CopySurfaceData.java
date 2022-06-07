package com.xiaomi.miplay.mylibrary.mirror.glec;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.PixelCopy;
import android.view.Surface;

/* loaded from: classes4.dex */
public class CopySurfaceData {
    private Surface a;
    private OnConvertBitmapListener d;
    private Context i;
    private int j;
    private int b = 1920;
    private int c = 1080;
    private String g = "MiPlayQuickCopySurfaceData";
    private int h = 0;
    private boolean k = false;
    private int l = 60;
    private int m = this.l;
    private int n = 90;
    private HandlerThread f = new HandlerThread("CopySurfaceData");
    private a e = new a(this.f.getLooper());

    /* loaded from: classes4.dex */
    public interface OnConvertBitmapListener {
        void onFailure();

        void onSuccess(Bitmap bitmap);
    }

    /* loaded from: classes4.dex */
    private class a extends Handler {
        a(Looper looper) {
            super(looper);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CopySurfaceData(Context context) {
        this.i = null;
        this.f.start();
        this.i = context;
        if (this.i != null) {
            this.j = context.getResources().getConfiguration().orientation;
        } else {
            this.j = 1;
        }
    }

    private boolean a(Context context) {
        if (context != null) {
            Configuration configuration = context.getResources().getConfiguration();
            if (configuration.orientation == 1 && this.j != configuration.orientation) {
                this.j = configuration.orientation;
                Log.i(this.g, "current screen change,ORIENTATION_LANDSCAPE to ORIENTATION_PORTRAIT");
                return true;
            } else if (configuration.orientation == 2 && this.j != configuration.orientation) {
                this.j = configuration.orientation;
                Log.i(this.g, "current screen change,ORIENTATION_PORTRAIT to ORIENTATION_LANDSCAPE");
                return true;
            }
        }
        return false;
    }

    public void setMsurface(Surface surface, int i, int i2, OnConvertBitmapListener onConvertBitmapListener) {
        this.a = surface;
        this.b = i;
        this.c = i2;
        this.d = onConvertBitmapListener;
    }

    public void convertLayoutToBitmap(int i, int i2) {
        if (this.a != null && this.d != null) {
            Rect rect = new Rect(0, 0, i, i2);
            if (Build.VERSION.SDK_INT >= 26) {
                if (a(this.i) && this.j == 2) {
                    this.k = true;
                    this.m = this.l;
                    Log.i(this.g, "@@@@Orientation start!!!!!");
                }
                if (this.k) {
                    if (this.m % 5 == 0) {
                        final Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                        PixelCopy.request(this.a, rect, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.xiaomi.miplay.mylibrary.mirror.glec.CopySurfaceData.1
                            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                            public void onPixelCopyFinished(int i3) {
                                if (i3 == 0) {
                                    CopySurfaceData.this.d.onSuccess(createBitmap);
                                } else {
                                    CopySurfaceData.this.d.onFailure();
                                }
                            }
                        }, this.e);
                    }
                    this.m--;
                    if (this.m == 0) {
                        this.k = false;
                        Log.i(this.g, "@@@@Orientation finish!!!!!");
                    }
                } else {
                    int i3 = this.h;
                    if (i3 >= 0 && i3 % this.n == 0) {
                        final Bitmap createBitmap2 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                        PixelCopy.request(this.a, rect, createBitmap2, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.xiaomi.miplay.mylibrary.mirror.glec.CopySurfaceData.2
                            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                            public void onPixelCopyFinished(int i4) {
                                if (i4 == 0) {
                                    CopySurfaceData.this.d.onSuccess(createBitmap2);
                                } else {
                                    CopySurfaceData.this.d.onFailure();
                                }
                            }
                        }, this.e);
                    }
                }
                this.h++;
            }
        }
    }
}
