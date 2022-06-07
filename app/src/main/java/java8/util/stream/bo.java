package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class bo implements Function {
    private static final bo a = new bo();

    private bo() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Collectors.a((Object[]) obj);
    }
}
