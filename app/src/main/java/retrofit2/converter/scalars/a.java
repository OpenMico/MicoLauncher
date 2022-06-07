package retrofit2.converter.scalars;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/* compiled from: ScalarRequestBodyConverter.java */
/* loaded from: classes6.dex */
final class a<T> implements Converter<T, RequestBody> {
    static final a<Object> a = new a<>();
    private static final MediaType b = MediaType.get("text/plain; charset=UTF-8");

    private a() {
    }

    /* renamed from: a */
    public RequestBody convert(T t) throws IOException {
        return RequestBody.create(b, String.valueOf(t));
    }
}
