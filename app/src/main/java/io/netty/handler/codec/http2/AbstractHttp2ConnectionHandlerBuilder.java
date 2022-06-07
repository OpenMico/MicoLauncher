package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractHttp2ConnectionHandlerBuilder<T extends Http2ConnectionHandler, B extends AbstractHttp2ConnectionHandlerBuilder<T, B>> {
    static final /* synthetic */ boolean a = !AbstractHttp2ConnectionHandlerBuilder.class.desiredAssertionStatus();
    private static final long b = TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS);
    private static final Http2HeadersEncoder.SensitivityDetector c = Http2HeadersEncoder.NEVER_SENSITIVE;
    private Http2FrameListener e;
    private Boolean g;
    private Http2Connection h;
    private Http2ConnectionDecoder i;
    private Http2ConnectionEncoder j;
    private Boolean k;
    private Http2FrameLogger l;
    private Http2HeadersEncoder.SensitivityDetector m;
    private Boolean n;
    private Http2Settings d = new Http2Settings();
    private long f = b;

    protected abstract T build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) throws Exception;

    protected final B self() {
        return this;
    }

    protected Http2Settings initialSettings() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B initialSettings(Http2Settings http2Settings) {
        this.d = (Http2Settings) ObjectUtil.checkNotNull(http2Settings, "settings");
        return self();
    }

    protected Http2FrameListener frameListener() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B frameListener(Http2FrameListener http2FrameListener) {
        this.e = (Http2FrameListener) ObjectUtil.checkNotNull(http2FrameListener, "frameListener");
        return self();
    }

    protected long gracefulShutdownTimeoutMillis() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B gracefulShutdownTimeoutMillis(long j) {
        this.f = j;
        return self();
    }

    protected boolean isServer() {
        Boolean bool = this.g;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B server(boolean z) {
        a("server", "connection", this.h);
        a("server", "codec", this.i);
        a("server", "codec", this.j);
        this.g = Boolean.valueOf(z);
        return self();
    }

    protected Http2Connection connection() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B connection(Http2Connection http2Connection) {
        a("connection", "server", this.g);
        a("connection", "codec", this.i);
        a("connection", "codec", this.j);
        this.h = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
        return self();
    }

    protected Http2ConnectionDecoder decoder() {
        return this.i;
    }

    protected Http2ConnectionEncoder encoder() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        a("codec", "server", this.g);
        a("codec", "connection", this.h);
        a("codec", "frameLogger", this.l);
        a("codec", "validateHeaders", this.k);
        a("codec", "headerSensitivityDetector", this.m);
        a("codec", "encoderEnforceMaxConcurrentStreams", this.n);
        ObjectUtil.checkNotNull(http2ConnectionDecoder, "decoder");
        ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        if (http2ConnectionDecoder.connection() == http2ConnectionEncoder.connection()) {
            this.i = http2ConnectionDecoder;
            this.j = http2ConnectionEncoder;
            return self();
        }
        throw new IllegalArgumentException("The specified encoder and decoder have different connections.");
    }

    protected boolean isValidateHeaders() {
        Boolean bool = this.k;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B validateHeaders(boolean z) {
        a("validateHeaders");
        this.k = Boolean.valueOf(z);
        return self();
    }

    protected Http2FrameLogger frameLogger() {
        return this.l;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B frameLogger(Http2FrameLogger http2FrameLogger) {
        a("frameLogger");
        this.l = (Http2FrameLogger) ObjectUtil.checkNotNull(http2FrameLogger, "frameLogger");
        return self();
    }

    protected boolean encoderEnforceMaxConcurrentStreams() {
        Boolean bool = this.n;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B encoderEnforceMaxConcurrentStreams(boolean z) {
        a("encoderEnforceMaxConcurrentStreams");
        this.n = Boolean.valueOf(z);
        return self();
    }

    protected Http2HeadersEncoder.SensitivityDetector headerSensitivityDetector() {
        Http2HeadersEncoder.SensitivityDetector sensitivityDetector = this.m;
        return sensitivityDetector != null ? sensitivityDetector : c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        a("headerSensitivityDetector");
        this.m = (Http2HeadersEncoder.SensitivityDetector) ObjectUtil.checkNotNull(sensitivityDetector, "headerSensitivityDetector");
        return self();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public T build() {
        if (this.j == null) {
            Http2Connection http2Connection = this.h;
            if (http2Connection == null) {
                http2Connection = new DefaultHttp2Connection(isServer());
            }
            return a(http2Connection);
        } else if (a || this.i != null) {
            return a(this.i, this.j);
        } else {
            throw new AssertionError();
        }
    }

    private T a(Http2Connection http2Connection) {
        Http2FrameReader http2FrameReader;
        Http2FrameWriter http2FrameWriter;
        Http2ConnectionEncoder http2ConnectionEncoder;
        DefaultHttp2FrameReader defaultHttp2FrameReader = new DefaultHttp2FrameReader(isValidateHeaders());
        DefaultHttp2FrameWriter defaultHttp2FrameWriter = new DefaultHttp2FrameWriter(headerSensitivityDetector());
        Http2FrameLogger http2FrameLogger = this.l;
        if (http2FrameLogger != null) {
            http2FrameReader = new Http2InboundFrameLogger(defaultHttp2FrameReader, http2FrameLogger);
            http2FrameWriter = new Http2OutboundFrameLogger(defaultHttp2FrameWriter, this.l);
        } else {
            http2FrameReader = defaultHttp2FrameReader;
            http2FrameWriter = defaultHttp2FrameWriter;
        }
        DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder = new DefaultHttp2ConnectionEncoder(http2Connection, http2FrameWriter);
        boolean encoderEnforceMaxConcurrentStreams = encoderEnforceMaxConcurrentStreams();
        if (!encoderEnforceMaxConcurrentStreams) {
            http2ConnectionEncoder = defaultHttp2ConnectionEncoder;
        } else if (!http2Connection.isServer()) {
            http2ConnectionEncoder = new StreamBufferingEncoder(defaultHttp2ConnectionEncoder);
        } else {
            defaultHttp2ConnectionEncoder.close();
            http2FrameReader.close();
            throw new IllegalArgumentException("encoderEnforceMaxConcurrentStreams: " + encoderEnforceMaxConcurrentStreams + " not supported for server");
        }
        return a(new DefaultHttp2ConnectionDecoder(http2Connection, http2ConnectionEncoder, http2FrameReader), http2ConnectionEncoder);
    }

    private T a(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        try {
            T build = build(http2ConnectionDecoder, http2ConnectionEncoder, this.d);
            build.gracefulShutdownTimeoutMillis(this.f);
            if (build.decoder().frameListener() == null) {
                build.decoder().frameListener(this.e);
            }
            return build;
        } catch (Throwable th) {
            http2ConnectionEncoder.close();
            http2ConnectionDecoder.close();
            throw new IllegalStateException("failed to build a Http2ConnectionHandler", th);
        }
    }

    private void a(String str) {
        a(str, "server/connection", this.i);
        a(str, "server/connection", this.j);
    }

    private static void a(String str, String str2, Object obj) {
        if (obj != null) {
            throw new IllegalStateException(str + "() cannot be called because " + str2 + "() has been called already.");
        }
    }
}
