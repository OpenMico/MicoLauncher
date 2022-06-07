package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class ASN1TaggedObject extends ASN1Primitive implements ASN1TaggedObjectParser {
    int a;
    boolean b = false;
    boolean c;
    ASN1Encodable d;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    public static ASN1TaggedObject getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return (ASN1TaggedObject) aSN1TaggedObject.getObject();
        }
        throw new IllegalArgumentException("implicitly tagged tagged object");
    }

    public static ASN1TaggedObject getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1TaggedObject)) {
            return (ASN1TaggedObject) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1TaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        this.c = true;
        this.d = null;
        if (aSN1Encodable instanceof ASN1Choice) {
            this.c = true;
        } else {
            this.c = z;
        }
        this.a = i;
        if (this.c) {
            this.d = aSN1Encodable;
            return;
        }
        boolean z2 = aSN1Encodable.toASN1Primitive() instanceof ASN1Set;
        this.d = aSN1Encodable;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
            return false;
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
        if (this.a != aSN1TaggedObject.a || this.b != aSN1TaggedObject.b || this.c != aSN1TaggedObject.c) {
            return false;
        }
        ASN1Encodable aSN1Encodable = this.d;
        return aSN1Encodable == null ? aSN1TaggedObject.d == null : aSN1Encodable.toASN1Primitive().equals(aSN1TaggedObject.d.toASN1Primitive());
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int i = this.a;
        ASN1Encodable aSN1Encodable = this.d;
        return aSN1Encodable != null ? i ^ aSN1Encodable.hashCode() : i;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.a;
    }

    public boolean isExplicit() {
        return this.c;
    }

    public boolean isEmpty() {
        return this.b;
    }

    public ASN1Primitive getObject() {
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive();
        }
        return null;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable getObjectParser(int i, boolean z) throws IOException {
        if (i == 4) {
            return ASN1OctetString.getInstance(this, z).parser();
        }
        switch (i) {
            case 16:
                return ASN1Sequence.getInstance(this, z).parser();
            case 17:
                return ASN1Set.getInstance(this, z).parser();
            default:
                if (z) {
                    return getObject();
                }
                throw new ASN1Exception("implicit tagging not implemented for tag: " + i);
        }
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive b() {
        return new DERTaggedObject(this.c, this.a, this.d);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive c() {
        return new DLTaggedObject(this.c, this.a, this.d);
    }

    public String toString() {
        return "[" + this.a + "]" + this.d;
    }
}
