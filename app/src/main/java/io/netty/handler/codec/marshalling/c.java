package io.netty.handler.codec.marshalling;

import java.io.IOException;
import org.jboss.marshalling.ByteInput;

/* compiled from: LimitingByteInput.java */
/* loaded from: classes4.dex */
class c implements ByteInput {
    private static final a a = new a();
    private final ByteInput b;
    private final long c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ByteInput byteInput, long j) {
        if (j > 0) {
            this.b = byteInput;
            this.c = j;
            return;
        }
        throw new IllegalArgumentException("The limit MUST be > 0");
    }

    /* compiled from: LimitingByteInput.java */
    /* loaded from: classes4.dex */
    static final class a extends IOException {
        private static final long serialVersionUID = 1;

        a() {
        }
    }
}
