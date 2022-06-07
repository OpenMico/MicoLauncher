package org.eclipse.jetty.http;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.handler.codec.http.HttpConstants;
import java.io.IOException;
import java.io.InterruptedIOException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.BufferUtil;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HttpGenerator extends AbstractGenerator {
    private static final int CHUNK_SPACE = 12;
    private static final byte[] CONNECTION_;
    private static final byte[] CONNECTION_CLOSE;
    private static final byte[] CONNECTION_KEEP_ALIVE;
    private static final byte[] CONTENT_LENGTH_0;
    private static final byte[] CRLF;
    private static final byte[] LAST_CHUNK;
    private static byte[] SERVER;
    private static final byte[] TRANSFER_ENCODING_CHUNKED;
    private static final Logger LOG = Log.getLogger(HttpGenerator.class);
    private static final Status[] __status = new Status[508];
    protected boolean _bypass = false;
    private boolean _needCRLF = false;
    private boolean _needEOC = false;
    private boolean _bufferChunked = false;

    static {
        int length = HttpVersions.HTTP_1_1_BUFFER.length();
        for (int i = 0; i < __status.length; i++) {
            HttpStatus.Code code = HttpStatus.getCode(i);
            if (code != null) {
                String message = code.getMessage();
                int i2 = length + 5;
                byte[] bArr = new byte[message.length() + i2 + 2];
                HttpVersions.HTTP_1_1_BUFFER.peek(0, bArr, 0, length);
                bArr[length + 0] = 32;
                bArr[length + 1] = (byte) ((i / 100) + 48);
                bArr[length + 2] = (byte) (((i % 100) / 10) + 48);
                bArr[length + 3] = (byte) ((i % 10) + 48);
                bArr[length + 4] = 32;
                for (int i3 = 0; i3 < message.length(); i3++) {
                    bArr[i2 + i3] = (byte) message.charAt(i3);
                }
                bArr[message.length() + i2] = 13;
                bArr[length + 6 + message.length()] = 10;
                __status[i] = new Status();
                __status[i]._reason = new ByteArrayBuffer(bArr, i2, (bArr.length - length) - 7, 0);
                __status[i]._schemeCode = new ByteArrayBuffer(bArr, 0, i2, 0);
                __status[i]._responseLine = new ByteArrayBuffer(bArr, 0, bArr.length, 0);
            }
        }
        LAST_CHUNK = new byte[]{48, 13, 10, 13, 10};
        CONTENT_LENGTH_0 = StringUtil.getBytes("Content-Length: 0\r\n");
        CONNECTION_KEEP_ALIVE = StringUtil.getBytes("Connection: keep-alive\r\n");
        CONNECTION_CLOSE = StringUtil.getBytes("Connection: close\r\n");
        CONNECTION_ = StringUtil.getBytes("Connection: ");
        CRLF = StringUtil.getBytes("\r\n");
        TRANSFER_ENCODING_CHUNKED = StringUtil.getBytes("Transfer-Encoding: chunked\r\n");
        SERVER = StringUtil.getBytes("Server: Jetty(7.0.x)\r\n");
    }

    /* loaded from: classes5.dex */
    public static class Status {
        Buffer _reason;
        Buffer _responseLine;
        Buffer _schemeCode;

        private Status() {
        }
    }

    public static Buffer getReasonBuffer(int i) {
        Status[] statusArr = __status;
        Status status = i < statusArr.length ? statusArr[i] : null;
        if (status != null) {
            return status._reason;
        }
        return null;
    }

    public static void setServerVersion(String str) {
        SERVER = StringUtil.getBytes("Server: Jetty(" + str + ")\r\n");
    }

    public HttpGenerator(Buffers buffers, EndPoint endPoint) {
        super(buffers, endPoint);
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public void reset() {
        if (this._persistent != null && !this._persistent.booleanValue() && this._endp != null && !this._endp.isOutputShutdown()) {
            try {
                this._endp.shutdownOutput();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
        super.reset();
        if (this._buffer != null) {
            this._buffer.clear();
        }
        if (this._header != null) {
            this._header.clear();
        }
        if (this._content != null) {
            this._content = null;
        }
        this._bypass = false;
        this._needCRLF = false;
        this._needEOC = false;
        this._bufferChunked = false;
        this._method = null;
        this._uri = null;
        this._noContent = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a2  */
    @Override // org.eclipse.jetty.http.Generator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addContent(org.eclipse.jetty.io.Buffer r7, boolean r8) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpGenerator.addContent(org.eclipse.jetty.io.Buffer, boolean):void");
    }

    public void sendResponse(Buffer buffer) throws IOException {
        if (this._noContent || this._state != 0 || ((this._content != null && this._content.length() > 0) || this._bufferChunked || this._head)) {
            throw new IllegalStateException();
        }
        this._last = true;
        this._content = buffer;
        this._bypass = true;
        this._state = 3;
        long length = buffer.length();
        this._contentWritten = length;
        this._contentLength = length;
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public int prepareUncheckedAddContent() throws IOException {
        if (this._noContent || this._last || this._state == 4) {
            return -1;
        }
        Buffer buffer = this._content;
        if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
            flushBuffer();
            if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
                throw new IllegalStateException("FULL");
            }
        }
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        this._contentWritten -= this._buffer.length();
        if (this._head) {
            return Integer.MAX_VALUE;
        }
        return this._buffer.space() - (this._contentLength == -2 ? 12 : 0);
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public boolean isBufferFull() {
        return super.isBufferFull() || this._bufferChunked || this._bypass || (this._contentLength == -2 && this._buffer != null && this._buffer.space() < 12);
    }

    public void send1xx(int i) throws IOException {
        if (this._state == 0) {
            if (i < 100 || i > 199) {
                throw new IllegalArgumentException("!1xx");
            }
            Status status = __status[i];
            if (status != null) {
                if (this._header == null) {
                    this._header = this._buffers.getHeader();
                }
                this._header.put(status._responseLine);
                this._header.put(HttpTokens.CRLF);
                while (this._header.length() > 0) {
                    try {
                        int flush = this._endp.flush(this._header);
                        if (flush < 0) {
                            throw new EofException();
                        } else if (flush == 0) {
                            Thread.sleep(100L);
                        }
                    } catch (InterruptedException e) {
                        LOG.debug(e);
                        throw new InterruptedIOException(e.toString());
                    }
                }
                return;
            }
            throw new IllegalArgumentException(i + "?");
        }
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public boolean isRequest() {
        return this._method != null;
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public boolean isResponse() {
        return this._method == null;
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public void completeHeader(HttpFields httpFields, boolean z) throws IOException {
        boolean z2;
        boolean z3;
        boolean z4;
        HttpFields.Field field;
        HttpFields.Field field2;
        long j;
        if (this._state == 0) {
            if (isResponse() && this._status == 0) {
                throw new EofException();
            } else if (!this._last || z) {
                this._last |= z;
                if (this._header == null) {
                    this._header = this._buffers.getHeader();
                }
                try {
                    int i = 48;
                    StringBuilder sb = null;
                    boolean z5 = false;
                    if (isRequest()) {
                        this._persistent = true;
                        if (this._version == 9) {
                            this._contentLength = 0L;
                            this._header.put(this._method);
                            this._header.put((byte) 32);
                            this._header.put(this._uri.getBytes("UTF-8"));
                            this._header.put(HttpTokens.CRLF);
                            this._state = 3;
                            this._noContent = true;
                            return;
                        }
                        this._header.put(this._method);
                        this._header.put((byte) 32);
                        this._header.put(this._uri.getBytes("UTF-8"));
                        this._header.put((byte) 32);
                        this._header.put(this._version == 10 ? HttpVersions.HTTP_1_0_BUFFER : HttpVersions.HTTP_1_1_BUFFER);
                        this._header.put(HttpTokens.CRLF);
                    } else if (this._version == 9) {
                        this._persistent = false;
                        this._contentLength = -1L;
                        this._state = 2;
                        return;
                    } else {
                        if (this._persistent == null) {
                            this._persistent = Boolean.valueOf(this._version > 10);
                        }
                        Status status = this._status < __status.length ? __status[this._status] : null;
                        if (status == null) {
                            this._header.put(HttpVersions.HTTP_1_1_BUFFER);
                            this._header.put((byte) 32);
                            this._header.put((byte) ((this._status / 100) + 48));
                            this._header.put((byte) (((this._status % 100) / 10) + 48));
                            this._header.put((byte) ((this._status % 10) + 48));
                            this._header.put((byte) 32);
                            if (this._reason == null) {
                                this._header.put((byte) ((this._status / 100) + 48));
                                this._header.put((byte) (((this._status % 100) / 10) + 48));
                                this._header.put((byte) ((this._status % 10) + 48));
                            } else {
                                this._header.put(this._reason);
                            }
                            this._header.put(HttpTokens.CRLF);
                        } else if (this._reason == null) {
                            this._header.put(status._responseLine);
                        } else {
                            this._header.put(status._schemeCode);
                            this._header.put(this._reason);
                            this._header.put(HttpTokens.CRLF);
                        }
                        if (this._status < 200 && this._status >= 100) {
                            this._noContent = true;
                            this._content = null;
                            if (this._buffer != null) {
                                this._buffer.clear();
                            }
                            if (this._status != 101) {
                                this._header.put(HttpTokens.CRLF);
                                this._state = 2;
                                return;
                            }
                        } else if (this._status == 204 || this._status == 304) {
                            this._noContent = true;
                            this._content = null;
                            if (this._buffer != null) {
                                this._buffer.clear();
                            }
                        }
                    }
                    if (this._status >= 200 && this._date != null) {
                        this._header.put(HttpHeaders.DATE_BUFFER);
                        this._header.put((byte) 58);
                        this._header.put((byte) 32);
                        this._header.put(this._date);
                        this._header.put(CRLF);
                    }
                    if (httpFields != null) {
                        int size = httpFields.size();
                        HttpFields.Field field3 = null;
                        HttpFields.Field field4 = null;
                        int i2 = 0;
                        z4 = false;
                        z5 = false;
                        z3 = false;
                        z2 = false;
                        while (i2 < size) {
                            HttpFields.Field field5 = httpFields.getField(i2);
                            if (field5 != null) {
                                int nameOrdinal = field5.getNameOrdinal();
                                if (nameOrdinal == 1) {
                                    if (isRequest()) {
                                        field5.putTo(this._header);
                                    }
                                    int valueOrdinal = field5.getValueOrdinal();
                                    if (valueOrdinal != -1) {
                                        if (valueOrdinal != 1) {
                                            if (valueOrdinal != 5) {
                                                if (valueOrdinal != 11) {
                                                    if (sb == null) {
                                                        sb = new StringBuilder();
                                                    } else {
                                                        sb.append(io.netty.util.internal.StringUtil.COMMA);
                                                    }
                                                    sb.append(field5.getValue());
                                                } else if (isResponse()) {
                                                    field5.putTo(this._header);
                                                }
                                            } else if (this._version == 10) {
                                                if (isResponse()) {
                                                    this._persistent = true;
                                                }
                                                z5 = true;
                                            }
                                        }
                                        if (isResponse()) {
                                            this._persistent = false;
                                        }
                                        if (!this._persistent.booleanValue() && isResponse() && this._contentLength == -3) {
                                            this._contentLength = -1L;
                                        }
                                        z3 = true;
                                    } else {
                                        String[] split = field5.getValue().split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                                        for (int i3 = 0; split != null && i3 < split.length; i3++) {
                                            BufferCache.CachedBuffer cachedBuffer = HttpHeaderValues.CACHE.get(split[i3].trim());
                                            if (cachedBuffer != null) {
                                                int ordinal = cachedBuffer.getOrdinal();
                                                if (ordinal == 1) {
                                                    if (isResponse()) {
                                                        this._persistent = false;
                                                    }
                                                    if (!this._persistent.booleanValue() && isResponse() && this._contentLength == -3) {
                                                        this._contentLength = -1L;
                                                    }
                                                    z3 = true;
                                                    z5 = false;
                                                } else if (ordinal != 5) {
                                                    if (sb == null) {
                                                        sb = new StringBuilder();
                                                    } else {
                                                        sb.append(io.netty.util.internal.StringUtil.COMMA);
                                                    }
                                                    sb.append(split[i3]);
                                                } else if (this._version == 10) {
                                                    if (isResponse()) {
                                                        this._persistent = true;
                                                    }
                                                    z5 = true;
                                                }
                                            } else {
                                                if (sb == null) {
                                                    sb = new StringBuilder();
                                                } else {
                                                    sb.append(io.netty.util.internal.StringUtil.COMMA);
                                                }
                                                sb.append(split[i3]);
                                            }
                                        }
                                    }
                                } else if (nameOrdinal != 5) {
                                    if (nameOrdinal == 12) {
                                        this._contentLength = field5.getLongValue();
                                        if (this._contentLength >= this._contentWritten && (!this._last || this._contentLength == this._contentWritten)) {
                                            field3 = field5;
                                            field5.putTo(this._header);
                                        }
                                        field3 = null;
                                        field5.putTo(this._header);
                                    } else if (nameOrdinal == 16) {
                                        if (BufferUtil.isPrefix(MimeTypes.MULTIPART_BYTERANGES_BUFFER, field5.getValueBuffer())) {
                                            this._contentLength = -4L;
                                        }
                                        field5.putTo(this._header);
                                        z4 = true;
                                    } else if (nameOrdinal != i) {
                                        field5.putTo(this._header);
                                    } else if (getSendServerVersion()) {
                                        field5.putTo(this._header);
                                        z2 = true;
                                    }
                                } else if (this._version == 11) {
                                    field4 = field5;
                                }
                            }
                            i2++;
                            i = 48;
                        }
                        field = field3;
                        field2 = field4;
                    } else {
                        z4 = false;
                        z5 = false;
                        z3 = false;
                        z2 = false;
                        field2 = null;
                        sb = null;
                        field = null;
                    }
                    switch ((int) this._contentLength) {
                        case -3:
                            if (this._contentWritten == 0 && isResponse() && (this._status < 200 || this._status == 204 || this._status == 304)) {
                                this._contentLength = 0L;
                                break;
                            } else if (this._last) {
                                this._contentLength = this._contentWritten;
                                if (field == null && ((isResponse() || this._contentLength > 0 || z4) && !this._noContent)) {
                                    this._header.put(HttpHeaders.CONTENT_LENGTH_BUFFER);
                                    this._header.put((byte) 58);
                                    this._header.put((byte) 32);
                                    BufferUtil.putDecLong(this._header, this._contentLength);
                                    this._header.put(HttpTokens.CRLF);
                                    break;
                                }
                            } else {
                                if (this._persistent.booleanValue() && this._version >= 11) {
                                    j = -2;
                                    this._contentLength = j;
                                    if (isRequest() && this._contentLength == -1) {
                                        this._contentLength = 0L;
                                        this._noContent = true;
                                        break;
                                    }
                                }
                                j = -1;
                                this._contentLength = j;
                                if (isRequest()) {
                                    this._contentLength = 0L;
                                    this._noContent = true;
                                }
                            }
                            break;
                        case -1:
                            this._persistent = Boolean.valueOf(isRequest());
                            break;
                        case 0:
                            if (field == null && isResponse() && this._status >= 200 && this._status != 204 && this._status != 304) {
                                this._header.put(CONTENT_LENGTH_0);
                                break;
                            }
                            break;
                    }
                    if (this._contentLength == -2) {
                        if (field2 == null || 2 == field2.getValueOrdinal()) {
                            this._header.put(TRANSFER_ENCODING_CHUNKED);
                        } else if (field2.getValue().endsWith("chunked")) {
                            field2.putTo(this._header);
                        } else {
                            throw new IllegalArgumentException("BAD TE");
                        }
                    }
                    if (this._contentLength == -1) {
                        this._persistent = false;
                    }
                    if (isResponse()) {
                        if (!this._persistent.booleanValue() && (z3 || this._version > 10)) {
                            this._header.put(CONNECTION_CLOSE);
                            if (sb != null) {
                                this._header.setPutIndex(this._header.putIndex() - 2);
                                this._header.put(HttpConstants.COMMA);
                                this._header.put(sb.toString().getBytes());
                                this._header.put(CRLF);
                            }
                        } else if (z5) {
                            this._header.put(CONNECTION_KEEP_ALIVE);
                            if (sb != null) {
                                this._header.setPutIndex(this._header.putIndex() - 2);
                                this._header.put(HttpConstants.COMMA);
                                this._header.put(sb.toString().getBytes());
                                this._header.put(CRLF);
                            }
                        } else if (sb != null) {
                            this._header.put(CONNECTION_);
                            this._header.put(sb.toString().getBytes());
                            this._header.put(CRLF);
                        }
                    }
                    if (!z2 && this._status > 199 && getSendServerVersion()) {
                        this._header.put(SERVER);
                    }
                    this._header.put(HttpTokens.CRLF);
                    this._state = 2;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new RuntimeException("Header>" + this._header.capacity(), e);
                }
            } else {
                throw new IllegalStateException("last?");
            }
        }
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public void complete() throws IOException {
        if (this._state != 4) {
            super.complete();
            if (this._state < 3) {
                this._state = 3;
                if (this._contentLength == -2) {
                    this._needEOC = true;
                }
            }
            flushBuffer();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0123, code lost:
        return r0;
     */
    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int flushBuffer() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpGenerator.flushBuffer():int");
    }

    private int flushMask() {
        int i = 0;
        int i2 = ((this._header == null || this._header.length() <= 0) ? 0 : 4) | ((this._buffer == null || this._buffer.length() <= 0) ? 0 : 2);
        if (this._bypass && this._content != null && this._content.length() > 0) {
            i = 1;
        }
        return i2 | i;
    }

    private void prepareBuffers() {
        int length;
        if (!this._bufferChunked) {
            if (!this._bypass && this._content != null && this._content.length() > 0 && this._buffer != null && this._buffer.space() > 0) {
                this._content.skip(this._buffer.put(this._content));
                if (this._content.length() == 0) {
                    this._content = null;
                }
            }
            if (this._contentLength == -2) {
                if (this._bypass && ((this._buffer == null || this._buffer.length() == 0) && this._content != null)) {
                    int length2 = this._content.length();
                    this._bufferChunked = true;
                    if (this._header == null) {
                        this._header = this._buffers.getHeader();
                    }
                    if (this._needCRLF) {
                        if (this._header.length() <= 0) {
                            this._header.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        } else {
                            throw new IllegalStateException("EOC");
                        }
                    }
                    BufferUtil.putHexInt(this._header, length2);
                    this._header.put(HttpTokens.CRLF);
                    this._needCRLF = true;
                } else if (this._buffer != null && (length = this._buffer.length()) > 0) {
                    this._bufferChunked = true;
                    if (this._buffer.getIndex() == 12) {
                        this._buffer.poke(this._buffer.getIndex() - 2, HttpTokens.CRLF, 0, 2);
                        this._buffer.setGetIndex(this._buffer.getIndex() - 2);
                        BufferUtil.prependHexInt(this._buffer, length);
                        if (this._needCRLF) {
                            this._buffer.poke(this._buffer.getIndex() - 2, HttpTokens.CRLF, 0, 2);
                            this._buffer.setGetIndex(this._buffer.getIndex() - 2);
                            this._needCRLF = false;
                        }
                    } else {
                        if (this._header == null) {
                            this._header = this._buffers.getHeader();
                        }
                        if (this._needCRLF) {
                            if (this._header.length() <= 0) {
                                this._header.put(HttpTokens.CRLF);
                                this._needCRLF = false;
                            } else {
                                throw new IllegalStateException("EOC");
                            }
                        }
                        BufferUtil.putHexInt(this._header, length);
                        this._header.put(HttpTokens.CRLF);
                    }
                    if (this._buffer.space() >= 2) {
                        this._buffer.put(HttpTokens.CRLF);
                    } else {
                        this._needCRLF = true;
                    }
                }
                if (this._needEOC && (this._content == null || this._content.length() == 0)) {
                    if (this._needCRLF) {
                        if (this._buffer == null && this._header.space() >= 2) {
                            this._header.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        } else if (this._buffer != null && this._buffer.space() >= 2) {
                            this._buffer.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        }
                    }
                    if (!this._needCRLF && this._needEOC) {
                        if (this._buffer == null && this._header.space() >= LAST_CHUNK.length) {
                            if (!this._head) {
                                this._header.put(LAST_CHUNK);
                                this._bufferChunked = true;
                            }
                            this._needEOC = false;
                        } else if (this._buffer != null && this._buffer.space() >= LAST_CHUNK.length) {
                            if (!this._head) {
                                this._buffer.put(LAST_CHUNK);
                                this._bufferChunked = true;
                            }
                            this._needEOC = false;
                        }
                    }
                }
            }
        }
        if (this._content != null && this._content.length() == 0) {
            this._content = null;
        }
    }

    public int getBytesBuffered() {
        int i = 0;
        int length = (this._header == null ? 0 : this._header.length()) + (this._buffer == null ? 0 : this._buffer.length());
        if (this._content != null) {
            i = this._content.length();
        }
        return length + i;
    }

    public boolean isEmpty() {
        return (this._header == null || this._header.length() == 0) && (this._buffer == null || this._buffer.length() == 0) && (this._content == null || this._content.length() == 0);
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = Integer.valueOf(this._state);
        int i = -1;
        objArr[2] = Integer.valueOf(this._header == null ? -1 : this._header.length());
        objArr[3] = Integer.valueOf(this._buffer == null ? -1 : this._buffer.length());
        if (this._content != null) {
            i = this._content.length();
        }
        objArr[4] = Integer.valueOf(i);
        return String.format("%s{s=%d,h=%d,b=%d,c=%d}", objArr);
    }
}
