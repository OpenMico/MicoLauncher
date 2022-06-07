package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;

/* compiled from: ObjectAnimatorUtils.java */
/* loaded from: classes.dex */
class k {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> ObjectAnimator a(T t, Property<T, PointF> property, Path path) {
        if (Build.VERSION.SDK_INT >= 21) {
            return ObjectAnimator.ofObject(t, property, (TypeConverter) null, path);
        }
        return ObjectAnimator.ofFloat(t, new l(property, path), 0.0f, 1.0f);
    }
}
