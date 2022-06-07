package androidx.appcompat.widget;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TooltipCompatHandler.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class r implements View.OnAttachStateChangeListener, View.OnHoverListener, View.OnLongClickListener {
    private static r j;
    private static r k;
    private final View a;
    private final CharSequence b;
    private final int c;
    private final Runnable d = new Runnable() { // from class: androidx.appcompat.widget.r.1
        @Override // java.lang.Runnable
        public void run() {
            r.this.a(false);
        }
    };
    private final Runnable e = new Runnable() { // from class: androidx.appcompat.widget.r.2
        @Override // java.lang.Runnable
        public void run() {
            r.this.a();
        }
    };
    private int f;
    private int g;
    private s h;
    private boolean i;

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    public static void a(View view, CharSequence charSequence) {
        r rVar = j;
        if (rVar != null && rVar.a == view) {
            a((r) null);
        }
        if (TextUtils.isEmpty(charSequence)) {
            r rVar2 = k;
            if (rVar2 != null && rVar2.a == view) {
                rVar2.a();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        new r(view, charSequence);
    }

    private r(View view, CharSequence charSequence) {
        this.a = view;
        this.b = charSequence;
        this.c = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(this.a.getContext()));
        d();
        this.a.setOnLongClickListener(this);
        this.a.setOnHoverListener(this);
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.f = view.getWidth() / 2;
        this.g = view.getHeight() / 2;
        a(true);
        return true;
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.h != null && this.i) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.a.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                d();
                a();
            }
        } else if (this.a.isEnabled() && this.h == null && a(motionEvent)) {
            a(this);
        }
        return false;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        a();
    }

    void a(boolean z) {
        long j2;
        if (ViewCompat.isAttachedToWindow(this.a)) {
            a((r) null);
            r rVar = k;
            if (rVar != null) {
                rVar.a();
            }
            k = this;
            this.i = z;
            this.h = new s(this.a.getContext());
            this.h.a(this.a, this.f, this.g, this.i, this.b);
            this.a.addOnAttachStateChangeListener(this);
            if (this.i) {
                j2 = 2500;
            } else if ((ViewCompat.getWindowSystemUiVisibility(this.a) & 1) == 1) {
                j2 = 3000 - ViewConfiguration.getLongPressTimeout();
            } else {
                j2 = 15000 - ViewConfiguration.getLongPressTimeout();
            }
            this.a.removeCallbacks(this.e);
            this.a.postDelayed(this.e, j2);
        }
    }

    void a() {
        if (k == this) {
            k = null;
            s sVar = this.h;
            if (sVar != null) {
                sVar.a();
                this.h = null;
                d();
                this.a.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (j == this) {
            a((r) null);
        }
        this.a.removeCallbacks(this.e);
    }

    private static void a(r rVar) {
        r rVar2 = j;
        if (rVar2 != null) {
            rVar2.c();
        }
        j = rVar;
        r rVar3 = j;
        if (rVar3 != null) {
            rVar3.b();
        }
    }

    private void b() {
        this.a.postDelayed(this.d, ViewConfiguration.getLongPressTimeout());
    }

    private void c() {
        this.a.removeCallbacks(this.d);
    }

    private boolean a(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (Math.abs(x - this.f) <= this.c && Math.abs(y - this.g) <= this.c) {
            return false;
        }
        this.f = x;
        this.g = y;
        return true;
    }

    private void d() {
        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
    }
}
