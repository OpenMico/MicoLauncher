package java8.util.stream;

import java8.util.Objects;
import java8.util.Spliterator;
import java8.util.function.Consumer;
import java8.util.function.IntFunction;
import java8.util.function.Supplier;
import java8.util.stream.BaseStream;
import java8.util.stream.Node;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractPipeline.java */
/* loaded from: classes5.dex */
public abstract class a<E_IN, E_OUT, S extends BaseStream<E_OUT, S>> extends gb<E_OUT> implements BaseStream<E_OUT, S> {
    protected final int a;
    private final a b;
    private final a c;
    private a d;
    private int e;
    private int f;
    private Spliterator<?> g;
    private Supplier<? extends Spliterator<?>> h;
    private boolean i;
    private boolean j;
    private Runnable k;
    private boolean l;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Spliterator c(Spliterator spliterator) {
        return spliterator;
    }

    abstract Spliterator<E_OUT> a(Supplier<? extends Spliterator<E_OUT>> supplier);

    abstract <P_IN> Spliterator<E_OUT> a(gb<E_OUT> gbVar, Supplier<Spliterator<P_IN>> supplier, boolean z);

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public abstract Node.Builder<E_OUT> a(long j, IntFunction<E_OUT[]> intFunction);

    abstract <P_IN> Node<E_OUT> a(gb<E_OUT> gbVar, Spliterator<P_IN> spliterator, boolean z, IntFunction<E_OUT[]> intFunction);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Sink<E_IN> a(int i, Sink<E_OUT> sink);

    abstract boolean a(Spliterator<E_OUT> spliterator, Sink<E_OUT> sink);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract gq e();

    abstract boolean f();

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Supplier<? extends Spliterator<?>> supplier, int i, boolean z) {
        this.c = null;
        this.h = supplier;
        this.b = this;
        this.a = gp.g & i;
        this.f = (~(this.a << 1)) & gp.k;
        this.e = 0;
        this.l = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Spliterator<?> spliterator, int i, boolean z) {
        this.c = null;
        this.g = spliterator;
        this.b = this;
        this.a = gp.g & i;
        this.f = (~(this.a << 1)) & gp.k;
        this.e = 0;
        this.l = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(a<?, E_IN, ?> aVar, int i) {
        if (!aVar.i) {
            aVar.i = true;
            aVar.d = this;
            this.c = aVar;
            this.a = gp.h & i;
            this.f = gp.a(i, aVar.f);
            this.b = aVar.b;
            if (f()) {
                this.b.j = true;
            }
            this.e = aVar.e + 1;
            return;
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    final <R> R a(hg<E_OUT, R> hgVar) {
        if (!this.i) {
            this.i = true;
            if (isParallel()) {
                return hgVar.b(this, b(hgVar.a()));
            }
            return hgVar.a(this, b(hgVar.a()));
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    final Node<E_OUT> a(IntFunction<E_OUT[]> intFunction) {
        if (!this.i) {
            this.i = true;
            if (!isParallel() || this.c == null || !f()) {
                return a((Spliterator) b(0), true, (IntFunction) intFunction);
            }
            this.e = 0;
            a aVar = this.c;
            return a(aVar, aVar.b(0), intFunction);
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    final Spliterator<E_OUT> a() {
        a<E_IN, E_OUT, S> aVar = this.b;
        if (this != aVar) {
            throw new IllegalStateException();
        } else if (!this.i) {
            this.i = true;
            Spliterator<E_OUT> spliterator = (Spliterator<E_OUT>) aVar.g;
            if (spliterator != null) {
                aVar.g = null;
                return spliterator;
            }
            Supplier<? extends Spliterator<?>> supplier = aVar.h;
            if (supplier != null) {
                Spliterator<E_OUT> spliterator2 = (Spliterator) supplier.get();
                this.b.h = null;
                return spliterator2;
            }
            throw new IllegalStateException("source already consumed or closed");
        } else {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
    }

    @Override // java8.util.stream.BaseStream
    public final S sequential() {
        this.b.l = false;
        return this;
    }

    @Override // java8.util.stream.BaseStream
    public final S parallel() {
        this.b.l = true;
        return this;
    }

    @Override // java8.util.stream.BaseStream
    public void close() {
        this.i = true;
        this.h = null;
        this.g = null;
        a aVar = this.b;
        Runnable runnable = aVar.k;
        if (runnable != null) {
            aVar.k = null;
            runnable.run();
        }
    }

    @Override // java8.util.stream.BaseStream
    public S onClose(Runnable runnable) {
        if (!this.i) {
            Objects.requireNonNull(runnable);
            a aVar = this.b;
            Runnable runnable2 = aVar.k;
            if (runnable2 != null) {
                runnable = hf.a(runnable2, runnable);
            }
            aVar.k = runnable;
            return this;
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    @Override // java8.util.stream.BaseStream
    public Spliterator<E_OUT> spliterator() {
        if (!this.i) {
            this.i = true;
            a<E_IN, E_OUT, S> aVar = this.b;
            if (this != aVar) {
                return a(this, b.a(this), isParallel());
            }
            Spliterator<E_OUT> spliterator = (Spliterator<E_OUT>) aVar.g;
            if (spliterator != null) {
                aVar.g = null;
                return spliterator;
            }
            Supplier<? extends Spliterator<?>> supplier = aVar.h;
            if (supplier != null) {
                aVar.h = null;
                return a(supplier);
            }
            throw new IllegalStateException("source already consumed or closed");
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    @Override // java8.util.stream.BaseStream
    public final boolean isParallel() {
        return this.b.l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Spliterator<?> b(int i) {
        Spliterator spliterator;
        int i2;
        int i3;
        a aVar = this.b;
        Spliterator spliterator2 = aVar.g;
        if (spliterator2 != null) {
            aVar.g = null;
            spliterator = spliterator2;
        } else {
            Supplier<? extends Spliterator<?>> supplier = aVar.h;
            if (supplier != null) {
                Spliterator spliterator3 = (Spliterator) supplier.get();
                this.b.h = null;
                spliterator = spliterator3;
            } else {
                throw new IllegalStateException("source already consumed or closed");
            }
        }
        if (isParallel()) {
            a<E_IN, E_OUT, S> aVar2 = this.b;
            if (aVar2.j) {
                int i4 = 1;
                spliterator = spliterator;
                a aVar3 = aVar2.d;
                while (aVar2 != this) {
                    int i5 = aVar3.a;
                    if (aVar3.f()) {
                        i4 = 0;
                        if (gp.SHORT_CIRCUIT.a(i5)) {
                            i5 &= ~gp.t;
                        }
                        spliterator = (Spliterator<E_OUT>) aVar3.a(aVar2, spliterator);
                        if (spliterator.hasCharacteristics(64)) {
                            i3 = i5 & (~gp.s);
                            i2 = gp.r;
                        } else {
                            i3 = i5 & (~gp.r);
                            i2 = gp.s;
                        }
                        i5 = i3 | i2;
                    }
                    i4++;
                    aVar3.e = i4;
                    aVar3.f = gp.a(i5, aVar2.f);
                    aVar3 = aVar3.d;
                    aVar2 = aVar3;
                    spliterator = spliterator;
                }
            }
        }
        if (i != 0) {
            this.f = gp.a(i, this.f);
        }
        return spliterator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final gq b() {
        a<E_IN, E_OUT, S> aVar = this;
        while (aVar.e > 0) {
            aVar = aVar.c;
        }
        return aVar.e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN> long a(Spliterator<P_IN> spliterator) {
        if (gp.SIZED.a(c())) {
            return spliterator.getExactSizeIfKnown();
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN, S_ extends Sink<E_OUT>> S_ a(S_ s_, Spliterator<P_IN> spliterator) {
        b(a((Sink) ((Sink) Objects.requireNonNull(s_))), spliterator);
        return s_;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN, S_ extends Consumer<E_OUT>> S_ a(S_ s_, Spliterator<P_IN> spliterator) {
        b(a((Consumer) Objects.requireNonNull(s_)), spliterator);
        return s_;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN> void b(Sink<P_IN> sink, Spliterator<P_IN> spliterator) {
        Objects.requireNonNull(sink);
        if (!gp.SHORT_CIRCUIT.a(c())) {
            sink.begin(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(sink);
            sink.end();
            return;
        }
        c(sink, spliterator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.stream.gb
    public final <P_IN> boolean c(Sink<P_IN> sink, Spliterator<P_IN> spliterator) {
        a<E_IN, E_OUT, S> aVar = this;
        while (aVar.e > 0) {
            aVar = aVar.c;
        }
        sink.begin(spliterator.getExactSizeIfKnown());
        boolean a = aVar.a(spliterator, sink);
        sink.end();
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final int c() {
        return this.f;
    }

    final boolean d() {
        return gp.ORDERED.a(this.f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN> Sink<P_IN> a(Sink<E_OUT> sink) {
        Objects.requireNonNull(sink);
        Consumer consumer = (Sink<P_IN>) sink;
        for (a<E_IN, E_OUT, S> aVar = this; aVar.e > 0; aVar = aVar.c) {
            consumer = (Sink<E_IN>) aVar.a(aVar.c.f, (Sink) consumer);
        }
        return (Sink<P_IN>) consumer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN> Sink<P_IN> a(final Consumer<E_OUT> consumer) {
        Objects.requireNonNull(consumer);
        Consumer consumer2 = (Sink<E_OUT>) new Sink<E_OUT>() { // from class: java8.util.stream.a.1
            @Override // java8.util.stream.Sink
            public void begin(long j) {
            }

            @Override // java8.util.stream.Sink
            public boolean cancellationRequested() {
                return false;
            }

            @Override // java8.util.stream.Sink
            public void end() {
            }

            @Override // java8.util.function.Consumer
            public void accept(E_OUT e_out) {
                consumer.accept(e_out);
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
        };
        for (a<E_IN, E_OUT, S> aVar = this; aVar.e > 0; aVar = aVar.c) {
            consumer2 = (Sink<E_IN>) aVar.a(aVar.c.f, (Sink) consumer2);
        }
        return (Sink<P_IN>) consumer2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java8.util.stream.gb
    public final <P_IN> Spliterator<E_OUT> b(Spliterator<P_IN> spliterator) {
        return this.e == 0 ? spliterator : a(this, c.a(spliterator), isParallel());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // java8.util.stream.gb
    public final <P_IN> Node<E_OUT> a(Spliterator<P_IN> spliterator, boolean z, IntFunction<E_OUT[]> intFunction) {
        if (isParallel()) {
            return a(this, spliterator, z, intFunction);
        }
        return ((Node.Builder) a((a<E_IN, E_OUT, S>) a(a(spliterator), intFunction), (Spliterator) spliterator)).build();
    }

    <P_IN> Node<E_OUT> a(gb<E_OUT> gbVar, Spliterator<P_IN> spliterator, IntFunction<E_OUT[]> intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object[] a(int i) {
        return new Object[i];
    }

    <P_IN> Spliterator<E_OUT> a(gb<E_OUT> gbVar, Spliterator<P_IN> spliterator) {
        return a(gbVar, spliterator, d.a()).f_();
    }
}
