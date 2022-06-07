package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class i implements DoubleConsumer {
    private final DoubleConsumer a;
    private final DoubleConsumer b;

    private i(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        this.a = doubleConsumer;
        this.b = doubleConsumer2;
    }

    public static DoubleConsumer a(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        return new i(doubleConsumer, doubleConsumer2);
    }

    @Override // java8.util.function.DoubleConsumer
    public void accept(double d) {
        DoubleConsumers.a(this.a, this.b, d);
    }
}
