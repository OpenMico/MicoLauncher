package java8.util.stream;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java8.util.function.Consumer;
import java8.util.stream.da;

/* loaded from: classes5.dex */
final /* synthetic */ class de implements Consumer {
    private final AtomicBoolean a;
    private final ConcurrentMap b;

    private de(AtomicBoolean atomicBoolean, ConcurrentMap concurrentMap) {
        this.a = atomicBoolean;
        this.b = concurrentMap;
    }

    public static Consumer a(AtomicBoolean atomicBoolean, ConcurrentMap concurrentMap) {
        return new de(atomicBoolean, concurrentMap);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        da.AnonymousClass1.a(this.a, this.b, obj);
    }
}
