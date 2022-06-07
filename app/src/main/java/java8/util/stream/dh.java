package java8.util.stream;

import java8.util.function.ObjDoubleConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dh implements ObjDoubleConsumer {
    private static final dh a = new dh();

    private dh() {
    }

    public static ObjDoubleConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjDoubleConsumer
    public void accept(Object obj, double d) {
        df.a((double[]) obj, d);
    }
}
