package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class MenuPopupHelper {
    private final Context a;
    private final MenuBuilder b;
    private final boolean c;
    private final int d;
    private final int e;
    private View f;
    private int g;
    private boolean h;
    private MenuPresenter.Callback i;
    private c j;
    private PopupWindow.OnDismissListener k;
    private final PopupWindow.OnDismissListener l;

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i) {
        this(context, menuBuilder, view, z, i, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i, @StyleRes int i2) {
        this.g = GravityCompat.START;
        this.l = new PopupWindow.OnDismissListener() { // from class: androidx.appcompat.view.menu.MenuPopupHelper.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.a = context;
        this.b = menuBuilder;
        this.f = view;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public void setAnchorView(@NonNull View view) {
        this.f = view;
    }

    public void setForceShowIcon(boolean z) {
        this.h = z;
        c cVar = this.j;
        if (cVar != null) {
            cVar.a(z);
        }
    }

    public void setGravity(int i) {
        this.g = i;
    }

    public int getGravity() {
        return this.g;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int i, int i2) {
        if (!tryShow(i, i2)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public c getPopup() {
        if (this.j == null) {
            this.j = a();
        }
        return this.j;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int i, int i2) {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(i, i2, true, true);
        return true;
    }

    @NonNull
    private c a() {
        c cVar;
        Display defaultDisplay = ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        if (Math.min(point.x, point.y) >= this.a.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width)) {
            cVar = new CascadingMenuPopup(this.a, this.f, this.d, this.e, this.c);
        } else {
            cVar = new d(this.a, this.b, this.f, this.d, this.e, this.c);
        }
        cVar.a(this.b);
        cVar.a(this.l);
        cVar.a(this.f);
        cVar.setCallback(this.i);
        cVar.a(this.h);
        cVar.a(this.g);
        return cVar;
    }

    private void a(int i, int i2, boolean z, boolean z2) {
        c popup = getPopup();
        popup.b(z2);
        if (z) {
            if ((GravityCompat.getAbsoluteGravity(this.g, ViewCompat.getLayoutDirection(this.f)) & 7) == 5) {
                i -= this.f.getWidth();
            }
            popup.b(i);
            popup.c(i2);
            int i3 = (int) ((this.a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            popup.a(new Rect(i - i3, i2 - i3, i + i3, i2 + i3));
        }
        popup.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.j.dismiss();
        }
    }

    public void onDismiss() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public boolean isShowing() {
        c cVar = this.j;
        return cVar != null && cVar.isShowing();
    }

    public void setPresenterCallback(@Nullable MenuPresenter.Callback callback) {
        this.i = callback;
        c cVar = this.j;
        if (cVar != null) {
            cVar.setCallback(callback);
        }
    }

    public ListView getListView() {
        return getPopup().getListView();
    }
}
