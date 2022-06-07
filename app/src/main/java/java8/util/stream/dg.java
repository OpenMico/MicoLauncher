package java8.util.stream;

import java8.util.function.DoubleConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dg implements DoubleConsumer {
    private final Sink a;

    private dg(Sink sink) {
        this.a = sink;
    }

    public static DoubleConsumer a(Sink sink) {
        return new dg(sink);
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        this.a.accept(d);
    }
}
