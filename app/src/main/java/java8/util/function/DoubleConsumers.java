package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class DoubleConsumers {
    public static DoubleConsumer andThen(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        Objects.requireNonNull(doubleConsumer);
        Objects.requireNonNull(doubleConsumer2);
        return i.a(doubleConsumer, doubleConsumer2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2, double d) {
        doubleConsumer.accept(d);
        doubleConsumer2.accept(d);
    }

    private DoubleConsumers() {
    }
}
