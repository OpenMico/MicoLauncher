package com.milink.kit.lock;

import androidx.annotation.NonNull;
import java.util.Objects;

/* compiled from: LockHolderImpl.java */
/* loaded from: classes2.dex */
class a implements LockHolder {
    private final String a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull String str, @NonNull String str2) {
        this.a = (String) Objects.requireNonNull(str);
        this.b = (String) Objects.requireNonNull(str2);
    }

    @Override // com.milink.kit.lock.LockHolder
    public String tag() {
        return this.b;
    }

    @Override // com.milink.kit.lock.LockHolder
    public String identify() {
        return this.a;
    }
}
