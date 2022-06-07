package com.xiaomi.ai.a;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.a.a.c;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public abstract class a {
    protected int a;
    protected com.xiaomi.ai.core.a b;
    protected AivsError c;
    private ObjectNode d;

    public a(int i) {
        this.a = i;
    }

    public a(int i, com.xiaomi.ai.core.a aVar) {
        this.a = i;
        this.b = aVar;
    }

    private void a(long j) {
        long j2;
        if (!this.b.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_REFRESH_TOKEN_AHEAD)) {
            Logger.b("AuthProvider", "refreshTokenIfNeed return ,not enable");
        } else if (!(this instanceof c)) {
            Logger.a("AuthProvider", "refreshTokenIfNeed return ,not OAuth token");
        } else {
            try {
                j2 = Long.parseLong(this.b.getListener().a(this.b, "refresh_at"));
            } catch (Exception e) {
                Logger.c("AuthProvider", "startRefreshTaskIfNeed get refreshAt error: " + e.toString());
                j2 = 0L;
            }
            if (j2 <= 0) {
                Logger.b("AuthProvider", "startRefreshTaskIfNeed fail ,refreshAt time error");
                return;
            }
            long nextInt = (j2 + ((long) (((new Random().nextInt(17) + 50) / 100.0d) * (j - j2)))) - (System.currentTimeMillis() / 1000);
            Logger.a("AuthProvider", "start RefreshTokenTask :" + nextInt);
            if (nextInt > 0) {
                Logger.b("AuthProvider", "refreshTokenIfNeed ,refresh next time ");
                return;
            }
            com.xiaomi.ai.b.c.a.execute(new Runnable() { // from class: com.xiaomi.ai.a.a.1
                @Override // java.lang.Runnable
                public void run() {
                    Logger.b("AuthProvider", "RefreshTokenRunnable run");
                    a.this.a(true, false, (Map<String, String>) null);
                }
            });
        }
    }

    public AivsError a() {
        AivsError aivsError = this.c;
        this.c = null;
        return aivsError;
    }

    public abstract String a(boolean z, boolean z2);

    public abstract String a(boolean z, boolean z2, Map<String, String> map);

    protected void a(int i, int i2, String str, boolean z) {
        if (this.b.getAivsConfig().getBoolean(AivsConfig.Track.ENABLE) && z) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = APIUtils.getObjectMapper().createObjectNode();
                    this.d.put("type", "gettoken");
                }
                this.d.put("status", i);
                this.d.put("result", i2);
                if (str != null) {
                    this.d.put("msg", str);
                }
                this.d.put("timestamp", System.currentTimeMillis());
                this.b.addTrackProcess(this.d);
                this.d = null;
            }
        }
    }

    public void a(com.xiaomi.ai.core.a aVar) {
        this.b = aVar;
    }

    protected void a(String str, int i, boolean z) {
        if (z) {
            this.b.updateTrack(str, i);
        }
    }

    protected void a(String str, int i, boolean z, boolean z2) {
        if (this.b.getAivsConfig().getBoolean(AivsConfig.Track.ENABLE) && z2) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = APIUtils.getObjectMapper().createObjectNode();
                    this.d.put("type", "gettoken");
                }
                this.d.put(str, i);
                if (z) {
                    this.d.put("timestamp", System.currentTimeMillis());
                    this.b.addTrackProcess(this.d);
                    this.d = null;
                }
            }
        }
    }

    protected void a(String str, String str2, boolean z) {
        if (z) {
            this.b.updateTrack(str, str2);
        }
    }

    protected void a(String str, String str2, boolean z, boolean z2) {
        if (this.b.getAivsConfig().getBoolean(AivsConfig.Track.ENABLE) && z2) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = APIUtils.getObjectMapper().createObjectNode();
                    this.d.put("type", "gettoken");
                }
                this.d.put(str, str2);
                if (z) {
                    this.d.put("timestamp", System.currentTimeMillis());
                    this.b.addTrackProcess(this.d);
                    this.d = null;
                }
            }
        }
    }

    public int b() {
        return this.a;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x010c A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x001a, B:8:0x002d, B:9:0x003b, B:10:0x003e, B:13:0x0042, B:15:0x0072, B:17:0x0078, B:19:0x00ab, B:23:0x00b5, B:25:0x00bb, B:27:0x00c6, B:29:0x00cc, B:30:0x00e8, B:33:0x00ec, B:35:0x00f1, B:36:0x00fc, B:38:0x010c, B:39:0x011e, B:41:0x0120, B:42:0x0127, B:44:0x012d, B:45:0x0134, B:46:0x013a, B:47:0x013e), top: B:52:0x0009, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0120 A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x001a, B:8:0x002d, B:9:0x003b, B:10:0x003e, B:13:0x0042, B:15:0x0072, B:17:0x0078, B:19:0x00ab, B:23:0x00b5, B:25:0x00bb, B:27:0x00c6, B:29:0x00cc, B:30:0x00e8, B:33:0x00ec, B:35:0x00f1, B:36:0x00fc, B:38:0x010c, B:39:0x011e, B:41:0x0120, B:42:0x0127, B:44:0x012d, B:45:0x0134, B:46:0x013a, B:47:0x013e), top: B:52:0x0009, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x012d A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x001a, B:8:0x002d, B:9:0x003b, B:10:0x003e, B:13:0x0042, B:15:0x0072, B:17:0x0078, B:19:0x00ab, B:23:0x00b5, B:25:0x00bb, B:27:0x00c6, B:29:0x00cc, B:30:0x00e8, B:33:0x00ec, B:35:0x00f1, B:36:0x00fc, B:38:0x010c, B:39:0x011e, B:41:0x0120, B:42:0x0127, B:44:0x012d, B:45:0x0134, B:46:0x013a, B:47:0x013e), top: B:52:0x0009, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0134 A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0009, B:6:0x001a, B:8:0x002d, B:9:0x003b, B:10:0x003e, B:13:0x0042, B:15:0x0072, B:17:0x0078, B:19:0x00ab, B:23:0x00b5, B:25:0x00bb, B:27:0x00c6, B:29:0x00cc, B:30:0x00e8, B:33:0x00ec, B:35:0x00f1, B:36:0x00fc, B:38:0x010c, B:39:0x011e, B:41:0x0120, B:42:0x0127, B:44:0x012d, B:45:0x0134, B:46:0x013a, B:47:0x013e), top: B:52:0x0009, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.String b(boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.a.a.b(boolean, boolean):java.lang.String");
    }
}
