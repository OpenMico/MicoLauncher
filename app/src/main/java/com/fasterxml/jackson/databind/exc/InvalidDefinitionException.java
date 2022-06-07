package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

/* loaded from: classes.dex */
public class InvalidDefinitionException extends JsonMappingException {
    protected transient BeanDescription _beanDesc;
    protected transient BeanPropertyDefinition _property;
    protected final JavaType _type;

    protected InvalidDefinitionException(JsonParser jsonParser, String str, JavaType javaType) {
        super(jsonParser, str);
        this._type = javaType;
        this._beanDesc = null;
        this._property = null;
    }

    protected InvalidDefinitionException(JsonGenerator jsonGenerator, String str, JavaType javaType) {
        super(jsonGenerator, str);
        this._type = javaType;
        this._beanDesc = null;
        this._property = null;
    }

    protected InvalidDefinitionException(JsonParser jsonParser, String str, BeanDescription beanDescription, BeanPropertyDefinition beanPropertyDefinition) {
        super(jsonParser, str);
        this._type = beanDescription == null ? null : beanDescription.getType();
        this._beanDesc = beanDescription;
        this._property = beanPropertyDefinition;
    }

    protected InvalidDefinitionException(JsonGenerator jsonGenerator, String str, BeanDescription beanDescription, BeanPropertyDefinition beanPropertyDefinition) {
        super(jsonGenerator, str);
        this._type = beanDescription == null ? null : beanDescription.getType();
        this._beanDesc = beanDescription;
        this._property = beanPropertyDefinition;
    }

    public static InvalidDefinitionException from(JsonParser jsonParser, String str, BeanDescription beanDescription, BeanPropertyDefinition beanPropertyDefinition) {
        return new InvalidDefinitionException(jsonParser, str, beanDescription, beanPropertyDefinition);
    }

    public static InvalidDefinitionException from(JsonParser jsonParser, String str, JavaType javaType) {
        return new InvalidDefinitionException(jsonParser, str, javaType);
    }

    public static InvalidDefinitionException from(JsonGenerator jsonGenerator, String str, BeanDescription beanDescription, BeanPropertyDefinition beanPropertyDefinition) {
        return new InvalidDefinitionException(jsonGenerator, str, beanDescription, beanPropertyDefinition);
    }

    public static InvalidDefinitionException from(JsonGenerator jsonGenerator, String str, JavaType javaType) {
        return new InvalidDefinitionException(jsonGenerator, str, javaType);
    }

    public JavaType getType() {
        return this._type;
    }

    public BeanDescription getBeanDescription() {
        return this._beanDesc;
    }

    public BeanPropertyDefinition getProperty() {
        return this._property;
    }
}
