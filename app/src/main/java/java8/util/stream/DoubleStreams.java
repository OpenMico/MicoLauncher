package java8.util.stream;

import java8.util.J8Arrays;
import java8.util.Objects;
import java8.util.Spliterators;
import java8.util.function.DoubleConsumer;
import java8.util.function.DoublePredicate;
import java8.util.function.DoubleSupplier;
import java8.util.function.DoubleUnaryOperator;
import java8.util.stream.DoubleStream;
import java8.util.stream.gr;
import java8.util.stream.hf;
import java8.util.stream.hi;

/* loaded from: classes5.dex */
public final class DoubleStreams {
    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfDouble] */
    public static DoubleStream takeWhile(DoubleStream doubleStream, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doubleStream);
        Objects.requireNonNull(doublePredicate);
        return StreamSupport.doubleStream(new hi.i.a.b(doubleStream.spliterator(), true, doublePredicate), doubleStream.isParallel()).onClose(StreamSupport.a(doubleStream));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfDouble] */
    public static DoubleStream dropWhile(DoubleStream doubleStream, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doubleStream);
        Objects.requireNonNull(doublePredicate);
        return StreamSupport.doubleStream(new hi.i.a.C0365a(doubleStream.spliterator(), true, doublePredicate), doubleStream.isParallel()).onClose(StreamSupport.a(doubleStream));
    }

    public static DoubleStream.Builder builder() {
        return new hf.c();
    }

    public static DoubleStream empty() {
        return StreamSupport.doubleStream(Spliterators.emptyDoubleSpliterator(), false);
    }

    public static DoubleStream of(double d) {
        return StreamSupport.doubleStream(new hf.c(d), false);
    }

    public static DoubleStream of(double... dArr) {
        return J8Arrays.stream(dArr);
    }

    public static DoubleStream iterate(final double d, final DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.DoubleStreams.1
            double a;
            boolean b;

            @Override // java8.util.Spliterator.OfDouble
            public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                double d2;
                Objects.requireNonNull(doubleConsumer);
                if (this.b) {
                    d2 = doubleUnaryOperator.applyAsDouble(this.a);
                } else {
                    d2 = d;
                    this.b = true;
                }
                this.a = d2;
                doubleConsumer.accept(d2);
                return true;
            }
        }, false);
    }

    public static DoubleStream iterate(final double d, final DoublePredicate doublePredicate, final DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        Objects.requireNonNull(doublePredicate);
        return StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(Long.MAX_VALUE, 1296) { // from class: java8.util.stream.DoubleStreams.2
            double a;
            boolean b;
            boolean c;

            @Override // java8.util.Spliterator.OfDouble
            public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                double d2;
                Objects.requireNonNull(doubleConsumer);
                if (this.c) {
                    return false;
                }
                if (this.b) {
                    d2 = doubleUnaryOperator.applyAsDouble(this.a);
                } else {
                    d2 = d;
                    this.b = true;
                }
                if (!doublePredicate.test(d2)) {
                    this.c = true;
                    return false;
                }
                this.a = d2;
                doubleConsumer.accept(d2);
                return true;
            }

            @Override // java8.util.Spliterators.AbstractDoubleSpliterator, java8.util.Spliterator.OfDouble
            public void forEachRemaining(DoubleConsumer doubleConsumer) {
                Objects.requireNonNull(doubleConsumer);
                if (!this.c) {
                    this.c = true;
                    double applyAsDouble = this.b ? doubleUnaryOperator.applyAsDouble(this.a) : d;
                    while (doublePredicate.test(applyAsDouble)) {
                        doubleConsumer.accept(applyAsDouble);
                        applyAsDouble = doubleUnaryOperator.applyAsDouble(applyAsDouble);
                    }
                }
            }
        }, false);
    }

    public static DoubleStream generate(DoubleSupplier doubleSupplier) {
        Objects.requireNonNull(doubleSupplier);
        return StreamSupport.doubleStream(new gr.f.a(Long.MAX_VALUE, doubleSupplier), false);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java8.util.Spliterator$OfDouble] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java8.util.Spliterator$OfDouble] */
    public static DoubleStream concat(DoubleStream doubleStream, DoubleStream doubleStream2) {
        Objects.requireNonNull(doubleStream);
        Objects.requireNonNull(doubleStream2);
        return StreamSupport.doubleStream(new hf.b.a(doubleStream.spliterator(), doubleStream2.spliterator()), doubleStream.isParallel() || doubleStream2.isParallel()).onClose(hf.a(doubleStream, doubleStream2));
    }

    private DoubleStreams() {
    }
}
