package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class k implements DoublePredicate {
    private final DoublePredicate a;

    private k(DoublePredicate doublePredicate) {
        this.a = doublePredicate;
    }

    public static DoublePredicate a(DoublePredicate doublePredicate) {
        return new k(doublePredicate);
    }

    @Override // java8.util.function.DoublePredicate
    public boolean test(double d) {
        return DoublePredicates.a(this.a, d);
    }
}
