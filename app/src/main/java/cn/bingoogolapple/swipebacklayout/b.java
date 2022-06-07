package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BGASwipeBackShadowView.java */
/* loaded from: classes.dex */
public class b extends FrameLayout {
    private static final String a = "b";
    private Activity b;
    private WeakReference<Activity> c;
    private ViewGroup d;
    private View e;
    private ImageView f;
    private View g;
    private boolean h = true;
    private int i = R.drawable.bga_sbl_shadow;
    private boolean j = true;
    private boolean k = true;
    private boolean l;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Activity activity) {
        super(activity);
        this.b = activity;
        TypedArray obtainStyledAttributes = this.b.getTheme().obtainStyledAttributes(new int[]{16842840});
        this.l = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.h = z;
        c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@DrawableRes int i) {
        this.i = i;
        c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(boolean z) {
        this.j = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(boolean z) {
        this.k = z;
    }

    private void c() {
        if (this.l) {
            if (this.h) {
                setBackgroundResource(this.i);
            } else {
                setBackgroundResource(17170445);
            }
        } else if (this.h) {
            if (this.g == null) {
                this.g = new View(getContext());
                addView(this.g, getChildCount(), new FrameLayout.LayoutParams(-1, -1));
            }
            this.g.setBackgroundResource(this.i);
        } else {
            View view = this.g;
            if (view != null) {
                removeView(view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        Activity a2;
        if (!this.l && this.c == null && (a2 = a.a().a(this.b)) != null) {
            this.c = new WeakReference<>(a2);
            this.d = (ViewGroup) a2.getWindow().getDecorView();
            this.e = this.d.getChildAt(0);
            this.d.removeView(this.e);
            addView(this.e, 0, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d(boolean r6) {
        /*
            r5 = this;
            boolean r0 = r5.l
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            java.lang.ref.WeakReference<android.app.Activity> r0 = r5.c
            if (r0 != 0) goto L_0x000a
            return
        L_0x000a:
            java.lang.Object r0 = r0.get()
            android.app.Activity r0 = (android.app.Activity) r0
            if (r0 == 0) goto L_0x00c7
            boolean r1 = r0.isFinishing()
            if (r1 == 0) goto L_0x001a
            goto L_0x00c7
        L_0x001a:
            android.view.ViewGroup r1 = r5.d
            if (r1 == 0) goto L_0x00c6
            android.view.View r1 = r5.e
            if (r1 == 0) goto L_0x00c6
            r2 = 0
            if (r6 == 0) goto L_0x0057
            android.widget.ImageView r6 = r5.f
            if (r6 != 0) goto L_0x0057
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            boolean r6 = r5.a(r1)
            if (r6 == 0) goto L_0x0057
            android.widget.ImageView r6 = new android.widget.ImageView
            android.app.Activity r1 = r5.b
            r6.<init>(r1)
            r5.f = r6
            android.widget.ImageView r6 = r5.f
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_XY
            r6.setScaleType(r1)
            android.widget.ImageView r6 = r5.f
            android.view.View r1 = r5.e
            android.graphics.Bitmap r1 = r5.a(r1)
            r6.setImageBitmap(r1)
            android.widget.ImageView r6 = r5.f
            android.widget.FrameLayout$LayoutParams r1 = new android.widget.FrameLayout$LayoutParams
            r3 = -1
            r1.<init>(r3, r3)
            r5.addView(r6, r2, r1)
        L_0x0057:
            android.view.View r6 = r5.e
            r5.removeView(r6)
            android.view.View r6 = r5.e
            boolean r6 = r6 instanceof cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout
            r1 = 0
            if (r6 != 0) goto L_0x00ab
            android.view.ViewGroup r6 = r5.d
            int r6 = r6.getWidth()
            android.view.ViewGroup r3 = r5.d
            int r3 = r3.getHeight()
            int r4 = cn.bingoogolapple.swipebacklayout.c.a(r0)
            int r3 = r3 - r4
            boolean r4 = cn.bingoogolapple.swipebacklayout.c.b(r0)
            if (r4 != 0) goto L_0x008b
            android.view.ViewGroup r6 = r5.d
            int r6 = r6.getWidth()
            int r0 = cn.bingoogolapple.swipebacklayout.c.a(r0)
            int r6 = r6 - r0
            android.view.ViewGroup r0 = r5.d
            int r3 = r0.getHeight()
        L_0x008b:
            android.view.ViewGroup r0 = r5.d
            boolean r4 = r0 instanceof android.widget.FrameLayout
            if (r4 == 0) goto L_0x0097
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r0.<init>(r6, r3)
            goto L_0x00ac
        L_0x0097:
            boolean r4 = r0 instanceof android.widget.LinearLayout
            if (r4 == 0) goto L_0x00a1
            android.widget.LinearLayout$LayoutParams r0 = new android.widget.LinearLayout$LayoutParams
            r0.<init>(r6, r3)
            goto L_0x00ac
        L_0x00a1:
            boolean r0 = r0 instanceof android.widget.RelativeLayout
            if (r0 == 0) goto L_0x00ab
            android.widget.RelativeLayout$LayoutParams r0 = new android.widget.RelativeLayout$LayoutParams
            r0.<init>(r6, r3)
            goto L_0x00ac
        L_0x00ab:
            r0 = r1
        L_0x00ac:
            if (r0 != 0) goto L_0x00b6
            android.view.ViewGroup r6 = r5.d
            android.view.View r0 = r5.e
            r6.addView(r0, r2)
            goto L_0x00bd
        L_0x00b6:
            android.view.ViewGroup r6 = r5.d
            android.view.View r3 = r5.e
            r6.addView(r3, r2, r0)
        L_0x00bd:
            r5.e = r1
            java.lang.ref.WeakReference<android.app.Activity> r6 = r5.c
            r6.clear()
            r5.c = r1
        L_0x00c6:
            return
        L_0x00c7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.bingoogolapple.swipebacklayout.b.d(boolean):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        ViewGroup viewGroup;
        super.dispatchDraw(canvas);
        if (!this.l && this.f == null && this.e == null && (viewGroup = this.d) != null) {
            viewGroup.draw(canvas);
        }
    }

    private Bitmap a(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, c.d(this.b), c.c(this.b) - c.a(this.b));
        view.destroyDrawingCache();
        return createBitmap;
    }

    private boolean a(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (a.a().a(childAt)) {
                return true;
            }
            if ((childAt instanceof ViewGroup) && a((ViewGroup) childAt)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f) {
        if (this.h && this.j) {
            if (this.l) {
                ViewCompat.setAlpha(this, f);
                return;
            }
            View view = this.g;
            if (view != null) {
                ViewCompat.setAlpha(view, f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(float f) {
        View view;
        if (this.k) {
            if (this.l) {
                a(this.b, f);
                return;
            }
            View view2 = this.e;
            if (view2 != null) {
                ViewCompat.setTranslationX(view2, view2.getMeasuredWidth() * 0.75f * (1.0f - f));
            }
        } else if (!this.l && (view = this.e) != null) {
            ViewCompat.setTranslationX(view, view.getMeasuredWidth() * (1.0f - f));
        }
    }

    private void a(Activity activity, float f) {
        try {
            Activity a2 = a.a().a(activity);
            if (a2 != null) {
                View decorView = a2.getWindow().getDecorView();
                ViewCompat.setTranslationX(decorView, (-decorView.getMeasuredWidth()) * 0.25f * (1.0f - f));
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        View view;
        if (this.k) {
            if (this.l) {
                a(this.b);
            } else {
                View view2 = this.e;
                if (view2 != null) {
                    ViewCompat.setTranslationX(view2, 0.0f);
                }
            }
        } else if (!this.l && (view = this.e) != null) {
            ViewCompat.setTranslationX(view, 0.0f);
        }
        d(false);
    }

    private void a(Activity activity) {
        try {
            Activity a2 = a.a().a(activity);
            if (a2 != null) {
                ViewCompat.setTranslationX(a2.getWindow().getDecorView(), 0.0f);
            }
        } catch (Exception unused) {
        }
    }
}
