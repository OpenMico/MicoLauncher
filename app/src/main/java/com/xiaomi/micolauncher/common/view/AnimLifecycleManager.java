package com.xiaomi.micolauncher.common.view;

import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.R;
import java.util.LinkedList;
import java.util.Queue;
import miuix.animation.Folme;

@MainThread
/* loaded from: classes3.dex */
public class AnimLifecycleManager implements MessageQueue.IdleHandler, View.OnAttachStateChangeListener {
    private static AnimLifecycleManager a;
    private boolean b = false;
    private final Queue<View> c = new LinkedList();

    private AnimLifecycleManager() {
    }

    private static AnimLifecycleManager a() {
        if (a == null) {
            a = new AnimLifecycleManager();
        }
        return a;
    }

    public static void repeatOnAttach(@NonNull View view, AnimConfigurator animConfigurator) {
        AnimLifecycleManager a2 = a();
        view.setTag(R.id.mico_anim_configurator, animConfigurator);
        view.removeOnAttachStateChangeListener(a2);
        view.addOnAttachStateChangeListener(a2);
    }

    private void a(@NonNull View view) {
        AnimConfigurator animConfigurator = (AnimConfigurator) view.getTag(R.id.mico_anim_configurator);
        Boolean bool = (Boolean) view.getTag(R.id.mico_anim_configured);
        if (animConfigurator == null) {
            return;
        }
        if ((bool == null || !bool.booleanValue()) && !this.c.contains(view)) {
            this.c.add(view);
            if (!this.b) {
                Looper.getMainLooper().getQueue().addIdleHandler(this);
                this.b = true;
            }
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        a(view);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        b(view);
    }

    @Override // android.os.MessageQueue.IdleHandler
    public boolean queueIdle() {
        View poll;
        long currentTimeMillis = System.currentTimeMillis();
        int i = 0;
        while (i < 12 && (poll = this.c.poll()) != null) {
            AnimConfigurator animConfigurator = (AnimConfigurator) poll.getTag(R.id.mico_anim_configurator);
            if (animConfigurator != null && poll.isAttachedToWindow()) {
                animConfigurator.config(poll);
                poll.setTag(R.id.mico_anim_configured, true);
                i++;
            }
        }
        L.base.d("%s - Execute %d idle tasks costs %dms, %d remaining", "AnimIdleTaskManager", Integer.valueOf(i), Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Integer.valueOf(this.c.size()));
        if (this.c.isEmpty()) {
            Looper.getMainLooper().getQueue().removeIdleHandler(this);
            this.b = false;
        }
        return true;
    }

    private void b(View view) {
        Folme.clean(view);
        view.setTag(R.id.mico_anim_configured, false);
        if (!this.c.isEmpty() && this.c.remove(view) && this.c.isEmpty()) {
            Looper.getMainLooper().getQueue().removeIdleHandler(this);
            this.b = false;
        }
    }
}
