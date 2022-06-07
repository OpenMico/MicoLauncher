package java8.util.stream;

/* compiled from: AbstractSpinedBuffer.java */
/* loaded from: classes5.dex */
abstract class f {
    protected final int a;
    protected int b;
    protected int c;
    protected long[] d;

    public abstract void b();

    /* JADX INFO: Access modifiers changed from: protected */
    public f() {
        this.a = 4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public f(int i) {
        if (i >= 0) {
            this.a = Math.max(4, 32 - Integer.numberOfLeadingZeros(i - 1));
            return;
        }
        throw new IllegalArgumentException("Illegal Capacity: " + i);
    }

    public long a() {
        int i = this.c;
        return i == 0 ? this.b : this.d[i] + this.b;
    }

    protected int a(int i) {
        int i2;
        if (i == 0 || i == 1) {
            i2 = this.a;
        } else {
            i2 = Math.min((this.a + i) - 1, 30);
        }
        return 1 << i2;
    }
}
