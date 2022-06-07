package org.eclipse.jetty.io;

import com.xiaomi.mipush.sdk.Constants;
import org.eclipse.jetty.io.Buffers;

/* loaded from: classes5.dex */
public class ThreadLocalBuffers extends AbstractBuffers {
    private final ThreadLocal<ThreadBuffers> _buffers = new ThreadLocal<ThreadBuffers>() { // from class: org.eclipse.jetty.io.ThreadLocalBuffers.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public ThreadBuffers initialValue() {
            return new ThreadBuffers();
        }
    };

    public ThreadLocalBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3) {
        super(type, i, type2, i2, type3);
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getBuffer() {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._buffer != null) {
            Buffer buffer = threadBuffers._buffer;
            threadBuffers._buffer = null;
            return buffer;
        } else if (threadBuffers._other == null || !isBuffer(threadBuffers._other)) {
            return newBuffer();
        } else {
            Buffer buffer2 = threadBuffers._other;
            threadBuffers._other = null;
            return buffer2;
        }
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getHeader() {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._header != null) {
            Buffer buffer = threadBuffers._header;
            threadBuffers._header = null;
            return buffer;
        } else if (threadBuffers._other == null || !isHeader(threadBuffers._other)) {
            return newHeader();
        } else {
            Buffer buffer2 = threadBuffers._other;
            threadBuffers._other = null;
            return buffer2;
        }
    }

    @Override // org.eclipse.jetty.io.Buffers
    public Buffer getBuffer(int i) {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._other == null || threadBuffers._other.capacity() != i) {
            return newBuffer(i);
        }
        Buffer buffer = threadBuffers._other;
        threadBuffers._other = null;
        return buffer;
    }

    @Override // org.eclipse.jetty.io.Buffers
    public void returnBuffer(Buffer buffer) {
        buffer.clear();
        if (!buffer.isVolatile() && !buffer.isImmutable()) {
            ThreadBuffers threadBuffers = this._buffers.get();
            if (threadBuffers._header == null && isHeader(buffer)) {
                threadBuffers._header = buffer;
            } else if (threadBuffers._buffer != null || !isBuffer(buffer)) {
                threadBuffers._other = buffer;
            } else {
                threadBuffers._buffer = buffer;
            }
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffers
    public String toString() {
        return "{{" + getHeaderSize() + Constants.ACCEPT_TIME_SEPARATOR_SP + getBufferSize() + "}}";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public static class ThreadBuffers {
        Buffer _buffer;
        Buffer _header;
        Buffer _other;

        protected ThreadBuffers() {
        }
    }
}
