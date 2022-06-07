package androidx.transition;

import android.graphics.Matrix;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* compiled from: ViewUtilsApi29.java */
@RequiresApi(29)
/* loaded from: classes.dex */
class ag extends af {
    @Override // androidx.transition.ac, androidx.transition.ah
    public void a(@NonNull View view, float f) {
        view.setTransitionAlpha(f);
    }

    @Override // androidx.transition.ac, androidx.transition.ah
    public float a(@NonNull View view) {
        return view.getTransitionAlpha();
    }

    @Override // androidx.transition.af, androidx.transition.ah
    public void a(@NonNull View view, int i) {
        view.setTransitionVisibility(i);
    }

    @Override // androidx.transition.ae, androidx.transition.ah
    public void a(@NonNull View view, int i, int i2, int i3, int i4) {
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    @Override // androidx.transition.ad, androidx.transition.ah
    public void a(@NonNull View view, @NonNull Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    @Override // androidx.transition.ad, androidx.transition.ah
    public void b(@NonNull View view, @NonNull Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }

    @Override // androidx.transition.ad, androidx.transition.ah
    public void c(@NonNull View view, @Nullable Matrix matrix) {
        view.setAnimationMatrix(matrix);
    }
}
