package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class y implements IntUnaryOperator {
    private static final y a = new y();

    private y() {
    }

    public static IntUnaryOperator a() {
        return a;
    }

    @Override // java8.util.function.IntUnaryOperator
    public int applyAsInt(int i) {
        return IntUnaryOperators.a(i);
    }
}
