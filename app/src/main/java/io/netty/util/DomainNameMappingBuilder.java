package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class DomainNameMappingBuilder<V> {
    private final V a;
    private final Map<String, V> b;

    public DomainNameMappingBuilder(V v) {
        this(4, v);
    }

    public DomainNameMappingBuilder(int i, V v) {
        this.a = (V) ObjectUtil.checkNotNull(v, "defaultValue");
        this.b = new LinkedHashMap(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DomainNameMappingBuilder<V> add(String str, V v) {
        this.b.put(ObjectUtil.checkNotNull(str, "hostname"), ObjectUtil.checkNotNull(v, "output"));
        return this;
    }

    public DomainNameMapping<V> build() {
        return new a(this.a, this.b);
    }

    /* loaded from: classes4.dex */
    private static final class a<V> extends DomainNameMapping<V> {
        private static final int b = 46;
        private final String[] c;
        private final V[] d;
        private final Map<String, V> e;

        private a(V v, Map<String, V> map) {
            super((Map) null, v);
            Set<Map.Entry<String, V>> entrySet = map.entrySet();
            int size = entrySet.size();
            this.c = new String[size];
            this.d = (V[]) new Object[size];
            LinkedHashMap linkedHashMap = new LinkedHashMap(map.size());
            int i = 0;
            for (Map.Entry<String, V> entry : entrySet) {
                String a = a(entry.getKey());
                V value = entry.getValue();
                this.c[i] = a;
                this.d[i] = value;
                linkedHashMap.put(a, value);
                i++;
            }
            this.e = Collections.unmodifiableMap(linkedHashMap);
        }

        @Override // io.netty.util.DomainNameMapping
        @Deprecated
        public DomainNameMapping<V> add(String str, V v) {
            throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
        }

        @Override // io.netty.util.DomainNameMapping
        public V map(String str) {
            if (str != null) {
                String a = a(str);
                int length = this.c.length;
                for (int i = 0; i < length; i++) {
                    if (a(this.c[i], a)) {
                        return this.d[i];
                    }
                }
            }
            return (V) this.a;
        }

        @Override // io.netty.util.DomainNameMapping
        public Map<String, V> asMap() {
            return this.e;
        }

        @Override // io.netty.util.DomainNameMapping
        public String toString() {
            String obj = this.a.toString();
            String[] strArr = this.c;
            int length = strArr.length;
            if (length == 0) {
                return "ImmutableDomainNameMapping(default: " + obj + ", map: {})";
            }
            String str = strArr[0];
            String obj2 = this.d[0].toString();
            StringBuilder sb = new StringBuilder(a(obj.length(), length, str.length() + obj2.length() + 3));
            sb.append("ImmutableDomainNameMapping(default: ");
            sb.append(obj);
            sb.append(", map: {");
            a(sb, str, obj2);
            for (int i = 1; i < length; i++) {
                sb.append(", ");
                a(sb, i);
            }
            sb.append("})");
            return sb.toString();
        }

        private static int a(int i, int i2, int i3) {
            return b + i + ((int) (i3 * i2 * 1.1d));
        }

        private StringBuilder a(StringBuilder sb, int i) {
            return a(sb, this.c[i], this.d[i].toString());
        }

        private static StringBuilder a(StringBuilder sb, String str, String str2) {
            sb.append(str);
            sb.append('=');
            sb.append(str2);
            return sb;
        }
    }
}
