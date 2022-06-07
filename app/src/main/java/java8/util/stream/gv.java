package java8.util.stream;

import java8.util.function.BooleanSupplier;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class gv implements BooleanSupplier {
    private final gr.h a;

    private gv(gr.h hVar) {
        this.a = hVar;
    }

    public static BooleanSupplier a(gr.h hVar) {
        return new gv(hVar);
    }

    @Override // java8.util.function.BooleanSupplier
    public boolean getAsBoolean() {
        boolean tryAdvance;
        tryAdvance = r0.c.tryAdvance(this.a.d);
        return tryAdvance;
    }
}
