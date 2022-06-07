package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class NavigationBarView extends FrameLayout {
    public static final int LABEL_VISIBILITY_AUTO = -1;
    public static final int LABEL_VISIBILITY_LABELED = 1;
    public static final int LABEL_VISIBILITY_SELECTED = 0;
    public static final int LABEL_VISIBILITY_UNLABELED = 2;
    @NonNull
    private final NavigationBarMenu a;
    @NonNull
    private final NavigationBarMenuView b;
    @NonNull
    private final NavigationBarPresenter c = new NavigationBarPresenter();
    @Nullable
    private ColorStateList d;
    private MenuInflater e;
    private OnItemSelectedListener f;
    private OnItemReselectedListener g;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface LabelVisibility {
    }

    /* loaded from: classes2.dex */
    public interface OnItemReselectedListener {
        void onNavigationItemReselected(@NonNull MenuItem menuItem);
    }

    /* loaded from: classes2.dex */
    public interface OnItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected abstract NavigationBarMenuView createNavigationBarMenuView(@NonNull Context context);

    public abstract int getMaxItemCount();

    public NavigationBarView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        Context context2 = getContext();
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.NavigationBarView, i, i2, R.styleable.NavigationBarView_itemTextAppearanceInactive, R.styleable.NavigationBarView_itemTextAppearanceActive);
        this.a = new NavigationBarMenu(context2, getClass(), getMaxItemCount());
        this.b = createNavigationBarMenuView(context2);
        this.c.setMenuView(this.b);
        this.c.setId(1);
        this.b.setPresenter(this.c);
        this.a.addMenuPresenter(this.c);
        this.c.initForMenu(getContext(), this.a);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_itemIconTint)) {
            this.b.setIconTintList(obtainTintedStyledAttributes.getColorStateList(R.styleable.NavigationBarView_itemIconTint));
        } else {
            NavigationBarMenuView navigationBarMenuView = this.b;
            navigationBarMenuView.setIconTintList(navigationBarMenuView.createDefaultColorStateList(16842808));
        }
        setItemIconSize(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationBarView_itemIconSize, getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_bar_item_default_icon_size)));
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_itemTextAppearanceInactive)) {
            setItemTextAppearanceInactive(obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationBarView_itemTextAppearanceInactive, 0));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_itemTextAppearanceActive)) {
            setItemTextAppearanceActive(obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationBarView_itemTextAppearanceActive, 0));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_itemTextColor)) {
            setItemTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.NavigationBarView_itemTextColor));
        }
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            ViewCompat.setBackground(this, a(context2));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_elevation)) {
            setElevation(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.NavigationBarView_elevation, 0));
        }
        DrawableCompat.setTintList(getBackground().mutate(), MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.NavigationBarView_backgroundTint));
        setLabelVisibilityMode(obtainTintedStyledAttributes.getInteger(R.styleable.NavigationBarView_labelVisibilityMode, -1));
        int resourceId = obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationBarView_itemBackground, 0);
        if (resourceId != 0) {
            this.b.setItemBackgroundRes(resourceId);
        } else {
            setItemRippleColor(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.NavigationBarView_itemRippleColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.NavigationBarView_menu)) {
            inflateMenu(obtainTintedStyledAttributes.getResourceId(R.styleable.NavigationBarView_menu, 0));
        }
        obtainTintedStyledAttributes.recycle();
        addView(this.b);
        this.a.setCallback(new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationBarView.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
                if (NavigationBarView.this.g == null || menuItem.getItemId() != NavigationBarView.this.getSelectedItemId()) {
                    return NavigationBarView.this.f != null && !NavigationBarView.this.f.onNavigationItemSelected(menuItem);
                }
                NavigationBarView.this.g.onNavigationItemReselected(menuItem);
                return true;
            }
        });
        a();
    }

    private void a() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.navigation.NavigationBarView.2
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            @NonNull
            public WindowInsetsCompat onApplyWindowInsets(View view, @NonNull WindowInsetsCompat windowInsetsCompat, @NonNull ViewUtils.RelativePadding relativePadding) {
                relativePadding.bottom += windowInsetsCompat.getSystemWindowInsetBottom();
                boolean z = true;
                if (ViewCompat.getLayoutDirection(view) != 1) {
                    z = false;
                }
                int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                relativePadding.start += z ? systemWindowInsetRight : systemWindowInsetLeft;
                int i = relativePadding.end;
                if (!z) {
                    systemWindowInsetLeft = systemWindowInsetRight;
                }
                relativePadding.end = i + systemWindowInsetLeft;
                relativePadding.applyToView(view);
                return windowInsetsCompat;
            }
        });
    }

    @NonNull
    private MaterialShapeDrawable a(Context context) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) background).getColor()));
        }
        materialShapeDrawable.initializeElevationOverlay(context);
        return materialShapeDrawable;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public void setElevation(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(f);
        }
        MaterialShapeUtils.setElevation(this, f);
    }

    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener onItemSelectedListener) {
        this.f = onItemSelectedListener;
    }

    public void setOnItemReselectedListener(@Nullable OnItemReselectedListener onItemReselectedListener) {
        this.g = onItemReselectedListener;
    }

    @NonNull
    public Menu getMenu() {
        return this.a;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public MenuView getMenuView() {
        return this.b;
    }

    public void inflateMenu(int i) {
        this.c.setUpdateSuspended(true);
        getMenuInflater().inflate(i, this.a);
        this.c.setUpdateSuspended(false);
        this.c.updateMenuView(true);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.b.getIconTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.b.setIconTintList(colorStateList);
    }

    public void setItemIconSize(@Dimension int i) {
        this.b.setItemIconSize(i);
    }

    public void setItemIconSizeRes(@DimenRes int i) {
        setItemIconSize(getResources().getDimensionPixelSize(i));
    }

    @Dimension
    public int getItemIconSize() {
        return this.b.getItemIconSize();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.b.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.b.setItemTextColor(colorStateList);
    }

    @DrawableRes
    @Deprecated
    public int getItemBackgroundResource() {
        return this.b.getItemBackgroundRes();
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        this.b.setItemBackgroundRes(i);
        this.d = null;
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.b.getItemBackground();
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.b.setItemBackground(drawable);
        this.d = null;
    }

    @Nullable
    public ColorStateList getItemRippleColor() {
        return this.d;
    }

    public void setItemRippleColor(@Nullable ColorStateList colorStateList) {
        if (this.d != colorStateList) {
            this.d = colorStateList;
            if (colorStateList == null) {
                this.b.setItemBackground(null);
                return;
            }
            ColorStateList convertToRippleDrawableColor = RippleUtils.convertToRippleDrawableColor(colorStateList);
            if (Build.VERSION.SDK_INT >= 21) {
                this.b.setItemBackground(new RippleDrawable(convertToRippleDrawableColor, null, null));
                return;
            }
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(1.0E-5f);
            Drawable wrap = DrawableCompat.wrap(gradientDrawable);
            DrawableCompat.setTintList(wrap, convertToRippleDrawableColor);
            this.b.setItemBackground(wrap);
        } else if (colorStateList == null && this.b.getItemBackground() != null) {
            this.b.setItemBackground(null);
        }
    }

    @IdRes
    public int getSelectedItemId() {
        return this.b.getSelectedItemId();
    }

    public void setSelectedItemId(@IdRes int i) {
        MenuItem findItem = this.a.findItem(i);
        if (findItem != null && !this.a.performItemAction(findItem, this.c, 0)) {
            findItem.setChecked(true);
        }
    }

    public void setLabelVisibilityMode(int i) {
        if (this.b.getLabelVisibilityMode() != i) {
            this.b.setLabelVisibilityMode(i);
            this.c.updateMenuView(false);
        }
    }

    public int getLabelVisibilityMode() {
        return this.b.getLabelVisibilityMode();
    }

    public void setItemTextAppearanceInactive(@StyleRes int i) {
        this.b.setItemTextAppearanceInactive(i);
    }

    @StyleRes
    public int getItemTextAppearanceInactive() {
        return this.b.getItemTextAppearanceInactive();
    }

    public void setItemTextAppearanceActive(@StyleRes int i) {
        this.b.setItemTextAppearanceActive(i);
    }

    @StyleRes
    public int getItemTextAppearanceActive() {
        return this.b.getItemTextAppearanceActive();
    }

    public void setItemOnTouchListener(int i, @Nullable View.OnTouchListener onTouchListener) {
        this.b.setItemOnTouchListener(i, onTouchListener);
    }

    @Nullable
    public BadgeDrawable getBadge(int i) {
        return this.b.getBadge(i);
    }

    @NonNull
    public BadgeDrawable getOrCreateBadge(int i) {
        return this.b.b(i);
    }

    public void removeBadge(int i) {
        this.b.c(i);
    }

    private MenuInflater getMenuInflater() {
        if (this.e == null) {
            this.e = new SupportMenuInflater(getContext());
        }
        return this.e;
    }

    @NonNull
    protected NavigationBarPresenter getPresenter() {
        return this.c;
    }

    @Override // android.view.View
    @NonNull
    protected Parcelable onSaveInstanceState() {
        a aVar = new a(super.onSaveInstanceState());
        aVar.a = new Bundle();
        this.a.savePresenterStates(aVar.a);
        return aVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (!(parcelable instanceof a)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        a aVar = (a) parcelable;
        super.onRestoreInstanceState(aVar.getSuperState());
        this.a.restorePresenterStates(aVar.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a extends AbsSavedState {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.ClassLoaderCreator<a>() { // from class: com.google.android.material.navigation.NavigationBarView.a.1
            @NonNull
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new a(parcel, classLoader);
            }

            @Nullable
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel) {
                return new a(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public a[] newArray(int i) {
                return new a[i];
            }
        };
        @Nullable
        Bundle a;

        public a(Parcelable parcelable) {
            super(parcelable);
        }

        public a(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel, classLoader == null ? getClass().getClassLoader() : classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBundle(this.a);
        }

        private void a(@NonNull Parcel parcel, ClassLoader classLoader) {
            this.a = parcel.readBundle(classLoader);
        }
    }
}
