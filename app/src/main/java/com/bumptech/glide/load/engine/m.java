package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Jobs.java */
/* loaded from: classes.dex */
public final class m {
    private final Map<Key, h<?>> a = new HashMap();
    private final Map<Key, h<?>> b = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public h<?> a(Key key, boolean z) {
        return a(z).get(key);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Key key, h<?> hVar) {
        a(hVar.a()).put(key, hVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Key key, h<?> hVar) {
        Map<Key, h<?>> a = a(hVar.a());
        if (hVar.equals(a.get(key))) {
            a.remove(key);
        }
    }

    private Map<Key, h<?>> a(boolean z) {
        return z ? this.b : this.a;
    }
}
