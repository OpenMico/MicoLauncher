package java8.util.function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class h implements Consumer {
    private final Consumer a;
    private final Consumer b;

    private h(Consumer consumer, Consumer consumer2) {
        this.a = consumer;
        this.b = consumer2;
    }

    public static Consumer a(Consumer consumer, Consumer consumer2) {
        return new h(consumer, consumer2);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        Consumers.a(this.a, this.b, obj);
    }
}
