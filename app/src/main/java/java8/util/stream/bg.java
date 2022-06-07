package java8.util.stream;

import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bg implements Supplier {
    private final Object a;

    private bg(Object obj) {
        this.a = obj;
    }

    public static Supplier a(Object obj) {
        return new bg(obj);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a(this.a);
    }
}
