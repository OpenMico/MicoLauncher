package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Consumer;

/* loaded from: classes5.dex */
final /* synthetic */ class gf implements Consumer {
    private final BiConsumer a;
    private final Object b;

    private gf(BiConsumer biConsumer, Object obj) {
        this.a = biConsumer;
        this.b = obj;
    }

    public static Consumer a(BiConsumer biConsumer, Object obj) {
        return new gf(biConsumer, obj);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.accept(this.b, obj);
    }
}
