package com.google.protobuf;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class LazyField extends LazyFieldLite {
    private final MessageLite a;

    public LazyField(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        super(extensionRegistryLite, byteString);
        this.a = messageLite;
    }

    @Override // com.google.protobuf.LazyFieldLite
    public boolean containsDefaultInstance() {
        return super.containsDefaultInstance() || this.value == this.a;
    }

    public MessageLite getValue() {
        return getValue(this.a);
    }

    @Override // com.google.protobuf.LazyFieldLite
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // com.google.protobuf.LazyFieldLite
    public boolean equals(Object obj) {
        return getValue().equals(obj);
    }

    public String toString() {
        return getValue().toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a<K> implements Map.Entry<K, Object> {
        private Map.Entry<K, LazyField> a;

        private a(Map.Entry<K, LazyField> entry) {
            this.a = entry;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.a.getKey();
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            LazyField value = this.a.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }

        public LazyField a() {
            return this.a.getValue();
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            if (obj instanceof MessageLite) {
                return this.a.getValue().setValue((MessageLite) obj);
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class b<K> implements Iterator<Map.Entry<K, Object>> {
        private Iterator<Map.Entry<K, Object>> a;

        public b(Iterator<Map.Entry<K, Object>> it) {
            this.a = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.a.hasNext();
        }

        /* renamed from: a */
        public Map.Entry<K, Object> next() {
            Map.Entry<K, Object> next = this.a.next();
            return next.getValue() instanceof LazyField ? new a(next) : next;
        }

        @Override // java.util.Iterator
        public void remove() {
            this.a.remove();
        }
    }
}
