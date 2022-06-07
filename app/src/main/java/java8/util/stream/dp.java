package java8.util.stream;

import java8.util.function.ObjDoubleConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dp implements ObjDoubleConsumer {
    private static final dp a = new dp();

    private dp() {
    }

    public static ObjDoubleConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjDoubleConsumer
    public void accept(Object obj, double d) {
        df.b((double[]) obj, d);
    }
}
