package io.netty.handler.codec.http;

import io.netty.util.AsciiString;

/* loaded from: classes4.dex */
public enum HttpStatusClass {
    INFORMATIONAL(100, 200, "Informational"),
    SUCCESS(200, 300, "Success"),
    REDIRECTION(300, 400, "Redirection"),
    CLIENT_ERROR(400, 500, "Client Error"),
    SERVER_ERROR(500, 600, "Server Error"),
    UNKNOWN(0, 0, "Unknown Status") {
        @Override // io.netty.handler.codec.http.HttpStatusClass
        public boolean contains(int i) {
            return i < 100 || i >= 600;
        }
    };
    
    private final AsciiString defaultReasonPhrase;
    private final int max;
    private final int min;

    public static HttpStatusClass valueOf(int i) {
        if (INFORMATIONAL.contains(i)) {
            return INFORMATIONAL;
        }
        if (SUCCESS.contains(i)) {
            return SUCCESS;
        }
        if (REDIRECTION.contains(i)) {
            return REDIRECTION;
        }
        if (CLIENT_ERROR.contains(i)) {
            return CLIENT_ERROR;
        }
        if (SERVER_ERROR.contains(i)) {
            return SERVER_ERROR;
        }
        return UNKNOWN;
    }

    HttpStatusClass(int i, int i2, String str) {
        this.min = i;
        this.max = i2;
        this.defaultReasonPhrase = new AsciiString(str);
    }

    public boolean contains(int i) {
        return i >= this.min && i < this.max;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AsciiString a() {
        return this.defaultReasonPhrase;
    }
}
