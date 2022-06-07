package com.google.android.material.navigation;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import java.util.HashSet;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    private static final int[] a = {16842912};
    private static final int[] b = {-16842910};
    private int g;
    @Nullable
    private NavigationBarItemView[] h;
    @Nullable
    private ColorStateList k;
    @Dimension
    private int l;
    private ColorStateList m;
    @StyleRes
    private int o;
    @StyleRes
    private int p;
    private Drawable q;
    private int r;
    private NavigationBarPresenter t;
    private MenuBuilder u;
    private final Pools.Pool<NavigationBarItemView> e = new Pools.SynchronizedPool(5);
    @NonNull
    private final SparseArray<View.OnTouchListener> f = new SparseArray<>(5);
    private int i = 0;
    private int j = 0;
    @NonNull
    private SparseArray<BadgeDrawable> s = new SparseArray<>(5);
    @Nullable
    private final ColorStateList n = createDefaultColorStateList(16842808);
    @NonNull
    private final TransitionSet c = new AutoTransition();
    @NonNull
    private final View.OnClickListener d = new View.OnClickListener() { // from class: com.google.android.material.navigation.NavigationBarMenuView.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            MenuItemImpl itemData = ((NavigationBarItemView) view).getItemData();
            if (!NavigationBarMenuView.this.u.performItemAction(itemData, NavigationBarMenuView.this.t, 0)) {
                itemData.setChecked(true);
            }
        }
    };

    private boolean d(int i) {
        return i != -1;
    }

    @NonNull
    protected abstract NavigationBarItemView createNavigationBarItemView(@NonNull Context context);

    @Override // androidx.appcompat.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    protected boolean isShifting(int i, int i2) {
        if (i == -1) {
            if (i2 > 3) {
                return true;
            }
        } else if (i == 0) {
            return true;
        }
        return false;
    }

    public NavigationBarMenuView(@NonNull Context context) {
        super(context);
        this.c.setOrdering(0);
        this.c.setDuration(115L);
        this.c.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
        this.c.addTransition(new TextScale());
        ViewCompat.setImportantForAccessibility(this, 1);
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public void initialize(@NonNull MenuBuilder menuBuilder) {
        this.u = menuBuilder;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.u.getVisibleItems().size(), false, 1));
    }

    public void setIconTintList(@Nullable ColorStateList colorStateList) {
        this.k = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setIconTintList(colorStateList);
            }
        }
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.k;
    }

    public void setItemIconSize(@Dimension int i) {
        this.l = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setIconSize(i);
            }
        }
    }

    @Dimension
    public int getItemIconSize() {
        return this.l;
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.m = colorStateList;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setTextColor(colorStateList);
            }
        }
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.m;
    }

    public void setItemTextAppearanceInactive(@StyleRes int i) {
        this.o = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setTextAppearanceInactive(i);
                ColorStateList colorStateList = this.m;
                if (colorStateList != null) {
                    navigationBarItemView.setTextColor(colorStateList);
                }
            }
        }
    }

    @StyleRes
    public int getItemTextAppearanceInactive() {
        return this.o;
    }

    public void setItemTextAppearanceActive(@StyleRes int i) {
        this.p = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setTextAppearanceActive(i);
                ColorStateList colorStateList = this.m;
                if (colorStateList != null) {
                    navigationBarItemView.setTextColor(colorStateList);
                }
            }
        }
    }

    @StyleRes
    public int getItemTextAppearanceActive() {
        return this.p;
    }

    public void setItemBackgroundRes(int i) {
        this.r = i;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setItemBackground(i);
            }
        }
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.r;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.q = drawable;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setItemBackground(drawable);
            }
        }
    }

    @Nullable
    public Drawable getItemBackground() {
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr == null || navigationBarItemViewArr.length <= 0) {
            return this.q;
        }
        return navigationBarItemViewArr[0].getBackground();
    }

    public void setLabelVisibilityMode(int i) {
        this.g = i;
    }

    public int getLabelVisibilityMode() {
        return this.g;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void setItemOnTouchListener(int i, @Nullable View.OnTouchListener onTouchListener) {
        if (onTouchListener == null) {
            this.f.remove(i);
        } else {
            this.f.put(i, onTouchListener);
        }
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView.getItemData().getItemId() == i) {
                    navigationBarItemView.setOnTouchListener(onTouchListener);
                }
            }
        }
    }

    @Nullable
    public ColorStateList createDefaultColorStateList(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{b, a, EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(b, defaultColor), i2, defaultColor});
    }

    public void setPresenter(@NonNull NavigationBarPresenter navigationBarPresenter) {
        this.t = navigationBarPresenter;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void buildMenuView() {
        removeAllViews();
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                if (navigationBarItemView != null) {
                    this.e.release(navigationBarItemView);
                    navigationBarItemView.a();
                }
            }
        }
        if (this.u.size() == 0) {
            this.i = 0;
            this.j = 0;
            this.h = null;
            return;
        }
        a();
        this.h = new NavigationBarItemView[this.u.size()];
        boolean isShifting = isShifting(this.g, this.u.getVisibleItems().size());
        for (int i = 0; i < this.u.size(); i++) {
            this.t.setUpdateSuspended(true);
            this.u.getItem(i).setCheckable(true);
            this.t.setUpdateSuspended(false);
            NavigationBarItemView newItem = getNewItem();
            this.h[i] = newItem;
            newItem.setIconTintList(this.k);
            newItem.setIconSize(this.l);
            newItem.setTextColor(this.n);
            newItem.setTextAppearanceInactive(this.o);
            newItem.setTextAppearanceActive(this.p);
            newItem.setTextColor(this.m);
            Drawable drawable = this.q;
            if (drawable != null) {
                newItem.setItemBackground(drawable);
            } else {
                newItem.setItemBackground(this.r);
            }
            newItem.setShifting(isShifting);
            newItem.setLabelVisibilityMode(this.g);
            MenuItemImpl menuItemImpl = (MenuItemImpl) this.u.getItem(i);
            newItem.initialize(menuItemImpl, 0);
            newItem.setItemPosition(i);
            int itemId = menuItemImpl.getItemId();
            newItem.setOnTouchListener(this.f.get(itemId));
            newItem.setOnClickListener(this.d);
            int i2 = this.i;
            if (i2 != 0 && itemId == i2) {
                this.j = i;
            }
            setBadgeIfNeeded(newItem);
            addView(newItem);
        }
        this.j = Math.min(this.u.size() - 1, this.j);
        this.u.getItem(this.j).setChecked(true);
    }

    public void updateMenuView() {
        MenuBuilder menuBuilder = this.u;
        if (!(menuBuilder == null || this.h == null)) {
            int size = menuBuilder.size();
            if (size != this.h.length) {
                buildMenuView();
                return;
            }
            int i = this.i;
            for (int i2 = 0; i2 < size; i2++) {
                MenuItem item = this.u.getItem(i2);
                if (item.isChecked()) {
                    this.i = item.getItemId();
                    this.j = i2;
                }
            }
            if (i != this.i) {
                TransitionManager.beginDelayedTransition(this, this.c);
            }
            boolean isShifting = isShifting(this.g, this.u.getVisibleItems().size());
            for (int i3 = 0; i3 < size; i3++) {
                this.t.setUpdateSuspended(true);
                this.h[i3].setLabelVisibilityMode(this.g);
                this.h[i3].setShifting(isShifting);
                this.h[i3].initialize((MenuItemImpl) this.u.getItem(i3), 0);
                this.t.setUpdateSuspended(false);
            }
        }
    }

    private NavigationBarItemView getNewItem() {
        NavigationBarItemView acquire = this.e.acquire();
        return acquire == null ? createNavigationBarItemView(getContext()) : acquire;
    }

    public int getSelectedItemId() {
        return this.i;
    }

    public void a(int i) {
        int size = this.u.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = this.u.getItem(i2);
            if (i == item.getItemId()) {
                this.i = i;
                this.j = i2;
                item.setChecked(true);
                return;
            }
        }
    }

    public SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.s;
    }

    public void setBadgeDrawables(SparseArray<BadgeDrawable> sparseArray) {
        this.s = sparseArray;
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr != null) {
            for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                navigationBarItemView.setBadge(sparseArray.get(navigationBarItemView.getId()));
            }
        }
    }

    @Nullable
    public BadgeDrawable getBadge(int i) {
        return this.s.get(i);
    }

    public BadgeDrawable b(int i) {
        e(i);
        BadgeDrawable badgeDrawable = this.s.get(i);
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(getContext());
            this.s.put(i, badgeDrawable);
        }
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView != null) {
            findItemView.setBadge(badgeDrawable);
        }
        return badgeDrawable;
    }

    public void c(int i) {
        e(i);
        BadgeDrawable badgeDrawable = this.s.get(i);
        NavigationBarItemView findItemView = findItemView(i);
        if (findItemView != null) {
            findItemView.a();
        }
        if (badgeDrawable != null) {
            this.s.remove(i);
        }
    }

    private void setBadgeIfNeeded(@NonNull NavigationBarItemView navigationBarItemView) {
        BadgeDrawable badgeDrawable;
        int id = navigationBarItemView.getId();
        if (d(id) && (badgeDrawable = this.s.get(id)) != null) {
            navigationBarItemView.setBadge(badgeDrawable);
        }
    }

    private void a() {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.u.size(); i++) {
            hashSet.add(Integer.valueOf(this.u.getItem(i).getItemId()));
        }
        for (int i2 = 0; i2 < this.s.size(); i2++) {
            int keyAt = this.s.keyAt(i2);
            if (!hashSet.contains(Integer.valueOf(keyAt))) {
                this.s.delete(keyAt);
            }
        }
    }

    @Nullable
    public NavigationBarItemView findItemView(int i) {
        e(i);
        NavigationBarItemView[] navigationBarItemViewArr = this.h;
        if (navigationBarItemViewArr == null) {
            return null;
        }
        for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
            if (navigationBarItemView.getId() == i) {
                return navigationBarItemView;
            }
        }
        return null;
    }

    protected int getSelectedItemPosition() {
        return this.j;
    }

    @Nullable
    protected MenuBuilder getMenu() {
        return this.u;
    }

    private void e(int i) {
        if (!d(i)) {
            throw new IllegalArgumentException(i + " is not a valid view id");
        }
    }
}
