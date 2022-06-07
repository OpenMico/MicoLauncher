package io.netty.util.internal.logging;

/* compiled from: FormattingTuple.java */
/* loaded from: classes4.dex */
class b {
    static final b a = new b(null);
    private final String b;
    private final Throwable c;
    private final Object[] d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(String str) {
        this(str, null, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(String str, Object[] objArr, Throwable th) {
        this.b = str;
        this.c = th;
        if (th == null) {
            this.d = objArr;
        } else {
            this.d = a(objArr);
        }
    }

    static Object[] a(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return objArr2;
    }

    public String a() {
        return this.b;
    }

    public Throwable b() {
        return this.c;
    }
}
