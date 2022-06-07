package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor8;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class TypeName {
    public final List<AnnotationSpec> annotations;
    private final String j;
    private String k;
    public static final TypeName VOID = new TypeName("void");
    public static final TypeName BOOLEAN = new TypeName("boolean");
    public static final TypeName BYTE = new TypeName("byte");
    public static final TypeName SHORT = new TypeName("short");
    public static final TypeName INT = new TypeName("int");
    public static final TypeName LONG = new TypeName("long");
    public static final TypeName CHAR = new TypeName("char");
    public static final TypeName FLOAT = new TypeName("float");
    public static final TypeName DOUBLE = new TypeName("double");
    public static final ClassName OBJECT = ClassName.get("java.lang", "Object", new String[0]);
    private static final ClassName a = ClassName.get("java.lang", "Void", new String[0]);
    private static final ClassName b = ClassName.get("java.lang", "Boolean", new String[0]);
    private static final ClassName c = ClassName.get("java.lang", "Byte", new String[0]);
    private static final ClassName d = ClassName.get("java.lang", "Short", new String[0]);
    private static final ClassName e = ClassName.get("java.lang", "Integer", new String[0]);
    private static final ClassName f = ClassName.get("java.lang", "Long", new String[0]);
    private static final ClassName g = ClassName.get("java.lang", "Character", new String[0]);
    private static final ClassName h = ClassName.get("java.lang", "Float", new String[0]);
    private static final ClassName i = ClassName.get("java.lang", "Double", new String[0]);

    private TypeName(String str) {
        this(str, new ArrayList());
    }

    private TypeName(String str, List<AnnotationSpec> list) {
        this.j = str;
        this.annotations = c.a(list);
    }

    public TypeName(List<AnnotationSpec> list) {
        this(null, list);
    }

    public final TypeName annotated(AnnotationSpec... annotationSpecArr) {
        return annotated(Arrays.asList(annotationSpecArr));
    }

    public TypeName annotated(List<AnnotationSpec> list) {
        c.a(list, "annotations == null", new Object[0]);
        return new TypeName(this.j, concatAnnotations(list));
    }

    public TypeName withoutAnnotations() {
        return new TypeName(this.j);
    }

    protected final List<AnnotationSpec> concatAnnotations(List<AnnotationSpec> list) {
        ArrayList arrayList = new ArrayList(this.annotations);
        arrayList.addAll(list);
        return arrayList;
    }

    public boolean isAnnotated() {
        return !this.annotations.isEmpty();
    }

    public boolean isPrimitive() {
        return (this.j == null || this == VOID) ? false : true;
    }

    public boolean isBoxedPrimitive() {
        return equals(b) || equals(c) || equals(d) || equals(e) || equals(f) || equals(g) || equals(h) || equals(i);
    }

    public TypeName box() {
        String str = this.j;
        if (str == null) {
            return this;
        }
        if (this == VOID) {
            return a;
        }
        if (this == BOOLEAN) {
            return b;
        }
        if (this == BYTE) {
            return c;
        }
        if (this == SHORT) {
            return d;
        }
        if (this == INT) {
            return e;
        }
        if (this == LONG) {
            return f;
        }
        if (this == CHAR) {
            return g;
        }
        if (this == FLOAT) {
            return h;
        }
        if (this == DOUBLE) {
            return i;
        }
        throw new AssertionError(str);
    }

    public TypeName unbox() {
        if (this.j != null) {
            return this;
        }
        if (equals(a)) {
            return VOID;
        }
        if (equals(b)) {
            return BOOLEAN;
        }
        if (equals(c)) {
            return BYTE;
        }
        if (equals(d)) {
            return SHORT;
        }
        if (equals(e)) {
            return INT;
        }
        if (equals(f)) {
            return LONG;
        }
        if (equals(g)) {
            return CHAR;
        }
        if (equals(h)) {
            return FLOAT;
        }
        if (equals(i)) {
            return DOUBLE;
        }
        throw new UnsupportedOperationException("cannot unbox " + this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public final int hashCode() {
        return toString().hashCode();
    }

    public final String toString() {
        String str = this.k;
        if (str != null) {
            return str;
        }
        try {
            StringBuilder sb = new StringBuilder();
            a(new a(sb));
            String sb2 = sb.toString();
            this.k = sb2;
            return sb2;
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public a a(a aVar) throws IOException {
        if (this.j != null) {
            if (isAnnotated()) {
                aVar.b("");
                b(aVar);
            }
            return aVar.c(this.j);
        }
        throw new AssertionError();
    }

    a b(a aVar) throws IOException {
        for (AnnotationSpec annotationSpec : this.annotations) {
            annotationSpec.a(aVar, true);
            aVar.b(StringUtils.SPACE);
        }
        return aVar;
    }

    public static TypeName get(TypeMirror typeMirror) {
        return a(typeMirror, new LinkedHashMap());
    }

    public static TypeName a(TypeMirror typeMirror, final Map<TypeParameterElement, TypeVariableName> map) {
        return (TypeName) typeMirror.accept(new SimpleTypeVisitor8<TypeName, Void>() { // from class: com.squareup.javapoet.x2c.TypeName.1
        }, (Object) null);
    }

    public static TypeName get(Type type) {
        return a(type, new LinkedHashMap());
    }

    public static TypeName a(Type type, Map<Type, TypeVariableName> map) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return type == Void.TYPE ? VOID : type == Boolean.TYPE ? BOOLEAN : type == Byte.TYPE ? BYTE : type == Short.TYPE ? SHORT : type == Integer.TYPE ? INT : type == Long.TYPE ? LONG : type == Character.TYPE ? CHAR : type == Float.TYPE ? FLOAT : type == Double.TYPE ? DOUBLE : cls.isArray() ? ArrayTypeName.of(a(cls.getComponentType(), map)) : ClassName.get((Class<?>) cls);
        } else if (type instanceof ParameterizedType) {
            return ParameterizedTypeName.a((ParameterizedType) type, map);
        } else {
            if (type instanceof WildcardType) {
                return WildcardTypeName.a((WildcardType) type, map);
            }
            if (type instanceof TypeVariable) {
                return TypeVariableName.a((TypeVariable<?>) type, map);
            }
            if (type instanceof GenericArrayType) {
                return ArrayTypeName.a((GenericArrayType) type, map);
            }
            throw new IllegalArgumentException("unexpected type: " + type);
        }
    }

    public static List<TypeName> a(Type[] typeArr) {
        return a(typeArr, new LinkedHashMap());
    }

    public static List<TypeName> a(Type[] typeArr, Map<Type, TypeVariableName> map) {
        ArrayList arrayList = new ArrayList(typeArr.length);
        for (Type type : typeArr) {
            arrayList.add(a(type, map));
        }
        return arrayList;
    }

    public static ArrayTypeName a(TypeName typeName) {
        if (typeName instanceof ArrayTypeName) {
            return (ArrayTypeName) typeName;
        }
        return null;
    }
}
