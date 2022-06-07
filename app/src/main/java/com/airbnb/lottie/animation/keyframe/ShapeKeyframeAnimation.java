package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: classes.dex */
public class ShapeKeyframeAnimation extends BaseKeyframeAnimation<ShapeData, Path> {
    private final ShapeData tempShapeData = new ShapeData();
    private final Path tempPath = new Path();

    public ShapeKeyframeAnimation(List<Keyframe<ShapeData>> list) {
        super(list);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public Path getValue(Keyframe<ShapeData> keyframe, float f) {
        this.tempShapeData.interpolateBetween(keyframe.startValue, keyframe.endValue, f);
        MiscUtils.getPathFromData(this.tempShapeData, this.tempPath);
        return this.tempPath;
    }
}
