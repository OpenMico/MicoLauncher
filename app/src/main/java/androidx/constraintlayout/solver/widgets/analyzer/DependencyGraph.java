package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class DependencyGraph {
    private ConstraintWidgetContainer b;
    private ConstraintWidgetContainer e;
    private boolean c = true;
    private boolean d = true;
    private ArrayList<WidgetRun> f = new ArrayList<>();
    private ArrayList<e> g = new ArrayList<>();
    private BasicMeasure.Measurer h = null;
    private BasicMeasure.Measure i = new BasicMeasure.Measure();
    ArrayList<e> a = new ArrayList<>();

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        this.b = constraintWidgetContainer;
        this.e = constraintWidgetContainer;
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.h = measurer;
    }

    private int a(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        int size = this.a.size();
        long j = 0;
        for (int i2 = 0; i2 < size; i2++) {
            j = Math.max(j, this.a.get(i2).a(constraintWidgetContainer, i));
        }
        return (int) j;
    }

    public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2) {
        if (this.c) {
            buildGraph();
            Iterator it = this.b.mChildren.iterator();
            boolean z = false;
            while (it.hasNext()) {
                ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
                constraintWidget.isTerminalWidget[0] = true;
                constraintWidget.isTerminalWidget[1] = true;
                if (constraintWidget instanceof Barrier) {
                    z = true;
                }
            }
            if (!z) {
                Iterator<e> it2 = this.a.iterator();
                while (it2.hasNext()) {
                    it2.next().a(dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                }
            }
        }
    }

    public boolean directMeasure(boolean z) {
        boolean z2;
        boolean z3 = z & true;
        boolean z4 = false;
        if (this.c || this.d) {
            Iterator it = this.b.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
                constraintWidget.ensureWidgetRuns();
                constraintWidget.measured = false;
                constraintWidget.horizontalRun.c();
                constraintWidget.verticalRun.c();
            }
            this.b.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.b;
            constraintWidgetContainer.measured = false;
            constraintWidgetContainer.horizontalRun.c();
            this.b.verticalRun.c();
            this.d = false;
        }
        if (a(this.e)) {
            return false;
        }
        this.b.setX(0);
        this.b.setY(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.b.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.b.getDimensionBehaviour(1);
        if (this.c) {
            buildGraph();
        }
        int x = this.b.getX();
        int y = this.b.getY();
        this.b.horizontalRun.start.resolve(x);
        this.b.verticalRun.start.resolve(y);
        measureWidgets();
        if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            if (z3) {
                Iterator<WidgetRun> it2 = this.f.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (!it2.next().a()) {
                            z3 = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (z3 && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.b.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.b;
                constraintWidgetContainer2.setWidth(a(constraintWidgetContainer2, 0));
                this.b.horizontalRun.d.resolve(this.b.getWidth());
            }
            if (z3 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.b.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer3 = this.b;
                constraintWidgetContainer3.setHeight(a(constraintWidgetContainer3, 1));
                this.b.verticalRun.d.resolve(this.b.getHeight());
            }
        }
        if (this.b.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || this.b.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int width = this.b.getWidth() + x;
            this.b.horizontalRun.end.resolve(width);
            this.b.horizontalRun.d.resolve(width - x);
            measureWidgets();
            if (this.b.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.b.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = this.b.getHeight() + y;
                this.b.verticalRun.end.resolve(height);
                this.b.verticalRun.d.resolve(height - y);
            }
            measureWidgets();
            z2 = true;
        } else {
            z2 = false;
        }
        Iterator<WidgetRun> it3 = this.f.iterator();
        while (it3.hasNext()) {
            WidgetRun next = it3.next();
            if (next.b != this.b || next.e) {
                next.applyToWidget();
            }
        }
        Iterator<WidgetRun> it4 = this.f.iterator();
        while (true) {
            if (!it4.hasNext()) {
                z4 = true;
                break;
            }
            WidgetRun next2 = it4.next();
            if (z2 || next2.b != this.b) {
                if (!next2.start.resolved) {
                    break;
                }
                if (!next2.end.resolved) {
                    if (!(next2 instanceof c)) {
                        break;
                    }
                }
                if (!next2.d.resolved && !(next2 instanceof ChainRun) && !(next2 instanceof c)) {
                    break;
                }
            }
        }
        this.b.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.b.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z4;
    }

    public boolean directMeasureSetup(boolean z) {
        if (this.c) {
            Iterator it = this.b.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
                constraintWidget.ensureWidgetRuns();
                constraintWidget.measured = false;
                constraintWidget.horizontalRun.d.resolved = false;
                constraintWidget.horizontalRun.e = false;
                constraintWidget.horizontalRun.c();
                constraintWidget.verticalRun.d.resolved = false;
                constraintWidget.verticalRun.e = false;
                constraintWidget.verticalRun.c();
            }
            this.b.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.b;
            constraintWidgetContainer.measured = false;
            constraintWidgetContainer.horizontalRun.d.resolved = false;
            this.b.horizontalRun.e = false;
            this.b.horizontalRun.c();
            this.b.verticalRun.d.resolved = false;
            this.b.verticalRun.e = false;
            this.b.verticalRun.c();
            buildGraph();
        }
        if (a(this.e)) {
            return false;
        }
        this.b.setX(0);
        this.b.setY(0);
        this.b.horizontalRun.start.resolve(0);
        this.b.verticalRun.start.resolve(0);
        return true;
    }

    public boolean directMeasureWithOrientation(boolean z, int i) {
        boolean z2;
        boolean z3 = z & true;
        boolean z4 = false;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.b.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.b.getDimensionBehaviour(1);
        int x = this.b.getX();
        int y = this.b.getY();
        if (z3 && (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
            Iterator<WidgetRun> it = this.f.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WidgetRun next = it.next();
                if (next.orientation == i && !next.a()) {
                    z3 = false;
                    break;
                }
            }
            if (i == 0) {
                if (z3 && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.b.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    ConstraintWidgetContainer constraintWidgetContainer = this.b;
                    constraintWidgetContainer.setWidth(a(constraintWidgetContainer, 0));
                    this.b.horizontalRun.d.resolve(this.b.getWidth());
                }
            } else if (z3 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.b.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.b;
                constraintWidgetContainer2.setHeight(a(constraintWidgetContainer2, 1));
                this.b.verticalRun.d.resolve(this.b.getHeight());
            }
        }
        if (i == 0) {
            if (this.b.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || this.b.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int width = this.b.getWidth() + x;
                this.b.horizontalRun.end.resolve(width);
                this.b.horizontalRun.d.resolve(width - x);
                z2 = true;
            }
            z2 = false;
        } else {
            if (this.b.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.b.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int height = this.b.getHeight() + y;
                this.b.verticalRun.end.resolve(height);
                this.b.verticalRun.d.resolve(height - y);
                z2 = true;
            }
            z2 = false;
        }
        measureWidgets();
        Iterator<WidgetRun> it2 = this.f.iterator();
        while (it2.hasNext()) {
            WidgetRun next2 = it2.next();
            if (next2.orientation == i && (next2.b != this.b || next2.e)) {
                next2.applyToWidget();
            }
        }
        Iterator<WidgetRun> it3 = this.f.iterator();
        while (true) {
            if (!it3.hasNext()) {
                z4 = true;
                break;
            }
            WidgetRun next3 = it3.next();
            if (next3.orientation == i && (z2 || next3.b != this.b)) {
                if (next3.start.resolved) {
                    if (next3.end.resolved) {
                        if (!(next3 instanceof ChainRun) && !next3.d.resolved) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        this.b.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.b.setVerticalDimensionBehaviour(dimensionBehaviour2);
        return z4;
    }

    private void a(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        BasicMeasure.Measure measure = this.i;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        this.h.measure(constraintWidget, measure);
        constraintWidget.setWidth(this.i.measuredWidth);
        constraintWidget.setHeight(this.i.measuredHeight);
        constraintWidget.setHasBaseline(this.i.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.i.measuredBaseline);
    }

    private boolean a(ConstraintWidgetContainer constraintWidgetContainer) {
        int i;
        int i2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        Iterator it = constraintWidgetContainer.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget.mListDimensionBehaviors[1];
            if (constraintWidget.getVisibility() == 8) {
                constraintWidget.measured = true;
            } else {
                if (constraintWidget.mMatchConstraintPercentWidth < 1.0f && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultWidth = 2;
                }
                if (constraintWidget.mMatchConstraintPercentHeight < 1.0f && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.mMatchConstraintDefaultHeight = 2;
                }
                if (constraintWidget.getDimensionRatio() > 0.0f) {
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultWidth = 3;
                    } else if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        constraintWidget.mMatchConstraintDefaultHeight = 3;
                    } else if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            constraintWidget.mMatchConstraintDefaultWidth = 3;
                        }
                        if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                            constraintWidget.mMatchConstraintDefaultHeight = 3;
                        }
                    }
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 1 && (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget == null)) ? ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : dimensionBehaviour2;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 1 && (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null)) ? ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : dimensionBehaviour3;
                constraintWidget.horizontalRun.dimensionBehavior = dimensionBehaviour4;
                constraintWidget.horizontalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultWidth;
                constraintWidget.verticalRun.dimensionBehavior = dimensionBehaviour5;
                constraintWidget.verticalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultHeight;
                if ((dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                    int width = constraintWidget.getWidth();
                    if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        i = (constraintWidgetContainer.getWidth() - constraintWidget.mLeft.mMargin) - constraintWidget.mRight.mMargin;
                        dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.FIXED;
                    } else {
                        i = width;
                    }
                    int height = constraintWidget.getHeight();
                    if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                        i2 = (constraintWidgetContainer.getHeight() - constraintWidget.mTop.mMargin) - constraintWidget.mBottom.mMargin;
                        dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    } else {
                        dimensionBehaviour = dimensionBehaviour5;
                        i2 = height;
                    }
                    a(constraintWidget, dimensionBehaviour4, i, dimensionBehaviour, i2);
                    constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                } else {
                    if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 3) {
                            if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                                a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            }
                            int height2 = constraintWidget.getHeight();
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((height2 * constraintWidget.mDimensionRatio) + 0.5f), ConstraintWidget.DimensionBehaviour.FIXED, height2);
                            constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        } else if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour5, 0);
                            constraintWidget.horizontalRun.d.h = constraintWidget.getWidth();
                        } else if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                            if (constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentWidth * constraintWidgetContainer.getWidth()) + 0.5f), dimensionBehaviour5, constraintWidget.getHeight());
                                constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                                constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            }
                        } else if (constraintWidget.mListAnchors[0].mTarget == null || constraintWidget.mListAnchors[1].mTarget == null) {
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour5, 0);
                            constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        }
                    }
                    if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                        if (constraintWidget.mMatchConstraintDefaultHeight == 3) {
                            if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                                a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            }
                            int width2 = constraintWidget.getWidth();
                            float f = constraintWidget.mDimensionRatio;
                            if (constraintWidget.getDimensionRatioSide() == -1) {
                                f = 1.0f / f;
                            }
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, width2, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((width2 * f) + 0.5f));
                            constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
                            a(constraintWidget, dimensionBehaviour4, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            constraintWidget.verticalRun.d.h = constraintWidget.getHeight();
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                            if (constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                                a(constraintWidget, dimensionBehaviour4, constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentHeight * constraintWidgetContainer.getHeight()) + 0.5f));
                                constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                                constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            }
                        } else if (constraintWidget.mListAnchors[2].mTarget == null || constraintWidget.mListAnchors[3].mTarget == null) {
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour5, 0);
                            constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                        }
                    }
                    if (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (constraintWidget.mMatchConstraintDefaultWidth == 1 || constraintWidget.mMatchConstraintDefaultHeight == 1) {
                            a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                            constraintWidget.horizontalRun.d.h = constraintWidget.getWidth();
                            constraintWidget.verticalRun.d.h = constraintWidget.getHeight();
                        } else if (constraintWidget.mMatchConstraintDefaultHeight == 2 && constraintWidget.mMatchConstraintDefaultWidth == 2 && (constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED)) {
                            if (constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
                                a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentWidth * constraintWidgetContainer.getWidth()) + 0.5f), ConstraintWidget.DimensionBehaviour.FIXED, (int) ((constraintWidget.mMatchConstraintPercentHeight * constraintWidgetContainer.getHeight()) + 0.5f));
                                constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                                constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                                constraintWidget.measured = true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void measureWidgets() {
        Iterator it = this.b.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            if (!constraintWidget.measured) {
                boolean z = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.mListDimensionBehaviors[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[1];
                int i = constraintWidget.mMatchConstraintDefaultWidth;
                int i2 = constraintWidget.mMatchConstraintDefaultHeight;
                boolean z2 = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i == 1);
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && i2 == 1)) {
                    z = true;
                }
                boolean z3 = constraintWidget.horizontalRun.d.resolved;
                boolean z4 = constraintWidget.verticalRun.d.resolved;
                if (z3 && z4) {
                    a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.horizontalRun.d.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.verticalRun.d.value);
                    constraintWidget.measured = true;
                } else if (z3 && z) {
                    a(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.horizontalRun.d.value, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.verticalRun.d.value);
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        constraintWidget.verticalRun.d.h = constraintWidget.getHeight();
                    } else {
                        constraintWidget.verticalRun.d.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                    }
                } else if (z4 && z2) {
                    a(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.horizontalRun.d.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.verticalRun.d.value);
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        constraintWidget.horizontalRun.d.h = constraintWidget.getWidth();
                    } else {
                        constraintWidget.horizontalRun.d.resolve(constraintWidget.getWidth());
                        constraintWidget.measured = true;
                    }
                }
                if (constraintWidget.measured && constraintWidget.verticalRun.a != null) {
                    constraintWidget.verticalRun.a.resolve(constraintWidget.getBaselineDistance());
                }
            }
        }
    }

    public void invalidateGraph() {
        this.c = true;
    }

    public void invalidateMeasures() {
        this.d = true;
    }

    public void buildGraph() {
        buildGraph(this.f);
        this.a.clear();
        e.a = 0;
        a(this.b.horizontalRun, 0, this.a);
        a(this.b.verticalRun, 1, this.a);
        this.c = false;
    }

    public void buildGraph(ArrayList<WidgetRun> arrayList) {
        arrayList.clear();
        this.e.horizontalRun.b();
        this.e.verticalRun.b();
        arrayList.add(this.e.horizontalRun);
        arrayList.add(this.e.verticalRun);
        Iterator it = this.e.mChildren.iterator();
        HashSet hashSet = null;
        while (it.hasNext()) {
            ConstraintWidget constraintWidget = (ConstraintWidget) it.next();
            if (constraintWidget instanceof Guideline) {
                arrayList.add(new c(constraintWidget));
            } else {
                if (constraintWidget.isInHorizontalChain()) {
                    if (constraintWidget.horizontalChainRun == null) {
                        constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.horizontalChainRun);
                } else {
                    arrayList.add(constraintWidget.horizontalRun);
                }
                if (constraintWidget.isInVerticalChain()) {
                    if (constraintWidget.verticalChainRun == null) {
                        constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(constraintWidget.verticalChainRun);
                } else {
                    arrayList.add(constraintWidget.verticalRun);
                }
                if (constraintWidget instanceof HelperWidget) {
                    arrayList.add(new d(constraintWidget));
                }
            }
        }
        if (hashSet != null) {
            arrayList.addAll(hashSet);
        }
        Iterator<WidgetRun> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            it2.next().b();
        }
        Iterator<WidgetRun> it3 = arrayList.iterator();
        while (it3.hasNext()) {
            WidgetRun next = it3.next();
            if (next.b != this.e) {
                next.d();
            }
        }
    }

    private void a(DependencyNode dependencyNode, int i, int i2, DependencyNode dependencyNode2, ArrayList<e> arrayList, e eVar) {
        WidgetRun widgetRun = dependencyNode.a;
        if (!(widgetRun.c != null || widgetRun == this.b.horizontalRun || widgetRun == this.b.verticalRun)) {
            if (eVar == null) {
                eVar = new e(widgetRun, i2);
                arrayList.add(eVar);
            }
            widgetRun.c = eVar;
            eVar.a(widgetRun);
            for (Dependency dependency : widgetRun.start.f) {
                if (dependency instanceof DependencyNode) {
                    a((DependencyNode) dependency, i, 0, dependencyNode2, arrayList, eVar);
                }
            }
            for (Dependency dependency2 : widgetRun.end.f) {
                if (dependency2 instanceof DependencyNode) {
                    a((DependencyNode) dependency2, i, 1, dependencyNode2, arrayList, eVar);
                }
            }
            if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.f) {
                    if (dependency3 instanceof DependencyNode) {
                        a((DependencyNode) dependency3, i, 2, dependencyNode2, arrayList, eVar);
                    }
                }
            }
            for (DependencyNode dependencyNode3 : widgetRun.start.g) {
                if (dependencyNode3 == dependencyNode2) {
                    eVar.c = true;
                }
                a(dependencyNode3, i, 0, dependencyNode2, arrayList, eVar);
            }
            for (DependencyNode dependencyNode4 : widgetRun.end.g) {
                if (dependencyNode4 == dependencyNode2) {
                    eVar.c = true;
                }
                a(dependencyNode4, i, 1, dependencyNode2, arrayList, eVar);
            }
            if (i == 1 && (widgetRun instanceof VerticalWidgetRun)) {
                for (DependencyNode dependencyNode5 : ((VerticalWidgetRun) widgetRun).baseline.g) {
                    a(dependencyNode5, i, 2, dependencyNode2, arrayList, eVar);
                }
            }
        }
    }

    private void a(WidgetRun widgetRun, int i, ArrayList<e> arrayList) {
        for (Dependency dependency : widgetRun.start.f) {
            if (dependency instanceof DependencyNode) {
                a((DependencyNode) dependency, i, 0, widgetRun.end, arrayList, null);
            } else if (dependency instanceof WidgetRun) {
                a(((WidgetRun) dependency).start, i, 0, widgetRun.end, arrayList, null);
            }
        }
        for (Dependency dependency2 : widgetRun.end.f) {
            if (dependency2 instanceof DependencyNode) {
                a((DependencyNode) dependency2, i, 1, widgetRun.start, arrayList, null);
            } else if (dependency2 instanceof WidgetRun) {
                a(((WidgetRun) dependency2).end, i, 1, widgetRun.start, arrayList, null);
            }
        }
        if (i == 1) {
            for (Dependency dependency3 : ((VerticalWidgetRun) widgetRun).baseline.f) {
                if (dependency3 instanceof DependencyNode) {
                    a((DependencyNode) dependency3, i, 2, null, arrayList, null);
                }
            }
        }
    }
}
