package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* compiled from: CardViewApi21Impl.java */
@RequiresApi(21)
/* loaded from: classes.dex */
class b implements e {
    @Override // androidx.cardview.widget.e
    public void a() {
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        dVar.a(new f(colorStateList, f));
        View d = dVar.d();
        d.setClipToOutline(true);
        d.setElevation(f2);
        b(dVar, f3);
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, float f) {
        j(dVar).a(f);
    }

    @Override // androidx.cardview.widget.e
    public void b(d dVar, float f) {
        j(dVar).a(f, dVar.a(), dVar.b());
        f(dVar);
    }

    @Override // androidx.cardview.widget.e
    public float a(d dVar) {
        return j(dVar).a();
    }

    @Override // androidx.cardview.widget.e
    public float b(d dVar) {
        return d(dVar) * 2.0f;
    }

    @Override // androidx.cardview.widget.e
    public float c(d dVar) {
        return d(dVar) * 2.0f;
    }

    @Override // androidx.cardview.widget.e
    public float d(d dVar) {
        return j(dVar).b();
    }

    @Override // androidx.cardview.widget.e
    public void c(d dVar, float f) {
        dVar.d().setElevation(f);
    }

    @Override // androidx.cardview.widget.e
    public float e(d dVar) {
        return dVar.d().getElevation();
    }

    @Override // androidx.cardview.widget.e
    public void f(d dVar) {
        if (!dVar.a()) {
            dVar.a(0, 0, 0, 0);
            return;
        }
        float a = a(dVar);
        float d = d(dVar);
        int ceil = (int) Math.ceil(g.b(a, d, dVar.b()));
        int ceil2 = (int) Math.ceil(g.a(a, d, dVar.b()));
        dVar.a(ceil, ceil2, ceil, ceil2);
    }

    @Override // androidx.cardview.widget.e
    public void g(d dVar) {
        b(dVar, a(dVar));
    }

    @Override // androidx.cardview.widget.e
    public void h(d dVar) {
        b(dVar, a(dVar));
    }

    @Override // androidx.cardview.widget.e
    public void a(d dVar, @Nullable ColorStateList colorStateList) {
        j(dVar).a(colorStateList);
    }

    @Override // androidx.cardview.widget.e
    public ColorStateList i(d dVar) {
        return j(dVar).c();
    }

    private f j(d dVar) {
        return (f) dVar.c();
    }
}
