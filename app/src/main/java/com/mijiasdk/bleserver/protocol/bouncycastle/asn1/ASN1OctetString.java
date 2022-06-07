package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.encoders.Hex;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public abstract class ASN1OctetString extends ASN1Primitive implements ASN1OctetStringParser {
    byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    public ASN1OctetStringParser parser() {
        return this;
    }

    public static ASN1OctetString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof ASN1OctetString)) {
            return getInstance(object);
        }
        return BEROctetString.a(ASN1Sequence.getInstance(object));
    }

    public static ASN1OctetString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1OctetString)) {
            return (ASN1OctetString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1OctetString) {
                    return (ASN1OctetString) aSN1Primitive;
                }
            }
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1OctetString(byte[] bArr) {
        if (bArr != null) {
            this.a = bArr;
            return;
        }
        throw new NullPointerException("string cannot be null");
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return new ByteArrayInputStream(this.a);
    }

    public byte[] getOctets() {
        return this.a;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(getOctets());
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1OctetString)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((ASN1OctetString) aSN1Primitive).a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive b() {
        return new DEROctetString(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive c() {
        return new DEROctetString(this.a);
    }

    public String toString() {
        return "#" + Strings.fromByteArray(Hex.encode(this.a));
    }
}
