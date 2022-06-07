package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DERExternal extends ASN1Primitive {
    private ASN1ObjectIdentifier a;
    private ASN1Integer b;
    private ASN1Primitive c;
    private int d;
    private ASN1Primitive e;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return true;
    }

    public DERExternal(ASN1EncodableVector aSN1EncodableVector) {
        int i = 0;
        ASN1Primitive a = a(aSN1EncodableVector, 0);
        if (a instanceof ASN1ObjectIdentifier) {
            this.a = (ASN1ObjectIdentifier) a;
            a = a(aSN1EncodableVector, 1);
            i = 1;
        }
        if (a instanceof ASN1Integer) {
            this.b = (ASN1Integer) a;
            i++;
            a = a(aSN1EncodableVector, i);
        }
        if (!(a instanceof ASN1TaggedObject)) {
            this.c = a;
            i++;
            a = a(aSN1EncodableVector, i);
        }
        if (aSN1EncodableVector.size() != i + 1) {
            throw new IllegalArgumentException("input vector too large");
        } else if (a instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) a;
            a(aSN1TaggedObject.getTagNo());
            this.e = aSN1TaggedObject.getObject();
        } else {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
    }

    private ASN1Primitive a(ASN1EncodableVector aSN1EncodableVector, int i) {
        if (aSN1EncodableVector.size() > i) {
            return aSN1EncodableVector.get(i).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, DERTaggedObject dERTaggedObject) {
        this(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject.getTagNo(), dERTaggedObject.toASN1Primitive());
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, int i, ASN1Primitive aSN1Primitive2) {
        a(aSN1ObjectIdentifier);
        a(aSN1Integer);
        a(aSN1Primitive);
        a(i);
        b(aSN1Primitive2.toASN1Primitive());
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.a;
        int hashCode = aSN1ObjectIdentifier != null ? aSN1ObjectIdentifier.hashCode() : 0;
        ASN1Integer aSN1Integer = this.b;
        if (aSN1Integer != null) {
            hashCode ^= aSN1Integer.hashCode();
        }
        ASN1Primitive aSN1Primitive = this.c;
        if (aSN1Primitive != null) {
            hashCode ^= aSN1Primitive.hashCode();
        }
        return hashCode ^ this.e.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        return getEncoded().length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.a;
        if (aSN1ObjectIdentifier != null) {
            byteArrayOutputStream.write(aSN1ObjectIdentifier.getEncoded(ASN1Encoding.DER));
        }
        ASN1Integer aSN1Integer = this.b;
        if (aSN1Integer != null) {
            byteArrayOutputStream.write(aSN1Integer.getEncoded(ASN1Encoding.DER));
        }
        ASN1Primitive aSN1Primitive = this.c;
        if (aSN1Primitive != null) {
            byteArrayOutputStream.write(aSN1Primitive.getEncoded(ASN1Encoding.DER));
        }
        byteArrayOutputStream.write(new DERTaggedObject(true, this.d, this.e).getEncoded(ASN1Encoding.DER));
        aSN1OutputStream.a(32, 8, byteArrayOutputStream.toByteArray());
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        ASN1Primitive aSN1Primitive2;
        ASN1Integer aSN1Integer;
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        if (!(aSN1Primitive instanceof DERExternal)) {
            return false;
        }
        if (this == aSN1Primitive) {
            return true;
        }
        DERExternal dERExternal = (DERExternal) aSN1Primitive;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = this.a;
        if (aSN1ObjectIdentifier2 != null && ((aSN1ObjectIdentifier = dERExternal.a) == null || !aSN1ObjectIdentifier.equals(aSN1ObjectIdentifier2))) {
            return false;
        }
        ASN1Integer aSN1Integer2 = this.b;
        if (aSN1Integer2 != null && ((aSN1Integer = dERExternal.b) == null || !aSN1Integer.equals(aSN1Integer2))) {
            return false;
        }
        ASN1Primitive aSN1Primitive3 = this.c;
        if (aSN1Primitive3 == null || ((aSN1Primitive2 = dERExternal.c) != null && aSN1Primitive2.equals(aSN1Primitive3))) {
            return this.e.equals(dERExternal.e);
        }
        return false;
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.c;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.a;
    }

    public int getEncoding() {
        return this.d;
    }

    public ASN1Primitive getExternalContent() {
        return this.e;
    }

    public ASN1Integer getIndirectReference() {
        return this.b;
    }

    private void a(ASN1Primitive aSN1Primitive) {
        this.c = aSN1Primitive;
    }

    private void a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
    }

    private void a(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("invalid encoding value: " + i);
        }
        this.d = i;
    }

    private void b(ASN1Primitive aSN1Primitive) {
        this.e = aSN1Primitive;
    }

    private void a(ASN1Integer aSN1Integer) {
        this.b = aSN1Integer;
    }
}
