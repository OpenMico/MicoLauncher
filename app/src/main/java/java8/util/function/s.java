package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class s implements IntConsumer {
    private final IntConsumer a;
    private final IntConsumer b;

    private s(IntConsumer intConsumer, IntConsumer intConsumer2) {
        this.a = intConsumer;
        this.b = intConsumer2;
    }

    public static IntConsumer a(IntConsumer intConsumer, IntConsumer intConsumer2) {
        return new s(intConsumer, intConsumer2);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        IntConsumers.a(this.a, this.b, i);
    }
}
