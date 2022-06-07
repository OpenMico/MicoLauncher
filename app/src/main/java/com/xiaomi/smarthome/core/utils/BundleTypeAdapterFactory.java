package com.xiaomi.smarthome.core.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BundleTypeAdapterFactory implements TypeAdapterFactory {
    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(final Gson gson, TypeToken<T> typeToken) {
        if (!Bundle.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) new TypeAdapter<Bundle>() { // from class: com.xiaomi.smarthome.core.utils.BundleTypeAdapterFactory.1
            /* renamed from: a */
            public void write(JsonWriter jsonWriter, Bundle bundle) throws IOException {
                if (bundle == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                for (String str : bundle.keySet()) {
                    jsonWriter.name(str);
                    Object obj = bundle.get(str);
                    if (obj == null) {
                        jsonWriter.nullValue();
                    } else {
                        gson.toJson(obj, obj.getClass(), jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }

            /* renamed from: a */
            public Bundle read(JsonReader jsonReader) throws IOException {
                switch (AnonymousClass2.a[jsonReader.peek().ordinal()]) {
                    case 1:
                        jsonReader.nextNull();
                        return null;
                    case 2:
                        return a(b(jsonReader));
                    default:
                        throw new IOException("expecting object: " + jsonReader.getPath());
                }
            }

            private Bundle a(List<Pair<String, Object>> list) throws IOException {
                Bundle bundle = new Bundle();
                for (Pair<String, Object> pair : list) {
                    String str = (String) pair.first;
                    Object obj = pair.second;
                    if (obj instanceof String) {
                        bundle.putString(str, (String) obj);
                    } else if (obj instanceof Integer) {
                        bundle.putInt(str, ((Integer) obj).intValue());
                    } else if (obj instanceof Long) {
                        bundle.putLong(str, ((Long) obj).longValue());
                    } else if (obj instanceof Double) {
                        bundle.putDouble(str, ((Double) obj).doubleValue());
                    } else if (obj instanceof Parcelable) {
                        bundle.putParcelable(str, (Parcelable) obj);
                    } else if (obj instanceof List) {
                        bundle.putParcelable(str, a((List) obj));
                    } else {
                        throw new IOException("Unparcelable key, value: " + str + ", " + obj);
                    }
                }
                return bundle;
            }

            private List<Pair<String, Object>> b(JsonReader jsonReader) throws IOException {
                ArrayList arrayList = new ArrayList();
                jsonReader.beginObject();
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    switch (AnonymousClass2.a[jsonReader.peek().ordinal()]) {
                        case 3:
                            arrayList.add(new Pair(jsonReader.nextName(), c(jsonReader)));
                            break;
                        case 4:
                            break;
                        default:
                            throw new IOException("expecting object: " + jsonReader.getPath());
                    }
                }
                jsonReader.endObject();
                return arrayList;
            }

            private Object c(JsonReader jsonReader) throws IOException {
                switch (AnonymousClass2.a[jsonReader.peek().ordinal()]) {
                    case 1:
                        jsonReader.nextNull();
                        return null;
                    case 2:
                        return b(jsonReader);
                    case 3:
                    case 4:
                    default:
                        throw new IOException("expecting value: " + jsonReader.getPath());
                    case 5:
                        return e(jsonReader);
                    case 6:
                        return Boolean.valueOf(jsonReader.nextBoolean());
                    case 7:
                        return d(jsonReader);
                    case 8:
                        return jsonReader.nextString();
                }
            }

            private Object d(JsonReader jsonReader) throws IOException {
                double nextDouble = jsonReader.nextDouble();
                if (nextDouble - Math.ceil(nextDouble) != 0.0d) {
                    return Double.valueOf(nextDouble);
                }
                long j = (long) nextDouble;
                if (j < -2147483648L || j > 2147483647L) {
                    return Long.valueOf(j);
                }
                return Integer.valueOf((int) j);
            }

            private List e(JsonReader jsonReader) throws IOException {
                ArrayList arrayList = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.peek() != JsonToken.END_ARRAY) {
                    arrayList.add(c(jsonReader));
                }
                jsonReader.endArray();
                return arrayList;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.smarthome.core.utils.BundleTypeAdapterFactory$2  reason: invalid class name */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[JsonToken.values().length];

        static {
            try {
                a[JsonToken.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[JsonToken.NAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[JsonToken.END_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[JsonToken.BEGIN_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[JsonToken.BOOLEAN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[JsonToken.NUMBER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[JsonToken.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}
