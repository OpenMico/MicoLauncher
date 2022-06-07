package java8.util.stream;

import java8.util.Objects;
import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;
import java8.util.function.IntConsumer;
import java8.util.function.LongConsumer;
import java8.util.stream.gj;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public interface Sink<T> extends Consumer<T> {

    /* loaded from: classes5.dex */
    public interface OfDouble extends DoubleConsumer, Sink<Double> {
        @Override // java8.util.function.DoubleConsumer
        void accept(double d);

        void accept(Double d);
    }

    /* loaded from: classes5.dex */
    public interface OfInt extends IntConsumer, Sink<Integer> {
        @Override // java8.util.function.IntConsumer
        void accept(int i);

        void accept(Integer num);
    }

    /* loaded from: classes5.dex */
    public interface OfLong extends LongConsumer, Sink<Long> {
        @Override // java8.util.function.LongConsumer
        void accept(long j);

        void accept(Long l);
    }

    void accept(double d);

    void accept(int i);

    void accept(long j);

    void begin(long j);

    boolean cancellationRequested();

    void end();

    /* loaded from: classes5.dex */
    public static abstract class ChainedReference<T, E_OUT> implements Sink<T> {
        protected final Sink<? super E_OUT> downstream;

        public ChainedReference(Sink<? super E_OUT> sink) {
            this.downstream = (Sink) Objects.requireNonNull(sink);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.downstream.begin(j);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class ChainedInt<E_OUT> implements OfInt {
        protected final Sink<? super E_OUT> downstream;

        public ChainedInt(Sink<? super E_OUT> sink) {
            this.downstream = (Sink) Objects.requireNonNull(sink);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.downstream.begin(j);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }

        @Override // java8.util.stream.Sink.OfInt
        public void accept(Integer num) {
            gj.b.a(this, num);
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class ChainedLong<E_OUT> implements OfLong {
        protected final Sink<? super E_OUT> downstream;

        public ChainedLong(Sink<? super E_OUT> sink) {
            this.downstream = (Sink) Objects.requireNonNull(sink);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.downstream.begin(j);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink.OfLong
        public void accept(Long l) {
            gj.c.a(this, l);
        }

        @Override // java8.util.stream.Sink
        public void accept(double d) {
            gj.a();
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class ChainedDouble<E_OUT> implements OfDouble {
        protected final Sink<? super E_OUT> downstream;

        public ChainedDouble(Sink<? super E_OUT> sink) {
            this.downstream = (Sink) Objects.requireNonNull(sink);
        }

        @Override // java8.util.stream.Sink
        public void begin(long j) {
            this.downstream.begin(j);
        }

        @Override // java8.util.stream.Sink
        public void end() {
            this.downstream.end();
        }

        @Override // java8.util.stream.Sink
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override // java8.util.stream.Sink
        public void accept(int i) {
            gj.a();
        }

        @Override // java8.util.stream.Sink
        public void accept(long j) {
            gj.a();
        }

        @Override // java8.util.stream.Sink.OfDouble
        public void accept(Double d) {
            gj.a.a(this, d);
        }
    }
}
