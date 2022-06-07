package java8.util.stream;

import java8.util.function.Consumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fo implements Consumer {
    private static final fo a = new fo();

    private fo() {
    }

    public static Consumer a() {
        return a;
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        fn.a(obj);
    }
}
