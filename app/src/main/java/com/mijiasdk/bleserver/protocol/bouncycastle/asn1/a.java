package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BERFactory.java */
/* loaded from: classes2.dex */
public class a {
    static final BERSequence a = new BERSequence();
    static final BERSet b = new BERSet();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BERSequence a(ASN1EncodableVector aSN1EncodableVector) {
        return aSN1EncodableVector.size() < 1 ? a : new BERSequence(aSN1EncodableVector);
    }
}
