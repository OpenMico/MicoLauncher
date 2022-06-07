package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ChildHelper.java */
/* loaded from: classes.dex */
public class b {
    final AbstractC0024b a;
    final a b = new a();
    final List<View> c = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ChildHelper.java */
    /* renamed from: androidx.recyclerview.widget.b$b  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface AbstractC0024b {
        int a();

        int a(View view);

        void a(int i);

        void a(View view, int i);

        void a(View view, int i, ViewGroup.LayoutParams layoutParams);

        View b(int i);

        RecyclerView.ViewHolder b(View view);

        void b();

        void c(int i);

        void c(View view);

        void d(View view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(AbstractC0024b bVar) {
        this.a = bVar;
    }

    private void g(View view) {
        this.c.add(view);
        this.a.c(view);
    }

    private boolean h(View view) {
        if (!this.c.remove(view)) {
            return false;
        }
        this.a.d(view);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(View view, boolean z) {
        a(view, -1, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(View view, int i, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = f(i);
        }
        this.b.a(i2, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        if (r4.b.c(r2) == false) goto L_0x0024;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0021, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0024, code lost:
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int f(int r5) {
        /*
            r4 = this;
            r0 = -1
            if (r5 >= 0) goto L_0x0004
            return r0
        L_0x0004:
            androidx.recyclerview.widget.b$b r1 = r4.a
            int r1 = r1.a()
            r2 = r5
        L_0x000b:
            if (r2 >= r1) goto L_0x0027
            androidx.recyclerview.widget.b$a r3 = r4.b
            int r3 = r3.e(r2)
            int r3 = r2 - r3
            int r3 = r5 - r3
            if (r3 != 0) goto L_0x0025
        L_0x0019:
            androidx.recyclerview.widget.b$a r5 = r4.b
            boolean r5 = r5.c(r2)
            if (r5 == 0) goto L_0x0024
            int r2 = r2 + 1
            goto L_0x0019
        L_0x0024:
            return r2
        L_0x0025:
            int r2 = r2 + r3
            goto L_0x000b
        L_0x0027:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.b.f(int):int");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(View view) {
        int a2 = this.a.a(view);
        if (a2 >= 0) {
            if (this.b.d(a2)) {
                h(view);
            }
            this.a.a(a2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        int f = f(i);
        View b = this.a.b(f);
        if (b != null) {
            if (this.b.d(f)) {
                h(b);
            }
            this.a.a(f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public View b(int i) {
        return this.a.b(f(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.b.a();
        for (int size = this.c.size() - 1; size >= 0; size--) {
            this.a.d(this.c.get(size));
            this.c.remove(size);
        }
        this.a.b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public View c(int i) {
        int size = this.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = this.c.get(i2);
            RecyclerView.ViewHolder b = this.a.b(view);
            if (!(b.getLayoutPosition() != i || b.isInvalid() || b.isRemoved())) {
                return view;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = f(i);
        }
        this.b.a(i2, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i2, layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.a.a() - this.c.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.a.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public View d(int i) {
        return this.a.b(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(int i) {
        int f = f(i);
        this.b.d(f);
        this.a.c(f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(View view) {
        int a2 = this.a.a(view);
        if (a2 != -1 && !this.b.c(a2)) {
            return a2 - this.b.e(a2);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c(View view) {
        return this.c.contains(view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(View view) {
        int a2 = this.a.a(view);
        if (a2 >= 0) {
            this.b.a(a2);
            g(view);
            return;
        }
        throw new IllegalArgumentException("view is not a child, cannot hide " + view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(View view) {
        int a2 = this.a.a(view);
        if (a2 < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else if (this.b.c(a2)) {
            this.b.b(a2);
            h(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    public String toString() {
        return this.b.toString() + ", hidden list:" + this.c.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean f(View view) {
        int a2 = this.a.a(view);
        if (a2 == -1) {
            h(view);
            return true;
        } else if (!this.b.c(a2)) {
            return false;
        } else {
            this.b.d(a2);
            h(view);
            this.a.a(a2);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ChildHelper.java */
    /* loaded from: classes.dex */
    public static class a {
        long a = 0;
        a b;

        a() {
        }

        void a(int i) {
            if (i >= 64) {
                b();
                this.b.a(i - 64);
                return;
            }
            this.a |= 1 << i;
        }

        private void b() {
            if (this.b == null) {
                this.b = new a();
            }
        }

        void b(int i) {
            if (i >= 64) {
                a aVar = this.b;
                if (aVar != null) {
                    aVar.b(i - 64);
                    return;
                }
                return;
            }
            this.a &= ~(1 << i);
        }

        boolean c(int i) {
            if (i < 64) {
                return (this.a & (1 << i)) != 0;
            }
            b();
            return this.b.c(i - 64);
        }

        void a() {
            this.a = 0L;
            a aVar = this.b;
            if (aVar != null) {
                aVar.a();
            }
        }

        void a(int i, boolean z) {
            if (i >= 64) {
                b();
                this.b.a(i - 64, z);
                return;
            }
            boolean z2 = (this.a & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            long j2 = this.a;
            this.a = ((j2 & (~j)) << 1) | (j2 & j);
            if (z) {
                a(i);
            } else {
                b(i);
            }
            if (z2 || this.b != null) {
                b();
                this.b.a(0, z2);
            }
        }

        boolean d(int i) {
            if (i >= 64) {
                b();
                return this.b.d(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.a & j) != 0;
            this.a &= ~j;
            long j2 = j - 1;
            long j3 = this.a;
            this.a = Long.rotateRight(j3 & (~j2), 1) | (j3 & j2);
            a aVar = this.b;
            if (aVar != null) {
                if (aVar.c(0)) {
                    a(63);
                }
                this.b.d(0);
            }
            return z;
        }

        int e(int i) {
            a aVar = this.b;
            if (aVar == null) {
                if (i >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else if (i < 64) {
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else {
                return aVar.e(i - 64) + Long.bitCount(this.a);
            }
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            return this.b.toString() + "xx" + Long.toBinaryString(this.a);
        }
    }
}
