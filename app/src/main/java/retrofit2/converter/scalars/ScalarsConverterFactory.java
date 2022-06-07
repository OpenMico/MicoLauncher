package retrofit2.converter.scalars;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.b;

/* loaded from: classes6.dex */
public final class ScalarsConverterFactory extends Converter.Factory {
    public static ScalarsConverterFactory create() {
        return new ScalarsConverterFactory();
    }

    private ScalarsConverterFactory() {
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        if (type == String.class || type == Boolean.TYPE || type == Boolean.class || type == Byte.TYPE || type == Byte.class || type == Character.TYPE || type == Character.class || type == Double.TYPE || type == Double.class || type == Float.TYPE || type == Float.class || type == Integer.TYPE || type == Integer.class || type == Long.TYPE || type == Long.class || type == Short.TYPE || type == Short.class) {
            return a.a;
        }
        return null;
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (type == String.class) {
            return b.i.a;
        }
        if (type == Boolean.class || type == Boolean.TYPE) {
            return b.a.a;
        }
        if (type == Byte.class || type == Byte.TYPE) {
            return b.C0393b.a;
        }
        if (type == Character.class || type == Character.TYPE) {
            return b.c.a;
        }
        if (type == Double.class || type == Double.TYPE) {
            return b.d.a;
        }
        if (type == Float.class || type == Float.TYPE) {
            return b.e.a;
        }
        if (type == Integer.class || type == Integer.TYPE) {
            return b.f.a;
        }
        if (type == Long.class || type == Long.TYPE) {
            return b.g.a;
        }
        if (type == Short.class || type == Short.TYPE) {
            return b.h.a;
        }
        return null;
    }
}
