package java8.util.stream;

import java8.util.function.IntFunction;
import java8.util.function.LongFunction;
import java8.util.stream.Node;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fw implements LongFunction {
    private final IntFunction a;

    private fw(IntFunction intFunction) {
        this.a = intFunction;
    }

    public static LongFunction a(IntFunction intFunction) {
        return new fw(intFunction);
    }

    @Override // java8.util.function.LongFunction
    public Object apply(long j) {
        Node.Builder a;
        a = fn.a(j, this.a);
        return a;
    }
}
