package org.apache.commons.lang3.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* loaded from: classes5.dex */
public class MultiBackgroundInitializer extends BackgroundInitializer<MultiBackgroundInitializerResults> {
    private final Map<String, BackgroundInitializer<?>> a = new HashMap();

    public MultiBackgroundInitializer() {
    }

    public MultiBackgroundInitializer(ExecutorService executorService) {
        super(executorService);
    }

    public void addInitializer(String str, BackgroundInitializer<?> backgroundInitializer) {
        if (str == null) {
            throw new IllegalArgumentException("Name of child initializer must not be null!");
        } else if (backgroundInitializer != null) {
            synchronized (this) {
                if (!isStarted()) {
                    this.a.put(str, backgroundInitializer);
                } else {
                    throw new IllegalStateException("addInitializer() must not be called after start()!");
                }
            }
        } else {
            throw new IllegalArgumentException("Child initializer must not be null!");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.lang3.concurrent.BackgroundInitializer
    public int getTaskCount() {
        int i = 1;
        for (BackgroundInitializer<?> backgroundInitializer : this.a.values()) {
            i += backgroundInitializer.getTaskCount();
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.lang3.concurrent.BackgroundInitializer
    public MultiBackgroundInitializerResults initialize() throws Exception {
        HashMap hashMap;
        synchronized (this) {
            hashMap = new HashMap(this.a);
        }
        ExecutorService activeExecutor = getActiveExecutor();
        for (BackgroundInitializer backgroundInitializer : hashMap.values()) {
            if (backgroundInitializer.getExternalExecutor() == null) {
                backgroundInitializer.setExternalExecutor(activeExecutor);
            }
            backgroundInitializer.start();
        }
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        for (Map.Entry entry : hashMap.entrySet()) {
            try {
                hashMap2.put(entry.getKey(), ((BackgroundInitializer) entry.getValue()).get());
            } catch (ConcurrentException e) {
                hashMap3.put(entry.getKey(), e);
            }
        }
        return new MultiBackgroundInitializerResults(hashMap, hashMap2, hashMap3);
    }

    /* loaded from: classes5.dex */
    public static class MultiBackgroundInitializerResults {
        private final Map<String, BackgroundInitializer<?>> a;
        private final Map<String, Object> b;
        private final Map<String, ConcurrentException> c;

        private MultiBackgroundInitializerResults(Map<String, BackgroundInitializer<?>> map, Map<String, Object> map2, Map<String, ConcurrentException> map3) {
            this.a = map;
            this.b = map2;
            this.c = map3;
        }

        public BackgroundInitializer<?> getInitializer(String str) {
            return a(str);
        }

        public Object getResultObject(String str) {
            a(str);
            return this.b.get(str);
        }

        public boolean isException(String str) {
            a(str);
            return this.c.containsKey(str);
        }

        public ConcurrentException getException(String str) {
            a(str);
            return this.c.get(str);
        }

        public Set<String> initializerNames() {
            return Collections.unmodifiableSet(this.a.keySet());
        }

        public boolean isSuccessful() {
            return this.c.isEmpty();
        }

        private BackgroundInitializer<?> a(String str) {
            BackgroundInitializer<?> backgroundInitializer = this.a.get(str);
            if (backgroundInitializer != null) {
                return backgroundInitializer;
            }
            throw new NoSuchElementException("No child initializer with name " + str);
        }
    }
}
