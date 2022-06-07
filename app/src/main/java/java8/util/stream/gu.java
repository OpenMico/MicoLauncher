package java8.util.stream;

import java8.util.function.BooleanSupplier;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class gu implements BooleanSupplier {
    private final gr.g a;

    private gu(gr.g gVar) {
        this.a = gVar;
    }

    public static BooleanSupplier a(gr.g gVar) {
        return new gu(gVar);
    }

    @Override // java8.util.function.BooleanSupplier
    public boolean getAsBoolean() {
        boolean tryAdvance;
        tryAdvance = r0.c.tryAdvance(this.a.d);
        return tryAdvance;
    }
}
