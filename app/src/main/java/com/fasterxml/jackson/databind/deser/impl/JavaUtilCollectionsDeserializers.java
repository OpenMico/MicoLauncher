package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.umeng.analytics.pro.ai;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class JavaUtilCollectionsDeserializers {
    public static final int TYPE_AS_LIST = 7;
    private static final Class<?> b;
    private static final Class<?> c;
    private static final Class<?> d;
    private static final Class<?> e;
    private static final Class<?> f;
    private static final Class<?> h;
    private static final Class<?> a = Arrays.asList(null, null).getClass();
    private static final Class<?> g = Collections.unmodifiableList(new LinkedList()).getClass();

    static {
        Set singleton = Collections.singleton(Boolean.TRUE);
        b = singleton.getClass();
        e = Collections.unmodifiableSet(singleton).getClass();
        List singletonList = Collections.singletonList(Boolean.TRUE);
        c = singletonList.getClass();
        f = Collections.unmodifiableList(singletonList).getClass();
        Map singletonMap = Collections.singletonMap(ai.at, "b");
        d = singletonMap.getClass();
        h = Collections.unmodifiableMap(singletonMap).getClass();
    }

    public static JsonDeserializer<?> findForCollection(DeserializationContext deserializationContext, JavaType javaType) throws JsonMappingException {
        a aVar;
        if (javaType.hasRawClass(a)) {
            aVar = a(7, javaType, List.class);
        } else if (javaType.hasRawClass(c)) {
            aVar = a(2, javaType, List.class);
        } else if (javaType.hasRawClass(b)) {
            aVar = a(1, javaType, Set.class);
        } else if (javaType.hasRawClass(f) || javaType.hasRawClass(g)) {
            aVar = a(5, javaType, List.class);
        } else if (!javaType.hasRawClass(e)) {
            return null;
        } else {
            aVar = a(4, javaType, Set.class);
        }
        return new StdDelegatingDeserializer(aVar);
    }

    public static JsonDeserializer<?> findForMap(DeserializationContext deserializationContext, JavaType javaType) throws JsonMappingException {
        a aVar;
        if (javaType.hasRawClass(d)) {
            aVar = a(3, javaType, Map.class);
        } else if (!javaType.hasRawClass(h)) {
            return null;
        } else {
            aVar = a(6, javaType, Map.class);
        }
        return new StdDelegatingDeserializer(aVar);
    }

    static a a(int i, JavaType javaType, Class<?> cls) {
        return new a(i, javaType.findSuperType(cls));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a implements Converter<Object, Object> {
        private final JavaType a;
        private final int b;

        private a(int i, JavaType javaType) {
            this.a = javaType;
            this.b = i;
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public Object convert(Object obj) {
            if (obj == null) {
                return null;
            }
            switch (this.b) {
                case 1:
                    Set set = (Set) obj;
                    a(set.size());
                    return Collections.singleton(set.iterator().next());
                case 2:
                    List list = (List) obj;
                    a(list.size());
                    return Collections.singletonList(list.get(0));
                case 3:
                    Map map = (Map) obj;
                    a(map.size());
                    Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
                    return Collections.singletonMap(entry.getKey(), entry.getValue());
                case 4:
                    return Collections.unmodifiableSet((Set) obj);
                case 5:
                    return Collections.unmodifiableList((List) obj);
                case 6:
                    return Collections.unmodifiableMap((Map) obj);
                default:
                    return obj;
            }
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public JavaType getInputType(TypeFactory typeFactory) {
            return this.a;
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public JavaType getOutputType(TypeFactory typeFactory) {
            return this.a;
        }

        private void a(int i) {
            if (i != 1) {
                throw new IllegalArgumentException("Can not deserialize Singleton container from " + i + " entries");
            }
        }
    }
}
