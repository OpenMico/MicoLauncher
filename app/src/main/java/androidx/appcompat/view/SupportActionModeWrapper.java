package androidx.appcompat.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class SupportActionModeWrapper extends ActionMode {
    final Context a;
    final ActionMode b;

    public SupportActionModeWrapper(Context context, ActionMode actionMode) {
        this.a = context;
        this.b = actionMode;
    }

    @Override // android.view.ActionMode
    public Object getTag() {
        return this.b.getTag();
    }

    @Override // android.view.ActionMode
    public void setTag(Object obj) {
        this.b.setTag(obj);
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.b.setTitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.b.setSubtitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.b.invalidate();
    }

    @Override // android.view.ActionMode
    public void finish() {
        this.b.finish();
    }

    @Override // android.view.ActionMode
    public Menu getMenu() {
        return new MenuWrapperICS(this.a, (SupportMenu) this.b.getMenu());
    }

    @Override // android.view.ActionMode
    public CharSequence getTitle() {
        return this.b.getTitle();
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        this.b.setTitle(i);
    }

    @Override // android.view.ActionMode
    public CharSequence getSubtitle() {
        return this.b.getSubtitle();
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        this.b.setSubtitle(i);
    }

    @Override // android.view.ActionMode
    public View getCustomView() {
        return this.b.getCustomView();
    }

    @Override // android.view.ActionMode
    public void setCustomView(View view) {
        this.b.setCustomView(view);
    }

    @Override // android.view.ActionMode
    public MenuInflater getMenuInflater() {
        return this.b.getMenuInflater();
    }

    @Override // android.view.ActionMode
    public boolean getTitleOptionalHint() {
        return this.b.getTitleOptionalHint();
    }

    @Override // android.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        this.b.setTitleOptionalHint(z);
    }

    @Override // android.view.ActionMode
    public boolean isTitleOptional() {
        return this.b.isTitleOptional();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public static class CallbackWrapper implements ActionMode.Callback {
        final ActionMode.Callback a;
        final Context b;
        final ArrayList<SupportActionModeWrapper> c = new ArrayList<>();
        final SimpleArrayMap<Menu, Menu> d = new SimpleArrayMap<>();

        public CallbackWrapper(Context context, ActionMode.Callback callback) {
            this.b = context;
            this.a = callback;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onCreateActionMode(getActionModeWrapper(actionMode), a(menu));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onPrepareActionMode(getActionModeWrapper(actionMode), a(menu));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.a.onActionItemClicked(getActionModeWrapper(actionMode), new MenuItemWrapperICS(this.b, (SupportMenuItem) menuItem));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.a.onDestroyActionMode(getActionModeWrapper(actionMode));
        }

        private Menu a(Menu menu) {
            Menu menu2 = this.d.get(menu);
            if (menu2 != null) {
                return menu2;
            }
            MenuWrapperICS menuWrapperICS = new MenuWrapperICS(this.b, (SupportMenu) menu);
            this.d.put(menu, menuWrapperICS);
            return menuWrapperICS;
        }

        public android.view.ActionMode getActionModeWrapper(ActionMode actionMode) {
            int size = this.c.size();
            for (int i = 0; i < size; i++) {
                SupportActionModeWrapper supportActionModeWrapper = this.c.get(i);
                if (supportActionModeWrapper != null && supportActionModeWrapper.b == actionMode) {
                    return supportActionModeWrapper;
                }
            }
            SupportActionModeWrapper supportActionModeWrapper2 = new SupportActionModeWrapper(this.b, actionMode);
            this.c.add(supportActionModeWrapper2);
            return supportActionModeWrapper2;
        }
    }
}
