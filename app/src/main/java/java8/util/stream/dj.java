package java8.util.stream;

import java8.util.DoubleSummaryStatistics;
import java8.util.function.ObjDoubleConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dj implements ObjDoubleConsumer {
    private static final dj a = new dj();

    private dj() {
    }

    public static ObjDoubleConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjDoubleConsumer
    public void accept(Object obj, double d) {
        ((DoubleSummaryStatistics) obj).accept(d);
    }
}
