package java8.util.stream;

import java8.util.function.IntFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class d implements IntFunction {
    private static final d a = new d();

    private d() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return a.a(i);
    }
}
