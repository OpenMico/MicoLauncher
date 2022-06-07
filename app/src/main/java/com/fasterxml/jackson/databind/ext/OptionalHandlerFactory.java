package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;

/* loaded from: classes.dex */
public class OptionalHandlerFactory implements Serializable {
    private static final Class<?> CLASS_DOM_DOCUMENT;
    private static final Class<?> CLASS_DOM_NODE;
    private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
    private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
    private static final String DESERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
    private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
    private static final String SERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
    private static final String SERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMSerializer";
    private static final Java7Support _jdk7Helper;
    public static final OptionalHandlerFactory instance;
    private static final long serialVersionUID = 1;

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:5:0x0006
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    static {
        /*
            r0 = 0
            java.lang.Class<org.w3c.dom.Node> r1 = org.w3c.dom.Node.class
            java.lang.Class<org.w3c.dom.Document> r2 = org.w3c.dom.Document.class
            goto L_0x0019
        L_0x0006:
            r1 = r0
        L_0x0007:
            java.lang.Class<com.fasterxml.jackson.databind.ext.OptionalHandlerFactory> r2 = com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.class
            java.lang.String r2 = r2.getName()
            java.util.logging.Logger r2 = java.util.logging.Logger.getLogger(r2)
            java.util.logging.Level r3 = java.util.logging.Level.INFO
            java.lang.String r4 = "Could not load DOM `Node` and/or `Document` classes: no DOM support"
            r2.log(r3, r4)
            r2 = r0
        L_0x0019:
            com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.CLASS_DOM_NODE = r1
            com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.CLASS_DOM_DOCUMENT = r2
            com.fasterxml.jackson.databind.ext.Java7Support r0 = com.fasterxml.jackson.databind.ext.Java7Support.instance()     // Catch: Throwable -> 0x0021
        L_0x0021:
            com.fasterxml.jackson.databind.ext.OptionalHandlerFactory._jdk7Helper = r0
            com.fasterxml.jackson.databind.ext.OptionalHandlerFactory r0 = new com.fasterxml.jackson.databind.ext.OptionalHandlerFactory
            r0.<init>()
            com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.instance = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.<clinit>():void");
    }

    protected OptionalHandlerFactory() {
    }

    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
        Object instantiate;
        JsonSerializer<?> serializerForJavaNioFilePath;
        Class<?> rawClass = javaType.getRawClass();
        Java7Support java7Support = _jdk7Helper;
        if (java7Support != null && (serializerForJavaNioFilePath = java7Support.getSerializerForJavaNioFilePath(rawClass)) != null) {
            return serializerForJavaNioFilePath;
        }
        Class<?> cls = CLASS_DOM_NODE;
        if (cls != null && cls.isAssignableFrom(rawClass)) {
            return (JsonSerializer) instantiate(SERIALIZER_FOR_DOM_NODE);
        }
        if ((rawClass.getName().startsWith(PACKAGE_PREFIX_JAVAX_XML) || hasSuperClassStartingWith(rawClass, PACKAGE_PREFIX_JAVAX_XML)) && (instantiate = instantiate(SERIALIZERS_FOR_JAVAX_XML)) != null) {
            return ((Serializers) instantiate).findSerializer(serializationConfig, javaType, beanDescription);
        }
        return null;
    }

    public JsonDeserializer<?> findDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Object instantiate;
        JsonDeserializer<?> deserializerForJavaNioFilePath;
        Class<?> rawClass = javaType.getRawClass();
        Java7Support java7Support = _jdk7Helper;
        if (java7Support != null && (deserializerForJavaNioFilePath = java7Support.getDeserializerForJavaNioFilePath(rawClass)) != null) {
            return deserializerForJavaNioFilePath;
        }
        Class<?> cls = CLASS_DOM_NODE;
        if (cls != null && cls.isAssignableFrom(rawClass)) {
            return (JsonDeserializer) instantiate(DESERIALIZER_FOR_DOM_NODE);
        }
        Class<?> cls2 = CLASS_DOM_DOCUMENT;
        if (cls2 != null && cls2.isAssignableFrom(rawClass)) {
            return (JsonDeserializer) instantiate(DESERIALIZER_FOR_DOM_DOCUMENT);
        }
        if ((rawClass.getName().startsWith(PACKAGE_PREFIX_JAVAX_XML) || hasSuperClassStartingWith(rawClass, PACKAGE_PREFIX_JAVAX_XML)) && (instantiate = instantiate(DESERIALIZERS_FOR_JAVAX_XML)) != null) {
            return ((Deserializers) instantiate).findBeanDeserializer(javaType, deserializationConfig, beanDescription);
        }
        return null;
    }

    private Object instantiate(String str) {
        try {
            return ClassUtil.createInstance(Class.forName(str), false);
        } catch (Exception | LinkageError unused) {
            return null;
        }
    }

    private boolean hasSuperClassStartingWith(Class<?> cls, String str) {
        do {
            cls = cls.getSuperclass();
            if (cls == null || cls == Object.class) {
                return false;
            }
        } while (!cls.getName().startsWith(str));
        return true;
    }
}
