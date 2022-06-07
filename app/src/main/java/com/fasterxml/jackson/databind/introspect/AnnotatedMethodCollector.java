package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotatedMethodCollector extends b {
    private final ClassIntrospector.MixInResolver a;

    AnnotatedMethodCollector(AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver) {
        super(annotationIntrospector);
        this.a = annotationIntrospector == null ? null : mixInResolver;
    }

    public static AnnotatedMethodMap collectMethods(AnnotationIntrospector annotationIntrospector, TypeResolutionContext typeResolutionContext, ClassIntrospector.MixInResolver mixInResolver, TypeFactory typeFactory, JavaType javaType, List<JavaType> list, Class<?> cls) {
        return new AnnotatedMethodCollector(annotationIntrospector, mixInResolver).a(typeFactory, typeResolutionContext, javaType, list, cls);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap a(com.fasterxml.jackson.databind.type.TypeFactory r5, com.fasterxml.jackson.databind.introspect.TypeResolutionContext r6, com.fasterxml.jackson.databind.JavaType r7, java.util.List<com.fasterxml.jackson.databind.JavaType> r8, java.lang.Class<?> r9) {
        /*
            r4 = this;
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            java.lang.Class r1 = r7.getRawClass()
            r4.a(r6, r1, r0, r9)
            java.util.Iterator r8 = r8.iterator()
        L_0x0010:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x003b
            java.lang.Object r9 = r8.next()
            com.fasterxml.jackson.databind.JavaType r9 = (com.fasterxml.jackson.databind.JavaType) r9
            com.fasterxml.jackson.databind.introspect.ClassIntrospector$MixInResolver r1 = r4.a
            if (r1 != 0) goto L_0x0022
            r1 = 0
            goto L_0x002a
        L_0x0022:
            java.lang.Class r2 = r9.getRawClass()
            java.lang.Class r1 = r1.findMixInClassFor(r2)
        L_0x002a:
            com.fasterxml.jackson.databind.introspect.TypeResolutionContext$Basic r2 = new com.fasterxml.jackson.databind.introspect.TypeResolutionContext$Basic
            com.fasterxml.jackson.databind.type.TypeBindings r3 = r9.getBindings()
            r2.<init>(r5, r3)
            java.lang.Class r9 = r9.getRawClass()
            r4.a(r2, r9, r0, r1)
            goto L_0x0010
        L_0x003b:
            com.fasterxml.jackson.databind.introspect.ClassIntrospector$MixInResolver r5 = r4.a
            r8 = 0
            if (r5 == 0) goto L_0x0051
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            java.lang.Class r5 = r5.findMixInClassFor(r9)
            if (r5 == 0) goto L_0x0051
            java.lang.Class r7 = r7.getRawClass()
            r4._addMethodMixIns(r6, r7, r0, r5)
            r5 = 1
            goto L_0x0052
        L_0x0051:
            r5 = r8
        L_0x0052:
            if (r5 == 0) goto L_0x00ae
            com.fasterxml.jackson.databind.AnnotationIntrospector r5 = r4._intr
            if (r5 == 0) goto L_0x00ae
            boolean r5 = r0.isEmpty()
            if (r5 != 0) goto L_0x00ae
            java.util.Set r5 = r0.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x0066:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00ae
            java.lang.Object r6 = r5.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r7 = r6.getKey()
            com.fasterxml.jackson.databind.introspect.MemberKey r7 = (com.fasterxml.jackson.databind.introspect.MemberKey) r7
            java.lang.String r9 = "hashCode"
            java.lang.String r1 = r7.getName()
            boolean r9 = r9.equals(r1)
            if (r9 == 0) goto L_0x0066
            int r9 = r7.argCount()
            if (r9 == 0) goto L_0x008b
            goto L_0x0066
        L_0x008b:
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            java.lang.String r7 = r7.getName()     // Catch: Exception -> 0x0066
            java.lang.Class[] r1 = new java.lang.Class[r8]     // Catch: Exception -> 0x0066
            java.lang.reflect.Method r7 = r9.getDeclaredMethod(r7, r1)     // Catch: Exception -> 0x0066
            if (r7 == 0) goto L_0x0066
            java.lang.Object r6 = r6.getValue()     // Catch: Exception -> 0x0066
            com.fasterxml.jackson.databind.introspect.AnnotatedMethodCollector$a r6 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethodCollector.a) r6     // Catch: Exception -> 0x0066
            com.fasterxml.jackson.databind.introspect.AnnotationCollector r9 = r6.c     // Catch: Exception -> 0x0066
            java.lang.annotation.Annotation[] r1 = r7.getDeclaredAnnotations()     // Catch: Exception -> 0x0066
            com.fasterxml.jackson.databind.introspect.AnnotationCollector r9 = r4.collectDefaultAnnotations(r9, r1)     // Catch: Exception -> 0x0066
            r6.c = r9     // Catch: Exception -> 0x0066
            r6.b = r7     // Catch: Exception -> 0x0066
            goto L_0x0066
        L_0x00ae:
            boolean r5 = r0.isEmpty()
            if (r5 == 0) goto L_0x00ba
            com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap r5 = new com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap
            r5.<init>()
            return r5
        L_0x00ba:
            java.util.LinkedHashMap r5 = new java.util.LinkedHashMap
            int r6 = r0.size()
            r5.<init>(r6)
            java.util.Set r6 = r0.entrySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x00cb:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00eb
            java.lang.Object r7 = r6.next()
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7
            java.lang.Object r8 = r7.getValue()
            com.fasterxml.jackson.databind.introspect.AnnotatedMethodCollector$a r8 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethodCollector.a) r8
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r8 = r8.a()
            if (r8 == 0) goto L_0x00cb
            java.lang.Object r7 = r7.getKey()
            r5.put(r7, r8)
            goto L_0x00cb
        L_0x00eb:
            com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap r6 = new com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.AnnotatedMethodCollector.a(com.fasterxml.jackson.databind.type.TypeFactory, com.fasterxml.jackson.databind.introspect.TypeResolutionContext, com.fasterxml.jackson.databind.JavaType, java.util.List, java.lang.Class):com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap");
    }

    private void a(TypeResolutionContext typeResolutionContext, Class<?> cls, Map<MemberKey, a> map, Class<?> cls2) {
        if (cls2 != null) {
            _addMethodMixIns(typeResolutionContext, cls, map, cls2);
        }
        if (cls != null) {
            Method[] classMethods = ClassUtil.getClassMethods(cls);
            for (Method method : classMethods) {
                if (a(method)) {
                    MemberKey memberKey = new MemberKey(method);
                    a aVar = map.get(memberKey);
                    if (aVar == null) {
                        map.put(memberKey, new a(typeResolutionContext, method, this._intr == null ? AnnotationCollector.emptyCollector() : collectAnnotations(method.getDeclaredAnnotations())));
                    } else {
                        if (this._intr != null) {
                            aVar.c = collectDefaultAnnotations(aVar.c, method.getDeclaredAnnotations());
                        }
                        Method method2 = aVar.b;
                        if (method2 == null) {
                            aVar.b = method;
                        } else if (Modifier.isAbstract(method2.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
                            aVar.b = method;
                            aVar.a = typeResolutionContext;
                        }
                    }
                }
            }
        }
    }

    protected void _addMethodMixIns(TypeResolutionContext typeResolutionContext, Class<?> cls, Map<MemberKey, a> map, Class<?> cls2) {
        if (this._intr != null) {
            for (Class<?> cls3 : ClassUtil.findRawSuperTypes(cls2, cls, true)) {
                Method[] declaredMethods = ClassUtil.getDeclaredMethods(cls3);
                for (Method method : declaredMethods) {
                    if (a(method)) {
                        MemberKey memberKey = new MemberKey(method);
                        a aVar = map.get(memberKey);
                        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                        if (aVar == null) {
                            map.put(memberKey, new a(typeResolutionContext, null, collectAnnotations(declaredAnnotations)));
                        } else {
                            aVar.c = collectDefaultAnnotations(aVar.c, declaredAnnotations);
                        }
                    }
                }
            }
        }
    }

    private boolean a(Method method) {
        return !Modifier.isStatic(method.getModifiers()) && !method.isSynthetic() && !method.isBridge() && method.getParameterTypes().length <= 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        public TypeResolutionContext a;
        public Method b;
        public AnnotationCollector c;

        public a(TypeResolutionContext typeResolutionContext, Method method, AnnotationCollector annotationCollector) {
            this.a = typeResolutionContext;
            this.b = method;
            this.c = annotationCollector;
        }

        public AnnotatedMethod a() {
            Method method = this.b;
            if (method == null) {
                return null;
            }
            return new AnnotatedMethod(this.a, method, this.c.asAnnotationMap(), null);
        }
    }
}
