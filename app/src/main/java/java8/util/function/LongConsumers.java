package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class LongConsumers {
    public static LongConsumer andThen(LongConsumer longConsumer, LongConsumer longConsumer2) {
        Objects.requireNonNull(longConsumer);
        Objects.requireNonNull(longConsumer2);
        return z.a(longConsumer, longConsumer2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(LongConsumer longConsumer, LongConsumer longConsumer2, long j) {
        longConsumer.accept(j);
        longConsumer2.accept(j);
    }

    private LongConsumers() {
    }
}
