package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LazyConstructionEnumeration.java */
/* loaded from: classes2.dex */
public class f implements Enumeration {
    private ASN1InputStream a;
    private Object b = a();

    public f(byte[] bArr) {
        this.a = new ASN1InputStream(bArr, true);
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.b != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.b;
        this.b = a();
        return obj;
    }

    private Object a() {
        try {
            return this.a.readObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("malformed DER construction: " + e, e);
        }
    }
}
