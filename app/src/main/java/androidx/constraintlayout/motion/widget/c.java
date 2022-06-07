package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MotionPaths.java */
/* loaded from: classes.dex */
public class c implements Comparable<c> {
    static String[] a = {"position", "x", "y", "width", "height", "pathRotate"};
    Easing b;
    int c;
    float d;
    float e;
    float f;
    float g;
    float h;
    float i;
    float j;
    float k;
    int l;
    LinkedHashMap<String, ConstraintAttribute> m;
    int n;
    double[] o;
    double[] p;

    private static final float a(float f, float f2, float f3, float f4, float f5, float f6) {
        return (((f5 - f3) * f2) - ((f6 - f4) * f)) + f3;
    }

    private static final float b(float f, float f2, float f3, float f4, float f5, float f6) {
        return ((f5 - f3) * f) + ((f6 - f4) * f2) + f4;
    }

    public c() {
        this.c = 0;
        this.j = Float.NaN;
        this.k = Float.NaN;
        this.l = Key.UNSET;
        this.m = new LinkedHashMap<>();
        this.n = 0;
        this.o = new double[18];
        this.p = new double[18];
    }

    void a(KeyPosition keyPosition, c cVar, c cVar2) {
        float f = keyPosition.a / 100.0f;
        this.d = f;
        this.c = keyPosition.g;
        float f2 = Float.isNaN(keyPosition.h) ? f : keyPosition.h;
        float f3 = Float.isNaN(keyPosition.i) ? f : keyPosition.i;
        float f4 = cVar2.h;
        float f5 = cVar.h;
        float f6 = cVar2.i;
        float f7 = cVar.i;
        this.e = this.d;
        float f8 = cVar.f;
        float f9 = cVar.g;
        float f10 = (cVar2.f + (f4 / 2.0f)) - ((f5 / 2.0f) + f8);
        float f11 = (cVar2.g + (f6 / 2.0f)) - (f9 + (f7 / 2.0f));
        float f12 = (f4 - f5) * f2;
        float f13 = f12 / 2.0f;
        this.f = (int) ((f8 + (f10 * f)) - f13);
        float f14 = (f6 - f7) * f3;
        float f15 = f14 / 2.0f;
        this.g = (int) ((f9 + (f11 * f)) - f15);
        this.h = (int) (f5 + f12);
        this.i = (int) (f7 + f14);
        float f16 = Float.isNaN(keyPosition.j) ? f : keyPosition.j;
        float f17 = 0.0f;
        float f18 = Float.isNaN(keyPosition.m) ? 0.0f : keyPosition.m;
        if (!Float.isNaN(keyPosition.k)) {
            f = keyPosition.k;
        }
        if (!Float.isNaN(keyPosition.l)) {
            f17 = keyPosition.l;
        }
        this.n = 2;
        this.f = (int) (((cVar.f + (f16 * f10)) + (f17 * f11)) - f13);
        this.g = (int) (((cVar.g + (f10 * f18)) + (f11 * f)) - f15);
        this.b = Easing.getInterpolator(keyPosition.e);
        this.l = keyPosition.f;
    }

    public c(int i, int i2, KeyPosition keyPosition, c cVar, c cVar2) {
        this.c = 0;
        this.j = Float.NaN;
        this.k = Float.NaN;
        this.l = Key.UNSET;
        this.m = new LinkedHashMap<>();
        this.n = 0;
        this.o = new double[18];
        this.p = new double[18];
        switch (keyPosition.n) {
            case 1:
                b(keyPosition, cVar, cVar2);
                return;
            case 2:
                a(i, i2, keyPosition, cVar, cVar2);
                return;
            default:
                a(keyPosition, cVar, cVar2);
                return;
        }
    }

    void a(int i, int i2, KeyPosition keyPosition, c cVar, c cVar2) {
        float f = keyPosition.a / 100.0f;
        this.d = f;
        this.c = keyPosition.g;
        float f2 = Float.isNaN(keyPosition.h) ? f : keyPosition.h;
        float f3 = Float.isNaN(keyPosition.i) ? f : keyPosition.i;
        float f4 = cVar2.h;
        float f5 = cVar.h;
        float f6 = cVar2.i;
        float f7 = cVar.i;
        this.e = this.d;
        float f8 = cVar.f;
        float f9 = cVar.g;
        float f10 = cVar2.f + (f4 / 2.0f);
        float f11 = cVar2.g + (f6 / 2.0f);
        float f12 = (f4 - f5) * f2;
        this.f = (int) ((f8 + ((f10 - ((f5 / 2.0f) + f8)) * f)) - (f12 / 2.0f));
        float f13 = (f6 - f7) * f3;
        this.g = (int) ((f9 + ((f11 - (f9 + (f7 / 2.0f))) * f)) - (f13 / 2.0f));
        this.h = (int) (f5 + f12);
        this.i = (int) (f7 + f13);
        this.n = 3;
        if (!Float.isNaN(keyPosition.j)) {
            this.f = (int) (keyPosition.j * ((int) (i - this.h)));
        }
        if (!Float.isNaN(keyPosition.k)) {
            this.g = (int) (keyPosition.k * ((int) (i2 - this.i)));
        }
        this.b = Easing.getInterpolator(keyPosition.e);
        this.l = keyPosition.f;
    }

    void b(KeyPosition keyPosition, c cVar, c cVar2) {
        float f = keyPosition.a / 100.0f;
        this.d = f;
        this.c = keyPosition.g;
        float f2 = Float.isNaN(keyPosition.h) ? f : keyPosition.h;
        float f3 = Float.isNaN(keyPosition.i) ? f : keyPosition.i;
        float f4 = cVar2.h - cVar.h;
        float f5 = cVar2.i - cVar.i;
        this.e = this.d;
        if (!Float.isNaN(keyPosition.j)) {
            f = keyPosition.j;
        }
        float f6 = cVar.f;
        float f7 = cVar.h;
        float f8 = cVar.g;
        float f9 = cVar.i;
        float f10 = (cVar2.f + (cVar2.h / 2.0f)) - ((f7 / 2.0f) + f6);
        float f11 = (cVar2.g + (cVar2.i / 2.0f)) - ((f9 / 2.0f) + f8);
        float f12 = f10 * f;
        float f13 = f4 * f2;
        float f14 = f13 / 2.0f;
        this.f = (int) ((f6 + f12) - f14);
        float f15 = f * f11;
        float f16 = f5 * f3;
        float f17 = f16 / 2.0f;
        this.g = (int) ((f8 + f15) - f17);
        this.h = (int) (f7 + f13);
        this.i = (int) (f9 + f16);
        float f18 = Float.isNaN(keyPosition.k) ? 0.0f : keyPosition.k;
        this.n = 1;
        this.f = (int) ((cVar.f + f12) - f14);
        this.g = (int) ((cVar.g + f15) - f17);
        this.f += (-f11) * f18;
        this.g += f10 * f18;
        this.b = Easing.getInterpolator(keyPosition.e);
        this.l = keyPosition.f;
    }

    private boolean a(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(c cVar, boolean[] zArr, String[] strArr, boolean z) {
        zArr[0] = zArr[0] | a(this.e, cVar.e);
        zArr[1] = zArr[1] | a(this.f, cVar.f) | z;
        zArr[2] = z | a(this.g, cVar.g) | zArr[2];
        zArr[3] = zArr[3] | a(this.h, cVar.h);
        zArr[4] = a(this.i, cVar.i) | zArr[4];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.f;
        float f2 = this.g;
        float f3 = this.h;
        float f4 = this.i;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f5 = (float) dArr[i2];
            switch (iArr[i2]) {
                case 1:
                    f = f5;
                    break;
                case 2:
                    f2 = f5;
                    break;
                case 3:
                    f3 = f5;
                    break;
                case 4:
                    f4 = f5;
                    break;
            }
        }
        fArr[i] = f + (f3 / 2.0f) + 0.0f;
        fArr[i + 1] = f2 + (f4 / 2.0f) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(View view, int[] iArr, double[] dArr, double[] dArr2, double[] dArr3) {
        float f = this.f;
        float f2 = this.g;
        float f3 = this.h;
        float f4 = this.i;
        if (iArr.length != 0 && this.o.length <= iArr[iArr.length - 1]) {
            int i = iArr[iArr.length - 1] + 1;
            this.o = new double[i];
            this.p = new double[i];
        }
        Arrays.fill(this.o, Double.NaN);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.o[iArr[i2]] = dArr[i2];
            this.p[iArr[i2]] = dArr2[i2];
        }
        float f5 = Float.NaN;
        float f6 = f;
        float f7 = f2;
        float f8 = f3;
        float f9 = f4;
        int i3 = 0;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = Float.NaN;
        while (true) {
            double[] dArr4 = this.o;
            if (i3 < dArr4.length) {
                double d = 0.0d;
                if (Double.isNaN(dArr4[i3])) {
                    if (dArr3 == null) {
                        f12 = f12;
                    } else if (dArr3[i3] == 0.0d) {
                        f12 = f12;
                    }
                    i3++;
                }
                if (dArr3 != null) {
                    d = dArr3[i3];
                }
                if (!Double.isNaN(this.o[i3])) {
                    d = this.o[i3] + d;
                }
                float f15 = (float) d;
                f12 = f12;
                float f16 = (float) this.p[i3];
                switch (i3) {
                    case 1:
                        f12 = f12;
                        f6 = f15;
                        f10 = f16;
                        continue;
                        i3++;
                    case 2:
                        f7 = f15;
                        f12 = f16;
                        continue;
                        i3++;
                    case 3:
                        f12 = f12;
                        f8 = f15;
                        f11 = f16;
                        continue;
                        i3++;
                    case 4:
                        f12 = f12;
                        f9 = f15;
                        f13 = f16;
                        continue;
                        i3++;
                    case 5:
                        f12 = f12;
                        f14 = f15;
                        continue;
                        i3++;
                }
                i3++;
            } else {
                if (!Float.isNaN(f14)) {
                    if (Float.isNaN(Float.NaN)) {
                        f5 = 0.0f;
                    }
                    view.setRotation((float) (f5 + f14 + Math.toDegrees(Math.atan2(f12 + (f13 / 2.0f), f10 + (f11 / 2.0f)))));
                } else if (!Float.isNaN(Float.NaN)) {
                    view.setRotation(Float.NaN);
                }
                float f17 = f6 + 0.5f;
                int i4 = (int) f17;
                float f18 = f7 + 0.5f;
                int i5 = (int) f18;
                int i6 = (int) (f17 + f8);
                int i7 = (int) (f18 + f9);
                int i8 = i6 - i4;
                int i9 = i7 - i5;
                if ((i8 == view.getMeasuredWidth() && i9 == view.getMeasuredHeight()) ? false : true) {
                    view.measure(View.MeasureSpec.makeMeasureSpec(i8, 1073741824), View.MeasureSpec.makeMeasureSpec(i9, 1073741824));
                }
                view.layout(i4, i5, i6, i7);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int[] iArr, double[] dArr, float[] fArr, int i) {
        float f = this.f;
        float f2 = this.g;
        float f3 = this.h;
        float f4 = this.i;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f5 = (float) dArr[i2];
            switch (iArr[i2]) {
                case 1:
                    f = f5;
                    break;
                case 2:
                    f2 = f5;
                    break;
                case 3:
                    f3 = f5;
                    break;
                case 4:
                    f4 = f5;
                    break;
            }
        }
        float f6 = f3 + f;
        float f7 = f4 + f2;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        int i3 = i + 1;
        fArr[i] = f + 0.0f;
        int i4 = i3 + 1;
        fArr[i3] = f2 + 0.0f;
        int i5 = i4 + 1;
        fArr[i4] = f6 + 0.0f;
        int i6 = i5 + 1;
        fArr[i5] = f2 + 0.0f;
        int i7 = i6 + 1;
        fArr[i6] = f6 + 0.0f;
        int i8 = i7 + 1;
        fArr[i7] = f7 + 0.0f;
        fArr[i8] = f + 0.0f;
        fArr[i8 + 1] = f7 + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f7 = (float) dArr[i];
            double d = dArr2[i];
            switch (iArr[i]) {
                case 1:
                    f3 = f7;
                    break;
                case 2:
                    f5 = f7;
                    break;
                case 3:
                    f4 = f7;
                    break;
                case 4:
                    f6 = f7;
                    break;
            }
        }
        float f8 = f3 - ((0.0f * f4) / 2.0f);
        float f9 = f5 - ((0.0f * f6) / 2.0f);
        fArr[0] = (f8 * (1.0f - f)) + (((f4 * 1.0f) + f8) * f) + 0.0f;
        fArr[1] = (f9 * (1.0f - f2)) + (((f6 * 1.0f) + f9) * f2) + 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(double[] dArr, int[] iArr) {
        float[] fArr = {this.e, this.f, this.g, this.h, this.i, this.j};
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] < fArr.length) {
                i++;
                dArr[i] = fArr[iArr[i2]];
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(String str) {
        return this.m.containsKey(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(String str) {
        return this.m.get(str).noOfInterpValues();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(String str, double[] dArr, int i) {
        ConstraintAttribute constraintAttribute = this.m.get(str);
        if (constraintAttribute.noOfInterpValues() == 1) {
            dArr[i] = constraintAttribute.getValueToInterpolate();
            return 1;
        }
        int noOfInterpValues = constraintAttribute.noOfInterpValues();
        float[] fArr = new float[noOfInterpValues];
        constraintAttribute.getValuesToInterpolate(fArr);
        for (int i2 = 0; i2 < noOfInterpValues; i2++) {
            i++;
            dArr[i] = fArr[i2];
        }
        return noOfInterpValues;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f, float f2, float f3, float f4) {
        this.f = f;
        this.g = f2;
        this.h = f3;
        this.i = f4;
    }

    /* renamed from: a */
    public int compareTo(@NonNull c cVar) {
        return Float.compare(this.e, cVar.e);
    }

    public void a(ConstraintSet.Constraint constraint) {
        this.b = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        this.l = constraint.motion.mPathMotionArc;
        this.j = constraint.motion.mPathRotate;
        this.c = constraint.motion.mDrawPath;
        this.k = constraint.propertySet.mProgress;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute.getType() != ConstraintAttribute.AttributeType.STRING_TYPE) {
                this.m.put(str, constraintAttribute);
            }
        }
    }
}
