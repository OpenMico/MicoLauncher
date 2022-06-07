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

/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    private static float f = 6.2831855f;
    long a;
    private int c;
    private String d;
    protected CurveFit mCurveFit;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance(float.class, 10, 3);
    private float[] e = new float[3];
    protected boolean mContinue = false;
    float b = Float.NaN;

    public abstract boolean setProperty(View view, float f2, long j2, KeyCache keyCache);

    public String toString() {
        String str = this.d;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i2 = 0; i2 < this.c; i2++) {
            str = str + "[" + this.mTimePoints[i2] + " , " + decimalFormat.format(this.mValues[i2]) + "] ";
        }
        return str;
    }

    public void setType(String str) {
        this.d = str;
    }

    public float get(float f2, long j2, View view, KeyCache keyCache) {
        this.mCurveFit.getPos(f2, this.e);
        float[] fArr = this.e;
        float f3 = fArr[1];
        int i2 = (f3 > 0.0f ? 1 : (f3 == 0.0f ? 0 : -1));
        boolean z = false;
        if (i2 == 0) {
            this.mContinue = false;
            return fArr[2];
        }
        if (Float.isNaN(this.b)) {
            this.b = keyCache.a(view, this.d, 0);
            if (Float.isNaN(this.b)) {
                this.b = 0.0f;
            }
        }
        this.b = (float) ((this.b + (((j2 - this.a) * 1.0E-9d) * f3)) % 1.0d);
        keyCache.a(view, this.d, 0, this.b);
        this.a = j2;
        float f4 = this.e[0];
        float calcWave = (calcWave(this.b) * f4) + this.e[2];
        if (!(f4 == 0.0f && i2 == 0)) {
            z = true;
        }
        this.mContinue = z;
        return calcWave;
    }

    protected float calcWave(float f2) {
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(f2 * f);
            case 2:
                return 1.0f - Math.abs(f2);
            case 3:
                return (((f2 * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                return 1.0f - (((f2 * 2.0f) + 1.0f) % 2.0f);
            case 5:
                return (float) Math.cos(f2 * f);
            case 6:
                float abs = 1.0f - Math.abs(((f2 * 4.0f) % 4.0f) - 2.0f);
                return 1.0f - (abs * abs);
            default:
                return (float) Math.sin(f2 * f);
        }
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TimeCycleSplineSet a(String str, SparseArray<ConstraintAttribute> sparseArray) {
        return new b(str, sparseArray);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static TimeCycleSplineSet a(String str, long j2) {
        char c2;
        TimeCycleSplineSet timeCycleSplineSet;
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
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1225497655:
                if (str.equals("translationZ")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c2 = 7;
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
                    c2 = 5;
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
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                timeCycleSplineSet = new a();
                break;
            case 1:
                timeCycleSplineSet = new c();
                break;
            case 2:
                timeCycleSplineSet = new f();
                break;
            case 3:
                timeCycleSplineSet = new g();
                break;
            case 4:
                timeCycleSplineSet = new h();
                break;
            case 5:
                timeCycleSplineSet = new d();
                break;
            case 6:
                timeCycleSplineSet = new i();
                break;
            case 7:
                timeCycleSplineSet = new j();
                break;
            case '\b':
                timeCycleSplineSet = new l();
                break;
            case '\t':
                timeCycleSplineSet = new m();
                break;
            case '\n':
                timeCycleSplineSet = new n();
                break;
            case 11:
                timeCycleSplineSet = new e();
                break;
            default:
                return null;
        }
        timeCycleSplineSet.setStartTime(j2);
        return timeCycleSplineSet;
    }

    protected void setStartTime(long j2) {
        this.a = j2;
    }

    public void setPoint(int i2, float f2, float f3, int i3, float f4) {
        int[] iArr = this.mTimePoints;
        int i4 = this.c;
        iArr[i4] = i2;
        float[][] fArr = this.mValues;
        fArr[i4][0] = f2;
        fArr[i4][1] = f3;
        fArr[i4][2] = f4;
        this.mWaveShape = Math.max(this.mWaveShape, i3);
        this.c++;
    }

    public void setup(int i2) {
        int i3 = this.c;
        if (i3 == 0) {
            Log.e("SplineSet", "Error no points added to " + this.d);
            return;
        }
        k.a(this.mTimePoints, this.mValues, 0, i3 - 1);
        int i4 = 1;
        int i5 = 0;
        while (true) {
            int[] iArr = this.mTimePoints;
            if (i4 >= iArr.length) {
                break;
            }
            if (iArr[i4] != iArr[i4 - 1]) {
                i5++;
            }
            i4++;
        }
        if (i5 == 0) {
            i5 = 1;
        }
        double[] dArr = new double[i5];
        double[][] dArr2 = (double[][]) Array.newInstance(double.class, i5, 3);
        int i6 = 0;
        for (int i7 = 0; i7 < this.c; i7++) {
            if (i7 > 0) {
                int[] iArr2 = this.mTimePoints;
                if (iArr2[i7] == iArr2[i7 - 1]) {
                }
            }
            dArr[i6] = this.mTimePoints[i7] * 0.01d;
            double[] dArr3 = dArr2[i6];
            float[][] fArr = this.mValues;
            dArr3[0] = fArr[i7][0];
            dArr2[i6][1] = fArr[i7][1];
            dArr2[i6][2] = fArr[i7][2];
            i6++;
        }
        this.mCurveFit = CurveFit.get(i2, dArr, dArr2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c extends TimeCycleSplineSet {
        c() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setElevation(get(f, j, view, keyCache));
            }
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends TimeCycleSplineSet {
        a() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setAlpha(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class f extends TimeCycleSplineSet {
        f() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotation(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class g extends TimeCycleSplineSet {
        g() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class h extends TimeCycleSplineSet {
        h() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d extends TimeCycleSplineSet {
        d() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            return this.mContinue;
        }

        public boolean a(View view, KeyCache keyCache, float f, long j, double d, double d2) {
            view.setRotation(get(f, j, view, keyCache) + ((float) Math.toDegrees(Math.atan2(d2, d))));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class i extends TimeCycleSplineSet {
        i() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setScaleX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class j extends TimeCycleSplineSet {
        j() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setScaleY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class l extends TimeCycleSplineSet {
        l() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setTranslationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class m extends TimeCycleSplineSet {
        m() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setTranslationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class n extends TimeCycleSplineSet {
        n() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(get(f, j, view, keyCache));
            }
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b extends TimeCycleSplineSet {
        String c;
        SparseArray<ConstraintAttribute> d;
        SparseArray<float[]> e = new SparseArray<>();
        float[] f;
        float[] g;

        public b(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.c = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)[1];
            this.d = sparseArray;
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setup(int i) {
            int size = this.d.size();
            int noOfInterpValues = this.d.valueAt(0).noOfInterpValues();
            double[] dArr = new double[size];
            int i2 = noOfInterpValues + 2;
            this.f = new float[i2];
            this.g = new float[noOfInterpValues];
            double[][] dArr2 = (double[][]) Array.newInstance(double.class, size, i2);
            for (int i3 = 0; i3 < size; i3++) {
                int keyAt = this.d.keyAt(i3);
                float[] valueAt = this.e.valueAt(i3);
                dArr[i3] = keyAt * 0.01d;
                this.d.valueAt(i3).getValuesToInterpolate(this.f);
                int i4 = 0;
                while (true) {
                    float[] fArr = this.f;
                    if (i4 < fArr.length) {
                        dArr2[i3][i4] = fArr[i4];
                        i4++;
                    }
                }
                dArr2[i3][noOfInterpValues] = valueAt[0];
                dArr2[i3][noOfInterpValues + 1] = valueAt[1];
            }
            this.mCurveFit = CurveFit.get(i, dArr, dArr2);
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setPoint(int i, float f, float f2, int i2, float f3) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void a(int i, ConstraintAttribute constraintAttribute, float f, int i2, float f2) {
            this.d.append(i, constraintAttribute);
            this.e.append(i, new float[]{f, f2});
            this.mWaveShape = Math.max(this.mWaveShape, i2);
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            this.mCurveFit.getPos(f, this.f);
            float[] fArr = this.f;
            float f2 = fArr[fArr.length - 2];
            float f3 = fArr[fArr.length - 1];
            long j2 = j - this.a;
            if (Float.isNaN(this.b)) {
                this.b = keyCache.a(view, this.c, 0);
                if (Float.isNaN(this.b)) {
                    this.b = 0.0f;
                }
            }
            this.b = (float) ((this.b + ((j2 * 1.0E-9d) * f2)) % 1.0d);
            this.a = j;
            float calcWave = calcWave(this.b);
            this.mContinue = false;
            for (int i = 0; i < this.g.length; i++) {
                this.mContinue |= ((double) this.f[i]) != 0.0d;
                this.g[i] = (this.f[i] * calcWave) + f3;
            }
            this.d.valueAt(0).setInterpolatedValue(view, this.g);
            if (f2 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class e extends TimeCycleSplineSet {
        boolean c = false;

        e() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f, j, view, keyCache));
            } else if (this.c) {
                return false;
            } else {
                Method method = null;
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.c = true;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(get(f, j, view, keyCache)));
                    } catch (IllegalAccessException e) {
                        Log.e("SplineSet", "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e("SplineSet", "unable to setProgress", e2);
                    }
                }
            }
            return this.mContinue;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class k {
        static void a(int[] iArr, float[][] fArr, int i, int i2) {
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

        private static int b(int[] iArr, float[][] fArr, int i, int i2) {
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

        private static void c(int[] iArr, float[][] fArr, int i, int i2) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
            float[] fArr2 = fArr[i];
            fArr[i] = fArr[i2];
            fArr[i2] = fArr2;
        }
    }
}
