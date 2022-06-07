package org.eclipse.jetty.io;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.io.Buffers;

/* loaded from: classes5.dex */
public class PooledBuffers extends AbstractBuffers {
    private final int _maxSize;
    private final boolean _otherBuffers;
    private final boolean _otherHeaders;
    private final AtomicInteger _size = new AtomicInteger();
    private final Queue<Buffer> _headers = new ConcurrentLinkedQueue();
    private final Queue<Buffer> _buffers = new ConcurrentLinkedQueue();
    private final Queue<Buffer> _others = new ConcurrentLinkedQueue();

    public PooledBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3, int i3) {
        super(type, i, type2, i2, type3);
        boolean z = true;
        this._otherHeaders = type == type3;
        this._otherBuffers = type2 != type3 ? false : z;
        this._maxSize = i3;
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getHeader() {
        Buffer poll = this._headers.poll();
        if (poll == null) {
            return newHeader();
        }
        this._size.decrementAndGet();
        return poll;
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getBuffer() {
        Buffer poll = this._buffers.poll();
        if (poll == null) {
            return newBuffer();
        }
        this._size.decrementAndGet();
        return poll;
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getBuffer(int i) {
        if (this._otherHeaders && i == getHeaderSize()) {
            return getHeader();
        }
        if (this._otherBuffers && i == getBufferSize()) {
            return getBuffer();
        }
        Buffer poll = this._others.poll();
        while (poll != null && poll.capacity() != i) {
            this._size.decrementAndGet();
            poll = this._others.poll();
        }
        if (poll == null) {
            return newBuffer(i);
        }
        this._size.decrementAndGet();
        return poll;
    }

    @Override // org.eclipse.jetty.io.Buffers
    public void returnBuffer(Buffer buffer) {
        buffer.clear();
        if (!buffer.isVolatile() && !buffer.isImmutable()) {
            if (this._size.incrementAndGet() > this._maxSize) {
                this._size.decrementAndGet();
            } else if (isHeader(buffer)) {
                this._headers.add(buffer);
            } else if (isBuffer(buffer)) {
                this._buffers.add(buffer);
            } else {
                this._others.add(buffer);
            }
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffers
    public String toString() {
        return String.format("%s [%d/%d@%d,%d/%d@%d,%d/%d@-]", getClass().getSimpleName(), Integer.valueOf(this._headers.size()), Integer.valueOf(this._maxSize), Integer.valueOf(this._headerSize), Integer.valueOf(this._buffers.size()), Integer.valueOf(this._maxSize), Integer.valueOf(this._bufferSize), Integer.valueOf(this._others.size()), Integer.valueOf(this._maxSize));
    }
}
