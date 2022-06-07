package java8.util.stream;

import java8.util.function.Consumer;

/* loaded from: classes5.dex */
final /* synthetic */ class hd implements Consumer {
    private final Consumer a;

    private hd(Consumer consumer) {
        this.a = consumer;
    }

    public static Consumer a(Consumer consumer) {
        return new hd(consumer);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.accept(obj);
    }
}
