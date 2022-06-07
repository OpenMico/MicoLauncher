package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class GlideExperiments {
    private final Map<Class<?>, b> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface b {
    }

    GlideExperiments(a aVar) {
        this.a = Collections.unmodifiableMap(new HashMap(aVar.a));
    }

    public boolean isEnabled(Class<? extends b> cls) {
        return this.a.containsKey(cls);
    }

    /* loaded from: classes.dex */
    static final class a {
        private final Map<Class<?>, b> a = new HashMap();

        /* JADX INFO: Access modifiers changed from: package-private */
        public a a(b bVar, boolean z) {
            if (z) {
                a(bVar);
            } else {
                this.a.remove(bVar.getClass());
            }
            return this;
        }

        a a(b bVar) {
            this.a.put(bVar.getClass(), bVar);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public GlideExperiments a() {
            return new GlideExperiments(this);
        }
    }
}
