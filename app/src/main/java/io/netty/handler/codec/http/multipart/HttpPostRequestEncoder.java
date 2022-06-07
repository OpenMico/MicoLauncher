package io.netty.handler.codec.http.multipart;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class HttpPostRequestEncoder implements ChunkedInput<HttpContent> {
    private static final Map<Pattern, String> d = new HashMap();
    final List<InterfaceHttpData> a;
    String b;
    String c;
    private final HttpDataFactory e;
    private final HttpRequest f;
    private final Charset g;
    private boolean h;
    private final List<InterfaceHttpData> i;
    private final boolean j;
    private boolean k;
    private final EncoderMode l;
    private boolean m;
    private boolean n;
    private FileUpload o;
    private boolean p;
    private long q;
    private long r;
    private ListIterator<InterfaceHttpData> s;
    private ByteBuf t;
    private InterfaceHttpData u;
    private boolean v;

    /* loaded from: classes4.dex */
    public enum EncoderMode {
        RFC1738,
        RFC3986,
        HTML5
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public void close() throws Exception {
    }

    static {
        d.put(Pattern.compile("\\*"), "%2A");
        d.put(Pattern.compile("\\+"), "%20");
        d.put(Pattern.compile("%7E"), Constants.WAVE_SEPARATOR);
    }

    public HttpPostRequestEncoder(HttpRequest httpRequest, boolean z) throws ErrorDataEncoderException {
        this(new DefaultHttpDataFactory(16384L), httpRequest, z, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, boolean z) throws ErrorDataEncoderException {
        this(httpDataFactory, httpRequest, z, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, boolean z, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
        this.v = true;
        if (httpDataFactory == null) {
            throw new NullPointerException("factory");
        } else if (httpRequest == null) {
            throw new NullPointerException("request");
        } else if (charset != null) {
            HttpMethod method = httpRequest.method();
            if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH) || method.equals(HttpMethod.OPTIONS)) {
                this.f = httpRequest;
                this.g = charset;
                this.e = httpDataFactory;
                this.i = new ArrayList();
                this.m = false;
                this.n = false;
                this.j = z;
                this.a = new ArrayList();
                this.l = encoderMode;
                if (this.j) {
                    a();
                    return;
                }
                return;
            }
            throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");
        } else {
            throw new NullPointerException("charset");
        }
    }

    public void cleanFiles() {
        this.e.cleanRequestHttpData(this.f);
    }

    public boolean isMultipart() {
        return this.j;
    }

    private void a() {
        this.b = c();
    }

    private void b() {
        this.c = c();
    }

    private static String c() {
        return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase();
    }

    public List<InterfaceHttpData> getBodyListAttributes() {
        return this.i;
    }

    public void setBodyHttpDatas(List<InterfaceHttpData> list) throws ErrorDataEncoderException {
        if (list != null) {
            this.q = 0L;
            this.i.clear();
            this.o = null;
            this.p = false;
            this.a.clear();
            for (InterfaceHttpData interfaceHttpData : list) {
                addBodyHttpData(interfaceHttpData);
            }
            return;
        }
        throw new NullPointerException("datas");
    }

    public void addBodyAttribute(String str, String str2) throws ErrorDataEncoderException {
        if (str != null) {
            if (str2 == null) {
                str2 = "";
            }
            addBodyHttpData(this.e.createAttribute(this.f, str, str2));
            return;
        }
        throw new NullPointerException("name");
    }

    public void addBodyFileUpload(String str, File file, String str2, boolean z) throws ErrorDataEncoderException {
        if (str == null) {
            throw new NullPointerException("name");
        } else if (file != null) {
            String str3 = null;
            String str4 = str2 == null ? z ? "text/plain" : "application/octet-stream" : str2;
            if (!z) {
                str3 = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
            }
            FileUpload createFileUpload = this.e.createFileUpload(this.f, str, file.getName(), str4, str3, null, file.length());
            try {
                createFileUpload.setContent(file);
                addBodyHttpData(createFileUpload);
            } catch (IOException e) {
                throw new ErrorDataEncoderException(e);
            }
        } else {
            throw new NullPointerException("file");
        }
    }

    public void addBodyFileUploads(String str, File[] fileArr, String[] strArr, boolean[] zArr) throws ErrorDataEncoderException {
        if (fileArr.length == strArr.length || fileArr.length == zArr.length) {
            for (int i = 0; i < fileArr.length; i++) {
                addBodyFileUpload(str, fileArr[i], strArr[i], zArr[i]);
            }
            return;
        }
        throw new NullPointerException("Different array length");
    }

    public void addBodyHttpData(InterfaceHttpData interfaceHttpData) throws ErrorDataEncoderException {
        FileUpload fileUpload;
        if (this.k) {
            throw new ErrorDataEncoderException("Cannot add value once finalized");
        } else if (interfaceHttpData != null) {
            this.i.add(interfaceHttpData);
            boolean z = true;
            if (!this.j) {
                if (interfaceHttpData instanceof Attribute) {
                    Attribute attribute = (Attribute) interfaceHttpData;
                    try {
                        Attribute createAttribute = this.e.createAttribute(this.f, a(attribute.getName(), this.g), a(attribute.getValue(), this.g));
                        this.a.add(createAttribute);
                        this.q += createAttribute.getName().length() + 1 + createAttribute.length() + 1;
                    } catch (IOException e) {
                        throw new ErrorDataEncoderException(e);
                    }
                } else if (interfaceHttpData instanceof FileUpload) {
                    FileUpload fileUpload2 = (FileUpload) interfaceHttpData;
                    Attribute createAttribute2 = this.e.createAttribute(this.f, a(fileUpload2.getName(), this.g), a(fileUpload2.getFilename(), this.g));
                    this.a.add(createAttribute2);
                    this.q += createAttribute2.getName().length() + 1 + createAttribute2.length() + 1;
                }
            } else if (interfaceHttpData instanceof Attribute) {
                if (this.p) {
                    b bVar = new b(this.g);
                    bVar.a("\r\n--" + this.c + "--");
                    this.a.add(bVar);
                    this.c = null;
                    this.o = null;
                    this.p = false;
                }
                b bVar2 = new b(this.g);
                if (!this.a.isEmpty()) {
                    bVar2.a("\r\n");
                }
                bVar2.a("--" + this.b + "\r\n");
                Attribute attribute2 = (Attribute) interfaceHttpData;
                bVar2.a(((Object) HttpHeaderNames.CONTENT_DISPOSITION) + ": " + ((Object) HttpHeaderValues.FORM_DATA) + "; " + ((Object) HttpHeaderValues.NAME) + "=\"" + attribute2.getName() + "\"\r\n");
                StringBuilder sb = new StringBuilder();
                sb.append((Object) HttpHeaderNames.CONTENT_LENGTH);
                sb.append(": ");
                sb.append(attribute2.length());
                sb.append("\r\n");
                bVar2.a(sb.toString());
                Charset charset = attribute2.getCharset();
                if (charset != null) {
                    bVar2.a(((Object) HttpHeaderNames.CONTENT_TYPE) + ": text/plain; " + ((Object) HttpHeaderValues.CHARSET) + '=' + charset.name() + "\r\n");
                }
                bVar2.a("\r\n");
                this.a.add(bVar2);
                this.a.add(interfaceHttpData);
                this.q += attribute2.length() + bVar2.a();
            } else if (interfaceHttpData instanceof FileUpload) {
                FileUpload fileUpload3 = (FileUpload) interfaceHttpData;
                b bVar3 = new b(this.g);
                if (!this.a.isEmpty()) {
                    bVar3.a("\r\n");
                }
                if (this.p) {
                    FileUpload fileUpload4 = this.o;
                    if (fileUpload4 == null || !fileUpload4.getName().equals(fileUpload3.getName())) {
                        bVar3.a("--" + this.c + "--");
                        this.a.add(bVar3);
                        this.c = null;
                        bVar3 = new b(this.g);
                        bVar3.a("\r\n");
                        this.o = fileUpload3;
                        this.p = false;
                        z = false;
                    }
                } else if (this.l == EncoderMode.HTML5 || (fileUpload = this.o) == null || !fileUpload.getName().equals(fileUpload3.getName())) {
                    this.o = fileUpload3;
                    this.p = false;
                    z = false;
                } else {
                    b();
                    List<InterfaceHttpData> list = this.a;
                    b bVar4 = (b) list.get(list.size() - 2);
                    this.q -= bVar4.a();
                    StringBuilder sb2 = new StringBuilder(this.b.length() + 139 + (this.c.length() * 2) + fileUpload3.getFilename().length() + fileUpload3.getName().length());
                    sb2.append("--");
                    sb2.append(this.b);
                    sb2.append("\r\n");
                    sb2.append((CharSequence) HttpHeaderNames.CONTENT_DISPOSITION);
                    sb2.append(": ");
                    sb2.append((CharSequence) HttpHeaderValues.FORM_DATA);
                    sb2.append("; ");
                    sb2.append((CharSequence) HttpHeaderValues.NAME);
                    sb2.append("=\"");
                    sb2.append(fileUpload3.getName());
                    sb2.append("\"\r\n");
                    sb2.append((CharSequence) HttpHeaderNames.CONTENT_TYPE);
                    sb2.append(": ");
                    sb2.append((CharSequence) HttpHeaderValues.MULTIPART_MIXED);
                    sb2.append("; ");
                    sb2.append((CharSequence) HttpHeaderValues.BOUNDARY);
                    sb2.append('=');
                    sb2.append(this.c);
                    sb2.append("\r\n\r\n");
                    sb2.append("--");
                    sb2.append(this.c);
                    sb2.append("\r\n");
                    sb2.append((CharSequence) HttpHeaderNames.CONTENT_DISPOSITION);
                    sb2.append(": ");
                    sb2.append((CharSequence) HttpHeaderValues.ATTACHMENT);
                    sb2.append("; ");
                    sb2.append((CharSequence) HttpHeaderValues.FILENAME);
                    sb2.append("=\"");
                    sb2.append(fileUpload3.getFilename());
                    sb2.append("\"\r\n");
                    bVar4.a(sb2.toString(), 1);
                    bVar4.a("", 2);
                    this.q += bVar4.a();
                    this.p = true;
                }
                if (z) {
                    bVar3.a("--" + this.c + "\r\n");
                    bVar3.a(((Object) HttpHeaderNames.CONTENT_DISPOSITION) + ": " + ((Object) HttpHeaderValues.ATTACHMENT) + "; " + ((Object) HttpHeaderValues.FILENAME) + "=\"" + fileUpload3.getFilename() + "\"\r\n");
                } else {
                    bVar3.a("--" + this.b + "\r\n");
                    bVar3.a(((Object) HttpHeaderNames.CONTENT_DISPOSITION) + ": " + ((Object) HttpHeaderValues.FORM_DATA) + "; " + ((Object) HttpHeaderValues.NAME) + "=\"" + fileUpload3.getName() + "\"; " + ((Object) HttpHeaderValues.FILENAME) + "=\"" + fileUpload3.getFilename() + "\"\r\n");
                }
                bVar3.a(((Object) HttpHeaderNames.CONTENT_LENGTH) + ": " + fileUpload3.length() + "\r\n");
                StringBuilder sb3 = new StringBuilder();
                sb3.append((Object) HttpHeaderNames.CONTENT_TYPE);
                sb3.append(": ");
                sb3.append(fileUpload3.getContentType());
                bVar3.a(sb3.toString());
                String contentTransferEncoding = fileUpload3.getContentTransferEncoding();
                if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                    bVar3.a("\r\n" + ((Object) HttpHeaderNames.CONTENT_TRANSFER_ENCODING) + ": " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
                } else if (fileUpload3.getCharset() != null) {
                    bVar3.a("; " + ((Object) HttpHeaderValues.CHARSET) + '=' + fileUpload3.getCharset().name() + "\r\n\r\n");
                } else {
                    bVar3.a("\r\n\r\n");
                }
                this.a.add(bVar3);
                this.a.add(interfaceHttpData);
                this.q += fileUpload3.length() + bVar3.a();
            }
        } else {
            throw new NullPointerException("data");
        }
    }

    public HttpRequest finalizeRequest() throws ErrorDataEncoderException {
        if (!this.k) {
            if (this.j) {
                b bVar = new b(this.g);
                if (this.p) {
                    bVar.a("\r\n--" + this.c + "--");
                }
                bVar.a("\r\n--" + this.b + "--\r\n");
                this.a.add(bVar);
                this.c = null;
                this.o = null;
                this.p = false;
                this.q += bVar.a();
            }
            this.k = true;
            HttpHeaders headers = this.f.headers();
            List<String> all = headers.getAll(HttpHeaderNames.CONTENT_TYPE);
            List<String> all2 = headers.getAll(HttpHeaderNames.TRANSFER_ENCODING);
            if (all != null) {
                headers.remove(HttpHeaderNames.CONTENT_TYPE);
                for (String str : all) {
                    String lowerCase = str.toLowerCase();
                    if (!lowerCase.startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString()) && !lowerCase.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())) {
                        headers.add(HttpHeaderNames.CONTENT_TYPE, str);
                    }
                }
            }
            if (this.j) {
                headers.add(HttpHeaderNames.CONTENT_TYPE, ((Object) HttpHeaderValues.MULTIPART_FORM_DATA) + "; " + ((Object) HttpHeaderValues.BOUNDARY) + '=' + this.b);
            } else {
                headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
            }
            long j = this.q;
            if (this.j) {
                this.s = this.a.listIterator();
            } else {
                j--;
                this.s = this.a.listIterator();
            }
            headers.set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(j));
            if (j > 8096 || this.j) {
                this.h = true;
                if (all2 != null) {
                    headers.remove(HttpHeaderNames.TRANSFER_ENCODING);
                    for (String str2 : all2) {
                        if (!HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(str2)) {
                            headers.add(HttpHeaderNames.TRANSFER_ENCODING, str2);
                        }
                    }
                }
                HttpUtil.setTransferEncodingChunked(this.f, true);
                return new b(this.f);
            }
            HttpContent e = e();
            HttpRequest httpRequest = this.f;
            if (!(httpRequest instanceof FullHttpRequest)) {
                return new a(httpRequest, e);
            }
            FullHttpRequest fullHttpRequest = (FullHttpRequest) httpRequest;
            ByteBuf content = e.content();
            if (fullHttpRequest.content() != content) {
                fullHttpRequest.content().clear().writeBytes(content);
                content.release();
            }
            return fullHttpRequest;
        }
        throw new ErrorDataEncoderException("Header already encoded");
    }

    public boolean isChunked() {
        return this.h;
    }

    private String a(String str, Charset charset) throws ErrorDataEncoderException {
        if (str == null) {
            return "";
        }
        try {
            String encode = URLEncoder.encode(str, charset.name());
            if (this.l == EncoderMode.RFC3986) {
                for (Map.Entry<Pattern, String> entry : d.entrySet()) {
                    encode = entry.getKey().matcher(encode).replaceAll(entry.getValue());
                }
            }
            return encode;
        } catch (UnsupportedEncodingException e) {
            throw new ErrorDataEncoderException(charset.name(), e);
        }
    }

    private ByteBuf d() {
        if (this.t.readableBytes() > 8096) {
            ByteBuf byteBuf = this.t;
            ByteBuf slice = byteBuf.slice(byteBuf.readerIndex(), 8096);
            this.t.skipBytes(8096);
            return slice;
        }
        ByteBuf byteBuf2 = this.t;
        this.t = null;
        return byteBuf2;
    }

    private HttpContent a(int i) throws ErrorDataEncoderException {
        ByteBuf byteBuf;
        InterfaceHttpData interfaceHttpData = this.u;
        if (interfaceHttpData == null) {
            return null;
        }
        if (interfaceHttpData instanceof b) {
            byteBuf = ((b) interfaceHttpData).b();
            this.u = null;
        } else {
            if (interfaceHttpData instanceof Attribute) {
                try {
                    byteBuf = ((Attribute) interfaceHttpData).getChunk(i);
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            } else {
                try {
                    byteBuf = ((HttpData) interfaceHttpData).getChunk(i);
                } catch (IOException e2) {
                    throw new ErrorDataEncoderException(e2);
                }
            }
            if (byteBuf.capacity() == 0) {
                this.u = null;
                return null;
            }
        }
        ByteBuf byteBuf2 = this.t;
        if (byteBuf2 == null) {
            this.t = byteBuf;
        } else {
            this.t = Unpooled.wrappedBuffer(byteBuf2, byteBuf);
        }
        if (this.t.readableBytes() >= 8096) {
            return new DefaultHttpContent(d());
        }
        this.u = null;
        return null;
    }

    private HttpContent b(int i) throws ErrorDataEncoderException {
        ByteBuf byteBuf;
        InterfaceHttpData interfaceHttpData = this.u;
        if (interfaceHttpData == null) {
            return null;
        }
        if (this.v) {
            ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(interfaceHttpData.getName().getBytes());
            this.v = false;
            ByteBuf byteBuf2 = this.t;
            if (byteBuf2 == null) {
                this.t = Unpooled.wrappedBuffer(wrappedBuffer, Unpooled.wrappedBuffer("=".getBytes()));
                i -= wrappedBuffer.readableBytes() + 1;
            } else {
                this.t = Unpooled.wrappedBuffer(byteBuf2, wrappedBuffer, Unpooled.wrappedBuffer("=".getBytes()));
                i -= wrappedBuffer.readableBytes() + 1;
            }
            if (this.t.readableBytes() >= 8096) {
                return new DefaultHttpContent(d());
            }
        }
        try {
            ByteBuf chunk = ((HttpData) this.u).getChunk(i);
            if (chunk.readableBytes() < i) {
                this.v = true;
                byteBuf = this.s.hasNext() ? Unpooled.wrappedBuffer(MusicGroupListActivity.SPECIAL_SYMBOL.getBytes()) : null;
            } else {
                byteBuf = null;
            }
            if (chunk.capacity() == 0) {
                this.u = null;
                ByteBuf byteBuf3 = this.t;
                if (byteBuf3 == null) {
                    this.t = byteBuf;
                } else if (byteBuf != null) {
                    this.t = Unpooled.wrappedBuffer(byteBuf3, byteBuf);
                }
                if (this.t.readableBytes() >= 8096) {
                    return new DefaultHttpContent(d());
                }
                return null;
            }
            ByteBuf byteBuf4 = this.t;
            if (byteBuf4 == null) {
                if (byteBuf != null) {
                    this.t = Unpooled.wrappedBuffer(chunk, byteBuf);
                } else {
                    this.t = chunk;
                }
            } else if (byteBuf != null) {
                this.t = Unpooled.wrappedBuffer(byteBuf4, chunk, byteBuf);
            } else {
                this.t = Unpooled.wrappedBuffer(byteBuf4, chunk);
            }
            if (this.t.readableBytes() >= 8096) {
                return new DefaultHttpContent(d());
            }
            this.u = null;
            this.v = true;
            return null;
        } catch (IOException e) {
            throw new ErrorDataEncoderException(e);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.stream.ChunkedInput
    public HttpContent readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        if (this.n) {
            return null;
        }
        HttpContent e = e();
        this.r += e.content().readableBytes();
        return e;
    }

    private HttpContent e() throws ErrorDataEncoderException {
        HttpContent httpContent;
        if (this.m) {
            this.n = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        ByteBuf byteBuf = this.t;
        int i = 8096;
        if (byteBuf != null) {
            i = 8096 - byteBuf.readableBytes();
        }
        if (i <= 0) {
            return new DefaultHttpContent(d());
        }
        if (this.u != null) {
            if (this.j) {
                HttpContent a2 = a(i);
                if (a2 != null) {
                    return a2;
                }
            } else {
                HttpContent b2 = b(i);
                if (b2 != null) {
                    return b2;
                }
            }
            i = 8096 - this.t.readableBytes();
        }
        if (!this.s.hasNext()) {
            this.m = true;
            ByteBuf byteBuf2 = this.t;
            this.t = null;
            return new DefaultHttpContent(byteBuf2);
        }
        while (i > 0 && this.s.hasNext()) {
            this.u = this.s.next();
            if (this.j) {
                httpContent = a(i);
            } else {
                httpContent = b(i);
            }
            if (httpContent != null) {
                return httpContent;
            }
            i = 8096 - this.t.readableBytes();
        }
        this.m = true;
        ByteBuf byteBuf3 = this.t;
        if (byteBuf3 == null) {
            this.n = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        this.t = null;
        return new DefaultHttpContent(byteBuf3);
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public boolean isEndOfInput() throws Exception {
        return this.n;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long length() {
        return this.j ? this.q : this.q - 1;
    }

    @Override // io.netty.handler.stream.ChunkedInput
    public long progress() {
        return this.r;
    }

    /* loaded from: classes4.dex */
    public static class ErrorDataEncoderException extends Exception {
        private static final long serialVersionUID = 5020247425493164465L;

        public ErrorDataEncoderException() {
        }

        public ErrorDataEncoderException(String str) {
            super(str);
        }

        public ErrorDataEncoderException(Throwable th) {
            super(th);
        }

        public ErrorDataEncoderException(String str, Throwable th) {
            super(str, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class b implements HttpRequest {
        private final HttpRequest a;

        b(HttpRequest httpRequest) {
            this.a = httpRequest;
        }

        @Override // io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
            this.a.setProtocolVersion(httpVersion);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public HttpRequest setMethod(HttpMethod httpMethod) {
            this.a.setMethod(httpMethod);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public HttpRequest setUri(String str) {
            this.a.setUri(str);
            return this;
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public HttpMethod getMethod() {
            return this.a.method();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public HttpMethod method() {
            return this.a.method();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public String getUri() {
            return this.a.uri();
        }

        @Override // io.netty.handler.codec.http.HttpRequest
        public String uri() {
            return this.a.uri();
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpVersion getProtocolVersion() {
            return this.a.protocolVersion();
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpVersion protocolVersion() {
            return this.a.protocolVersion();
        }

        @Override // io.netty.handler.codec.http.HttpMessage
        public HttpHeaders headers() {
            return this.a.headers();
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public DecoderResult decoderResult() {
            return this.a.decoderResult();
        }

        @Override // io.netty.handler.codec.http.HttpObject
        @Deprecated
        public DecoderResult getDecoderResult() {
            return this.a.getDecoderResult();
        }

        @Override // io.netty.handler.codec.DecoderResultProvider
        public void setDecoderResult(DecoderResult decoderResult) {
            this.a.setDecoderResult(decoderResult);
        }
    }

    /* loaded from: classes4.dex */
    private static final class a extends b implements FullHttpRequest {
        private final HttpContent a;

        private a(HttpRequest httpRequest, HttpContent httpContent) {
            super(httpRequest);
            this.a = httpContent;
        }

        @Override // io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.b, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        @Override // io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.b, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setMethod(HttpMethod httpMethod) {
            super.setMethod(httpMethod);
            return this;
        }

        @Override // io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.b, io.netty.handler.codec.http.HttpRequest, io.netty.handler.codec.http.FullHttpRequest
        public FullHttpRequest setUri(String str) {
            super.setUri(str);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest copy() {
            return replace(content().copy());
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest duplicate() {
            return replace(content().duplicate());
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest retainedDuplicate() {
            return replace(content().retainedDuplicate());
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
        public FullHttpRequest replace(ByteBuf byteBuf) {
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), byteBuf);
            defaultFullHttpRequest.headers().set(headers());
            defaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
            return defaultFullHttpRequest;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest retain(int i) {
            this.a.retain(i);
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest retain() {
            this.a.retain();
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest touch() {
            this.a.touch();
            return this;
        }

        @Override // io.netty.handler.codec.http.FullHttpRequest, io.netty.handler.codec.http.FullHttpMessage, io.netty.handler.codec.http.LastHttpContent, io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
        public FullHttpRequest touch(Object obj) {
            this.a.touch(obj);
            return this;
        }

        @Override // io.netty.buffer.ByteBufHolder
        public ByteBuf content() {
            return this.a.content();
        }

        @Override // io.netty.handler.codec.http.LastHttpContent
        public HttpHeaders trailingHeaders() {
            HttpContent httpContent = this.a;
            if (httpContent instanceof LastHttpContent) {
                return ((LastHttpContent) httpContent).trailingHeaders();
            }
            return EmptyHttpHeaders.INSTANCE;
        }

        @Override // io.netty.util.ReferenceCounted
        public int refCnt() {
            return this.a.refCnt();
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release() {
            return this.a.release();
        }

        @Override // io.netty.util.ReferenceCounted
        public boolean release(int i) {
            return this.a.release(i);
        }
    }
}
