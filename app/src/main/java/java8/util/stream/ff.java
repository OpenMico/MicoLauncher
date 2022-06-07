package java8.util.stream;

import java8.util.function.ObjLongConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class ff implements ObjLongConsumer {
    private static final ff a = new ff();

    private ff() {
    }

    public static ObjLongConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjLongConsumer
    public void accept(Object obj, long j) {
        eu.a((long[]) obj, j);
    }
}
