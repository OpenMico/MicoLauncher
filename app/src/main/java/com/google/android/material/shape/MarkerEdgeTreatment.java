package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public final class MarkerEdgeTreatment extends EdgeTreatment {
    private final float a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.shape.EdgeTreatment
    public boolean d() {
        return true;
    }

    public MarkerEdgeTreatment(float f) {
        this.a = f - 0.001f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f, float f2, float f3, @NonNull ShapePath shapePath) {
        float sqrt = (float) ((this.a * Math.sqrt(2.0d)) / 2.0d);
        float sqrt2 = (float) Math.sqrt(Math.pow(this.a, 2.0d) - Math.pow(sqrt, 2.0d));
        shapePath.reset(f2 - sqrt, ((float) (-((this.a * Math.sqrt(2.0d)) - this.a))) + sqrt2);
        shapePath.lineTo(f2, (float) (-((this.a * Math.sqrt(2.0d)) - this.a)));
        shapePath.lineTo(f2 + sqrt, ((float) (-((this.a * Math.sqrt(2.0d)) - this.a))) + sqrt2);
    }
}
