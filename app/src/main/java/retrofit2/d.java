package retrofit2;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.m;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: HttpServiceMethod.java */
/* loaded from: classes6.dex */
public abstract class d<ResponseT, ReturnT> extends k<ReturnT> {
    private final j a;
    private final Call.Factory b;
    private final Converter<ResponseBody, ResponseT> c;

    @Nullable
    protected abstract ReturnT a(Call<ResponseT> call, Object[] objArr);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <ResponseT, ReturnT> d<ResponseT, ReturnT> a(Retrofit retrofit, Method method, j jVar) {
        Type type;
        boolean z;
        boolean z2 = jVar.b;
        Annotation[] annotations = method.getAnnotations();
        if (z2) {
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Type b2 = m.b(0, (ParameterizedType) genericParameterTypes[genericParameterTypes.length - 1]);
            if (m.a(b2) != Response.class || !(b2 instanceof ParameterizedType)) {
                z = false;
            } else {
                b2 = m.a(0, (ParameterizedType) b2);
                z = true;
            }
            type = new m.b(null, Call.class, b2);
            annotations = l.a(annotations);
        } else {
            type = method.getGenericReturnType();
            z = false;
        }
        CallAdapter a2 = a(retrofit, method, type, annotations);
        Type responseType = a2.responseType();
        if (responseType == Response.class) {
            throw m.a(method, LrcRow.SINGLE_QUOTE + m.a(responseType).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
        } else if (responseType == Response.class) {
            throw m.a(method, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        } else if (!jVar.a.equals("HEAD") || Void.class.equals(responseType)) {
            Converter a3 = a(retrofit, method, responseType);
            Call.Factory factory = retrofit.a;
            if (!z2) {
                return new a(jVar, factory, a3, a2);
            }
            if (z) {
                return new c(jVar, factory, a3, a2);
            }
            return new b(jVar, factory, a3, a2, false);
        } else {
            throw m.a(method, "HEAD method must use Void as response type.", new Object[0]);
        }
    }

    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> a(Retrofit retrofit, Method method, Type type, Annotation[] annotationArr) {
        try {
            return (CallAdapter<ResponseT, ReturnT>) retrofit.callAdapter(type, annotationArr);
        } catch (RuntimeException e) {
            throw m.a(method, e, "Unable to create call adapter for %s", type);
        }
    }

    private static <ResponseT> Converter<ResponseBody, ResponseT> a(Retrofit retrofit, Method method, Type type) {
        try {
            return retrofit.responseBodyConverter(type, method.getAnnotations());
        } catch (RuntimeException e) {
            throw m.a(method, e, "Unable to create converter for %s", type);
        }
    }

    d(j jVar, Call.Factory factory, Converter<ResponseBody, ResponseT> converter) {
        this.a = jVar;
        this.b = factory;
        this.c = converter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // retrofit2.k
    @Nullable
    public final ReturnT a(Object[] objArr) {
        return a(new e(this.a, objArr, this.b, this.c), objArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: HttpServiceMethod.java */
    /* loaded from: classes6.dex */
    public static final class a<ResponseT, ReturnT> extends d<ResponseT, ReturnT> {
        private final CallAdapter<ResponseT, ReturnT> a;

        a(j jVar, Call.Factory factory, Converter<ResponseBody, ResponseT> converter, CallAdapter<ResponseT, ReturnT> callAdapter) {
            super(jVar, factory, converter);
            this.a = callAdapter;
        }

        @Override // retrofit2.d
        protected ReturnT a(Call<ResponseT> call, Object[] objArr) {
            return this.a.adapt(call);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: HttpServiceMethod.java */
    /* loaded from: classes6.dex */
    public static final class c<ResponseT> extends d<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> a;

        c(j jVar, Call.Factory factory, Converter<ResponseBody, ResponseT> converter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter) {
            super(jVar, factory, converter);
            this.a = callAdapter;
        }

        @Override // retrofit2.d
        protected Object a(Call<ResponseT> call, Object[] objArr) {
            Call<ResponseT> adapt = this.a.adapt(call);
            Continuation continuation = (Continuation) objArr[objArr.length - 1];
            try {
                return KotlinExtensions.awaitResponse(adapt, continuation);
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: HttpServiceMethod.java */
    /* loaded from: classes6.dex */
    public static final class b<ResponseT> extends d<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> a;
        private final boolean b;

        b(j jVar, Call.Factory factory, Converter<ResponseBody, ResponseT> converter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter, boolean z) {
            super(jVar, factory, converter);
            this.a = callAdapter;
            this.b = z;
        }

        @Override // retrofit2.d
        protected Object a(Call<ResponseT> call, Object[] objArr) {
            Call<ResponseT> adapt = this.a.adapt(call);
            Continuation continuation = (Continuation) objArr[objArr.length - 1];
            try {
                if (this.b) {
                    return KotlinExtensions.awaitNullable(adapt, continuation);
                }
                return KotlinExtensions.await(adapt, continuation);
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }
}
