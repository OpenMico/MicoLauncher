package java8.util.stream;

import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ak implements Function {
    private static final ak a = new ak();

    private ak() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Long valueOf;
        valueOf = Long.valueOf(((long[]) obj)[0]);
        return valueOf;
    }
}
