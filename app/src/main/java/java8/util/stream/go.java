package java8.util.stream;

import java.util.List;
import java8.util.function.Consumer;

/* loaded from: classes5.dex */
final /* synthetic */ class go implements Consumer {
    private final List a;

    private go(List list) {
        this.a = list;
    }

    public static Consumer a(List list) {
        return new go(list);
    }

    @Override // java8.util.function.Consumer
    public void accept(Object obj) {
        this.a.add(obj);
    }
}
