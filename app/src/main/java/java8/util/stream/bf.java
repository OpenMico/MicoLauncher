package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class bf implements Function {
    private static final bf a = new bf();

    private bf() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Collectors.b((Object[]) obj);
    }
}
