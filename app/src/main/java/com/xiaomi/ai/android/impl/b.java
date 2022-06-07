package com.xiaomi.ai.android.impl;

import android.content.Context;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.android.capability.StorageCapability;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;

/* loaded from: classes2.dex */
public class b extends StorageCapability {
    private final Context a;
    private String b;
    private final d c;

    public b(d dVar, int i, int i2) {
        StringBuilder sb;
        String str;
        this.a = dVar.a();
        this.c = dVar;
        this.b = i2 == 2 ? "staging" : i2 == 1 ? "preview" : SchemaActivity.VALUE_ENV_PRODUCTION;
        switch (i) {
            case 1:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_DO-TOKEN-V1";
                break;
            case 2:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_MIOT-TOKEN-V1";
                break;
            case 3:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_TP-TOKEN-V1";
                break;
            case 4:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_AO-TOKEN-V1";
                break;
            case 5:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_AA-TOKEN-V1";
                break;
            case 6:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_DAA-TOKEN-V1";
                break;
            default:
                sb = new StringBuilder();
                sb.append(this.b);
                str = "_unknown_auth_type";
                break;
        }
        sb.append(str);
        this.b = sb.toString();
        if (dVar.b().getBoolean(AivsConfig.Auth.SUPPORT_MULTIPLY_CLIENT_ID)) {
            this.b += "_" + dVar.n().getDeviceId().get();
            this.b += "_" + dVar.b().getString(AivsConfig.Auth.CLIENT_ID);
        }
        this.b += "_";
    }

    @Override // com.xiaomi.ai.android.capability.StorageCapability
    public void clearKeyValue() {
        com.xiaomi.ai.android.utils.d.a(this.a, "aivs_user_data.xml");
    }

    @Override // com.xiaomi.ai.android.capability.StorageCapability
    public String readKeyValue(String str) {
        if (XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2.equals(str) || "refresh_token".equals(str) || "expire_at".equals(str)) {
            str = this.c.l() + str;
        }
        return com.xiaomi.ai.android.utils.d.a(this.a, "aivs_user_data.xml", this.b + str);
    }

    @Override // com.xiaomi.ai.android.capability.StorageCapability
    public void removeKeyValue(String str) {
        if (XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2.equals(str) || "refresh_token".equals(str) || "expire_at".equals(str)) {
            str = this.c.l() + str;
        }
        com.xiaomi.ai.android.utils.d.b(this.a, "aivs_user_data.xml", this.b + str);
    }

    @Override // com.xiaomi.ai.android.capability.StorageCapability
    public boolean writeKeyValue(String str, String str2) {
        if (XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2.equals(str) || "refresh_token".equals(str) || "expire_at".equals(str)) {
            str = this.c.l() + str;
        }
        com.xiaomi.ai.android.utils.d.a(this.a, "aivs_user_data.xml", this.b + str, str2);
        return true;
    }
}
