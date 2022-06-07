package java8.util.stream;

import java8.util.function.LongConsumer;
import java8.util.stream.gr;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class gy implements LongConsumer {
    private static final gy a = new gy();

    private gy() {
    }

    public static LongConsumer a() {
        return a;
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        gr.i.c.a(j);
    }
}
