package java8.util.stream;

import java.util.Map;
import java8.util.Maps;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bq implements Function {
    private final Function a;

    private bq(Function function) {
        this.a = function;
    }

    public static Function a(Function function) {
        return new bq(function);
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Maps.replaceAll((Map) obj, cp.a(this.a));
    }
}
