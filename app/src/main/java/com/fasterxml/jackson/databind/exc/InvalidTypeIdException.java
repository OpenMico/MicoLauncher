package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;

/* loaded from: classes.dex */
public class InvalidTypeIdException extends MismatchedInputException {
    private static final long serialVersionUID = 1;
    protected final JavaType _baseType;
    protected final String _typeId;

    public InvalidTypeIdException(JsonParser jsonParser, String str, JavaType javaType, String str2) {
        super(jsonParser, str);
        this._baseType = javaType;
        this._typeId = str2;
    }

    public static InvalidTypeIdException from(JsonParser jsonParser, String str, JavaType javaType, String str2) {
        return new InvalidTypeIdException(jsonParser, str, javaType, str2);
    }

    public JavaType getBaseType() {
        return this._baseType;
    }

    public String getTypeId() {
        return this._typeId;
    }
}
