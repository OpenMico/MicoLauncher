package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

/* compiled from: ImageViewUtils.java */
/* loaded from: classes.dex */
class i {
    private static boolean a = true;
    private static Field b;
    private static boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull ImageView imageView, @Nullable Matrix matrix) {
        if (Build.VERSION.SDK_INT >= 29) {
            imageView.animateTransform(matrix);
        } else if (matrix == null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                drawable.setBounds(0, 0, (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight(), (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom());
                imageView.invalidate();
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            b(imageView, matrix);
        } else {
            Drawable drawable2 = imageView.getDrawable();
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                Matrix matrix2 = null;
                a();
                Field field = b;
                if (field != null) {
                    try {
                        Matrix matrix3 = (Matrix) field.get(imageView);
                        if (matrix3 == null) {
                            try {
                                matrix2 = new Matrix();
                                b.set(imageView, matrix2);
                            } catch (IllegalAccessException unused) {
                                matrix2 = matrix3;
                            }
                        } else {
                            matrix2 = matrix3;
                        }
                    } catch (IllegalAccessException unused2) {
                    }
                }
                if (matrix2 != null) {
                    matrix2.set(matrix);
                }
                imageView.invalidate();
            }
        }
    }

    @RequiresApi(21)
    @SuppressLint({"NewApi"})
    private static void b(@NonNull ImageView imageView, @Nullable Matrix matrix) {
        if (a) {
            try {
                imageView.animateTransform(matrix);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }

    private static void a() {
        if (!c) {
            try {
                b = ImageView.class.getDeclaredField("mDrawMatrix");
                b.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            c = true;
        }
    }
}
