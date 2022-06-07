package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ChainHead {
    int a;
    int b;
    int c;
    boolean d;
    private int e;
    private boolean f;
    private boolean g;
    protected ConstraintWidget mFirst;
    protected ConstraintWidget mFirstMatchConstraintWidget;
    protected ConstraintWidget mFirstVisibleWidget;
    protected boolean mHasComplexMatchWeights;
    protected boolean mHasDefinedWeights;
    protected boolean mHasRatio;
    protected boolean mHasUndefinedWeights;
    protected ConstraintWidget mHead;
    protected ConstraintWidget mLast;
    protected ConstraintWidget mLastMatchConstraintWidget;
    protected ConstraintWidget mLastVisibleWidget;
    protected float mTotalWeight = 0.0f;
    protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
    protected int mWidgetsCount;
    protected int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int i, boolean z) {
        this.f = false;
        this.mFirst = constraintWidget;
        this.e = i;
        this.f = z;
    }

    private static boolean a(ConstraintWidget constraintWidget, int i) {
        return constraintWidget.getVisibility() != 8 && constraintWidget.mListDimensionBehaviors[i] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (constraintWidget.mResolvedMatchConstraintDefault[i] == 0 || constraintWidget.mResolvedMatchConstraintDefault[i] == 3);
    }

    private void a() {
        int i = this.e * 2;
        ConstraintWidget constraintWidget = this.mFirst;
        boolean z = true;
        this.d = true;
        ConstraintWidget constraintWidget2 = constraintWidget;
        ConstraintWidget constraintWidget3 = constraintWidget2;
        boolean z2 = false;
        while (!z2) {
            this.mWidgetsCount++;
            constraintWidget2 = null;
            constraintWidget2.mNextChainWidget[this.e] = null;
            constraintWidget2.mListNextMatchConstraintsWidget[this.e] = null;
            if (constraintWidget2.getVisibility() != 8) {
                this.a++;
                if (constraintWidget2.getDimensionBehaviour(this.e) != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.b += constraintWidget2.getLength(this.e);
                }
                this.b += constraintWidget2.mListAnchors[i].getMargin();
                int i2 = i + 1;
                this.b += constraintWidget2.mListAnchors[i2].getMargin();
                this.c += constraintWidget2.mListAnchors[i].getMargin();
                this.c += constraintWidget2.mListAnchors[i2].getMargin();
                if (this.mFirstVisibleWidget == null) {
                    this.mFirstVisibleWidget = constraintWidget2;
                }
                this.mLastVisibleWidget = constraintWidget2;
                if (constraintWidget2.mListDimensionBehaviors[this.e] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (constraintWidget2.mResolvedMatchConstraintDefault[this.e] == 0 || constraintWidget2.mResolvedMatchConstraintDefault[this.e] == 3 || constraintWidget2.mResolvedMatchConstraintDefault[this.e] == 2) {
                        this.mWidgetsMatchCount++;
                        float f = constraintWidget2.mWeight[this.e];
                        if (f > 0.0f) {
                            this.mTotalWeight += constraintWidget2.mWeight[this.e];
                        }
                        if (a(constraintWidget2, this.e)) {
                            if (f < 0.0f) {
                                this.mHasUndefinedWeights = true;
                            } else {
                                this.mHasDefinedWeights = true;
                            }
                            if (this.mWeightedMatchConstraintsWidgets == null) {
                                this.mWeightedMatchConstraintsWidgets = new ArrayList<>();
                            }
                            this.mWeightedMatchConstraintsWidgets.add(constraintWidget2);
                        }
                        if (this.mFirstMatchConstraintWidget == null) {
                            this.mFirstMatchConstraintWidget = constraintWidget2;
                        }
                        ConstraintWidget constraintWidget4 = this.mLastMatchConstraintWidget;
                        if (constraintWidget4 != null) {
                            constraintWidget4.mListNextMatchConstraintsWidget[this.e] = constraintWidget2;
                        }
                        this.mLastMatchConstraintWidget = constraintWidget2;
                    }
                    if (this.e == 0) {
                        if (constraintWidget2.mMatchConstraintDefaultWidth != 0) {
                            this.d = false;
                        } else if (!(constraintWidget2.mMatchConstraintMinWidth == 0 && constraintWidget2.mMatchConstraintMaxWidth == 0)) {
                            this.d = false;
                        }
                    } else if (constraintWidget2.mMatchConstraintDefaultHeight != 0) {
                        this.d = false;
                    } else if (!(constraintWidget2.mMatchConstraintMinHeight == 0 && constraintWidget2.mMatchConstraintMaxHeight == 0)) {
                        this.d = false;
                    }
                    if (constraintWidget2.mDimensionRatio != 0.0f) {
                        this.d = false;
                        this.mHasRatio = true;
                    }
                }
            }
            if (constraintWidget3 != constraintWidget2) {
                constraintWidget3.mNextChainWidget[this.e] = constraintWidget2;
            }
            ConstraintAnchor constraintAnchor = constraintWidget2.mListAnchors[i + 1].mTarget;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.mOwner;
                if (constraintWidget5.mListAnchors[i].mTarget != null && constraintWidget5.mListAnchors[i].mTarget.mOwner == constraintWidget2) {
                    constraintWidget2 = constraintWidget5;
                }
            }
            if (constraintWidget2 == null) {
                z2 = true;
                constraintWidget2 = constraintWidget2;
            }
            constraintWidget3 = constraintWidget2;
        }
        ConstraintWidget constraintWidget6 = this.mFirstVisibleWidget;
        if (constraintWidget6 != null) {
            this.b -= constraintWidget6.mListAnchors[i].getMargin();
        }
        ConstraintWidget constraintWidget7 = this.mLastVisibleWidget;
        if (constraintWidget7 != null) {
            this.b -= constraintWidget7.mListAnchors[i + 1].getMargin();
        }
        this.mLast = constraintWidget2;
        if (this.e != 0 || !this.f) {
            this.mHead = this.mFirst;
        } else {
            this.mHead = this.mLast;
        }
        if (!this.mHasDefinedWeights || !this.mHasUndefinedWeights) {
            z = false;
        }
        this.mHasComplexMatchWeights = z;
    }

    public ConstraintWidget getFirst() {
        return this.mFirst;
    }

    public ConstraintWidget getFirstVisibleWidget() {
        return this.mFirstVisibleWidget;
    }

    public ConstraintWidget getLast() {
        return this.mLast;
    }

    public ConstraintWidget getLastVisibleWidget() {
        return this.mLastVisibleWidget;
    }

    public ConstraintWidget getHead() {
        return this.mHead;
    }

    public ConstraintWidget getFirstMatchConstraintWidget() {
        return this.mFirstMatchConstraintWidget;
    }

    public ConstraintWidget getLastMatchConstraintWidget() {
        return this.mLastMatchConstraintWidget;
    }

    public float getTotalWeight() {
        return this.mTotalWeight;
    }

    public void define() {
        if (!this.g) {
            a();
        }
        this.g = true;
    }
}
