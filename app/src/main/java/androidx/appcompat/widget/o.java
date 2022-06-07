package androidx.appcompat.widget;

/* compiled from: RtlSpacingHelper.java */
/* loaded from: classes.dex */
class o {
    private int a = 0;
    private int b = 0;
    private int c = Integer.MIN_VALUE;
    private int d = Integer.MIN_VALUE;
    private int e = 0;
    private int f = 0;
    private boolean g = false;
    private boolean h = false;

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.g ? this.b : this.a;
    }

    public int d() {
        return this.g ? this.a : this.b;
    }

    public void a(int i, int i2) {
        this.c = i;
        this.d = i2;
        this.h = true;
        if (this.g) {
            if (i2 != Integer.MIN_VALUE) {
                this.a = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.b = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.b = i2;
        }
    }

    public void b(int i, int i2) {
        this.h = false;
        if (i != Integer.MIN_VALUE) {
            this.e = i;
            this.a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f = i2;
            this.b = i2;
        }
    }

    public void a(boolean z) {
        if (z != this.g) {
            this.g = z;
            if (!this.h) {
                this.a = this.e;
                this.b = this.f;
            } else if (z) {
                int i = this.d;
                if (i == Integer.MIN_VALUE) {
                    i = this.e;
                }
                this.a = i;
                int i2 = this.c;
                if (i2 == Integer.MIN_VALUE) {
                    i2 = this.f;
                }
                this.b = i2;
            } else {
                int i3 = this.c;
                if (i3 == Integer.MIN_VALUE) {
                    i3 = this.e;
                }
                this.a = i3;
                int i4 = this.d;
                if (i4 == Integer.MIN_VALUE) {
                    i4 = this.f;
                }
                this.b = i4;
            }
        }
    }
}
