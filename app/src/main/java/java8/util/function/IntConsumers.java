package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class IntConsumers {
    public static IntConsumer andThen(IntConsumer intConsumer, IntConsumer intConsumer2) {
        Objects.requireNonNull(intConsumer);
        Objects.requireNonNull(intConsumer2);
        return s.a(intConsumer, intConsumer2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(IntConsumer intConsumer, IntConsumer intConsumer2, int i) {
        intConsumer.accept(i);
        intConsumer2.accept(i);
    }

    private IntConsumers() {
    }
}
