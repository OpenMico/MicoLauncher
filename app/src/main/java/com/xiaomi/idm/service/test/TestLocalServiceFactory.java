package com.xiaomi.idm.service.test;

import com.xiaomi.idm.api.IDMServiceFactoryBase;

/* loaded from: classes3.dex */
public class TestLocalServiceFactory extends IDMServiceFactoryBase {
    /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaomi.idm.api.IDMServiceFactoryBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.idm.api.IDMService createIDMService(com.xiaomi.idm.api.IDMClient r5, com.xiaomi.idm.api.proto.IDMServiceProto.IDMService r6) {
        /*
            r4 = this;
            if (r6 == 0) goto L_0x0023
            java.lang.String r0 = r6.getType()
            r1 = -1
            int r2 = r0.hashCode()
            r3 = -1848362396(0xffffffff91d43a64, float:-3.3483688E-28)
            if (r2 == r3) goto L_0x0011
            goto L_0x001a
        L_0x0011:
            java.lang.String r2 = "TestBuiltinService"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x001a
            r1 = 0
        L_0x001a:
            if (r1 == 0) goto L_0x001d
            goto L_0x0023
        L_0x001d:
            com.xiaomi.idm.service.test.TestBuiltinService$Stub r0 = new com.xiaomi.idm.service.test.TestBuiltinService$Stub
            r0.<init>(r5, r6)
            goto L_0x0024
        L_0x0023:
            r0 = 0
        L_0x0024:
            if (r0 != 0) goto L_0x002a
            com.xiaomi.idm.api.IDMService r0 = super.createIDMService(r5, r6)
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.idm.service.test.TestLocalServiceFactory.createIDMService(com.xiaomi.idm.api.IDMClient, com.xiaomi.idm.api.proto.IDMServiceProto$IDMService):com.xiaomi.idm.api.IDMService");
    }
}
