package java8.util.stream;

import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class es implements BiConsumer {
    private static final es a = new es();

    private es() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        eg.a((long[]) obj, (long[]) obj2);
    }
}
