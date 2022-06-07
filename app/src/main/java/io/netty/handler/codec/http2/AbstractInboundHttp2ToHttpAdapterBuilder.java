package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractInboundHttp2ToHttpAdapterBuilder;
import io.netty.handler.codec.http2.InboundHttp2ToHttpAdapter;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public abstract class AbstractInboundHttp2ToHttpAdapterBuilder<T extends InboundHttp2ToHttpAdapter, B extends AbstractInboundHttp2ToHttpAdapterBuilder<T, B>> {
    private final Http2Connection a;
    private int b;
    private boolean c;
    private boolean d;

    protected abstract T build(Http2Connection http2Connection, int i, boolean z, boolean z2) throws Exception;

    protected final B self() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractInboundHttp2ToHttpAdapterBuilder(Http2Connection http2Connection) {
        this.a = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
    }

    protected Http2Connection connection() {
        return this.a;
    }

    protected int maxContentLength() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B maxContentLength(int i) {
        this.b = i;
        return self();
    }

    protected boolean isValidateHttpHeaders() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B validateHttpHeaders(boolean z) {
        this.c = z;
        return self();
    }

    protected boolean isPropagateSettings() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public B propagateSettings(boolean z) {
        this.d = z;
        return self();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public T build() {
        try {
            T build = build(connection(), maxContentLength(), isValidateHttpHeaders(), isPropagateSettings());
            this.a.addListener(build);
            return build;
        } catch (Throwable th) {
            throw new IllegalStateException("failed to create a new InboundHttp2ToHttpAdapter", th);
        }
    }
}
