package java8.util.function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class q implements Function {
    private final Function a;
    private final Function b;

    private q(Function function, Function function2) {
        this.a = function;
        this.b = function2;
    }

    public static Function a(Function function, Function function2) {
        return new q(function, function2);
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Object apply;
        apply = this.a.apply(this.b.apply(obj));
        return apply;
    }
}
