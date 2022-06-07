package java8.util.stream;

import java8.util.function.IntConsumer;
import java8.util.stream.fn;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fz implements IntConsumer {
    private static final fz a = new fz();

    private fz() {
    }

    public static IntConsumer a() {
        return a;
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        fn.s.a(i);
    }
}
