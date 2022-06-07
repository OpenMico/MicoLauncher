package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.solver.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.solver.state.helpers.BarrierReference;
import androidx.constraintlayout.solver.state.helpers.GuidelineReference;
import androidx.constraintlayout.solver.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.solver.state.helpers.VerticalChainReference;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class State {
    public static final Integer PARENT = 0;
    protected HashMap<Object, Reference> mReferences = new HashMap<>();
    protected HashMap<Object, HelperReference> mHelperReferences = new HashMap<>();
    public final ConstraintReference mParent = new ConstraintReference(this);
    private int a = 0;

    /* loaded from: classes.dex */
    public enum Chain {
        SPREAD,
        SPREAD_INSIDE,
        PACKED
    }

    /* loaded from: classes.dex */
    public enum Constraint {
        LEFT_TO_LEFT,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        RIGHT_TO_RIGHT,
        START_TO_START,
        START_TO_END,
        END_TO_START,
        END_TO_END,
        TOP_TO_TOP,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        BOTTOM_TO_BOTTOM,
        BASELINE_TO_BASELINE,
        CENTER_HORIZONTALLY,
        CENTER_VERTICALLY
    }

    /* loaded from: classes.dex */
    public enum Direction {
        LEFT,
        RIGHT,
        START,
        END,
        TOP,
        BOTTOM
    }

    /* loaded from: classes.dex */
    public enum Helper {
        HORIZONTAL_CHAIN,
        VERTICAL_CHAIN,
        ALIGN_HORIZONTALLY,
        ALIGN_VERTICALLY,
        BARRIER,
        LAYER,
        FLOW
    }

    public State() {
        this.mReferences.put(PARENT, this.mParent);
    }

    public void reset() {
        this.mHelperReferences.clear();
    }

    public int convertDimension(Object obj) {
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }

    public ConstraintReference createConstraintReference(Object obj) {
        return new ConstraintReference(this);
    }

    public State width(Dimension dimension) {
        return setWidth(dimension);
    }

    public State height(Dimension dimension) {
        return setHeight(dimension);
    }

    public State setWidth(Dimension dimension) {
        this.mParent.setWidth(dimension);
        return this;
    }

    public State setHeight(Dimension dimension) {
        this.mParent.setHeight(dimension);
        return this;
    }

    public Reference a(Object obj) {
        return this.mReferences.get(obj);
    }

    public ConstraintReference constraints(Object obj) {
        Reference reference = this.mReferences.get(obj);
        if (reference == null) {
            reference = createConstraintReference(obj);
            this.mReferences.put(obj, reference);
            reference.setKey(obj);
        }
        if (reference instanceof ConstraintReference) {
            return (ConstraintReference) reference;
        }
        return null;
    }

    private String a() {
        StringBuilder sb = new StringBuilder();
        sb.append("__HELPER_KEY_");
        int i = this.a;
        this.a = i + 1;
        sb.append(i);
        sb.append("__");
        return sb.toString();
    }

    public HelperReference helper(Object obj, Helper helper) {
        if (obj == null) {
            obj = a();
        }
        HelperReference helperReference = this.mHelperReferences.get(obj);
        if (helperReference == null) {
            switch (helper) {
                case HORIZONTAL_CHAIN:
                    helperReference = new HorizontalChainReference(this);
                    break;
                case VERTICAL_CHAIN:
                    helperReference = new VerticalChainReference(this);
                    break;
                case ALIGN_HORIZONTALLY:
                    helperReference = new AlignHorizontallyReference(this);
                    break;
                case ALIGN_VERTICALLY:
                    helperReference = new AlignVerticallyReference(this);
                    break;
                case BARRIER:
                    helperReference = new BarrierReference(this);
                    break;
                default:
                    helperReference = new HelperReference(this, helper);
                    break;
            }
            this.mHelperReferences.put(obj, helperReference);
        }
        return helperReference;
    }

    public GuidelineReference horizontalGuideline(Object obj) {
        return guideline(obj, 0);
    }

    public GuidelineReference verticalGuideline(Object obj) {
        return guideline(obj, 1);
    }

    public GuidelineReference guideline(Object obj, int i) {
        Reference reference = this.mReferences.get(obj);
        GuidelineReference guidelineReference = reference;
        if (reference == null) {
            GuidelineReference guidelineReference2 = new GuidelineReference(this);
            guidelineReference2.setOrientation(i);
            guidelineReference2.setKey(obj);
            this.mReferences.put(obj, guidelineReference2);
            guidelineReference = guidelineReference2;
        }
        return (GuidelineReference) guidelineReference;
    }

    public BarrierReference barrier(Object obj, Direction direction) {
        BarrierReference barrierReference = (BarrierReference) helper(obj, Helper.BARRIER);
        barrierReference.setBarrierDirection(direction);
        return barrierReference;
    }

    public VerticalChainReference verticalChain(Object... objArr) {
        VerticalChainReference verticalChainReference = (VerticalChainReference) helper(null, Helper.VERTICAL_CHAIN);
        verticalChainReference.add(objArr);
        return verticalChainReference;
    }

    public HorizontalChainReference horizontalChain(Object... objArr) {
        HorizontalChainReference horizontalChainReference = (HorizontalChainReference) helper(null, Helper.HORIZONTAL_CHAIN);
        horizontalChainReference.add(objArr);
        return horizontalChainReference;
    }

    public AlignHorizontallyReference centerHorizontally(Object... objArr) {
        AlignHorizontallyReference alignHorizontallyReference = (AlignHorizontallyReference) helper(null, Helper.ALIGN_HORIZONTALLY);
        alignHorizontallyReference.add(objArr);
        return alignHorizontallyReference;
    }

    public AlignVerticallyReference centerVertically(Object... objArr) {
        AlignVerticallyReference alignVerticallyReference = (AlignVerticallyReference) helper(null, Helper.ALIGN_VERTICALLY);
        alignVerticallyReference.add(objArr);
        return alignVerticallyReference;
    }

    public void directMapping() {
        for (Object obj : this.mReferences.keySet()) {
            constraints(obj).setView(obj);
        }
    }

    public void map(Object obj, Object obj2) {
        constraints(obj).setView(obj2);
    }

    public void apply(ConstraintWidgetContainer constraintWidgetContainer) {
        constraintWidgetContainer.removeAllChildren();
        this.mParent.getWidth().apply(this, constraintWidgetContainer, 0);
        this.mParent.getHeight().apply(this, constraintWidgetContainer, 1);
        for (Object obj : this.mHelperReferences.keySet()) {
            HelperWidget helperWidget = this.mHelperReferences.get(obj).getHelperWidget();
            if (helperWidget != null) {
                Reference reference = this.mReferences.get(obj);
                if (reference == null) {
                    reference = constraints(obj);
                }
                reference.setConstraintWidget(helperWidget);
            }
        }
        for (Object obj2 : this.mReferences.keySet()) {
            Reference reference2 = this.mReferences.get(obj2);
            if (reference2 != this.mParent) {
                ConstraintWidget constraintWidget = reference2.getConstraintWidget();
                constraintWidget.setParent(null);
                if (reference2 instanceof GuidelineReference) {
                    reference2.apply();
                }
                constraintWidgetContainer.add(constraintWidget);
            } else {
                reference2.setConstraintWidget(constraintWidgetContainer);
            }
        }
        for (Object obj3 : this.mHelperReferences.keySet()) {
            HelperReference helperReference = this.mHelperReferences.get(obj3);
            if (helperReference.getHelperWidget() != null) {
                Iterator<Object> it = helperReference.mReferences.iterator();
                while (it.hasNext()) {
                    helperReference.getHelperWidget().add(this.mReferences.get(it.next()).getConstraintWidget());
                }
                helperReference.apply();
            }
        }
        for (Object obj4 : this.mReferences.keySet()) {
            this.mReferences.get(obj4).apply();
        }
    }
}
