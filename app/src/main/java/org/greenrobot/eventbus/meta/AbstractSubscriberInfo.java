package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.SubscriberMethod;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes5.dex */
public abstract class AbstractSubscriberInfo implements SubscriberInfo {
    private final Class a;
    private final Class<? extends SubscriberInfo> b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractSubscriberInfo(Class cls, Class<? extends SubscriberInfo> cls2, boolean z) {
        this.a = cls;
        this.b = cls2;
        this.c = z;
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public Class getSubscriberClass() {
        return this.a;
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public SubscriberInfo getSuperSubscriberInfo() {
        Class<? extends SubscriberInfo> cls = this.b;
        if (cls == null) {
            return null;
        }
        try {
            return (SubscriberInfo) cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public boolean shouldCheckSuperclass() {
        return this.c;
    }

    protected SubscriberMethod createSubscriberMethod(String str, Class<?> cls) {
        return createSubscriberMethod(str, cls, ThreadMode.POSTING, 0, false);
    }

    protected SubscriberMethod createSubscriberMethod(String str, Class<?> cls, ThreadMode threadMode) {
        return createSubscriberMethod(str, cls, threadMode, 0, false);
    }

    protected SubscriberMethod createSubscriberMethod(String str, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        try {
            return new SubscriberMethod(this.a.getDeclaredMethod(str, cls), cls, threadMode, i, z);
        } catch (NoSuchMethodException e) {
            throw new EventBusException("Could not find subscriber method in " + this.a + ". Maybe a missing ProGuard rule?", e);
        }
    }
}
