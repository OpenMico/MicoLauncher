package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

/* compiled from: ArcCurveFit.java */
/* loaded from: classes.dex */
class a extends CurveFit {
    C0005a[] a;
    private final double[] b;

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, double[] dArr) {
        if (d < this.a[0].c) {
            d = this.a[0].c;
        }
        C0005a[] aVarArr = this.a;
        if (d > aVarArr[aVarArr.length - 1].d) {
            C0005a[] aVarArr2 = this.a;
            d = aVarArr2[aVarArr2.length - 1].d;
        }
        int i = 0;
        while (true) {
            C0005a[] aVarArr3 = this.a;
            if (i >= aVarArr3.length) {
                return;
            }
            if (d > aVarArr3[i].d) {
                i++;
            } else if (this.a[i].r) {
                dArr[0] = this.a[i].b(d);
                dArr[1] = this.a[i].c(d);
                return;
            } else {
                this.a[i].a(d);
                dArr[0] = this.a[i].a();
                dArr[1] = this.a[i].b();
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, float[] fArr) {
        if (d < this.a[0].c) {
            d = this.a[0].c;
        } else {
            C0005a[] aVarArr = this.a;
            if (d > aVarArr[aVarArr.length - 1].d) {
                C0005a[] aVarArr2 = this.a;
                d = aVarArr2[aVarArr2.length - 1].d;
            }
        }
        int i = 0;
        while (true) {
            C0005a[] aVarArr3 = this.a;
            if (i >= aVarArr3.length) {
                return;
            }
            if (d > aVarArr3[i].d) {
                i++;
            } else if (this.a[i].r) {
                fArr[0] = (float) this.a[i].b(d);
                fArr[1] = (float) this.a[i].c(d);
                return;
            } else {
                this.a[i].a(d);
                fArr[0] = (float) this.a[i].a();
                fArr[1] = (float) this.a[i].b();
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getSlope(double d, double[] dArr) {
        if (d < this.a[0].c) {
            d = this.a[0].c;
        } else {
            C0005a[] aVarArr = this.a;
            if (d > aVarArr[aVarArr.length - 1].d) {
                C0005a[] aVarArr2 = this.a;
                d = aVarArr2[aVarArr2.length - 1].d;
            }
        }
        int i = 0;
        while (true) {
            C0005a[] aVarArr3 = this.a;
            if (i >= aVarArr3.length) {
                return;
            }
            if (d > aVarArr3[i].d) {
                i++;
            } else if (this.a[i].r) {
                dArr[0] = this.a[i].d(d);
                dArr[1] = this.a[i].e(d);
                return;
            } else {
                this.a[i].a(d);
                dArr[0] = this.a[i].c();
                dArr[1] = this.a[i].d();
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double getPos(double d, int i) {
        int i2 = 0;
        if (d < this.a[0].c) {
            d = this.a[0].c;
        } else {
            C0005a[] aVarArr = this.a;
            if (d > aVarArr[aVarArr.length - 1].d) {
                C0005a[] aVarArr2 = this.a;
                d = aVarArr2[aVarArr2.length - 1].d;
            }
        }
        while (true) {
            C0005a[] aVarArr3 = this.a;
            if (i2 >= aVarArr3.length) {
                return Double.NaN;
            }
            if (d > aVarArr3[i2].d) {
                i2++;
            } else if (!this.a[i2].r) {
                this.a[i2].a(d);
                if (i == 0) {
                    return this.a[i2].a();
                }
                return this.a[i2].b();
            } else if (i == 0) {
                return this.a[i2].b(d);
            } else {
                return this.a[i2].c(d);
            }
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double getSlope(double d, int i) {
        int i2 = 0;
        if (d < this.a[0].c) {
            d = this.a[0].c;
        }
        C0005a[] aVarArr = this.a;
        if (d > aVarArr[aVarArr.length - 1].d) {
            C0005a[] aVarArr2 = this.a;
            d = aVarArr2[aVarArr2.length - 1].d;
        }
        while (true) {
            C0005a[] aVarArr3 = this.a;
            if (i2 >= aVarArr3.length) {
                return Double.NaN;
            }
            if (d > aVarArr3[i2].d) {
                i2++;
            } else if (!this.a[i2].r) {
                this.a[i2].a(d);
                if (i == 0) {
                    return this.a[i2].c();
                }
                return this.a[i2].d();
            } else if (i == 0) {
                return this.a[i2].d(d);
            } else {
                return this.a[i2].e(d);
            }
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.b;
    }

    public a(int[] iArr, double[] dArr, double[][] dArr2) {
        this.b = dArr;
        this.a = new C0005a[dArr.length - 1];
        int i = 0;
        int i2 = 1;
        int i3 = 1;
        while (i < this.a.length) {
            i2 = 2;
            switch (iArr[i]) {
                case 0:
                    i3 = 3;
                    break;
                case 1:
                    i2 = 1;
                    i3 = 1;
                    break;
                case 2:
                    i2 = 2;
                    i3 = 2;
                    break;
                case 3:
                    i2 = i2 != 1 ? 1 : i2;
                    i3 = i2;
                    break;
            }
            int i4 = i + 1;
            this.a[i] = new C0005a(i3, dArr[i], dArr[i4], dArr2[i][0], dArr2[i][1], dArr2[i4][0], dArr2[i4][1]);
            i = i4;
        }
    }

    /* compiled from: ArcCurveFit.java */
    /* renamed from: androidx.constraintlayout.motion.utils.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private static class C0005a {
        private static double[] s = new double[91];
        double[] a;
        double b;
        double c;
        double d;
        double e;
        double f;
        double g;
        double h;
        double i;
        double j;
        double k;
        double l;
        double m;
        double n;
        double o;
        double p;
        boolean q;
        boolean r;

        C0005a(int i, double d, double d2, double d3, double d4, double d5, double d6) {
            boolean z = false;
            this.r = false;
            this.q = i == 1 ? true : z;
            this.c = d;
            this.d = d2;
            this.i = 1.0d / (this.d - this.c);
            if (3 == i) {
                this.r = true;
            }
            double d7 = d5 - d3;
            double d8 = d6 - d4;
            if (this.r || Math.abs(d7) < 0.001d || Math.abs(d8) < 0.001d) {
                this.r = true;
                this.e = d3;
                this.f = d5;
                this.g = d4;
                this.h = d6;
                this.b = Math.hypot(d8, d7);
                this.n = this.b * this.i;
                double d9 = this.d;
                double d10 = this.c;
                this.l = d7 / (d9 - d10);
                this.m = d8 / (d9 - d10);
                return;
            }
            this.a = new double[101];
            this.j = d7 * (this.q ? -1 : 1);
            this.k = d8 * (this.q ? 1 : -1);
            this.l = this.q ? d5 : d3;
            this.m = this.q ? d4 : d6;
            a(d3, d4, d5, d6);
            this.n = this.b * this.i;
        }

        void a(double d) {
            double f = f((this.q ? this.d - d : d - this.c) * this.i) * 1.5707963267948966d;
            this.o = Math.sin(f);
            this.p = Math.cos(f);
        }

        double a() {
            return this.l + (this.j * this.o);
        }

        double b() {
            return this.m + (this.k * this.p);
        }

        double c() {
            double d = this.j * this.p;
            double hypot = this.n / Math.hypot(d, (-this.k) * this.o);
            if (this.q) {
                d = -d;
            }
            return d * hypot;
        }

        double d() {
            double d = this.j * this.p;
            double d2 = (-this.k) * this.o;
            double hypot = this.n / Math.hypot(d, d2);
            return this.q ? (-d2) * hypot : d2 * hypot;
        }

        public double b(double d) {
            double d2 = (d - this.c) * this.i;
            double d3 = this.e;
            return d3 + (d2 * (this.f - d3));
        }

        public double c(double d) {
            double d2 = (d - this.c) * this.i;
            double d3 = this.g;
            return d3 + (d2 * (this.h - d3));
        }

        public double d(double d) {
            return this.l;
        }

        public double e(double d) {
            return this.m;
        }

        double f(double d) {
            if (d <= 0.0d) {
                return 0.0d;
            }
            if (d >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.a;
            double length = d * (dArr.length - 1);
            int i = (int) length;
            return dArr[i] + ((length - i) * (dArr[i + 1] - dArr[i]));
        }

        private void a(double d, double d2, double d3, double d4) {
            double d5 = d3 - d;
            double d6 = d2 - d4;
            int i = 0;
            double d7 = 0.0d;
            double d8 = 0.0d;
            double d9 = 0.0d;
            while (true) {
                double[] dArr = s;
                if (i >= dArr.length) {
                    break;
                }
                double radians = Math.toRadians((i * 90.0d) / (dArr.length - 1));
                double sin = Math.sin(radians) * d5;
                double cos = Math.cos(radians) * d6;
                if (i > 0) {
                    d7 += Math.hypot(sin - d8, cos - d9);
                    s[i] = d7;
                } else {
                    d7 = d7;
                }
                i++;
                d9 = cos;
                d8 = sin;
            }
            this.b = d7;
            int i2 = 0;
            while (true) {
                double[] dArr2 = s;
                if (i2 >= dArr2.length) {
                    break;
                }
                dArr2[i2] = dArr2[i2] / d7;
                i2++;
            }
            int i3 = 0;
            while (true) {
                double[] dArr3 = this.a;
                if (i3 < dArr3.length) {
                    double length = i3 / (dArr3.length - 1);
                    int binarySearch = Arrays.binarySearch(s, length);
                    if (binarySearch >= 0) {
                        this.a[i3] = binarySearch / (s.length - 1);
                    } else if (binarySearch == -1) {
                        this.a[i3] = 0.0d;
                    } else {
                        int i4 = -binarySearch;
                        int i5 = i4 - 2;
                        double[] dArr4 = s;
                        this.a[i3] = (i5 + ((length - dArr4[i5]) / (dArr4[i4 - 1] - dArr4[i5]))) / (dArr4.length - 1);
                    }
                    i3++;
                } else {
                    return;
                }
            }
        }
    }
}
