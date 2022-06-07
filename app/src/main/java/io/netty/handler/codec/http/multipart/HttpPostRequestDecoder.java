package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.List;

/* loaded from: classes4.dex */
public class HttpPostRequestDecoder implements InterfaceHttpPostRequestDecoder {
    private final InterfaceHttpPostRequestDecoder a;

    /* loaded from: classes4.dex */
    public static class EndOfDataDecoderException extends DecoderException {
        private static final long serialVersionUID = 1336267941020800769L;
    }

    /* loaded from: classes4.dex */
    public enum MultiPartStatus {
        NOTSTARTED,
        PREAMBLE,
        HEADERDELIMITER,
        DISPOSITION,
        FIELD,
        FILEUPLOAD,
        MIXEDPREAMBLE,
        MIXEDDELIMITER,
        MIXEDDISPOSITION,
        MIXEDFILEUPLOAD,
        MIXEDCLOSEDELIMITER,
        CLOSEDELIMITER,
        PREEPILOGUE,
        EPILOGUE
    }

    public HttpPostRequestDecoder(HttpRequest httpRequest) {
        this(new DefaultHttpDataFactory(16384L), httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest) {
        this(httpDataFactory, httpRequest, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostRequestDecoder(HttpDataFactory httpDataFactory, HttpRequest httpRequest, Charset charset) {
        if (httpDataFactory == null) {
            throw new NullPointerException("factory");
        } else if (httpRequest == null) {
            throw new NullPointerException("request");
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else if (isMultipart(httpRequest)) {
            this.a = new HttpPostMultipartRequestDecoder(httpDataFactory, httpRequest, charset);
        } else {
            this.a = new HttpPostStandardRequestDecoder(httpDataFactory, httpRequest, charset);
        }
    }

    public static boolean isMultipart(HttpRequest httpRequest) {
        return httpRequest.headers().contains(HttpHeaderNames.CONTENT_TYPE) && getMultipartDataBoundary(httpRequest.headers().get(HttpHeaderNames.CONTENT_TYPE)) != null;
    }

    public static String[] getMultipartDataBoundary(String str) {
        char c;
        char c2;
        String substringAfter;
        String[] a = a(str);
        if (!a[0].toLowerCase().startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString())) {
            return null;
        }
        if (a[1].toLowerCase().startsWith(HttpHeaderValues.BOUNDARY.toString())) {
            c2 = 1;
            c = 2;
        } else if (!a[2].toLowerCase().startsWith(HttpHeaderValues.BOUNDARY.toString())) {
            return null;
        } else {
            c = 1;
            c2 = 2;
        }
        String substringAfter2 = StringUtil.substringAfter(a[c2], '=');
        if (substringAfter2 != null) {
            if (substringAfter2.charAt(0) == '\"') {
                String trim = substringAfter2.trim();
                int length = trim.length() - 1;
                if (trim.charAt(length) == '\"') {
                    substringAfter2 = trim.substring(1, length);
                }
            }
            if (!a[c].toLowerCase().startsWith(HttpHeaderValues.CHARSET.toString()) || (substringAfter = StringUtil.substringAfter(a[c], '=')) == null) {
                return new String[]{"--" + substringAfter2};
            }
            return new String[]{"--" + substringAfter2, substringAfter};
        }
        throw new ErrorDataDecoderException("Needs a boundary value");
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean isMultipart() {
        return this.a.isMultipart();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void setDiscardThreshold(int i) {
        this.a.setDiscardThreshold(i);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public int getDiscardThreshold() {
        return this.a.getDiscardThreshold();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas() {
        return this.a.getBodyHttpDatas();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public List<InterfaceHttpData> getBodyHttpDatas(String str) {
        return this.a.getBodyHttpDatas(str);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData getBodyHttpData(String str) {
        return this.a.getBodyHttpData(str);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpPostRequestDecoder offer(HttpContent httpContent) {
        return this.a.offer(httpContent);
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData next() {
        return this.a.next();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public InterfaceHttpData currentPartialHttpData() {
        return this.a.currentPartialHttpData();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void destroy() {
        this.a.destroy();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void cleanFiles() {
        this.a.cleanFiles();
    }

    @Override // io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder
    public void removeHttpDataFromClean(InterfaceHttpData interfaceHttpData) {
        this.a.removeHttpDataFromClean(interfaceHttpData);
    }

    private static String[] a(String str) {
        int a = HttpPostBodyUtil.a(str, 0);
        int indexOf = str.indexOf(59);
        if (indexOf == -1) {
            return new String[]{str, "", ""};
        }
        int a2 = HttpPostBodyUtil.a(str, indexOf + 1);
        if (str.charAt(indexOf - 1) == ' ') {
            indexOf--;
        }
        int indexOf2 = str.indexOf(59, a2);
        if (indexOf2 == -1) {
            return new String[]{str.substring(a, indexOf), str.substring(a2, HttpPostBodyUtil.a(str)), ""};
        }
        int a3 = HttpPostBodyUtil.a(str, indexOf2 + 1);
        if (str.charAt(indexOf2 - 1) == ' ') {
            indexOf2--;
        }
        return new String[]{str.substring(a, indexOf), str.substring(a2, indexOf2), str.substring(a3, HttpPostBodyUtil.a(str))};
    }

    /* loaded from: classes4.dex */
    public static class NotEnoughDataDecoderException extends DecoderException {
        private static final long serialVersionUID = -7846841864603865638L;

        public NotEnoughDataDecoderException() {
        }

        public NotEnoughDataDecoderException(String str) {
            super(str);
        }

        public NotEnoughDataDecoderException(Throwable th) {
            super(th);
        }

        public NotEnoughDataDecoderException(String str, Throwable th) {
            super(str, th);
        }
    }

    /* loaded from: classes4.dex */
    public static class ErrorDataDecoderException extends DecoderException {
        private static final long serialVersionUID = 5020247425493164465L;

        public ErrorDataDecoderException() {
        }

        public ErrorDataDecoderException(String str) {
            super(str);
        }

        public ErrorDataDecoderException(Throwable th) {
            super(th);
        }

        public ErrorDataDecoderException(String str, Throwable th) {
            super(str, th);
        }
    }
}
