package miuix.animation.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import miuix.animation.IAnimTarget;
import miuix.animation.ViewTarget;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.styles.ForegroundColorStyle;
import miuix.animation.utils.LogUtils;

/* loaded from: classes5.dex */
public final class TargetHandler extends Handler {
    public static final int ANIM_MSG_END = 2;
    public static final int ANIM_MSG_REMOVE_WAIT = 3;
    public static final int ANIM_MSG_REPLACED = 5;
    public static final int ANIM_MSG_START_TAG = 0;
    public static final int ANIM_MSG_UPDATE_LISTENER = 4;
    private final IAnimTarget a;
    private final List<TransitionInfo> b = new ArrayList();
    public final long threadId = Thread.currentThread().getId();

    public TargetHandler(IAnimTarget iAnimTarget) {
        this.a = iAnimTarget;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.os.Handler
    public void handleMessage(@NonNull Message message) {
        int i = message.what;
        if (i != 0) {
            switch (i) {
                case 2:
                    TransitionInfo remove = TransitionInfo.a.remove(Integer.valueOf(message.arg1));
                    if (remove == null) {
                        remove = (TransitionInfo) message.obj;
                    }
                    if (remove != null) {
                        a(remove, message.arg2);
                        break;
                    }
                    break;
                case 3:
                    this.a.animManager.f.clear();
                    return;
                case 4:
                    TransitionInfo remove2 = TransitionInfo.a.remove(Integer.valueOf(message.arg1));
                    if (remove2 != null) {
                        this.a.getNotifier().removeListeners(remove2.e);
                        this.a.getNotifier().addListeners(remove2.e, remove2.f);
                        return;
                    }
                    return;
                case 5:
                    break;
                default:
                    return;
            }
            TransitionInfo remove3 = TransitionInfo.a.remove(Integer.valueOf(message.arg1));
            if (remove3 != null) {
                b(remove3);
                return;
            }
            return;
        }
        TransitionInfo remove4 = TransitionInfo.a.remove(Integer.valueOf(message.arg1));
        if (remove4 != null) {
            a(remove4);
        }
    }

    public boolean isInTargetThread() {
        return Looper.myLooper() == getLooper();
    }

    private void a(TransitionInfo transitionInfo) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug(">>> onStart, " + this.a + ", info.key = " + transitionInfo.e, new Object[0]);
        }
        transitionInfo.c.getNotifier().addListeners(transitionInfo.e, transitionInfo.f);
        transitionInfo.c.getNotifier().notifyBegin(transitionInfo.e, transitionInfo.d);
        List<UpdateInfo> list = transitionInfo.j;
        if (!list.isEmpty() && list.size() <= 4000) {
            transitionInfo.c.getNotifier().notifyPropertyBegin(transitionInfo.e, transitionInfo.d, list);
        }
        a(transitionInfo, true);
    }

    private static void a(TransitionInfo transitionInfo, boolean z) {
        if (transitionInfo.a() <= 4000) {
            for (UpdateInfo updateInfo : transitionInfo.j) {
                if (updateInfo.property == ViewPropertyExt.FOREGROUND) {
                    if (z) {
                        ForegroundColorStyle.start(transitionInfo.c, updateInfo);
                    } else {
                        ForegroundColorStyle.end(transitionInfo.c, updateInfo);
                    }
                }
            }
        }
    }

    public void update(boolean z) {
        this.a.animManager.a(this.b);
        for (TransitionInfo transitionInfo : this.b) {
            a(z, transitionInfo);
        }
        this.b.clear();
    }

    private void a(boolean z, TransitionInfo transitionInfo) {
        ArrayList arrayList = new ArrayList(transitionInfo.j);
        if (!arrayList.isEmpty()) {
            a(arrayList);
            if (!arrayList.isEmpty()) {
                a(transitionInfo.c, transitionInfo.e, transitionInfo.d, arrayList, z);
            }
        }
    }

    private static void a(IAnimTarget iAnimTarget, Object obj, Object obj2, List<UpdateInfo> list, boolean z) {
        if (!z || (iAnimTarget instanceof ViewTarget)) {
            a(iAnimTarget, list);
        }
        if (list.size() > 40000) {
            iAnimTarget.getNotifier().notifyMassUpdate(obj, obj2);
            return;
        }
        iAnimTarget.getNotifier().notifyPropertyEnd(obj, obj2, list);
        iAnimTarget.getNotifier().notifyUpdate(obj, obj2, list);
    }

    private void a(TransitionInfo transitionInfo, int i) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("<<< onEnd, " + this.a + ", info.key = " + transitionInfo.e, new Object[0]);
        }
        a(false, transitionInfo);
        a(transitionInfo, false);
        if (i == 4) {
            transitionInfo.c.getNotifier().notifyCancelAll(transitionInfo.e, transitionInfo.d);
        } else {
            transitionInfo.c.getNotifier().notifyEndAll(transitionInfo.e, transitionInfo.d);
        }
        transitionInfo.c.getNotifier().removeListeners(transitionInfo.e);
    }

    private void b(TransitionInfo transitionInfo) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("<<< onReplaced, " + this.a + ", info.key = " + transitionInfo.e, new Object[0]);
        }
        if (transitionInfo.a() <= 4000) {
            this.a.getNotifier().notifyPropertyEnd(transitionInfo.e, transitionInfo.d, transitionInfo.j);
        }
        this.a.getNotifier().notifyCancelAll(transitionInfo.e, transitionInfo.d);
        this.a.getNotifier().removeListeners(transitionInfo.e);
    }

    private static void a(IAnimTarget iAnimTarget, List<UpdateInfo> list) {
        for (UpdateInfo updateInfo : list) {
            if (!AnimValueUtils.isInvalid(updateInfo.animInfo.value)) {
                updateInfo.setTargetValue(iAnimTarget);
            }
        }
    }

    private static void a(List<UpdateInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (UpdateInfo updateInfo : list) {
            if (AnimValueUtils.isInvalid(updateInfo.animInfo.value)) {
                arrayList.add(updateInfo);
            }
        }
        if (arrayList.size() > 0) {
            list.removeAll(arrayList);
        }
    }
}
