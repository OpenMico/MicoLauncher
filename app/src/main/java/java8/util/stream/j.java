package java8.util.stream;

import java.util.List;
import java8.util.Lists;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class j implements Function {
    private static final j a = new j();

    private j() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        List of;
        of = Lists.of(((List) obj).toArray());
        return of;
    }
}
