package com.xiaomi.ai.android.core;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.android.capability.AuthCapability;
import com.xiaomi.ai.android.capability.ConnectionCapability;
import com.xiaomi.ai.android.capability.ErrorCapability;
import com.xiaomi.ai.android.capability.StorageCapability;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.b;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.track.TrackData;
import com.xiaomi.mipush.sdk.Constants;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes2.dex */
public class a extends b {
    private d a;
    private boolean c;
    private C0154a d;
    private int b = 0;
    private boolean e = false;
    private Set<String> f = new HashSet();

    /* renamed from: com.xiaomi.ai.android.core.a$a */
    /* loaded from: classes2.dex */
    public class C0154a extends Thread {
        private boolean b;
        private boolean c;

        private C0154a() {
            a.this = r1;
            this.b = false;
            this.c = false;
        }

        private int a(int i) {
            if (i <= 0) {
                return 0;
            }
            if (i > 10) {
                i = 10;
            }
            int i2 = a.this.a.b().getInt(AivsConfig.Connection.MAX_RECONNECT_INTERVAL);
            if (i2 < 1800) {
                Logger.a("ChannelManager", "MAX_RECONNECT_INTERVAL must be larger than 1800");
                i2 = 1800;
            }
            int pow = ((int) Math.pow(2.0d, i)) - 1;
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(UUID.randomUUID().toString().getBytes());
            int nextInt = secureRandom.nextInt((((int) Math.pow(2.0d, i + 1.0f)) - 1) - pow) + pow;
            return nextInt > i2 ? i2 : nextInt;
        }

        public boolean b() {
            boolean z;
            synchronized (this) {
                z = this.b;
            }
            return z;
        }

        public synchronized void a(boolean z) {
            this.c = z;
        }

        public synchronized boolean a() {
            return this.c;
        }

        /* JADX WARN: Removed duplicated region for block: B:113:0x0154 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0159 A[Catch: Exception -> 0x0196, all -> 0x0194, TryCatch #7 {Exception -> 0x0196, blocks: (B:15:0x0068, B:17:0x0070, B:21:0x0075, B:23:0x0097, B:24:0x009f, B:25:0x00a6, B:29:0x00ab, B:31:0x00bb, B:34:0x00cb, B:35:0x00d0, B:38:0x00ef, B:39:0x00f4, B:41:0x00fa, B:44:0x0126, B:45:0x012a, B:46:0x0143, B:48:0x014c, B:51:0x0159, B:54:0x0171, B:60:0x0179, B:63:0x017c, B:66:0x017f), top: B:97:0x0068, outer: #1 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 463
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.android.core.a.C0154a.run():void");
        }
    }

    public a(d dVar) {
        this.a = dVar;
        this.f.add(AIApiConstants.SpeechSynthesizer.Speak);
        this.f.add(AIApiConstants.SpeechSynthesizer.FinishSpeakStream);
    }

    static /* synthetic */ int c(a aVar) {
        int i = aVar.b;
        aVar.b = i + 1;
        return i;
    }

    @Override // com.xiaomi.ai.core.b
    public String a(com.xiaomi.ai.core.a aVar, String str) {
        StorageCapability storageCapability = (StorageCapability) this.a.a(StorageCapability.class);
        if (storageCapability == null) {
            return null;
        }
        String readKeyValue = storageCapability.readKeyValue(str);
        Logger.a("ChannelManager", "onRead: key=" + str + ", value=" + readKeyValue);
        return readKeyValue;
    }

    @Override // com.xiaomi.ai.core.b
    public String a(com.xiaomi.ai.core.a aVar, boolean z) {
        Logger.b("ChannelManager", "onGetToken");
        AuthCapability authCapability = (AuthCapability) this.a.a(AuthCapability.class);
        if (authCapability != null) {
            return authCapability.onGetToken(this.a.m(), z);
        }
        Logger.d("ChannelManager", "onGetToken: AuthCapability was not registered");
        return null;
    }

    public void a() {
        Logger.b("ChannelManager", "release");
        synchronized (this) {
            this.e = true;
            this.c = false;
            if (this.d != null) {
                this.d.interrupt();
                this.d = null;
            }
        }
    }

    @Override // com.xiaomi.ai.core.b
    public void a(long j, long j2) {
        this.a.k().a(this.a.e().c(), j2);
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar) {
        Logger.b("ChannelManager", "onAuthExpired");
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar, int i) {
        this.a.a(i, true);
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar, Instruction instruction) {
        this.b = 0;
        this.a.k().c(instruction);
        if (this.f.contains(instruction.getFullName())) {
            this.a.f().b(instruction);
        }
        this.a.d().obtainMessage(1, instruction).sendToTarget();
        this.a.j().a(instruction);
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar, AivsError aivsError) {
        Logger.d("ChannelManager", "onError:" + aivsError.getErrorCode() + Constants.ACCEPT_TIME_SEPARATOR_SP + aivsError.getErrorMessage());
        this.a.k().a(false);
        C0154a aVar2 = this.d;
        if (aVar2 == null || !aVar2.a()) {
            ErrorCapability errorCapability = (ErrorCapability) this.a.a(ErrorCapability.class);
            if (errorCapability != null) {
                Logger.b("ChannelManager", "capability.onError");
                errorCapability.onError(aivsError);
            }
            if (this.a.c().b() > 0) {
                this.a.c().a();
                return;
            }
            return;
        }
        Logger.b("ChannelManager", " not callback error on backoff reconnect ");
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar, TrackData trackData) {
        if (trackData != null) {
            Logger.a("ChannelManager", "onSetNetworkInfo");
            String c = NetworkUtils.c(this.a.a());
            trackData.set("network", c);
            if ("WIFI".equals(c)) {
                trackData.set("network.wifi.signal.level", NetworkUtils.d(this.a.a()));
            } else {
                trackData.set("network.data.carrier.type", NetworkUtils.e(this.a.a()));
            }
        }
    }

    @Override // com.xiaomi.ai.core.b
    public void a(com.xiaomi.ai.core.a aVar, byte[] bArr) {
        this.a.k().d();
        this.a.f().a(bArr);
        this.a.j().a();
        Logger.a("ChannelManager", "onBinaryMessage length=" + bArr.length);
    }

    public void a(boolean z) {
        String str;
        String str2;
        Logger.b("ChannelManager", " asyncConnect : is reconnect = " + z);
        synchronized (this) {
            com.xiaomi.ai.core.a g = this.a.g();
            if (!this.e && g != null) {
                if (g.isConnected()) {
                    Logger.b("ChannelManager", "reconnect: already connected");
                    return;
                }
                if (this.d == null) {
                    this.b = 0;
                    this.c = true;
                    this.d = new C0154a();
                    this.d.a(z);
                    this.d.start();
                    str = "ChannelManager";
                    str2 = "reconnect: start new reconnect thread";
                } else if (this.d.b()) {
                    this.b = 0;
                    if (!z) {
                        this.d.a(false);
                    }
                    this.d.interrupt();
                    str = "ChannelManager";
                    str2 = "reconnect: interrupt reconnect thread";
                } else {
                    str = "ChannelManager";
                    str2 = "reconnect: is already connecting";
                }
                Logger.b(str, str2);
                return;
            }
            Logger.c("ChannelManager", "reconnect: already released");
        }
    }

    @Override // com.xiaomi.ai.core.b
    public boolean a(com.xiaomi.ai.core.a aVar, String str, String str2) {
        Logger.a("ChannelManager", "onWrite: key=" + str + ", value=" + str2);
        StorageCapability storageCapability = (StorageCapability) this.a.a(StorageCapability.class);
        return storageCapability != null && storageCapability.writeKeyValue(str, str2);
    }

    @Override // com.xiaomi.ai.core.b
    public String b() {
        ConnectionCapability connectionCapability = (ConnectionCapability) this.a.a(ConnectionCapability.class);
        if (connectionCapability != null) {
            return connectionCapability.onGetSSID();
        }
        return null;
    }

    @Override // com.xiaomi.ai.core.b
    public void b(com.xiaomi.ai.core.a aVar) {
        Logger.b("ChannelManager", "onAuthRefreshed");
    }

    @Override // com.xiaomi.ai.core.b
    public void b(com.xiaomi.ai.core.a aVar, String str) {
        StorageCapability storageCapability = (StorageCapability) this.a.a(StorageCapability.class);
        if (storageCapability != null) {
            storageCapability.removeKeyValue(str);
        }
        Logger.a("ChannelManager", "onRemove: key=" + str);
    }

    public void b(boolean z) {
        Logger.a("ChannelManager", "onNetworkAvailable");
        if (this.a.b().getInt(AivsConfig.Connection.KEEP_ALIVE_TYPE) == 0 || !z) {
            a(z);
        }
    }

    @Override // com.xiaomi.ai.core.b
    public String c(com.xiaomi.ai.core.a aVar) {
        Logger.b("ChannelManager", "onGetOAuthCode");
        AuthCapability authCapability = (AuthCapability) this.a.a(AuthCapability.class);
        if (authCapability != null) {
            return authCapability.onGetOAuthCode();
        }
        Logger.d("ChannelManager", "onGetOAuthCode: AuthCapability was not registered");
        return null;
    }

    @Override // com.xiaomi.ai.core.b
    public boolean c() {
        return NetworkUtils.ipv6Available();
    }

    @Override // com.xiaomi.ai.core.b
    public String d() {
        return NetworkUtils.c(this.a.a());
    }

    @Override // com.xiaomi.ai.core.b
    public String d(com.xiaomi.ai.core.a aVar) {
        String str;
        String str2;
        Logger.b("ChannelManager", "onGetAuthorizationToken");
        AuthCapability authCapability = (AuthCapability) this.a.a(AuthCapability.class);
        if (authCapability != null) {
            AuthCapability.AuthorizationTokens onGetAuthorizationTokens = authCapability.onGetAuthorizationTokens();
            if (onGetAuthorizationTokens == null) {
                aVar.updateTrack("sdk.connect.error.msg", "onGetAuthorizationToken error : authorizationTokens is null");
                str = "ChannelManager";
                str2 = "authorizationTokens is null";
            } else {
                aVar.getListener().a(aVar, XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2, onGetAuthorizationTokens.accessToken);
                aVar.getListener().a(aVar, "refresh_token", onGetAuthorizationTokens.refreshToken);
                aVar.getListener().a(aVar, "expire_at", String.format("%d", Long.valueOf((System.currentTimeMillis() / 1000) + onGetAuthorizationTokens.expireIn)));
                aVar.getListener().a(aVar, "refresh_at", String.format("%d", Long.valueOf(System.currentTimeMillis() / 1000)));
                String str3 = onGetAuthorizationTokens.accessToken;
                if (f.a(str3)) {
                    aVar.updateTrack("sdk.connect.error.msg", "onGetAuthorizationToken error : emptyToken");
                }
                return str3;
            }
        } else {
            aVar.updateTrack("sdk.connect.error.msg", "onGetAuthorizationToken error : capability is null");
            str = "ChannelManager";
            str2 = "onGetAuthorizationToken: AuthCapability was not registered";
        }
        Logger.d(str, str2);
        return null;
    }

    @Override // com.xiaomi.ai.core.b
    public void e(com.xiaomi.ai.core.a aVar) {
        Logger.b("ChannelManager", "onConnected");
        this.a.d().removeCallbacksAndMessages(null);
        this.a.f().b();
        this.a.c().c();
        if (this.a.b().getInt(AivsConfig.Connection.KEEP_ALIVE_TYPE) != 0) {
            this.a.j().d();
        }
        ConnectionCapability connectionCapability = (ConnectionCapability) this.a.a(ConnectionCapability.class);
        if (connectionCapability != null) {
            connectionCapability.onConnected();
        }
    }

    @Override // com.xiaomi.ai.core.b
    public void f(com.xiaomi.ai.core.a aVar) {
        Logger.d("ChannelManager", "onDisconnected");
        boolean z = false;
        this.a.k().a(false);
        synchronized (this) {
            if (!this.e) {
                if (this.d == null) {
                    if (this.a.b().getInt(AivsConfig.Connection.KEEP_ALIVE_TYPE) == 0) {
                        this.c = true;
                        this.d = new C0154a();
                        this.d.a(true);
                        this.d.start();
                    } else {
                        Logger.c("ChannelManager", "onDisconnected: will not reconnect");
                    }
                    z = true;
                } else {
                    Logger.c("ChannelManager", "onDisconnected: is already reconnecting or do not need reconnect");
                }
                if (z) {
                    ConnectionCapability connectionCapability = (ConnectionCapability) this.a.a(ConnectionCapability.class);
                    if (connectionCapability != null) {
                        connectionCapability.onDisconnected();
                    }
                    ErrorCapability errorCapability = (ErrorCapability) this.a.a(ErrorCapability.class);
                    if (errorCapability != null) {
                        errorCapability.onError(new AivsError(StdStatuses.CONNECTION_INTERRUPT, "Network disconnected"));
                    }
                }
                this.a.c().a();
                this.a.j().e();
            }
        }
    }

    @Override // com.xiaomi.ai.core.b
    public int g(com.xiaomi.ai.core.a aVar) {
        int i = 0;
        try {
            String str = "1.35.1";
            if (str.contains("SNAPSHOT")) {
                str = str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER)[0];
            }
            String[] split = str.split("\\.");
            i = Integer.parseInt(split[2]) + (Integer.parseInt(split[0]) * 1000000) + (Integer.parseInt(split[1]) * 1000);
            Logger.a("ChannelManager", "onGetSDKVersion : " + i);
            return i;
        } catch (Exception e) {
            Logger.d("ChannelManager", Logger.throwableToString(e));
            return i;
        }
    }
}
