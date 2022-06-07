package miuix.animation.controller;

import miuix.animation.IAnimTarget;
import miuix.animation.IStateContainer;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.FloatProperty;

/* loaded from: classes5.dex */
public abstract class FolmeBase implements IStateContainer {
    IFolmeStateStyle a;

    public FolmeBase(IAnimTarget... iAnimTargetArr) {
        this.a = StateComposer.composeStyle(iAnimTargetArr);
    }

    @Override // miuix.animation.IStateContainer
    public void clean() {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.clean();
        }
    }

    @Override // miuix.animation.ICancelableStyle
    public void end(Object... objArr) {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.end(objArr);
        }
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel() {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.cancel();
        }
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel(FloatProperty... floatPropertyArr) {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.cancel(floatPropertyArr);
        }
    }

    @Override // miuix.animation.ICancelableStyle
    public void cancel(String... strArr) {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.cancel(strArr);
        }
    }

    @Override // miuix.animation.IStateContainer
    public void enableDefaultAnim(boolean z) {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.enableDefaultAnim(z);
        }
    }

    @Override // miuix.animation.IStateContainer
    public void addConfig(Object obj, AnimConfig... animConfigArr) {
        IFolmeStateStyle iFolmeStateStyle = this.a;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.addConfig(obj, animConfigArr);
        }
    }
}
