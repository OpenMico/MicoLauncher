package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class TypeBase extends JavaType implements JsonSerializable {
    private static final TypeBindings b = TypeBindings.emptyBindings();
    private static final JavaType[] c = new JavaType[0];
    private static final long serialVersionUID = 1;
    protected final TypeBindings _bindings;
    protected final JavaType _superClass;
    protected final JavaType[] _superInterfaces;
    volatile transient String a;

    @Override // com.fasterxml.jackson.databind.JavaType
    public abstract StringBuilder getErasedSignature(StringBuilder sb);

    @Override // com.fasterxml.jackson.databind.JavaType
    public abstract StringBuilder getGenericSignature(StringBuilder sb);

    public TypeBase(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr, int i, Object obj, Object obj2, boolean z) {
        super(cls, i, obj, obj2, z);
        this._bindings = typeBindings == null ? b : typeBindings;
        this._superClass = javaType;
        this._superInterfaces = javaTypeArr;
    }

    public TypeBase(TypeBase typeBase) {
        super(typeBase);
        this._superClass = typeBase._superClass;
        this._superInterfaces = typeBase._superInterfaces;
        this._bindings = typeBase._bindings;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public String toCanonical() {
        String str = this.a;
        return str == null ? buildCanonicalName() : str;
    }

    protected String buildCanonicalName() {
        return this._class.getName();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public TypeBindings getBindings() {
        return this._bindings;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public int containedTypeCount() {
        return this._bindings.size();
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType containedType(int i) {
        return this._bindings.getBoundType(i);
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    @Deprecated
    public String containedTypeName(int i) {
        return this._bindings.getBoundName(i);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType getSuperClass() {
        return this._superClass;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public List<JavaType> getInterfaces() {
        JavaType[] javaTypeArr = this._superInterfaces;
        if (javaTypeArr == null) {
            return Collections.emptyList();
        }
        switch (javaTypeArr.length) {
            case 0:
                return Collections.emptyList();
            case 1:
                return Collections.singletonList(javaTypeArr[0]);
            default:
                return Arrays.asList(javaTypeArr);
        }
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public final JavaType findSuperType(Class<?> cls) {
        JavaType findSuperType;
        JavaType[] javaTypeArr;
        if (cls == this._class) {
            return this;
        }
        if (cls.isInterface() && (javaTypeArr = this._superInterfaces) != null) {
            int length = javaTypeArr.length;
            for (int i = 0; i < length; i++) {
                JavaType findSuperType2 = this._superInterfaces[i].findSuperType(cls);
                if (findSuperType2 != null) {
                    return findSuperType2;
                }
            }
        }
        JavaType javaType = this._superClass;
        if (javaType == null || (findSuperType = javaType.findSuperType(cls)) == null) {
            return null;
        }
        return findSuperType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType[] findTypeParameters(Class<?> cls) {
        JavaType findSuperType = findSuperType(cls);
        if (findSuperType == null) {
            return c;
        }
        return findSuperType.getBindings().typeParameterArray();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writableTypeId = new WritableTypeId(this, JsonToken.VALUE_STRING);
        typeSerializer.writeTypePrefix(jsonGenerator, writableTypeId);
        serialize(jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeId);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(toCanonical());
    }

    protected static StringBuilder _classSignature(Class<?> cls, StringBuilder sb, boolean z) {
        if (!cls.isPrimitive()) {
            sb.append('L');
            String name = cls.getName();
            int length = name.length();
            for (int i = 0; i < length; i++) {
                char charAt = name.charAt(i);
                if (charAt == '.') {
                    charAt = JsonPointer.SEPARATOR;
                }
                sb.append(charAt);
            }
            if (z) {
                sb.append(';');
            }
        } else if (cls == Boolean.TYPE) {
            sb.append('Z');
        } else if (cls == Byte.TYPE) {
            sb.append('B');
        } else if (cls == Short.TYPE) {
            sb.append('S');
        } else if (cls == Character.TYPE) {
            sb.append('C');
        } else if (cls == Integer.TYPE) {
            sb.append('I');
        } else if (cls == Long.TYPE) {
            sb.append('J');
        } else if (cls == Float.TYPE) {
            sb.append('F');
        } else if (cls == Double.TYPE) {
            sb.append('D');
        } else if (cls == Void.TYPE) {
            sb.append('V');
        } else {
            throw new IllegalStateException("Unrecognized primitive type: " + cls.getName());
        }
        return sb;
    }

    protected static JavaType _bogusSuperClass(Class<?> cls) {
        if (cls.getSuperclass() == null) {
            return null;
        }
        return TypeFactory.unknownType();
    }
}
