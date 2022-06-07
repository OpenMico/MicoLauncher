package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class ASN1ObjectIdentifier extends ASN1Primitive {
    private static final ConcurrentMap<a, ASN1ObjectIdentifier> c = new ConcurrentHashMap();
    private final String a;
    private byte[] b;

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static ASN1ObjectIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ObjectIdentifier)) {
            return (ASN1ObjectIdentifier) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable) obj;
            if (aSN1Encodable.toASN1Primitive() instanceof ASN1ObjectIdentifier) {
                return (ASN1ObjectIdentifier) aSN1Encodable.toASN1Primitive();
            }
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1ObjectIdentifier) fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct object identifier from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof ASN1ObjectIdentifier)) {
            return getInstance(object);
        }
        return a(ASN1OctetString.getInstance(aSN1TaggedObject.getObject()).getOctets());
    }

    ASN1ObjectIdentifier(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        long j = 0;
        BigInteger bigInteger = null;
        for (int i = 0; i != bArr.length; i++) {
            int i2 = bArr[i] & 255;
            if (j <= 72057594037927808L) {
                long j2 = j + (i2 & 127);
                if ((i2 & 128) == 0) {
                    if (z) {
                        if (j2 < 40) {
                            stringBuffer.append('0');
                        } else if (j2 < 80) {
                            stringBuffer.append('1');
                            j2 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j2 -= 80;
                        }
                        z = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                }
            } else {
                BigInteger or = (bigInteger == null ? BigInteger.valueOf(j) : bigInteger).or(BigInteger.valueOf(i2 & 127));
                if ((i2 & 128) == 0) {
                    if (z) {
                        stringBuffer.append('2');
                        or = or.subtract(BigInteger.valueOf(80L));
                        z = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(or);
                    j = 0;
                    bigInteger = null;
                } else {
                    bigInteger = or.shiftLeft(7);
                }
            }
        }
        this.a = stringBuffer.toString();
        this.b = Arrays.clone(bArr);
    }

    public ASN1ObjectIdentifier(String str) {
        if (str == null) {
            throw new IllegalArgumentException("'identifier' cannot be null");
        } else if (a(str)) {
            this.a = str;
        } else {
            throw new IllegalArgumentException("string " + str + " not an OID");
        }
    }

    ASN1ObjectIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (a(str, 0)) {
            this.a = aSN1ObjectIdentifier.getId() + "." + str;
            return;
        }
        throw new IllegalArgumentException("string " + str + " not a valid OID branch");
    }

    public String getId() {
        return this.a;
    }

    public ASN1ObjectIdentifier branch(String str) {
        return new ASN1ObjectIdentifier(this, str);
    }

    public boolean on(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String id = getId();
        String id2 = aSN1ObjectIdentifier.getId();
        return id.length() > id2.length() && id.charAt(id2.length()) == '.' && id.startsWith(id2);
    }

    private void a(ByteArrayOutputStream byteArrayOutputStream, long j) {
        byte[] bArr = new byte[9];
        int i = 8;
        bArr[8] = (byte) (((int) j) & 127);
        while (j >= 128) {
            j >>= 7;
            i--;
            bArr[i] = (byte) ((((int) j) & 127) | 128);
        }
        byteArrayOutputStream.write(bArr, i, 9 - i);
    }

    private void a(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int bitLength = (bigInteger.bitLength() + 6) / 7;
        if (bitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[bitLength];
        int i = bitLength - 1;
        BigInteger bigInteger2 = bigInteger;
        for (int i2 = i; i2 >= 0; i2--) {
            bArr[i2] = (byte) ((bigInteger2.intValue() & 127) | 128);
            bigInteger2 = bigInteger2.shiftRight(7);
        }
        bArr[i] = (byte) (bArr[i] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, bArr.length);
    }

    private void a(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.a);
        int parseInt = Integer.parseInt(oIDTokenizer.nextToken()) * 40;
        String nextToken = oIDTokenizer.nextToken();
        if (nextToken.length() <= 18) {
            a(byteArrayOutputStream, parseInt + Long.parseLong(nextToken));
        } else {
            a(byteArrayOutputStream, new BigInteger(nextToken).add(BigInteger.valueOf(parseInt)));
        }
        while (oIDTokenizer.hasMoreTokens()) {
            String nextToken2 = oIDTokenizer.nextToken();
            if (nextToken2.length() <= 18) {
                a(byteArrayOutputStream, Long.parseLong(nextToken2));
            } else {
                a(byteArrayOutputStream, new BigInteger(nextToken2));
            }
        }
    }

    private synchronized byte[] d() {
        if (this.b == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            a(byteArrayOutputStream);
            this.b = byteArrayOutputStream.toByteArray();
        }
        return this.b;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        int length = d().length;
        return i.a(length) + 1 + length;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] d = d();
        aSN1OutputStream.b(6);
        aSN1OutputStream.a(d.length);
        aSN1OutputStream.a(d);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == this) {
            return true;
        }
        if (!(aSN1Primitive instanceof ASN1ObjectIdentifier)) {
            return false;
        }
        return this.a.equals(((ASN1ObjectIdentifier) aSN1Primitive).a);
    }

    public String toString() {
        return getId();
    }

    private static boolean a(String str, int i) {
        int length = str.length();
        boolean z = false;
        while (true) {
            length--;
            if (length < i) {
                return z;
            }
            char charAt = str.charAt(length);
            if ('0' <= charAt && charAt <= '9') {
                z = true;
            } else if (charAt != '.' || !z) {
                return false;
            } else {
                z = false;
            }
        }
    }

    private static boolean a(String str) {
        char charAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (charAt = str.charAt(0)) < '0' || charAt > '2') {
            return false;
        }
        return a(str, 2);
    }

    public ASN1ObjectIdentifier intern() {
        a aVar = new a(d());
        ASN1ObjectIdentifier aSN1ObjectIdentifier = c.get(aVar);
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        ASN1ObjectIdentifier putIfAbsent = c.putIfAbsent(aVar, this);
        return putIfAbsent == null ? this : putIfAbsent;
    }

    /* loaded from: classes2.dex */
    public static class a {
        private final int a;
        private final byte[] b;

        a(byte[] bArr) {
            this.a = Arrays.hashCode(bArr);
            this.b = bArr;
        }

        public int hashCode() {
            return this.a;
        }

        public boolean equals(Object obj) {
            if (obj instanceof a) {
                return Arrays.areEqual(this.b, ((a) obj).b);
            }
            return false;
        }
    }

    public static ASN1ObjectIdentifier a(byte[] bArr) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = c.get(new a(bArr));
        return aSN1ObjectIdentifier == null ? new ASN1ObjectIdentifier(bArr) : aSN1ObjectIdentifier;
    }
}
