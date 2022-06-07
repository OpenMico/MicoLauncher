package com.xiaomi.ai.a.a;

import com.xiaomi.ai.a.a;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import java.util.Map;

/* loaded from: classes.dex */
public class b extends a {
    private String d;
    private String e;

    public b(com.xiaomi.ai.core.a aVar) {
        super(2, aVar);
        c();
    }

    private void c() {
        this.d = this.b.getAivsConfig().getString(AivsConfig.Auth.CLIENT_ID);
        if (f.a(this.d)) {
            Logger.d("MIOTProvider", "initProvider: CLIENT_ID is not set");
            throw new IllegalArgumentException("CLIENT_ID is not set");
        }
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2) {
        return this.e;
    }

    @Override // com.xiaomi.ai.a.a
    public String a(boolean z, boolean z2, Map<String, String> map) {
        Logger.b("MIOTProvider", "getAuthHeader: forceRefresh : " + z + " isTrack : " + z2);
        if (f.a(this.e) || z) {
            this.e = this.b.getListener().a(this.b, z);
        }
        if (!f.a(this.e)) {
            return String.format("%s app_id:%s,%s", "MIOT-TOKEN-V1", this.d, this.e);
        }
        Logger.d("MIOTProvider", "getAuthHeader:token is empty");
        return null;
    }
}
