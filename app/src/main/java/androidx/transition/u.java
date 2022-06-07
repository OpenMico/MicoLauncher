package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: ViewGroupOverlayApi18.java */
@RequiresApi(18)
/* loaded from: classes.dex */
class u implements v {
    private final ViewGroupOverlay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public u(@NonNull ViewGroup viewGroup) {
        this.a = viewGroup.getOverlay();
    }

    @Override // androidx.transition.aa
    public void a(@NonNull Drawable drawable) {
        this.a.add(drawable);
    }

    @Override // androidx.transition.aa
    public void b(@NonNull Drawable drawable) {
        this.a.remove(drawable);
    }

    @Override // androidx.transition.v
    public void a(@NonNull View view) {
        this.a.add(view);
    }

    @Override // androidx.transition.v
    public void b(@NonNull View view) {
        this.a.remove(view);
    }
}
