package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERUTF8String extends ASN1Primitive implements ASN1String {
    private final byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return false;
    }

    public static DERUTF8String getInstance(Object obj) {
        if (obj == null || (obj instanceof DERUTF8String)) {
            return (DERUTF8String) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERUTF8String) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERUTF8String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof DERUTF8String)) {
            return getInstance(object);
        }
        return new DERUTF8String(ASN1OctetString.getInstance(object).getOctets());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DERUTF8String(byte[] bArr) {
        this.a = bArr;
    }

    public DERUTF8String(String str) {
        this.a = Strings.toUTF8ByteArray(str);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1String
    public String getString() {
        return Strings.fromUTF8ByteArray(this.a);
    }

    public String toString() {
        return getString();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERUTF8String)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERUTF8String) aSN1Primitive).a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        return i.a(this.a.length) + 1 + this.a.length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.a(12, this.a);
    }
}
