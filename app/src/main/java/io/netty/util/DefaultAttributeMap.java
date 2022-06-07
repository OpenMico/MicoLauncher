package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public class DefaultAttributeMap implements AttributeMap {
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> a;
    private volatile AtomicReferenceArray<a<?>> b;

    static {
        AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultAttributeMap.class, "attributes");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "b");
        }
        a = newAtomicReferenceFieldUpdater;
    }

    @Override // io.netty.util.AttributeMap
    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        if (attributeKey != null) {
            AtomicReferenceArray<a<?>> atomicReferenceArray = this.b;
            if (atomicReferenceArray == null) {
                atomicReferenceArray = new AtomicReferenceArray<>(4);
                if (!a.compareAndSet(this, null, atomicReferenceArray)) {
                    atomicReferenceArray = this.b;
                }
            }
            int a2 = a(attributeKey);
            a<?> aVar = atomicReferenceArray.get(a2);
            if (aVar == null) {
                a<?> aVar2 = new a<>(attributeKey);
                if (atomicReferenceArray.compareAndSet(a2, null, aVar2)) {
                    return aVar2;
                }
                aVar = atomicReferenceArray.get(a2);
            }
            synchronized (aVar) {
                a<?> aVar3 = aVar;
                while (true) {
                    if (!((a) aVar3).removed && ((a) aVar3).key == attributeKey) {
                        return aVar3;
                    }
                    a<?> aVar4 = ((a) aVar3).next;
                    if (aVar4 == null) {
                        a aVar5 = new a(aVar, attributeKey);
                        ((a) aVar3).next = aVar5;
                        aVar5.prev = aVar3;
                        return aVar5;
                    }
                    aVar3 = aVar4;
                }
            }
        } else {
            throw new NullPointerException("key");
        }
    }

    @Override // io.netty.util.AttributeMap
    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        a<?> aVar;
        if (attributeKey != null) {
            AtomicReferenceArray<a<?>> atomicReferenceArray = this.b;
            if (atomicReferenceArray == null || (aVar = atomicReferenceArray.get(a(attributeKey))) == null) {
                return false;
            }
            if (((a) aVar).key == attributeKey && !((a) aVar).removed) {
                return true;
            }
            synchronized (aVar) {
                for (a aVar2 = ((a) aVar).next; aVar2 != null; aVar2 = aVar2.next) {
                    if (!aVar2.removed && aVar2.key == attributeKey) {
                        return true;
                    }
                }
                return false;
            }
        }
        throw new NullPointerException("key");
    }

    private static int a(AttributeKey<?> attributeKey) {
        return attributeKey.id() & 3;
    }

    /* loaded from: classes4.dex */
    private static final class a<T> extends AtomicReference<T> implements Attribute<T> {
        private static final long serialVersionUID = -2661411462200283011L;
        private final a<?> head;
        private final AttributeKey<T> key;
        private a<?> next;
        private a<?> prev;
        private volatile boolean removed;

        a(a<?> aVar, AttributeKey<T> attributeKey) {
            this.head = aVar;
            this.key = attributeKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        a(AttributeKey<T> attributeKey) {
            this.head = this;
            this.key = attributeKey;
        }

        @Override // io.netty.util.Attribute
        public AttributeKey<T> key() {
            return this.key;
        }

        @Override // io.netty.util.Attribute
        public T setIfAbsent(T t) {
            while (!compareAndSet(null, t)) {
                T t2 = get();
                if (t2 != null) {
                    return t2;
                }
            }
            return null;
        }

        @Override // io.netty.util.Attribute
        public T getAndRemove() {
            this.removed = true;
            T andSet = getAndSet(null);
            a();
            return andSet;
        }

        @Override // io.netty.util.Attribute
        public void remove() {
            this.removed = true;
            set(null);
            a();
        }

        private void a() {
            synchronized (this.head) {
                if (this.prev != null) {
                    this.prev.next = this.next;
                    if (this.next != null) {
                        this.next.prev = this.prev;
                    }
                    this.prev = null;
                    this.next = null;
                }
            }
        }
    }
}
