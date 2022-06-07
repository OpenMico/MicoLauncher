package org.eclipse.jetty.util.ajax;

import org.eclipse.jetty.util.ajax.JSON;

/* loaded from: classes5.dex */
public class JSONPojoConvertorFactory implements JSON.Convertor {
    private final boolean _fromJson;
    private final JSON _json;

    public JSONPojoConvertorFactory(JSON json) {
        if (json != null) {
            this._json = json;
            this._fromJson = true;
            return;
        }
        throw new IllegalArgumentException();
    }

    public JSONPojoConvertorFactory(JSON json, boolean z) {
        if (json != null) {
            this._json = json;
            this._fromJson = z;
            return;
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void toJSON(java.lang.Object r6, org.eclipse.jetty.util.ajax.JSON.Output r7) {
        /*
            r5 = this;
            java.lang.Class r0 = r6.getClass()
            java.lang.String r0 = r0.getName()
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json
            org.eclipse.jetty.util.ajax.JSON$Convertor r1 = r1.getConvertorFor(r0)
            if (r1 != 0) goto L_0x002d
            java.lang.Class<org.eclipse.jetty.util.ajax.JSON> r2 = org.eclipse.jetty.util.ajax.JSON.class
            java.lang.Class r2 = org.eclipse.jetty.util.Loader.loadClass(r2, r0)     // Catch: ClassNotFoundException -> 0x0027
            org.eclipse.jetty.util.ajax.JSONPojoConvertor r3 = new org.eclipse.jetty.util.ajax.JSONPojoConvertor     // Catch: ClassNotFoundException -> 0x0027
            boolean r4 = r5._fromJson     // Catch: ClassNotFoundException -> 0x0027
            r3.<init>(r2, r4)     // Catch: ClassNotFoundException -> 0x0027
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json     // Catch: ClassNotFoundException -> 0x0024
            r1.addConvertorFor(r0, r3)     // Catch: ClassNotFoundException -> 0x0024
            r1 = r3
            goto L_0x002d
        L_0x0024:
            r0 = move-exception
            r1 = r3
            goto L_0x0028
        L_0x0027:
            r0 = move-exception
        L_0x0028:
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.ajax.JSON.LOG
            r2.warn(r0)
        L_0x002d:
            if (r1 == 0) goto L_0x0032
            r1.toJSON(r6, r7)
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSONPojoConvertorFactory.toJSON(java.lang.Object, org.eclipse.jetty.util.ajax.JSON$Output):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object fromJSON(java.util.Map r6) {
        /*
            r5 = this;
            java.lang.String r0 = "class"
            java.lang.Object r0 = r6.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0036
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json
            org.eclipse.jetty.util.ajax.JSON$Convertor r1 = r1.getConvertorFor(r0)
            if (r1 != 0) goto L_0x002f
            java.lang.Class<org.eclipse.jetty.util.ajax.JSON> r2 = org.eclipse.jetty.util.ajax.JSON.class
            java.lang.Class r2 = org.eclipse.jetty.util.Loader.loadClass(r2, r0)     // Catch: ClassNotFoundException -> 0x0029
            org.eclipse.jetty.util.ajax.JSONPojoConvertor r3 = new org.eclipse.jetty.util.ajax.JSONPojoConvertor     // Catch: ClassNotFoundException -> 0x0029
            boolean r4 = r5._fromJson     // Catch: ClassNotFoundException -> 0x0029
            r3.<init>(r2, r4)     // Catch: ClassNotFoundException -> 0x0029
            org.eclipse.jetty.util.ajax.JSON r1 = r5._json     // Catch: ClassNotFoundException -> 0x0026
            r1.addConvertorFor(r0, r3)     // Catch: ClassNotFoundException -> 0x0026
            r1 = r3
            goto L_0x002f
        L_0x0026:
            r0 = move-exception
            r1 = r3
            goto L_0x002a
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.util.ajax.JSON.LOG
            r2.warn(r0)
        L_0x002f:
            if (r1 == 0) goto L_0x0036
            java.lang.Object r6 = r1.fromJSON(r6)
            return r6
        L_0x0036:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSONPojoConvertorFactory.fromJSON(java.util.Map):java.lang.Object");
    }
}
