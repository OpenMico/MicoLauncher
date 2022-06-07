package java8.util.stream;

import java8.util.function.BooleanSupplier;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class hc implements BooleanSupplier {
    private final gr.k a;

    private hc(gr.k kVar) {
        this.a = kVar;
    }

    public static BooleanSupplier a(gr.k kVar) {
        return new hc(kVar);
    }

    @Override // java8.util.function.BooleanSupplier
    public boolean getAsBoolean() {
        boolean tryAdvance;
        tryAdvance = r0.c.tryAdvance(this.a.d);
        return tryAdvance;
    }
}
