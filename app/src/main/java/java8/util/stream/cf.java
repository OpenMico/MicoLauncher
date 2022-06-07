package java8.util.stream;

import java8.util.DoubleSummaryStatistics;
import java8.util.function.BiConsumer;
import java8.util.function.ToDoubleFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class cf implements BiConsumer {
    private final ToDoubleFunction a;

    private cf(ToDoubleFunction toDoubleFunction) {
        this.a = toDoubleFunction;
    }

    public static BiConsumer a(ToDoubleFunction toDoubleFunction) {
        return new cf(toDoubleFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((DoubleSummaryStatistics) obj).accept(this.a.applyAsDouble(obj2));
    }
}
