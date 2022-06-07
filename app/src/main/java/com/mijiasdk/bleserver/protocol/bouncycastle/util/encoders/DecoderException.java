package com.mijiasdk.bleserver.protocol.bouncycastle.util.encoders;

/* loaded from: classes2.dex */
public class DecoderException extends IllegalStateException {
    private Throwable cause;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DecoderException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
