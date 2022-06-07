package org.eclipse.jetty.http;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class AbstractGenerator implements Generator {
    private static final Logger LOG = Log.getLogger(AbstractGenerator.class);
    public static final byte[] NO_BYTES = new byte[0];
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 4;
    public static final int STATE_FLUSHING = 3;
    public static final int STATE_HEADER = 0;
    protected Buffer _buffer;
    protected final Buffers _buffers;
    protected Buffer _content;
    protected Buffer _date;
    protected final EndPoint _endp;
    protected Buffer _header;
    protected Buffer _method;
    protected Buffer _reason;
    private boolean _sendServerVersion;
    protected String _uri;
    protected int _state = 0;
    protected int _status = 0;
    protected int _version = 11;
    protected long _contentWritten = 0;
    protected long _contentLength = -3;
    protected boolean _last = false;
    protected boolean _head = false;
    protected boolean _noContent = false;
    protected Boolean _persistent = null;

    @Override // org.eclipse.jetty.http.Generator
    public abstract void completeHeader(HttpFields httpFields, boolean z) throws IOException;

    @Override // org.eclipse.jetty.http.Generator
    public abstract int flushBuffer() throws IOException;

    public abstract boolean isRequest();

    public abstract boolean isResponse();

    public abstract int prepareUncheckedAddContent() throws IOException;

    public AbstractGenerator(Buffers buffers, EndPoint endPoint) {
        this._buffers = buffers;
        this._endp = endPoint;
    }

    public boolean isOpen() {
        return this._endp.isOpen();
    }

    @Override // org.eclipse.jetty.http.Generator
    public void reset() {
        this._state = 0;
        this._status = 0;
        this._version = 11;
        this._reason = null;
        this._last = false;
        this._head = false;
        this._noContent = false;
        this._persistent = null;
        this._contentWritten = 0L;
        this._contentLength = -3L;
        this._date = null;
        this._content = null;
        this._method = null;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void returnBuffers() {
        Buffer buffer = this._buffer;
        if (buffer != null && buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
        Buffer buffer2 = this._header;
        if (buffer2 != null && buffer2.length() == 0) {
            this._buffers.returnBuffer(this._header);
            this._header = null;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void resetBuffer() {
        if (this._state < 3) {
            this._last = false;
            this._persistent = null;
            this._contentWritten = 0L;
            this._contentLength = -3L;
            this._content = null;
            Buffer buffer = this._buffer;
            if (buffer != null) {
                buffer.clear();
                return;
            }
            return;
        }
        throw new IllegalStateException("Flushed");
    }

    @Override // org.eclipse.jetty.http.Generator
    public int getContentBufferSize() {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        return this._buffer.capacity();
    }

    @Override // org.eclipse.jetty.http.Generator
    public void increaseContentBufferSize(int i) {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        if (i > this._buffer.capacity()) {
            Buffer buffer = this._buffers.getBuffer(i);
            buffer.put(this._buffer);
            this._buffers.returnBuffer(this._buffer);
            this._buffer = buffer;
        }
    }

    public Buffer getUncheckedBuffer() {
        return this._buffer;
    }

    public boolean getSendServerVersion() {
        return this._sendServerVersion;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setSendServerVersion(boolean z) {
        this._sendServerVersion = z;
    }

    public int getState() {
        return this._state;
    }

    public boolean isState(int i) {
        return this._state == i;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isComplete() {
        return this._state == 4;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isIdle() {
        return this._state == 0 && this._method == null && this._status == 0;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isCommitted() {
        return this._state != 0;
    }

    public boolean isHead() {
        return this._head;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setContentLength(long j) {
        if (j < 0) {
            this._contentLength = -3L;
        } else {
            this._contentLength = j;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setHead(boolean z) {
        this._head = z;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isPersistent() {
        Boolean bool = this._persistent;
        return bool != null ? bool.booleanValue() : isRequest() || this._version > 10;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setPersistent(boolean z) {
        this._persistent = Boolean.valueOf(z);
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setVersion(int i) {
        if (this._state == 0) {
            this._version = i;
            if (this._version == 9 && this._method != null) {
                this._noContent = true;
                return;
            }
            return;
        }
        throw new IllegalStateException("STATE!=START " + this._state);
    }

    public int getVersion() {
        return this._version;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setDate(Buffer buffer) {
        this._date = buffer;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setRequest(String str, String str2) {
        if (str == null || "GET".equals(str)) {
            this._method = HttpMethods.GET_BUFFER;
        } else {
            this._method = HttpMethods.CACHE.lookup(str);
        }
        this._uri = str2;
        if (this._version == 9) {
            this._noContent = true;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setResponse(int i, String str) {
        if (this._state == 0) {
            this._method = null;
            this._status = i;
            if (str != null) {
                int length = str.length();
                if (length > 1024) {
                    length = 1024;
                }
                this._reason = new ByteArrayBuffer(length);
                for (int i2 = 0; i2 < length; i2++) {
                    char charAt = str.charAt(i2);
                    if (charAt == '\r' || charAt == '\n') {
                        this._reason.put((byte) 32);
                    } else {
                        this._reason.put((byte) charAt);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("STATE!=START");
    }

    void uncheckedAddContent(int i) {
        this._buffer.put((byte) i);
    }

    public void completeUncheckedAddContent() {
        if (this._noContent) {
            Buffer buffer = this._buffer;
            if (buffer != null) {
                buffer.clear();
                return;
            }
            return;
        }
        this._contentWritten += this._buffer.length();
        if (this._head) {
            this._buffer.clear();
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isBufferFull() {
        Buffer buffer = this._buffer;
        if (buffer == null || buffer.space() != 0) {
            Buffer buffer2 = this._content;
            return buffer2 != null && buffer2.length() > 0;
        }
        if (this._buffer.length() == 0 && !this._buffer.isImmutable()) {
            this._buffer.compact();
        }
        return this._buffer.space() == 0;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isWritten() {
        return this._contentWritten > 0;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isAllContentWritten() {
        long j = this._contentLength;
        return j >= 0 && this._contentWritten >= j;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void complete() throws IOException {
        if (this._state != 0) {
            long j = this._contentLength;
            if (j >= 0 && j != this._contentWritten && !this._head) {
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("ContentLength written==" + this._contentWritten + " != contentLength==" + this._contentLength, new Object[0]);
                }
                this._persistent = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("State==HEADER");
    }

    public void flush(long j) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = j + currentTimeMillis;
        Buffer buffer = this._content;
        Buffer buffer2 = this._buffer;
        if ((buffer != null && buffer.length() > 0) || ((buffer2 != null && buffer2.length() > 0) || isBufferFull())) {
            flushBuffer();
            while (currentTimeMillis < j2) {
                if (((buffer != null && buffer.length() > 0) || (buffer2 != null && buffer2.length() > 0)) && this._endp.isOpen() && !this._endp.isOutputShutdown()) {
                    blockForOutput(j2 - currentTimeMillis);
                    currentTimeMillis = System.currentTimeMillis();
                } else {
                    return;
                }
            }
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void sendError(int i, String str, String str2, boolean z) throws IOException {
        if (z) {
            this._persistent = false;
        }
        if (isCommitted()) {
            LOG.debug("sendError on committed: {} {}", Integer.valueOf(i), str);
            return;
        }
        LOG.debug("sendError: {} {}", Integer.valueOf(i), str);
        setResponse(i, str);
        if (str2 != null) {
            completeHeader(null, false);
            addContent(new View(new ByteArrayBuffer(str2)), true);
        } else {
            completeHeader(null, true);
        }
        complete();
    }

    @Override // org.eclipse.jetty.http.Generator
    public long getContentWritten() {
        return this._contentWritten;
    }

    public void blockForOutput(long j) throws IOException {
        if (this._endp.isBlocking()) {
            try {
                flushBuffer();
            } catch (IOException e) {
                this._endp.close();
                throw e;
            }
        } else if (this._endp.blockWritable(j)) {
            flushBuffer();
        } else {
            this._endp.close();
            throw new EofException(RtspHeaders.Values.TIMEOUT);
        }
    }
}
