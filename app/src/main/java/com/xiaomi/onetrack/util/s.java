package com.xiaomi.onetrack.util;

import com.xiaomi.onetrack.util.r;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class s implements r.a {
    final /* synthetic */ boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(boolean z) {
        this.a = z;
    }

    @Override // com.xiaomi.onetrack.util.r.a
    public boolean a(Object obj) {
        if (this.a) {
            return r.a(obj);
        }
        return r.b(obj);
    }
}
