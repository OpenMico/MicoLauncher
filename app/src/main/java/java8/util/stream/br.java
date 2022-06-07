package java8.util.stream;

import java.util.concurrent.ConcurrentMap;
import java8.util.Objects;
import java8.util.concurrent.ConcurrentMaps;
import java8.util.function.BiConsumer;
import java8.util.function.Function;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class br implements BiConsumer {
    private final Function a;
    private final Supplier b;
    private final BiConsumer c;

    private br(Function function, Supplier supplier, BiConsumer biConsumer) {
        this.a = function;
        this.b = supplier;
        this.c = biConsumer;
    }

    public static BiConsumer a(Function function, Supplier supplier, BiConsumer biConsumer) {
        return new br(function, supplier, biConsumer);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        this.c.accept(ConcurrentMaps.computeIfAbsent((ConcurrentMap) obj, Objects.requireNonNull(this.a.apply(obj2), "element cannot be mapped to a null key"), co.a(this.b)), obj2);
    }
}
