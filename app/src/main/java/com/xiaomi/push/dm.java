package com.xiaomi.push;

/* loaded from: classes4.dex */
public class dm {
    private static volatile dm b;
    private dl a;

    public static dm a() {
        if (b == null) {
            synchronized (dm.class) {
                if (b == null) {
                    b = new dm();
                }
            }
        }
        return b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public dl m824a() {
        return this.a;
    }

    public void a(dl dlVar) {
        this.a = dlVar;
    }
}
