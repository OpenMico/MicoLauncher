package org.apache.commons.lang;

/* compiled from: IntHashMap.java */
/* loaded from: classes5.dex */
class b {
    private transient a[] a;
    private transient int b;
    private int c;
    private final float d;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: IntHashMap.java */
    /* loaded from: classes5.dex */
    public static class a {
        final int a;
        final int b;
        Object c;
        a d;

        protected a(int i, int i2, Object obj, a aVar) {
            this.a = i;
            this.b = i2;
            this.c = obj;
            this.d = aVar;
        }
    }

    public b() {
        this(20, 0.75f);
    }

    public b(int i, float f) {
        if (i < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal Capacity: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        } else if (f > 0.0f) {
            i = i == 0 ? 1 : i;
            this.d = f;
            this.a = new a[i];
            this.c = (int) (i * f);
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Illegal Load: ");
            stringBuffer2.append(f);
            throw new IllegalArgumentException(stringBuffer2.toString());
        }
    }

    public Object a(int i) {
        a[] aVarArr = this.a;
        for (a aVar = aVarArr[(Integer.MAX_VALUE & i) % aVarArr.length]; aVar != null; aVar = aVar.d) {
            if (aVar.a == i) {
                return aVar.c;
            }
        }
        return null;
    }

    protected void a() {
        a[] aVarArr = this.a;
        int length = aVarArr.length;
        int i = (length * 2) + 1;
        a[] aVarArr2 = new a[i];
        this.c = (int) (i * this.d);
        this.a = aVarArr2;
        while (true) {
            int i2 = length - 1;
            if (length > 0) {
                a aVar = aVarArr[i2];
                while (aVar != null) {
                    a aVar2 = aVar.d;
                    int i3 = (aVar.a & Integer.MAX_VALUE) % i;
                    aVar.d = aVarArr2[i3];
                    aVarArr2[i3] = aVar;
                    aVar = aVar2;
                }
                length = i2;
            } else {
                return;
            }
        }
    }

    public Object a(int i, Object obj) {
        a[] aVarArr = this.a;
        int i2 = Integer.MAX_VALUE & i;
        int length = i2 % aVarArr.length;
        for (a aVar = aVarArr[length]; aVar != null; aVar = aVar.d) {
            if (aVar.a == i) {
                Object obj2 = aVar.c;
                aVar.c = obj;
                return obj2;
            }
        }
        if (this.b >= this.c) {
            a();
            aVarArr = this.a;
            length = i2 % aVarArr.length;
        }
        aVarArr[length] = new a(i, i, obj, aVarArr[length]);
        this.b++;
        return null;
    }
}
