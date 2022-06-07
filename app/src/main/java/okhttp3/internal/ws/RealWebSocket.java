package okhttp3.internal.ws;

import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.library.apache.http.protocol.HTTP;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.ws.RealWebSocket;
import okhttp3.internal.ws.WebSocketReader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.IChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealWebSocket.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u0018\u0000 \\2\u00020\u00012\u00020\u0002:\u0005[\\]^_B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0016\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\f2\u0006\u00102\u001a\u000203J\b\u00104\u001a\u000200H\u0016J\u001f\u00105\u001a\u0002002\u0006\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u000109H\u0000¢\u0006\u0002\b:J\u001a\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\"2\b\u0010=\u001a\u0004\u0018\u00010\u0015H\u0016J \u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\"2\b\u0010=\u001a\u0004\u0018\u00010\u00152\u0006\u0010>\u001a\u00020\fJ\u000e\u0010?\u001a\u0002002\u0006\u0010@\u001a\u00020AJ\u001c\u0010B\u001a\u0002002\n\u0010C\u001a\u00060Dj\u0002`E2\b\u00106\u001a\u0004\u0018\u000107J\u0016\u0010F\u001a\u0002002\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(J\u0006\u0010G\u001a\u000200J\u0018\u0010H\u001a\u0002002\u0006\u0010<\u001a\u00020\"2\u0006\u0010=\u001a\u00020\u0015H\u0016J\u0010\u0010I\u001a\u0002002\u0006\u0010J\u001a\u00020\u0015H\u0016J\u0010\u0010I\u001a\u0002002\u0006\u0010K\u001a\u00020\u001dH\u0016J\u0010\u0010L\u001a\u0002002\u0006\u0010M\u001a\u00020\u001dH\u0016J\u0010\u0010N\u001a\u0002002\u0006\u0010M\u001a\u00020\u001dH\u0016J\u000e\u0010O\u001a\u00020\u000f2\u0006\u0010M\u001a\u00020\u001dJ\u0006\u0010P\u001a\u00020\u000fJ\b\u0010\u001e\u001a\u00020\fH\u0016J\u0006\u0010$\u001a\u00020\"J\u0006\u0010%\u001a\u00020\"J\b\u0010Q\u001a\u00020\u0006H\u0016J\b\u0010R\u001a\u000200H\u0002J\u0010\u0010S\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020\u0015H\u0016J\u0010\u0010S\u001a\u00020\u000f2\u0006\u0010K\u001a\u00020\u001dH\u0016J\u0018\u0010S\u001a\u00020\u000f2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010U\u001a\u00020\"H\u0002J\u0006\u0010&\u001a\u00020\"J\u0006\u0010V\u001a\u000200J\r\u0010W\u001a\u00020\u000fH\u0000¢\u0006\u0002\bXJ\r\u0010Y\u001a\u000200H\u0000¢\u0006\u0002\bZR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006`"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket;", "Lokhttp3/WebSocket;", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "originalRequest", "Lokhttp3/Request;", "listener", "Lokhttp3/WebSocketListener;", LoopType.RANDOM, "Ljava/util/Random;", "pingIntervalMillis", "", "(Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/Request;Lokhttp3/WebSocketListener;Ljava/util/Random;J)V", "awaitingPong", "", "call", "Lokhttp3/Call;", "enqueuedClose", MiotMeshDeviceItem.CONNECT_STATE_FAILED, "key", "", "getListener$okhttp", "()Lokhttp3/WebSocketListener;", "messageAndCloseQueue", "Ljava/util/ArrayDeque;", "", "name", "pongQueue", "Lokio/ByteString;", "queueSize", "reader", "Lokhttp3/internal/ws/WebSocketReader;", "receivedCloseCode", "", "receivedCloseReason", "receivedPingCount", "receivedPongCount", "sentPingCount", ChannelMessage.KEY_STREAMS, "Lokhttp3/internal/ws/RealWebSocket$Streams;", "taskQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "writer", "Lokhttp3/internal/ws/WebSocketWriter;", "writerTask", "Lokhttp3/internal/concurrent/Task;", "awaitTermination", "", RtspHeaders.Values.TIMEOUT, "timeUnit", "Ljava/util/concurrent/TimeUnit;", "cancel", "checkUpgradeSuccess", b.I, "Lokhttp3/Response;", "exchange", "Lokhttp3/internal/connection/Exchange;", "checkUpgradeSuccess$okhttp", "close", "code", IChannel.EXTRA_CLOSE_REASON, "cancelAfterCloseMillis", "connect", "client", "Lokhttp3/OkHttpClient;", "failWebSocket", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "initReaderAndWriter", "loopReader", "onReadClose", "onReadMessage", "text", "bytes", "onReadPing", SchemaActivity.KEY_PAYLOAD, "onReadPong", "pong", "processNextFrame", "request", "runWriter", "send", "data", "formatOpcode", "tearDown", "writeOneFrame", "writeOneFrame$okhttp", "writePingFrame", "writePingFrame$okhttp", HTTP.CONN_CLOSE, "Companion", "Message", "Streams", "WriterTask", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback {
    private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000;
    private static final long MAX_QUEUE_SIZE = 16777216;
    private boolean awaitingPong;
    private Call call;
    private boolean enqueuedClose;
    private boolean failed;
    private final String key;
    @NotNull
    private final WebSocketListener listener;
    private String name;
    private final Request originalRequest;
    private final long pingIntervalMillis;
    private long queueSize;
    private final Random random;
    private WebSocketReader reader;
    private String receivedCloseReason;
    private int receivedPingCount;
    private int receivedPongCount;
    private int sentPingCount;
    private Streams streams;
    private TaskQueue taskQueue;
    private WebSocketWriter writer;
    private Task writerTask;
    public static final Companion Companion = new Companion(null);
    private static final List<Protocol> ONLY_HTTP1 = CollectionsKt.listOf(Protocol.HTTP_1_1);
    private final ArrayDeque<ByteString> pongQueue = new ArrayDeque<>();
    private final ArrayDeque<Object> messageAndCloseQueue = new ArrayDeque<>();
    private int receivedCloseCode = -1;

    public RealWebSocket(@NotNull TaskRunner taskRunner, @NotNull Request originalRequest, @NotNull WebSocketListener listener, @NotNull Random random, long j) {
        Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
        Intrinsics.checkParameterIsNotNull(originalRequest, "originalRequest");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        Intrinsics.checkParameterIsNotNull(random, "random");
        this.originalRequest = originalRequest;
        this.listener = listener;
        this.random = random;
        this.pingIntervalMillis = j;
        this.taskQueue = taskRunner.newQueue();
        if (Intrinsics.areEqual("GET", this.originalRequest.method())) {
            ByteString.Companion companion = ByteString.Companion;
            byte[] bArr = new byte[16];
            this.random.nextBytes(bArr);
            this.key = ByteString.Companion.of$default(companion, bArr, 0, 0, 3, null).base64();
            return;
        }
        throw new IllegalArgumentException(("Request must be GET: " + this.originalRequest.method()).toString());
    }

    @NotNull
    public final WebSocketListener getListener$okhttp() {
        return this.listener;
    }

    @Override // okhttp3.WebSocket
    @NotNull
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.WebSocket
    public synchronized long queueSize() {
        return this.queueSize;
    }

    @Override // okhttp3.WebSocket
    public void cancel() {
        Call call = this.call;
        if (call == null) {
            Intrinsics.throwNpe();
        }
        call.cancel();
    }

    public final void connect(@NotNull OkHttpClient client) {
        Intrinsics.checkParameterIsNotNull(client, "client");
        OkHttpClient build = client.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
        final Request build2 = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header(HttpHeaders.Names.SEC_WEBSOCKET_KEY, this.key).header(HttpHeaders.Names.SEC_WEBSOCKET_VERSION, "13").build();
        this.call = new RealCall(build, build2, true);
        Call call = this.call;
        if (call == null) {
            Intrinsics.throwNpe();
        }
        call.enqueue(new Callback() { // from class: okhttp3.internal.ws.RealWebSocket$connect$1
            @Override // okhttp3.Callback
            public void onResponse(@NotNull Call call2, @NotNull Response response) {
                Intrinsics.checkParameterIsNotNull(call2, "call");
                Intrinsics.checkParameterIsNotNull(response, "response");
                Exchange exchange = response.exchange();
                try {
                    RealWebSocket.this.checkUpgradeSuccess$okhttp(response, exchange);
                    if (exchange == null) {
                        Intrinsics.throwNpe();
                    }
                    RealWebSocket.Streams newWebSocketStreams = exchange.newWebSocketStreams();
                    try {
                        RealWebSocket.this.initReaderAndWriter(Util.okHttpName + " WebSocket " + build2.url().redact(), newWebSocketStreams);
                        RealWebSocket.this.getListener$okhttp().onOpen(RealWebSocket.this, response);
                        RealWebSocket.this.loopReader();
                    } catch (Exception e) {
                        RealWebSocket.this.failWebSocket(e, null);
                    }
                } catch (IOException e2) {
                    if (exchange != null) {
                        exchange.webSocketUpgradeFailed();
                    }
                    RealWebSocket.this.failWebSocket(e2, response);
                    Util.closeQuietly(response);
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(@NotNull Call call2, @NotNull IOException e) {
                Intrinsics.checkParameterIsNotNull(call2, "call");
                Intrinsics.checkParameterIsNotNull(e, "e");
                RealWebSocket.this.failWebSocket(e, null);
            }
        });
    }

    public final void checkUpgradeSuccess$okhttp(@NotNull Response response, @Nullable Exchange exchange) throws IOException {
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.code() == 101) {
            String header$default = Response.header$default(response, "Connection", null, 2, null);
            if (StringsKt.equals("Upgrade", header$default, true)) {
                String header$default2 = Response.header$default(response, "Upgrade", null, 2, null);
                if (StringsKt.equals("websocket", header$default2, true)) {
                    String header$default3 = Response.header$default(response, HttpHeaders.Names.SEC_WEBSOCKET_ACCEPT, null, 2, null);
                    ByteString.Companion companion = ByteString.Companion;
                    String base64 = companion.encodeUtf8(this.key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
                    if (!Intrinsics.areEqual(base64, header$default3)) {
                        throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + base64 + "' but was '" + header$default3 + '\'');
                    } else if (exchange == null) {
                        throw new ProtocolException("Web Socket exchange missing: bad interceptor?");
                    }
                } else {
                    throw new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + header$default2 + '\'');
                }
            } else {
                throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + header$default + '\'');
            }
        } else {
            throw new ProtocolException("Expected HTTP 101 response but was '" + response.code() + ' ' + response.message() + '\'');
        }
    }

    public final void initReaderAndWriter(@NotNull final String name, @NotNull final Streams streams) throws IOException {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(streams, "streams");
        synchronized (this) {
            this.name = name;
            this.streams = streams;
            this.writer = new WebSocketWriter(streams.getClient(), streams.getSink(), this.random);
            this.writerTask = new WriterTask();
            if (this.pingIntervalMillis != 0) {
                final long nanos = TimeUnit.MILLISECONDS.toNanos(this.pingIntervalMillis);
                final String str = name + " ping";
                this.taskQueue.schedule(new Task(str) { // from class: okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$$inlined$synchronized$lambda$1
                    @Override // okhttp3.internal.concurrent.Task
                    public long runOnce() {
                        this.writePingFrame$okhttp();
                        return nanos;
                    }
                }, nanos);
            }
            if (!this.messageAndCloseQueue.isEmpty()) {
                runWriter();
            }
            Unit unit = Unit.INSTANCE;
        }
        this.reader = new WebSocketReader(streams.getClient(), streams.getSource(), this);
    }

    public final void loopReader() throws IOException {
        while (this.receivedCloseCode == -1) {
            WebSocketReader webSocketReader = this.reader;
            if (webSocketReader == null) {
                Intrinsics.throwNpe();
            }
            webSocketReader.processNextFrame();
        }
    }

    public final boolean processNextFrame() throws IOException {
        try {
            WebSocketReader webSocketReader = this.reader;
            if (webSocketReader == null) {
                Intrinsics.throwNpe();
            }
            webSocketReader.processNextFrame();
            return this.receivedCloseCode == -1;
        } catch (Exception e) {
            failWebSocket(e, null);
            return false;
        }
    }

    public final void awaitTermination(long j, @NotNull TimeUnit timeUnit) throws InterruptedException {
        Intrinsics.checkParameterIsNotNull(timeUnit, "timeUnit");
        this.taskQueue.idleLatch().await(j, timeUnit);
    }

    public final void tearDown() throws InterruptedException {
        this.taskQueue.shutdown();
        this.taskQueue.idleLatch().await(10L, TimeUnit.SECONDS);
    }

    public final synchronized int sentPingCount() {
        return this.sentPingCount;
    }

    public final synchronized int receivedPingCount() {
        return this.receivedPingCount;
    }

    public final synchronized int receivedPongCount() {
        return this.receivedPongCount;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(@NotNull String text) throws IOException {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.listener.onMessage(this, text);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(@NotNull ByteString bytes) throws IOException {
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
        this.listener.onMessage(this, bytes);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPing(@NotNull ByteString payload) {
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(payload);
            runWriter();
            this.receivedPingCount++;
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPong(@NotNull ByteString payload) {
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        this.receivedPongCount++;
        this.awaitingPong = false;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadClose(int i, @NotNull String reason) {
        Intrinsics.checkParameterIsNotNull(reason, "reason");
        boolean z = true;
        if (i != -1) {
            Streams streams = null;
            synchronized (this) {
                if (this.receivedCloseCode != -1) {
                    z = false;
                }
                if (z) {
                    this.receivedCloseCode = i;
                    this.receivedCloseReason = reason;
                    if (this.enqueuedClose && this.messageAndCloseQueue.isEmpty()) {
                        streams = this.streams;
                        this.streams = null;
                        this.taskQueue.shutdown();
                    }
                    Unit unit = Unit.INSTANCE;
                } else {
                    throw new IllegalStateException("already closed".toString());
                }
            }
            try {
                this.listener.onClosing(this, i, reason);
                if (streams != null) {
                    this.listener.onClosed(this, i, reason);
                }
            } finally {
                if (streams != null) {
                    Util.closeQuietly(streams);
                }
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    @Override // okhttp3.WebSocket
    public boolean send(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return send(ByteString.Companion.encodeUtf8(text), 1);
    }

    @Override // okhttp3.WebSocket
    public boolean send(@NotNull ByteString bytes) {
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
        return send(bytes, 2);
    }

    private final synchronized boolean send(ByteString byteString, int i) {
        if (!this.failed && !this.enqueuedClose) {
            if (this.queueSize + byteString.size() > MAX_QUEUE_SIZE) {
                close(1001, null);
                return false;
            }
            this.queueSize += byteString.size();
            this.messageAndCloseQueue.add(new Message(i, byteString));
            runWriter();
            return true;
        }
        return false;
    }

    public final synchronized boolean pong(@NotNull ByteString payload) {
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(payload);
            runWriter();
            return true;
        }
        return false;
    }

    @Override // okhttp3.WebSocket
    public boolean close(int i, @Nullable String str) {
        return close(i, str, 60000L);
    }

    public final synchronized boolean close(int i, @Nullable String str, long j) {
        WebSocketProtocol.INSTANCE.validateCloseCode(i);
        ByteString byteString = null;
        if (str != null) {
            byteString = ByteString.Companion.encodeUtf8(str);
            if (!(((long) byteString.size()) <= 123)) {
                throw new IllegalArgumentException(("reason.size() > 123: " + str).toString());
            }
        }
        if (!this.failed && !this.enqueuedClose) {
            this.enqueuedClose = true;
            this.messageAndCloseQueue.add(new Close(i, byteString, j));
            runWriter();
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [okhttp3.internal.ws.WebSocketWriter] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r2v16, types: [T, okhttp3.internal.ws.RealWebSocket$Streams] */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v12, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v16, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, okhttp3.internal.ws.RealWebSocket$Streams] */
    public final boolean writeOneFrame$okhttp() throws IOException {
        Ref.ObjectRef objectRef;
        WebSocketWriter webSocketWriter;
        ByteString byteString;
        Ref.ObjectRef objectRef2;
        Throwable th;
        Ref.ObjectRef objectRef3;
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        objectRef4.element = null;
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = -1;
        final Ref.ObjectRef objectRef5 = new Ref.ObjectRef();
        objectRef5.element = (String) 0;
        final Ref.ObjectRef objectRef6 = new Ref.ObjectRef();
        objectRef6.element = (Streams) 0;
        synchronized (this) {
            if (this.failed) {
                return false;
            }
            final WebSocketWriter webSocketWriter2 = this.writer;
            final ByteString poll = this.pongQueue.poll();
            if (poll == null) {
                objectRef4.element = this.messageAndCloseQueue.poll();
                if (objectRef4.element instanceof Close) {
                    intRef.element = this.receivedCloseCode;
                    objectRef5.element = this.receivedCloseReason;
                    if (intRef.element != -1) {
                        objectRef6.element = this.streams;
                        this.streams = null;
                        this.taskQueue.shutdown();
                        byteString = poll;
                        webSocketWriter = webSocketWriter2;
                        objectRef = objectRef6;
                    } else {
                        T t = objectRef4.element;
                        if (t != 0) {
                            long cancelAfterCloseMillis = ((Close) t).getCancelAfterCloseMillis();
                            final String str = this.name + " cancel";
                            byteString = poll;
                            webSocketWriter = webSocketWriter2;
                            objectRef = objectRef6;
                            this.taskQueue.schedule(new Task(str, true) { // from class: okhttp3.internal.ws.RealWebSocket$writeOneFrame$$inlined$synchronized$lambda$1
                                @Override // okhttp3.internal.concurrent.Task
                                public long runOnce() {
                                    this.cancel();
                                    return -1L;
                                }
                            }, TimeUnit.MILLISECONDS.toNanos(cancelAfterCloseMillis));
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close");
                        }
                    }
                } else {
                    byteString = poll;
                    webSocketWriter = webSocketWriter2;
                    objectRef = objectRef6;
                    if (objectRef4.element == 0) {
                        return false;
                    }
                }
            } else {
                byteString = poll;
                webSocketWriter = webSocketWriter2;
                objectRef = objectRef6;
            }
            Unit unit = Unit.INSTANCE;
            try {
                if (byteString != null) {
                    if (webSocketWriter == null) {
                        Intrinsics.throwNpe();
                    }
                    webSocketWriter.writePong(byteString);
                    objectRef3 = objectRef;
                } else {
                    objectRef2 = webSocketWriter;
                    if (objectRef4.element instanceof Message) {
                        T t2 = objectRef4.element;
                        if (t2 != 0) {
                            ByteString data = ((Message) t2).getData();
                            if (objectRef2 == 0) {
                                Intrinsics.throwNpe();
                            }
                            T t3 = objectRef4.element;
                            if (t3 != 0) {
                                BufferedSink buffer = Okio.buffer(objectRef2.newMessageSink(((Message) t3).getFormatOpcode(), data.size()));
                                buffer.write(data);
                                buffer.close();
                                synchronized (this) {
                                    this.queueSize -= data.size();
                                    Unit unit2 = Unit.INSTANCE;
                                }
                                objectRef3 = objectRef;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message");
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message");
                        }
                    } else {
                        try {
                            if (objectRef4.element instanceof Close) {
                                T t4 = objectRef4.element;
                                if (t4 != 0) {
                                    Close close = (Close) t4;
                                    if (objectRef2 == 0) {
                                        Intrinsics.throwNpe();
                                    }
                                    objectRef2.writeClose(close.getCode(), close.getReason());
                                    objectRef3 = objectRef;
                                    if (((Streams) objectRef3.element) != null) {
                                        WebSocketListener webSocketListener = this.listener;
                                        RealWebSocket realWebSocket = this;
                                        int i = intRef.element;
                                        String str2 = (String) objectRef5.element;
                                        if (str2 == null) {
                                            Intrinsics.throwNpe();
                                        }
                                        webSocketListener.onClosed(realWebSocket, i, str2);
                                    }
                                } else {
                                    throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close");
                                }
                            } else {
                                throw new AssertionError();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            Streams streams = (Streams) objectRef2.element;
                            if (streams != null) {
                                Util.closeQuietly(streams);
                            }
                            throw th;
                        }
                    }
                }
                Streams streams2 = (Streams) objectRef3.element;
                if (streams2 != null) {
                    Util.closeQuietly(streams2);
                }
                return true;
            } catch (Throwable th3) {
                th = th3;
                objectRef2 = objectRef;
            }
        }
    }

    public final void writePingFrame$okhttp() {
        synchronized (this) {
            if (!this.failed) {
                WebSocketWriter webSocketWriter = this.writer;
                int i = this.awaitingPong ? this.sentPingCount : -1;
                this.sentPingCount++;
                this.awaitingPong = true;
                Unit unit = Unit.INSTANCE;
                if (i != -1) {
                    failWebSocket(new SocketTimeoutException("sent ping but didn't receive pong within " + this.pingIntervalMillis + "ms (after " + (i - 1) + " successful ping/pongs)"), null);
                    return;
                }
                if (webSocketWriter == null) {
                    try {
                        Intrinsics.throwNpe();
                    } catch (IOException e) {
                        failWebSocket(e, null);
                        return;
                    }
                }
                webSocketWriter.writePing(ByteString.EMPTY);
            }
        }
    }

    public final void failWebSocket(@NotNull Exception e, @Nullable Response response) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        synchronized (this) {
            if (!this.failed) {
                this.failed = true;
                Streams streams = this.streams;
                this.streams = null;
                this.taskQueue.shutdown();
                Unit unit = Unit.INSTANCE;
                try {
                    this.listener.onFailure(this, e, response);
                } finally {
                    if (streams != null) {
                        Util.closeQuietly(streams);
                    }
                }
            }
        }
    }

    /* compiled from: RealWebSocket.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Message;", "", "formatOpcode", "", "data", "Lokio/ByteString;", "(ILokio/ByteString;)V", "getData", "()Lokio/ByteString;", "getFormatOpcode", "()I", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Message {
        @NotNull
        private final ByteString data;
        private final int formatOpcode;

        public Message(int i, @NotNull ByteString data) {
            Intrinsics.checkParameterIsNotNull(data, "data");
            this.formatOpcode = i;
            this.data = data;
        }

        public final int getFormatOpcode() {
            return this.formatOpcode;
        }

        @NotNull
        public final ByteString getData() {
            return this.data;
        }
    }

    /* compiled from: RealWebSocket.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Close;", "", "code", "", IChannel.EXTRA_CLOSE_REASON, "Lokio/ByteString;", "cancelAfterCloseMillis", "", "(ILokio/ByteString;J)V", "getCancelAfterCloseMillis", "()J", "getCode", "()I", "getReason", "()Lokio/ByteString;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Close {
        private final long cancelAfterCloseMillis;
        private final int code;
        @Nullable
        private final ByteString reason;

        public Close(int i, @Nullable ByteString byteString, long j) {
            this.code = i;
            this.reason = byteString;
            this.cancelAfterCloseMillis = j;
        }

        public final int getCode() {
            return this.code;
        }

        @Nullable
        public final ByteString getReason() {
            return this.reason;
        }

        public final long getCancelAfterCloseMillis() {
            return this.cancelAfterCloseMillis;
        }
    }

    /* compiled from: RealWebSocket.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Streams;", "Ljava/io/Closeable;", "client", "", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(ZLokio/BufferedSource;Lokio/BufferedSink;)V", "getClient", "()Z", "getSink", "()Lokio/BufferedSink;", "getSource", "()Lokio/BufferedSource;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static abstract class Streams implements Closeable {
        private final boolean client;
        @NotNull
        private final BufferedSink sink;
        @NotNull
        private final BufferedSource source;

        public Streams(boolean z, @NotNull BufferedSource source, @NotNull BufferedSink sink) {
            Intrinsics.checkParameterIsNotNull(source, "source");
            Intrinsics.checkParameterIsNotNull(sink, "sink");
            this.client = z;
            this.source = source;
            this.sink = sink;
        }

        public final boolean getClient() {
            return this.client;
        }

        @NotNull
        public final BufferedSource getSource() {
            return this.source;
        }

        @NotNull
        public final BufferedSink getSink() {
            return this.sink;
        }
    }

    /* compiled from: RealWebSocket.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$WriterTask;", "Lokhttp3/internal/concurrent/Task;", "(Lokhttp3/internal/ws/RealWebSocket;)V", "runOnce", "", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    private final class WriterTask extends Task {
        public WriterTask() {
            super(RealWebSocket.this.name + " writer", false, 2, null);
        }

        @Override // okhttp3.internal.concurrent.Task
        public long runOnce() {
            try {
                return RealWebSocket.this.writeOneFrame$okhttp() ? 0L : -1L;
            } catch (IOException e) {
                RealWebSocket.this.failWebSocket(e, null);
                return -1L;
            }
        }
    }

    /* compiled from: RealWebSocket.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lokhttp3/internal/ws/RealWebSocket$Companion;", "", "()V", "CANCEL_AFTER_CLOSE_MILLIS", "", "MAX_QUEUE_SIZE", "ONLY_HTTP1", "", "Lokhttp3/Protocol;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final void runWriter() {
        if (!Util.assertionsEnabled || Thread.holdsLock(this)) {
            Task task = this.writerTask;
            if (task != null) {
                TaskQueue.schedule$default(this.taskQueue, task, 0L, 2, null);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }
}
