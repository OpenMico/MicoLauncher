package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class AnnotationMap implements Annotations {
    protected HashMap<Class<?>, Annotation> _annotations;

    public AnnotationMap() {
    }

    public static AnnotationMap of(Class<?> cls, Annotation annotation) {
        HashMap hashMap = new HashMap(4);
        hashMap.put(cls, annotation);
        return new AnnotationMap(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationMap(HashMap<Class<?>, Annotation> hashMap) {
        this._annotations = hashMap;
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public <A extends Annotation> A get(Class<A> cls) {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        if (hashMap == null) {
            return null;
        }
        return (A) hashMap.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public boolean has(Class<?> cls) {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        if (hashMap == null) {
            return false;
        }
        return hashMap.containsKey(cls);
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        if (this._annotations != null) {
            for (Class<? extends Annotation> cls : clsArr) {
                if (this._annotations.containsKey(cls)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Iterable<Annotation> annotations() {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        if (hashMap == null || hashMap.size() == 0) {
            return Collections.emptyList();
        }
        return this._annotations.values();
    }

    public static AnnotationMap merge(AnnotationMap annotationMap, AnnotationMap annotationMap2) {
        HashMap<Class<?>, Annotation> hashMap;
        HashMap<Class<?>, Annotation> hashMap2;
        if (annotationMap == null || (hashMap = annotationMap._annotations) == null || hashMap.isEmpty()) {
            return annotationMap2;
        }
        if (annotationMap2 == null || (hashMap2 = annotationMap2._annotations) == null || hashMap2.isEmpty()) {
            return annotationMap;
        }
        HashMap hashMap3 = new HashMap();
        for (Annotation annotation : annotationMap2._annotations.values()) {
            hashMap3.put(annotation.annotationType(), annotation);
        }
        for (Annotation annotation2 : annotationMap._annotations.values()) {
            hashMap3.put(annotation2.annotationType(), annotation2);
        }
        return new AnnotationMap(hashMap3);
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public int size() {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        if (hashMap == null) {
            return 0;
        }
        return hashMap.size();
    }

    public boolean addIfNotPresent(Annotation annotation) {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        if (hashMap != null && hashMap.containsKey(annotation.annotationType())) {
            return false;
        }
        _add(annotation);
        return true;
    }

    public boolean add(Annotation annotation) {
        return _add(annotation);
    }

    public String toString() {
        HashMap<Class<?>, Annotation> hashMap = this._annotations;
        return hashMap == null ? "[null]" : hashMap.toString();
    }

    protected final boolean _add(Annotation annotation) {
        if (this._annotations == null) {
            this._annotations = new HashMap<>();
        }
        Annotation put = this._annotations.put(annotation.annotationType(), annotation);
        return put == null || !put.equals(annotation);
    }
}
