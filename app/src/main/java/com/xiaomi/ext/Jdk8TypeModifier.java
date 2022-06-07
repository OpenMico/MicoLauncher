package com.xiaomi.ext;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.xiaomi.common.Optional;
import java.lang.reflect.Type;

/* loaded from: classes3.dex */
public class Jdk8TypeModifier extends TypeModifier {
    @Override // com.fasterxml.jackson.databind.type.TypeModifier
    public JavaType modifyType(JavaType javaType, Type type, TypeBindings typeBindings, TypeFactory typeFactory) {
        return (javaType.isReferenceType() || javaType.isContainerType() || javaType.getRawClass() != Optional.class) ? javaType : ReferenceType.upgradeFrom(javaType, javaType.containedTypeOrUnknown(0));
    }
}
