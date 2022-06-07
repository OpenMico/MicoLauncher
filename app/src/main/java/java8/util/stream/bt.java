package java8.util.stream;

import java.util.concurrent.ConcurrentMap;
import java8.util.concurrent.ConcurrentMaps;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bt implements Function {
    private final Function a;

    private bt(Function function) {
        this.a = function;
    }

    public static Function a(Function function) {
        return new bt(function);
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return ConcurrentMaps.replaceAll((ConcurrentMap) obj, cm.a(this.a));
    }
}
