package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewOverlayApi18.java */
@RequiresApi(18)
/* loaded from: classes.dex */
public class z implements aa {
    private final ViewOverlay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public z(@NonNull View view) {
        this.a = view.getOverlay();
    }

    @Override // androidx.transition.aa
    public void a(@NonNull Drawable drawable) {
        this.a.add(drawable);
    }

    @Override // androidx.transition.aa
    public void b(@NonNull Drawable drawable) {
        this.a.remove(drawable);
    }
}
