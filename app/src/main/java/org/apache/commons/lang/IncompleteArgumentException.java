package org.apache.commons.lang;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class IncompleteArgumentException extends IllegalArgumentException {
    private static final long serialVersionUID = 4954193403612068178L;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public IncompleteArgumentException(java.lang.String r2) {
        /*
            r1 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = " is incomplete."
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.IncompleteArgumentException.<init>(java.lang.String):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public IncompleteArgumentException(java.lang.String r2, java.lang.String[] r3) {
        /*
            r1 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = " is missing the following items: "
            r0.append(r2)
            java.lang.String r2 = a(r3)
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.IncompleteArgumentException.<init>(java.lang.String, java.lang.String[]):void");
    }

    private static final String a(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        return Arrays.asList(objArr).toString();
    }
}
