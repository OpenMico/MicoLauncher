package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Predicate;

/* loaded from: classes5.dex */
final /* synthetic */ class aa implements BiConsumer {
    private final Predicate a;
    private final BiConsumer b;

    private aa(Predicate predicate, BiConsumer biConsumer) {
        this.a = predicate;
        this.b = biConsumer;
    }

    public static BiConsumer a(Predicate predicate, BiConsumer biConsumer) {
        return new aa(predicate, biConsumer);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, this.b, obj, obj2);
    }
}
