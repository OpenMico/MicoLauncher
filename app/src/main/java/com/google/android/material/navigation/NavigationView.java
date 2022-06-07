package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes2.dex */
public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int[] d = {16842912};
    private static final int[] e = {-16842910};
    private static final int f = R.style.Widget_Design_NavigationView;
    OnNavigationItemSelectedListener c;
    @NonNull
    private final NavigationMenu g;
    private final NavigationMenuPresenter h;
    private final int i;
    private final int[] j;
    private MenuInflater k;
    private ViewTreeObserver.OnGlobalLayoutListener l;

    /* loaded from: classes2.dex */
    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public NavigationView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.navigationViewStyle);
    }

    public NavigationView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, f), attributeSet, i);
        ColorStateList colorStateList;
        boolean z;
        int i2;
        this.h = new NavigationMenuPresenter();
        this.j = new int[2];
        Context context2 = getContext();
        this.g = new NavigationMenu(context2);
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.NavigationView, i, f, new int[0]);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_android_background)) {
            ViewCompat.setBackground(this, obtainTintedStyledAttributes.getDrawable(R.styleable.NavigationView_android_background));
        }
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, attributeSet, i, f).build();
            Drawable background = getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(build);
            if (background instanceof ColorDrawable) {
                materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) background).getColor()));
            }
            materialShapeDrawable.initializeElevationOverlay(context2);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_elevation)) {
            setElevation(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
        }
        setFitsSystemWindows(obtainTintedStyledAttributes.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.i = obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_itemIconTint)) {
            colorStateList = obtainTintedStyledAttributes.getColorStateList(R.styleable.NavigationView_itemIconTint);
        } else {
            colorStateList = a(16842808);
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            i2 = obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
            z = true;
        } else {
            z = false;
            i2 = 0;
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_itemIconSize)) {
            setItemIconSize(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationView_itemIconSize, 0));
        }
        ColorStateList colorStateList2 = obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_itemTextColor) ? obtainTintedStyledAttributes.getColorStateList(R.styleable.NavigationView_itemTextColor) : null;
        if (!z && colorStateList2 == null) {
            colorStateList2 = a(16842806);
        }
        Drawable drawable = obtainTintedStyledAttributes.getDrawable(R.styleable.NavigationView_itemBackground);
        if (drawable == null && a(obtainTintedStyledAttributes)) {
            drawable = b(obtainTintedStyledAttributes);
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_itemHorizontalPadding)) {
            this.h.setItemHorizontalPadding(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationView_itemHorizontalPadding, 0));
        }
        int dimensionPixelSize = obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationView_itemIconPadding, 0);
        setItemMaxLines(obtainTintedStyledAttributes.getInt(R.styleable.NavigationView_itemMaxLines, 1));
        this.g.setCallback(new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationView.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return NavigationView.this.c != null && NavigationView.this.c.onNavigationItemSelected(menuItem);
            }
        });
        this.h.setId(1);
        this.h.initForMenu(context2, this.g);
        this.h.setItemIconTintList(colorStateList);
        this.h.setOverScrollMode(getOverScrollMode());
        if (z) {
            this.h.setItemTextAppearance(i2);
        }
        this.h.setItemTextColor(colorStateList2);
        this.h.setItemBackground(drawable);
        this.h.setItemIconPadding(dimensionPixelSize);
        this.g.addMenuPresenter(this.h);
        addView((View) this.h.getMenuView(this));
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_menu)) {
            inflateMenu(obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationView_menu, 0));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationView_headerLayout)) {
            inflateHeaderView(obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationView_headerLayout, 0));
        }
        obtainTintedStyledAttributes.recycle();
        a();
    }

    @Override // android.view.View
    public void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
        NavigationMenuPresenter navigationMenuPresenter = this.h;
        if (navigationMenuPresenter != null) {
            navigationMenuPresenter.setOverScrollMode(i);
        }
    }

    private boolean a(@NonNull TintTypedArray tintTypedArray) {
        return tintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearance) || tintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearanceOverlay);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public void setElevation(float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(f2);
        }
        MaterialShapeUtils.setElevation(this, f2);
    }

    @NonNull
    private final Drawable b(@NonNull TintTypedArray tintTypedArray) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(getContext(), tintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearance, 0), tintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearanceOverlay, 0)).build());
        materialShapeDrawable.setFillColor(MaterialResources.getColorStateList(getContext(), tintTypedArray, R.styleable.NavigationView_itemShapeFillColor));
        return new InsetDrawable((Drawable) materialShapeDrawable, tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetStart, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetTop, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetEnd, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetBottom, 0));
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.menuState = new Bundle();
        this.g.savePresenterStates(savedState.menuState);
        return savedState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.g.restorePresenterStates(savedState.menuState);
    }

    public void setNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.c = onNavigationItemSelectedListener;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), this.i), 1073741824);
        } else if (mode == 0) {
            i = View.MeasureSpec.makeMeasureSpec(this.i, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected void onInsetsChanged(@NonNull WindowInsetsCompat windowInsetsCompat) {
        this.h.dispatchApplyWindowInsets(windowInsetsCompat);
    }

    public void inflateMenu(int i) {
        this.h.setUpdateSuspended(true);
        getMenuInflater().inflate(i, this.g);
        this.h.setUpdateSuspended(false);
        this.h.updateMenuView(false);
    }

    @NonNull
    public Menu getMenu() {
        return this.g;
    }

    public View inflateHeaderView(@LayoutRes int i) {
        return this.h.inflateHeaderView(i);
    }

    public void addHeaderView(@NonNull View view) {
        this.h.addHeaderView(view);
    }

    public void removeHeaderView(@NonNull View view) {
        this.h.removeHeaderView(view);
    }

    public int getHeaderCount() {
        return this.h.getHeaderCount();
    }

    public View getHeaderView(int i) {
        return this.h.getHeaderView(i);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.h.getItemTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.h.setItemIconTintList(colorStateList);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.h.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.h.setItemTextColor(colorStateList);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.h.getItemBackground();
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        setItemBackground(ContextCompat.getDrawable(getContext(), i));
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.h.setItemBackground(drawable);
    }

    @Dimension
    public int getItemHorizontalPadding() {
        return this.h.getItemHorizontalPadding();
    }

    public void setItemHorizontalPadding(@Dimension int i) {
        this.h.setItemHorizontalPadding(i);
    }

    public void setItemHorizontalPaddingResource(@DimenRes int i) {
        this.h.setItemHorizontalPadding(getResources().getDimensionPixelSize(i));
    }

    @Dimension
    public int getItemIconPadding() {
        return this.h.getItemIconPadding();
    }

    public void setItemIconPadding(@Dimension int i) {
        this.h.setItemIconPadding(i);
    }

    public void setItemIconPaddingResource(int i) {
        this.h.setItemIconPadding(getResources().getDimensionPixelSize(i));
    }

    public void setCheckedItem(@IdRes int i) {
        MenuItem findItem = this.g.findItem(i);
        if (findItem != null) {
            this.h.setCheckedItem((MenuItemImpl) findItem);
        }
    }

    public void setCheckedItem(@NonNull MenuItem menuItem) {
        MenuItem findItem = this.g.findItem(menuItem.getItemId());
        if (findItem != null) {
            this.h.setCheckedItem((MenuItemImpl) findItem);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }

    @Nullable
    public MenuItem getCheckedItem() {
        return this.h.getCheckedItem();
    }

    public void setItemTextAppearance(@StyleRes int i) {
        this.h.setItemTextAppearance(i);
    }

    public void setItemIconSize(@Dimension int i) {
        this.h.setItemIconSize(i);
    }

    public void setItemMaxLines(int i) {
        this.h.setItemMaxLines(i);
    }

    public int getItemMaxLines() {
        return this.h.getItemMaxLines();
    }

    private MenuInflater getMenuInflater() {
        if (this.k == null) {
            this.k = new SupportMenuInflater(getContext());
        }
        return this.k;
    }

    @Nullable
    private ColorStateList a(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{e, d, EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(e, defaultColor), i2, defaultColor});
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(this.l);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.l);
        }
    }

    private void a() {
        this.l = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.navigation.NavigationView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                NavigationView navigationView = NavigationView.this;
                navigationView.getLocationOnScreen(navigationView.j);
                boolean z = true;
                boolean z2 = NavigationView.this.j[1] == 0;
                NavigationView.this.h.setBehindStatusBar(z2);
                NavigationView.this.setDrawTopInsetForeground(z2);
                Activity activity = ContextUtils.getActivity(NavigationView.this.getContext());
                if (activity != null && Build.VERSION.SDK_INT >= 21) {
                    boolean z3 = activity.findViewById(16908290).getHeight() == NavigationView.this.getHeight();
                    boolean z4 = Color.alpha(activity.getWindow().getNavigationBarColor()) != 0;
                    NavigationView navigationView2 = NavigationView.this;
                    if (!z3 || !z4) {
                        z = false;
                    }
                    navigationView2.setDrawBottomInsetForeground(z);
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.l);
    }

    /* loaded from: classes2.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.navigation.NavigationView.SavedState.1
            @NonNull
            /* renamed from: a */
            public SavedState createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Nullable
            /* renamed from: a */
            public SavedState createFromParcel(@NonNull Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        @Nullable
        public Bundle menuState;

        public SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuState = parcel.readBundle(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.menuState);
        }
    }
}
