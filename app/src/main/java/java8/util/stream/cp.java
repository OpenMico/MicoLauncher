package java8.util.stream;

import java8.util.function.BiFunction;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class cp implements BiFunction {
    private final Function a;

    private cp(Function function) {
        this.a = function;
    }

    public static BiFunction a(Function function) {
        return new cp(function);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        Object apply;
        apply = this.a.apply(obj2);
        return apply;
    }
}
