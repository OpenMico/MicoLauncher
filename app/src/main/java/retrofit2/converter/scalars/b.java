package retrofit2.converter.scalars;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* compiled from: ScalarResponseBodyConverters.java */
/* loaded from: classes6.dex */
final class b {

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class i implements Converter<ResponseBody, String> {
        static final i a = new i();

        i() {
        }

        /* renamed from: a */
        public String convert(ResponseBody responseBody) throws IOException {
            return responseBody.string();
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class a implements Converter<ResponseBody, Boolean> {
        static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public Boolean convert(ResponseBody responseBody) throws IOException {
            return Boolean.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* renamed from: retrofit2.converter.scalars.b$b  reason: collision with other inner class name */
    /* loaded from: classes6.dex */
    static final class C0393b implements Converter<ResponseBody, Byte> {
        static final C0393b a = new C0393b();

        C0393b() {
        }

        /* renamed from: a */
        public Byte convert(ResponseBody responseBody) throws IOException {
            return Byte.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class c implements Converter<ResponseBody, Character> {
        static final c a = new c();

        c() {
        }

        /* renamed from: a */
        public Character convert(ResponseBody responseBody) throws IOException {
            String string = responseBody.string();
            if (string.length() == 1) {
                return Character.valueOf(string.charAt(0));
            }
            throw new IOException("Expected body of length 1 for Character conversion but was " + string.length());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class d implements Converter<ResponseBody, Double> {
        static final d a = new d();

        d() {
        }

        /* renamed from: a */
        public Double convert(ResponseBody responseBody) throws IOException {
            return Double.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class e implements Converter<ResponseBody, Float> {
        static final e a = new e();

        e() {
        }

        /* renamed from: a */
        public Float convert(ResponseBody responseBody) throws IOException {
            return Float.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class f implements Converter<ResponseBody, Integer> {
        static final f a = new f();

        f() {
        }

        /* renamed from: a */
        public Integer convert(ResponseBody responseBody) throws IOException {
            return Integer.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class g implements Converter<ResponseBody, Long> {
        static final g a = new g();

        g() {
        }

        /* renamed from: a */
        public Long convert(ResponseBody responseBody) throws IOException {
            return Long.valueOf(responseBody.string());
        }
    }

    /* compiled from: ScalarResponseBodyConverters.java */
    /* loaded from: classes6.dex */
    static final class h implements Converter<ResponseBody, Short> {
        static final h a = new h();

        h() {
        }

        /* renamed from: a */
        public Short convert(ResponseBody responseBody) throws IOException {
            return Short.valueOf(responseBody.string());
        }
    }
}
