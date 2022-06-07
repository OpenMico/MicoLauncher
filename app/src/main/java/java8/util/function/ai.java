package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ai implements Predicate {
    private final Predicate a;
    private final Predicate b;

    private ai(Predicate predicate, Predicate predicate2) {
        this.a = predicate;
        this.b = predicate2;
    }

    public static Predicate a(Predicate predicate, Predicate predicate2) {
        return new ai(predicate, predicate2);
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return Predicates.a(this.a, this.b, obj);
    }
}
