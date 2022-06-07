package com.google.android.material.resources;

import android.graphics.Typeface;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    private final Typeface a;
    private final ApplyFont b;
    private boolean c;

    /* loaded from: classes2.dex */
    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont, Typeface typeface) {
        this.a = typeface;
        this.b = applyFont;
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public void onFontRetrieved(Typeface typeface, boolean z) {
        a(typeface);
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public void onFontRetrievalFailed(int i) {
        a(this.a);
    }

    public void cancel() {
        this.c = true;
    }

    private void a(Typeface typeface) {
        if (!this.c) {
            this.b.apply(typeface);
        }
    }
}
