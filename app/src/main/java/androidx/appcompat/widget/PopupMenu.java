package androidx.appcompat.widget;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import androidx.annotation.AttrRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.ShowableListMenu;

/* loaded from: classes.dex */
public class PopupMenu {
    final MenuPopupHelper a;
    OnMenuItemClickListener b;
    OnDismissListener c;
    private final Context d;
    private final MenuBuilder e;
    private final View f;
    private View.OnTouchListener g;

    /* loaded from: classes.dex */
    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu);
    }

    /* loaded from: classes.dex */
    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view) {
        this(context, view, 0);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view, int i) {
        this(context, view, i, R.attr.popupMenuStyle, 0);
    }

    public PopupMenu(@NonNull Context context, @NonNull View view, int i, @AttrRes int i2, @StyleRes int i3) {
        this.d = context;
        this.f = view;
        this.e = new MenuBuilder(context);
        this.e.setCallback(new MenuBuilder.Callback() { // from class: androidx.appcompat.widget.PopupMenu.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
                if (PopupMenu.this.b != null) {
                    return PopupMenu.this.b.onMenuItemClick(menuItem);
                }
                return false;
            }
        });
        this.a = new MenuPopupHelper(context, this.e, view, false, i2, i3);
        this.a.setGravity(i);
        this.a.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: androidx.appcompat.widget.PopupMenu.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                if (PopupMenu.this.c != null) {
                    PopupMenu.this.c.onDismiss(PopupMenu.this);
                }
            }
        });
    }

    public void setGravity(int i) {
        this.a.setGravity(i);
    }

    public int getGravity() {
        return this.a.getGravity();
    }

    @NonNull
    public View.OnTouchListener getDragToOpenListener() {
        if (this.g == null) {
            this.g = new ForwardingListener(this.f) { // from class: androidx.appcompat.widget.PopupMenu.3
                @Override // androidx.appcompat.widget.ForwardingListener
                protected boolean onForwardingStarted() {
                    PopupMenu.this.show();
                    return true;
                }

                @Override // androidx.appcompat.widget.ForwardingListener
                protected boolean onForwardingStopped() {
                    PopupMenu.this.dismiss();
                    return true;
                }

                @Override // androidx.appcompat.widget.ForwardingListener
                public ShowableListMenu getPopup() {
                    return PopupMenu.this.a.getPopup();
                }
            };
        }
        return this.g;
    }

    @NonNull
    public Menu getMenu() {
        return this.e;
    }

    @NonNull
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.d);
    }

    public void inflate(@MenuRes int i) {
        getMenuInflater().inflate(i, this.e);
    }

    public void show() {
        this.a.show();
    }

    public void dismiss() {
        this.a.dismiss();
    }

    public void setOnMenuItemClickListener(@Nullable OnMenuItemClickListener onMenuItemClickListener) {
        this.b = onMenuItemClickListener;
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.c = onDismissListener;
    }
}
