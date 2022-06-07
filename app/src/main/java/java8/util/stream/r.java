package java8.util.stream;

import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class r implements Supplier {
    private final CharSequence a;
    private final CharSequence b;
    private final CharSequence c;

    private r(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.a = charSequence;
        this.b = charSequence2;
        this.c = charSequence3;
    }

    public static Supplier a(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new r(charSequence, charSequence2, charSequence3);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a(this.a, this.b, this.c);
    }
}
