package java8.util.stream;

import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dq implements BiConsumer {
    private static final dq a = new dq();

    private dq() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        df.b((double[]) obj, (double[]) obj2);
    }
}
