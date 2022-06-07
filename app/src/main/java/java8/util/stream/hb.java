package java8.util.stream;

import java8.util.function.Consumer;

/* loaded from: classes5.dex */
final /* synthetic */ class hb implements Consumer {
    private final gn a;

    private hb(gn gnVar) {
        this.a = gnVar;
    }

    public static Consumer a(gn gnVar) {
        return new hb(gnVar);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.accept(obj);
    }
}
