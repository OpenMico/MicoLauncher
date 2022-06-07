package androidx.appcompat.view;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    private Context a;
    private ActionBarContextView b;
    private ActionMode.Callback c;
    private WeakReference<View> d;
    private boolean e;
    private boolean f;
    private MenuBuilder g;

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    public StandaloneActionMode(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, boolean z) {
        this.a = context;
        this.b = actionBarContextView;
        this.c = callback;
        this.g = new MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1);
        this.g.setCallback(this);
        this.f = z;
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.b.setTitle(charSequence);
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.b.setSubtitle(charSequence);
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setTitle(int i) {
        setTitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setSubtitle(int i) {
        setSubtitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.b.setTitleOptional(z);
    }

    @Override // androidx.appcompat.view.ActionMode
    public boolean isTitleOptional() {
        return this.b.isTitleOptional();
    }

    @Override // androidx.appcompat.view.ActionMode
    public void setCustomView(View view) {
        this.b.setCustomView(view);
        this.d = view != null ? new WeakReference<>(view) : null;
    }

    @Override // androidx.appcompat.view.ActionMode
    public void invalidate() {
        this.c.onPrepareActionMode(this, this.g);
    }

    @Override // androidx.appcompat.view.ActionMode
    public void finish() {
        if (!this.e) {
            this.e = true;
            this.b.sendAccessibilityEvent(32);
            this.c.onDestroyActionMode(this);
        }
    }

    @Override // androidx.appcompat.view.ActionMode
    public Menu getMenu() {
        return this.g;
    }

    @Override // androidx.appcompat.view.ActionMode
    public CharSequence getTitle() {
        return this.b.getTitle();
    }

    @Override // androidx.appcompat.view.ActionMode
    public CharSequence getSubtitle() {
        return this.b.getSubtitle();
    }

    @Override // androidx.appcompat.view.ActionMode
    public View getCustomView() {
        WeakReference<View> weakReference = this.d;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // androidx.appcompat.view.ActionMode
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.b.getContext());
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        return this.c.onActionItemClicked(this, menuItem);
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.b.getContext(), subMenuBuilder).show();
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
        invalidate();
        this.b.showOverflowMenu();
    }

    @Override // androidx.appcompat.view.ActionMode
    public boolean isUiFocusable() {
        return this.f;
    }
}
