package java8.util.stream;

import java.util.Collection;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class bv implements BiConsumer {
    private static final bv a = new bv();

    private bv() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((Collection) obj).add(obj2);
    }
}
