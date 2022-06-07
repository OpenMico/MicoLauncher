package com.xiaomi.mico.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/* loaded from: classes3.dex */
public class Gsons {
    private static final Gson a = new Gson();
    private static final TypeAdapter<Boolean> b = new a<Boolean>() { // from class: com.xiaomi.mico.common.Gsons.1
        /* renamed from: a */
        public Boolean read(JsonReader jsonReader) throws IOException {
            JsonToken peek = jsonReader.peek();
            boolean z = true;
            switch (AnonymousClass2.a[peek.ordinal()]) {
                case 1:
                    return Boolean.valueOf(jsonReader.nextBoolean());
                case 2:
                    jsonReader.nextNull();
                    return null;
                case 3:
                    if (jsonReader.nextInt() == 0) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                case 4:
                    String nextString = jsonReader.nextString();
                    if (ContainerUtil.isEmpty(nextString) || "0".equals(nextString)) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };
    private static final Gson c = new GsonBuilder().registerTypeAdapter(Boolean.TYPE, b).create();

    /* renamed from: com.xiaomi.mico.common.Gsons$2 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[JsonToken.values().length];

        static {
            try {
                a[JsonToken.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[JsonToken.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[JsonToken.NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[JsonToken.STRING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static Gson getGson() {
        return a;
    }

    public static Gson getIntAsBoolGson() {
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static abstract class a<T> extends TypeAdapter<T> {
        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t) throws IOException {
        }

        private a() {
        }
    }
}
