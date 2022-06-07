package com.xiaomi.micolauncher.api.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.converter.handler.IHandler;
import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.common.L;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* loaded from: classes3.dex */
public final class GsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static GsonConverterFactory create() {
        return create(Gsons.getGson());
    }

    public static GsonConverterFactory create(Gson gson) {
        if (gson != null) {
            return new GsonConverterFactory(gson);
        }
        throw new NullPointerException("gson == null");
    }

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override // retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        IHandler iHandler = null;
        Type type2 = type;
        for (Annotation annotation : annotationArr) {
            if (annotation.annotationType().equals(ResponseHandler.class)) {
                ResponseHandler responseHandler = (ResponseHandler) annotation;
                type2 = wrapperType(responseHandler.cls(), type);
                try {
                    iHandler = (IHandler) responseHandler.handler().newInstance();
                } catch (Exception e) {
                    L.base.e("creath handle failed", e);
                }
            }
        }
        return new GsonResponseBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type2)), iHandler);
    }

    @Override // retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        return new GsonRequestBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type)));
    }

    /* loaded from: classes3.dex */
    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;
        private final Gson gson;
        private final IHandler handler;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter, IHandler iHandler) {
            this.gson = gson;
            this.adapter = typeAdapter;
            this.handler = iHandler;
        }

        public T convert(ResponseBody responseBody) throws IOException {
            try {
                T read = this.adapter.read(this.gson.newJsonReader(responseBody.charStream()));
                return this.handler != null ? (T) this.handler.getResponse(read) : read;
            } finally {
                responseBody.close();
            }
        }
    }

    /* loaded from: classes3.dex */
    static final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");
        private final TypeAdapter<T> adapter;
        private final Gson gson;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
            this.gson = gson;
            this.adapter = typeAdapter;
        }

        @Override // retrofit2.Converter
        /* renamed from: convert  reason: avoid collision after fix types in other method */
        public RequestBody convert2(T t) throws IOException {
            Buffer buffer = new Buffer();
            JsonWriter newJsonWriter = this.gson.newJsonWriter(new OutputStreamWriter(buffer.outputStream(), UTF_8));
            this.adapter.write(newJsonWriter, t);
            newJsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    /* loaded from: classes3.dex */
    public class MinaResponseHandler {
        public MinaResponseHandler() {
        }
    }

    public static Type wrapperType(final Class cls, final Type type) {
        return new ParameterizedType() { // from class: com.xiaomi.micolauncher.api.converter.GsonConverterFactory.1
            @Override // java.lang.reflect.ParameterizedType
            public Type getOwnerType() {
                return null;
            }

            @Override // java.lang.reflect.ParameterizedType
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override // java.lang.reflect.ParameterizedType
            public Type getRawType() {
                return cls;
            }
        };
    }
}
