package java8.util.stream;

import java8.util.function.Consumer;

/* loaded from: classes5.dex */
final /* synthetic */ class gm implements Consumer {
    private final Sink a;

    private gm(Sink sink) {
        this.a = sink;
    }

    public static Consumer a(Sink sink) {
        return new gm(sink);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.accept((Sink) obj);
    }
}
