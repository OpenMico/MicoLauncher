package java8.util.stream;

import java.util.LinkedHashSet;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dc implements BiConsumer {
    private static final dc a = new dc();

    private dc() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((LinkedHashSet) obj).add(obj2);
    }
}
