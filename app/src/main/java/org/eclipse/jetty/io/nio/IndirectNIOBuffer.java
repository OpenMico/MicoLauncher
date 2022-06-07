package org.eclipse.jetty.io.nio;

import java.nio.ByteBuffer;
import org.eclipse.jetty.io.ByteArrayBuffer;

/* loaded from: classes5.dex */
public class IndirectNIOBuffer extends ByteArrayBuffer implements NIOBuffer {
    protected final ByteBuffer _buf;

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public boolean isDirect() {
        return false;
    }

    public IndirectNIOBuffer(int i) {
        super(i, 2, false);
        this._buf = ByteBuffer.wrap(this._bytes);
        this._buf.position(0);
        ByteBuffer byteBuffer = this._buf;
        byteBuffer.limit(byteBuffer.capacity());
    }

    public IndirectNIOBuffer(ByteBuffer byteBuffer, boolean z) {
        super(byteBuffer.array(), 0, 0, z ? 0 : 2, false);
        if (!byteBuffer.isDirect()) {
            this._buf = byteBuffer;
            this._get = byteBuffer.position();
            this._put = byteBuffer.limit();
            byteBuffer.position(0);
            byteBuffer.limit(byteBuffer.capacity());
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public ByteBuffer getByteBuffer() {
        return this._buf;
    }
}
