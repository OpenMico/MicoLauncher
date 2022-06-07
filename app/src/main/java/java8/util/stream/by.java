package java8.util.stream;

import java8.util.function.Function;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class by implements Function {
    private final Collector a;

    private by(Collector collector) {
        this.a = collector;
    }

    public static Function a(Collector collector) {
        return new by(collector);
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Collectors.a(this.a, (Collectors.d) obj);
    }
}
