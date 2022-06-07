package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class t implements IntPredicate {
    private final IntPredicate a;
    private final IntPredicate b;

    private t(IntPredicate intPredicate, IntPredicate intPredicate2) {
        this.a = intPredicate;
        this.b = intPredicate2;
    }

    public static IntPredicate a(IntPredicate intPredicate, IntPredicate intPredicate2) {
        return new t(intPredicate, intPredicate2);
    }

    @Override // java8.util.function.IntPredicate
    public boolean test(int i) {
        return IntPredicates.b(this.a, this.b, i);
    }
}
