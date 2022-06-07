package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class u implements IntPredicate {
    private final IntPredicate a;

    private u(IntPredicate intPredicate) {
        this.a = intPredicate;
    }

    public static IntPredicate a(IntPredicate intPredicate) {
        return new u(intPredicate);
    }

    @Override // java8.util.function.IntPredicate
    public boolean test(int i) {
        return IntPredicates.a(this.a, i);
    }
}
