package io.reactivex.internal.util;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class LinkedArrayList {
    final int a;
    Object[] b;
    Object[] c;
    volatile int d;
    int e;

    public LinkedArrayList(int i) {
        this.a = i;
    }

    public void add(Object obj) {
        if (this.d == 0) {
            this.b = new Object[this.a + 1];
            Object[] objArr = this.b;
            this.c = objArr;
            objArr[0] = obj;
            this.e = 1;
            this.d = 1;
            return;
        }
        int i = this.e;
        int i2 = this.a;
        if (i == i2) {
            Object[] objArr2 = new Object[i2 + 1];
            objArr2[0] = obj;
            this.c[i2] = objArr2;
            this.c = objArr2;
            this.e = 1;
            this.d++;
            return;
        }
        this.c[i] = obj;
        this.e = i + 1;
        this.d++;
    }

    public Object[] head() {
        return this.b;
    }

    public int size() {
        return this.d;
    }

    public String toString() {
        int i = this.a;
        int i2 = this.d;
        ArrayList arrayList = new ArrayList(i2 + 1);
        Object[] head = head();
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            arrayList.add(head[i4]);
            i3++;
            i4++;
            if (i4 == i) {
                head = (Object[]) head[i];
                i4 = 0;
            }
        }
        return arrayList.toString();
    }
}
