package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes2.dex */
public class ASN1Enumerated extends ASN1Primitive {
    private static ASN1Enumerated[] b = new ASN1Enumerated[12];
    private final byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static ASN1Enumerated getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Enumerated)) {
            return (ASN1Enumerated) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Enumerated) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Enumerated getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof ASN1Enumerated)) {
            return getInstance(object);
        }
        return a(((ASN1OctetString) object).getOctets());
    }

    public ASN1Enumerated(int i) {
        this.a = BigInteger.valueOf(i).toByteArray();
    }

    public ASN1Enumerated(BigInteger bigInteger) {
        this.a = bigInteger.toByteArray();
    }

    public ASN1Enumerated(byte[] bArr) {
        if (bArr.length > 1) {
            if (bArr[0] == 0 && (bArr[1] & 128) == 0) {
                throw new IllegalArgumentException("malformed enumerated");
            } else if (bArr[0] == -1 && (bArr[1] & 128) != 0) {
                throw new IllegalArgumentException("malformed enumerated");
            }
        }
        this.a = Arrays.clone(bArr);
    }

    public BigInteger getValue() {
        return new BigInteger(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() {
        return i.a(this.a.length) + 1 + this.a.length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.a(10, this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Enumerated)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((ASN1Enumerated) aSN1Primitive).a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ASN1Enumerated a(byte[] bArr) {
        if (bArr.length > 1) {
            return new ASN1Enumerated(bArr);
        }
        if (bArr.length != 0) {
            int i = bArr[0] & 255;
            ASN1Enumerated[] aSN1EnumeratedArr = b;
            if (i >= aSN1EnumeratedArr.length) {
                return new ASN1Enumerated(Arrays.clone(bArr));
            }
            ASN1Enumerated aSN1Enumerated = aSN1EnumeratedArr[i];
            if (aSN1Enumerated != null) {
                return aSN1Enumerated;
            }
            ASN1Enumerated aSN1Enumerated2 = new ASN1Enumerated(Arrays.clone(bArr));
            aSN1EnumeratedArr[i] = aSN1Enumerated2;
            return aSN1Enumerated2;
        }
        throw new IllegalArgumentException("ENUMERATED has zero length");
    }
}
