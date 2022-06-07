package java8.util;

import java8.util.concurrent.CountedCompleter;
import java8.util.concurrent.ForkJoinPool;
import java8.util.function.BinaryOperator;
import java8.util.function.DoubleBinaryOperator;
import java8.util.function.IntBinaryOperator;
import java8.util.function.LongBinaryOperator;

/* compiled from: ArrayPrefixHelpers.java */
/* loaded from: classes5.dex */
class c {

    /* compiled from: ArrayPrefixHelpers.java */
    /* loaded from: classes5.dex */
    static final class a<T> extends CountedCompleter<Void> {
        final T[] array;
        final int fence;
        final BinaryOperator<T> function;
        final int hi;
        T in;
        a<T> left;
        final int lo;
        final int origin;
        T out;
        a<T> right;
        final int threshold;

        public a(a<T> aVar, BinaryOperator<T> binaryOperator, T[] tArr, int i, int i2) {
            super(aVar);
            this.function = binaryOperator;
            this.array = tArr;
            this.origin = i;
            this.lo = i;
            this.fence = i2;
            this.hi = i2;
            int commonPoolParallelism = (i2 - i) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            this.threshold = commonPoolParallelism > 16 ? commonPoolParallelism : 16;
        }

        a(a<T> aVar, BinaryOperator<T> binaryOperator, T[] tArr, int i, int i2, int i3, int i4, int i5) {
            super(aVar);
            this.function = binaryOperator;
            this.array = tArr;
            this.origin = i;
            this.fence = i2;
            this.threshold = i3;
            this.lo = i4;
            this.hi = i5;
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x0154 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0082 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0096  */
        @Override // java8.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void compute() {
            /*
                Method dump skipped, instructions count: 347
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: java8.util.c.a.compute():void");
        }
    }

    /* compiled from: ArrayPrefixHelpers.java */
    /* loaded from: classes5.dex */
    static final class d extends CountedCompleter<Void> {
        final long[] array;
        final int fence;
        final LongBinaryOperator function;
        final int hi;
        long in;
        d left;
        final int lo;
        final int origin;
        long out;
        d right;
        final int threshold;

        public d(d dVar, LongBinaryOperator longBinaryOperator, long[] jArr, int i, int i2) {
            super(dVar);
            this.function = longBinaryOperator;
            this.array = jArr;
            this.origin = i;
            this.lo = i;
            this.fence = i2;
            this.hi = i2;
            int commonPoolParallelism = (i2 - i) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            this.threshold = commonPoolParallelism > 16 ? commonPoolParallelism : 16;
        }

        d(d dVar, LongBinaryOperator longBinaryOperator, long[] jArr, int i, int i2, int i3, int i4, int i5) {
            super(dVar);
            this.function = longBinaryOperator;
            this.array = jArr;
            this.origin = i;
            this.fence = i2;
            this.threshold = i3;
            this.lo = i4;
            this.hi = i5;
        }

        /* JADX WARN: Removed duplicated region for block: B:102:0x0157 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:110:0x0082 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0095  */
        @Override // java8.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void compute() {
            /*
                Method dump skipped, instructions count: 350
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: java8.util.c.d.compute():void");
        }
    }

    /* compiled from: ArrayPrefixHelpers.java */
    /* loaded from: classes5.dex */
    static final class b extends CountedCompleter<Void> {
        final double[] array;
        final int fence;
        final DoubleBinaryOperator function;
        final int hi;
        double in;
        b left;
        final int lo;
        final int origin;
        double out;
        b right;
        final int threshold;

        public b(b bVar, DoubleBinaryOperator doubleBinaryOperator, double[] dArr, int i, int i2) {
            super(bVar);
            this.function = doubleBinaryOperator;
            this.array = dArr;
            this.origin = i;
            this.lo = i;
            this.fence = i2;
            this.hi = i2;
            int commonPoolParallelism = (i2 - i) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            this.threshold = commonPoolParallelism > 16 ? commonPoolParallelism : 16;
        }

        b(b bVar, DoubleBinaryOperator doubleBinaryOperator, double[] dArr, int i, int i2, int i3, int i4, int i5) {
            super(bVar);
            this.function = doubleBinaryOperator;
            this.array = dArr;
            this.origin = i;
            this.fence = i2;
            this.threshold = i3;
            this.lo = i4;
            this.hi = i5;
        }

        /* JADX WARN: Removed duplicated region for block: B:102:0x0157 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:110:0x0082 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0095  */
        @Override // java8.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void compute() {
            /*
                Method dump skipped, instructions count: 350
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: java8.util.c.b.compute():void");
        }
    }

    /* compiled from: ArrayPrefixHelpers.java */
    /* renamed from: java8.util.c$c  reason: collision with other inner class name */
    /* loaded from: classes5.dex */
    static final class C0349c extends CountedCompleter<Void> {
        final int[] array;
        final int fence;
        final IntBinaryOperator function;
        final int hi;
        int in;
        C0349c left;
        final int lo;
        final int origin;
        int out;
        C0349c right;
        final int threshold;

        public C0349c(C0349c cVar, IntBinaryOperator intBinaryOperator, int[] iArr, int i, int i2) {
            super(cVar);
            this.function = intBinaryOperator;
            this.array = iArr;
            this.origin = i;
            this.lo = i;
            this.fence = i2;
            this.hi = i2;
            int commonPoolParallelism = (i2 - i) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            this.threshold = commonPoolParallelism > 16 ? commonPoolParallelism : 16;
        }

        C0349c(C0349c cVar, IntBinaryOperator intBinaryOperator, int[] iArr, int i, int i2, int i3, int i4, int i5) {
            super(cVar);
            this.function = intBinaryOperator;
            this.array = iArr;
            this.origin = i;
            this.fence = i2;
            this.threshold = i3;
            this.lo = i4;
            this.hi = i5;
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x0154 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0082 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0096  */
        @Override // java8.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void compute() {
            /*
                Method dump skipped, instructions count: 347
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: java8.util.c.C0349c.compute():void");
        }
    }
}
