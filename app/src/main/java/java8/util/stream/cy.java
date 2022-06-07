package java8.util.stream;

import java.util.Set;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class cy implements BiConsumer {
    private static final cy a = new cy();

    private cy() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((Set) obj).add(obj2);
    }
}
