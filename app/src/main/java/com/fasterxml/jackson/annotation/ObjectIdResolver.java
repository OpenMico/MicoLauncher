package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/* loaded from: classes.dex */
public interface ObjectIdResolver {
    void bindItem(ObjectIdGenerator.IdKey idKey, Object obj);

    boolean canUseFor(ObjectIdResolver objectIdResolver);

    ObjectIdResolver newForDeserialization(Object obj);

    Object resolveId(ObjectIdGenerator.IdKey idKey);
}
