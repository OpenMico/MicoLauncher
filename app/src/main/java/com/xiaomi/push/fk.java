package com.xiaomi.push;

import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class fk extends fg {
    public fk() {
        a("PING", (String) null);
        a("0");
        a(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaomi.push.fg
    public ByteBuffer a(ByteBuffer byteBuffer) {
        return a().length == 0 ? byteBuffer : super.a(byteBuffer);
    }

    @Override // com.xiaomi.push.fg
    public int c() {
        if (a().length == 0) {
            return 0;
        }
        return super.c();
    }
}
