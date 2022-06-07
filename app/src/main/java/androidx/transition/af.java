package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: ViewUtilsApi23.java */
@RequiresApi(23)
/* loaded from: classes.dex */
class af extends ae {
    private static boolean a = true;

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void a(@NonNull View view, int i) {
        if (Build.VERSION.SDK_INT == 28) {
            super.a(view, i);
        } else if (a) {
            try {
                view.setTransitionVisibility(i);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }
}
