package java8.util.stream;

import java.util.Map;
import java8.util.Maps;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class cw implements Function {
    private static final cw a = new cw();

    private cw() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Map ofEntries;
        ofEntries = Maps.ofEntries((Map.Entry[]) ((Map) obj).entrySet().toArray(new Map.Entry[0]));
        return ofEntries;
    }
}
