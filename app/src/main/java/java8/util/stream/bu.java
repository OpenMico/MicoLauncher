package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Predicate;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bu implements BiConsumer {
    private final BiConsumer a;
    private final Predicate b;

    private bu(BiConsumer biConsumer, Predicate predicate) {
        this.a = biConsumer;
        this.b = predicate;
    }

    public static BiConsumer a(BiConsumer biConsumer, Predicate predicate) {
        return new bu(biConsumer, predicate);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        BiConsumer biConsumer = this.a;
        Predicate predicate = this.b;
        biConsumer.accept(r1.test(r3) ? r3.a : ((Collectors.d) obj).b, obj2);
    }
}
