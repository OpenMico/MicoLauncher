package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class q implements Function {
    private static final q a = new q();

    private q() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return ((StringBuilder) obj).toString();
    }
}
