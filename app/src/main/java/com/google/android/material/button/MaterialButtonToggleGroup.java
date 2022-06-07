package com.google.android.material.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.BoolRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class MaterialButtonToggleGroup extends LinearLayout {
    private static final String a = "MaterialButtonToggleGroup";
    private static final int b = R.style.Widget_MaterialComponents_MaterialButtonToggleGroup;
    private final List<b> c;
    private final a d;
    private final c e;
    private final LinkedHashSet<OnButtonCheckedListener> f;
    private final Comparator<MaterialButton> g;
    private Integer[] h;
    private boolean i;
    private boolean j;
    private boolean k;
    @IdRes
    private int l;

    /* loaded from: classes2.dex */
    public interface OnButtonCheckedListener {
        void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, @IdRes int i, boolean z);
    }

    public MaterialButtonToggleGroup(@NonNull Context context) {
        this(context, null);
    }

    public MaterialButtonToggleGroup(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonToggleGroupStyle);
    }

    public MaterialButtonToggleGroup(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, b), attributeSet, i);
        this.c = new ArrayList();
        this.d = new a();
        this.e = new c();
        this.f = new LinkedHashSet<>();
        this.g = new Comparator<MaterialButton>() { // from class: com.google.android.material.button.MaterialButtonToggleGroup.1
            /* renamed from: a */
            public int compare(MaterialButton materialButton, MaterialButton materialButton2) {
                int compareTo = Boolean.valueOf(materialButton.isChecked()).compareTo(Boolean.valueOf(materialButton2.isChecked()));
                if (compareTo != 0) {
                    return compareTo;
                }
                int compareTo2 = Boolean.valueOf(materialButton.isPressed()).compareTo(Boolean.valueOf(materialButton2.isPressed()));
                return compareTo2 != 0 ? compareTo2 : Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(materialButton)).compareTo(Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(materialButton2)));
            }
        };
        this.i = false;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R.styleable.MaterialButtonToggleGroup, i, b, new int[0]);
        setSingleSelection(obtainStyledAttributes.getBoolean(R.styleable.MaterialButtonToggleGroup_singleSelection, false));
        this.l = obtainStyledAttributes.getResourceId(R.styleable.MaterialButtonToggleGroup_checkedButton, -1);
        this.k = obtainStyledAttributes.getBoolean(R.styleable.MaterialButtonToggleGroup_selectionRequired, false);
        setChildrenDrawingOrderEnabled(true);
        obtainStyledAttributes.recycle();
        ViewCompat.setImportantForAccessibility(this, 1);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.l;
        if (i != -1) {
            d(i, true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(@NonNull Canvas canvas) {
        c();
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof MaterialButton)) {
            Log.e(a, "Child views must be of type MaterialButton.");
            return;
        }
        super.addView(view, i, layoutParams);
        MaterialButton materialButton = (MaterialButton) view;
        setGeneratedIdIfNeeded(materialButton);
        setupButtonChild(materialButton);
        if (materialButton.isChecked()) {
            b(materialButton.getId(), true);
            setCheckedId(materialButton.getId());
        }
        ShapeAppearanceModel shapeAppearanceModel = materialButton.getShapeAppearanceModel();
        this.c.add(new b(shapeAppearanceModel.getTopLeftCornerSize(), shapeAppearanceModel.getBottomLeftCornerSize(), shapeAppearanceModel.getTopRightCornerSize(), shapeAppearanceModel.getBottomRightCornerSize()));
        ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() { // from class: com.google.android.material.button.MaterialButtonToggleGroup.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, MaterialButtonToggleGroup.this.a(view2), 1, false, ((MaterialButton) view2).isChecked()));
            }
        });
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            MaterialButton materialButton = (MaterialButton) view;
            materialButton.removeOnCheckedChangeListener(this.d);
            materialButton.setOnPressedChangeListenerInternal(null);
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            this.c.remove(indexOfChild);
        }
        a();
        b();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        a();
        b();
        super.onMeasure(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    @NonNull
    public CharSequence getAccessibilityClassName() {
        return MaterialButtonToggleGroup.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getVisibleButtonCount(), false, isSingleSelection() ? 1 : 2));
    }

    public void check(@IdRes int i) {
        if (i != this.l) {
            d(i, true);
        }
    }

    public void uncheck(@IdRes int i) {
        d(i, false);
    }

    public void clearChecked() {
        this.i = true;
        for (int i = 0; i < getChildCount(); i++) {
            MaterialButton a2 = a(i);
            a2.setChecked(false);
            c(a2.getId(), false);
        }
        this.i = false;
        setCheckedId(-1);
    }

    @IdRes
    public int getCheckedButtonId() {
        if (this.j) {
            return this.l;
        }
        return -1;
    }

    @NonNull
    public List<Integer> getCheckedButtonIds() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            MaterialButton a2 = a(i);
            if (a2.isChecked()) {
                arrayList.add(Integer.valueOf(a2.getId()));
            }
        }
        return arrayList;
    }

    public void addOnButtonCheckedListener(@NonNull OnButtonCheckedListener onButtonCheckedListener) {
        this.f.add(onButtonCheckedListener);
    }

    public void removeOnButtonCheckedListener(@NonNull OnButtonCheckedListener onButtonCheckedListener) {
        this.f.remove(onButtonCheckedListener);
    }

    public void clearOnButtonCheckedListeners() {
        this.f.clear();
    }

    public boolean isSingleSelection() {
        return this.j;
    }

    public void setSingleSelection(boolean z) {
        if (this.j != z) {
            this.j = z;
            clearChecked();
        }
    }

    public void setSelectionRequired(boolean z) {
        this.k = z;
    }

    public boolean isSelectionRequired() {
        return this.k;
    }

    public void setSingleSelection(@BoolRes int i) {
        setSingleSelection(getResources().getBoolean(i));
    }

    private void a(@IdRes int i, boolean z) {
        View findViewById = findViewById(i);
        if (findViewById instanceof MaterialButton) {
            this.i = true;
            ((MaterialButton) findViewById).setChecked(z);
            this.i = false;
        }
    }

    private void setCheckedId(int i) {
        this.l = i;
        c(i, true);
    }

    private void b() {
        int firstVisibleChildIndex = getFirstVisibleChildIndex();
        if (firstVisibleChildIndex != -1) {
            for (int i = firstVisibleChildIndex + 1; i < getChildCount(); i++) {
                MaterialButton a2 = a(i);
                int min = Math.min(a2.getStrokeWidth(), a(i - 1).getStrokeWidth());
                LinearLayout.LayoutParams b2 = b(a2);
                if (getOrientation() == 0) {
                    MarginLayoutParamsCompat.setMarginEnd(b2, 0);
                    MarginLayoutParamsCompat.setMarginStart(b2, -min);
                    b2.topMargin = 0;
                } else {
                    b2.bottomMargin = 0;
                    b2.topMargin = -min;
                    MarginLayoutParamsCompat.setMarginStart(b2, 0);
                }
                a2.setLayoutParams(b2);
            }
            b(firstVisibleChildIndex);
        }
    }

    private MaterialButton a(int i) {
        return (MaterialButton) getChildAt(i);
    }

    private void b(int i) {
        if (getChildCount() != 0 && i != -1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) a(i).getLayoutParams();
            if (getOrientation() == 1) {
                layoutParams.topMargin = 0;
                layoutParams.bottomMargin = 0;
                return;
            }
            MarginLayoutParamsCompat.setMarginEnd(layoutParams, 0);
            MarginLayoutParamsCompat.setMarginStart(layoutParams, 0);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }
    }

    @VisibleForTesting
    void a() {
        int childCount = getChildCount();
        int firstVisibleChildIndex = getFirstVisibleChildIndex();
        int lastVisibleChildIndex = getLastVisibleChildIndex();
        for (int i = 0; i < childCount; i++) {
            MaterialButton a2 = a(i);
            if (a2.getVisibility() != 8) {
                ShapeAppearanceModel.Builder builder = a2.getShapeAppearanceModel().toBuilder();
                a(builder, a(i, firstVisibleChildIndex, lastVisibleChildIndex));
                a2.setShapeAppearanceModel(builder.build());
            }
        }
    }

    private int getFirstVisibleChildIndex() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (c(i)) {
                return i;
            }
        }
        return -1;
    }

    private int getLastVisibleChildIndex() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (c(childCount)) {
                return childCount;
            }
        }
        return -1;
    }

    private boolean c(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    private int getVisibleButtonCount() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof MaterialButton) && c(i2)) {
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(@Nullable View view) {
        if (!(view instanceof MaterialButton)) {
            return -1;
        }
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2) == view) {
                return i;
            }
            if ((getChildAt(i2) instanceof MaterialButton) && c(i2)) {
                i++;
            }
        }
        return -1;
    }

    @Nullable
    private b a(int i, int i2, int i3) {
        b bVar = this.c.get(i);
        if (i2 == i3) {
            return bVar;
        }
        boolean z = getOrientation() == 0;
        if (i == i2) {
            return z ? b.a(bVar, this) : b.c(bVar);
        }
        if (i == i3) {
            return z ? b.b(bVar, this) : b.d(bVar);
        }
        return null;
    }

    private static void a(ShapeAppearanceModel.Builder builder, @Nullable b bVar) {
        if (bVar == null) {
            builder.setAllCornerSizes(0.0f);
        } else {
            builder.setTopLeftCornerSize(bVar.a).setBottomLeftCornerSize(bVar.d).setTopRightCornerSize(bVar.b).setBottomRightCornerSize(bVar.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(int i, boolean z) {
        List<Integer> checkedButtonIds = getCheckedButtonIds();
        if (!this.k || !checkedButtonIds.isEmpty()) {
            if (z && this.j) {
                checkedButtonIds.remove(Integer.valueOf(i));
                for (Integer num : checkedButtonIds) {
                    int intValue = num.intValue();
                    a(intValue, false);
                    c(intValue, false);
                }
            }
            return true;
        }
        a(i, true);
        this.l = i;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(@IdRes int i, boolean z) {
        Iterator<OnButtonCheckedListener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onButtonChecked(this, i, z);
        }
    }

    private void d(int i, boolean z) {
        MaterialButton materialButton = (MaterialButton) findViewById(i);
        if (materialButton != null) {
            materialButton.setChecked(z);
        }
    }

    private void setGeneratedIdIfNeeded(@NonNull MaterialButton materialButton) {
        if (materialButton.getId() == -1) {
            materialButton.setId(ViewCompat.generateViewId());
        }
    }

    private void setupButtonChild(@NonNull MaterialButton materialButton) {
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        materialButton.setCheckable(true);
        materialButton.addOnCheckedChangeListener(this.d);
        materialButton.setOnPressedChangeListenerInternal(this.e);
        materialButton.setShouldDrawSurfaceColorStroke(true);
    }

    @NonNull
    private LinearLayout.LayoutParams b(@NonNull View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return (LinearLayout.LayoutParams) layoutParams;
        }
        return new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        Integer[] numArr = this.h;
        if (numArr != null && i2 < numArr.length) {
            return numArr[i2].intValue();
        }
        Log.w(a, "Child order wasn't updated");
        return i2;
    }

    private void c() {
        TreeMap treeMap = new TreeMap(this.g);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            treeMap.put(a(i), Integer.valueOf(i));
        }
        this.h = (Integer[]) treeMap.values().toArray(new Integer[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a implements MaterialButton.OnCheckedChangeListener {
        private a() {
        }

        @Override // com.google.android.material.button.MaterialButton.OnCheckedChangeListener
        public void onCheckedChanged(@NonNull MaterialButton materialButton, boolean z) {
            if (!MaterialButtonToggleGroup.this.i) {
                if (MaterialButtonToggleGroup.this.j) {
                    MaterialButtonToggleGroup.this.l = z ? materialButton.getId() : -1;
                }
                if (MaterialButtonToggleGroup.this.b(materialButton.getId(), z)) {
                    MaterialButtonToggleGroup.this.c(materialButton.getId(), materialButton.isChecked());
                }
                MaterialButtonToggleGroup.this.invalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c implements MaterialButton.a {
        private c() {
        }

        @Override // com.google.android.material.button.MaterialButton.a
        public void a(@NonNull MaterialButton materialButton, boolean z) {
            MaterialButtonToggleGroup.this.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b {
        private static final CornerSize e = new AbsoluteCornerSize(0.0f);
        CornerSize a;
        CornerSize b;
        CornerSize c;
        CornerSize d;

        b(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
            this.a = cornerSize;
            this.b = cornerSize3;
            this.c = cornerSize4;
            this.d = cornerSize2;
        }

        public static b a(b bVar, View view) {
            return ViewUtils.isLayoutRtl(view) ? b(bVar) : a(bVar);
        }

        public static b b(b bVar, View view) {
            return ViewUtils.isLayoutRtl(view) ? a(bVar) : b(bVar);
        }

        public static b a(b bVar) {
            CornerSize cornerSize = bVar.a;
            CornerSize cornerSize2 = bVar.d;
            CornerSize cornerSize3 = e;
            return new b(cornerSize, cornerSize2, cornerSize3, cornerSize3);
        }

        public static b b(b bVar) {
            CornerSize cornerSize = e;
            return new b(cornerSize, cornerSize, bVar.b, bVar.c);
        }

        public static b c(b bVar) {
            CornerSize cornerSize = bVar.a;
            CornerSize cornerSize2 = e;
            return new b(cornerSize, cornerSize2, bVar.b, cornerSize2);
        }

        public static b d(b bVar) {
            CornerSize cornerSize = e;
            return new b(cornerSize, bVar.d, cornerSize, bVar.c);
        }
    }
}
