package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERBMPString extends ASN1Primitive implements ASN1String {
    private final char[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static DERBMPString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBMPString)) {
            return (DERBMPString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERBMPString) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERBMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof DERBMPString)) {
            return getInstance(object);
        }
        return new DERBMPString(ASN1OctetString.getInstance(object).getOctets());
    }

    DERBMPString(byte[] bArr) {
        char[] cArr = new char[bArr.length / 2];
        for (int i = 0; i != cArr.length; i++) {
            int i2 = i * 2;
            cArr[i] = (char) ((bArr[i2 + 1] & 255) | (bArr[i2] << 8));
        }
        this.a = cArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DERBMPString(char[] cArr) {
        this.a = cArr;
    }

    public DERBMPString(String str) {
        this.a = str.toCharArray();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1String
    public String getString() {
        return new String(this.a);
    }

    public String toString() {
        return getString();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERBMPString)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERBMPString) aSN1Primitive).a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() {
        return i.a(this.a.length * 2) + 1 + (this.a.length * 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.b(30);
        aSN1OutputStream.a(this.a.length * 2);
        int i = 0;
        while (true) {
            char[] cArr = this.a;
            if (i != cArr.length) {
                char c = cArr[i];
                aSN1OutputStream.b((byte) (c >> '\b'));
                aSN1OutputStream.b((byte) c);
                i++;
            } else {
                return;
            }
        }
    }
}
