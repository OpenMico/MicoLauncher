package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.util.ClassUtil;

/* loaded from: classes.dex */
public class InvalidNullException extends MismatchedInputException {
    private static final long serialVersionUID = 1;
    protected final PropertyName _propertyName;

    protected InvalidNullException(DeserializationContext deserializationContext, String str, PropertyName propertyName) {
        super(deserializationContext.getParser(), str);
        this._propertyName = propertyName;
    }

    public static InvalidNullException from(DeserializationContext deserializationContext, PropertyName propertyName, JavaType javaType) {
        InvalidNullException invalidNullException = new InvalidNullException(deserializationContext, String.format("Invalid `null` value encountered for property %s", ClassUtil.quotedOr(propertyName, "<UNKNOWN>")), propertyName);
        if (javaType != null) {
            invalidNullException.setTargetType(javaType);
        }
        return invalidNullException;
    }

    public PropertyName getPropertyName() {
        return this._propertyName;
    }
}
