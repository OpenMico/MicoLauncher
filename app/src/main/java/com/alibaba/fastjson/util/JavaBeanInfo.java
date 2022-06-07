package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;

    /* renamed from: kotlin  reason: collision with root package name */
    public boolean f194kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> cls, Class<?> cls2, Constructor<?> constructor, Constructor<?> constructor2, Method method, Method method2, JSONType jSONType, List<FieldInfo> list) {
        JSONField jSONField;
        this.clazz = cls;
        this.builderClass = cls2;
        this.defaultConstructor = constructor;
        this.creatorConstructor = constructor2;
        this.factoryMethod = method;
        this.parserFeatures = TypeUtils.getParserFeatures(cls);
        this.buildMethod = method2;
        this.jsonType = jSONType;
        if (jSONType != null) {
            String typeName = jSONType.typeName();
            String typeKey = jSONType.typeKey();
            this.typeKey = typeKey.length() <= 0 ? null : typeKey;
            if (typeName.length() != 0) {
                this.typeName = typeName;
            } else {
                this.typeName = cls.getName();
            }
            String[] orders = jSONType.orders();
            this.orders = orders.length == 0 ? null : orders;
        } else {
            this.typeName = cls.getName();
            this.typeKey = null;
            this.orders = null;
        }
        this.fields = new FieldInfo[list.size()];
        list.toArray(this.fields);
        FieldInfo[] fieldInfoArr = this.fields;
        FieldInfo[] fieldInfoArr2 = new FieldInfo[fieldInfoArr.length];
        boolean z = false;
        if (this.orders != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            FieldInfo[] fieldInfoArr3 = this.fields;
            for (FieldInfo fieldInfo : fieldInfoArr3) {
                linkedHashMap.put(fieldInfo.name, fieldInfo);
            }
            String[] strArr = this.orders;
            int i = 0;
            for (String str : strArr) {
                FieldInfo fieldInfo2 = (FieldInfo) linkedHashMap.get(str);
                if (fieldInfo2 != null) {
                    i++;
                    fieldInfoArr2[i] = fieldInfo2;
                    linkedHashMap.remove(str);
                }
            }
            for (FieldInfo fieldInfo3 : linkedHashMap.values()) {
                i++;
                fieldInfoArr2[i] = fieldInfo3;
            }
        } else {
            System.arraycopy(fieldInfoArr, 0, fieldInfoArr2, 0, fieldInfoArr.length);
            Arrays.sort(fieldInfoArr2);
        }
        this.sortedFields = Arrays.equals(this.fields, fieldInfoArr2) ? this.fields : fieldInfoArr2;
        if (constructor != null) {
            this.defaultConstructorParameterSize = constructor.getParameterTypes().length;
        } else if (method != null) {
            this.defaultConstructorParameterSize = method.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (constructor2 != null) {
            this.creatorConstructorParameterTypes = constructor2.getParameterTypes();
            this.f194kotlin = TypeUtils.isKotlin(cls);
            if (this.f194kotlin) {
                this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
                try {
                    this.kotlinDefaultConstructor = cls.getConstructor(new Class[0]);
                } catch (Throwable unused) {
                }
                Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor2);
                for (int i2 = 0; i2 < this.creatorConstructorParameters.length && i2 < parameterAnnotations.length; i2++) {
                    Annotation[] annotationArr = parameterAnnotations[i2];
                    int length = annotationArr.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            jSONField = null;
                            break;
                        }
                        Annotation annotation = annotationArr[i3];
                        if (annotation instanceof JSONField) {
                            jSONField = (JSONField) annotation;
                            break;
                        }
                        i3++;
                    }
                    if (jSONField != null) {
                        String name = jSONField.name();
                        if (name.length() > 0) {
                            this.creatorConstructorParameters[i2] = name;
                        }
                    }
                }
                return;
            }
            if (this.creatorConstructorParameterTypes.length == this.fields.length) {
                int i4 = 0;
                while (true) {
                    Type[] typeArr = this.creatorConstructorParameterTypes;
                    if (i4 >= typeArr.length) {
                        z = true;
                        break;
                    } else if (typeArr[i4] != this.fields[i4].fieldClass) {
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            if (!z) {
                this.creatorConstructorParameters = ASMUtils.lookupParameterNames(constructor2);
            }
        }
    }

    private static FieldInfo getField(List<FieldInfo> list, String str) {
        for (FieldInfo fieldInfo : list) {
            if (fieldInfo.name.equals(str)) {
                return fieldInfo;
            }
            Field field = fieldInfo.field;
            if (!(field == null || fieldInfo.getAnnotation() == null || !field.getName().equals(str))) {
                return fieldInfo;
            }
        }
        return null;
    }

    static boolean add(List<FieldInfo> list, FieldInfo fieldInfo) {
        for (int size = list.size() - 1; size >= 0; size--) {
            FieldInfo fieldInfo2 = list.get(size);
            if (fieldInfo2.name.equals(fieldInfo.name) && (!fieldInfo2.getOnly || fieldInfo.getOnly)) {
                if (fieldInfo2.fieldClass.isAssignableFrom(fieldInfo.fieldClass)) {
                    list.set(size, fieldInfo);
                    return true;
                } else if (fieldInfo2.compareTo(fieldInfo) >= 0) {
                    return false;
                } else {
                    list.set(size, fieldInfo);
                    return true;
                }
            }
        }
        list.add(fieldInfo);
        return true;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(cls, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    private static Map<TypeVariable, Type> buildGenericInfo(Class<?> cls) {
        Class<? super Object> superclass = cls.getSuperclass();
        HashMap hashMap = null;
        if (superclass == null) {
            return null;
        }
        Class<? super Object> cls2 = cls;
        for (Class<? super Object> cls3 = superclass; cls3 != null && cls3 != Object.class; cls3 = cls3.getSuperclass()) {
            if (cls2.getGenericSuperclass() instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) cls2.getGenericSuperclass()).getActualTypeArguments();
                TypeVariable<Class<? super Object>>[] typeParameters = cls3.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    if (hashMap.containsKey(actualTypeArguments[i])) {
                        hashMap.put(typeParameters[i], hashMap.get(actualTypeArguments[i]));
                    } else {
                        hashMap.put(typeParameters[i], actualTypeArguments[i]);
                    }
                }
            }
            cls2 = cls3;
        }
        return hashMap;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z, boolean z2) {
        return build(cls, type, propertyNamingStrategy, z, z2, false);
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z, boolean z2, boolean z3) {
        Constructor<?> constructor;
        boolean z4;
        PropertyNamingStrategy propertyNamingStrategy2;
        Constructor<?> constructor2;
        ArrayList arrayList;
        Method[] methodArr;
        PropertyNamingStrategy propertyNamingStrategy3;
        Field[] fieldArr;
        JSONType jSONType;
        Method[] methodArr2;
        Class<?> cls2;
        int i;
        int i2;
        int i3;
        Field[] fieldArr2;
        String str;
        JSONField jSONField;
        int i4;
        boolean z5;
        Class<?> cls3;
        Field[] fieldArr3;
        Field field;
        String str2;
        int i5;
        boolean z6;
        JSONField jSONField2;
        String substring;
        int i6;
        String str3;
        int i7;
        int i8;
        int i9;
        String str4;
        int i10;
        StringBuilder sb;
        int i11;
        String[] strArr;
        JSONField jSONField3;
        int i12;
        int i13;
        int i14;
        String[] lookupParameterNames;
        JSONField jSONField4;
        int i15;
        int i16;
        int i17;
        String str5;
        String str6;
        JSONField jSONField5;
        int i18;
        int i19;
        int i20;
        Field field2;
        String str7;
        PropertyNamingStrategy naming;
        Class<?> cls4 = cls;
        boolean z7 = z3;
        JSONType jSONType2 = (JSONType) TypeUtils.getAnnotation(cls4, JSONType.class);
        PropertyNamingStrategy propertyNamingStrategy4 = (jSONType2 == null || (naming = jSONType2.naming()) == null || naming == PropertyNamingStrategy.CamelCase) ? propertyNamingStrategy : naming;
        Class<?> builderClass = getBuilderClass(cls4, jSONType2);
        Field[] declaredFields = cls.getDeclaredFields();
        Method[] methods = cls.getMethods();
        Map<TypeVariable, Type> buildGenericInfo = buildGenericInfo(cls);
        boolean isKotlin = TypeUtils.isKotlin(cls);
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        if (isKotlin && declaredConstructors.length != 1) {
            constructor = null;
        } else if (builderClass == null) {
            constructor = getDefaultConstructor(cls4, declaredConstructors);
        } else {
            constructor = getDefaultConstructor(builderClass, builderClass.getDeclaredConstructors());
        }
        Method method = null;
        Method method2 = null;
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            for (Class<?> cls5 = cls4; cls5 != null; cls5 = cls5.getSuperclass()) {
                computeFields(cls4, type, propertyNamingStrategy4, arrayList2, cls5.getDeclaredFields());
            }
            if (constructor != null) {
                TypeUtils.setAccessible(constructor);
            }
            return new JavaBeanInfo(cls, builderClass, constructor, null, null, null, jSONType2, arrayList2);
        }
        boolean z8 = cls.isInterface() || Modifier.isAbstract(cls.getModifiers());
        if (!(constructor == null && builderClass == null) && !z8) {
            arrayList = arrayList2;
            z4 = true;
            propertyNamingStrategy2 = propertyNamingStrategy4;
            methodArr = methods;
            constructor2 = null;
        } else {
            constructor2 = getCreatorConstructor(declaredConstructors);
            if (constructor2 == null || z8) {
                arrayList = arrayList2;
                propertyNamingStrategy2 = propertyNamingStrategy4;
                methodArr = methods;
                method2 = getFactoryMethod(cls4, methodArr, z7);
                if (method2 != null) {
                    TypeUtils.setAccessible(method2);
                    Class<?>[] parameterTypes = method2.getParameterTypes();
                    if (parameterTypes.length > 0) {
                        Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(method2);
                        String[] strArr2 = null;
                        int i21 = 0;
                        while (i21 < parameterTypes.length) {
                            Annotation[] annotationArr = parameterAnnotations[i21];
                            int length = annotationArr.length;
                            int i22 = 0;
                            while (true) {
                                if (i22 >= length) {
                                    jSONField4 = null;
                                    break;
                                }
                                Annotation annotation = annotationArr[i22];
                                if (annotation instanceof JSONField) {
                                    jSONField4 = (JSONField) annotation;
                                    break;
                                }
                                i22++;
                            }
                            if (jSONField4 != null || (z7 && TypeUtils.isJacksonCreator(method2))) {
                                if (jSONField4 != null) {
                                    str5 = jSONField4.name();
                                    int ordinal = jSONField4.ordinal();
                                    i16 = SerializerFeature.of(jSONField4.serialzeFeatures());
                                    i15 = Feature.of(jSONField4.parseFeatures());
                                    i17 = ordinal;
                                } else {
                                    str5 = null;
                                    i17 = 0;
                                    i16 = 0;
                                    i15 = 0;
                                }
                                if (str5 == null || str5.length() == 0) {
                                    if (strArr2 == null) {
                                        strArr2 = ASMUtils.lookupParameterNames(method2);
                                    }
                                    str6 = strArr2[i21];
                                    strArr2 = strArr2;
                                } else {
                                    strArr2 = strArr2;
                                    str6 = str5;
                                }
                                add(arrayList, new FieldInfo(str6, cls, parameterTypes[i21], method2.getGenericParameterTypes()[i21], TypeUtils.getField(cls4, str6, declaredFields), i17, i16, i15));
                                i21++;
                                parameterTypes = parameterTypes;
                                z7 = z3;
                            } else {
                                throw new JSONException("illegal json creator");
                            }
                        }
                        return new JavaBeanInfo(cls, builderClass, null, null, method2, null, jSONType2, arrayList);
                    }
                } else if (!z8) {
                    String name = cls.getName();
                    if (!isKotlin || declaredConstructors.length <= 0) {
                        int length2 = declaredConstructors.length;
                        String[] strArr3 = null;
                        int i23 = 0;
                        while (true) {
                            if (i23 >= length2) {
                                z4 = true;
                                i11 = 0;
                                strArr = strArr3;
                                break;
                            }
                            Constructor<?> constructor3 = declaredConstructors[i23];
                            Class<?>[] parameterTypes2 = constructor3.getParameterTypes();
                            if (name.equals("org.springframework.security.web.authentication.WebAuthenticationDetails")) {
                                if (parameterTypes2.length == 2 && parameterTypes2[0] == String.class && parameterTypes2[1] == String.class) {
                                    constructor3.setAccessible(true);
                                    strArr = ASMUtils.lookupParameterNames(constructor3);
                                    constructor2 = constructor3;
                                    i11 = 0;
                                    z4 = true;
                                    break;
                                }
                                i23++;
                            } else if (name.equals("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken")) {
                                if (parameterTypes2.length == 3 && parameterTypes2[0] == Object.class) {
                                    if (parameterTypes2[1] == Object.class && parameterTypes2[2] == Collection.class) {
                                        constructor3.setAccessible(true);
                                        strArr = new String[]{"principal", "credentials", "authorities"};
                                        constructor2 = constructor3;
                                        z4 = true;
                                        i11 = 0;
                                        break;
                                    }
                                }
                                i23++;
                            } else {
                                if (name.equals("org.springframework.security.core.authority.SimpleGrantedAuthority")) {
                                    z4 = true;
                                    if (parameterTypes2.length == 1) {
                                        i11 = 0;
                                        if (parameterTypes2[0] == String.class) {
                                            strArr = new String[]{"authority"};
                                            constructor2 = constructor3;
                                            break;
                                        }
                                    }
                                } else {
                                    boolean z9 = false;
                                    if ((constructor3.getModifiers() & 1) != 0) {
                                        z9 = true;
                                    }
                                    if (z9 && (lookupParameterNames = ASMUtils.lookupParameterNames(constructor3)) != null && lookupParameterNames.length != 0 && (constructor2 == null || strArr3 == null || lookupParameterNames.length > strArr3.length)) {
                                        constructor2 = constructor3;
                                        strArr3 = lookupParameterNames;
                                    }
                                }
                                i23++;
                            }
                        }
                    } else {
                        String[] koltinConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
                        Constructor<?> koltinConstructor = TypeUtils.getKoltinConstructor(declaredConstructors, koltinConstructorParameters);
                        TypeUtils.setAccessible(koltinConstructor);
                        constructor2 = koltinConstructor;
                        strArr = koltinConstructorParameters;
                        z4 = true;
                        i11 = 0;
                    }
                    Class<?>[] parameterTypes3 = strArr != null ? constructor2.getParameterTypes() : null;
                    if (strArr == null || parameterTypes3.length != strArr.length) {
                        throw new JSONException("default constructor not found. " + cls4);
                    }
                    Annotation[][] parameterAnnotations2 = TypeUtils.getParameterAnnotations(constructor2);
                    int i24 = i11;
                    while (i24 < parameterTypes3.length) {
                        Annotation[] annotationArr2 = parameterAnnotations2[i24];
                        String str8 = strArr[i24];
                        int length3 = annotationArr2.length;
                        int i25 = i11;
                        while (true) {
                            if (i25 >= length3) {
                                jSONField3 = null;
                                break;
                            }
                            Annotation annotation2 = annotationArr2[i25];
                            if (annotation2 instanceof JSONField) {
                                jSONField3 = (JSONField) annotation2;
                                break;
                            }
                            i25++;
                        }
                        Class<?> cls6 = parameterTypes3[i24];
                        Type type2 = constructor2.getGenericParameterTypes()[i24];
                        Field field3 = TypeUtils.getField(cls4, str8, declaredFields);
                        if (field3 != null && jSONField3 == null) {
                            jSONField3 = (JSONField) TypeUtils.getAnnotation(field3, JSONField.class);
                        }
                        if (jSONField3 != null) {
                            String name2 = jSONField3.name();
                            if (name2.length() != 0) {
                                str8 = name2;
                            }
                            int ordinal2 = jSONField3.ordinal();
                            i13 = SerializerFeature.of(jSONField3.serialzeFeatures());
                            i12 = Feature.of(jSONField3.parseFeatures());
                            i14 = ordinal2;
                        } else if (!"org.springframework.security.core.userdetails.User".equals(name) || !"password".equals(str8)) {
                            i14 = i11;
                            i13 = i14;
                            i12 = i13;
                        } else {
                            i12 = Feature.InitStringFieldAsEmpty.mask;
                            i14 = i11;
                            i13 = i14;
                        }
                        z4 = true;
                        add(arrayList, new FieldInfo(str8, cls, cls6, type2, field3, i14, i13, i12));
                        i24++;
                        name = name;
                        strArr = strArr;
                        parameterTypes3 = parameterTypes3;
                        i11 = 0;
                    }
                    if (!isKotlin && !cls.getName().equals("javax.servlet.http.Cookie")) {
                        return new JavaBeanInfo(cls, builderClass, null, constructor2, null, null, jSONType2, arrayList);
                    }
                }
                z4 = true;
            } else {
                TypeUtils.setAccessible(constructor2);
                Class<?>[] parameterTypes4 = constructor2.getParameterTypes();
                if (parameterTypes4.length > 0) {
                    Annotation[][] parameterAnnotations3 = TypeUtils.getParameterAnnotations(constructor2);
                    String[] strArr4 = null;
                    int i26 = 0;
                    while (i26 < parameterTypes4.length && i26 < parameterAnnotations3.length) {
                        Annotation[] annotationArr3 = parameterAnnotations3[i26];
                        int length4 = annotationArr3.length;
                        int i27 = 0;
                        while (true) {
                            if (i27 >= length4) {
                                parameterAnnotations3 = parameterAnnotations3;
                                jSONField5 = null;
                                break;
                            }
                            Annotation annotation3 = annotationArr3[i27];
                            parameterAnnotations3 = parameterAnnotations3;
                            if (annotation3 instanceof JSONField) {
                                jSONField5 = (JSONField) annotation3;
                                break;
                            }
                            i27++;
                            parameterAnnotations3 = parameterAnnotations3;
                        }
                        Class<?> cls7 = parameterTypes4[i26];
                        Type type3 = constructor2.getGenericParameterTypes()[i26];
                        if (jSONField5 != null) {
                            field2 = TypeUtils.getField(cls4, jSONField5.name(), declaredFields);
                            int ordinal3 = jSONField5.ordinal();
                            i20 = SerializerFeature.of(jSONField5.serialzeFeatures());
                            i18 = Feature.of(jSONField5.parseFeatures());
                            str7 = jSONField5.name();
                            i19 = ordinal3;
                        } else {
                            str7 = null;
                            field2 = null;
                            i20 = 0;
                            i19 = 0;
                            i18 = 0;
                        }
                        if (str7 == null || str7.length() == 0) {
                            if (strArr4 == null) {
                                strArr4 = ASMUtils.lookupParameterNames(constructor2);
                            }
                            str7 = strArr4[i26];
                        }
                        if (field2 == null) {
                            if (strArr4 != null) {
                                field2 = field2;
                            } else if (isKotlin) {
                                strArr4 = TypeUtils.getKoltinConstructorParameters(cls);
                                field2 = field2;
                            } else {
                                strArr4 = ASMUtils.lookupParameterNames(constructor2);
                                field2 = field2;
                            }
                            if (strArr4.length > i26) {
                                field2 = TypeUtils.getField(cls4, strArr4[i26], declaredFields);
                            }
                        }
                        add(arrayList2, new FieldInfo(str7, cls, cls7, type3, field2, i19, i20, i18));
                        i26++;
                        methods = methods;
                        arrayList2 = arrayList2;
                        propertyNamingStrategy4 = propertyNamingStrategy4;
                    }
                    arrayList = arrayList2;
                    propertyNamingStrategy2 = propertyNamingStrategy4;
                    methodArr = methods;
                } else {
                    arrayList = arrayList2;
                    propertyNamingStrategy2 = propertyNamingStrategy4;
                    methodArr = methods;
                }
                z4 = true;
            }
        }
        if (constructor != null) {
            TypeUtils.setAccessible(constructor);
        }
        if (builderClass != null) {
            JSONPOJOBuilder jSONPOJOBuilder = (JSONPOJOBuilder) TypeUtils.getAnnotation(builderClass, JSONPOJOBuilder.class);
            String withPrefix = jSONPOJOBuilder != null ? jSONPOJOBuilder.withPrefix() : null;
            String str9 = withPrefix == null ? JsonPOJOBuilder.DEFAULT_WITH_PREFIX : withPrefix;
            Method[] methods2 = builderClass.getMethods();
            int length5 = methods2.length;
            int i28 = 0;
            while (i28 < length5) {
                Method method3 = methods2[i28];
                if (Modifier.isStatic(method3.getModifiers())) {
                    i6 = i28;
                    length5 = length5;
                    methods2 = methods2;
                    str9 = str9;
                    declaredFields = declaredFields;
                    builderClass = builderClass;
                    jSONType2 = jSONType2;
                    propertyNamingStrategy2 = propertyNamingStrategy2;
                    methodArr = methodArr;
                } else if (!method3.getReturnType().equals(builderClass)) {
                    i6 = i28;
                    length5 = length5;
                    methods2 = methods2;
                    str9 = str9;
                    declaredFields = declaredFields;
                    builderClass = builderClass;
                    jSONType2 = jSONType2;
                    propertyNamingStrategy2 = propertyNamingStrategy2;
                    methodArr = methodArr;
                } else {
                    JSONField jSONField6 = (JSONField) TypeUtils.getAnnotation(method3, JSONField.class);
                    JSONField superMethodAnnotation = jSONField6 == null ? TypeUtils.getSuperMethodAnnotation(cls4, method3) : jSONField6;
                    if (superMethodAnnotation == null) {
                        i6 = i28;
                        length5 = length5;
                        methods2 = methods2;
                        str3 = str9;
                        declaredFields = declaredFields;
                        builderClass = builderClass;
                        jSONType2 = jSONType2;
                        propertyNamingStrategy2 = propertyNamingStrategy2;
                        methodArr = methodArr;
                        i9 = 0;
                        i8 = 0;
                        i7 = 0;
                    } else if (!superMethodAnnotation.deserialize()) {
                        i6 = i28;
                        length5 = length5;
                        methods2 = methods2;
                        str9 = str9;
                        declaredFields = declaredFields;
                        builderClass = builderClass;
                        jSONType2 = jSONType2;
                        propertyNamingStrategy2 = propertyNamingStrategy2;
                        methodArr = methodArr;
                    } else {
                        int ordinal4 = superMethodAnnotation.ordinal();
                        int of = SerializerFeature.of(superMethodAnnotation.serialzeFeatures());
                        int of2 = Feature.of(superMethodAnnotation.parseFeatures());
                        if (superMethodAnnotation.name().length() != 0) {
                            i6 = i28;
                            length5 = length5;
                            methods2 = methods2;
                            declaredFields = declaredFields;
                            builderClass = builderClass;
                            jSONType2 = jSONType2;
                            propertyNamingStrategy2 = propertyNamingStrategy2;
                            methodArr = methodArr;
                            add(arrayList, new FieldInfo(superMethodAnnotation.name(), method3, null, cls, type, ordinal4, of, of2, superMethodAnnotation, null, null, buildGenericInfo));
                            str9 = str9;
                        } else {
                            i6 = i28;
                            length5 = length5;
                            methods2 = methods2;
                            str3 = str9;
                            declaredFields = declaredFields;
                            builderClass = builderClass;
                            jSONType2 = jSONType2;
                            propertyNamingStrategy2 = propertyNamingStrategy2;
                            methodArr = methodArr;
                            i9 = ordinal4;
                            i8 = of;
                            i7 = of2;
                        }
                    }
                    String name3 = method3.getName();
                    if (name3.startsWith(BluetoothConstants.SET) && name3.length() > 3) {
                        sb = new StringBuilder(name3.substring(3));
                        str4 = str3;
                        i10 = 0;
                    } else if (str3.length() == 0) {
                        sb = new StringBuilder(name3);
                        str4 = str3;
                        i10 = 0;
                    } else {
                        str4 = str3;
                        if (!name3.startsWith(str4)) {
                            str9 = str4;
                        } else if (name3.length() <= str4.length()) {
                            str9 = str4;
                        } else {
                            sb = new StringBuilder(name3.substring(str4.length()));
                            i10 = 0;
                        }
                    }
                    char charAt = sb.charAt(i10);
                    if (str4.length() == 0 || Character.isUpperCase(charAt)) {
                        sb.setCharAt(i10, Character.toLowerCase(charAt));
                        str9 = str4;
                        add(arrayList, new FieldInfo(sb.toString(), method3, null, cls, type, i9, i8, i7, superMethodAnnotation, null, null, buildGenericInfo));
                    } else {
                        str9 = str4;
                    }
                }
                i28 = i6 + 1;
                cls4 = cls;
                z4 = true;
            }
            fieldArr = declaredFields;
            cls2 = builderClass;
            jSONType = jSONType2;
            propertyNamingStrategy3 = propertyNamingStrategy2;
            methodArr2 = methodArr;
            if (cls2 != null) {
                JSONPOJOBuilder jSONPOJOBuilder2 = (JSONPOJOBuilder) TypeUtils.getAnnotation(cls2, JSONPOJOBuilder.class);
                String buildMethod = jSONPOJOBuilder2 != null ? jSONPOJOBuilder2.buildMethod() : null;
                if (buildMethod == null || buildMethod.length() == 0) {
                    buildMethod = "build";
                    i = 0;
                } else {
                    i = 0;
                }
                try {
                    method = cls2.getMethod(buildMethod, new Class[i]);
                } catch (NoSuchMethodException | SecurityException unused) {
                }
                if (method == null) {
                    try {
                        method = cls2.getMethod("create", new Class[i]);
                    } catch (NoSuchMethodException | SecurityException unused2) {
                    }
                }
                if (method != null) {
                    TypeUtils.setAccessible(method);
                } else {
                    throw new JSONException("buildMethod not found.");
                }
            } else {
                i = 0;
            }
        } else {
            fieldArr = declaredFields;
            cls2 = builderClass;
            jSONType = jSONType2;
            propertyNamingStrategy3 = propertyNamingStrategy2;
            methodArr2 = methodArr;
            i = 0;
        }
        int length6 = methodArr2.length;
        int i29 = i;
        while (true) {
            i2 = 4;
            if (i29 >= length6) {
                break;
            }
            Method method4 = methodArr2[i29];
            int i30 = 0;
            int i31 = 0;
            int i32 = 0;
            String name4 = method4.getName();
            if (Modifier.isStatic(method4.getModifiers())) {
                i4 = i29;
                length6 = length6;
                i = i;
                cls2 = cls2;
                methodArr2 = methodArr2;
                fieldArr = fieldArr;
                propertyNamingStrategy3 = propertyNamingStrategy3;
            } else {
                Class<?> returnType = method4.getReturnType();
                if (!returnType.equals(Void.TYPE) && !returnType.equals(method4.getDeclaringClass())) {
                    i4 = i29;
                    length6 = length6;
                    i = i;
                    cls2 = cls2;
                    methodArr2 = methodArr2;
                    fieldArr = fieldArr;
                    propertyNamingStrategy3 = propertyNamingStrategy3;
                } else if (method4.getDeclaringClass() == Object.class) {
                    i4 = i29;
                    length6 = length6;
                    i = i;
                    cls2 = cls2;
                    methodArr2 = methodArr2;
                    fieldArr = fieldArr;
                    propertyNamingStrategy3 = propertyNamingStrategy3;
                } else {
                    Class<?>[] parameterTypes5 = method4.getParameterTypes();
                    if (parameterTypes5.length == 0) {
                        i4 = i29;
                        length6 = length6;
                        i = i;
                        cls2 = cls2;
                        methodArr2 = methodArr2;
                        fieldArr = fieldArr;
                        propertyNamingStrategy3 = propertyNamingStrategy3;
                    } else if (parameterTypes5.length > 2) {
                        i4 = i29;
                        length6 = length6;
                        i = i;
                        cls2 = cls2;
                        methodArr2 = methodArr2;
                        fieldArr = fieldArr;
                        propertyNamingStrategy3 = propertyNamingStrategy3;
                    } else {
                        JSONField jSONField7 = (JSONField) TypeUtils.getAnnotation(method4, JSONField.class);
                        if (jSONField7 != null && parameterTypes5.length == 2 && parameterTypes5[i] == String.class && parameterTypes5[1] == Object.class) {
                            i4 = i29;
                            length6 = length6;
                            methodArr2 = methodArr2;
                            add(arrayList, new FieldInfo("", method4, null, cls, type, 0, 0, 0, jSONField7, null, null, buildGenericInfo));
                            cls2 = cls2;
                            i = i;
                            fieldArr = fieldArr;
                            propertyNamingStrategy3 = propertyNamingStrategy3;
                        } else {
                            i4 = i29;
                            length6 = length6;
                            methodArr2 = methodArr2;
                            if (parameterTypes5.length != 1) {
                                cls2 = cls2;
                                i = i;
                                fieldArr = fieldArr;
                                propertyNamingStrategy3 = propertyNamingStrategy3;
                            } else {
                                if (jSONField7 == null) {
                                    z5 = true;
                                    jSONField7 = TypeUtils.getSuperMethodAnnotation(cls, method4);
                                } else {
                                    z5 = true;
                                }
                                if (jSONField7 != null || name4.length() >= 4) {
                                    if (jSONField7 != null) {
                                        if (!jSONField7.deserialize()) {
                                            cls2 = cls2;
                                            i = i;
                                            fieldArr = fieldArr;
                                            propertyNamingStrategy3 = propertyNamingStrategy3;
                                        } else {
                                            i30 = jSONField7.ordinal();
                                            i31 = SerializerFeature.of(jSONField7.serialzeFeatures());
                                            i32 = Feature.of(jSONField7.parseFeatures());
                                            if (jSONField7.name().length() != 0) {
                                                add(arrayList, new FieldInfo(jSONField7.name(), method4, null, cls, type, i30, i31, i32, jSONField7, null, null, buildGenericInfo));
                                                cls2 = cls2;
                                                fieldArr = fieldArr;
                                                propertyNamingStrategy3 = propertyNamingStrategy3;
                                                i = 0;
                                            }
                                        }
                                    }
                                    if (jSONField7 == null && !name4.startsWith(BluetoothConstants.SET)) {
                                        cls2 = cls2;
                                        fieldArr = fieldArr;
                                        propertyNamingStrategy3 = propertyNamingStrategy3;
                                        i = 0;
                                    } else if (cls2 != null) {
                                        cls2 = cls2;
                                        fieldArr = fieldArr;
                                        propertyNamingStrategy3 = propertyNamingStrategy3;
                                        i = 0;
                                    } else {
                                        char charAt2 = name4.charAt(3);
                                        if (Character.isUpperCase(charAt2) || charAt2 > 512) {
                                            fieldArr3 = fieldArr;
                                            cls3 = cls;
                                            if (TypeUtils.compatibleWithJavaBean) {
                                                str2 = TypeUtils.decapitalize(name4.substring(3));
                                                field = null;
                                            } else {
                                                str2 = Character.toLowerCase(name4.charAt(3)) + name4.substring(4);
                                                field = null;
                                            }
                                        } else if (charAt2 == '_') {
                                            str2 = name4.substring(4);
                                            fieldArr3 = fieldArr;
                                            cls3 = cls;
                                            field = TypeUtils.getField(cls3, str2, fieldArr3);
                                            if (field == null && (field = TypeUtils.getField(cls3, (substring = name4.substring(3)), fieldArr3)) != null) {
                                                str2 = substring;
                                            }
                                        } else {
                                            fieldArr3 = fieldArr;
                                            cls3 = cls;
                                            if (charAt2 == 'f') {
                                                str2 = name4.substring(3);
                                                field = null;
                                            } else if (name4.length() < 5 || !Character.isUpperCase(name4.charAt(4))) {
                                                str2 = name4.substring(3);
                                                Field field4 = TypeUtils.getField(cls3, str2, fieldArr3);
                                                if (field4 == null) {
                                                    fieldArr = fieldArr3;
                                                    cls2 = cls2;
                                                    propertyNamingStrategy3 = propertyNamingStrategy3;
                                                    i = 0;
                                                } else {
                                                    field = field4;
                                                }
                                            } else {
                                                str2 = TypeUtils.decapitalize(name4.substring(3));
                                                field = null;
                                            }
                                        }
                                        if (field == null) {
                                            field = TypeUtils.getField(cls3, str2, fieldArr3);
                                        }
                                        if (field == null) {
                                            i5 = 0;
                                            if (parameterTypes5[0] == Boolean.TYPE) {
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(ai.ae);
                                                sb2.append(Character.toUpperCase(str2.charAt(0)));
                                                z6 = true;
                                                sb2.append(str2.substring(1));
                                                field = TypeUtils.getField(cls3, sb2.toString(), fieldArr3);
                                            } else {
                                                z6 = true;
                                            }
                                        } else {
                                            z6 = true;
                                            i5 = 0;
                                        }
                                        if (field != null) {
                                            JSONField jSONField8 = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                                            if (jSONField8 == null) {
                                                i = i5;
                                                fieldArr = fieldArr3;
                                                cls2 = cls2;
                                                jSONField2 = jSONField8;
                                                propertyNamingStrategy3 = propertyNamingStrategy3;
                                            } else if (!jSONField8.deserialize()) {
                                                i = i5;
                                                fieldArr = fieldArr3;
                                                cls2 = cls2;
                                                propertyNamingStrategy3 = propertyNamingStrategy3;
                                            } else {
                                                i30 = jSONField8.ordinal();
                                                i31 = SerializerFeature.of(jSONField8.serialzeFeatures());
                                                i32 = Feature.of(jSONField8.parseFeatures());
                                                if (jSONField8.name().length() != 0) {
                                                    i = i5;
                                                    fieldArr = fieldArr3;
                                                    cls2 = cls2;
                                                    add(arrayList, new FieldInfo(jSONField8.name(), method4, field, cls, type, i30, i31, i32, jSONField7, jSONField8, null, buildGenericInfo));
                                                    propertyNamingStrategy3 = propertyNamingStrategy3;
                                                } else {
                                                    i = i5;
                                                    fieldArr = fieldArr3;
                                                    cls2 = cls2;
                                                    jSONField2 = jSONField8;
                                                    propertyNamingStrategy3 = propertyNamingStrategy3;
                                                }
                                            }
                                        } else {
                                            i = i5;
                                            fieldArr = fieldArr3;
                                            cls2 = cls2;
                                            jSONField2 = null;
                                            propertyNamingStrategy3 = propertyNamingStrategy3;
                                        }
                                        add(arrayList, new FieldInfo(propertyNamingStrategy3 != null ? propertyNamingStrategy3.translate(str2) : str2, method4, field, cls, type, i30, i31, i32, jSONField7, jSONField2, null, buildGenericInfo));
                                    }
                                } else {
                                    cls2 = cls2;
                                    i = i;
                                    fieldArr = fieldArr;
                                    propertyNamingStrategy3 = propertyNamingStrategy3;
                                }
                            }
                        }
                    }
                }
            }
            i29 = i4 + 1;
        }
        Field[] fieldArr4 = fieldArr;
        boolean z10 = true;
        int i33 = 3;
        Type type4 = type;
        Class<?> cls8 = cls;
        computeFields(cls8, type4, propertyNamingStrategy3, arrayList, cls.getFields());
        Method[] methods3 = cls.getMethods();
        int length7 = methods3.length;
        int i34 = i;
        while (i34 < length7) {
            Method method5 = methods3[i34];
            String name5 = method5.getName();
            if (name5.length() < i2) {
                i3 = i34;
                length7 = length7;
                i2 = i2;
                methods3 = methods3;
                i33 = i33;
                type4 = type4;
                fieldArr4 = fieldArr4;
            } else if (Modifier.isStatic(method5.getModifiers())) {
                i3 = i34;
                length7 = length7;
                i2 = i2;
                methods3 = methods3;
                i33 = i33;
                type4 = type4;
                fieldArr4 = fieldArr4;
            } else if (cls2 != null || !name5.startsWith(BluetoothConstants.GET) || !Character.isUpperCase(name5.charAt(i33))) {
                i3 = i34;
                length7 = length7;
                i2 = i2;
                methods3 = methods3;
                i33 = i33;
                type4 = type4;
                fieldArr4 = fieldArr4;
            } else if (method5.getParameterTypes().length != 0) {
                i3 = i34;
                length7 = length7;
                i2 = i2;
                methods3 = methods3;
                i33 = i33;
                type4 = type4;
                fieldArr4 = fieldArr4;
            } else if (Collection.class.isAssignableFrom(method5.getReturnType()) || Map.class.isAssignableFrom(method5.getReturnType()) || AtomicBoolean.class == method5.getReturnType() || AtomicInteger.class == method5.getReturnType() || AtomicLong.class == method5.getReturnType()) {
                JSONField jSONField9 = (JSONField) TypeUtils.getAnnotation(method5, JSONField.class);
                if (jSONField9 == null || !jSONField9.deserialize()) {
                    if (jSONField9 == null || jSONField9.name().length() <= 0) {
                        str = Character.toLowerCase(name5.charAt(i33)) + name5.substring(i2);
                        fieldArr2 = fieldArr4;
                        Field field5 = TypeUtils.getField(cls8, str, fieldArr2);
                        if (!(field5 == null || (jSONField = (JSONField) TypeUtils.getAnnotation(field5, JSONField.class)) == null || jSONField.deserialize())) {
                            fieldArr4 = fieldArr2;
                            i3 = i34;
                            length7 = length7;
                            i2 = i2;
                            methods3 = methods3;
                            i33 = i33;
                            type4 = type4;
                        }
                    } else {
                        str = jSONField9.name();
                        fieldArr2 = fieldArr4;
                    }
                    String translate = propertyNamingStrategy3 != null ? propertyNamingStrategy3.translate(str) : str;
                    if (getField(arrayList, translate) != null) {
                        fieldArr4 = fieldArr2;
                        i3 = i34;
                        length7 = length7;
                        i2 = i2;
                        methods3 = methods3;
                        i33 = i33;
                        type4 = type4;
                    } else {
                        fieldArr4 = fieldArr2;
                        i3 = i34;
                        length7 = length7;
                        i2 = i2;
                        methods3 = methods3;
                        i33 = i33;
                        type4 = type4;
                        add(arrayList, new FieldInfo(translate, method5, null, cls, type, 0, 0, 0, jSONField9, null, null, buildGenericInfo));
                    }
                } else {
                    i3 = i34;
                    length7 = length7;
                    i2 = i2;
                    methods3 = methods3;
                    i33 = i33;
                    type4 = type4;
                    fieldArr4 = fieldArr4;
                }
            } else {
                i3 = i34;
                length7 = length7;
                i2 = i2;
                methods3 = methods3;
                i33 = i33;
                type4 = type4;
                fieldArr4 = fieldArr4;
            }
            i34 = i3 + 1;
            cls8 = cls;
        }
        if (arrayList.size() == 0) {
            if (!TypeUtils.isXmlField(cls)) {
                z10 = z;
            }
            if (z10) {
                for (Class<?> cls9 = cls; cls9 != null; cls9 = cls9.getSuperclass()) {
                    computeFields(cls, type4, propertyNamingStrategy3, arrayList, fieldArr4);
                }
            }
        }
        return new JavaBeanInfo(cls, cls2, constructor, constructor2, method2, method, jSONType, arrayList);
    }

    private static void computeFields(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, List<FieldInfo> list, Field[] fieldArr) {
        int i;
        int i2;
        int i3;
        int i4;
        Map<TypeVariable, Type> buildGenericInfo = buildGenericInfo(cls);
        int length = fieldArr.length;
        int i5 = 0;
        while (i5 < length) {
            Field field = fieldArr[i5];
            int modifiers = field.getModifiers();
            if ((modifiers & 8) != 0) {
                i = i5;
                length = length;
            } else {
                boolean z = true;
                if ((modifiers & 16) != 0) {
                    Class<?> type2 = field.getType();
                    if (!(Map.class.isAssignableFrom(type2) || Collection.class.isAssignableFrom(type2) || AtomicLong.class.equals(type2) || AtomicInteger.class.equals(type2) || AtomicBoolean.class.equals(type2))) {
                        i = i5;
                        length = length;
                    }
                }
                Iterator<FieldInfo> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().name.equals(field.getName())) {
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    i = i5;
                    length = length;
                } else {
                    String name = field.getName();
                    JSONField jSONField = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                    if (jSONField == null) {
                        i4 = 0;
                        i3 = 0;
                        i2 = 0;
                    } else if (!jSONField.deserialize()) {
                        i = i5;
                        length = length;
                    } else {
                        int ordinal = jSONField.ordinal();
                        int of = SerializerFeature.of(jSONField.serialzeFeatures());
                        int of2 = Feature.of(jSONField.parseFeatures());
                        if (jSONField.name().length() != 0) {
                            name = jSONField.name();
                            i4 = ordinal;
                            i3 = of;
                            i2 = of2;
                        } else {
                            i4 = ordinal;
                            i3 = of;
                            i2 = of2;
                        }
                    }
                    i = i5;
                    length = length;
                    add(list, new FieldInfo(propertyNamingStrategy != null ? propertyNamingStrategy.translate(name) : name, null, field, cls, type, i4, i3, i2, null, jSONField, null, buildGenericInfo));
                }
            }
            i5 = i + 1;
        }
    }

    static Constructor<?> getDefaultConstructor(Class<?> cls, Constructor<?>[] constructorArr) {
        Constructor<?> constructor = null;
        if (Modifier.isAbstract(cls.getModifiers())) {
            return null;
        }
        int length = constructorArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor2 = constructorArr[i];
            if (constructor2.getParameterTypes().length == 0) {
                constructor = constructor2;
                break;
            }
            i++;
        }
        if (constructor != null || !cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
            return constructor;
        }
        for (Constructor<?> constructor3 : constructorArr) {
            Class<?>[] parameterTypes = constructor3.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(cls.getDeclaringClass())) {
                return constructor3;
            }
        }
        return constructor;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructorArr) {
        boolean z;
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) != null) {
                if (constructor == null) {
                    constructor = constructor2;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructorArr) {
            Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor3);
            if (parameterAnnotations.length != 0) {
                int length = parameterAnnotations.length;
                int i = 0;
                while (true) {
                    z = true;
                    if (i >= length) {
                        break;
                    }
                    Annotation[] annotationArr = parameterAnnotations[i];
                    int length2 = annotationArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            z = false;
                            break;
                        } else if (annotationArr[i2] instanceof JSONField) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (!z) {
                        z = false;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    continue;
                } else if (constructor == null) {
                    constructor = constructor3;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        return constructor != null ? constructor : constructor;
    }

    private static Method getFactoryMethod(Class<?> cls, Method[] methodArr, boolean z) {
        Method method = null;
        for (Method method2 : methodArr) {
            if (Modifier.isStatic(method2.getModifiers()) && cls.isAssignableFrom(method2.getReturnType()) && ((JSONCreator) TypeUtils.getAnnotation(method2, JSONCreator.class)) != null) {
                if (method == null) {
                    method = method2;
                } else {
                    throw new JSONException("multi-JSONCreator");
                }
            }
        }
        if (method == null && z) {
            for (Method method3 : methodArr) {
                if (TypeUtils.isJacksonCreator(method3)) {
                    return method3;
                }
            }
        }
        return method;
    }

    public static Class<?> getBuilderClass(JSONType jSONType) {
        return getBuilderClass(null, jSONType);
    }

    public static Class<?> getBuilderClass(Class<?> cls, JSONType jSONType) {
        Class<?> builder;
        if (cls != null && cls.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (jSONType == null || (builder = jSONType.builder()) == Void.class) {
            return null;
        }
        return builder;
    }
}
