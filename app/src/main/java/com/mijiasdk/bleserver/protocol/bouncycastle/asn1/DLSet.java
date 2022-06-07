package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class DLSet extends ASN1Set {
    private int a = -1;

    public DLSet() {
    }

    public DLSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public DLSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
    }

    public DLSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
    }

    private int d() throws IOException {
        if (this.a < 0) {
            int i = 0;
            Enumeration objects = getObjects();
            while (objects.hasMoreElements()) {
                i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().c().a();
            }
            this.a = i;
        }
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        int d = d();
        return i.a(d) + 1 + d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Set, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        ASN1OutputStream b = aSN1OutputStream.b();
        int d = d();
        aSN1OutputStream.b(49);
        aSN1OutputStream.a(d);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            b.writeObject((ASN1Encodable) objects.nextElement());
        }
    }
}
