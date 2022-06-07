package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ShapeGroupParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("nm", "hd", "it");

    private ShapeGroupParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ShapeGroup parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList = new ArrayList();
        String str = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    z = jsonReader.nextBoolean();
                    break;
                case 2:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonReader, lottieComposition);
                        if (parse != null) {
                            arrayList.add(parse);
                        }
                    }
                    jsonReader.endArray();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        return new ShapeGroup(str, arrayList, z);
    }
}
