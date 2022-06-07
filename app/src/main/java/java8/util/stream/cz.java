package java8.util.stream;

import java8.util.function.Function;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class cz implements Function {
    private static final cz a = new cz();

    private cz() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return Collectors.c.a(obj);
    }
}
