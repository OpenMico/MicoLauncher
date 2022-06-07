package java8.util.stream;

import java8.util.function.ObjIntConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class er implements ObjIntConsumer {
    private static final er a = new er();

    private er() {
    }

    public static ObjIntConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjIntConsumer
    public void accept(Object obj, int i) {
        eg.a((long[]) obj, i);
    }
}
