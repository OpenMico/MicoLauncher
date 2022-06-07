package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: classes.dex */
public class TextKeyframeAnimation extends KeyframeAnimation<DocumentData> {
    public TextKeyframeAnimation(List<Keyframe<DocumentData>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    DocumentData getValue(Keyframe<DocumentData> keyframe, float f) {
        if (f != 1.0f || keyframe.endValue == null) {
            return keyframe.startValue;
        }
        return keyframe.endValue;
    }
}
