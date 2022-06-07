package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/* compiled from: CollectorBase.java */
/* loaded from: classes.dex */
class b {
    protected final AnnotationIntrospector _intr;
    protected static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
    protected static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

    /* JADX INFO: Access modifiers changed from: protected */
    public b(AnnotationIntrospector annotationIntrospector) {
        this._intr = annotationIntrospector;
    }

    protected final AnnotationCollector collectAnnotations(Annotation[] annotationArr) {
        AnnotationCollector emptyCollector = AnnotationCollector.emptyCollector();
        for (Annotation annotation : annotationArr) {
            emptyCollector = emptyCollector.addOrOverride(annotation);
            if (this._intr.isAnnotationBundle(annotation)) {
                emptyCollector = collectFromBundle(emptyCollector, annotation);
            }
        }
        return emptyCollector;
    }

    protected final AnnotationCollector collectAnnotations(AnnotationCollector annotationCollector, Annotation[] annotationArr) {
        for (Annotation annotation : annotationArr) {
            annotationCollector = annotationCollector.addOrOverride(annotation);
            if (this._intr.isAnnotationBundle(annotation)) {
                annotationCollector = collectFromBundle(annotationCollector, annotation);
            }
        }
        return annotationCollector;
    }

    protected final AnnotationCollector collectFromBundle(AnnotationCollector annotationCollector, Annotation annotation) {
        Annotation[] findClassAnnotations = ClassUtil.findClassAnnotations(annotation.annotationType());
        for (Annotation annotation2 : findClassAnnotations) {
            if (!_ignorableAnnotation(annotation2)) {
                if (!this._intr.isAnnotationBundle(annotation2)) {
                    annotationCollector = annotationCollector.addOrOverride(annotation2);
                } else if (!annotationCollector.isPresent(annotation2)) {
                    annotationCollector = collectFromBundle(annotationCollector.addOrOverride(annotation2), annotation2);
                }
            }
        }
        return annotationCollector;
    }

    protected final AnnotationCollector collectDefaultAnnotations(AnnotationCollector annotationCollector, Annotation[] annotationArr) {
        for (Annotation annotation : annotationArr) {
            if (!annotationCollector.isPresent(annotation)) {
                annotationCollector = annotationCollector.addOrOverride(annotation);
                if (this._intr.isAnnotationBundle(annotation)) {
                    annotationCollector = collectDefaultFromBundle(annotationCollector, annotation);
                }
            }
        }
        return annotationCollector;
    }

    protected final AnnotationCollector collectDefaultFromBundle(AnnotationCollector annotationCollector, Annotation annotation) {
        Annotation[] findClassAnnotations = ClassUtil.findClassAnnotations(annotation.annotationType());
        for (Annotation annotation2 : findClassAnnotations) {
            if (!_ignorableAnnotation(annotation2) && !annotationCollector.isPresent(annotation2)) {
                annotationCollector = annotationCollector.addOrOverride(annotation2);
                if (this._intr.isAnnotationBundle(annotation2)) {
                    annotationCollector = collectFromBundle(annotationCollector, annotation2);
                }
            }
        }
        return annotationCollector;
    }

    protected static final boolean _ignorableAnnotation(Annotation annotation) {
        return (annotation instanceof Target) || (annotation instanceof Retention);
    }

    static AnnotationMap a() {
        return new AnnotationMap();
    }

    static AnnotationMap[] a(int i) {
        if (i == 0) {
            return NO_ANNOTATION_MAPS;
        }
        AnnotationMap[] annotationMapArr = new AnnotationMap[i];
        for (int i2 = 0; i2 < i; i2++) {
            annotationMapArr[i2] = a();
        }
        return annotationMapArr;
    }
}
