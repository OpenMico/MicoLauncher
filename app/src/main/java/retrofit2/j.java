package retrofit2;

import com.google.common.net.HttpHeaders;
import com.zhy.http.okhttp.OkHttpUtils;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import org.eclipse.jetty.http.HttpMethods;
import retrofit2.g;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Tag;
import retrofit2.http.Url;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RequestFactory.java */
/* loaded from: classes6.dex */
public final class j {
    final String a;
    final boolean b;
    private final Method c;
    private final HttpUrl d;
    @Nullable
    private final String e;
    @Nullable
    private final Headers f;
    @Nullable
    private final MediaType g;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final g<?>[] k;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static j a(Retrofit retrofit, Method method) {
        return new a(retrofit, method).a();
    }

    j(a aVar) {
        this.c = aVar.b;
        this.d = aVar.a.b;
        this.a = aVar.n;
        this.e = aVar.r;
        this.f = aVar.s;
        this.g = aVar.t;
        this.h = aVar.o;
        this.i = aVar.p;
        this.j = aVar.q;
        this.k = aVar.v;
        this.b = aVar.w;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Request a(Object[] objArr) throws IOException {
        g<?>[] gVarArr = this.k;
        int length = objArr.length;
        if (length == gVarArr.length) {
            i iVar = new i(this.a, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
            if (this.b) {
                length--;
            }
            ArrayList arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(objArr[i]);
                gVarArr[i].a(iVar, objArr[i]);
            }
            return iVar.a().tag(Invocation.class, new Invocation(this.c, arrayList)).build();
        }
        throw new IllegalArgumentException("Argument count (" + length + ") doesn't match expected count (" + gVarArr.length + ")");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RequestFactory.java */
    /* loaded from: classes6.dex */
    public static final class a {
        private static final Pattern x = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        private static final Pattern y = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]*");
        final Retrofit a;
        final Method b;
        final Annotation[] c;
        final Annotation[][] d;
        final Type[] e;
        boolean f;
        boolean g;
        boolean h;
        boolean i;
        boolean j;
        boolean k;
        boolean l;
        boolean m;
        @Nullable
        String n;
        boolean o;
        boolean p;
        boolean q;
        @Nullable
        String r;
        @Nullable
        Headers s;
        @Nullable
        MediaType t;
        @Nullable
        Set<String> u;
        @Nullable
        g<?>[] v;
        boolean w;

        a(Retrofit retrofit, Method method) {
            this.a = retrofit;
            this.b = method;
            this.c = method.getAnnotations();
            this.e = method.getGenericParameterTypes();
            this.d = method.getParameterAnnotations();
        }

        j a() {
            for (Annotation annotation : this.c) {
                a(annotation);
            }
            if (this.n != null) {
                if (!this.o) {
                    if (this.q) {
                        throw m.a(this.b, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    } else if (this.p) {
                        throw m.a(this.b, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    }
                }
                int length = this.d.length;
                this.v = new g[length];
                int i = length - 1;
                int i2 = 0;
                while (true) {
                    boolean z = true;
                    if (i2 >= length) {
                        break;
                    }
                    g<?>[] gVarArr = this.v;
                    Type type = this.e[i2];
                    Annotation[] annotationArr = this.d[i2];
                    if (i2 != i) {
                        z = false;
                    }
                    gVarArr[i2] = a(i2, type, annotationArr, z);
                    i2++;
                }
                if (this.r == null && !this.m) {
                    throw m.a(this.b, "Missing either @%s URL or @Url parameter.", this.n);
                } else if (!this.p && !this.q && !this.o && this.h) {
                    throw m.a(this.b, "Non-body HTTP method cannot contain @Body.", new Object[0]);
                } else if (this.p && !this.f) {
                    throw m.a(this.b, "Form-encoded method must contain at least one @Field.", new Object[0]);
                } else if (!this.q || this.g) {
                    return new j(this);
                } else {
                    throw m.a(this.b, "Multipart method must contain at least one @Part.", new Object[0]);
                }
            } else {
                throw m.a(this.b, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
        }

        private void a(Annotation annotation) {
            if (annotation instanceof DELETE) {
                a("DELETE", ((DELETE) annotation).value(), false);
            } else if (annotation instanceof GET) {
                a("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof HEAD) {
                a("HEAD", ((HEAD) annotation).value(), false);
            } else if (annotation instanceof PATCH) {
                a(OkHttpUtils.METHOD.PATCH, ((PATCH) annotation).value(), true);
            } else if (annotation instanceof POST) {
                a("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof PUT) {
                a("PUT", ((PUT) annotation).value(), true);
            } else if (annotation instanceof OPTIONS) {
                a(HttpMethods.OPTIONS, ((OPTIONS) annotation).value(), false);
            } else if (annotation instanceof HTTP) {
                HTTP http = (HTTP) annotation;
                a(http.method(), http.path(), http.hasBody());
            } else if (annotation instanceof retrofit2.http.Headers) {
                String[] value = ((retrofit2.http.Headers) annotation).value();
                if (value.length != 0) {
                    this.s = a(value);
                    return;
                }
                throw m.a(this.b, "@Headers annotation is empty.", new Object[0]);
            } else if (annotation instanceof Multipart) {
                if (!this.p) {
                    this.q = true;
                    return;
                }
                throw m.a(this.b, "Only one encoding annotation is allowed.", new Object[0]);
            } else if (!(annotation instanceof FormUrlEncoded)) {
            } else {
                if (!this.q) {
                    this.p = true;
                    return;
                }
                throw m.a(this.b, "Only one encoding annotation is allowed.", new Object[0]);
            }
        }

        private void a(String str, String str2, boolean z) {
            String str3 = this.n;
            if (str3 == null) {
                this.n = str;
                this.o = z;
                if (!str2.isEmpty()) {
                    int indexOf = str2.indexOf(63);
                    if (indexOf != -1 && indexOf < str2.length() - 1) {
                        String substring = str2.substring(indexOf + 1);
                        if (x.matcher(substring).find()) {
                            throw m.a(this.b, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", substring);
                        }
                    }
                    this.r = str2;
                    this.u = a(str2);
                    return;
                }
                return;
            }
            throw m.a(this.b, "Only one HTTP method is allowed. Found: %s and %s.", str3, str);
        }

        private Headers a(String[] strArr) {
            Headers.Builder builder = new Headers.Builder();
            for (String str : strArr) {
                int indexOf = str.indexOf(58);
                if (indexOf == -1 || indexOf == 0 || indexOf == str.length() - 1) {
                    throw m.a(this.b, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", str);
                }
                String substring = str.substring(0, indexOf);
                String trim = str.substring(indexOf + 1).trim();
                if ("Content-Type".equalsIgnoreCase(substring)) {
                    try {
                        this.t = MediaType.get(trim);
                    } catch (IllegalArgumentException e) {
                        throw m.a(this.b, e, "Malformed content type: %s", trim);
                    }
                } else {
                    builder.add(substring, trim);
                }
            }
            return builder.build();
        }

        @Nullable
        private g<?> a(int i, Type type, @Nullable Annotation[] annotationArr, boolean z) {
            g<?> gVar;
            if (annotationArr != null) {
                gVar = null;
                for (Annotation annotation : annotationArr) {
                    g<?> a = a(i, type, annotationArr, annotation);
                    if (a != null) {
                        if (gVar == null) {
                            gVar = a;
                        } else {
                            throw m.a(this.b, i, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                        }
                    }
                }
            } else {
                gVar = null;
            }
            if (gVar != null) {
                return gVar;
            }
            if (z) {
                try {
                    if (m.a(type) == Continuation.class) {
                        this.w = true;
                        return null;
                    }
                } catch (NoClassDefFoundError unused) {
                }
            }
            throw m.a(this.b, i, "No Retrofit annotation found.", new Object[0]);
        }

        @Nullable
        private g<?> a(int i, Type type, Annotation[] annotationArr, Annotation annotation) {
            if (annotation instanceof Url) {
                a(i, type);
                if (this.m) {
                    throw m.a(this.b, i, "Multiple @Url method annotations found.", new Object[0]);
                } else if (this.i) {
                    throw m.a(this.b, i, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.j) {
                    throw m.a(this.b, i, "A @Url parameter must not come after a @Query.", new Object[0]);
                } else if (this.k) {
                    throw m.a(this.b, i, "A @Url parameter must not come after a @QueryName.", new Object[0]);
                } else if (this.l) {
                    throw m.a(this.b, i, "A @Url parameter must not come after a @QueryMap.", new Object[0]);
                } else if (this.r == null) {
                    this.m = true;
                    if (type == HttpUrl.class || type == String.class || type == URI.class || ((type instanceof Class) && "android.net.Uri".equals(((Class) type).getName()))) {
                        return new g.n(this.b, i);
                    }
                    throw m.a(this.b, i, "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.", new Object[0]);
                } else {
                    throw m.a(this.b, i, "@Url cannot be used with @%s URL", this.n);
                }
            } else if (annotation instanceof Path) {
                a(i, type);
                if (this.j) {
                    throw m.a(this.b, i, "A @Path parameter must not come after a @Query.", new Object[0]);
                } else if (this.k) {
                    throw m.a(this.b, i, "A @Path parameter must not come after a @QueryName.", new Object[0]);
                } else if (this.l) {
                    throw m.a(this.b, i, "A @Path parameter must not come after a @QueryMap.", new Object[0]);
                } else if (this.m) {
                    throw m.a(this.b, i, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.r != null) {
                    this.i = true;
                    Path path = (Path) annotation;
                    String value = path.value();
                    a(i, value);
                    return new g.i(this.b, i, value, this.a.stringConverter(type, annotationArr), path.encoded());
                } else {
                    throw m.a(this.b, i, "@Path can only be used with relative url on @%s", this.n);
                }
            } else if (annotation instanceof Query) {
                a(i, type);
                Query query = (Query) annotation;
                String value2 = query.value();
                boolean encoded = query.encoded();
                Class<?> a = m.a(type);
                this.j = true;
                if (Iterable.class.isAssignableFrom(a)) {
                    if (type instanceof ParameterizedType) {
                        return new g.j(value2, this.a.stringConverter(m.a(0, (ParameterizedType) type), annotationArr), encoded).a();
                    }
                    throw m.a(this.b, i, a.getSimpleName() + " must include generic type (e.g., " + a.getSimpleName() + "<String>)", new Object[0]);
                } else if (a.isArray()) {
                    return new g.j(value2, this.a.stringConverter(a(a.getComponentType()), annotationArr), encoded).b();
                } else {
                    return new g.j(value2, this.a.stringConverter(type, annotationArr), encoded);
                }
            } else if (annotation instanceof QueryName) {
                a(i, type);
                boolean encoded2 = ((QueryName) annotation).encoded();
                Class<?> a2 = m.a(type);
                this.k = true;
                if (Iterable.class.isAssignableFrom(a2)) {
                    if (type instanceof ParameterizedType) {
                        return new g.l(this.a.stringConverter(m.a(0, (ParameterizedType) type), annotationArr), encoded2).a();
                    }
                    throw m.a(this.b, i, a2.getSimpleName() + " must include generic type (e.g., " + a2.getSimpleName() + "<String>)", new Object[0]);
                } else if (a2.isArray()) {
                    return new g.l(this.a.stringConverter(a(a2.getComponentType()), annotationArr), encoded2).b();
                } else {
                    return new g.l(this.a.stringConverter(type, annotationArr), encoded2);
                }
            } else if (annotation instanceof QueryMap) {
                a(i, type);
                Class<?> a3 = m.a(type);
                this.l = true;
                if (Map.class.isAssignableFrom(a3)) {
                    Type b = m.b(type, a3, Map.class);
                    if (b instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) b;
                        Type a4 = m.a(0, parameterizedType);
                        if (String.class == a4) {
                            return new g.k(this.b, i, this.a.stringConverter(m.a(1, parameterizedType), annotationArr), ((QueryMap) annotation).encoded());
                        }
                        throw m.a(this.b, i, "@QueryMap keys must be of type String: " + a4, new Object[0]);
                    }
                    throw m.a(this.b, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw m.a(this.b, i, "@QueryMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof Header) {
                a(i, type);
                String value3 = ((Header) annotation).value();
                Class<?> a5 = m.a(type);
                if (Iterable.class.isAssignableFrom(a5)) {
                    if (type instanceof ParameterizedType) {
                        return new g.d(value3, this.a.stringConverter(m.a(0, (ParameterizedType) type), annotationArr)).a();
                    }
                    throw m.a(this.b, i, a5.getSimpleName() + " must include generic type (e.g., " + a5.getSimpleName() + "<String>)", new Object[0]);
                } else if (a5.isArray()) {
                    return new g.d(value3, this.a.stringConverter(a(a5.getComponentType()), annotationArr)).b();
                } else {
                    return new g.d(value3, this.a.stringConverter(type, annotationArr));
                }
            } else if (annotation instanceof HeaderMap) {
                if (type == Headers.class) {
                    return new g.f(this.b, i);
                }
                a(i, type);
                Class<?> a6 = m.a(type);
                if (Map.class.isAssignableFrom(a6)) {
                    Type b2 = m.b(type, a6, Map.class);
                    if (b2 instanceof ParameterizedType) {
                        ParameterizedType parameterizedType2 = (ParameterizedType) b2;
                        Type a7 = m.a(0, parameterizedType2);
                        if (String.class == a7) {
                            return new g.e(this.b, i, this.a.stringConverter(m.a(1, parameterizedType2), annotationArr));
                        }
                        throw m.a(this.b, i, "@HeaderMap keys must be of type String: " + a7, new Object[0]);
                    }
                    throw m.a(this.b, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw m.a(this.b, i, "@HeaderMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof Field) {
                a(i, type);
                if (this.p) {
                    Field field = (Field) annotation;
                    String value4 = field.value();
                    boolean encoded3 = field.encoded();
                    this.f = true;
                    Class<?> a8 = m.a(type);
                    if (Iterable.class.isAssignableFrom(a8)) {
                        if (type instanceof ParameterizedType) {
                            return new g.b(value4, this.a.stringConverter(m.a(0, (ParameterizedType) type), annotationArr), encoded3).a();
                        }
                        throw m.a(this.b, i, a8.getSimpleName() + " must include generic type (e.g., " + a8.getSimpleName() + "<String>)", new Object[0]);
                    } else if (a8.isArray()) {
                        return new g.b(value4, this.a.stringConverter(a(a8.getComponentType()), annotationArr), encoded3).b();
                    } else {
                        return new g.b(value4, this.a.stringConverter(type, annotationArr), encoded3);
                    }
                } else {
                    throw m.a(this.b, i, "@Field parameters can only be used with form encoding.", new Object[0]);
                }
            } else if (annotation instanceof FieldMap) {
                a(i, type);
                if (this.p) {
                    Class<?> a9 = m.a(type);
                    if (Map.class.isAssignableFrom(a9)) {
                        Type b3 = m.b(type, a9, Map.class);
                        if (b3 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType3 = (ParameterizedType) b3;
                            Type a10 = m.a(0, parameterizedType3);
                            if (String.class == a10) {
                                Converter stringConverter = this.a.stringConverter(m.a(1, parameterizedType3), annotationArr);
                                this.f = true;
                                return new g.c(this.b, i, stringConverter, ((FieldMap) annotation).encoded());
                            }
                            throw m.a(this.b, i, "@FieldMap keys must be of type String: " + a10, new Object[0]);
                        }
                        throw m.a(this.b, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw m.a(this.b, i, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                throw m.a(this.b, i, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
            } else if (annotation instanceof Part) {
                a(i, type);
                if (this.q) {
                    Part part = (Part) annotation;
                    this.g = true;
                    String value5 = part.value();
                    Class<?> a11 = m.a(type);
                    if (!value5.isEmpty()) {
                        Headers of = Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"" + value5 + "\"", HttpHeaders.Names.CONTENT_TRANSFER_ENCODING, part.encoding());
                        if (Iterable.class.isAssignableFrom(a11)) {
                            if (type instanceof ParameterizedType) {
                                Type a12 = m.a(0, (ParameterizedType) type);
                                if (!MultipartBody.Part.class.isAssignableFrom(m.a(a12))) {
                                    return new g.C0394g(this.b, i, of, this.a.requestBodyConverter(a12, annotationArr, this.c)).a();
                                }
                                throw m.a(this.b, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                            }
                            throw m.a(this.b, i, a11.getSimpleName() + " must include generic type (e.g., " + a11.getSimpleName() + "<String>)", new Object[0]);
                        } else if (a11.isArray()) {
                            Class<?> a13 = a(a11.getComponentType());
                            if (!MultipartBody.Part.class.isAssignableFrom(a13)) {
                                return new g.C0394g(this.b, i, of, this.a.requestBodyConverter(a13, annotationArr, this.c)).b();
                            }
                            throw m.a(this.b, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        } else if (!MultipartBody.Part.class.isAssignableFrom(a11)) {
                            return new g.C0394g(this.b, i, of, this.a.requestBodyConverter(type, annotationArr, this.c));
                        } else {
                            throw m.a(this.b, i, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        }
                    } else if (Iterable.class.isAssignableFrom(a11)) {
                        if (!(type instanceof ParameterizedType)) {
                            throw m.a(this.b, i, a11.getSimpleName() + " must include generic type (e.g., " + a11.getSimpleName() + "<String>)", new Object[0]);
                        } else if (MultipartBody.Part.class.isAssignableFrom(m.a(m.a(0, (ParameterizedType) type)))) {
                            return g.m.a.a();
                        } else {
                            throw m.a(this.b, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                    } else if (a11.isArray()) {
                        if (MultipartBody.Part.class.isAssignableFrom(a11.getComponentType())) {
                            return g.m.a.b();
                        }
                        throw m.a(this.b, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    } else if (MultipartBody.Part.class.isAssignableFrom(a11)) {
                        return g.m.a;
                    } else {
                        throw m.a(this.b, i, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                } else {
                    throw m.a(this.b, i, "@Part parameters can only be used with multipart encoding.", new Object[0]);
                }
            } else if (annotation instanceof PartMap) {
                a(i, type);
                if (this.q) {
                    this.g = true;
                    Class<?> a14 = m.a(type);
                    if (Map.class.isAssignableFrom(a14)) {
                        Type b4 = m.b(type, a14, Map.class);
                        if (b4 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType4 = (ParameterizedType) b4;
                            Type a15 = m.a(0, parameterizedType4);
                            if (String.class == a15) {
                                Type a16 = m.a(1, parameterizedType4);
                                if (!MultipartBody.Part.class.isAssignableFrom(m.a(a16))) {
                                    return new g.h(this.b, i, this.a.requestBodyConverter(a16, annotationArr, this.c), ((PartMap) annotation).encoding());
                                }
                                throw m.a(this.b, i, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                            }
                            throw m.a(this.b, i, "@PartMap keys must be of type String: " + a15, new Object[0]);
                        }
                        throw m.a(this.b, i, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw m.a(this.b, i, "@PartMap parameter type must be Map.", new Object[0]);
                }
                throw m.a(this.b, i, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
            } else if (annotation instanceof Body) {
                a(i, type);
                if (this.p || this.q) {
                    throw m.a(this.b, i, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                } else if (!this.h) {
                    try {
                        Converter requestBodyConverter = this.a.requestBodyConverter(type, annotationArr, this.c);
                        this.h = true;
                        return new g.a(this.b, i, requestBodyConverter);
                    } catch (RuntimeException e) {
                        throw m.a(this.b, e, i, "Unable to create @Body converter for %s", type);
                    }
                } else {
                    throw m.a(this.b, i, "Multiple @Body method annotations found.", new Object[0]);
                }
            } else if (!(annotation instanceof Tag)) {
                return null;
            } else {
                a(i, type);
                Class<?> a17 = m.a(type);
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    g<?> gVar = this.v[i2];
                    if ((gVar instanceof g.o) && ((g.o) gVar).a.equals(a17)) {
                        throw m.a(this.b, i, "@Tag type " + a17.getName() + " is duplicate of parameter #" + (i2 + 1) + " and would always overwrite its value.", new Object[0]);
                    }
                }
                return new g.o(a17);
            }
        }

        private void a(int i, Type type) {
            if (m.d(type)) {
                throw m.a(this.b, i, "Parameter type must not include a type variable or wildcard: %s", type);
            }
        }

        private void a(int i, String str) {
            if (!y.matcher(str).matches()) {
                throw m.a(this.b, i, "@Path parameter name must match %s. Found: %s", x.pattern(), str);
            } else if (!this.u.contains(str)) {
                throw m.a(this.b, i, "URL \"%s\" does not contain \"{%s}\".", this.r, str);
            }
        }

        static Set<String> a(String str) {
            Matcher matcher = x.matcher(str);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            while (matcher.find()) {
                linkedHashSet.add(matcher.group(1));
            }
            return linkedHashSet;
        }

        private static Class<?> a(Class<?> cls) {
            return Boolean.TYPE == cls ? Boolean.class : Byte.TYPE == cls ? Byte.class : Character.TYPE == cls ? Character.class : Double.TYPE == cls ? Double.class : Float.TYPE == cls ? Float.class : Integer.TYPE == cls ? Integer.class : Long.TYPE == cls ? Long.class : Short.TYPE == cls ? Short.class : cls;
        }
    }
}
