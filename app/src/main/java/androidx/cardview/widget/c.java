package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import androidx.cardview.widget.g;

/* compiled from: CardViewBaseImpl.java */
/* loaded from: classes.dex */
class c implements e {
    final RectF a = new RectF();

    @Override // androidx.cardview.widget.e
    public void g(d dVar) {
    }

    @Override // androidx.cardview.widget.e
    public void a() {
        g.a = new g.a() { // from class: androidx.cardview.widget.c.1
            @Override // androidx.cardview.widget.g.a
            public void a(Canvas canvas, RectF rectF, float f, Paint paint) {
                float f2 = 2.0f * f;
                float width = (rectF.width() - f2) - 1.0f;
                float height = (rectF.height() - f2) - 1.0f;
                if (f >= 1.0f) {
                    float f3 = f + 0.5f;
                    float f4 = -f3;
                    c.this.a.set(f4, f4, f3, f3);
                    int save = canvas.save();
                    canvas.translate(rectF.left + f3, rectF.top + f3);
                    canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint);
                    canvas.translate(width, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint);
                    canvas.translate(height, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint);
                    canvas.translate(width, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint);
                    canvas.restoreToCount(save);
                    canvas.drawRect((rectF.left + f3) - 1.0f, rectF.top, (rectF.right - f3) + 1.0f, rectF.top + f3, paint);
                    canvas.drawRect((rectF.left + f3) - 1.0f, rectF.bottom - f3, (rectF.right - f3) + 1.0f, rectF.bottom, paint);
                }
                canvas.drawRect(rectF.left, rectF.top + f, rectF.right, rectF.bottom - f, paint);
            }
        };
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        g a = a(context, colorStateList, f, f2, f3);
        a.a(dVar.b());
        dVar.a(a);
        f(dVar);
    }

    private g a(Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        return new g(context.getResources(), colorStateList, f, f2, f3);
    }

    @Override // androidx.cardview.widget.e
    public void f(d dVar) {
        Rect rect = new Rect();
        j(dVar).a(rect);
        dVar.a((int) Math.ceil(b(dVar)), (int) Math.ceil(c(dVar)));
        dVar.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // androidx.cardview.widget.e
    public void h(d dVar) {
        j(dVar).a(dVar.b());
        f(dVar);
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, @Nullable ColorStateList colorStateList) {
        j(dVar).a(colorStateList);
    }

    @Override // androidx.cardview.widget.e
    public ColorStateList i(d dVar) {
        return j(dVar).f();
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, float f) {
        j(dVar).a(f);
        f(dVar);
    }

    @Override // androidx.cardview.widget.e
    public float d(d dVar) {
        return j(dVar).a();
    }

    @Override // androidx.cardview.widget.e
    public void c(d dVar, float f) {
        j(dVar).b(f);
    }

    @Override // androidx.cardview.widget.e
    public float e(d dVar) {
        return j(dVar).b();
    }

    @Override // androidx.cardview.widget.e
    public void b(d dVar, float f) {
        j(dVar).c(f);
        f(dVar);
    }

    @Override // androidx.cardview.widget.e
    public float a(d dVar) {
        return j(dVar).c();
    }

    @Override // androidx.cardview.widget.e
    public float b(d dVar) {
        return j(dVar).d();
    }

    @Override // androidx.cardview.widget.e
    public float c(d dVar) {
        return j(dVar).e();
    }

    private g j(d dVar) {
        return (g) dVar.c();
    }
}
