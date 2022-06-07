package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ClassStack {
    protected final Class<?> _current;
    protected final ClassStack _parent;
    private ArrayList<ResolvedRecursiveType> a;

    public ClassStack(Class<?> cls) {
        this(null, cls);
    }

    private ClassStack(ClassStack classStack, Class<?> cls) {
        this._parent = classStack;
        this._current = cls;
    }

    public ClassStack child(Class<?> cls) {
        return new ClassStack(this, cls);
    }

    public void addSelfReference(ResolvedRecursiveType resolvedRecursiveType) {
        if (this.a == null) {
            this.a = new ArrayList<>();
        }
        this.a.add(resolvedRecursiveType);
    }

    public void resolveSelfReferences(JavaType javaType) {
        ArrayList<ResolvedRecursiveType> arrayList = this.a;
        if (arrayList != null) {
            Iterator<ResolvedRecursiveType> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().setReference(javaType);
            }
        }
    }

    public ClassStack find(Class<?> cls) {
        if (this._current == cls) {
            return this;
        }
        for (ClassStack classStack = this._parent; classStack != null; classStack = classStack._parent) {
            if (classStack._current == cls) {
                return classStack;
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ClassStack (self-refs: ");
        ArrayList<ResolvedRecursiveType> arrayList = this.a;
        sb.append(arrayList == null ? "0" : String.valueOf(arrayList.size()));
        sb.append(')');
        for (ClassStack classStack = this; classStack != null; classStack = classStack._parent) {
            sb.append(' ');
            sb.append(classStack._current.getName());
        }
        sb.append(']');
        return sb.toString();
    }
}
