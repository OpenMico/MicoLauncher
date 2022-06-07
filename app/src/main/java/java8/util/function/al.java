package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class al implements UnaryOperator {
    private static final al a = new al();

    private al() {
    }

    public static UnaryOperator a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return UnaryOperators.a(obj);
    }
}
