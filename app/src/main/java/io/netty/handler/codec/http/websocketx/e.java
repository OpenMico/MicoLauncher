package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.FastThreadLocal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: WebSocketUtil.java */
/* loaded from: classes4.dex */
final class e {
    private static final FastThreadLocal<MessageDigest> a = new FastThreadLocal<MessageDigest>() { // from class: io.netty.handler.codec.http.websocketx.e.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public MessageDigest initialValue() throws Exception {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException unused) {
                throw new InternalError("MD5 not supported on this platform - Outdated?");
            }
        }
    };
    private static final FastThreadLocal<MessageDigest> b = new FastThreadLocal<MessageDigest>() { // from class: io.netty.handler.codec.http.websocketx.e.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public MessageDigest initialValue() throws Exception {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException unused) {
                throw new InternalError("SHA-1 not supported on this platform - Outdated?");
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] a(byte[] bArr) {
        return a(a, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] b(byte[] bArr) {
        return a(b, bArr);
    }

    private static byte[] a(FastThreadLocal<MessageDigest> fastThreadLocal, byte[] bArr) {
        MessageDigest messageDigest = fastThreadLocal.get();
        messageDigest.reset();
        return messageDigest.digest(bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String c(byte[] bArr) {
        ByteBuf encode = Base64.encode(Unpooled.wrappedBuffer(bArr));
        String byteBuf = encode.toString(CharsetUtil.UTF_8);
        encode.release();
        return byteBuf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] a(int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) a(0, 255);
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i, int i2) {
        return (int) ((Math.random() * i2) + i);
    }
}
