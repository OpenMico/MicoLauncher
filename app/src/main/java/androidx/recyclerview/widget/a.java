package androidx.recyclerview.widget;

import androidx.core.util.Pools;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.j;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AdapterHelper.java */
/* loaded from: classes.dex */
public final class a implements j.a {
    final ArrayList<b> a;
    final ArrayList<b> b;
    final AbstractC0023a c;
    Runnable d;
    final boolean e;
    final j f;
    private Pools.Pool<b> g;
    private int h;

    /* compiled from: AdapterHelper.java */
    /* renamed from: androidx.recyclerview.widget.a$a */
    /* loaded from: classes.dex */
    public interface AbstractC0023a {
        RecyclerView.ViewHolder a(int i);

        void a(int i, int i2);

        void a(int i, int i2, Object obj);

        void a(b bVar);

        void b(int i, int i2);

        void b(b bVar);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    public a(AbstractC0023a aVar) {
        this(aVar, false);
    }

    a(AbstractC0023a aVar, boolean z) {
        this.g = new Pools.SimplePool(30);
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.h = 0;
        this.c = aVar;
        this.e = z;
        this.f = new j(this);
    }

    public void a() {
        a(this.a);
        a(this.b);
        this.h = 0;
    }

    public void b() {
        this.f.a(this.a);
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.a.get(i);
            int i2 = bVar.a;
            if (i2 == 4) {
                d(bVar);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        f(bVar);
                        break;
                    case 2:
                        c(bVar);
                        break;
                }
            } else {
                b(bVar);
            }
            Runnable runnable = this.d;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.a.clear();
    }

    public void c() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            this.c.b(this.b.get(i));
        }
        a(this.b);
        this.h = 0;
    }

    private void b(b bVar) {
        g(bVar);
    }

    private void c(b bVar) {
        boolean z;
        int i = bVar.b;
        int i2 = bVar.b + bVar.d;
        int i3 = bVar.b;
        char c = 65535;
        int i4 = 0;
        while (i3 < i2) {
            if (this.c.a(i3) != null || d(i3)) {
                if (c == 0) {
                    e(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                c = 1;
            } else {
                if (c == 1) {
                    g(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                c = 0;
            }
            if (z) {
                i3 -= i4;
                i2 -= i4;
                i4 = 1;
            } else {
                i4++;
            }
            i3++;
        }
        if (i4 != bVar.d) {
            a(bVar);
            bVar = a(2, i, i4, null);
        }
        if (c == 0) {
            e(bVar);
        } else {
            g(bVar);
        }
    }

    private void d(b bVar) {
        int i = bVar.b;
        int i2 = bVar.b + bVar.d;
        char c = 65535;
        int i3 = i;
        int i4 = 0;
        for (int i5 = bVar.b; i5 < i2; i5++) {
            if (this.c.a(i5) != null || d(i5)) {
                if (c == 0) {
                    e(a(4, i3, i4, bVar.c));
                    i3 = i5;
                    i4 = 0;
                }
                c = 1;
            } else {
                if (c == 1) {
                    g(a(4, i3, i4, bVar.c));
                    i3 = i5;
                    i4 = 0;
                }
                c = 0;
            }
            i4++;
        }
        if (i4 != bVar.d) {
            Object obj = bVar.c;
            a(bVar);
            bVar = a(4, i3, i4, obj);
        }
        if (c == 0) {
            e(bVar);
        } else {
            g(bVar);
        }
    }

    private void e(b bVar) {
        int i;
        if (bVar.a == 1 || bVar.a == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int d = d(bVar.b, bVar.a);
        int i2 = bVar.b;
        int i3 = bVar.a;
        if (i3 == 2) {
            i = 0;
        } else if (i3 == 4) {
            i = 1;
        } else {
            throw new IllegalArgumentException("op should be remove or update." + bVar);
        }
        int i4 = i2;
        int i5 = 1;
        for (int i6 = 1; i6 < bVar.d; i6++) {
            int d2 = d(bVar.b + (i * i6), bVar.a);
            int i7 = bVar.a;
            if (i7 != 2 ? i7 != 4 ? false : d2 == d + 1 : d2 == d) {
                i5++;
            } else {
                b a = a(bVar.a, d, i5, bVar.c);
                a(a, i4);
                a(a);
                if (bVar.a == 4) {
                    i4 += i5;
                }
                i5 = 1;
                d = d2;
            }
        }
        Object obj = bVar.c;
        a(bVar);
        if (i5 > 0) {
            b a2 = a(bVar.a, d, i5, obj);
            a(a2, i4);
            a(a2);
        }
    }

    void a(b bVar, int i) {
        this.c.a(bVar);
        int i2 = bVar.a;
        if (i2 == 2) {
            this.c.a(i, bVar.d);
        } else if (i2 == 4) {
            this.c.a(i, bVar.d, bVar.c);
        } else {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    private int d(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            b bVar = this.b.get(size);
            if (bVar.a == 8) {
                if (bVar.b < bVar.d) {
                    i4 = bVar.b;
                    i3 = bVar.d;
                } else {
                    i4 = bVar.d;
                    i3 = bVar.b;
                }
                if (i < i4 || i > i3) {
                    if (i < bVar.b) {
                        if (i2 == 1) {
                            bVar.b++;
                            bVar.d++;
                        } else if (i2 == 2) {
                            bVar.b--;
                            bVar.d--;
                        }
                    }
                } else if (i4 == bVar.b) {
                    if (i2 == 1) {
                        bVar.d++;
                    } else if (i2 == 2) {
                        bVar.d--;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        bVar.b++;
                    } else if (i2 == 2) {
                        bVar.b--;
                    }
                    i--;
                }
            } else if (bVar.b <= i) {
                if (bVar.a == 1) {
                    i -= bVar.d;
                } else if (bVar.a == 2) {
                    i += bVar.d;
                }
            } else if (i2 == 1) {
                bVar.b++;
            } else if (i2 == 2) {
                bVar.b--;
            }
        }
        for (int size2 = this.b.size() - 1; size2 >= 0; size2--) {
            b bVar2 = this.b.get(size2);
            if (bVar2.a == 8) {
                if (bVar2.d == bVar2.b || bVar2.d < 0) {
                    this.b.remove(size2);
                    a(bVar2);
                }
            } else if (bVar2.d <= 0) {
                this.b.remove(size2);
                a(bVar2);
            }
        }
        return i;
    }

    private boolean d(int i) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.b.get(i2);
            if (bVar.a == 8) {
                if (a(bVar.d, i2 + 1) == i) {
                    return true;
                }
            } else if (bVar.a == 1) {
                int i3 = bVar.b + bVar.d;
                for (int i4 = bVar.b; i4 < i3; i4++) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void f(b bVar) {
        g(bVar);
    }

    private void g(b bVar) {
        this.b.add(bVar);
        int i = bVar.a;
        if (i == 4) {
            this.c.a(bVar.b, bVar.d, bVar.c);
        } else if (i != 8) {
            switch (i) {
                case 1:
                    this.c.c(bVar.b, bVar.d);
                    return;
                case 2:
                    this.c.b(bVar.b, bVar.d);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown update op type for " + bVar);
            }
        } else {
            this.c.d(bVar.b, bVar.d);
        }
    }

    public boolean d() {
        return this.a.size() > 0;
    }

    public boolean a(int i) {
        return (i & this.h) != 0;
    }

    public int b(int i) {
        return a(i, 0);
    }

    int a(int i, int i2) {
        int size = this.b.size();
        while (i2 < size) {
            b bVar = this.b.get(i2);
            if (bVar.a == 8) {
                if (bVar.b == i) {
                    i = bVar.d;
                } else {
                    if (bVar.b < i) {
                        i--;
                    }
                    if (bVar.d <= i) {
                        i++;
                    }
                }
            } else if (bVar.b > i) {
                continue;
            } else if (bVar.a == 2) {
                if (i < bVar.b + bVar.d) {
                    return -1;
                }
                i -= bVar.d;
            } else if (bVar.a == 1) {
                i += bVar.d;
            }
            i2++;
        }
        return i;
    }

    public boolean a(int i, int i2, Object obj) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(4, i, i2, obj));
        this.h |= 4;
        return this.a.size() == 1;
    }

    public boolean b(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(1, i, i2, null));
        this.h |= 1;
        return this.a.size() == 1;
    }

    public boolean c(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(2, i, i2, null));
        this.h |= 2;
        return this.a.size() == 1;
    }

    public boolean a(int i, int i2, int i3) {
        if (i == i2) {
            return false;
        }
        if (i3 == 1) {
            this.a.add(a(8, i, i2, null));
            this.h |= 8;
            return this.a.size() == 1;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    public void e() {
        c();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.a.get(i);
            int i2 = bVar.a;
            if (i2 == 4) {
                this.c.b(bVar);
                this.c.a(bVar.b, bVar.d, bVar.c);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        this.c.b(bVar);
                        this.c.c(bVar.b, bVar.d);
                        break;
                    case 2:
                        this.c.b(bVar);
                        this.c.a(bVar.b, bVar.d);
                        break;
                }
            } else {
                this.c.b(bVar);
                this.c.d(bVar.b, bVar.d);
            }
            Runnable runnable = this.d;
            if (runnable != null) {
                runnable.run();
            }
        }
        a(this.a);
        this.h = 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0047, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int c(int r6) {
        /*
            r5 = this;
            java.util.ArrayList<androidx.recyclerview.widget.a$b> r0 = r5.a
            int r0 = r0.size()
            r1 = 0
        L_0x0007:
            if (r1 >= r0) goto L_0x004a
            java.util.ArrayList<androidx.recyclerview.widget.a$b> r2 = r5.a
            java.lang.Object r2 = r2.get(r1)
            androidx.recyclerview.widget.a$b r2 = (androidx.recyclerview.widget.a.b) r2
            int r3 = r2.a
            r4 = 8
            if (r3 == r4) goto L_0x0034
            switch(r3) {
                case 1: goto L_0x002c;
                case 2: goto L_0x001b;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x0047
        L_0x001b:
            int r3 = r2.b
            if (r3 > r6) goto L_0x0047
            int r3 = r2.b
            int r4 = r2.d
            int r3 = r3 + r4
            if (r3 <= r6) goto L_0x0028
            r6 = -1
            return r6
        L_0x0028:
            int r2 = r2.d
            int r6 = r6 - r2
            goto L_0x0047
        L_0x002c:
            int r3 = r2.b
            if (r3 > r6) goto L_0x0047
            int r2 = r2.d
            int r6 = r6 + r2
            goto L_0x0047
        L_0x0034:
            int r3 = r2.b
            if (r3 != r6) goto L_0x003b
            int r6 = r2.d
            goto L_0x0047
        L_0x003b:
            int r3 = r2.b
            if (r3 >= r6) goto L_0x0041
            int r6 = r6 + (-1)
        L_0x0041:
            int r2 = r2.d
            if (r2 > r6) goto L_0x0047
            int r6 = r6 + 1
        L_0x0047:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x004a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.a.c(int):int");
    }

    public boolean f() {
        return !this.b.isEmpty() && !this.a.isEmpty();
    }

    /* compiled from: AdapterHelper.java */
    /* loaded from: classes.dex */
    public static final class b {
        int a;
        int b;
        Object c;
        int d;

        b(int i, int i2, int i3, Object obj) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = obj;
        }

        String a() {
            int i = this.a;
            if (i == 4) {
                return "up";
            }
            if (i == 8) {
                return "mv";
            }
            switch (i) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                default:
                    return "??";
            }
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + a() + ",s:" + this.b + "c:" + this.d + ",p:" + this.c + "]";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            int i = this.a;
            if (i != bVar.a) {
                return false;
            }
            if (i == 8 && Math.abs(this.d - this.b) == 1 && this.d == bVar.b && this.b == bVar.d) {
                return true;
            }
            if (this.d != bVar.d || this.b != bVar.b) {
                return false;
            }
            Object obj2 = this.c;
            if (obj2 != null) {
                if (!obj2.equals(bVar.c)) {
                    return false;
                }
            } else if (bVar.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }
    }

    @Override // androidx.recyclerview.widget.j.a
    public b a(int i, int i2, int i3, Object obj) {
        b acquire = this.g.acquire();
        if (acquire == null) {
            return new b(i, i2, i3, obj);
        }
        acquire.a = i;
        acquire.b = i2;
        acquire.d = i3;
        acquire.c = obj;
        return acquire;
    }

    @Override // androidx.recyclerview.widget.j.a
    public void a(b bVar) {
        if (!this.e) {
            bVar.c = null;
            this.g.release(bVar);
        }
    }

    void a(List<b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i));
        }
        list.clear();
    }
}
