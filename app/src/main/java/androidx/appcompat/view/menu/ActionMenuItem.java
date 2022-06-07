package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ActionMenuItem implements SupportMenuItem {
    private final int a;
    private final int b;
    private final int c;
    private CharSequence d;
    private CharSequence e;
    private Intent f;
    private char g;
    private char i;
    private Drawable k;
    private Context l;
    private MenuItem.OnMenuItemClickListener m;
    private CharSequence n;
    private CharSequence o;
    private int h = 4096;
    private int j = 4096;
    private ColorStateList p = null;
    private PorterDuff.Mode q = null;
    private boolean r = false;
    private boolean s = false;
    private int t = 16;

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public boolean collapseActionView() {
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public boolean expandActionView() {
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public View getActionView() {
        return null;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return null;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public ActionProvider getSupportActionProvider() {
        return null;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public boolean requiresActionButton() {
        return true;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public boolean requiresOverflow() {
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public void setShowAsAction(int i) {
    }

    public ActionMenuItem(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.l = context;
        this.a = i2;
        this.b = i;
        this.c = i4;
        this.d = charSequence;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return this.i;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public int getAlphabeticModifiers() {
        return this.j;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return this.b;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return this.k;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return this.f;
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return this.a;
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return this.g;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public int getNumericModifiers() {
        return this.h;
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return this.c;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return this.d;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.e;
        return charSequence != null ? charSequence : this.d;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return (this.t & 1) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return (this.t & 2) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return (this.t & 16) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return (this.t & 8) == 0;
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c) {
        this.i = Character.toLowerCase(c);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c, int i) {
        this.i = Character.toLowerCase(c);
        this.j = KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        this.t = (z ? 1 : 0) | (this.t & (-2));
        return this;
    }

    public ActionMenuItem setExclusiveCheckable(boolean z) {
        this.t = (z ? 4 : 0) | (this.t & (-5));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        this.t = (z ? 2 : 0) | (this.t & (-3));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        this.t = (z ? 16 : 0) | (this.t & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.k = drawable;
        a();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.k = ContextCompat.getDrawable(this.l, i);
        a();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        this.f = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c) {
        this.g = c;
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public MenuItem setNumericShortcut(char c, int i) {
        this.g = c;
        this.h = KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.m = onMenuItemClickListener;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char c, char c2) {
        this.g = c;
        this.i = Character.toLowerCase(c2);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public MenuItem setShortcut(char c, char c2, int i, int i2) {
        this.g = c;
        this.h = KeyEvent.normalizeMetaState(i);
        this.i = Character.toLowerCase(c2);
        this.j = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.d = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        this.d = this.l.getResources().getString(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.e = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        int i = 8;
        int i2 = this.t & 8;
        if (z) {
            i = 0;
        }
        this.t = i2 | i;
        return this;
    }

    public boolean invoke() {
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = this.m;
        if (onMenuItemClickListener != null && onMenuItemClickListener.onMenuItemClick(this)) {
            return true;
        }
        Intent intent = this.f;
        if (intent == null) {
            return false;
        }
        this.l.startActivity(intent);
        return true;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public SupportMenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public android.view.ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public SupportMenuItem setActionView(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public SupportMenuItem setShowAsActionFlags(int i) {
        setShowAsAction(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.n = charSequence;
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public CharSequence getContentDescription() {
        return this.n;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.o = charSequence;
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public CharSequence getTooltipText() {
        return this.o;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public MenuItem setIconTintList(@Nullable ColorStateList colorStateList) {
        this.p = colorStateList;
        this.r = true;
        a();
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public ColorStateList getIconTintList() {
        return this.p;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.q = mode;
        this.s = true;
        a();
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public PorterDuff.Mode getIconTintMode() {
        return this.q;
    }

    private void a() {
        if (this.k == null) {
            return;
        }
        if (this.r || this.s) {
            this.k = DrawableCompat.wrap(this.k);
            this.k = this.k.mutate();
            if (this.r) {
                DrawableCompat.setTintList(this.k, this.p);
            }
            if (this.s) {
                DrawableCompat.setTintMode(this.k, this.q);
            }
        }
    }
}
