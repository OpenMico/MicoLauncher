package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.exc.InvalidNullException;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.Serializable;

/* loaded from: classes.dex */
public class NullsFailProvider implements NullValueProvider, Serializable {
    private static final long serialVersionUID = 1;
    protected final PropertyName _name;
    protected final JavaType _type;

    protected NullsFailProvider(PropertyName propertyName, JavaType javaType) {
        this._name = propertyName;
        this._type = javaType;
    }

    public static NullsFailProvider constructForProperty(BeanProperty beanProperty) {
        return new NullsFailProvider(beanProperty.getFullName(), beanProperty.getType());
    }

    public static NullsFailProvider constructForRootValue(JavaType javaType) {
        return new NullsFailProvider(null, javaType);
    }

    @Override // com.fasterxml.jackson.databind.deser.NullValueProvider
    public AccessPattern getNullAccessPattern() {
        return AccessPattern.DYNAMIC;
    }

    @Override // com.fasterxml.jackson.databind.deser.NullValueProvider
    public Object getNullValue(DeserializationContext deserializationContext) throws JsonMappingException {
        throw InvalidNullException.from(deserializationContext, this._name, this._type);
    }
}
