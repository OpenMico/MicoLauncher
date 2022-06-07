package java8.util.stream;

import java.util.concurrent.ConcurrentMap;
import java8.util.concurrent.ConcurrentMaps;
import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ca implements BiConsumer {
    private final Function a;
    private final Function b;
    private final BinaryOperator c;

    private ca(Function function, Function function2, BinaryOperator binaryOperator) {
        this.a = function;
        this.b = function2;
        this.c = binaryOperator;
    }

    public static BiConsumer a(Function function, Function function2, BinaryOperator binaryOperator) {
        return new ca(function, function2, binaryOperator);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ConcurrentMap concurrentMap = (ConcurrentMap) obj;
        ConcurrentMaps.merge(concurrentMap, this.a.apply(obj2), this.b.apply(obj2), this.c);
    }
}
