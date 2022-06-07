package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.xiaomi.micolauncher.common.ubus.UBus;

/* loaded from: classes3.dex */
public class MeshHandler implements UBus.UbusHandler {
    private static final String LOG_TAG = "[MeshHandler]: ";
    private static final String MODEL_NAME = "mibt_mesh";
    private static final String REBOOT_REASON_DISABLE_MESH = "disable mesh";
    private static final String URI_MESH_SCAN = "mico://skill/mesh?action=action_discover_device_by_ubus";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.equals(MODEL_NAME);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0093  */
    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String handle(android.content.Context r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r5 = this;
            com.xiaomi.micolauncher.common.ubus.UBus$Response r7 = new com.xiaomi.micolauncher.common.ubus.UBus$Response
            r7.<init>()
            r9 = 0
            r7.code = r9
            java.lang.String r0 = "success"
            r7.info = r0
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.mesh
            java.lang.String r1 = "%s [handle method]: %s"
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r4 = "[MeshHandler]: "
            r3[r9] = r4
            r4 = 1
            r3[r4] = r8
            r0.i(r1, r3)
            if (r8 == 0) goto L_0x00ae
            r0 = -1
            int r1 = r8.hashCode()
            r3 = -1756424817(0xffffffff974f158f, float:-6.691252E-25)
            if (r1 == r3) goto L_0x0057
            r2 = -392759275(0xffffffffe896f815, float:-5.70345E24)
            if (r1 == r2) goto L_0x004d
            r2 = -304700778(0xffffffffedd6a296, float:-8.3032933E27)
            if (r1 == r2) goto L_0x0043
            r2 = 13607556(0xcfa284, float:1.9068247E-38)
            if (r1 == r2) goto L_0x0039
            goto L_0x0061
        L_0x0039:
            java.lang.String r1 = "mesh_status"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0061
            r8 = 3
            goto L_0x0062
        L_0x0043:
            java.lang.String r1 = "mesh_disable"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0061
            r8 = r4
            goto L_0x0062
        L_0x004d:
            java.lang.String r1 = "mesh_enable"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0061
            r8 = r9
            goto L_0x0062
        L_0x0057:
            java.lang.String r1 = "mesh_scan"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0061
            r8 = r2
            goto L_0x0062
        L_0x0061:
            r8 = r0
        L_0x0062:
            switch(r8) {
                case 0: goto L_0x0093;
                case 1: goto L_0x0077;
                case 2: goto L_0x0071;
                case 3: goto L_0x0066;
                default: goto L_0x0065;
            }
        L_0x0065:
            goto L_0x00ae
        L_0x0066:
            com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper r6 = com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.getInstance()
            java.lang.String r6 = r6.getMeshStatusForApp()
            r7.info = r6
            goto L_0x00ae
        L_0x0071:
            java.lang.String r8 = "mico://skill/mesh?action=action_discover_device_by_ubus"
            com.xiaomi.micolauncher.common.schema.SchemaManager.handleSchema(r6, r8)
            goto L_0x00ae
        L_0x0077:
            boolean r6 = com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.isMeshEnable()
            if (r6 != 0) goto L_0x008b
            com.elvishew.xlog.Logger r6 = com.xiaomi.micolauncher.common.L.mesh
            java.lang.String r8 = "%s mesh is already disabled!"
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r1 = "[MeshHandler]: "
            r0[r9] = r1
            r6.d(r8, r0)
            goto L_0x00ae
        L_0x008b:
            com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper r6 = com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.getInstance()
            r6.quitMesh()
            goto L_0x00ae
        L_0x0093:
            boolean r6 = com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.isMeshEnable()
            if (r6 == 0) goto L_0x00a7
            com.elvishew.xlog.Logger r6 = com.xiaomi.micolauncher.common.L.mesh
            java.lang.String r8 = "%s mesh is already enabled!"
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r1 = "[MeshHandler]: "
            r0[r9] = r1
            r6.d(r8, r0)
            goto L_0x00ae
        L_0x00a7:
            com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper r6 = com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper.getInstance()
            r6.initMiotMeshManager(r9)
        L_0x00ae:
            java.lang.String r6 = r7.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.ubus.handlers.MeshHandler.handle(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }
}
