package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.umeng.analytics.pro.ai;
import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class TypeBindings implements Serializable {
    private static final String[] a = new String[0];
    private static final JavaType[] b = new JavaType[0];
    private static final TypeBindings c = new TypeBindings(a, b, null);
    private static final long serialVersionUID = 1;
    private final int _hashCode;
    private final String[] _names;
    private final JavaType[] _types;
    private final String[] _unboundVariables;

    private TypeBindings(String[] strArr, JavaType[] javaTypeArr, String[] strArr2) {
        this._names = strArr == null ? a : strArr;
        this._types = javaTypeArr == null ? b : javaTypeArr;
        int length = this._names.length;
        JavaType[] javaTypeArr2 = this._types;
        if (length == javaTypeArr2.length) {
            int length2 = javaTypeArr2.length;
            int i = 1;
            for (int i2 = 0; i2 < length2; i2++) {
                i += this._types[i2].hashCode();
            }
            this._unboundVariables = strArr2;
            this._hashCode = i;
            return;
        }
        throw new IllegalArgumentException("Mismatching names (" + this._names.length + "), types (" + this._types.length + ")");
    }

    public static TypeBindings emptyBindings() {
        return c;
    }

    protected Object readResolve() {
        String[] strArr = this._names;
        return (strArr == null || strArr.length == 0) ? c : this;
    }

    public static TypeBindings create(Class<?> cls, List<JavaType> list) {
        JavaType[] javaTypeArr;
        if (list == null || list.isEmpty()) {
            javaTypeArr = b;
        } else {
            javaTypeArr = (JavaType[]) list.toArray(new JavaType[list.size()]);
        }
        return create(cls, javaTypeArr);
    }

    public static TypeBindings create(Class<?> cls, JavaType[] javaTypeArr) {
        String[] strArr;
        if (javaTypeArr == null) {
            javaTypeArr = b;
        } else {
            switch (javaTypeArr.length) {
                case 1:
                    return create(cls, javaTypeArr[0]);
                case 2:
                    return create(cls, javaTypeArr[0], javaTypeArr[1]);
            }
        }
        TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
        if (typeParameters == null || typeParameters.length == 0) {
            strArr = a;
        } else {
            int length = typeParameters.length;
            strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = typeParameters[i].getName();
            }
        }
        if (strArr.length == javaTypeArr.length) {
            return new TypeBindings(strArr, javaTypeArr, null);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot create TypeBindings for class ");
        sb.append(cls.getName());
        sb.append(" with ");
        sb.append(javaTypeArr.length);
        sb.append(" type parameter");
        sb.append(javaTypeArr.length == 1 ? "" : ai.az);
        sb.append(": class expects ");
        sb.append(strArr.length);
        throw new IllegalArgumentException(sb.toString());
    }

    public static TypeBindings create(Class<?> cls, JavaType javaType) {
        TypeVariable<?>[] a2 = b.a(cls);
        int length = a2 == null ? 0 : a2.length;
        if (length == 1) {
            return new TypeBindings(new String[]{a2[0].getName()}, new JavaType[]{javaType}, null);
        }
        throw new IllegalArgumentException("Cannot create TypeBindings for class " + cls.getName() + " with 1 type parameter: class expects " + length);
    }

    public static TypeBindings create(Class<?> cls, JavaType javaType, JavaType javaType2) {
        TypeVariable<?>[] b2 = b.b(cls);
        int length = b2 == null ? 0 : b2.length;
        if (length == 2) {
            return new TypeBindings(new String[]{b2[0].getName(), b2[1].getName()}, new JavaType[]{javaType, javaType2}, null);
        }
        throw new IllegalArgumentException("Cannot create TypeBindings for class " + cls.getName() + " with 2 type parameters: class expects " + length);
    }

    public static TypeBindings createIfNeeded(Class<?> cls, JavaType javaType) {
        TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
        int length = typeParameters == null ? 0 : typeParameters.length;
        if (length == 0) {
            return c;
        }
        if (length == 1) {
            return new TypeBindings(new String[]{typeParameters[0].getName()}, new JavaType[]{javaType}, null);
        }
        throw new IllegalArgumentException("Cannot create TypeBindings for class " + cls.getName() + " with 1 type parameter: class expects " + length);
    }

    public static TypeBindings createIfNeeded(Class<?> cls, JavaType[] javaTypeArr) {
        TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
        if (typeParameters == null || typeParameters.length == 0) {
            return c;
        }
        if (javaTypeArr == null) {
            javaTypeArr = b;
        }
        int length = typeParameters.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = typeParameters[i].getName();
        }
        if (strArr.length == javaTypeArr.length) {
            return new TypeBindings(strArr, javaTypeArr, null);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot create TypeBindings for class ");
        sb.append(cls.getName());
        sb.append(" with ");
        sb.append(javaTypeArr.length);
        sb.append(" type parameter");
        sb.append(javaTypeArr.length == 1 ? "" : ai.az);
        sb.append(": class expects ");
        sb.append(strArr.length);
        throw new IllegalArgumentException(sb.toString());
    }

    public TypeBindings withUnboundVariable(String str) {
        String[] strArr;
        String[] strArr2 = this._unboundVariables;
        int length = strArr2 == null ? 0 : strArr2.length;
        if (length == 0) {
            strArr = new String[1];
        } else {
            strArr = (String[]) Arrays.copyOf(this._unboundVariables, length + 1);
        }
        strArr[length] = str;
        return new TypeBindings(this._names, this._types, strArr);
    }

    public JavaType findBoundType(String str) {
        JavaType selfReferencedType;
        int length = this._names.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(this._names[i])) {
                JavaType javaType = this._types[i];
                return (!(javaType instanceof ResolvedRecursiveType) || (selfReferencedType = ((ResolvedRecursiveType) javaType).getSelfReferencedType()) == null) ? javaType : selfReferencedType;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this._types.length == 0;
    }

    public int size() {
        return this._types.length;
    }

    public String getBoundName(int i) {
        if (i < 0) {
            return null;
        }
        String[] strArr = this._names;
        if (i >= strArr.length) {
            return null;
        }
        return strArr[i];
    }

    public JavaType getBoundType(int i) {
        if (i < 0) {
            return null;
        }
        JavaType[] javaTypeArr = this._types;
        if (i >= javaTypeArr.length) {
            return null;
        }
        return javaTypeArr[i];
    }

    public List<JavaType> getTypeParameters() {
        JavaType[] javaTypeArr = this._types;
        if (javaTypeArr.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(javaTypeArr);
    }

    public boolean hasUnbound(String str) {
        String[] strArr = this._unboundVariables;
        if (strArr == null) {
            return false;
        }
        int length = strArr.length;
        do {
            length--;
            if (length < 0) {
                return false;
            }
        } while (!str.equals(this._unboundVariables[length]));
        return true;
    }

    public Object asKey(Class<?> cls) {
        return new a(cls, this._types, this._hashCode);
    }

    public String toString() {
        if (this._types.length == 0) {
            return "<>";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Typography.less);
        int length = this._types.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(this._types[i].getGenericSignature());
        }
        sb.append(Typography.greater);
        return sb.toString();
    }

    public int hashCode() {
        return this._hashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!ClassUtil.hasClass(obj, getClass())) {
            return false;
        }
        TypeBindings typeBindings = (TypeBindings) obj;
        int length = this._types.length;
        if (length != typeBindings.size()) {
            return false;
        }
        JavaType[] javaTypeArr = typeBindings._types;
        for (int i = 0; i < length; i++) {
            if (!javaTypeArr[i].equals(this._types[i])) {
                return false;
            }
        }
        return true;
    }

    public JavaType[] typeParameterArray() {
        return this._types;
    }

    /* loaded from: classes.dex */
    public static class b {
        private static final TypeVariable<?>[] a = AbstractList.class.getTypeParameters();
        private static final TypeVariable<?>[] b = Collection.class.getTypeParameters();
        private static final TypeVariable<?>[] c = Iterable.class.getTypeParameters();
        private static final TypeVariable<?>[] d = List.class.getTypeParameters();
        private static final TypeVariable<?>[] e = ArrayList.class.getTypeParameters();
        private static final TypeVariable<?>[] f = Map.class.getTypeParameters();
        private static final TypeVariable<?>[] g = HashMap.class.getTypeParameters();
        private static final TypeVariable<?>[] h = LinkedHashMap.class.getTypeParameters();

        public static TypeVariable<?>[] a(Class<?> cls) {
            if (cls == Collection.class) {
                return b;
            }
            if (cls == List.class) {
                return d;
            }
            if (cls == ArrayList.class) {
                return e;
            }
            if (cls == AbstractList.class) {
                return a;
            }
            if (cls == Iterable.class) {
                return c;
            }
            return cls.getTypeParameters();
        }

        public static TypeVariable<?>[] b(Class<?> cls) {
            if (cls == Map.class) {
                return f;
            }
            if (cls == HashMap.class) {
                return g;
            }
            if (cls == LinkedHashMap.class) {
                return h;
            }
            return cls.getTypeParameters();
        }
    }

    /* loaded from: classes.dex */
    public static final class a {
        private final Class<?> a;
        private final JavaType[] b;
        private final int c;

        public a(Class<?> cls, JavaType[] javaTypeArr, int i) {
            this.a = cls;
            this.b = javaTypeArr;
            this.c = i;
        }

        public int hashCode() {
            return this.c;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            a aVar = (a) obj;
            if (this.c == aVar.c && this.a == aVar.a) {
                JavaType[] javaTypeArr = aVar.b;
                int length = this.b.length;
                if (length == javaTypeArr.length) {
                    for (int i = 0; i < length; i++) {
                        if (!this.b[i].equals(javaTypeArr[i])) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return this.a.getName() + "<>";
        }
    }
}
