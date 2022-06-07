package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Consumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class cs implements Consumer {
    private final BiConsumer a;
    private final Object b;

    private cs(BiConsumer biConsumer, Object obj) {
        this.a = biConsumer;
        this.b = obj;
    }

    public static Consumer a(BiConsumer biConsumer, Object obj) {
        return new cs(biConsumer, obj);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.accept(this.b, obj);
    }
}
