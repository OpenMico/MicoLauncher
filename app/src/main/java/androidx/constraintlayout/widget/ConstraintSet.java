package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintSet {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static final int END = 7;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int HORIZONTAL_GUIDELINE = 0;
    public static final int INVISIBLE = 4;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int VERTICAL = 1;
    public static final int VERTICAL_GUIDELINE = 1;
    public static final int VISIBILITY_MODE_IGNORE = 1;
    public static final int VISIBILITY_MODE_NORMAL = 0;
    public static final int VISIBLE = 0;
    public static final int WRAP_CONTENT = -2;
    private static final int[] d = {0, 4, 8};
    private static SparseIntArray f = new SparseIntArray();
    private boolean a;
    private HashMap<String, ConstraintAttribute> b = new HashMap<>();
    private boolean c = true;
    private HashMap<Integer, Constraint> e = new HashMap<>();

    private String b(int i) {
        switch (i) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    static {
        f.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
        f.append(R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
        f.append(R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
        f.append(R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
        f.append(R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
        f.append(R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
        f.append(R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
        f.append(R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
        f.append(R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        f.append(R.styleable.Constraint_layout_editor_absoluteX, 6);
        f.append(R.styleable.Constraint_layout_editor_absoluteY, 7);
        f.append(R.styleable.Constraint_layout_constraintGuide_begin, 17);
        f.append(R.styleable.Constraint_layout_constraintGuide_end, 18);
        f.append(R.styleable.Constraint_layout_constraintGuide_percent, 19);
        f.append(R.styleable.Constraint_android_orientation, 27);
        f.append(R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
        f.append(R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
        f.append(R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
        f.append(R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
        f.append(R.styleable.Constraint_layout_goneMarginLeft, 13);
        f.append(R.styleable.Constraint_layout_goneMarginTop, 16);
        f.append(R.styleable.Constraint_layout_goneMarginRight, 14);
        f.append(R.styleable.Constraint_layout_goneMarginBottom, 11);
        f.append(R.styleable.Constraint_layout_goneMarginStart, 15);
        f.append(R.styleable.Constraint_layout_goneMarginEnd, 12);
        f.append(R.styleable.Constraint_layout_constraintVertical_weight, 40);
        f.append(R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
        f.append(R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
        f.append(R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
        f.append(R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
        f.append(R.styleable.Constraint_layout_constraintVertical_bias, 37);
        f.append(R.styleable.Constraint_layout_constraintDimensionRatio, 5);
        f.append(R.styleable.Constraint_layout_constraintLeft_creator, 82);
        f.append(R.styleable.Constraint_layout_constraintTop_creator, 82);
        f.append(R.styleable.Constraint_layout_constraintRight_creator, 82);
        f.append(R.styleable.Constraint_layout_constraintBottom_creator, 82);
        f.append(R.styleable.Constraint_layout_constraintBaseline_creator, 82);
        f.append(R.styleable.Constraint_android_layout_marginLeft, 24);
        f.append(R.styleable.Constraint_android_layout_marginRight, 28);
        f.append(R.styleable.Constraint_android_layout_marginStart, 31);
        f.append(R.styleable.Constraint_android_layout_marginEnd, 8);
        f.append(R.styleable.Constraint_android_layout_marginTop, 34);
        f.append(R.styleable.Constraint_android_layout_marginBottom, 2);
        f.append(R.styleable.Constraint_android_layout_width, 23);
        f.append(R.styleable.Constraint_android_layout_height, 21);
        f.append(R.styleable.Constraint_android_visibility, 22);
        f.append(R.styleable.Constraint_android_alpha, 43);
        f.append(R.styleable.Constraint_android_elevation, 44);
        f.append(R.styleable.Constraint_android_rotationX, 45);
        f.append(R.styleable.Constraint_android_rotationY, 46);
        f.append(R.styleable.Constraint_android_rotation, 60);
        f.append(R.styleable.Constraint_android_scaleX, 47);
        f.append(R.styleable.Constraint_android_scaleY, 48);
        f.append(R.styleable.Constraint_android_transformPivotX, 49);
        f.append(R.styleable.Constraint_android_transformPivotY, 50);
        f.append(R.styleable.Constraint_android_translationX, 51);
        f.append(R.styleable.Constraint_android_translationY, 52);
        f.append(R.styleable.Constraint_android_translationZ, 53);
        f.append(R.styleable.Constraint_layout_constraintWidth_default, 54);
        f.append(R.styleable.Constraint_layout_constraintHeight_default, 55);
        f.append(R.styleable.Constraint_layout_constraintWidth_max, 56);
        f.append(R.styleable.Constraint_layout_constraintHeight_max, 57);
        f.append(R.styleable.Constraint_layout_constraintWidth_min, 58);
        f.append(R.styleable.Constraint_layout_constraintHeight_min, 59);
        f.append(R.styleable.Constraint_layout_constraintCircle, 61);
        f.append(R.styleable.Constraint_layout_constraintCircleRadius, 62);
        f.append(R.styleable.Constraint_layout_constraintCircleAngle, 63);
        f.append(R.styleable.Constraint_animate_relativeTo, 64);
        f.append(R.styleable.Constraint_transitionEasing, 65);
        f.append(R.styleable.Constraint_drawPath, 66);
        f.append(R.styleable.Constraint_transitionPathRotate, 67);
        f.append(R.styleable.Constraint_motionStagger, 79);
        f.append(R.styleable.Constraint_android_id, 38);
        f.append(R.styleable.Constraint_motionProgress, 68);
        f.append(R.styleable.Constraint_layout_constraintWidth_percent, 69);
        f.append(R.styleable.Constraint_layout_constraintHeight_percent, 70);
        f.append(R.styleable.Constraint_chainUseRtl, 71);
        f.append(R.styleable.Constraint_barrierDirection, 72);
        f.append(R.styleable.Constraint_barrierMargin, 73);
        f.append(R.styleable.Constraint_constraint_referenced_ids, 74);
        f.append(R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
        f.append(R.styleable.Constraint_pathMotionArc, 76);
        f.append(R.styleable.Constraint_layout_constraintTag, 77);
        f.append(R.styleable.Constraint_visibilityMode, 78);
        f.append(R.styleable.Constraint_layout_constrainedWidth, 80);
        f.append(R.styleable.Constraint_layout_constrainedHeight, 81);
    }

    public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
        return this.b;
    }

    public Constraint getParameters(int i) {
        return a(i);
    }

    public void readFallback(ConstraintSet constraintSet) {
        for (Integer num : constraintSet.e.keySet()) {
            int intValue = num.intValue();
            Constraint constraint = constraintSet.e.get(num);
            if (!this.e.containsKey(Integer.valueOf(intValue))) {
                this.e.put(Integer.valueOf(intValue), new Constraint());
            }
            Constraint constraint2 = this.e.get(Integer.valueOf(intValue));
            if (!constraint2.layout.mApply) {
                constraint2.layout.copyFrom(constraint.layout);
            }
            if (!constraint2.propertySet.mApply) {
                constraint2.propertySet.copyFrom(constraint.propertySet);
            }
            if (!constraint2.transform.mApply) {
                constraint2.transform.copyFrom(constraint.transform);
            }
            if (!constraint2.motion.mApply) {
                constraint2.motion.copyFrom(constraint.motion);
            }
            for (String str : constraint.mCustomConstraints.keySet()) {
                if (!constraint2.mCustomConstraints.containsKey(str)) {
                    constraint2.mCustomConstraints.put(str, constraint.mCustomConstraints.get(str));
                }
            }
        }
    }

    public void readFallback(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (!this.c || id != -1) {
                if (!this.e.containsKey(Integer.valueOf(id))) {
                    this.e.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.e.get(Integer.valueOf(id));
                if (!constraint.layout.mApply) {
                    constraint.a(id, layoutParams);
                    if (childAt instanceof ConstraintHelper) {
                        constraint.layout.mReferenceIds = ((ConstraintHelper) childAt).getReferencedIds();
                        if (childAt instanceof Barrier) {
                            Barrier barrier = (Barrier) childAt;
                            constraint.layout.mBarrierAllowsGoneWidgets = barrier.allowsGoneWidget();
                            constraint.layout.mBarrierDirection = barrier.getType();
                            constraint.layout.mBarrierMargin = barrier.getMargin();
                        }
                    }
                    constraint.layout.mApply = true;
                }
                if (!constraint.propertySet.mApply) {
                    constraint.propertySet.visibility = childAt.getVisibility();
                    constraint.propertySet.alpha = childAt.getAlpha();
                    constraint.propertySet.mApply = true;
                }
                if (Build.VERSION.SDK_INT >= 17 && !constraint.transform.mApply) {
                    constraint.transform.mApply = true;
                    constraint.transform.rotation = childAt.getRotation();
                    constraint.transform.rotationX = childAt.getRotationX();
                    constraint.transform.rotationY = childAt.getRotationY();
                    constraint.transform.scaleX = childAt.getScaleX();
                    constraint.transform.scaleY = childAt.getScaleY();
                    float pivotX = childAt.getPivotX();
                    float pivotY = childAt.getPivotY();
                    if (!(pivotX == 0.0d && pivotY == 0.0d)) {
                        constraint.transform.transformPivotX = pivotX;
                        constraint.transform.transformPivotY = pivotY;
                    }
                    constraint.transform.translationX = childAt.getTranslationX();
                    constraint.transform.translationY = childAt.getTranslationY();
                    if (Build.VERSION.SDK_INT >= 21) {
                        constraint.transform.translationZ = childAt.getTranslationZ();
                        if (constraint.transform.applyElevation) {
                            constraint.transform.elevation = childAt.getElevation();
                        }
                    }
                }
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Layout {
        public static final int UNSET = -1;
        private static SparseIntArray a = new SparseIntArray();
        public String mConstraintTag;
        public int mHeight;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public boolean mIsGuideline = false;
        public boolean mApply = false;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int topToTop = -1;
        public int topToBottom = -1;
        public int bottomToTop = -1;
        public int bottomToBottom = -1;
        public int baselineToBaseline = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int endToStart = -1;
        public int endToEnd = -1;
        public float horizontalBias = 0.5f;
        public float verticalBias = 0.5f;
        public String dimensionRatio = null;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public float circleAngle = 0.0f;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int orientation = -1;
        public int leftMargin = -1;
        public int rightMargin = -1;
        public int topMargin = -1;
        public int bottomMargin = -1;
        public int endMargin = -1;
        public int startMargin = -1;
        public int goneLeftMargin = -1;
        public int goneTopMargin = -1;
        public int goneRightMargin = -1;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneStartMargin = -1;
        public float verticalWeight = -1.0f;
        public float horizontalWeight = -1.0f;
        public int horizontalChainStyle = 0;
        public int verticalChainStyle = 0;
        public int widthDefault = 0;
        public int heightDefault = 0;
        public int widthMax = -1;
        public int heightMax = -1;
        public int widthMin = -1;
        public int heightMin = -1;
        public float widthPercent = 1.0f;
        public float heightPercent = 1.0f;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public int mHelperType = -1;
        public boolean constrainedWidth = false;
        public boolean constrainedHeight = false;
        public boolean mBarrierAllowsGoneWidgets = true;

        public void copyFrom(Layout layout) {
            this.mIsGuideline = layout.mIsGuideline;
            this.mWidth = layout.mWidth;
            this.mApply = layout.mApply;
            this.mHeight = layout.mHeight;
            this.guideBegin = layout.guideBegin;
            this.guideEnd = layout.guideEnd;
            this.guidePercent = layout.guidePercent;
            this.leftToLeft = layout.leftToLeft;
            this.leftToRight = layout.leftToRight;
            this.rightToLeft = layout.rightToLeft;
            this.rightToRight = layout.rightToRight;
            this.topToTop = layout.topToTop;
            this.topToBottom = layout.topToBottom;
            this.bottomToTop = layout.bottomToTop;
            this.bottomToBottom = layout.bottomToBottom;
            this.baselineToBaseline = layout.baselineToBaseline;
            this.startToEnd = layout.startToEnd;
            this.startToStart = layout.startToStart;
            this.endToStart = layout.endToStart;
            this.endToEnd = layout.endToEnd;
            this.horizontalBias = layout.horizontalBias;
            this.verticalBias = layout.verticalBias;
            this.dimensionRatio = layout.dimensionRatio;
            this.circleConstraint = layout.circleConstraint;
            this.circleRadius = layout.circleRadius;
            this.circleAngle = layout.circleAngle;
            this.editorAbsoluteX = layout.editorAbsoluteX;
            this.editorAbsoluteY = layout.editorAbsoluteY;
            this.orientation = layout.orientation;
            this.leftMargin = layout.leftMargin;
            this.rightMargin = layout.rightMargin;
            this.topMargin = layout.topMargin;
            this.bottomMargin = layout.bottomMargin;
            this.endMargin = layout.endMargin;
            this.startMargin = layout.startMargin;
            this.goneLeftMargin = layout.goneLeftMargin;
            this.goneTopMargin = layout.goneTopMargin;
            this.goneRightMargin = layout.goneRightMargin;
            this.goneBottomMargin = layout.goneBottomMargin;
            this.goneEndMargin = layout.goneEndMargin;
            this.goneStartMargin = layout.goneStartMargin;
            this.verticalWeight = layout.verticalWeight;
            this.horizontalWeight = layout.horizontalWeight;
            this.horizontalChainStyle = layout.horizontalChainStyle;
            this.verticalChainStyle = layout.verticalChainStyle;
            this.widthDefault = layout.widthDefault;
            this.heightDefault = layout.heightDefault;
            this.widthMax = layout.widthMax;
            this.heightMax = layout.heightMax;
            this.widthMin = layout.widthMin;
            this.heightMin = layout.heightMin;
            this.widthPercent = layout.widthPercent;
            this.heightPercent = layout.heightPercent;
            this.mBarrierDirection = layout.mBarrierDirection;
            this.mBarrierMargin = layout.mBarrierMargin;
            this.mHelperType = layout.mHelperType;
            this.mConstraintTag = layout.mConstraintTag;
            int[] iArr = layout.mReferenceIds;
            if (iArr != null) {
                this.mReferenceIds = Arrays.copyOf(iArr, iArr.length);
            } else {
                this.mReferenceIds = null;
            }
            this.mReferenceIdString = layout.mReferenceIdString;
            this.constrainedWidth = layout.constrainedWidth;
            this.constrainedHeight = layout.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = layout.mBarrierAllowsGoneWidgets;
        }

        static {
            a.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
            a.append(R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
            a.append(R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
            a.append(R.styleable.Layout_layout_constraintRight_toRightOf, 29);
            a.append(R.styleable.Layout_layout_constraintTop_toTopOf, 35);
            a.append(R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
            a.append(R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
            a.append(R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
            a.append(R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
            a.append(R.styleable.Layout_layout_editor_absoluteX, 6);
            a.append(R.styleable.Layout_layout_editor_absoluteY, 7);
            a.append(R.styleable.Layout_layout_constraintGuide_begin, 17);
            a.append(R.styleable.Layout_layout_constraintGuide_end, 18);
            a.append(R.styleable.Layout_layout_constraintGuide_percent, 19);
            a.append(R.styleable.Layout_android_orientation, 26);
            a.append(R.styleable.Layout_layout_constraintStart_toEndOf, 31);
            a.append(R.styleable.Layout_layout_constraintStart_toStartOf, 32);
            a.append(R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
            a.append(R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
            a.append(R.styleable.Layout_layout_goneMarginLeft, 13);
            a.append(R.styleable.Layout_layout_goneMarginTop, 16);
            a.append(R.styleable.Layout_layout_goneMarginRight, 14);
            a.append(R.styleable.Layout_layout_goneMarginBottom, 11);
            a.append(R.styleable.Layout_layout_goneMarginStart, 15);
            a.append(R.styleable.Layout_layout_goneMarginEnd, 12);
            a.append(R.styleable.Layout_layout_constraintVertical_weight, 38);
            a.append(R.styleable.Layout_layout_constraintHorizontal_weight, 37);
            a.append(R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
            a.append(R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
            a.append(R.styleable.Layout_layout_constraintHorizontal_bias, 20);
            a.append(R.styleable.Layout_layout_constraintVertical_bias, 36);
            a.append(R.styleable.Layout_layout_constraintDimensionRatio, 5);
            a.append(R.styleable.Layout_layout_constraintLeft_creator, 76);
            a.append(R.styleable.Layout_layout_constraintTop_creator, 76);
            a.append(R.styleable.Layout_layout_constraintRight_creator, 76);
            a.append(R.styleable.Layout_layout_constraintBottom_creator, 76);
            a.append(R.styleable.Layout_layout_constraintBaseline_creator, 76);
            a.append(R.styleable.Layout_android_layout_marginLeft, 23);
            a.append(R.styleable.Layout_android_layout_marginRight, 27);
            a.append(R.styleable.Layout_android_layout_marginStart, 30);
            a.append(R.styleable.Layout_android_layout_marginEnd, 8);
            a.append(R.styleable.Layout_android_layout_marginTop, 33);
            a.append(R.styleable.Layout_android_layout_marginBottom, 2);
            a.append(R.styleable.Layout_android_layout_width, 22);
            a.append(R.styleable.Layout_android_layout_height, 21);
            a.append(R.styleable.Layout_layout_constraintCircle, 61);
            a.append(R.styleable.Layout_layout_constraintCircleRadius, 62);
            a.append(R.styleable.Layout_layout_constraintCircleAngle, 63);
            a.append(R.styleable.Layout_layout_constraintWidth_percent, 69);
            a.append(R.styleable.Layout_layout_constraintHeight_percent, 70);
            a.append(R.styleable.Layout_chainUseRtl, 71);
            a.append(R.styleable.Layout_barrierDirection, 72);
            a.append(R.styleable.Layout_barrierMargin, 73);
            a.append(R.styleable.Layout_constraint_referenced_ids, 74);
            a.append(R.styleable.Layout_barrierAllowsGoneWidgets, 75);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Layout);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                int i2 = a.get(index);
                switch (i2) {
                    case 1:
                        this.baselineToBaseline = ConstraintSet.b(obtainStyledAttributes, index, this.baselineToBaseline);
                        break;
                    case 2:
                        this.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.bottomMargin);
                        break;
                    case 3:
                        this.bottomToBottom = ConstraintSet.b(obtainStyledAttributes, index, this.bottomToBottom);
                        break;
                    case 4:
                        this.bottomToTop = ConstraintSet.b(obtainStyledAttributes, index, this.bottomToTop);
                        break;
                    case 5:
                        this.dimensionRatio = obtainStyledAttributes.getString(index);
                        break;
                    case 6:
                        this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                        break;
                    case 7:
                        this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                        break;
                    case 8:
                        if (Build.VERSION.SDK_INT >= 17) {
                            this.endMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.endMargin);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        this.endToEnd = ConstraintSet.b(obtainStyledAttributes, index, this.endToEnd);
                        break;
                    case 10:
                        this.endToStart = ConstraintSet.b(obtainStyledAttributes, index, this.endToStart);
                        break;
                    case 11:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 12:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 13:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 14:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 15:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 16:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 17:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 18:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 19:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 20:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 21:
                        this.mHeight = obtainStyledAttributes.getLayoutDimension(index, this.mHeight);
                        break;
                    case 22:
                        this.mWidth = obtainStyledAttributes.getLayoutDimension(index, this.mWidth);
                        break;
                    case 23:
                        this.leftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.leftMargin);
                        break;
                    case 24:
                        this.leftToLeft = ConstraintSet.b(obtainStyledAttributes, index, this.leftToLeft);
                        break;
                    case 25:
                        this.leftToRight = ConstraintSet.b(obtainStyledAttributes, index, this.leftToRight);
                        break;
                    case 26:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 27:
                        this.rightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.rightMargin);
                        break;
                    case 28:
                        this.rightToLeft = ConstraintSet.b(obtainStyledAttributes, index, this.rightToLeft);
                        break;
                    case 29:
                        this.rightToRight = ConstraintSet.b(obtainStyledAttributes, index, this.rightToRight);
                        break;
                    case 30:
                        if (Build.VERSION.SDK_INT >= 17) {
                            this.startMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.startMargin);
                            break;
                        } else {
                            break;
                        }
                    case 31:
                        this.startToEnd = ConstraintSet.b(obtainStyledAttributes, index, this.startToEnd);
                        break;
                    case 32:
                        this.startToStart = ConstraintSet.b(obtainStyledAttributes, index, this.startToStart);
                        break;
                    case 33:
                        this.topMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.topMargin);
                        break;
                    case 34:
                        this.topToBottom = ConstraintSet.b(obtainStyledAttributes, index, this.topToBottom);
                        break;
                    case 35:
                        this.topToTop = ConstraintSet.b(obtainStyledAttributes, index, this.topToTop);
                        break;
                    case 36:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 37:
                        this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                        break;
                    case 38:
                        this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                        break;
                    case 39:
                        this.horizontalChainStyle = obtainStyledAttributes.getInt(index, this.horizontalChainStyle);
                        break;
                    case 40:
                        this.verticalChainStyle = obtainStyledAttributes.getInt(index, this.verticalChainStyle);
                        break;
                    default:
                        switch (i2) {
                            case 54:
                                this.widthDefault = obtainStyledAttributes.getInt(index, this.widthDefault);
                                continue;
                            case 55:
                                this.heightDefault = obtainStyledAttributes.getInt(index, this.heightDefault);
                                continue;
                            case 56:
                                this.widthMax = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMax);
                                continue;
                            case 57:
                                this.heightMax = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMax);
                                continue;
                            case 58:
                                this.widthMin = obtainStyledAttributes.getDimensionPixelSize(index, this.widthMin);
                                continue;
                            case 59:
                                this.heightMin = obtainStyledAttributes.getDimensionPixelSize(index, this.heightMin);
                                continue;
                            default:
                                switch (i2) {
                                    case 61:
                                        this.circleConstraint = ConstraintSet.b(obtainStyledAttributes, index, this.circleConstraint);
                                        continue;
                                    case 62:
                                        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                                        continue;
                                    case 63:
                                        this.circleAngle = obtainStyledAttributes.getFloat(index, this.circleAngle);
                                        continue;
                                    default:
                                        switch (i2) {
                                            case 69:
                                                this.widthPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                                continue;
                                            case 70:
                                                this.heightPercent = obtainStyledAttributes.getFloat(index, 1.0f);
                                                continue;
                                            case 71:
                                                Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                                                continue;
                                            case 72:
                                                this.mBarrierDirection = obtainStyledAttributes.getInt(index, this.mBarrierDirection);
                                                continue;
                                            case 73:
                                                this.mBarrierMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.mBarrierMargin);
                                                continue;
                                            case 74:
                                                this.mReferenceIdString = obtainStyledAttributes.getString(index);
                                                continue;
                                            case 75:
                                                this.mBarrierAllowsGoneWidgets = obtainStyledAttributes.getBoolean(index, this.mBarrierAllowsGoneWidgets);
                                                continue;
                                            case 76:
                                                Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                                                continue;
                                            case 77:
                                                this.mConstraintTag = obtainStyledAttributes.getString(index);
                                                continue;
                                            default:
                                                switch (i2) {
                                                    case 80:
                                                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                                                        continue;
                                                    case 81:
                                                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                                                        continue;
                                                    default:
                                                        Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                                                        continue;
                                                        continue;
                                                        continue;
                                                        continue;
                                                }
                                        }
                                }
                        }
                }
            }
            obtainStyledAttributes.recycle();
        }

        public void dump(MotionScene motionScene, StringBuilder sb) {
            Field[] declaredFields = getClass().getDeclaredFields();
            sb.append("\n");
            for (Field field : declaredFields) {
                String name = field.getName();
                if (!Modifier.isStatic(field.getModifiers())) {
                    try {
                        Object obj = field.get(this);
                        Class<?> type = field.getType();
                        if (type == Integer.TYPE) {
                            Integer num = (Integer) obj;
                            if (num.intValue() != -1) {
                                Object lookUpConstraintName = motionScene.lookUpConstraintName(num.intValue());
                                sb.append("    ");
                                sb.append(name);
                                sb.append(" = \"");
                                if (lookUpConstraintName == null) {
                                    lookUpConstraintName = num;
                                }
                                sb.append(lookUpConstraintName);
                                sb.append("\"\n");
                            }
                        } else if (type == Float.TYPE) {
                            Float f = (Float) obj;
                            if (f.floatValue() != -1.0f) {
                                sb.append("    ");
                                sb.append(name);
                                sb.append(" = \"");
                                sb.append(f);
                                sb.append("\"\n");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Transform {
        private static SparseIntArray a = new SparseIntArray();
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;
        public boolean applyElevation = false;
        public float elevation = 0.0f;

        public void copyFrom(Transform transform) {
            this.mApply = transform.mApply;
            this.rotation = transform.rotation;
            this.rotationX = transform.rotationX;
            this.rotationY = transform.rotationY;
            this.scaleX = transform.scaleX;
            this.scaleY = transform.scaleY;
            this.transformPivotX = transform.transformPivotX;
            this.transformPivotY = transform.transformPivotY;
            this.translationX = transform.translationX;
            this.translationY = transform.translationY;
            this.translationZ = transform.translationZ;
            this.applyElevation = transform.applyElevation;
            this.elevation = transform.elevation;
        }

        static {
            a.append(R.styleable.Transform_android_rotation, 1);
            a.append(R.styleable.Transform_android_rotationX, 2);
            a.append(R.styleable.Transform_android_rotationY, 3);
            a.append(R.styleable.Transform_android_scaleX, 4);
            a.append(R.styleable.Transform_android_scaleY, 5);
            a.append(R.styleable.Transform_android_transformPivotX, 6);
            a.append(R.styleable.Transform_android_transformPivotY, 7);
            a.append(R.styleable.Transform_android_translationX, 8);
            a.append(R.styleable.Transform_android_translationY, 9);
            a.append(R.styleable.Transform_android_translationZ, 10);
            a.append(R.styleable.Transform_android_elevation, 11);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Transform);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        this.rotation = obtainStyledAttributes.getFloat(index, this.rotation);
                        break;
                    case 2:
                        this.rotationX = obtainStyledAttributes.getFloat(index, this.rotationX);
                        break;
                    case 3:
                        this.rotationY = obtainStyledAttributes.getFloat(index, this.rotationY);
                        break;
                    case 4:
                        this.scaleX = obtainStyledAttributes.getFloat(index, this.scaleX);
                        break;
                    case 5:
                        this.scaleY = obtainStyledAttributes.getFloat(index, this.scaleY);
                        break;
                    case 6:
                        this.transformPivotX = obtainStyledAttributes.getDimension(index, this.transformPivotX);
                        break;
                    case 7:
                        this.transformPivotY = obtainStyledAttributes.getDimension(index, this.transformPivotY);
                        break;
                    case 8:
                        this.translationX = obtainStyledAttributes.getDimension(index, this.translationX);
                        break;
                    case 9:
                        this.translationY = obtainStyledAttributes.getDimension(index, this.translationY);
                        break;
                    case 10:
                        if (Build.VERSION.SDK_INT >= 21) {
                            this.translationZ = obtainStyledAttributes.getDimension(index, this.translationZ);
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (Build.VERSION.SDK_INT >= 21) {
                            this.applyElevation = true;
                            this.elevation = obtainStyledAttributes.getDimension(index, this.elevation);
                            break;
                        } else {
                            break;
                        }
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* loaded from: classes.dex */
    public static class PropertySet {
        public boolean mApply = false;
        public int visibility = 0;
        public int mVisibilityMode = 0;
        public float alpha = 1.0f;
        public float mProgress = Float.NaN;

        public void copyFrom(PropertySet propertySet) {
            this.mApply = propertySet.mApply;
            this.visibility = propertySet.visibility;
            this.alpha = propertySet.alpha;
            this.mProgress = propertySet.mProgress;
            this.mVisibilityMode = propertySet.mVisibilityMode;
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PropertySet);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.PropertySet_android_alpha) {
                    this.alpha = obtainStyledAttributes.getFloat(index, this.alpha);
                } else if (index == R.styleable.PropertySet_android_visibility) {
                    this.visibility = obtainStyledAttributes.getInt(index, this.visibility);
                    this.visibility = ConstraintSet.d[this.visibility];
                } else if (index == R.styleable.PropertySet_visibilityMode) {
                    this.mVisibilityMode = obtainStyledAttributes.getInt(index, this.mVisibilityMode);
                } else if (index == R.styleable.PropertySet_motionProgress) {
                    this.mProgress = obtainStyledAttributes.getFloat(index, this.mProgress);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* loaded from: classes.dex */
    public static class Motion {
        private static SparseIntArray a = new SparseIntArray();
        public boolean mApply = false;
        public int mAnimateRelativeTo = -1;
        public String mTransitionEasing = null;
        public int mPathMotionArc = -1;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public float mPathRotate = Float.NaN;

        public void copyFrom(Motion motion) {
            this.mApply = motion.mApply;
            this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
            this.mTransitionEasing = motion.mTransitionEasing;
            this.mPathMotionArc = motion.mPathMotionArc;
            this.mDrawPath = motion.mDrawPath;
            this.mPathRotate = motion.mPathRotate;
            this.mMotionStagger = motion.mMotionStagger;
        }

        static {
            a.append(R.styleable.Motion_motionPathRotate, 1);
            a.append(R.styleable.Motion_pathMotionArc, 2);
            a.append(R.styleable.Motion_transitionEasing, 3);
            a.append(R.styleable.Motion_drawPath, 4);
            a.append(R.styleable.Motion_animate_relativeTo, 5);
            a.append(R.styleable.Motion_motionStagger, 6);
        }

        void a(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Motion);
            this.mApply = true;
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        this.mPathRotate = obtainStyledAttributes.getFloat(index, this.mPathRotate);
                        break;
                    case 2:
                        this.mPathMotionArc = obtainStyledAttributes.getInt(index, this.mPathMotionArc);
                        break;
                    case 3:
                        if (obtainStyledAttributes.peekValue(index).type == 3) {
                            this.mTransitionEasing = obtainStyledAttributes.getString(index);
                            break;
                        } else {
                            this.mTransitionEasing = Easing.NAMED_EASING[obtainStyledAttributes.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        this.mDrawPath = obtainStyledAttributes.getInt(index, 0);
                        break;
                    case 5:
                        this.mAnimateRelativeTo = ConstraintSet.b(obtainStyledAttributes, index, this.mAnimateRelativeTo);
                        break;
                    case 6:
                        this.mMotionStagger = obtainStyledAttributes.getFloat(index, this.mMotionStagger);
                        break;
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* loaded from: classes.dex */
    public static class Constraint {
        int a;
        public final PropertySet propertySet = new PropertySet();
        public final Motion motion = new Motion();
        public final Layout layout = new Layout();
        public final Transform transform = new Transform();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<>();

        private ConstraintAttribute a(String str, ConstraintAttribute.AttributeType attributeType) {
            if (this.mCustomConstraints.containsKey(str)) {
                ConstraintAttribute constraintAttribute = this.mCustomConstraints.get(str);
                if (constraintAttribute.getType() == attributeType) {
                    return constraintAttribute;
                }
                throw new IllegalArgumentException("ConstraintAttribute is already a " + constraintAttribute.getType().name());
            }
            ConstraintAttribute constraintAttribute2 = new ConstraintAttribute(str, attributeType);
            this.mCustomConstraints.put(str, constraintAttribute2);
            return constraintAttribute2;
        }

        public void a(String str, String str2) {
            a(str, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(str2);
        }

        public void a(String str, float f) {
            a(str, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(f);
        }

        public void a(String str, int i) {
            a(str, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(i);
        }

        public void b(String str, int i) {
            a(str, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(i);
        }

        public Constraint clone() {
            Constraint constraint = new Constraint();
            constraint.layout.copyFrom(this.layout);
            constraint.motion.copyFrom(this.motion);
            constraint.propertySet.copyFrom(this.propertySet);
            constraint.transform.copyFrom(this.transform);
            constraint.a = this.a;
            return constraint;
        }

        public void a(ConstraintHelper constraintHelper, int i, Constraints.LayoutParams layoutParams) {
            a(i, layoutParams);
            if (constraintHelper instanceof Barrier) {
                Layout layout = this.layout;
                layout.mHelperType = 1;
                Barrier barrier = (Barrier) constraintHelper;
                layout.mBarrierDirection = barrier.getType();
                this.layout.mReferenceIds = barrier.getReferencedIds();
                this.layout.mBarrierMargin = barrier.getMargin();
            }
        }

        public void a(int i, Constraints.LayoutParams layoutParams) {
            a(i, (ConstraintLayout.LayoutParams) layoutParams);
            this.propertySet.alpha = layoutParams.alpha;
            this.transform.rotation = layoutParams.rotation;
            this.transform.rotationX = layoutParams.rotationX;
            this.transform.rotationY = layoutParams.rotationY;
            this.transform.scaleX = layoutParams.scaleX;
            this.transform.scaleY = layoutParams.scaleY;
            this.transform.transformPivotX = layoutParams.transformPivotX;
            this.transform.transformPivotY = layoutParams.transformPivotY;
            this.transform.translationX = layoutParams.translationX;
            this.transform.translationY = layoutParams.translationY;
            this.transform.translationZ = layoutParams.translationZ;
            this.transform.elevation = layoutParams.elevation;
            this.transform.applyElevation = layoutParams.applyElevation;
        }

        public void a(int i, ConstraintLayout.LayoutParams layoutParams) {
            this.a = i;
            this.layout.leftToLeft = layoutParams.leftToLeft;
            this.layout.leftToRight = layoutParams.leftToRight;
            this.layout.rightToLeft = layoutParams.rightToLeft;
            this.layout.rightToRight = layoutParams.rightToRight;
            this.layout.topToTop = layoutParams.topToTop;
            this.layout.topToBottom = layoutParams.topToBottom;
            this.layout.bottomToTop = layoutParams.bottomToTop;
            this.layout.bottomToBottom = layoutParams.bottomToBottom;
            this.layout.baselineToBaseline = layoutParams.baselineToBaseline;
            this.layout.startToEnd = layoutParams.startToEnd;
            this.layout.startToStart = layoutParams.startToStart;
            this.layout.endToStart = layoutParams.endToStart;
            this.layout.endToEnd = layoutParams.endToEnd;
            this.layout.horizontalBias = layoutParams.horizontalBias;
            this.layout.verticalBias = layoutParams.verticalBias;
            this.layout.dimensionRatio = layoutParams.dimensionRatio;
            this.layout.circleConstraint = layoutParams.circleConstraint;
            this.layout.circleRadius = layoutParams.circleRadius;
            this.layout.circleAngle = layoutParams.circleAngle;
            this.layout.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.layout.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.layout.orientation = layoutParams.orientation;
            this.layout.guidePercent = layoutParams.guidePercent;
            this.layout.guideBegin = layoutParams.guideBegin;
            this.layout.guideEnd = layoutParams.guideEnd;
            this.layout.mWidth = layoutParams.width;
            this.layout.mHeight = layoutParams.height;
            this.layout.leftMargin = layoutParams.leftMargin;
            this.layout.rightMargin = layoutParams.rightMargin;
            this.layout.topMargin = layoutParams.topMargin;
            this.layout.bottomMargin = layoutParams.bottomMargin;
            this.layout.verticalWeight = layoutParams.verticalWeight;
            this.layout.horizontalWeight = layoutParams.horizontalWeight;
            this.layout.verticalChainStyle = layoutParams.verticalChainStyle;
            this.layout.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.layout.constrainedWidth = layoutParams.constrainedWidth;
            this.layout.constrainedHeight = layoutParams.constrainedHeight;
            this.layout.widthDefault = layoutParams.matchConstraintDefaultWidth;
            this.layout.heightDefault = layoutParams.matchConstraintDefaultHeight;
            this.layout.widthMax = layoutParams.matchConstraintMaxWidth;
            this.layout.heightMax = layoutParams.matchConstraintMaxHeight;
            this.layout.widthMin = layoutParams.matchConstraintMinWidth;
            this.layout.heightMin = layoutParams.matchConstraintMinHeight;
            this.layout.widthPercent = layoutParams.matchConstraintPercentWidth;
            this.layout.heightPercent = layoutParams.matchConstraintPercentHeight;
            this.layout.mConstraintTag = layoutParams.constraintTag;
            this.layout.goneTopMargin = layoutParams.goneTopMargin;
            this.layout.goneBottomMargin = layoutParams.goneBottomMargin;
            this.layout.goneLeftMargin = layoutParams.goneLeftMargin;
            this.layout.goneRightMargin = layoutParams.goneRightMargin;
            this.layout.goneStartMargin = layoutParams.goneStartMargin;
            this.layout.goneEndMargin = layoutParams.goneEndMargin;
            if (Build.VERSION.SDK_INT >= 17) {
                this.layout.endMargin = layoutParams.getMarginEnd();
                this.layout.startMargin = layoutParams.getMarginStart();
            }
        }

        public void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.leftToLeft = this.layout.leftToLeft;
            layoutParams.leftToRight = this.layout.leftToRight;
            layoutParams.rightToLeft = this.layout.rightToLeft;
            layoutParams.rightToRight = this.layout.rightToRight;
            layoutParams.topToTop = this.layout.topToTop;
            layoutParams.topToBottom = this.layout.topToBottom;
            layoutParams.bottomToTop = this.layout.bottomToTop;
            layoutParams.bottomToBottom = this.layout.bottomToBottom;
            layoutParams.baselineToBaseline = this.layout.baselineToBaseline;
            layoutParams.startToEnd = this.layout.startToEnd;
            layoutParams.startToStart = this.layout.startToStart;
            layoutParams.endToStart = this.layout.endToStart;
            layoutParams.endToEnd = this.layout.endToEnd;
            layoutParams.leftMargin = this.layout.leftMargin;
            layoutParams.rightMargin = this.layout.rightMargin;
            layoutParams.topMargin = this.layout.topMargin;
            layoutParams.bottomMargin = this.layout.bottomMargin;
            layoutParams.goneStartMargin = this.layout.goneStartMargin;
            layoutParams.goneEndMargin = this.layout.goneEndMargin;
            layoutParams.goneTopMargin = this.layout.goneTopMargin;
            layoutParams.goneBottomMargin = this.layout.goneBottomMargin;
            layoutParams.horizontalBias = this.layout.horizontalBias;
            layoutParams.verticalBias = this.layout.verticalBias;
            layoutParams.circleConstraint = this.layout.circleConstraint;
            layoutParams.circleRadius = this.layout.circleRadius;
            layoutParams.circleAngle = this.layout.circleAngle;
            layoutParams.dimensionRatio = this.layout.dimensionRatio;
            layoutParams.editorAbsoluteX = this.layout.editorAbsoluteX;
            layoutParams.editorAbsoluteY = this.layout.editorAbsoluteY;
            layoutParams.verticalWeight = this.layout.verticalWeight;
            layoutParams.horizontalWeight = this.layout.horizontalWeight;
            layoutParams.verticalChainStyle = this.layout.verticalChainStyle;
            layoutParams.horizontalChainStyle = this.layout.horizontalChainStyle;
            layoutParams.constrainedWidth = this.layout.constrainedWidth;
            layoutParams.constrainedHeight = this.layout.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = this.layout.widthDefault;
            layoutParams.matchConstraintDefaultHeight = this.layout.heightDefault;
            layoutParams.matchConstraintMaxWidth = this.layout.widthMax;
            layoutParams.matchConstraintMaxHeight = this.layout.heightMax;
            layoutParams.matchConstraintMinWidth = this.layout.widthMin;
            layoutParams.matchConstraintMinHeight = this.layout.heightMin;
            layoutParams.matchConstraintPercentWidth = this.layout.widthPercent;
            layoutParams.matchConstraintPercentHeight = this.layout.heightPercent;
            layoutParams.orientation = this.layout.orientation;
            layoutParams.guidePercent = this.layout.guidePercent;
            layoutParams.guideBegin = this.layout.guideBegin;
            layoutParams.guideEnd = this.layout.guideEnd;
            layoutParams.width = this.layout.mWidth;
            layoutParams.height = this.layout.mHeight;
            if (this.layout.mConstraintTag != null) {
                layoutParams.constraintTag = this.layout.mConstraintTag;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.layout.startMargin);
                layoutParams.setMarginEnd(this.layout.endMargin);
            }
            layoutParams.validate();
        }
    }

    public void clone(Context context, int i) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(i, (ViewGroup) null));
    }

    public void clone(ConstraintSet constraintSet) {
        this.e.clear();
        for (Integer num : constraintSet.e.keySet()) {
            this.e.put(num, constraintSet.e.get(num).clone());
        }
    }

    public void clone(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        this.e.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (!this.c || id != -1) {
                if (!this.e.containsKey(Integer.valueOf(id))) {
                    this.e.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.e.get(Integer.valueOf(id));
                constraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.b, childAt);
                constraint.a(id, layoutParams);
                constraint.propertySet.visibility = childAt.getVisibility();
                if (Build.VERSION.SDK_INT >= 17) {
                    constraint.propertySet.alpha = childAt.getAlpha();
                    constraint.transform.rotation = childAt.getRotation();
                    constraint.transform.rotationX = childAt.getRotationX();
                    constraint.transform.rotationY = childAt.getRotationY();
                    constraint.transform.scaleX = childAt.getScaleX();
                    constraint.transform.scaleY = childAt.getScaleY();
                    float pivotX = childAt.getPivotX();
                    float pivotY = childAt.getPivotY();
                    if (!(pivotX == 0.0d && pivotY == 0.0d)) {
                        constraint.transform.transformPivotX = pivotX;
                        constraint.transform.transformPivotY = pivotY;
                    }
                    constraint.transform.translationX = childAt.getTranslationX();
                    constraint.transform.translationY = childAt.getTranslationY();
                    if (Build.VERSION.SDK_INT >= 21) {
                        constraint.transform.translationZ = childAt.getTranslationZ();
                        if (constraint.transform.applyElevation) {
                            constraint.transform.elevation = childAt.getElevation();
                        }
                    }
                }
                if (childAt instanceof Barrier) {
                    Barrier barrier = (Barrier) childAt;
                    constraint.layout.mBarrierAllowsGoneWidgets = barrier.allowsGoneWidget();
                    constraint.layout.mReferenceIds = barrier.getReferencedIds();
                    constraint.layout.mBarrierDirection = barrier.getType();
                    constraint.layout.mBarrierMargin = barrier.getMargin();
                }
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public void clone(Constraints constraints) {
        int childCount = constraints.getChildCount();
        this.e.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraints.getChildAt(i);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (!this.c || id != -1) {
                if (!this.e.containsKey(Integer.valueOf(id))) {
                    this.e.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.e.get(Integer.valueOf(id));
                if (childAt instanceof ConstraintHelper) {
                    constraint.a((ConstraintHelper) childAt, id, layoutParams);
                }
                constraint.a(id, layoutParams);
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        a(constraintLayout, true);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    public void applyToWithoutCustom(ConstraintLayout constraintLayout) {
        a(constraintLayout, false);
        constraintLayout.setConstraintSet(null);
    }

    public void applyCustomAttributes(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.e.containsKey(Integer.valueOf(id))) {
                Log.v("ConstraintSet", "id unknown " + Debug.getName(childAt));
            } else if (this.c && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (this.e.containsKey(Integer.valueOf(id))) {
                ConstraintAttribute.setAttributes(childAt, this.e.get(Integer.valueOf(id)).mCustomConstraints);
            }
        }
    }

    public void applyToHelper(ConstraintHelper constraintHelper, ConstraintWidget constraintWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        int id = constraintHelper.getId();
        if (this.e.containsKey(Integer.valueOf(id))) {
            Constraint constraint = this.e.get(Integer.valueOf(id));
            if (constraintWidget instanceof HelperWidget) {
                constraintHelper.loadParameters(constraint, (HelperWidget) constraintWidget, layoutParams, sparseArray);
            }
        }
    }

    public void applyToLayoutParams(int i, ConstraintLayout.LayoutParams layoutParams) {
        if (this.e.containsKey(Integer.valueOf(i))) {
            this.e.get(Integer.valueOf(i)).applyTo(layoutParams);
        }
    }

    public void a(ConstraintLayout constraintLayout, boolean z) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.e.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!this.e.containsKey(Integer.valueOf(id))) {
                Log.w("ConstraintSet", "id unknown " + Debug.getName(childAt));
            } else if (this.c && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (id != -1) {
                if (this.e.containsKey(Integer.valueOf(id))) {
                    hashSet.remove(Integer.valueOf(id));
                    Constraint constraint = this.e.get(Integer.valueOf(id));
                    if (childAt instanceof Barrier) {
                        constraint.layout.mHelperType = 1;
                    }
                    if (constraint.layout.mHelperType != -1 && constraint.layout.mHelperType == 1) {
                        Barrier barrier = (Barrier) childAt;
                        barrier.setId(id);
                        barrier.setType(constraint.layout.mBarrierDirection);
                        barrier.setMargin(constraint.layout.mBarrierMargin);
                        barrier.setAllowsGoneWidget(constraint.layout.mBarrierAllowsGoneWidgets);
                        if (constraint.layout.mReferenceIds != null) {
                            barrier.setReferencedIds(constraint.layout.mReferenceIds);
                        } else if (constraint.layout.mReferenceIdString != null) {
                            constraint.layout.mReferenceIds = a(barrier, constraint.layout.mReferenceIdString);
                            barrier.setReferencedIds(constraint.layout.mReferenceIds);
                        }
                    }
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                    layoutParams.validate();
                    constraint.applyTo(layoutParams);
                    if (z) {
                        ConstraintAttribute.setAttributes(childAt, constraint.mCustomConstraints);
                    }
                    childAt.setLayoutParams(layoutParams);
                    if (constraint.propertySet.mVisibilityMode == 0) {
                        childAt.setVisibility(constraint.propertySet.visibility);
                    }
                    if (Build.VERSION.SDK_INT >= 17) {
                        childAt.setAlpha(constraint.propertySet.alpha);
                        childAt.setRotation(constraint.transform.rotation);
                        childAt.setRotationX(constraint.transform.rotationX);
                        childAt.setRotationY(constraint.transform.rotationY);
                        childAt.setScaleX(constraint.transform.scaleX);
                        childAt.setScaleY(constraint.transform.scaleY);
                        if (!Float.isNaN(constraint.transform.transformPivotX)) {
                            childAt.setPivotX(constraint.transform.transformPivotX);
                        }
                        if (!Float.isNaN(constraint.transform.transformPivotY)) {
                            childAt.setPivotY(constraint.transform.transformPivotY);
                        }
                        childAt.setTranslationX(constraint.transform.translationX);
                        childAt.setTranslationY(constraint.transform.translationY);
                        if (Build.VERSION.SDK_INT >= 21) {
                            childAt.setTranslationZ(constraint.transform.translationZ);
                            if (constraint.transform.applyElevation) {
                                childAt.setElevation(constraint.transform.elevation);
                            }
                        }
                    }
                } else {
                    Log.v("ConstraintSet", "WARNING NO CONSTRAINTS for view " + id);
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.e.get(num);
            if (constraint2.layout.mHelperType != -1 && constraint2.layout.mHelperType == 1) {
                Barrier barrier2 = new Barrier(constraintLayout.getContext());
                barrier2.setId(num.intValue());
                if (constraint2.layout.mReferenceIds != null) {
                    barrier2.setReferencedIds(constraint2.layout.mReferenceIds);
                } else if (constraint2.layout.mReferenceIdString != null) {
                    constraint2.layout.mReferenceIds = a(barrier2, constraint2.layout.mReferenceIdString);
                    barrier2.setReferencedIds(constraint2.layout.mReferenceIds);
                }
                barrier2.setType(constraint2.layout.mBarrierDirection);
                barrier2.setMargin(constraint2.layout.mBarrierMargin);
                ConstraintLayout.LayoutParams generateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                barrier2.validateParams();
                constraint2.applyTo(generateDefaultLayoutParams);
                constraintLayout.addView(barrier2, generateDefaultLayoutParams);
            }
            if (constraint2.layout.mIsGuideline) {
                Guideline guideline = new Guideline(constraintLayout.getContext());
                guideline.setId(num.intValue());
                ConstraintLayout.LayoutParams generateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                constraint2.applyTo(generateDefaultLayoutParams2);
                constraintLayout.addView(guideline, generateDefaultLayoutParams2);
            }
        }
    }

    public void center(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        if (i4 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (i7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (f2 <= 0.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        } else if (i3 == 1 || i3 == 2) {
            connect(i, 1, i2, i3, i4);
            connect(i, 2, i5, i6, i7);
            this.e.get(Integer.valueOf(i)).layout.horizontalBias = f2;
        } else if (i3 == 6 || i3 == 7) {
            connect(i, 6, i2, i3, i4);
            connect(i, 7, i5, i6, i7);
            this.e.get(Integer.valueOf(i)).layout.horizontalBias = f2;
        } else {
            connect(i, 3, i2, i3, i4);
            connect(i, 4, i5, i6, i7);
            this.e.get(Integer.valueOf(i)).layout.verticalBias = f2;
        }
    }

    public void centerHorizontally(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(i, 1, i2, i3, i4);
        connect(i, 2, i5, i6, i7);
        this.e.get(Integer.valueOf(i)).layout.horizontalBias = f2;
    }

    public void centerHorizontallyRtl(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(i, 6, i2, i3, i4);
        connect(i, 7, i5, i6, i7);
        this.e.get(Integer.valueOf(i)).layout.horizontalBias = f2;
    }

    public void centerVertically(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f2) {
        connect(i, 3, i2, i3, i4);
        connect(i, 4, i5, i6, i7);
        this.e.get(Integer.valueOf(i)).layout.verticalBias = f2;
    }

    public void createVerticalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr == null || fArr.length == iArr.length) {
            if (fArr != null) {
                a(iArr[0]).layout.verticalWeight = fArr[0];
            }
            a(iArr[0]).layout.verticalChainStyle = i5;
            connect(iArr[0], 3, i, i2, 0);
            for (int i6 = 1; i6 < iArr.length; i6++) {
                int i7 = iArr[i6];
                int i8 = i6 - 1;
                connect(iArr[i6], 3, iArr[i8], 4, 0);
                connect(iArr[i8], 4, iArr[i6], 3, 0);
                if (fArr != null) {
                    a(iArr[i6]).layout.verticalWeight = fArr[i6];
                }
            }
            connect(iArr[iArr.length - 1], 4, i3, i4, 0);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void createHorizontalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        a(i, i2, i3, i4, iArr, fArr, i5, 1, 2);
    }

    public void createHorizontalChainRtl(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        a(i, i2, i3, i4, iArr, fArr, i5, 6, 7);
    }

    private void a(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5, int i6, int i7) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr == null || fArr.length == iArr.length) {
            if (fArr != null) {
                a(iArr[0]).layout.horizontalWeight = fArr[0];
            }
            a(iArr[0]).layout.horizontalChainStyle = i5;
            connect(iArr[0], i6, i, i2, -1);
            for (int i8 = 1; i8 < iArr.length; i8++) {
                int i9 = iArr[i8];
                int i10 = i8 - 1;
                connect(iArr[i8], i6, iArr[i10], i7, -1);
                connect(iArr[i10], i7, iArr[i8], i6, -1);
                if (fArr != null) {
                    a(iArr[i8]).layout.horizontalWeight = fArr[i8];
                }
            }
            connect(iArr[iArr.length - 1], i7, i3, i4, -1);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void connect(int i, int i2, int i3, int i4, int i5) {
        if (!this.e.containsKey(Integer.valueOf(i))) {
            this.e.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = this.e.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.layout.leftToLeft = i3;
                    constraint.layout.leftToRight = -1;
                } else if (i4 == 2) {
                    constraint.layout.leftToRight = i3;
                    constraint.layout.leftToLeft = -1;
                } else {
                    throw new IllegalArgumentException("Left to " + b(i4) + " undefined");
                }
                constraint.layout.leftMargin = i5;
                return;
            case 2:
                if (i4 == 1) {
                    constraint.layout.rightToLeft = i3;
                    constraint.layout.rightToRight = -1;
                } else if (i4 == 2) {
                    constraint.layout.rightToRight = i3;
                    constraint.layout.rightToLeft = -1;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
                constraint.layout.rightMargin = i5;
                return;
            case 3:
                if (i4 == 3) {
                    constraint.layout.topToTop = i3;
                    constraint.layout.topToBottom = -1;
                    constraint.layout.baselineToBaseline = -1;
                } else if (i4 == 4) {
                    constraint.layout.topToBottom = i3;
                    constraint.layout.topToTop = -1;
                    constraint.layout.baselineToBaseline = -1;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
                constraint.layout.topMargin = i5;
                return;
            case 4:
                if (i4 == 4) {
                    constraint.layout.bottomToBottom = i3;
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.baselineToBaseline = -1;
                } else if (i4 == 3) {
                    constraint.layout.bottomToTop = i3;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.baselineToBaseline = -1;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
                constraint.layout.bottomMargin = i5;
                return;
            case 5:
                if (i4 == 5) {
                    constraint.layout.baselineToBaseline = i3;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.topToTop = -1;
                    constraint.layout.topToBottom = -1;
                    return;
                }
                throw new IllegalArgumentException("right to " + b(i4) + " undefined");
            case 6:
                if (i4 == 6) {
                    constraint.layout.startToStart = i3;
                    constraint.layout.startToEnd = -1;
                } else if (i4 == 7) {
                    constraint.layout.startToEnd = i3;
                    constraint.layout.startToStart = -1;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
                constraint.layout.startMargin = i5;
                return;
            case 7:
                if (i4 == 7) {
                    constraint.layout.endToEnd = i3;
                    constraint.layout.endToStart = -1;
                } else if (i4 == 6) {
                    constraint.layout.endToStart = i3;
                    constraint.layout.endToEnd = -1;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
                constraint.layout.endMargin = i5;
                return;
            default:
                throw new IllegalArgumentException(b(i2) + " to " + b(i4) + " unknown");
        }
    }

    public void connect(int i, int i2, int i3, int i4) {
        if (!this.e.containsKey(Integer.valueOf(i))) {
            this.e.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = this.e.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.layout.leftToLeft = i3;
                    constraint.layout.leftToRight = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.layout.leftToRight = i3;
                    constraint.layout.leftToLeft = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("left to " + b(i4) + " undefined");
                }
            case 2:
                if (i4 == 1) {
                    constraint.layout.rightToLeft = i3;
                    constraint.layout.rightToRight = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.layout.rightToRight = i3;
                    constraint.layout.rightToLeft = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
            case 3:
                if (i4 == 3) {
                    constraint.layout.topToTop = i3;
                    constraint.layout.topToBottom = -1;
                    constraint.layout.baselineToBaseline = -1;
                    return;
                } else if (i4 == 4) {
                    constraint.layout.topToBottom = i3;
                    constraint.layout.topToTop = -1;
                    constraint.layout.baselineToBaseline = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
            case 4:
                if (i4 == 4) {
                    constraint.layout.bottomToBottom = i3;
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.baselineToBaseline = -1;
                    return;
                } else if (i4 == 3) {
                    constraint.layout.bottomToTop = i3;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.baselineToBaseline = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
            case 5:
                if (i4 == 5) {
                    constraint.layout.baselineToBaseline = i3;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.topToTop = -1;
                    constraint.layout.topToBottom = -1;
                    return;
                }
                throw new IllegalArgumentException("right to " + b(i4) + " undefined");
            case 6:
                if (i4 == 6) {
                    constraint.layout.startToStart = i3;
                    constraint.layout.startToEnd = -1;
                    return;
                } else if (i4 == 7) {
                    constraint.layout.startToEnd = i3;
                    constraint.layout.startToStart = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
            case 7:
                if (i4 == 7) {
                    constraint.layout.endToEnd = i3;
                    constraint.layout.endToStart = -1;
                    return;
                } else if (i4 == 6) {
                    constraint.layout.endToStart = i3;
                    constraint.layout.endToEnd = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + b(i4) + " undefined");
                }
            default:
                throw new IllegalArgumentException(b(i2) + " to " + b(i4) + " unknown");
        }
    }

    public void centerHorizontally(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(i, i2, 2, 0, i2, 1, 0, 0.5f);
        }
    }

    public void centerHorizontallyRtl(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(i, i2, 7, 0, i2, 6, 0, 0.5f);
        }
    }

    public void centerVertically(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(i, i2, 4, 0, i2, 3, 0, 0.5f);
        }
    }

    public void clear(int i) {
        this.e.remove(Integer.valueOf(i));
    }

    public void clear(int i, int i2) {
        if (this.e.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.e.get(Integer.valueOf(i));
            switch (i2) {
                case 1:
                    constraint.layout.leftToRight = -1;
                    constraint.layout.leftToLeft = -1;
                    constraint.layout.leftMargin = -1;
                    constraint.layout.goneLeftMargin = -1;
                    return;
                case 2:
                    constraint.layout.rightToRight = -1;
                    constraint.layout.rightToLeft = -1;
                    constraint.layout.rightMargin = -1;
                    constraint.layout.goneRightMargin = -1;
                    return;
                case 3:
                    constraint.layout.topToBottom = -1;
                    constraint.layout.topToTop = -1;
                    constraint.layout.topMargin = -1;
                    constraint.layout.goneTopMargin = -1;
                    return;
                case 4:
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.bottomMargin = -1;
                    constraint.layout.goneBottomMargin = -1;
                    return;
                case 5:
                    constraint.layout.baselineToBaseline = -1;
                    return;
                case 6:
                    constraint.layout.startToEnd = -1;
                    constraint.layout.startToStart = -1;
                    constraint.layout.startMargin = -1;
                    constraint.layout.goneStartMargin = -1;
                    return;
                case 7:
                    constraint.layout.endToStart = -1;
                    constraint.layout.endToEnd = -1;
                    constraint.layout.endMargin = -1;
                    constraint.layout.goneEndMargin = -1;
                    return;
                default:
                    throw new IllegalArgumentException("unknown constraint");
            }
        }
    }

    public void setMargin(int i, int i2, int i3) {
        Constraint a = a(i);
        switch (i2) {
            case 1:
                a.layout.leftMargin = i3;
                return;
            case 2:
                a.layout.rightMargin = i3;
                return;
            case 3:
                a.layout.topMargin = i3;
                return;
            case 4:
                a.layout.bottomMargin = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                a.layout.startMargin = i3;
                return;
            case 7:
                a.layout.endMargin = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGoneMargin(int i, int i2, int i3) {
        Constraint a = a(i);
        switch (i2) {
            case 1:
                a.layout.goneLeftMargin = i3;
                return;
            case 2:
                a.layout.goneRightMargin = i3;
                return;
            case 3:
                a.layout.goneTopMargin = i3;
                return;
            case 4:
                a.layout.goneBottomMargin = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                a.layout.goneStartMargin = i3;
                return;
            case 7:
                a.layout.goneEndMargin = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setHorizontalBias(int i, float f2) {
        a(i).layout.horizontalBias = f2;
    }

    public void setVerticalBias(int i, float f2) {
        a(i).layout.verticalBias = f2;
    }

    public void setDimensionRatio(int i, String str) {
        a(i).layout.dimensionRatio = str;
    }

    public void setVisibility(int i, int i2) {
        a(i).propertySet.visibility = i2;
    }

    public void setVisibilityMode(int i, int i2) {
        a(i).propertySet.mVisibilityMode = i2;
    }

    public int getVisibilityMode(int i) {
        return a(i).propertySet.mVisibilityMode;
    }

    public int getVisibility(int i) {
        return a(i).propertySet.visibility;
    }

    public int getHeight(int i) {
        return a(i).layout.mHeight;
    }

    public int getWidth(int i) {
        return a(i).layout.mWidth;
    }

    public void setAlpha(int i, float f2) {
        a(i).propertySet.alpha = f2;
    }

    public boolean getApplyElevation(int i) {
        return a(i).transform.applyElevation;
    }

    public void setApplyElevation(int i, boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            a(i).transform.applyElevation = z;
        }
    }

    public void setElevation(int i, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            a(i).transform.elevation = f2;
            a(i).transform.applyElevation = true;
        }
    }

    public void setRotation(int i, float f2) {
        a(i).transform.rotation = f2;
    }

    public void setRotationX(int i, float f2) {
        a(i).transform.rotationX = f2;
    }

    public void setRotationY(int i, float f2) {
        a(i).transform.rotationY = f2;
    }

    public void setScaleX(int i, float f2) {
        a(i).transform.scaleX = f2;
    }

    public void setScaleY(int i, float f2) {
        a(i).transform.scaleY = f2;
    }

    public void setTransformPivotX(int i, float f2) {
        a(i).transform.transformPivotX = f2;
    }

    public void setTransformPivotY(int i, float f2) {
        a(i).transform.transformPivotY = f2;
    }

    public void setTransformPivot(int i, float f2, float f3) {
        Constraint a = a(i);
        a.transform.transformPivotY = f3;
        a.transform.transformPivotX = f2;
    }

    public void setTranslationX(int i, float f2) {
        a(i).transform.translationX = f2;
    }

    public void setTranslationY(int i, float f2) {
        a(i).transform.translationY = f2;
    }

    public void setTranslation(int i, float f2, float f3) {
        Constraint a = a(i);
        a.transform.translationX = f2;
        a.transform.translationY = f3;
    }

    public void setTranslationZ(int i, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            a(i).transform.translationZ = f2;
        }
    }

    public void setEditorAbsoluteX(int i, int i2) {
        a(i).layout.editorAbsoluteX = i2;
    }

    public void setEditorAbsoluteY(int i, int i2) {
        a(i).layout.editorAbsoluteY = i2;
    }

    public void constrainHeight(int i, int i2) {
        a(i).layout.mHeight = i2;
    }

    public void constrainWidth(int i, int i2) {
        a(i).layout.mWidth = i2;
    }

    public void constrainCircle(int i, int i2, int i3, float f2) {
        Constraint a = a(i);
        a.layout.circleConstraint = i2;
        a.layout.circleRadius = i3;
        a.layout.circleAngle = f2;
    }

    public void constrainMaxHeight(int i, int i2) {
        a(i).layout.heightMax = i2;
    }

    public void constrainMaxWidth(int i, int i2) {
        a(i).layout.widthMax = i2;
    }

    public void constrainMinHeight(int i, int i2) {
        a(i).layout.heightMin = i2;
    }

    public void constrainMinWidth(int i, int i2) {
        a(i).layout.widthMin = i2;
    }

    public void constrainPercentWidth(int i, float f2) {
        a(i).layout.widthPercent = f2;
    }

    public void constrainPercentHeight(int i, float f2) {
        a(i).layout.heightPercent = f2;
    }

    public void constrainDefaultHeight(int i, int i2) {
        a(i).layout.heightDefault = i2;
    }

    public void constrainedWidth(int i, boolean z) {
        a(i).layout.constrainedWidth = z;
    }

    public void constrainedHeight(int i, boolean z) {
        a(i).layout.constrainedHeight = z;
    }

    public void constrainDefaultWidth(int i, int i2) {
        a(i).layout.widthDefault = i2;
    }

    public void setHorizontalWeight(int i, float f2) {
        a(i).layout.horizontalWeight = f2;
    }

    public void setVerticalWeight(int i, float f2) {
        a(i).layout.verticalWeight = f2;
    }

    public void setHorizontalChainStyle(int i, int i2) {
        a(i).layout.horizontalChainStyle = i2;
    }

    public void setVerticalChainStyle(int i, int i2) {
        a(i).layout.verticalChainStyle = i2;
    }

    public void addToHorizontalChain(int i, int i2, int i3) {
        connect(i, 1, i2, i2 == 0 ? 1 : 2, 0);
        connect(i, 2, i3, i3 == 0 ? 2 : 1, 0);
        if (i2 != 0) {
            connect(i2, 2, i, 1, 0);
        }
        if (i3 != 0) {
            connect(i3, 1, i, 2, 0);
        }
    }

    public void addToHorizontalChainRTL(int i, int i2, int i3) {
        connect(i, 6, i2, i2 == 0 ? 6 : 7, 0);
        connect(i, 7, i3, i3 == 0 ? 7 : 6, 0);
        if (i2 != 0) {
            connect(i2, 7, i, 6, 0);
        }
        if (i3 != 0) {
            connect(i3, 6, i, 7, 0);
        }
    }

    public void addToVerticalChain(int i, int i2, int i3) {
        connect(i, 3, i2, i2 == 0 ? 3 : 4, 0);
        connect(i, 4, i3, i3 == 0 ? 4 : 3, 0);
        if (i2 != 0) {
            connect(i2, 4, i, 3, 0);
        }
        if (i3 != 0) {
            connect(i3, 3, i, 4, 0);
        }
    }

    public void removeFromVerticalChain(int i) {
        if (this.e.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.e.get(Integer.valueOf(i));
            int i2 = constraint.layout.topToBottom;
            int i3 = constraint.layout.bottomToTop;
            if (!(i2 == -1 && i3 == -1)) {
                if (i2 != -1 && i3 != -1) {
                    connect(i2, 4, i3, 3, 0);
                    connect(i3, 3, i2, 4, 0);
                } else if (!(i2 == -1 && i3 == -1)) {
                    if (constraint.layout.bottomToBottom != -1) {
                        connect(i2, 4, constraint.layout.bottomToBottom, 4, 0);
                    } else if (constraint.layout.topToTop != -1) {
                        connect(i3, 3, constraint.layout.topToTop, 3, 0);
                    }
                }
            }
        }
        clear(i, 3);
        clear(i, 4);
    }

    public void removeFromHorizontalChain(int i) {
        if (this.e.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.e.get(Integer.valueOf(i));
            int i2 = constraint.layout.leftToRight;
            int i3 = constraint.layout.rightToLeft;
            if (i2 == -1 && i3 == -1) {
                int i4 = constraint.layout.startToEnd;
                int i5 = constraint.layout.endToStart;
                if (!(i4 == -1 && i5 == -1)) {
                    if (i4 != -1 && i5 != -1) {
                        connect(i4, 7, i5, 6, 0);
                        connect(i5, 6, i2, 7, 0);
                    } else if (!(i2 == -1 && i5 == -1)) {
                        if (constraint.layout.rightToRight != -1) {
                            connect(i2, 7, constraint.layout.rightToRight, 7, 0);
                        } else if (constraint.layout.leftToLeft != -1) {
                            connect(i5, 6, constraint.layout.leftToLeft, 6, 0);
                        }
                    }
                }
                clear(i, 6);
                clear(i, 7);
                return;
            }
            if (i2 != -1 && i3 != -1) {
                connect(i2, 2, i3, 1, 0);
                connect(i3, 1, i2, 2, 0);
            } else if (!(i2 == -1 && i3 == -1)) {
                if (constraint.layout.rightToRight != -1) {
                    connect(i2, 2, constraint.layout.rightToRight, 2, 0);
                } else if (constraint.layout.leftToLeft != -1) {
                    connect(i3, 1, constraint.layout.leftToLeft, 1, 0);
                }
            }
            clear(i, 1);
            clear(i, 2);
        }
    }

    public void create(int i, int i2) {
        Constraint a = a(i);
        a.layout.mIsGuideline = true;
        a.layout.orientation = i2;
    }

    public void createBarrier(int i, int i2, int i3, int... iArr) {
        Constraint a = a(i);
        a.layout.mHelperType = 1;
        a.layout.mBarrierDirection = i2;
        a.layout.mBarrierMargin = i3;
        a.layout.mIsGuideline = false;
        a.layout.mReferenceIds = iArr;
    }

    public void setGuidelineBegin(int i, int i2) {
        a(i).layout.guideBegin = i2;
        a(i).layout.guideEnd = -1;
        a(i).layout.guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int i, int i2) {
        a(i).layout.guideEnd = i2;
        a(i).layout.guideBegin = -1;
        a(i).layout.guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int i, float f2) {
        a(i).layout.guidePercent = f2;
        a(i).layout.guideEnd = -1;
        a(i).layout.guideBegin = -1;
    }

    public int[] getReferencedIds(int i) {
        Constraint a = a(i);
        if (a.layout.mReferenceIds == null) {
            return new int[0];
        }
        return Arrays.copyOf(a.layout.mReferenceIds, a.layout.mReferenceIds.length);
    }

    public void setReferencedIds(int i, int... iArr) {
        a(i).layout.mReferenceIds = iArr;
    }

    public void setBarrierType(int i, int i2) {
        a(i).layout.mHelperType = i2;
    }

    public void removeAttribute(String str) {
        this.b.remove(str);
    }

    public void setIntValue(int i, String str, int i2) {
        a(i).a(str, i2);
    }

    public void setColorValue(int i, String str, int i2) {
        a(i).b(str, i2);
    }

    public void setFloatValue(int i, String str, float f2) {
        a(i).a(str, f2);
    }

    public void setStringValue(int i, String str, String str2) {
        a(i).a(str, str2);
    }

    private void a(ConstraintAttribute.AttributeType attributeType, String... strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (this.b.containsKey(strArr[i])) {
                ConstraintAttribute constraintAttribute = this.b.get(strArr[i]);
                if (constraintAttribute.getType() != attributeType) {
                    throw new IllegalArgumentException("ConstraintAttribute is already a " + constraintAttribute.getType().name());
                }
            } else {
                this.b.put(strArr[i], new ConstraintAttribute(strArr[i], attributeType));
            }
        }
    }

    public void parseIntAttributes(Constraint constraint, String str) {
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("=");
            if (split2.length != 2) {
                Log.w("ConstraintSet", " Unable to parse " + split[i]);
            } else {
                constraint.a(split2[0], Integer.decode(split2[1]).intValue());
            }
        }
    }

    public void parseColorAttributes(Constraint constraint, String str) {
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("=");
            if (split2.length != 2) {
                Log.w("ConstraintSet", " Unable to parse " + split[i]);
            } else {
                constraint.b(split2[0], Color.parseColor(split2[1]));
            }
        }
    }

    public void parseFloatAttributes(Constraint constraint, String str) {
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("=");
            if (split2.length != 2) {
                Log.w("ConstraintSet", " Unable to parse " + split[i]);
            } else {
                constraint.a(split2[0], Float.parseFloat(split2[1]));
            }
        }
    }

    public void parseStringAttributes(Constraint constraint, String str) {
        String[] a = a(str);
        for (int i = 0; i < a.length; i++) {
            String[] split = a[i].split("=");
            Log.w("ConstraintSet", " Unable to parse " + a[i]);
            constraint.a(split[0], split[1]);
        }
    }

    private static String[] a(String str) {
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] == ',' && !z) {
                arrayList.add(new String(charArray, i, i2 - i));
                i = i2 + 1;
            } else if (charArray[i2] == '\"') {
                z = !z;
            }
        }
        arrayList.add(new String(charArray, i, charArray.length - i));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public void addIntAttributes(String... strArr) {
        a(ConstraintAttribute.AttributeType.INT_TYPE, strArr);
    }

    public void addColorAttributes(String... strArr) {
        a(ConstraintAttribute.AttributeType.COLOR_TYPE, strArr);
    }

    public void addFloatAttributes(String... strArr) {
        a(ConstraintAttribute.AttributeType.FLOAT_TYPE, strArr);
    }

    public void addStringAttributes(String... strArr) {
        a(ConstraintAttribute.AttributeType.STRING_TYPE, strArr);
    }

    private Constraint a(int i) {
        if (!this.e.containsKey(Integer.valueOf(i))) {
            this.e.put(Integer.valueOf(i), new Constraint());
        }
        return this.e.get(Integer.valueOf(i));
    }

    public void load(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType != 0) {
                    switch (eventType) {
                        case 2:
                            String name = xml.getName();
                            Constraint a = a(context, Xml.asAttributeSet(xml));
                            if (name.equalsIgnoreCase("Guideline")) {
                                a.layout.mIsGuideline = true;
                            }
                            this.e.put(Integer.valueOf(a.a), a);
                            continue;
                        case 3:
                            continue;
                        default:
                            continue;
                    }
                } else {
                    xml.getName();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public void load(Context context, XmlPullParser xmlPullParser) {
        try {
            int eventType = xmlPullParser.getEventType();
            Constraint constraint = null;
            while (eventType != 1) {
                if (eventType != 0) {
                    switch (eventType) {
                        case 2:
                            String name = xmlPullParser.getName();
                            char c = 65535;
                            switch (name.hashCode()) {
                                case -2025855158:
                                    if (name.equals("Layout")) {
                                        c = 5;
                                        break;
                                    }
                                    break;
                                case -1984451626:
                                    if (name.equals("Motion")) {
                                        c = 6;
                                        break;
                                    }
                                    break;
                                case -1269513683:
                                    if (name.equals("PropertySet")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case -1238332596:
                                    if (name.equals("Transform")) {
                                        c = 4;
                                        break;
                                    }
                                    break;
                                case -71750448:
                                    if (name.equals("Guideline")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case 1331510167:
                                    if (name.equals("Barrier")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 1791837707:
                                    if (name.equals("CustomAttribute")) {
                                        c = 7;
                                        break;
                                    }
                                    break;
                                case 1803088381:
                                    if (name.equals("Constraint")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    constraint = a(context, Xml.asAttributeSet(xmlPullParser));
                                    continue;
                                case 1:
                                    constraint = a(context, Xml.asAttributeSet(xmlPullParser));
                                    constraint.layout.mIsGuideline = true;
                                    constraint.layout.mApply = true;
                                    continue;
                                case 2:
                                    constraint = a(context, Xml.asAttributeSet(xmlPullParser));
                                    constraint.layout.mHelperType = 1;
                                    continue;
                                case 3:
                                    if (constraint != null) {
                                        constraint.propertySet.a(context, Xml.asAttributeSet(xmlPullParser));
                                        continue;
                                    } else {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                    }
                                case 4:
                                    if (constraint != null) {
                                        constraint.transform.a(context, Xml.asAttributeSet(xmlPullParser));
                                        continue;
                                    } else {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                    }
                                case 5:
                                    if (constraint != null) {
                                        constraint.layout.a(context, Xml.asAttributeSet(xmlPullParser));
                                        continue;
                                    } else {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                    }
                                case 6:
                                    if (constraint != null) {
                                        constraint.motion.a(context, Xml.asAttributeSet(xmlPullParser));
                                        continue;
                                    } else {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                    }
                                case 7:
                                    if (constraint != null) {
                                        ConstraintAttribute.parse(context, xmlPullParser, constraint.mCustomConstraints);
                                        continue;
                                    } else {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlPullParser.getLineNumber());
                                    }
                                default:
                                    continue;
                                    continue;
                            }
                        case 3:
                            String name2 = xmlPullParser.getName();
                            if (!"ConstraintSet".equals(name2)) {
                                if (name2.equalsIgnoreCase("Constraint")) {
                                    this.e.put(Integer.valueOf(constraint.a), constraint);
                                    constraint = null;
                                    break;
                                } else {
                                    continue;
                                }
                            } else {
                                return;
                            }
                        default:
                            continue;
                    }
                } else {
                    xmlPullParser.getName();
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public static int b(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    private Constraint a(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Constraint);
        a(context, constraint, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        return constraint;
    }

    private void a(Context context, Constraint constraint, TypedArray typedArray) {
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (!(index == R.styleable.Constraint_android_id || R.styleable.Constraint_android_layout_marginStart == index || R.styleable.Constraint_android_layout_marginEnd == index)) {
                constraint.motion.mApply = true;
                constraint.layout.mApply = true;
                constraint.propertySet.mApply = true;
                constraint.transform.mApply = true;
            }
            switch (f.get(index)) {
                case 1:
                    constraint.layout.baselineToBaseline = b(typedArray, index, constraint.layout.baselineToBaseline);
                    break;
                case 2:
                    constraint.layout.bottomMargin = typedArray.getDimensionPixelSize(index, constraint.layout.bottomMargin);
                    break;
                case 3:
                    constraint.layout.bottomToBottom = b(typedArray, index, constraint.layout.bottomToBottom);
                    break;
                case 4:
                    constraint.layout.bottomToTop = b(typedArray, index, constraint.layout.bottomToTop);
                    break;
                case 5:
                    constraint.layout.dimensionRatio = typedArray.getString(index);
                    break;
                case 6:
                    constraint.layout.editorAbsoluteX = typedArray.getDimensionPixelOffset(index, constraint.layout.editorAbsoluteX);
                    break;
                case 7:
                    constraint.layout.editorAbsoluteY = typedArray.getDimensionPixelOffset(index, constraint.layout.editorAbsoluteY);
                    break;
                case 8:
                    if (Build.VERSION.SDK_INT >= 17) {
                        constraint.layout.endMargin = typedArray.getDimensionPixelSize(index, constraint.layout.endMargin);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    constraint.layout.endToEnd = b(typedArray, index, constraint.layout.endToEnd);
                    break;
                case 10:
                    constraint.layout.endToStart = b(typedArray, index, constraint.layout.endToStart);
                    break;
                case 11:
                    constraint.layout.goneBottomMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneBottomMargin);
                    break;
                case 12:
                    constraint.layout.goneEndMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneEndMargin);
                    break;
                case 13:
                    constraint.layout.goneLeftMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneLeftMargin);
                    break;
                case 14:
                    constraint.layout.goneRightMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneRightMargin);
                    break;
                case 15:
                    constraint.layout.goneStartMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneStartMargin);
                    break;
                case 16:
                    constraint.layout.goneTopMargin = typedArray.getDimensionPixelSize(index, constraint.layout.goneTopMargin);
                    break;
                case 17:
                    constraint.layout.guideBegin = typedArray.getDimensionPixelOffset(index, constraint.layout.guideBegin);
                    break;
                case 18:
                    constraint.layout.guideEnd = typedArray.getDimensionPixelOffset(index, constraint.layout.guideEnd);
                    break;
                case 19:
                    constraint.layout.guidePercent = typedArray.getFloat(index, constraint.layout.guidePercent);
                    break;
                case 20:
                    constraint.layout.horizontalBias = typedArray.getFloat(index, constraint.layout.horizontalBias);
                    break;
                case 21:
                    constraint.layout.mHeight = typedArray.getLayoutDimension(index, constraint.layout.mHeight);
                    break;
                case 22:
                    constraint.propertySet.visibility = typedArray.getInt(index, constraint.propertySet.visibility);
                    constraint.propertySet.visibility = d[constraint.propertySet.visibility];
                    break;
                case 23:
                    constraint.layout.mWidth = typedArray.getLayoutDimension(index, constraint.layout.mWidth);
                    break;
                case 24:
                    constraint.layout.leftMargin = typedArray.getDimensionPixelSize(index, constraint.layout.leftMargin);
                    break;
                case 25:
                    constraint.layout.leftToLeft = b(typedArray, index, constraint.layout.leftToLeft);
                    break;
                case 26:
                    constraint.layout.leftToRight = b(typedArray, index, constraint.layout.leftToRight);
                    break;
                case 27:
                    constraint.layout.orientation = typedArray.getInt(index, constraint.layout.orientation);
                    break;
                case 28:
                    constraint.layout.rightMargin = typedArray.getDimensionPixelSize(index, constraint.layout.rightMargin);
                    break;
                case 29:
                    constraint.layout.rightToLeft = b(typedArray, index, constraint.layout.rightToLeft);
                    break;
                case 30:
                    constraint.layout.rightToRight = b(typedArray, index, constraint.layout.rightToRight);
                    break;
                case 31:
                    if (Build.VERSION.SDK_INT >= 17) {
                        constraint.layout.startMargin = typedArray.getDimensionPixelSize(index, constraint.layout.startMargin);
                        break;
                    } else {
                        break;
                    }
                case 32:
                    constraint.layout.startToEnd = b(typedArray, index, constraint.layout.startToEnd);
                    break;
                case 33:
                    constraint.layout.startToStart = b(typedArray, index, constraint.layout.startToStart);
                    break;
                case 34:
                    constraint.layout.topMargin = typedArray.getDimensionPixelSize(index, constraint.layout.topMargin);
                    break;
                case 35:
                    constraint.layout.topToBottom = b(typedArray, index, constraint.layout.topToBottom);
                    break;
                case 36:
                    constraint.layout.topToTop = b(typedArray, index, constraint.layout.topToTop);
                    break;
                case 37:
                    constraint.layout.verticalBias = typedArray.getFloat(index, constraint.layout.verticalBias);
                    break;
                case 38:
                    constraint.a = typedArray.getResourceId(index, constraint.a);
                    break;
                case 39:
                    constraint.layout.horizontalWeight = typedArray.getFloat(index, constraint.layout.horizontalWeight);
                    break;
                case 40:
                    constraint.layout.verticalWeight = typedArray.getFloat(index, constraint.layout.verticalWeight);
                    break;
                case 41:
                    constraint.layout.horizontalChainStyle = typedArray.getInt(index, constraint.layout.horizontalChainStyle);
                    break;
                case 42:
                    constraint.layout.verticalChainStyle = typedArray.getInt(index, constraint.layout.verticalChainStyle);
                    break;
                case 43:
                    constraint.propertySet.alpha = typedArray.getFloat(index, constraint.propertySet.alpha);
                    break;
                case 44:
                    if (Build.VERSION.SDK_INT >= 21) {
                        constraint.transform.applyElevation = true;
                        constraint.transform.elevation = typedArray.getDimension(index, constraint.transform.elevation);
                        break;
                    } else {
                        break;
                    }
                case 45:
                    constraint.transform.rotationX = typedArray.getFloat(index, constraint.transform.rotationX);
                    break;
                case 46:
                    constraint.transform.rotationY = typedArray.getFloat(index, constraint.transform.rotationY);
                    break;
                case 47:
                    constraint.transform.scaleX = typedArray.getFloat(index, constraint.transform.scaleX);
                    break;
                case 48:
                    constraint.transform.scaleY = typedArray.getFloat(index, constraint.transform.scaleY);
                    break;
                case 49:
                    constraint.transform.transformPivotX = typedArray.getDimension(index, constraint.transform.transformPivotX);
                    break;
                case 50:
                    constraint.transform.transformPivotY = typedArray.getDimension(index, constraint.transform.transformPivotY);
                    break;
                case 51:
                    constraint.transform.translationX = typedArray.getDimension(index, constraint.transform.translationX);
                    break;
                case 52:
                    constraint.transform.translationY = typedArray.getDimension(index, constraint.transform.translationY);
                    break;
                case 53:
                    if (Build.VERSION.SDK_INT >= 21) {
                        constraint.transform.translationZ = typedArray.getDimension(index, constraint.transform.translationZ);
                        break;
                    } else {
                        break;
                    }
                case 54:
                    constraint.layout.widthDefault = typedArray.getInt(index, constraint.layout.widthDefault);
                    break;
                case 55:
                    constraint.layout.heightDefault = typedArray.getInt(index, constraint.layout.heightDefault);
                    break;
                case 56:
                    constraint.layout.widthMax = typedArray.getDimensionPixelSize(index, constraint.layout.widthMax);
                    break;
                case 57:
                    constraint.layout.heightMax = typedArray.getDimensionPixelSize(index, constraint.layout.heightMax);
                    break;
                case 58:
                    constraint.layout.widthMin = typedArray.getDimensionPixelSize(index, constraint.layout.widthMin);
                    break;
                case 59:
                    constraint.layout.heightMin = typedArray.getDimensionPixelSize(index, constraint.layout.heightMin);
                    break;
                case 60:
                    constraint.transform.rotation = typedArray.getFloat(index, constraint.transform.rotation);
                    break;
                case 61:
                    constraint.layout.circleConstraint = b(typedArray, index, constraint.layout.circleConstraint);
                    break;
                case 62:
                    constraint.layout.circleRadius = typedArray.getDimensionPixelSize(index, constraint.layout.circleRadius);
                    break;
                case 63:
                    constraint.layout.circleAngle = typedArray.getFloat(index, constraint.layout.circleAngle);
                    break;
                case 64:
                    constraint.motion.mAnimateRelativeTo = b(typedArray, index, constraint.motion.mAnimateRelativeTo);
                    break;
                case 65:
                    if (typedArray.peekValue(index).type == 3) {
                        constraint.motion.mTransitionEasing = typedArray.getString(index);
                        break;
                    } else {
                        constraint.motion.mTransitionEasing = Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                        break;
                    }
                case 66:
                    constraint.motion.mDrawPath = typedArray.getInt(index, 0);
                    break;
                case 67:
                    constraint.motion.mPathRotate = typedArray.getFloat(index, constraint.motion.mPathRotate);
                    break;
                case 68:
                    constraint.propertySet.mProgress = typedArray.getFloat(index, constraint.propertySet.mProgress);
                    break;
                case 69:
                    constraint.layout.widthPercent = typedArray.getFloat(index, 1.0f);
                    break;
                case 70:
                    constraint.layout.heightPercent = typedArray.getFloat(index, 1.0f);
                    break;
                case 71:
                    Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    constraint.layout.mBarrierDirection = typedArray.getInt(index, constraint.layout.mBarrierDirection);
                    break;
                case 73:
                    constraint.layout.mBarrierMargin = typedArray.getDimensionPixelSize(index, constraint.layout.mBarrierMargin);
                    break;
                case 74:
                    constraint.layout.mReferenceIdString = typedArray.getString(index);
                    break;
                case 75:
                    constraint.layout.mBarrierAllowsGoneWidgets = typedArray.getBoolean(index, constraint.layout.mBarrierAllowsGoneWidgets);
                    break;
                case 76:
                    constraint.motion.mPathMotionArc = typedArray.getInt(index, constraint.motion.mPathMotionArc);
                    break;
                case 77:
                    constraint.layout.mConstraintTag = typedArray.getString(index);
                    break;
                case 78:
                    constraint.propertySet.mVisibilityMode = typedArray.getInt(index, constraint.propertySet.mVisibilityMode);
                    break;
                case 79:
                    constraint.motion.mMotionStagger = typedArray.getFloat(index, constraint.motion.mMotionStagger);
                    break;
                case 80:
                    constraint.layout.constrainedWidth = typedArray.getBoolean(index, constraint.layout.constrainedWidth);
                    break;
                case 81:
                    constraint.layout.constrainedHeight = typedArray.getBoolean(index, constraint.layout.constrainedHeight);
                    break;
                case 82:
                    Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + f.get(index));
                    break;
                default:
                    Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + f.get(index));
                    break;
            }
        }
    }

    private int[] a(View view, String str) {
        int i;
        Object designInformation;
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        Context context = view.getContext();
        int[] iArr = new int[split.length];
        int i2 = 0;
        for (String str2 : split) {
            String trim = str2.trim();
            try {
                i = R.id.class.getField(trim).getInt(null);
            } catch (Exception unused) {
                i = 0;
            }
            if (i == 0) {
                i = context.getResources().getIdentifier(trim, "id", context.getPackageName());
            }
            if (i == 0 && view.isInEditMode() && (view.getParent() instanceof ConstraintLayout) && (designInformation = ((ConstraintLayout) view.getParent()).getDesignInformation(0, trim)) != null && (designInformation instanceof Integer)) {
                i = ((Integer) designInformation).intValue();
            }
            i2++;
            iArr[i2] = i;
        }
        return i2 != split.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    public Constraint getConstraint(int i) {
        if (this.e.containsKey(Integer.valueOf(i))) {
            return this.e.get(Integer.valueOf(i));
        }
        return null;
    }

    public int[] getKnownIds() {
        Integer[] numArr = (Integer[]) this.e.keySet().toArray(new Integer[0]);
        int[] iArr = new int[numArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = numArr[i].intValue();
        }
        return iArr;
    }

    public boolean isForceId() {
        return this.c;
    }

    public void setForceId(boolean z) {
        this.c = z;
    }

    public void setValidateOnParse(boolean z) {
        this.a = z;
    }

    public void dump(MotionScene motionScene, int... iArr) {
        HashSet hashSet;
        Set<Integer> keySet = this.e.keySet();
        if (iArr.length != 0) {
            HashSet hashSet2 = new HashSet();
            for (int i : iArr) {
                hashSet2.add(Integer.valueOf(i));
            }
            hashSet = hashSet2;
        } else {
            hashSet = new HashSet(keySet);
        }
        System.out.println(hashSet.size() + " constraints");
        StringBuilder sb = new StringBuilder();
        Integer[] numArr = (Integer[]) hashSet.toArray(new Integer[0]);
        for (Integer num : numArr) {
            sb.append("<Constraint id=");
            sb.append(num);
            sb.append(" \n");
            this.e.get(num).layout.dump(motionScene, sb);
            sb.append("/>\n");
        }
        System.out.println(sb.toString());
    }
}
