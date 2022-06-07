package org.slf4j.helpers;

/* loaded from: classes4.dex */
public class FormattingTuple {
    public static FormattingTuple NULL = new FormattingTuple(null);
    private String a;
    private Throwable b;
    private Object[] c;

    public FormattingTuple(String str) {
        this(str, null, null);
    }

    public FormattingTuple(String str, Object[] objArr, Throwable th) {
        this.a = str;
        this.b = th;
        if (th == null) {
            this.c = objArr;
        } else {
            this.c = a(objArr);
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

    public String getMessage() {
        return this.a;
    }

    public Object[] getArgArray() {
        return this.c;
    }

    public Throwable getThrowable() {
        return this.b;
    }
}
