package jp.wasabeef.glide.transformations.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/* loaded from: classes5.dex */
public final class Utils {
    private Utils() {
    }

    public static Drawable getMaskDrawable(Context context, int i) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = context.getDrawable(i);
        } else {
            drawable = context.getResources().getDrawable(i);
        }
        if (drawable != null) {
            return drawable;
        }
        throw new IllegalArgumentException("maskId is invalid");
    }
}
