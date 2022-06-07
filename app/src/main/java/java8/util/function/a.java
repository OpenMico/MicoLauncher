package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class a implements BiConsumer {
    private final BiConsumer a;
    private final BiConsumer b;

    private a(BiConsumer biConsumer, BiConsumer biConsumer2) {
        this.a = biConsumer;
        this.b = biConsumer2;
    }

    public static BiConsumer a(BiConsumer biConsumer, BiConsumer biConsumer2) {
        return new a(biConsumer, biConsumer2);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        BiConsumers.a(this.a, this.b, obj, obj2);
    }
}
