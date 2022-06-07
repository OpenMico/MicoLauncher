package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.Predicate;

/* loaded from: classes5.dex */
public class AppendOnlyLinkedArrayList<T> {
    final int a;
    final Object[] b;
    Object[] c;
    int d;

    /* loaded from: classes5.dex */
    public interface NonThrowingPredicate<T> extends Predicate<T> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        boolean test(T t);
    }

    public AppendOnlyLinkedArrayList(int i) {
        this.a = i;
        this.b = new Object[i + 1];
        this.c = this.b;
    }

    public void add(T t) {
        int i = this.a;
        int i2 = this.d;
        if (i2 == i) {
            Object[] objArr = new Object[i + 1];
            this.c[i] = objArr;
            this.c = objArr;
            i2 = 0;
        }
        this.c[i2] = t;
        this.d = i2 + 1;
    }

    public void setFirst(T t) {
        this.b[0] = t;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0018, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void forEachWhile(io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate<? super T> r5) {
        /*
            r4 = this;
            java.lang.Object[] r0 = r4.b
            int r1 = r4.a
        L_0x0004:
            if (r0 == 0) goto L_0x001d
            r2 = 0
        L_0x0007:
            if (r2 >= r1) goto L_0x0018
            r3 = r0[r2]
            if (r3 != 0) goto L_0x000e
            goto L_0x0018
        L_0x000e:
            boolean r3 = r5.test(r3)
            if (r3 == 0) goto L_0x0015
            return
        L_0x0015:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0018:
            r0 = r0[r1]
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            goto L_0x0004
        L_0x001d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.forEachWhile(io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList$NonThrowingPredicate):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0019, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <U> boolean accept(org.reactivestreams.Subscriber<? super U> r5) {
        /*
            r4 = this;
            java.lang.Object[] r0 = r4.b
            int r1 = r4.a
        L_0x0004:
            r2 = 0
            if (r0 == 0) goto L_0x001e
        L_0x0007:
            if (r2 >= r1) goto L_0x0019
            r3 = r0[r2]
            if (r3 != 0) goto L_0x000e
            goto L_0x0019
        L_0x000e:
            boolean r3 = io.reactivex.rxjava3.internal.util.NotificationLite.acceptFull(r3, r5)
            if (r3 == 0) goto L_0x0016
            r5 = 1
            return r5
        L_0x0016:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0019:
            r0 = r0[r1]
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            goto L_0x0004
        L_0x001e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.accept(org.reactivestreams.Subscriber):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0019, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <U> boolean accept(io.reactivex.rxjava3.core.Observer<? super U> r5) {
        /*
            r4 = this;
            java.lang.Object[] r0 = r4.b
            int r1 = r4.a
        L_0x0004:
            r2 = 0
            if (r0 == 0) goto L_0x001e
        L_0x0007:
            if (r2 >= r1) goto L_0x0019
            r3 = r0[r2]
            if (r3 != 0) goto L_0x000e
            goto L_0x0019
        L_0x000e:
            boolean r3 = io.reactivex.rxjava3.internal.util.NotificationLite.acceptFull(r3, r5)
            if (r3 == 0) goto L_0x0016
            r5 = 1
            return r5
        L_0x0016:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x0019:
            r0 = r0[r1]
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            goto L_0x0004
        L_0x001e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.accept(io.reactivex.rxjava3.core.Observer):boolean");
    }

    public <S> void forEachWhile(S s, BiPredicate<? super S, ? super T> biPredicate) throws Throwable {
        Object[] objArr = this.b;
        int i = this.a;
        while (true) {
            for (int i2 = 0; i2 < i; i2++) {
                Object obj = objArr[i2];
                if (obj == null || biPredicate.test(s, obj)) {
                    return;
                }
            }
            objArr = objArr[i];
        }
    }
}
