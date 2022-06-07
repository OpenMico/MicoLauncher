package java8.util.stream;

import java8.util.function.LongConsumer;
import java8.util.stream.fn;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ga implements LongConsumer {
    private static final ga a = new ga();

    private ga() {
    }

    public static LongConsumer a() {
        return a;
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        fn.t.a(j);
    }
}
