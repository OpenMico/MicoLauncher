package com.xiaomi.mico.base.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

/* loaded from: classes3.dex */
public class ViewUtil {
    public static Bitmap screenshotActivity(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(decorView.getDrawingCache());
        decorView.setDrawingCacheEnabled(false);
        decorView.destroyDrawingCache();
        return createBitmap;
    }
}
