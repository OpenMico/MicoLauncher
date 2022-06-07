package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class aa implements LongPredicate {
    private final LongPredicate a;
    private final LongPredicate b;

    private aa(LongPredicate longPredicate, LongPredicate longPredicate2) {
        this.a = longPredicate;
        this.b = longPredicate2;
    }

    public static LongPredicate a(LongPredicate longPredicate, LongPredicate longPredicate2) {
        return new aa(longPredicate, longPredicate2);
    }

    @Override // java8.util.function.LongPredicate
    public boolean test(long j) {
        return LongPredicates.b(this.a, this.b, j);
    }
}
