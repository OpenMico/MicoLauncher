package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DrawingDelegate.java */
/* loaded from: classes2.dex */
public abstract class d<S extends BaseProgressIndicatorSpec> {
    S a;
    protected c b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int a();

    abstract void a(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float f);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(@NonNull Canvas canvas, @NonNull Paint paint);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2, @ColorInt int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int b();

    public d(S s) {
        this.a = s;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull c cVar) {
        this.b = cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.a.a();
        a(canvas, f);
    }
}
