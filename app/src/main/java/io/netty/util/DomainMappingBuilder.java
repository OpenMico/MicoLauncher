package io.netty.util;

@Deprecated
/* loaded from: classes4.dex */
public final class DomainMappingBuilder<V> {
    private final DomainNameMappingBuilder<V> a;

    public DomainMappingBuilder(V v) {
        this.a = new DomainNameMappingBuilder<>(v);
    }

    public DomainMappingBuilder(int i, V v) {
        this.a = new DomainNameMappingBuilder<>(i, v);
    }

    public DomainMappingBuilder<V> add(String str, V v) {
        this.a.add(str, v);
        return this;
    }

    public DomainNameMapping<V> build() {
        return this.a.build();
    }
}
