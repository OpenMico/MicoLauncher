package java8.util.stream;

import java8.util.function.IntConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class gh implements IntConsumer {
    private final Sink a;

    private gh(Sink sink) {
        this.a = sink;
    }

    public static IntConsumer a(Sink sink) {
        return new gh(sink);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        this.a.accept(i);
    }
}
