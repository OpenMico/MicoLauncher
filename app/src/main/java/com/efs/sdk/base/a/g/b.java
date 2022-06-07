package com.efs.sdk.base.a.g;

import com.efs.sdk.base.a.h.c.a;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.processor.action.ILogEncryptAction;

/* loaded from: classes.dex */
public final class b implements ILogEncryptAction {
    @Override // com.efs.sdk.base.processor.action.ILogEncryptAction
    public final int getDeVal() {
        return 2;
    }

    @Override // com.efs.sdk.base.processor.action.ILogEncryptAction
    public final byte[] encrypt(String str, byte[] bArr) {
        try {
            return a.b(bArr, str);
        } catch (Exception e) {
            d.b("efs.base", "aes encrypt error", e);
            return null;
        }
    }

    @Override // com.efs.sdk.base.processor.action.ILogEncryptAction
    public final byte[] decrypt(String str, byte[] bArr) {
        try {
            return a.a(bArr, str);
        } catch (Exception e) {
            d.b("efs.base", "aes decrypt error", e);
            return null;
        }
    }
}
