package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class CheckedTextViewCompat {
    private CheckedTextViewCompat() {
    }

    public static void setCheckMarkTintList(@NonNull CheckedTextView checkedTextView, @Nullable ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            c.a(checkedTextView, colorStateList);
        } else if (checkedTextView instanceof TintableCheckedTextView) {
            ((TintableCheckedTextView) checkedTextView).setSupportCheckMarkTintList(colorStateList);
        }
    }

    @Nullable
    public static ColorStateList getCheckMarkTintList(@NonNull CheckedTextView checkedTextView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return c.a(checkedTextView);
        }
        if (checkedTextView instanceof TintableCheckedTextView) {
            return ((TintableCheckedTextView) checkedTextView).getSupportCheckMarkTintList();
        }
        return null;
    }

    public static void setCheckMarkTintMode(@NonNull CheckedTextView checkedTextView, @Nullable PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT >= 21) {
            c.a(checkedTextView, mode);
        } else if (checkedTextView instanceof TintableCheckedTextView) {
            ((TintableCheckedTextView) checkedTextView).setSupportCheckMarkTintMode(mode);
        }
    }

    @Nullable
    public static PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView checkedTextView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return c.b(checkedTextView);
        }
        if (checkedTextView instanceof TintableCheckedTextView) {
            return ((TintableCheckedTextView) checkedTextView).getSupportCheckMarkTintMode();
        }
        return null;
    }

    @Nullable
    public static Drawable getCheckMarkDrawable(@NonNull CheckedTextView checkedTextView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return b.a(checkedTextView);
        }
        return a.a(checkedTextView);
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    private static class c {
        static void a(@NonNull CheckedTextView checkedTextView, @Nullable ColorStateList colorStateList) {
            checkedTextView.setCheckMarkTintList(colorStateList);
        }

        @Nullable
        static ColorStateList a(@NonNull CheckedTextView checkedTextView) {
            return checkedTextView.getCheckMarkTintList();
        }

        static void a(@NonNull CheckedTextView checkedTextView, @Nullable PorterDuff.Mode mode) {
            checkedTextView.setCheckMarkTintMode(mode);
        }

        @Nullable
        static PorterDuff.Mode b(@NonNull CheckedTextView checkedTextView) {
            return checkedTextView.getCheckMarkTintMode();
        }
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    private static class b {
        @Nullable
        static Drawable a(@NonNull CheckedTextView checkedTextView) {
            return checkedTextView.getCheckMarkDrawable();
        }
    }

    /* loaded from: classes.dex */
    private static class a {
        private static Field a;
        private static boolean b;

        @Nullable
        static Drawable a(@NonNull CheckedTextView checkedTextView) {
            if (!b) {
                try {
                    a = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
                    a.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i("CheckedTextViewCompat", "Failed to retrieve mCheckMarkDrawable field", e);
                }
                b = true;
            }
            Field field = a;
            if (field != null) {
                try {
                    return (Drawable) field.get(checkedTextView);
                } catch (IllegalAccessException e2) {
                    Log.i("CheckedTextViewCompat", "Failed to get check mark drawable via reflection", e2);
                    a = null;
                }
            }
            return null;
        }
    }
}
