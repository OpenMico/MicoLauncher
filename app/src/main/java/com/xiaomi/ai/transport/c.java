package com.xiaomi.ai.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.b.a;
import com.xiaomi.ai.b.b;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.e;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.track.TrackData;
import com.xiaomi.common.Optional;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* loaded from: classes3.dex */
public class c extends WebSocketListener {
    private final e a;
    private final AivsConfig b;
    private WebSocket c;
    private LiteCryptInterceptor d;
    private volatile boolean e;
    private String f;
    private int g;
    private int h;
    private AivsError i;
    private ObjectNode j;
    private final Object k = new Object();
    private String l;

    public c(e eVar) {
        this.a = eVar;
        this.b = this.a.getAivsConfig();
    }

    private void a(Instruction<?> instruction) {
        if (this.c != null && AIApiConstants.Settings.ConnectionChallenge.equals(instruction.getHeader().getFullName())) {
            this.l = instruction.getId();
            Logger.b("LiteCryptWsClient", "handShake: challenge id:" + this.l);
            this.a.updateTrackTimestamp("sdk.connect.ws.recv.challenge", System.currentTimeMillis());
            Settings.ConnectionChallenge connectionChallenge = (Settings.ConnectionChallenge) instruction.getPayload();
            String challenge = connectionChallenge.getChallenge();
            Optional<String> aesToken = connectionChallenge.getAesToken();
            Optional<Integer> tokenExpiresIn = connectionChallenge.getTokenExpiresIn();
            if (aesToken.isPresent() && tokenExpiresIn.isPresent()) {
                this.d.updateAesToken(aesToken.get(), (tokenExpiresIn.get().intValue() * 1000) + System.currentTimeMillis());
            }
            Settings.ConnectionChallengeAck connectionChallengeAck = new Settings.ConnectionChallengeAck();
            connectionChallengeAck.setChallengeMd5(b.a(challenge));
            Event buildEvent = APIUtils.buildEvent(connectionChallengeAck);
            String event = buildEvent.toString();
            this.a.updateTrackTimestamp("sdk.connect.ws.send.challengeack", System.currentTimeMillis());
            Logger.b("LiteCryptWsClient", "handShake:send ackString, ackEvent:" + buildEvent.getId());
            a(event);
            f();
            this.a.updateTrackTimestamp("sdk.connect.finish", System.currentTimeMillis());
            this.e = true;
            this.a.getListener().e(this.a);
            synchronized (this) {
                notify();
            }
        }
    }

    private boolean a(Response response, String str) {
        Logger.a("LiteCryptWsClient", "shouldSwitchToWss: errorMsg=" + str);
        if (response != null && response.code() == 426) {
            return true;
        }
        if (f.a(str)) {
            return false;
        }
        if (!str.contains("Too many follow-up requests") && !str.contains("Expected 'Connection' header value 'Upgrade' but")) {
            return str.contains("Control frames must be final.");
        }
        return true;
    }

    private void f() {
        String str;
        Event<Settings.GlobalConfig> initEvent = this.a.getInitEvent();
        try {
            str = initEvent.toJsonString();
        } catch (JsonProcessingException e) {
            Logger.d("LiteCryptWsClient", Logger.throwableToString(e));
            this.a.getListener().a(this.a, new AivsError(StdStatuses.MISSING_PARAMETER, "required field not set"));
            str = null;
        }
        if (str == null) {
            Logger.d("LiteCryptWsClient", "eventString: GlobalConfig Event is null");
            return;
        }
        Logger.b("LiteCryptWsClient", "sendInitEvent:" + initEvent.getId() + ", device id:" + this.a.getClientInfo().getDeviceId().get());
        a(str);
    }

    public void a() {
        this.e = false;
        WebSocket webSocket = this.c;
        if (webSocket != null) {
            webSocket.cancel();
            this.c = null;
        }
    }

    public boolean a(Event<?> event) {
        Logger.b("LiteCryptWsClient", "send: " + event.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + event.getId());
        try {
            return a(event.toJsonString());
        } catch (JsonProcessingException e) {
            Logger.d("LiteCryptWsClient", "send: event failed, required field not set");
            Logger.d("LiteCryptWsClient", Logger.throwableToString(e));
            this.a.getListener().a(this.a, new AivsError(StdStatuses.MISSING_PARAMETER, "required field not set"));
            return false;
        }
    }

    public boolean a(String str) {
        if (Logger.getLogLevel() == 3) {
            Logger.a("LiteCryptWsClient", "send: " + str);
        } else {
            Logger.b("LiteCryptWsClient", "send: text");
        }
        WebSocket webSocket = this.c;
        if (webSocket == null) {
            Logger.d("LiteCryptWsClient", "send: already closed");
            this.e = false;
            return false;
        }
        try {
            if (this.d != null) {
                str = a.b(this.d.aesCrypt(1, str.getBytes()), 10);
            }
            boolean send = webSocket.send(str);
            Logger.b("LiteCryptWsClient", "send: text, success=" + send);
            return send;
        } catch (Exception e) {
            Logger.d("LiteCryptWsClient", Logger.throwableToString(e));
            return false;
        }
    }

    public boolean a(String str, Map<String, String> map, OkHttpClient okHttpClient) {
        Logger.b("LiteCryptWsClient", "connectBlocking: " + str);
        this.a.updateTrackTimestamp("sdk.connect.ws.start", System.currentTimeMillis());
        if (this.b.getBoolean(AivsConfig.Track.ENABLE)) {
            this.j = APIUtils.getObjectMapper().createObjectNode();
            this.j.put("type", "connect");
            this.j.put("url", str);
        }
        this.i = null;
        this.e = false;
        this.g = 0;
        this.h = 0;
        this.f = str;
        int i = this.b.getInt(AivsConfig.Connection.CONNECT_TIMEOUT, 5);
        int i2 = this.b.getInt(AivsConfig.Connection.PING_INTERVAL, 90);
        Logger.b("LiteCryptWsClient", "ws pingInterval:" + i2);
        long j = (long) i;
        OkHttpClient.Builder pingInterval = okHttpClient.newBuilder().connectTimeout(j, TimeUnit.SECONDS).pingInterval((long) i2, TimeUnit.SECONDS);
        if (this.f.startsWith("ws:")) {
            this.d = new LiteCryptInterceptor(this.a);
            pingInterval.addInterceptor(this.d);
        } else {
            this.d = null;
        }
        OkHttpClient build = pingInterval.build();
        Request.Builder url = new Request.Builder().url(this.f);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                url.addHeader(entry.getKey(), entry.getValue());
            }
        }
        this.c = build.newWebSocket(url.build(), this);
        Logger.a("LiteCryptWsClient", "mWebSocket:" + this.c + ", timeout:" + i);
        synchronized (this) {
            try {
                wait(j * 1000);
            } catch (InterruptedException e) {
                Logger.d("LiteCryptWsClient", Logger.throwableToString(e));
            }
        }
        if (this.e) {
            synchronized (this.k) {
                if (this.j != null) {
                    this.j.put("result", 0);
                    this.j.put("timestamp", System.currentTimeMillis());
                    this.a.addTrackProcess(this.j);
                    this.j = null;
                }
            }
        } else if (this.c != null) {
            Logger.c("LiteCryptWsClient", "connectBlocking: cancel connection");
            this.c.cancel();
            this.c = null;
            this.e = false;
            synchronized (this.k) {
                if (this.j != null) {
                    this.j.put("result", -1);
                    this.j.put("timestamp", System.currentTimeMillis());
                    if (!this.j.hasNonNull("msg")) {
                        ObjectNode objectNode = this.j;
                        objectNode.put("msg", "connection time out at " + this.f);
                    }
                    if (this.a.getTrackData() != null && !this.a.getTrackData().getJsonNode().hasNonNull("sdk.connect.error.msg")) {
                        e eVar = this.a;
                        eVar.updateTrack("sdk.connect.error.msg", "connection time out at " + this.f);
                    }
                    this.a.addTrackProcess(this.j);
                    this.j = null;
                }
            }
        }
        return this.e;
    }

    public boolean a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("send: ");
        sb.append(bArr != null ? bArr.length : 0);
        Logger.b("LiteCryptWsClient", sb.toString());
        WebSocket webSocket = this.c;
        if (webSocket == null) {
            Logger.d("LiteCryptWsClient", "send: already closed");
            this.e = false;
            return false;
        }
        try {
            boolean send = webSocket.send(this.d != null ? ByteString.of(this.d.aesCrypt(1, bArr)) : ByteString.of(bArr));
            Logger.b("LiteCryptWsClient", "send: data, success=" + send);
            return send;
        } catch (GeneralSecurityException e) {
            Logger.d("LiteCryptWsClient", "send byte exception:" + Logger.throwableToString(e));
            return false;
        } catch (Exception e2) {
            Logger.d("LiteCryptWsClient", Logger.throwableToString(e2));
            return false;
        }
    }

    public int b() {
        return this.g;
    }

    public int c() {
        return this.h;
    }

    public boolean d() {
        return this.e;
    }

    public AivsError e() {
        return this.i;
    }

    @Override // okhttp3.WebSocketListener
    public void onClosed(WebSocket webSocket, int i, String str) {
        Logger.d("LiteCryptWsClient", "onClosed: code=" + i + "reason=" + str + ", webSocket:" + webSocket + ",mWebSocket:" + this.c);
        if (webSocket != this.c) {
            Logger.c("LiteCryptWsClient", "onClosed: not same websocket, do nothing");
            return;
        }
        this.g = i;
        if (this.e) {
            this.a.getListener().f(this.a);
        }
        synchronized (this) {
            if (!this.e) {
                notify();
            }
            this.e = false;
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onClosing(WebSocket webSocket, int i, String str) {
        Logger.c("LiteCryptWsClient", "onClosing: code=" + i + ", reason=" + str + ", webSocket:" + webSocket + ",mWebSocket:" + this.c);
        if (webSocket != null) {
            webSocket.cancel();
            Logger.a("LiteCryptWsClient", "onClosing: cancel");
        }
        if (webSocket != this.c) {
            Logger.c("LiteCryptWsClient", "onClosing: not same websocket, do nothing");
            return;
        }
        if (this.e) {
            this.a.getListener().f(this.a);
        }
        synchronized (this) {
            if (!this.e) {
                notify();
            }
            this.e = false;
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onFailure(WebSocket webSocket, Throwable th, Response response) {
        boolean z;
        AivsError aivsError;
        Logger.d("LiteCryptWsClient", "onFailure: " + th + ", " + response + " , webSocket:" + webSocket + ",mWebSocket:" + this.c);
        if (webSocket != this.c) {
            Logger.c("LiteCryptWsClient", "onFailure: not same websocket, do nothing");
            return;
        }
        TrackData trackData = this.a.getTrackData();
        if (trackData == null || trackData.isFinished()) {
            trackData = this.a.createTrackData();
            z = true;
        } else {
            z = false;
        }
        String str = "";
        String str2 = "";
        if (response != null) {
            try {
                str = response.body().string();
                str2 = response.headers().toString();
                if (str != null && str.contains("device scope data validate error")) {
                    Logger.c("LiteCryptWsClient", "onFailure: clear cached token");
                    this.a.clearAuthToken();
                }
            } catch (IOException e) {
                Logger.d("LiteCryptWsClient", Logger.throwableToString(e));
            }
        }
        if (trackData != null) {
            trackData.set("sdk.connect.error.msg", "onFailure: " + th + ", " + response + ", body=" + str + ", header=" + str2 + ", challengeId=" + this.l);
        }
        Logger.d("LiteCryptWsClient", "onFailure: " + Logger.throwableToString(th) + ", " + response + ", body=" + str + ", header=" + str2);
        if (Logger.getLogLevel() >= 3) {
            StackTraceElement[] stackTrace = th.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                Logger.d("LiteCryptWsClient", "onFailure: " + stackTraceElement.toString());
            }
        }
        if (response != null) {
            this.g = response.code();
            if (trackData != null) {
                trackData.set("sdk.connect.error.code", this.g);
            }
            int i = this.g;
            if (i == 401) {
                aivsError = new AivsError(401, str);
            } else {
                if (i == 500) {
                    aivsError = new AivsError(500, str);
                }
                this.h = this.a.processErrorMsg(this.d, str);
            }
            this.i = aivsError;
            this.h = this.a.processErrorMsg(this.d, str);
        }
        if (a(response, Logger.throwableToString(th))) {
            this.a.switchToWssMode();
        }
        if (trackData != null) {
            trackData.setTimestamp("sdk.disconnect", System.currentTimeMillis());
            if (z) {
                trackData.finishTrack();
            }
        }
        if (this.e) {
            this.a.getListener().f(this.a);
        }
        synchronized (this) {
            if (!this.e) {
                notify();
            }
            this.e = false;
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, String str) {
        String str2;
        String str3;
        Throwable e;
        Logger.a("LiteCryptWsClient", "onMessage: " + str + " ,webSocket:" + webSocket + ",mWebSocket:" + this.c);
        try {
            Instruction<?> readInstruction = APIUtils.readInstruction(this.d != null ? new String(this.d.aesCrypt(2, a.a(str.getBytes(), 0))) : str);
            if (!(readInstruction.getPayload() instanceof JsonNode)) {
                String str4 = "";
                if (readInstruction.getDialogId().isPresent()) {
                    str4 = readInstruction.getDialogId().get();
                }
                Logger.b("LiteCryptWsClient", "onMessage:" + readInstruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str4);
                if (!this.e) {
                    a(readInstruction);
                } else {
                    this.a.getListener().a(this.a, readInstruction);
                }
            } else {
                String str5 = "";
                if (readInstruction.getDialogId().isPresent()) {
                    str5 = readInstruction.getDialogId().get();
                }
                Logger.d("LiteCryptWsClient", "onMessage: failed to readInstruction from, " + readInstruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str5);
            }
        } catch (IOException e2) {
            e = e2;
            str2 = "LiteCryptWsClient";
            str3 = "onMessage: Exception: " + Logger.throwableToString(e);
            Logger.d(str2, str3);
        } catch (GeneralSecurityException e3) {
            e = e3;
            str2 = "LiteCryptWsClient";
            str3 = "onMessage: Exception: " + Logger.throwableToString(e);
            Logger.d(str2, str3);
        } catch (Exception e4) {
            str2 = "LiteCryptWsClient";
            str3 = Logger.throwableToString(e4);
            Logger.d(str2, str3);
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, ByteString byteString) {
        String str;
        String str2;
        Logger.b("LiteCryptWsClient", "onMessage: size=" + byteString.size() + ", webSocket:" + webSocket + ",mWebSocket:" + this.c);
        try {
            byte[] byteArray = byteString.toByteArray();
            if (this.d != null) {
                byteArray = this.d.aesCrypt(2, byteString.toByteArray());
            }
            if (byteArray != null) {
                this.a.getListener().a(this.a, byteArray);
                return;
            }
            Logger.d("LiteCryptWsClient", "onMessage: failed to decode bytes=" + byteString);
        } catch (GeneralSecurityException e) {
            str = "LiteCryptWsClient";
            str2 = "onMessage1: Exception: " + Logger.throwableToString(e);
            Logger.d(str, str2);
        } catch (Exception e2) {
            str = "LiteCryptWsClient";
            str2 = Logger.throwableToString(e2);
            Logger.d(str, str2);
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onOpen(WebSocket webSocket, Response response) {
        String str;
        this.a.updateTrackTimestamp("sdk.connect.ws.finish", System.currentTimeMillis());
        Logger.b("LiteCryptWsClient", "onOpen webSocket:" + webSocket + ",mWebSocket:" + this.c);
        if (this.c != null && (str = this.f) != null && str.startsWith("wss:")) {
            synchronized (this) {
                f();
                this.a.updateTrackTimestamp("sdk.connect.finish", System.currentTimeMillis());
                this.e = true;
                this.a.getListener().e(this.a);
                if (webSocket == this.c) {
                    notify();
                }
            }
        }
    }
}
