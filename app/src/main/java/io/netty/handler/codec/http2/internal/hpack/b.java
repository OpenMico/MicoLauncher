package io.netty.handler.codec.http2.internal.hpack;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HeaderField.java */
/* loaded from: classes4.dex */
public class b implements Comparable<b> {
    final byte[] f;
    final byte[] g;

    public static int a(byte[] bArr, byte[] bArr2) {
        return bArr.length + bArr2.length + 32;
    }

    public b(String str, String str2) {
        this(str.getBytes(CharsetUtil.ISO_8859_1), str2.getBytes(CharsetUtil.ISO_8859_1));
    }

    public b(byte[] bArr, byte[] bArr2) {
        this.f = (byte[]) ObjectUtil.checkNotNull(bArr, "name");
        this.g = (byte[]) ObjectUtil.checkNotNull(bArr2, com.xiaomi.onetrack.api.b.p);
    }

    public int a() {
        return this.f.length + this.g.length + 32;
    }

    public int hashCode() {
        return super.hashCode();
    }

    /* renamed from: a */
    public int compareTo(b bVar) {
        int b = b(this.f, bVar.f);
        return b == 0 ? b(this.g, bVar.g) : b;
    }

    private int b(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int length2 = bArr2.length;
        int min = Math.min(length, length2);
        for (int i = 0; i < min; i++) {
            byte b = bArr[i];
            byte b2 = bArr2[i];
            if (b != b2) {
                return b - b2;
            }
        }
        return length - length2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return c.a(this.f, bVar.f) && c.a(this.g, bVar.g);
    }

    public String toString() {
        String str = new String(this.f);
        String str2 = new String(this.g);
        return str + ": " + str2;
    }
}
