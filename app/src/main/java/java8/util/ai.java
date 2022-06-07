package java8.util;

import java8.util.function.Consumer;
import java8.util.function.IntConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ai implements IntConsumer {
    private final Consumer a;

    private ai(Consumer consumer) {
        this.a = consumer;
    }

    public static IntConsumer a(Consumer consumer) {
        return new ai(consumer);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        this.a.accept(Integer.valueOf(i));
    }
}
