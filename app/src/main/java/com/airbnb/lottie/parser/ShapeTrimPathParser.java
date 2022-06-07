package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ShapeTrimPathParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of(ai.az, "e", "o", "nm", MessageElement.XPATH_PREFIX, "hd");

    private ShapeTrimPathParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ShapeTrimPath parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String str = null;
        boolean z = false;
        ShapeTrimPath.Type type = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 1:
                    animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 2:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 3:
                    str = jsonReader.nextString();
                    break;
                case 4:
                    type = ShapeTrimPath.Type.forId(jsonReader.nextInt());
                    break;
                case 5:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        return new ShapeTrimPath(str, type, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, z);
    }
}
