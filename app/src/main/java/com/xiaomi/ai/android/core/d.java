package com.xiaomi.ai.android.core;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.ai.android.capability.AuthCapability;
import com.xiaomi.ai.android.capability.Capability;
import com.xiaomi.ai.android.capability.ConnectionCapability;
import com.xiaomi.ai.android.capability.ErrorCapability;
import com.xiaomi.ai.android.capability.InstructionCapability;
import com.xiaomi.ai.android.capability.SpeechSynthesizerCapability;
import com.xiaomi.ai.android.capability.StorageCapability;
import com.xiaomi.ai.android.capability.TrackCapability;
import com.xiaomi.ai.android.impl.b;
import com.xiaomi.ai.android.impl.c;
import com.xiaomi.ai.android.track.f;
import com.xiaomi.ai.android.utils.DeviceUtils;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.XMDChannel;
import com.xiaomi.ai.core.a;
import com.xiaomi.ai.core.e;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.log.LoggerHooker;
import com.xiaomi.common.Optional;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class d extends Engine {
    private AivsConfig a;
    private f b;
    private e c;
    private a d;
    private a e;
    private Map<Class<?>, Capability> f;
    private c g;
    private j h;
    private HandlerThread i;
    private HandlerThread j;
    private Context k;
    private int l;
    private g m;
    private Settings.ClientInfo n;
    private i o;
    private b p;
    private NetworkUtils.a q;
    private volatile boolean r = false;
    private h s;

    public d(Context context, AivsConfig aivsConfig, Settings.ClientInfo clientInfo, int i) {
        this.a = aivsConfig;
        this.k = context.getApplicationContext();
        this.l = i;
        a(clientInfo);
        q();
    }

    private void a(Settings.ClientInfo clientInfo) {
        Logger.setLogHooker(new com.xiaomi.ai.android.b.a());
        this.n = b(clientInfo);
        this.f = new HashMap();
        registerCapability(new b(this, this.l, this.a.getInt(AivsConfig.ENV)));
        registerCapability(new c(this));
        if (this.a.getBoolean(AivsConfig.LimitedDiskCache.ENABLE)) {
            f.a().a(this.a.getInt(AivsConfig.LimitedDiskCache.MAX_DISK_SAVE_TIMES));
        }
        this.s = new h();
        this.c = new e(this);
        this.b = new f(this);
        this.d = new a(this);
        this.i = new HandlerThread("DownloadThread");
        this.i.start();
        this.g = new c(this, this.i.getLooper());
        this.j = new HandlerThread("UploadThread");
        this.j.start();
        this.h = new j(this, this.j.getLooper());
        p();
        this.o = new i(this);
        this.m = new g(this);
        this.p = new b(this);
        this.q = new NetworkUtils.a(this);
        this.k.registerReceiver(this.q, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private Settings.ClientInfo b(Settings.ClientInfo clientInfo) {
        String str;
        StringBuilder sb;
        String str2;
        if (this.a == null) {
            Logger.d("EngineImpl", "rebuildClientInfo: mConfig is null");
            return null;
        }
        if (clientInfo == null) {
            clientInfo = new Settings.ClientInfo();
        }
        if (!clientInfo.getTimeZone().isPresent()) {
            clientInfo.setTimeZone(TimeZone.getDefault().getID());
        }
        if (!clientInfo.getNetwork().isPresent()) {
            clientInfo.setNetwork(NetworkUtils.b(this.k));
        }
        if (!this.a.getBoolean(AivsConfig.Auth.SUPPORT_MULTIPLY_CLIENT_ID)) {
            clientInfo.setDeviceId(DeviceUtils.getDeviceId(this.k));
            str2 = "EngineImpl";
            sb = new StringBuilder();
            str = "device id set by sdk ";
        } else if (clientInfo.getDeviceId().isPresent()) {
            str2 = "EngineImpl";
            sb = new StringBuilder();
            str = "device id set by client ";
        } else {
            Logger.d("EngineImpl", "error: device id not set!!!");
            throw new IllegalArgumentException("device id not set!!!");
        }
        sb.append(str);
        sb.append(clientInfo.getDeviceId().get());
        Logger.c(str2, sb.toString());
        Logger.b("EngineImpl", "deviceId:" + clientInfo.getDeviceId().get());
        return clientInfo;
    }

    private void o() {
        if (!this.h.hasMessages(3)) {
            Logger.a("EngineImpl", "start wait net, time out " + this.a.getInt(AivsConfig.Connection.NET_AVAILABLE_WAIT_TIME));
            this.h.sendMessageDelayed(this.h.obtainMessage(3), (long) this.a.getInt(AivsConfig.Connection.NET_AVAILABLE_WAIT_TIME));
        }
    }

    private void p() {
        a(i(), false);
    }

    private void q() {
        String str = StringUtils.SPACE;
        Settings.ClientInfo clientInfo = this.n;
        if (clientInfo != null && clientInfo.getEngineId().isPresent()) {
            str = this.n.getEngineId().get();
        }
        Logger.c("EngineImpl", String.format(Locale.US, "versionName=%s, versionCode=%d, engineId=%s,GIT_COMMIT=%s, spec version=%s", "1.35.1", 20220317, str, "b17b530", "0.0.411"));
    }

    public Context a() {
        return this.k;
    }

    public Capability a(Class<?> cls) {
        return this.f.get(cls);
    }

    public void a(int i, boolean z) {
        a aVar;
        Logger.b("EngineImpl", "changeChannel: channelType=" + i + ", isNeedTrackInfo=" + z);
        a aVar2 = this.e;
        if (aVar2 != null) {
            aVar2.stop();
        }
        if (z) {
            this.o.a(false);
        }
        int i2 = this.l;
        if (i2 == 5 || i2 == 6) {
            com.xiaomi.ai.android.a.a.a aVar3 = new com.xiaomi.ai.android.a.a.a(this, this.l);
            aVar = i == 0 ? new e(this.a, this.n, aVar3, this.d) : new XMDChannel(this.a, this.n, aVar3, this.d);
            aVar3.a(aVar);
        } else {
            aVar = i == 0 ? new e(this.a, this.n, i2, this.d) : new XMDChannel(this.a, this.n, i2, this.d);
        }
        this.e = aVar;
        if (z && this.a.getBoolean(AivsConfig.Track.ENABLE)) {
            this.o.b();
            this.e.setTrackInfo(this.o.f());
        }
    }

    public void a(AivsError aivsError) {
        ErrorCapability errorCapability = (ErrorCapability) a(ErrorCapability.class);
        if (errorCapability != null) {
            errorCapability.onError(aivsError);
        }
        Logger.d("EngineImpl", "Error:" + aivsError.getEventId() + Constants.COLON_SEPARATOR + aivsError.getErrorMessage());
    }

    public boolean a(ObjectNode objectNode) {
        String str;
        String str2;
        if (this.r) {
            str = "EngineImpl";
            str2 = "postTrackData:mIsReleased=" + this.r;
        } else if (!this.a.getBoolean(AivsConfig.Track.ENABLE)) {
            str = "EngineImpl";
            str2 = "postTrackData: AivsConfig.Track.ENABLE is false";
        } else {
            Logger.a("EngineImpl", "postTrackData:" + objectNode.toString());
            this.o.a(objectNode);
            return true;
        }
        Logger.d(str, str2);
        return false;
    }

    public AivsConfig b() {
        return this.a;
    }

    public j c() {
        return this.h;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void clearUserData() {
        Logger.d("EngineImpl", "clearUserData");
        f.a().a(this.k, "aivs_user_data.xml", this.a.getBoolean(AivsConfig.LimitedDiskCache.ENABLE));
    }

    public c d() {
        return this.g;
    }

    public e e() {
        return this.c;
    }

    public f f() {
        return this.b;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void finishTrace() {
        ObjectNode b = this.s.b();
        if (b != null) {
            a(b);
        } else {
            Logger.b("EngineImpl", "node is null");
        }
    }

    public a g() {
        return this.e;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public String getAccessToken() {
        StorageCapability storageCapability = (StorageCapability) a(StorageCapability.class);
        if (storageCapability != null) {
            return storageCapability.readKeyValue(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2);
        }
        return null;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public String getAuthorization() {
        String str;
        String str2;
        Logger.b("EngineImpl", "getAuthorization ");
        a aVar = this.e;
        if (aVar == null || aVar.getAuthProvider() == null) {
            str = "EngineImpl";
            str2 = "getAuthorization: AuthProvider not set";
        } else {
            String a = this.e.getAuthProvider().a(false, false, (Map<String, String>) null);
            if (!TextUtils.isEmpty(a)) {
                return a;
            }
            str = "EngineImpl";
            str2 = "getAuthorization: failed to getAuthHeader";
        }
        Logger.d(str, str2);
        return null;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public long getExpireAt() {
        StorageCapability storageCapability = (StorageCapability) a(StorageCapability.class);
        if (storageCapability == null) {
            return -1L;
        }
        String readKeyValue = storageCapability.readKeyValue("expire_at");
        if (TextUtils.isEmpty(readKeyValue)) {
            return -1L;
        }
        try {
            return Long.parseLong(readKeyValue) * 1000;
        } catch (Exception e) {
            Logger.d("EngineImpl", Logger.throwableToString(e));
            return -1L;
        }
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public int getSDKVersion() {
        return this.d.g(this.e);
    }

    public a h() {
        return this.d;
    }

    public int i() {
        long j;
        int i = this.a.getInt(AivsConfig.Connection.CHANNEL_TYPE, -1);
        if (i == -1) {
            String a = com.xiaomi.ai.android.utils.d.a(this.k, "aivs_cloud_control", "link_mode");
            if (TextUtils.isEmpty(a)) {
                a = this.d.a(this.e, "link_mode");
            }
            Logger.b("EngineImpl", "use cloud control link mode " + a);
            if (TextUtils.isEmpty(a)) {
                a = "ws-wss";
                Logger.b("EngineImpl", "use default link mode " + a);
            }
            i = ("ws-wss".equals(a) || "wss".equals(a) || !a.equals("xmd")) ? 0 : 1;
        }
        if (i == 0) {
            return 0;
        }
        String a2 = this.d.a(this.e, "xmd_ws_expire_at");
        if (TextUtils.isEmpty(a2)) {
            return 1;
        }
        try {
            j = Long.parseLong(a2);
        } catch (NumberFormatException e) {
            Logger.d("EngineImpl", Log.getStackTraceString(e));
            j = 0;
        }
        if (j - (System.currentTimeMillis() / 1000) >= 0) {
            Logger.c("EngineImpl", "createChannel: use websocket channel in xmd mode");
            this.a.putBoolean(AivsConfig.Connection.ENABLE_LITE_CRYPT, false);
            return 0;
        }
        Logger.b("EngineImpl", "createChannel: clear wss expire time in xmd mode");
        this.d.b(this.e, "xmd_ws_expire_at");
        this.a.putBoolean(AivsConfig.Connection.ENABLE_LITE_CRYPT, true);
        return i;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void interrupt() {
        if (this.r) {
            Logger.d("EngineImpl", "interrupt error,engine has been released");
            return;
        }
        Logger.b("EngineImpl", "interrupt");
        this.h.a();
        this.g.removeCallbacksAndMessages(null);
        this.c.a();
        this.b.b();
        this.m.b();
    }

    public g j() {
        return this.m;
    }

    public i k() {
        return this.o;
    }

    public String l() {
        if (this.l != 1 || !this.a.getBoolean(AivsConfig.Auth.OAuth.ENABLE_UPLOAD_MIOT_DID)) {
            return "";
        }
        Optional<String> deviceId = this.n.getDeviceId();
        Optional<String> miotDid = this.n.getMiotDid();
        if (!miotDid.isPresent()) {
            return "";
        }
        String a = com.xiaomi.ai.b.b.a(deviceId.get() + "_" + miotDid.get());
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("_");
        String sb2 = sb.toString();
        Logger.a("EngineImpl", "getAuthPrefix upload miot did. prefix is " + sb2);
        return sb2;
    }

    public int m() {
        return this.l;
    }

    public Settings.ClientInfo n() {
        return this.n;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean postData(byte[] bArr, int i, int i2, boolean z) {
        Logger.a("EngineImpl", "postData: offset=" + i + ", length=" + i2 + ", eof=" + z);
        if (i2 > 65536) {
            Logger.d("EngineImpl", "postData: Max frame length has been exceeded");
            return false;
        } else if (this.e == null || this.h == null) {
            Logger.d("EngineImpl", "postData: already released or disconnected");
            a(new AivsError(StdStatuses.CONNECT_FAILED, "postData: already released or disconnected"));
            return false;
        } else {
            Bundle bundle = new Bundle();
            if (bArr != null && i >= 0 && i2 > 0) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                bundle.putByteArray("data", bArr2);
            }
            bundle.putBoolean(com.umeng.analytics.pro.c.aB, z);
            bundle.putBoolean("raw", false);
            Message obtainMessage = this.h.obtainMessage(1);
            obtainMessage.setData(bundle);
            this.o.a(this.c.c(), z);
            this.h.a(obtainMessage);
            return true;
        }
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean postData(byte[] bArr, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("postData: length=");
        sb.append(bArr != null ? bArr.length : 0);
        sb.append(", eof=");
        sb.append(z);
        Logger.a("EngineImpl", sb.toString());
        if (bArr != null && bArr.length > 65536) {
            Logger.d("EngineImpl", "postData: Max frame length has been exceeded");
            return false;
        } else if (this.e == null || this.h == null) {
            Logger.d("EngineImpl", "postData: already released or disconnected");
            a(new AivsError(StdStatuses.CONNECT_FAILED, "postData: already released or disconnected"));
            return false;
        } else {
            Bundle bundle = new Bundle();
            bundle.putByteArray("data", bArr);
            bundle.putBoolean(com.umeng.analytics.pro.c.aB, z);
            bundle.putBoolean("raw", false);
            Message obtainMessage = this.h.obtainMessage(1);
            obtainMessage.setData(bundle);
            this.o.a(this.c.c(), z);
            this.h.a(obtainMessage);
            return true;
        }
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean postEvent(Event event) {
        if (event == null) {
            Logger.d("EngineImpl", "postEvent:event is null");
            return false;
        }
        if (Logger.getLogLevel() == 3) {
            try {
                String jsonString = event.toJsonString();
                Logger.a("EngineImpl", "postEvent: event " + jsonString);
            } catch (JsonProcessingException e) {
                a(new AivsError(StdStatuses.MISSING_PARAMETER, "required field not set", event.getId()));
                Logger.d("EngineImpl", Log.getStackTraceString(e));
                Logger.d("EngineImpl", "postEvent: event failed, required field not set");
                return false;
            }
        } else {
            Logger.b("EngineImpl", "postEvent: event: " + event.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + event.getId());
        }
        if (this.e == null || this.h == null) {
            Logger.d("EngineImpl", "postEvent: already released or disconnected");
            a(new AivsError(StdStatuses.CONNECT_FAILED, "postEvent: already released or disconnected", event.getId()));
            return false;
        }
        this.o.a(event);
        this.c.a(event);
        j jVar = this.h;
        jVar.a(jVar.obtainMessage(0, event));
        if (NetworkUtils.a(this.k)) {
            a aVar = this.e;
            if (aVar == null || aVar.isConnected()) {
                return true;
            }
            q();
            this.d.a(false);
            return true;
        }
        o();
        return true;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean postRawData(byte[] bArr, int i, int i2) {
        Logger.a("EngineImpl", "postRawData: offset=" + i + ", length=" + i2);
        if (i2 > 65536) {
            Logger.d("EngineImpl", "postRawData: Max frame length has been exceeded");
            return false;
        } else if (this.e == null || this.h == null) {
            Logger.d("EngineImpl", "postRawData: already released or disconnected");
            a(new AivsError(StdStatuses.CONNECT_FAILED, "postData: already released or disconnected"));
            return false;
        } else {
            Bundle bundle = new Bundle();
            if (bArr != null && i >= 0 && i2 > 0) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                bundle.putByteArray("data", bArr2);
            }
            bundle.putBoolean("raw", true);
            Message obtainMessage = this.h.obtainMessage(1);
            obtainMessage.setData(bundle);
            this.o.a(this.c.c(), false);
            this.h.a(obtainMessage);
            return true;
        }
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean registerCapability(Capability capability) {
        Map<Class<?>, Capability> map;
        Class<?> cls;
        if (capability instanceof AuthCapability) {
            map = this.f;
            cls = AuthCapability.class;
        } else if (capability instanceof ConnectionCapability) {
            map = this.f;
            cls = ConnectionCapability.class;
        } else if (capability instanceof ErrorCapability) {
            map = this.f;
            cls = ErrorCapability.class;
        } else if (capability instanceof InstructionCapability) {
            map = this.f;
            cls = InstructionCapability.class;
        } else if (capability instanceof SpeechSynthesizerCapability) {
            map = this.f;
            cls = SpeechSynthesizerCapability.class;
        } else if (capability instanceof StorageCapability) {
            map = this.f;
            cls = StorageCapability.class;
        } else if (capability instanceof TrackCapability) {
            map = this.f;
            cls = TrackCapability.class;
        } else {
            Logger.d("EngineImpl", "registerCapability: unknown Capability " + capability);
            return false;
        }
        map.put(cls, capability);
        return true;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void release() {
        Logger.b("EngineImpl", "release start");
        this.r = true;
        this.p.b();
        NetworkUtils.a aVar = this.q;
        if (aVar != null) {
            this.k.unregisterReceiver(aVar);
            this.q = null;
        }
        this.h.a();
        this.j.quit();
        try {
            this.j.join();
        } catch (InterruptedException e) {
            Logger.d("EngineImpl", Logger.throwableToString(e));
        }
        a aVar2 = this.e;
        if (aVar2 != null) {
            aVar2.stop();
            this.e = null;
        }
        this.g.removeCallbacksAndMessages(null);
        this.i.quit();
        try {
            this.i.join();
        } catch (InterruptedException e2) {
            Logger.d("EngineImpl", Logger.throwableToString(e2));
        }
        this.c.b();
        this.d.a();
        this.b.a();
        this.m.c();
        this.o.c();
        Logger.b("EngineImpl", "release end");
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public String requestAuthorization() {
        String str;
        String str2;
        Logger.b("EngineImpl", "requestAuthorization: start");
        if (this.r) {
            str = "EngineImpl";
            str2 = "requestAuthorization error,engine has been released";
        } else {
            a aVar = this.e;
            if (aVar == null || aVar.getAuthProvider() == null) {
                str = "EngineImpl";
                str2 = "forceRefreshAuthorization: AuthProvider not set";
            } else {
                String a = this.e.getAuthProvider().a(true, false, (Map<String, String>) null);
                if (TextUtils.isEmpty(a)) {
                    str = "EngineImpl";
                    str2 = "forceRefreshAuthorization: failed to getAuthHeader";
                } else {
                    Logger.b("EngineImpl", "requestAuthorization: end");
                    return a;
                }
            }
        }
        Logger.d(str, str2);
        return null;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void restart() {
        if (this.r) {
            Logger.d("EngineImpl", "restart error,engine has been released");
            return;
        }
        Logger.b("EngineImpl", "restart");
        this.h.a();
        this.g.removeCallbacksAndMessages(null);
        this.c.b();
        this.b.b();
        this.m.c();
        a aVar = this.e;
        if (aVar != null) {
            aVar.stop();
            this.d.a(true);
        }
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public boolean setAuthorizationTokens(String str, String str2, long j) {
        StorageCapability storageCapability = (StorageCapability) a(StorageCapability.class);
        if (storageCapability == null) {
            return false;
        }
        storageCapability.writeKeyValue(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2, str);
        storageCapability.writeKeyValue("refresh_token", str2);
        storageCapability.writeKeyValue("expire_at", String.format("%d", Long.valueOf((System.currentTimeMillis() / 1000) + j)));
        return true;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void setLoggerHooker(LoggerHooker loggerHooker) {
        Logger.setLogHooker(loggerHooker);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public synchronized boolean start() {
        Logger.b("EngineImpl", "start");
        if (this.r) {
            Logger.d("EngineImpl", "start error ,engine has been released");
            return false;
        }
        this.p.a();
        this.c.b();
        this.h.a();
        this.g.removeCallbacksAndMessages(null);
        this.e.stop();
        int i = i();
        int type = this.e.getType();
        Logger.b("EngineImpl", "currentChannelType =" + type + ", nextChannelType=" + i);
        if (i != type) {
            a(i, true);
        }
        if (this.a.getBoolean(AivsConfig.Track.ENABLE)) {
            this.e.setTrackInfo(this.o.f());
        }
        this.o.a(true);
        if (NetworkUtils.a(this.k)) {
            this.d.a(false);
        } else {
            o();
        }
        return true;
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void startTrace() {
        this.s.a();
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void traceRequestId(String str) {
        this.s.b(str);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void traceResult(String str, int i) {
        this.s.b(str, i);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void traceResult(String str, String str2) {
        this.s.a(str, str2);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void traceTimeStamps(String str) {
        this.s.a(str);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void traceTimeStamps(String str, long j) {
        this.s.a(str, j);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void uploadLogError(String str, String str2) {
        Logger.c(str, str2, true, false);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void uploadLogInfo(String str, String str2) {
        Logger.a(str, str2, true, false);
    }

    @Override // com.xiaomi.ai.android.core.Engine
    public void uploadLogWarn(String str, String str2) {
        Logger.b(str, str2, true, false);
    }
}
