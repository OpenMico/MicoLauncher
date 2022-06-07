package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class HttpObjectDecoder extends ByteToMessageDecoder {
    static final /* synthetic */ boolean c = !HttpObjectDecoder.class.desiredAssertionStatus();
    private final int a;
    private final boolean d;
    private final a e;
    private final b f;
    private HttpMessage g;
    private long h;
    private long i;
    private volatile boolean j;
    private CharSequence k;
    private CharSequence l;
    private LastHttpContent m;
    private c n;
    protected final boolean validateHeaders;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum c {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER,
        BAD_MESSAGE,
        UPGRADED
    }

    protected abstract HttpMessage createInvalidMessage();

    protected abstract HttpMessage createMessage(String[] strArr) throws Exception;

    protected abstract boolean isDecodingRequest();

    /* JADX INFO: Access modifiers changed from: protected */
    public HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public HttpObjectDecoder(int i, int i2, int i3, boolean z) {
        this(i, i2, i3, z, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public HttpObjectDecoder(int i, int i2, int i3, boolean z, boolean z2) {
        this(i, i2, i3, z, z2, 128);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public HttpObjectDecoder(int i, int i2, int i3, boolean z, boolean z2, int i4) {
        this.i = Long.MIN_VALUE;
        this.n = c.SKIP_CONTROL_CHARS;
        if (i <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + i);
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + i2);
        } else if (i3 > 0) {
            AppendableCharSequence appendableCharSequence = new AppendableCharSequence(i4);
            this.f = new b(appendableCharSequence, i);
            this.e = new a(appendableCharSequence, i2);
            this.a = i3;
            this.d = z;
            this.validateHeaders = z2;
        } else {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + i3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x011c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x013c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x013d A[Catch: Exception -> 0x01d4, TryCatch #3 {Exception -> 0x01d4, blocks: (B:70:0x0134, B:73:0x013d, B:75:0x0145, B:77:0x014a), top: B:125:0x0134 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x015b A[Catch: Exception -> 0x01cb, TryCatch #2 {Exception -> 0x01cb, blocks: (B:78:0x0154, B:81:0x015b, B:82:0x0165, B:83:0x0168, B:84:0x016d, B:86:0x0171, B:88:0x0177, B:89:0x017e, B:90:0x017f, B:96:0x0197, B:99:0x019e, B:101:0x01a2, B:103:0x01a6, B:106:0x01ab, B:107:0x01b0, B:108:0x01b1, B:110:0x01ba, B:112:0x01bd), top: B:124:0x0154 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void decode(io.netty.channel.ChannelHandlerContext r8, io.netty.buffer.ByteBuf r9, java.util.List<java.lang.Object> r10) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 512
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.HttpObjectDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        super.decodeLast(channelHandlerContext, byteBuf, list);
        if (this.j) {
            a();
        }
        HttpMessage httpMessage = this.g;
        if (httpMessage != null) {
            boolean isTransferEncodingChunked = HttpUtil.isTransferEncodingChunked(httpMessage);
            if (this.n != c.READ_VARIABLE_LENGTH_CONTENT || byteBuf.isReadable() || isTransferEncodingChunked) {
                boolean z = true;
                if (!isDecodingRequest() && !isTransferEncodingChunked && b() <= 0) {
                    z = false;
                }
                a();
                if (!z) {
                    list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                    return;
                }
                return;
            }
            list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            reset();
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof HttpExpectationFailedEvent) {
            int i = AnonymousClass1.a[this.n.ordinal()];
            if (i != 2) {
                switch (i) {
                }
            }
            reset();
        }
        super.userEventTriggered(channelHandlerContext, obj);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        if (httpMessage instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse) httpMessage;
            int code = httpResponse.status().code();
            if (code >= 100 && code < 200) {
                return code != 101 || httpResponse.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT) || !httpResponse.headers().contains((CharSequence) HttpHeaderNames.UPGRADE, (CharSequence) HttpHeaderValues.WEBSOCKET, true);
            }
            if (code != 304) {
                switch (code) {
                }
            }
            return true;
        }
        return false;
    }

    public void reset() {
        this.j = true;
    }

    private void a() {
        HttpResponse httpResponse;
        HttpMessage httpMessage = this.g;
        this.g = null;
        this.k = null;
        this.l = null;
        this.i = Long.MIN_VALUE;
        this.f.a();
        this.e.a();
        this.m = null;
        if (isDecodingRequest() || (httpResponse = (HttpResponse) httpMessage) == null || httpResponse.status().code() != 101) {
            this.n = c.SKIP_CONTROL_CHARS;
        } else {
            this.n = c.UPGRADED;
        }
    }

    private HttpMessage a(ByteBuf byteBuf, Exception exc) {
        this.n = c.BAD_MESSAGE;
        byteBuf.skipBytes(byteBuf.readableBytes());
        HttpMessage httpMessage = this.g;
        if (httpMessage != null) {
            httpMessage.setDecoderResult(DecoderResult.failure(exc));
        } else {
            this.g = createInvalidMessage();
            this.g.setDecoderResult(DecoderResult.failure(exc));
        }
        HttpMessage httpMessage2 = this.g;
        this.g = null;
        return httpMessage2;
    }

    private HttpContent b(ByteBuf byteBuf, Exception exc) {
        this.n = c.BAD_MESSAGE;
        byteBuf.skipBytes(byteBuf.readableBytes());
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        defaultLastHttpContent.setDecoderResult(DecoderResult.failure(exc));
        this.g = null;
        this.m = null;
        return defaultLastHttpContent;
    }

    private static boolean a(ByteBuf byteBuf) {
        boolean z;
        int writerIndex = byteBuf.writerIndex();
        int readerIndex = byteBuf.readerIndex();
        while (true) {
            if (writerIndex <= readerIndex) {
                z = false;
                break;
            }
            int i = readerIndex + 1;
            short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
            if (!Character.isISOControl(unsignedByte) && !Character.isWhitespace(unsignedByte)) {
                readerIndex = i - 1;
                z = true;
                break;
            }
            readerIndex = i;
        }
        byteBuf.readerIndex(readerIndex);
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private io.netty.handler.codec.http.HttpObjectDecoder.c b(io.netty.buffer.ByteBuf r10) {
        /*
            r9 = this;
            io.netty.handler.codec.http.HttpMessage r0 = r9.g
            io.netty.handler.codec.http.HttpHeaders r1 = r0.headers()
            io.netty.handler.codec.http.HttpObjectDecoder$a r2 = r9.e
            io.netty.util.internal.AppendableCharSequence r2 = r2.a(r10)
            r3 = 0
            if (r2 != 0) goto L_0x0010
            return r3
        L_0x0010:
            int r4 = r2.length()
            r5 = 0
            if (r4 <= 0) goto L_0x006e
        L_0x0017:
            char r4 = r2.charAt(r5)
            java.lang.CharSequence r6 = r9.k
            if (r6 == 0) goto L_0x0053
            r6 = 32
            if (r4 == r6) goto L_0x0027
            r7 = 9
            if (r4 != r7) goto L_0x0053
        L_0x0027:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.CharSequence r7 = r9.l
            int r7 = r7.length()
            int r8 = r2.length()
            int r7 = r7 + r8
            int r7 = r7 + 1
            r4.<init>(r7)
            java.lang.CharSequence r7 = r9.l
            r4.append(r7)
            r4.append(r6)
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r2.trim()
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r9.l = r2
            goto L_0x005f
        L_0x0053:
            java.lang.CharSequence r4 = r9.k
            if (r4 == 0) goto L_0x005c
            java.lang.CharSequence r6 = r9.l
            r1.add(r4, r6)
        L_0x005c:
            r9.b(r2)
        L_0x005f:
            io.netty.handler.codec.http.HttpObjectDecoder$a r2 = r9.e
            io.netty.util.internal.AppendableCharSequence r2 = r2.a(r10)
            if (r2 != 0) goto L_0x0068
            return r3
        L_0x0068:
            int r4 = r2.length()
            if (r4 > 0) goto L_0x0017
        L_0x006e:
            java.lang.CharSequence r10 = r9.k
            if (r10 == 0) goto L_0x0077
            java.lang.CharSequence r2 = r9.l
            r1.add(r10, r2)
        L_0x0077:
            r9.k = r3
            r9.l = r3
            boolean r10 = r9.isContentAlwaysEmpty(r0)
            if (r10 == 0) goto L_0x0087
            io.netty.handler.codec.http.HttpUtil.setTransferEncodingChunked(r0, r5)
            io.netty.handler.codec.http.HttpObjectDecoder$c r10 = io.netty.handler.codec.http.HttpObjectDecoder.c.SKIP_CONTROL_CHARS
            goto L_0x009f
        L_0x0087:
            boolean r10 = io.netty.handler.codec.http.HttpUtil.isTransferEncodingChunked(r0)
            if (r10 == 0) goto L_0x0090
            io.netty.handler.codec.http.HttpObjectDecoder$c r10 = io.netty.handler.codec.http.HttpObjectDecoder.c.READ_CHUNK_SIZE
            goto L_0x009f
        L_0x0090:
            long r0 = r9.b()
            r2 = 0
            int r10 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r10 < 0) goto L_0x009d
            io.netty.handler.codec.http.HttpObjectDecoder$c r10 = io.netty.handler.codec.http.HttpObjectDecoder.c.READ_FIXED_LENGTH_CONTENT
            goto L_0x009f
        L_0x009d:
            io.netty.handler.codec.http.HttpObjectDecoder$c r10 = io.netty.handler.codec.http.HttpObjectDecoder.c.READ_VARIABLE_LENGTH_CONTENT
        L_0x009f:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.HttpObjectDecoder.b(io.netty.buffer.ByteBuf):io.netty.handler.codec.http.HttpObjectDecoder$c");
    }

    private long b() {
        if (this.i == Long.MIN_VALUE) {
            this.i = HttpUtil.getContentLength(this.g, -1L);
        }
        return this.i;
    }

    private LastHttpContent c(ByteBuf byteBuf) {
        DefaultLastHttpContent defaultLastHttpContent;
        CharSequence charSequence;
        AppendableCharSequence a2 = this.e.a(byteBuf);
        if (a2 == null) {
            return null;
        }
        if (a2.length() <= 0) {
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        LastHttpContent lastHttpContent = this.m;
        if (lastHttpContent == null) {
            DefaultLastHttpContent defaultLastHttpContent2 = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
            this.m = defaultLastHttpContent2;
            defaultLastHttpContent = defaultLastHttpContent2;
            charSequence = null;
        } else {
            defaultLastHttpContent = lastHttpContent;
            charSequence = null;
        }
        do {
            char charAt = a2.charAt(0);
            if (charSequence == null || !(charAt == ' ' || charAt == '\t')) {
                b(a2);
                CharSequence charSequence2 = this.k;
                if (!HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(charSequence2) && !HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase(charSequence2) && !HttpHeaderNames.TRAILER.contentEqualsIgnoreCase(charSequence2)) {
                    defaultLastHttpContent.trailingHeaders().add(charSequence2, this.l);
                }
                CharSequence charSequence3 = this.k;
                this.k = null;
                this.l = null;
                charSequence = charSequence3;
            } else {
                List<String> all = defaultLastHttpContent.trailingHeaders().getAll(charSequence);
                if (!all.isEmpty()) {
                    int size = all.size() - 1;
                    String trim = a2.toString().trim();
                    String str = all.get(size);
                    StringBuilder sb = new StringBuilder(str.length() + trim.length());
                    sb.append((CharSequence) str);
                    sb.append(trim);
                    all.set(size, sb.toString());
                }
            }
            a2 = this.e.a(byteBuf);
            if (a2 == null) {
                return null;
            }
        } while (a2.length() > 0);
        this.m = null;
        return defaultLastHttpContent;
    }

    private static int a(String str) {
        String trim = str.trim();
        for (int i = 0; i < trim.length(); i++) {
            char charAt = trim.charAt(i);
            if (charAt == ';' || Character.isWhitespace(charAt) || Character.isISOControl(charAt)) {
                trim = trim.substring(0, i);
                break;
            }
        }
        return Integer.parseInt(trim, 16);
    }

    private static String[] a(AppendableCharSequence appendableCharSequence) {
        int a2 = a(appendableCharSequence, 0);
        int b2 = b(appendableCharSequence, a2);
        int a3 = a(appendableCharSequence, b2);
        int b3 = b(appendableCharSequence, a3);
        int a4 = a(appendableCharSequence, b3);
        int c2 = c(appendableCharSequence);
        String[] strArr = new String[3];
        strArr[0] = appendableCharSequence.subStringUnsafe(a2, b2);
        strArr[1] = appendableCharSequence.subStringUnsafe(a3, b3);
        strArr[2] = a4 < c2 ? appendableCharSequence.subStringUnsafe(a4, c2) : "";
        return strArr;
    }

    private void b(AppendableCharSequence appendableCharSequence) {
        char charAt;
        int length = appendableCharSequence.length();
        int a2 = a(appendableCharSequence, 0);
        int i = a2;
        while (i < length && (charAt = appendableCharSequence.charAt(i)) != ':' && !Character.isWhitespace(charAt)) {
            i++;
        }
        int i2 = i;
        while (true) {
            if (i2 >= length) {
                break;
            } else if (appendableCharSequence.charAt(i2) == ':') {
                i2++;
                break;
            } else {
                i2++;
            }
        }
        this.k = appendableCharSequence.subStringUnsafe(a2, i);
        int a3 = a(appendableCharSequence, i2);
        if (a3 == length) {
            this.l = "";
        } else {
            this.l = appendableCharSequence.subStringUnsafe(a3, c(appendableCharSequence));
        }
    }

    private static int a(AppendableCharSequence appendableCharSequence, int i) {
        while (i < appendableCharSequence.length()) {
            if (!Character.isWhitespace(appendableCharSequence.charAtUnsafe(i))) {
                return i;
            }
            i++;
        }
        return appendableCharSequence.length();
    }

    private static int b(AppendableCharSequence appendableCharSequence, int i) {
        while (i < appendableCharSequence.length()) {
            if (Character.isWhitespace(appendableCharSequence.charAtUnsafe(i))) {
                return i;
            }
            i++;
        }
        return appendableCharSequence.length();
    }

    private static int c(AppendableCharSequence appendableCharSequence) {
        for (int length = appendableCharSequence.length() - 1; length > 0; length--) {
            if (!Character.isWhitespace(appendableCharSequence.charAtUnsafe(length))) {
                return length + 1;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements ByteProcessor {
        private final AppendableCharSequence a;
        private final int b;
        private int c;

        a(AppendableCharSequence appendableCharSequence, int i) {
            this.a = appendableCharSequence;
            this.b = i;
        }

        public AppendableCharSequence a(ByteBuf byteBuf) {
            int i = this.c;
            this.a.reset();
            int forEachByte = byteBuf.forEachByte(this);
            if (forEachByte == -1) {
                this.c = i;
                return null;
            }
            byteBuf.readerIndex(forEachByte + 1);
            return this.a;
        }

        public void a() {
            this.c = 0;
        }

        @Override // io.netty.util.ByteProcessor
        public boolean process(byte b) throws Exception {
            char c = (char) b;
            if (c == '\r') {
                return true;
            }
            if (c == '\n') {
                return false;
            }
            int i = this.c + 1;
            this.c = i;
            int i2 = this.b;
            if (i <= i2) {
                this.a.append(c);
                return true;
            }
            throw a(i2);
        }

        protected TooLongFrameException a(int i) {
            return new TooLongFrameException("HTTP header is larger than " + i + " bytes.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b extends a {
        b(AppendableCharSequence appendableCharSequence, int i) {
            super(appendableCharSequence, i);
        }

        @Override // io.netty.handler.codec.http.HttpObjectDecoder.a
        public AppendableCharSequence a(ByteBuf byteBuf) {
            a();
            return super.a(byteBuf);
        }

        @Override // io.netty.handler.codec.http.HttpObjectDecoder.a
        protected TooLongFrameException a(int i) {
            return new TooLongFrameException("An HTTP line is larger than " + i + " bytes.");
        }
    }
}
