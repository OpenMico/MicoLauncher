package com.xiaomi.smarthome.core.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/* loaded from: classes4.dex */
public class GsonTypeAdapterFactory implements TypeAdapterFactory {
    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, typeToken);
        return new TypeAdapter<T>() { // from class: com.xiaomi.smarthome.core.utils.GsonTypeAdapterFactory.1
            @Override // com.google.gson.TypeAdapter
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                delegateAdapter.write(jsonWriter, t);
            }

            /* JADX WARN: Type inference failed for: r2v3, types: [T, java.lang.Object] */
            @Override // com.google.gson.TypeAdapter
            public T read(JsonReader jsonReader) throws IOException {
                try {
                    return delegateAdapter.read(jsonReader);
                } catch (Throwable unused) {
                    a(jsonReader);
                    return null;
                }
            }

            private void a(JsonReader jsonReader) throws IOException {
                if (jsonReader.hasNext()) {
                    JsonToken peek = jsonReader.peek();
                    if (peek == JsonToken.STRING) {
                        jsonReader.nextString();
                    } else if (peek == JsonToken.BEGIN_ARRAY) {
                        jsonReader.beginArray();
                        a(jsonReader);
                        jsonReader.endArray();
                    } else if (peek == JsonToken.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        a(jsonReader);
                        jsonReader.endObject();
                    } else if (peek == JsonToken.END_ARRAY) {
                        jsonReader.endArray();
                    } else if (peek == JsonToken.END_OBJECT) {
                        jsonReader.endObject();
                    } else if (peek == JsonToken.NUMBER) {
                        jsonReader.nextString();
                    } else if (peek == JsonToken.BOOLEAN) {
                        jsonReader.nextBoolean();
                    } else if (peek == JsonToken.NAME) {
                        jsonReader.nextName();
                        a(jsonReader);
                    } else if (peek == JsonToken.NULL) {
                        jsonReader.nextNull();
                    }
                }
            }
        };
    }
}
