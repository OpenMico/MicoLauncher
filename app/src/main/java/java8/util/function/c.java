package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class c implements BiPredicate {
    private final BiPredicate a;
    private final BiPredicate b;

    private c(BiPredicate biPredicate, BiPredicate biPredicate2) {
        this.a = biPredicate;
        this.b = biPredicate2;
    }

    public static BiPredicate a(BiPredicate biPredicate, BiPredicate biPredicate2) {
        return new c(biPredicate, biPredicate2);
    }

    @Override // java8.util.function.BiPredicate
    public boolean test(Object obj, Object obj2) {
        return BiPredicates.b(this.a, this.b, obj, obj2);
    }
}
