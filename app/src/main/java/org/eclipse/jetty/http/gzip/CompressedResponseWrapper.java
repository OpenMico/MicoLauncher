package org.eclipse.jetty.http.gzip;

import com.xiaomi.infra.galaxy.fds.Common;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes5.dex */
public abstract class CompressedResponseWrapper extends HttpServletResponseWrapper {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int DEFAULT_MIN_COMPRESS_SIZE = 256;
    private AbstractCompressedStream _compressedStream;
    private Set<String> _mimeTypes;
    private boolean _noCompression;
    protected HttpServletRequest _request;
    private PrintWriter _writer;
    private int _bufferSize = 8192;
    private int _minCompressSize = 256;
    private long _contentLength = -1;

    protected abstract AbstractCompressedStream newCompressedStream(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, long j, int i, int i2) throws IOException;

    public CompressedResponseWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this._request = httpServletRequest;
    }

    public void setMimeTypes(Set<String> set) {
        this._mimeTypes = set;
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void setBufferSize(int i) {
        this._bufferSize = i;
    }

    public void setMinCompressSize(int i) {
        this._minCompressSize = i;
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void setContentType(String str) {
        int indexOf;
        super.setContentType(str);
        if (str != null && (indexOf = str.indexOf(";")) > 0) {
            str = str.substring(0, indexOf);
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream == null || abstractCompressedStream.getOutputStream() == null) {
            if (this._mimeTypes != null || str == null || !str.contains("gzip")) {
                Set<String> set = this._mimeTypes;
                if (set == null) {
                    return;
                }
                if (str != null && set.contains(StringUtil.asciiToLowerCase(str))) {
                    return;
                }
            }
            noCompression();
        }
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void setStatus(int i, String str) {
        super.setStatus(i, str);
        if (i < 200 || i == 204 || i == 205 || i >= 300) {
            noCompression();
        }
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void setStatus(int i) {
        super.setStatus(i);
        if (i < 200 || i == 204 || i == 205 || i >= 300) {
            noCompression();
        }
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void setContentLength(int i) {
        setContentLength(i);
    }

    protected void setContentLength(long j) {
        this._contentLength = j;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setContentLength(j);
        } else if (this._noCompression && this._contentLength >= 0) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) getResponse();
            long j2 = this._contentLength;
            if (j2 < 2147483647L) {
                httpServletResponse.setContentLength((int) j2);
            } else {
                httpServletResponse.setHeader("Content-Length", Long.toString(j2));
            }
        }
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void addHeader(String str, String str2) {
        if (Common.CONTENT_LENGTH.equalsIgnoreCase(str)) {
            this._contentLength = Long.parseLong(str2);
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream != null) {
                abstractCompressedStream.setContentLength(this._contentLength);
            }
        } else if (Common.CONTENT_TYPE.equalsIgnoreCase(str)) {
            setContentType(str2);
        } else if (Common.CONTENT_ENCODING.equalsIgnoreCase(str)) {
            super.addHeader(str, str2);
            if (!isCommitted()) {
                noCompression();
            }
        } else {
            super.addHeader(str, str2);
        }
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void flushBuffer() throws IOException {
        PrintWriter printWriter = this._writer;
        if (printWriter != null) {
            printWriter.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.finish();
        } else {
            getResponse().flushBuffer();
        }
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void reset() {
        super.reset();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
        this._noCompression = false;
        this._contentLength = -1L;
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public void resetBuffer() {
        super.resetBuffer();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void sendError(int i, String str) throws IOException {
        resetBuffer();
        super.sendError(i, str);
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void sendError(int i) throws IOException {
        resetBuffer();
        super.sendError(i);
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void sendRedirect(String str) throws IOException {
        resetBuffer();
        super.sendRedirect(str);
    }

    public void noCompression() {
        this._noCompression = true;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            try {
                abstractCompressedStream.doNotCompress();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void finish() throws IOException {
        if (this._writer != null && !this._compressedStream.isClosed()) {
            this._writer.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.finish();
        }
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void setHeader(String str, String str2) {
        if (Common.CONTENT_LENGTH.equalsIgnoreCase(str)) {
            setContentLength(Long.parseLong(str2));
        } else if (Common.CONTENT_TYPE.equalsIgnoreCase(str)) {
            setContentType(str2);
        } else if (Common.CONTENT_ENCODING.equalsIgnoreCase(str)) {
            super.setHeader(str, str2);
            if (!isCommitted()) {
                noCompression();
            }
        } else {
            super.setHeader(str, str2);
        }
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public ServletOutputStream getOutputStream() throws IOException {
        if (this._compressedStream == null) {
            if (getResponse().isCommitted() || this._noCompression) {
                setContentLength(this._contentLength);
                return getResponse().getOutputStream();
            }
            this._compressedStream = newCompressedStream(this._request, (HttpServletResponse) getResponse(), this._contentLength, this._bufferSize, this._minCompressSize);
        } else if (this._writer != null) {
            throw new IllegalStateException("getWriter() called");
        }
        return this._compressedStream;
    }

    @Override // javax.servlet.ServletResponseWrapper, javax.servlet.ServletResponse
    public PrintWriter getWriter() throws IOException {
        if (this._writer == null) {
            if (this._compressedStream != null) {
                throw new IllegalStateException("getOutputStream() called");
            } else if (getResponse().isCommitted() || this._noCompression) {
                setContentLength(this._contentLength);
                return getResponse().getWriter();
            } else {
                this._compressedStream = newCompressedStream(this._request, (HttpServletResponse) getResponse(), this._contentLength, this._bufferSize, this._minCompressSize);
                this._writer = newWriter(this._compressedStream, getCharacterEncoding());
            }
        }
        return this._writer;
    }

    @Override // javax.servlet.http.HttpServletResponseWrapper, javax.servlet.http.HttpServletResponse
    public void setIntHeader(String str, int i) {
        if (Common.CONTENT_LENGTH.equalsIgnoreCase(str)) {
            this._contentLength = i;
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream != null) {
                abstractCompressedStream.setContentLength(this._contentLength);
                return;
            }
            return;
        }
        super.setIntHeader(str, i);
    }

    protected PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }
}
