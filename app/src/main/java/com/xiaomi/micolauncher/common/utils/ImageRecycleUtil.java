package com.xiaomi.micolauncher.common.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class ImageRecycleUtil {
    public static void recycleImageViewBitmap(ImageView imageView) {
        if (imageView != null) {
            a((BitmapDrawable) imageView.getDrawable());
        }
    }

    public static void recycleViewBitmapBackground(View view) {
        if (view != null) {
            a((BitmapDrawable) view.getBackground());
        }
    }

    private static void a(BitmapDrawable bitmapDrawable) {
        if (bitmapDrawable != null) {
            a(bitmapDrawable.getBitmap());
        }
    }

    private static void a(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            L.base.i("recycle bitmap width : %d,height : %d", Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()));
            bitmap.recycle();
        }
    }
}
