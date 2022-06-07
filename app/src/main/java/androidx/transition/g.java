package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/* compiled from: GhostViewPort.java */
@SuppressLint({"ViewConstructor"})
/* loaded from: classes.dex */
public class g extends ViewGroup implements d {
    ViewGroup a;
    View b;
    final View c;
    int d;
    @Nullable
    private Matrix e;
    private final ViewTreeObserver.OnPreDrawListener f = new ViewTreeObserver.OnPreDrawListener() { // from class: androidx.transition.g.1
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            ViewCompat.postInvalidateOnAnimation(g.this);
            if (g.this.a == null || g.this.b == null) {
                return true;
            }
            g.this.a.endViewTransition(g.this.b);
            ViewCompat.postInvalidateOnAnimation(g.this.a);
            g gVar = g.this;
            gVar.a = null;
            gVar.b = null;
            return true;
        }
    };

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    g(View view) {
        super(view.getContext());
        this.c = view;
        setWillNotDraw(false);
        setLayerType(2, null);
    }

    @Override // android.view.View, androidx.transition.d
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (a(this.c) == this) {
            ab.a(this.c, i == 0 ? 4 : 0);
        }
    }

    void a(@NonNull Matrix matrix) {
        this.e = matrix;
    }

    @Override // androidx.transition.d
    public void a(ViewGroup viewGroup, View view) {
        this.a = viewGroup;
        this.b = view;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(this.c, this);
        this.c.getViewTreeObserver().addOnPreDrawListener(this.f);
        ab.a(this.c, 4);
        if (this.c.getParent() != null) {
            ((View) this.c.getParent()).invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.c.getViewTreeObserver().removeOnPreDrawListener(this.f);
        ab.a(this.c, 0);
        a(this.c, (g) null);
        if (this.c.getParent() != null) {
            ((View) this.c.getParent()).invalidate();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        b.a(canvas, true);
        canvas.setMatrix(this.e);
        ab.a(this.c, 0);
        this.c.invalidate();
        ab.a(this.c, 4);
        drawChild(canvas, this.c, getDrawingTime());
        b.a(canvas, false);
    }

    static void a(View view, View view2) {
        ab.a(view2, view2.getLeft(), view2.getTop(), view2.getLeft() + view.getWidth(), view2.getTop() + view.getHeight());
    }

    static g a(View view) {
        return (g) view.getTag(R.id.ghost_view);
    }

    static void a(@NonNull View view, @Nullable g gVar) {
        view.setTag(R.id.ghost_view, gVar);
    }

    static void a(View view, ViewGroup viewGroup, Matrix matrix) {
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        matrix.reset();
        ab.a(viewGroup2, matrix);
        matrix.preTranslate(-viewGroup2.getScrollX(), -viewGroup2.getScrollY());
        ab.b(viewGroup, matrix);
    }

    public static g b(View view, ViewGroup viewGroup, Matrix matrix) {
        e eVar;
        if (view.getParent() instanceof ViewGroup) {
            e a = e.a(viewGroup);
            g a2 = a(view);
            int i = 0;
            if (!(a2 == null || (eVar = (e) a2.getParent()) == a)) {
                i = a2.d;
                eVar.removeView(a2);
                a2 = null;
            }
            if (a2 == null) {
                if (matrix == null) {
                    matrix = new Matrix();
                    a(view, viewGroup, matrix);
                }
                a2 = new g(view);
                a2.a(matrix);
                if (a == null) {
                    a = new e(viewGroup);
                } else {
                    a.a();
                }
                a((View) viewGroup, (View) a);
                a((View) viewGroup, (View) a2);
                a.a(a2);
                a2.d = i;
            } else if (matrix != null) {
                a2.a(matrix);
            }
            a2.d++;
            return a2;
        }
        throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
    }

    public static void b(View view) {
        g a = a(view);
        if (a != null) {
            a.d--;
            if (a.d <= 0) {
                ((e) a.getParent()).removeView(a);
            }
        }
    }
}
