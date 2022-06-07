package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.Unit;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.http.Streaming;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BuiltInConverters.java */
/* loaded from: classes6.dex */
public final class a extends Converter.Factory {
    private boolean a = true;

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (type == ResponseBody.class) {
            if (m.a(annotationArr, (Class<? extends Annotation>) Streaming.class)) {
                return c.a;
            }
            return C0388a.a;
        } else if (type == Void.class) {
            return f.a;
        } else {
            if (!this.a || type != Unit.class) {
                return null;
            }
            try {
                return e.a;
            } catch (NoClassDefFoundError unused) {
                this.a = false;
                return null;
            }
        }
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        if (RequestBody.class.isAssignableFrom(m.a(type))) {
            return b.a;
        }
        return null;
    }

    /* compiled from: BuiltInConverters.java */
    /* loaded from: classes6.dex */
    static final class f implements Converter<ResponseBody, Void> {
        static final f a = new f();

        f() {
        }

        /* renamed from: a */
        public Void convert(ResponseBody responseBody) {
            responseBody.close();
            return null;
        }
    }

    /* compiled from: BuiltInConverters.java */
    /* loaded from: classes6.dex */
    static final class e implements Converter<ResponseBody, Unit> {
        static final e a = new e();

        e() {
        }

        /* renamed from: a */
        public Unit convert(ResponseBody responseBody) {
            responseBody.close();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: BuiltInConverters.java */
    /* loaded from: classes6.dex */
    static final class b implements Converter<RequestBody, RequestBody> {
        static final b a = new b();

        /* renamed from: a */
        public RequestBody convert(RequestBody requestBody) {
            return requestBody;
        }

        b() {
        }
    }

    /* compiled from: BuiltInConverters.java */
    /* loaded from: classes6.dex */
    static final class c implements Converter<ResponseBody, ResponseBody> {
        static final c a = new c();

        /* renamed from: a */
        public ResponseBody convert(ResponseBody responseBody) {
            return responseBody;
        }

        c() {
        }
    }

    /* compiled from: BuiltInConverters.java */
    /* renamed from: retrofit2.a$a  reason: collision with other inner class name */
    /* loaded from: classes6.dex */
    static final class C0388a implements Converter<ResponseBody, ResponseBody> {
        static final C0388a a = new C0388a();

        C0388a() {
        }

        /* renamed from: a */
        public ResponseBody convert(ResponseBody responseBody) throws IOException {
            try {
                return m.a(responseBody);
            } finally {
                responseBody.close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BuiltInConverters.java */
    /* loaded from: classes6.dex */
    public static final class d implements Converter<Object, String> {
        static final d a = new d();

        d() {
        }

        /* renamed from: a */
        public String convert(Object obj) {
            return obj.toString();
        }
    }
}
