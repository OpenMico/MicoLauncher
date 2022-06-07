package io.realm;

import io.realm.internal.OsList;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public abstract class i<T> {
    final BaseRealm a;
    final OsList b;
    @Nullable
    final Class<T> c;

    protected abstract void a(int i, Object obj);

    protected abstract void a(@Nullable Object obj);

    public abstract boolean a();

    @Nullable
    public abstract T b(int i);

    protected abstract void b(int i, Object obj);

    protected abstract void b(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(BaseRealm baseRealm, OsList osList, @Nullable Class<T> cls) {
        this.a = baseRealm;
        this.c = cls;
        this.b = osList;
    }

    public final OsList b() {
        return this.b;
    }

    public final boolean c() {
        return this.b.isValid();
    }

    public final int d() {
        long size = this.b.size();
        if (size < 2147483647L) {
            return (int) size;
        }
        return Integer.MAX_VALUE;
    }

    public final boolean e() {
        return this.b.isEmpty();
    }

    public final void c(@Nullable Object obj) {
        a(obj);
        if (obj == null) {
            i();
        } else {
            b(obj);
        }
    }

    private void i() {
        this.b.addNull();
    }

    public final void c(int i, @Nullable Object obj) {
        a(obj);
        if (obj == null) {
            c(i);
        } else {
            a(i, obj);
        }
    }

    protected void c(int i) {
        this.b.insertNull(i);
    }

    @Nullable
    public final T d(int i, @Nullable Object obj) {
        a(obj);
        T b = b(i);
        if (obj == null) {
            d(i);
        } else {
            b(i, obj);
        }
        return b;
    }

    protected void d(int i) {
        this.b.setNull(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(int i, int i2) {
        this.b.move(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void e(int i) {
        this.b.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void f() {
        this.b.removeAll();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void f(int i) {
        this.b.delete(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void g() {
        OsList osList = this.b;
        osList.delete(osList.size() - 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void h() {
        this.b.deleteAll();
    }
}
