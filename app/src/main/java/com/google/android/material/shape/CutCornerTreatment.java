package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class CutCornerTreatment extends CornerTreatment {
    float a;

    public CutCornerTreatment() {
        this.a = -1.0f;
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.a = -1.0f;
        this.a = f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public void getCornerPath(@NonNull ShapePath shapePath, float f, float f2, float f3) {
        shapePath.reset(0.0f, f3 * f2, 180.0f, 180.0f - f);
        double d = f3;
        double d2 = f2;
        shapePath.lineTo((float) (Math.sin(Math.toRadians(f)) * d * d2), (float) (Math.sin(Math.toRadians(90.0f - f)) * d * d2));
    }
}
