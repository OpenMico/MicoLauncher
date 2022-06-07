package org.apache.commons.lang3.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.apache.commons.lang3.Validate;

/* loaded from: classes5.dex */
public abstract class TypeLiteral<T> implements Typed<T> {
    private static final TypeVariable<Class<TypeLiteral>> a = TypeLiteral.class.getTypeParameters()[0];
    public final Type value = (Type) Validate.notNull(TypeUtils.getTypeArguments(getClass(), TypeLiteral.class).get(a), "%s does not assign type parameter %s", getClass(), TypeUtils.toLongString(a));
    private final String b = String.format("%s<%s>", TypeLiteral.class.getSimpleName(), TypeUtils.toString(this.value));

    protected TypeLiteral() {
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TypeLiteral)) {
            return false;
        }
        return TypeUtils.equals(this.value, ((TypeLiteral) obj).value);
    }

    public int hashCode() {
        return this.value.hashCode() | 592;
    }

    public String toString() {
        return this.b;
    }

    @Override // org.apache.commons.lang3.reflect.Typed
    public Type getType() {
        return this.value;
    }
}
