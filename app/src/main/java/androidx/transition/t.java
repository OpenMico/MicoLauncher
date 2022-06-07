package androidx.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewGroupOverlayApi14.java */
/* loaded from: classes.dex */
public class t extends y implements v {
    /* JADX INFO: Access modifiers changed from: package-private */
    public t(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static t a(ViewGroup viewGroup) {
        return (t) y.d(viewGroup);
    }

    @Override // androidx.transition.v
    public void a(@NonNull View view) {
        this.a.a(view);
    }

    @Override // androidx.transition.v
    public void b(@NonNull View view) {
        this.a.b(view);
    }
}
