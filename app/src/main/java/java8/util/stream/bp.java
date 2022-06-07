package java8.util.stream;

import java.util.Map;
import java8.util.Maps;
import java8.util.Objects;
import java8.util.function.BiConsumer;
import java8.util.function.Function;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bp implements BiConsumer {
    private final Function a;
    private final Supplier b;
    private final BiConsumer c;

    private bp(Function function, Supplier supplier, BiConsumer biConsumer) {
        this.a = function;
        this.b = supplier;
        this.c = biConsumer;
    }

    public static BiConsumer a(Function function, Supplier supplier, BiConsumer biConsumer) {
        return new bp(function, supplier, biConsumer);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        this.c.accept(Maps.computeIfAbsent((Map) obj, Objects.requireNonNull(this.a.apply(obj2), "element cannot be mapped to a null key"), cq.a(this.b)), obj2);
    }
}
