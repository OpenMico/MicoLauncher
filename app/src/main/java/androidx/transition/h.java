package androidx.transition;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: GhostViewUtils.java */
/* loaded from: classes.dex */
class h {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static d a(@NonNull View view, @NonNull ViewGroup viewGroup, @Nullable Matrix matrix) {
        if (Build.VERSION.SDK_INT == 28) {
            return f.a(view, viewGroup, matrix);
        }
        return g.b(view, viewGroup, matrix);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(View view) {
        if (Build.VERSION.SDK_INT == 28) {
            f.a(view);
        } else {
            g.b(view);
        }
    }
}
