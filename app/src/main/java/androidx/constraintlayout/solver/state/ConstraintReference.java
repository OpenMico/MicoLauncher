package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ConstraintReference implements Reference {
    private Object H;
    private Object I;
    private ConstraintWidget J;
    final State a;
    int b = 0;
    int c = 0;
    float d = 0.5f;
    float e = 0.5f;
    int f = 0;
    int g = 0;
    int h = 0;
    int i = 0;
    int j = 0;
    int k = 0;
    int l = 0;
    int m = 0;
    int n = 0;
    int o = 0;
    int p = 0;
    int q = 0;
    Object r = null;
    Object s = null;
    Object t = null;
    Object u = null;
    Object v = null;
    Object w = null;
    Object x = null;
    Object y = null;
    Object z = null;
    Object A = null;
    Object B = null;
    Object C = null;
    Object D = null;
    State.Constraint E = null;
    Dimension F = Dimension.Fixed(Dimension.WRAP_DIMENSION);
    Dimension G = Dimension.Fixed(Dimension.WRAP_DIMENSION);

    /* loaded from: classes.dex */
    public interface ConstraintReferenceFactory {
        ConstraintReference create(State state);
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setKey(Object obj) {
        this.H = obj;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public Object getKey() {
        return this.H;
    }

    public void setView(Object obj) {
        this.I = obj;
        ConstraintWidget constraintWidget = this.J;
        if (constraintWidget != null) {
            constraintWidget.setCompanionWidget(this.I);
        }
    }

    public Object getView() {
        return this.I;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setConstraintWidget(ConstraintWidget constraintWidget) {
        if (constraintWidget != null) {
            this.J = constraintWidget;
            this.J.setCompanionWidget(this.I);
        }
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public ConstraintWidget getConstraintWidget() {
        if (this.J == null) {
            this.J = createConstraintWidget();
            this.J.setCompanionWidget(this.I);
        }
        return this.J;
    }

    public ConstraintWidget createConstraintWidget() {
        return new ConstraintWidget(getWidth().a(), getHeight().a());
    }

    /* loaded from: classes.dex */
    class a extends Exception {
        private final ArrayList<String> mErrors;

        public a(ArrayList<String> arrayList) {
            ConstraintReference.this = r1;
            this.mErrors = arrayList;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "IncorrectConstraintException: " + this.mErrors.toString();
        }
    }

    public void validate() throws a {
        ArrayList arrayList = new ArrayList();
        if (!(this.r == null || this.s == null)) {
            arrayList.add("LeftToLeft and LeftToRight both defined");
        }
        if (!(this.t == null || this.u == null)) {
            arrayList.add("RightToLeft and RightToRight both defined");
        }
        if (!(this.v == null || this.w == null)) {
            arrayList.add("StartToStart and StartToEnd both defined");
        }
        if (!(this.x == null || this.y == null)) {
            arrayList.add("EndToStart and EndToEnd both defined");
        }
        if (!((this.r == null && this.s == null && this.t == null && this.u == null) || (this.v == null && this.w == null && this.x == null && this.y == null))) {
            arrayList.add("Both left/right and start/end constraints defined");
        }
        if (arrayList.size() > 0) {
            throw new a(arrayList);
        }
    }

    private Object a(Object obj) {
        if (obj == null) {
            return null;
        }
        return !(obj instanceof ConstraintReference) ? this.a.a(obj) : obj;
    }

    public ConstraintReference(State state) {
        this.a = state;
    }

    public void setHorizontalChainStyle(int i) {
        this.b = i;
    }

    public int getHorizontalChainStyle() {
        return this.b;
    }

    public void setVerticalChainStyle(int i) {
        this.c = i;
    }

    public int getVerticalChainStyle(int i) {
        return this.c;
    }

    public ConstraintReference clearVertical() {
        top().clear();
        baseline().clear();
        bottom().clear();
        return this;
    }

    public ConstraintReference clearHorizontal() {
        start().clear();
        end().clear();
        left().clear();
        right().clear();
        return this;
    }

    public ConstraintReference left() {
        if (this.r != null) {
            this.E = State.Constraint.LEFT_TO_LEFT;
        } else {
            this.E = State.Constraint.LEFT_TO_RIGHT;
        }
        return this;
    }

    public ConstraintReference right() {
        if (this.t != null) {
            this.E = State.Constraint.RIGHT_TO_LEFT;
        } else {
            this.E = State.Constraint.RIGHT_TO_RIGHT;
        }
        return this;
    }

    public ConstraintReference start() {
        if (this.v != null) {
            this.E = State.Constraint.START_TO_START;
        } else {
            this.E = State.Constraint.START_TO_END;
        }
        return this;
    }

    public ConstraintReference end() {
        if (this.x != null) {
            this.E = State.Constraint.END_TO_START;
        } else {
            this.E = State.Constraint.END_TO_END;
        }
        return this;
    }

    public ConstraintReference top() {
        if (this.z != null) {
            this.E = State.Constraint.TOP_TO_TOP;
        } else {
            this.E = State.Constraint.TOP_TO_BOTTOM;
        }
        return this;
    }

    public ConstraintReference bottom() {
        if (this.B != null) {
            this.E = State.Constraint.BOTTOM_TO_TOP;
        } else {
            this.E = State.Constraint.BOTTOM_TO_BOTTOM;
        }
        return this;
    }

    public ConstraintReference baseline() {
        this.E = State.Constraint.BASELINE_TO_BASELINE;
        return this;
    }

    private void a() {
        this.r = a(this.r);
        this.s = a(this.s);
        this.t = a(this.t);
        this.u = a(this.u);
        this.v = a(this.v);
        this.w = a(this.w);
        this.x = a(this.x);
        this.y = a(this.y);
        this.z = a(this.z);
        this.A = a(this.A);
        this.B = a(this.B);
        this.C = a(this.C);
        this.D = a(this.D);
    }

    public ConstraintReference leftToLeft(Object obj) {
        this.E = State.Constraint.LEFT_TO_LEFT;
        this.r = obj;
        return this;
    }

    public ConstraintReference leftToRight(Object obj) {
        this.E = State.Constraint.LEFT_TO_RIGHT;
        this.s = obj;
        return this;
    }

    public ConstraintReference rightToLeft(Object obj) {
        this.E = State.Constraint.RIGHT_TO_LEFT;
        this.t = obj;
        return this;
    }

    public ConstraintReference rightToRight(Object obj) {
        this.E = State.Constraint.RIGHT_TO_RIGHT;
        this.u = obj;
        return this;
    }

    public ConstraintReference startToStart(Object obj) {
        this.E = State.Constraint.START_TO_START;
        this.v = obj;
        return this;
    }

    public ConstraintReference startToEnd(Object obj) {
        this.E = State.Constraint.START_TO_END;
        this.w = obj;
        return this;
    }

    public ConstraintReference endToStart(Object obj) {
        this.E = State.Constraint.END_TO_START;
        this.x = obj;
        return this;
    }

    public ConstraintReference endToEnd(Object obj) {
        this.E = State.Constraint.END_TO_END;
        this.y = obj;
        return this;
    }

    public ConstraintReference topToTop(Object obj) {
        this.E = State.Constraint.TOP_TO_TOP;
        this.z = obj;
        return this;
    }

    public ConstraintReference topToBottom(Object obj) {
        this.E = State.Constraint.TOP_TO_BOTTOM;
        this.A = obj;
        return this;
    }

    public ConstraintReference bottomToTop(Object obj) {
        this.E = State.Constraint.BOTTOM_TO_TOP;
        this.B = obj;
        return this;
    }

    public ConstraintReference bottomToBottom(Object obj) {
        this.E = State.Constraint.BOTTOM_TO_BOTTOM;
        this.C = obj;
        return this;
    }

    public ConstraintReference baselineToBaseline(Object obj) {
        this.E = State.Constraint.BASELINE_TO_BASELINE;
        this.D = obj;
        return this;
    }

    public ConstraintReference centerHorizontally(Object obj) {
        Object a2 = a(obj);
        this.v = a2;
        this.y = a2;
        this.E = State.Constraint.CENTER_HORIZONTALLY;
        this.d = 0.5f;
        return this;
    }

    public ConstraintReference centerVertically(Object obj) {
        Object a2 = a(obj);
        this.z = a2;
        this.C = a2;
        this.E = State.Constraint.CENTER_VERTICALLY;
        this.e = 0.5f;
        return this;
    }

    public ConstraintReference width(Dimension dimension) {
        return setWidth(dimension);
    }

    public ConstraintReference height(Dimension dimension) {
        return setHeight(dimension);
    }

    public Dimension getWidth() {
        return this.F;
    }

    public ConstraintReference setWidth(Dimension dimension) {
        this.F = dimension;
        return this;
    }

    public Dimension getHeight() {
        return this.G;
    }

    public ConstraintReference setHeight(Dimension dimension) {
        this.G = dimension;
        return this;
    }

    public ConstraintReference margin(Object obj) {
        return margin(this.a.convertDimension(obj));
    }

    public ConstraintReference margin(int i) {
        if (this.E != null) {
            switch (this.E) {
                case LEFT_TO_LEFT:
                case LEFT_TO_RIGHT:
                    this.f = i;
                    break;
                case RIGHT_TO_LEFT:
                case RIGHT_TO_RIGHT:
                    this.g = i;
                    break;
                case START_TO_START:
                case START_TO_END:
                    this.h = i;
                    break;
                case END_TO_START:
                case END_TO_END:
                    this.i = i;
                    break;
                case TOP_TO_TOP:
                case TOP_TO_BOTTOM:
                    this.j = i;
                    break;
                case BOTTOM_TO_TOP:
                case BOTTOM_TO_BOTTOM:
                    this.k = i;
                    break;
            }
        } else {
            this.f = i;
            this.g = i;
            this.h = i;
            this.i = i;
            this.j = i;
            this.k = i;
        }
        return this;
    }

    public ConstraintReference marginGone(int i) {
        if (this.E != null) {
            switch (this.E) {
                case LEFT_TO_LEFT:
                case LEFT_TO_RIGHT:
                    this.l = i;
                    break;
                case RIGHT_TO_LEFT:
                case RIGHT_TO_RIGHT:
                    this.m = i;
                    break;
                case START_TO_START:
                case START_TO_END:
                    this.n = i;
                    break;
                case END_TO_START:
                case END_TO_END:
                    this.o = i;
                    break;
                case TOP_TO_TOP:
                case TOP_TO_BOTTOM:
                    this.p = i;
                    break;
                case BOTTOM_TO_TOP:
                case BOTTOM_TO_BOTTOM:
                    this.q = i;
                    break;
            }
        } else {
            this.l = i;
            this.m = i;
            this.n = i;
            this.o = i;
            this.p = i;
            this.q = i;
        }
        return this;
    }

    public ConstraintReference horizontalBias(float f) {
        this.d = f;
        return this;
    }

    public ConstraintReference verticalBias(float f) {
        this.e = f;
        return this;
    }

    public ConstraintReference bias(float f) {
        if (this.E == null) {
            return this;
        }
        switch (this.E) {
            case LEFT_TO_LEFT:
            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
            case RIGHT_TO_RIGHT:
            case START_TO_START:
            case START_TO_END:
            case END_TO_START:
            case END_TO_END:
            case CENTER_HORIZONTALLY:
                this.d = f;
                break;
            case TOP_TO_TOP:
            case TOP_TO_BOTTOM:
            case BOTTOM_TO_TOP:
            case BOTTOM_TO_BOTTOM:
            case CENTER_VERTICALLY:
                this.e = f;
                break;
        }
        return this;
    }

    public ConstraintReference clear() {
        if (this.E != null) {
            switch (this.E) {
                case LEFT_TO_LEFT:
                case LEFT_TO_RIGHT:
                    this.r = null;
                    this.s = null;
                    this.f = 0;
                    this.l = 0;
                    break;
                case RIGHT_TO_LEFT:
                case RIGHT_TO_RIGHT:
                    this.t = null;
                    this.u = null;
                    this.g = 0;
                    this.m = 0;
                    break;
                case START_TO_START:
                case START_TO_END:
                    this.v = null;
                    this.w = null;
                    this.h = 0;
                    this.n = 0;
                    break;
                case END_TO_START:
                case END_TO_END:
                    this.x = null;
                    this.y = null;
                    this.i = 0;
                    this.o = 0;
                    break;
                case TOP_TO_TOP:
                case TOP_TO_BOTTOM:
                    this.z = null;
                    this.A = null;
                    this.j = 0;
                    this.p = 0;
                    break;
                case BOTTOM_TO_TOP:
                case BOTTOM_TO_BOTTOM:
                    this.B = null;
                    this.C = null;
                    this.k = 0;
                    this.q = 0;
                    break;
                case BASELINE_TO_BASELINE:
                    this.D = null;
                    break;
            }
        } else {
            this.r = null;
            this.s = null;
            this.f = 0;
            this.t = null;
            this.u = null;
            this.g = 0;
            this.v = null;
            this.w = null;
            this.h = 0;
            this.x = null;
            this.y = null;
            this.i = 0;
            this.z = null;
            this.A = null;
            this.j = 0;
            this.B = null;
            this.C = null;
            this.k = 0;
            this.D = null;
            this.d = 0.5f;
            this.e = 0.5f;
            this.l = 0;
            this.m = 0;
            this.n = 0;
            this.o = 0;
            this.p = 0;
            this.q = 0;
        }
        return this;
    }

    private ConstraintWidget b(Object obj) {
        if (obj instanceof Reference) {
            return ((Reference) obj).getConstraintWidget();
        }
        return null;
    }

    private void a(ConstraintWidget constraintWidget, Object obj, State.Constraint constraint) {
        ConstraintWidget b = b(obj);
        if (b != null) {
            int i = AnonymousClass1.a[constraint.ordinal()];
            switch (constraint) {
                case LEFT_TO_LEFT:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(b.getAnchor(ConstraintAnchor.Type.LEFT), this.f, this.l, false);
                    return;
                case LEFT_TO_RIGHT:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(b.getAnchor(ConstraintAnchor.Type.RIGHT), this.f, this.l, false);
                    return;
                case RIGHT_TO_LEFT:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(b.getAnchor(ConstraintAnchor.Type.LEFT), this.g, this.m, false);
                    return;
                case RIGHT_TO_RIGHT:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(b.getAnchor(ConstraintAnchor.Type.RIGHT), this.g, this.m, false);
                    return;
                case START_TO_START:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(b.getAnchor(ConstraintAnchor.Type.LEFT), this.h, this.n, false);
                    return;
                case START_TO_END:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(b.getAnchor(ConstraintAnchor.Type.RIGHT), this.h, this.n, false);
                    return;
                case END_TO_START:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(b.getAnchor(ConstraintAnchor.Type.LEFT), this.i, this.o, false);
                    return;
                case END_TO_END:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(b.getAnchor(ConstraintAnchor.Type.RIGHT), this.i, this.o, false);
                    return;
                case TOP_TO_TOP:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(b.getAnchor(ConstraintAnchor.Type.TOP), this.j, this.p, false);
                    return;
                case TOP_TO_BOTTOM:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(b.getAnchor(ConstraintAnchor.Type.BOTTOM), this.j, this.p, false);
                    return;
                case BOTTOM_TO_TOP:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(b.getAnchor(ConstraintAnchor.Type.TOP), this.k, this.q, false);
                    return;
                case BOTTOM_TO_BOTTOM:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(b.getAnchor(ConstraintAnchor.Type.BOTTOM), this.k, this.q, false);
                    return;
                case BASELINE_TO_BASELINE:
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, b, ConstraintAnchor.Type.BASELINE, 0, 0);
                    return;
                default:
                    return;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void apply() {
        ConstraintWidget constraintWidget = this.J;
        if (constraintWidget != null) {
            this.F.apply(this.a, constraintWidget, 0);
            this.G.apply(this.a, this.J, 1);
            a();
            a(this.J, this.r, State.Constraint.LEFT_TO_LEFT);
            a(this.J, this.s, State.Constraint.LEFT_TO_RIGHT);
            a(this.J, this.t, State.Constraint.RIGHT_TO_LEFT);
            a(this.J, this.u, State.Constraint.RIGHT_TO_RIGHT);
            a(this.J, this.v, State.Constraint.START_TO_START);
            a(this.J, this.w, State.Constraint.START_TO_END);
            a(this.J, this.x, State.Constraint.END_TO_START);
            a(this.J, this.y, State.Constraint.END_TO_END);
            a(this.J, this.z, State.Constraint.TOP_TO_TOP);
            a(this.J, this.A, State.Constraint.TOP_TO_BOTTOM);
            a(this.J, this.B, State.Constraint.BOTTOM_TO_TOP);
            a(this.J, this.C, State.Constraint.BOTTOM_TO_BOTTOM);
            a(this.J, this.D, State.Constraint.BASELINE_TO_BASELINE);
            int i = this.b;
            if (i != 0) {
                this.J.setHorizontalChainStyle(i);
            }
            int i2 = this.c;
            if (i2 != 0) {
                this.J.setVerticalChainStyle(i2);
            }
            this.J.setHorizontalBiasPercent(this.d);
            this.J.setVerticalBiasPercent(this.e);
        }
    }
}
