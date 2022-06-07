package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.Charset;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/* loaded from: classes.dex */
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {
    public static final MediaType APPLICATION_JAVASCRIPT = new MediaType(MimeTypes.BASE_TYPE_APPLICATION, "javascript");
    @Deprecated
    protected String dateFormat;
    @Deprecated
    protected SerializerFeature[] features = new SerializerFeature[0];
    @Deprecated
    protected SerializeFilter[] filters = new SerializeFilter[0];
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean supports(Class<?> cls) {
        return true;
    }

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public FastJsonHttpMessageConverter() {
        super(MediaType.ALL);
    }

    @Deprecated
    public Charset getCharset() {
        return this.fastJsonConfig.getCharset();
    }

    @Deprecated
    public void setCharset(Charset charset) {
        this.fastJsonConfig.setCharset(charset);
    }

    @Deprecated
    public String getDateFormat() {
        return this.fastJsonConfig.getDateFormat();
    }

    @Deprecated
    public void setDateFormat(String str) {
        this.fastJsonConfig.setDateFormat(str);
    }

    @Deprecated
    public SerializerFeature[] getFeatures() {
        return this.fastJsonConfig.getSerializerFeatures();
    }

    @Deprecated
    public void setFeatures(SerializerFeature... serializerFeatureArr) {
        this.fastJsonConfig.setSerializerFeatures(serializerFeatureArr);
    }

    @Deprecated
    public SerializeFilter[] getFilters() {
        return this.fastJsonConfig.getSerializeFilters();
    }

    @Deprecated
    public void setFilters(SerializeFilter... serializeFilterArr) {
        this.fastJsonConfig.setSerializeFilters(serializeFilterArr);
    }

    @Deprecated
    public void addSerializeFilter(SerializeFilter serializeFilter) {
        if (serializeFilter != null) {
            int length = this.fastJsonConfig.getSerializeFilters().length;
            SerializeFilter[] serializeFilterArr = new SerializeFilter[length + 1];
            System.arraycopy(this.fastJsonConfig.getSerializeFilters(), 0, serializeFilterArr, 0, length);
            serializeFilterArr[serializeFilterArr.length - 1] = serializeFilter;
            this.fastJsonConfig.setSerializeFilters(serializeFilterArr);
        }
    }

    public boolean canRead(Type type, Class<?> cls, MediaType mediaType) {
        return FastJsonHttpMessageConverter.super.canRead(cls, mediaType);
    }

    public boolean canWrite(Type type, Class<?> cls, MediaType mediaType) {
        return FastJsonHttpMessageConverter.super.canWrite(cls, mediaType);
    }

    public Object read(Type type, Class<?> cls, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return readType(getType(type, cls), httpInputMessage);
    }

    public void write(Object obj, Type type, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        FastJsonHttpMessageConverter.super.write(obj, mediaType, httpOutputMessage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object readInternal(Class<? extends Object> cls, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return readType(getType(cls, null), httpInputMessage);
    }

    private Object readType(Type type, HttpInputMessage httpInputMessage) {
        try {
            return JSON.parseObject(httpInputMessage.getBody(), this.fastJsonConfig.getCharset(), type, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        } catch (JSONException e) {
            throw new HttpMessageNotReadableException("JSON parse error: " + e.getMessage(), e);
        } catch (IOException e2) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0085 A[Catch: JSONException -> 0x00a3, all -> 0x00a1, TryCatch #1 {JSONException -> 0x00a3, blocks: (B:3:0x0005, B:5:0x0021, B:7:0x0035, B:9:0x003b, B:12:0x004a, B:16:0x0051, B:18:0x0085, B:19:0x008a, B:21:0x0092, B:22:0x0096), top: B:32:0x0005, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0092 A[Catch: JSONException -> 0x00a3, all -> 0x00a1, TryCatch #1 {JSONException -> 0x00a3, blocks: (B:3:0x0005, B:5:0x0021, B:7:0x0035, B:9:0x003b, B:12:0x004a, B:16:0x0051, B:18:0x0085, B:19:0x008a, B:21:0x0092, B:22:0x0096), top: B:32:0x0005, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeInternal(java.lang.Object r12, org.springframework.http.HttpOutputMessage r13) throws java.io.IOException, org.springframework.http.converter.HttpMessageNotWritableException {
        /*
            r11 = this;
            java.io.ByteArrayOutputStream r8 = new java.io.ByteArrayOutputStream
            r8.<init>()
            org.springframework.http.HttpHeaders r9 = r13.getHeaders()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.support.config.FastJsonConfig r0 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.serializer.SerializeFilter[] r0 = r0.getSerializeFilters()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.util.List r0 = java.util.Arrays.asList(r0)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r1.<init>(r0)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r0 = 0
            java.lang.Object r12 = r11.strangeCodeForJackson(r12)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            boolean r2 = r12 instanceof com.alibaba.fastjson.support.spring.FastJsonContainer     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            if (r2 == 0) goto L_0x0034
            com.alibaba.fastjson.support.spring.FastJsonContainer r12 = (com.alibaba.fastjson.support.spring.FastJsonContainer) r12     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.support.spring.PropertyPreFilters r2 = r12.getFilters()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.util.List r2 = r2.getFilters()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r1.addAll(r2)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.lang.Object r12 = r12.getValue()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r2 = r12
            goto L_0x0035
        L_0x0034:
            r2 = r12
        L_0x0035:
            boolean r12 = r2 instanceof com.alibaba.fastjson.support.spring.MappingFastJsonValue     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r3 = 1
            if (r12 == 0) goto L_0x004a
            r12 = r2
            com.alibaba.fastjson.support.spring.MappingFastJsonValue r12 = (com.alibaba.fastjson.support.spring.MappingFastJsonValue) r12     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.lang.String r12 = r12.getJsonpFunction()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            boolean r12 = org.springframework.util.StringUtils.isEmpty(r12)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            if (r12 != 0) goto L_0x0050
            r12 = r3
            goto L_0x0051
        L_0x004a:
            boolean r12 = r2 instanceof com.alibaba.fastjson.JSONPObject     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            if (r12 == 0) goto L_0x0050
            r12 = r3
            goto L_0x0051
        L_0x0050:
            r12 = r0
        L_0x0051:
            com.alibaba.fastjson.support.config.FastJsonConfig r0 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.nio.charset.Charset r3 = r0.getCharset()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.support.config.FastJsonConfig r0 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.serializer.SerializeConfig r4 = r0.getSerializeConfig()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            int r0 = r1.size()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.serializer.SerializeFilter[] r0 = new com.alibaba.fastjson.serializer.SerializeFilter[r0]     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.lang.Object[] r0 = r1.toArray(r0)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r5 = r0
            com.alibaba.fastjson.serializer.SerializeFilter[] r5 = (com.alibaba.fastjson.serializer.SerializeFilter[]) r5     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.support.config.FastJsonConfig r0 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            java.lang.String r6 = r0.getDateFormat()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            int r7 = com.alibaba.fastjson.JSON.DEFAULT_GENERATE_FEATURE     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.support.config.FastJsonConfig r0 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            com.alibaba.fastjson.serializer.SerializerFeature[] r10 = r0.getSerializerFeatures()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r0 = r8
            r1 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r10
            int r0 = com.alibaba.fastjson.JSON.writeJSONString(r0, r1, r2, r3, r4, r5, r6, r7)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            if (r12 == 0) goto L_0x008a
            org.springframework.http.MediaType r12 = com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter.APPLICATION_JAVASCRIPT     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r9.setContentType(r12)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
        L_0x008a:
            com.alibaba.fastjson.support.config.FastJsonConfig r12 = r11.fastJsonConfig     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            boolean r12 = r12.isWriteContentLength()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            if (r12 == 0) goto L_0x0096
            long r0 = (long) r0     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r9.setContentLength(r0)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
        L_0x0096:
            java.io.OutputStream r12 = r13.getBody()     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r8.writeTo(r12)     // Catch: JSONException -> 0x00a3, all -> 0x00a1
            r8.close()
            return
        L_0x00a1:
            r12 = move-exception
            goto L_0x00bf
        L_0x00a3:
            r12 = move-exception
            org.springframework.http.converter.HttpMessageNotWritableException r13 = new org.springframework.http.converter.HttpMessageNotWritableException     // Catch: all -> 0x00a1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: all -> 0x00a1
            r0.<init>()     // Catch: all -> 0x00a1
            java.lang.String r1 = "Could not write JSON: "
            r0.append(r1)     // Catch: all -> 0x00a1
            java.lang.String r1 = r12.getMessage()     // Catch: all -> 0x00a1
            r0.append(r1)     // Catch: all -> 0x00a1
            java.lang.String r0 = r0.toString()     // Catch: all -> 0x00a1
            r13.<init>(r0, r12)     // Catch: all -> 0x00a1
            throw r13     // Catch: all -> 0x00a1
        L_0x00bf:
            r8.close()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter.writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage):void");
    }

    private Object strangeCodeForJackson(Object obj) {
        return (obj == null || !"com.fasterxml.jackson.databind.node.ObjectNode".equals(obj.getClass().getName())) ? obj : obj.toString();
    }

    protected Type getType(Type type, Class<?> cls) {
        return Spring4TypeResolvableHelper.isSupport() ? Spring4TypeResolvableHelper.getType(type, cls) : type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Spring4TypeResolvableHelper {
        private static boolean hasClazzResolvableType;

        private Spring4TypeResolvableHelper() {
        }

        static {
            try {
                Class.forName("org.springframework.core.ResolvableType");
                hasClazzResolvableType = true;
            } catch (ClassNotFoundException unused) {
                hasClazzResolvableType = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isSupport() {
            return hasClazzResolvableType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Type getType(Type type, Class<?> cls) {
            if (cls != null) {
                ResolvableType forType = ResolvableType.forType(type);
                if (type instanceof TypeVariable) {
                    ResolvableType resolveVariable = resolveVariable((TypeVariable) type, ResolvableType.forClass(cls));
                    if (resolveVariable != ResolvableType.NONE) {
                        return resolveVariable.resolve();
                    }
                } else if ((type instanceof ParameterizedType) && forType.hasUnresolvableGenerics()) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Class[] clsArr = new Class[parameterizedType.getActualTypeArguments().length];
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (int i = 0; i < actualTypeArguments.length; i++) {
                        Type type2 = actualTypeArguments[i];
                        if (type2 instanceof TypeVariable) {
                            ResolvableType resolveVariable2 = resolveVariable((TypeVariable) type2, ResolvableType.forClass(cls));
                            if (resolveVariable2 != ResolvableType.NONE) {
                                clsArr[i] = resolveVariable2.resolve();
                            } else {
                                clsArr[i] = ResolvableType.forType(type2).resolve();
                            }
                        } else {
                            clsArr[i] = ResolvableType.forType(type2).resolve();
                        }
                    }
                    return ResolvableType.forClassWithGenerics(forType.getRawClass(), clsArr).getType();
                }
            }
            return type;
        }

        private static ResolvableType resolveVariable(TypeVariable<?> typeVariable, ResolvableType resolvableType) {
            if (resolvableType.hasGenerics()) {
                ResolvableType forType = ResolvableType.forType(typeVariable, resolvableType);
                if (forType.resolve() != null) {
                    return forType;
                }
            }
            ResolvableType superType = resolvableType.getSuperType();
            if (superType != ResolvableType.NONE) {
                ResolvableType resolveVariable = resolveVariable(typeVariable, superType);
                if (resolveVariable.resolve() != null) {
                    return resolveVariable;
                }
            }
            for (ResolvableType resolvableType2 : resolvableType.getInterfaces()) {
                ResolvableType resolveVariable2 = resolveVariable(typeVariable, resolvableType2);
                if (resolveVariable2.resolve() != null) {
                    return resolveVariable2;
                }
            }
            return ResolvableType.NONE;
        }
    }
}
