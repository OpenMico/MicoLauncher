package io.netty.handler.codec;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.util.HashingStrategy;

/* loaded from: classes4.dex */
public final class DefaultHeadersImpl<K, V> extends DefaultHeaders<K, V, DefaultHeadersImpl<K, V>> {
    public DefaultHeadersImpl(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, DefaultHeaders.NameValidator<K> nameValidator) {
        super(hashingStrategy, valueConverter, nameValidator);
    }
}
