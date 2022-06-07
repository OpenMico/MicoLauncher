package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class ag implements Function {
    private static final ag a = new ag();

    private ag() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Integer valueOf;
        valueOf = Integer.valueOf(((int[]) obj)[0]);
        return valueOf;
    }
}
