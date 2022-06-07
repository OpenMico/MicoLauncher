package miuix.animation.internal;

import java.util.Collection;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.ListenerNotifier;
import miuix.animation.listener.UpdateInfo;

/* loaded from: classes5.dex */
public class NotifyManager {
    ListenerNotifier a;
    ListenerNotifier b;
    IAnimTarget c;
    private AnimConfig d = new AnimConfig();

    public NotifyManager(IAnimTarget iAnimTarget) {
        this.c = iAnimTarget;
        this.a = new ListenerNotifier(iAnimTarget);
        this.b = new ListenerNotifier(iAnimTarget);
    }

    public void setToNotify(AnimState animState, AnimConfigLink animConfigLink) {
        if (animConfigLink != null) {
            Object tag = animState.getTag();
            this.d.copy(animState.getConfig());
            animConfigLink.addTo(this.d);
            if (!this.a.addListeners(tag, this.d)) {
                this.d.clear();
                return;
            }
            this.a.notifyBegin(tag, tag);
            Collection<UpdateInfo> values = this.c.animManager.d.values();
            this.a.notifyPropertyBegin(tag, tag, values);
            this.a.notifyUpdate(tag, tag, values);
            this.a.notifyPropertyEnd(tag, tag, values);
            this.a.notifyEndAll(tag, tag);
            this.a.removeListeners(tag);
            this.d.clear();
        }
    }

    public ListenerNotifier getNotifier() {
        return this.b;
    }
}
