package java8.util.stream;

import java8.util.function.ToLongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class ab implements ToLongFunction {
    private static final ab a = new ab();

    private ab() {
    }

    public static ToLongFunction a() {
        return a;
    }

    @Override // java8.util.function.ToLongFunction
    public long applyAsLong(Object obj) {
        return Collectors.b(obj);
    }
}
