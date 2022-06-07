package java8.util.stream;

import java8.util.StringJoiner;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class v implements Function {
    private static final v a = new v();

    private v() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return ((StringJoiner) obj).toString();
    }
}
