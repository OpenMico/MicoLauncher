package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class bc implements Function {
    private static final bc a = new bc();

    private bc() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Collectors.b((double[]) obj);
    }
}
