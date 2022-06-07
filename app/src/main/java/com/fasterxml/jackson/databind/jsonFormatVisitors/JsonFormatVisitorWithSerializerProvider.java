package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.SerializerProvider;

/* loaded from: classes.dex */
public interface JsonFormatVisitorWithSerializerProvider {
    SerializerProvider getProvider();

    void setProvider(SerializerProvider serializerProvider);
}
