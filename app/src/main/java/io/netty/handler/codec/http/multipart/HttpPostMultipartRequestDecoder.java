package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class HttpPostMultipartRequestDecoder implements InterfaceHttpPostRequestDecoder {
    private final HttpDataFactory a;
    private final HttpRequest b;
    private Charset c;
    private boolean d;
    private final List<InterfaceHttpData> e;
    private final Map<String, List<InterfaceHttpData>> f;
    private ByteBuf g;
    private int h;
    private String i;
    private String j;
    private HttpPostRequestDecoder.MultiPartStatus k;
    private Map<CharSequence, Attribute> l;
    private FileUpload m;
    private Attribute n;
    private boolean o;
    private int p;

    public HttpPostMultipartRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        this.e = new ArrayList();
        this.f = new TreeMap(a.a);
        this.k = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
        this.p = 10485760;
        if (httpDataFactory == null) {
            throw new NullPointerException("factory");
        } else if (httpRequest == null) {
            throw new NullPointerException("request");
        } else if (charset != null) {
            this.b = httpRequest;
            this.c = charset;
            this.a = httpDataFactory;
            a(this.b.headers().get(HttpHeaderNames.CONTENT_TYPE));
            if (httpRequest instanceof HttpContent) {
                offer((HttpContent) httpRequest);
                return;
            }
            this.g = Unpooled.buffer();
            d();
        } else {
            throw new NullPointerException("charset");
        }
    }

    private void a(String str) {
        String[] multipartDataBoundary = HttpPostRequestDecoder.getMultipartDataBoundary(str);
        if (multipartDataBoundary != null) {
            this.i = multipartDataBoundary[0];
            if (multipartDataBoundary.length > 1 && multipartDataBoundary[1] != null) {
                this.c = Charset.forName(multipartDataBoundary[1]);
            }
        } else {
            this.i = null;
        }
        this.k = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
    }

    private void c() {
        if (this.o) {
            throw new IllegalStateException(HttpPostMultipartRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean isMultipart() {
        c();
        return true;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void setDiscardThreshold(int i) {
        if (i >= 0) {
            this.p = i;
            return;
        }
        throw new IllegalArgumentException("discardThreshold must be >= 0");
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public int getDiscardThreshold() {
        return this.p;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas() {
        c();
        if (this.d) {
            return this.e;
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas(String str) {
        c();
        if (this.d) {
            return this.f.get(str);
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData getBodyHttpData(String str) {
        c();
        if (this.d) {
            List<InterfaceHttpData> list = this.f.get(str);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public HttpPostMultipartRequestDecoder offer(HttpContent httpContent) {
        c();
        ByteBuf content = httpContent.content();
        ByteBuf byteBuf = this.g;
        if (byteBuf == null) {
            this.g = content.copy();
        } else {
            byteBuf.writeBytes(content);
        }
        if (httpContent instanceof LastHttpContent) {
            this.d = true;
        }
        d();
        ByteBuf byteBuf2 = this.g;
        if (byteBuf2 != null && byteBuf2.writerIndex() > this.p) {
            this.g.discardReadBytes();
        }
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean hasNext() {
        c();
        if (this.k != HttpPostRequestDecoder.MultiPartStatus.EPILOGUE || this.h < this.e.size()) {
            return !this.e.isEmpty() && this.h < this.e.size();
        }
        throw new HttpPostRequestDecoder.EndOfDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData next() {
        c();
        if (!hasNext()) {
            return null;
        }
        List<InterfaceHttpData> list = this.e;
        int i = this.h;
        this.h = i + 1;
        return list.get(i);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData currentPartialHttpData() {
        FileUpload fileUpload = this.m;
        return fileUpload != null ? fileUpload : this.n;
    }

    private void d() {
        if (this.k != HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE && this.k != HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
            e();
        } else if (this.d) {
            this.k = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
        }
    }

    protected void addHttpData(InterfaceHttpData interfaceHttpData) {
        if (interfaceHttpData != null) {
            List<InterfaceHttpData> list = this.f.get(interfaceHttpData.getName());
            if (list == null) {
                list = new ArrayList<>(1);
                this.f.put(interfaceHttpData.getName(), list);
            }
            list.add(interfaceHttpData);
            this.e.add(interfaceHttpData);
        }
    }

    private void e() {
        ByteBuf byteBuf = this.g;
        if (byteBuf != null && byteBuf.readableBytes() != 0) {
            InterfaceHttpData a = a(this.k);
            while (a != null) {
                addHttpData(a);
                if (this.k != HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE && this.k != HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
                    a = a(this.k);
                } else {
                    return;
                }
            }
        }
    }

    private InterfaceHttpData a(HttpPostRequestDecoder.MultiPartStatus multiPartStatus) {
        Charset forName;
        long j;
        switch (multiPartStatus) {
            case NOTSTARTED:
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            case PREAMBLE:
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Should not be called with the current getStatus");
            case HEADERDELIMITER:
                return a(this.i, HttpPostRequestDecoder.MultiPartStatus.DISPOSITION, HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE);
            case DISPOSITION:
                return f();
            case FIELD:
                Attribute attribute = this.l.get(HttpHeaderValues.CHARSET);
                if (attribute != null) {
                    try {
                        forName = Charset.forName(attribute.getValue());
                    } catch (IOException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    } catch (UnsupportedCharsetException e2) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
                    }
                } else {
                    forName = null;
                }
                Attribute attribute2 = this.l.get(HttpHeaderValues.NAME);
                if (this.n == null) {
                    Attribute attribute3 = this.l.get(HttpHeaderNames.CONTENT_LENGTH);
                    if (attribute3 != null) {
                        try {
                            j = Long.parseLong(attribute3.getValue());
                        } catch (IOException e3) {
                            throw new HttpPostRequestDecoder.ErrorDataDecoderException(e3);
                        } catch (NumberFormatException unused) {
                            j = 0;
                        }
                    } else {
                        j = 0;
                    }
                    try {
                        if (j > 0) {
                            this.n = this.a.createAttribute(this.b, h(attribute2.getValue()), j);
                        } else {
                            this.n = this.a.createAttribute(this.b, h(attribute2.getValue()));
                        }
                        if (forName != null) {
                            this.n.setCharset(forName);
                        }
                    } catch (IOException e4) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e4);
                    } catch (IllegalArgumentException e5) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e5);
                    } catch (NullPointerException e6) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e6);
                    }
                }
                try {
                    g(this.i);
                    Attribute attribute4 = this.n;
                    this.n = null;
                    this.l = null;
                    this.k = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
                    return attribute4;
                } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException unused2) {
                    return null;
                }
            case FILEUPLOAD:
                return getFileUpload(this.i);
            case MIXEDDELIMITER:
                return a(this.j, HttpPostRequestDecoder.MultiPartStatus.MIXEDDISPOSITION, HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
            case MIXEDDISPOSITION:
                return f();
            case MIXEDFILEUPLOAD:
                return getFileUpload(this.j);
            case PREEPILOGUE:
                return null;
            case EPILOGUE:
                return null;
            default:
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("Shouldn't reach here.");
        }
    }

    void a() {
        try {
            try {
                HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
                while (bVar.c < bVar.e) {
                    byte[] bArr = bVar.a;
                    int i = bVar.c;
                    bVar.c = i + 1;
                    char c = (char) (bArr[i] & 255);
                    if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                        bVar.a(1);
                        return;
                    }
                }
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException("Access out of bounds");
            } catch (HttpPostBodyUtil.a unused) {
                b();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
    }

    void b() {
        while (true) {
            char readUnsignedByte = (char) this.g.readUnsignedByte();
            if (!Character.isISOControl(readUnsignedByte) && !Character.isWhitespace(readUnsignedByte)) {
                ByteBuf byteBuf = this.g;
                byteBuf.readerIndex(byteBuf.readerIndex() - 1);
                return;
            }
        }
    }

    private InterfaceHttpData a(String str, HttpPostRequestDecoder.MultiPartStatus multiPartStatus, HttpPostRequestDecoder.MultiPartStatus multiPartStatus2) {
        int readerIndex = this.g.readerIndex();
        try {
            a();
            j();
            try {
                String c = c(str);
                if (c.equals(str)) {
                    this.k = multiPartStatus;
                    return a(multiPartStatus);
                }
                if (c.equals(str + "--")) {
                    this.k = multiPartStatus2;
                    if (this.k != HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER) {
                        return null;
                    }
                    this.l = null;
                    return a(HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER);
                }
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.ErrorDataDecoderException("No Multipart delimiter found");
            } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException unused) {
                this.g.readerIndex(readerIndex);
                return null;
            }
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException unused2) {
            this.g.readerIndex(readerIndex);
            return null;
        }
    }

    private InterfaceHttpData f() {
        boolean z;
        String str;
        int readerIndex = this.g.readerIndex();
        if (this.k == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            this.l = new TreeMap(a.a);
        }
        while (!j()) {
            try {
                a();
                String i = i();
                String[] i2 = i(i);
                if (HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(i2[0])) {
                    if (this.k == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                        z = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(i2[1]);
                    } else {
                        z = HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(i2[1]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(i2[1]);
                    }
                    if (z) {
                        for (int i3 = 2; i3 < i2.length; i3++) {
                            String[] split = StringUtil.split(i2[i3], '=', 2);
                            try {
                                String h = h(split[0]);
                                String str2 = split[1];
                                if (HttpHeaderValues.FILENAME.contentEquals(h)) {
                                    str = str2.substring(1, str2.length() - 1);
                                } else {
                                    str = h(str2);
                                }
                                Attribute createAttribute = this.a.createAttribute(this.b, h, str);
                                this.l.put(createAttribute.getName(), createAttribute);
                            } catch (IllegalArgumentException e) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                            } catch (NullPointerException e2) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                } else if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(i2[0])) {
                    try {
                        this.l.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, this.a.createAttribute(this.b, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), h(i2[1])));
                    } catch (IllegalArgumentException e3) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e3);
                    } catch (NullPointerException e4) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e4);
                    }
                } else if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(i2[0])) {
                    try {
                        this.l.put(HttpHeaderNames.CONTENT_LENGTH, this.a.createAttribute(this.b, HttpHeaderNames.CONTENT_LENGTH.toString(), h(i2[1])));
                    } catch (IllegalArgumentException e5) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e5);
                    } catch (NullPointerException e6) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e6);
                    }
                } else if (!HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(i2[0])) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Unknown Params: " + i);
                } else if (!HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(i2[1])) {
                    for (int i4 = 1; i4 < i2.length; i4++) {
                        if (i2[i4].toLowerCase().startsWith(HttpHeaderValues.CHARSET.toString())) {
                            try {
                                this.l.put(HttpHeaderValues.CHARSET, this.a.createAttribute(this.b, HttpHeaderValues.CHARSET.toString(), h(StringUtil.substringAfter(i2[i4], '='))));
                            } catch (IllegalArgumentException e7) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e7);
                            } catch (NullPointerException e8) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e8);
                            }
                        } else {
                            try {
                                Attribute createAttribute2 = this.a.createAttribute(this.b, h(i2[0]), i2[i4]);
                                this.l.put(createAttribute2.getName(), createAttribute2);
                            } catch (IllegalArgumentException e9) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e9);
                            } catch (NullPointerException e10) {
                                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e10);
                            }
                        }
                    }
                    continue;
                } else if (this.k == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
                    this.j = "--" + StringUtil.substringAfter(i2[2], '=');
                    this.k = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
                    return a(HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER);
                } else {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
                }
            } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException unused) {
                this.g.readerIndex(readerIndex);
                return null;
            }
        }
        Attribute attribute = this.l.get(HttpHeaderValues.FILENAME);
        if (this.k == HttpPostRequestDecoder.MultiPartStatus.DISPOSITION) {
            if (attribute != null) {
                this.k = HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD;
                return a(HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD);
            }
            this.k = HttpPostRequestDecoder.MultiPartStatus.FIELD;
            return a(HttpPostRequestDecoder.MultiPartStatus.FIELD);
        } else if (attribute != null) {
            this.k = HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD;
            return a(HttpPostRequestDecoder.MultiPartStatus.MIXEDFILEUPLOAD);
        } else {
            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Filename not found");
        }
    }

    protected InterfaceHttpData getFileUpload(String str) {
        String value;
        Attribute attribute = this.l.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        Charset charset = this.c;
        HttpPostBodyUtil.TransferEncodingMechanism transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
        if (attribute != null) {
            try {
                String lowerCase = attribute.getValue().toLowerCase();
                if (lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
                    charset = CharsetUtil.US_ASCII;
                } else if (lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
                    charset = CharsetUtil.ISO_8859_1;
                    transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
                } else if (lowerCase.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                    transferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
                } else {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("TransferEncoding Unknown: " + lowerCase);
                }
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        }
        Attribute attribute2 = this.l.get(HttpHeaderValues.CHARSET);
        if (attribute2 != null) {
            try {
                charset = Charset.forName(attribute2.getValue());
            } catch (IOException e2) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
            } catch (UnsupportedCharsetException e3) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e3);
            }
        }
        if (this.m == null) {
            Attribute attribute3 = this.l.get(HttpHeaderValues.FILENAME);
            Attribute attribute4 = this.l.get(HttpHeaderValues.NAME);
            Attribute attribute5 = this.l.get(HttpHeaderNames.CONTENT_TYPE);
            Attribute attribute6 = this.l.get(HttpHeaderNames.CONTENT_LENGTH);
            long j = 0;
            if (attribute6 != null) {
                try {
                    j = Long.parseLong(attribute6.getValue());
                } catch (IOException e4) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e4);
                } catch (NumberFormatException unused) {
                    j = 0;
                }
            }
            if (attribute5 != null) {
                try {
                    value = attribute5.getValue();
                } catch (IOException e5) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e5);
                } catch (IllegalArgumentException e6) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e6);
                } catch (NullPointerException e7) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e7);
                }
            } else {
                value = "application/octet-stream";
            }
            this.m = this.a.createFileUpload(this.b, h(attribute4.getValue()), h(attribute3.getValue()), value, transferEncodingMechanism.value(), charset, j);
        }
        try {
            e(str);
            if (!this.m.isCompleted()) {
                return null;
            }
            if (this.k == HttpPostRequestDecoder.MultiPartStatus.FILEUPLOAD) {
                this.k = HttpPostRequestDecoder.MultiPartStatus.HEADERDELIMITER;
                this.l = null;
            } else {
                this.k = HttpPostRequestDecoder.MultiPartStatus.MIXEDDELIMITER;
                g();
            }
            FileUpload fileUpload = this.m;
            this.m = null;
            return fileUpload;
        } catch (HttpPostRequestDecoder.NotEnoughDataDecoderException unused2) {
            return null;
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void destroy() {
        c();
        cleanFiles();
        this.o = true;
        ByteBuf byteBuf = this.g;
        if (byteBuf != null && byteBuf.refCnt() > 0) {
            this.g.release();
            this.g = null;
        }
        for (int i = this.h; i < this.e.size(); i++) {
            this.e.get(i).release();
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void cleanFiles() {
        c();
        this.a.cleanRequestHttpData(this.b);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData) {
        c();
        this.a.removeHttpDataFromClean(this.b, interfaceHttpData);
    }

    private void g() {
        this.l.remove(HttpHeaderValues.CHARSET);
        this.l.remove(HttpHeaderNames.CONTENT_LENGTH);
        this.l.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        this.l.remove(HttpHeaderNames.CONTENT_TYPE);
        this.l.remove(HttpHeaderValues.FILENAME);
    }

    private String h() {
        int readerIndex = this.g.readerIndex();
        try {
            ByteBuf buffer = Unpooled.buffer(64);
            while (this.g.isReadable()) {
                byte readByte = this.g.readByte();
                if (readByte == 13) {
                    if (this.g.getByte(this.g.readerIndex()) == 10) {
                        this.g.readByte();
                        return buffer.toString(this.c);
                    }
                    buffer.writeByte(13);
                } else if (readByte == 10) {
                    return buffer.toString(this.c);
                } else {
                    buffer.writeByte(readByte);
                }
            }
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        } catch (IndexOutOfBoundsException e) {
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
    }

    private String i() {
        try {
            HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
            int readerIndex = this.g.readerIndex();
            try {
                ByteBuf buffer = Unpooled.buffer(64);
                while (bVar.c < bVar.e) {
                    byte[] bArr = bVar.a;
                    int i = bVar.c;
                    bVar.c = i + 1;
                    byte b = bArr[i];
                    if (b == 13) {
                        if (bVar.c < bVar.e) {
                            byte[] bArr2 = bVar.a;
                            int i2 = bVar.c;
                            bVar.c = i2 + 1;
                            if (bArr2[i2] == 10) {
                                bVar.a(0);
                                return buffer.toString(this.c);
                            }
                            bVar.c--;
                            buffer.writeByte(13);
                        } else {
                            buffer.writeByte(b);
                        }
                    } else if (b == 10) {
                        bVar.a(0);
                        return buffer.toString(this.c);
                    } else {
                        buffer.writeByte(b);
                    }
                }
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            } catch (IndexOutOfBoundsException e) {
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
            }
        } catch (HttpPostBodyUtil.a unused) {
            return h();
        }
    }

    private String b(String str) {
        int readerIndex = this.g.readerIndex();
        try {
            StringBuilder sb = new StringBuilder(64);
            int i = 0;
            int length = str.length();
            while (this.g.isReadable() && i < length) {
                byte readByte = this.g.readByte();
                if (readByte == str.charAt(i)) {
                    i++;
                    sb.append((char) readByte);
                } else {
                    this.g.readerIndex(readerIndex);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                }
            }
            if (this.g.isReadable()) {
                byte readByte2 = this.g.readByte();
                if (readByte2 == 13) {
                    if (this.g.readByte() == 10) {
                        return sb.toString();
                    }
                    this.g.readerIndex(readerIndex);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                } else if (readByte2 == 10) {
                    return sb.toString();
                } else {
                    if (readByte2 == 45) {
                        sb.append('-');
                        if (this.g.readByte() == 45) {
                            sb.append('-');
                            if (!this.g.isReadable()) {
                                return sb.toString();
                            }
                            byte readByte3 = this.g.readByte();
                            if (readByte3 == 13) {
                                if (this.g.readByte() == 10) {
                                    return sb.toString();
                                }
                                this.g.readerIndex(readerIndex);
                                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                            } else if (readByte3 == 10) {
                                return sb.toString();
                            } else {
                                this.g.readerIndex(this.g.readerIndex() - 1);
                                return sb.toString();
                            }
                        }
                    }
                }
            }
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
        } catch (IndexOutOfBoundsException e) {
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
        }
    }

    private String c(String str) {
        try {
            HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
            int readerIndex = this.g.readerIndex();
            int length = str.length();
            try {
                StringBuilder sb = new StringBuilder(64);
                int i = 0;
                while (bVar.c < bVar.e && i < length) {
                    byte[] bArr = bVar.a;
                    int i2 = bVar.c;
                    bVar.c = i2 + 1;
                    byte b = bArr[i2];
                    if (b == str.charAt(i)) {
                        i++;
                        sb.append((char) b);
                    } else {
                        this.g.readerIndex(readerIndex);
                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                    }
                }
                if (bVar.c < bVar.e) {
                    byte[] bArr2 = bVar.a;
                    int i3 = bVar.c;
                    bVar.c = i3 + 1;
                    byte b2 = bArr2[i3];
                    if (b2 == 13) {
                        if (bVar.c < bVar.e) {
                            byte[] bArr3 = bVar.a;
                            int i4 = bVar.c;
                            bVar.c = i4 + 1;
                            if (bArr3[i4] == 10) {
                                bVar.a(0);
                                return sb.toString();
                            }
                            this.g.readerIndex(readerIndex);
                            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                        }
                        this.g.readerIndex(readerIndex);
                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                    } else if (b2 == 10) {
                        bVar.a(0);
                        return sb.toString();
                    } else if (b2 == 45) {
                        sb.append('-');
                        if (bVar.c < bVar.e) {
                            byte[] bArr4 = bVar.a;
                            int i5 = bVar.c;
                            bVar.c = i5 + 1;
                            if (bArr4[i5] == 45) {
                                sb.append('-');
                                if (bVar.c < bVar.e) {
                                    byte[] bArr5 = bVar.a;
                                    int i6 = bVar.c;
                                    bVar.c = i6 + 1;
                                    byte b3 = bArr5[i6];
                                    if (b3 == 13) {
                                        if (bVar.c < bVar.e) {
                                            byte[] bArr6 = bVar.a;
                                            int i7 = bVar.c;
                                            bVar.c = i7 + 1;
                                            if (bArr6[i7] == 10) {
                                                bVar.a(0);
                                                return sb.toString();
                                            }
                                            this.g.readerIndex(readerIndex);
                                            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                                        }
                                        this.g.readerIndex(readerIndex);
                                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                                    } else if (b3 == 10) {
                                        bVar.a(0);
                                        return sb.toString();
                                    } else {
                                        bVar.a(1);
                                        return sb.toString();
                                    }
                                } else {
                                    bVar.a(0);
                                    return sb.toString();
                                }
                            }
                        }
                    }
                }
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            } catch (IndexOutOfBoundsException e) {
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e);
            }
        } catch (HttpPostBodyUtil.a unused) {
            return b(str);
        }
    }

    private void d(String str) {
        boolean z;
        int readerIndex = this.g.readerIndex();
        int readerIndex2 = this.g.readerIndex();
        int i = 0;
        boolean z2 = true;
        while (true) {
            if (!this.g.isReadable()) {
                z = false;
                break;
            }
            byte readByte = this.g.readByte();
            if (z2) {
                if (readByte == str.codePointAt(i)) {
                    i++;
                    if (str.length() == i) {
                        z = true;
                        break;
                    }
                } else if (readByte == 13) {
                    if (!this.g.isReadable()) {
                        z2 = false;
                        i = 0;
                    } else if (this.g.readByte() == 10) {
                        readerIndex2 = this.g.readerIndex() - 2;
                        i = 0;
                        z2 = true;
                    } else {
                        int readerIndex3 = this.g.readerIndex() - 1;
                        this.g.readerIndex(readerIndex3);
                        readerIndex2 = readerIndex3;
                        z2 = false;
                        i = 0;
                    }
                } else if (readByte == 10) {
                    readerIndex2 = this.g.readerIndex() - 1;
                    i = 0;
                    z2 = true;
                } else {
                    readerIndex2 = this.g.readerIndex();
                    z2 = false;
                    i = 0;
                }
            } else if (readByte == 13) {
                if (this.g.isReadable()) {
                    if (this.g.readByte() == 10) {
                        readerIndex2 = this.g.readerIndex() - 2;
                        i = 0;
                        z2 = true;
                    } else {
                        readerIndex2 = this.g.readerIndex() - 1;
                        this.g.readerIndex(readerIndex2);
                    }
                }
            } else if (readByte == 10) {
                readerIndex2 = this.g.readerIndex() - 1;
                i = 0;
                z2 = true;
            } else {
                readerIndex2 = this.g.readerIndex();
            }
        }
        ByteBuf copy = this.g.copy(readerIndex, readerIndex2 - readerIndex);
        if (z) {
            try {
                this.m.addContent(copy, true);
                this.g.readerIndex(readerIndex2);
            } catch (IOException e) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
            }
        } else {
            try {
                this.m.addContent(copy, false);
                this.g.readerIndex(readerIndex2);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
            } catch (IOException e2) {
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
            }
        }
    }

    private void e(String str) {
        boolean z;
        try {
            HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
            int readerIndex = this.g.readerIndex();
            int i = bVar.c;
            int i2 = 0;
            boolean z2 = true;
            while (true) {
                if (bVar.c >= bVar.e) {
                    z = false;
                    break;
                }
                byte[] bArr = bVar.a;
                int i3 = bVar.c;
                bVar.c = i3 + 1;
                byte b = bArr[i3];
                if (z2) {
                    if (b == str.codePointAt(i2)) {
                        i2++;
                        if (str.length() == i2) {
                            z = true;
                            break;
                        }
                    } else if (b == 13) {
                        if (bVar.c < bVar.e) {
                            byte[] bArr2 = bVar.a;
                            int i4 = bVar.c;
                            bVar.c = i4 + 1;
                            if (bArr2[i4] == 10) {
                                i = bVar.c - 2;
                                i2 = 0;
                                z2 = true;
                            } else {
                                bVar.c--;
                                i = bVar.c;
                                z2 = false;
                                i2 = 0;
                            }
                        } else {
                            z2 = false;
                            i2 = 0;
                        }
                    } else if (b == 10) {
                        i = bVar.c - 1;
                        i2 = 0;
                        z2 = true;
                    } else {
                        i = bVar.c;
                        z2 = false;
                        i2 = 0;
                    }
                } else if (b == 13) {
                    if (bVar.c < bVar.e) {
                        byte[] bArr3 = bVar.a;
                        int i5 = bVar.c;
                        bVar.c = i5 + 1;
                        if (bArr3[i5] == 10) {
                            i = bVar.c - 2;
                            i2 = 0;
                            z2 = true;
                        } else {
                            bVar.c--;
                            i = bVar.c;
                        }
                    }
                } else if (b == 10) {
                    i = bVar.c - 1;
                    i2 = 0;
                    z2 = true;
                } else {
                    i = bVar.c;
                }
            }
            int b2 = bVar.b(i);
            ByteBuf copy = this.g.copy(readerIndex, b2 - readerIndex);
            if (z) {
                try {
                    this.m.addContent(copy, true);
                    this.g.readerIndex(b2);
                } catch (IOException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
            } else {
                try {
                    this.m.addContent(copy, false);
                    this.g.readerIndex(b2);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                } catch (IOException e2) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
                }
            }
        } catch (HttpPostBodyUtil.a unused) {
            d(str);
        }
    }

    private void f(String str) {
        boolean z;
        int readerIndex = this.g.readerIndex();
        try {
            int readerIndex2 = this.g.readerIndex();
            int i = 0;
            boolean z2 = true;
            while (true) {
                if (!this.g.isReadable()) {
                    z = false;
                    break;
                }
                byte readByte = this.g.readByte();
                if (z2) {
                    if (readByte == str.codePointAt(i)) {
                        i++;
                        if (str.length() == i) {
                            z = true;
                            break;
                        }
                    } else if (readByte == 13) {
                        if (!this.g.isReadable()) {
                            readerIndex2 = this.g.readerIndex() - 1;
                            z2 = false;
                            i = 0;
                        } else if (this.g.readByte() == 10) {
                            readerIndex2 = this.g.readerIndex() - 2;
                            i = 0;
                            z2 = true;
                        } else {
                            int readerIndex3 = this.g.readerIndex() - 1;
                            this.g.readerIndex(readerIndex3);
                            readerIndex2 = readerIndex3;
                            z2 = false;
                            i = 0;
                        }
                    } else if (readByte == 10) {
                        readerIndex2 = this.g.readerIndex() - 1;
                        i = 0;
                        z2 = true;
                    } else {
                        readerIndex2 = this.g.readerIndex();
                        z2 = false;
                        i = 0;
                    }
                } else if (readByte == 13) {
                    if (!this.g.isReadable()) {
                        readerIndex2 = this.g.readerIndex() - 1;
                    } else if (this.g.readByte() == 10) {
                        readerIndex2 = this.g.readerIndex() - 2;
                        i = 0;
                        z2 = true;
                    } else {
                        readerIndex2 = this.g.readerIndex() - 1;
                        this.g.readerIndex(readerIndex2);
                    }
                } else if (readByte == 10) {
                    readerIndex2 = this.g.readerIndex() - 1;
                    i = 0;
                    z2 = true;
                } else {
                    readerIndex2 = this.g.readerIndex();
                }
            }
            if (z) {
                try {
                    this.n.addContent(this.g.copy(readerIndex, readerIndex2 - readerIndex), true);
                    this.g.readerIndex(readerIndex2);
                    return;
                } catch (IOException e) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                }
            } else {
                try {
                    this.n.addContent(this.g.copy(readerIndex, readerIndex2 - readerIndex), false);
                    this.g.readerIndex(readerIndex2);
                    throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                } catch (IOException e2) {
                    throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
                }
            }
        } catch (IndexOutOfBoundsException e3) {
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e3);
        }
        this.g.readerIndex(readerIndex);
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e3);
    }

    private void g(String str) {
        boolean z;
        try {
            HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
            int readerIndex = this.g.readerIndex();
            try {
                int i = bVar.c;
                int i2 = 0;
                boolean z2 = true;
                while (true) {
                    if (bVar.c >= bVar.e) {
                        z = false;
                        break;
                    }
                    byte[] bArr = bVar.a;
                    int i3 = bVar.c;
                    bVar.c = i3 + 1;
                    byte b = bArr[i3];
                    if (z2) {
                        if (b == str.codePointAt(i2)) {
                            i2++;
                            if (str.length() == i2) {
                                z = true;
                                break;
                            }
                        } else if (b == 13) {
                            if (bVar.c < bVar.e) {
                                byte[] bArr2 = bVar.a;
                                int i4 = bVar.c;
                                bVar.c = i4 + 1;
                                if (bArr2[i4] == 10) {
                                    i = bVar.c - 2;
                                    i2 = 0;
                                    z2 = true;
                                } else {
                                    bVar.c--;
                                    i = bVar.c;
                                    z2 = false;
                                    i2 = 0;
                                }
                            } else {
                                z2 = false;
                                i2 = 0;
                            }
                        } else if (b == 10) {
                            i = bVar.c - 1;
                            i2 = 0;
                            z2 = true;
                        } else {
                            i = bVar.c;
                            z2 = false;
                            i2 = 0;
                        }
                    } else if (b == 13) {
                        if (bVar.c < bVar.e) {
                            byte[] bArr3 = bVar.a;
                            int i5 = bVar.c;
                            bVar.c = i5 + 1;
                            if (bArr3[i5] == 10) {
                                i = bVar.c - 2;
                                i2 = 0;
                                z2 = true;
                            } else {
                                bVar.c--;
                                i = bVar.c;
                            }
                        }
                    } else if (b == 10) {
                        i = bVar.c - 1;
                        i2 = 0;
                        z2 = true;
                    } else {
                        i = bVar.c;
                    }
                }
                int b2 = bVar.b(i);
                if (z) {
                    try {
                        this.n.addContent(this.g.copy(readerIndex, b2 - readerIndex), true);
                        this.g.readerIndex(b2);
                        return;
                    } catch (IOException e) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e);
                    }
                } else {
                    try {
                        this.n.addContent(this.g.copy(readerIndex, b2 - readerIndex), false);
                        this.g.readerIndex(b2);
                        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
                    } catch (IOException e2) {
                        throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
                    }
                }
            } catch (IndexOutOfBoundsException e3) {
                this.g.readerIndex(readerIndex);
                throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e3);
            }
            this.g.readerIndex(readerIndex);
            throw new HttpPostRequestDecoder.NotEnoughDataDecoderException(e3);
        } catch (HttpPostBodyUtil.a unused) {
            f(str);
        }
    }

    private static String h(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == ':') {
                sb.append(' ');
            } else if (charAt == ',') {
                sb.append(' ');
            } else if (charAt == '=') {
                sb.append(' ');
            } else if (charAt == ';') {
                sb.append(' ');
            } else if (charAt == '\t') {
                sb.append(' ');
            } else if (charAt != '\"') {
                sb.append(charAt);
            }
        }
        return sb.toString().trim();
    }

    private boolean j() {
        if (!this.g.isReadable()) {
            return false;
        }
        byte readByte = this.g.readByte();
        if (readByte == 13) {
            if (!this.g.isReadable()) {
                ByteBuf byteBuf = this.g;
                byteBuf.readerIndex(byteBuf.readerIndex() - 1);
                return false;
            } else if (this.g.readByte() == 10) {
                return true;
            } else {
                ByteBuf byteBuf2 = this.g;
                byteBuf2.readerIndex(byteBuf2.readerIndex() - 2);
                return false;
            }
        } else if (readByte == 10) {
            return true;
        } else {
            ByteBuf byteBuf3 = this.g;
            byteBuf3.readerIndex(byteBuf3.readerIndex() - 1);
            return false;
        }
    }

    private static String[] i(String str) {
        String[] strArr;
        char charAt;
        ArrayList arrayList = new ArrayList(1);
        int a = HttpPostBodyUtil.a(str, 0);
        int i = a;
        while (i < str.length() && (charAt = str.charAt(i)) != ':' && !Character.isWhitespace(charAt)) {
            i++;
        }
        int i2 = i;
        while (true) {
            if (i2 >= str.length()) {
                break;
            } else if (str.charAt(i2) == ':') {
                i2++;
                break;
            } else {
                i2++;
            }
        }
        int a2 = HttpPostBodyUtil.a(str, i2);
        int a3 = HttpPostBodyUtil.a(str);
        arrayList.add(str.substring(a, i));
        String substring = str.substring(a2, a3);
        if (substring.indexOf(59) >= 0) {
            strArr = j(substring);
        } else {
            strArr = StringUtil.split(substring, StringUtil.COMMA);
        }
        for (String str2 : strArr) {
            arrayList.add(str2.trim());
        }
        String[] strArr2 = new String[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            strArr2[i3] = (String) arrayList.get(i3);
        }
        return strArr2;
    }

    private static String[] j(String str) {
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList(1);
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (z) {
                if (z2) {
                    z2 = false;
                } else if (charAt == '\\') {
                    z2 = true;
                } else if (charAt == '\"') {
                    z = false;
                }
            } else if (charAt == '\"') {
                z = true;
            } else if (charAt == ';') {
                arrayList.add(str.substring(i, i2));
                i = i2 + 1;
            }
        }
        arrayList.add(str.substring(i));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
