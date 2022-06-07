package java8.util.stream;

import java.util.Map;
import java8.util.function.BiConsumer;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bk implements BiConsumer {
    private final Function a;
    private final Function b;

    private bk(Function function, Function function2) {
        this.a = function;
        this.b = function2;
    }

    public static BiConsumer a(Function function, Function function2) {
        return new bk(function, function2);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, this.b, (Map) obj, obj2);
    }
}
