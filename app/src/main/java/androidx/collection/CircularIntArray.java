package androidx.collection;

/* loaded from: classes.dex */
public final class CircularIntArray {
    private int[] a;
    private int b;
    private int c;
    private int d;

    private void a() {
        int[] iArr = this.a;
        int length = iArr.length;
        int i = this.b;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 >= 0) {
            int[] iArr2 = new int[i3];
            System.arraycopy(iArr, i, iArr2, 0, i2);
            System.arraycopy(this.a, 0, iArr2, i2, this.b);
            this.a = iArr2;
            this.b = 0;
            this.c = length;
            this.d = i3 - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public CircularIntArray() {
        this(8);
    }

    public CircularIntArray(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if (i <= 1073741824) {
            i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
            this.d = i - 1;
            this.a = new int[i];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(int i) {
        this.b = (this.b - 1) & this.d;
        int[] iArr = this.a;
        int i2 = this.b;
        iArr[i2] = i;
        if (i2 == this.c) {
            a();
        }
    }

    public void addLast(int i) {
        int[] iArr = this.a;
        int i2 = this.c;
        iArr[i2] = i;
        this.c = this.d & (i2 + 1);
        if (this.c == this.b) {
            a();
        }
    }

    public int popFirst() {
        int i = this.b;
        if (i != this.c) {
            int i2 = this.a[i];
            this.b = (i + 1) & this.d;
            return i2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int popLast() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            int i3 = this.d & (i2 - 1);
            int i4 = this.a[i3];
            this.c = i3;
            return i4;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        this.c = this.b;
    }

    public void removeFromStart(int i) {
        if (i > 0) {
            if (i <= size()) {
                this.b = this.d & (this.b + i);
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void removeFromEnd(int i) {
        if (i > 0) {
            if (i <= size()) {
                this.c = this.d & (this.c - i);
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getFirst() {
        int i = this.b;
        if (i != this.c) {
            return this.a[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getLast() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            return this.a[(i2 - 1) & this.d];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int get(int i) {
        if (i < 0 || i >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.a[this.d & (this.b + i)];
    }

    public int size() {
        return (this.c - this.b) & this.d;
    }

    public boolean isEmpty() {
        return this.b == this.c;
    }
}
