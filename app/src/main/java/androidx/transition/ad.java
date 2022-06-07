package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* compiled from: ViewUtilsApi21.java */
@RequiresApi(21)
/* loaded from: classes.dex */
class ad extends ac {
    private static boolean a = true;
    private static boolean b = true;
    private static boolean c = true;

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void a(@NonNull View view, @NonNull Matrix matrix) {
        if (b) {
            try {
                view.transformMatrixToGlobal(matrix);
            } catch (NoSuchMethodError unused) {
                b = false;
            }
        }
    }

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void b(@NonNull View view, @NonNull Matrix matrix) {
        if (c) {
            try {
                view.transformMatrixToLocal(matrix);
            } catch (NoSuchMethodError unused) {
                c = false;
            }
        }
    }

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void c(@NonNull View view, @Nullable Matrix matrix) {
        if (a) {
            try {
                view.setAnimationMatrix(matrix);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }
}
