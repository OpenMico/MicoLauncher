package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public abstract class TypeParameter<T> extends b<T> {
    final TypeVariable<?> a;

    protected TypeParameter() {
        Type a = a();
        Preconditions.checkArgument(a instanceof TypeVariable, "%s should be a type variable.", a);
        this.a = (TypeVariable) a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof TypeParameter) {
            return this.a.equals(((TypeParameter) obj).a);
        }
        return false;
    }

    public String toString() {
        return this.a.toString();
    }
}
