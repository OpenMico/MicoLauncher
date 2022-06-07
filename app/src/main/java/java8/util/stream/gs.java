package java8.util.stream;

import java8.util.function.Consumer;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class gs implements Consumer {
    private final gr.d a;
    private final Consumer b;

    private gs(gr.d dVar, Consumer consumer) {
        this.a = dVar;
        this.b = consumer;
    }

    public static Consumer a(gr.d dVar, Consumer consumer) {
        return new gs(dVar, consumer);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        gr.d.a(this.a, this.b, obj);
    }
}
