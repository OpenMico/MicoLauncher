package java8.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;
import java8.util.function.LongConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SpinedBuffer.java */
/* loaded from: classes5.dex */
public class gn<E> extends f implements Consumer<E> {
    protected E[] e = (E[]) new Object[1 << this.a];
    protected E[][] f;

    protected long d() {
        return this.c == 0 ? this.e.length : this.d[this.c] + this.f[this.c].length;
    }

    private void c() {
        if (this.f == null) {
            this.f = (E[][]) new Object[8];
            this.d = new long[8];
            this.f[0] = this.e;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void a(long j) {
        long d2 = d();
        if (j > d2) {
            c();
            int i = this.c;
            while (true) {
                i++;
                if (j > d2) {
                    E[][] eArr = this.f;
                    if (i >= eArr.length) {
                        int length = eArr.length * 2;
                        this.f = (E[][]) ((Object[][]) Arrays.copyOf(eArr, length));
                        this.d = Arrays.copyOf(this.d, length);
                    }
                    int a2 = a(i);
                    this.f[i] = new Object[a2];
                    int i2 = i - 1;
                    this.d[i] = this.d[i2] + this.f[i2].length;
                    d2 += a2;
                } else {
                    return;
                }
            }
        }
    }

    protected void e() {
        a(d() + 1);
    }

    public E b(long j) {
        if (this.c == 0) {
            if (j < this.b) {
                return this.e[(int) j];
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        } else if (j < a()) {
            for (int i = 0; i <= this.c; i++) {
                long j2 = this.d[i];
                E[][] eArr = this.f;
                if (j < j2 + eArr[i].length) {
                    return eArr[i][(int) (j - this.d[i])];
                }
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        } else {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
    }

    public void a(E[] eArr, int i) {
        long j = i;
        long a2 = a() + j;
        if (a2 > eArr.length || a2 < j) {
            throw new IndexOutOfBoundsException("does not fit");
        } else if (this.c == 0) {
            System.arraycopy(this.e, 0, eArr, i, this.b);
        } else {
            int i2 = i;
            for (int i3 = 0; i3 < this.c; i3++) {
                E[][] eArr2 = this.f;
                System.arraycopy(eArr2[i3], 0, eArr, i2, eArr2[i3].length);
                i2 += this.f[i3].length;
            }
            if (this.b > 0) {
                System.arraycopy(this.e, 0, eArr, i2, this.b);
            }
        }
    }

    public E[] asArray(IntFunction<E[]> intFunction) {
        long a2 = a();
        if (a2 < 2147483639) {
            E[] apply = intFunction.apply((int) a2);
            a(apply, 0);
            return apply;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    @Override // java8.util.stream.f
    public void b() {
        E[][] eArr = this.f;
        if (eArr != null) {
            this.e = eArr[0];
            int i = 0;
            while (true) {
                E[] eArr2 = this.e;
                if (i >= eArr2.length) {
                    break;
                }
                eArr2[i] = null;
                i++;
            }
            this.f = (E[][]) null;
            this.d = null;
        } else {
            for (int i2 = 0; i2 < this.b; i2++) {
                this.e[i2] = null;
            }
        }
        this.b = 0;
        this.c = 0;
    }

    public void forEach(Consumer<? super E> consumer) {
        for (int i = 0; i < this.c; i++) {
            for (E e2 : this.f[i]) {
                consumer.accept((Object) e2);
            }
        }
        for (int i2 = 0; i2 < this.b; i2++) {
            consumer.accept((Object) this.e[i2]);
        }
    }

    public void accept(E e2) {
        if (this.b == this.e.length) {
            c();
            int i = this.c + 1;
            E[][] eArr = this.f;
            if (i >= eArr.length || eArr[this.c + 1] == null) {
                e();
            }
            this.b = 0;
            this.c++;
            this.e = this.f[this.c];
        }
        E[] eArr2 = this.e;
        int i2 = this.b;
        this.b = i2 + 1;
        eArr2[i2] = e2;
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        arrayList.getClass();
        forEach(go.a(arrayList));
        return "SpinedBuffer:" + arrayList.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SpinedBuffer.java */
    /* loaded from: classes5.dex */
    public class a implements Spliterator<E> {
        int a;
        final int b;
        int c;
        final int d;
        E[] e;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 16464;
        }

        a(int i, int i2, int i3, int i4) {
            gn.this = r1;
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = r1.f == null ? r1.e : r1.f[i];
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.a == this.b ? this.d - this.c : ((gn.this.d[this.b] + this.d) - gn.this.d[this.a]) - this.c;
        }

        @Override // java8.util.Spliterator
        public long getExactSizeIfKnown() {
            return Spliterators.getExactSizeIfKnown(this);
        }

        @Override // java8.util.Spliterator
        public boolean hasCharacteristics(int i) {
            return Spliterators.hasCharacteristics(this, i);
        }

        @Override // java8.util.Spliterator
        public Comparator<? super E> getComparator() {
            return Spliterators.getComparator(this);
        }

        @Override // java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            int i = this.a;
            int i2 = this.b;
            if (i >= i2 && (i != i2 || this.c >= this.d)) {
                return false;
            }
            int i3 = this.c;
            this.c = i3 + 1;
            consumer.accept((Object) this.e[i3]);
            if (this.c == this.e.length) {
                this.c = 0;
                this.a++;
                if (gn.this.f != null && this.a <= this.b) {
                    this.e = gn.this.f[this.a];
                }
            }
            return true;
        }

        @Override // java8.util.Spliterator
        public void forEachRemaining(Consumer<? super E> consumer) {
            int i;
            Objects.requireNonNull(consumer);
            int i2 = this.a;
            int i3 = this.b;
            if (i2 < i3 || (i2 == i3 && this.c < this.d)) {
                int i4 = this.c;
                int i5 = this.a;
                while (true) {
                    i = this.b;
                    if (i5 >= i) {
                        break;
                    }
                    E[] eArr = gn.this.f[i5];
                    while (i4 < eArr.length) {
                        consumer.accept((Object) eArr[i4]);
                        i4++;
                    }
                    i4 = 0;
                    i5++;
                }
                E[] eArr2 = this.a == i ? this.e : gn.this.f[this.b];
                int i6 = this.d;
                while (i4 < i6) {
                    consumer.accept((Object) eArr2[i4]);
                    i4++;
                }
                this.a = this.b;
                this.c = this.d;
            }
        }

        @Override // java8.util.Spliterator
        public Spliterator<E> trySplit() {
            int i = this.a;
            int i2 = this.b;
            if (i < i2) {
                gn gnVar = gn.this;
                a aVar = new a(i, i2 - 1, this.c, gnVar.f[this.b - 1].length);
                this.a = this.b;
                this.c = 0;
                this.e = gn.this.f[this.a];
                return aVar;
            } else if (i != i2) {
                return null;
            } else {
                int i3 = this.d;
                int i4 = this.c;
                int i5 = (i3 - i4) / 2;
                if (i5 == 0) {
                    return null;
                }
                Spliterator<E> spliterator = J8Arrays.spliterator(this.e, i4, i4 + i5);
                this.c += i5;
                return spliterator;
            }
        }
    }

    public Spliterator<E> f_() {
        return new a(0, this.c, 0, this.b);
    }

    /* compiled from: SpinedBuffer.java */
    /* loaded from: classes5.dex */
    public static abstract class e<E, T_ARR, T_CONS> extends f {
        T_ARR e = newArray(1 << this.a);
        T_ARR[] f;

        protected abstract int a(T_ARR t_arr);

        protected abstract void a(T_ARR t_arr, int i, int i2, T_CONS t_cons);

        protected abstract T_ARR[] d(int i);

        public abstract T_ARR newArray(int i);

        e(int i) {
            super(i);
        }

        e() {
        }

        protected long f() {
            if (this.c == 0) {
                return a((e<E, T_ARR, T_CONS>) this.e);
            }
            return this.d[this.c] + a((e<E, T_ARR, T_CONS>) this.f[this.c]);
        }

        private void c() {
            if (this.f == null) {
                this.f = d(8);
                this.d = new long[8];
                this.f[0] = this.e;
            }
        }

        protected final void b(long j) {
            long f = f();
            if (j > f) {
                c();
                int i = this.c;
                while (true) {
                    i++;
                    if (j > f) {
                        T_ARR[] t_arrArr = this.f;
                        if (i >= t_arrArr.length) {
                            int length = t_arrArr.length * 2;
                            this.f = (T_ARR[]) Arrays.copyOf(t_arrArr, length);
                            this.d = Arrays.copyOf(this.d, length);
                        }
                        int a2 = a(i);
                        this.f[i] = newArray(a2);
                        int i2 = i - 1;
                        this.d[i] = this.d[i2] + a((e<E, T_ARR, T_CONS>) this.f[i2]);
                        f += a2;
                    } else {
                        return;
                    }
                }
            }
        }

        protected void g() {
            b(f() + 1);
        }

        protected int c(long j) {
            if (this.c == 0) {
                if (j < this.b) {
                    return 0;
                }
                throw new IndexOutOfBoundsException(Long.toString(j));
            } else if (j < a()) {
                for (int i = 0; i <= this.c; i++) {
                    if (j < this.d[i] + a((e<E, T_ARR, T_CONS>) this.f[i])) {
                        return i;
                    }
                }
                throw new IndexOutOfBoundsException(Long.toString(j));
            } else {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
        }

        public void copyInto(T_ARR t_arr, int i) {
            long j = i;
            long a2 = a() + j;
            if (a2 > a((e<E, T_ARR, T_CONS>) t_arr) || a2 < j) {
                throw new IndexOutOfBoundsException("does not fit");
            } else if (this.c == 0) {
                System.arraycopy(this.e, 0, t_arr, i, this.b);
            } else {
                int i2 = i;
                for (int i3 = 0; i3 < this.c; i3++) {
                    T_ARR[] t_arrArr = this.f;
                    System.arraycopy(t_arrArr[i3], 0, t_arr, i2, a((e<E, T_ARR, T_CONS>) t_arrArr[i3]));
                    i2 += a((e<E, T_ARR, T_CONS>) this.f[i3]);
                }
                if (this.b > 0) {
                    System.arraycopy(this.e, 0, t_arr, i2, this.b);
                }
            }
        }

        public T_ARR asPrimitiveArray() {
            long a2 = a();
            if (a2 < 2147483639) {
                T_ARR newArray = newArray((int) a2);
                copyInto(newArray, 0);
                return newArray;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        protected void h() {
            if (this.b == a((e<E, T_ARR, T_CONS>) this.e)) {
                c();
                int i = this.c + 1;
                T_ARR[] t_arrArr = this.f;
                if (i >= t_arrArr.length || t_arrArr[this.c + 1] == null) {
                    g();
                }
                this.b = 0;
                this.c++;
                this.e = this.f[this.c];
            }
        }

        @Override // java8.util.stream.f
        public void b() {
            T_ARR[] t_arrArr = this.f;
            if (t_arrArr != null) {
                this.e = t_arrArr[0];
                this.f = null;
                this.d = null;
            }
            this.b = 0;
            this.c = 0;
        }

        public void forEach(T_CONS t_cons) {
            for (int i = 0; i < this.c; i++) {
                T_ARR[] t_arrArr = this.f;
                a(t_arrArr[i], 0, a((e<E, T_ARR, T_CONS>) t_arrArr[i]), t_cons);
            }
            a(this.e, 0, this.b, t_cons);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: SpinedBuffer.java */
        /* loaded from: classes5.dex */
        public abstract class a<T_SPLITR extends Spliterator.OfPrimitive<E, T_CONS, T_SPLITR>> implements Spliterator.OfPrimitive<E, T_CONS, T_SPLITR> {
            int b;
            final int c;
            int d;
            final int e;
            T_ARR f;

            abstract T_SPLITR a(T_ARR t_arr, int i, int i2);

            abstract void a(T_ARR t_arr, int i, T_CONS t_cons);

            abstract T_SPLITR b(int i, int i2, int i3, int i4);

            @Override // java8.util.Spliterator
            public int characteristics() {
                return 16464;
            }

            a(int i, int i2, int i3, int i4) {
                e.this = r1;
                this.b = i;
                this.c = i2;
                this.d = i3;
                this.e = i4;
                this.f = r1.f == null ? r1.e : r1.f[i];
            }

            @Override // java8.util.Spliterator
            public long estimateSize() {
                return this.b == this.c ? this.e - this.d : ((e.this.d[this.c] + this.e) - e.this.d[this.b]) - this.d;
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public boolean tryAdvance(T_CONS t_cons) {
                Objects.requireNonNull(t_cons);
                int i = this.b;
                int i2 = this.c;
                if (i >= i2 && (i != i2 || this.d >= this.e)) {
                    return false;
                }
                T_ARR t_arr = this.f;
                int i3 = this.d;
                this.d = i3 + 1;
                a((a<T_SPLITR>) t_arr, i3, (int) t_cons);
                if (this.d == e.this.a((e) this.f)) {
                    this.d = 0;
                    this.b++;
                    if (e.this.f != null && this.b <= this.c) {
                        this.f = e.this.f[this.b];
                    }
                }
                return true;
            }

            @Override // java8.util.Spliterator.OfPrimitive
            public void forEachRemaining(T_CONS t_cons) {
                int i;
                Objects.requireNonNull(t_cons);
                int i2 = this.b;
                int i3 = this.c;
                if (i2 < i3 || (i2 == i3 && this.d < this.e)) {
                    int i4 = this.d;
                    int i5 = this.b;
                    while (true) {
                        i = this.c;
                        if (i5 >= i) {
                            break;
                        }
                        T_ARR t_arr = e.this.f[i5];
                        e eVar = e.this;
                        eVar.a(t_arr, i4, eVar.a((e) t_arr), t_cons);
                        i4 = 0;
                        i5++;
                    }
                    e.this.a(this.b == i ? this.f : e.this.f[this.c], i4, this.e, t_cons);
                    this.b = this.c;
                    this.d = this.e;
                }
            }

            @Override // java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public T_SPLITR trySplit() {
                int i = this.b;
                int i2 = this.c;
                if (i < i2) {
                    int i3 = this.d;
                    e eVar = e.this;
                    T_SPLITR b = b(i, i2 - 1, i3, eVar.a((e) eVar.f[this.c - 1]));
                    this.b = this.c;
                    this.d = 0;
                    this.f = e.this.f[this.b];
                    return b;
                } else if (i != i2) {
                    return null;
                } else {
                    int i4 = this.e;
                    int i5 = this.d;
                    int i6 = (i4 - i5) / 2;
                    if (i6 == 0) {
                        return null;
                    }
                    T_SPLITR a = a((a<T_SPLITR>) this.f, i5, i6);
                    this.d += i6;
                    return a;
                }
            }
        }
    }

    /* compiled from: SpinedBuffer.java */
    /* loaded from: classes5.dex */
    public static class c extends e<Integer, int[], IntConsumer> implements IntConsumer {
        public c() {
        }

        public c(int i) {
            super(i);
        }

        public void forEach(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                forEach((c) consumer);
            } else {
                d().forEachRemaining((Consumer) consumer);
            }
        }

        /* renamed from: c */
        public int[][] d(int i) {
            return new int[i];
        }

        @Override // java8.util.stream.gn.e
        public int[] newArray(int i) {
            return new int[i];
        }

        public int a(int[] iArr) {
            return iArr.length;
        }

        public void a(int[] iArr, int i, int i2, IntConsumer intConsumer) {
            while (i < i2) {
                intConsumer.accept(iArr[i]);
                i++;
            }
        }

        public void accept(int i) {
            h();
            int i2 = this.b;
            this.b = i2 + 1;
            ((int[]) this.e)[i2] = i;
        }

        public int a(long j) {
            int c = c(j);
            if (this.c == 0 && c == 0) {
                return ((int[]) this.e)[(int) j];
            }
            return ((int[][]) this.f)[c][(int) (j - this.d[c])];
        }

        /* compiled from: SpinedBuffer.java */
        /* loaded from: classes5.dex */
        public class a extends e<Integer, int[], IntConsumer>.a implements Spliterator.OfInt {
            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((a) intConsumer);
            }

            @Override // java8.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((a) intConsumer);
            }

            @Override // java8.util.stream.gn.e.a, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
                c.this = r1;
            }

            /* renamed from: a */
            public a b(int i, int i2, int i3, int i4) {
                return new a(i, i2, i3, i4);
            }

            public void a(int[] iArr, int i, IntConsumer intConsumer) {
                intConsumer.accept(iArr[i]);
            }

            public Spliterator.OfInt a(int[] iArr, int i, int i2) {
                return J8Arrays.spliterator(iArr, i, i2 + i);
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator
            public Comparator<? super Integer> getComparator() {
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Integer> consumer) {
                return Spliterators.OfInt.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Integer> consumer) {
                Spliterators.OfInt.forEachRemaining(this, consumer);
            }
        }

        public Spliterator.OfInt d() {
            return new a(0, this.c, 0, this.b);
        }

        public String toString() {
            int[] asPrimitiveArray = asPrimitiveArray();
            if (asPrimitiveArray.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(asPrimitiveArray));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(asPrimitiveArray, 200)));
        }
    }

    /* compiled from: SpinedBuffer.java */
    /* loaded from: classes5.dex */
    public static class d extends e<Long, long[], LongConsumer> implements LongConsumer {
        public d() {
        }

        public d(int i) {
            super(i);
        }

        public void forEach(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                forEach((d) consumer);
            } else {
                d().forEachRemaining((Consumer) consumer);
            }
        }

        /* renamed from: c */
        public long[][] d(int i) {
            return new long[i];
        }

        @Override // java8.util.stream.gn.e
        public long[] newArray(int i) {
            return new long[i];
        }

        public int a(long[] jArr) {
            return jArr.length;
        }

        public void a(long[] jArr, int i, int i2, LongConsumer longConsumer) {
            while (i < i2) {
                longConsumer.accept(jArr[i]);
                i++;
            }
        }

        public void accept(long j) {
            h();
            int i = this.b;
            this.b = i + 1;
            ((long[]) this.e)[i] = j;
        }

        public long a(long j) {
            int c = c(j);
            if (this.c == 0 && c == 0) {
                return ((long[]) this.e)[(int) j];
            }
            return ((long[][]) this.f)[c][(int) (j - this.d[c])];
        }

        /* compiled from: SpinedBuffer.java */
        /* loaded from: classes5.dex */
        public class a extends e<Long, long[], LongConsumer>.a implements Spliterator.OfLong {
            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((a) longConsumer);
            }

            @Override // java8.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((a) longConsumer);
            }

            @Override // java8.util.stream.gn.e.a, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
                d.this = r1;
            }

            /* renamed from: a */
            public a b(int i, int i2, int i3, int i4) {
                return new a(i, i2, i3, i4);
            }

            public void a(long[] jArr, int i, LongConsumer longConsumer) {
                longConsumer.accept(jArr[i]);
            }

            public Spliterator.OfLong a(long[] jArr, int i, int i2) {
                return J8Arrays.spliterator(jArr, i, i2 + i);
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator
            public Comparator<? super Long> getComparator() {
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Long> consumer) {
                return Spliterators.OfLong.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Long> consumer) {
                Spliterators.OfLong.forEachRemaining(this, consumer);
            }
        }

        public Spliterator.OfLong d() {
            return new a(0, this.c, 0, this.b);
        }

        public String toString() {
            long[] asPrimitiveArray = asPrimitiveArray();
            if (asPrimitiveArray.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(asPrimitiveArray));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(asPrimitiveArray, 200)));
        }
    }

    /* compiled from: SpinedBuffer.java */
    /* loaded from: classes5.dex */
    public static class b extends e<Double, double[], DoubleConsumer> implements DoubleConsumer {
        public b() {
        }

        public b(int i) {
            super(i);
        }

        public void forEach(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                forEach((b) consumer);
            } else {
                d().forEachRemaining((Consumer) consumer);
            }
        }

        /* renamed from: c */
        public double[][] d(int i) {
            return new double[i];
        }

        @Override // java8.util.stream.gn.e
        public double[] newArray(int i) {
            return new double[i];
        }

        public int a(double[] dArr) {
            return dArr.length;
        }

        public void a(double[] dArr, int i, int i2, DoubleConsumer doubleConsumer) {
            while (i < i2) {
                doubleConsumer.accept(dArr[i]);
                i++;
            }
        }

        public void accept(double d) {
            h();
            int i = this.b;
            this.b = i + 1;
            ((double[]) this.e)[i] = d;
        }

        public double a(long j) {
            int c = c(j);
            if (this.c == 0 && c == 0) {
                return ((double[]) this.e)[(int) j];
            }
            return ((double[][]) this.f)[c][(int) (j - this.d[c])];
        }

        /* compiled from: SpinedBuffer.java */
        /* loaded from: classes5.dex */
        public class a extends e<Double, double[], DoubleConsumer>.a implements Spliterator.OfDouble {
            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((a) doubleConsumer);
            }

            @Override // java8.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((a) doubleConsumer);
            }

            @Override // java8.util.stream.gn.e.a, java8.util.Spliterator.OfPrimitive, java8.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(int i, int i2, int i3, int i4) {
                super(i, i2, i3, i4);
                b.this = r1;
            }

            /* renamed from: a */
            public a b(int i, int i2, int i3, int i4) {
                return new a(i, i2, i3, i4);
            }

            public void a(double[] dArr, int i, DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(dArr[i]);
            }

            public Spliterator.OfDouble a(double[] dArr, int i, int i2) {
                return J8Arrays.spliterator(dArr, i, i2 + i);
            }

            @Override // java8.util.Spliterator
            public long getExactSizeIfKnown() {
                return Spliterators.getExactSizeIfKnown(this);
            }

            @Override // java8.util.Spliterator
            public boolean hasCharacteristics(int i) {
                return Spliterators.hasCharacteristics(this, i);
            }

            @Override // java8.util.Spliterator
            public Comparator<? super Double> getComparator() {
                return Spliterators.getComparator(this);
            }

            @Override // java8.util.Spliterator
            public boolean tryAdvance(Consumer<? super Double> consumer) {
                return Spliterators.OfDouble.tryAdvance(this, consumer);
            }

            @Override // java8.util.Spliterator
            public void forEachRemaining(Consumer<? super Double> consumer) {
                Spliterators.OfDouble.forEachRemaining(this, consumer);
            }
        }

        public Spliterator.OfDouble d() {
            return new a(0, this.c, 0, this.b);
        }

        public String toString() {
            double[] asPrimitiveArray = asPrimitiveArray();
            if (asPrimitiveArray.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(asPrimitiveArray));
            }
            return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(asPrimitiveArray.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(asPrimitiveArray, 200)));
        }
    }
}
