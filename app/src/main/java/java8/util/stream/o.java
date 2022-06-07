package java8.util.stream;

import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class o implements BiConsumer {
    private static final o a = new o();

    private o() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((StringBuilder) obj).append((CharSequence) obj2);
    }
}
