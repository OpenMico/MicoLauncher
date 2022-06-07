package java8.util.stream;

import java.util.Map;
import java8.util.Maps;
import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bz implements BiConsumer {
    private final Function a;
    private final Function b;
    private final BinaryOperator c;

    private bz(Function function, Function function2, BinaryOperator binaryOperator) {
        this.a = function;
        this.b = function2;
        this.c = binaryOperator;
    }

    public static BiConsumer a(Function function, Function function2, BinaryOperator binaryOperator) {
        return new bz(function, function2, binaryOperator);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Map map = (Map) obj;
        Maps.merge(map, this.a.apply(obj2), this.b.apply(obj2), this.c);
    }
}
