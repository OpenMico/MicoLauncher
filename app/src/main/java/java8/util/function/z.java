package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class z implements LongConsumer {
    private final LongConsumer a;
    private final LongConsumer b;

    private z(LongConsumer longConsumer, LongConsumer longConsumer2) {
        this.a = longConsumer;
        this.b = longConsumer2;
    }

    public static LongConsumer a(LongConsumer longConsumer, LongConsumer longConsumer2) {
        return new z(longConsumer, longConsumer2);
    }

    @Override // java8.util.function.LongConsumer
    public void accept(long j) {
        LongConsumers.a(this.a, this.b, j);
    }
}
