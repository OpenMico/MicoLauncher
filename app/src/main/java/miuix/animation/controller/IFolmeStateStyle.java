package miuix.animation.controller;

import miuix.animation.IAnimTarget;
import miuix.animation.IStateStyle;

/* loaded from: classes5.dex */
public interface IFolmeStateStyle extends IStateStyle {
    void addState(AnimState animState);

    AnimState getState(Object obj);

    IAnimTarget getTarget();
}
