package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ak implements Predicate {
    private final Object a;

    private ak(Object obj) {
        this.a = obj;
    }

    public static Predicate a(Object obj) {
        return new ak(obj);
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        boolean equals;
        equals = this.a.equals(obj);
        return equals;
    }
}
