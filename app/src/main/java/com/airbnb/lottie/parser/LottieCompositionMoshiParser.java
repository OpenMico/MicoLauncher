package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.umeng.analytics.pro.ai;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.seamless.xhtml.XHTMLElement;

/* loaded from: classes.dex */
public class LottieCompositionMoshiParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("w", XHTMLElement.XPATH_PREFIX, "ip", "op", "fr", ai.aC, RtspHeaders.Values.LAYERS, "assets", "fonts", "chars", "markers");
    static JsonReader.Options ASSETS_NAMES = JsonReader.Options.of("id", RtspHeaders.Values.LAYERS, "w", XHTMLElement.XPATH_PREFIX, ai.av, ai.aE);
    private static final JsonReader.Options FONT_NAMES = JsonReader.Options.of("list");
    private static final JsonReader.Options MARKER_NAMES = JsonReader.Options.of("cm", "tm", "dr");

    public static LottieComposition parse(JsonReader jsonReader) throws IOException {
        JsonReader jsonReader2 = jsonReader;
        float dpScale = Utils.dpScale();
        LongSparseArray<Layer> longSparseArray = new LongSparseArray<>();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        SparseArrayCompat<FontCharacter> sparseArrayCompat = new SparseArrayCompat<>();
        LottieComposition lottieComposition = new LottieComposition();
        jsonReader.beginObject();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = 0;
        int i2 = 0;
        while (jsonReader.hasNext()) {
            switch (jsonReader2.selectName(NAMES)) {
                case 0:
                    i = jsonReader.nextInt();
                    jsonReader2 = jsonReader;
                    continue;
                case 1:
                    i2 = jsonReader.nextInt();
                    jsonReader2 = jsonReader;
                    continue;
                case 2:
                    f = (float) jsonReader.nextDouble();
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    jsonReader2 = jsonReader;
                    continue;
                case 3:
                    f2 = ((float) jsonReader.nextDouble()) - 0.01f;
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    jsonReader2 = jsonReader;
                    continue;
                case 4:
                    f3 = (float) jsonReader.nextDouble();
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    jsonReader2 = jsonReader;
                    continue;
                case 5:
                    String[] split = jsonReader.nextString().split("\\.");
                    if (Utils.isAtLeastVersion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 4, 4, 0)) {
                        hashMap3 = hashMap3;
                        arrayList2 = arrayList2;
                        break;
                    } else {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                        hashMap3 = hashMap3;
                        arrayList2 = arrayList2;
                        break;
                    }
                case 6:
                    parseLayers(jsonReader2, lottieComposition, arrayList, longSparseArray);
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    break;
                case 7:
                    parseAssets(jsonReader2, lottieComposition, hashMap, hashMap2);
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    break;
                case 8:
                    parseFonts(jsonReader2, hashMap3);
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    break;
                case 9:
                    parseChars(jsonReader2, lottieComposition, sparseArrayCompat);
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    break;
                case 10:
                    parseMarkers(jsonReader2, lottieComposition, arrayList2);
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    break;
                default:
                    hashMap3 = hashMap3;
                    arrayList2 = arrayList2;
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
            jsonReader2 = jsonReader;
        }
        lottieComposition.init(new Rect(0, 0, (int) (i * dpScale), (int) (i2 * dpScale)), f, f2, f3, arrayList, longSparseArray, hashMap, hashMap2, sparseArrayCompat, hashMap3, arrayList2);
        return lottieComposition;
    }

    private static void parseLayers(JsonReader jsonReader, LottieComposition lottieComposition, List<Layer> list, LongSparseArray<Layer> longSparseArray) throws IOException {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer parse = LayerParser.parse(jsonReader, lottieComposition);
            if (parse.getLayerType() == Layer.LayerType.IMAGE) {
                i++;
            }
            list.add(parse);
            longSparseArray.put(parse.getId(), parse);
            if (i > 4) {
                Logger.warning("You have " + i + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        jsonReader.endArray();
    }

    private static void parseAssets(JsonReader jsonReader, LottieComposition lottieComposition, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            ArrayList arrayList = new ArrayList();
            LongSparseArray longSparseArray = new LongSparseArray();
            jsonReader.beginObject();
            int i = 0;
            String str = null;
            int i2 = 0;
            String str2 = null;
            String str3 = null;
            while (jsonReader.hasNext()) {
                switch (jsonReader.selectName(ASSETS_NAMES)) {
                    case 0:
                        str = jsonReader.nextString();
                        break;
                    case 1:
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            Layer parse = LayerParser.parse(jsonReader, lottieComposition);
                            longSparseArray.put(parse.getId(), parse);
                            arrayList.add(parse);
                        }
                        jsonReader.endArray();
                        break;
                    case 2:
                        i = jsonReader.nextInt();
                        break;
                    case 3:
                        i2 = jsonReader.nextInt();
                        break;
                    case 4:
                        str2 = jsonReader.nextString();
                        break;
                    case 5:
                        str3 = jsonReader.nextString();
                        break;
                    default:
                        jsonReader.skipName();
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            if (str2 != null) {
                LottieImageAsset lottieImageAsset = new LottieImageAsset(i, i2, str, str2, str3);
                map2.put(lottieImageAsset.getId(), lottieImageAsset);
            } else {
                map.put(str, arrayList);
            }
        }
        jsonReader.endArray();
    }

    private static void parseFonts(JsonReader jsonReader, Map<String, Font> map) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(FONT_NAMES) != 0) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Font parse = FontParser.parse(jsonReader);
                    map.put(parse.getName(), parse);
                }
                jsonReader.endArray();
            }
        }
        jsonReader.endObject();
    }

    private static void parseChars(JsonReader jsonReader, LottieComposition lottieComposition, SparseArrayCompat<FontCharacter> sparseArrayCompat) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            FontCharacter parse = FontCharacterParser.parse(jsonReader, lottieComposition);
            sparseArrayCompat.put(parse.hashCode(), parse);
        }
        jsonReader.endArray();
    }

    private static void parseMarkers(JsonReader jsonReader, LottieComposition lottieComposition, List<Marker> list) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            String str = null;
            jsonReader.beginObject();
            float f = 0.0f;
            float f2 = 0.0f;
            while (jsonReader.hasNext()) {
                switch (jsonReader.selectName(MARKER_NAMES)) {
                    case 0:
                        str = jsonReader.nextString();
                        break;
                    case 1:
                        f = (float) jsonReader.nextDouble();
                        break;
                    case 2:
                        f2 = (float) jsonReader.nextDouble();
                        break;
                    default:
                        jsonReader.skipName();
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            list.add(new Marker(str, f, f2));
        }
        jsonReader.endArray();
    }
}
