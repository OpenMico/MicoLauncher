package java8.util.stream;

import java8.lang.Longs;
import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterators;
import java8.util.function.LongConsumer;
import java8.util.function.LongPredicate;
import java8.util.function.LongSupplier;
import java8.util.function.LongUnaryOperator;
import java8.util.stream.LongStream;
import java8.util.stream.gr;
import java8.util.stream.hf;
import java8.util.stream.hi;

/* loaded from: classes5.dex */
public final class LongStreams {
    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfLong] */
    public static LongStream takeWhile(LongStream longStream, LongPredicate longPredicate) {
        Objects.requireNonNull(longStream);
        Objects.requireNonNull(longPredicate);
        return StreamSupport.longStream(new hi.i.c.b(longStream.spliterator(), true, longPredicate), longStream.isParallel()).onClose(StreamSupport.a(longStream));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfLong] */
    public static LongStream dropWhile(LongStream longStream, LongPredicate longPredicate) {
        Objects.requireNonNull(longStream);
        Objects.requireNonNull(longPredicate);
        return StreamSupport.longStream(new hi.i.c.a(longStream.spliterator(), true, longPredicate), longStream.isParallel()).onClose(StreamSupport.a(longStream));
    }

    public static LongStream.Builder builder() {
        return new hf.e();
    }

    public static LongStream empty() {
        return StreamSupport.longStream(Spliterators.emptyLongSpliterator(), false);
    }

    public static LongStream of(long j) {
        return StreamSupport.longStream(new hf.e(j), false);
    }

    public static LongStream of(long... jArr) {
        return J8Arrays.stream(jArr);
    }

    public static LongStream iterate(final long j, final LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.LongStreams.1
            long a;
            boolean b;

            @Override // java8.util.Spliterator.OfLong
            public boolean tryAdvance(LongConsumer longConsumer) {
                long j2;
                Objects.requireNonNull(longConsumer);
                if (this.b) {
                    j2 = longUnaryOperator.applyAsLong(this.a);
                } else {
                    j2 = j;
                    this.b = true;
                }
                this.a = j2;
                longConsumer.accept(j2);
                return true;
            }
        }, false);
    }

    public static LongStream iterate(final long j, final LongPredicate longPredicate, final LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        Objects.requireNonNull(longPredicate);
        return StreamSupport.longStream(new Spliterators.AbstractLongSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.LongStreams.2
            long a;
            boolean b;
            boolean c;

            @Override // java8.util.Spliterator.OfLong
            public boolean tryAdvance(LongConsumer longConsumer) {
                long j2;
                Objects.requireNonNull(longConsumer);
                if (this.c) {
                    return false;
                }
                if (this.b) {
                    j2 = longUnaryOperator.applyAsLong(this.a);
                } else {
                    j2 = j;
                    this.b = true;
                }
                if (!longPredicate.test(j2)) {
                    this.c = true;
                    return false;
                }
                this.a = j2;
                longConsumer.accept(j2);
                return true;
            }

            @Override // java8.util.Spliterators.AbstractLongSpliterator, java8.util.Spliterator.OfLong
            public void forEachRemaining(LongConsumer longConsumer) {
                Objects.requireNonNull(longConsumer);
                if (!this.c) {
                    this.c = true;
                    long applyAsLong = this.b ? longUnaryOperator.applyAsLong(this.a) : j;
                    while (longPredicate.test(applyAsLong)) {
                        longConsumer.accept(applyAsLong);
                        applyAsLong = longUnaryOperator.applyAsLong(applyAsLong);
                    }
                }
            }
        }, false);
    }

    public static LongStream generate(LongSupplier longSupplier) {
        Objects.requireNonNull(longSupplier);
        return StreamSupport.longStream(new gr.f.c(Long.MAX_VALUE, longSupplier), false);
    }

    public static LongStream range(long j, long j2) {
        if (j >= j2) {
            return empty();
        }
        long j3 = j2 - j;
        if (j3 >= 0) {
            return StreamSupport.longStream(new hf.g(j, j2, false), false);
        }
        long divideUnsigned = Longs.divideUnsigned(j3, 2L) + j + 1;
        return concat(range(j, divideUnsigned), range(divideUnsigned, j2));
    }

    public static LongStream rangeClosed(long j, long j2) {
        if (j > j2) {
            return empty();
        }
        long j3 = j2 - j;
        if (j3 + 1 > 0) {
            return StreamSupport.longStream(new hf.g(j, j2, true), false);
        }
        long divideUnsigned = Longs.divideUnsigned(j3, 2L) + j + 1;
        return concat(range(j, divideUnsigned), rangeClosed(divideUnsigned, j2));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfLong] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java8.util.Spliterator$OfLong] */
    public static LongStream concat(LongStream longStream, LongStream longStream2) {
        Objects.requireNonNull(longStream);
        Objects.requireNonNull(longStream2);
        return StreamSupport.longStream(new hf.b.c(longStream.spliterator(), longStream2.spliterator()), longStream.isParallel() || longStream2.isParallel()).onClose(hf.a(longStream, longStream2));
    }

    private LongStreams() {
    }
}
