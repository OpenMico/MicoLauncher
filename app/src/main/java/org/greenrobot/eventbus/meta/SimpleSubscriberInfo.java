package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.SubscriberMethod;

/* loaded from: classes5.dex */
public class SimpleSubscriberInfo extends AbstractSubscriberInfo {
    private final SubscriberMethodInfo[] a;

    public SimpleSubscriberInfo(Class cls, boolean z, SubscriberMethodInfo[] subscriberMethodInfoArr) {
        super(cls, null, z);
        this.a = subscriberMethodInfoArr;
    }

    @Override // org.greenrobot.eventbus.meta.SubscriberInfo
    public synchronized SubscriberMethod[] getSubscriberMethods() {
        SubscriberMethod[] subscriberMethodArr;
        int length = this.a.length;
        subscriberMethodArr = new SubscriberMethod[length];
        for (int i = 0; i < length; i++) {
            SubscriberMethodInfo subscriberMethodInfo = this.a[i];
            subscriberMethodArr[i] = createSubscriberMethod(subscriberMethodInfo.a, subscriberMethodInfo.c, subscriberMethodInfo.b, subscriberMethodInfo.d, subscriberMethodInfo.e);
        }
        return subscriberMethodArr;
    }
}
