package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* loaded from: classes6.dex */
public interface CallAdapter<R, T> {
    T adapt(Call<R> call);

    Type responseType();

    /* loaded from: classes6.dex */
    public static abstract class Factory {
        @Nullable
        public abstract CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit);

        protected static Type getParameterUpperBound(int i, ParameterizedType parameterizedType) {
            return m.a(i, parameterizedType);
        }

        protected static Class<?> getRawType(Type type) {
            return m.a(type);
        }
    }
}
