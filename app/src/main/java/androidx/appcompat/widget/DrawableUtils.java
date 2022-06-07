package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.WrappedDrawable;

@SuppressLint({"RestrictedAPI"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class DrawableUtils {
    private static Class<?> c;
    private static final int[] a = {16842912};
    private static final int[] b = new int[0];
    public static final Rect INSETS_NONE = new Rect();

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                c = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException unused) {
            }
        }
    }

    private DrawableUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009a A[Catch: Exception -> 0x00b9, TryCatch #0 {Exception -> 0x00b9, blocks: (B:8:0x0024, B:10:0x003d, B:12:0x004c, B:21:0x006c, B:24:0x0076, B:27:0x0080, B:30:0x008b, B:34:0x0096, B:36:0x009a, B:37:0x00a1, B:38:0x00a8, B:39:0x00af), top: B:45:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a1 A[Catch: Exception -> 0x00b9, TryCatch #0 {Exception -> 0x00b9, blocks: (B:8:0x0024, B:10:0x003d, B:12:0x004c, B:21:0x006c, B:24:0x0076, B:27:0x0080, B:30:0x008b, B:34:0x0096, B:36:0x009a, B:37:0x00a1, B:38:0x00a8, B:39:0x00af), top: B:45:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a8 A[Catch: Exception -> 0x00b9, TryCatch #0 {Exception -> 0x00b9, blocks: (B:8:0x0024, B:10:0x003d, B:12:0x004c, B:21:0x006c, B:24:0x0076, B:27:0x0080, B:30:0x008b, B:34:0x0096, B:36:0x009a, B:37:0x00a1, B:38:0x00a8, B:39:0x00af), top: B:45:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00af A[Catch: Exception -> 0x00b9, TRY_LEAVE, TryCatch #0 {Exception -> 0x00b9, blocks: (B:8:0x0024, B:10:0x003d, B:12:0x004c, B:21:0x006c, B:24:0x0076, B:27:0x0080, B:30:0x008b, B:34:0x0096, B:36:0x009a, B:37:0x00a1, B:38:0x00a8, B:39:0x00af), top: B:45:0x0024 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Rect getOpticalBounds(android.graphics.drawable.Drawable r10) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 < r1) goto L_0x0020
            android.graphics.Insets r10 = r10.getOpticalInsets()
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            int r1 = r10.left
            r0.left = r1
            int r1 = r10.right
            r0.right = r1
            int r1 = r10.top
            r0.top = r1
            int r10 = r10.bottom
            r0.bottom = r10
            return r0
        L_0x0020:
            java.lang.Class<?> r0 = androidx.appcompat.widget.DrawableUtils.c
            if (r0 == 0) goto L_0x00c0
            android.graphics.drawable.Drawable r10 = androidx.core.graphics.drawable.DrawableCompat.unwrap(r10)     // Catch: Exception -> 0x00b9
            java.lang.Class r0 = r10.getClass()     // Catch: Exception -> 0x00b9
            java.lang.String r1 = "getOpticalInsets"
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch: Exception -> 0x00b9
            java.lang.reflect.Method r0 = r0.getMethod(r1, r3)     // Catch: Exception -> 0x00b9
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch: Exception -> 0x00b9
            java.lang.Object r10 = r0.invoke(r10, r1)     // Catch: Exception -> 0x00b9
            if (r10 == 0) goto L_0x00c0
            android.graphics.Rect r0 = new android.graphics.Rect     // Catch: Exception -> 0x00b9
            r0.<init>()     // Catch: Exception -> 0x00b9
            java.lang.Class<?> r1 = androidx.appcompat.widget.DrawableUtils.c     // Catch: Exception -> 0x00b9
            java.lang.reflect.Field[] r1 = r1.getFields()     // Catch: Exception -> 0x00b9
            int r3 = r1.length     // Catch: Exception -> 0x00b9
            r4 = r2
        L_0x004a:
            if (r4 >= r3) goto L_0x00b8
            r5 = r1[r4]     // Catch: Exception -> 0x00b9
            java.lang.String r6 = r5.getName()     // Catch: Exception -> 0x00b9
            r7 = -1
            int r8 = r6.hashCode()     // Catch: Exception -> 0x00b9
            r9 = -1383228885(0xffffffffad8d9a2b, float:-1.6098308E-11)
            if (r8 == r9) goto L_0x008b
            r9 = 115029(0x1c155, float:1.6119E-40)
            if (r8 == r9) goto L_0x0080
            r9 = 3317767(0x32a007, float:4.649182E-39)
            if (r8 == r9) goto L_0x0076
            r9 = 108511772(0x677c21c, float:4.6598146E-35)
            if (r8 == r9) goto L_0x006c
            goto L_0x0095
        L_0x006c:
            java.lang.String r8 = "right"
            boolean r6 = r6.equals(r8)     // Catch: Exception -> 0x00b9
            if (r6 == 0) goto L_0x0095
            r6 = 2
            goto L_0x0096
        L_0x0076:
            java.lang.String r8 = "left"
            boolean r6 = r6.equals(r8)     // Catch: Exception -> 0x00b9
            if (r6 == 0) goto L_0x0095
            r6 = r2
            goto L_0x0096
        L_0x0080:
            java.lang.String r8 = "top"
            boolean r6 = r6.equals(r8)     // Catch: Exception -> 0x00b9
            if (r6 == 0) goto L_0x0095
            r6 = 1
            goto L_0x0096
        L_0x008b:
            java.lang.String r8 = "bottom"
            boolean r6 = r6.equals(r8)     // Catch: Exception -> 0x00b9
            if (r6 == 0) goto L_0x0095
            r6 = 3
            goto L_0x0096
        L_0x0095:
            r6 = r7
        L_0x0096:
            switch(r6) {
                case 0: goto L_0x00af;
                case 1: goto L_0x00a8;
                case 2: goto L_0x00a1;
                case 3: goto L_0x009a;
                default: goto L_0x0099;
            }     // Catch: Exception -> 0x00b9
        L_0x0099:
            goto L_0x00b5
        L_0x009a:
            int r5 = r5.getInt(r10)     // Catch: Exception -> 0x00b9
            r0.bottom = r5     // Catch: Exception -> 0x00b9
            goto L_0x00b5
        L_0x00a1:
            int r5 = r5.getInt(r10)     // Catch: Exception -> 0x00b9
            r0.right = r5     // Catch: Exception -> 0x00b9
            goto L_0x00b5
        L_0x00a8:
            int r5 = r5.getInt(r10)     // Catch: Exception -> 0x00b9
            r0.top = r5     // Catch: Exception -> 0x00b9
            goto L_0x00b5
        L_0x00af:
            int r5 = r5.getInt(r10)     // Catch: Exception -> 0x00b9
            r0.left = r5     // Catch: Exception -> 0x00b9
        L_0x00b5:
            int r4 = r4 + 1
            goto L_0x004a
        L_0x00b8:
            return r0
        L_0x00b9:
            java.lang.String r10 = "DrawableUtils"
            java.lang.String r0 = "Couldn't obtain the optical insets. Ignoring."
            android.util.Log.e(r10, r0)
        L_0x00c0:
            android.graphics.Rect r10 = androidx.appcompat.widget.DrawableUtils.INSETS_NONE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.DrawableUtils.getOpticalBounds(android.graphics.drawable.Drawable):android.graphics.Rect");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName())) {
            b(drawable);
        }
    }

    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (!(constantState instanceof DrawableContainer.DrawableContainerState)) {
                return true;
            }
            for (Drawable drawable2 : ((DrawableContainer.DrawableContainerState) constantState).getChildren()) {
                if (!canSafelyMutateDrawable(drawable2)) {
                    return false;
                }
            }
            return true;
        } else if (drawable instanceof WrappedDrawable) {
            return canSafelyMutateDrawable(((WrappedDrawable) drawable).getWrappedDrawable());
        } else {
            if (drawable instanceof DrawableWrapper) {
                return canSafelyMutateDrawable(((DrawableWrapper) drawable).getWrappedDrawable());
            }
            if (drawable instanceof ScaleDrawable) {
                return canSafelyMutateDrawable(((ScaleDrawable) drawable).getDrawable());
            }
            return true;
        }
    }

    private static void b(Drawable drawable) {
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(a);
        } else {
            drawable.setState(b);
        }
        drawable.setState(state);
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
