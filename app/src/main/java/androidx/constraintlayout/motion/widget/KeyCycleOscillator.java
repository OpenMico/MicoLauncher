package androidx.constraintlayout.motion.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    private CurveFit b;
    private c c;
    private String d;
    protected ConstraintAttribute mCustom;
    private int e = 0;
    public int mVariesBy = 0;
    ArrayList<o> a = new ArrayList<>();

    public abstract void setProperty(View view, float f2);

    public boolean variesByPath() {
        return this.mVariesBy == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class o {
        int a;
        float b;
        float c;
        float d;

        public o(int i, float f, float f2, float f3) {
            this.a = i;
            this.b = f3;
            this.c = f2;
            this.d = f;
        }
    }

    public String toString() {
        String str = this.d;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator<o> it = this.a.iterator();
        while (it.hasNext()) {
            o next = it.next();
            str = str + "[" + next.a + " , " + decimalFormat.format(next.b) + "] ";
        }
        return str;
    }

    public void setType(String str) {
        this.d = str;
    }

    public float get(float f2) {
        return (float) this.c.a(f2);
    }

    public float getSlope(float f2) {
        return (float) this.c.b(f2);
    }

    public CurveFit getCurveFit() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static KeyCycleOscillator a(String str) {
        if (str.startsWith("CUSTOM")) {
            return new b();
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1249320806:
                if (str.equals("rotationX")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1249320805:
                if (str.equals("rotationY")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1225497657:
                if (str.equals("translationX")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c2 = 11;
                    break;
                }
                break;
            case -1225497655:
                if (str.equals("translationZ")) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c2 = '\r';
                    break;
                }
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c2 = 6;
                    break;
                }
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c2 = 7;
                    break;
                }
                break;
            case -797520672:
                if (str.equals("waveVariesBy")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    c2 = 2;
                    break;
                }
                break;
            case -4379043:
                if (str.equals("elevation")) {
                    c2 = 1;
                    break;
                }
                break;
            case 37232917:
                if (str.equals("transitionPathRotate")) {
                    c2 = 5;
                    break;
                }
                break;
            case 92909918:
                if (str.equals("alpha")) {
                    c2 = 0;
                    break;
                }
                break;
            case 156108012:
                if (str.equals("waveOffset")) {
                    c2 = '\b';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return new a();
            case 1:
                return new d();
            case 2:
                return new g();
            case 3:
                return new h();
            case 4:
                return new i();
            case 5:
                return new e();
            case 6:
                return new j();
            case 7:
                return new k();
            case '\b':
                return new a();
            case '\t':
                return new a();
            case '\n':
                return new l();
            case 11:
                return new m();
            case '\f':
                return new n();
            case '\r':
                return new f();
            default:
                return null;
        }
    }

    public void setPoint(int i2, int i3, int i4, float f2, float f3, float f4, ConstraintAttribute constraintAttribute) {
        this.a.add(new o(i2, f2, f3, f4));
        if (i4 != -1) {
            this.mVariesBy = i4;
        }
        this.e = i3;
        this.mCustom = constraintAttribute;
    }

    public void setPoint(int i2, int i3, int i4, float f2, float f3, float f4) {
        this.a.add(new o(i2, f2, f3, f4));
        if (i4 != -1) {
            this.mVariesBy = i4;
        }
        this.e = i3;
    }

    @TargetApi(19)
    public void setup(float f2) {
        int size = this.a.size();
        if (size != 0) {
            Collections.sort(this.a, new Comparator<o>() { // from class: androidx.constraintlayout.motion.widget.KeyCycleOscillator.1
                /* renamed from: a */
                public int compare(o oVar, o oVar2) {
                    return Integer.compare(oVar.a, oVar2.a);
                }
            });
            double[] dArr = new double[size];
            double[][] dArr2 = (double[][]) Array.newInstance(double.class, size, 2);
            this.c = new c(this.e, this.mVariesBy, size);
            Iterator<o> it = this.a.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                o next = it.next();
                dArr[i2] = next.d * 0.01d;
                dArr2[i2][0] = next.b;
                dArr2[i2][1] = next.c;
                this.c.a(i2, next.a, next.d, next.c, next.b);
                i2++;
            }
            this.c.c(f2);
            this.b = CurveFit.get(0, dArr, dArr2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d extends KeyCycleOscillator {
        d() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setElevation(get(f));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends KeyCycleOscillator {
        a() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setAlpha(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class g extends KeyCycleOscillator {
        g() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setRotation(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class h extends KeyCycleOscillator {
        h() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setRotationX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class i extends KeyCycleOscillator {
        i() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setRotationY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class e extends KeyCycleOscillator {
        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
        }

        e() {
        }

        public void a(View view, float f, double d, double d2) {
            view.setRotation(get(f) + ((float) Math.toDegrees(Math.atan2(d2, d))));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class j extends KeyCycleOscillator {
        j() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setScaleX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class k extends KeyCycleOscillator {
        k() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setScaleY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class l extends KeyCycleOscillator {
        l() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setTranslationX(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class m extends KeyCycleOscillator {
        m() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            view.setTranslationY(get(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class n extends KeyCycleOscillator {
        n() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(get(f));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b extends KeyCycleOscillator {
        float[] b = new float[1];

        b() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            this.b[0] = get(f);
            this.mCustom.setInterpolatedValue(view, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class f extends KeyCycleOscillator {
        boolean b = false;

        f() {
        }

        @Override // androidx.constraintlayout.motion.widget.KeyCycleOscillator
        public void setProperty(View view, float f) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(f));
            } else if (!this.b) {
                Method method = null;
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    this.b = true;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(get(f)));
                    } catch (IllegalAccessException e) {
                        Log.e("KeyCycleOscillator", "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e("KeyCycleOscillator", "unable to setProgress", e2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c {
        float[] b;
        double[] c;
        float[] d;
        float[] e;
        float[] f;
        int g;
        CurveFit h;
        double[] i;
        double[] j;
        float k;
        private final int m;
        Oscillator a = new Oscillator();
        public HashMap<String, ConstraintAttribute> l = new HashMap<>();

        c(int i, int i2, int i3) {
            this.g = i;
            this.m = i2;
            this.a.setType(i);
            this.b = new float[i3];
            this.c = new double[i3];
            this.d = new float[i3];
            this.e = new float[i3];
            this.f = new float[i3];
        }

        public double a(float f) {
            CurveFit curveFit = this.h;
            if (curveFit != null) {
                curveFit.getPos(f, this.i);
            } else {
                double[] dArr = this.i;
                dArr[0] = this.e[0];
                dArr[1] = this.b[0];
            }
            return this.i[0] + (this.a.getValue(f) * this.i[1]);
        }

        public double b(float f) {
            CurveFit curveFit = this.h;
            if (curveFit != null) {
                double d = f;
                curveFit.getSlope(d, this.j);
                this.h.getPos(d, this.i);
            } else {
                double[] dArr = this.j;
                dArr[0] = 0.0d;
                dArr[1] = 0.0d;
            }
            double d2 = f;
            double value = this.a.getValue(d2);
            double slope = this.a.getSlope(d2);
            double[] dArr2 = this.j;
            return dArr2[0] + (value * dArr2[1]) + (slope * this.i[1]);
        }

        public void a(int i, int i2, float f, float f2, float f3) {
            this.c[i] = i2 / 100.0d;
            this.d[i] = f;
            this.e[i] = f2;
            this.b[i] = f3;
        }

        public void c(float f) {
            this.k = f;
            double[][] dArr = (double[][]) Array.newInstance(double.class, this.c.length, 2);
            float[] fArr = this.b;
            this.i = new double[fArr.length + 1];
            this.j = new double[fArr.length + 1];
            if (this.c[0] > 0.0d) {
                this.a.addPoint(0.0d, this.d[0]);
            }
            double[] dArr2 = this.c;
            int length = dArr2.length - 1;
            if (dArr2[length] < 1.0d) {
                this.a.addPoint(1.0d, this.d[length]);
            }
            for (int i = 0; i < dArr.length; i++) {
                dArr[i][0] = this.e[i];
                int i2 = 0;
                while (true) {
                    float[] fArr2 = this.b;
                    if (i2 < fArr2.length) {
                        dArr[i2][1] = fArr2[i2];
                        i2++;
                    }
                }
                this.a.addPoint(this.c[i], this.d[i]);
            }
            this.a.normalize();
            double[] dArr3 = this.c;
            if (dArr3.length > 1) {
                this.h = CurveFit.get(0, dArr3, dArr);
            } else {
                this.h = null;
            }
        }
    }
}
