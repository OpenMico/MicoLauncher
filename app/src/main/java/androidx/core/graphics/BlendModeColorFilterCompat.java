package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class BlendModeColorFilterCompat {
    @Nullable
    public static ColorFilter createBlendModeColorFilterCompat(int i, @NonNull BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            BlendMode a = a.a(blendModeCompat);
            if (a != null) {
                return new BlendModeColorFilter(i, a);
            }
            return null;
        }
        PorterDuff.Mode b = a.b(blendModeCompat);
        if (b != null) {
            return new PorterDuffColorFilter(i, b);
        }
        return null;
    }

    private BlendModeColorFilterCompat() {
    }
}
