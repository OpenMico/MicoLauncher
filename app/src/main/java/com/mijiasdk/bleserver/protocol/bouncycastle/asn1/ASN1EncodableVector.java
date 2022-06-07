package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes2.dex */
public class ASN1EncodableVector {
    private final Vector a = new Vector();

    public void add(ASN1Encodable aSN1Encodable) {
        this.a.addElement(aSN1Encodable);
    }

    public void addAll(ASN1EncodableVector aSN1EncodableVector) {
        Enumeration elements = aSN1EncodableVector.a.elements();
        while (elements.hasMoreElements()) {
            this.a.addElement(elements.nextElement());
        }
    }

    public ASN1Encodable get(int i) {
        return (ASN1Encodable) this.a.elementAt(i);
    }

    public int size() {
        return this.a.size();
    }
}
