package org.greenrobot.eventbus;

import java.lang.reflect.Method;

/* loaded from: classes5.dex */
public class SubscriberMethod {
    final Method a;
    final ThreadMode b;
    final Class<?> c;
    final int d;
    final boolean e;
    String f;

    public SubscriberMethod(Method method, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        this.a = method;
        this.b = threadMode;
        this.c = cls;
        this.d = i;
        this.e = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SubscriberMethod)) {
            return false;
        }
        a();
        SubscriberMethod subscriberMethod = (SubscriberMethod) obj;
        subscriberMethod.a();
        return this.f.equals(subscriberMethod.f);
    }

    private synchronized void a() {
        if (this.f == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(this.a.getDeclaringClass().getName());
            sb.append('#');
            sb.append(this.a.getName());
            sb.append('(');
            sb.append(this.c.getName());
            this.f = sb.toString();
        }
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
