package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@GwtCompatible
/* loaded from: classes2.dex */
public abstract class DiscreteDomain<C extends Comparable> {
    final boolean supportsFastOffset;

    public abstract long distance(C c2, C c3);

    public abstract C next(C c2);

    public abstract C previous(C c2);

    public static DiscreteDomain<Integer> integers() {
        return b.a;
    }

    /* loaded from: classes2.dex */
    private static final class b extends DiscreteDomain<Integer> implements Serializable {
        private static final b a = new b();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        b() {
            super(true);
        }

        /* renamed from: a */
        public Integer next(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MAX_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue + 1);
        }

        /* renamed from: b */
        public Integer previous(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MIN_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue - 1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Integer a(Integer num, long j) {
            t.a(j, "distance");
            return Integer.valueOf(Ints.checkedCast(num.longValue() + j));
        }

        /* renamed from: a */
        public long distance(Integer num, Integer num2) {
            return num2.intValue() - num.intValue();
        }

        /* renamed from: a */
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        /* renamed from: b */
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        private Object readResolve() {
            return a;
        }
    }

    public static DiscreteDomain<Long> longs() {
        return c.a;
    }

    /* loaded from: classes2.dex */
    private static final class c extends DiscreteDomain<Long> implements Serializable {
        private static final c a = new c();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        c() {
            super(true);
        }

        /* renamed from: a */
        public Long next(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MAX_VALUE) {
                return null;
            }
            return Long.valueOf(longValue + 1);
        }

        /* renamed from: b */
        public Long previous(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(longValue - 1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Long a(Long l, long j) {
            t.a(j, "distance");
            long longValue = l.longValue() + j;
            if (longValue < 0) {
                Preconditions.checkArgument(l.longValue() < 0, "overflow");
            }
            return Long.valueOf(longValue);
        }

        /* renamed from: a */
        public long distance(Long l, Long l2) {
            long longValue = l2.longValue() - l.longValue();
            if (l2.longValue() > l.longValue() && longValue < 0) {
                return Long.MAX_VALUE;
            }
            if (l2.longValue() >= l.longValue() || longValue <= 0) {
                return longValue;
            }
            return Long.MIN_VALUE;
        }

        /* renamed from: a */
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        /* renamed from: b */
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        private Object readResolve() {
            return a;
        }
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return a.a;
    }

    /* loaded from: classes2.dex */
    private static final class a extends DiscreteDomain<BigInteger> implements Serializable {
        private static final a a = new a();
        private static final BigInteger b = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger c = BigInteger.valueOf(Long.MAX_VALUE);
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        a() {
            super(true);
        }

        /* renamed from: a */
        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        /* renamed from: b */
        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public BigInteger a(BigInteger bigInteger, long j) {
            t.a(j, "distance");
            return bigInteger.add(BigInteger.valueOf(j));
        }

        /* renamed from: a */
        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(b).min(c).longValue();
        }

        private Object readResolve() {
            return a;
        }
    }

    protected DiscreteDomain() {
        this(false);
    }

    private DiscreteDomain(boolean z) {
        this.supportsFastOffset = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public C a(C c2, long j) {
        t.a(j, "distance");
        for (long j2 = 0; j2 < j; j2++) {
            c2 = next(c2);
        }
        return c2;
    }

    @CanIgnoreReturnValue
    public C minValue() {
        throw new NoSuchElementException();
    }

    @CanIgnoreReturnValue
    public C maxValue() {
        throw new NoSuchElementException();
    }
}
