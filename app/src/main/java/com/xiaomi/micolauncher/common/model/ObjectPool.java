package com.xiaomi.micolauncher.common.model;

import com.xiaomi.mico.common.ContainerUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class ObjectPool {
    private int a;
    private HashMap<String, a> b = null;

    public ObjectPool() {
        this.a = 20;
        this.a = 20;
    }

    public synchronized void createPool() {
        if (this.b == null) {
            this.b = new HashMap<>(this.a);
        }
    }

    public void setMaxObjects(int i) {
        this.a = i;
    }

    public synchronized IPoolObject getObject() {
        if (this.b == null) {
            return null;
        }
        return a();
    }

    private IPoolObject a() {
        a aVar;
        if (ContainerUtil.isEmpty(this.b)) {
            return null;
        }
        Iterator<Map.Entry<String, a>> it = this.b.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                aVar = null;
                break;
            }
            aVar = it.next().getValue();
            if (!aVar.a()) {
                break;
            }
        }
        if (aVar != null) {
            return aVar.b();
        }
        return null;
    }

    public synchronized void returnObject(IPoolObject iPoolObject) {
        if (this.b != null) {
            iPoolObject.resetDataStatus();
            a aVar = this.b.get(iPoolObject.poolObjectKey());
            if (aVar != null) {
                aVar.a(false);
            } else if (this.b.size() < this.a) {
                a aVar2 = new a(iPoolObject);
                this.b.put(aVar2.c(), aVar2);
            }
        }
    }

    public synchronized void closeObjectPool() {
        if (this.b != null) {
            this.b.clear();
            this.b = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a {
        private IPoolObject b;
        private boolean c = false;

        public a(IPoolObject iPoolObject) {
            this.b = null;
            this.b = iPoolObject;
        }

        public boolean a() {
            return this.c;
        }

        public void a(boolean z) {
            this.c = z;
        }

        public IPoolObject b() {
            return this.b;
        }

        public String c() {
            return this.b.poolObjectKey();
        }
    }
}
