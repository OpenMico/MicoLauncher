package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class SimpleType extends TypeBase {
    private static final long serialVersionUID = 1;

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean hasContentType() {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType refine(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleType(Class<?> cls) {
        this(cls, TypeBindings.emptyBindings(), null, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleType(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        this(cls, typeBindings, javaType, javaTypeArr, null, null, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleType(TypeBase typeBase) {
        super(typeBase);
    }

    protected SimpleType(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr, Object obj, Object obj2, boolean z) {
        super(cls, typeBindings, javaType, javaTypeArr, 0, obj, obj2, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleType(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr, int i, Object obj, Object obj2, boolean z) {
        super(cls, typeBindings, javaType, javaTypeArr, i, obj, obj2, z);
    }

    public static SimpleType constructUnsafe(Class<?> cls) {
        return new SimpleType(cls, null, null, null, null, null, false);
    }

    @Deprecated
    public static SimpleType construct(Class<?> cls) {
        if (Map.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Cannot construct SimpleType for a Map (class: " + cls.getName() + ")");
        } else if (Collection.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Cannot construct SimpleType for a Collection (class: " + cls.getName() + ")");
        } else if (!cls.isArray()) {
            TypeBindings emptyBindings = TypeBindings.emptyBindings();
            return new SimpleType(cls, emptyBindings, a(cls.getSuperclass(), emptyBindings), null, null, null, false);
        } else {
            throw new IllegalArgumentException("Cannot construct SimpleType for an array (class: " + cls.getName() + ")");
        }
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    @Deprecated
    protected JavaType _narrow(Class<?> cls) {
        if (this._class == cls) {
            return this;
        }
        if (!this._class.isAssignableFrom(cls)) {
            return new SimpleType(cls, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass == this._class) {
            return new SimpleType(cls, this._bindings, this, this._superInterfaces, this._valueHandler, this._typeHandler, this._asStatic);
        }
        if (superclass != null && this._class.isAssignableFrom(superclass)) {
            return new SimpleType(cls, this._bindings, _narrow(superclass), null, this._valueHandler, this._typeHandler, this._asStatic);
        }
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> cls2 : interfaces) {
            if (cls2 == this._class) {
                return new SimpleType(cls, this._bindings, null, new JavaType[]{this}, this._valueHandler, this._typeHandler, this._asStatic);
            }
            if (this._class.isAssignableFrom(cls2)) {
                return new SimpleType(cls, this._bindings, null, new JavaType[]{_narrow(cls2)}, this._valueHandler, this._typeHandler, this._asStatic);
            }
        }
        throw new IllegalArgumentException("Internal error: Cannot resolve sub-type for Class " + cls.getName() + " to " + this._class.getName());
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withContentType(JavaType javaType) {
        throw new IllegalArgumentException("Simple types have no content types; cannot call withContentType()");
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withTypeHandler(Object obj) {
        return this._typeHandler == obj ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, obj, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withContentTypeHandler(Object obj) {
        throw new IllegalArgumentException("Simple types have no content types; cannot call withContenTypeHandler()");
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withValueHandler(Object obj) {
        return obj == this._valueHandler ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, obj, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withContentValueHandler(Object obj) {
        throw new IllegalArgumentException("Simple types have no content types; cannot call withContenValueHandler()");
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withStaticTyping() {
        return this._asStatic ? this : new SimpleType(this._class, this._bindings, this._superClass, this._superInterfaces, this._valueHandler, this._typeHandler, true);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        int size = this._bindings.size();
        if (size > 0) {
            sb.append(Typography.less);
            for (int i = 0; i < size; i++) {
                JavaType containedType = containedType(i);
                if (i > 0) {
                    sb.append(StringUtil.COMMA);
                }
                sb.append(containedType.toCanonical());
            }
            sb.append(Typography.greater);
        }
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getErasedSignature(StringBuilder sb) {
        return _classSignature(this._class, sb, true);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getGenericSignature(StringBuilder sb) {
        _classSignature(this._class, sb, false);
        int size = this._bindings.size();
        if (size > 0) {
            sb.append(Typography.less);
            for (int i = 0; i < size; i++) {
                sb = containedType(i).getGenericSignature(sb);
            }
            sb.append(Typography.greater);
        }
        sb.append(';');
        return sb;
    }

    private static JavaType a(Class<?> cls, TypeBindings typeBindings) {
        if (cls == null) {
            return null;
        }
        if (cls == Object.class) {
            return TypeFactory.unknownType();
        }
        return new SimpleType(cls, typeBindings, a(cls.getSuperclass(), typeBindings), null, null, null, false);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("[simple type, class ");
        sb.append(buildCanonicalName());
        sb.append(']');
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        SimpleType simpleType = (SimpleType) obj;
        if (simpleType._class != this._class) {
            return false;
        }
        return this._bindings.equals(simpleType._bindings);
    }
}
