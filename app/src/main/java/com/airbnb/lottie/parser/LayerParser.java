package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.seamless.xhtml.XHTMLElement;

/* loaded from: classes.dex */
public class LayerParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", ai.aF, "ef", "sr", "st", "w", XHTMLElement.XPATH_PREFIX, "ip", "op", "tm", "cl", "hd");
    private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", ai.at);
    private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("nm");

    private LayerParser() {
    }

    public static Layer parse(LottieComposition lottieComposition) {
        Rect bounds = lottieComposition.getBounds();
        return new Layer(Collections.emptyList(), lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false);
    }

    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList;
        float f;
        String str = "UNSET";
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        jsonReader.beginObject();
        Layer.LayerType layerType = null;
        long j = 0;
        long j2 = -1;
        Layer.MatteType matteType2 = matteType;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        String str2 = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 1.0f;
        String str3 = null;
        float f5 = 0.0f;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    j = jsonReader.nextInt();
                    break;
                case 2:
                    str2 = jsonReader.nextString();
                    break;
                case 3:
                    int nextInt = jsonReader.nextInt();
                    if (nextInt >= Layer.LayerType.UNKNOWN.ordinal()) {
                        layerType = Layer.LayerType.UNKNOWN;
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[nextInt];
                        break;
                    }
                case 4:
                    j2 = jsonReader.nextInt();
                    break;
                case 5:
                    i = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 6:
                    i2 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 7:
                    i3 = Color.parseColor(jsonReader.nextString());
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    break;
                case 9:
                    int nextInt2 = jsonReader.nextInt();
                    if (nextInt2 < Layer.MatteType.values().length) {
                        matteType2 = Layer.MatteType.values()[nextInt2];
                        switch (matteType2) {
                            case LUMA:
                                lottieComposition.addWarning("Unsupported matte type: Luma");
                                break;
                            case LUMA_INVERTED:
                                lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                                break;
                        }
                        lottieComposition.incrementMatteOrMaskCount(1);
                        break;
                    } else {
                        lottieComposition.addWarning("Unsupported matte type: " + nextInt2);
                        break;
                    }
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList2.add(MaskParser.parse(jsonReader, lottieComposition));
                    }
                    lottieComposition.incrementMatteOrMaskCount(arrayList2.size());
                    jsonReader.endArray();
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonReader, lottieComposition);
                        if (parse != null) {
                            arrayList3.add(parse);
                        }
                    }
                    jsonReader.endArray();
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        switch (jsonReader.selectName(TEXT_NAMES)) {
                            case 0:
                                animatableTextFrame = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                                break;
                            case 1:
                                jsonReader.beginArray();
                                if (jsonReader.hasNext()) {
                                    animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                                }
                                while (jsonReader.hasNext()) {
                                    jsonReader.skipValue();
                                }
                                jsonReader.endArray();
                                break;
                            default:
                                jsonReader.skipName();
                                jsonReader.skipValue();
                                break;
                        }
                    }
                    jsonReader.endObject();
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList4 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader.selectName(EFFECTS_NAMES) != 0) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                arrayList4.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList4);
                    break;
                case 14:
                    f4 = (float) jsonReader.nextDouble();
                    break;
                case 15:
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 16:
                    i4 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 17:
                    i5 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 18:
                    f5 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    f2 = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 21:
                    str3 = jsonReader.nextString();
                    break;
                case 22:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        float f6 = f5 / f4;
        float f7 = f2 / f4;
        ArrayList arrayList5 = new ArrayList();
        if (f6 > 0.0f) {
            arrayList = arrayList5;
            arrayList.add(new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(f6)));
            f = 0.0f;
        } else {
            arrayList = arrayList5;
            f = 0.0f;
        }
        if (f7 <= f) {
            f7 = lottieComposition.getEndFrame();
        }
        arrayList.add(new Keyframe(lottieComposition, Float.valueOf(1.0f), Float.valueOf(1.0f), null, f6, Float.valueOf(f7)));
        arrayList.add(new Keyframe(lottieComposition, Float.valueOf(0.0f), Float.valueOf(0.0f), null, f7, Float.valueOf(Float.MAX_VALUE)));
        if (str.endsWith(".ai") || "ai".equals(str3)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList3, lottieComposition, str, j, layerType, j2, str2, arrayList2, animatableTransform, i, i2, i3, f4, f3, i4, i5, animatableTextFrame, animatableTextProperties, arrayList, matteType2, animatableFloatValue, z);
    }
}
