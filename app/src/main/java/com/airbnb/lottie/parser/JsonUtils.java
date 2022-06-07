package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import androidx.annotation.ColorInt;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class JsonUtils {
    private static final JsonReader.Options POINT_NAMES = JsonReader.Options.of("x", "y");

    private JsonUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public static int jsonToColor(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        int nextDouble = (int) (jsonReader.nextDouble() * 255.0d);
        int nextDouble2 = (int) (jsonReader.nextDouble() * 255.0d);
        int nextDouble3 = (int) (jsonReader.nextDouble() * 255.0d);
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return Color.argb(255, nextDouble, nextDouble2, nextDouble3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<PointF> jsonToPoints(JsonReader jsonReader, float f) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
            arrayList.add(jsonToPoint(jsonReader, f));
            jsonReader.endArray();
        }
        jsonReader.endArray();
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PointF jsonToPoint(JsonReader jsonReader, float f) throws IOException {
        switch (jsonReader.peek()) {
            case NUMBER:
                return jsonNumbersToPoint(jsonReader, f);
            case BEGIN_ARRAY:
                return jsonArrayToPoint(jsonReader, f);
            case BEGIN_OBJECT:
                return jsonObjectToPoint(jsonReader, f);
            default:
                throw new IllegalArgumentException("Unknown point starts with " + jsonReader.peek());
        }
    }

    private static PointF jsonNumbersToPoint(JsonReader jsonReader, float f) throws IOException {
        float nextDouble = (float) jsonReader.nextDouble();
        float nextDouble2 = (float) jsonReader.nextDouble();
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        return new PointF(nextDouble * f, nextDouble2 * f);
    }

    private static PointF jsonArrayToPoint(JsonReader jsonReader, float f) throws IOException {
        jsonReader.beginArray();
        float nextDouble = (float) jsonReader.nextDouble();
        float nextDouble2 = (float) jsonReader.nextDouble();
        while (jsonReader.peek() != JsonReader.Token.END_ARRAY) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return new PointF(nextDouble * f, nextDouble2 * f);
    }

    private static PointF jsonObjectToPoint(JsonReader jsonReader, float f) throws IOException {
        jsonReader.beginObject();
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(POINT_NAMES)) {
                case 0:
                    f2 = valueFromObject(jsonReader);
                    break;
                case 1:
                    f3 = valueFromObject(jsonReader);
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return new PointF(f2 * f, f3 * f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static float valueFromObject(JsonReader jsonReader) throws IOException {
        JsonReader.Token peek = jsonReader.peek();
        switch (peek) {
            case NUMBER:
                return (float) jsonReader.nextDouble();
            case BEGIN_ARRAY:
                jsonReader.beginArray();
                float nextDouble = (float) jsonReader.nextDouble();
                while (jsonReader.hasNext()) {
                    jsonReader.skipValue();
                }
                jsonReader.endArray();
                return nextDouble;
            default:
                throw new IllegalArgumentException("Unknown value for token of type " + peek);
        }
    }
}
