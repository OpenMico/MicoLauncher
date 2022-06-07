package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;

/* loaded from: classes.dex */
public class VelocityMatrix {
    private static String g = "VelocityMatrix";
    float a;
    float b;
    float c;
    float d;
    float e;
    float f;

    public void clear() {
        this.e = 0.0f;
        this.d = 0.0f;
        this.c = 0.0f;
        this.b = 0.0f;
        this.a = 0.0f;
    }

    public void setRotationVelocity(SplineSet splineSet, float f) {
        if (splineSet != null) {
            this.e = splineSet.getSlope(f);
            this.f = splineSet.get(f);
        }
    }

    public void setTranslationVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.c = splineSet.getSlope(f);
        }
        if (splineSet2 != null) {
            this.d = splineSet2.getSlope(f);
        }
    }

    public void setScaleVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.a = splineSet.getSlope(f);
        }
        if (splineSet2 != null) {
            this.b = splineSet2.getSlope(f);
        }
    }

    public void setRotationVelocity(KeyCycleOscillator keyCycleOscillator, float f) {
        if (keyCycleOscillator != null) {
            this.e = keyCycleOscillator.getSlope(f);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f) {
        if (keyCycleOscillator != null) {
            this.c = keyCycleOscillator.getSlope(f);
        }
        if (keyCycleOscillator2 != null) {
            this.d = keyCycleOscillator2.getSlope(f);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f) {
        if (keyCycleOscillator != null || keyCycleOscillator2 != null) {
            if (keyCycleOscillator == null) {
                this.a = keyCycleOscillator.getSlope(f);
            }
            if (keyCycleOscillator2 == null) {
                this.b = keyCycleOscillator2.getSlope(f);
            }
        }
    }

    public void applyTransform(float f, float f2, int i, int i2, float[] fArr) {
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f - 0.5f) * 2.0f;
        float f6 = (f2 - 0.5f) * 2.0f;
        float f7 = f3 + this.c;
        float f8 = f4 + this.d;
        float f9 = f7 + (this.a * f5);
        float f10 = f8 + (this.b * f6);
        float radians = (float) Math.toRadians(this.e);
        double radians2 = (float) Math.toRadians(this.f);
        double d = i2 * f6;
        fArr[0] = f9 + (((float) ((((-i) * f5) * Math.sin(radians2)) - (Math.cos(radians2) * d))) * radians);
        fArr[1] = f10 + (radians * ((float) (((i * f5) * Math.cos(radians2)) - (d * Math.sin(radians2)))));
    }
}
