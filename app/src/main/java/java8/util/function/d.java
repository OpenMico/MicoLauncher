package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class d implements BiPredicate {
    private final BiPredicate a;

    private d(BiPredicate biPredicate) {
        this.a = biPredicate;
    }

    public static BiPredicate a(BiPredicate biPredicate) {
        return new d(biPredicate);
    }

    @Override // java8.util.function.BiPredicate
    public boolean test(Object obj, Object obj2) {
        return BiPredicates.a(this.a, obj, obj2);
    }
}
