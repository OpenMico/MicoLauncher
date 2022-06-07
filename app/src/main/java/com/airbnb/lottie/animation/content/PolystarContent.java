package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class PolystarContent implements KeyPathElementContent, PathContent, BaseKeyframeAnimation.AnimationListener {
    private static final float POLYGON_MAGIC_NUMBER = 0.25f;
    private static final float POLYSTAR_MAGIC_NUMBER = 0.47829f;
    private final boolean hidden;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
    private boolean isPathValid;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
    private final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
    private final BaseKeyframeAnimation<?, Float> pointsAnimation;
    private final BaseKeyframeAnimation<?, PointF> positionAnimation;
    private final BaseKeyframeAnimation<?, Float> rotationAnimation;
    private final PolystarShape.Type type;
    private final Path path = new Path();
    private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        this.type = polystarShape.getType();
        this.hidden = polystarShape.isHidden();
        this.pointsAnimation = polystarShape.getPoints().createAnimation();
        this.positionAnimation = polystarShape.getPosition().createAnimation();
        this.rotationAnimation = polystarShape.getRotation().createAnimation();
        this.outerRadiusAnimation = polystarShape.getOuterRadius().createAnimation();
        this.outerRoundednessAnimation = polystarShape.getOuterRoundedness().createAnimation();
        if (this.type == PolystarShape.Type.STAR) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(this.pointsAnimation);
        baseLayer.addAnimation(this.positionAnimation);
        baseLayer.addAnimation(this.rotationAnimation);
        baseLayer.addAnimation(this.outerRadiusAnimation);
        baseLayer.addAnimation(this.outerRoundednessAnimation);
        if (this.type == PolystarShape.Type.STAR) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        this.pointsAnimation.addUpdateListener(this);
        this.positionAnimation.addUpdateListener(this);
        this.rotationAnimation.addUpdateListener(this);
        this.outerRadiusAnimation.addUpdateListener(this);
        this.outerRoundednessAnimation.addUpdateListener(this);
        if (this.type == PolystarShape.Type.STAR) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    private void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        switch (this.type) {
            case STAR:
                createStarPath();
                break;
            case POLYGON:
                createPolygonPath();
                break;
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    private void createStarPath() {
        double d;
        int i;
        float f;
        float f2;
        float f3;
        double d2;
        float f4;
        float f5;
        float floatValue = this.pointsAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d3 = floatValue;
        float f6 = (float) (6.283185307179586d / d3);
        float f7 = f6 / 2.0f;
        float f8 = floatValue - ((int) floatValue);
        int i2 = (f8 > 0.0f ? 1 : (f8 == 0.0f ? 0 : -1));
        if (i2 != 0) {
            radians += (1.0f - f8) * f7;
        }
        float floatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        float floatValue3 = this.innerRadiusAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.innerRoundednessAnimation;
        float floatValue4 = baseKeyframeAnimation2 != null ? baseKeyframeAnimation2.getValue().floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation3 = this.outerRoundednessAnimation;
        float floatValue5 = baseKeyframeAnimation3 != null ? baseKeyframeAnimation3.getValue().floatValue() / 100.0f : 0.0f;
        if (i2 != 0) {
            f = ((floatValue2 - floatValue3) * f8) + floatValue3;
            i = i2;
            double d4 = f;
            d = d3;
            f3 = (float) (d4 * Math.cos(radians));
            f2 = (float) (d4 * Math.sin(radians));
            this.path.moveTo(f3, f2);
            d2 = radians + ((f6 * f8) / 2.0f);
        } else {
            d = d3;
            i = i2;
            double d5 = floatValue2;
            float cos = (float) (Math.cos(radians) * d5);
            float sin = (float) (d5 * Math.sin(radians));
            this.path.moveTo(cos, sin);
            d2 = radians + f7;
            f3 = cos;
            f2 = sin;
            f = 0.0f;
        }
        double ceil = Math.ceil(d) * 2.0d;
        boolean z = false;
        float f9 = f7;
        int i3 = 0;
        while (true) {
            double d6 = i3;
            if (d6 < ceil) {
                float f10 = z ? floatValue2 : floatValue3;
                int i4 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i4 == 0 || d6 != ceil - 2.0d) {
                    f9 = f9;
                } else {
                    f9 = (f6 * f8) / 2.0f;
                    f9 = f9;
                }
                if (i4 == 0 || d6 != ceil - 1.0d) {
                    f6 = f6;
                    f4 = floatValue3;
                    f5 = floatValue2;
                } else {
                    f6 = f6;
                    f5 = floatValue2;
                    f4 = floatValue3;
                    f10 = f;
                }
                double d7 = f10;
                float cos2 = (float) (d7 * Math.cos(d2));
                float sin2 = (float) (d7 * Math.sin(d2));
                if (floatValue4 == 0.0f && floatValue5 == 0.0f) {
                    this.path.lineTo(cos2, sin2);
                    f2 = sin2;
                    floatValue4 = floatValue4;
                    floatValue5 = floatValue5;
                    f = f;
                } else {
                    floatValue4 = floatValue4;
                    floatValue5 = floatValue5;
                    double atan2 = (float) (Math.atan2(f2, f3) - 1.5707963267948966d);
                    float cos3 = (float) Math.cos(atan2);
                    float sin3 = (float) Math.sin(atan2);
                    f = f;
                    f2 = sin2;
                    double atan22 = (float) (Math.atan2(sin2, cos2) - 1.5707963267948966d);
                    float cos4 = (float) Math.cos(atan22);
                    float sin4 = (float) Math.sin(atan22);
                    float f11 = z ? floatValue4 : floatValue5;
                    float f12 = z ? floatValue5 : floatValue4;
                    float f13 = z ? f4 : f5;
                    float f14 = z ? f5 : f4;
                    float f15 = f13 * f11 * POLYSTAR_MAGIC_NUMBER;
                    float f16 = cos3 * f15;
                    float f17 = f15 * sin3;
                    float f18 = f14 * f12 * POLYSTAR_MAGIC_NUMBER;
                    float f19 = cos4 * f18;
                    float f20 = f18 * sin4;
                    if (i != 0) {
                        if (i3 == 0) {
                            f16 *= f8;
                            f17 *= f8;
                        } else if (d6 == ceil - 1.0d) {
                            f19 *= f8;
                            f20 *= f8;
                        }
                    }
                    this.path.cubicTo(f3 - f16, f2 - f17, cos2 + f19, f2 + f20, cos2, f2);
                }
                d2 += f9;
                z = !z;
                i3++;
                f3 = cos2;
                floatValue2 = f5;
                floatValue3 = f4;
            } else {
                PointF value = this.positionAnimation.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
        }
    }

    private void createPolygonPath() {
        double d;
        double d2;
        int i;
        int floor = (int) Math.floor(this.pointsAnimation.getValue().floatValue());
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.rotationAnimation;
        double radians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d3 = floor;
        float floatValue = this.outerRoundednessAnimation.getValue().floatValue() / 100.0f;
        float floatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        double d4 = floatValue2;
        float cos = (float) (Math.cos(radians) * d4);
        float sin = (float) (Math.sin(radians) * d4);
        this.path.moveTo(cos, sin);
        double d5 = (float) (6.283185307179586d / d3);
        double d6 = radians + d5;
        double ceil = Math.ceil(d3);
        int i2 = 0;
        while (i2 < ceil) {
            float cos2 = (float) (Math.cos(d6) * d4);
            float sin2 = (float) (d4 * Math.sin(d6));
            if (floatValue != 0.0f) {
                d4 = d4;
                i = i2;
                d2 = d6;
                double atan2 = (float) (Math.atan2(sin, cos) - 1.5707963267948966d);
                float cos3 = (float) Math.cos(atan2);
                d = d5;
                double atan22 = (float) (Math.atan2(sin2, cos2) - 1.5707963267948966d);
                float f = floatValue2 * floatValue * POLYGON_MAGIC_NUMBER;
                this.path.cubicTo(cos - (cos3 * f), sin - (((float) Math.sin(atan2)) * f), cos2 + (((float) Math.cos(atan22)) * f), sin2 + (f * ((float) Math.sin(atan22))), cos2, sin2);
            } else {
                d2 = d6;
                d4 = d4;
                d = d5;
                i = i2;
                this.path.lineTo(cos2, sin2);
            }
            d6 = d2 + d;
            i2 = i + 1;
            sin = sin2;
            cos = cos2;
            ceil = ceil;
            d5 = d;
        }
        PointF value = this.positionAnimation.getValue();
        this.path.offset(value.x, value.y);
        this.path.close();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, @Nullable LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2;
        if (t == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_RADIUS && (baseKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (baseKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }
}
