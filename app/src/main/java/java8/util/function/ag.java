package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ag implements Predicate {
    private final Predicate a;
    private final Predicate b;

    private ag(Predicate predicate, Predicate predicate2) {
        this.a = predicate;
        this.b = predicate2;
    }

    public static Predicate a(Predicate predicate, Predicate predicate2) {
        return new ag(predicate, predicate2);
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return Predicates.b(this.a, this.b, obj);
    }
}
