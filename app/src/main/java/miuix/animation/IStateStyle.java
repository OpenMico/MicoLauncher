package miuix.animation;

import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public interface IStateStyle extends IStateContainer {
    IStateStyle add(String str, float f);

    IStateStyle add(String str, float f, long j);

    IStateStyle add(String str, int i);

    IStateStyle add(String str, int i, long j);

    IStateStyle add(FloatProperty floatProperty, float f);

    IStateStyle add(FloatProperty floatProperty, float f, long j);

    IStateStyle add(FloatProperty floatProperty, int i);

    IStateStyle add(FloatProperty floatProperty, int i, long j);

    IStateStyle addInitProperty(String str, float f);

    IStateStyle addInitProperty(String str, int i);

    IStateStyle addInitProperty(FloatProperty floatProperty, float f);

    IStateStyle addInitProperty(FloatProperty floatProperty, int i);

    IStateStyle addListener(TransitionListener transitionListener);

    IStateStyle autoSetTo(Object... objArr);

    IStateStyle fromTo(Object obj, Object obj2, AnimConfig... animConfigArr);

    AnimState getCurrentState();

    float getPredictFriction(FloatProperty floatProperty, float f);

    float getPredictValue(FloatProperty floatProperty, float... fArr);

    long predictDuration(Object... objArr);

    IStateStyle removeListener(TransitionListener transitionListener);

    IStateStyle set(Object obj);

    IStateStyle setConfig(AnimConfig animConfig, FloatProperty... floatPropertyArr);

    IStateStyle setEase(int i, float... fArr);

    IStateStyle setEase(FloatProperty floatProperty, int i, float... fArr);

    IStateStyle setEase(EaseManager.EaseStyle easeStyle, FloatProperty... floatPropertyArr);

    IStateStyle setFlags(long j);

    IStateStyle setTo(Object obj);

    IStateStyle setTo(Object obj, AnimConfig... animConfigArr);

    IStateStyle setTo(Object... objArr);

    IStateStyle setTransitionFlags(long j, FloatProperty... floatPropertyArr);

    IStateStyle setup(Object obj);

    IStateStyle then(Object obj, AnimConfig... animConfigArr);

    IStateStyle then(Object... objArr);

    IStateStyle to(Object obj, AnimConfig... animConfigArr);

    IStateStyle to(Object... objArr);

    IStateStyle to(AnimConfig... animConfigArr);
}
