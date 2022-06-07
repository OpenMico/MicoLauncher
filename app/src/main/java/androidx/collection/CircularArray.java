package androidx.collection;

/* loaded from: classes.dex */
public final class CircularArray<E> {
    private E[] a;
    private int b;
    private int c;
    private int d;

    private void a() {
        E[] eArr = this.a;
        int length = eArr.length;
        int i = this.b;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 >= 0) {
            E[] eArr2 = (E[]) new Object[i3];
            System.arraycopy(eArr, i, eArr2, 0, i2);
            System.arraycopy(this.a, 0, eArr2, i2, this.b);
            this.a = eArr2;
            this.b = 0;
            this.c = length;
            this.d = i3 - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public CircularArray() {
        this(8);
    }

    public CircularArray(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        } else if (i <= 1073741824) {
            i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
            this.d = i - 1;
            this.a = (E[]) new Object[i];
        } else {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
    }

    public void addFirst(E e) {
        this.b = (this.b - 1) & this.d;
        E[] eArr = this.a;
        int i = this.b;
        eArr[i] = e;
        if (i == this.c) {
            a();
        }
    }

    public void addLast(E e) {
        E[] eArr = this.a;
        int i = this.c;
        eArr[i] = e;
        this.c = this.d & (i + 1);
        if (this.c == this.b) {
            a();
        }
    }

    public E popFirst() {
        int i = this.b;
        if (i != this.c) {
            E[] eArr = this.a;
            E e = eArr[i];
            eArr[i] = null;
            this.b = (i + 1) & this.d;
            return e;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E popLast() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            int i3 = this.d & (i2 - 1);
            E[] eArr = this.a;
            E e = eArr[i3];
            eArr[i3] = null;
            this.c = i3;
            return e;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void clear() {
        removeFromStart(size());
    }

    public void removeFromStart(int i) {
        if (i > 0) {
            if (i <= size()) {
                int length = this.a.length;
                int i2 = this.b;
                if (i < length - i2) {
                    length = i2 + i;
                }
                for (int i3 = this.b; i3 < length; i3++) {
                    this.a[i3] = null;
                }
                int i4 = this.b;
                int i5 = length - i4;
                int i6 = i - i5;
                this.b = this.d & (i4 + i5);
                if (i6 > 0) {
                    for (int i7 = 0; i7 < i6; i7++) {
                        this.a[i7] = null;
                    }
                    this.b = i6;
                    return;
                }
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void removeFromEnd(int i) {
        int i2;
        if (i > 0) {
            if (i <= size()) {
                int i3 = 0;
                int i4 = this.c;
                if (i < i4) {
                    i3 = i4 - i;
                }
                int i5 = i3;
                while (true) {
                    i2 = this.c;
                    if (i5 >= i2) {
                        break;
                    }
                    this.a[i5] = null;
                    i5++;
                }
                int i6 = i2 - i3;
                int i7 = i - i6;
                this.c = i2 - i6;
                if (i7 > 0) {
                    this.c = this.a.length;
                    int i8 = this.c - i7;
                    for (int i9 = i8; i9 < this.c; i9++) {
                        this.a[i9] = null;
                    }
                    this.c = i8;
                    return;
                }
                return;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public E getFirst() {
        int i = this.b;
        if (i != this.c) {
            return this.a[i];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E getLast() {
        int i = this.b;
        int i2 = this.c;
        if (i != i2) {
            return this.a[(i2 - 1) & this.d];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public E get(int i) {
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
