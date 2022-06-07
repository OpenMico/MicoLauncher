package java8.util.stream;

import java8.util.function.DoubleConsumer;
import java8.util.stream.fn;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fy implements DoubleConsumer {
    private static final fy a = new fy();

    private fy() {
    }

    public static DoubleConsumer a() {
        return a;
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        fn.r.a(d);
    }
}
