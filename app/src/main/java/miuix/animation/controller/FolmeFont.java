package miuix.animation.controller;

import android.widget.TextView;
import miuix.animation.Folme;
import miuix.animation.IAnimTarget;
import miuix.animation.IVarFontStyle;
import miuix.animation.ViewTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.font.FontWeightProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class FolmeFont extends FolmeBase implements IVarFontStyle {
    private FontWeightProperty b;
    private int c;
    private AnimConfig d = new AnimConfig();
    private boolean e;

    /* loaded from: classes5.dex */
    public enum FontType {
        INIT,
        TARGET
    }

    public FolmeFont() {
        super(new IAnimTarget[0]);
        this.d.setEase(EaseManager.getStyle(0, 350.0f, 0.9f, 0.86f));
    }

    @Override // miuix.animation.IVarFontStyle
    public IVarFontStyle useAt(TextView textView, int i, int i2) {
        this.a = new FolmeState(Folme.getTarget(textView, ViewTarget.sCreator));
        this.b = new FontWeightProperty(textView, i);
        this.c = i2;
        this.a.getState(FontType.INIT).add(this.b, i2);
        this.e = false;
        return this;
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        this.a = null;
        this.b = null;
        this.c = 0;
    }

    @Override // miuix.animation.IVarFontStyle
    public IVarFontStyle to(int i, AnimConfig... animConfigArr) {
        if (this.a != null) {
            if (!this.e) {
                this.e = true;
                this.a.setTo(FontType.INIT);
            }
            AnimConfig[] animConfigArr2 = (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.d);
            if (this.c == i) {
                this.a.to(FontType.INIT, animConfigArr2);
            } else {
                this.a.getState(FontType.TARGET).add(this.b, i);
                this.a.to(FontType.TARGET, animConfigArr2);
            }
        }
        return this;
    }

    @Override // miuix.animation.IVarFontStyle
    public IVarFontStyle setTo(int i) {
        if (this.a != null) {
            this.a.getState(FontType.TARGET).add(this.b, i);
            this.a.setTo(FontType.TARGET);
        }
        return this;
    }

    @Override // miuix.animation.IVarFontStyle
    public IVarFontStyle fromTo(int i, int i2, AnimConfig... animConfigArr) {
        if (this.a != null) {
            this.a.getState(FontType.INIT).add(this.b, i);
            this.a.getState(FontType.TARGET).add(this.b, i2);
            this.a.fromTo(FontType.INIT, FontType.TARGET, animConfigArr);
        }
        return this;
    }
}
