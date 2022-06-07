package java8.util.stream;

import java8.util.function.IntConsumer;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class gx implements IntConsumer {
    private static final gx a = new gx();

    private gx() {
    }

    public static IntConsumer a() {
        return a;
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        gr.i.b.a(i);
    }
}
