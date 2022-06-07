package io.netty.handler.codec.http;

import androidx.core.app.FrameMetricsAggregator;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.infra.galaxy.fds.Common;
import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import okhttp3.internal.http.StatusLine;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

/* loaded from: classes4.dex */
public class HttpResponseStatus implements Comparable<HttpResponseStatus> {
    private final int a;
    private final AsciiString b;
    private HttpStatusClass c;
    private final String d;
    private final byte[] e;
    public static final HttpResponseStatus CONTINUE = a(100, "Continue");
    public static final HttpResponseStatus SWITCHING_PROTOCOLS = a(101, "Switching Protocols");
    public static final HttpResponseStatus PROCESSING = a(102, "Processing");
    public static final HttpResponseStatus OK = a(200, "OK");
    public static final HttpResponseStatus CREATED = a(201, "Created");
    public static final HttpResponseStatus ACCEPTED = a(202, "Accepted");
    public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = a(203, "Non-Authoritative Information");
    public static final HttpResponseStatus NO_CONTENT = a(204, "No Content");
    public static final HttpResponseStatus RESET_CONTENT = a(205, "Reset Content");
    public static final HttpResponseStatus PARTIAL_CONTENT = a(206, "Partial Content");
    public static final HttpResponseStatus MULTI_STATUS = a(HttpStatus.MULTI_STATUS_207, "Multi-Status");
    public static final HttpResponseStatus MULTIPLE_CHOICES = a(300, "Multiple Choices");
    public static final HttpResponseStatus MOVED_PERMANENTLY = a(301, "Moved Permanently");
    public static final HttpResponseStatus FOUND = a(302, "Found");
    public static final HttpResponseStatus SEE_OTHER = a(303, "See Other");
    public static final HttpResponseStatus NOT_MODIFIED = a(304, "Not Modified");
    public static final HttpResponseStatus USE_PROXY = a(305, "Use Proxy");
    public static final HttpResponseStatus TEMPORARY_REDIRECT = a(307, "Temporary Redirect");
    public static final HttpResponseStatus BAD_REQUEST = a(400, "Bad Request");
    public static final HttpResponseStatus UNAUTHORIZED = a(401, "Unauthorized");
    public static final HttpResponseStatus PAYMENT_REQUIRED = a(402, "Payment Required");
    public static final HttpResponseStatus FORBIDDEN = a(403, "Forbidden");
    public static final HttpResponseStatus NOT_FOUND = a(404, "Not Found");
    public static final HttpResponseStatus METHOD_NOT_ALLOWED = a(405, "Method Not Allowed");
    public static final HttpResponseStatus NOT_ACCEPTABLE = a(406, "Not Acceptable");
    public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = a(407, "Proxy Authentication Required");
    public static final HttpResponseStatus REQUEST_TIMEOUT = a(408, "Request Timeout");
    public static final HttpResponseStatus CONFLICT = a(409, "Conflict");
    public static final HttpResponseStatus GONE = a(410, "Gone");
    public static final HttpResponseStatus LENGTH_REQUIRED = a(411, "Length Required");
    public static final HttpResponseStatus PRECONDITION_FAILED = a(412, "Precondition Failed");
    public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = a(413, "Request Entity Too Large");
    public static final HttpResponseStatus REQUEST_URI_TOO_LONG = a(414, "Request-URI Too Long");
    public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = a(415, "Unsupported Media Type");
    public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = a(416, "Requested Range Not Satisfiable");
    public static final HttpResponseStatus EXPECTATION_FAILED = a(417, "Expectation Failed");
    public static final HttpResponseStatus MISDIRECTED_REQUEST = a(StatusLine.HTTP_MISDIRECTED_REQUEST, "Misdirected Request");
    public static final HttpResponseStatus UNPROCESSABLE_ENTITY = a(HttpStatus.UNPROCESSABLE_ENTITY_422, "Unprocessable Entity");
    public static final HttpResponseStatus LOCKED = a(HttpStatus.LOCKED_423, "Locked");
    public static final HttpResponseStatus FAILED_DEPENDENCY = a(HttpStatus.FAILED_DEPENDENCY_424, "Failed Dependency");
    public static final HttpResponseStatus UNORDERED_COLLECTION = a(425, "Unordered Collection");
    public static final HttpResponseStatus UPGRADE_REQUIRED = a(StdStatuses.UPGRADE_REQUIRED, "Upgrade Required");
    public static final HttpResponseStatus PRECONDITION_REQUIRED = a(428, "Precondition Required");
    public static final HttpResponseStatus TOO_MANY_REQUESTS = a(Common.HTTP_STATUS_TOO_MANY_REQUESTS, "Too Many Requests");
    public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = a(431, "Request Header Fields Too Large");
    public static final HttpResponseStatus INTERNAL_SERVER_ERROR = a(500, "Internal Server Error");
    public static final HttpResponseStatus NOT_IMPLEMENTED = a(501, "Not Implemented");
    public static final HttpResponseStatus BAD_GATEWAY = a(502, "Bad Gateway");
    public static final HttpResponseStatus SERVICE_UNAVAILABLE = a(503, "Service Unavailable");
    public static final HttpResponseStatus GATEWAY_TIMEOUT = a(504, "Gateway Timeout");
    public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = a(505, "HTTP Version Not Supported");
    public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = a(506, "Variant Also Negotiates");
    public static final HttpResponseStatus INSUFFICIENT_STORAGE = a(507, "Insufficient Storage");
    public static final HttpResponseStatus NOT_EXTENDED = a(510, "Not Extended");
    public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED = a(FrameMetricsAggregator.EVERY_DURATION, "Network Authentication Required");

    private static HttpResponseStatus a(int i, String str) {
        return new HttpResponseStatus(i, str, true);
    }

    public static HttpResponseStatus valueOf(int i) {
        if (i == 307) {
            return TEMPORARY_REDIRECT;
        }
        if (i == 431) {
            return REQUEST_HEADER_FIELDS_TOO_LARGE;
        }
        switch (i) {
            case 100:
                return CONTINUE;
            case 101:
                return SWITCHING_PROTOCOLS;
            case 102:
                return PROCESSING;
            default:
                switch (i) {
                    case 200:
                        return OK;
                    case 201:
                        return CREATED;
                    case 202:
                        return ACCEPTED;
                    case 203:
                        return NON_AUTHORITATIVE_INFORMATION;
                    case 204:
                        return NO_CONTENT;
                    case 205:
                        return RESET_CONTENT;
                    case 206:
                        return PARTIAL_CONTENT;
                    case HttpStatus.MULTI_STATUS_207 /* 207 */:
                        return MULTI_STATUS;
                    default:
                        switch (i) {
                            case 300:
                                return MULTIPLE_CHOICES;
                            case 301:
                                return MOVED_PERMANENTLY;
                            case 302:
                                return FOUND;
                            case 303:
                                return SEE_OTHER;
                            case 304:
                                return NOT_MODIFIED;
                            case 305:
                                return USE_PROXY;
                            default:
                                switch (i) {
                                    case 400:
                                        return BAD_REQUEST;
                                    case 401:
                                        return UNAUTHORIZED;
                                    case 402:
                                        return PAYMENT_REQUIRED;
                                    case 403:
                                        return FORBIDDEN;
                                    case 404:
                                        return NOT_FOUND;
                                    case 405:
                                        return METHOD_NOT_ALLOWED;
                                    case 406:
                                        return NOT_ACCEPTABLE;
                                    case 407:
                                        return PROXY_AUTHENTICATION_REQUIRED;
                                    case 408:
                                        return REQUEST_TIMEOUT;
                                    case 409:
                                        return CONFLICT;
                                    case 410:
                                        return GONE;
                                    case 411:
                                        return LENGTH_REQUIRED;
                                    case 412:
                                        return PRECONDITION_FAILED;
                                    case 413:
                                        return REQUEST_ENTITY_TOO_LARGE;
                                    case 414:
                                        return REQUEST_URI_TOO_LONG;
                                    case 415:
                                        return UNSUPPORTED_MEDIA_TYPE;
                                    case 416:
                                        return REQUESTED_RANGE_NOT_SATISFIABLE;
                                    case 417:
                                        return EXPECTATION_FAILED;
                                    default:
                                        switch (i) {
                                            case StatusLine.HTTP_MISDIRECTED_REQUEST /* 421 */:
                                                return MISDIRECTED_REQUEST;
                                            case HttpStatus.UNPROCESSABLE_ENTITY_422 /* 422 */:
                                                return UNPROCESSABLE_ENTITY;
                                            case HttpStatus.LOCKED_423 /* 423 */:
                                                return LOCKED;
                                            case HttpStatus.FAILED_DEPENDENCY_424 /* 424 */:
                                                return FAILED_DEPENDENCY;
                                            case 425:
                                                return UNORDERED_COLLECTION;
                                            case StdStatuses.UPGRADE_REQUIRED /* 426 */:
                                                return UPGRADE_REQUIRED;
                                            default:
                                                switch (i) {
                                                    case 428:
                                                        return PRECONDITION_REQUIRED;
                                                    case Common.HTTP_STATUS_TOO_MANY_REQUESTS /* 429 */:
                                                        return TOO_MANY_REQUESTS;
                                                    default:
                                                        switch (i) {
                                                            case 500:
                                                                return INTERNAL_SERVER_ERROR;
                                                            case 501:
                                                                return NOT_IMPLEMENTED;
                                                            case 502:
                                                                return BAD_GATEWAY;
                                                            case 503:
                                                                return SERVICE_UNAVAILABLE;
                                                            case 504:
                                                                return GATEWAY_TIMEOUT;
                                                            case 505:
                                                                return HTTP_VERSION_NOT_SUPPORTED;
                                                            case 506:
                                                                return VARIANT_ALSO_NEGOTIATES;
                                                            case 507:
                                                                return INSUFFICIENT_STORAGE;
                                                            default:
                                                                switch (i) {
                                                                    case 510:
                                                                        return NOT_EXTENDED;
                                                                    case FrameMetricsAggregator.EVERY_DURATION /* 511 */:
                                                                        return NETWORK_AUTHENTICATION_REQUIRED;
                                                                    default:
                                                                        return new HttpResponseStatus(i);
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public static HttpResponseStatus parseLine(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        try {
            int indexOf = charSequence2.indexOf(32);
            if (indexOf == -1) {
                return valueOf(Integer.parseInt(charSequence2));
            }
            int parseInt = Integer.parseInt(charSequence2.substring(0, indexOf));
            String substring = charSequence2.substring(indexOf + 1);
            HttpResponseStatus valueOf = valueOf(parseInt);
            return valueOf.reasonPhrase().contentEquals(substring) ? valueOf : new HttpResponseStatus(parseInt, substring);
        } catch (Exception e) {
            throw new IllegalArgumentException("malformed status line: " + charSequence2, e);
        }
    }

    /* loaded from: classes4.dex */
    private static final class a implements ByteProcessor {
        private final AsciiString a;
        private int b;
        private int c;
        private HttpResponseStatus d;

        public a(AsciiString asciiString) {
            this.a = asciiString;
        }

        @Override // io.netty.util.ByteProcessor
        public boolean process(byte b) {
            switch (this.c) {
                case 1:
                    a(this.b);
                    this.c = 2;
                    return false;
                case 0:
                    if (b == 32) {
                        this.c = 1;
                        break;
                    }
                    break;
            }
            this.b++;
            return true;
        }

        private void a(int i) {
            int parseInt = this.a.parseInt(0, i);
            this.d = HttpResponseStatus.valueOf(parseInt);
            if (i < this.a.length()) {
                AsciiString asciiString = this.a;
                String asciiString2 = asciiString.toString(i + 1, asciiString.length());
                if (!this.d.reasonPhrase().contentEquals(asciiString2)) {
                    this.d = new HttpResponseStatus(parseInt, asciiString2);
                }
            }
        }

        public HttpResponseStatus a() {
            if (this.c <= 1) {
                a(this.a.length());
                this.c = 3;
            }
            return this.d;
        }
    }

    public static HttpResponseStatus parseLine(AsciiString asciiString) {
        try {
            a aVar = new a(asciiString);
            asciiString.forEachByte(aVar);
            HttpResponseStatus a2 = aVar.a();
            if (a2 != null) {
                return a2;
            }
            throw new IllegalArgumentException("unable to get status after parsing input");
        } catch (Exception e) {
            throw new IllegalArgumentException("malformed status line: " + ((Object) asciiString), e);
        }
    }

    private HttpResponseStatus(int i) {
        this(i, ((Object) HttpStatusClass.valueOf(i).a()) + " (" + i + ')', false);
    }

    public HttpResponseStatus(int i, String str) {
        this(i, str, false);
    }

    private HttpResponseStatus(int i, String str, boolean z) {
        if (i < 0) {
            throw new IllegalArgumentException("code: " + i + " (expected: 0+)");
        } else if (str != null) {
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt = str.charAt(i2);
                if (charAt == '\n' || charAt == '\r') {
                    throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + str);
                }
            }
            this.a = i;
            this.b = new AsciiString(Integer.toString(i));
            this.d = str;
            if (z) {
                this.e = (i + StringUtils.SPACE + str).getBytes(CharsetUtil.US_ASCII);
                return;
            }
            this.e = null;
        } else {
            throw new NullPointerException("reasonPhrase");
        }
    }

    public int code() {
        return this.a;
    }

    public AsciiString codeAsText() {
        return this.b;
    }

    public String reasonPhrase() {
        return this.d;
    }

    public HttpStatusClass codeClass() {
        HttpStatusClass httpStatusClass = this.c;
        if (httpStatusClass != null) {
            return httpStatusClass;
        }
        HttpStatusClass valueOf = HttpStatusClass.valueOf(this.a);
        this.c = valueOf;
        return valueOf;
    }

    public int hashCode() {
        return code();
    }

    public boolean equals(Object obj) {
        return (obj instanceof HttpResponseStatus) && code() == ((HttpResponseStatus) obj).code();
    }

    public int compareTo(HttpResponseStatus httpResponseStatus) {
        return code() - httpResponseStatus.code();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.d.length() + 5);
        sb.append(this.a);
        sb.append(' ');
        sb.append(this.d);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteBuf byteBuf) {
        byte[] bArr = this.e;
        if (bArr == null) {
            HttpUtil.a(String.valueOf(code()), byteBuf);
            byteBuf.writeByte(32);
            HttpUtil.a(String.valueOf(reasonPhrase()), byteBuf);
            return;
        }
        byteBuf.writeBytes(bArr);
    }
}
