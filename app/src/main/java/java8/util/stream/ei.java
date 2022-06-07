package java8.util.stream;

import java8.util.IntSummaryStatistics;
import java8.util.function.ObjIntConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class ei implements ObjIntConsumer {
    private static final ei a = new ei();

    private ei() {
    }

    public static ObjIntConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjIntConsumer
    public void accept(Object obj, int i) {
        ((IntSummaryStatistics) obj).accept(i);
    }
}
