package java8.util.stream;

import java8.util.function.BooleanSupplier;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class gt implements BooleanSupplier {
    private final gr.e a;

    private gt(gr.e eVar) {
        this.a = eVar;
    }

    public static BooleanSupplier a(gr.e eVar) {
        return new gt(eVar);
    }

    @Override // java8.util.function.BooleanSupplier
    public boolean getAsBoolean() {
        boolean tryAdvance;
        tryAdvance = r0.c.tryAdvance(this.a.d);
        return tryAdvance;
    }
}
