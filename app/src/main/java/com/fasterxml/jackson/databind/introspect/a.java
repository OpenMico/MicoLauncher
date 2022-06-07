package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnnotatedCreatorCollector.java */
/* loaded from: classes.dex */
public final class a extends b {
    private final TypeResolutionContext a;
    private AnnotatedConstructor b;

    a(AnnotationIntrospector annotationIntrospector, TypeResolutionContext typeResolutionContext) {
        super(annotationIntrospector);
        this.a = typeResolutionContext;
    }

    public static AnnotatedClass.Creators a(AnnotationIntrospector annotationIntrospector, TypeResolutionContext typeResolutionContext, JavaType javaType, Class<?> cls) {
        return new a(annotationIntrospector, typeResolutionContext).a(javaType, cls);
    }

    AnnotatedClass.Creators a(JavaType javaType, Class<?> cls) {
        List<AnnotatedConstructor> b = b(javaType, cls);
        List<AnnotatedMethod> c = c(javaType, cls);
        if (this._intr != null) {
            if (this.b != null && this._intr.hasIgnoreMarker(this.b)) {
                this.b = null;
            }
            int size = b.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else if (this._intr.hasIgnoreMarker(b.get(size))) {
                    b.remove(size);
                }
            }
            int size2 = c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    break;
                } else if (this._intr.hasIgnoreMarker(c.get(size2))) {
                    c.remove(size2);
                }
            }
        }
        return new AnnotatedClass.Creators(this.b, b, c);
    }

    private List<AnnotatedConstructor> b(JavaType javaType, Class<?> cls) {
        ArrayList arrayList;
        ClassUtil.Ctor ctor;
        int i;
        List<AnnotatedConstructor> list;
        if (!javaType.isEnumType()) {
            ClassUtil.Ctor[] constructors = ClassUtil.getConstructors(javaType.getRawClass());
            ctor = null;
            arrayList = null;
            for (ClassUtil.Ctor ctor2 : constructors) {
                if (a(ctor2.getConstructor())) {
                    if (ctor2.getParamCount() == 0) {
                        ctor = ctor2;
                    } else {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(ctor2);
                    }
                }
            }
        } else {
            ctor = null;
            arrayList = null;
        }
        if (arrayList == null) {
            List<AnnotatedConstructor> emptyList = Collections.emptyList();
            if (ctor == null) {
                return emptyList;
            }
            list = emptyList;
            i = 0;
        } else {
            i = arrayList.size();
            list = new ArrayList<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                list.add(null);
            }
        }
        if (cls != null) {
            ClassUtil.Ctor[] constructors2 = ClassUtil.getConstructors(cls);
            MemberKey[] memberKeyArr = null;
            ClassUtil.Ctor ctor3 = ctor;
            for (ClassUtil.Ctor ctor4 : constructors2) {
                if (ctor4.getParamCount() == 0) {
                    if (ctor3 != null) {
                        this.b = a(ctor3, ctor4);
                        ctor3 = null;
                    }
                } else if (arrayList != null) {
                    if (memberKeyArr == null) {
                        memberKeyArr = new MemberKey[i];
                        for (int i3 = 0; i3 < i; i3++) {
                            memberKeyArr[i3] = new MemberKey(((ClassUtil.Ctor) arrayList.get(i3)).getConstructor());
                        }
                    }
                    MemberKey memberKey = new MemberKey(ctor4.getConstructor());
                    int i4 = 0;
                    while (true) {
                        if (i4 >= i) {
                            break;
                        } else if (memberKey.equals(memberKeyArr[i4])) {
                            list.set(i4, b((ClassUtil.Ctor) arrayList.get(i4), ctor4));
                            break;
                        } else {
                            i4++;
                        }
                    }
                }
            }
            ctor = ctor3;
        }
        if (ctor != null) {
            this.b = a(ctor, (ClassUtil.Ctor) null);
        }
        for (int i5 = 0; i5 < i; i5++) {
            if (list.get(i5) == null) {
                list.set(i5, b((ClassUtil.Ctor) arrayList.get(i5), (ClassUtil.Ctor) null));
            }
        }
        return list;
    }

    private List<AnnotatedMethod> c(JavaType javaType, Class<?> cls) {
        Method[] classMethods = ClassUtil.getClassMethods(javaType.getRawClass());
        ArrayList arrayList = null;
        for (Method method : classMethods) {
            if (Modifier.isStatic(method.getModifiers())) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(method);
            }
        }
        if (arrayList == null) {
            return Collections.emptyList();
        }
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(null);
        }
        if (cls != null) {
            Method[] declaredMethods = ClassUtil.getDeclaredMethods(cls);
            MemberKey[] memberKeyArr = null;
            for (Method method2 : declaredMethods) {
                if (Modifier.isStatic(method2.getModifiers())) {
                    if (memberKeyArr == null) {
                        memberKeyArr = new MemberKey[size];
                        for (int i2 = 0; i2 < size; i2++) {
                            memberKeyArr[i2] = new MemberKey((Method) arrayList.get(i2));
                        }
                    }
                    MemberKey memberKey = new MemberKey(method2);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            break;
                        } else if (memberKey.equals(memberKeyArr[i3])) {
                            arrayList2.set(i3, a((Method) arrayList.get(i3), method2));
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
            }
        }
        for (int i4 = 0; i4 < size; i4++) {
            if (((AnnotatedMethod) arrayList2.get(i4)) == null) {
                arrayList2.set(i4, a((Method) arrayList.get(i4), (Method) null));
            }
        }
        return arrayList2;
    }

    protected AnnotatedConstructor a(ClassUtil.Ctor ctor, ClassUtil.Ctor ctor2) {
        if (this._intr == null) {
            return new AnnotatedConstructor(this.a, ctor.getConstructor(), a(), NO_ANNOTATION_MAPS);
        }
        return new AnnotatedConstructor(this.a, ctor.getConstructor(), c(ctor, ctor2), a(ctor.getConstructor().getParameterAnnotations(), ctor2 == null ? null : ctor2.getConstructor().getParameterAnnotations()));
    }

    protected AnnotatedConstructor b(ClassUtil.Ctor ctor, ClassUtil.Ctor ctor2) {
        Annotation[][] annotationArr;
        Annotation[][] annotationArr2;
        int paramCount = ctor.getParamCount();
        if (this._intr == null) {
            return new AnnotatedConstructor(this.a, ctor.getConstructor(), a(), a(paramCount));
        }
        if (paramCount == 0) {
            return new AnnotatedConstructor(this.a, ctor.getConstructor(), c(ctor, ctor2), NO_ANNOTATION_MAPS);
        }
        Annotation[][] parameterAnnotations = ctor.getParameterAnnotations();
        AnnotationMap[] annotationMapArr = null;
        if (paramCount != parameterAnnotations.length) {
            Class<?> declaringClass = ctor.getDeclaringClass();
            if (declaringClass.isEnum() && paramCount == parameterAnnotations.length + 2) {
                annotationArr2 = new Annotation[parameterAnnotations.length + 2];
                System.arraycopy(parameterAnnotations, 0, annotationArr2, 2, parameterAnnotations.length);
                annotationMapArr = a(annotationArr2, (Annotation[][]) null);
            } else if (!declaringClass.isMemberClass() || paramCount != parameterAnnotations.length + 1) {
                annotationArr2 = parameterAnnotations;
            } else {
                annotationArr2 = new Annotation[parameterAnnotations.length + 1];
                System.arraycopy(parameterAnnotations, 0, annotationArr2, 1, parameterAnnotations.length);
                annotationArr2[0] = NO_ANNOTATIONS;
                annotationMapArr = a(annotationArr2, (Annotation[][]) null);
            }
            if (annotationMapArr == null) {
                throw new IllegalStateException(String.format("Internal error: constructor for %s has mismatch: %d parameters; %d sets of annotations", ctor.getDeclaringClass().getName(), Integer.valueOf(paramCount), Integer.valueOf(annotationArr2.length)));
            }
        } else {
            if (ctor2 == null) {
                annotationArr = null;
            } else {
                annotationArr = ctor2.getParameterAnnotations();
            }
            annotationMapArr = a(parameterAnnotations, annotationArr);
        }
        return new AnnotatedConstructor(this.a, ctor.getConstructor(), c(ctor, ctor2), annotationMapArr);
    }

    protected AnnotatedMethod a(Method method, Method method2) {
        int length = method.getParameterTypes().length;
        if (this._intr == null) {
            return new AnnotatedMethod(this.a, method, a(), a(length));
        }
        if (length == 0) {
            return new AnnotatedMethod(this.a, method, a((AnnotatedElement) method, (AnnotatedElement) method2), NO_ANNOTATION_MAPS);
        }
        return new AnnotatedMethod(this.a, method, a((AnnotatedElement) method, (AnnotatedElement) method2), a(method.getParameterAnnotations(), method2 == null ? null : method2.getParameterAnnotations()));
    }

    private AnnotationMap[] a(Annotation[][] annotationArr, Annotation[][] annotationArr2) {
        int length = annotationArr.length;
        AnnotationMap[] annotationMapArr = new AnnotationMap[length];
        for (int i = 0; i < length; i++) {
            AnnotationCollector collectAnnotations = collectAnnotations(AnnotationCollector.emptyCollector(), annotationArr[i]);
            if (annotationArr2 != null) {
                collectAnnotations = collectAnnotations(collectAnnotations, annotationArr2[i]);
            }
            annotationMapArr[i] = collectAnnotations.asAnnotationMap();
        }
        return annotationMapArr;
    }

    private AnnotationMap c(ClassUtil.Ctor ctor, ClassUtil.Ctor ctor2) {
        AnnotationCollector collectAnnotations = collectAnnotations(ctor.getConstructor().getDeclaredAnnotations());
        if (ctor2 != null) {
            collectAnnotations = collectAnnotations(collectAnnotations, ctor2.getConstructor().getDeclaredAnnotations());
        }
        return collectAnnotations.asAnnotationMap();
    }

    private final AnnotationMap a(AnnotatedElement annotatedElement, AnnotatedElement annotatedElement2) {
        AnnotationCollector collectAnnotations = collectAnnotations(annotatedElement.getDeclaredAnnotations());
        if (annotatedElement2 != null) {
            collectAnnotations = collectAnnotations(collectAnnotations, annotatedElement2.getDeclaredAnnotations());
        }
        return collectAnnotations.asAnnotationMap();
    }

    private static boolean a(Constructor<?> constructor) {
        return !constructor.isSynthetic();
    }
}
