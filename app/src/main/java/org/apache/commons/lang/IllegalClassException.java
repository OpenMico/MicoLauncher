package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class IllegalClassException extends IllegalArgumentException {
    private static final long serialVersionUID = 8063272569377254819L;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public IllegalClassException(java.lang.Class r3, java.lang.Object r4) {
        /*
            r2 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "Expected: "
            r0.append(r1)
            java.lang.String r3 = a(r3)
            r0.append(r3)
            java.lang.String r3 = ", actual: "
            r0.append(r3)
            if (r4 != 0) goto L_0x001b
            java.lang.String r3 = "null"
            goto L_0x0023
        L_0x001b:
            java.lang.Class r3 = r4.getClass()
            java.lang.String r3 = r3.getName()
        L_0x0023:
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r2.<init>(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.IllegalClassException.<init>(java.lang.Class, java.lang.Object):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public IllegalClassException(java.lang.Class r3, java.lang.Class r4) {
        /*
            r2 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "Expected: "
            r0.append(r1)
            java.lang.String r3 = a(r3)
            r0.append(r3)
            java.lang.String r3 = ", actual: "
            r0.append(r3)
            java.lang.String r3 = a(r4)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            r2.<init>(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.IllegalClassException.<init>(java.lang.Class, java.lang.Class):void");
    }

    public IllegalClassException(String str) {
        super(str);
    }

    private static final String a(Class cls) {
        if (cls == null) {
            return null;
        }
        return cls.getName();
    }
}
