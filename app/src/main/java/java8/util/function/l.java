package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class l implements DoublePredicate {
    private final DoublePredicate a;
    private final DoublePredicate b;

    private l(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        this.a = doublePredicate;
        this.b = doublePredicate2;
    }

    public static DoublePredicate a(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        return new l(doublePredicate, doublePredicate2);
    }

    @Override // java8.util.function.DoublePredicate
    public boolean test(double d) {
        return DoublePredicates.a(this.a, this.b, d);
    }
}
