package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    private static final int[] a = {16842912};
    private float c;
    private float d;
    private float e;
    private int f;
    private boolean g;
    @Nullable
    private MenuItemImpl m;
    @Nullable
    private ColorStateList n;
    @Nullable
    private Drawable o;
    @Nullable
    private Drawable p;
    @Nullable
    private BadgeDrawable q;
    private int l = -1;
    private ImageView h = (ImageView) findViewById(R.id.navigation_bar_item_icon_view);
    private final ViewGroup i = (ViewGroup) findViewById(R.id.navigation_bar_item_labels_group);
    private final TextView j = (TextView) findViewById(R.id.navigation_bar_item_small_label_view);
    private final TextView k = (TextView) findViewById(R.id.navigation_bar_item_large_label_view);
    private final int b = getResources().getDimensionPixelSize(getItemDefaultMarginResId());

    @LayoutRes
    protected abstract int getItemLayoutResId();

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public NavigationBarItemView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        setBackgroundResource(getItemBackgroundResId());
        this.i.setTag(R.id.mtrl_view_tag_bottom_padding, Integer.valueOf(this.i.getPaddingBottom()));
        ViewCompat.setImportantForAccessibility(this.j, 2);
        ViewCompat.setImportantForAccessibility(this.k, 2);
        setFocusable(true);
        a(this.j.getTextSize(), this.k.getTextSize());
        ImageView imageView = this.h;
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (NavigationBarItemView.this.h.getVisibility() == 0) {
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        navigationBarItemView.a(navigationBarItemView.h);
                    }
                }
            });
        }
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.i.getLayoutParams();
        return Math.max(getSuggestedIconWidth(), layoutParams.leftMargin + this.i.getMeasuredWidth() + layoutParams.rightMargin);
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.i.getLayoutParams();
        return getSuggestedIconHeight() + layoutParams.topMargin + this.i.getMeasuredHeight() + layoutParams.bottomMargin;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(@NonNull MenuItemImpl menuItemImpl, int i) {
        CharSequence charSequence;
        this.m = menuItemImpl;
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setIcon(menuItemImpl.getIcon());
        setTitle(menuItemImpl.getTitle());
        setId(menuItemImpl.getItemId());
        if (!TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(menuItemImpl.getContentDescription());
        }
        if (!TextUtils.isEmpty(menuItemImpl.getTooltipText())) {
            charSequence = menuItemImpl.getTooltipText();
        } else {
            charSequence = menuItemImpl.getTitle();
        }
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, charSequence);
        }
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
    }

    public void setItemPosition(int i) {
        this.l = i;
    }

    public int getItemPosition() {
        return this.l;
    }

    public void setShifting(boolean z) {
        if (this.g != z) {
            this.g = z;
            if (this.m != null) {
                setChecked(this.m.isChecked());
            }
        }
    }

    public void setLabelVisibilityMode(int i) {
        if (this.f != i) {
            this.f = i;
            if (this.m != null) {
                setChecked(this.m.isChecked());
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    @Nullable
    public MenuItemImpl getItemData() {
        return this.m;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(@Nullable CharSequence charSequence) {
        this.j.setText(charSequence);
        this.k.setText(charSequence);
        MenuItemImpl menuItemImpl = this.m;
        if (menuItemImpl == null || TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(charSequence);
        }
        MenuItemImpl menuItemImpl2 = this.m;
        if (menuItemImpl2 != null && !TextUtils.isEmpty(menuItemImpl2.getTooltipText())) {
            charSequence = this.m.getTooltipText();
        }
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, charSequence);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        refreshDrawableState();
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        TextView textView = this.k;
        textView.setPivotX(textView.getWidth() / 2);
        TextView textView2 = this.k;
        textView2.setPivotY(textView2.getBaseline());
        TextView textView3 = this.j;
        textView3.setPivotX(textView3.getWidth() / 2);
        TextView textView4 = this.j;
        textView4.setPivotY(textView4.getBaseline());
        switch (this.f) {
            case -1:
                if (!this.g) {
                    ViewGroup viewGroup = this.i;
                    a(viewGroup, ((Integer) viewGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    if (!z) {
                        a(this.h, this.b, 49);
                        TextView textView5 = this.k;
                        float f = this.e;
                        a(textView5, f, f, 4);
                        a(this.j, 1.0f, 1.0f, 0);
                        break;
                    } else {
                        a(this.h, (int) (this.b + this.c), 49);
                        a(this.k, 1.0f, 1.0f, 0);
                        TextView textView6 = this.j;
                        float f2 = this.d;
                        a(textView6, f2, f2, 4);
                        break;
                    }
                } else {
                    if (z) {
                        a(this.h, this.b, 49);
                        ViewGroup viewGroup2 = this.i;
                        a(viewGroup2, ((Integer) viewGroup2.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                        this.k.setVisibility(0);
                    } else {
                        a(this.h, this.b, 17);
                        a(this.i, 0);
                        this.k.setVisibility(4);
                    }
                    this.j.setVisibility(4);
                    break;
                }
            case 0:
                if (z) {
                    a(this.h, this.b, 49);
                    ViewGroup viewGroup3 = this.i;
                    a(viewGroup3, ((Integer) viewGroup3.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    this.k.setVisibility(0);
                } else {
                    a(this.h, this.b, 17);
                    a(this.i, 0);
                    this.k.setVisibility(4);
                }
                this.j.setVisibility(4);
                break;
            case 1:
                ViewGroup viewGroup4 = this.i;
                a(viewGroup4, ((Integer) viewGroup4.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                if (!z) {
                    a(this.h, this.b, 49);
                    TextView textView7 = this.k;
                    float f3 = this.e;
                    a(textView7, f3, f3, 4);
                    a(this.j, 1.0f, 1.0f, 0);
                    break;
                } else {
                    a(this.h, (int) (this.b + this.c), 49);
                    a(this.k, 1.0f, 1.0f, 0);
                    TextView textView8 = this.j;
                    float f4 = this.d;
                    a(textView8, f4, f4, 4);
                    break;
                }
            case 2:
                a(this.h, this.b, 17);
                this.k.setVisibility(8);
                this.j.setVisibility(8);
                break;
        }
        refreshDrawableState();
        setSelected(z);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        BadgeDrawable badgeDrawable = this.q;
        if (badgeDrawable != null && badgeDrawable.isVisible()) {
            CharSequence title = this.m.getTitle();
            if (!TextUtils.isEmpty(this.m.getContentDescription())) {
                title = this.m.getContentDescription();
            }
            accessibilityNodeInfo.setContentDescription(((Object) title) + ", " + ((Object) this.q.getContentDescription()));
        }
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            wrap.setClickable(false);
            wrap.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        wrap.setRoleDescription(getResources().getString(R.string.item_view_role_description));
    }

    private int getItemVisiblePosition() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        int indexOfChild = viewGroup.indexOfChild(this);
        int i = 0;
        for (int i2 = 0; i2 < indexOfChild; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof NavigationBarItemView) && childAt.getVisibility() == 0) {
                i++;
            }
        }
        return i;
    }

    private static void a(@NonNull View view, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        layoutParams.gravity = i2;
        view.setLayoutParams(layoutParams);
    }

    private static void a(@NonNull View view, float f, float f2, int i) {
        view.setScaleX(f);
        view.setScaleY(f2);
        view.setVisibility(i);
    }

    private static void a(@NonNull View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    @Override // android.view.View, androidx.appcompat.view.menu.MenuView.ItemView
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.j.setEnabled(z);
        this.k.setEnabled(z);
        this.h.setEnabled(z);
        if (z) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        } else {
            ViewCompat.setPointerIcon(this, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @NonNull
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.m;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.m.isChecked()) {
            mergeDrawableStates(onCreateDrawableState, a);
        }
        return onCreateDrawableState;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(@Nullable Drawable drawable) {
        if (drawable != this.o) {
            this.o = drawable;
            if (drawable != null) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = DrawableCompat.wrap(drawable).mutate();
                this.p = drawable;
                ColorStateList colorStateList = this.n;
                if (colorStateList != null) {
                    DrawableCompat.setTintList(this.p, colorStateList);
                }
            }
            this.h.setImageDrawable(drawable);
        }
    }

    public void setIconTintList(@Nullable ColorStateList colorStateList) {
        Drawable drawable;
        this.n = colorStateList;
        if (this.m != null && (drawable = this.p) != null) {
            DrawableCompat.setTintList(drawable, this.n);
            this.p.invalidateSelf();
        }
    }

    public void setIconSize(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.h.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.h.setLayoutParams(layoutParams);
    }

    public void setTextAppearanceInactive(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.j, i);
        a(this.j.getTextSize(), this.k.getTextSize());
    }

    public void setTextAppearanceActive(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.k, i);
        a(this.j.getTextSize(), this.k.getTextSize());
    }

    public void setTextColor(@Nullable ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.j.setTextColor(colorStateList);
            this.k.setTextColor(colorStateList);
        }
    }

    private void a(float f, float f2) {
        this.c = f - f2;
        this.d = (f2 * 1.0f) / f;
        this.e = (f * 1.0f) / f2;
    }

    public void setItemBackground(int i) {
        setItemBackground(i == 0 ? null : ContextCompat.getDrawable(getContext(), i));
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        if (!(drawable == null || drawable.getConstantState() == null)) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        ViewCompat.setBackground(this, drawable);
    }

    public void setBadge(@NonNull BadgeDrawable badgeDrawable) {
        this.q = badgeDrawable;
        ImageView imageView = this.h;
        if (imageView != null) {
            b(imageView);
        }
    }

    @Nullable
    public BadgeDrawable getBadge() {
        return this.q;
    }

    public void a() {
        c(this.h);
    }

    private boolean b() {
        return this.q != null;
    }

    public void a(View view) {
        if (b()) {
            BadgeUtils.setBadgeDrawableBounds(this.q, view, d(view));
        }
    }

    private void b(@Nullable View view) {
        if (b() && view != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.q, view, d(view));
        }
    }

    private void c(@Nullable View view) {
        if (b()) {
            if (view != null) {
                setClipChildren(true);
                setClipToPadding(true);
                BadgeUtils.detachBadgeDrawable(this.q, view);
            }
            this.q = null;
        }
    }

    @Nullable
    private FrameLayout d(View view) {
        if (view != this.h || !BadgeUtils.USE_COMPAT_PARENT) {
            return null;
        }
        return (FrameLayout) this.h.getParent();
    }

    private int getSuggestedIconWidth() {
        BadgeDrawable badgeDrawable = this.q;
        int minimumWidth = badgeDrawable == null ? 0 : badgeDrawable.getMinimumWidth() - this.q.getHorizontalOffset();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.h.getLayoutParams();
        return Math.max(minimumWidth, layoutParams.leftMargin) + this.h.getMeasuredWidth() + Math.max(minimumWidth, layoutParams.rightMargin);
    }

    private int getSuggestedIconHeight() {
        BadgeDrawable badgeDrawable = this.q;
        int minimumHeight = badgeDrawable != null ? badgeDrawable.getMinimumHeight() / 2 : 0;
        return Math.max(minimumHeight, ((FrameLayout.LayoutParams) this.h.getLayoutParams()).topMargin) + this.h.getMeasuredWidth() + minimumHeight;
    }

    @DrawableRes
    protected int getItemBackgroundResId() {
        return R.drawable.mtrl_navigation_bar_item_background;
    }

    @DimenRes
    protected int getItemDefaultMarginResId() {
        return R.dimen.mtrl_navigation_bar_item_default_margin;
    }
}
