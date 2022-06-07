package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ac implements LongPredicate {
    private final LongPredicate a;
    private final LongPredicate b;

    private ac(LongPredicate longPredicate, LongPredicate longPredicate2) {
        this.a = longPredicate;
        this.b = longPredicate2;
    }

    public static LongPredicate a(LongPredicate longPredicate, LongPredicate longPredicate2) {
        return new ac(longPredicate, longPredicate2);
    }

    @Override // java8.util.function.LongPredicate
    public boolean test(long j) {
        return LongPredicates.a(this.a, this.b, j);
    }
}
