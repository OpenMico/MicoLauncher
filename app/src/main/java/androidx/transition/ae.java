package androidx.transition;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewUtilsApi22.java */
@RequiresApi(22)
/* loaded from: classes.dex */
public class ae extends ad {
    private static boolean a = true;

    @Override // androidx.transition.ah
    @SuppressLint({"NewApi"})
    public void a(@NonNull View view, int i, int i2, int i3, int i4) {
        if (a) {
            try {
                view.setLeftTopRightBottom(i, i2, i3, i4);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }
}
