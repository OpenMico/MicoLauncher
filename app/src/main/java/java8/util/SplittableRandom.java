package java8.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;
import java8.util.Spliterator;
import java8.util.Spliterators;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;
import java8.util.stream.DoubleStream;
import java8.util.stream.IntStream;
import java8.util.stream.LongStream;
import java8.util.stream.StreamSupport;

/* loaded from: classes5.dex */
public final class SplittableRandom {
    private static final AtomicLong c = new AtomicLong(a(System.currentTimeMillis()) ^ a(System.nanoTime()));
    private long a;
    private final long b;

    private static long a(long j) {
        long j2 = (j ^ (j >>> 30)) * (-4658895280553007687L);
        long j3 = (j2 ^ (j2 >>> 27)) * (-7723592293110705685L);
        return j3 ^ (j3 >>> 31);
    }

    private static int b(long j) {
        long j2 = (j ^ (j >>> 33)) * 7109453100751455733L;
        return (int) (((j2 ^ (j2 >>> 28)) * (-3808689974395783757L)) >>> 32);
    }

    private SplittableRandom(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    private static long c(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
        long j4 = (j3 ^ (j3 >>> 33)) | 1;
        return Long.bitCount((j4 >>> 1) ^ j4) < 24 ? j4 ^ (-6148914691236517206L) : j4;
    }

    private long a() {
        long j = this.a + this.b;
        this.a = j;
        return j;
    }

    static {
        if (((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() { // from class: java8.util.SplittableRandom.1
            /* renamed from: a */
            public Boolean run() {
                return Boolean.valueOf(Boolean.getBoolean("java.util.secureRandomSeed"));
            }
        })).booleanValue()) {
            byte[] seed = SecureRandom.getSeed(8);
            long j = seed[0] & 255;
            for (int i = 1; i < 8; i++) {
                j = (j << 8) | (seed[i] & 255);
            }
            c.set(j);
        }
    }

    final long a(long j, long j2) {
        long a2 = a(a());
        if (j >= j2) {
            return a2;
        }
        long j3 = j2 - j;
        long j4 = j3 - 1;
        if ((j3 & j4) == 0) {
            return (a2 & j4) + j;
        }
        if (j3 > 0) {
            while (true) {
                long j5 = a2 >>> 1;
                long j6 = j5 + j4;
                long j7 = j5 % j3;
                if (j6 - j7 >= 0) {
                    return j7 + j;
                }
                a2 = a(a());
            }
        } else {
            while (true) {
                if (a2 >= j && a2 < j2) {
                    return a2;
                }
                a2 = a(a());
            }
        }
    }

    final int a(int i, int i2) {
        int b2 = b(a());
        if (i >= i2) {
            return b2;
        }
        int i3 = i2 - i;
        int i4 = i3 - 1;
        if ((i3 & i4) == 0) {
            return (b2 & i4) + i;
        }
        if (i3 > 0) {
            int i5 = b2 >>> 1;
            while (true) {
                int i6 = i5 + i4;
                int i7 = i5 % i3;
                if (i6 - i7 >= 0) {
                    return i7 + i;
                }
                i5 = b(a()) >>> 1;
            }
        } else {
            while (true) {
                if (b2 >= i && b2 < i2) {
                    return b2;
                }
                b2 = b(a());
            }
        }
    }

    final double a(double d, double d2) {
        double nextLong = (nextLong() >>> 11) * 1.1102230246251565E-16d;
        if (d >= d2) {
            return nextLong;
        }
        double d3 = (nextLong * (d2 - d)) + d;
        return d3 >= d2 ? Double.longBitsToDouble(Double.doubleToLongBits(d2) - 1) : d3;
    }

    public SplittableRandom(long j) {
        this(j, -7046029254386353131L);
    }

    public SplittableRandom() {
        long andAdd = c.getAndAdd(4354685564936845354L);
        this.a = a(andAdd);
        this.b = c(andAdd - 7046029254386353131L);
    }

    public SplittableRandom split() {
        return new SplittableRandom(nextLong(), c(a()));
    }

    public void nextBytes(byte[] bArr) {
        int length = bArr.length;
        int i = length >> 3;
        int i2 = 0;
        while (true) {
            i--;
            if (i <= 0) {
                break;
            }
            long nextLong = nextLong();
            int i3 = 8;
            while (true) {
                i3--;
                if (i3 > 0) {
                    i2++;
                    bArr[i2] = (byte) nextLong;
                    nextLong >>>= 8;
                }
            }
        }
        if (i2 < length) {
            long nextLong2 = nextLong();
            while (i2 < length) {
                i2++;
                bArr[i2] = (byte) nextLong2;
                nextLong2 >>>= 8;
            }
        }
    }

    public int nextInt() {
        return b(a());
    }

    public int nextInt(int i) {
        if (i > 0) {
            int b2 = b(a());
            int i2 = i - 1;
            if ((i & i2) == 0) {
                return b2 & i2;
            }
            while (true) {
                int i3 = b2 >>> 1;
                int i4 = i3 + i2;
                int i5 = i3 % i;
                if (i4 - i5 >= 0) {
                    return i5;
                }
                b2 = b(a());
            }
        } else {
            throw new IllegalArgumentException("bound must be positive");
        }
    }

    public int nextInt(int i, int i2) {
        if (i < i2) {
            return a(i, i2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public long nextLong() {
        return a(a());
    }

    public long nextLong(long j) {
        if (j > 0) {
            long a2 = a(a());
            long j2 = j - 1;
            if ((j & j2) == 0) {
                return a2 & j2;
            }
            while (true) {
                long j3 = a2 >>> 1;
                long j4 = j3 + j2;
                long j5 = j3 % j;
                if (j4 - j5 >= 0) {
                    return j5;
                }
                a2 = a(a());
            }
        } else {
            throw new IllegalArgumentException("bound must be positive");
        }
    }

    public long nextLong(long j, long j2) {
        if (j < j2) {
            return a(j, j2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public double nextDouble() {
        return (a(a()) >>> 11) * 1.1102230246251565E-16d;
    }

    public double nextDouble(double d) {
        if (d > 0.0d) {
            double a2 = (a(a()) >>> 11) * 1.1102230246251565E-16d * d;
            return a2 < d ? a2 : Double.longBitsToDouble(Double.doubleToLongBits(d) - 1);
        }
        throw new IllegalArgumentException("bound must be positive");
    }

    public double nextDouble(double d, double d2) {
        if (d < d2) {
            return a(d, d2);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public boolean nextBoolean() {
        return b(a()) < 0;
    }

    public IntStream ints(long j) {
        if (j >= 0) {
            return StreamSupport.intStream(new b(this, 0L, j, Integer.MAX_VALUE, 0), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public IntStream ints() {
        return StreamSupport.intStream(new b(this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
    }

    public IntStream ints(long j, int i, int i2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (i < i2) {
            return StreamSupport.intStream(new b(this, 0L, j, i, i2), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    public IntStream ints(int i, int i2) {
        if (i < i2) {
            return StreamSupport.intStream(new b(this, 0L, Long.MAX_VALUE, i, i2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public LongStream longs(long j) {
        if (j >= 0) {
            return StreamSupport.longStream(new c(this, 0L, j, Long.MAX_VALUE, 0L), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public LongStream longs() {
        return StreamSupport.longStream(new c(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }

    public LongStream longs(long j, long j2, long j3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (j2 < j3) {
            return StreamSupport.longStream(new c(this, 0L, j, j2, j3), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    public LongStream longs(long j, long j2) {
        if (j < j2) {
            return StreamSupport.longStream(new c(this, 0L, Long.MAX_VALUE, j, j2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    public DoubleStream doubles(long j) {
        if (j >= 0) {
            return StreamSupport.doubleStream(new a(this, 0L, j, Double.MAX_VALUE, 0.0d), false);
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new a(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0d), false);
    }

    public DoubleStream doubles(long j, double d, double d2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        } else if (d < d2) {
            return StreamSupport.doubleStream(new a(this, 0L, j, d, d2), false);
        } else {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
    }

    public DoubleStream doubles(double d, double d2) {
        if (d < d2) {
            return StreamSupport.doubleStream(new a(this, 0L, Long.MAX_VALUE, d, d2), false);
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    /* loaded from: classes5.dex */
    private static final class b implements Spliterator.OfInt {
        final SplittableRandom a;
        long b;
        final long c;
        final int d;
        final int e;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        b(SplittableRandom splittableRandom, long j, long j2, int i, int i2) {
            this.a = splittableRandom;
            this.b = j;
            this.c = j2;
            this.d = i;
            this.e = i2;
        }

        /* renamed from: a */
        public b trySplit() {
            long j = this.b;
            long j2 = (this.c + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            SplittableRandom split = this.a.split();
            this.b = j2;
            return new b(split, j, j2, this.d, this.e);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator.OfInt
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            long j = this.b;
            if (j >= this.c) {
                return false;
            }
            intConsumer.accept(this.a.a(this.d, this.e));
            this.b = j + 1;
            return true;
        }

        @Override // java8.util.Spliterator.OfInt
        public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            long j = this.b;
            long j2 = this.c;
            if (j < j2) {
                this.b = j2;
                SplittableRandom splittableRandom = this.a;
                int i = this.d;
                int i2 = this.e;
                do {
                    intConsumer.accept(splittableRandom.a(i, i2));
                    j++;
                } while (j < j2);
            }
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

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Integer> consumer) {
            return Spliterators.OfInt.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfInt, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Integer> consumer) {
            Spliterators.OfInt.forEachRemaining(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    private static final class c implements Spliterator.OfLong {
        final SplittableRandom a;
        long b;
        final long c;
        final long d;
        final long e;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        c(SplittableRandom splittableRandom, long j, long j2, long j3, long j4) {
            this.a = splittableRandom;
            this.b = j;
            this.c = j2;
            this.d = j3;
            this.e = j4;
        }

        /* renamed from: a */
        public c trySplit() {
            long j = this.b;
            long j2 = (this.c + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            SplittableRandom split = this.a.split();
            this.b = j2;
            return new c(split, j, j2, this.d, this.e);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator.OfLong
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long j = this.b;
            if (j >= this.c) {
                return false;
            }
            longConsumer.accept(this.a.a(this.d, this.e));
            this.b = j + 1;
            return true;
        }

        @Override // java8.util.Spliterator.OfLong
        public void forEachRemaining(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long j = this.b;
            long j2 = this.c;
            if (j < j2) {
                this.b = j2;
                SplittableRandom splittableRandom = this.a;
                long j3 = this.d;
                long j4 = this.e;
                do {
                    longConsumer.accept(splittableRandom.a(j3, j4));
                    j++;
                } while (j < j2);
            }
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

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Long> consumer) {
            return Spliterators.OfLong.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfLong, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Long> consumer) {
            Spliterators.OfLong.forEachRemaining(this, consumer);
        }
    }

    /* loaded from: classes5.dex */
    private static final class a implements Spliterator.OfDouble {
        final SplittableRandom a;
        long b;
        final long c;
        final double d;
        final double e;

        @Override // java8.util.Spliterator
        public int characteristics() {
            return 17728;
        }

        a(SplittableRandom splittableRandom, long j, long j2, double d, double d2) {
            this.a = splittableRandom;
            this.b = j;
            this.c = j2;
            this.d = d;
            this.e = d2;
        }

        /* renamed from: a */
        public a trySplit() {
            long j = this.b;
            long j2 = (this.c + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            SplittableRandom split = this.a.split();
            this.b = j2;
            return new a(split, j, j2, this.d, this.e);
        }

        @Override // java8.util.Spliterator
        public long estimateSize() {
            return this.c - this.b;
        }

        @Override // java8.util.Spliterator.OfDouble
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            long j = this.b;
            if (j >= this.c) {
                return false;
            }
            doubleConsumer.accept(this.a.a(this.d, this.e));
            this.b = j + 1;
            return true;
        }

        @Override // java8.util.Spliterator.OfDouble
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            long j = this.b;
            long j2 = this.c;
            if (j < j2) {
                this.b = j2;
                SplittableRandom splittableRandom = this.a;
                double d = this.d;
                double d2 = this.e;
                do {
                    doubleConsumer.accept(splittableRandom.a(d, d2));
                    j++;
                } while (j < j2);
            }
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

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public boolean tryAdvance(Consumer<? super Double> consumer) {
            return Spliterators.OfDouble.tryAdvance(this, consumer);
        }

        @Override // java8.util.Spliterator.OfDouble, java8.util.Spliterator
        public void forEachRemaining(Consumer<? super Double> consumer) {
            Spliterators.OfDouble.forEachRemaining(this, consumer);
        }
    }
}
