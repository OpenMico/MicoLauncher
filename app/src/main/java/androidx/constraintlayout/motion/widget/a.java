package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.view.View;
import java.util.HashSet;

/* compiled from: KeyPositionBase.java */
/* loaded from: classes.dex */
abstract class a extends Key {
    protected static final float SELECTION_SLOPE = 20.0f;
    int o = UNSET;

    abstract void a(int i, int i2, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    public abstract boolean intersects(int i, int i2, RectF rectF, RectF rectF2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void positionAttributes(View view, RectF rectF, RectF rectF2, float f, float f2, String[] strArr, float[] fArr);
}
