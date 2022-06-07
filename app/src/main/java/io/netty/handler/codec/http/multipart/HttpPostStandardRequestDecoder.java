package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class HttpPostStandardRequestDecoder implements InterfaceHttpPostRequestDecoder {
    private final HttpDataFactory a;
    private final HttpRequest b;
    private final Charset c;
    private boolean d;
    private final List<InterfaceHttpData> e;
    private final Map<String, List<InterfaceHttpData>> f;
    private ByteBuf g;
    private int h;
    private HttpPostRequestDecoder.MultiPartStatus i;
    private Attribute j;
    private boolean k;
    private int l;

    public HttpPostStandardRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        this.e = new ArrayList();
        this.f = new TreeMap(a.a);
        this.i = HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED;
        this.l = 10485760;
        if (httpDataFactory == null) {
            throw new NullPointerException("factory");
        } else if (httpRequest == null) {
            throw new NullPointerException("request");
        } else if (charset != null) {
            this.b = httpRequest;
            this.c = charset;
            this.a = httpDataFactory;
            if (httpRequest instanceof HttpContent) {
                offer((HttpContent) httpRequest);
                return;
            }
            this.g = Unpooled.buffer();
            b();
        } else {
            throw new NullPointerException("charset");
        }
    }

    private void a() {
        if (this.k) {
            throw new IllegalStateException(HttpPostStandardRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean isMultipart() {
        a();
        return false;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void setDiscardThreshold(int i) {
        if (i >= 0) {
            this.l = i;
            return;
        }
        throw new IllegalArgumentException("discardThreshold must be >= 0");
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public int getDiscardThreshold() {
        return this.l;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas() {
        a();
        if (this.d) {
            return this.e;
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas(String str) {
        a();
        if (this.d) {
            return this.f.get(str);
        }
        throw new HttpPostRequestDecoder.NotEnoughDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData getBodyHttpData(String str) {
        a();
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
    public HttpPostStandardRequestDecoder offer(HttpContent httpContent) {
        a();
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
        b();
        ByteBuf byteBuf2 = this.g;
        if (byteBuf2 != null && byteBuf2.writerIndex() > this.l) {
            this.g.discardReadBytes();
        }
        return this;
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean hasNext() {
        a();
        if (this.i != HttpPostRequestDecoder.MultiPartStatus.EPILOGUE || this.h < this.e.size()) {
            return !this.e.isEmpty() && this.h < this.e.size();
        }
        throw new HttpPostRequestDecoder.EndOfDataDecoderException();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData next() {
        a();
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
        return this.j;
    }

    private void b() {
        if (this.i != HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE && this.i != HttpPostRequestDecoder.MultiPartStatus.EPILOGUE) {
            d();
        } else if (this.d) {
            this.i = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
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

    private void c() {
        HttpPostRequestDecoder.ErrorDataDecoderException e;
        IOException e2;
        int readerIndex = this.g.readerIndex();
        if (this.i == HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED) {
            this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
        }
        int i = readerIndex;
        boolean z = true;
        while (this.g.isReadable() && z) {
            try {
                char readUnsignedByte = (char) this.g.readUnsignedByte();
                readerIndex++;
                switch (this.i) {
                    case DISPOSITION:
                        if (readUnsignedByte != '=') {
                            if (readUnsignedByte != '&') {
                                break;
                            } else {
                                this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                                this.j = this.a.createAttribute(this.b, a(this.g.toString(i, (readerIndex - 1) - i, this.c), this.c));
                                this.j.setValue("");
                                addHttpData(this.j);
                                this.j = null;
                                z = true;
                                i = readerIndex;
                                break;
                            }
                        } else {
                            this.i = HttpPostRequestDecoder.MultiPartStatus.FIELD;
                            this.j = this.a.createAttribute(this.b, a(this.g.toString(i, (readerIndex - 1) - i, this.c), this.c));
                            i = readerIndex;
                            break;
                        }
                    case FIELD:
                        if (readUnsignedByte != '&') {
                            if (readUnsignedByte != '\r') {
                                if (readUnsignedByte != '\n') {
                                    break;
                                } else {
                                    this.i = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                    a(this.g.copy(i, (readerIndex - 1) - i));
                                    i = readerIndex;
                                    z = false;
                                    break;
                                }
                            } else if (!this.g.isReadable()) {
                                readerIndex--;
                                break;
                            } else {
                                readerIndex++;
                                if (((char) this.g.readUnsignedByte()) == '\n') {
                                    this.i = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                    a(this.g.copy(i, (readerIndex - 2) - i));
                                    i = readerIndex;
                                    z = false;
                                    break;
                                } else {
                                    throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad end of line");
                                }
                            }
                        } else {
                            this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                            a(this.g.copy(i, (readerIndex - 1) - i));
                            z = true;
                            i = readerIndex;
                            break;
                        }
                    default:
                        z = false;
                        break;
                }
            } catch (HttpPostRequestDecoder.ErrorDataDecoderException e3) {
                e = e3;
            } catch (IOException e4) {
                e2 = e4;
            }
        }
        try {
            if (this.d && this.j != null) {
                if (readerIndex > i) {
                    a(this.g.copy(i, readerIndex - i));
                } else if (!this.j.isCompleted()) {
                    a(Unpooled.EMPTY_BUFFER);
                }
                this.i = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
                this.g.readerIndex(readerIndex);
            } else if (!z || this.j == null) {
                this.g.readerIndex(i);
            } else {
                if (this.i == HttpPostRequestDecoder.MultiPartStatus.FIELD) {
                    this.j.addContent(this.g.copy(i, readerIndex - i), false);
                } else {
                    readerIndex = i;
                }
                this.g.readerIndex(readerIndex);
            }
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException e5) {
            e = e5;
            i = readerIndex;
            this.g.readerIndex(i);
            throw e;
        } catch (IOException e6) {
            e2 = e6;
            i = readerIndex;
            this.g.readerIndex(i);
            throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
        }
    }

    private void d() {
        HttpPostRequestDecoder.ErrorDataDecoderException e;
        IOException e2;
        IllegalArgumentException e3;
        boolean z;
        int i;
        try {
            HttpPostBodyUtil.b bVar = new HttpPostBodyUtil.b(this.g);
            int readerIndex = this.g.readerIndex();
            if (this.i == HttpPostRequestDecoder.MultiPartStatus.NOTSTARTED) {
                this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
            }
            int i2 = readerIndex;
            while (true) {
                try {
                    if (bVar.c < bVar.e) {
                        byte[] bArr = bVar.a;
                        int i3 = bVar.c;
                        bVar.c = i3 + 1;
                        char c = (char) (bArr[i3] & 255);
                        readerIndex++;
                        switch (this.i) {
                            case DISPOSITION:
                                if (c != '=') {
                                    if (c != '&') {
                                        break;
                                    } else {
                                        this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                                        this.j = this.a.createAttribute(this.b, a(this.g.toString(i2, (readerIndex - 1) - i2, this.c), this.c));
                                        this.j.setValue("");
                                        addHttpData(this.j);
                                        this.j = null;
                                        i2 = readerIndex;
                                        break;
                                    }
                                } else {
                                    this.i = HttpPostRequestDecoder.MultiPartStatus.FIELD;
                                    this.j = this.a.createAttribute(this.b, a(this.g.toString(i2, (readerIndex - 1) - i2, this.c), this.c));
                                    i2 = readerIndex;
                                    break;
                                }
                            case FIELD:
                                if (c != '&') {
                                    if (c != '\r') {
                                        if (c != '\n') {
                                            break;
                                        } else {
                                            this.i = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                            bVar.a(0);
                                            a(this.g.copy(i2, (readerIndex - 1) - i2));
                                            i = readerIndex;
                                            z = false;
                                            break;
                                        }
                                    } else if (bVar.c >= bVar.e) {
                                        if (bVar.e <= 0) {
                                            break;
                                        } else {
                                            readerIndex--;
                                            break;
                                        }
                                    } else {
                                        byte[] bArr2 = bVar.a;
                                        int i4 = bVar.c;
                                        bVar.c = i4 + 1;
                                        readerIndex++;
                                        if (((char) (bArr2[i4] & 255)) == '\n') {
                                            this.i = HttpPostRequestDecoder.MultiPartStatus.PREEPILOGUE;
                                            bVar.a(0);
                                            a(this.g.copy(i2, (readerIndex - 2) - i2));
                                            i = readerIndex;
                                            z = false;
                                            break;
                                        } else {
                                            bVar.a(0);
                                            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad end of line");
                                        }
                                    }
                                } else {
                                    this.i = HttpPostRequestDecoder.MultiPartStatus.DISPOSITION;
                                    a(this.g.copy(i2, (readerIndex - 1) - i2));
                                    i2 = readerIndex;
                                    break;
                                }
                            default:
                                bVar.a(0);
                                z = false;
                                i = readerIndex;
                                readerIndex = i2;
                                break;
                        }
                    } else {
                        z = true;
                        i = readerIndex;
                        readerIndex = i2;
                    }
                } catch (HttpPostRequestDecoder.ErrorDataDecoderException e4) {
                    e = e4;
                } catch (IOException e5) {
                    e2 = e5;
                } catch (IllegalArgumentException e6) {
                    e3 = e6;
                }
            }
            try {
                if (this.d && this.j != null) {
                    if (i > readerIndex) {
                        a(this.g.copy(readerIndex, i - readerIndex));
                    } else if (!this.j.isCompleted()) {
                        a(Unpooled.EMPTY_BUFFER);
                    }
                    this.i = HttpPostRequestDecoder.MultiPartStatus.EPILOGUE;
                    this.g.readerIndex(i);
                } else if (!z || this.j == null) {
                    this.g.readerIndex(readerIndex);
                } else {
                    if (this.i == HttpPostRequestDecoder.MultiPartStatus.FIELD) {
                        this.j.addContent(this.g.copy(readerIndex, i - readerIndex), false);
                    } else {
                        i = readerIndex;
                    }
                    this.g.readerIndex(i);
                }
            } catch (HttpPostRequestDecoder.ErrorDataDecoderException e7) {
                e = e7;
                i2 = readerIndex;
                this.g.readerIndex(i2);
                throw e;
            } catch (IOException e8) {
                e2 = e8;
                i2 = readerIndex;
                this.g.readerIndex(i2);
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e2);
            } catch (IllegalArgumentException e9) {
                e3 = e9;
                i2 = readerIndex;
                this.g.readerIndex(i2);
                throw new HttpPostRequestDecoder.ErrorDataDecoderException(e3);
            }
        } catch (HttpPostBodyUtil.a unused) {
            c();
        }
    }

    private void a(ByteBuf byteBuf) throws IOException {
        this.j.addContent(byteBuf, true);
        this.j.setValue(a(this.j.getByteBuf().toString(this.c), this.c));
        addHttpData(this.j);
        this.j = null;
    }

    private static String a(String str, Charset charset) {
        try {
            return QueryStringDecoder.decodeComponent(str, charset);
        } catch (IllegalArgumentException e) {
            throw new HttpPostRequestDecoder.ErrorDataDecoderException("Bad string: '" + str + '\'', e);
        }
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void destroy() {
        a();
        cleanFiles();
        this.k = true;
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
        a();
        this.a.cleanRequestHttpData(this.b);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData) {
        a();
        this.a.removeHttpDataFromClean(this.b, interfaceHttpData);
    }
}
