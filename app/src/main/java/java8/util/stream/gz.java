package java8.util.stream;

import java8.util.function.Consumer;
import java8.util.stream.gr;

/* loaded from: classes5.dex */
final /* synthetic */ class gz implements Consumer {
    private static final gz a = new gz();

    private gz() {
    }

    public static Consumer a() {
        return a;
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        gr.i.e.b(obj);
    }
}
