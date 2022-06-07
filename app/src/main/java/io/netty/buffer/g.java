package io.netty.buffer;

/* compiled from: HeapByteBufUtil.java */
/* loaded from: classes4.dex */
final class g {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte a(byte[] bArr, int i) {
        return bArr[i];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static short b(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] & 255) | (bArr[i] << 8));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static short c(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] << 8) | (bArr[i] & 255));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int d(byte[] bArr, int i) {
        return (bArr[i + 2] & 255) | ((bArr[i] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int e(byte[] bArr, int i) {
        return ((bArr[i + 2] & 255) << 16) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int f(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int g(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long h(byte[] bArr, int i) {
        return (bArr[i + 7] & 255) | ((bArr[i] & 255) << 56) | ((bArr[i + 1] & 255) << 48) | ((bArr[i + 2] & 255) << 40) | ((bArr[i + 3] & 255) << 32) | ((bArr[i + 4] & 255) << 24) | ((bArr[i + 5] & 255) << 16) | ((bArr[i + 6] & 255) << 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long i(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void c(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void d(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 16);
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void e(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) (i2 >>> 16);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void f(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 24);
        bArr[i + 1] = (byte) (i2 >>> 16);
        bArr[i + 2] = (byte) (i2 >>> 8);
        bArr[i + 3] = (byte) i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void g(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) (i2 >>> 16);
        bArr[i + 3] = (byte) (i2 >>> 24);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(byte[] bArr, int i, long j) {
        bArr[i] = (byte) (j >>> 56);
        bArr[i + 1] = (byte) (j >>> 48);
        bArr[i + 2] = (byte) (j >>> 40);
        bArr[i + 3] = (byte) (j >>> 32);
        bArr[i + 4] = (byte) (j >>> 24);
        bArr[i + 5] = (byte) (j >>> 16);
        bArr[i + 6] = (byte) (j >>> 8);
        bArr[i + 7] = (byte) j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(byte[] bArr, int i, long j) {
        bArr[i] = (byte) j;
        bArr[i + 1] = (byte) (j >>> 8);
        bArr[i + 2] = (byte) (j >>> 16);
        bArr[i + 3] = (byte) (j >>> 24);
        bArr[i + 4] = (byte) (j >>> 32);
        bArr[i + 5] = (byte) (j >>> 40);
        bArr[i + 6] = (byte) (j >>> 48);
        bArr[i + 7] = (byte) (j >>> 56);
    }
}
