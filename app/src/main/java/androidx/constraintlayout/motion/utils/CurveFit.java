package androidx.constraintlayout.motion.utils;

/* loaded from: classes.dex */
public abstract class CurveFit {
    public static final int CONSTANT = 2;
    public static final int LINEAR = 1;
    public static final int SPLINE = 0;

    public abstract double getPos(double d, int i);

    public abstract void getPos(double d, double[] dArr);

    public abstract void getPos(double d, float[] fArr);

    public abstract double getSlope(double d, int i);

    public abstract void getSlope(double d, double[] dArr);

    public abstract double[] getTimePoints();

    public static CurveFit get(int i, double[] dArr, double[][] dArr2) {
        if (dArr.length == 1) {
            i = 2;
        }
        if (i == 0) {
            return new MonotonicCurveFit(dArr, dArr2);
        }
        if (i != 2) {
            return new LinearCurveFit(dArr, dArr2);
        }
        return new a(dArr[0], dArr2[0]);
    }

    public static CurveFit getArc(int[] iArr, double[] dArr, double[][] dArr2) {
        return new a(iArr, dArr, dArr2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a extends CurveFit {
        double a;
        double[] b;

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public double getSlope(double d, int i) {
            return 0.0d;
        }

        a(double d, double[] dArr) {
            this.a = d;
            this.b = dArr;
        }

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public void getPos(double d, double[] dArr) {
            double[] dArr2 = this.b;
            System.arraycopy(dArr2, 0, dArr, 0, dArr2.length);
        }

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public void getPos(double d, float[] fArr) {
            int i = 0;
            while (true) {
                double[] dArr = this.b;
                if (i < dArr.length) {
                    fArr[i] = (float) dArr[i];
                    i++;
                } else {
                    return;
                }
            }
        }

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public double getPos(double d, int i) {
            return this.b[i];
        }

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public void getSlope(double d, double[] dArr) {
            for (int i = 0; i < this.b.length; i++) {
                dArr[i] = 0.0d;
            }
        }

        @Override // androidx.constraintlayout.motion.utils.CurveFit
        public double[] getTimePoints() {
            return new double[]{this.a};
        }
    }
}
