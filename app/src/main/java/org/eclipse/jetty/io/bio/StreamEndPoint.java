package org.eclipse.jetty.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.EndPoint;

/* loaded from: classes5.dex */
public class StreamEndPoint implements EndPoint {
    InputStream _in;
    boolean _ishut;
    int _maxIdleTime;
    boolean _oshut;
    OutputStream _out;

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockReadable(long j) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockWritable(long j) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalAddr() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalHost() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getLocalPort() {
        return 0;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteAddr() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteHost() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getRemotePort() {
        return 0;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public Object getTransport() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isBlocking() {
        return true;
    }

    public StreamEndPoint(InputStream inputStream, OutputStream outputStream) {
        this._in = inputStream;
        this._out = outputStream;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOpen() {
        return this._in != null;
    }

    public final boolean isClosed() {
        return !isOpen();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownOutput() throws IOException {
        OutputStream outputStream;
        this._oshut = true;
        if (this._ishut && (outputStream = this._out) != null) {
            outputStream.close();
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isInputShutdown() {
        return this._ishut;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownInput() throws IOException {
        InputStream inputStream;
        this._ishut = true;
        if (this._oshut && (inputStream = this._in) != null) {
            inputStream.close();
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOutputShutdown() {
        return this._oshut;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void close() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            inputStream.close();
        }
        this._in = null;
        OutputStream outputStream = this._out;
        if (outputStream != null) {
            outputStream.close();
        }
        this._out = null;
    }

    protected void idleExpired() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int fill(Buffer buffer) throws IOException {
        if (this._ishut) {
            return -1;
        }
        if (this._in == null) {
            return 0;
        }
        int space = buffer.space();
        if (space > 0) {
            try {
                int readFrom = buffer.readFrom(this._in, space);
                if (readFrom < 0) {
                    shutdownInput();
                }
                return readFrom;
            } catch (SocketTimeoutException unused) {
                idleExpired();
                return -1;
            }
        } else if (buffer.hasContent()) {
            return 0;
        } else {
            throw new IOException("FULL");
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        if (this._oshut) {
            return -1;
        }
        if (this._out == null) {
            return 0;
        }
        int length = buffer.length();
        if (length > 0) {
            buffer.writeTo(this._out);
        }
        if (!buffer.isImmutable()) {
            buffer.clear();
        }
        return length;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        int i;
        int length;
        int length2;
        if (buffer == null || (length2 = buffer.length()) <= 0) {
            i = 0;
        } else {
            i = flush(buffer);
            if (i < length2) {
                return i;
            }
        }
        if (buffer2 != null && (length = buffer2.length()) > 0) {
            int flush = flush(buffer2);
            if (flush < 0) {
                return i > 0 ? i : flush;
            }
            i += flush;
            if (flush < length) {
                return i;
            }
        }
        if (buffer3 == null || buffer3.length() <= 0) {
            return i;
        }
        int flush2 = flush(buffer3);
        return flush2 < 0 ? i > 0 ? i : flush2 : i + flush2;
    }

    public InputStream getInputStream() {
        return this._in;
    }

    public void setInputStream(InputStream inputStream) {
        this._in = inputStream;
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public void setOutputStream(OutputStream outputStream) {
        this._out = outputStream;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void flush() throws IOException {
        OutputStream outputStream = this._out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void setMaxIdleTime(int i) throws IOException {
        this._maxIdleTime = i;
    }
}
