package java8.util.stream;

import java8.util.function.Consumer;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class ha implements Consumer {
    private static final ha a = new ha();

    private ha() {
    }

    public static Consumer a() {
        return a;
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        gr.i.e.a(obj);
    }
}
