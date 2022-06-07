package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: ViewOverlayApi18.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
class e implements ViewOverlayImpl {
    private final ViewOverlay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(@NonNull View view) {
        this.a = view.getOverlay();
    }

    @Override // com.google.android.material.internal.ViewOverlayImpl
    public void add(@NonNull Drawable drawable) {
        this.a.add(drawable);
    }

    @Override // com.google.android.material.internal.ViewOverlayImpl
    public void remove(@NonNull Drawable drawable) {
        this.a.remove(drawable);
    }
}
