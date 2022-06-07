package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public class ASN1Exception extends IOException {
    private Throwable cause;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ASN1Exception(String str) {
        super(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ASN1Exception(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
