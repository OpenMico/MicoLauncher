package androidx.transition;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

/* compiled from: RectEvaluator.java */
/* loaded from: classes.dex */
class n implements TypeEvaluator<Rect> {
    private Rect a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(Rect rect) {
        this.a = rect;
    }

    /* renamed from: a */
    public Rect evaluate(float f, Rect rect, Rect rect2) {
        int i = rect.left + ((int) ((rect2.left - rect.left) * f));
        int i2 = rect.top + ((int) ((rect2.top - rect.top) * f));
        int i3 = rect.right + ((int) ((rect2.right - rect.right) * f));
        int i4 = rect.bottom + ((int) ((rect2.bottom - rect.bottom) * f));
        Rect rect3 = this.a;
        if (rect3 == null) {
            return new Rect(i, i2, i3, i4);
        }
        rect3.set(i, i2, i3, i4);
        return this.a;
    }
}
