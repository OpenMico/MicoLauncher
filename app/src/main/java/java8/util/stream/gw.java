package java8.util.stream;

import java8.util.function.DoubleConsumer;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class gw implements DoubleConsumer {
    private static final gw a = new gw();

    private gw() {
    }

    public static DoubleConsumer a() {
        return a;
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        gr.i.a.a(d);
    }
}
