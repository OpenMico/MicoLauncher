package java8.util.stream;

import java.util.LinkedHashSet;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dd implements BiConsumer {
    private static final dd a = new dd();

    private dd() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((LinkedHashSet) obj).addAll((LinkedHashSet) obj2);
    }
}
