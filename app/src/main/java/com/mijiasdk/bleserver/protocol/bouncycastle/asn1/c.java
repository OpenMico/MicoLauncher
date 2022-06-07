package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DERFactory.java */
/* loaded from: classes2.dex */
public class c {
    static final ASN1Sequence a = new DERSequence();
    static final ASN1Set b = new DERSet();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ASN1Sequence a(ASN1EncodableVector aSN1EncodableVector) {
        return aSN1EncodableVector.size() < 1 ? a : new DLSequence(aSN1EncodableVector);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ASN1Set b(ASN1EncodableVector aSN1EncodableVector) {
        return aSN1EncodableVector.size() < 1 ? b : new DLSet(aSN1EncodableVector);
    }
}
