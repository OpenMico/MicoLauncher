package java8.util.stream;

import java8.util.function.LongConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class gg implements LongConsumer {
    private final Sink a;

    private gg(Sink sink) {
        this.a = sink;
    }

    public static LongConsumer a(Sink sink) {
        return new gg(sink);
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        this.a.accept(j);
    }
}
