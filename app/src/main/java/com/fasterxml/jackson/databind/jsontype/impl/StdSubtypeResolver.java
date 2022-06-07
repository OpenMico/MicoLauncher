package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class StdSubtypeResolver extends SubtypeResolver implements Serializable {
    private static final long serialVersionUID = 1;
    protected LinkedHashSet<NamedType> _registeredSubtypes;

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public void registerSubtypes(NamedType... namedTypeArr) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet<>();
        }
        for (NamedType namedType : namedTypeArr) {
            this._registeredSubtypes.add(namedType);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public void registerSubtypes(Class<?>... clsArr) {
        NamedType[] namedTypeArr = new NamedType[clsArr.length];
        int length = clsArr.length;
        for (int i = 0; i < length; i++) {
            namedTypeArr[i] = new NamedType(clsArr[i]);
        }
        registerSubtypes(namedTypeArr);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public void registerSubtypes(Collection<Class<?>> collection) {
        NamedType[] namedTypeArr = new NamedType[collection.size()];
        int i = 0;
        for (Class<?> cls : collection) {
            i++;
            namedTypeArr[i] = new NamedType(cls);
        }
        registerSubtypes(namedTypeArr);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        List<NamedType> findSubtypes;
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class<?> rawType = javaType == null ? annotatedMember.getRawType() : javaType.getRawClass();
        HashMap<NamedType, NamedType> hashMap = new HashMap<>();
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator<NamedType> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType next = it.next();
                if (rawType.isAssignableFrom(next.getType())) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, next.getType()), next, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        if (!(annotatedMember == null || (findSubtypes = annotationIntrospector.findSubtypes(annotatedMember)) == null)) {
            for (NamedType namedType : findSubtypes) {
                _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, annotationIntrospector, hashMap);
            }
        }
        _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, rawType), new NamedType(rawType, null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList(hashMap.values());
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        HashMap<NamedType, NamedType> hashMap = new HashMap<>();
        if (this._registeredSubtypes != null) {
            Class<?> rawType = annotatedClass.getRawType();
            Iterator<NamedType> it = this._registeredSubtypes.iterator();
            while (it.hasNext()) {
                NamedType next = it.next();
                if (rawType.isAssignableFrom(next.getType())) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, next.getType()), next, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
        _collectAndResolve(annotatedClass, new NamedType(annotatedClass.getRawType(), null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList(hashMap.values());
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        List<NamedType> findSubtypes;
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class<?> rawClass = javaType.getRawClass();
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, rawClass), new NamedType(rawClass, null), mapperConfig, hashSet, linkedHashMap);
        if (!(annotatedMember == null || (findSubtypes = annotationIntrospector.findSubtypes(annotatedMember)) == null)) {
            for (NamedType namedType : findSubtypes) {
                _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType.getType()), namedType, mapperConfig, hashSet, linkedHashMap);
            }
        }
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator<NamedType> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType next = it.next();
                if (rawClass.isAssignableFrom(next.getType())) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, next.getType()), next, mapperConfig, hashSet, linkedHashMap);
                }
            }
        }
        return _combineNamedAndUnnamed(rawClass, hashSet, linkedHashMap);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass) {
        Class<?> rawType = annotatedClass.getRawType();
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        _collectAndResolveByTypeId(annotatedClass, new NamedType(rawType, null), mapperConfig, hashSet, linkedHashMap);
        LinkedHashSet<NamedType> linkedHashSet = this._registeredSubtypes;
        if (linkedHashSet != null) {
            Iterator<NamedType> it = linkedHashSet.iterator();
            while (it.hasNext()) {
                NamedType next = it.next();
                if (rawType.isAssignableFrom(next.getType())) {
                    _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, next.getType()), next, mapperConfig, hashSet, linkedHashMap);
                }
            }
        }
        return _combineNamedAndUnnamed(rawType, hashSet, linkedHashMap);
    }

    protected void _collectAndResolve(AnnotatedClass annotatedClass, NamedType namedType, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, HashMap<NamedType, NamedType> hashMap) {
        String findTypeName;
        if (!namedType.hasName() && (findTypeName = annotationIntrospector.findTypeName(annotatedClass)) != null) {
            namedType = new NamedType(namedType.getType(), findTypeName);
        }
        if (!hashMap.containsKey(namedType)) {
            hashMap.put(namedType, namedType);
            List<NamedType> findSubtypes = annotationIntrospector.findSubtypes(annotatedClass);
            if (!(findSubtypes == null || findSubtypes.isEmpty())) {
                for (NamedType namedType2 : findSubtypes) {
                    _collectAndResolve(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        } else if (namedType.hasName() && !hashMap.get(namedType).hasName()) {
            hashMap.put(namedType, namedType);
        }
    }

    protected void _collectAndResolveByTypeId(AnnotatedClass annotatedClass, NamedType namedType, MapperConfig<?> mapperConfig, Set<Class<?>> set, Map<String, NamedType> map) {
        List<NamedType> findSubtypes;
        String findTypeName;
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        if (!namedType.hasName() && (findTypeName = annotationIntrospector.findTypeName(annotatedClass)) != null) {
            namedType = new NamedType(namedType.getType(), findTypeName);
        }
        if (namedType.hasName()) {
            map.put(namedType.getName(), namedType);
        }
        if (!(!set.add(namedType.getType()) || (findSubtypes = annotationIntrospector.findSubtypes(annotatedClass)) == null || findSubtypes.isEmpty())) {
            for (NamedType namedType2 : findSubtypes) {
                _collectAndResolveByTypeId(AnnotatedClassResolver.resolveWithoutSuperTypes(mapperConfig, namedType2.getType()), namedType2, mapperConfig, set, map);
            }
        }
    }

    protected Collection<NamedType> _combineNamedAndUnnamed(Class<?> cls, Set<Class<?>> set, Map<String, NamedType> map) {
        ArrayList arrayList = new ArrayList(map.values());
        for (NamedType namedType : map.values()) {
            set.remove(namedType.getType());
        }
        for (Class<?> cls2 : set) {
            if (cls2 != cls || !Modifier.isAbstract(cls2.getModifiers())) {
                arrayList.add(new NamedType(cls2));
            }
        }
        return arrayList;
    }
}
