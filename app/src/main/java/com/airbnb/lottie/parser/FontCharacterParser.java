package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
class FontCharacterParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("ch", "size", "w", "style", "fFamily", "data");
    private static final JsonReader.Options DATA_NAMES = JsonReader.Options.of("shapes");

    private FontCharacterParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static FontCharacter parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginObject();
        String str = null;
        double d = 0.0d;
        String str2 = null;
        double d2 = 0.0d;
        char c = 0;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    c = jsonReader.nextString().charAt(0);
                    break;
                case 1:
                    d = jsonReader.nextDouble();
                    break;
                case 2:
                    d2 = jsonReader.nextDouble();
                    break;
                case 3:
                    str = jsonReader.nextString();
                    break;
                case 4:
                    str2 = jsonReader.nextString();
                    break;
                case 5:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.selectName(DATA_NAMES) != 0) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                arrayList.add((ShapeGroup) ContentModelParser.parse(jsonReader, lottieComposition));
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return new FontCharacter(arrayList, c, d, d2, str, str2);
    }
}
