package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ab implements LongPredicate {
    private final LongPredicate a;

    private ab(LongPredicate longPredicate) {
        this.a = longPredicate;
    }

    public static LongPredicate a(LongPredicate longPredicate) {
        return new ab(longPredicate);
    }

    @Override // java8.util.function.LongPredicate
    public boolean test(long j) {
        return LongPredicates.a(this.a, j);
    }
}
