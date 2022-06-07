package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bi implements BiConsumer {
    private static final bi a = new bi();

    private bi() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((Collectors.a) obj).accept(obj2);
    }
}
