package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;

/* loaded from: classes4.dex */
final class HttpPostBodyUtil {

    /* loaded from: classes4.dex */
    public enum TransferEncodingMechanism {
        BIT7("7bit"),
        BIT8("8bit"),
        BINARY(HttpHeaders.Values.BINARY);
        
        private final String value;

        TransferEncodingMechanism(String str) {
            this.value = str;
        }

        public String value() {
            return this.value;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.value;
        }
    }

    /* loaded from: classes4.dex */
    static class a extends Exception {
        private static final long serialVersionUID = -630418804938699495L;

        a() {
        }
    }

    /* loaded from: classes4.dex */
    static class b {
        byte[] a;
        int b;
        int c;
        int d;
        int e;
        ByteBuf f;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(ByteBuf byteBuf) throws a {
            if (byteBuf.hasArray()) {
                this.f = byteBuf;
                this.a = byteBuf.array();
                this.b = byteBuf.readerIndex();
                int arrayOffset = byteBuf.arrayOffset() + this.b;
                this.c = arrayOffset;
                this.d = arrayOffset;
                this.e = byteBuf.arrayOffset() + byteBuf.writerIndex();
                return;
            }
            throw new a();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(int i) {
            this.c -= i;
            this.b = b(this.c);
            this.f.readerIndex(this.b);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int b(int i) {
            return (i - this.d) + this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(String str, int i) {
        while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
            i++;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(String str) {
        int length = str.length();
        while (length > 0 && Character.isWhitespace(str.charAt(length - 1))) {
            length--;
        }
        return length;
    }
}
