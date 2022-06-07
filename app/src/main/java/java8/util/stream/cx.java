package java8.util.stream;

import java.util.List;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class cx implements BiConsumer {
    private static final cx a = new cx();

    private cx() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((List) obj).add(obj2);
    }
}
