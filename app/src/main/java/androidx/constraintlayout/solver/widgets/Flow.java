package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] ab;
    private int E = -1;
    private int F = -1;
    private int G = -1;
    private int H = -1;
    private int I = -1;
    private int J = -1;
    private float K = 0.5f;
    private float L = 0.5f;
    private float M = 0.5f;
    private float N = 0.5f;
    private float O = 0.5f;
    private float P = 0.5f;
    private int Q = 0;
    private int R = 0;
    private int S = 2;
    private int T = 2;
    private int U = 0;
    private int V = -1;
    private int W = 0;
    private ArrayList<a> X = new ArrayList<>();
    private ConstraintWidget[] Y = null;
    private ConstraintWidget[] Z = null;
    private int[] aa = null;
    private int ac = 0;

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        Flow flow = (Flow) constraintWidget;
        this.E = flow.E;
        this.F = flow.F;
        this.G = flow.G;
        this.H = flow.H;
        this.I = flow.I;
        this.J = flow.J;
        this.K = flow.K;
        this.L = flow.L;
        this.M = flow.M;
        this.N = flow.N;
        this.O = flow.O;
        this.P = flow.P;
        this.Q = flow.Q;
        this.R = flow.R;
        this.S = flow.S;
        this.T = flow.T;
        this.U = flow.U;
        this.V = flow.V;
        this.W = flow.W;
    }

    public void setOrientation(int i) {
        this.W = i;
    }

    public void setFirstHorizontalStyle(int i) {
        this.G = i;
    }

    public void setFirstVerticalStyle(int i) {
        this.H = i;
    }

    public void setLastHorizontalStyle(int i) {
        this.I = i;
    }

    public void setLastVerticalStyle(int i) {
        this.J = i;
    }

    public void setHorizontalStyle(int i) {
        this.E = i;
    }

    public void setVerticalStyle(int i) {
        this.F = i;
    }

    public void setHorizontalBias(float f) {
        this.K = f;
    }

    public void setVerticalBias(float f) {
        this.L = f;
    }

    public void setFirstHorizontalBias(float f) {
        this.M = f;
    }

    public void setFirstVerticalBias(float f) {
        this.N = f;
    }

    public void setLastHorizontalBias(float f) {
        this.O = f;
    }

    public void setLastVerticalBias(float f) {
        this.P = f;
    }

    public void setHorizontalAlign(int i) {
        this.S = i;
    }

    public void setVerticalAlign(int i) {
        this.T = i;
    }

    public void setWrapMode(int i) {
        this.U = i;
    }

    public void setHorizontalGap(int i) {
        this.Q = i;
    }

    public void setVerticalGap(int i) {
        this.R = i;
    }

    public void setMaxElementsWrap(int i) {
        this.V = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int a(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentWidth * i);
                if (i2 != constraintWidget.getWidth()) {
                    constraintWidget.setMeasureRequested(true);
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i2, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                }
                return i2;
            } else if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
                return constraintWidget.getWidth();
            } else {
                if (constraintWidget.mMatchConstraintDefaultWidth == 3) {
                    return (int) ((constraintWidget.getHeight() * constraintWidget.mDimensionRatio) + 0.5f);
                }
            }
        }
        return constraintWidget.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int b(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentHeight * i);
                if (i2 != constraintWidget.getHeight()) {
                    constraintWidget.setMeasureRequested(true);
                    measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i2);
                }
                return i2;
            } else if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
                return constraintWidget.getHeight();
            } else {
                if (constraintWidget.mMatchConstraintDefaultHeight == 3) {
                    return (int) ((constraintWidget.getWidth() * constraintWidget.mDimensionRatio) + 0.5f);
                }
            }
        }
        return constraintWidget.getHeight();
    }

    @Override // androidx.constraintlayout.solver.widgets.VirtualLayout
    public void measure(int i, int i2, int i3, int i4) {
        int i5;
        ConstraintWidget[] constraintWidgetArr;
        int[] iArr;
        boolean z;
        int i6;
        if (this.mWidgetsCount <= 0 || measureChildren()) {
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int[] iArr2 = new int[2];
            int i7 = (i2 - paddingLeft) - paddingRight;
            if (this.W == 1) {
                i7 = (i4 - paddingTop) - paddingBottom;
            }
            if (this.W == 0) {
                if (this.E == -1) {
                    this.E = 0;
                }
                if (this.F == -1) {
                    this.F = 0;
                }
            } else {
                if (this.E == -1) {
                    this.E = 0;
                }
                if (this.F == -1) {
                    this.F = 0;
                }
            }
            ConstraintWidget[] constraintWidgetArr2 = this.mWidgets;
            int i8 = 0;
            for (int i9 = 0; i9 < this.mWidgetsCount; i9++) {
                if (this.mWidgets[i9].getVisibility() == 8) {
                    i8++;
                }
            }
            int i10 = this.mWidgetsCount;
            if (i8 > 0) {
                ConstraintWidget[] constraintWidgetArr3 = new ConstraintWidget[this.mWidgetsCount - i8];
                i5 = 0;
                for (int i11 = 0; i11 < this.mWidgetsCount; i11++) {
                    ConstraintWidget constraintWidget = this.mWidgets[i11];
                    if (constraintWidget.getVisibility() != 8) {
                        constraintWidgetArr3[i5] = constraintWidget;
                        i5++;
                    }
                }
                constraintWidgetArr = constraintWidgetArr3;
            } else {
                i5 = i10;
                constraintWidgetArr = constraintWidgetArr2;
            }
            this.ab = constraintWidgetArr;
            this.ac = i5;
            switch (this.U) {
                case 0:
                    iArr = iArr2;
                    z = true;
                    b(constraintWidgetArr, i5, this.W, i7, iArr2);
                    i6 = 0;
                    break;
                case 1:
                    iArr = iArr2;
                    z = true;
                    a(constraintWidgetArr, i5, this.W, i7, iArr2);
                    i6 = 0;
                    break;
                case 2:
                    z = true;
                    iArr = iArr2;
                    c(constraintWidgetArr, i5, this.W, i7, iArr2);
                    i6 = 0;
                    break;
                default:
                    iArr = iArr2;
                    z = true;
                    i6 = 0;
                    break;
            }
            int i12 = iArr[i6] + paddingLeft + paddingRight;
            char c = z ? 1 : 0;
            char c2 = z ? 1 : 0;
            char c3 = z ? 1 : 0;
            char c4 = z ? 1 : 0;
            char c5 = z ? 1 : 0;
            char c6 = z ? 1 : 0;
            int i13 = iArr[c] + paddingTop + paddingBottom;
            if (i == 1073741824) {
                i12 = i2;
            } else if (i == Integer.MIN_VALUE) {
                i12 = Math.min(i12, i2);
            } else if (i != 0) {
                i12 = i6;
            }
            if (i3 == 1073741824) {
                i13 = i4;
            } else if (i3 == Integer.MIN_VALUE) {
                i13 = Math.min(i13, i4);
            } else if (i3 != 0) {
                i13 = i6;
            }
            setMeasure(i12, i13);
            setWidth(i12);
            setHeight(i13);
            if (this.mWidgetsCount <= 0) {
                z = i6;
            }
            needsCallbackFromSolver(z);
            return;
        }
        setMeasure(0, 0);
        needsCallbackFromSolver(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a {
        private int c;
        private ConstraintAnchor e;
        private ConstraintAnchor f;
        private ConstraintAnchor g;
        private ConstraintAnchor h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int r;
        private ConstraintWidget d = null;
        int a = 0;
        private int m = 0;
        private int n = 0;
        private int o = 0;
        private int p = 0;
        private int q = 0;

        public a(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
            this.c = 0;
            this.i = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
            this.r = 0;
            this.c = i;
            this.e = constraintAnchor;
            this.f = constraintAnchor2;
            this.g = constraintAnchor3;
            this.h = constraintAnchor4;
            this.i = Flow.this.getPaddingLeft();
            this.j = Flow.this.getPaddingTop();
            this.k = Flow.this.getPaddingRight();
            this.l = Flow.this.getPaddingBottom();
            this.r = i2;
        }

        public void a(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2, int i3, int i4, int i5, int i6) {
            this.c = i;
            this.e = constraintAnchor;
            this.f = constraintAnchor2;
            this.g = constraintAnchor3;
            this.h = constraintAnchor4;
            this.i = i2;
            this.j = i3;
            this.k = i4;
            this.l = i5;
            this.r = i6;
        }

        public void a() {
            this.a = 0;
            this.d = null;
            this.m = 0;
            this.n = 0;
            this.o = 0;
            this.p = 0;
            this.q = 0;
        }

        public void a(int i) {
            this.o = i;
        }

        public int b() {
            if (this.c == 0) {
                return this.m - Flow.this.Q;
            }
            return this.m;
        }

        public int c() {
            if (this.c == 1) {
                return this.n - Flow.this.R;
            }
            return this.n;
        }

        public void a(ConstraintWidget constraintWidget) {
            int i = 0;
            if (this.c == 0) {
                int a = Flow.this.a(constraintWidget, this.r);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.q++;
                    a = 0;
                }
                i = Flow.this.Q;
                if (constraintWidget.getVisibility() != 8) {
                }
                this.m += a + i;
                int b = Flow.this.b(constraintWidget, this.r);
                if (this.d == null || this.a < b) {
                    this.d = constraintWidget;
                    this.a = b;
                    this.n = b;
                }
            } else {
                int a2 = Flow.this.a(constraintWidget, this.r);
                int b2 = Flow.this.b(constraintWidget, this.r);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.q++;
                    b2 = 0;
                }
                i = Flow.this.R;
                if (constraintWidget.getVisibility() != 8) {
                }
                this.n += b2 + i;
                if (this.d == null || this.a < a2) {
                    this.d = constraintWidget;
                    this.a = a2;
                    this.m = a2;
                }
            }
            this.p++;
        }

        public void a(boolean z, int i, boolean z2) {
            ConstraintWidget constraintWidget;
            char c;
            int i2 = this.p;
            for (int i3 = 0; i3 < i2 && this.o + i3 < Flow.this.ac; i3++) {
                ConstraintWidget constraintWidget2 = Flow.this.ab[this.o + i3];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
            }
            if (!(i2 == 0 || this.d == null)) {
                boolean z3 = z2 && i == 0;
                int i4 = -1;
                int i5 = -1;
                for (int i6 = 0; i6 < i2; i6++) {
                    int i7 = z ? (i2 - 1) - i6 : i6;
                    if (this.o + i7 >= Flow.this.ac) {
                        break;
                    }
                    if (Flow.this.ab[this.o + i7].getVisibility() == 0) {
                        if (i4 == -1) {
                            i4 = i6;
                        }
                        i5 = i6;
                    }
                }
                if (this.c == 0) {
                    ConstraintWidget constraintWidget3 = this.d;
                    constraintWidget3.setVerticalChainStyle(Flow.this.F);
                    int i8 = this.j;
                    if (i > 0) {
                        i8 += Flow.this.R;
                    }
                    constraintWidget3.mTop.connect(this.f, i8);
                    if (z2) {
                        constraintWidget3.mBottom.connect(this.h, this.l);
                    }
                    if (i > 0) {
                        this.f.mOwner.mBottom.connect(constraintWidget3.mTop, 0);
                    }
                    if (Flow.this.T == 3 && !constraintWidget3.hasBaseline()) {
                        for (int i9 = 0; i9 < i2; i9++) {
                            int i10 = z ? (i2 - 1) - i9 : i9;
                            if (this.o + i10 >= Flow.this.ac) {
                                break;
                            }
                            constraintWidget = Flow.this.ab[this.o + i10];
                            if (constraintWidget.hasBaseline()) {
                                break;
                            }
                        }
                    }
                    constraintWidget = constraintWidget3;
                    ConstraintWidget constraintWidget4 = null;
                    int i11 = 0;
                    while (i11 < i2) {
                        int i12 = z ? (i2 - 1) - i11 : i11;
                        if (this.o + i12 < Flow.this.ac) {
                            ConstraintWidget constraintWidget5 = Flow.this.ab[this.o + i12];
                            if (i11 == 0) {
                                constraintWidget5.connect(constraintWidget5.mLeft, this.e, this.i);
                            }
                            if (i12 == 0) {
                                int i13 = Flow.this.E;
                                float f = Flow.this.K;
                                if (this.o == 0 && Flow.this.G != -1) {
                                    i13 = Flow.this.G;
                                    f = Flow.this.M;
                                } else if (z2 && Flow.this.I != -1) {
                                    i13 = Flow.this.I;
                                    f = Flow.this.O;
                                }
                                constraintWidget5.setHorizontalChainStyle(i13);
                                constraintWidget5.setHorizontalBiasPercent(f);
                            }
                            if (i11 == i2 - 1) {
                                constraintWidget5.connect(constraintWidget5.mRight, this.g, this.k);
                            }
                            if (constraintWidget4 != null) {
                                constraintWidget5.mLeft.connect(constraintWidget4.mRight, Flow.this.Q);
                                if (i11 == i4) {
                                    constraintWidget5.mLeft.setGoneMargin(this.i);
                                }
                                constraintWidget4.mRight.connect(constraintWidget5.mLeft, 0);
                                if (i11 == i5 + 1) {
                                    constraintWidget4.mRight.setGoneMargin(this.k);
                                }
                            }
                            if (constraintWidget5 != constraintWidget3) {
                                c = 3;
                                if (Flow.this.T != 3 || !constraintWidget.hasBaseline() || constraintWidget5 == constraintWidget || !constraintWidget5.hasBaseline()) {
                                    switch (Flow.this.T) {
                                        case 0:
                                            constraintWidget5.mTop.connect(constraintWidget3.mTop, 0);
                                            continue;
                                        case 1:
                                            constraintWidget5.mBottom.connect(constraintWidget3.mBottom, 0);
                                            continue;
                                        default:
                                            if (!z3) {
                                                constraintWidget5.mTop.connect(constraintWidget3.mTop, 0);
                                                constraintWidget5.mBottom.connect(constraintWidget3.mBottom, 0);
                                                break;
                                            } else {
                                                constraintWidget5.mTop.connect(this.f, this.j);
                                                constraintWidget5.mBottom.connect(this.h, this.l);
                                                continue;
                                            }
                                    }
                                } else {
                                    constraintWidget5.mBaseline.connect(constraintWidget.mBaseline, 0);
                                }
                            } else {
                                c = 3;
                            }
                            i11++;
                            constraintWidget4 = constraintWidget5;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                ConstraintWidget constraintWidget6 = this.d;
                constraintWidget6.setHorizontalChainStyle(Flow.this.E);
                int i14 = this.i;
                if (i > 0) {
                    i14 += Flow.this.Q;
                }
                if (z) {
                    constraintWidget6.mRight.connect(this.g, i14);
                    if (z2) {
                        constraintWidget6.mLeft.connect(this.e, this.k);
                    }
                    if (i > 0) {
                        this.g.mOwner.mLeft.connect(constraintWidget6.mRight, 0);
                    }
                } else {
                    constraintWidget6.mLeft.connect(this.e, i14);
                    if (z2) {
                        constraintWidget6.mRight.connect(this.g, this.k);
                    }
                    if (i > 0) {
                        this.e.mOwner.mRight.connect(constraintWidget6.mLeft, 0);
                    }
                }
                ConstraintWidget constraintWidget7 = null;
                int i15 = 0;
                while (i15 < i2 && this.o + i15 < Flow.this.ac) {
                    ConstraintWidget constraintWidget8 = Flow.this.ab[this.o + i15];
                    if (i15 == 0) {
                        constraintWidget8.connect(constraintWidget8.mTop, this.f, this.j);
                        int i16 = Flow.this.F;
                        float f2 = Flow.this.L;
                        if (this.o == 0 && Flow.this.H != -1) {
                            i16 = Flow.this.H;
                            f2 = Flow.this.N;
                        } else if (z2 && Flow.this.J != -1) {
                            i16 = Flow.this.J;
                            f2 = Flow.this.P;
                        }
                        constraintWidget8.setVerticalChainStyle(i16);
                        constraintWidget8.setVerticalBiasPercent(f2);
                    }
                    if (i15 == i2 - 1) {
                        constraintWidget8.connect(constraintWidget8.mBottom, this.h, this.l);
                    }
                    if (constraintWidget7 != null) {
                        constraintWidget8.mTop.connect(constraintWidget7.mBottom, Flow.this.R);
                        if (i15 == i4) {
                            constraintWidget8.mTop.setGoneMargin(this.j);
                        }
                        constraintWidget7.mBottom.connect(constraintWidget8.mTop, 0);
                        if (i15 == i5 + 1) {
                            constraintWidget7.mBottom.setGoneMargin(this.l);
                        }
                    }
                    if (constraintWidget8 != constraintWidget6) {
                        if (!z) {
                            switch (Flow.this.S) {
                                case 0:
                                    constraintWidget8.mLeft.connect(constraintWidget6.mLeft, 0);
                                    continue;
                                case 1:
                                    constraintWidget8.mRight.connect(constraintWidget6.mRight, 0);
                                    continue;
                                case 2:
                                    if (!z3) {
                                        constraintWidget8.mLeft.connect(constraintWidget6.mLeft, 0);
                                        constraintWidget8.mRight.connect(constraintWidget6.mRight, 0);
                                        break;
                                    } else {
                                        constraintWidget8.mLeft.connect(this.e, this.i);
                                        constraintWidget8.mRight.connect(this.g, this.k);
                                        continue;
                                    }
                            }
                        } else {
                            switch (Flow.this.S) {
                                case 0:
                                    constraintWidget8.mRight.connect(constraintWidget6.mRight, 0);
                                    continue;
                                case 1:
                                    constraintWidget8.mLeft.connect(constraintWidget6.mLeft, 0);
                                    continue;
                                case 2:
                                    constraintWidget8.mLeft.connect(constraintWidget6.mLeft, 0);
                                    constraintWidget8.mRight.connect(constraintWidget6.mRight, 0);
                                    continue;
                            }
                        }
                    }
                    i15++;
                    constraintWidget7 = constraintWidget8;
                }
            }
        }

        public void b(int i) {
            int i2 = this.q;
            if (i2 != 0) {
                int i3 = this.p;
                int i4 = i / i2;
                for (int i5 = 0; i5 < i3 && this.o + i5 < Flow.this.ac; i5++) {
                    ConstraintWidget constraintWidget = Flow.this.ab[this.o + i5];
                    if (this.c == 0) {
                        if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                        }
                    } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                        Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i4);
                    }
                }
                d();
            }
        }

        private void d() {
            this.m = 0;
            this.n = 0;
            this.d = null;
            this.a = 0;
            int i = this.p;
            for (int i2 = 0; i2 < i && this.o + i2 < Flow.this.ac; i2++) {
                ConstraintWidget constraintWidget = Flow.this.ab[this.o + i2];
                if (this.c == 0) {
                    int width = constraintWidget.getWidth();
                    int i3 = Flow.this.Q;
                    if (constraintWidget.getVisibility() == 8) {
                        i3 = 0;
                    }
                    this.m += width + i3;
                    int b = Flow.this.b(constraintWidget, this.r);
                    if (this.d == null || this.a < b) {
                        this.d = constraintWidget;
                        this.a = b;
                        this.n = b;
                    }
                } else {
                    int a = Flow.this.a(constraintWidget, this.r);
                    int b2 = Flow.this.b(constraintWidget, this.r);
                    int i4 = Flow.this.R;
                    if (constraintWidget.getVisibility() == 8) {
                        i4 = 0;
                    }
                    this.n += b2 + i4;
                    if (this.d == null || this.a < a) {
                        this.d = constraintWidget;
                        this.a = a;
                        this.m = a;
                    }
                }
            }
        }
    }

    private void a(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        int i4;
        int i5;
        int i6;
        if (i != 0) {
            this.X.clear();
            a aVar = new a(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
            this.X.add(aVar);
            if (i2 == 0) {
                a aVar2 = aVar;
                i4 = 0;
                int i7 = 0;
                int i8 = 0;
                while (i8 < i) {
                    ConstraintWidget constraintWidget = constraintWidgetArr[i8];
                    int a2 = a(constraintWidget, i3);
                    i4 = constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                    boolean z = (i7 == i3 || (this.Q + i7) + a2 > i3) && aVar2.d != null;
                    if (!z && i8 > 0 && (i6 = this.V) > 0 && i8 % i6 == 0) {
                        z = true;
                    }
                    if (z) {
                        a aVar3 = new a(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                        aVar3.a(i8);
                        this.X.add(aVar3);
                        i7 = a2;
                        aVar2 = aVar3;
                    } else {
                        i7 = i8 > 0 ? i7 + this.Q + a2 : a2;
                    }
                    aVar2.a(constraintWidget);
                    i8++;
                }
            } else {
                a aVar4 = aVar;
                i4 = 0;
                int i9 = 0;
                int i10 = 0;
                while (i10 < i) {
                    ConstraintWidget constraintWidget2 = constraintWidgetArr[i10];
                    int b = b(constraintWidget2, i3);
                    i4 = constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                    boolean z2 = (i9 == i3 || (this.R + i9) + b > i3) && aVar4.d != null;
                    if (!z2 && i10 > 0 && (i5 = this.V) > 0 && i10 % i5 == 0) {
                        z2 = true;
                    }
                    if (z2) {
                        a aVar5 = new a(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                        aVar5.a(i10);
                        this.X.add(aVar5);
                        i9 = b;
                        aVar4 = aVar5;
                    } else {
                        i9 = i10 > 0 ? i9 + this.R + b : b;
                    }
                    aVar4.a(constraintWidget2);
                    i10++;
                }
            }
            int size = this.X.size();
            ConstraintAnchor constraintAnchor = this.mLeft;
            ConstraintAnchor constraintAnchor2 = this.mTop;
            ConstraintAnchor constraintAnchor3 = this.mRight;
            ConstraintAnchor constraintAnchor4 = this.mBottom;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            boolean z3 = getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (i4 > 0 && z3) {
                for (int i11 = 0; i11 < size; i11++) {
                    a aVar6 = this.X.get(i11);
                    if (i2 == 0) {
                        aVar6.b(i3 - aVar6.b());
                    } else {
                        aVar6.b(i3 - aVar6.c());
                    }
                }
            }
            ConstraintAnchor constraintAnchor5 = constraintAnchor3;
            int i12 = paddingTop;
            int i13 = paddingRight;
            int i14 = paddingBottom;
            ConstraintAnchor constraintAnchor6 = constraintAnchor;
            int i15 = paddingLeft;
            int i16 = 0;
            ConstraintAnchor constraintAnchor7 = constraintAnchor4;
            ConstraintAnchor constraintAnchor8 = constraintAnchor2;
            int i17 = 0;
            for (int i18 = 0; i18 < size; i18++) {
                a aVar7 = this.X.get(i18);
                if (i2 == 0) {
                    if (i18 < size - 1) {
                        constraintAnchor7 = this.X.get(i18 + 1).d.mTop;
                        i14 = 0;
                    } else {
                        constraintAnchor7 = this.mBottom;
                        i14 = getPaddingBottom();
                    }
                    constraintAnchor8 = aVar7.d.mBottom;
                    aVar7.a(i2, constraintAnchor6, constraintAnchor8, constraintAnchor5, constraintAnchor7, i15, i12, i13, i14, i3);
                    i16 = Math.max(i16, aVar7.b());
                    i17 += aVar7.c();
                    if (i18 > 0) {
                        i17 += this.R;
                    }
                    i12 = 0;
                } else {
                    if (i18 < size - 1) {
                        constraintAnchor5 = this.X.get(i18 + 1).d.mLeft;
                        i13 = 0;
                    } else {
                        constraintAnchor5 = this.mRight;
                        i13 = getPaddingRight();
                    }
                    constraintAnchor6 = aVar7.d.mRight;
                    aVar7.a(i2, constraintAnchor6, constraintAnchor8, constraintAnchor5, constraintAnchor7, i15, i12, i13, i14, i3);
                    i16 += aVar7.b();
                    i17 = Math.max(i17, aVar7.c());
                    if (i18 > 0) {
                        i16 += this.Q;
                        i15 = 0;
                    } else {
                        i15 = 0;
                    }
                }
            }
            iArr[0] = i16;
            iArr[1] = i17;
        }
    }

    private void b(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        a aVar;
        if (i != 0) {
            if (this.X.size() == 0) {
                aVar = new a(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                this.X.add(aVar);
            } else {
                a aVar2 = this.X.get(0);
                aVar2.a();
                aVar = aVar2;
                aVar.a(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i3);
            }
            for (int i4 = 0; i4 < i; i4++) {
                aVar.a(constraintWidgetArr[i4]);
            }
            iArr[0] = aVar.b();
            iArr[1] = aVar.c();
        }
    }

    private void c(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        int i4;
        int i5;
        int i6;
        boolean z;
        ConstraintWidget constraintWidget;
        if (i2 == 0) {
            int i7 = this.V;
            if (i7 <= 0) {
                int i8 = 0;
                i4 = 0;
                for (int i9 = 0; i9 < i; i9++) {
                    if (i9 > 0) {
                        i8 += this.Q;
                    }
                    ConstraintWidget constraintWidget2 = constraintWidgetArr[i9];
                    if (constraintWidget2 != null) {
                        i8 += a(constraintWidget2, i3);
                        if (i8 > i3) {
                            break;
                        }
                        i4++;
                    }
                }
                i5 = 0;
            } else {
                i4 = i7;
                i5 = 0;
            }
        } else {
            i5 = this.V;
            if (i5 <= 0) {
                int i10 = 0;
                int i11 = 0;
                for (int i12 = 0; i12 < i; i12++) {
                    if (i12 > 0) {
                        i10 += this.R;
                    }
                    ConstraintWidget constraintWidget3 = constraintWidgetArr[i12];
                    if (constraintWidget3 != null) {
                        i10 += b(constraintWidget3, i3);
                        if (i10 > i3) {
                            break;
                        }
                        i11++;
                    }
                }
                i5 = i11;
                i4 = 0;
            } else {
                i4 = 0;
            }
        }
        if (this.aa == null) {
            this.aa = new int[2];
        }
        if ((i5 == 0 && i2 == 1) || (i4 == 0 && i2 == 0)) {
            i6 = i5;
            z = true;
        } else {
            i6 = i5;
            z = false;
        }
        while (!z) {
            if (i2 == 0) {
                i6 = (int) Math.ceil(i / i4);
            } else {
                i4 = (int) Math.ceil(i / i6);
            }
            ConstraintWidget[] constraintWidgetArr2 = this.Z;
            if (constraintWidgetArr2 == null || constraintWidgetArr2.length < i4) {
                this.Z = new ConstraintWidget[i4];
            } else {
                Arrays.fill(constraintWidgetArr2, (Object) null);
            }
            ConstraintWidget[] constraintWidgetArr3 = this.Y;
            if (constraintWidgetArr3 == null || constraintWidgetArr3.length < i6) {
                this.Y = new ConstraintWidget[i6];
            } else {
                Arrays.fill(constraintWidgetArr3, (Object) null);
            }
            for (int i13 = 0; i13 < i4; i13++) {
                for (int i14 = 0; i14 < i6; i14++) {
                    int i15 = (i14 * i4) + i13;
                    if (i2 == 1) {
                        i15 = (i13 * i6) + i14;
                    }
                    if (i15 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i15]) != null) {
                        int a2 = a(constraintWidget, i3);
                        ConstraintWidget[] constraintWidgetArr4 = this.Z;
                        if (constraintWidgetArr4[i13] == null || constraintWidgetArr4[i13].getWidth() < a2) {
                            this.Z[i13] = constraintWidget;
                        }
                        int b = b(constraintWidget, i3);
                        ConstraintWidget[] constraintWidgetArr5 = this.Y;
                        if (constraintWidgetArr5[i14] == null || constraintWidgetArr5[i14].getHeight() < b) {
                            this.Y[i14] = constraintWidget;
                        }
                    }
                }
            }
            int i16 = 0;
            for (int i17 = 0; i17 < i4; i17++) {
                ConstraintWidget constraintWidget4 = this.Z[i17];
                if (constraintWidget4 != null) {
                    if (i17 > 0) {
                        i16 += this.Q;
                    }
                    i16 += a(constraintWidget4, i3);
                }
            }
            int i18 = 0;
            for (int i19 = 0; i19 < i6; i19++) {
                ConstraintWidget constraintWidget5 = this.Y[i19];
                if (constraintWidget5 != null) {
                    if (i19 > 0) {
                        i18 += this.R;
                    }
                    i18 += b(constraintWidget5, i3);
                }
            }
            iArr[0] = i16;
            iArr[1] = i18;
            if (i2 == 0) {
                if (i16 <= i3) {
                    z = true;
                } else if (i4 > 1) {
                    i4--;
                } else {
                    z = true;
                }
            } else if (i18 <= i3) {
                z = true;
            } else if (i6 > 1) {
                i6--;
            } else {
                z = true;
            }
        }
        int[] iArr2 = this.aa;
        iArr2[0] = i4;
        iArr2[1] = i6;
    }

    private void a(boolean z) {
        ConstraintWidget constraintWidget;
        if (!(this.aa == null || this.Z == null || this.Y == null)) {
            for (int i = 0; i < this.ac; i++) {
                this.ab[i].resetAnchors();
            }
            int[] iArr = this.aa;
            int i2 = iArr[0];
            int i3 = iArr[1];
            ConstraintWidget constraintWidget2 = null;
            for (int i4 = 0; i4 < i2; i4++) {
                ConstraintWidget constraintWidget3 = this.Z[z ? (i2 - i4) - 1 : i4];
                if (!(constraintWidget3 == null || constraintWidget3.getVisibility() == 8)) {
                    if (i4 == 0) {
                        constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                        constraintWidget3.setHorizontalChainStyle(this.E);
                        constraintWidget3.setHorizontalBiasPercent(this.K);
                    }
                    if (i4 == i2 - 1) {
                        constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                    }
                    if (i4 > 0) {
                        constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.Q);
                        constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                    }
                    constraintWidget2 = constraintWidget3;
                }
            }
            for (int i5 = 0; i5 < i3; i5++) {
                ConstraintWidget constraintWidget4 = this.Y[i5];
                if (!(constraintWidget4 == null || constraintWidget4.getVisibility() == 8)) {
                    if (i5 == 0) {
                        constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                        constraintWidget4.setVerticalChainStyle(this.F);
                        constraintWidget4.setVerticalBiasPercent(this.L);
                    }
                    if (i5 == i3 - 1) {
                        constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                    }
                    if (i5 > 0) {
                        constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.R);
                        constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                    }
                    constraintWidget2 = constraintWidget4;
                }
            }
            for (int i6 = 0; i6 < i2; i6++) {
                for (int i7 = 0; i7 < i3; i7++) {
                    int i8 = (i7 * i2) + i6;
                    if (this.W == 1) {
                        i8 = (i6 * i3) + i7;
                    }
                    ConstraintWidget[] constraintWidgetArr = this.ab;
                    if (!(i8 >= constraintWidgetArr.length || (constraintWidget = constraintWidgetArr[i8]) == null || constraintWidget.getVisibility() == 8)) {
                        ConstraintWidget constraintWidget5 = this.Z[i6];
                        ConstraintWidget constraintWidget6 = this.Y[i7];
                        if (constraintWidget != constraintWidget5) {
                            constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                            constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                        }
                        if (constraintWidget != constraintWidget6) {
                            constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                            constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem, boolean z) {
        super.addToSolver(linearSystem, z);
        boolean isRtl = getParent() != null ? ((ConstraintWidgetContainer) getParent()).isRtl() : false;
        switch (this.U) {
            case 0:
                if (this.X.size() > 0) {
                    this.X.get(0).a(isRtl, 0, true);
                    break;
                }
                break;
            case 1:
                int size = this.X.size();
                int i = 0;
                while (i < size) {
                    this.X.get(i).a(isRtl, i, i == size + (-1));
                    i++;
                }
                break;
            case 2:
                a(isRtl);
                break;
        }
        needsCallbackFromSolver(false);
    }
}
