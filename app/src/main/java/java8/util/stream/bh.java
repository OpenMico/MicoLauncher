package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bh implements Supplier {
    private final BinaryOperator a;

    private bh(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static Supplier a(BinaryOperator binaryOperator) {
        return new bh(binaryOperator);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a(this.a);
    }
}
