package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class NavigationMenuItemView extends ForegroundLinearLayout implements MenuView.ItemView {
    private static final int[] c = {16842912};
    boolean b;
    private int d;
    private boolean e;
    private final CheckedTextView f;
    private FrameLayout g;
    private MenuItemImpl h;
    private ColorStateList i;
    private boolean j;
    private Drawable k;
    private final AccessibilityDelegateCompat l;

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c2) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public NavigationMenuItemView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationMenuItemView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.l = new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.NavigationMenuItemView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(NavigationMenuItemView.this.b);
            }
        };
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.design_navigation_menu_item, (ViewGroup) this, true);
        setIconSize(context.getResources().getDimensionPixelSize(R.dimen.design_navigation_icon_size));
        this.f = (CheckedTextView) findViewById(R.id.design_menu_item_text);
        this.f.setDuplicateParentStateEnabled(true);
        ViewCompat.setAccessibilityDelegate(this.f, this.l);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(@NonNull MenuItemImpl menuItemImpl, int i) {
        this.h = menuItemImpl;
        if (menuItemImpl.getItemId() > 0) {
            setId(menuItemImpl.getItemId());
        }
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, c());
        }
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setTitle(menuItemImpl.getTitle());
        setIcon(menuItemImpl.getIcon());
        setActionView(menuItemImpl.getActionView());
        setContentDescription(menuItemImpl.getContentDescription());
        TooltipCompat.setTooltipText(this, menuItemImpl.getTooltipText());
        b();
    }

    private boolean a() {
        return this.h.getTitle() == null && this.h.getIcon() == null && this.h.getActionView() != null;
    }

    private void b() {
        if (a()) {
            this.f.setVisibility(8);
            FrameLayout frameLayout = this.g;
            if (frameLayout != null) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) frameLayout.getLayoutParams();
                layoutParams.width = -1;
                this.g.setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.f.setVisibility(0);
        FrameLayout frameLayout2 = this.g;
        if (frameLayout2 != null) {
            LinearLayoutCompat.LayoutParams layoutParams2 = (LinearLayoutCompat.LayoutParams) frameLayout2.getLayoutParams();
            layoutParams2.width = -2;
            this.g.setLayoutParams(layoutParams2);
        }
    }

    public void recycle() {
        FrameLayout frameLayout = this.g;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        this.f.setCompoundDrawables(null, null, null, null);
    }

    private void setActionView(@Nullable View view) {
        if (view != null) {
            if (this.g == null) {
                this.g = (FrameLayout) ((ViewStub) findViewById(R.id.design_menu_item_action_area_stub)).inflate();
            }
            this.g.removeAllViews();
            this.g.addView(view);
        }
    }

    @Nullable
    private StateListDrawable c() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorControlHighlight, typedValue, true)) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(c, new ColorDrawable(typedValue.data));
        stateListDrawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
        return stateListDrawable;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.h;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        this.f.setText(charSequence);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        refreshDrawableState();
        if (this.b != z) {
            this.b = z;
            this.l.sendAccessibilityEvent(this.f, 2048);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        refreshDrawableState();
        this.f.setChecked(z);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            if (this.j) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = DrawableCompat.wrap(drawable).mutate();
                DrawableCompat.setTintList(drawable, this.i);
            }
            int i = this.d;
            drawable.setBounds(0, 0, i, i);
        } else if (this.e) {
            if (this.k == null) {
                this.k = ResourcesCompat.getDrawable(getResources(), R.drawable.navigation_empty_icon, getContext().getTheme());
                Drawable drawable2 = this.k;
                if (drawable2 != null) {
                    int i2 = this.d;
                    drawable2.setBounds(0, 0, i2, i2);
                }
            }
            drawable = this.k;
        }
        TextViewCompat.setCompoundDrawablesRelative(this.f, drawable, null, null, null);
    }

    public void setIconSize(@Dimension int i) {
        this.d = i;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.h;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.h.isChecked()) {
            mergeDrawableStates(onCreateDrawableState, c);
        }
        return onCreateDrawableState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIconTintList(ColorStateList colorStateList) {
        this.i = colorStateList;
        this.j = this.i != null;
        MenuItemImpl menuItemImpl = this.h;
        if (menuItemImpl != null) {
            setIcon(menuItemImpl.getIcon());
        }
    }

    public void setTextAppearance(int i) {
        TextViewCompat.setTextAppearance(this.f, i);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.f.setTextColor(colorStateList);
    }

    public void setNeedsEmptyIcon(boolean z) {
        this.e = z;
    }

    public void setHorizontalPadding(int i) {
        setPadding(i, 0, i, 0);
    }

    public void setIconPadding(int i) {
        this.f.setCompoundDrawablePadding(i);
    }

    public void setMaxLines(int i) {
        this.f.setMaxLines(i);
    }
}
