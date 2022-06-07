package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SplineSet {
    private int a;
    private String b;
    protected CurveFit mCurveFit;
    protected int[] mTimePoints = new int[10];
    protected float[] mValues = new float[10];

    public abstract void setProperty(View view, float f2);

    public String toString() {
        String str = this.b;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.a; i2++) {
            str = str + "[" + this.mTimePoints[i2] + " , " + decimalFormat.format(this.mValues[i2]) + "] ";
        }
        return str;
    }

    public void setType(String str) {
        this.b = str;
    }

    public float get(float f2) {
        return (float) this.mCurveFit.getPos(f2, 0);
    }

    public float getSlope(float f2) {
        return (float) this.mCurveFit.getSlope(f2, 0);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SplineSet a(String str, SparseArray<ConstraintAttribute> sparseArray) {
        return new b(str, sparseArray);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static SplineSet a(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1249320806:
                if (str.equals("rotationX")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1249320805:
                if (str.equals("rotationY")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1225497657:
                if (str.equals("translationX")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case -1225497655:
                if (str.equals("translationZ")) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -797520672:
                if (str.equals("waveVariesBy")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -760884510:
                if (str.equals("transformPivotX")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -760884509:
                if (str.equals("transformPivotY")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -4379043:
                if (str.equals("elevation")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 37232917:
                if (str.equals("transitionPathRotate")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 92909918:
                if (str.equals("alpha")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 156108012:
                if (str.equals("waveOffset")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                return new a();
            case 1:
                return new c();
            case 2:
                return new h();
            case 3:
                return new i();
            case 4:
                return new j();
            case 5:
                return new e();
            case 6:
                return new f();
            case 7:
                return new d();
            case '\b':
                return new k();
            case '\t':
                return new l();
            case '\n':
                return new a();
            case 11:
                return new a();
            case '\f':
                return new n();
            case '\r':
                return new o();
            case 14:
                return new p();
            case 15:
                return new g();
            default:
                return null;
        }
    }

    public void setPoint(int i2, float f2) {
        int[] iArr = this.mTimePoints;
        if (iArr.length < this.a + 1) {
            this.mTimePoints = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.mValues;
            this.mValues = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.mTimePoints;
        int i3 = this.a;
        iArr2[i3] = i2;
        this.mValues[i3] = f2;
        this.a = i3 + 1;
    }

    public void setup(int i2) {
        int i3 = this.a;
        if (i3 != 0) {
            m.a(this.mTimePoints, this.mValues, 0, i3 - 1);
            int i4 = 1;
            for (int i5 = 1; i5 < this.a; i5++) {
                int[] iArr = this.mTimePoints;
                if (iArr[i5 - 1] != iArr[i5]) {
                    i4++;
                }
            }
            double[] dArr = new double[i4];
            double[][] dArr2 = (double[][]) Array.newInstance(double.class, i4, 1);
            int i6 = 0;
            for (int i7 = 0; i7 < this.a; i7++) {
                if (i7 > 0) {
                    int[] iArr2 = this.mTimePoints;
                    if (iArr2[i7] == iArr2[i7 - 1]) {
                    }
                }
                dArr[i6] = this.mTimePoints[i7] * 0.01d;
                dArr2[i6][0] = this.mValues[i7];
                i6++;
            }
            this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c extends SplineSet {
        c() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setElevation(get(f));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends SplineSet {
        a() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setAlpha(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class h extends SplineSet {
        h() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setRotation(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class i extends SplineSet {
        i() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setRotationX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class j extends SplineSet {
        j() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setRotationY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class e extends SplineSet {
        e() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setPivotX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class f extends SplineSet {
        f() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setPivotY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d extends SplineSet {
        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
        }

        d() {
        }

        public void a(View view, float f, double d, double d2) {
            view.setRotation(get(f) + ((float) Math.toDegrees(Math.atan2(d2, d))));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class k extends SplineSet {
        k() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setScaleX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class l extends SplineSet {
        l() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setScaleY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class n extends SplineSet {
        n() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setTranslationX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class o extends SplineSet {
        o() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            view.setTranslationY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class p extends SplineSet {
        p() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(get(f));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b extends SplineSet {
        String a;
        SparseArray<ConstraintAttribute> b;
        float[] c;

        public b(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.a = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)[1];
            this.b = sparseArray;
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setup(int i) {
            int size = this.b.size();
            int noOfInterpValues = this.b.valueAt(0).noOfInterpValues();
            double[] dArr = new double[size];
            this.c = new float[noOfInterpValues];
            double[][] dArr2 = (double[][]) Array.newInstance(double.class, size, noOfInterpValues);
            for (int i2 = 0; i2 < size; i2++) {
                dArr[i2] = this.b.keyAt(i2) * 0.01d;
                this.b.valueAt(i2).getValuesToInterpolate(this.c);
                int i3 = 0;
                while (true) {
                    float[] fArr = this.c;
                    if (i3 < fArr.length) {
                        dArr2[i2][i3] = fArr[i3];
                        i3++;
                    }
                }
            }
            this.mCurveFit = CurveFit.get(i, dArr, dArr2);
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setPoint(int i, float f) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        public void a(int i, ConstraintAttribute constraintAttribute) {
            this.b.append(i, constraintAttribute);
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            this.mCurveFit.getPos(f, this.c);
            this.b.valueAt(0).setInterpolatedValue(view, this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class g extends SplineSet {
        boolean a = false;

        g() {
        }

        @Override // androidx.constraintlayout.motion.widget.SplineSet
        public void setProperty(View view, float f) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f));
            } else if (!this.a) {
                Method method = null;
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.a = true;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(get(f)));
                    } catch (IllegalAccessException e) {
                        Log.e("SplineSet", "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e("SplineSet", "unable to setProgress", e2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class m {
        static void a(int[] iArr, float[] fArr, int i, int i2) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i2;
            iArr2[1] = i;
            int i3 = 2;
            while (i3 > 0) {
                int i4 = i3 - 1;
                int i5 = iArr2[i4];
                i3 = i4 - 1;
                int i6 = iArr2[i3];
                if (i5 < i6) {
                    int b = b(iArr, fArr, i5, i6);
                    int i7 = i3 + 1;
                    iArr2[i3] = b - 1;
                    int i8 = i7 + 1;
                    iArr2[i7] = i5;
                    int i9 = i8 + 1;
                    iArr2[i8] = i6;
                    i3 = i9 + 1;
                    iArr2[i9] = b + 1;
                }
            }
        }

        private static int b(int[] iArr, float[] fArr, int i, int i2) {
            int i3 = iArr[i2];
            int i4 = i;
            while (i < i2) {
                if (iArr[i] <= i3) {
                    c(iArr, fArr, i4, i);
                    i4++;
                }
                i++;
            }
            c(iArr, fArr, i4, i2);
            return i4;
        }

        private static void c(int[] iArr, float[] fArr, int i, int i2) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
            float f = fArr[i];
            fArr[i] = fArr[i2];
            fArr[i2] = f;
        }
    }
}
