package androidx.transition;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewUtilsApi19.java */
@RequiresApi(19)
/* loaded from: classes.dex */
public class ac extends ah {
    private static boolean a = true;

    @Override // androidx.transition.ah
    public void b(@NonNull View view) {
    }

    @Override // androidx.transition.ah
    public void c(@NonNull View view) {
    }

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void a(@NonNull View view, float f) {
        if (a) {
            try {
                view.setTransitionAlpha(f);
                return;
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
        view.setAlpha(f);
    }

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public float a(@NonNull View view) {
        if (a) {
            try {
                return view.getTransitionAlpha();
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
        return view.getAlpha();
    }
}
