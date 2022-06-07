package androidx.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ArcMotion extends PathMotion {
    private static final float a = (float) Math.tan(Math.toRadians(35.0d));
    private float b = 0.0f;
    private float c = 0.0f;
    private float d = 70.0f;
    private float e = 0.0f;
    private float f = 0.0f;
    private float g = a;

    public ArcMotion() {
    }

    @SuppressLint({"RestrictedApi"})
    public ArcMotion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.j);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "maximumAngle", 2, 70.0f));
        obtainStyledAttributes.recycle();
    }

    public void setMinimumHorizontalAngle(float f) {
        this.b = f;
        this.e = a(f);
    }

    public float getMinimumHorizontalAngle() {
        return this.b;
    }

    public void setMinimumVerticalAngle(float f) {
        this.c = f;
        this.f = a(f);
    }

    public float getMinimumVerticalAngle() {
        return this.c;
    }

    public void setMaximumAngle(float f) {
        this.d = f;
        this.g = a(f);
    }

    public float getMaximumAngle() {
        return this.d;
    }

    private static float a(float f) {
        if (f >= 0.0f && f <= 90.0f) {
            return (float) Math.tan(Math.toRadians(f / 2.0f));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    @Override // androidx.transition.PathMotion
    public Path getPath(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7;
        Path path = new Path();
        path.moveTo(f, f2);
        float f8 = f3 - f;
        float f9 = f4 - f2;
        float f10 = (f8 * f8) + (f9 * f9);
        float f11 = (f + f3) / 2.0f;
        float f12 = (f2 + f4) / 2.0f;
        float f13 = 0.25f * f10;
        boolean z = f2 > f4;
        if (Math.abs(f8) < Math.abs(f9)) {
            float abs = Math.abs(f10 / (f9 * 2.0f));
            if (z) {
                f6 = abs + f4;
                f7 = f3;
            } else {
                f6 = abs + f2;
                f7 = f;
            }
            float f14 = this.f;
            f5 = f13 * f14 * f14;
        } else {
            float f15 = f10 / (f8 * 2.0f);
            if (z) {
                f7 = f15 + f;
                f6 = f2;
            } else {
                f7 = f3 - f15;
                f6 = f4;
            }
            float f16 = this.e;
            f5 = f13 * f16 * f16;
        }
        float f17 = f11 - f7;
        float f18 = f12 - f6;
        float f19 = (f17 * f17) + (f18 * f18);
        float f20 = this.g;
        float f21 = f13 * f20 * f20;
        if (f19 < f5) {
            f21 = f5;
        } else if (f19 <= f21) {
            f21 = 0.0f;
        }
        if (f21 != 0.0f) {
            float sqrt = (float) Math.sqrt(f21 / f19);
            f7 = ((f7 - f11) * sqrt) + f11;
            f6 = f12 + (sqrt * (f6 - f12));
        }
        path.cubicTo((f + f7) / 2.0f, (f2 + f6) / 2.0f, (f7 + f3) / 2.0f, (f6 + f4) / 2.0f, f3, f4);
        return path;
    }
}
