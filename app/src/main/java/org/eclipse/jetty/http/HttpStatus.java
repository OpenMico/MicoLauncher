package org.eclipse.jetty.http;

/* loaded from: classes5.dex */
public class HttpStatus {
    public static final int ACCEPTED_202 = 202;
    public static final int BAD_GATEWAY_502 = 502;
    public static final int BAD_REQUEST_400 = 400;
    public static final int CONFLICT_409 = 409;
    public static final int CONTINUE_100 = 100;
    public static final int CREATED_201 = 201;
    public static final int EXPECTATION_FAILED_417 = 417;
    public static final int FAILED_DEPENDENCY_424 = 424;
    public static final int FORBIDDEN_403 = 403;
    public static final int FOUND_302 = 302;
    public static final int GATEWAY_TIMEOUT_504 = 504;
    public static final int GONE_410 = 410;
    public static final int HTTP_VERSION_NOT_SUPPORTED_505 = 505;
    public static final int INSUFFICIENT_STORAGE_507 = 507;
    public static final int INTERNAL_SERVER_ERROR_500 = 500;
    public static final int LENGTH_REQUIRED_411 = 411;
    public static final int LOCKED_423 = 423;
    public static final int MAX_CODE = 507;
    public static final int METHOD_NOT_ALLOWED_405 = 405;
    public static final int MOVED_PERMANENTLY_301 = 301;
    public static final int MOVED_TEMPORARILY_302 = 302;
    public static final int MULTIPLE_CHOICES_300 = 300;
    public static final int MULTI_STATUS_207 = 207;
    public static final int NON_AUTHORITATIVE_INFORMATION_203 = 203;
    public static final int NOT_ACCEPTABLE_406 = 406;
    public static final int NOT_FOUND_404 = 404;
    public static final int NOT_IMPLEMENTED_501 = 501;
    public static final int NOT_MODIFIED_304 = 304;
    public static final int NO_CONTENT_204 = 204;
    public static final int OK_200 = 200;
    public static final int PARTIAL_CONTENT_206 = 206;
    public static final int PAYMENT_REQUIRED_402 = 402;
    public static final int PRECONDITION_FAILED_412 = 412;
    public static final int PROCESSING_102 = 102;
    public static final int PROXY_AUTHENTICATION_REQUIRED_407 = 407;
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE_416 = 416;
    public static final int REQUEST_ENTITY_TOO_LARGE_413 = 413;
    public static final int REQUEST_TIMEOUT_408 = 408;
    public static final int REQUEST_URI_TOO_LONG_414 = 414;
    public static final int RESET_CONTENT_205 = 205;
    public static final int SEE_OTHER_303 = 303;
    public static final int SERVICE_UNAVAILABLE_503 = 503;
    public static final int SWITCHING_PROTOCOLS_101 = 101;
    public static final int TEMPORARY_REDIRECT_307 = 307;
    public static final int UNAUTHORIZED_401 = 401;
    public static final int UNPROCESSABLE_ENTITY_422 = 422;
    public static final int UNSUPPORTED_MEDIA_TYPE_415 = 415;
    public static final int USE_PROXY_305 = 305;
    private static final Code[] codeMap = new Code[508];

    public static boolean isClientError(int i) {
        return 400 <= i && i <= 499;
    }

    public static boolean isInformational(int i) {
        return 100 <= i && i <= 199;
    }

    public static boolean isRedirection(int i) {
        return 300 <= i && i <= 399;
    }

    public static boolean isServerError(int i) {
        return 500 <= i && i <= 599;
    }

    public static boolean isSuccess(int i) {
        return 200 <= i && i <= 299;
    }

    static {
        Code[] values = Code.values();
        for (Code code : values) {
            codeMap[code._code] = code;
        }
    }

    /* loaded from: classes5.dex */
    public enum Code {
        CONTINUE(100, "Continue"),
        SWITCHING_PROTOCOLS(101, "Switching Protocols"),
        PROCESSING(102, "Processing"),
        OK(200, "OK"),
        CREATED(201, "Created"),
        ACCEPTED(202, "Accepted"),
        NON_AUTHORITATIVE_INFORMATION(203, "Non Authoritative Information"),
        NO_CONTENT(204, "No Content"),
        RESET_CONTENT(205, "Reset Content"),
        PARTIAL_CONTENT(206, "Partial Content"),
        MULTI_STATUS(HttpStatus.MULTI_STATUS_207, "Multi-Status"),
        MULTIPLE_CHOICES(300, "Multiple Choices"),
        MOVED_PERMANENTLY(301, "Moved Permanently"),
        MOVED_TEMPORARILY(302, "Moved Temporarily"),
        FOUND(302, "Found"),
        SEE_OTHER(303, "See Other"),
        NOT_MODIFIED(304, "Not Modified"),
        USE_PROXY(305, "Use Proxy"),
        TEMPORARY_REDIRECT(307, "Temporary Redirect"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        PAYMENT_REQUIRED(402, "Payment Required"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found"),
        METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
        NOT_ACCEPTABLE(406, "Not Acceptable"),
        PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
        REQUEST_TIMEOUT(408, "Request Timeout"),
        CONFLICT(409, "Conflict"),
        GONE(410, "Gone"),
        LENGTH_REQUIRED(411, "Length Required"),
        PRECONDITION_FAILED(412, "Precondition Failed"),
        REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
        REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
        UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
        REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
        EXPECTATION_FAILED(417, "Expectation Failed"),
        UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY_422, "Unprocessable Entity"),
        LOCKED(HttpStatus.LOCKED_423, "Locked"),
        FAILED_DEPENDENCY(HttpStatus.FAILED_DEPENDENCY_424, "Failed Dependency"),
        INTERNAL_SERVER_ERROR(500, "Server Error"),
        NOT_IMPLEMENTED(501, "Not Implemented"),
        BAD_GATEWAY(502, "Bad Gateway"),
        SERVICE_UNAVAILABLE(503, "Service Unavailable"),
        GATEWAY_TIMEOUT(504, "Gateway Timeout"),
        HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
        INSUFFICIENT_STORAGE(507, "Insufficient Storage");
        
        private final int _code;
        private final String _message;

        Code(int i, String str) {
            this._code = i;
            this._message = str;
        }

        public int getCode() {
            return this._code;
        }

        public String getMessage() {
            return this._message;
        }

        public boolean equals(int i) {
            return this._code == i;
        }

        @Override // java.lang.Enum
        public String toString() {
            return String.format("[%03d %s]", Integer.valueOf(this._code), getMessage());
        }

        public boolean isInformational() {
            return HttpStatus.isInformational(this._code);
        }

        public boolean isSuccess() {
            return HttpStatus.isSuccess(this._code);
        }

        public boolean isRedirection() {
            return HttpStatus.isRedirection(this._code);
        }

        public boolean isClientError() {
            return HttpStatus.isClientError(this._code);
        }

        public boolean isServerError() {
            return HttpStatus.isServerError(this._code);
        }
    }

    public static Code getCode(int i) {
        if (i <= 507) {
            return codeMap[i];
        }
        return null;
    }

    public static String getMessage(int i) {
        Code code = getCode(i);
        if (code != null) {
            return code.getMessage();
        }
        return Integer.toString(i);
    }
}
