package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LazyEncodedSequence.java */
/* loaded from: classes2.dex */
public class g extends ASN1Sequence {
    private byte[] a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(byte[] bArr) throws IOException {
        this.a = bArr;
    }

    private void d() {
        f fVar = new f(this.a);
        while (fVar.hasMoreElements()) {
            this.seq.addElement(fVar.nextElement());
        }
        this.a = null;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence
    public synchronized ASN1Encodable getObjectAt(int i) {
        if (this.a != null) {
            d();
        }
        return super.getObjectAt(i);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence
    public synchronized Enumeration getObjects() {
        if (this.a == null) {
            return super.getObjects();
        }
        return new f(this.a);
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence
    public synchronized int size() {
        if (this.a != null) {
            d();
        }
        return super.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive b() {
        if (this.a != null) {
            d();
        }
        return super.b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive c() {
        if (this.a != null) {
            d();
        }
        return super.c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public int a() throws IOException {
        byte[] bArr = this.a;
        if (bArr != null) {
            return i.a(bArr.length) + 1 + this.a.length;
        }
        return super.c().a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Sequence, com.mijiasdk.bleserver.protocol.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] bArr = this.a;
        if (bArr != null) {
            aSN1OutputStream.a(48, bArr);
        } else {
            super.c().encode(aSN1OutputStream);
        }
    }
}
