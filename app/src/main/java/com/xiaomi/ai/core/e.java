package com.xiaomi.ai.core;

import com.xiaomi.ai.a.a;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.transport.b;
import com.xiaomi.ai.transport.c;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;

/* loaded from: classes3.dex */
public class e extends a {
    private volatile boolean a;
    private OkHttpClient b;
    private c c;

    public e(AivsConfig aivsConfig, Settings.ClientInfo clientInfo, int i, b bVar) {
        super(aivsConfig, clientInfo, i, bVar);
        a();
        b();
        this.mHttpDns = this.a ? new b(this, new c(this.mAivsConfig).a()) : new b(this, new c(this.mAivsConfig).b());
    }

    public e(AivsConfig aivsConfig, Settings.ClientInfo clientInfo, a aVar, b bVar) {
        super(aivsConfig, clientInfo, aVar, bVar);
        a();
        b();
        this.mHttpDns = this.a ? new b(this, new c(this.mAivsConfig).a()) : new b(this, new c(this.mAivsConfig).b());
    }

    private Map<String, String> a(boolean z) {
        HashMap hashMap = new HashMap();
        String a = this.mAuthProvider.a(z, true, (Map<String, String>) hashMap);
        if (a == null) {
            Logger.d("WSChannel", "getHeaders: failed to getAuthHeader");
            this.mLastError = this.mAuthProvider.a();
            return null;
        }
        hashMap.put("Authorization", a);
        String string = this.mAivsConfig.getString(AivsConfig.Connection.USER_AGENT);
        if (!f.a(string)) {
            hashMap.put("User-Agent", string);
        }
        hashMap.put("Heartbeat-Client", String.valueOf(this.mAivsConfig.getInt(AivsConfig.Connection.PING_INTERVAL, 90)));
        hashMap.put("Host", this.mHttpDns.a());
        String randomRequestId = APIUtils.randomRequestId(false);
        hashMap.put("Client-Connection-Id", randomRequestId);
        Logger.b("WSChannel", "request Headers: clientConnectionId = " + randomRequestId);
        if (Logger.getLogLevel() == 3) {
            Logger.a("WSChannel", "request Headers:\n" + f.a(hashMap));
        }
        return hashMap;
    }

    private void a() {
        this.b = new OkHttpClient.Builder().build();
    }

    private void b() {
        if (!this.mAivsConfig.getBoolean(AivsConfig.Connection.ENABLE_LITE_CRYPT)) {
            this.a = true;
            Logger.b("WSChannel", "checkWssMode:use wss by config");
            return;
        }
        String a = this.mListener.a(this, "link_mode");
        if (f.a(a) || !a.equals("wss")) {
            String a2 = this.mListener.a(this, "wss_expire_at");
            if (f.a(a2)) {
                this.a = false;
                Logger.c("WSChannel", "checkWssMode:not in wss mode");
            } else if (System.currentTimeMillis() / 1000 > Long.parseLong(a2)) {
                this.a = false;
                this.mListener.b(this, "wss_expire_at");
                Logger.c("WSChannel", "checkWssMode:wss mode expired, try ws mode");
            } else {
                this.a = true;
                Logger.c("WSChannel", "checkWssMode:in wss mode");
            }
        } else {
            this.a = true;
            Logger.b("WSChannel", "checkWssMode:use cloud wss mode");
        }
    }

    @Override // com.xiaomi.ai.core.a
    public String getChannelType() {
        return "ws";
    }

    @Override // com.xiaomi.ai.core.a
    public int getErrorCode() {
        c cVar = this.c;
        if (cVar != null) {
            return cVar.b();
        }
        return 0;
    }

    @Override // com.xiaomi.ai.core.a
    public int getFailureCode() {
        c cVar = this.c;
        if (cVar != null) {
            return cVar.c();
        }
        return 0;
    }

    @Override // com.xiaomi.ai.core.a
    public int getType() {
        return 0;
    }

    @Override // com.xiaomi.ai.core.a
    public boolean isConnected() {
        c cVar = this.c;
        return cVar != null && cVar.d();
    }

    @Override // com.xiaomi.ai.core.a
    public boolean postData(byte[] bArr) {
        boolean z;
        synchronized (this) {
            z = this.c != null && this.c.d() && this.c.a(bArr);
        }
        return z;
    }

    @Override // com.xiaomi.ai.core.a
    public boolean postData(byte[] bArr, int i, int i2) {
        synchronized (this) {
            if (this.c == null || !this.c.d()) {
                Logger.d("WSChannel", "postData: channel was not started");
                return false;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return this.c.a(bArr2);
        }
    }

    @Override // com.xiaomi.ai.core.a
    public boolean postEvent(Event event) {
        boolean z;
        synchronized (this) {
            updateGlobalConfig(event);
            z = this.c != null && this.c.d() && this.c.a(event);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x011e  */
    @Override // com.xiaomi.ai.core.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean startConnect(boolean r7) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.core.e.startConnect(boolean):boolean");
    }

    @Override // com.xiaomi.ai.core.a
    public void stop() {
        Logger.b("WSChannel", "stop");
        synchronized (this) {
            if (this.c != null) {
                this.c.a();
            }
        }
    }

    @Override // com.xiaomi.ai.core.a
    public void switchToWssMode() {
        String str;
        String str2;
        if (this.a) {
            str = "WSChannel";
            str2 = "switchToWss: already in wss mode";
        } else {
            this.mListener.a(this, "wss_expire_at", String.valueOf((System.currentTimeMillis() / 1000) + 259200));
            this.a = true;
            this.mHttpDns = new b(this, new c(this.mAivsConfig).a());
            str = "WSChannel";
            str2 = "switchToWss: done";
        }
        Logger.c(str, str2);
    }
}
