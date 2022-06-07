package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ModifierContent;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.BaseLayer;

/* loaded from: classes.dex */
public class AnimatableTransform implements ModifierContent, ContentModel {
    @Nullable
    private final AnimatablePathValue anchorPoint;
    @Nullable
    private final AnimatableFloatValue endOpacity;
    @Nullable
    private final AnimatableIntegerValue opacity;
    @Nullable
    private final AnimatableValue<PointF, PointF> position;
    @Nullable
    private final AnimatableFloatValue rotation;
    @Nullable
    private final AnimatableScaleValue scale;
    @Nullable
    private final AnimatableFloatValue skew;
    @Nullable
    private final AnimatableFloatValue skewAngle;
    @Nullable
    private final AnimatableFloatValue startOpacity;

    @Override // com.airbnb.lottie.model.content.ContentModel
    @Nullable
    public Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return null;
    }

    public AnimatableTransform() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public AnimatableTransform(@Nullable AnimatablePathValue animatablePathValue, @Nullable AnimatableValue<PointF, PointF> animatableValue, @Nullable AnimatableScaleValue animatableScaleValue, @Nullable AnimatableFloatValue animatableFloatValue, @Nullable AnimatableIntegerValue animatableIntegerValue, @Nullable AnimatableFloatValue animatableFloatValue2, @Nullable AnimatableFloatValue animatableFloatValue3, @Nullable AnimatableFloatValue animatableFloatValue4, @Nullable AnimatableFloatValue animatableFloatValue5) {
        this.anchorPoint = animatablePathValue;
        this.position = animatableValue;
        this.scale = animatableScaleValue;
        this.rotation = animatableFloatValue;
        this.opacity = animatableIntegerValue;
        this.startOpacity = animatableFloatValue2;
        this.endOpacity = animatableFloatValue3;
        this.skew = animatableFloatValue4;
        this.skewAngle = animatableFloatValue5;
    }

    @Nullable
    public AnimatablePathValue getAnchorPoint() {
        return this.anchorPoint;
    }

    @Nullable
    public AnimatableValue<PointF, PointF> getPosition() {
        return this.position;
    }

    @Nullable
    public AnimatableScaleValue getScale() {
        return this.scale;
    }

    @Nullable
    public AnimatableFloatValue getRotation() {
        return this.rotation;
    }

    @Nullable
    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    @Nullable
    public AnimatableFloatValue getStartOpacity() {
        return this.startOpacity;
    }

    @Nullable
    public AnimatableFloatValue getEndOpacity() {
        return this.endOpacity;
    }

    @Nullable
    public AnimatableFloatValue getSkew() {
        return this.skew;
    }

    @Nullable
    public AnimatableFloatValue getSkewAngle() {
        return this.skewAngle;
    }

    public TransformKeyframeAnimation createAnimation() {
        return new TransformKeyframeAnimation(this);
    }
}
