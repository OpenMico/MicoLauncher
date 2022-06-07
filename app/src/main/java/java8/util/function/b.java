package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class b implements BiFunction {
    private final Function a;
    private final BiFunction b;

    private b(Function function, BiFunction biFunction) {
        this.a = function;
        this.b = biFunction;
    }

    public static BiFunction a(Function function, BiFunction biFunction) {
        return new b(function, biFunction);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        Object apply;
        apply = this.a.apply(this.b.apply(obj, obj2));
        return apply;
    }
}
