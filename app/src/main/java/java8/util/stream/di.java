package java8.util.stream;

import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class di implements BiConsumer {
    private static final di a = new di();

    private di() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        df.a((double[]) obj, (double[]) obj2);
    }
}
