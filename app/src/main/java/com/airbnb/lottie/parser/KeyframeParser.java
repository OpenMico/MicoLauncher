package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import java.lang.ref.WeakReference;
import org.seamless.xhtml.XHTMLElement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class KeyframeParser {
    private static final float MAX_CP_VALUE = 100.0f;
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static JsonReader.Options NAMES = JsonReader.Options.of(ai.aF, ai.az, "e", "o", ai.aA, XHTMLElement.XPATH_PREFIX, "to", "ti");
    static JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.of("x", "y");

    KeyframeParser() {
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    @Nullable
    private static WeakReference<Interpolator> getInterpolator(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = pathInterpolatorCache().get(i);
        }
        return weakReference;
    }

    private static void putInterpolator(int i, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i, weakReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z, boolean z2) throws IOException {
        if (z && z2) {
            return parseMultiDimensionalKeyframe(lottieComposition, jsonReader, f, valueParser);
        }
        if (z) {
            return parseKeyframe(lottieComposition, jsonReader, f, valueParser);
        }
        return parseStaticValue(jsonReader, f, valueParser);
    }

    private static <T> Keyframe<T> parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        T t;
        jsonReader.beginObject();
        PointF pointF = null;
        float f2 = 0.0f;
        T t2 = null;
        T t3 = null;
        PointF pointF2 = null;
        PointF pointF3 = null;
        boolean z = false;
        PointF pointF4 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    f2 = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    t3 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    t2 = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    pointF4 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 4:
                    pointF = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 5:
                    if (jsonReader.nextInt() != 1) {
                        z = false;
                        break;
                    } else {
                        z = true;
                        break;
                    }
                case 6:
                    pointF2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolator = LINEAR_INTERPOLATOR;
            t = t3;
        } else if (pointF4 == null || pointF == null) {
            interpolator = LINEAR_INTERPOLATOR;
            t = t2;
        } else {
            interpolator = interpolatorFor(pointF4, pointF);
            t = t2;
        }
        Keyframe<T> keyframe = new Keyframe<>(lottieComposition, t3, t, interpolator, f2, null);
        keyframe.pathCp1 = pointF2;
        keyframe.pathCp2 = pointF3;
        return keyframe;
    }

    private static <T> Keyframe<T> parseMultiDimensionalKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        Interpolator interpolator2;
        T t;
        Interpolator interpolator3;
        PointF pointF;
        Keyframe<T> keyframe;
        jsonReader.beginObject();
        PointF pointF2 = null;
        boolean z = false;
        PointF pointF3 = null;
        PointF pointF4 = null;
        PointF pointF5 = null;
        T t2 = null;
        PointF pointF6 = null;
        PointF pointF7 = null;
        PointF pointF8 = null;
        float f2 = 0.0f;
        PointF pointF9 = null;
        T t3 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    f2 = (float) jsonReader.nextDouble();
                    pointF2 = pointF2;
                    break;
                case 1:
                    t2 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    t3 = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    float f3 = f2;
                    if (jsonReader.peek() != JsonReader.Token.BEGIN_OBJECT) {
                        pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                        pointF9 = pointF9;
                        pointF2 = pointF2;
                        f2 = f3;
                        break;
                    } else {
                        jsonReader.beginObject();
                        float f4 = 0.0f;
                        float f5 = 0.0f;
                        float f6 = 0.0f;
                        float f7 = 0.0f;
                        while (jsonReader.hasNext()) {
                            switch (jsonReader.selectName(INTERPOLATOR_NAMES)) {
                                case 0:
                                    if (jsonReader.peek() != JsonReader.Token.NUMBER) {
                                        jsonReader.beginArray();
                                        f5 = (float) jsonReader.nextDouble();
                                        f6 = jsonReader.peek() == JsonReader.Token.NUMBER ? (float) jsonReader.nextDouble() : f5;
                                        jsonReader.endArray();
                                        f3 = f3;
                                        break;
                                    } else {
                                        f6 = (float) jsonReader.nextDouble();
                                        f5 = f6;
                                        f3 = f3;
                                        break;
                                    }
                                case 1:
                                    if (jsonReader.peek() != JsonReader.Token.NUMBER) {
                                        jsonReader.beginArray();
                                        f4 = (float) jsonReader.nextDouble();
                                        f7 = jsonReader.peek() == JsonReader.Token.NUMBER ? (float) jsonReader.nextDouble() : f4;
                                        jsonReader.endArray();
                                        f3 = f3;
                                        break;
                                    } else {
                                        f7 = (float) jsonReader.nextDouble();
                                        f4 = f7;
                                        f3 = f3;
                                        break;
                                    }
                                default:
                                    jsonReader.skipValue();
                                    break;
                            }
                        }
                        PointF pointF10 = new PointF(f5, f4);
                        PointF pointF11 = new PointF(f6, f7);
                        jsonReader.endObject();
                        pointF5 = pointF10;
                        pointF9 = pointF9;
                        f2 = f3;
                        pointF6 = pointF11;
                        pointF2 = pointF2;
                        break;
                    }
                case 4:
                    if (jsonReader.peek() != JsonReader.Token.BEGIN_OBJECT) {
                        pointF4 = JsonUtils.jsonToPoint(jsonReader, f);
                        pointF9 = pointF9;
                        break;
                    } else {
                        jsonReader.beginObject();
                        float f8 = 0.0f;
                        float f9 = 0.0f;
                        float f10 = 0.0f;
                        float f11 = 0.0f;
                        while (jsonReader.hasNext()) {
                            switch (jsonReader.selectName(INTERPOLATOR_NAMES)) {
                                case 0:
                                    if (jsonReader.peek() != JsonReader.Token.NUMBER) {
                                        jsonReader.beginArray();
                                        float nextDouble = (float) jsonReader.nextDouble();
                                        if (jsonReader.peek() == JsonReader.Token.NUMBER) {
                                            f8 = nextDouble;
                                            f10 = (float) jsonReader.nextDouble();
                                        } else {
                                            f8 = nextDouble;
                                            f10 = f8;
                                        }
                                        jsonReader.endArray();
                                        f2 = f2;
                                        pointF9 = pointF9;
                                        pointF2 = pointF2;
                                        break;
                                    } else {
                                        f10 = (float) jsonReader.nextDouble();
                                        f8 = f10;
                                        f2 = f2;
                                        pointF9 = pointF9;
                                        pointF2 = pointF2;
                                        break;
                                    }
                                case 1:
                                    if (jsonReader.peek() != JsonReader.Token.NUMBER) {
                                        jsonReader.beginArray();
                                        f9 = (float) jsonReader.nextDouble();
                                        f11 = jsonReader.peek() == JsonReader.Token.NUMBER ? (float) jsonReader.nextDouble() : f9;
                                        jsonReader.endArray();
                                        f2 = f2;
                                        pointF9 = pointF9;
                                        break;
                                    } else {
                                        f11 = (float) jsonReader.nextDouble();
                                        f9 = f11;
                                        f2 = f2;
                                        pointF9 = pointF9;
                                        break;
                                    }
                                default:
                                    jsonReader.skipValue();
                                    pointF9 = pointF9;
                                    break;
                            }
                        }
                        PointF pointF12 = new PointF(f8, f9);
                        PointF pointF13 = new PointF(f10, f11);
                        jsonReader.endObject();
                        pointF7 = pointF12;
                        pointF8 = pointF13;
                        pointF9 = pointF9;
                        pointF2 = pointF2;
                        break;
                    }
                case 5:
                    z = true;
                    if (jsonReader.nextInt() != 1) {
                        z = false;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    pointF9 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointF2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolator3 = LINEAR_INTERPOLATOR;
            t = t2;
            interpolator2 = null;
            interpolator = null;
        } else if (pointF3 != null && pointF4 != null) {
            interpolator3 = interpolatorFor(pointF3, pointF4);
            t = t3;
            interpolator2 = null;
            interpolator = null;
        } else if (pointF5 == null || pointF6 == null || pointF7 == null || pointF8 == null) {
            interpolator3 = LINEAR_INTERPOLATOR;
            t = t3;
            interpolator2 = null;
            interpolator = null;
        } else {
            Interpolator interpolatorFor = interpolatorFor(pointF5, pointF7);
            interpolator = interpolatorFor(pointF6, pointF8);
            interpolator2 = interpolatorFor;
            t = t3;
            interpolator3 = null;
        }
        if (interpolator2 == null || interpolator == null) {
            pointF = pointF9;
            keyframe = new Keyframe<>(lottieComposition, t2, t, interpolator3, f2, null);
        } else {
            pointF = pointF9;
            keyframe = new Keyframe<>(lottieComposition, t2, t, interpolator2, interpolator, f2, null);
        }
        keyframe.pathCp1 = pointF;
        keyframe.pathCp2 = pointF2;
        return keyframe;
    }

    private static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, (float) MAX_CP_VALUE);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        pointF2.y = MiscUtils.clamp(pointF2.y, -100.0f, (float) MAX_CP_VALUE);
        int hashFor = Utils.hashFor(pointF.x, pointF.y, pointF2.x, pointF2.y);
        WeakReference<Interpolator> interpolator = getInterpolator(hashFor);
        Interpolator interpolator2 = interpolator != null ? interpolator.get() : null;
        if (interpolator == null || interpolator2 == null) {
            try {
                interpolator2 = PathInterpolatorCompat.create(pointF.x, pointF.y, pointF2.x, pointF2.y);
            } catch (IllegalArgumentException e) {
                if ("The Path cannot loop back on itself.".equals(e.getMessage())) {
                    interpolator2 = PathInterpolatorCompat.create(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y);
                } else {
                    interpolator2 = new LinearInterpolator();
                }
            }
            try {
                putInterpolator(hashFor, new WeakReference(interpolator2));
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        return interpolator2;
    }

    private static <T> Keyframe<T> parseStaticValue(JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.parse(jsonReader, f));
    }
}
