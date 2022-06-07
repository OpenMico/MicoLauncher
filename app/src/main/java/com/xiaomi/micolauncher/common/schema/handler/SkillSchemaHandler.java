package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.net.Uri;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.instruciton.impl.DeviceScanOperation;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class SkillSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "skill".equalsIgnoreCase(uri.getAuthority());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        String path = uri.getPath();
        if (path != null) {
            L.base.d("SkillSchemaHandler process.path=%s", path);
            char c = 65535;
            int hashCode = path.hashCode();
            if (hashCode != 46727238) {
                if (hashCode == 46753436 && path.equals("/mesh")) {
                    c = 0;
                }
            } else if (path.equals("/like")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    String queryParameter = uri.getQueryParameter("action");
                    if (DeviceScanOperation.ACTION_DISCOVER_BY_UBUS.equals(queryParameter) || DeviceScanOperation.ACTION_DISCOVER_DEVICE.equals(queryParameter)) {
                        MiotProvisionManagerWrapper.getInstance().discoveryDevice();
                        return;
                    }
                    return;
                case 1:
                    ThirdPartyAppProxy.getInstance().like(context);
                    return;
                default:
                    return;
            }
        }
    }
}
