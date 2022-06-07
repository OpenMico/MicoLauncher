package java8.util;

import java8.util.function.Consumer;
import java8.util.function.DoubleConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ah implements DoubleConsumer {
    private final Consumer a;

    private ah(Consumer consumer) {
        this.a = consumer;
    }

    public static DoubleConsumer a(Consumer consumer) {
        return new ah(consumer);
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        this.a.accept(Double.valueOf(d));
    }
}
