package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* compiled from: GsonResponseBodyConverter.java */
/* loaded from: classes6.dex */
final class b<T> implements Converter<ResponseBody, T> {
    private final Gson a;
    private final TypeAdapter<T> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Gson gson, TypeAdapter<T> typeAdapter) {
        this.a = gson;
        this.b = typeAdapter;
    }

    /* renamed from: a */
    public T convert(ResponseBody responseBody) throws IOException {
        JsonReader newJsonReader = this.a.newJsonReader(responseBody.charStream());
        try {
            T read = this.b.read(newJsonReader);
            if (newJsonReader.peek() == JsonToken.END_DOCUMENT) {
                return read;
            }
            throw new JsonIOException("JSON document was not fully consumed.");
        } finally {
            responseBody.close();
        }
    }
}
