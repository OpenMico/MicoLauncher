package org.eclipse.jetty.http.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/* loaded from: classes5.dex */
public abstract class AbstractCompressedStream extends ServletOutputStream {
    protected ByteArrayOutputStream2 _bOut;
    protected int _bufferSize;
    protected boolean _closed;
    protected DeflaterOutputStream _compressedOutputStream;
    protected long _contentLength;
    protected boolean _doNotCompress;
    private final String _encoding;
    protected int _minCompressSize;
    protected OutputStream _out;
    protected HttpServletRequest _request;
    protected HttpServletResponse _response;

    protected abstract DeflaterOutputStream createStream() throws IOException;

    public AbstractCompressedStream(String str, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, long j, int i, int i2) throws IOException {
        this._encoding = str;
        this._request = httpServletRequest;
        this._response = httpServletResponse;
        this._contentLength = j;
        this._bufferSize = i;
        this._minCompressSize = i2;
        if (i2 == 0) {
            doCompress();
        }
    }

    public void resetBuffer() {
        if (!this._response.isCommitted()) {
            this._closed = false;
            this._out = null;
            this._bOut = null;
            if (this._compressedOutputStream != null) {
                this._response.setHeader("Content-Encoding", null);
            }
            this._compressedOutputStream = null;
            this._doNotCompress = false;
            return;
        }
        throw new IllegalStateException("Committed");
    }

    public void setContentLength(long j) {
        this._contentLength = j;
        if (this._doNotCompress && j >= 0) {
            long j2 = this._contentLength;
            if (j2 < 2147483647L) {
                this._response.setContentLength((int) j2);
            } else {
                this._response.setHeader("Content-Length", Long.toString(j2));
            }
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        if (this._out == null || this._bOut != null) {
            long j = this._contentLength;
            if (j <= 0 || j >= this._minCompressSize) {
                doCompress();
            } else {
                doNotCompress();
            }
        }
        this._out.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this._closed) {
            if (this._request.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
                flush();
                return;
            }
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 != null) {
                if (this._contentLength < 0) {
                    this._contentLength = byteArrayOutputStream2.getCount();
                }
                if (this._contentLength < this._minCompressSize) {
                    doNotCompress();
                } else {
                    doCompress();
                }
            } else if (this._out == null) {
                doNotCompress();
            }
            DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
            if (deflaterOutputStream != null) {
                deflaterOutputStream.close();
            } else {
                this._out.close();
            }
            this._closed = true;
        }
    }

    public void finish() throws IOException {
        if (!this._closed) {
            if (this._out == null || this._bOut != null) {
                long j = this._contentLength;
                if (j <= 0 || j >= this._minCompressSize) {
                    doCompress();
                } else {
                    doNotCompress();
                }
            }
            DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
            if (deflaterOutputStream != null && !this._closed) {
                this._closed = true;
                deflaterOutputStream.close();
            }
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        checkOut(1);
        this._out.write(i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        checkOut(bArr.length);
        this._out.write(bArr);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        checkOut(i2);
        this._out.write(bArr, i, i2);
    }

    public void doCompress() throws IOException {
        if (this._compressedOutputStream != null) {
            return;
        }
        if (!this._response.isCommitted()) {
            setHeader("Content-Encoding", this._encoding);
            if (this._response.containsHeader("Content-Encoding")) {
                DeflaterOutputStream createStream = createStream();
                this._compressedOutputStream = createStream;
                this._out = createStream;
                ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
                if (byteArrayOutputStream2 != null) {
                    this._out.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
                    this._bOut = null;
                    return;
                }
                return;
            }
            doNotCompress();
            return;
        }
        throw new IllegalStateException();
    }

    public void doNotCompress() throws IOException {
        if (this._compressedOutputStream != null) {
            throw new IllegalStateException("Compressed output stream is already assigned.");
        } else if (this._out == null || this._bOut != null) {
            this._doNotCompress = true;
            this._out = this._response.getOutputStream();
            setContentLength(this._contentLength);
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 != null) {
                this._out.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
            }
            this._bOut = null;
        }
    }

    private void checkOut(int i) throws IOException {
        if (this._closed) {
            throw new IOException("CLOSED");
        } else if (this._out == null) {
            if (!this._response.isCommitted()) {
                long j = this._contentLength;
                if (j < 0 || j >= this._minCompressSize) {
                    if (i > this._minCompressSize) {
                        doCompress();
                        return;
                    }
                    ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2(this._bufferSize);
                    this._bOut = byteArrayOutputStream2;
                    this._out = byteArrayOutputStream2;
                    return;
                }
            }
            doNotCompress();
        } else if (this._bOut != null) {
            if (!this._response.isCommitted()) {
                long j2 = this._contentLength;
                if (j2 < 0 || j2 >= this._minCompressSize) {
                    if (i >= this._bOut.getBuf().length - this._bOut.getCount()) {
                        doCompress();
                        return;
                    }
                    return;
                }
            }
            doNotCompress();
        }
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public boolean isClosed() {
        return this._closed;
    }

    protected PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }

    protected void setHeader(String str, String str2) {
        this._response.setHeader(str, str2);
    }
}
