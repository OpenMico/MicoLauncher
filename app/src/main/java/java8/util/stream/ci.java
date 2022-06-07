package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.BinaryOperator;
import java8.util.function.Function;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ci implements Supplier {
    private final Supplier a;
    private final Supplier b;
    private final BiConsumer c;
    private final BiConsumer d;
    private final BinaryOperator e;
    private final BinaryOperator f;
    private final Function g;
    private final Function h;
    private final BiFunction i;

    private ci(Supplier supplier, Supplier supplier2, BiConsumer biConsumer, BiConsumer biConsumer2, BinaryOperator binaryOperator, BinaryOperator binaryOperator2, Function function, Function function2, BiFunction biFunction) {
        this.a = supplier;
        this.b = supplier2;
        this.c = biConsumer;
        this.d = biConsumer2;
        this.e = binaryOperator;
        this.f = binaryOperator2;
        this.g = function;
        this.h = function2;
        this.i = biFunction;
    }

    public static Supplier a(Supplier supplier, Supplier supplier2, BiConsumer biConsumer, BiConsumer biConsumer2, BinaryOperator binaryOperator, BinaryOperator binaryOperator2, Function function, Function function2, BiFunction biFunction) {
        return new ci(supplier, supplier2, biConsumer, biConsumer2, binaryOperator, binaryOperator2, function, function2, biFunction);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
    }
}
