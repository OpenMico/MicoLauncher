package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.umeng.analytics.pro.ai;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CircleShapeParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("nm", ai.av, ai.az, "hd", "d");

    private CircleShapeParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CircleShape parse(JsonReader jsonReader, LottieComposition lottieComposition, int i) throws IOException {
        boolean z = i == 3;
        String str = null;
        boolean z2 = false;
        AnimatableValue<PointF, PointF> animatableValue = null;
        AnimatablePointValue animatablePointValue = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    animatableValue = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
                    break;
                case 2:
                    animatablePointValue = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    break;
                case 3:
                    z2 = jsonReader.nextBoolean();
                    break;
                case 4:
                    if (jsonReader.nextInt() != 3) {
                        z = false;
                        break;
                    } else {
                        z = true;
                        break;
                    }
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        return new CircleShape(str, animatableValue, animatablePointValue, z, z2);
    }
}
