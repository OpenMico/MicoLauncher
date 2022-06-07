package com.google.android.material.chip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.annotation.BoolRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ChipGroup extends FlowLayout {
    private static final int a = R.style.Widget_MaterialComponents_ChipGroup;
    @Dimension
    private int b;
    @Dimension
    private int c;
    private boolean d;
    private boolean e;
    @Nullable
    private OnCheckedChangeListener f;
    private final a g;
    @NonNull
    private b h;
    @IdRes
    private int i;
    private boolean j;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(ChipGroup chipGroup, @IdRes int i);
    }

    /* loaded from: classes2.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public ChipGroup(Context context) {
        this(context, null);
    }

    public ChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    public ChipGroup(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        this.g = new a();
        this.h = new b();
        this.i = -1;
        this.j = false;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R.styleable.ChipGroup, i, a, new int[0]);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
        setChipSpacingHorizontal(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, dimensionPixelOffset));
        setChipSpacingVertical(obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, dimensionPixelOffset));
        setSingleLine(obtainStyledAttributes.getBoolean(R.styleable.ChipGroup_singleLine, false));
        setSingleSelection(obtainStyledAttributes.getBoolean(R.styleable.ChipGroup_singleSelection, false));
        setSelectionRequired(obtainStyledAttributes.getBoolean(R.styleable.ChipGroup_selectionRequired, false));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
        if (resourceId != -1) {
            this.i = resourceId;
        }
        obtainStyledAttributes.recycle();
        super.setOnHierarchyChangeListener(this.h);
        ViewCompat.setImportantForAccessibility(this, 1);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCount(), isSingleLine() ? getChipCount() : -1, false, isSingleSelection() ? 1 : 2));
    }

    @Override // android.view.ViewGroup
    @NonNull
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    @NonNull
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    @NonNull
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.h.b = onHierarchyChangeListener;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.i;
        if (i != -1) {
            b(i, true);
            setCheckedId(this.i);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof Chip) {
            Chip chip = (Chip) view;
            if (chip.isChecked()) {
                int i2 = this.i;
                if (i2 != -1 && this.d) {
                    b(i2, false);
                }
                setCheckedId(chip.getId());
            }
        }
        super.addView(view, i, layoutParams);
    }

    @Deprecated
    public void setDividerDrawableHorizontal(Drawable drawable) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setDividerDrawableVertical(@Nullable Drawable drawable) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerHorizontal(int i) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerVertical(int i) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setFlexWrap(int i) {
        throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
    }

    public void check(@IdRes int i) {
        int i2 = this.i;
        if (i != i2) {
            if (i2 != -1 && this.d) {
                b(i2, false);
            }
            if (i != -1) {
                b(i, true);
            }
            setCheckedId(i);
        }
    }

    @IdRes
    public int getCheckedChipId() {
        if (this.d) {
            return this.i;
        }
        return -1;
    }

    @NonNull
    public List<Integer> getCheckedChipIds() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof Chip) && ((Chip) childAt).isChecked()) {
                arrayList.add(Integer.valueOf(childAt.getId()));
                if (this.d) {
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    public void clearCheck() {
        this.j = true;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Chip) {
                ((Chip) childAt).setChecked(false);
            }
        }
        this.j = false;
        setCheckedId(-1);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.f = onCheckedChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(int i) {
        a(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, boolean z) {
        this.i = i;
        OnCheckedChangeListener onCheckedChangeListener = this.f;
        if (onCheckedChangeListener != null && this.d && z) {
            onCheckedChangeListener.onCheckedChanged(this, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(@IdRes int i, boolean z) {
        View findViewById = findViewById(i);
        if (findViewById instanceof Chip) {
            this.j = true;
            ((Chip) findViewById).setChecked(z);
            this.j = false;
        }
    }

    private int getChipCount() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2) instanceof Chip) {
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(@Nullable View view) {
        if (!(view instanceof Chip)) {
            return -1;
        }
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2) instanceof Chip) {
                if (((Chip) getChildAt(i2)) == view) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public void setChipSpacing(@Dimension int i) {
        setChipSpacingHorizontal(i);
        setChipSpacingVertical(i);
    }

    public void setChipSpacingResource(@DimenRes int i) {
        setChipSpacing(getResources().getDimensionPixelOffset(i));
    }

    @Dimension
    public int getChipSpacingHorizontal() {
        return this.b;
    }

    public void setChipSpacingHorizontal(@Dimension int i) {
        if (this.b != i) {
            this.b = i;
            setItemSpacing(i);
            requestLayout();
        }
    }

    public void setChipSpacingHorizontalResource(@DimenRes int i) {
        setChipSpacingHorizontal(getResources().getDimensionPixelOffset(i));
    }

    @Dimension
    public int getChipSpacingVertical() {
        return this.c;
    }

    public void setChipSpacingVertical(@Dimension int i) {
        if (this.c != i) {
            this.c = i;
            setLineSpacing(i);
            requestLayout();
        }
    }

    public void setChipSpacingVerticalResource(@DimenRes int i) {
        setChipSpacingVertical(getResources().getDimensionPixelOffset(i));
    }

    @Override // com.google.android.material.internal.FlowLayout
    public boolean isSingleLine() {
        return super.isSingleLine();
    }

    @Override // com.google.android.material.internal.FlowLayout
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
    }

    public void setSingleLine(@BoolRes int i) {
        setSingleLine(getResources().getBoolean(i));
    }

    public boolean isSingleSelection() {
        return this.d;
    }

    public void setSingleSelection(boolean z) {
        if (this.d != z) {
            this.d = z;
            clearCheck();
        }
    }

    public void setSingleSelection(@BoolRes int i) {
        setSingleSelection(getResources().getBoolean(i));
    }

    public void setSelectionRequired(boolean z) {
        this.e = z;
    }

    public boolean isSelectionRequired() {
        return this.e;
    }

    /* loaded from: classes2.dex */
    private class a implements CompoundButton.OnCheckedChangeListener {
        private a() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean z) {
            if (!ChipGroup.this.j) {
                if (!ChipGroup.this.getCheckedChipIds().isEmpty() || !ChipGroup.this.e) {
                    int id = compoundButton.getId();
                    if (z) {
                        if (!(ChipGroup.this.i == -1 || ChipGroup.this.i == id || !ChipGroup.this.d)) {
                            ChipGroup chipGroup = ChipGroup.this;
                            chipGroup.b(chipGroup.i, false);
                        }
                        ChipGroup.this.setCheckedId(id);
                    } else if (ChipGroup.this.i == id) {
                        ChipGroup.this.setCheckedId(-1);
                    }
                } else {
                    ChipGroup.this.b(compoundButton.getId(), true);
                    ChipGroup.this.a(compoundButton.getId(), false);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private class b implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener b;

        private b() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            if (view == ChipGroup.this && (view2 instanceof Chip)) {
                if (view2.getId() == -1) {
                    view2.setId(ViewCompat.generateViewId());
                }
                Chip chip = (Chip) view2;
                if (chip.isChecked()) {
                    ((ChipGroup) view).check(chip.getId());
                }
                chip.setOnCheckedChangeListenerInternal(ChipGroup.this.g);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.b;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            if (view == ChipGroup.this && (view2 instanceof Chip)) {
                ((Chip) view2).setOnCheckedChangeListenerInternal(null);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.b;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }
}
