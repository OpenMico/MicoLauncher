package com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;

/* loaded from: classes2.dex */
public class FitXYStrategy extends PreviewScalingStrategy {
    private static final String a = "FitXYStrategy";

    private static float a(float f) {
        return f < 1.0f ? 1.0f / f : f;
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    protected float getScore(Size size, Size size2) {
        if (size.width <= 0 || size.height <= 0) {
            return 0.0f;
        }
        float a2 = (1.0f / a((size.width * 1.0f) / size2.width)) / a((size.height * 1.0f) / size2.height);
        float a3 = a(((size.width * 1.0f) / size.height) / ((size2.width * 1.0f) / size2.height));
        return a2 * (((1.0f / a3) / a3) / a3);
    }

    @Override // com.journeyapps.barcodescanner.camera.PreviewScalingStrategy
    public Rect scalePreview(Size size, Size size2) {
        return new Rect(0, 0, size2.width, size2.height);
    }
}
