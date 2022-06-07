package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class Consumers {
    public static <T> Consumer<T> andThen(Consumer<? super T> consumer, Consumer<? super T> consumer2) {
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(consumer2);
        return h.a(consumer, consumer2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(Consumer consumer, Consumer consumer2, Object obj) {
        consumer.accept(obj);
        consumer2.accept(obj);
    }

    private Consumers() {
    }
}
