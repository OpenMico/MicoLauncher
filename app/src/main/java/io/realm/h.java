package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RealmList.java */
/* loaded from: classes5.dex */
public final class h<T> extends i<T> {
    @Override // io.realm.i
    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(BaseRealm baseRealm, OsList osList, Class<T> cls) {
        super(baseRealm, osList, cls);
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.Long] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // io.realm.i
    @javax.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public T b(int r4) {
        /*
            r3 = this;
            io.realm.internal.OsList r0 = r3.b
            long r1 = (long) r4
            java.lang.Object r4 = r0.getValue(r1)
            java.lang.Long r4 = (java.lang.Long) r4
            if (r4 != 0) goto L_0x000d
            r4 = 0
            return r4
        L_0x000d:
            java.lang.Class r0 = r3.c
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            if (r0 != r1) goto L_0x0014
            return r4
        L_0x0014:
            java.lang.Class r0 = r3.c
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            if (r0 != r1) goto L_0x0029
            java.lang.Class r0 = r3.c
            int r4 = r4.intValue()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object r4 = r0.cast(r4)
            return r4
        L_0x0029:
            java.lang.Class r0 = r3.c
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            if (r0 != r1) goto L_0x003e
            java.lang.Class r0 = r3.c
            short r4 = r4.shortValue()
            java.lang.Short r4 = java.lang.Short.valueOf(r4)
            java.lang.Object r4 = r0.cast(r4)
            return r4
        L_0x003e:
            java.lang.Class r0 = r3.c
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            if (r0 != r1) goto L_0x0053
            java.lang.Class r0 = r3.c
            byte r4 = r4.byteValue()
            java.lang.Byte r4 = java.lang.Byte.valueOf(r4)
            java.lang.Object r4 = r0.cast(r4)
            return r4
        L_0x0053:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Unexpected element type: "
            r0.append(r1)
            java.lang.Class r1 = r3.c
            java.lang.String r1 = r1.getName()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.realm.h.b(int):java.lang.Object");
    }

    @Override // io.realm.i
    protected void a(@Nullable Object obj) {
        if (obj != null && !(obj instanceof Number)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.Long, java.lang.Integer, java.lang.Short, java.lang.Byte", obj.getClass().getName()));
        }
    }

    @Override // io.realm.i
    public void b(Object obj) {
        this.b.addLong(((Number) obj).longValue());
    }

    @Override // io.realm.i
    public void a(int i, Object obj) {
        this.b.insertLong(i, ((Number) obj).longValue());
    }

    @Override // io.realm.i
    protected void b(int i, Object obj) {
        this.b.setLong(i, ((Number) obj).longValue());
    }
}
