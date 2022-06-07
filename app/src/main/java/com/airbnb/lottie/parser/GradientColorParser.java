package com.airbnb.lottie.parser;

import android.graphics.Color;
import androidx.annotation.IntRange;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GradientColorParser implements ValueParser<GradientColor> {
    private int colorPoints;

    public GradientColorParser(int i) {
        this.colorPoints = i;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.parser.ValueParser
    public GradientColor parse(JsonReader jsonReader, float f) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        if (z) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i = this.colorPoints;
        float[] fArr = new float[i];
        int[] iArr = new int[i];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.colorPoints * 4; i4++) {
            int i5 = i4 / 4;
            double floatValue = arrayList.get(i4).floatValue();
            switch (i4 % 4) {
                case 0:
                    if (i5 > 0) {
                        float f2 = (float) floatValue;
                        if (fArr[i5 - 1] >= f2) {
                            fArr[i5] = f2 + 0.01f;
                            break;
                        }
                    }
                    fArr[i5] = (float) floatValue;
                    break;
                case 1:
                    i2 = (int) (floatValue * 255.0d);
                    break;
                case 2:
                    i3 = (int) (floatValue * 255.0d);
                    break;
                case 3:
                    iArr[i5] = Color.argb(255, i2, i3, (int) (floatValue * 255.0d));
                    break;
            }
        }
        GradientColor gradientColor = new GradientColor(fArr, iArr);
        addOpacityStopsToGradientIfNeeded(gradientColor, arrayList);
        return gradientColor;
    }

    private void addOpacityStopsToGradientIfNeeded(GradientColor gradientColor, List<Float> list) {
        int i = this.colorPoints * 4;
        if (list.size() > i) {
            int size = (list.size() - i) / 2;
            double[] dArr = new double[size];
            double[] dArr2 = new double[size];
            int i2 = 0;
            while (i < list.size()) {
                if (i % 2 == 0) {
                    dArr[i2] = list.get(i).floatValue();
                } else {
                    dArr2[i2] = list.get(i).floatValue();
                    i2++;
                }
                i++;
            }
            for (int i3 = 0; i3 < gradientColor.getSize(); i3++) {
                int i4 = gradientColor.getColors()[i3];
                gradientColor.getColors()[i3] = Color.argb(getOpacityAtPosition(gradientColor.getPositions()[i3], dArr, dArr2), Color.red(i4), Color.green(i4), Color.blue(i4));
            }
        }
    }

    @IntRange(from = 0, to = 255)
    private int getOpacityAtPosition(double d, double[] dArr, double[] dArr2) {
        for (int i = 1; i < dArr.length; i++) {
            int i2 = i - 1;
            double d2 = dArr[i2];
            double d3 = dArr[i];
            if (dArr[i] >= d) {
                return (int) (MiscUtils.lerp(dArr2[i2], dArr2[i], MiscUtils.clamp((d - d2) / (d3 - d2), 0.0d, 1.0d)) * 255.0d);
            }
        }
        return (int) (dArr2[dArr2.length - 1] * 255.0d);
    }
}
