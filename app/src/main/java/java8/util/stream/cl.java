package java8.util.stream;

import java8.util.function.Function;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class cl implements Function {
    private static final cl a = new cl();

    private cl() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        return ((Collectors.b) obj).a();
    }
}
