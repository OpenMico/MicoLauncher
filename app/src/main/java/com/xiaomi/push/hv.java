package com.xiaomi.push;

/* loaded from: classes4.dex */
public enum hv {
    RegIdExpired(0),
    PackageUnregistered(1),
    Init(2);
    

    /* renamed from: a  reason: collision with other field name */
    private final int f89a;

    hv(int i) {
        this.f89a = i;
    }

    public static hv a(int i) {
        switch (i) {
            case 0:
                return RegIdExpired;
            case 1:
                return PackageUnregistered;
            case 2:
                return Init;
            default:
                return null;
        }
    }

    public int a() {
        return this.f89a;
    }
}
