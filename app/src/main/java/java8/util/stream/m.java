package java8.util.stream;

import java.util.Set;
import java8.util.Sets;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class m implements Function {
    private static final m a = new m();

    private m() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Set of;
        of = Sets.of(((Set) obj).toArray());
        return of;
    }
}
