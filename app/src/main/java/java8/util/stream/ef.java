package java8.util.stream;

import java8.util.function.IntFunction;
import java8.util.stream.ee;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ef implements IntFunction {
    private static final ef a = new ef();

    private ef() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return ee.b.a(i);
    }
}
