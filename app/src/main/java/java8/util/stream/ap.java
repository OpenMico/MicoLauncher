package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class ap implements Function {
    private static final ap a = new ap();

    private ap() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Double valueOf;
        valueOf = Double.valueOf(Collectors.a((double[]) obj));
        return valueOf;
    }
}
