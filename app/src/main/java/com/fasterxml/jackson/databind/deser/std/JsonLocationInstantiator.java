package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

/* loaded from: classes.dex */
public class JsonLocationInstantiator extends ValueInstantiator.Base {
    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromObjectWith() {
        return true;
    }

    public JsonLocationInstantiator() {
        super(JsonLocation.class);
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig deserializationConfig) {
        JavaType constructType = deserializationConfig.constructType(Integer.TYPE);
        JavaType constructType2 = deserializationConfig.constructType(Long.TYPE);
        return new SettableBeanProperty[]{a("sourceRef", deserializationConfig.constructType(Object.class), 0), a("byteOffset", constructType2, 1), a("charOffset", constructType2, 2), a("lineNr", constructType, 3), a("columnNr", constructType, 4)};
    }

    private static CreatorProperty a(String str, JavaType javaType, int i) {
        return new CreatorProperty(PropertyName.construct(str), javaType, null, null, null, null, i, null, PropertyMetadata.STD_REQUIRED);
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromObjectWith(DeserializationContext deserializationContext, Object[] objArr) {
        return new JsonLocation(objArr[0], a(objArr[1]), a(objArr[2]), b(objArr[3]), b(objArr[4]));
    }

    private static final long a(Object obj) {
        if (obj == null) {
            return 0L;
        }
        return ((Number) obj).longValue();
    }

    private static final int b(Object obj) {
        if (obj == null) {
            return 0;
        }
        return ((Number) obj).intValue();
    }
}
