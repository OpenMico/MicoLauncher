package org.apache.commons.logging.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public final class WeakHashtable extends Hashtable {
    private static final long serialVersionUID = -1546036869799732453L;
    private final ReferenceQueue queue = new ReferenceQueue();
    private int changeCount = 0;

    @Override // java.util.Hashtable, java.util.Map
    public boolean containsKey(Object obj) {
        return super.containsKey(new b(obj, (e) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary
    public Enumeration elements() {
        a();
        return super.elements();
    }

    @Override // java.util.Hashtable, java.util.Map
    public Set entrySet() {
        a();
        Set<Map.Entry> entrySet = super.entrySet();
        HashSet hashSet = new HashSet();
        for (Map.Entry entry : entrySet) {
            Object a2 = b.a((b) entry.getKey());
            Object value = entry.getValue();
            if (a2 != null) {
                hashSet.add(new a(a2, value, null));
            }
        }
        return hashSet;
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public Object get(Object obj) {
        return super.get(new b(obj, (e) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary
    public Enumeration keys() {
        a();
        return new e(this, super.keys());
    }

    @Override // java.util.Hashtable, java.util.Map
    public Set keySet() {
        a();
        Set<b> keySet = super.keySet();
        HashSet hashSet = new HashSet();
        for (b bVar : keySet) {
            Object a2 = b.a(bVar);
            if (a2 != null) {
                hashSet.add(a2);
            }
        }
        return hashSet;
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public synchronized Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("Null keys are not allowed");
        } else if (obj2 != null) {
            int i = this.changeCount;
            this.changeCount = i + 1;
            if (i > 100) {
                a();
                this.changeCount = 0;
            } else if (this.changeCount % 10 == 0) {
                b();
            }
        } else {
            throw new NullPointerException("Null values are not allowed");
        }
        return super.put(new b(obj, this.queue, null), obj2);
    }

    @Override // java.util.Hashtable, java.util.Map
    public void putAll(Map map) {
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // java.util.Hashtable, java.util.Map
    public Collection values() {
        a();
        return super.values();
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public synchronized Object remove(Object obj) {
        int i = this.changeCount;
        this.changeCount = i + 1;
        if (i > 100) {
            a();
            this.changeCount = 0;
        } else if (this.changeCount % 10 == 0) {
            b();
        }
        return super.remove(new b(obj, (e) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public boolean isEmpty() {
        a();
        return super.isEmpty();
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public int size() {
        a();
        return super.size();
    }

    @Override // java.util.Hashtable
    public String toString() {
        a();
        return super.toString();
    }

    @Override // java.util.Hashtable
    protected void rehash() {
        a();
        super.rehash();
    }

    private void a() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.queue) {
            while (true) {
                c cVar = (c) this.queue.poll();
                if (cVar == null) {
                    break;
                }
                arrayList.add(c.a(cVar));
            }
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            super.remove(arrayList.get(i));
        }
    }

    private void b() {
        synchronized (this.queue) {
            c cVar = (c) this.queue.poll();
            if (cVar != null) {
                super.remove(c.a(cVar));
            }
        }
    }

    /* loaded from: classes5.dex */
    private static final class a implements Map.Entry {
        private final Object a;
        private final Object b;

        a(Object obj, Object obj2, e eVar) {
            this(obj, obj2);
        }

        private a(Object obj, Object obj2) {
            this.a = obj;
            this.b = obj2;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (getKey() == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!getKey().equals(entry.getKey())) {
                return false;
            }
            if (getValue() == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!getValue().equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int i = 0;
            int hashCode = getKey() == null ? 0 : getKey().hashCode();
            if (getValue() != null) {
                i = getValue().hashCode();
            }
            return hashCode ^ i;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            throw new UnsupportedOperationException("Entry.setValue is not supported.");
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.a;
        }
    }

    /* loaded from: classes5.dex */
    public static final class b {
        private final WeakReference a;
        private final int b;

        b(Object obj, ReferenceQueue referenceQueue, e eVar) {
            this(obj, referenceQueue);
        }

        b(Object obj, e eVar) {
            this(obj);
        }

        public static Object a(b bVar) {
            return bVar.a();
        }

        private b(Object obj) {
            this.a = new WeakReference(obj);
            this.b = obj.hashCode();
        }

        private b(Object obj, ReferenceQueue referenceQueue) {
            this.a = new c(obj, referenceQueue, this, null);
            this.b = obj.hashCode();
        }

        public int hashCode() {
            return this.b;
        }

        private Object a() {
            return this.a.get();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            Object a = a();
            Object a2 = bVar.a();
            if (a != null) {
                return a.equals(a2);
            }
            return (a2 == null) && hashCode() == bVar.hashCode();
        }
    }

    /* loaded from: classes5.dex */
    public static final class c extends WeakReference {
        private final b a;

        c(Object obj, ReferenceQueue referenceQueue, b bVar, e eVar) {
            this(obj, referenceQueue, bVar);
        }

        static b a(c cVar) {
            return cVar.a();
        }

        private c(Object obj, ReferenceQueue referenceQueue, b bVar) {
            super(obj, referenceQueue);
            this.a = bVar;
        }

        private b a() {
            return this.a;
        }
    }
}
