package java8.util.function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ah implements Predicate {
    private final Predicate a;

    private ah(Predicate predicate) {
        this.a = predicate;
    }

    public static Predicate a(Predicate predicate) {
        return new ah(predicate);
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return Predicates.a(this.a, obj);
    }
}
