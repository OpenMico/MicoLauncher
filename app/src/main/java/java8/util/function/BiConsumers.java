package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class BiConsumers {
    public static <T, U> BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> biConsumer, BiConsumer<? super T, ? super U> biConsumer2) {
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return a.a(biConsumer, biConsumer2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(BiConsumer biConsumer, BiConsumer biConsumer2, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        biConsumer2.accept(obj, obj2);
    }

    private BiConsumers() {
    }
}
