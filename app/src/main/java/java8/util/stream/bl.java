package java8.util.stream;

import java8.util.Optional;
import java8.util.function.Function;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bl implements Function {
    private static final bl a = new bl();

    private bl() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Optional ofNullable;
        ofNullable = Optional.ofNullable(((Collectors.a) obj).a);
        return ofNullable;
    }
}
