package java8.util.stream;

import java8.util.StringJoiner;
import java8.util.function.BiConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class t implements BiConsumer {
    private static final t a = new t();

    private t() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((StringJoiner) obj).add((CharSequence) obj2);
    }
}
