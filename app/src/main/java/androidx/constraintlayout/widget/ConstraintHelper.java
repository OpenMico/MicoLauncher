package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class ConstraintHelper extends View {
    private View[] a;
    private HashMap<Integer, String> b;
    protected int mCount;
    protected Helper mHelperWidget;
    protected int[] mIds;
    protected String mReferenceIds;
    protected String mReferenceTags;
    protected boolean mUseViewMeasure;
    protected Context myContext;

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    public void resolveRtl(ConstraintWidget constraintWidget, boolean z) {
    }

    public void updatePostConstraints(ConstraintLayout constraintLayout) {
    }

    public void updatePostLayout(ConstraintLayout constraintLayout) {
    }

    public void updatePostMeasure(ConstraintLayout constraintLayout) {
    }

    public void updatePreDraw(ConstraintLayout constraintLayout) {
    }

    public ConstraintHelper(Context context) {
        super(context);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.a = null;
        this.b = new HashMap<>();
        this.myContext = context;
        init(null);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.a = null;
        this.b = new HashMap<>();
        this.myContext = context;
        init(attributeSet);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.a = null;
        this.b = new HashMap<>();
        this.myContext = context;
        init(attributeSet);
    }

    public void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                    this.mReferenceIds = obtainStyledAttributes.getString(index);
                    setIds(this.mReferenceIds);
                } else if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_tags) {
                    this.mReferenceTags = obtainStyledAttributes.getString(index);
                    setReferenceTags(this.mReferenceTags);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String str = this.mReferenceIds;
        if (str != null) {
            setIds(str);
        }
        String str2 = this.mReferenceTags;
        if (str2 != null) {
            setReferenceTags(str2);
        }
    }

    public void addView(View view) {
        if (view != this) {
            if (view.getId() == -1) {
                Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have an id");
            } else if (view.getParent() == null) {
                Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have a parent");
            } else {
                this.mReferenceIds = null;
                a(view.getId());
                requestLayout();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        r5 = r4.mCount;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
        if (r1 >= (r5 - 1)) goto L_0x0027;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
        r5 = r4.mIds;
        r2 = r1 + 1;
        r5[r1] = r5[r2];
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
        r4.mIds[r5 - 1] = 0;
        r4.mCount = r5 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void removeView(android.view.View r5) {
        /*
            r4 = this;
            int r5 = r5.getId()
            r0 = -1
            if (r5 != r0) goto L_0x0008
            return
        L_0x0008:
            r0 = 0
            r4.mReferenceIds = r0
            r0 = 0
            r1 = r0
        L_0x000d:
            int r2 = r4.mCount
            if (r1 >= r2) goto L_0x0035
            int[] r2 = r4.mIds
            r2 = r2[r1]
            if (r2 != r5) goto L_0x0032
        L_0x0017:
            int r5 = r4.mCount
            int r2 = r5 + (-1)
            if (r1 >= r2) goto L_0x0027
            int[] r5 = r4.mIds
            int r2 = r1 + 1
            r3 = r5[r2]
            r5[r1] = r3
            r1 = r2
            goto L_0x0017
        L_0x0027:
            int[] r1 = r4.mIds
            int r2 = r5 + (-1)
            r1[r2] = r0
            int r5 = r5 + (-1)
            r4.mCount = r5
            goto L_0x0035
        L_0x0032:
            int r1 = r1 + 1
            goto L_0x000d
        L_0x0035:
            r4.requestLayout()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintHelper.removeView(android.view.View):void");
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.mIds, this.mCount);
    }

    public void setReferencedIds(int[] iArr) {
        this.mReferenceIds = null;
        this.mCount = 0;
        for (int i : iArr) {
            a(i);
        }
    }

    private void a(int i) {
        if (i != getId()) {
            int i2 = this.mCount + 1;
            int[] iArr = this.mIds;
            if (i2 > iArr.length) {
                this.mIds = Arrays.copyOf(iArr, iArr.length * 2);
            }
            int[] iArr2 = this.mIds;
            int i3 = this.mCount;
            iArr2[i3] = i;
            this.mCount = i3 + 1;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mUseViewMeasure) {
            super.onMeasure(i, i2);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public void validateParams() {
        if (this.mHelperWidget != null) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                ((ConstraintLayout.LayoutParams) layoutParams).t = (ConstraintWidget) this.mHelperWidget;
            }
        }
    }

    private void a(String str) {
        if (str != null && str.length() != 0 && this.myContext != null) {
            String trim = str.trim();
            if (getParent() instanceof ConstraintLayout) {
                ConstraintLayout constraintLayout = (ConstraintLayout) getParent();
            }
            int c = c(trim);
            if (c != 0) {
                this.b.put(Integer.valueOf(c), trim);
                a(c);
                return;
            }
            Log.w("ConstraintHelper", "Could not find id of \"" + trim + "\"");
        }
    }

    private void b(String str) {
        if (!(str == null || str.length() == 0 || this.myContext == null)) {
            String trim = str.trim();
            ConstraintLayout constraintLayout = null;
            if (getParent() instanceof ConstraintLayout) {
                constraintLayout = (ConstraintLayout) getParent();
            }
            if (constraintLayout == null) {
                Log.w("ConstraintHelper", "Parent not a ConstraintLayout");
                return;
            }
            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = constraintLayout.getChildAt(i);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if ((layoutParams instanceof ConstraintLayout.LayoutParams) && trim.equals(((ConstraintLayout.LayoutParams) layoutParams).constraintTag)) {
                    if (childAt.getId() == -1) {
                        Log.w("ConstraintHelper", "to use ConstraintTag view " + childAt.getClass().getSimpleName() + " must have an ID");
                    } else {
                        a(childAt.getId());
                    }
                }
            }
        }
    }

    private int c(String str) {
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        int i = 0;
        if (isInEditMode() && constraintLayout != null) {
            Object designInformation = constraintLayout.getDesignInformation(0, str);
            if (designInformation instanceof Integer) {
                i = ((Integer) designInformation).intValue();
            }
        }
        if (i == 0 && constraintLayout != null) {
            i = a(constraintLayout, str);
        }
        if (i == 0) {
            try {
                i = R.id.class.getField(str).getInt(null);
            } catch (Exception unused) {
            }
        }
        return i == 0 ? this.myContext.getResources().getIdentifier(str, "id", this.myContext.getPackageName()) : i;
    }

    private int a(ConstraintLayout constraintLayout, String str) {
        Resources resources;
        if (str == null || constraintLayout == null || (resources = this.myContext.getResources()) == null) {
            return 0;
        }
        int childCount = constraintLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            if (childAt.getId() != -1) {
                String str2 = null;
                try {
                    str2 = resources.getResourceEntryName(childAt.getId());
                } catch (Resources.NotFoundException unused) {
                }
                if (str.equals(str2)) {
                    return childAt.getId();
                }
            }
        }
        return 0;
    }

    protected void setIds(String str) {
        this.mReferenceIds = str;
        if (str != null) {
            int i = 0;
            this.mCount = 0;
            while (true) {
                int indexOf = str.indexOf(44, i);
                if (indexOf == -1) {
                    a(str.substring(i));
                    return;
                } else {
                    a(str.substring(i, indexOf));
                    i = indexOf + 1;
                }
            }
        }
    }

    protected void setReferenceTags(String str) {
        this.mReferenceTags = str;
        if (str != null) {
            int i = 0;
            this.mCount = 0;
            while (true) {
                int indexOf = str.indexOf(44, i);
                if (indexOf == -1) {
                    b(str.substring(i));
                    return;
                } else {
                    b(str.substring(i, indexOf));
                    i = indexOf + 1;
                }
            }
        }
    }

    protected void applyLayoutFeatures(ConstraintLayout constraintLayout) {
        int visibility = getVisibility();
        float elevation = Build.VERSION.SDK_INT >= 21 ? getElevation() : 0.0f;
        for (int i = 0; i < this.mCount; i++) {
            View viewById = constraintLayout.getViewById(this.mIds[i]);
            if (viewById != null) {
                viewById.setVisibility(visibility);
                if (elevation > 0.0f && Build.VERSION.SDK_INT >= 21) {
                    viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                }
            }
        }
    }

    protected void applyLayoutFeatures() {
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ConstraintLayout)) {
            applyLayoutFeatures((ConstraintLayout) parent);
        }
    }

    public void updatePreLayout(ConstraintLayout constraintLayout) {
        String str;
        int a;
        if (isInEditMode()) {
            setIds(this.mReferenceIds);
        }
        Helper helper = this.mHelperWidget;
        if (helper != null) {
            helper.removeAllIds();
            for (int i = 0; i < this.mCount; i++) {
                int i2 = this.mIds[i];
                View viewById = constraintLayout.getViewById(i2);
                if (viewById == null && (a = a(constraintLayout, (str = this.b.get(Integer.valueOf(i2))))) != 0) {
                    this.mIds[i] = a;
                    this.b.put(Integer.valueOf(a), str);
                    viewById = constraintLayout.getViewById(a);
                }
                if (viewById != null) {
                    this.mHelperWidget.add(constraintLayout.getViewWidget(viewById));
                }
            }
            this.mHelperWidget.updateConstraints(constraintLayout.mLayoutWidget);
        }
    }

    public void updatePreLayout(ConstraintWidgetContainer constraintWidgetContainer, Helper helper, SparseArray<ConstraintWidget> sparseArray) {
        helper.removeAllIds();
        for (int i = 0; i < this.mCount; i++) {
            helper.add(sparseArray.get(this.mIds[i]));
        }
    }

    protected View[] getViews(ConstraintLayout constraintLayout) {
        View[] viewArr = this.a;
        if (viewArr == null || viewArr.length != this.mCount) {
            this.a = new View[this.mCount];
        }
        for (int i = 0; i < this.mCount; i++) {
            this.a[i] = constraintLayout.getViewById(this.mIds[i]);
        }
        return this.a;
    }

    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        if (constraint.layout.mReferenceIds != null) {
            setReferencedIds(constraint.layout.mReferenceIds);
        } else if (constraint.layout.mReferenceIdString != null && constraint.layout.mReferenceIdString.length() > 0) {
            constraint.layout.mReferenceIds = a(this, constraint.layout.mReferenceIdString);
        }
        helperWidget.removeAllIds();
        if (constraint.layout.mReferenceIds != null) {
            for (int i = 0; i < constraint.layout.mReferenceIds.length; i++) {
                ConstraintWidget constraintWidget = sparseArray.get(constraint.layout.mReferenceIds[i]);
                if (constraintWidget != null) {
                    helperWidget.add(constraintWidget);
                }
            }
        }
    }

    private int[] a(View view, String str) {
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        view.getContext();
        int[] iArr = new int[split.length];
        int i = 0;
        for (String str2 : split) {
            int c = c(str2.trim());
            if (c != 0) {
                i++;
                iArr[i] = c;
            }
        }
        return i != split.length ? Arrays.copyOf(iArr, i) : iArr;
    }

    @Override // android.view.View
    public void setTag(int i, Object obj) {
        super.setTag(i, obj);
        if (obj == null && this.mReferenceIds == null) {
            a(i);
        }
    }
}
