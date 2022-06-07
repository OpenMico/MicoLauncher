package java8.util;

import java.util.Comparator;
import java8.util.concurrent.CountedCompleter;

/* compiled from: ArraysParallelSortHelpers.java */
/* loaded from: classes5.dex */
final class e {

    /* compiled from: ArraysParallelSortHelpers.java */
    /* loaded from: classes5.dex */
    static final class a extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;

        @Override // java8.util.concurrent.CountedCompleter
        public final void compute() {
        }

        a(CountedCompleter<?> countedCompleter) {
            super(countedCompleter);
        }
    }

    /* compiled from: ArraysParallelSortHelpers.java */
    /* loaded from: classes5.dex */
    static final class c extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;
        final CountedCompleter<?> task;

        @Override // java8.util.concurrent.CountedCompleter
        public final void compute() {
        }

        c(CountedCompleter<?> countedCompleter) {
            super(null, 1);
            this.task = countedCompleter;
        }

        @Override // java8.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            this.task.compute();
        }
    }

    /* compiled from: ArraysParallelSortHelpers.java */
    /* loaded from: classes5.dex */
    static final class b {

        /* compiled from: ArraysParallelSortHelpers.java */
        /* renamed from: java8.util.e$b$b  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        static final class C0351b<T> extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a;
            final int base;
            Comparator<? super T> comparator;
            final int gran;
            final int size;
            final T[] w;
            final int wbase;

            /* JADX INFO: Access modifiers changed from: package-private */
            public C0351b(CountedCompleter<?> countedCompleter, T[] tArr, T[] tArr2, int i, int i2, int i3, int i4, Comparator<? super T> comparator) {
                super(countedCompleter);
                this.a = tArr;
                this.w = tArr2;
                this.base = i;
                this.size = i2;
                this.wbase = i3;
                this.gran = i4;
                this.comparator = comparator;
            }

            @Override // java8.util.concurrent.CountedCompleter
            public final void compute() {
                Comparator<? super T> comparator = this.comparator;
                T[] tArr = this.a;
                T[] tArr2 = this.w;
                int i = this.base;
                int i2 = this.size;
                int i3 = this.wbase;
                int i4 = this.gran;
                CountedCompleter countedCompleter = this;
                int i5 = i2;
                while (i5 > i4) {
                    int i6 = i5 >>> 1;
                    int i7 = i6 >>> 1;
                    int i8 = i6 + i7;
                    int i9 = i3 + i6;
                    c cVar = new c(new a(countedCompleter, tArr2, tArr, i3, i6, i9, i5 - i6, i, i4, comparator));
                    int i10 = i + i6;
                    int i11 = i + i8;
                    int i12 = i5 - i8;
                    c cVar2 = new c(new a(cVar, tArr, tArr2, i10, i7, i11, i12, i9, i4, comparator));
                    new C0351b(cVar2, tArr, tArr2, i11, i12, i3 + i8, i4, comparator).fork();
                    new C0351b(cVar2, tArr, tArr2, i10, i7, i9, i4, comparator).fork();
                    int i13 = i + i7;
                    int i14 = i6 - i7;
                    c cVar3 = new c(new a(cVar, tArr, tArr2, i, i7, i13, i14, i3, i4, comparator));
                    new C0351b(cVar3, tArr, tArr2, i13, i14, i3 + i7, i4, comparator).fork();
                    countedCompleter = new a(cVar3);
                    i5 = i7;
                    tArr2 = tArr2;
                    i4 = i4;
                    i3 = i3;
                }
                ak.a(tArr, i, i + i5, comparator, tArr2, i3, i5);
                countedCompleter.tryComplete();
            }
        }

        /* compiled from: ArraysParallelSortHelpers.java */
        /* loaded from: classes5.dex */
        static final class a<T> extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a;
            Comparator<? super T> comparator;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final T[] w;
            final int wbase;

            a(CountedCompleter<?> countedCompleter, T[] tArr, T[] tArr2, int i, int i2, int i3, int i4, int i5, int i6, Comparator<? super T> comparator) {
                super(countedCompleter);
                this.a = tArr;
                this.w = tArr2;
                this.lbase = i;
                this.lsize = i2;
                this.rbase = i3;
                this.rsize = i4;
                this.wbase = i5;
                this.gran = i6;
                this.comparator = comparator;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java8.util.concurrent.CountedCompleter
            public final void compute() {
                int i;
                int i2;
                Comparator<? super T> comparator = this.comparator;
                Object[] objArr = this.a;
                T[] tArr = this.w;
                int i3 = this.lbase;
                int i4 = this.lsize;
                int i5 = this.rbase;
                int i6 = this.rsize;
                int i7 = this.wbase;
                int i8 = this.gran;
                if (objArr == null || tArr == 0 || i3 < 0 || i5 < 0 || i7 < 0 || comparator == null) {
                    throw new IllegalStateException();
                }
                while (true) {
                    int i9 = 0;
                    int i10 = 1;
                    if (i4 >= i6) {
                        if (i4 <= i8) {
                            break;
                        }
                        int i11 = i4 >>> 1;
                        Object obj = objArr[i11 + i3];
                        int i12 = i6;
                        while (i9 < i12) {
                            int i13 = (i9 + i12) >>> i10;
                            if (comparator.compare(obj, objArr[i13 + i5]) <= 0) {
                                i12 = i13;
                            } else {
                                i9 = i13 + 1;
                            }
                            i10 = 1;
                        }
                        i2 = i11;
                        i = i12;
                        a aVar = new a(this, objArr, tArr, i3 + i2, i4 - i2, i5 + i, i6 - i, i7 + i2 + i, i8, comparator);
                        addToPendingCount(1);
                        aVar.fork();
                        i4 = i2;
                        i8 = i8;
                        i6 = i;
                        i7 = i7;
                        objArr = objArr;
                        i5 = i5;
                    } else if (i6 <= i8) {
                        break;
                    } else {
                        int i14 = i6 >>> 1;
                        Object obj2 = objArr[i14 + i5];
                        int i15 = i4;
                        while (i9 < i15) {
                            int i16 = (i9 + i15) >>> 1;
                            if (comparator.compare(obj2, objArr[i16 + i3]) <= 0) {
                                i15 = i16;
                            } else {
                                i9 = i16 + 1;
                            }
                        }
                        i = i14;
                        i2 = i15;
                        a aVar2 = new a(this, objArr, tArr, i3 + i2, i4 - i2, i5 + i, i6 - i, i7 + i2 + i, i8, comparator);
                        addToPendingCount(1);
                        aVar2.fork();
                        i4 = i2;
                        i8 = i8;
                        i6 = i;
                        i7 = i7;
                        objArr = objArr;
                        i5 = i5;
                    }
                }
                int i17 = i4 + i3;
                int i18 = i6 + i5;
                while (i3 < i17 && i5 < i18) {
                    Object obj3 = objArr[i3];
                    Object obj4 = objArr[i5];
                    if (comparator.compare(obj3, obj4) <= 0) {
                        i3++;
                    } else {
                        i5++;
                        obj3 = obj4;
                    }
                    i7++;
                    tArr[i7] = obj3;
                }
                if (i5 < i18) {
                    System.arraycopy(objArr, i5, tArr, i7, i18 - i5);
                } else if (i3 < i17) {
                    System.arraycopy(objArr, i3, tArr, i7, i17 - i3);
                }
                tryComplete();
            }
        }
    }
}
