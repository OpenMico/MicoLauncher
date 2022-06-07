package java8.util.stream;

import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class fg implements BiConsumer {
    private static final fg a = new fg();

    private fg() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        eu.a((long[]) obj, (long[]) obj2);
    }
}
