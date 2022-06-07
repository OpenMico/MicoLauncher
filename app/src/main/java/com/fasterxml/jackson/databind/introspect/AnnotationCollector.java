package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AnnotationCollector {
    protected static final Annotations NO_ANNOTATIONS = new NoAnnotations();
    protected final Object _data;

    public abstract AnnotationCollector addOrOverride(Annotation annotation);

    public abstract AnnotationMap asAnnotationMap();

    public abstract Annotations asAnnotations();

    public abstract boolean isPresent(Annotation annotation);

    protected AnnotationCollector(Object obj) {
        this._data = obj;
    }

    public static Annotations emptyAnnotations() {
        return NO_ANNOTATIONS;
    }

    public static AnnotationCollector emptyCollector() {
        return a.a;
    }

    public static AnnotationCollector emptyCollector(Object obj) {
        return new a(obj);
    }

    public Object getData() {
        return this._data;
    }

    /* loaded from: classes.dex */
    static class a extends AnnotationCollector {
        public static final a a = new a(null);

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public boolean isPresent(Annotation annotation) {
            return false;
        }

        a(Object obj) {
            super(obj);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public Annotations asAnnotations() {
            return NO_ANNOTATIONS;
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationMap asAnnotationMap() {
            return new AnnotationMap();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationCollector addOrOverride(Annotation annotation) {
            return new c(this._data, annotation.annotationType(), annotation);
        }
    }

    /* loaded from: classes.dex */
    static class c extends AnnotationCollector {
        private Class<?> a;
        private Annotation b;

        public c(Object obj, Class<?> cls, Annotation annotation) {
            super(obj);
            this.a = cls;
            this.b = annotation;
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public Annotations asAnnotations() {
            return new OneAnnotation(this.a, this.b);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationMap asAnnotationMap() {
            return AnnotationMap.of(this.a, this.b);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public boolean isPresent(Annotation annotation) {
            return annotation.annotationType() == this.a;
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationCollector addOrOverride(Annotation annotation) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (this.a != annotationType) {
                return new b(this._data, this.a, this.b, annotationType, annotation);
            }
            this.b = annotation;
            return this;
        }
    }

    /* loaded from: classes.dex */
    static class b extends AnnotationCollector {
        protected final HashMap<Class<?>, Annotation> a = new HashMap<>();

        public b(Object obj, Class<?> cls, Annotation annotation, Class<?> cls2, Annotation annotation2) {
            super(obj);
            this.a.put(cls, annotation);
            this.a.put(cls2, annotation2);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public Annotations asAnnotations() {
            if (this.a.size() != 2) {
                return new AnnotationMap(this.a);
            }
            Iterator<Map.Entry<Class<?>, Annotation>> it = this.a.entrySet().iterator();
            Map.Entry<Class<?>, Annotation> next = it.next();
            Map.Entry<Class<?>, Annotation> next2 = it.next();
            return new TwoAnnotations(next.getKey(), next.getValue(), next2.getKey(), next2.getValue());
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationMap asAnnotationMap() {
            AnnotationMap annotationMap = new AnnotationMap();
            for (Annotation annotation : this.a.values()) {
                annotationMap.add(annotation);
            }
            return annotationMap;
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public boolean isPresent(Annotation annotation) {
            return this.a.containsKey(annotation.annotationType());
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotationCollector
        public AnnotationCollector addOrOverride(Annotation annotation) {
            this.a.put(annotation.annotationType(), annotation);
            return this;
        }
    }

    /* loaded from: classes.dex */
    public static class NoAnnotations implements Annotations, Serializable {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public <A extends Annotation> A get(Class<A> cls) {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean has(Class<?> cls) {
            return false;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
            return false;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public int size() {
            return 0;
        }

        NoAnnotations() {
        }
    }

    /* loaded from: classes.dex */
    public static class OneAnnotation implements Annotations, Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?> _type;
        private final Annotation _value;

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public int size() {
            return 1;
        }

        public OneAnnotation(Class<?> cls, Annotation annotation) {
            this._type = cls;
            this._value = annotation;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public <A extends Annotation> A get(Class<A> cls) {
            if (this._type == cls) {
                return (A) this._value;
            }
            return null;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean has(Class<?> cls) {
            return this._type == cls;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
            for (Class<? extends Annotation> cls : clsArr) {
                if (cls == this._type) {
                    return true;
                }
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class TwoAnnotations implements Annotations, Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?> _type1;
        private final Class<?> _type2;
        private final Annotation _value1;
        private final Annotation _value2;

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public int size() {
            return 2;
        }

        public TwoAnnotations(Class<?> cls, Annotation annotation, Class<?> cls2, Annotation annotation2) {
            this._type1 = cls;
            this._value1 = annotation;
            this._type2 = cls2;
            this._value2 = annotation2;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public <A extends Annotation> A get(Class<A> cls) {
            if (this._type1 == cls) {
                return (A) this._value1;
            }
            if (this._type2 == cls) {
                return (A) this._value2;
            }
            return null;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean has(Class<?> cls) {
            return this._type1 == cls || this._type2 == cls;
        }

        @Override // com.fasterxml.jackson.databind.util.Annotations
        public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
            for (Class<? extends Annotation> cls : clsArr) {
                if (cls == this._type1 || cls == this._type2) {
                    return true;
                }
            }
            return false;
        }
    }
}
