package java8.util.stream;

import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterators;
import java8.util.function.IntConsumer;
import java8.util.function.IntPredicate;
import java8.util.function.IntSupplier;
import java8.util.function.IntUnaryOperator;
import java8.util.stream.IntStream;
import java8.util.stream.gr;
import java8.util.stream.hf;
import java8.util.stream.hi;

/* loaded from: classes5.dex */
public final class IntStreams {
    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfInt] */
    public static IntStream takeWhile(IntStream intStream, IntPredicate intPredicate) {
        Objects.requireNonNull(intStream);
        Objects.requireNonNull(intPredicate);
        return StreamSupport.intStream(new hi.i.b.C0366b(intStream.spliterator(), true, intPredicate), intStream.isParallel()).onClose(StreamSupport.a(intStream));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfInt] */
    public static IntStream dropWhile(IntStream intStream, IntPredicate intPredicate) {
        Objects.requireNonNull(intStream);
        Objects.requireNonNull(intPredicate);
        return StreamSupport.intStream(new hi.i.b.a(intStream.spliterator(), true, intPredicate), intStream.isParallel()).onClose(StreamSupport.a(intStream));
    }

    public static IntStream.Builder builder() {
        return new hf.d();
    }

    public static IntStream empty() {
        return StreamSupport.intStream(Spliterators.emptyIntSpliterator(), false);
    }

    public static IntStream of(int i) {
        return StreamSupport.intStream(new hf.d(i), false);
    }

    public static IntStream of(int... iArr) {
        return J8Arrays.stream(iArr);
    }

    public static IntStream iterate(final int i, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.IntStreams.1
            int a;
            boolean b;

            @Override // java8.util.Spliterator.OfInt
            public boolean tryAdvance(IntConsumer intConsumer) {
                int i2;
                Objects.requireNonNull(intConsumer);
                if (this.b) {
                    i2 = intUnaryOperator.applyAsInt(this.a);
                } else {
                    i2 = i;
                    this.b = true;
                }
                this.a = i2;
                intConsumer.accept(i2);
                return true;
            }
        }, false);
    }

    public static IntStream iterate(final int i, final IntPredicate intPredicate, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        Objects.requireNonNull(intPredicate);
        return StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.IntStreams.2
            int a;
            boolean b;
            boolean c;

            @Override // java8.util.Spliterator.OfInt
            public boolean tryAdvance(IntConsumer intConsumer) {
                int i2;
                Objects.requireNonNull(intConsumer);
                if (this.c) {
                    return false;
                }
                if (this.b) {
                    i2 = intUnaryOperator.applyAsInt(this.a);
                } else {
                    i2 = i;
                    this.b = true;
                }
                if (!intPredicate.test(i2)) {
                    this.c = true;
                    return false;
                }
                this.a = i2;
                intConsumer.accept(i2);
                return true;
            }

            @Override // java8.util.Spliterators.AbstractIntSpliterator, java8.util.Spliterator.OfInt
            public void forEachRemaining(IntConsumer intConsumer) {
                Objects.requireNonNull(intConsumer);
                if (!this.c) {
                    this.c = true;
                    int applyAsInt = this.b ? intUnaryOperator.applyAsInt(this.a) : i;
                    while (intPredicate.test(applyAsInt)) {
                        intConsumer.accept(applyAsInt);
                        applyAsInt = intUnaryOperator.applyAsInt(applyAsInt);
                    }
                }
            }
        }, false);
    }

    public static IntStream generate(IntSupplier intSupplier) {
        Objects.requireNonNull(intSupplier);
        return StreamSupport.intStream(new gr.f.b(Long.MAX_VALUE, intSupplier), false);
    }

    public static IntStream range(int i, int i2) {
        if (i >= i2) {
            return empty();
        }
        return StreamSupport.intStream(new hf.f(i, i2, false), false);
    }

    public static IntStream rangeClosed(int i, int i2) {
        if (i > i2) {
            return empty();
        }
        return StreamSupport.intStream(new hf.f(i, i2, true), false);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfInt] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java8.util.Spliterator$OfInt] */
    public static IntStream concat(IntStream intStream, IntStream intStream2) {
        Objects.requireNonNull(intStream);
        Objects.requireNonNull(intStream2);
        return StreamSupport.intStream(new hf.b.C0362b(intStream.spliterator(), intStream2.spliterator()), intStream.isParallel() || intStream2.isParallel()).onClose(hf.a(intStream, intStream2));
    }

    private IntStreams() {
    }
}
