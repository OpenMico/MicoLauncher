package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.util.AccessPattern;

/* loaded from: classes.dex */
public interface NullValueProvider {
    AccessPattern getNullAccessPattern();

    Object getNullValue(DeserializationContext deserializationContext) throws JsonMappingException;
}
