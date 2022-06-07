package org.eclipse.jetty.server.nio;

import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.server.AbstractConnector;

/* loaded from: classes5.dex */
public abstract class AbstractNIOConnector extends AbstractConnector implements NIOConnector {
    public AbstractNIOConnector() {
        this._buffers.setRequestBufferType(Buffers.Type.DIRECT);
        this._buffers.setRequestHeaderType(Buffers.Type.INDIRECT);
        this._buffers.setResponseBufferType(Buffers.Type.DIRECT);
        this._buffers.setResponseHeaderType(Buffers.Type.INDIRECT);
    }

    @Override // org.eclipse.jetty.server.nio.NIOConnector
    public boolean getUseDirectBuffers() {
        return getRequestBufferType() == Buffers.Type.DIRECT;
    }

    public void setUseDirectBuffers(boolean z) {
        this._buffers.setRequestBufferType(z ? Buffers.Type.DIRECT : Buffers.Type.INDIRECT);
        this._buffers.setResponseBufferType(z ? Buffers.Type.DIRECT : Buffers.Type.INDIRECT);
    }
}
