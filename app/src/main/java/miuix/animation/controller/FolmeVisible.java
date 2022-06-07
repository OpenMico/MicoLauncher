package miuix.animation.controller;

import java.util.Collection;
import miuix.animation.IAnimTarget;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class FolmeVisible extends FolmeBase implements IVisibleStyle {
    private boolean b;
    private boolean c;
    private boolean d;
    private final AnimConfig e = new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.animation.controller.FolmeVisible.1
        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj, Collection<UpdateInfo> collection) {
            if (obj.equals(IVisibleStyle.VisibleType.SHOW) && FolmeVisible.this.d) {
                AnimState.alignState(FolmeVisible.this.a.getState(IVisibleStyle.VisibleType.HIDE), collection);
            }
        }
    });

    public FolmeVisible(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        useAutoAlpha(true);
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        this.c = false;
        this.b = false;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setBound(int i, int i2, int i3, int i4) {
        this.d = true;
        this.a.getState(IVisibleStyle.VisibleType.SHOW).add(ViewProperty.X, i).add(ViewProperty.Y, i2).add(ViewProperty.WIDTH, i3).add(ViewProperty.HEIGHT, i4);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle useAutoAlpha(boolean z) {
        ViewProperty viewProperty = ViewProperty.AUTO_ALPHA;
        ViewProperty viewProperty2 = ViewProperty.ALPHA;
        if (z) {
            this.a.getState(IVisibleStyle.VisibleType.SHOW).remove(viewProperty2).add(viewProperty, 1.0d);
            this.a.getState(IVisibleStyle.VisibleType.HIDE).remove(viewProperty2).add(viewProperty, 0.0d);
        } else {
            this.a.getState(IVisibleStyle.VisibleType.SHOW).remove(viewProperty).add(viewProperty2, 1.0d);
            this.a.getState(IVisibleStyle.VisibleType.HIDE).remove(viewProperty).add(viewProperty2, 0.0d);
        }
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setFlags(long j) {
        this.a.setFlags(j);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setAlpha(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.a.getState(a(visibleTypeArr)).add(ViewProperty.AUTO_ALPHA, f);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setScale(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.c = true;
        double d = f;
        this.a.getState(a(visibleTypeArr)).add(ViewProperty.SCALE_Y, d).add(ViewProperty.SCALE_X, d);
        return this;
    }

    private IVisibleStyle.VisibleType a(IVisibleStyle.VisibleType... visibleTypeArr) {
        return visibleTypeArr.length > 0 ? visibleTypeArr[0] : IVisibleStyle.VisibleType.HIDE;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2) {
        return setMove(i, i2, IVisibleStyle.VisibleType.HIDE);
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.b = Math.abs(i) > 0 || Math.abs(i2) > 0;
        if (this.b) {
            this.a.getState(a(visibleTypeArr)).add(ViewProperty.X, i, 1).add(ViewProperty.Y, i2, 1);
        }
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setShowDelay(long j) {
        this.a.getState(IVisibleStyle.VisibleType.SHOW).getConfig().delay = j;
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public void show(AnimConfig... animConfigArr) {
        this.a.to(IVisibleStyle.VisibleType.SHOW, a(IVisibleStyle.VisibleType.SHOW, animConfigArr));
    }

    @Override // miuix.animation.IVisibleStyle
    public void hide(AnimConfig... animConfigArr) {
        this.a.to(IVisibleStyle.VisibleType.HIDE, a(IVisibleStyle.VisibleType.HIDE, animConfigArr));
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setShow() {
        this.a.setTo(IVisibleStyle.VisibleType.SHOW);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setHide() {
        this.a.setTo(IVisibleStyle.VisibleType.HIDE);
        return this;
    }

    private AnimConfig[] a(IVisibleStyle.VisibleType visibleType, AnimConfig... animConfigArr) {
        EaseManager.EaseStyle easeStyle;
        EaseManager.EaseStyle easeStyle2;
        EaseManager.EaseStyle easeStyle3;
        if (!this.c && !this.b) {
            this.e.setEase(visibleType == IVisibleStyle.VisibleType.SHOW ? EaseManager.getStyle(16, 300.0f) : EaseManager.getStyle(-2, 1.0f, 0.15f));
        } else if (this.c && !this.b) {
            AnimConfig animConfig = this.e;
            if (visibleType == IVisibleStyle.VisibleType.SHOW) {
                easeStyle3 = EaseManager.getStyle(-2, 0.6f, 0.35f);
            } else {
                easeStyle3 = EaseManager.getStyle(-2, 0.75f, 0.2f);
            }
            animConfig.setEase(easeStyle3);
        } else if (!this.c) {
            AnimConfig animConfig2 = this.e;
            if (visibleType == IVisibleStyle.VisibleType.SHOW) {
                easeStyle2 = EaseManager.getStyle(-2, 0.75f, 0.35f);
            } else {
                easeStyle2 = EaseManager.getStyle(-2, 0.75f, 0.25f);
            }
            animConfig2.setEase(easeStyle2);
        } else {
            AnimConfig animConfig3 = this.e;
            if (visibleType == IVisibleStyle.VisibleType.SHOW) {
                easeStyle = EaseManager.getStyle(-2, 0.65f, 0.35f);
            } else {
                easeStyle = EaseManager.getStyle(-2, 0.75f, 0.25f);
            }
            animConfig3.setEase(easeStyle);
        }
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.e);
    }
}
