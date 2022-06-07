package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;

/* loaded from: classes.dex */
public class MinimalClassNameIdResolver extends ClassNameIdResolver {
    protected final String _basePackageName;
    protected final String _basePackagePrefix;

    /* JADX INFO: Access modifiers changed from: protected */
    public MinimalClassNameIdResolver(JavaType javaType, TypeFactory typeFactory) {
        super(javaType, typeFactory);
        String name = javaType.getRawClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf < 0) {
            this._basePackageName = "";
            this._basePackagePrefix = ".";
            return;
        }
        this._basePackagePrefix = name.substring(0, lastIndexOf + 1);
        this._basePackageName = name.substring(0, lastIndexOf);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver, com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.MINIMAL_CLASS;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver, com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public String idFromValue(Object obj) {
        String name = obj.getClass().getName();
        return name.startsWith(this._basePackagePrefix) ? name.substring(this._basePackagePrefix.length() - 1) : name;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver
    public JavaType _typeFromId(String str, DatabindContext databindContext) throws IOException {
        if (str.startsWith(".")) {
            StringBuilder sb = new StringBuilder(str.length() + this._basePackageName.length());
            if (this._basePackageName.length() == 0) {
                sb.append(str.substring(1));
            } else {
                sb.append(this._basePackageName);
                sb.append(str);
            }
            str = sb.toString();
        }
        return super._typeFromId(str, databindContext);
    }
}
