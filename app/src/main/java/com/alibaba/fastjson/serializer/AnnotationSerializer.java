package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotationSerializer implements ObjectSerializer {
    public static AnnotationSerializer instance = new AnnotationSerializer();
    private static volatile Class sun_AnnotationType = null;
    private static volatile boolean sun_AnnotationType_error = false;
    private static volatile Method sun_AnnotationType_getInstance;
    private static volatile Method sun_AnnotationType_members;

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        JSONException jSONException;
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
            Class<?> cls = interfaces[0];
            if (sun_AnnotationType == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType = Class.forName("sun.reflect.annotation.AnnotationType");
                } finally {
                }
            }
            if (sun_AnnotationType != null) {
                if (sun_AnnotationType_getInstance == null && !sun_AnnotationType_error) {
                    try {
                        sun_AnnotationType_getInstance = sun_AnnotationType.getMethod("getInstance", Class.class);
                    } finally {
                    }
                }
                if (sun_AnnotationType_members == null && !sun_AnnotationType_error) {
                    try {
                        sun_AnnotationType_members = sun_AnnotationType.getMethod("members", new Class[0]);
                    } finally {
                    }
                }
                if (sun_AnnotationType_getInstance == null || sun_AnnotationType_error) {
                    throw new JSONException("not support Type Annotation.");
                }
                try {
                    Object[] objArr = {cls};
                    Object obj3 = null;
                    try {
                        Map map = (Map) sun_AnnotationType_members.invoke(sun_AnnotationType_getInstance.invoke(null, objArr), new Object[0]);
                        JSONObject jSONObject = new JSONObject(map.size());
                        for (Map.Entry entry : map.entrySet()) {
                            try {
                                obj3 = ((Method) entry.getValue()).invoke(obj, new Object[0]);
                            } catch (IllegalAccessException | InvocationTargetException unused) {
                            }
                            jSONObject.put((String) entry.getKey(), JSON.toJSON(obj3));
                        }
                        jSONSerializer.write(jSONObject);
                    } finally {
                    }
                } finally {
                }
            } else {
                throw new JSONException("not support Type Annotation.");
            }
        }
    }
}
