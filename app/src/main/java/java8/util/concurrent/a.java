package java8.util.concurrent;

import java.util.concurrent.ConcurrentMap;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class a implements BiConsumer {
    private final ConcurrentMap a;
    private final BiFunction b;

    private a(ConcurrentMap concurrentMap, BiFunction biFunction) {
        this.a = concurrentMap;
        this.b = biFunction;
    }

    public static BiConsumer a(ConcurrentMap concurrentMap, BiFunction biFunction) {
        return new a(concurrentMap, biFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ConcurrentMaps.a(this.a, this.b, obj, obj2);
    }
}
