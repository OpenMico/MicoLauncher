package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.Converter;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: OptionalConverterFactory.java */
@IgnoreJRERequirement
/* loaded from: classes6.dex */
public final class f extends Converter.Factory {
    static final Converter.Factory a = new f();

    f() {
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (getRawType(type) != Optional.class) {
            return null;
        }
        return new a(retrofit.responseBodyConverter(getParameterUpperBound(0, (ParameterizedType) type), annotationArr));
    }

    /* compiled from: OptionalConverterFactory.java */
    @IgnoreJRERequirement
    /* loaded from: classes6.dex */
    static final class a<T> implements Converter<ResponseBody, Optional<T>> {
        final Converter<ResponseBody, T> a;

        a(Converter<ResponseBody, T> converter) {
            this.a = converter;
        }

        /* renamed from: a */
        public Optional<T> convert(ResponseBody responseBody) throws IOException {
            return Optional.ofNullable(this.a.convert(responseBody));
        }
    }
}
