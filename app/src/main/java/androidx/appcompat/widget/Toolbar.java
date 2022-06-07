package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    private ColorStateList A;
    private ColorStateList B;
    private boolean C;
    private boolean D;
    private final ArrayList<View> E;
    private final ArrayList<View> F;
    private final int[] G;
    private final ActionMenuView.OnMenuItemClickListener H;
    private ToolbarWidgetWrapper I;
    private b J;
    private a K;
    private MenuPresenter.Callback L;
    private MenuBuilder.Callback M;
    private boolean N;
    private final Runnable O;
    ImageButton a;
    View b;
    int c;
    OnMenuItemClickListener d;
    private ActionMenuView e;
    private TextView f;
    private TextView g;
    private ImageButton h;
    private ImageView i;
    private Drawable j;
    private CharSequence k;
    private Context l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private o u;
    private int v;
    private int w;
    private int x;
    private CharSequence y;
    private CharSequence z;

    /* loaded from: classes.dex */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.x = 8388627;
        this.E = new ArrayList<>();
        this.F = new ArrayList<>();
        this.G = new int[2];
        this.H = new ActionMenuView.OnMenuItemClickListener() { // from class: androidx.appcompat.widget.Toolbar.1
            @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.d != null) {
                    return Toolbar.this.d.onMenuItemClick(menuItem);
                }
                return false;
            }
        };
        this.O = new Runnable() { // from class: androidx.appcompat.widget.Toolbar.2
            @Override // java.lang.Runnable
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.Toolbar, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.Toolbar, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        this.n = obtainStyledAttributes.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.o = obtainStyledAttributes.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.x = obtainStyledAttributes.getInteger(R.styleable.Toolbar_android_gravity, this.x);
        this.c = obtainStyledAttributes.getInteger(R.styleable.Toolbar_buttonGravity, 48);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        dimensionPixelOffset = obtainStyledAttributes.hasValue(R.styleable.Toolbar_titleMargins) ? obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, dimensionPixelOffset) : dimensionPixelOffset;
        this.t = dimensionPixelOffset;
        this.s = dimensionPixelOffset;
        this.r = dimensionPixelOffset;
        this.q = dimensionPixelOffset;
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.q = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.r = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.s = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.t = dimensionPixelOffset5;
        }
        this.p = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        int dimensionPixelOffset6 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        j();
        this.u.b(dimensionPixelSize, dimensionPixelSize2);
        if (!(dimensionPixelOffset6 == Integer.MIN_VALUE && dimensionPixelOffset7 == Integer.MIN_VALUE)) {
            this.u.a(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.v = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.w = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.j = obtainStyledAttributes.getDrawable(R.styleable.Toolbar_collapseIcon);
        this.k = obtainStyledAttributes.getText(R.styleable.Toolbar_collapseContentDescription);
        CharSequence text = obtainStyledAttributes.getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = obtainStyledAttributes.getText(R.styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.l = getContext();
        setPopupTheme(obtainStyledAttributes.getResourceId(R.styleable.Toolbar_popupTheme, 0));
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.Toolbar_navigationIcon);
        if (drawable != null) {
            setNavigationIcon(drawable);
        }
        CharSequence text3 = obtainStyledAttributes.getText(R.styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(R.styleable.Toolbar_logo);
        if (drawable2 != null) {
            setLogo(drawable2);
        }
        CharSequence text4 = obtainStyledAttributes.getText(R.styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(obtainStyledAttributes.getColorStateList(R.styleable.Toolbar_titleTextColor));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(obtainStyledAttributes.getColorStateList(R.styleable.Toolbar_subtitleTextColor));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Toolbar_menu)) {
            inflateMenu(obtainStyledAttributes.getResourceId(R.styleable.Toolbar_menu, 0));
        }
        obtainStyledAttributes.recycle();
    }

    public void setPopupTheme(@StyleRes int i) {
        if (this.m != i) {
            this.m = i;
            if (i == 0) {
                this.l = getContext();
            } else {
                this.l = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public int getPopupTheme() {
        return this.m;
    }

    public void setTitleMargin(int i, int i2, int i3, int i4) {
        this.q = i;
        this.s = i2;
        this.r = i3;
        this.t = i4;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.q;
    }

    public void setTitleMarginStart(int i) {
        this.q = i;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.s;
    }

    public void setTitleMarginTop(int i) {
        this.s = i;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.r;
    }

    public void setTitleMarginEnd(int i) {
        this.r = i;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.t;
    }

    public void setTitleMarginBottom(int i) {
        this.t = i;
        requestLayout();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i);
        }
        j();
        o oVar = this.u;
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        oVar.a(z);
    }

    public void setLogo(@DrawableRes int i) {
        setLogo(AppCompatResources.getDrawable(getContext(), i));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean canShowOverflowMenu() {
        ActionMenuView actionMenuView;
        return getVisibility() == 0 && (actionMenuView = this.e) != null && actionMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowing();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowMenuShowPending() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        ActionMenuView actionMenuView = this.e;
        return actionMenuView != null && actionMenuView.hideOverflowMenu();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setMenu(MenuBuilder menuBuilder, b bVar) {
        if (menuBuilder != null || this.e != null) {
            f();
            MenuBuilder peekMenu = this.e.peekMenu();
            if (peekMenu != menuBuilder) {
                if (peekMenu != null) {
                    peekMenu.removeMenuPresenter(this.J);
                    peekMenu.removeMenuPresenter(this.K);
                }
                if (this.K == null) {
                    this.K = new a();
                }
                bVar.b(true);
                if (menuBuilder != null) {
                    menuBuilder.addMenuPresenter(bVar, this.l);
                    menuBuilder.addMenuPresenter(this.K, this.l);
                } else {
                    bVar.initForMenu(this.l, null);
                    this.K.initForMenu(this.l, null);
                    bVar.updateMenuView(true);
                    this.K.updateMenuView(true);
                }
                this.e.setPopupTheme(this.m);
                this.e.setPresenter(bVar);
                this.J = bVar;
            }
        }
    }

    public void dismissPopupMenus() {
        ActionMenuView actionMenuView = this.e;
        if (actionMenuView != null) {
            actionMenuView.dismissPopupMenus();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isTitleTruncated() {
        Layout layout;
        TextView textView = this.f;
        if (textView == null || (layout = textView.getLayout()) == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            d();
            if (!d(this.i)) {
                a((View) this.i, true);
            }
        } else {
            ImageView imageView = this.i;
            if (imageView != null && d(imageView)) {
                removeView(this.i);
                this.F.remove(this.i);
            }
        }
        ImageView imageView2 = this.i;
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
        }
    }

    public Drawable getLogo() {
        ImageView imageView = this.i;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public void setLogoDescription(@StringRes int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            d();
        }
        ImageView imageView = this.i;
        if (imageView != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.i;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    private void d() {
        if (this.i == null) {
            this.i = new AppCompatImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        a aVar = this.K;
        return (aVar == null || aVar.b == null) ? false : true;
    }

    public void collapseActionView() {
        a aVar = this.K;
        MenuItemImpl menuItemImpl = aVar == null ? null : aVar.b;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.y;
    }

    public void setTitle(@StringRes int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.f == null) {
                Context context = getContext();
                this.f = new AppCompatTextView(context);
                this.f.setSingleLine();
                this.f.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.n;
                if (i != 0) {
                    this.f.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.A;
                if (colorStateList != null) {
                    this.f.setTextColor(colorStateList);
                }
            }
            if (!d(this.f)) {
                a((View) this.f, true);
            }
        } else {
            TextView textView = this.f;
            if (textView != null && d(textView)) {
                removeView(this.f);
                this.F.remove(this.f);
            }
        }
        TextView textView2 = this.f;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.y = charSequence;
    }

    public CharSequence getSubtitle() {
        return this.z;
    }

    public void setSubtitle(@StringRes int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.g == null) {
                Context context = getContext();
                this.g = new AppCompatTextView(context);
                this.g.setSingleLine();
                this.g.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.o;
                if (i != 0) {
                    this.g.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.B;
                if (colorStateList != null) {
                    this.g.setTextColor(colorStateList);
                }
            }
            if (!d(this.g)) {
                a((View) this.g, true);
            }
        } else {
            TextView textView = this.g;
            if (textView != null && d(textView)) {
                removeView(this.g);
                this.F.remove(this.g);
            }
        }
        TextView textView2 = this.g;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.z = charSequence;
    }

    public void setTitleTextAppearance(Context context, @StyleRes int i) {
        this.n = i;
        TextView textView = this.f;
        if (textView != null) {
            textView.setTextAppearance(context, i);
        }
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int i) {
        this.o = i;
        TextView textView = this.g;
        if (textView != null) {
            textView.setTextAppearance(context, i);
        }
    }

    public void setTitleTextColor(@ColorInt int i) {
        setTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.A = colorStateList;
        TextView textView = this.f;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void setSubtitleTextColor(@ColorInt int i) {
        setSubtitleTextColor(ColorStateList.valueOf(i));
    }

    public void setSubtitleTextColor(@NonNull ColorStateList colorStateList) {
        this.B = colorStateList;
        TextView textView = this.g;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.h;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public void setNavigationContentDescription(@StringRes int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            g();
        }
        ImageButton imageButton = this.h;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(@DrawableRes int i) {
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setNavigationIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            g();
            if (!d(this.h)) {
                a((View) this.h, true);
            }
        } else {
            ImageButton imageButton = this.h;
            if (imageButton != null && d(imageButton)) {
                removeView(this.h);
                this.F.remove(this.h);
            }
        }
        ImageButton imageButton2 = this.h;
        if (imageButton2 != null) {
            imageButton2.setImageDrawable(drawable);
        }
    }

    @Nullable
    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.h;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        g();
        this.h.setOnClickListener(onClickListener);
    }

    @Nullable
    public CharSequence getCollapseContentDescription() {
        ImageButton imageButton = this.a;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public void setCollapseContentDescription(@StringRes int i) {
        setCollapseContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setCollapseContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            a();
        }
        ImageButton imageButton = this.a;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    @Nullable
    public Drawable getCollapseIcon() {
        ImageButton imageButton = this.a;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public void setCollapseIcon(@DrawableRes int i) {
        setCollapseIcon(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setCollapseIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            a();
            this.a.setImageDrawable(drawable);
            return;
        }
        ImageButton imageButton = this.a;
        if (imageButton != null) {
            imageButton.setImageDrawable(this.j);
        }
    }

    public Menu getMenu() {
        e();
        return this.e.getMenu();
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        e();
        this.e.setOverflowIcon(drawable);
    }

    @Nullable
    public Drawable getOverflowIcon() {
        e();
        return this.e.getOverflowIcon();
    }

    private void e() {
        f();
        if (this.e.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder) this.e.getMenu();
            if (this.K == null) {
                this.K = new a();
            }
            this.e.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.K, this.l);
        }
    }

    private void f() {
        if (this.e == null) {
            this.e = new ActionMenuView(getContext());
            this.e.setPopupTheme(this.m);
            this.e.setOnMenuItemClickListener(this.H);
            this.e.setMenuCallbacks(this.L, this.M);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = 8388613 | (this.c & 112);
            this.e.setLayoutParams(generateDefaultLayoutParams);
            a((View) this.e, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(getContext());
    }

    public void inflateMenu(@MenuRes int i) {
        getMenuInflater().inflate(i, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.d = onMenuItemClickListener;
    }

    public void setContentInsetsRelative(int i, int i2) {
        j();
        this.u.a(i, i2);
    }

    public int getContentInsetStart() {
        o oVar = this.u;
        if (oVar != null) {
            return oVar.c();
        }
        return 0;
    }

    public int getContentInsetEnd() {
        o oVar = this.u;
        if (oVar != null) {
            return oVar.d();
        }
        return 0;
    }

    public void setContentInsetsAbsolute(int i, int i2) {
        j();
        this.u.b(i, i2);
    }

    public int getContentInsetLeft() {
        o oVar = this.u;
        if (oVar != null) {
            return oVar.a();
        }
        return 0;
    }

    public int getContentInsetRight() {
        o oVar = this.u;
        if (oVar != null) {
            return oVar.b();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i = this.v;
        return i != Integer.MIN_VALUE ? i : getContentInsetStart();
    }

    public void setContentInsetStartWithNavigation(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.v) {
            this.v = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        int i = this.w;
        return i != Integer.MIN_VALUE ? i : getContentInsetEnd();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.w) {
            this.w = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.v, 0));
        }
        return getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        boolean z;
        ActionMenuView actionMenuView = this.e;
        if (actionMenuView != null) {
            MenuBuilder peekMenu = actionMenuView.peekMenu();
            z = peekMenu != null && peekMenu.hasVisibleItems();
        } else {
            z = false;
        }
        if (z) {
            return Math.max(getContentInsetEnd(), Math.max(this.w, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    private void g() {
        if (this.h == null) {
            this.h = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = 8388611 | (this.c & 112);
            this.h.setLayoutParams(generateDefaultLayoutParams);
        }
    }

    void a() {
        if (this.a == null) {
            this.a = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.a.setImageDrawable(this.j);
            this.a.setContentDescription(this.k);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = 8388611 | (this.c & 112);
            generateDefaultLayoutParams.a = 2;
            this.a.setLayoutParams(generateDefaultLayoutParams);
            this.a.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.Toolbar.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    private void a(View view, boolean z) {
        LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams2)) {
            layoutParams = generateLayoutParams(layoutParams2);
        } else {
            layoutParams = (LayoutParams) layoutParams2;
        }
        layoutParams.a = 1;
        if (!z || this.b == null) {
            addView(view, layoutParams);
            return;
        }
        view.setLayoutParams(layoutParams);
        this.F.add(view);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        a aVar = this.K;
        if (!(aVar == null || aVar.b == null)) {
            savedState.a = this.K.b.getItemId();
        }
        savedState.b = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        ActionMenuView actionMenuView = this.e;
        MenuBuilder peekMenu = actionMenuView != null ? actionMenuView.peekMenu() : null;
        if (!(savedState.a == 0 || this.K == null || peekMenu == null || (findItem = peekMenu.findItem(savedState.a)) == null)) {
            findItem.expandActionView();
        }
        if (savedState.b) {
            h();
        }
    }

    private void h() {
        removeCallbacks(this.O);
        post(this.O);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.O);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.C = false;
        }
        if (!this.C) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.C = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.C = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.D = false;
        }
        if (!this.D) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.D = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.D = false;
        }
        return true;
    }

    private void a(View view, int i, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int a(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int max = Math.max(0, i5) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + max + i2, marginLayoutParams.width), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + max;
    }

    private boolean i() {
        if (!this.N) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (a(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        char c;
        char c2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int[] iArr = this.G;
        if (ViewUtils.isLayoutRtl(this)) {
            c2 = 1;
            c = 0;
        } else {
            c = 1;
            c2 = 0;
        }
        if (a(this.h)) {
            a(this.h, i, 0, i2, 0, this.p);
            i5 = this.h.getMeasuredWidth() + b(this.h);
            i4 = Math.max(0, this.h.getMeasuredHeight() + c(this.h));
            i3 = View.combineMeasuredStates(0, this.h.getMeasuredState());
        } else {
            i5 = 0;
            i4 = 0;
            i3 = 0;
        }
        if (a(this.a)) {
            a(this.a, i, 0, i2, 0, this.p);
            i5 = this.a.getMeasuredWidth() + b(this.a);
            i4 = Math.max(i4, this.a.getMeasuredHeight() + c(this.a));
            i3 = View.combineMeasuredStates(i3, this.a.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int max = 0 + Math.max(currentContentInsetStart, i5);
        iArr[c2] = Math.max(0, currentContentInsetStart - i5);
        if (a(this.e)) {
            a(this.e, i, max, i2, 0, this.p);
            i6 = this.e.getMeasuredWidth() + b(this.e);
            i4 = Math.max(i4, this.e.getMeasuredHeight() + c(this.e));
            i3 = View.combineMeasuredStates(i3, this.e.getMeasuredState());
        } else {
            i6 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int max2 = max + Math.max(currentContentInsetEnd, i6);
        iArr[c] = Math.max(0, currentContentInsetEnd - i6);
        if (a(this.b)) {
            max2 += a(this.b, i, max2, i2, 0, iArr);
            i4 = Math.max(i4, this.b.getMeasuredHeight() + c(this.b));
            i3 = View.combineMeasuredStates(i3, this.b.getMeasuredState());
        }
        if (a(this.i)) {
            max2 += a(this.i, i, max2, i2, 0, iArr);
            i4 = Math.max(i4, this.i.getMeasuredHeight() + c(this.i));
            i3 = View.combineMeasuredStates(i3, this.i.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (((LayoutParams) childAt.getLayoutParams()).a == 0 && a(childAt)) {
                max2 += a(childAt, i, max2, i2, 0, iArr);
                i4 = Math.max(i4, childAt.getMeasuredHeight() + c(childAt));
                i3 = View.combineMeasuredStates(i3, childAt.getMeasuredState());
            }
        }
        int i11 = this.s + this.t;
        int i12 = this.q + this.r;
        if (a(this.f)) {
            a(this.f, i, max2 + i12, i2, i11, iArr);
            i8 = this.f.getMeasuredWidth() + b(this.f);
            i7 = this.f.getMeasuredHeight() + c(this.f);
            i9 = View.combineMeasuredStates(i3, this.f.getMeasuredState());
        } else {
            i7 = 0;
            i9 = i3;
            i8 = 0;
        }
        if (a(this.g)) {
            i8 = Math.max(i8, a(this.g, i, max2 + i12, i2, i7 + i11, iArr));
            i7 += this.g.getMeasuredHeight() + c(this.g);
            i9 = View.combineMeasuredStates(i9, this.g.getMeasuredState());
        }
        int max3 = Math.max(i4, i7);
        int paddingLeft = max2 + i8 + getPaddingLeft() + getPaddingRight();
        int paddingTop = max3 + getPaddingTop() + getPaddingBottom();
        int resolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i, (-16777216) & i9);
        int resolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i2, i9 << 16);
        if (i()) {
            resolveSizeAndState2 = 0;
        }
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        TextView textView;
        TextView textView2;
        boolean z2;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        char c;
        int i18;
        int i19;
        boolean z3 = ViewCompat.getLayoutDirection(this) == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i20 = width - paddingRight;
        int[] iArr = this.G;
        iArr[1] = 0;
        iArr[0] = 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        int min = minimumHeight >= 0 ? Math.min(minimumHeight, i4 - i2) : 0;
        if (!a(this.h)) {
            i6 = paddingLeft;
            i5 = i20;
        } else if (z3) {
            i5 = b(this.h, i20, iArr, min);
            i6 = paddingLeft;
        } else {
            i6 = a(this.h, paddingLeft, iArr, min);
            i5 = i20;
        }
        if (a(this.a)) {
            if (z3) {
                i5 = b(this.a, i5, iArr, min);
            } else {
                i6 = a(this.a, i6, iArr, min);
            }
        }
        if (a(this.e)) {
            if (z3) {
                i6 = a(this.e, i6, iArr, min);
            } else {
                i5 = b(this.e, i5, iArr, min);
            }
        }
        int currentContentInsetLeft = getCurrentContentInsetLeft();
        int currentContentInsetRight = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft - i6);
        iArr[1] = Math.max(0, currentContentInsetRight - (i20 - i5));
        int max = Math.max(i6, currentContentInsetLeft);
        int min2 = Math.min(i5, i20 - currentContentInsetRight);
        if (a(this.b)) {
            if (z3) {
                min2 = b(this.b, min2, iArr, min);
            } else {
                max = a(this.b, max, iArr, min);
            }
        }
        if (a(this.i)) {
            if (z3) {
                min2 = b(this.i, min2, iArr, min);
            } else {
                max = a(this.i, max, iArr, min);
            }
        }
        boolean a2 = a(this.f);
        boolean a3 = a(this.g);
        if (a2) {
            LayoutParams layoutParams = (LayoutParams) this.f.getLayoutParams();
            i7 = paddingRight;
            i8 = layoutParams.topMargin + this.f.getMeasuredHeight() + layoutParams.bottomMargin + 0;
        } else {
            i7 = paddingRight;
            i8 = 0;
        }
        if (a3) {
            LayoutParams layoutParams2 = (LayoutParams) this.g.getLayoutParams();
            i9 = width;
            i8 += layoutParams2.topMargin + this.g.getMeasuredHeight() + layoutParams2.bottomMargin;
        } else {
            i9 = width;
        }
        if (a2 || a3) {
            if (a2) {
                textView = this.f;
            } else {
                textView = this.g;
            }
            if (a3) {
                textView2 = this.g;
            } else {
                textView2 = this.f;
            }
            LayoutParams layoutParams3 = (LayoutParams) textView.getLayoutParams();
            LayoutParams layoutParams4 = (LayoutParams) textView2.getLayoutParams();
            if ((!a2 || this.f.getMeasuredWidth() <= 0) && (!a3 || this.g.getMeasuredWidth() <= 0)) {
                i11 = paddingLeft;
                z2 = false;
            } else {
                i11 = paddingLeft;
                z2 = true;
            }
            int i21 = this.x & 112;
            i10 = min;
            if (i21 == 48) {
                i13 = max;
                i14 = getPaddingTop() + layoutParams3.topMargin + this.s;
            } else if (i21 != 80) {
                int i22 = (((height - paddingTop) - paddingBottom) - i8) / 2;
                i13 = max;
                if (i22 < layoutParams3.topMargin + this.s) {
                    i22 = layoutParams3.topMargin + this.s;
                } else {
                    int i23 = (((height - paddingBottom) - i8) - i22) - paddingTop;
                    if (i23 < layoutParams3.bottomMargin + this.t) {
                        i22 = Math.max(0, i22 - ((layoutParams4.bottomMargin + this.t) - i23));
                    }
                }
                i14 = paddingTop + i22;
            } else {
                i13 = max;
                i14 = (((height - paddingBottom) - layoutParams4.bottomMargin) - this.t) - i8;
            }
            if (z3) {
                if (z2) {
                    i17 = this.q;
                    c = 1;
                } else {
                    c = 1;
                    i17 = 0;
                }
                int i24 = i17 - iArr[c];
                min2 -= Math.max(0, i24);
                iArr[c] = Math.max(0, -i24);
                if (a2) {
                    int measuredWidth = min2 - this.f.getMeasuredWidth();
                    int measuredHeight = this.f.getMeasuredHeight() + i14;
                    this.f.layout(measuredWidth, i14, min2, measuredHeight);
                    i18 = measuredWidth - this.r;
                    i14 = measuredHeight + ((LayoutParams) this.f.getLayoutParams()).bottomMargin;
                } else {
                    i18 = min2;
                }
                if (a3) {
                    LayoutParams layoutParams5 = (LayoutParams) this.g.getLayoutParams();
                    int i25 = i14 + layoutParams5.topMargin;
                    this.g.layout(min2 - this.g.getMeasuredWidth(), i25, min2, this.g.getMeasuredHeight() + i25);
                    i19 = min2 - this.r;
                    int i26 = layoutParams5.bottomMargin;
                } else {
                    i19 = min2;
                }
                if (z2) {
                    min2 = Math.min(i18, i19);
                }
                max = i13;
                i12 = 0;
            } else {
                i12 = 0;
                int i27 = (z2 ? this.q : 0) - iArr[0];
                max = i13 + Math.max(0, i27);
                iArr[0] = Math.max(0, -i27);
                if (a2) {
                    int measuredWidth2 = this.f.getMeasuredWidth() + max;
                    int measuredHeight2 = this.f.getMeasuredHeight() + i14;
                    this.f.layout(max, i14, measuredWidth2, measuredHeight2);
                    i15 = measuredWidth2 + this.r;
                    i14 = measuredHeight2 + ((LayoutParams) this.f.getLayoutParams()).bottomMargin;
                } else {
                    i15 = max;
                }
                if (a3) {
                    LayoutParams layoutParams6 = (LayoutParams) this.g.getLayoutParams();
                    int i28 = i14 + layoutParams6.topMargin;
                    int measuredWidth3 = this.g.getMeasuredWidth() + max;
                    this.g.layout(max, i28, measuredWidth3, this.g.getMeasuredHeight() + i28);
                    i16 = measuredWidth3 + this.r;
                    int i29 = layoutParams6.bottomMargin;
                } else {
                    i16 = max;
                }
                if (z2) {
                    max = Math.max(i15, i16);
                }
            }
        } else {
            i11 = paddingLeft;
            i10 = min;
            i12 = 0;
        }
        a(this.E, 3);
        int size = this.E.size();
        for (int i30 = i12; i30 < size; i30++) {
            max = a(this.E.get(i30), max, iArr, i10);
        }
        a(this.E, 5);
        int size2 = this.E.size();
        for (int i31 = i12; i31 < size2; i31++) {
            min2 = b(this.E.get(i31), min2, iArr, i10);
        }
        a(this.E, 1);
        int a4 = a(this.E, iArr);
        int i32 = (i11 + (((i9 - i11) - i7) / 2)) - (a4 / 2);
        int i33 = a4 + i32;
        if (i32 >= max) {
            max = i33 > min2 ? i32 - (i33 - min2) : i32;
        }
        int size3 = this.E.size();
        while (i12 < size3) {
            max = a(this.E.get(i12), max, iArr, i10);
            i12++;
        }
        this.E.clear();
    }

    private int a(List<View> list, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int size = list.size();
        int i3 = i2;
        int i4 = 0;
        int i5 = i;
        int i6 = 0;
        while (i4 < size) {
            View view = list.get(i4);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int i7 = layoutParams.leftMargin - i5;
            int i8 = layoutParams.rightMargin - i3;
            int max = Math.max(0, i7);
            int max2 = Math.max(0, i8);
            int max3 = Math.max(0, -i7);
            int max4 = Math.max(0, -i8);
            i6 += max + view.getMeasuredWidth() + max2;
            i4++;
            i3 = max4;
            i5 = max3;
        }
        return i6;
    }

    private int a(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.leftMargin - iArr[0];
        int max = i + Math.max(0, i3);
        iArr[0] = Math.max(0, -i3);
        int a2 = a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max, a2, max + measuredWidth, view.getMeasuredHeight() + a2);
        return max + measuredWidth + layoutParams.rightMargin;
    }

    private int b(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.rightMargin - iArr[1];
        int max = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int a2 = a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max - measuredWidth, a2, max, view.getMeasuredHeight() + a2);
        return max - (measuredWidth + layoutParams.leftMargin);
    }

    private int a(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int a2 = a(layoutParams.gravity);
        if (a2 == 48) {
            return getPaddingTop() - i2;
        }
        if (a2 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int i3 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        if (i3 < layoutParams.topMargin) {
            i3 = layoutParams.topMargin;
        } else {
            int i4 = (((height - paddingBottom) - measuredHeight) - i3) - paddingTop;
            if (i4 < layoutParams.bottomMargin) {
                i3 = Math.max(0, i3 - (layoutParams.bottomMargin - i4));
            }
        }
        return paddingTop + i3;
    }

    private int a(int i) {
        int i2 = i & 112;
        return (i2 == 16 || i2 == 48 || i2 == 80) ? i2 : this.x & 112;
    }

    private void a(List<View> list, int i) {
        boolean z = ViewCompat.getLayoutDirection(this) == 1;
        int childCount = getChildCount();
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        list.clear();
        if (z) {
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                View childAt = getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.a == 0 && a(childAt) && b(layoutParams.gravity) == absoluteGravity) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt2 = getChildAt(i3);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.a == 0 && a(childAt2) && b(layoutParams2.gravity) == absoluteGravity) {
                list.add(childAt2);
            }
        }
    }

    private int b(int i) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, layoutDirection) & 7;
        return (absoluteGravity == 1 || absoluteGravity == 3 || absoluteGravity == 5) ? absoluteGravity : layoutDirection == 1 ? 5 : 3;
    }

    private boolean a(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int b(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart(marginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
    }

    private int c(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DecorToolbar getWrapper() {
        if (this.I == null) {
            this.I = new ToolbarWidgetWrapper(this, true);
        }
        return this.I;
    }

    void b() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (!(((LayoutParams) childAt.getLayoutParams()).a == 2 || childAt == this.e)) {
                removeViewAt(childCount);
                this.F.add(childAt);
            }
        }
    }

    void c() {
        for (int size = this.F.size() - 1; size >= 0; size--) {
            addView(this.F.get(size));
        }
        this.F.clear();
    }

    private boolean d(View view) {
        return view.getParent() == this || this.F.contains(view);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setCollapsible(boolean z) {
        this.N = z;
        requestLayout();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.L = callback;
        this.M = callback2;
        ActionMenuView actionMenuView = this.e;
        if (actionMenuView != null) {
            actionMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    private void j() {
        if (this.u == null) {
            this.u = new o();
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.TESTS})
    final TextView getTitleTextView() {
        return this.f;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.TESTS})
    final TextView getSubtitleTextView() {
        return this.g;
    }

    b getOuterActionMenuPresenter() {
        return this.J;
    }

    Context getPopupContext() {
        return this.l;
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends ActionBar.LayoutParams {
        int a;

        public LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.a = 0;
            this.gravity = i3;
        }

        public LayoutParams(int i) {
            this(-2, -1, i);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ActionBar.LayoutParams) layoutParams);
            this.a = 0;
            this.a = layoutParams.a;
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 0;
            a(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }

        void a(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    /* loaded from: classes.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.appcompat.widget.Toolbar.SavedState.1
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        boolean b;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
            this.b = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b ? 1 : 0);
        }
    }

    /* loaded from: classes.dex */
    public class a implements MenuPresenter {
        MenuBuilder a;
        MenuItemImpl b;

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        a() {
            Toolbar.this = r1;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.a;
            if (!(menuBuilder2 == null || (menuItemImpl = this.b) == null)) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.a = menuBuilder;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.b != null) {
                MenuBuilder menuBuilder = this.a;
                boolean z2 = false;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        } else if (this.a.getItem(i) == this.b) {
                            z2 = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (!z2) {
                    collapseItemActionView(this.a, this.b);
                }
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            Toolbar.this.a();
            ViewParent parent = Toolbar.this.a.getParent();
            Toolbar toolbar = Toolbar.this;
            if (parent != toolbar) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar.a);
                }
                Toolbar toolbar2 = Toolbar.this;
                toolbar2.addView(toolbar2.a);
            }
            Toolbar.this.b = menuItemImpl.getActionView();
            this.b = menuItemImpl;
            ViewParent parent2 = Toolbar.this.b.getParent();
            Toolbar toolbar3 = Toolbar.this;
            if (parent2 != toolbar3) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar3.b);
                }
                LayoutParams generateDefaultLayoutParams = Toolbar.this.generateDefaultLayoutParams();
                generateDefaultLayoutParams.gravity = 8388611 | (Toolbar.this.c & 112);
                generateDefaultLayoutParams.a = 2;
                Toolbar.this.b.setLayoutParams(generateDefaultLayoutParams);
                Toolbar toolbar4 = Toolbar.this;
                toolbar4.addView(toolbar4.b);
            }
            Toolbar.this.b();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.b instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.b).onActionViewExpanded();
            }
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.b instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.b).onActionViewCollapsed();
            }
            Toolbar toolbar = Toolbar.this;
            toolbar.removeView(toolbar.b);
            Toolbar toolbar2 = Toolbar.this;
            toolbar2.removeView(toolbar2.a);
            Toolbar toolbar3 = Toolbar.this;
            toolbar3.b = null;
            toolbar3.c();
            this.b = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }
    }
}
