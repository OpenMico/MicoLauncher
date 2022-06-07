package java8.util;

import java8.util.function.Consumer;
import java8.util.function.LongConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class aj implements LongConsumer {
    private final Consumer a;

    private aj(Consumer consumer) {
        this.a = consumer;
    }

    public static LongConsumer a(Consumer consumer) {
        return new aj(consumer);
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        this.a.accept(Long.valueOf(j));
    }
}
